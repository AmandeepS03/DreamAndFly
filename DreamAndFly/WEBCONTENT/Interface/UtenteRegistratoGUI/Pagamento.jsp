<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<script src="<%=request.getContextPath()%>/scripts/validate.js"></script>
<link href="<%=request.getContextPath() %>/styles/Pagamento.css" rel="stylesheet" />
<title>Pagamento</title>
</head>
<body>
<%@ include file="../Header.jsp"%>

	<div class="v8_43">
		<section class="cartaSection">      
		<section class="carta-section">
			<h2>Dati carta</h2>
			<form action="/DreamAndFly/PagamentoServlet" method="post"  id="formCarta">
				<div class="form-group">
					<label for="nome">Nome intestatario:</label> 
					<!-- da controllare TODO  -->
					<input class="inputField" type="text" id="cardName" name="cardName" required onChange="return validateNome()"  onInput="return validateNome()"> <span id="errorName" ></span>
					
				</div>
				<div class="form-group">
					<label for="carta">Numero carta:</label> 
					<input class="inputField" type="text" id="cardNumber" name="cardNumber" required onChange="return validateNumCarta()"  onInput="return validateNumCarta()"> <span id="cardNumberError" ></span>
					
				</div>
				
				
					<div class="form-group">
						<label for="scadenza">Scadenza:</label> 
						<input class="js-iframe-input date-field input-field" id="expirationDate" name="expirationDate" type="tel" placeholder="MM/YY"   maxlength="5" onChange="return validateScadenzaCarta()" onInput="return validateScadenzaCarta()" > <span id="expiryError"></span>
					</div>
					<div class="form-group">
						<label for="cvc">CVC:</label> 
						<input id="cvc" class="inputField" type="text" maxlength="3" name="cvc" required onChange="return validateCVV()" onInput="return validateCVV()"> <span id="CVVError"></span>
						
					</div>
					
				
				
			<%-- <p  style="color:red "> <%=error %> </p> --%>
				
				

				<button type="submit" onclick="errorLogin()">Conferma pagamento</button>
			</form>
			
		</section>
		</section>
		</div>
</body>
<%@ include file="../Footer.jsp"%>

</html>