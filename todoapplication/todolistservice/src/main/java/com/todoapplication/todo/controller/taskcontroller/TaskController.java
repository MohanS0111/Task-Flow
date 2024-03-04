package com.todoapplication.todo.controller.taskcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapplication.todo.domain.Task;
import com.todoapplication.todo.exceptions.TaskAlreadyExistsException;
import com.todoapplication.todo.exceptions.TaskNotExistException;
import com.todoapplication.todo.exceptions.UserNotFoundException;
import com.todoapplication.todo.services.taskservices.ITaskService;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/v3")
public class TaskController {
	
	private final ITaskService taskService;
	
	@Autowired
	public TaskController(ITaskService taskService) {
		this.taskService = taskService;
	}
	
	@GetMapping("/getUserDetails")
	public ResponseEntity<?> getUserDetails(HttpServletRequest httpServletRequest) {
		ResponseEntity<?> responseEntity;
		try {
			String emailId = httpServletRequest.getAttribute("UserEmailId").toString();
		    responseEntity = new ResponseEntity<>(taskService.getUser(emailId),HttpStatus.PARTIAL_CONTENT);
		}
		catch(UserNotFoundException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
		
	}
	
	@PostMapping("/addToDoTask")
	public ResponseEntity<?> addTaskToUser(@RequestBody Task task, HttpServletRequest httpServletRequest){
		ResponseEntity<?> responseEntity;
		try {
			String userEmailId = httpServletRequest.getAttribute("UserEmailId").toString();
			responseEntity = new ResponseEntity<>(taskService.addTask(userEmailId,task),HttpStatus.OK);
		}
		catch(UserNotFoundException | TaskAlreadyExistsException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	
	@PutMapping("/updateToDoTask")
	public ResponseEntity<?> updateTask(@RequestBody Task task, HttpServletRequest httpServletRequest){
		ResponseEntity<?> responseEntity;
		try {
			String userEmailId = httpServletRequest.getAttribute("UserEmailId").toString();
			responseEntity = new ResponseEntity<>(taskService.updateTask(userEmailId, task),HttpStatus.OK);
		}
		catch(UserNotFoundException | TaskNotExistException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		return responseEntity;
	}
	
	@GetMapping("/getAllToDoTask")
	public ResponseEntity<?> fetchAllTasks(HttpServletRequest httpServletRequest){
		ResponseEntity<?> responseEntity;
		try {
			String userEmailId = httpServletRequest.getAttribute("UserEmailId").toString();
			responseEntity = new ResponseEntity<>(taskService.getAllTasks(userEmailId),HttpStatus.OK);
		}
		catch(UserNotFoundException | TaskNotExistException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;	
	}
	
	@GetMapping("/getOneToDoTask/{taskName}")
	public ResponseEntity<?> fetchOneTask(@PathVariable String taskName, HttpServletRequest httpServletRequest){
		ResponseEntity<?> responseEntity;
		try {
			String userEmailId = httpServletRequest.getAttribute("UserEmailId").toString();
			responseEntity = new ResponseEntity<>(taskService.getOneTask(userEmailId, taskName), HttpStatus.OK);
		}
		catch(UserNotFoundException | TaskNotExistException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	
	@DeleteMapping("/deleteToDoTask")
	public ResponseEntity<?> deleteOneTask(@RequestBody Task task, HttpServletRequest httpServletRequest){
		ResponseEntity<?> responseEntity;
		try {
			String userEmailId = httpServletRequest.getAttribute("UserEmailId").toString();
			responseEntity = new ResponseEntity<>(taskService.deleteTask(userEmailId, task), HttpStatus.OK);
		}
		catch(UserNotFoundException | TaskNotExistException exception) {
			responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
}
