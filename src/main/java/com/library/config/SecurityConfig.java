package com.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.library.service.MemberService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter { 

	@Autowired
	MemberService memberService;
	

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/javascript/**", "/images/**", "/lib/**","/");  //로그인 여부를 조사 안할 요청값 입니다.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.authorizeRequests()  //사용자의 로그인이 필요한 요청목록 입니다.
        		.mvcMatchers("/css/**", "/javascript/**", "/images/**", "/lib/**", "/","/login/**","/email/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN") // /admin 경로 접근자는 ADMIN Role일 경우만 접근가능하도록 설정
                .anyRequest().authenticated(); // 나머지 경로들은 모두 인증을 요구하도록 설정
                

        
        http.formLogin()  //사용자가 로그인을 하는 경우 입니다.  
                .loginPage("/login/signIn")
                .defaultSuccessUrl("/")
                .usernameParameter("id")
                .failureUrl("/login/error")
                .permitAll();
        
        //SPA 같은 싱글페이지 어플리케이션과 연동하려면 아래처럼 CSRF값을 헤더에 포함하여 사용 할 수 있습니다!
        //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        http.logout()	//사용자가 로그아웃을 하는 경우 입니다.
                .logoutRequestMatcher(new AntPathRequestMatcher("/"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        http.exceptionHandling()
                .accessDeniedPage("/");
    }

    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    	//auth.userDetailsService(service).passwordEncoder(passwordEncoder());  //비밀번호 암호화 합니다.
    }

    
}