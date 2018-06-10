package com.adamkorzeniak.taskmanager.task.repository;

import java.util.List;

import com.adamkorzeniak.taskmanager.task.model.Task;
import com.adamkorzeniak.taskmanager.task.model.TaskSearch;

public interface TaskRepositoryCustom {

	public List<Task> search(TaskSearch task);
}
