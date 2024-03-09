<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registra nuovo gestore</title>
<link href="<%=request.getContextPath() %>/styles/RegistraNuovoAccountGestore.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/validate.js"></script>
<script src="<%=request.getContextPath() %>/scripts/ajax.js"></script>
</head>
<body>

<%@include file="../../Header.jsp" %>


	
	
	<div class="v65_12">
			<p class="titolo">Registra nuovo account gestore</p>
		</div>
	
	<div class="v8_61">
		
		<form id="regForm" action="/DreamAndFly/RegistraNuovoAccountGestoreServlet" method="post" 
			onsubmit="event.preventDefault();checkSignup2(this)">
			
				<div class="form-group">
                	<label for="nome">*Nome:</label> <input type="text" id="nome"
					name="nome" required onChange="return validateNome()"> <span
					id="errorName"></span></div>
            	
            	<div class="form-group">
                	<label for="cognome">*Cognome:</label> <br>
                	<input type="text" id="cognome" name="cognome" required onChange="return validateCognome()"><span id="errorLastname"></span>
            	</div>
            	
            	<div class="form-group">
                <label for="cellulare">*Cellulare:</label> <br>
                <input type="text" id="cellulare" name="cellulare" required onChange="return validateCellulare()"><span id="errorCellulare"></span>
            	</div>
            
            <div class="form-group">
                <label for="email">*Email:</label> <br>
                <input type="email" id="email" name="email" required onBlur="return validateEmail()" onChange="return tryEmail2()"><span id="errorEmail"></span><span id="emailCheckDisponibility"></span>
            </div>
            <div class="form-group">
                <label for="password">*Password:</label> <br>
                <input type="password" id="password" name="password" required onChange="return validatePassword()" onInput="return validatePassword()"><span id="errorpswd"></span>
            </div>
            <div class="form-group">
                <label for="Ruolo">*Ruolo:</label> <br>
                <select id="dropdown-menu" name="ruolo">
				    <option value="" disabled selected>Scegli un'opzione</option>
				    <option value="1">Gestore capsule</option>
				    <option value="2">Gestore account</option>
				    <option value="3">Gestore prenotazioni</option>
				</select>
            </div>
            	
				
           	<a> <button type="submit" onclick="tryEmail2()">Registra gestore</button></a>
           <p>*I campi sono obbligatori</p>
        </form>
	</div>
	
<%@include file="../../Footer.jsp" %>

	
</body>
</html>