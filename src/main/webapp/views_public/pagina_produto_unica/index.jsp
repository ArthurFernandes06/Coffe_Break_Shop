<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/views_public/base/style_geral.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/views_public/pagina_produto_unica/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/views_public/imgs/xicara-fav-icon.png" type="image/png">
    <title>Coffe Break Shop</title>
    <script src="${pageContext.request.contextPath}/views_public/pagina_produto_unica/script.js"></script>
</head>
<body>
	<header>
	    <img id="img_logo" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/xicara-logo.png" alt="Icone logo Coffe Break">
	    <nav>
	        <a href="#">Produtos</a>
	        <a href="#">Categorias</a>
	        <a href="#">Ofertas</a>
	        <a href="#">Sobre Nós</a>

	    </nav>
	    <div id="div_imgs_menu">
	        <img class="img_header" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/carrinho.png" alt="Icone da Sacola">
	        <img class="img_header" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/user.png" alt="Icone User">
	    </div>
	</header>
	<body>
		<main class="container-produto">
			<h1>${produto.nome}</h1>
			<div class="produto-detalhes">
				
				<div class="foto-produto">
					<img src="${pageContext.request.contextPath}/uploads/${produto.foto}" alt="${produto.nome}" width="350">
				</div>

				<div class="informacoes-produto">
					<h2 class="preco">${produto.preco} R$</h2>
					<button class="btn-comprar">Adicionar ao Carrinho</button>
					<h3><u>Descrição: </u></h3>
					<p class="descricao">${produto.descricao}</p>
				</div>

			</div>
		</main>
	</body>
</body>