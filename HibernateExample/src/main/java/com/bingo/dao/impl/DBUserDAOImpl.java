package com.bingo.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.bingo.dao.DBUserDAO;
import com.bingo.user.DBUser;

public class DBUserDAOImpl implements DBUserDAO {

  private SessionFactory sessionFactory;

  public DBUserDAOImpl() {}

  public DBUserDAOImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void setSessionFactor(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean insertUser(DBUser user) {
    System.out.println("Insert user: " + user);

    try (Session session = sessionFactory.openSession()) {
      session.save(user);
      session.flush();
      System.out.println("Insert user: " + user);
    } catch (HibernateException e) {
      System.out.println("Failed to insert user" + user + ": " + e.getMessage());
      return false;
    }

    System.out.println(user + " is inserted successfully");
    return true;
  }

  @Override
  public void deleteUser(DBUser user) {
    System.out.println("Delete user " + user);

    try (Session session = sessionFactory.openSession()) {
      session.delete("DBUser", user);
      session.flush();
    } catch (HibernateException e) {
      System.out.println("Failed to delete user " + user + ": " + e.getMessage());
    }
  }

  @Override
  public List<DBUser> listUsers() {
    System.out.println("List users");

    List<DBUser> users = null;
    try (Session session = sessionFactory.openSession()) {
      Criteria criteria = session.createCriteria(DBUser.class);
      users = (List<DBUser>) criteria.list();
      for (DBUser u : users) {
        System.out.println(u);
      }
    } catch (HibernateException e) {
      System.out.println("Failed to list users: " + e.getMessage());
    }
    return users;
  }

  @Override
  public DBUser findUserById(int id) {
    DBUser user = null;
    try (Session session = sessionFactory.openSession()) {
      user = session.get(DBUser.class, id);
    } catch (HibernateException e) {
      System.out.println("Failed to list users: " + e.getMessage());
    }
    return user;
  }

  @Override
  public void deleteUser(int id) {
    // TODO Auto-generated method stub

  }
}
