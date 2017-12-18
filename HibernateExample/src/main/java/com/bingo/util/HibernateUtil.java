package com.bingo.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

  private static final SessionFactory sessionFactory = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      // hibernate.cfg.xml is by default expected to be in the root of CLASSPATH.
      return new Configuration().configure().buildSessionFactory();
    } catch (Exception ex) {
      System.err.println("Initial SessionFactory creation failed." + ex.getMessage());
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static void shutdown() {
    getSessionFactory().close();
  }
}
