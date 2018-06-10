package com.adamkorzeniak.taskmanager.user.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.taskmanager.user.model.User;
import com.adamkorzeniak.taskmanager.user.model.UserDTO;
import com.adamkorzeniak.taskmanager.user.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<Principal> user(Principal user) {
		if (user == null) {
			return new ResponseEntity<Principal>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Principal>(user, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody UserDTO userForm) {
		User user = userService.register(userForm);
		if (user == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("message", "User already exists");
			return new ResponseEntity<User>(headers, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
}
