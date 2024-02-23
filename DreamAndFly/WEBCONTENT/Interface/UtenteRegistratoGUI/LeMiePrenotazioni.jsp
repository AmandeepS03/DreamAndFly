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
			
				<tr>
					<td></td>
					<td></td>
					<td></td>
                	<td></td>
                	<td></td>
					<td></td>
                	<td></td> 
                	<td></td>
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