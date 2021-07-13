/*This servlet is backend of form suscribe at many pages which get just email as parameter and simply save in subscribe table 
and then redirect to shopping_page.html */


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class Subscribe extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");

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
				if(checkRole.equals("customer"))
				{
					flag1 = false;
					try{

					Class.forName("com.mysql.jdbc.Driver");

					String url = "jdbc:mysql://127.0.0.1/akcollection";

					Connection con=DriverManager.getConnection(url, "root", "root");

					String query = "Select * from subscribe;";
					PreparedStatement pst = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = pst.executeQuery();
					
					boolean flag=true;
					
					rs.beforeFirst();
					while(rs.next())
					{
						if(rs.getString("email").equals(email))
						{
							response.sendRedirect("shopping_page.html");
							flag = false;
						}
					}
					if(flag)
					{
						rs.moveToInsertRow();
						rs.updateString("email", email);
						rs.insertRow();
					}

					pst.close();	
					
					response.sendRedirect("shopping_page.html");		
					
					con.close();

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