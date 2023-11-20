<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%            	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
 %>
 <jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Malware Scanner Home</title>
<link rel="stylesheet" href="css/auth.css">
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
	<link rel="stylesheet" href="css/styles.css">
<script src="https://kit.fontawesome.com/166366233a.js"
	crossorigin="anonymous"></script>
</head>
<body>

<div class="center">
         <div class="container">
            <div class="text">
               Sign Up
            </div>
            <form action="register" method="post">
               <div class="data">
                  <label>Username</label>
                  <input type="text" name="user" required>
               </div>
               <div class="data">
                  <label>Email</label>
                  <input type="email" name="email" required>
               </div>
               <div class="data">
                  <label>Password</label>
                  <input type="password" name="pass" required>
               </div>
               <div class="signup-link">
                  Already have an account? <a href="login.jsp">Login instead</a>
               </div>
               <div class="btn">
                  <div class="inner"></div>
                  <button type="submit">Sign Up</button>
               </div>
            </form>
         </div>
      </div>
</body>
</html>
