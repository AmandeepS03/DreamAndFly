<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link href="<%=request.getContextPath() %>/styles/CapsuleDisponibili.css" rel="stylesheet" />
<title>Capsule disponibili</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
</head>

<body>

	<%@include file="../Header.jsp" %>
	<div class="image">
		
	
	
		<div class="v35_177">
	
			<form action="" method="post">
	
	
				<div class="containerLabel">
					<div>
						<label for="dal">Dal:</label><br> 
						<input
							type="text" id="dal" name="dal">
	
						<link rel="stylesheet"
							href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
						<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
						<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
						<script>
							// Inizializza il datepicker con il formato desiderato
							$(function() {
								$("#dal").datepicker({
									dateFormat : 'dd/mm/yy'
								});
							});
						</script>
					</div>
				
				
					<div>
						<label for="alOrario"></label> <br>
						<select
							id="dalOrario" title="dalOrario">
							<option value="" disabled selected>Scegli un'opzione</option>
							<option value="opzione1">00:00</option>
							<option value="opzione2">01:00</option>
							<option value="opzione3">orari disponibili</option>
							<!-- /*inserisci orari disponibili dinamicamente*/ -->
						</select>
					</div>
					
					<div>
						<label for="al">Al:</label><br> 
						<input
							type="text" id="al" name="al">
	
						<link rel="stylesheet"
							href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
						<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
						<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
						<script>
							// Inizializza il datepicker con il formato desiderato
							$(function() {
								$("#al").datepicker({
									dateFormat : 'dd/mm/yy'
								});
							});
						</script>
					</div>
				
				
					<div>
						<label for="alOrario"></label> <br>
						<select
							id="alOrario" title="alOrario">
							<option value="" disabled selected>Scegli un'opzione</option>
							<option value="opzione1">00:00</option>
							<option value="opzione2">01:00</option>
							<option value="opzione3">orari e giorni disponibili( dopo l orario di arrivo)</option>
							<!-- /*inserisci orari disponibili dinamicamente*/ -->
						</select>
					</div>
					
					</div>
	
				
	
				
	
				<br>
				<button type="submit" class="btn btn-primary" value="Cerca">Cerca</button>
			</form>
		</div>
		
		<!-- capsule disponibili e prenotabili -->
		<%-- <main>
		   <section class="novita-section">
		   
			
		      
		      
		    <div class="album py-5">
		  <div class="container">
		    <div class="row">
		      <% if (prodotti != null && prodotti.size() != 0) {
		        Iterator<?> it = prodotti.iterator();
		        while (it.hasNext()) {
		          Prodotto prodotto = (Prodotto) it.next();
		      %>
		      <div class="col-md-4">
		        <div class="card mb-4 box-shadow">
		          <div class="card-body d-flex flex-column align-items-center">
		            <a href="<%=request.getContextPath()%>/common/DettaglioProdotto.jsp?code=<%=prodotto.getCodice()%>" class="card-text nome"><%=prodotto.getNome()%>
		            <img class="card-img-top img" src="<%=request.getContextPath()%>/getPicture?codice=<%=prodotto.getCodice()%>" alt="immagine prodotto"></a>
		            <div class="d-flex justify-content-center flex-column ">
		 				 <p class="text-center prezzo"><%=prodotto.getPrezzo()%>&euro;</p>
		 				<!-- Trasforma il link in un bottone utilizzando un button tag -->
		<button type="button" class="btn btn-info btn-block aggiungiAlCarrello" data-id="<%=prodotto.getCodice()%>">Aggiungi al carrello</button>
		
		
		
					</div>
		          </div>
		        </div>
		      </div>
		      <%}}%>
		    </div>
		  </div>
		</div>
		
		   </section>
		</main> --%>
		
		<main>
    <section class="novita-section">
        <div class="album py-5">
            <div class="container">
                <div class="row">
                    <!-- Inizio del loop per la visualizzazione dei prodotti -->
                    <%--  <% if (prodotti != null && prodotti.size() != 0) {
                        Iterator<?> it = prodotti.iterator();
                        while (it.hasNext()) {
                            Prodotto prodotto = (Prodotto) it.next();
                    %>  --%>
                    <div class="col-md-4">
                        <div class="card mb-4 box-shadow">
                            <div class="card-body d-flex flex-column align-items-center">
                            	<h4>CIAO</h4>
                            	<h4>CIAO2</h4>
                            	<h4>CIAO3</h4>
                            	<h4>CIAO</h4>
                                <!-- Link al dettaglio del prodotto -->
                                <%-- <!-- <a href="<%=request.getContextPath()%>/common/DettaglioProdotto.jsp?code=<%=prodotto.getCodice()%>" class="card-text nome"><%=prodotto.getNome()%> -->
                                <!-- Immagine del prodotto -->
                                <!-- <img class="card-img-top img" src="<%=request.getContextPath()%>/getPicture?codice=<%=prodotto.getCodice()%>" alt="immagine prodotto"></a> -->
                                <!-- Prezzo del prodotto -->
                                <!-- <div class="d-flex justify-content-center flex-column ">
                                    <p class="text-center prezzo"><%=prodotto.getPrezzo()%>&euro;</p>
                                    <button type="button" class="btn btn-info btn-block aggiungiAlCarrello" data-id="<%=prodotto.getCodice()%>">Aggiungi al carrello</button>
                                </div> --> --%>
                            </div>
                        </div>
                    </div>
                    
                                   
                    
                  
                    
                    <%-- <!-- <%}}%> --> --%>
                    <!-- Fine del loop -->
                </div>
            </div>
        </div>
    </section>
</main>
		
		
	</div> <!-- DIV IMMAGINE CIELO CHIUSO  -->
	
	
	
</body>
<%@include file="../Footer.jsp" %>
</html>