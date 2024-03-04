package com.todoapplication.todo.services.userservices;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapplication.todo.domain.User;
import com.todoapplication.todo.exceptions.UserAlreadyExistException;
import com.todoapplication.todo.exceptions.UserNotFoundException;
import com.todoapplication.todo.proxy.IUserProxy;
import com.todoapplication.todo.repository.IUserRepository;

@Service
public class UserService implements IUserService{
	
	private final IUserRepository userRepository;
	private final IUserProxy userProxy;
	
	@Autowired
	public UserService(IUserRepository userRepository, IUserProxy userProxy) {
		this.userRepository = userRepository;
		this.userProxy = userProxy;
	}


	@Override
	public User registerUser(User user) throws UserAlreadyExistException{
		Optional<User> fetchedUser = userRepository.findById(user.getEmailId());
		User newlyAddedUser;
		if(fetchedUser.isPresent()) {
			throw new UserAlreadyExistException("User with the given EmailId Already Exist");
		}
		else {
			newlyAddedUser = userRepository.save(user);
			userProxy.registerNewUser(newlyAddedUser);
		}
		return newlyAddedUser;
	}


	@Override
	public User updateUserDetails(String emailId,User user) throws UserNotFoundException{
		Optional<User> fetchedUser = userRepository.findById(emailId);
		User updatedUser;
		if(fetchedUser.isEmpty()) {
			throw new UserNotFoundException("User with the given EmailId didn't Exist");
		}
		else {
			updatedUser = fetchedUser.get();
			updatedUser.setUserName(user.getUserName());
			updatedUser.setPhoneNumber(user.getPhoneNumber());
			updatedUser.setAge(user.getAge());
		}
		userRepository.save(updatedUser);
		return updatedUser;
	}


	@Override
	public boolean deleteUser(String emailId) throws UserNotFoundException {
		Optional<User> fetchedUser = userRepository.findById(emailId);
		User user = fetchedUser.get();
		boolean flag = false;
		if(fetchedUser.isEmpty()) {
			throw new UserNotFoundException("User with the given EmailId didn't Exist");
		}
		else {
			userRepository.deleteById(emailId);
			userProxy.deleteUserAccount(user);
			flag = true;
		}
		return flag;
	}
}
