<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/views_public/base/style_geral.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/views_public/pagina_produto_unica/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/imgs/xicara-fav-icon.png" type="image/png">
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
	        <a href="${pageContext.request.contextPath}/carrinho"><img class="img_header" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/carrinho.png" alt="Icone da Sacola"></a>
	        <a href="${pageContext.request.contextPath}${not empty sessionScope.usuarioLogado ? '/usuario' : '/login'}"><img class="img_header" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/user.png" alt="Icone User"></a>
	    </div>
	</header>
	<main class="container-produto">
		<h1>${fn:escapeXml(produto.nome)}</h1>
		<div class="produto-detalhes">

			<div class="foto-produto">
				<c:choose>
					<c:when test="${not empty produto.foto}">
						<img src="${pageContext.request.contextPath}/uploads/${fn:escapeXml(produto.foto)}" alt="${fn:escapeXml(produto.nome)}" width="350">
					</c:when>
					<c:otherwise>
						<div class="produto-sem-foto">
							<span>${fn:escapeXml(produto.nome)}</span>
						</div>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="informacoes-produto">
				<h2 class="preco"><fmt:formatNumber value="${produto.preco}" type="currency" currencySymbol="R$" /></h2>
				<form class="form-comprar" action="${pageContext.request.contextPath}/carrinho/adicionar" method="post">
					<input type="hidden" name="produto_id" value="${produto.id}">
					<label for="quantidade">Quantidade</label>
					<input type="number" id="quantidade" name="quantidade" value="1" min="1" max="${produto.quantidade}" required>
					<button type="submit" class="btn-comprar">Adicionar ao Carrinho</button>
				</form>
				<h3><u>Descrição: </u></h3>
				<p class="descricao">${fn:escapeXml(empty produto.descricao ? 'Produto disponível para compra.' : produto.descricao)}</p>
			</div>

		</div>
	</main>
</body>
