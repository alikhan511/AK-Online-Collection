<!-- This jsp page get all attributes which was set in UpdateFormServlet and show all those data in form to Admin he can change now 
data and then click Update Form button then UpdateRecord Servlet will execute we also send two hidden fields to UpdateRecord which is username and role  -->

<%@page language = "java" %>
<%@page import="java.util.*" %>


<%
	String firstName=(String) request.getAttribute("fname");
	String lastName= (String) request.getAttribute("lname");
	String userName = (String) request.getAttribute("username");
	String password=(String) request.getAttribute("password");
	String email = (String) request.getAttribute("email");
	String contact = (String) request.getAttribute("contact"); 
	String role = (String) request.getAttribute("role"); 
	
%>

<!DOCTYPE html>
<html>
<head>
	<title>Updation Form</title>
	<link rel="stylesheet" type="text/css" href="css_files\stylish.css">
	<script language="JavaScript" type="text/javaScript">

		function letternumber(event)
		{
			var keychar;
			keychar = event.key;
			// alphas and numbers
			if (((".+-0123456789").indexOf(keychar) > -1))
			return true;
			else
			return false;
		}
		function ValidateAlpha(evt)
		{
			var keyCode = (evt.which) ? evt.which : evt.keyCode
			if ((keyCode < 65 || keyCode > 90) && (keyCode < 97 || keyCode > 123) && keyCode != 32)
			{	
				return false;
			}
			return true;
		}
	</script>
</head>
<body>
	<div class="loginbox">
		<img src="Login.png" class="avatar">
		<h1>Signup Here</h1>
		<form method="POST" name="employer" action="UpdateRecord" onsubmit="return validate()">
			<p>Enter First Name</p>
			<input name="fname" class="form-control"  type="text" required onkeypress="return ValidateAlpha(event)" value=<%= firstName %> />
			<p>Enter Last Name</p>
			<input name="lname" class="form-control" type="text" required onkeypress="return ValidateAlpha(event)" value=<%= lastName %> />
			<p>Enter Email</p>
			<input name="email" class="form-control"  type="text" pattern= "[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required  value=<%= email %> >
			<p>Enter Contact</p>
			<input name="contact"  class="form-control" type="text" required onkeypress="return letternumber(event)" value=<%= contact %> />
			<p>Enter Password</p>
			<input name="password"  class="form-control" type="password" required max="8" value=<%= password %> />

			<input type="hidden" name="role" value=<%= role %> />
			<input type="hidden" name="username" value=<%= userName %> />
			<input type="submit" name="submit" value="Update the Record">
		</form>	
	</div>
</body>
</html>