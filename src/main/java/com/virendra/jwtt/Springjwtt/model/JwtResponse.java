package com.virendra.jwtt.Springjwtt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	
	private String JwtToken;  // this is genrate token and return the token

}
