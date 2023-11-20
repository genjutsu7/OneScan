<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.Scans, com.dao.*, com.model.*" %>
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
<title>Your Scan</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Recent Scans</title>
<link rel="stylesheet" href="css/admin.css">
<link rel="stylesheet" href="css/recent.css">
<link rel="stylesheet" href="css/history.css">


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
	
</script>
</head>
<body>
	<main class="table">
		<table>
			<thead>
				<tr>
					<th>File Name</th>
					<th>Uploaded By</th>
					<th>SHA-256</th>
					<th>Analysis</th>
					<th>Date</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
			<%
			List<Scans> scanList = ScanDAO.listScans();

				for(Scans scan : scanList) { 
			%>
				<tr>
            <td><%= scan.getFilename() %></td>
            <td><%= scan.getUploadedBy() %></td>
            <td><%= scan.getSha256() %></td>
            <td><%= scan.getAnalysis() %></td>
            <td><%= scan.getScanDate() %></td>
			<td><a href="analysis.jsp?hash=<%=scan.getSha256()%>" class="rescan-link" style="color: blue;"><i class="fa-solid fa-up-right-from-square"></i> Rescan</a>
			<form action="deletescan" method="post">
    <input type="hidden" name="scanDate" value="<%=scan.getScanDate()%>">
    <button type="submit"> 
      <i class="fas fa-trash"></i>
    </button>
  </form>
  </td>
				</tr>
				 <% } %>
			</tbody>
		</table>
	</main>
</body>
</html>