<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
 	<div class="v68_26">
 	 <div class="v68_27">
	 <div class="icons">  
        
         
         	
         	<a href="" title="I miei dati"><i class="fas fa-user"></i></a>
         
         
         <a href=""><i class="fas fa-shopping-cart" title="Carrello"></i></a> <!-- Icona per il carrello -->
        
        
         <a href="#" onclick="toggleDropdown()" title="Area riservata"><i class="fas fa-cog"></i></a>
         <div id="dropdownMenu" class="dropdown-content">
         </div>
         
     
         	<a href="" title="Logout"><i class="fas fa-sign-out-alt"></i></a> 
         
         
         	<a href="" title="Login"><i class="fas fa-user"></i></a>
         	
     
         	
         
         
      </div></div>
	  <a href="<%=request.getContextPath() %>/Interface/Homepage.jsp"> <div class="v68_30"></div></a>
	
	
   
     
   
		<!-- <div class="v68_27"></div>
		<span class="v68_28">Logout </span><span class="v68_29">Area
			utente</span>
		<div class="v68_30"></div>
		<span class="v68_31">Area riservata</span> -->
</div> 
	 
</body>
</html>