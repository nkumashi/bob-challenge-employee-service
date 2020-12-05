package com.takeaway.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration  
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)  
public class SecurityConfig extends WebSecurityConfigurerAdapter {			
	protected void configure(final HttpSecurity http) throws Exception {
		// since, we have our own custom csrf implementation
		// disabling the Spring Boot provided csrf support				
		http
			.authorizeRequests()
			.anyRequest().authenticated()
	        .and()
	        .httpBasic()
	        .and()
	        .formLogin();
	}  
}
