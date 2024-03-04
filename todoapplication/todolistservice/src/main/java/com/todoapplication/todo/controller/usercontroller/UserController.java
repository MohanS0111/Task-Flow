package com.todoapplication.todo.controller.usercontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapplication.todo.domain.User;
import com.todoapplication.todo.exceptions.UserAlreadyExistException;
import com.todoapplication.todo.exceptions.UserNotFoundException;
import com.todoapplication.todo.services.userservices.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	private final IUserService userService;

	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user){
		ResponseEntity<?> responseEntity;
		try {
			responseEntity = new ResponseEntity<>(userService.registerUser(user),HttpStatus.ACCEPTED);
		}
		catch(UserAlreadyExistException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}
		return responseEntity;
	}
	
	@PutMapping("/updateUserProfile")
	public ResponseEntity<?> updateProfile(@RequestBody User user){
		ResponseEntity<?> responseEntity;
		try {
			responseEntity = new ResponseEntity<>(userService.updateUserDetails(user.getEmailId(), user), HttpStatus.OK);
		}
		catch(UserNotFoundException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	
	@DeleteMapping("/deleteAccount")
	public ResponseEntity<?> deleteUserAccount(@RequestBody User user){
		ResponseEntity<?> responseEntity;
		try {
			responseEntity = new ResponseEntity<>(userService.deleteUser(user.getEmailId()), HttpStatus.OK);
		}
		catch(UserNotFoundException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
		}
		return responseEntity;
		
	}
}
