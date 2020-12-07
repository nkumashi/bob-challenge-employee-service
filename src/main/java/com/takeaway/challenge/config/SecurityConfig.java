package com.takeaway.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Naveen Kumashi
 */

@Configuration  
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)  
public class SecurityConfig extends WebSecurityConfigurerAdapter {			
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable();				
//		http
//			.authorizeRequests()
//			.anyRequest().authenticated()
//	        .and()
//	        .httpBasic()
//	        .and()
//	        .formLogin();
	}  
}
