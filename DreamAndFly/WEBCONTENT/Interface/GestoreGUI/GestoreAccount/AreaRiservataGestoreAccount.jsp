<%
    
    int ruolo = (int) session.getAttribute("ruolo");
    if (ruolo == 2) {
%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/styles/AreaRiservataGestoreAccount.css">

<title>Area Riservata</title>
</head>
<body>
	<%@ include file="../../Header.jsp"%>



	<div class="v8_225">
		<div class="v8_226">
			<h1>AREA RISERVATA</h1>
			<br> <a
				href="<%=request.getContextPath() %>/Interface/GestoreGUI/GestoreAccount/RegistraNuovoAccountGestore.jsp"><h2>Registra
					nuovo account gestore</h2> </a> <a
				href="<%=request.getContextPath() %>/Interface/GestoreGUI/GestoreAccount/Visualizza-EliminaAccount.jsp"><h2>Visualizza/elimina
					account</h2></a>
		</div>
	</div>

</body>
<%@ include file="../../Footer.jsp"%>
</html>
<%
    } else{
%> 
<h2>Accesso negato.</h2>
<%
    }
%> 