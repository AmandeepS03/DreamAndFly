<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" import="java.util.*, storage.*"%>
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
<%
/* if(auth.getRuolo()==1){ */
	List<Capsula> capsule = (List<Capsula>) request.getAttribute("listaCapsule");
	request.setAttribute("page", 3);

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
		
		<div class="v65_12">
			<p class="titolo">Prolunga disponibilità</p>
		</div>
		<div class="v65_14">
			<form action="" method="post">
			<!-- Form con label e input type su due righe -->
			<div class="container">
				<div>
					<label for="numero">Numero:</label>
					<br> 
					<select class="inputField" id="numero" name="numero" required>
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
				
			</div>
<button type="submit" >Salva</button>
		</form>

		</div>
	
</body>
<%@ include file="../../Footer.jsp" %>	
</html>