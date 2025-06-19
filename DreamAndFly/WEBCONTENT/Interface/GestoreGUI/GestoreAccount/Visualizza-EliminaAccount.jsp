<%
    
    int ruolo = (int) session.getAttribute("ruolo");
    if (ruolo == 2) {
%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, storage.*" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/Visualizza-EliminaAccount.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<title>Visualizza/Elimina account</title>
</head>
<body>
	<%@ include file="../../Header.jsp"%>

	<%
		List<AccountUser> listaUtenti = (List<AccountUser>) request.getAttribute("listaUtenti");
		List<AccountUser> listaUtentiPerSelect = (List<AccountUser>) request.getAttribute("listaUtentiPerSelect");

		if (listaUtenti == null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzaAccountServlet");
			dispatcher.forward(request, response);
		}

		String type = (String) request.getAttribute("type");
		if (type != null && type.equals("elemento")) {
			AccountUser user = (AccountUser) request.getAttribute("user");
			listaUtenti.clear();
			listaUtenti.add(user);
		}

		String utenteParam = request.getParameter("utente");
	%>

	<div class="v35_167">
		<p class="titolo">Visualizza/Elimina account</p>
	</div>

	<div class="v35_177">
		<form action="/DreamAndFly/RicercaAccountServlet" method="post">
			<select class="inputField" id="utente" name="utente" required>
				<option value="all">Tutti gli utenti</option>
				<% if (listaUtentiPerSelect != null) {
					for (AccountUser user : listaUtentiPerSelect) { %>
						<option value="<%= user.getEmail() %>"><%= user.getEmail() %></option>
				<% } } %>
			</select>
			<button type="submit" class="btn btn-primary" value="Cerca">Cerca</button>
		</form>
	</div>

	<%-- Mostra la tabella solo se è stato selezionato un utente o se è stato passato un tipo specifico --%>
	<% if ((utenteParam != null && !utenteParam.isEmpty()) || (type != null && type.equals("elemento"))) { %>
		<table id="accountTable" border="1">
			<tr>
				<th>E-mail</th>
				<th>Nome</th>
				<th>Cognome</th>
				<th>Cellulare</th>
				<th>Elimina</th>
			</tr>
			<% if (listaUtenti != null) {
				for (AccountUser user : listaUtenti) { %>
					<tr>
						<td><%= user.getEmail() %></td>
						<td><%= user.getName() %></td>
						<td><%= user.getSurname() %></td>
						<td><%= user.getNumber() %></td>
						<td><a href="/DreamAndFly/EliminaAccountServlet?email=<%= user.getEmail() %>">
							<i class="fas fa-trash-alt trash-icon"></i>
						</a></td>
					</tr>
			<% }} %>
		</table>
	<% } %>

</body>
<%@ include file="../../Footer.jsp"%>

<%
    } else{
%> 
<h2>Accesso negato.</h2>
<%
    }
%> 