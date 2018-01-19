package com.bingo.helloworld;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitDestroyCounter extends HttpServlet {

  private static final String COUNT_FILE = "count.ini";

  private int count = 0;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(COUNT_FILE))) {
      count = (Integer) (is.readObject());
      System.out.println("Load count: " + count);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
      System.out.println("Failed to read count from init file");
    }
  }

  @Override
  public void destroy() {
    try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(COUNT_FILE))) {
      os.writeObject(Integer.valueOf(count));
      System.out.println("Save count: " + count);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
      System.out.println("Failed to save count to the file");
    }
    super.destroy();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();
    count++;
    out.println("Since loading, this servlet instance has been accessed " + count + " times.");
  }
}
