package com.adamkorzeniak.taskmanager.user.service;

import com.adamkorzeniak.taskmanager.user.model.User;
import com.adamkorzeniak.taskmanager.user.model.UserDTO;

public interface UserService {

	User getUser(String username);

	User register(UserDTO userForm);

	String getPrincipal();

}