<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.coffeebreak.models.Usuario.Usuario" %>
<%
    HttpSession sessaoAtual = request.getSession(false);
    Usuario usuarioLogado = (sessaoAtual != null) ? (Usuario) sessaoAtual.getAttribute("usuarioLogado") : null;
    if (usuarioLogado == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String erro = (String) request.getAttribute("erro");
    String sucesso = (String) request.getAttribute("sucesso");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Minha Conta – Coffee Break</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<header>
    <nav>
        <img id="img_logo" src="${pageContext.request.contextPath}/views_public/imgs/pagina_inicial/header/xicara-logo.png" alt="Logo">
        <a href="#">Início</a>
        <a href="#">Cardápio</a>
        <a href="#">Sobre</a>
    </nav>
    <div id="div_imgs_menu">
        <a href="${pageContext.request.contextPath}/carrinho">
            <img class="img_header" src="${pageContext.request.contextPath}/views_public/imgs/pagina_inicial/header/carrinho.png" alt="Carrinho">
        </a>
        <a href="${pageContext.request.contextPath}/usuario">
            <img class="img_header" src="${pageContext.request.contextPath}/views_public/imgs/pagina_inicial/header/user.png" alt="Usuário">
        </a>
    </div>
</header>

<main>
    <aside>
        <nav>
            <a href="#" class="nav-link" data-section="perfil">👤 Meu Perfil</a>
            <a href="#" class="nav-link" data-section="pedidos">📦 Pedidos</a>
            <a href="#" class="nav-link" data-section="carrinho">🛒 Carrinho</a>
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
            <div class="card">
                <h3>Total de pedidos: <span id="total-pedidos">0</span></h3>
                <h3>Último pedido: <span id="ultimo-pedido">–</span></h3>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Produto</th>
                        <th>Data</th>
                        <th>Status</th>
                        <th>Valor</th>
                    </tr>
                </thead>
                <tbody id="historico-pedidos"></tbody>
            </table>
        </section>

        <!-- ===== CARRINHO ===== -->
        <section class="pagina" id="carrinho">
            <h2>Meu Carrinho</h2>
            <div class="card">
                <div id="itens-carrinho"></div>
                <p id="preco-total">Preço total: R$ 0,00</p>
            </div>
            <button id="btn-finalizar">Finalizar Compra</button>
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

<script src="script.js"></script>
</body>
</html>