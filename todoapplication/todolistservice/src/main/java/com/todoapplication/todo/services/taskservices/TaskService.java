package com.todoapplication.todo.services.taskservices;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.todoapplication.todo.domain.Task;
import com.todoapplication.todo.domain.User;
import com.todoapplication.todo.exceptions.TaskAlreadyExistsException;
import com.todoapplication.todo.exceptions.TaskNotExistException;
import com.todoapplication.todo.exceptions.UserNotFoundException;
import com.todoapplication.todo.repository.IUserRepository;


@Service
public class TaskService implements ITaskService{
	
	
	private final IUserRepository userRepository;
	
	@Autowired
	public TaskService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public User getUser(String emailId) throws UserNotFoundException {
		Optional<User> fetchedUser = userRepository.findById(emailId);
		User user = fetchedUser.get();
		if(fetchedUser.isEmpty()) {
			throw new UserNotFoundException("User with the given EmailId didn't Exist");
		}
		else {
			return user;
		}
	}

	@Override
	public User addTask(String emailId, Task task) throws UserNotFoundException, TaskAlreadyExistsException {
		 Optional<User> fetchedUser = userRepository.findById(emailId);
		    User currentUser;
		    if(fetchedUser.isEmpty()) {
		        throw new UserNotFoundException("User with the given EmailId did't Exist");
		    }
		    else {
		        currentUser = fetchedUser.get();
		        List<Task> existingTasks = currentUser.getTasks();
		        if(existingTasks == null) {
		        	existingTasks = new ArrayList<>();
		            currentUser.setTasks(Arrays.asList(task));
		        }
		        else {
		            boolean flag = false;
		            for(Task iteratedTask : existingTasks) {
		                if(task.getTaskName()!= null && task.getTaskName().equalsIgnoreCase(iteratedTask.getTaskName())) {
		                    flag = true;
		                    break;
		                }
		            }
		            if(flag == true) {
		                throw new TaskAlreadyExistsException("Task with the given Name Already Exist, So If You want to add the Task Change the Task Name");
		            }
		        }
		        
		        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		        task.setTaskDate(dateFormatter.format(LocalDate.parse(task.getTaskDate(), dateFormatter)));
		        task.setTaskStartTime(timeFormatter.format(LocalTime.parse(task.getTaskStartTime(), timeFormatter)));
		        task.setTaskDueTime(timeFormatter.format(LocalTime.parse(task.getTaskDueTime(), timeFormatter)));
		        
		        task.setIsapplyToRepetitive(false);

		        if (task.getIsrepetitive()) {
		            generateRepetitiveTasks(currentUser, task);
		        }
		        else {
		            existingTasks.add(task);
		            currentUser.setTasks(existingTasks);
		        }
		        
		        userRepository.save(currentUser);
		    }
		    return currentUser;
	}
	
	private void generateRepetitiveTasks(User user, Task task) {
	    LocalDate startDate = LocalDate.parse(task.getTaskDate());
	    LocalDate endDate = LocalDate.parse(task.getRepeatEndDate());
	    String repeatType = task.getRepeatType();

	    List<Task> repetitiveTasks = new ArrayList<>();

	    while (!startDate.isAfter(endDate)) {
	        Task repetitiveTask = new Task(task.getTaskName(),task.getTaskDescription(),startDate.toString(),
	        		task.getTaskStartTime(),task.getTaskDueTime(),task.getIsrepetitive(),task.getRepeatType(),
	        		task.getRepeatEndDate(),task.getIshighPriority(),task.getIscompleted(),task.getIsarchived(),
	        		task.getIsapplyToRepetitive());
	        repetitiveTasks.add(repetitiveTask);

	        switch (repeatType) {
	            case "Daily":
	                startDate = startDate.plusDays(1);
	                break;
	            case "Weekly":
	                startDate = startDate.plusWeeks(1);
	                break;
	            case "Monthly":
	            	startDate = startDate.plusMonths(1);
	            	break;
	        }
	    }

	    List<Task> existingTasks = user.getTasks();
	    if (existingTasks == null) {
	        user.setTasks(repetitiveTasks);
	    } else {
	        List<Task> updatedTasks = new ArrayList<>(existingTasks); // Create a new ArrayList
	        for (Task repetitiveTask : repetitiveTasks) {
	            boolean isDuplicate = updatedTasks.stream()
	                    .anyMatch(existingTask -> existingTask.getTaskName().equals(repetitiveTask.getTaskName())
	                            && existingTask.getTaskDate().equals(repetitiveTask.getTaskDate()));
	            if (!isDuplicate) {
	                updatedTasks.add(repetitiveTask);
	            }
	        }
	        user.setTasks(updatedTasks);
	    }
	}

