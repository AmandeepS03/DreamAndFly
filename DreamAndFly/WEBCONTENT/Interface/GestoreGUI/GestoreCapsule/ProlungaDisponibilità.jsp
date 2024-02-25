<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/ProlungaDisponibilità.css">
<title>Prolunga Disponibilità</title>
</head>
<body>
<%@ include file="../../Header.jsp" %>
	
		
		
		<div class="v65_12">
			<p class="titolo">Prolunga disponibilità</p>
		</div>
		<div class="v65_14">
			<form action="" method="post">
			<!-- Form con label e input type su due righe -->
			<div class="container">
				<div>
					<label for="numero">Numero:</label><br> <input type="text"
						id="numero" name="numero">
				</div>
				<div>
					<label for="data">Data fine:</label><br> <input type="text"
						id="data" name="data">
				</div>
				<div>

					<label for="orarioFine">Orario fine:</label><br> <select
						class="inputField" id="orarioFine" name="orarioFine" required>
						<option value="all">00:00-01:00</option>

						<option value="example">example</option>

					</select>
				</div>
				
			</div>
<button type="submit" >Salva</button>
		</form>

		</div>
	
</body>
<%@ include file="../../Footer.jsp" %>	
</html>