/*
This servlet is backend of UpdateForm.jsp which is getting parameters from Form and updating that data in database 
according to that username and then redirect to admin_panel.html
*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class UpdateRecord extends HttpServlet
{
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
									rs.updateString("firstname", firstName);
									rs.updateString("lastname", lastName);
									rs.updateString("password", password);
									rs.updateString("email", email);
									rs.updateString("contact", contact);
									rs.updateRow();
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
									rs.updateString("firstname", firstName);
									rs.updateString("lastname", lastName);
									rs.updateString("password", password);
									rs.updateString("email", email);
									rs.updateString("contact", contact);
									rs.updateRow();
								}
							}
							pst.close();
						}
						con.close();
						response.sendRedirect("admin_panel.html");
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