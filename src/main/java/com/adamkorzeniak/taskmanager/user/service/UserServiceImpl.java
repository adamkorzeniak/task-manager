package com.adamkorzeniak.taskmanager.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.taskmanager.exception.DuplicateUserException;
import com.adamkorzeniak.taskmanager.user.model.User;
import com.adamkorzeniak.taskmanager.user.model.UserDTO;
import com.adamkorzeniak.taskmanager.user.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public User register(UserDTO userForm) {
		
		if (userForm == null) {
			throw new IllegalArgumentException();
		}
		ifUserNotExistsAlready(userForm.getUsername());
		
		User user = new User();
		user.setUsername(userForm.getUsername());
		user.setPassword(encoder.encode(userForm.getPassword()));
		user.setEmail(userForm.getEmail());
		user.setRole(userForm.getRole());
		
		return userRepository.save(user);
	}
	
	@Override
    public String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
	
	@Override
	public User getUser(String username) {
		if (username == null) {
			throw new IllegalArgumentException();
		}
		return userRepository.findByUsername(username);
	}
	
	private void ifUserNotExistsAlready(String username) {
		User user = userRepository.findByUsername(username);
		if (user!= null) {
			throw new DuplicateUserException();
		}
	}
}
