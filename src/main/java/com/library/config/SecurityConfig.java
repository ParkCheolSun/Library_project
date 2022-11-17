package com.library.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class.getName());
	

	@Autowired
	MemberService memberService;
	

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/javascript/**", "/images/**", "/lib/**", "/board/**", "/file/**");  //인증을 무시할 경로(무조건 접근 가능)
    }	

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	/*
    	http
    	.csrf()
    	.ignoringAntMatchers("/board/**");
    	*/
    	
    	
        http.authorizeRequests()  //사용자의 로그인이 필요한 요청목록 입니다.
        		.mvcMatchers("/","/login/**","/email/**").permitAll()	//권한 없이 접근 가능
                .mvcMatchers("/admin/**").hasRole("ADMIN")	// /admin 경로 접근자는 ADMIN Role일 경우만 접근가능하도록 설정
                .anyRequest().authenticated(); // 나머지 경로들은 모두 인증된 사용자만 접근 가능하도록 설정


        
        http.formLogin()  //사용자가 로그인을 하는 경우 입니다.  
                .loginPage("/login/signIn") 		//로그인 페이지 url 설정
                .defaultSuccessUrl("/")				//로그인 성공 시 이동하는 url
                .usernameParameter("id")			//로그인 시 사용할 파라미터 이름으로 id지정
                .failureUrl("/login/error")			//로그인 실패 시 이동하는 url 설정
        		.and()
        		.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/login/logout"))	//로그아웃 url 설정
                .logoutSuccessUrl("/")				//로그아웃 성공 시 이동할 url 설정
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