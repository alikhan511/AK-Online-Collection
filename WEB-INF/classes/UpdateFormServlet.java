/*
This Servlet will be execute when admin will press update button from CustomerAccounts.jsp or AdminAccounts.jsp
and will get two parameters one is username and other is role.
Then create an object of UserBean class then take all data of corresponding username from database and set those values into setters
of USerBean class using objext then call getters and get all values and then they all set as attribute using request.setAttribute() and
the forward to UpdateForm.jsp using RequestDispatching.*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import MyPackage.UserBean;

public class UpdateFormServlet extends HttpServlet 
{
  	
  //Process the HTTP Get request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();

		String userName = request.getParameter("username");
		String role = request.getParameter("role");
		UserBean bean = new UserBean();

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
					try
					{
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
									bean.setFirstName(rs.getString("firstname"));
									bean.setLastName(rs.getString("lastname"));
									bean.setUserName(rs.getString("username"));
									bean.setPassword(rs.getString("password"));
									bean.setEmail(rs.getString("email"));
									bean.setContact(rs.getString("contact"));
									bean.setRole(role);
								}
							}	
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
									bean.setFirstName(rs.getString("firstname"));
									bean.setLastName(rs.getString("lastname"));
									bean.setUserName(rs.getString("username"));
									bean.setPassword(rs.getString("password"));
									bean.setEmail(rs.getString("email"));
									bean.setContact(rs.getString("contact"));
									bean.setRole(role);
								}
							}	
						}
					}
					catch(Exception e)
					{
						out.println(e);
					}

					request.setAttribute("fname", bean.getFirstName());
					request.setAttribute("lname", bean.getLastName());
					request.setAttribute("username", bean.getUserName());
					request.setAttribute("password", bean.getPassword());
					request.setAttribute("email", bean.getEmail());
					request.setAttribute("contact", bean.getContact());										
					request.setAttribute("role", bean.getRole());

					RequestDispatcher rd = request.getRequestDispatcher("/UpdateForm.jsp");
					rd.forward(request, response);
				}
			}
		}
		if(flag)
		{
			response.sendRedirect("index.html");
		}
	}
}