package com.adamkorzeniak.taskmanager.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adamkorzeniak.taskmanager.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);
}