/* This servlet is backend of shopping.html which get all parameters from Form and save that in shopping table in database
and then redirect to showBill.jsp */

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class ShoppingForm extends HttpServlet 
{
  
  //Process the HTTP Get request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();

		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String tehsil = request.getParameter("tehsil");
		String country = request.getParameter("country");

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
				if(checkRole.equals("customer"))
				{
					flag = false;

					try{

					Class.forName("com.mysql.jdbc.Driver");

					String url = "jdbc:mysql://127.0.0.1/akcollection";

					Connection con=DriverManager.getConnection(url, "root", "root");

					String query = "Select * from shopping;";
					PreparedStatement pst = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = pst.executeQuery();
				
					rs.moveToInsertRow();
					rs.updateString("fname", fname);
					rs.updateString("lname", lname);
					rs.updateString("email", email);
					rs.updateString("contact", contact);
					rs.updateString("address", address);
					rs.updateString("city", city);
					rs.updateString("tehsil", tehsil);
					rs.updateString("country", country);
					rs.insertRow();
					pst.close();
					
					response.sendRedirect("ShowBill.jsp");
				
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