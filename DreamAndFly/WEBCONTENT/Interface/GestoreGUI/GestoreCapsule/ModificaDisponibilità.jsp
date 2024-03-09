<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, storage.*"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/styles/ModificaDisponibilità.css">
	<link rel="stylesheet"
	href="<%=request.getContextPath() %>/styles/Popup.css">
<title>Modifica Disponibilità</title>
</head>
<body>
	<%@ include file="../../Header.jsp"%>
	<%
	
	/* if(auth.getRuolo()==1){ */
	List<Capsula> capsule = (List<Capsula>) request.getAttribute("listaCapsule");
	request.setAttribute("page", 2);

	 if(capsule == null){
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzaCapsuleServlet");
	    dispatcher.forward(request, response);
	 }  
	List<FasciaOraria> fasceOrarie = (List<FasciaOraria>) request.getAttribute("listaFasceOrarie");

	if(fasceOrarie == null){
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/GetFasceOrarieServlet");
	    dispatcher.forward(request, response);
	 } 
	
%>

	<div class="v32_14">
		<p class="titolo">Modifica disponibilità</p>
	</div>

	<div class="v32_16">
		<form action="/DreamAndFly/ModificaDisponibilitaServlet" method="post">
			<!-- Form con label e input type su due righe -->
			<div class="containerLabel">
				<div>
					<label for="numero">Numero:</label><br> <select class="inputField" id="numero" name="numero" required>
				<%if (capsule != null) {
        		for(Capsula capsula: capsule) {%>
        		<option value="<%= capsula.getId() %>"><%= capsula.getId() %></option>
        	<%}} %>
				</select>
				</div>
					   <div>
        <label for="data" required>Data:</label> <br>
        <input type="text" id="data" name="data" required>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script>
        $(function() {
            var today = new Date(); // Ottiene la data odierna

            $("#data").datepicker({
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

					<label for="fascia_oraria" required>Fascia oraria:</label><br> <select
						class="inputField" id="fasciaOraria" name="fasciaOraria" required>
						<%if (fasceOrarie != null) {
        		for(FasciaOraria fascia: fasceOrarie) {%>
        		<option value="<%= fascia.getNumero() %>"><%= fascia.getorarioInizio()+"-"+fascia.getorarioFine() %></option>
        	<%}} %>

					</select>
				</div>
				<div>


					<label for="disponibile">Disponibile:</label><br> <input
						type="radio" id="disponibile_si" name="disponibile" value="si" required>
					<label for="disponibile_si">si</label> <input type="radio"
						id="disponibile_no" name="disponibile" value="no" required> <label
						for="disponibile_no">no</label>


				</div>
			</div>
<button type="submit" >Salva</button>
		</form>


	</div>
	 <script>
        <% if (request.getAttribute("showPopup") != null && (Boolean)request.getAttribute("showPopup")) { %>
            setTimeout(function() {
                var popupDiv = document.createElement('div');
                popupDiv.className = 'popup';
                popupDiv.innerHTML = "<p>Modifica avvenuta con successo!</p>";
                var closeButton = document.createElement('button');
                closeButton.textContent = 'OK';
                closeButton.onclick = function() {
                    popupDiv.style.display = 'none';
                };
                popupDiv.appendChild(closeButton);
                document.body.appendChild(popupDiv);
            }, 100); // Aggiungi un ritardo di 100 millisecondi prima di mostrare l'alert
        <% } %>
    </script>
</body>
<%@ include file="../../Footer.jsp"%>
</html>