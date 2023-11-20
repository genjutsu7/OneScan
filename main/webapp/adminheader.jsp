<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.model.User"%>
<% 
    User user = (User) session.getAttribute("user");
    String greeting;
    if (user == null) {
        greeting = "Not logged in";
    } else {
        String name = user.getName();
        greeting = "Hi, " + name;
    }
    
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>OneScan Home</title>
<link rel="stylesheet" href="css/index.css">

<link
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700"
	rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;400&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Anton&family=Poppins:wght@100;400&display=swap"
	rel="stylesheet">
<script src="https://kit.fontawesome.com/166366233a.js"
	crossorigin="anonymous"></script>
</head>
<body>
<div class="navbar">
		<a href="admin.jsp" class="logo">Admin Dashboard</a>
		<nav>
			<ul class="links">
				<li><a href="history.jsp">SCANS</a></li>
				<li><a href="users.jsp">USERS</a></li>
  <% 
            if (session.getAttribute("user") != null) { 
            %>
                <li>
<form action="logout" method="post">
                        <button class="logout-btn" type="submit" cursor: pointer;">
<i class="fa-solid fa-arrow-right-from-bracket fa-lg"></i>                        
						</button>
                	</form>
                </li>
                <label class="profile"> <i class="fa-regular fa-user fa-lg" style="color: #ffffff;"></i>
                <div class="dropdown-content">
        			 <a href="#"><%= greeting %></a>
        		</div>
				</label>
            <% 
            }  
            %>
			</ul>
		</nav>
	</div>
	
</body>
</html>