package com.todoapplication.auth.service;

import com.todoapplication.auth.domain.User;
import com.todoapplication.auth.exception.UserAlreadyExistException;
import com.todoapplication.auth.exception.UserNotFoundException;

public interface IUserService {
	
	public User registerUser(User user) throws UserAlreadyExistException;
	public User loginUser(User user) throws UserNotFoundException;
	public boolean deleteUser(String emailId) throws UserNotFoundException;

}
