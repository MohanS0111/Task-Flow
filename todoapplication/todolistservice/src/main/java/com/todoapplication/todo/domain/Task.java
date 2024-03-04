package com.todoapplication.todo.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


import org.springframework.data.annotation.Id;

public class Task {

	private String taskName;
    private String taskDescription;
    private String taskDate;
    private String taskStartTime;
    private String taskDueTime;
    private boolean isrepetitive;
    private String repeatType;
    private String repeatEndDate;
    private boolean ishighPriority;
    private boolean iscompleted;
    private boolean isarchived;
    private boolean isapplyToRepetitive;

    public Task() {
    }

	public Task(String taskName, String taskDescription, String taskDate, String taskStartTime, String taskDueTime,
			boolean isrepetitive,String repeatType, String repeatEndDate, boolean ishighPriority, boolean iscompleted,
			boolean isarchived, boolean isapplyToRepetitive) {
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.taskDate = taskDate;
		this.taskStartTime = taskStartTime;
		this.taskDueTime = taskDueTime;
		this.isrepetitive = isrepetitive;
		this.repeatType = repeatType;
		this.repeatEndDate = repeatEndDate;
		this.ishighPriority = ishighPriority;
		this.iscompleted = iscompleted;
		this.isarchived = isarchived;
		this.isapplyToRepetitive = isapplyToRepetitive;
	}


	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}

	public String getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public String getTaskDueTime() {
		return taskDueTime;
	}

	public void setTaskDueTime(String taskDueTime) {
		this.taskDueTime = taskDueTime;
	}

	public boolean getIsrepetitive() {
		return isrepetitive;
	}

	public void setIsrepetitive(boolean isrepetitive) {
		this.isrepetitive = isrepetitive;
	}
	
	public String getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}


	public String getRepeatEndDate() {
		return repeatEndDate;
	}

	public void setRepeatEndDate(String repeatEndDate) {
		this.repeatEndDate = repeatEndDate;
	}

	public boolean getIshighPriority() {
		return ishighPriority;
	}

	public void setIshighPriority(boolean ishighPriority) {
		this.ishighPriority = ishighPriority;
	}

	public boolean getIscompleted() {
		return iscompleted;
	}

	public void setIscompleted(boolean iscompleted) {
		this.iscompleted = iscompleted;
	}

	public boolean getIsarchived() {
		return isarchived;
	}

	public void setIsarchived(boolean isarchived) {
		this.isarchived = isarchived;
	}
	
	public boolean getIsapplyToRepetitive() {
		return isapplyToRepetitive;
	}

	public void setIsapplyToRepetitive(boolean isapplyToRepetitive) {
		this.isapplyToRepetitive = isapplyToRepetitive;
	}

	@Override
	public String toString() {
		return "Task [taskName=" + taskName + ", taskDescription=" + taskDescription + ", taskDate=" + taskDate
				+ ", taskStartTime=" + taskStartTime + ", taskDueTime=" + taskDueTime + ", isrepetitive=" + isrepetitive
				+ ", repeatType=" + repeatType + ", repeatEndDate=" + repeatEndDate + ", ishighPriority="
				+ ishighPriority + ", iscompleted=" + iscompleted + ", isarchived=" + isarchived
				+ ", isapplyToRepetitive=" + isapplyToRepetitive + "]";
	}
	
}
