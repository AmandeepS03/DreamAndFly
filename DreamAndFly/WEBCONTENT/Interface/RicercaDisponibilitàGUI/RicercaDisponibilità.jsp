<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link href="<%=request.getContextPath() %>/styles/RicercaDisponibilità.css" rel="stylesheet" />
<title>Ricerca disponibilità</title>
</head>
<body>
	
	 <%@include file="../Header.jsp" %>
	<div class="v35_177">

		<form action="" method="post">


			<div class="containerLabel">
				<div>
					<label for="dal">Dal:</label><br> 
					<input
						type="text" id="dal" name="dal">

					<link rel="stylesheet"
						href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
					<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
					<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
					<script>
						// Inizializza il datepicker con il formato desiderato
						$(function() {
							$("#dal").datepicker({
								dateFormat : 'dd/mm/yy'
							});
						});
					</script>
				</div>
			
			
				<div>
					<label for="alOrario"></label> <br>
					<select
						id="dalOrario" title="dalOrario">
						<option value="" disabled selected>Scegli un'opzione</option>
						<option value="opzione1">00:00</option>
						<option value="opzione2">01:00</option>
						<option value="opzione3">orari disponibili</option>
						<!-- /*inserisci orari disponibili dinamicamente*/ -->
					</select>
				</div>
				
				<div>
					<label for="al">Al:</label><br> 
					<input
						type="text" id="al" name="al">

					<link rel="stylesheet"
						href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
					<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
					<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
					<script>
						// Inizializza il datepicker con il formato desiderato
						$(function() {
							$("#al").datepicker({
								dateFormat : 'dd/mm/yy'
							});
						});
					</script>
				</div>
			
			
				<div>
					<label for="alOrario"></label> <br>
					<select
						id="alOrario" title="alOrario">
						<option value="" disabled selected>Scegli un'opzione</option>
						<option value="opzione1">00:00</option>
						<option value="opzione2">01:00</option>
						<option value="opzione3">orari e giorni disponibili( dopo l orario di arrivo)</option>
						<!-- /*inserisci orari disponibili dinamicamente*/ -->
					</select>
				</div>
				
				</div>

			

			

			<br>
			<button type="submit" class="btn btn-primary" value="Cerca">Cerca</button>
		</form>
	</div>
	
	
	
</body>
<%@include file="../Footer.jsp" %>
</html>