package com.bingo.customer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.bingo.customer.dao.CustomerDAO;
import com.bingo.customer.model.Customer;

public class CustomerDAOImpl implements CustomerDAO {

  private DataSource source;

  public void setDataSource(DataSource dataSource) {
    source = dataSource;
  }

  public void insert(Customer customer) {
    String sql = "INSERT INTO CUSTOMER " + "(cust_id, name, age) VALUES (?,?,?)";
    try (Connection conn = source.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, customer.getCustId());
      ps.setString(2, customer.getName());
      ps.setInt(3, customer.getAge());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Customer insertion error");
    }
  }

  public Customer findByCustomerId(int custId) {
    String sql = "SELECT * FROM CUSTOMER WHERE cust_id = ?";
    Customer customer = null;
    try (Connection conn = source.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, custId);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        customer = new Customer(rs.getInt("cust_id"), rs.getString("name"), rs.getInt("age"));
      }
    } catch (SQLException e) {
      System.out.println("Error occurs when finding customer");
    }
    return customer;
  }
}
