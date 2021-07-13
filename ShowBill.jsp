<!-- This page will execute when customer will finalize the shopping and after filling the shopping form details
this page will get retrieve all data from session and string of dress_quantity will divide into two parts and then 
show each dress details which include 'dress code' , 'price' and 'quantity'  and then finalize the bill -->

<%@page language = "java" import="java.util.*" %> 
<%@page import="javax.servlet.http.*" %>
<%@page import="java.io.*" %>
<%@page import="javax.servlet.*" %>

<!DOCTYPE html>
<html>
<head>
	<title>Total Bill</title>
</head>
<h1></h1>
<body>
	<center>
		<h2>Your bill has created!</h2>
		<table border = "1" width = "400">
			<tr>
				<th>Dress Code</th>
				<th>Unit Price</th>
				<th>Quantity</th>
				<th>Total Price</th>
			</tr>
				<%
				String username = null;
				String role = null;
				Double total_price = 0.00;
				if(session != null)
				{
						Enumeration en = session.getAttributeNames();

							String tokens[] = null;

							while(en.hasMoreElements())
							{
								Object obj1 = en.nextElement();
								String code = (String)obj1;
								String line = (String)session.getAttribute(code);

								if(!(line.equals("customer")))
								{
									session.setAttribute(code, null);
							  		tokens = line.split(",");
							  		String price = tokens[0];
							  		String quantity = tokens[1];
							  		%>
							  		<tr>
							  		<td><%= code %></td>
							  		<td><%= price %></td>
							  		<td><%= quantity %></td>
							  		<td><%= Integer.parseInt(price)*Integer.parseInt(quantity) %></td>
							  		</tr>
							  		<%
									Double dress_quantity = Double.parseDouble(quantity);
									Double dress_price = Double.parseDouble(price);
									dress_price = dress_price*dress_quantity;
									total_price = dress_price + total_price;
								}
							}
							String final_price = String.valueOf(total_price); 
					%>
					</table>
					<table border = "1" width = "400">
						<tr>
							<th>Total Bill</th>
							<th><%= final_price %></th>
						</tr>
					</table>
					<br>

					<button>&nbsp&nbsp&nbsp&nbsp&nbsp<a href="shopping.html">Done</a>&nbsp&nbsp&nbsp&nbsp&nbsp</button>
					</center>
				<%
			}
			else
			{
				response.sendRedirect("index.html");	
			}
			
		%>
</body>
</html>