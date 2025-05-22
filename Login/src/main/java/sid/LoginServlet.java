package sid;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		String n = request.getParameter("txtName");
		String p = request.getParameter("txtPwd");

		// Name validation: Starts with a capital letter and at least 3 characters
		if (n == null || !n.matches("^[A-Z][a-zA-Z]{2,}$")) {
			out.println("<font color='red' size='18'>Invalid Name!<br>");
			out.println("Name must start with a capital letter and have at least 3 characters.<br>");
			out.println("<a href='login.jsp'>Try AGAIN!!</a>");
			return;
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sid", "root", "Sai@2004");

			PreparedStatement ps = con.prepareStatement("select uname from login where uname=? and password=?");
			ps.setString(1, n);
			ps.setString(2, p);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
				rd.forward(request, response);
			} else {
				out.println("<font color='red' size='18'>Login Failed!!<br>");
				out.println("<a href='login.jsp'>Try AGAIN!!</a>");
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			out.println("<font color='red'>Internal error occurred. Please try again later.</font>");
		}
	}
}
