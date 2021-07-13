<!--
This JSP page will be execute when admin will create Edit Customer Accounts Button from admin_panel.html page
this page will take all data from customer table and show details of customer accounts with two buttons 'update' and 'delete'
when admin press update button then UpdateFormServlet will execute and by clicking delete button 
DeleteAccount Servlet will execute.
And also we're sending two hidden fields one is username and other is role to UpdateFormServlet and DeleteAccount-->

<%@page language = "java" import="java.util.*" %> 
<%@page import="javax.servlet.http.*" %>
<%@page import="java.io.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="java.sql.*"%>

<!DOCTYPE html>
 <html>
 <head>
 	<title>Customer Accounts</title>
 </head>
 <body>
 	<center>
		<h2>Edit Customer Accounts</h2>
		<table border = "1" width = "400">
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>User Name</th>
				<th>Email</th>
				<th>Contact</th>
				<th>Update Accounts</th>
				<th>Delete Accounts</th>
			</tr>
				<%
					
					boolean flag = true;
					if(session != null)
					{
						Enumeration en = session.getAttributeNames();
						while(en.hasMoreElements())
						{
							Object obj = en.nextElement();
							String u_name = (String)obj;
							String checkRole = (String)session.getAttribute(u_name);
						
							if(checkRole.equals("admin"))
							{
								flag = false;
								String role=request.getParameter("role");	
								try{
								Class.forName("com.mysql.jdbc.Driver");
								String url = "jdbc:mysql://127.0.0.1/akcollection";
								Connection con=DriverManager.getConnection(url, "root", "root");
								if(role.equals("customer"))
								{
									String query = "Select * from customer;";
									PreparedStatement pst = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
									ResultSet rs = pst.executeQuery();

									rs.beforeFirst();
									while(rs.next())
									{
										String firstName = rs.getString("firstname");
										String lastName = rs.getString("lastname");
										String userName = rs.getString("username");
										String email = rs.getString("email");
										String contact = rs.getString("contact");

							%>
					  		<tr>
					  		<td><%= firstName %></td>
					  		<td><%= lastName %></td>
					  		<td><%= userName %></td>
					  		<td><%= email %></td>
					  		<td><%= contact %></td>
					  		<td>
					  			<form method="POST" action="UpdateFormServlet">
					  				<input type="hidden" name="role" value = "<%= role %>" >
					  				<input type="hidden" name="username" value="<%= userName %>">
					  				<input type="submit" name="update" value="Update">
					  			</form>
					  		</td>
					  		<td>	
				  	  			<form method="POST" action="DeleteAccount">
					  				<input type="hidden" name="role" value="<%= role %>">
					  				<input type="hidden" name="username" value="<%= userName %>">
					  				<input type="submit" name="delete" value="Delete">
						  		</form>
					  		</td>
					  		</tr>
					  		<%
					  		}	
								pst.close();							
							}
							con.close();
							}
							catch(Exception e)
							{
								out.println(e);
							}

						}
					}
					if(flag)
					{
						response.sendRedirect("index.html");
					}
				}
				// else
				// {
				// 	response.sendRedirect("index.html");
				// }
							%>
				</table>
		</center>
 </body>
 </html> 