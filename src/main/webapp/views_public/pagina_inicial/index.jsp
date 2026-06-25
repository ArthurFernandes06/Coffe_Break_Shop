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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views_public/pagina_inicial/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/imgs/xicara-fav-icon.png" type="image/png">
    <title>Coffe Break Shop</title>
    <script defer src="${pageContext.request.contextPath}/views_public/pagina_inicial/script.js"></script>
</head>
<body>
    <header>
        <img id="img_logo" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/xicara-logo.png" alt="Icone logo Coffe Break">
        <nav>
            <a href="#produtos">Produtos</a>
            <a href="#categorias">Categorias</a>

        </nav>
        <div id="div_imgs_menu">
            <img class="img_header" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/carrinho.png" alt="Icone da Sacola">
            <img class="img_header" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/user.png" alt="Icone User">
        </div>
    </header>
    <main>
        <section id="section_main">
            <div id ="texto_main">
                <h1>Coffe Break<br>Shop</h1>
                <p>
                    Precisa de um break do estudo ou trabalho?<br>
                    Tudo que você precisa para um <strong>coffe break</strong> está aqui.
                </p>
            </div>

            <div id="div_imgs_section">
                <div style="display: flex;">
                    <div class="div_section_maior" >
                        <img id="img_mesa" class="img_section_maior"   src="${pageContext.request.contextPath}/imgs/pagina_inicial/img_section/mesa_cafe.png" alt="Mesa com café">
                    </div>
        
                    <div class="div_section_menor" >
                        <img id="img_computadores" class="img_section_menor"  src="${pageContext.request.contextPath}/imgs/pagina_inicial/img_section/computadores.jpg" alt="Imagem de pessoas em computadores">
                    </div>
                </div>

                <div style="display: flex;">
                    <div class="div_section_menor">
                        <img id="img_alunos"  class="img_section_menor" src="${pageContext.request.contextPath}/imgs/pagina_inicial/img_section/alunos.png" alt="Alunos estudando">
                    </div>
    
                    <div class="div_section_maior">
                        <img  id="img_baralho" class="img_section_maior" src="${pageContext.request.contextPath}/imgs/pagina_inicial/img_section/baralho.jpg" alt="Baralho">
                    </div>
                </div>
                
               
            </div>
            
        </section>

        <section id="produtos" class="section_produtos">
            <div class="section_title">
                <h2>Produtos em estoque</h2>
            </div>

            <div class="produtos_grid">
                <c:forEach var="produto" items="${produtosEmEstoque}">
                    <article class="produto_card">
                        <a class="produto_link" href="${pageContext.request.contextPath}/produto?id=${produto.id}">
                            <c:choose>
                                <c:when test="${not empty produto.foto}">
                                    <img class="produto_foto"
                                         src="${pageContext.request.contextPath}/uploads/${fn:escapeXml(produto.foto)}"
                                         alt="${fn:escapeXml(produto.nome)}">
                                </c:when>
                                <c:otherwise>
                                    <div class="produto_sem_foto">
                                        <span>${fn:escapeXml(produto.nome)}</span>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <div class="produto_info">
                                <c:if test="${not empty produto.categoria}">
                                    <span class="produto_categoria">${fn:escapeXml(produto.categoria.nome)}</span>
                                </c:if>
                                <h3>${fn:escapeXml(produto.nome)}</h3>
                                <strong>
                                    <fmt:formatNumber value="${produto.preco}" type="currency" currencySymbol="R$" />
                                </strong>
                            </div>
                        </a>
                    </article>
                </c:forEach>

                <c:if test="${empty produtosEmEstoque}">
                    <p class="sem_produtos">Nenhum produto em estoque no momento.</p>
                </c:if>
            </div>
        </section>

        <section id="categorias" class="section_categorias">
            <h2>Categorias</h2>
            <div class="categorias_lista">
                <c:forEach var="categoria" items="${categoriasCadastradas}">
                    <a href="#" class="link_categorias">
                        ${fn:escapeXml(categoria.nome)}
                    </a>
                </c:forEach>
            </div>
        </section>
        
        
    </main>

</body>
</html>
