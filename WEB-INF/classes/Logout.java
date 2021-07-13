/*
This servlet is backend of button signout on many pages this servlet ivalidate session and redirect to index.html page
*/
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;

public class Logout extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession sess = request.getSession(false);

		if(sess != null)
		{
			sess.invalidate();
		}
		response.sendRedirect("index.html");
	}		
}