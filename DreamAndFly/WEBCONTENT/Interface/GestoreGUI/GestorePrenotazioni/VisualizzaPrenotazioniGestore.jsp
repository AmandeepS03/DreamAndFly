<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,java.io.IOException"%>
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
	<%
		/* if(auth.getRuolo()==1){ */
			List<Prenotazione> listaUtentiPrenotati = (List<Prenotazione>) request.getAttribute("listaUtentiPrenotati");
			if(listaUtentiPrenotati == null){
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzaPrenotazioniGestoreServlet");
			    dispatcher.forward(request, response);
			 }  
			
			
		List<Capsula> capsule = (List<Capsula>) request.getAttribute("listaCapsule");
		request.setAttribute("page", 5);
	
		 if(capsule == null){
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzaCapsuleServlet");
		    dispatcher.forward(request, response);
		 }  
		
		 
		 
	
%>
	

	<div class="v35_167">
		<p class="titolo">Visualizza prenotazioni</p>
	</div>

	<div class="v35_177">

		<form action="/DreamAndFly/VisualizzaPrenotazioniGestoreFiltri" method="post">


			<div class="containerLabel">
				<div>
					<label for="NumeroCapsula">Numero caspula:</label> <br> 
					<select
						id="NumeroCapsula" name="numeroCapsula" title="numeroCapsula">
						<option value="" disabled selected>Seleziona capsula</option>
						<%if (capsule != null) {
        		          for(Capsula capsula: capsule) {%>
        		           <option value="<%= capsula.getId() %>"><%= capsula.getId() %></option>
        	  		   <%}} %>
						
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
								dateFormat : 'yy-mm-dd'
							});
						});
					</script>





				</div>
