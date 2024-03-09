<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, utils.*"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link href="<%=request.getContextPath() %>/styles/LeMiePrenotazioni.css" rel="stylesheet" />
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/styles/Popup.css">
<title>Le mie prenotazioni</title>


</head>

<%
	    Collection<?> prenotazioni = (Collection<?>) request.getAttribute("prenotazioni");

		if (prenotazioni == null){
		  RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/LeMiePrenotazioniServlet");
		  dispatcher.forward(request, response);  
		  return;
		}
      	
	%>
<body>
	<div class="v76_3">
	
	
	<!-- tabella -->
		
		<div class="table-container">
			<table >
			<caption> Le mie prenotazioni </caption>  
			<thead>
				<tr>
					<th scope="col">Codice</th>
					<th scope="col">Numero capsula</th>
					<th scope="col">Data e ora inizio</th>
					<th scope="col">Data e ora fine</th>
					<th scope="col">Data effettuazione</th>
					<th scope="col">Tipologia</th>
					<th scope="col">Prezzo totale</th>
					<th scope="col">Elimina</th>
				</tr>
			</thead>
			<tbody>
				<%if (prenotazioni != null && prenotazioni.size() != 0) {
		        Iterator<?> it = prenotazioni.iterator();
		        while (it.hasNext()) {
		          PrenotazioneWrapper prenotazioneWrapper = (PrenotazioneWrapper) it.next();
		          Prenotazione prenotazione = prenotazioneWrapper.getPrenotazione();
		          boolean validità = prenotazione.isValidita();
		          String tipologia = prenotazioneWrapper.getTipologiaCapsula();
		          String codice = HelperClass.aggiungiZeri(prenotazione.getCodiceDiAccesso());
		          if(validità){
		          %>
				<tr>
					<td><%=codice %></td>
					<td><%=prenotazione.getCapsulaId() %></td>
					<td><%=prenotazione.getDataInizio()%> <%=prenotazione.getOrarioInizio() %></td>
                	<td><%=prenotazione.getDataFine()%> <%=prenotazione.getOrarioFine() %></td>
                	<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=tipologia%></td>
                	<td><%=prenotazione.getPrezzoTotale() %>&euro;</td> 
                	<td><a onclick="showPopup(<%=prenotazione.getCodiceDiAccesso() %>)"><i class="fas fa-trash-alt trash-icon" onclick="showPopup()"></i></a></td>
				</tr>
				<%}} }%>
			</tbody>
		</table>
		</div>
		
		
		
		<div class="v76_5">
			<%@include file="../Footer.jsp" %>
		</div>
		<div class="v76_7">
			<%@include file="../Header.jsp" %>
		</div>
	</div>
	
	<script>
    function showPopup(codice) {
    	  console.log("codice:"+codice);

        setTimeout(function() {
            var popupDiv = document.createElement('div');
            popupDiv.className = 'popup';
            popupDiv.innerHTML = "<p>Se decidi di annullare la tua prenotazione con un preavviso superiore a 3 giorni dalla data di arrivo prevista, ti verrà rimborsato il 100% dell'importo pagato. Nel caso in cui tu voglia annullare la prenotazione con meno di 3 giorni di preavviso, riceverai un rimborso pari al 50% dell'importo pagato</p>";
            
            var proceedButton = document.createElement('button');
            proceedButton.textContent = 'Procedi';
            proceedButton.onclick = function() {
                window.location.href = '<%= request.getContextPath() %>/Interface/UtenteRegistratoGUI/Pagamento.jsp?codicePrenotazione=' + codice;

            };

            var cancelButton = document.createElement('button');
            cancelButton.textContent = 'Annulla';
            cancelButton.onclick = function() {
                // Azione da eseguire quando si clicca sul pulsante "Annulla"
                popupDiv.style.display = 'none';
                // Aggiungi qui la logica per l'azione da eseguire quando si fa clic sul pulsante "Annulla"
            };
            proceedButton.style.marginRight = '10px';
            popupDiv.appendChild(proceedButton);
            popupDiv.appendChild(cancelButton);
            document.body.appendChild(popupDiv);
        }, 100); // Aggiungi un ritardo di 100 millisecondi prima di mostrare l'alert
    }
</script>

</body>
</html>