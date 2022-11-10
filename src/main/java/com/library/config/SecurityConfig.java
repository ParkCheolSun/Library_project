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
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/", "/newMember");  //로그인 여부를 조사 안할 요청값 입니다.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.authorizeRequests()  //사용자의 로그인이 필요한 요청목록 입니다.
                .antMatchers("/member.html*").authenticated()
                .antMatchers("/super.html*").hasAuthority("super")
                .antMatchers("/normal.html*").hasAnyAuthority("super","normal")
                .antMatchers("/**").permitAll();
                

        
        http.formLogin()  //사용자가 로그인을 하는 경우 입니다.  
                .loginPage("/login/signIn")
                .defaultSuccessUrl("/")
                .usernameParameter("id")
                .failureUrl("/login/error")
                .usernameParameter("id")
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