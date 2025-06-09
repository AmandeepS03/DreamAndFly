<%
    
    int ruolo = (int) session.getAttribute("ruolo");
    if (ruolo == 1) {
%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/AreaRiservataGestoreCapsule.css">
<title>Area Riservata</title>
</head>
<body>
	<%@ include file="../../Header.jsp" %>
	
		<div class="v8_212">
		<div class="v8_213">
			<h1>AREA RISERVATA</h1>
			<br>
			<a href="<%=request.getContextPath() %>/Interface/GestoreGUI/GestoreCapsule/RegistraCapsula.jsp"><h2>Registrazione capsula</h2> </a>
			<a href="<%=request.getContextPath() %>/Interface/GestoreGUI/GestoreCapsule/VisualizzaCapsule.jsp"><h2>Visualizza capsule</h2></a>
			<a href="<%=request.getContextPath() %>/Interface/GestoreGUI/GestoreCapsule/ModificaDisponibilità.jsp"><h2>Modifica disponibilità</h2></a>
			<a href="<%=request.getContextPath() %>/Interface/GestoreGUI/GestoreCapsule/ModificaPrezzo.jsp"><h2> Modifica prezzo orario</h2>  </a>
			<a href="<%=request.getContextPath() %>/Interface/GestoreGUI/GestoreCapsule/ProlungaDisponibilità.jsp"><h2>Prolunga disponibilità</h2></a>
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