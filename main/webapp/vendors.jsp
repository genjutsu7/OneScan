<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% if (session.getAttribute("user")== null){ 
	response.sendRedirect("login.jsp");
 }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="navbar">
		<a href="index.jsp" class="logo">OneScan</a>
		<nav>
			<ul class="links">
				<li><a href="index.jsp">HOME </a></li>
				<li><a href="admin.jsp">ADMIN</a></li>
				<li><a href="vendors.jsp">VENDOR LIST</a></li>
				<li><a href="scans.jsp">YOUR SCANS</a></li>
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
                <label class="icons"> <i class="fa-solid fa-bars"></i>
				</label>
            <% 
            } else { 
            %>
                <li><a href="login.jsp">SIGN IN</a></li>
            <% 
            } 
            %>

			</ul>
		</nav>
	</div>
		
</body>
</html>
