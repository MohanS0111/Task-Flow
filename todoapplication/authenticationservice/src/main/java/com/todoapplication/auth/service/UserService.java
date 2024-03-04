package com.todoapplication.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapplication.auth.domain.User;
import com.todoapplication.auth.exception.UserAlreadyExistException;
import com.todoapplication.auth.exception.UserNotFoundException;
import com.todoapplication.auth.repository.IUserRepository;

@Service
public class UserService implements IUserService {
	
	private final IUserRepository userRepository;
	
	@Autowired
	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User registerUser(User user) throws UserAlreadyExistException {
		Optional<User> fetchedUser = userRepository.findById(user.getEmailId());
		User newlyAddedUser;
		if(fetchedUser.isPresent()) {
			throw new UserAlreadyExistException("User with the given emailId Already Exist");
		}
		else {
			newlyAddedUser = userRepository.save(user);
		}
		
		return newlyAddedUser;
	}

	@Override
	public User loginUser(User user) throws UserNotFoundException {
		Optional<User> fetchedUser = userRepository.findByEmailIdAndPassword(user.getEmailId(),user.getPassword());
		User loggedInUser;
		if(fetchedUser.isEmpty()) {
			throw new UserNotFoundException("User with the given emailId and Password did't Exist");
		}
		else {
			loggedInUser = fetchedUser.get();
		}
		return loggedInUser;
	}

	@Override
	public boolean deleteUser(String emailId) throws UserNotFoundException {
		Optional<User> fetchedUser = userRepository.findById(emailId);
		boolean flag = false;
		if(fetchedUser.isEmpty()) {
			throw new UserNotFoundException("User with the given EmailId didn't Exist");
		}
		else {
			userRepository.deleteById(emailId);
			flag = true;
		}
		return flag;
	}

}
