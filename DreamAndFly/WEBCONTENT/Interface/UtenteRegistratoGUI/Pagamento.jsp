<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<link href="<%=request.getContextPath() %>/styles/Pagamento.css" rel="stylesheet" />
<title>Pagamento</title>
</head>
<body>
<%@ include file="../Header.jsp"%>

	<div class="v8_43">
		<section class="cartaSection">      
		<section class="carta-section">
			<h2>Dati carta</h2>
			<form method="post"  id="formCarta">
				<div class="form-group">
					<label for="nome">Nome intestatario:</label> <input type="nome" id="nome"
						name="nome" required>
				</div>
				<div class="form-group">
					<label for="carta">Numero carta:</label> <input type="carta"
						id="carta" name="carta" required>
				</div>
				
				<!-- <div class="containerLabel"> -->
					<div class="form-group">
						<label for="scadenza">Scadenza:</label> <input type="scadenza" id="scadenza"
						name="scadenza" required>
					</div>
					<div class="form-group">
						<label for="cvc">CVC:</label> <input type="cvc" id="cvc"
						name="cvc" required>
					</div>
					
				<!-- </div> -->
				
			<%-- <p  style="color:red "> <%=error %> </p> --%>
				
				

				<button type="submit" onclick="errorLogin()">Conferma pagamento</button>
			</form>
			
		</section>
		</section>
		</div>
</body>
<%@ include file="../Footer.jsp"%>

</html>