package com.bingo.customer.dao;

import com.bingo.customer.model.Customer;

public interface CustomerDAO {
  public void insert(Customer customer);

  public Customer findByCustomerId(int custId);
}