<!--  TODO impedisci che la data fine possa essere prima della data inizio-->
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
								dateFormat : 'yy-mm-dd'
							});
						});
					</script>



				</div>

				<div>
					<label for="Account">Account:</label> <br> 
					<select id="Account" title="Account" name="account">
					    <option value="" disabled selected>Tutti gli account</option>
					    <% if (listaUtentiPrenotati != null) {
					        for (Prenotazione prenotazione : listaUtentiPrenotati) { %>
					            <option value="<%=prenotazione.getUserAccountEmail() %>"><%= prenotazione.getUserAccountEmail() %></option>
					    <% }
					    } %>
					    
					</select>


				</div>



				
			</div>

			

			<br>
			<button type="submit" class="btn btn-primary" value="Cerca">Cerca</button>
		</form>
	</div>
	
	<%List<Prenotazione> prenotazioneByAccount = (List<Prenotazione>) request.getAttribute("listaPrenotazioneByAccount");
	List<Prenotazione> prenotazioneByCapsula = (List<Prenotazione>) request.getAttribute("listaPrenotazioneByCapsula");
	List<Prenotazione> prenotazioneByDate = (List<Prenotazione>) request.getAttribute("listaPrenotazioneByDate");
	List<Prenotazione> prenotazioneByDateFine = (List <Prenotazione>)request.getAttribute("listaPrenotazioneByDateFine");
	List<Prenotazione> prenotazioneByDateInizioAndFine = (List<Prenotazione>) request.getAttribute("listaPrenotazioneByDateInizioAndFine");
	List<Prenotazione> prenotazioneByDateInizioAndAccount = (List<Prenotazione>) request.getAttribute("prenotazioneByDateInizioAndAccount");
	List<Prenotazione> prenotazioneByNumeroCapsulaAndAccount = (List<Prenotazione>) request.getAttribute("prenotazioneByNumeroCapsulaAndAccount");
	List<Prenotazione> prenotazioneByDateInizioAndNumeroCapsula = (List<Prenotazione>) request.getAttribute("prenotazioneByDateInizioAndNumeroCapsula");
	List<Prenotazione> prenotazioneByNumeroCapsulaAndDataFine = (List<Prenotazione>) request.getAttribute("prenotazioneByNumeroCapsulaAndDataFine");
	List<Prenotazione> prenotazioneByNumeroCapsulaAndDataInizioAndDataFine = (List<Prenotazione>) request.getAttribute("prenotazioneByNumeroCapsulaAndDataInizioAndDataFine");
	List<Prenotazione> prenotazioneByNumeroCapsulaAndDataInizioAndAccount = (List<Prenotazione>)request.getAttribute("prenotazioneByNumeroCapsulaAndDataInizioAndAccount");
	List<Prenotazione> prenotazioneByNumeroCapsulaAndDataFineAndAccount = (List<Prenotazione>) request.getAttribute("prenotazioneByNumeroCapsulaAndDataFineAndAccount");	
	List<Prenotazione> prenotazioneByAll = (List<Prenotazione>) request.getAttribute("prenotazioneByAll");
	List<Prenotazione> prenotazioneByDataInizioAndDataFineAndAccount = (List<Prenotazione>) request.getAttribute("prenotazioneByDataInizioAndDataFineAndAccount");
	List<Prenotazione> prenotazioneByDataFineAndAccount = (List<Prenotazione>) request.getAttribute("prenotazioneByDataFineAndAccount");
	List<Prenotazione> tutteLePrenotazioni = (List<Prenotazione>) request.getAttribute("tutteLePrenotazioni");
	%>
	
	<table id="accountTable" border="1">
				

				<tr>
					<th>Codice prenotazione</th>
					<th>Numero capsula</th>
					<th>Account</th>
					<th>Data e ora inizio</th>
					<th>Data e ora fine</th>
					<th>Prezzo totale</th>
					<th>Data effettuazione</th>
					<th>Validità</th>
					<th>Rimborso</th>
					

				</tr>
				<tr>
				
				
				<%if (prenotazioneByAccount != null) {
					for(Prenotazione prenotazione: prenotazioneByAccount){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				
				<%if (prenotazioneByCapsula != null) {
					for(Prenotazione prenotazione: prenotazioneByCapsula){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByDate != null) {
					
					for(Prenotazione prenotazione: prenotazioneByDate){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByDateFine != null) {
					
				
					for(Prenotazione prenotazione: prenotazioneByDateFine){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByDateInizioAndFine != null) {
					
					for(Prenotazione prenotazione: prenotazioneByDateInizioAndFine){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByDateInizioAndAccount != null) {
					
					for(Prenotazione prenotazione: prenotazioneByDateInizioAndAccount){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByNumeroCapsulaAndAccount != null) {
					
					for(Prenotazione prenotazione: prenotazioneByNumeroCapsulaAndAccount){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByDateInizioAndNumeroCapsula != null) {
					for(Prenotazione prenotazione: prenotazioneByDateInizioAndNumeroCapsula){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByNumeroCapsulaAndDataFine != null) {
					for(Prenotazione prenotazione: prenotazioneByNumeroCapsulaAndDataFine){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByNumeroCapsulaAndDataInizioAndDataFine != null) {
					for(Prenotazione prenotazione: prenotazioneByNumeroCapsulaAndDataInizioAndDataFine){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByNumeroCapsulaAndDataInizioAndAccount != null) {
					for(Prenotazione prenotazione: prenotazioneByNumeroCapsulaAndDataInizioAndAccount){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByNumeroCapsulaAndDataFineAndAccount != null) {
					for(Prenotazione prenotazione: prenotazioneByNumeroCapsulaAndDataFineAndAccount){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByAll != null) {
					for(Prenotazione prenotazione: prenotazioneByAll){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByDataInizioAndDataFineAndAccount != null) {
					for(Prenotazione prenotazione: prenotazioneByDataInizioAndDataFineAndAccount){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByDataFineAndAccount != null) {
					for(Prenotazione prenotazione: prenotazioneByDataFineAndAccount){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				<%if (tutteLePrenotazioni != null) {
					for(Prenotazione prenotazione: tutteLePrenotazioni){ %>
				<tr>
		
					<td><%= prenotazione.getCodiceDiAccesso()%></td>
					<td><%= prenotazione.getCapsulaId() %></td>
					<td><%= prenotazione.getUserAccountEmail() %></td>
					<td><%= prenotazione.getDataInizio()  %> <%=prenotazione.getOrarioInizio() %></td>
					<td><%= prenotazione.getDataFine()  %> <%=prenotazione.getOrarioFine() %></td>
					<td><%=prenotazione.getPrezzoTotale() %></td>
					<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.isValidita() %></td>
					<td><%=prenotazione.getRimborso() %></td>
		 			<%} }%>
				</tr>
				
				
			</table>



</body>
<%@ include file="../../Footer.jsp"%>
</html>