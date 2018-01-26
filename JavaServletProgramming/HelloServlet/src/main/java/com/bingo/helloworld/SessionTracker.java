package com.bingo.helloworld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionTracker extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    HttpSession session = req.getSession(true);
    int c = 0;
    // It follows that for any two strings s and t, s.intern() == t.intern() is true if and
    // only if s.equals(t) is true.
    synchronized (session.getId().intern()) {
      Integer count = (Integer) session.getAttribute("count");
      if (count == null) {
        count = 0;
      }
      count = count + 1;
      session.setAttribute("count", count);
      c = count;
    }

    out.println("<HTML><HEAD><TITLE>SessionTracker</TITLE></HEAD>");
    out.println("<BODY><H1>Session Tracking Demo</H1>");
    // Display the hit count for this page
    out.println("You've visited this page " + c + ((c == 1) ? " time." : " times."));
    out.println("</BODY></HTML>");
  }
}
