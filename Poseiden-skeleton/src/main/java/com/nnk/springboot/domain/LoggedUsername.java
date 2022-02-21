package com.nnk.springboot.domain;

public class LoggedUsername {

    private String username;

    public LoggedUsername() {

    }

    public LoggedUsername(String username) {
	this.username = username;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

}
