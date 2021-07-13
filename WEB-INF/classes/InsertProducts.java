/*
This Servlet is backend of insertproducts.html page which is getting 5 parameters from admin using form 
and then storing that product into database if that product code not exist in table of 'products' in database
and then redirect to admin_panel.html page
*/


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class InsertProducts extends HttpServlet 
{
  
  //Process the HTTP Get request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();

		String brandName=request.getParameter("brandname");
		String productName=request.getParameter("productname");
		String productCode = request.getParameter("productcode");
		String productPrice=request.getParameter("productprice");
		String productColour = request.getParameter("productcolour");


		HttpSession sess = request.getSession(false);
		boolean flag1 = true;
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
					flag1 = false;
					try{

					Class.forName("com.mysql.jdbc.Driver");

					String url = "jdbc:mysql://127.0.0.1/akcollection";

					Connection con=DriverManager.getConnection(url, "root", "root");

					String query = "Select * from products;";
					PreparedStatement pst = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = pst.executeQuery();
					boolean flag = true;

					rs.beforeFirst();
					while(rs.next())
					{
						if(rs.getString("productcode").equals(productCode))
						{
							response.sendRedirect("admin_panel.html");
							flag = false;
						}
					}
					if(flag)
					{			
						rs.moveToInsertRow();
						rs.updateString("brandName", brandName);
						rs.updateString("productName", productName);
						rs.updateString("productCode", productCode);
						rs.updateString("productPrice", productPrice);
						rs.updateString("productColour", productColour);
						rs.insertRow();
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
		if(flag1)
		{
			response.sendRedirect("index.html");
		}
	}	
}