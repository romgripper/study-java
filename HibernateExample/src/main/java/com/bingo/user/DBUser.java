package com.bingo.user;

import java.util.Date;

/* Entity class uses standard JavaBean naming conventions for property getter and setter methods,
 * as well as private visibility for the fields. Although this is the recommended design, it is
 * not required.
 *
 * The no-argument constructor, which is also a JavaBean convention, is a requirement for all
 * persistent classes. Hibernate needs to create objects for you, using Java Reflection.
 * The constructor can be private. However, package or public visibility is required for runtime
 * proxy generation and efficient data retrieval without bytecode instrumentation.
 */
public class DBUser {

  private int userId;
  private String username;
  private String createdBy;
  private Date createdDate;

  public DBUser() {}

  public DBUser(int userId, String username, String createdBy, Date createdDate) {
    super();
    this.userId = userId;
    this.username = username;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
  }

  public String toString() {
    return "DBUser [" + userId + ", " + username + ", " + createdBy + ", " + createdDate + "]";
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}
