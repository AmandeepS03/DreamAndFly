<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link href="<%=request.getContextPath() %>/styles/LeMiePrenotazioni.css" rel="stylesheet" />
<title>Le mie prenotazioni</title>

<style>
		caption{
			border: 2px solid #dddddd;
			font-weight: bold;
			background-color: #A1C9E6;
            font-size: 1.2em;
		}
        table {
            border-collapse: collapse;
            width: 80%;
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
            padding: 10px;
        }
    </style>

</head>
<body>
	<div class="v76_3">
	
	
	<!-- tabella -->
		<div class="v76_4">
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