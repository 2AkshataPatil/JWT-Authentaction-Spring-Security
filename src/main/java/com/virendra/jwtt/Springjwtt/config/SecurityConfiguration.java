package com.virendra.jwtt.Springjwtt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.virendra.jwtt.Springjwtt.filter.JwtFilter;
import com.virendra.jwtt.Springjwtt.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	 @Autowired
	 private UserService userService;
	 
	 
	 @Autowired // filter ko apply karne ke lia ham ye thoda kuch code add kia   
	 private JwtFilter jwtFilter;  //......filter
	 
	 

	   

	    @Override // Esko-> configure   overRide karna hai
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	        auth.userDetailsService(userService);
	    }
	    
	    
	    @Override // tumko  Esko-> authenticationManagerBean , override krana hai
	    @Bean // COntroller ko Bean provide kare ga ye
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	    	
	    	return super.authenticationManagerBean();
	    }
	    
	    
	    
	    //..........'particular'.  overRide karo..-->  configure....    api ko purmit all karne ke lia to genrate token without Credintila -> esko overRide karna pade ga
	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	
//	    	super.configure(http);
	    	
	    	// yaha pe permit and other operation karne ke lia Es method ko overRide karna padta hai
	      http.csrf()
			    	.disable()
			    	.authorizeRequests()
			    	.antMatchers("/authenticate").permitAll() // Esko permit all kar dia
			    	.anyRequest() // baki sabko Authenticate karna padega
			    	.authenticated()
			    	.and() //-> filter ko lagane ke lia karte hai eske aage //......filter
			    	.sessionManagement()   //......filter
			    	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);// ye jwt stateless ko follow karta hai  //......filter
	      
	      http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //......filter
	      
	    }
	    

}
