package com.todoapplication.todo.filter;

import java.io.IOException;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String authHeader = httpServletRequest.getHeader("Authorization");
		if(authHeader == null) {
			httpServletResponse.setStatus(httpServletResponse.SC_UNAUTHORIZED);
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			outputStream.println("Token is Missing");
			outputStream.close();
		}
		else {
			String jwtToken = authHeader.substring(7);
			String emailId = Jwts.parser().setSigningKey("EncodedKey").parseClaimsJws(jwtToken).getBody().getSubject();
			httpServletRequest.setAttribute("UserEmailId", emailId);
			chain.doFilter(request, response);
		}	
	}
}
