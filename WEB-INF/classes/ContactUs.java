/*
This Servlet is backend of contact.html which is getting four parameters from 'Form'
and save data in contact table in database. and then redirect to contact.html
*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class ContactUs extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();

		String name = request.getParameter("name");		
		String email = request.getParameter("email");
		String website = request.getParameter("website");
		String message = request.getParameter("message");
		HttpSession sess = request.getSession(false);
		boolean flag = true;
		if(sess != null)
		{
			Enumeration en = sess.getAttributeNames();
			while(en.hasMoreElements())
			{
				Object obj = en.nextElement();
				String u_name = (String)obj;
				String checkRole = (String)sess.getAttribute(u_name);
			
				if(checkRole.equals("customer"))
				{
					flag = false;

					try{

					Class.forName("com.mysql.jdbc.Driver");

					String url = "jdbc:mysql://127.0.0.1/akcollection";

					Connection con=DriverManager.getConnection(url, "root", "root");

					String query = "Select * from contactus;";
					PreparedStatement pst = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = pst.executeQuery();
					
					rs.moveToInsertRow();
					rs.updateString("name", name);
					rs.updateString("email", email);
					rs.updateString("website", website);
					rs.updateString("message", message);
					rs.insertRow();

					pst.close();	
					
					response.sendRedirect("contact.html");		
					
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