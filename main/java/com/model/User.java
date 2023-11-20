package com.model;

import java.sql.Timestamp;

public class User {
    private String name;
    private String email;
    private String password;
	private Timestamp creationDate;

	public User() {
	}
    public User(String name, String email, String password, Timestamp creationDate) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.creationDate = creationDate;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
    
}
