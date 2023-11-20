<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, com.model.Scans, com.model.User, com.dao.*" %>
    <%            	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
 %>
 <% if (session.getAttribute("user")== null){ 
	response.sendRedirect("login.jsp");
	return;
 }
 else{
	 User user = (User)session.getAttribute("user");
	 if(!user.getName().equals("admin")){
		response.sendRedirect("unauth.jsp");
		return;
	 }
 }

%>
<jsp:include page="adminheader.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<title>Admin Dashboard</title>
<link rel="stylesheet" href="css/admin.css">
</head>
<body>
<h1>Welcome to Admin Dashboard,</h1><h2>Navigate to <a href="users.jsp">Users page</a> to view and modify the list of users</h2><br>
Or, to view and modify scan history, visit <a href="history.jsp">Scan History</a>.
</body>
</html>