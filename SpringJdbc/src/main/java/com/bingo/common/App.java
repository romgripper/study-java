package com.bingo.common;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bingo.customer.dao.CustomerDAO;
import com.bingo.customer.model.Customer;

public class App {

  public static void main(String[] args) {
    try (ClassPathXmlApplicationContext context =
        new ClassPathXmlApplicationContext("Spring-Module.xml")) {

      CustomerDAO dao = (CustomerDAO) context.getBean("customerDAO");
      Customer customer = new Customer(1, "Bingo", 35);
      dao.insert(customer);

      customer = dao.findByCustomerId(1);
      System.out.println(customer);
    }
  }
}
