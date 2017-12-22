package com.bingo.dao;

import java.util.List;

import com.bingo.user.DBUser;

public interface DBUserDAO {

  boolean insertUser(DBUser user);

  void deleteUser(DBUser user);

  void deleteUser(int id);

  List<DBUser> listUsers();

  DBUser findUserById(int id);
}
