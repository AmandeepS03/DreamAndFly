<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/VisualizzaPrenotazioniGestore.css">
<title>Visualizza prenotazioni</title>
</head>
<body>



	<%@ include file="../../Header.jsp"%>

	<div class="v35_167">
		<p class="titolo">Visualizza prenotazioni</p>
	</div>

	<div class="v35_177">

		<form action="" method="post">


			<div class="containerLabel">
				<div>
					<label for="NumeroCapsula">Numero caspula:</label> <br> <select
						id="NumeroCapsula" title="numeroCapsula">
						<option value="" disabled selected>Scegli un'opzione</option>
						<option value="opzione1">1</option>
						<option value="opzione2">2</option>
						<option value="opzione3">example</option>
						<!-- /*inserisci numero capsule dinamicamente*/ -->
					</select>
				</div>
				<div>
					<label for="dataInizio">Data inizio:</label><br> 
					<input
						type="text" id="dataInizio" name="dataInizio">

					<link rel="stylesheet"
						href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
					<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
					<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
					<script>
						// Inizializza il datepicker con il formato desiderato
						$(function() {
							$("#dataInizio").datepicker({
								dateFormat : 'dd/mm/yy'
							});
						});
					</script>





				</div>
				<div>
					<label for="dataFine">Data fine:</label><br> <input type="text"
						id="dataFine" name="dataFine">
						
					<link rel="stylesheet"
						href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
					<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
					<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
					<script>
						// Inizializza il datepicker con il formato desiderato
						$(function() {
							$("#dataFine").datepicker({
								dateFormat : 'dd/mm/yy'
							});
						});
					</script>



				</div>

				<div>
					<label for="Account">Account:</label> <br> <select
						id="Account" title="Account">
						<option value="" disabled selected>Scegli un'opzione</option>
						<option value="opzione1">Account1</option>
						<option value="opzione2">Account2</option>
						<option value="opzione3">example</option>
						<!-- /*inserisci account dinamicamente*/ -->
					</select>

				</div>



				<div>


					<label for="Filtri_prenotabilita">Filtri prenotabilita:</label><br>

					<input type="radio" id="prenotabili" name="Filtri_prenotabilita"
						value="prenotabili"> <label for="prenotabili">Prenotabili</label>

					<input type="radio" id="prenotate" name="Filtri_prenotabilita"
						value="prenotate"> <label for="prenotate">Prenotate</label>


					<input type="radio" id="tutte" name="Filtri_prenotabilita"
						value="tutte"> <label for="tutte">Tutte</label>


				</div>
			</div>

			

			<br>
			<button type="submit" class="btn btn-primary" value="Cerca">Cerca</button>
		</form>
	</div>
	
	<table id="accountTable" border="1">
				

				<tr>
					<th>Codice prenotazione</th>
					<th>Numero capsula</th>
					<th>Account</th>
					<th>Data e ora inizio</th>
					<th>Data e ora fine</th>
					<th>Prenotabile</th>

				</tr>
				<tr>

					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>

				</tr>
			</table>



</body>
<%@ include file="../../Footer.jsp"%>
</html>