	@Override
	public User updateTask(String emailId, Task task) throws UserNotFoundException, TaskNotExistException {
		  Optional<User> fetchedUser = userRepository.findById(emailId);
		    User currentUser;
		    DateTimeFormatter dateFormatter;
		    DateTimeFormatter timeFormatter;
		    if (fetchedUser.isEmpty()) {
		        throw new UserNotFoundException("User with the given EmailId didn't Exist");
		    } else {
		        currentUser = fetchedUser.get();
		        List<Task> existingTasks = currentUser.getTasks();
		        if (existingTasks == null) {
		            throw new TaskNotExistException("No Task is Available for the User");
		        } else {
		            boolean flag = false;
		            for (Task iteratedTask : existingTasks) {
		                if (task.getTaskName() != null && iteratedTask.getTaskName().equalsIgnoreCase(task.getTaskName())) {
		                    iteratedTask.setTaskDescription(task.getTaskDescription());
		                    iteratedTask.setTaskDate(task.getTaskDate());
		                    iteratedTask.setTaskStartTime(task.getTaskStartTime());
		                    iteratedTask.setTaskDueTime(task.getTaskDueTime());
		                    iteratedTask.setIsrepetitive(task.getIsrepetitive());
		                    iteratedTask.setRepeatType(task.getRepeatType());
		                    iteratedTask.setRepeatEndDate(task.getRepeatEndDate());
		                    iteratedTask.setIshighPriority(task.getIshighPriority());
		                    iteratedTask.setIscompleted(task.getIscompleted());
		                    iteratedTask.setIsarchived(task.getIscompleted()); // Archived should be set same as completed
		                    iteratedTask.setIsapplyToRepetitive(task.getIsapplyToRepetitive());
		                    flag = true;
		                    break;
		                }
		            }
		            if (flag) {
		                dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		                timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		                if (task.getIsrepetitive() && task.getIsapplyToRepetitive()) {
		                    updateRepetitiveTasks(currentUser, task, dateFormatter, timeFormatter);
		                }

		                if (task.getIsapplyToRepetitive() == false) {
		                    for (Task iteratedTask : existingTasks) {
		                        if (task.getTaskName() != null && iteratedTask.getTaskName().equalsIgnoreCase(task.getTaskName()) &&
		                                LocalDate.parse(iteratedTask.getTaskDate()).isEqual(LocalDate.parse(task.getTaskDate())) &&
		                                !iteratedTask.getIscompleted()) { // Check if the task for the specific date is not already completed
		                            iteratedTask.setIscompleted(true);
		                            iteratedTask.setIsarchived(true);
		                            break; // No need to continue checking further tasks
		                        }
		                    }
		                    userRepository.save(currentUser);
		                }

		                
		            } 
		            else {
		                throw new TaskNotExistException("Task Name Doesn't Exist");
		            }
		        }
		    }
		    
		    return currentUser;
	}

	private void updateRepetitiveTasks(User user, Task updatedTask, DateTimeFormatter dateFormatter, DateTimeFormatter timeFormatter) {
	    List<Task> existingTasks = user.getTasks();
	    boolean isUpdated = false;
	    if (existingTasks != null) {
	        List<Task> updatedTasks = new ArrayList<>();
	        for (Task task : existingTasks) {
	            if (task.getTaskName().equalsIgnoreCase(updatedTask.getTaskName()) && task.getIsrepetitive() && task.getIsapplyToRepetitive()) {
	            	isUpdated = true;
	                // Update task details for the current task
	                task.setTaskDescription(updatedTask.getTaskDescription());
	                task.setTaskStartTime(updatedTask.getTaskStartTime());
	                task.setTaskDueTime(updatedTask.getTaskDueTime());
	                task.setRepeatType(updatedTask.getRepeatType());
	                task.setRepeatEndDate(updatedTask.getRepeatEndDate());
	                task.setIshighPriority(updatedTask.getIshighPriority());

	                // Update task date based on repeat type and start date
	                LocalDate startDate = LocalDate.parse(task.getTaskDate());
	                LocalDate endDate = LocalDate.parse(task.getRepeatEndDate());
	                String repeatType = task.getRepeatType();

	                // Remove the original task from updatedTasks
	                updatedTasks.removeIf(t -> t.getTaskName().equalsIgnoreCase(task.getTaskName()));

	                while (!startDate.isAfter(endDate)) {
	                    Task repetitiveTask = new Task(task.getTaskName(), task.getTaskDescription(),
	                            startDate.toString(), task.getTaskStartTime(), task.getTaskDueTime(),
	                            task.getIsrepetitive(), task.getRepeatType(), task.getRepeatEndDate(),
	                            task.getIshighPriority(), task.getIscompleted(), task.getIsarchived(),
	                            task.getIsapplyToRepetitive());
	                    updatedTasks.add(repetitiveTask);

	                    switch (repeatType) {
	                        case "Daily":
	                            startDate = startDate.plusDays(1);
	                            break;
	                        case "Weekly":
	                            startDate = startDate.plusWeeks(1);
	                            break;
	                        case "Monthly":
	                        	startDate = startDate.plusMonths(1);
	                        // Add cases for other repetition types as needed
	                    }
	                }
	            }
	            else {
	            	if(isUpdated == false) {
	            	    updatedTasks.add(task);
	            	}
	            }
	        }
	        // Update existingTasks in the user object
	        
	        user.setTasks(updatedTasks);
	        userRepository.save(user);
	    }
	}
	
