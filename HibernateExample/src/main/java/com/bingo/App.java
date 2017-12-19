package com.bingo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bingo.user.DBUser;
import com.bingo.util.HibernateUtil;

public class App {
  public static void main(String[] args) {
    System.out.println("Maven + Hibernate + MySQL");
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

      //session.beginTransaction();
      Transaction transaction = session.beginTransaction();
      DBUser user = new DBUser(100, "superman", "system", new Date());
      session.saveOrUpdate(user);
      user = new DBUser(101, "superwoman", "system", new Date());
      session.saveOrUpdate(user);
      user = new DBUser(102, "superson", "superwoman", new Date());
      session.saveOrUpdate(user);
      user = new DBUser(103, "superdaughter", "superwoman", new Date());
      session.saveOrUpdate(user);
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
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    HibernateUtil.shutdown();
  }
}
