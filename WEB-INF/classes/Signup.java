/* This servlet is backend of singup.html which is taking parameters of form and check in database 
if given username and contact exist or not if exist then simply redirect to useralready.html and contactalready.html respectively 
if not exist then creat a new account save data according to role if role is admin then save in admin table otherwise save in
customer table. if successfully create account then simply redirect to index.html
*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class Signup extends HttpServlet 
{
  
  //Process the HTTP Get request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();

		String firstName=request.getParameter("fname");
		String lastName=request.getParameter("lname");
		String userName = request.getParameter("username");
		String password=request.getParameter("password");
		String email = request.getParameter("email");
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
				if(rs.getString("username").equals(userName))
				{
					response.sendRedirect("useralready.html");
					flag = false;
				}
				
				if(rs.getString("contact").equals(contact))
				{
					response.sendRedirect("contactalready.html");
					flag = false;
				}
			}
			if(flag)
			{			
				rs.moveToInsertRow();
				rs.updateString("firstname", firstName);
				rs.updateString("lastname", lastName);
				rs.updateString("username", userName);
				rs.updateString("password", password);
				rs.updateString("email", email);
				rs.updateString("contact", contact);
				rs.insertRow();
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
				if(rs.getString("username").equals(userName))
				{
					response.sendRedirect("useralready.html");
					flag = false;
				}
				
				if(rs.getString("contact").equals(contact))
				{
					response.sendRedirect("contactalready.html");
					flag = false;
				}
			}
			if(flag)
			{			
				rs.moveToInsertRow();
				rs.updateString("firstname", firstName);
				rs.updateString("lastname", lastName);
				rs.updateString("username", userName);
				rs.updateString("password", password);
				rs.updateString("email", email);
				rs.updateString("contact", contact);
				rs.insertRow();
			}
			pst.close();
		}
		response.sendRedirect("index.html");
	
		con.close();

		}
		catch(Exception e)
		{
			out.println(e);
		}
	}	
}