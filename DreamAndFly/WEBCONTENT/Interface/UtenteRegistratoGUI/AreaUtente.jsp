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
<title>Area Utente</title>
</head>
<body>

	<%@ include file="../Header.jsp"%>

	<div class="v8_125">
		<div class="user-info">
			<h2>I miei dati</h2>
			<p>
				<strong>Nome:</strong> 
				<span id="nome">
					<%= auth.getName() %>
				</span>
			</p>
			<p>
				<strong>Cognome:</strong> 
				<span id="cognome">
				<%= auth.getSurname() %> 
				</span>
			</p>
			<p>
				<strong>E-mail:</strong> 
				<span id="email">
					<%= auth.getEmail() %>
				</span>
			</p>

			<p>
				<strong>Cellulare:</strong> 
				<span id="cellulare">
					<%= auth.getNumber() %> 
				</span>
			</p>
		</div>
		<form id="regForm" method="post" action="/DreamAndFly/ModificaDatiServlet" onsubmit="event.preventDefault();checkModifica(this)" >
		<fieldset>
			<legend>Modifica Dati</legend>
			<p>Modifica password:</p>
			<input type="password" id="password" name="password"
				placeholder="Nuova password" onChange="return validatePassword()"
				onInput="return validatePassword()"> <br> <span
				id="errorpswd"></span> 
				<p>Conferma password:</p>
			 <input type="password" id="ConfermaPassword"
				name="ConfermaPassword" placeholder="Conferma password"
				onChange="return pswMatching()" onInput="return pswMatching()">
			<br> <span id="matchError"></span>

			<p>Cellulare:</p>
			<input type="text" id="cellulare" name="cellulare"
				onInput="return validateCellulare()"
				onChange="return validateCellulare()"> <br> <span
				id="errorCellulare"></span> <br> <br>

			<button type="submit">Invia</button>
		</fieldset>
	</form>

	</div>

</body>
<%@ include file="../Footer.jsp"%>
</html>

