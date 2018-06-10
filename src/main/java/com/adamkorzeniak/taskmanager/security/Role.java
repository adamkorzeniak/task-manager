package com.adamkorzeniak.taskmanager.security;

public enum Role {

	USER,
	ADMIN;
	
	public boolean isUser() {
		return this.equals(USER);
	}
	
	public boolean isAdmin() {
		return this.equals(ADMIN);
	}
}
