package com.bingo.helloworld;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeaderSnoop extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();
    out.println("Request Headers:");
    out.println();
    Enumeration<String> headerNames = req.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      // The browser will not show the page until receiving all the bytes,
      // So it waits for a long time, if sleep() here.
      /*try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
      }*/
      String name = headerNames.nextElement();
      String value = req.getHeader(name);
      if (value != null) {
        out.println(name + ": " + value);
      }
    }
  }
}
