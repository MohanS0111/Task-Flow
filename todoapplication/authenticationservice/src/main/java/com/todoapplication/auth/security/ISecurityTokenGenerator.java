package com.todoapplication.auth.security;

import java.util.Map;
import com.todoapplication.auth.domain.User;


public interface ISecurityTokenGenerator {
	
	public Map<String,String> generateToken(User user);

}
