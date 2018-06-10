package com.adamkorzeniak.taskmanager.task.model;

import java.time.LocalDateTime;

public class TaskDTO {
	
	private Long id;
	private String name;
	private String description;
	private Long minutesEstimation;
	private Long minutesInvested;
	private LocalDateTime date;
	private LocalDateTime deadline;
	private String status;
	private Long parentTaskId;
	private String user;
	
	public TaskDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getMinutesEstimation() {
		return minutesEstimation;
	}

	public void setMinutesEstimation(Long minutesEstimation) {
		this.minutesEstimation = minutesEstimation;
	}

	public Long getMinutesInvested() {
		return minutesInvested;
	}

	public void setMinutesInvested(Long minutesInvested) {
		this.minutesInvested = minutesInvested;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
