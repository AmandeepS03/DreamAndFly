<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Inter&display=swap"
	rel="stylesheet" />
<link href="<%=request.getContextPath() %>/styles/Pagamento.css" rel="stylesheet" />
<title>Document</title>
</head>
<body>
	<div class="v9_194">
		<div class="v9_195">
			<%@include file="../Header.jsp" %>
		</div>
		<div class="v9_201">
			<%@include file="../Footer.jsp" %>
		</div>
		<div class="v9_203">
			<div class="v9_212">
				<span class="v9_214">Conferma pagamento</span>
			</div>
			<div class="v9_215">
				<span class="v9_209">CVC:</span>
				<div class="v9_210"></div>
			</div>
			<span class="v9_218">Dati carta</span>
			<div class="v9_219">
				<div class="v9_205"></div>
				<span class="v9_206">Numero carta:</span>
			</div>
			<div class="v9_220">
				<div class="v9_208"></div>
				<span class="v9_207">Scadenza:</span>
			</div>
			<div class="v42_2">
				<div class="v42_3"></div>
				<span class="v42_4">Nome intestatario:</span>
			</div>
		</div>
	</div>
</body>
</html>