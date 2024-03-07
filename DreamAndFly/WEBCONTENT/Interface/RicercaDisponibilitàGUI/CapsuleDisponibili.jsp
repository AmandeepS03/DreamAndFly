<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" import="java.util.*, storage.*"%>
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
	 <%
/* if(auth.getRuolo()==1){ */
	request.setAttribute("page", 5);
	List<FasciaOraria> fasceOrarie = (List<FasciaOraria>) request.getAttribute("listaFasceOrarie");

	if(fasceOrarie == null){
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/GetFasceOrarieServlet");
	    dispatcher.forward(request, response);
	 } 
	List<Capsula> capsule = (List<Capsula>) request.getAttribute("listaCapsule");
	Integer counterOre = (Integer) request.getAttribute("counterOre");
	String dataInizio = (String) request.getAttribute("dataInizio");
%>	
	<div class="image">
		
	
	
		<div class="v35_177">
    <form action="/DreamAndFly/RicercaDisponibilitaServlet" method="post">

        <div class="containerLabel">
            <div>
                <label for="dal">Dal:</label> <br>
                <input type="text" id="dal" name="dal" required>
            </div>

            <div>
                <label for="orarioInizio"></label> <br> <br>
                <select class="inputField" id="fasciaOrariaInizio" name="orarioInizio" required>
                	<option disabled selected></option>
                    <% if (fasceOrarie != null) {
                        for (FasciaOraria fascia : fasceOrarie) { %>
                            <option value="<%= fascia.getNumero() %>"><%= fascia.getorarioInizio() %></option>
                    <% }} %>
                </select>
            </div>

            <div>
                <label for="al">Al:</label><br>
                <input type="text" id="al" name="al" required>
            </div>

            <div>
                <label for="orarioFine"></label> <br> <br>
                <select class="inputField" id="fasciaOrariaFine" name="orarioFine" required>
                <option disabled id="disabledOption" selected></option>
 					<% if (fasceOrarie != null) {
                        for (FasciaOraria fascia : fasceOrarie) { %>
                            <option value="<%= fascia.getNumero() %>"><%= fascia.getorarioFine() %></option>
                    <% } } %>
                </select>
                <script>
					// Recupera l'elemento select e l'opzione disabilitata
					const select = document.getElementById('fasciaOrariaFine');
					const disabledOption = document.getElementById('disabledOption');
					
					// Recupera l'elemento specifico
					const specificButton = document.getElementById('fasciaOrariaInizio');
					
					// Recupera gli input
					const input1 = document.getElementById('dal');
					const input2 = document.getElementById('al');
					
					// Associa un gestore di eventi al pulsante specifico
					specificButton.addEventListener('click', function() {
					  // Imposta l'opzione disabilitata come selezionata ogni volta che viene cliccato il pulsante specifico
					  if (input1.value === input2.value) {
					  	select.value = disabledOption.value;
					  }
					});
					
					input2.addEventListener('click', function() {
						  // Imposta l'opzione disabilitata come selezionata ogni volta che viene cliccato il pulsante specifico
						  
						  	select.value = disabledOption.value;
						  
						});
				</script>
            </div>
        </div>

        <br>
        <button type="submit" id="cercaButton" value="Cerca">Cerca</button>
    </form>
</div>

<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
    $(function() {
        var today = new Date(); // Ottiene la data odierna

        $("#dal, #al").datepicker({
            dateFormat: 'yy-mm-dd',
            minDate: today, // Imposta la data minima come odierna
            onSelect: function(selectedDate) {
                // Imposta la data minima per il datepicker #al come la data selezionata nel datepicker #dal
                if ($(this).attr('id') === 'dal') {
                    $("#al").datepicker("option", "minDate", selectedDate);
                }

                // Abilita/disabilita le fasce orarie di fine in base alla data di inizio e fine
                checkOrarioFine();
            }
        });

        // Gestisci la modifica delle fasce orarie di inizio
        $("#fasciaOrariaInizio").change(function() {
            // Abilita/disabilita le fasce orarie di fine in base alla data di inizio e fine
            checkOrarioFine();
        });

        // Funzione per abilitare/disabilitare le fasce orarie di fine in base alla data di inizio e fine
        function checkOrarioFine() {
            var startDate = $("#dal").datepicker("getDate");
            var endDate = $("#al").datepicker("getDate");
            if (startDate && endDate && startDate.getTime() === endDate.getTime()) {
                // Se le date di inizio e fine sono uguali, abilita solo le fasce orarie di fine successive alla fascia oraria di inizio
                var selectedStart = parseInt($("#fasciaOrariaInizio").val());
                $("#fasciaOrariaFine option").prop("disabled", function() {
                    return parseInt($(this).val()) <= selectedStart-1;
                });

                /* // Se l'orario di fine inserito è minore dell'orario di inizio, impostiamo l'orario di fine sulla prima fascia oraria maggiore
                var startTime = parseInt($("#fasciaOrariaInizio").val());
                var endTime = parseInt($("#fasciaOrariaFine").val());
                if (endTime && startTime > endTime) {
                    $("#fasciaOrariaFine").val('');
                } */
                
            } else {
                // Altrimenti, abilita tutte le fasce orarie di fine
                $("#fasciaOrariaFine option").prop("disabled", false);
            }
        }
    });
</script>



	
		
		
		<main>
    <section class="novita-section">
        <div class="album py-5">
            <div class="container">
                <div class="row">
                    <% if (capsule != null && capsule.size() != 0) {
				        Iterator<?> it = capsule.iterator();
				        while (it.hasNext()) {
				          Capsula capsula = (Capsula) it.next();
				      %>
                    <div class="col-md-4">
                        <div class="card mb-4 box-shadow">
                            <div class="card-body d-flex flex-column">
                            	
                          
                                <div>
	                                <h4 style="display: inline-block;">Capsula <%=capsula.getId() %> </h4>
	                                
                                </div>
                                <ul>
						            <li><%= capsula.getTipologia() %></li>
						            
						        </ul>
						        <p>Dal <%=dataInizio %> alle <%=request.getAttribute("orarioInizio") %> </p>
						        <p>Al <%=request.getAttribute("dataFine") %> alle <%=request.getAttribute("orarioFine") %></p>
						        
						        <div>
	                                <h4 style="display: inline-block;" class="prezzo"><%= capsula.getPrezzo_orario()*counterOre %>&euro;</h4> <!-- prezzo dinamicamente -->
	                               
                                </div>
						        <% request.setAttribute("prezzoTotale",  (capsula.getPrezzo_orario()*counterOre) ); %>
 								<!-- //form e ridireziona a pagamento.jsp salvando la capusla che si intende prenotare -->
 								<form action="/DreamAndFly/CapsuleDisponibili" method="post">
						        	<button type="submit" class="prenotaButton" value="Prenota" >Prenota</button>
						        </form>              
                            </div>
                        </div>
                    </div>
                    
                                   
                    
                  
                    
                    <%}}%>
                    
                </div>
            </div>
        </div>
    </section>
</main>
		
		
	</div> <!-- DIV IMMAGINE CIELO CHIUSO  -->
	
	
	
</body>
<%@include file="../Footer.jsp" %>
</html>