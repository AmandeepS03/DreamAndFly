<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, storage.*" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/VisualizzaCapsule.css">
<title>Visualizza Capsule</title>
</head>
<body>
<%@ include file="../../Header.jsp" %>
<%
	
	/* if(auth.getRuolo()==1){ */
	List<Capsula> capsule = (List<Capsula>) request.getAttribute("listaCapsule");

	if(capsule == null){
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzaCapsuleServlet");
	    dispatcher.forward(request, response);
	 } 
	
%>
	
		
		<div class="v34_152">
			<p class="titolo">Visualizza capsule</p>
		</div>
		<table id="capsuleTable" border="1">
		<caption></caption>

		<tr>
			<th>Numero</th>
			<th>Tipologia</th>
			<th>Prezzo/h</th>
		

		</tr>
		<tr>
		<%if (capsule != null) {
		for(Capsula capsula: capsule){ %>

			<td><%= capsula.getId() %></td>
			<td><%= capsula.getTipologia() %></td>
			<td><%= capsula.getPrezzo_orario() %> &euro;</td>
			

		</tr>
		 <%}}%>
	</table>

	
</body>
<%@ include file="../../Footer.jsp" %>	
</html>