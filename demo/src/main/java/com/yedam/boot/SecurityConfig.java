package com.yedam.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean // <bean id="메소드명" class="리턴값">
	public PasswordEncoder bcryptPassword() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
		http.authorizeHttpRequests( (requests) -> requests
			.antMatchers("/home", "/").permitAll()
			.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN") 
			.anyRequest().authenticated() )
		.formLogin()
			.loginPage("/login")
			.usernameParameter("userid")
			.defaultSuccessUrl("/home")
			.permitAll()
			.and()
		.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/home") 
			.permitAll()
//			.and()
//		.csrf()
//			.disable()
			;
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user =
//			 User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("1234")
//				.roles("USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user);
//	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/css/**"); 
	}
	
}
