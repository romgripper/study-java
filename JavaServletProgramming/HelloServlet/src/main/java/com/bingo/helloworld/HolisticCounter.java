package com.bingo.helloworld;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HolisticCounter extends HttpServlet {
  private static int totalCount = 0;
  private int count = 0;
  private static Set<HolisticCounter> instances = new HashSet<>();

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();
    count++;
    out.println("Since loading, this servlet instance has been accessed " + count + " times.");

    totalCount++;
    out.println(
        "Across all instances, this servlet class has been accessed " + totalCount + " times.");

    instances.add(this);
    out.println("There are currently " + instances.size() + " instances.");
  }
}
