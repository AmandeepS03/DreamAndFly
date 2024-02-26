<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link href="<%=request.getContextPath() %>/styles/RegistraAccountGestore.css" rel="stylesheet" />
<title>Registra account gestore</title>
</head>
<body>
	<div class="v31_60">
		<div class="v31_61">
			<%@include file="../../Footer.jsp" %>
		</div>
		<div class="v31_70">
			<span class="v31_69">Registra nuovo account gestore</span>
		</div>
		<div class="v31_71">
			
			
			
			<div class="v31_101">
			<form id="regForm" action="/PackAndTravel/RegistratiServlet" method="post" 
			onsubmit="event.preventDefault();checkSignup(this)">
			
				<div class="form-group">
                	<label for="nome">*Nome:</label> <br>
                	<input type="text" id="nome" name="nome" required onChange="return validateNome()"><span id="errorLastname"></span>
            	</div>
            	
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
                <input type="email" id="email" name="email" required onBlur="return validateEmail()" onChange="return tryEmail()"><span id="errorEmail"></span><span id="emailCheckDisponibility"></span>
            </div>
            <div class="form-group">
                <label for="password">*Password:</label> <br>
                <input type="password" id="password" name="password" required onChange="return validatePassword()" onInput="return validatePassword()"><span id="errorpswd"></span>
            </div>
            <div class="form-group">
                <label for="Ruolo">*Ruolo:</label> <br>
                <select id="dropdown-menu">
				    <option value="" disabled selected>Scegli un'opzione</option>
				    <option value="opzione1">Gestore capsule</option>
				    <option value="opzione2">Gestore account</option>
				    <option value="opzione3">Gestore prenotazioni</option>
				</select>
            </div>
            	
				
           	<a> <button type="submit" onclick="registraAccountGestore()">Registrati</button></a>
           <p>*I campi sono obbligatori</p>
        </form>
            </div>
            
            
		</div>
		</div>
		<div class="v31_63">
			<%@include file="../../Header.jsp" %>
		</div>
	
</body>
</html>