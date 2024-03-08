<%@page import="storage.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%
	AccountUser auth = (AccountUser) request.getSession().getAttribute("auth") ;
	if(auth!=null){
		request.setAttribute("auth",auth);
	}
%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

<link rel="stylesheet" href="<%=request.getContextPath() %>/styles/Header.css">

<title></title>
</head>
<body>
<header class="header">
 	<div class="logo">
 	      <a href="<%=request.getContextPath() %>/Interface/Homepage.jsp"><img src="<%=request.getContextPath() %>/images/logo.png" alt="Logo"> </a>
 	
	</div>
	 <div class="icons">  
	 <% if(auth == null){ %>
     <a href="<%=request.getContextPath()%>/Interface/AutenticazioneGUI/Login.jsp" title="Login">Login</a>
     <a href="<%=request.getContextPath()%>/Interface/AutenticazioneGUI/Registrati.jsp" title="Registrati">Registrati</a>
     
      <%} else { %>
          <% if(auth.getRuolo() == 0){%>
          <a href="<%=request.getContextPath()%>/Interface/UtenteRegistratoGUI/LeMiePrenotazioni.jsp" title="Le mie prenotazioni" >Le mie prenotazioni</a>
         <%}%>  
         <a href="<%=request.getContextPath()%>/Interface/UtenteRegistratoGUI/AreaUtente.jsp" title="Area utente"><i class="fas fa-user"></i></a> 
         <% if(auth.getRuolo() == 1){%>
          <a href="<%=request.getContextPath()%>/Interface/GestoreGUI/GestoreCapsule/AreaRiservataGestoreCapsule.jsp" title="Area riservata"><i class="fas fa-cog"></i></a>
         <%} else if(auth.getRuolo() == 2){%>
          <a href="<%=request.getContextPath()%>/Interface/GestoreGUI/GestoreAccount/AreaRiservataGestoreAccount.jsp" title="Area riservata"><i class="fas fa-cog"></i></a>
         <%} else if(auth.getRuolo() == 3){%>
          <a href="<%=request.getContextPath()%>/Interface/GestoreGUI/GestorePrenotazioni/AreaRiservataGestorePrenotazioni.jsp" title="Area riservata"><i class="fas fa-cog"></i></a>
         <%} %>
         <a href="/DreamAndFly/LogoutServlet" title="Logout"><i class="fas fa-sign-out-alt"></i></a>
      <%} %>
      </div>
 
	 </header>
</body>
</html>