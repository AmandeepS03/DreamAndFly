<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/styles/ModificaDisponibilità.css">
<title>Modifica Disponibilità</title>
</head>
<body>
	<%@ include file="../../Header.jsp"%>

	<div class="v32_14">
		<p class="titolo">Modifica disponibilità</p>
	</div>

	<div class="v32_16">
		<form action="" method="post">
			<!-- Form con label e input type su due righe -->
			<div class="containerLabel">
				<div>
					<label for="numero">Numero:</label><br> <input type="text"
						id="numero" name="numero">
				</div>
					   <div>
        <label for="data">Data:</label> <br>
        <input type="text" id="data" name="data">
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script>
        $(function() {
            var today = new Date(); // Ottiene la data odierna

            $("#data").datepicker({
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

					<label for="fascia_oraria">Fascia oraria:</label><br> <select
						class="inputField" id="fasciaOraria" name="fasciaOraria" required>
						<option value="all">00:00-01:00</option>

						<option value="example">example</option>

					</select>
				</div>
				<div>


					<label for="disponibile">Disponibile:</label><br> <input
						type="radio" id="disponibile_si" name="disponibile" value="si">
					<label for="disponibile_si">si</label> <input type="radio"
						id="disponibile_no" name="disponibile" value="no"> <label
						for="disponibile_no">no</label>


				</div>
			</div>
<button type="submit" >Salva</button>
		</form>


	</div>
</body>
<%@ include file="../../Footer.jsp"%>
</html>