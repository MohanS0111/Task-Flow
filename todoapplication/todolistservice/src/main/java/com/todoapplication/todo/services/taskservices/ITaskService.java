package com.todoapplication.todo.services.taskservices;

import java.util.List;

import com.todoapplication.todo.domain.Task;
import com.todoapplication.todo.domain.User;
import com.todoapplication.todo.exceptions.TaskAlreadyExistsException;
import com.todoapplication.todo.exceptions.TaskNotExistException;
import com.todoapplication.todo.exceptions.UserNotFoundException;

public interface ITaskService {
	public User getUser(String emailId) throws UserNotFoundException;
	public User addTask(String emailId, Task task) throws UserNotFoundException, TaskAlreadyExistsException;
	public User updateTask(String emailId, Task task) throws UserNotFoundException, TaskNotExistException;
	public List<Task> getAllTasks(String emailId) throws UserNotFoundException, TaskNotExistException;
	public List<Task> getOneTask(String emailId, String taskName) throws UserNotFoundException, TaskNotExistException;
	public boolean deleteTask(String emailId, Task task) throws UserNotFoundException, TaskNotExistException;
}
