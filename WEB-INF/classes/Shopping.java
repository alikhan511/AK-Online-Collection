/*
This Servlet is backend of shopping.html page which is getting 3 parameters from customer using form 
where 2 are hidden fields one is of dress price and 2nd is of dress code
and 3rd parameter is as input field quantity of dress and then i'm making string of price & quantity which is concating by comma 
and if session exist then storing dress code as 'key' and price_quantity as 'value' in session. and then rediredct to shopping page for
more shopping otherwise if session not exist redirect to index page which is login page
*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class Shopping extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");

		// get PrintWriter object
		PrintWriter out = response.getWriter();

		String code = request.getParameter("code");
		String price = request.getParameter("price");
		String quantity = request.getParameter("quantity");
		String price_quantity = price+","+ quantity; 

		boolean flag = true;
		HttpSession sess = request.getSession(false);
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
					sess.setAttribute(code, price_quantity);		
					response.sendRedirect("shopping.html");
				}
			}
		}
		if(flag)
		{
			response.sendRedirect("index.html");			
		}

	}
}