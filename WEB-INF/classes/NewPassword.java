/*
This servlet is backend of newpassword.html page and getting two attributes from previous servlet which is
conatct and rule and get parameter of password and update password to corresponding conatct and then redirect
to changepassword.html 
*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;


public class NewPassword extends HttpServlet 
{  
  //Process the HTTP Get request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();
		
		String contact = (String)request.getSession().getAttribute("contact");
		String role = (String)request.getSession().getAttribute("role");
		String password = request.getParameter("password");
		
		try{

		Class.forName("com.mysql.jdbc.Driver");

		String url = "jdbc:mysql://127.0.0.1/akcollection";

		Connection con=DriverManager.getConnection(url, "root", "root");

		if(role.equals("admin"))
		{
			String query = "Select * from admin;";
			PreparedStatement pst = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = pst.executeQuery();

			rs.beforeFirst();
			while(rs.next())
			{					
				if(rs.getString("contact").equals(contact))
				{
					rs.updateString("password", password);
					rs.updateRow();
					response.sendRedirect("changepassword.html");
				}
			}		
			pst.close();
		}
		else
		{
			String query = "Select * from customer;";
			PreparedStatement pst = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = pst.executeQuery();

			rs.beforeFirst();
			while(rs.next())
			{					
				if(rs.getString("contact").equals(contact))
				{
					rs.updateString("password", password);
					rs.updateRow();
					response.sendRedirect("changepassword.html");
				}
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