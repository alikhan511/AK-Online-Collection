/*
This Servlet will execute when admin will press delete button of corresponding record from CustomerAccounts.jsp or AdminAccounts.jsp
either that is customer account or admin account. servlet is getting two parameters as hidden fields one is username and other is role. 
delete button is in form where there are two hidden fields.
check role and delete corresponding record from corresponding table and simply redirect to admin_panel.html and if given username
doesn't exist in database simply redirect to admin_panel.html*/


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class DeleteAccount extends HttpServlet 
{
  //Process the HTTP Get request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();

		String role = request.getParameter("role");
		String userName = request.getParameter("username");

		HttpSession sess = request.getSession(false);
		boolean flag = true;
		if(sess!=null)
		{
			Enumeration en = sess.getAttributeNames();
			while(en.hasMoreElements())
			{
				Object obj = en.nextElement();
				String u_name = (String)obj;
				String checkRole = (String)sess.getAttribute(u_name);
				if(checkRole.equals("admin"))
				{
					flag = false;
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
								rs.deleteRow();
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
								rs.deleteRow();
							}
						}
						pst.close();
						
					}	
					response.sendRedirect("admin_panel.html");
							
					con.close();
					}
					catch(Exception e)
					{
						out.println(e);
					}
				}
			}
		}
		if(flag)
		{
			response.sendRedirect("index.html");
		}
	}	
}