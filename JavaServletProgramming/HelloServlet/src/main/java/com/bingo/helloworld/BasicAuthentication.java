package com.bingo.helloworld;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicAuthentication extends HttpServlet {
  Hashtable<String, Boolean> validUsers = new Hashtable<>();

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    // This user has no password
    validUsers.put("bingo:", true);
    validUsers.put("bin:mypassword", true);
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    // Get Authorization header
    String auth = req.getHeader("Authorization");
    // Do we allow that user?
    if (!allowUser(auth)) {
      /* Tell the browser to ask user to input credentials. Without this header, credential
       * dialog will not show, and Authorization header will not be included in the request.
       */
      res.setHeader("WWW-Authenticate", "BASIC");
      // Send the error code: 401 unauthorized
      res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      // Could offer to add him to the allowed user list
    } else {
      // Allowed, so show him the secret stuff
      out.println("Hello");
    }
  }
  // This method checks the user information sent in the Authorization
  // header against the database of users maintained in the users Hashtable.
  protected boolean allowUser(String auth) throws IOException {

    if (auth == null) {
      return false; // no auth
    }
    if (!auth.toUpperCase().startsWith("BASIC ")) {
      return false; // we only do BASIC
    }
    // Get encoded user and password, comes after "BASIC "
    String userpassEncoded = auth.substring(6);
    // Decode it, using any base 64 decoder
    Base64.Decoder dec = Base64.getDecoder();
    String userpassDecoded = new String(dec.decode(userpassEncoded));

    // Check our user list to see if that user and password are "allowed"
    if (validUsers.containsKey(userpassDecoded)) {
      return true;
    } else {
      return false;
    }
  }
}
