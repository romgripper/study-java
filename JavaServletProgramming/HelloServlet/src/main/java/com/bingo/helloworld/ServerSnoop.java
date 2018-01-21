package com.bingo.helloworld;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServerSnoop extends GenericServlet {

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    out.println("From servlet init parameters:");
    Enumeration<String> params = getInitParameterNames();
    while (params.hasMoreElements()) {
      String param = params.nextElement();
      out.println(param + ": " + getInitParameter(param));
    }
    out.println();

    out.println("From request:");
    out.println("Server name: " + req.getServerName());
    out.println("Server port: " + req.getServerPort());
    out.println("Prototol: " + req.getProtocol());
    out.println();

    out.println("From servlet context:");
    ServletContext context = getServletContext();
    out.println("Servlet config: " + context);
    out.println("Server info: " + context.getServerInfo());
    out.println("Context path: " + context.getContextPath());
    out.println();

    out.println("Form servlet context parameters:");
    params = context.getInitParameterNames();
    while (params.hasMoreElements()) {
      String param = params.nextElement();
      out.println(param + ": " + getInitParameter(param));
    }
  }

  public void setServletName(String servletName) {
    System.out.println("Servlet name: " + servletName);
  }
}
