<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, storage.*" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/styles/Visualizza-EliminaAccount.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

<title>Visualizza/Elimina account</title>
</head>
<body>
	<%@ include file="../../Header.jsp"%>
	<%
	
	/* if(auth.getRuolo()==2){ */
	List<AccountUser> listaUtenti = (List<AccountUser>) request.getAttribute("listaUtenti");
	if(listaUtenti==null){
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzaAccountServlet");
	    dispatcher.forward(request, response);
	 } 
%>
	


	<div class="v35_167">
		<p class="titolo">Visualizza/Elimina account</p>
	</div>

	<div class="v35_177">

		<form action="" method="post">

			<select class="inputField" id="utente" name="utente" required>
				<option value="all">Tutti gli utenti</option>

				<option value="example">example</option>

			</select>

			<button type="submit" class="btn btn-primary" value="Cerca">Cerca</button>
		</form>
	</div>
	<table id="accountTable" border="1">
		<caption></caption>

		<tr>
			<th>E-mail</th>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Cellulare</th>
			<th>Elimina</th>

		</tr>
		<%for(AccountUser user: listaUtenti){ %>
		<tr>

			<td><%= user.getEmail()%></td>
			<td><%= user.getName() %></td>
			<td><%= user.getSurname() %></td>
			<td><%= user.getNumber() %></td>
			<td><a href=""> <i class="fas fa-trash-alt trash-icon"></i>
			</a></td>
 <%}%>
		</tr>
		
	</table>


</body>
<%@ include file="../../Footer.jsp"%>
<%-- <%}else{ %>
	<p>Accesso Negato</p>
<%} %> --%>
</html>