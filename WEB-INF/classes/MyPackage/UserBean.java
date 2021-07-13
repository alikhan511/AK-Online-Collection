/*This is bean class which have just attributes and thier setters and getters with no argument constructer*/

package MyPackage;
import java.io.*;
import java.util.*;

public class UserBean implements Serializable
{ 
	private	String firstName;
	private	String lastName;
	private	String userName;
	private	String password;
	private	String email;
	private	String contact;
	private	String role;

	// no argument constructor 
	public UserBean() 
	{
		firstName = "";
		lastName = "";
		userName = "";
		password = "";
		email = "";
		contact = "";
		role = "";
	} 

	// setters 
	public void setFirstName(String first)
	{
		firstName = first;
	} 

	public void setLastName(String last)
	{
		lastName = last;
	} 

	public void setUserName(String user)
	{ 
	 	userName = user;
	}

	public void setPassword(String pass)
	{ 
	 	password = pass;
	}

	public void setEmail(String mail)
	{ 
	 	email = mail;
	}

	public void setContact(String cont)
	{ 
	 	contact = cont;
	}

	public void setRole(String role1)
	{ 
	 	role = role1;
	}
	 
	// getters 
	public String getFirstName( )
	{ 
		 return firstName; 
	} 

	public String getLastName( )
	{ 
		 return lastName; 
	} 

	public String getUserName( )
	{ 
		 return userName; 
	}

	public String getPassword( )
	{ 
		 return password; 
	}

		public String getEmail( )
	{ 
		 return email; 
	}

	public String getContact( )
	{ 
		 return contact; 
	} 

	public String getRole( )
	{ 
		return role; 
	} 

} // end class 