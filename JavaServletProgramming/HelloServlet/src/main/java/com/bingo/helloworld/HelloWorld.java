package com.bingo.helloworld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorld extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    res.setContentType("text/html");
    if (req.getMethod().equals("HEAD")) {
      return;
    }

    String name = req.getParameter("name");
    if (name != null) {
      doPost(req, res);
      return;
    }

    PrintWriter out = res.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>Hello</title></head>");
    out.println("<body>");
    out.println(
        "<form method='post' action='/HelloServlet/hello'>"
            + "If you don&quot;t mind me asking, what is your name?"
            + "<input type='text' name='name'><p>"
            + "<input type='submit' value='Submit'></p>"
            + "</form>");
    out.println("</body>");
    out.println("</html>");
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    String name = req.getParameter("name");

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>Hello " + name + "</title></head>");
    out.println("<body><h1>Hello " + name + "</h1>");
    out.println("</body>");
    out.println("</html>");
  }

  @Override
  public String getServletInfo() {
    return "A servlet that knows the name of the person to whom it's saying hello";
  }
}
