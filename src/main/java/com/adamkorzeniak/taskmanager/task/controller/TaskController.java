package com.adamkorzeniak.taskmanager.task.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.taskmanager.task.model.TaskDTO;
import com.adamkorzeniak.taskmanager.task.model.TaskSearch;
import com.adamkorzeniak.taskmanager.task.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value = "/tasks", method = RequestMethod.POST)
	public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
		
		taskService.create(taskDTO);
        return new ResponseEntity<TaskDTO>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public ResponseEntity<List<TaskDTO>> findTasks() {
		
		List<TaskDTO> tasks = taskService.findAllTasks();
		return new ResponseEntity<List<TaskDTO>>(tasks, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tasks/search", method = RequestMethod.GET)
	public ResponseEntity<List<TaskDTO>> searchTasks(
				@RequestParam(required=false) String name,
				@RequestParam(required=false) String description,
				@RequestParam(required=false) Long estimationLessThan,
				@RequestParam(required=false) Long estimationGreaterThan,
				@RequestParam(required=false) Long timeInvestedLessThan,
				@RequestParam(required=false) Long timeInvestedGreaterThan,
				@RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateLessThan,
				@RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateGreaterThan,
				@RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadlineLessThan,
				@RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadlineGreaterThan,
				@RequestParam(required=false) String status,
				@RequestParam(required=false) boolean recursiveParent,
				@RequestParam(required=false) String parentTaskId
			) {
		TaskSearch taskSearch = new TaskSearch(name, 
				description, 
				estimationLessThan, 
				estimationGreaterThan, 
				timeInvestedLessThan, 
				timeInvestedGreaterThan, 
				dateLessThan, 
				dateGreaterThan, 
				deadlineLessThan, 
				deadlineGreaterThan, 
				status, 
				recursiveParent, 
				parentTaskId);
		List<TaskDTO> tasks = taskService.searchTasks(taskSearch);
		return new ResponseEntity<List<TaskDTO>>(tasks, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
	public ResponseEntity<TaskDTO> findTask(@PathVariable long id) {
		
		TaskDTO task = taskService.findTask(id);
		if (task == null) {
			return new ResponseEntity<TaskDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TaskDTO>(task, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tasks/{id}", method = RequestMethod.POST)
	public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO task, @PathVariable long id) {
		
		task = taskService.updateTask(task, id);
		if (task == null) {
			return new ResponseEntity<TaskDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TaskDTO>(task, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<TaskDTO> deleteTask(@PathVariable long id) {
		
		boolean deleted = taskService.deleteTask(id);
		if (!deleted) {
			return new ResponseEntity<TaskDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TaskDTO>(HttpStatus.OK);
	}
}
