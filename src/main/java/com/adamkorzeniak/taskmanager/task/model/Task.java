package com.adamkorzeniak.taskmanager.task.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.adamkorzeniak.taskmanager.user.model.User;

@Entity
public class Task {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	private String name;
	
	private String description;
	
	private Long minutesEstimation;
	
	private Long minutesInvested;
	
	private LocalDateTime date;
	
	private LocalDateTime deadline;
	
	private String status;
	
	@ManyToOne
	private Task parentTask;
	
	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

	public Task() {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void update(Task task) {
		this.setName(task.getName());
		this.setDescription(task.getDescription());
		this.setMinutesEstimation(task.getMinutesEstimation());
		this.setMinutesInvested(task.getMinutesInvested());
		this.setDate(task.getDate());
		this.setDeadline(task.getDeadline());
		this.setStatus(task.getStatus());
		this.setParentTask(task.getParentTask());
		//user should not be updated
	}
}
