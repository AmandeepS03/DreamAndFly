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
	 <div class="image">
		<div class="v35_177">
	
			<form action="" method="post">
	
	
				<div class="containerLabel">
    <div>
        <label for="dal">Dal:</label> <br>
        <input type="text" id="dal" name="dal">
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script>
        $(function() {
            var today = new Date(); // Ottiene la data odierna

            $("#dal").datepicker({
                dateFormat: 'yy-mm-dd',
                minDate: today, // Imposta la data minima come odierna
                onSelect: function(selectedDate) {
                    // Imposta la data minima per il datepicker #al come la data selezionata nel datepicker #dal
                    var minDate = $(this).datepicker('getDate'); // Ottiene la data selezionata in #dal
                    minDate.setDate(minDate.getDate()); // Incrementa la data di un giorno
                    $("#al").datepicker("option", "minDate", minDate); // Imposta la data minima per #al
                }
            });

            $("#al").datepicker({
                dateFormat: 'yy-mm-dd',
                minDate: today // Imposta la data minima come odierna
            });
        });
    </script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
            $(function() {
                $("#dal").datepicker({
                    dateFormat: 'yy-mm-dd',
                    onSelect: function(selectedDate) {
                        // Imposta la data minima per il datepicker #al come la data selezionata nel datepicker #dal
                        $("#al").datepicker("option", "minDate", selectedDate);
                    }
                });
            });
        </script>
    </div>

    <div>
        <label for="dalOrario"></label> <br>
        <select id="dalOrario" title="dalOrario">
            <option value="" disabled selected>Scegli un'opzione</option>
            <option value="opzione1">00:00</option>
            <option value="opzione2">01:00</option>
            <option value="opzione3">orari disponibili</option>
        </select>
    </div>

    <div>
        <label for="al">Al:</label><br> 
        <input type="text" id="al" name="al">
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        
        <script>
            $(function() {
                $("#al").datepicker({
                    dateFormat: 'yy-mm-dd'
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
				<button type="submit" id="cercaButton" value="Cerca">Cerca</button>
			</form>
		</div>
	</div>
	
	
</body>
<%@include file="../Footer.jsp" %>
</html>