package com.adamkorzeniak.taskmanager.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adamkorzeniak.taskmanager.task.model.Task;
import com.adamkorzeniak.taskmanager.user.model.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom {

	List<Task> findByUser(User user);

	Task findByUserAndId(User user, long taskId);
}
