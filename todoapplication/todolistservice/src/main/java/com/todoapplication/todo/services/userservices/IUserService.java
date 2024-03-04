package com.todoapplication.todo.services.userservices;

import com.todoapplication.todo.domain.User;
import com.todoapplication.todo.exceptions.UserAlreadyExistException;
import com.todoapplication.todo.exceptions.UserNotFoundException;


public interface IUserService {
	public User registerUser(User user) throws UserAlreadyExistException; 
	public User updateUserDetails(String emailId,User user) throws UserNotFoundException;
	public boolean deleteUser(String emailId) throws UserNotFoundException;

}
