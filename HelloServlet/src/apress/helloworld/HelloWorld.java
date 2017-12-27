package apress.helloworld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/h" }, description = "A hello world servlet")
public class HelloWorld extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//request.getRequestDispatcher("/hello.jsp").forward(request, response);
			response.setContentType("text/html");
			PrintWriter printWriter = response.getWriter();
			printWriter.println("<h2>");
			printWriter.println("Hello World");
			printWriter.println("</h2>");
			printWriter.println("<h2>");
			printWriter.println("Contact: " + getInitParameter("email"));
			printWriter.println("</h2>");
			printWriter.println(getServletContext().getInitParameter("email"));
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
