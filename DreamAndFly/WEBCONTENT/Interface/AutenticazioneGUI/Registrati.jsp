<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/styles/Registrati.css">
	<link rel="stylesheet"
	href="<%=request.getContextPath() %>/styles/Popup.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/validate.js"></script>
	<script src="<%=request.getContextPath() %>/scripts/ajax.js"></script>
 
<title>Registrati</title>
</head>
<body>
	<%@ include file="../Header.jsp"%>

	<div class="v8_61">
		<h1>REGISTRATI</h1>
		<form id="regForm" action="/DreamAndFly/RegistratiServlet"
			method="post" onsubmit="event.preventDefault();checkSignup(this)">
			<div class="form-group">
				<label for="nome">*Nome:</label> <input type="text" id="nome"
					name="nome" required onChange="return validateNome()"> <span
					id="errorName"></span>
			</div>
			<div class="form-group">
				<label for="cognome">*Cognome:</label> <input type="text"
					id="cognome" name="cognome" required
					onChange="return validateCognome()"><span
					id="errorLastname"></span>
			</div>
			<div>
				<div class="form-group">
					<label for="cellulare">*Cellulare:</label> <input type="text"
						id="cellulare" name="cellulare" required
						onChange="return validateCellulare()"><span
						id="errorCellulare"></span>
				</div>
				<div class="form-group">
					<label for="email">*Email:</label> <input type="email" id="email"
						name="email" required onBlur="return validateEmail()"
						onChange="return tryEmail()" onInput="return tryEmail()"><span id="errorEmail"></span><span
						id="emailCheckDisponibility"></span>
				</div>
				<div class="form-group">
					<label for="password">*Password:</label> <input type="password"
						id="password" name="password" required
						onChange="return validatePassword()"
						onInput="return validatePassword()"><span id="errorpswd"></span>
				</div>
				<div class="form-group">
					<label for="ConfermaPassword">*Conferma password:</label> <input
						type="password" id="ConfermaPassword" name="ConfermaPassword"
						required onChange="return pswMatching()"
						onInput="return pswMatching()"><span id="matchError"></span>
				</div>


			</div>
			<a>
				<button type="submit" onclick="tryEmail()">Registrati</button>
			</a>
			<p>*I campi sono obbligatori</p>
		</form>
	</div>

 <script>
        <% if (request.getAttribute("showPopup") != null && (Boolean)request.getAttribute("showPopup")) { %>
            setTimeout(function() {
                var popupDiv = document.createElement('div');
                popupDiv.className = 'popup';
                popupDiv.innerHTML = "<p>Il tuo account è stato registrato con successo!</p>";
                var okButton = document.createElement('button');
                okButton.textContent = 'OK';
                okButton.onclick = function() {
                    window.location.href = '<%=request.getContextPath() %>/Interface/AutenticazioneGUI/Login.jsp'; // Reindirizzamento alla pagina di login
                };
                popupDiv.appendChild(okButton);
                document.body.appendChild(popupDiv);
            }, 100); // Aggiungi un ritardo di 100 millisecondi prima di mostrare l'alert
        <% } %>
    </script>

</body>
<%@ include file="../Footer.jsp"%>

</html>