<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/Login.css">
<title>Inserisci e-mail</title>
</head>
<body>
<% 
    String error = (String)request.getAttribute("error");
    if(error == null)
      error="";
  
  %>
<%@ include file="../Header.jsp" %>

<div class="v8_43">
		   <section class="loginSection">      
		<section class="login-section">
			<h2>Inserisci e-mail</h2>
			<form method="post" action="/DreamAndFly/InserisciEmailServlet" id="formLogin">
				<div class="form-group">
					<label for="email">Email:</label> <input type="email" id="email"
						name="email" required>
				</div>
				<p  style="color:red "> <%=error %> </p> 				

				<button type="submit" onclick="errorLogin()">Invia</button>
			</form>
				</section>
		</section>
		</div>


</body>
<%@ include file="../Footer.jsp" %>
</html>