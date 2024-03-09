<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/styles/AreaUtente.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/validate.js"></script>
<title>Reimposta Password</title>
</head>
<body>

	<%@ include file="../Header.jsp"%>
	<%
	request.getSession().setAttribute("pageModifica", "1");
	
%>

	<div class="v8_125">
		<form id="regForm" method="post" action="/DreamAndFly/ModificaDatiServlet" onsubmit="event.preventDefault();checkModificaPassword(this)" >
		
			<p>Password:</p>
			<input type="password" id="password" name="password"
				placeholder="Nuova password" onChange="return validatePassword()"
				onInput="return validatePassword()"> <br> <span
				id="errorpswd"></span> 
				<p>Conferma password:</p>
			 <input type="password" id="ConfermaPassword"
				name="ConfermaPassword" placeholder="Conferma password"
				onChange="return pswMatching()" onInput="return pswMatching()">
			<br> <span id="matchError"></span>

			 <br> <br>

			<button type="submit">Invia</button>
	
	</form>

	</div>

</body>
<%@ include file="../Footer.jsp"%>
</html>

