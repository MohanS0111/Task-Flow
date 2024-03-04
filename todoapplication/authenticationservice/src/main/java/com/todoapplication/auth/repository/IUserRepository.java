package com.todoapplication.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.todoapplication.auth.domain.User;

@Repository
public interface IUserRepository extends JpaRepository<User,String> {
	
	public Optional<User> findByEmailIdAndPassword(String emailId, String password);
	
}
