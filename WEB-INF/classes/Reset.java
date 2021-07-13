/*This servlet is backend of reset.html which get two parameters contact and role 
and check that contact exist or not in database and then forward two parameters using requestDispatching
to newpassword.html if conatct not exist then simply redirect to contactnotexist.html 
*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class Reset extends HttpServlet 
{
  
  //Process the HTTP Get request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		 response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();
		
		String contact = request.getParameter("contact");
		String role = request.getParameter("role");
		
		try{

		Class.forName("com.mysql.jdbc.Driver");

		String url = "jdbc:mysql://127.0.0.1/akcollection";

		Connection con=DriverManager.getConnection(url, "root", "root");

		if(role.equals("admin"))
		{
			String query = "Select * from admin;";
			PreparedStatement pst = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = pst.executeQuery();
			
			boolean flag = true;
			
			rs.beforeFirst();
			while(rs.next())
			{		
				if(rs.getString("contact").equals(contact))
				{
					request.getSession().setAttribute("contact", contact);
					request.getSession().setAttribute("role", role);
					RequestDispatcher rd = request.getRequestDispatcher("newpassword.html");
					rd.forward(request,response);
					flag = false;
				}
			}
	 		
			if(flag)
			{
				response.sendRedirect("contactnotexist.html");
			}
			
			pst.close();
		}
		else
		{
			String query = "Select * from customer;";
			PreparedStatement pst = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = pst.executeQuery();
			
			boolean flag = true;
			
			rs.beforeFirst();
			while(rs.next())
			{		
				if(rs.getString("contact").equals(contact))
				{
					request.getSession().setAttribute("contact", contact);
					request.getSession().setAttribute("role", role);
					RequestDispatcher rd = request.getRequestDispatcher("newpassword.html");
					rd.forward(request,response);
					flag = false;
				}
			}
	 		
			if(flag)
			{
				response.sendRedirect("contactnotexist.html");
			}
			
			pst.close();

		}
		
		con.close();

		}
		catch(Exception e)
		{
			out.println(e);
		}
	}
}
