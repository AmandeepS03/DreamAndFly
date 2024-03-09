<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Conferma prenotazione</title>
<link href="<%=request.getContextPath() %>/styles/ConfermaPrenotazione.css" rel="stylesheet" />
</head>
<body>
<%@include file="../Header.jsp" %>
	<div class="confermaPrenotazione">
		<h1>Conferma prenotazione</h1>
		<p>Codice prenotazione: <%=request.getAttribute("codiceDiAccesso") %> </p>
		<p>Dal <%=request.getSession().getAttribute("dataInizio")%> alle <%=request.getSession().getAttribute("orarioInizio")%> </p>
		<p>Al <%=request.getSession().getAttribute("dataFine")%> alle <%=request.getSession().getAttribute("orarioFine")%></p>
		
		<h3>Prenotazione avvenuta con successo!</h3>
		
	</div>
<%@include file="../Footer.jsp" %>
</body>
</html>