	@Override
	public List<Task> getAllTasks(String emailId) throws UserNotFoundException, TaskNotExistException {
		Optional<User> fetchedUser = userRepository.findById(emailId);
		User currentUser;
		if(fetchedUser.isEmpty()) {
			throw new UserNotFoundException("User with the given EmailId did't Exist");
		}
		else {
			currentUser = fetchedUser.get();
			List<Task> existingTasks = currentUser.getTasks();
			if(existingTasks == null) {
				throw new TaskNotExistException("No Task is Available for the User");
			}
			else {
				return existingTasks;
			}
		}
	}

	@Override
	public List<Task> getOneTask(String emailId, String taskName) throws UserNotFoundException, TaskNotExistException{
		Optional<User> fetchedUser = userRepository.findById(emailId);
		User currentUser;
		if(fetchedUser.isEmpty()) {
			throw new UserNotFoundException("User with the given EmailId did't Exist");
		}
		else {
			currentUser = fetchedUser.get();
			List<Task> existingTasks = currentUser.getTasks();
			List<Task> neededTask = null;
			neededTask = new ArrayList<>();
			boolean flag = false;
			if(existingTasks == null) {
				throw new TaskNotExistException("No Task is Available for the User");
			}
			for(Task iteratedTask : existingTasks) {
				if(taskName != null && iteratedTask.getTaskName().equalsIgnoreCase(taskName)) {
					neededTask.add(iteratedTask);
					flag = true;
				}
			}
			if(flag == true) {
				return neededTask;
			}
			else {
				throw new TaskNotExistException("No Task is Available with the given Task Name");
			}
		}
	}

	@Override
	public boolean deleteTask(String emailId, Task task) throws UserNotFoundException, TaskNotExistException{
		Optional<User> fetchedUser = userRepository.findById(emailId);
		User currentUser;
		if(fetchedUser.isEmpty()) {
			throw new UserNotFoundException("User with the given EmailId did't Exist");
		}
		else {
			currentUser = fetchedUser.get();
			List<Task> existingTasks = currentUser.getTasks();
			boolean flag = false;
			if(existingTasks == null) {
				throw new TaskNotExistException("No Task is Available for the User");
			}
			else {
				Iterator<Task> iteratedTask = existingTasks.iterator();
				while(iteratedTask.hasNext()) {
					Task currentTask = iteratedTask.next();
					if(task.getTaskName() != null && currentTask.getTaskName().equalsIgnoreCase(task.getTaskName())){
						iteratedTask.remove();
						flag = true;
						break;
					}
				}
			}
			if(flag == true) {
				 if (currentUser.getTasks().stream().anyMatch(t -> task.getTaskName().equalsIgnoreCase(task.getTaskName()) && task.getIsrepetitive() && task.getIsapplyToRepetitive())) {
	                    deleteRepetitiveTasks(currentUser, task.getTaskName());
	                }
				currentUser.setTasks(existingTasks);
				userRepository.save(currentUser);
				return flag;
			}
			else {
				throw new TaskNotExistException("Task with the given TaskName didn't Exist");
			}
		}
	}
	
	private void deleteRepetitiveTasks(User user, String taskName) {
	    List<Task> existingTasks = user.getTasks();
	    existingTasks.removeIf(task -> task.getTaskName().equalsIgnoreCase(taskName) && task.getIsrepetitive() && task.getIsapplyToRepetitive());
	    user.setTasks(existingTasks);
	    userRepository.save(user);
	}


}
