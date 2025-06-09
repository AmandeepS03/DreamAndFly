<%
    
    int ruolo = (int) session.getAttribute("ruolo");
    if (ruolo == 1) {
%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, storage.*" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/ModificaPrezzo.css">
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/styles/Popup.css">
<title>Modifica Prezzo</title>
</head>
<body>
<%@ include file="../../Header.jsp" %>
<%
	
	/* if(auth.getRuolo()==1){ */
	List<Capsula> capsule = (List<Capsula>) request.getAttribute("listaCapsule");
	request.setAttribute("page", 1);

	if(capsule == null){
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzaCapsuleServlet");
	    dispatcher.forward(request, response);
	 } 
	
%>
	
	
		
		<div class="v34_91">
			<p class="titolo">Modifica prezzo</p>
		</div>
		<div class="v34_93">
			<form action="/DreamAndFly/ModificaPrezzoServlet" method="post">
			<!-- Form con label e input type su due righe -->
			<div class="container">
				<div>
					<label for="numero">Numero:</label><br> <select class="inputField" id="numero" name="numero" required>
				<%if (capsule != null) {
        		for(Capsula capsula: capsule) {%>
        		<option value="<%= capsula.getId() %>"><%= capsula.getId() %></option>
        	<%}} %>
				</select>
				</div>
				<div>
					<label for="prezzo">Prezzo/h:</label><br> <input type="number"
						id="prezzo" name="prezzo" required>
				</div>
				<div class="buttonContainer">
				<button type="submit" >Salva</button>
				</div>
			</div>

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
<%@ include file="../../Footer.jsp" %>	
</html>
<%
    } else{
%> 
<h2>Accesso negato.</h2>
<%
    }
%> 