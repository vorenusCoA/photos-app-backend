package com.example.photos.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JWTProvider {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private int expiration;
	
	@Value("${jwt.issuer}")
	private String issuer;

	public String generateToken(String email) throws JWTProviderException {

		String token = "";
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			token = JWT.create()
					   .withSubject(email)
					   .withIssuer(issuer)
					   .withIssuedAt(new Date(System.currentTimeMillis()))
					   .withNotBefore(new Date(System.currentTimeMillis()))
					   .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
					   .sign(algorithm);
		} catch (JWTCreationException e){
		    throw new JWTProviderException("Can't create JWT", e);
		}

		return token;
	}
	
	public String verifyTokenAndGetEmail(String jwt) throws JWTProviderException {
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm)
									  .withIssuer(issuer)
									  .build();
			
			DecodedJWT decodedJWT = verifier.verify(jwt);

			return decodedJWT.getSubject();
			
		} catch (JWTVerificationException e){
			throw new JWTProviderException("Can't verify JWT", e);
		}
		
	}

}
