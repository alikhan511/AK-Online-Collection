/*
This Servlet is backend of deleteproducts.html which is getting 2 parametrs as input fields 
one is brand name and other is product code and check that in database if that record exist in database simply delete that record 
and redirect to admin_panel.html page if record not exist simply redirect to admin_panel.html*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class DeleteProducts extends HttpServlet 
{
  
  //Process the HTTP Get request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();

		String brandName=request.getParameter("brandname");
		String productCode = request.getParameter("productcode");
		
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

					String query = "Select * from products;";
					PreparedStatement pst = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = pst.executeQuery();
					
					rs.beforeFirst();
					while(rs.next())
					{
						if(rs.getString("brandName").equals(brandName) && rs.getString("productCode").equals(productCode))
						{
							rs.deleteRow();
						}
					}
					pst.close();	
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