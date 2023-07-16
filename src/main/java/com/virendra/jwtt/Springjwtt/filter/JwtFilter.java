package com.virendra.jwtt.Springjwtt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.virendra.jwtt.Springjwtt.service.UserService;
import com.virendra.jwtt.Springjwtt.utility.JWTUtility;

// Es filter ko lagane se pahle mera token Directly Genrate ho raha hai, but ham filter se pass karna chte hai so ham
//filter class me code likh rahe hai to make filter
@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		// filter karne ke lia ham yaha ye Authrization heder me kuch pass kare ge
		
		String authorization = httpServletRequest.getHeader("Authorization");
		String token = null;
		String userName = null;
		
		
		//ye Condition add karna hota hai... check karne ke lia
		if(null != authorization && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7); //Beror ->ke sare number ko Skip karne ke lia karte hai ye 7 
            userName = jwtUtility.getUsernameFromToken(token);
        }

        if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails
                    = userService.loadUserByUsername(userName);

            if(jwtUtility.validateToken(token,userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

		
		
	}

}
