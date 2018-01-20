package com.bingo.helloworld;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrimeSearcher extends HttpServlet {

  long lastPrime = 0;
  Date lastPrimeModified = new Date();
  Thread searcher;
  volatile boolean searching;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    startSearch();
  }

  private void startSearch() {
    searcher = new Thread(() -> search());
    searcher.setDaemon(true);
    searching = true;
    searcher.start();
  }

  private void stopSearch() {
    searching = false;
  }

  private void search() {
    System.out.println("Start searching primes");
    long candidate = 10000000001L;
    while (searching) {
      if (isPrime(candidate)) {
        System.out.println("Found prime: " + candidate);
        lastPrime = candidate;
        lastPrimeModified = new Date();
      }
      candidate += 2;
      try {
        Thread.sleep(200);
      } catch (InterruptedException ignored) {

      }
    }
  }

  private static boolean isPrime(long candidate) {
    double sqrt = Math.sqrt(candidate);
    for (long i = 3; i <= sqrt; i += 2) {
      if (candidate % i == 0) {
        return false;
      }
    }
    return true;
  }

  public void destroy() {
    stopSearch();
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();
    if (lastPrime == 0) {
      out.println("Still searching for first prime...");
    } else {
      out.print("The last prime discovered was " + lastPrime);
      out.println(" at " + lastPrimeModified);
    }
  }
}
