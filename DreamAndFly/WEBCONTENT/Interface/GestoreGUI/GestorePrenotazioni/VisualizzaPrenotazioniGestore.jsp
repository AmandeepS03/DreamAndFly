<%
    
    int ruolo = (int) session.getAttribute("ruolo");
    if (ruolo == 3) {
%> <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>
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



			<!-- 	 <div>


					<label for="Filtri_prenotabilita">Fasce orarie:</label><br>

					<input type="radio" id="prenotabili" name="Filtri_prenotabilita"
						value="prenotabili" required> <label for="prenotabili">Prenotabili</label>

					<input type="radio" id="prenotate" name="Filtri_prenotabilita"
						value="prenotate" required > <label for="prenotate">Prenotate</label>


					<input type="radio" id="tutte" name="Filtri_prenotabilita"
						value="tutte" required> <label for="tutte">Tutte</label>


				</div> -->
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
	List<Prenotazione> prenotazioneByDateFineAndAccount = (List<Prenotazione>) request.getAttribute("prenotazioneByDateFineAndAccount");
	List<Prenotazione> prenotazioneByAll = (List<Prenotazione>) request.getAttribute("prenotazioneByAll");
	List<Prenotazione> prenotazioneByAccountAndIdAndDataInizio = (List<Prenotazione>) request.getAttribute("prenotazioneByAccountAndIdAndDataInizio");
	List<Prenotazione> prenotazioneByAccountAndIdAndDataFine = (List<Prenotazione>) request.getAttribute("prenotazioneByAccountAndIdAndDataFine");
	List<Prenotazione> prenotazioneByNumeroCapsulaAndAccount = (List<Prenotazione>) request.getAttribute("prenotazioneByNumeroCapsulaAndAccount");
	
	List<Prenotabile> prenotabileByCapsula = (List<Prenotabile>) request.getAttribute("listaPrenotabiliByCapsula");
	List<Prenotabile> prenotabileByDataInizio = (List<Prenotabile>) request.getAttribute("listaPrenotabileByDateInizio");
	List<Prenotabile> prenotabileByDataFine = (List<Prenotabile>) request.getAttribute("listaPrenotabileByDateFine");
	List<Prenotabile> prenotabileByDataInizioAndFine = (List<Prenotabile>) request.getAttribute("listaPrenotabileByDateInizioAndFine");


	List<Prenotazione> listaPrenotazioni = (List<Prenotazione>) request.getAttribute("listaPrenotazioni");
	List<Prenotabile> listaPrenotabili = (List<Prenotabile>) request.getAttribute("listaPrenotabili");
	
	List<Prenotazione> listaPrenotazioneByCapsulaAndDataInizio = (List<Prenotazione>) request.getAttribute("listaPrenotazioneByCapsulaAndDataInizio");
	List<Prenotabile> listaPrenotabileByCapsulaAndDataInizio = (List<Prenotabile>) request.getAttribute("listaPrenotabiliByCapsulaAndDataInizio");

	List<Prenotazione> listaPrenotazioneByCapsulaAndDataFine = (List<Prenotazione>) request.getAttribute("listaPrenotazioneByCapsulaAndDataFine");
	List<Prenotabile> listaPrenotabiliByCapsulaAndDataFine = (List<Prenotabile>) request.getAttribute("listaPrenotabiliByCapsulaAndDataFine");
	
	List<Prenotazione> listaPrenotazioneByCapsulaAndDataInizioAndDataFine = (List<Prenotazione>) request.getAttribute("listaPrenotazioneByCapsulaAndDataInizioAndDataFine");
	List<Prenotabile> listaPrenotabiliByCapsulaAndDataInizioAndDataFine = (List<Prenotabile>) request.getAttribute("listaPrenotabiliByCapsulaAndDataInizioAndDataFine");
	
	List<Prenotazione> listaPrenotazioneByAccountAndDataInizioAndDataFine = (List<Prenotazione>) request.getAttribute("listaPrenotazioneByAccountAndDataInizioAndDataFine");
	
	ArrayList<String> orariInizioList = new ArrayList<>();
    orariInizioList.add("00:00");
    orariInizioList.add("01:00");
    orariInizioList.add("02:00");
    orariInizioList.add("03:00");
    orariInizioList.add("04:00");
    orariInizioList.add("05:00");
    orariInizioList.add("06:00");
    orariInizioList.add("07:00");
    orariInizioList.add("08:00");
    orariInizioList.add("09:00");
    orariInizioList.add("10:00");
    orariInizioList.add("11:00");
    orariInizioList.add("12:00");
    orariInizioList.add("13:00");
    orariInizioList.add("14:00");
    orariInizioList.add("15:00");
    orariInizioList.add("16:00");
    orariInizioList.add("17:00");
    orariInizioList.add("18:00");
    orariInizioList.add("19:00");
    orariInizioList.add("20:00");
    orariInizioList.add("21:00");
    orariInizioList.add("22:00");
    orariInizioList.add("23:00");
	%>
	
	<table id="accountTable" border="1">
				<caption>Prenotazioni</caption>

				<tr>
					<th>Codice prenotazione</th>
					<th>Numero capsula</th>
					<th>Account</th>
					<th>Data e ora inizio</th>
					<th>Data e ora fine</th>
					<th>Prezzo totale</th>
					<th>Data effettuazione</th>
					<th>ValiditÓ</th>
					<th>Rimborso</th>
					

				</tr>
				<tr>
				
				<%if (listaPrenotazioni != null) {
					for(Prenotazione prenotazione: listaPrenotazioni){ %>
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
				
				<%if (listaPrenotazioneByAccountAndDataInizioAndDataFine != null) {
					for(Prenotazione prenotazione: listaPrenotazioneByAccountAndDataInizioAndDataFine){ %>
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
				
				<%if (listaPrenotazioneByCapsulaAndDataInizio != null) {
					for(Prenotazione prenotazione: listaPrenotazioneByCapsulaAndDataInizio){ %>
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
				
				<%if (listaPrenotazioneByCapsulaAndDataInizioAndDataFine != null) {
					for(Prenotazione prenotazione: listaPrenotazioneByCapsulaAndDataInizioAndDataFine){ %>
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
				
				<%if (prenotazioneByDateFineAndAccount != null) {
					
					for(Prenotazione prenotazione: prenotazioneByDateFineAndAccount){ %>
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
				
				<%if (prenotazioneByAccountAndIdAndDataInizio != null) {
					
					for(Prenotazione prenotazione: prenotazioneByAccountAndIdAndDataInizio){ %>
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
				
				<%if (prenotazioneByAccountAndIdAndDataFine != null) {
					
					for(Prenotazione prenotazione: prenotazioneByAccountAndIdAndDataInizio){ %>
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
				
				<%if (listaPrenotazioneByCapsulaAndDataFine != null) {
					for(Prenotazione prenotazione: listaPrenotazioneByCapsulaAndDataFine){ %>
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
			
			
			<table id="accountTable2" border="1">
				<caption>DisponibilitÓ</caption>

				<tr>
					<th>Numero capsula</th>
					<th>Data e ora inizio</th>
					<th>Data e ora fine</th>
					
					

				</tr>
				
				<%if (listaPrenotabili != null) {
					for(Prenotabile prenotabile: listaPrenotabili){ %>
				<tr>
		
					
					<td><%= prenotabile.getCapsulaId() %></td>
					<td><%= prenotabile.getDataPrenotabile() %>  <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())-1) %></td>
					<td><%= prenotabile.getDataPrenotabile() %> <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())) %></td>
		 			<%} }%>
				</tr>
				
				
				<%if (listaPrenotabiliByCapsulaAndDataInizioAndDataFine != null) {
					for(Prenotabile prenotabile: listaPrenotabiliByCapsulaAndDataInizioAndDataFine){ %>
				<tr>
		
					
					<td><%= prenotabile.getCapsulaId() %></td>
					<td><%= prenotabile.getDataPrenotabile() %>  <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())-1) %></td>
					<td><%= prenotabile.getDataPrenotabile() %> <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())) %></td>
		 			<%} }%>
				</tr>
				
				
				<%if (listaPrenotabileByCapsulaAndDataInizio != null) {
					
					for(Prenotabile prenotabile: listaPrenotabileByCapsulaAndDataInizio){ %>
				<tr>
					
					
					<td><%= prenotabile.getCapsulaId() %></td>
					<td><%= prenotabile.getDataPrenotabile() %>  <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())-1) %></td>
					<td><%= prenotabile.getDataPrenotabile() %> <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())) %></td>
		 			<%} }%>
				</tr>

				<%if (listaPrenotabiliByCapsulaAndDataFine != null) {
					
					for(Prenotabile prenotabile: listaPrenotabiliByCapsulaAndDataFine){ %>
				<tr>
					
					
					<td><%= prenotabile.getCapsulaId() %></td>
					<td><%= prenotabile.getDataPrenotabile() %>  <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())-1) %></td>
					<td><%= prenotabile.getDataPrenotabile() %> <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())) %></td>
		 			<%} }%>
				</tr>
			
				
				
				<%if (prenotabileByCapsula != null) {
					for(Prenotabile prenotabile: prenotabileByCapsula){ %>
				<tr>
		
					
					<td><%= prenotabile.getCapsulaId() %></td>
					<td><%= prenotabile.getDataPrenotabile() %>  <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())-1) %></td>
					<td><%= prenotabile.getDataPrenotabile() %> <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())) %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotabileByDataInizio != null) {
					for(Prenotabile prenotabile: prenotabileByDataInizio){ %>
				<tr>
		
					<td><%= prenotabile.getCapsulaId() %></td>
					<td><%= prenotabile.getDataPrenotabile() %>  <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())-1)%></td>
					<td><%= prenotabile.getDataPrenotabile() %> <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())) %></td>
		 			<%} }%>
				</tr>
				
				<%if (prenotazioneByDateFine != null) {
					for(Prenotabile prenotabile: prenotabileByDataFine){ %>
					<tr>
			
						<td><%= prenotabile.getCapsulaId() %></td>
						<td><%= prenotabile.getDataPrenotabile() %>  <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())-1) %></td>
						<td><%= prenotabile.getDataPrenotabile() %> <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())) %></td>
			 			<%} }%>
				</tr>
				
				<%if (prenotabileByDataInizioAndFine != null) {
					
					for(Prenotabile prenotabile: prenotabileByDataInizioAndFine){ %>
				<tr>
		
					<td><%= prenotabile.getCapsulaId() %></td>
						<td><%= prenotabile.getDataPrenotabile() %>  <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero())-1) %></td>
						<td><%= prenotabile.getDataPrenotabile() %> <%=orariInizioList.get((prenotabile.getFasciaOrariaNumero()))%></td>
			 			<%} }%>
				</tr>
				
				
				
				
			</table>



</body>
<%@ include file="../../Footer.jsp"%>
</html>
<%
    } else{
%> 
<h2>Accesso negato.</h2>
<%
    }
%> 