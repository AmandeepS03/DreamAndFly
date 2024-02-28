<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/RegistraCapsula.css">
<title>Registra Capsula</title>
</head>
<body>
<%@ include file="../../Header.jsp" %>
	
			<div class="v32_3"><p class="titolo">Registra capsula</p></div>
		<div class="v8_249">
			<form action="" method="post">
			<!-- Form con label e input type su due righe -->
			<div class="containerLabel">
				<div>
					<label for="numero">Numero:</label><br> <input type="text"
						id="numero" name="numero">
				</div>
				<div>
					<label for="prezzoOrario">Prezzo/h:</label><br> 
					<input type="text"
						id="prezzoOrario" name="prezzoOrario">
				</div>
				<div class="containerData">
	   <div>
        <label for="dal">Dal:</label> <br>
        <input type="text" id="dal" name="dal">
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script>
        $(function() {
            var today = new Date(); // Ottiene la data odierna

            $("#dal").datepicker({
                dateFormat: 'dd/mm/yy',
                minDate: today, // Imposta la data minima come odierna
                onSelect: function(selectedDate) {
                    // Imposta la data minima per il datepicker #al come la data selezionata nel datepicker #dal
                    var minDate = $(this).datepicker('getDate'); // Ottiene la data selezionata in #dal
                    minDate.setDate(minDate.getDate()); // Incrementa la data di un giorno
                    $("#al").datepicker("option", "minDate", minDate); // Imposta la data minima per #al
                }
            });

            $("#al").datepicker({
                dateFormat: 'dd/mm/yy',
                minDate: today // Imposta la data minima come odierna
            });
        });
    </script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
            $(function() {
                $("#dal").datepicker({
                    dateFormat: 'dd/mm/yy',
                    onSelect: function(selectedDate) {
                        // Imposta la data minima per il datepicker #al come la data selezionata nel datepicker #dal
                        $("#al").datepicker("option", "minDate", selectedDate);
                    }
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
				
				<!-- <div>

					<label for="fascia_oraria">Fascia oraria:</label><br> <select
						class="inputField" id="fasciaOraria" name="fasciaOraria" required>
						<option value="all">00:00-01:00</option>

						<option value="example">example</option>

					</select>
				</div> -->
				<div>


					<label for="tipologia">Tipologia</label> <br>
					<select
						id="tipologia" title="tipologia">
						<option value="" disabled selected>Scegli un'opzione</option>
						<option value="standard">Standard</option>
						<option value="superior">Superior</option>
						<option value="deluxe">Deluxe</option>
				
					</select>


				</div>
			</div>
			<button type="submit" >Inserisci</button>
			</form>
			<!-- <span class="v8_250">Numero:</span>
			<div class="v8_251"></div>
			<span class="v8_252">Prezzo/h:</span>
			<div class="v8_253"></div>
			<div class="v8_254">
				<span class="v8_255">Dal:</span><span class="v8_256">Al:</span>
				<div class="v8_257">
					<div class="v8_258">
						<span class="v8_259">17/03/2024 18:00</span>
					</div>
				</div>
				<div class="v8_260">
					<span class="v8_261">17/06/2024 21:00</span>
				</div>
				<div class="v8_262">
					<span class="v8_263">CERCA</span>
				</div>
			</div>
			<span class="v26_2">Tipologia:</span>
			<div class="v26_3"></div>
			<div class="v26_4">
				<span class="v26_5">Inserisci</span>
			</div> -->
		</div>
	
		
	
</body>
<%@ include file="../../Footer.jsp" %>	
</html>