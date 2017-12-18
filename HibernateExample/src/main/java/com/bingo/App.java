package com.bingo;

import java.util.Date;

import org.hibernate.Session;

import com.bingo.user.DBUser;
import com.bingo.util.HibernateUtil;

public class App {
  public static void main(String[] args) {
    System.out.println("Maven + Hibernate + MySQL");
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();
    DBUser user = new DBUser(100, "superman", "system", new Date());
    session.save(user);
    session.getTransaction().commit();

    HibernateUtil.shutdown();
  }
}
