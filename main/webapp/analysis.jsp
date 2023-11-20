<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.scanner.*, java.util.List, com.dao.*,com.model.*" %>

<% 
String hash = request.getParameter("hash");
if (hash == null || hash.length()<64){
	response.sendRedirect("index.jsp");
	return;
}
%>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Malware Scanner Home</title>
<link rel="stylesheet" href="css/analysis.css">
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
<script src="js/loading.js"></script>
</head>
<body>
	<div class="result">
	<div class="container">
		<h1>Analysis</h1>
		<hr>
		<% 
		List<Analysis> analysisList = ScanUtils.parser(ScanDAO.fetchJSON(hash));
		int count = ScanUtils.counter(analysisList);
		%>
		<div class="progress-bar">
		
		</div>
		<%
		if(count>0){
			out.print("<h3><div style='color: red;'>"+count+" Detected this file as malicious"+"</div><h3>");	
		}
		else{
			out.print("<h3><div style='color: green;'>"+count+" Detected this file as malicious"+"</div><h3>")
			;	
		}
		 %>
		<h2>File Details</h1>
		<hr>
	<%
	
		Analysis an = analysisList.get(0);
		if(an.getSignatureInfo().getType().contains("EXE")){
			out.println("&nbsp;<strong>File Version:</strong> "+an.getSignatureInfo().getFileVersion()+"<br>");
			out.println("&nbsp;<strong>Magic:</strong> "+an.getSignatureInfo().getMagic()+"<br>");
			out.println("&nbsp;<strong>Description:</strong> "+an.getSignatureInfo().getDescription()+"<br>");
	    	out.println("&nbsp;<strong>Original Name:</strong> "+an.getSignatureInfo().getOriginalName()+"<br>");
		}
		else{
			out.println("&nbsp;<strong>File Type:</strong> "+an.getSignatureInfo().getType());
		}	
	%>
	<h2>Detection</h1>
	<hr>
	<div class="heading">
	<div class="eng-name">Engine Name</div>
	<div class="analysish">Analysis</div>
	</div>
		<%
	    for(Analysis ann: analysisList){
	        out.println("<div style='display: flex; justify-content: space-between;'>");
	        out.println("<div>" + ann.getEngineName() + "</div>");
	        if(ann.getCategory().equals("malicious")) {
	            out.println("<div style='color: red; font-weight: bolder;'>" + ann.getCategory() + "</div>");
	        } 
	        else if (ann.getCategory().contains("unsupported")){
	            out.println("<div style='color: #DAA520;'>" + ann.getCategory() + "</div>");
	        }
	        else {
	            out.println("<div style='color: green;''>" + ann.getCategory() + "</div>");
	        }
	        out.println("</div>");
	    }
		%>
</div>
</div>
</body>
</html>