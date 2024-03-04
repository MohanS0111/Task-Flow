package com.todoapplication.todo.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.todoapplication.todo.domain.User;

@FeignClient(name="userauthentication", url="localhost:8083")
public interface IUserProxy {
	
	@PostMapping("/api/v2/register")
	public ResponseEntity<?> registerNewUser(@RequestBody User user);

	@DeleteMapping("api/v2/deleteAccount")
	public ResponseEntity<?> deleteUserAccount(@RequestBody User user);
}
