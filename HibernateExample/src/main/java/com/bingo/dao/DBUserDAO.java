package com.bingo.dao;

import java.util.List;

import com.bingo.user.DBUser;

public interface DBUserDAO {

  boolean insertUser(DBUser user);

  boolean insertOrUpdateUser(DBUser user);

  boolean deleteUser(DBUser user);

  boolean deleteUser(int id);

  List<DBUser> listUsers();

  List<DBUser> listUsersWithName(String name);

  DBUser findUserById(int id);
}
