<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/Registrati.css">
<title>Registrati</title>
</head>
<body>

	<div class="v8_53">
		<div class="v8_54">

			<%@ include file="../Footer.jsp" %>	
		</div>
		<%@ include file="../Header.jsp" %>
		<div class="v8_61">
		<h1>REGISTRATI</h1>
        <form id="regForm" action="/PackAndTravel/RegistratiServlet" method="post" onsubmit="event.preventDefault();checkSignup(this)">
            <div class="form-group">
                <label for="nome">*Nome:</label>
                <input type="text" id="nome" name="nome" required onChange="return validateNome()"> <span id="errorName" ></span>
            </div>
             <div class="form-group">
                <label for="cognome">*Cognome:</label>
                <input type="text" id="cognome" name="cognome" required onChange="return validateCognome()"><span id="errorLastname"></span>
            </div>
            <div>
            	<div class="form-group">
                <label for="cellulare">*Cellulare:</label>
                <input type="text" id="cellulare" name="cellulare" required onChange="return validateCellulare()"><span id="errorCellulare"></span>
            </div>
            <div class="form-group">
                <label for="email">*Email:</label>
                <input type="email" id="email" name="email" required onBlur="return validateEmail()" onChange="return tryEmail()"><span id="errorEmail"></span><span id="emailCheckDisponibility"></span>
            </div>
            <div class="form-group">
                <label for="password">*Password:</label>
                <input type="password" id="password" name="password" required onChange="return validatePassword()" onInput="return validatePassword()"><span id="errorpswd"></span>
            </div>
            <div class="form-group">
                <label for="ConfermaPassword">*Conferma password:</label>
                <input type="password" id="ConfermaPassword" name="ConfermaPassword" required onChange="return pswMatching()" onInput="return pswMatching()"><span id="matchError"></span>
            </div>
            
            
            </div>
           <a> <button type="submit" onclick="tryEmail()">Registrati</button></a>
           <p>*I campi sono obbligatori</p>
        </form>
			<!-- <span class="v8_62">REGISTRATI</span><span class="v8_63">Nome</span>
			<div class="v8_64"></div>
			<span class="v8_65">Cognome</span>
			<div class="v8_66"></div>
			<div class="v8_69">
				<span class="v8_70">Login</span>
			</div>
			<span class="v8_74">E-mail</span>
			<div class="v8_75"></div>
			<span class="v8_76">Password</span>
			<div class="v8_77"></div>
			<span class="v8_78">Conferma password</span>
			<div class="v8_79"></div> -->
		</div>
	</div>
</body>
</html>