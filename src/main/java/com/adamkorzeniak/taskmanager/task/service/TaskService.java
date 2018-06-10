package com.adamkorzeniak.taskmanager.task.service;

import java.util.List;

import com.adamkorzeniak.taskmanager.task.model.TaskDTO;
import com.adamkorzeniak.taskmanager.task.model.TaskSearch;

public interface TaskService {

	TaskDTO create(TaskDTO dto);

	List<TaskDTO> findAllTasks();

	TaskDTO findTask(long taskId);

	TaskDTO updateTask(TaskDTO dto, long id);

	boolean deleteTask(long id);

	List<TaskDTO> searchTasks(TaskSearch taskSearch);
}
