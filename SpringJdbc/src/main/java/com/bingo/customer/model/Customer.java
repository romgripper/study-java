package com.bingo.customer.model;

public class Customer {
  private int custId;
  private String name;
  private int age;

  public int getCustId() {
    return custId;
  }

  public Customer(int custId, String name, int age) {
    super();
    this.custId = custId;
    this.name = name;
    this.age = age;
  }

  public void setCustId(int custId) {
    this.custId = custId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String toString() {
    return "cust_id: " + custId + "\nname: " + name + "\nage: " + age + "\n";
  }
}
