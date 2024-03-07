<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link href="<%=request.getContextPath() %>/styles/LeMiePrenotazioni.css" rel="stylesheet" />
<title>Le mie prenotazioni</title>


</head>

<%
	    
	//TODO
		Collection<?> prenotazioni = (Collection<?>) request.getAttribute("prenotazioni");
		if (prenotazioni == null){
		  RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/");
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
		          PrenotazioneWrapper prenotazione = (PrenotazioneWrapper) it.next();%>
				<tr>
					<td><%=prenotazione.getCodiceDiAccesso() %></td>
					<td><%=prenotazione.getCapsulaId() %></td>
					<td><%=prenotazione.getDataInizio()%> <%=prenotazione.getOrarioInizio() %></td>
                	<td><%=prenotazione.getDataFine()%> <%=prenotazione.getOrarioFine() %></td>
                	<td><%=prenotazione.getDataEffettuazione() %></td>
					<td><%=prenotazione.getTipologia()%></td> <!-- //TODO -->
                	<td><%=request.getAttribute("prezzoTotale") %></td> <!-- //TODO -->
                	<td><!-- icona del cestino e chiamata alla servlet che chiama il metodo doDeletePrenotzione nel db --></td>
				</tr>
			
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
</body>
</html>