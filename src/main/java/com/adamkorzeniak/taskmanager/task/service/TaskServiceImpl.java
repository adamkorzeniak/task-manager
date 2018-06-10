package com.adamkorzeniak.taskmanager.task.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.taskmanager.exception.ParentOfItselfException;
import com.adamkorzeniak.taskmanager.exception.ParentTaskNotFoundException;
import com.adamkorzeniak.taskmanager.task.model.Task;
import com.adamkorzeniak.taskmanager.task.model.TaskDTO;
import com.adamkorzeniak.taskmanager.task.model.TaskSearch;
import com.adamkorzeniak.taskmanager.task.repository.TaskRepository;
import com.adamkorzeniak.taskmanager.user.model.User;
import com.adamkorzeniak.taskmanager.user.service.UserService;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserService userService;

	public TaskDTO create(TaskDTO taskDTO) {
		User user = userService.getUser(userService.getPrincipal());
		Task task = convertDTOToEntity(taskDTO);
		user.addTask(task);
		task = taskRepository.save(task);
		return convertEntityToDTO(task);
	}

	public List<TaskDTO> findAllTasks() {
		User user = userService.getUser(userService.getPrincipal());
		List<Task> tasks = taskRepository.findByUser(user);
		return convertTaskstoDTOs(tasks);
	}

	@Override
	public TaskDTO findTask(long taskId) {
		User user = userService.getUser(userService.getPrincipal());
		Task task = taskRepository.findByUserAndId(user, taskId);
		return convertEntityToDTO(task);
	}

	@Override
	public TaskDTO updateTask(TaskDTO taskDTO, long taskId) {
		
		User user = userService.getUser(userService.getPrincipal());
		Task currentTask = taskRepository.findByUserAndId(user, taskId);
		if (currentTask == null) {
			return null;
		}
		currentTask.update(convertDTOToEntity(taskDTO));
		if (currentTask.getParentTask() != null && currentTask.getId().equals(currentTask.getParentTask().getId())) {
			throw new ParentOfItselfException();
		}
		currentTask = taskRepository.save(currentTask);
		return convertEntityToDTO(currentTask);
	}
	
	@Override
	public boolean deleteTask(long id) {
		User user = userService.getUser(userService.getPrincipal());
		Task task = taskRepository.findByUserAndId(user, id);
		if (task == null) {
			return false;
		}
		taskRepository.delete(task);
		return true;
	}
	
	private Task convertDTOToEntity(TaskDTO dto) {
		
		if (dto == null) {
			return null;
		}
		
		User user = userService.getUser(userService.getPrincipal());

		Task task = new Task();
		task.setName(dto.getName());
		task.setDescription(dto.getDescription());
		task.setMinutesEstimation(dto.getMinutesEstimation());
		task.setMinutesInvested(dto.getMinutesInvested());
		task.setDate(dto.getDate());
		task.setDeadline(dto.getDeadline());
		task.setStatus(dto.getStatus());
		task.setUser(user);
		if (dto.getParentTaskId() != null) {
			Task parentTask = taskRepository.findByUserAndId(user, dto.getParentTaskId());
			if (parentTask == null) {
				throw new ParentTaskNotFoundException();
			}
			task.setParentTask(parentTask);
		}
		return task;		
	}
	
	private TaskDTO convertEntityToDTO(Task task) {
		
		if (task == null) {
			return null;
		}
		
		TaskDTO dto = new TaskDTO();
		dto.setId(task.getId());
		dto.setName(task.getName());
		dto.setDescription(task.getDescription());
		dto.setMinutesEstimation(task.getMinutesEstimation());
		dto.setMinutesInvested(task.getMinutesInvested());
		dto.setDate(task.getDate());
		dto.setDeadline(task.getDeadline());
		dto.setStatus(task.getStatus());
		dto.setUser(task.getUser().getUsername());
		if (task.getParentTask() != null) {
			dto.setParentTaskId(task.getParentTask().getId());
		}
		return dto;
	}

	@Override
	public List<TaskDTO> searchTasks(TaskSearch taskSearch) {
		List<Task> tasks = taskRepository.search(taskSearch);
		return convertTaskstoDTOs(tasks);
	}

	private List<TaskDTO> convertTaskstoDTOs(List<Task> tasks) {
		List<TaskDTO> dtos = new ArrayList<>();
		for (Task task: tasks) {
			dtos.add(convertEntityToDTO(task));
		}
		return dtos;
	}
}
