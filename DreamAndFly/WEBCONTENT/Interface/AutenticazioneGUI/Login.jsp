<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />


<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/Login.css">
<title>Login</title>
</head>
<body>
		<%@ include file="../Header.jsp" %>
	
		<div class="v8_43">
		   <section class="loginSection">      
		<section class="login-section">
			<h2>LOGIN</h2>
			<form method="post" action="/PackAndTravel/LoginServlet" id="formLogin">
				<div class="form-group">
					<label for="email">Email:</label> <input type="email" id="email"
						name="email" required>
				</div>
				<div class="form-group">
					<label for="password">Password:</label> <input type="password"
						id="password" name="password" required>
				</div>
				
			<%-- <p  style="color:red "> <%=error %> </p> --%>
				
				

				<button type="submit" onclick="errorLogin()">Login</button>
			</form>
			<br> Non hai un account? <a href="<%=request.getContextPath() %>/Interface/AutenticazioneGUI/Registrati.jsp">Registrati!</a>
			<br> <br><a href="">Password dimenticata?</a>
		</section>
		</section>
		</div>
	
</body>
<%@ include file="../Footer.jsp" %>
</html>