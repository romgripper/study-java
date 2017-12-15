package com.bingo.test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "messageBeanName", eager = true)
@RequestScoped
public class Message {

	private String message = "Hello World";

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
