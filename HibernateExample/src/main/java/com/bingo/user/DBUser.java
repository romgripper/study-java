package com.bingo.user;

import java.io.Serializable;
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
@Table(name = DBUser.TABLE)
public class DBUser implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String TABLE = "DBUSER";
  public static final String COLUMN_ID = "USER_ID";
  public static final String COLUMN_USERNAME = "USERNAME";
  public static final String COLUMN_CREATED_BY = "CREATED_BY";
  public static final String COLUMN_CREATED_DATE = "CREATED_DATE";

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
  @Column(name = COLUMN_ID, unique = true, nullable = false, precision = 5, scale = 0)
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Column(name = COLUMN_USERNAME, nullable = false, length = 20)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Column(name = COLUMN_CREATED_BY, nullable = false, length = 20)
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  @Temporal(TemporalType.DATE)
  @Column(name = COLUMN_CREATED_DATE, nullable = false, length = 7)
  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}
