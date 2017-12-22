package com.bingo.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DBUSER")
public class DBUser {

  private Integer userId;
  private String username;
  private String createdBy;
  private Date createdDate;

  public DBUser() {}

  public DBUser(Integer userId, String username, String createdBy, Date createdDate) {
    super();
    this.userId = userId;
    this.username = username;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
  }

  public String toString() {
    return "DBUser [" + userId + ", " + username + ", " + createdBy + ", " + createdDate + "]";
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID", unique = true, nullable = false, precision = 5, scale = 0)
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Column(name = "USERNAME", nullable = false, length = 20)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Column(name = "CREATED_BY", nullable = false, length = 20)
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  @Temporal(TemporalType.DATE)
  @Column(name = "CREATED_DATE", nullable = false, length = 7)
  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}
