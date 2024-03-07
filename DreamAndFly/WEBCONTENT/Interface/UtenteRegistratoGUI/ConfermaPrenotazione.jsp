<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link href="<%=request.getContextPath() %>/styles/ConfermaPrenotazione.css" rel="stylesheet" />
<title>Conferma prenotazione</title>
</head>
<body>
	<div class="v9_221">
		<div class="v9_222">
			
			<%@include file="../Header.jsp" %>
		</div>
		<div class="v9_228">
			<%@include file="../Footer.jsp" %>
		</div>
		<div class="v9_404">
			
			<div class="v9_413">
				<span class="v9_406">Prenotazione avvenuta con successo!</span>
			</div>
			<div class="v9_409">
				<span class="v9_408">Conferma prenotazione!</span>
			</div>
			<span class="v9_412">Codice prenotazione: <%=request.getAttribute("codiceDiAccesso") %> </span>
		</div>
	</div>
</body>
</html>