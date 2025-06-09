<%
    
    int ruolo = (int) session.getAttribute("ruolo");
    if (ruolo == 1) {
%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" import="java.util.*, storage.*"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/RegistraCapsula.css">
	<script src="<%=request.getContextPath() %>/scripts/checkCapsulaNumber.js"></script>

<title>Registra Capsula</title>
</head>
<body>
<%@ include file="../../Header.jsp" %>
<%
/* if(auth.getRuolo()==1){ */
	request.setAttribute("page", 4);

	List<FasciaOraria> fasceOrarie = (List<FasciaOraria>) request.getAttribute("listaFasceOrarie");

	if(fasceOrarie == null){
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/GetFasceOrarieServlet");
	    dispatcher.forward(request, response);
	 } 
	
%>	
	
			<div class="v32_3"><p class="titolo">Registra capsula</p></div>
		<div class="v8_249">
			<form action="/DreamAndFly/RegistraCapsulaServlet" method="post">
			<!-- Form con label e input type su due righe -->
			<div class="containerLabel">
				<div>
					<label for="numero" >Numero:</label> <br>
					<input type="number" min=0
						id="numero" name="numero" required onChange="return tryNumber()" onInput="return tryNumber()">
					<br> 
					<span id="errorNumber"></span><span
						id="numberCheckDisponibility"></span>
				</div>
				<div>
					<label for="prezzoOrario" >Prezzo/h:</label><br> 
					<input type="number" min=0
						id="prezzoOrario" name="prezzoOrario" required>
				</div>
				<div class="containerData">
	   <div>
        <label for="dal">Dal:</label> <br>
        <input type="text" id="dal" name="dal" required>
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
					<label for="orarioInizio" ></label> <br>
					<select
						class="inputField" id="fasciaOraria" name="orarioInizio" required>
						<%if (fasceOrarie != null) {
        		for(FasciaOraria fascia: fasceOrarie) {%>
        		<option value="<%= fascia.getNumero() %>"><%= fascia.getorarioInizio()+"-"+fascia.getorarioFine() %></option>
        	<%}} %>

					</select>				
					</div>
				
				<div>
					<label for="al">Al:</label><br> 
					<input
						type="text" id="al" name="al" required>

					<link rel="stylesheet"
						href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
					<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
					<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
					<script>
						// Inizializza il datepicker con il formato desiderato
						$(function() {
							$("#al").datepicker({
								dateFormat : 'yy-mm-dd'
							});
						});
					</script>
				</div>
			
			
				<div>
					<label for="orarioFine"></label> <br>
					<select
						class="inputField" id="fasciaOraria" name="orarioFine" required>
						<%if (fasceOrarie != null) {
        		for(FasciaOraria fascia: fasceOrarie) {%>
        		<option value="<%= fascia.getNumero() %>"><%= fascia.getorarioInizio()+"-"+fascia.getorarioFine() %></option>
        	<%}} %>

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


					<label for="tipologia" >Tipologia</label> <br>
					<select
						id="tipologia" title="tipologia" name="tipologia">
						<option value="Standard">Standard</option>
						<option value="Superior">Superior</option>
						<option value="Deluxe">Deluxe</option>
				
					</select>


				</div>
			</div>
			<button type="submit" onclick="tryNumber()" >Inserisci</button>
			</form>
		
		</div>
	
		
	
</body>
<%@ include file="../../Footer.jsp" %>	
</html>
<%
    } else{
%> 
<h2>Accesso negato.</h2>
<%
    }
%> 