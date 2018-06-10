package com.adamkorzeniak.taskmanager.task.model;

import java.time.LocalDateTime;
import java.util.Map;

public class TaskSearch {

	private String name;
	private String description;
	private Long estimationLessThan;
	private Long estimationGreaterThan;
	private Long timeInvestedLessThan;
	private Long timeInvestedGreaterThan;
	private LocalDateTime dateLessThan;
	private LocalDateTime dateGreaterThen;
	private LocalDateTime deadlineLessThan;
	private LocalDateTime deadlineGreaterThan;
	private String status;
	private Long parentTaskId;
	private boolean recursiveParent;
	
	public TaskSearch(
		String name,
		String description,
		Long estimationLessThan,
		Long estimationGreaterThan,
		Long timeInvestedLessThan,
		Long timeInvestedGreaterThan,
		LocalDateTime dateLessThan,
		LocalDateTime dateGreaterThen,
		LocalDateTime deadlineLessThan,
		LocalDateTime deadlineGreaterThan,
		String status,
		boolean recursiveParent,
		String parentTaskId
	) {
		this.name = name;
		this.description = description;
		this.estimationLessThan = estimationLessThan;
		this.estimationGreaterThan = estimationGreaterThan;
		this.timeInvestedLessThan = timeInvestedLessThan;
		this.timeInvestedGreaterThan = timeInvestedGreaterThan;
		this.dateLessThan = dateLessThan;
		this.dateGreaterThen = dateGreaterThen;
		this.deadlineLessThan = deadlineLessThan;
		this.deadlineGreaterThan = deadlineGreaterThan;
		this.status = status;
		this.recursiveParent = recursiveParent;
		if (parentTaskId != null) {
			this.parentTaskId = Long.parseLong(parentTaskId);
		}
//		if (parentTaskId != null) {
//			String[] parentTaskList = parentTaskId.split(",");
//			this.parentTaskId = new Long[parentTaskList.length];
//			for (int i = 0; i < parentTaskList.length; i++) {
//				this.parentTaskId[i] = Long.parseLong(parentTaskList[i]);
//			}
//		}
	}

	public TaskSearch(Map<String, String> allRequestParams) {
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Long getEstimationLessThan() {
		return estimationLessThan;
	}

	public Long getEstimationGreaterThan() {
		return estimationGreaterThan;
	}

	public Long getTimeInvestedLessThan() {
		return timeInvestedLessThan;
	}

	public Long getTimeInvestedGreaterThan() {
		return timeInvestedGreaterThan;
	}

	public LocalDateTime getDateLessThan() {
		return dateLessThan;
	}

	public LocalDateTime getDateGreaterThan() {
		return dateGreaterThen;
	}

	public LocalDateTime getDeadlineLessThan() {
		return deadlineLessThan;
	}

	public LocalDateTime getDeadlineGreaterThan() {
		return deadlineGreaterThan;
	}

	public String getStatus() {
		return status;
	}

	public Long getParentTaskId() {
		return parentTaskId;
	}

	public boolean isRecursiveParent() {
		return recursiveParent;
	}
}