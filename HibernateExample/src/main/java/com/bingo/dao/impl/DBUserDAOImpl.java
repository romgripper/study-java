package com.bingo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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

    // With a connection pool, the overhead to open and close session isn't big.
    try (Session session = sessionFactory.openSession()) {
      // save() will always insert a new row without checking the id.
      // For example:
      // Insert user: DBUser [18, superman, system, Mon Dec 25 23:52:26 CST 2017]
      // DBUser [19, superman, system, Mon Dec 25 23:52:26 CST 2017] is inserted successfully
      session.save(user);
      session.flush();
    } catch (HibernateException e) {
      System.out.println("Failed to insert user" + user + ": " + e.getMessage());
      return false;
    }

    System.out.println(user + " is inserted successfully");
    return true;
  }

  @Override
  public boolean insertOrUpdateUser(DBUser user) {
    System.out.println("Insert/update user: " + user);
    Integer id = user.getUserId();

    // With a connection pool, the overhead to open and close session isn't big.
    try (Session session = sessionFactory.openSession()) {
      // save() will always insert a new row without checking the id.
      // For example:
      // Insert user: DBUser [18, superman, system, Mon Dec 25 23:52:26 CST 2017]
      // DBUser [19, superman, system, Mon Dec 25 23:52:26 CST 2017] is inserted successfully
      session.saveOrUpdate(user);
      session.flush();
    } catch (HibernateException e) {
      System.out.println("Failed to insert/update user" + user + ": " + e.getMessage());
      return false;
    }
    Integer newId = user.getUserId();
    if (newId != null) {
      if (newId.equals(id)) {
        System.out.println(user + " is updated successfully");
      } else {
        System.out.println(user + " is inserted successfully");
      }
    }

    return true;
  }

  @Override
  public boolean deleteUser(DBUser user) {
    System.out.println("Delete user " + user);

    try (Session session = sessionFactory.openSession()) {
      session.delete("DBUser", user);
      session.flush();
    } catch (HibernateException e) {
      System.out.println("Failed to delete user " + user + ": " + e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public List<DBUser> listUsers() {
    System.out.println("List users");

    List<DBUser> users = new ArrayList<>();
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
    System.out.println("Found user: " + user + " with ID=" + id);
    return user;
  }

  @Override
  public boolean deleteUser(int id) {
    System.out.println("Delete user with ID=" + id);
    return deleteUser(findUserById(id));
  }

  @Override
  public List<DBUser> listUsersWithName(String name) {
    System.out.println("List users with name=" + name);

    List<DBUser> users = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      Criteria criteria = session.createCriteria(DBUser.class);
      // properties are properties of Java class, not the columns of the table
      criteria
          .add(Restrictions.eq("username", name))
          .addOrder(Order.asc("id"))
          .addOrder(Order.asc("createdBy"));
      users = (List<DBUser>) criteria.list();
      for (DBUser u : users) {
        System.out.println(u);
      }
    } catch (HibernateException e) {
      System.out.println("Failed to list users: " + e.getMessage());
    }
    return users;
  }
}
