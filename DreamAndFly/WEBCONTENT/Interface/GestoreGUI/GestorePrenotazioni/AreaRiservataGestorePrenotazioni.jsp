<%
    
    int ruolo = (int) session.getAttribute("ruolo");
    if (ruolo == 3) {
%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/AreaRiservataGestorePrenotazioni.css">

<title>Area Riservata</title>
</head>
<body>
<%@ include file="../../Header.jsp" %>
	
		<div class="v8_229">
			<div class="v8_230">
			<h1>AREA RISERVATA</h1>
			<br>
			<a href="<%=request.getContextPath() %>/Interface/GestoreGUI/GestorePrenotazioni/VisualizzaPrenotazioniGestore.jsp"><h2>Visualizza prenotazioni</h2> </a>
			</div>
		</div>
		
		
</body>
<%@ include file="../../Footer.jsp" %>	
</html>
<%
    } else{
%> 
<h2>Accesso negato.</h2>
<%
    }
%> 