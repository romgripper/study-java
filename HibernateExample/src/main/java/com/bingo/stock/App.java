package com.bingo.stock;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.bingo.util.HibernateUtil;

public class App {

  private static void testStock(SessionFactory factory) {
    try (Session session = factory.openSession()) {
      session.beginTransaction();

      Stock stock = new Stock();
      Random random = new Random();
      int code = random.nextInt(1000000);
      stock.setCode("" + code);
      stock.setName("PADINI " + code);

      StockDetail stockDetail = new StockDetail();
      stockDetail.setCompName("PADINI Holding Malaysia " + code);
      stockDetail.setCompDesc("one stop shopping " + code);
      stockDetail.setRemark("vinci vinci");
      stockDetail.setListedDate(new Date());

      stock.setDetail(stockDetail);
      stockDetail.setStock(stock); // This line cannot be omitted

      StockDailyRecord stockDailyRecord = new StockDailyRecord();
      stockDailyRecord.setPriceOpen(new Float("1.2"));
      stockDailyRecord.setPriceClose(new Float("1.1"));
      stockDailyRecord.setPriceChange(new Float("10.0"));
      stockDailyRecord.setVolume(3000000L);
      Date today = new Date();
      stockDailyRecord.setDate(today);

      stockDailyRecord.setStock(stock);
      stock.getRecords().add(stockDailyRecord);

      stockDailyRecord = new StockDailyRecord();
      stockDailyRecord.setPriceOpen(new Float("1.2"));
      stockDailyRecord.setPriceClose(new Float("1.1"));
      stockDailyRecord.setPriceChange(new Float("10.0"));
      stockDailyRecord.setVolume(3000000L);
      long timeadj = 24 * 60 * 60 * 1000;
      Date tomorrow = new Date(today.getTime() + timeadj);
      stockDailyRecord.setDate(tomorrow);

      stockDailyRecord.setStock(stock);
      stock.getRecords().add(stockDailyRecord);

      session.save(stock);
      session.getTransaction().commit();

      List<Stock> stocks = session.createCriteria(Stock.class).list();
      System.out.println("Stocks:");
      for (Stock s : stocks) {
        System.out.println(s);
        Set<StockDailyRecord> records = s.getRecords();
        if (!records.isEmpty()) {
          System.out.println("  Records:");
          for (StockDailyRecord r : records) {
            System.out.println("    " + r);
          }
        }
      }

      System.out.println(stock);
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    System.out.println("Done");
  }

  public static void main(String[] args) {
    System.out.println("Maven + Hibernate + MySQL");
    SessionFactory factory = HibernateUtil.getSessionFactory();
    testStock(factory);

    HibernateUtil.shutdown();
  }
}
