package com.bingo.helloworld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorld extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    String url = req.getRequestURI();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>Hello World</title></head>");
    out.println("<body><h1>Hello World</h1>");
    out.println("request url: " + url);
    out.println("</body>");
    out.println("</html>");
  }
}
