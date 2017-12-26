package com.bingo;

import java.util.Date;

import com.bingo.dao.DBUserDAO;
import com.bingo.dao.impl.DBUserDAOImpl;
import com.bingo.user.DBUser;
import com.bingo.util.HibernateUtil;

public class App {
  public static void main(String[] args) {
    System.out.println("Maven + Hibernate + MySQL");
    DBUserDAO dao = new DBUserDAOImpl(HibernateUtil.getSessionFactory());

    DBUser user = new DBUser(null, "superman", "system", new Date());
    dao.insertUser(user);
    user.setCreatedDate(new Date());
    dao.insertUser(user);
    user.setCreatedDate(new Date());
    dao.insertOrUpdateUser(user);
    dao.insertUser(new DBUser(null, "superwoman", "system", new Date()));
    dao.insertUser(new DBUser(null, "superson", "superwoman", new Date()));
    dao.insertUser(new DBUser(null, "superdaughter", "superwoman", new Date()));
    dao.listUsers();

    dao.deleteUser(user.getUserId());
    dao.listUsers();

    System.out.println("DBUser with ID = 1: " + dao.findUserById(1));

    dao.listUsersWithName("superwoman");

    dao.listUsernamesCreatedOn(new Date());

    /*try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      //session.beginTransaction();
      Transaction transaction = session.beginTransaction();

      user = new DBUser(null, "superman", "system", new Date());
      session.saveOrUpdate(user);
      user = new DBUser(null, "superwoman", "system", new Date());
      session.saveOrUpdate(user);
      System.out.println(user);
      user = new DBUser(null, "superson", "superwoman", new Date());
      session.saveOrUpdate(user);
      user = new DBUser(null, "superdaughter", "superwoman", new Date());
      session.saveOrUpdate(user);
      System.out.println(user);
      transaction.commit();

      transaction = session.beginTransaction();
      user.setCreatedBy("superman");
      session.update(user);
      // The DBUser after "from" is the class name
      List<DBUser> users = (List<DBUser>) session.createQuery("from DBUser").list();
      for (DBUser u : users) {
        System.out.println(u);
      }
      // Unable to locate persister: BUSER
      //System.out.println(session.get("BUSER", 100));
      System.out.println(session.get(DBUser.class, 101));

      //session.getTransaction().commit();
      transaction.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }*/
    HibernateUtil.shutdown();
  }
}
