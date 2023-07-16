package com.virendra.jwtt.Springjwtt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // this is for getter and Setter
@AllArgsConstructor //for parametrised constructor
@NoArgsConstructor // this is for Default Constructor
public class JwtRequest {
	
	private String username;
	private String password;

}
