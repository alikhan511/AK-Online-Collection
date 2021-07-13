/*
This servlet is backend of index.html and getting three parameters username, password and role
if username exist and password is true then create a session and set username as 'key' and role as 'value'
and redirect to shopping_page.html otherwise redirect to loginfail.html
*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class Login extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();

		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");

		HttpSession session = request.getSession(false);
		if(session!=null)
		{
			session.invalidate();
		}

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
				if(rs.getString("username").equals(userName))
				{
					if(rs.getString("password").equals(password))
					{	
						HttpSession sess = request.getSession();
						sess.setAttribute(userName, role);	
						response.sendRedirect("admin_panel.html");
					}
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
				if(rs.getString("username").equals(userName))
				{
					if(rs.getString("password").equals(password))
					{	
						HttpSession sess = request.getSession();
						sess.setAttribute(userName, role);	
						response.sendRedirect("shopping_page.html");
					}
				}
			}
			pst.close();	
		}
		
		
		response.sendRedirect("loginfail.html");		
		
		con.close();

		}
		catch(Exception e)
		{
			out.println(e);
		}
	}
}