package com.ebeauty;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	
		String redirectURL = request.getContextPath();
		
		if(userDetails.hasRole("CLIENT")){
			redirectURL	+= "/main";
		}else if (userDetails.hasRole("EXPERT")) {
			redirectURL += "/expertmain";
		}else if (userDetails.hasRole("ADMIN")) {
			redirectURL += "/ebeauty-admin.ca";
		}

		response.sendRedirect(redirectURL);
	}
	
}
