<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.coffeebreak.models.Usuario.Usuario" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%
    HttpSession sessaoAtual = request.getSession(false);
    Usuario usuarioLogado = (sessaoAtual != null) ? (Usuario) sessaoAtual.getAttribute("usuarioLogado") : null;
    if (usuarioLogado == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String erro = (String) request.getAttribute("erro");
    String sucesso = (String) request.getAttribute("sucesso");
    String activeSection = (String) request.getAttribute("activeSection");
    if (activeSection == null || activeSection.isBlank()) {
        activeSection = "perfil";
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Minha Conta – Coffee Break</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views_public/pagina_do_usuario/style.css">
</head>
<body>

<header>
    <img id="img_logo" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/xicara-logo.png" alt="Logo">
    <nav>
        <a href="${pageContext.request.contextPath}/home">Início</a>
        <a href="${pageContext.request.contextPath}/home#produtos">Cardápio</a>
        <a href="#">Sobre</a>
    </nav>
    <div id="div_imgs_menu">
        <a href="${pageContext.request.contextPath}/carrinho">
            <img class="img_header" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/carrinho.png" alt="Carrinho">
        </a>
        <a href="${pageContext.request.contextPath}/usuario">
            <img class="img_header" src="${pageContext.request.contextPath}/imgs/pagina_inicial/header/user.png" alt="Usuário">
        </a>
    </div>
</header>

<main>
    <aside>
        <nav>
            <a href="#" class="nav-link" data-section="perfil">👤 Meu Perfil</a>
            <a href="${pageContext.request.contextPath}/usuario?secao=pedidos" class="nav-link" data-section="pedidos">📦 Pedidos</a>
            <a href="${pageContext.request.contextPath}/carrinho" class="nav-link <%= "carrinho".equals(activeSection) ? "active" : "" %>">🛒 Carrinho</a>
            <a href="#" class="nav-link" data-section="configuracoes">⚙️ Configurações</a>
            <a href="${pageContext.request.contextPath}/logoff" class="nav-link sair-link">🚪 Sair</a>
        </nav>
    </aside>

    <div id="conteudo-principal">

        <!-- ===== PERFIL ===== -->
        <section class="pagina" id="perfil">
            <h2>Meu Perfil</h2>

            <% if (erro != null) { %>
                <div class="alerta alerta-erro"><%= erro %></div>
            <% } %>
            <% if (sucesso != null) { %>
                <div class="alerta alerta-sucesso"><%= sucesso %></div>
            <% } %>

            <form action="${pageContext.request.contextPath}/usuario/atualizar" method="post" id="form-perfil">

                <!-- Nome -->
                <div class="card">
                    <h3>Nome</h3>
                    <p>
                        <span id="exibir-nome"><%= usuarioLogado.getNome() %></span>
                        <button type="button" class="editar" id="btn-editar-nome" data-campo="nome">Editar</button>
                    </p>
                    <label for="input-nome" style="display:none;">Novo nome:</label>
                    <input type="text" id="input-nome" name="nome" value="<%= usuarioLogado.getNome() %>" style="display:none;" required>
                    <button type="button" class="salvar" id="btn-salvar-nome" data-campo="nome" style="display:none;">Salvar</button>
                </div>

                <!-- E-mail -->
                <div class="card">
                    <h3>E-mail</h3>
                    <p>
                        <span id="exibir-email"><%= usuarioLogado.getEmail() %></span>
                        <button type="button" class="editar" id="btn-editar-email" data-campo="email">Editar</button>
                    </p>
                    <label for="input-email" style="display:none;">Novo e-mail:</label>
                    <input type="email" id="input-email" name="email" value="<%= usuarioLogado.getEmail() %>" style="display:none;" required>
                    <button type="button" class="salvar" id="btn-salvar-email" data-campo="email" style="display:none;">Salvar</button>
                </div>

                <!-- Endereço -->
                <div class="card">
                    <h3>Endereço</h3>
                    <p>
                        <span id="exibir-endereco"><%= usuarioLogado.getEndereco() %></span>
                        <button type="button" class="editar" id="btn-editar-endereco" data-campo="endereco">Editar</button>
                    </p>
                    <label for="input-endereco" style="display:none;">Novo endereço:</label>
                    <input type="text" id="input-endereco" name="endereco" value="<%= usuarioLogado.getEndereco() %>" style="display:none;">
                    <button type="button" class="salvar" id="btn-salvar-endereco" data-campo="endereco" style="display:none;">Salvar</button>
                </div>

                <!-- Senha -->
                <div class="card">
                    <h3>Senha</h3>
                    <p>
                        <span id="exibir-senha">••••••••</span>
                        <button type="button" class="editar" id="btn-editar-senha" data-campo="senha">Editar</button>
                    </p>
                    <label for="input-senha" style="display:none;">Nova senha:</label>
                    <input type="password" id="input-senha" name="senha" placeholder="Nova senha" style="display:none;">
                    <button type="button" class="salvar" id="btn-salvar-senha" data-campo="senha" style="display:none;">Salvar</button>
                </div>

                <button type="submit" class="btn-atualizar" id="btn-atualizar-dados" style="display:none;">
                    ✔ Confirmar Alterações
                </button>
            </form>
        </section>

        <!-- ===== PEDIDOS ===== -->
        <section class="pagina" id="pedidos">
            <h2>Meus Pedidos</h2>

            <c:if test="${not empty param.sucesso}">
                <div class="alerta alerta-sucesso">${fn:escapeXml(param.sucesso)}</div>
            </c:if>
            <c:if test="${not empty param.erro}">
                <div class="alerta alerta-erro">${fn:escapeXml(param.erro)}</div>
            </c:if>

            <div class="card">
                <h3>Total de pedidos: <span id="total-pedidos">${fn:length(vendasUsuario)}</span></h3>
                <h3>
                    Último pedido:
                    <span id="ultimo-pedido">
                        <c:choose>
                            <c:when test="${not empty vendasUsuario}">
                                #${vendasUsuario[0].id}
                            </c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </span>
                </h3>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Pedido</th>
                        <th>Produtos</th>
                        <th>Data</th>
                        <th>Status</th>
                        <th>Valor</th>
                    </tr>
                </thead>
                <tbody id="historico-pedidos">
                    <c:forEach var="venda" items="${vendasUsuario}">
                        <tr>
                            <td>#${venda.id}</td>
                            <td>
                                <c:forEach var="itemVenda" items="${produtosPorVenda[venda.id]}" varStatus="loop">
                                    ${fn:escapeXml(itemVenda.produto.nome)} (${itemVenda.quantidade})<c:if test="${not loop.last}">, </c:if>
                                </c:forEach>
                            </td>
                            <td><fmt:formatDate value="${venda.dataHora}" pattern="dd/MM/yyyy HH:mm" /></td>
                            <td>${fn:escapeXml(venda.status)}</td>
                            <td>R$ <fmt:formatNumber value="${venda.valorTotal}" pattern="0.00" /></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty vendasUsuario}">
                        <tr><td colspan="5">Nenhum pedido realizado ainda.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </section>

        <!-- ===== CARRINHO ===== -->
        <section class="pagina" id="carrinho">
            <h2>Meu Carrinho</h2>

            <c:if test="${not empty param.sucesso}">
                <div class="alerta alerta-sucesso">${fn:escapeXml(param.sucesso)}</div>
            </c:if>
            <c:if test="${not empty param.erro}">
                <div class="alerta alerta-erro">${fn:escapeXml(param.erro)}</div>
            </c:if>

            <div class="card">
                <c:choose>
                    <c:when test="${not empty itensCarrinho}">
                        <div class="carrinho-lista">
                            <c:forEach var="item" items="${itensCarrinho}">
                                <div class="carrinho-item">
                                    <div class="carrinho-produto">
                                        <c:choose>
                                            <c:when test="${not empty item.produto.foto}">
                                                <img src="${pageContext.request.contextPath}/uploads/${fn:escapeXml(item.produto.foto)}" alt="${fn:escapeXml(item.produto.nome)}">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="carrinho-sem-foto">${fn:escapeXml(item.produto.nome)}</div>
                                            </c:otherwise>
                                        </c:choose>
                                        <div>
                                            <h3>${fn:escapeXml(item.produto.nome)}</h3>
                                            <p>
                                                R$ <fmt:formatNumber value="${item.produto.preco}" pattern="0.00" />
                                                <span>Estoque: ${item.produto.quantidade}</span>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="carrinho-acoes">
                                        <form action="${pageContext.request.contextPath}/carrinho/atualizar" method="post">
                                            <input type="hidden" name="produto_id" value="${item.produto.id}">
                                            <label for="qtd-${item.produto.id}">Quantidade</label>
                                            <input type="number" id="qtd-${item.produto.id}" name="quantidade"
                                                   value="${item.quantidade}" min="1" max="${item.produto.quantidade}" required>
                                            <button type="submit" class="btn-carrinho">Atualizar</button>
                                        </form>

                                        <form action="${pageContext.request.contextPath}/carrinho/remover" method="post">
                                            <input type="hidden" name="produto_id" value="${item.produto.id}">
                                            <button type="submit" class="btn-remover-carrinho">Remover</button>
                                        </form>
                                    </div>

                                    <strong>
                                        R$ <fmt:formatNumber value="${item.produto.preco * item.quantidade}" pattern="0.00" />
                                    </strong>
                                </div>
                            </c:forEach>
                        </div>

                        <p id="preco-total">
                            Preço total:
                            <strong>R$ <fmt:formatNumber value="${totalCarrinho}" pattern="0.00" /></strong>
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p>Carrinho vazio.</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${not empty itensCarrinho}">
                <form action="${pageContext.request.contextPath}/compra/finalizar" method="post">
                    <button id="btn-finalizar" type="submit">Finalizar Compra</button>
                </form>
            </c:if>
        </section>

        <!-- ===== CONFIGURAÇÕES ===== -->
        <section class="pagina" id="configuracoes">
            <h2>Configurações da Conta</h2>

            <div class="card">
                <h3>🗑️ Excluir Conta</h3>
                <p>Esta ação é permanente e não pode ser desfeita. Todos os seus dados serão removidos.</p>
                <button type="button" class="btn-deletar" id="btn-abrir-modal-deletar">Excluir minha conta</button>
            </div>
        </section>

    </div><!-- fim #conteudo-principal -->
</main>

<!-- ===== MODAL CONFIRMAÇÃO DE DELEÇÃO ===== -->
<div id="modal-deletar" class="modal-overlay" style="display:none;">
    <div class="modal-box">
        <h3>⚠️ Confirmar exclusão</h3>
        <p>Tem certeza que deseja excluir sua conta? Esta ação é <strong>irreversível</strong>.</p>
        <div class="modal-acoes">
            <button type="button" id="btn-cancelar-deletar" class="editar">Cancelar</button>
            <form action="${pageContext.request.contextPath}/usuario/remover" method="post" style="display:inline;">
                <button type="submit" class="btn-deletar">Sim, excluir</button>
            </form>
        </div>
    </div>
</div>

<script>
    window.activeSection = "<%= activeSection %>";
</script>
<script src="${pageContext.request.contextPath}/views_public/pagina_do_usuario/script.js"></script>
</body>
</html>
