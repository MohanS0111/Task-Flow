package com.todoapplication.auth.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapplication.auth.domain.User;
import com.todoapplication.auth.exception.UserAlreadyExistException;
import com.todoapplication.auth.exception.UserNotFoundException;
import com.todoapplication.auth.security.ISecurityTokenGenerator;
import com.todoapplication.auth.service.IUserService;

@RestController
@RequestMapping("/api/v2")
public class UserController {
	
	private final IUserService userservice;
	private final ISecurityTokenGenerator securityTokenGenerator;
	
	@Autowired
	public UserController(IUserService userservice, ISecurityTokenGenerator securityTokenGenerator) {
		this.userservice = userservice;
		this.securityTokenGenerator = securityTokenGenerator;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerNewUser(@RequestBody User user){
		ResponseEntity<?> responseEntity;
		try {
			responseEntity = new ResponseEntity<>(userservice.registerUser(user),HttpStatus.ACCEPTED);
		}
		catch(UserAlreadyExistException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}
		return responseEntity;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user){
		ResponseEntity<?> responseEntity;
		try {
			User fecthedUser = userservice.loginUser(user);
			Map<String, String> map = securityTokenGenerator.generateToken(fecthedUser);
			responseEntity = new ResponseEntity<>(map,HttpStatus.ACCEPTED);
		}
		catch(UserNotFoundException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}
		return responseEntity;
	}
	
	@DeleteMapping("/deleteAccount")
	public ResponseEntity<?> deleteUserAccount(@RequestBody User user){
		ResponseEntity<?> responseEntity;
		try {
			responseEntity = new ResponseEntity<>(userservice.deleteUser(user.getEmailId()), HttpStatus.OK);
		}
		catch(UserNotFoundException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
		}
		return responseEntity;
		
	}

}
