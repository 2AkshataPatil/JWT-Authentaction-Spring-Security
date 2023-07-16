package com.virendra.jwtt.Springjwtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.virendra.jwtt.Springjwtt.model.JwtRequest;
import com.virendra.jwtt.Springjwtt.model.JwtResponse;
import com.virendra.jwtt.Springjwtt.service.UserService;
import com.virendra.jwtt.Springjwtt.utility.JWTUtility;

@RestController
public class HomeController {
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	
	
	 @GetMapping("/")
	    public String home() {
	        return "This is virendra Sing Yadav to Understand the proper deta...!!";
	    }
	 
	 
	 @PostMapping("/authenticate") // Es api ko heat karne se mera token Genrate ho jaye ga
	 public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
		 
		 try {
			 
			 authenticationManager.authenticate(
					 new UsernamePasswordAuthenticationToken(
							 jwtRequest.getUsername(),
							 jwtRequest.getPassword()
							 )
					 );
			
		} catch (BadCredentialsException e) {
			
			throw new Exception("INVALID_CREDENTIAL", e); 
		}
		 
		 final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
		 
		 final String token = jwtUtility.generateToken(userDetails);
		 
		 return new JwtResponse(token); // ye finaly token ko return kar raha hai to hamko esko Recive karna hota hai
		 
		 
	 }

}
