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

      // If the following line is omitted, no stock detail is inserted into DB.
      stock.setDetail(stockDetail);
      // If the following line is ommited, "attempted to assign id from null one-to-one property" is reported
      stockDetail.setStock(stock);

      StockDailyRecord stockDailyRecord = new StockDailyRecord();
      stockDailyRecord.setPriceOpen(new Float("1.2"));
      stockDailyRecord.setPriceClose(new Float("1.1"));
      stockDailyRecord.setPriceChange(new Float("10.0"));
      stockDailyRecord.setVolume(3000000L);
      Date today = new Date();
      stockDailyRecord.setDate(today);

      /* If the following line is omitted, "not-null property references a null or transient value :
       * com.bingo.stock.StockDailyRecord.stock" is reported
       */
      stockDailyRecord.setStock(stock);
      // If the following line is omitted, the record is not inserted into DB.
      stock.getRecords().add(stockDailyRecord);

      stockDailyRecord = new StockDailyRecord();
      stockDailyRecord.setPriceOpen(new Float("1.2"));
      stockDailyRecord.setPriceClose(new Float("1.1"));
      stockDailyRecord.setPriceChange(new Float("10.0"));
      stockDailyRecord.setVolume(3000000L);
      long timeadj = 24 * 60 * 60 * 1000;
      Date tomorrow = new Date(today.getTime() + timeadj);
      stockDailyRecord.setDate(tomorrow);

      /* If the following line is omitted, "not-null property references a null or transient value :
       * com.bingo.stock.StockDailyRecord.stock" is reported
       */
      stockDailyRecord.setStock(stock);
      stock.getRecords().add(stockDailyRecord);

      /* When a Stock is inserted, the associated StockDetail is inserted, whose ID (STOCK_ID) is derived from Stock,
       * and the associated StockDailyRecord(s) are inserted, whose "STOCK_ID" column is derived from Stock.
       * So if either stock is not set for StockDetail or StockDailyRecord, error is reported as the STOCK_ID columns
       * of both tables cannot be null.
       */
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
