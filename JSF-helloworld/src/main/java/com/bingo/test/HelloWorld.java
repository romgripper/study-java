package com.bingo.test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "helloWorld", eager = true)
@RequestScoped
public class HelloWorld {

	@ManagedProperty(value = "#{messageBeanName}")
	private Message messageBean;

	public void setMessageBean(Message messageBean) {
		this.messageBean = messageBean;
	}

	public Message getMessageBean() {
		return messageBean;
	}

	public HelloWorld() {
		System.out.println("HelloWorld started!");
	}

	public String getMessage() {
		return messageBean.getMessage();
	}
}