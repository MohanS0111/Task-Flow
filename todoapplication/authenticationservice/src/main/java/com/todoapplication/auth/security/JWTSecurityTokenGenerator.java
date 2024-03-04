package com.todoapplication.auth.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.todoapplication.auth.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JWTSecurityTokenGenerator implements ISecurityTokenGenerator{

	@Override
	public Map<String, String> generateToken(User user) {
		String token = Jwts.builder().setSubject(user.getEmailId())
				       .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "EncodedKey")
				       .compact();
		Map<String,String> map = new HashMap<>();
		map.put("Token", token);
		map.put("Message", "Login Successfull");
		return map;
	}

}
