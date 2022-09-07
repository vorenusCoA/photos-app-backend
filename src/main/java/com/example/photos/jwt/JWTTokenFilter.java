package com.example.photos.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.photos.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTTokenFilter extends OncePerRequestFilter {

	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final JWTProvider jwtProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = getToken(request);
		if (token != null) {
			String email;
			try {
				email = jwtProvider.verifyTokenAndGetEmail(token);
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (JWTProviderException e) {
				e.printStackTrace();
			}
		}

		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest req) {
		String authReq = req.getHeader("Authorization");
		if (authReq != null && authReq.startsWith("Bearer "))
			return authReq.replace("Bearer ", "");
		return null;
	}

}
