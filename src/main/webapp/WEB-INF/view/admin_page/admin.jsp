<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views_admin/admin_page/admin_style.css?v=perfil-admin-2">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/imgs/xicara-fav-icon.png" type="image/png">
    <title>Coffe Break Shop — Admin</title>
</head>
<body>

<header>
    <div id="header-logo">
        <img src="${pageContext.request.contextPath}/../imgs/pagina_inicial/header/xicara-logo.png" alt="Logo Coffe Break">
        <span>Coffe Break <strong>Admin</strong></span>
    </div>
    <span id="admin-name">👤 ${sessionScope.adminLogado.nome}</span>
</header>

<div id="layout">
    <nav id="sidebar">
        <button class="nav-btn active" data-secao="dashboard" onclick="showPage('dashboard', this)">📊 Dashboard</button>
        <button class="nav-btn" data-secao="produtos" onclick="showPage('produtos', this)">☕ Produtos</button>
        <button class="nav-btn" data-secao="categorias" onclick="showPage('categorias', this)">🏷️ Categorias</button>
        <button class="nav-btn" data-secao="pedidos" onclick="showPage('pedidos', this)">🛒 Pedidos</button>
        <button class="nav-btn" data-secao="usuarios" onclick="showPage('usuarios', this)">👥 Usuários</button>
        <button class="nav-btn" data-secao="perfil-admin" onclick="showPage('perfil-admin', this)">👤 Meus dados</button>
        <button class="nav-btn" onclick="window.location.href='${pageContext.request.contextPath}/admin/logout'">🚪 Sair</button>
    </nav>

    <main id="content">
        <c:if test="${param.sucesso == 'dadosAtualizados'}">
            <div class="alert alert-success">Dados atualizados com sucesso.</div>
        </c:if>
        <c:if test="${param.erro == 'emailInvalido'}">
            <div class="alert alert-error">E-mail inválido. As alterações não foram salvas.</div>
        </c:if>
        <c:if test="${param.erro == 'atualizacao'}">
            <div class="alert alert-error">Não foi possível atualizar os dados. Tente novamente.</div>
        </c:if>
        <c:if test="${param.erro == 'remocao'}">
            <div class="alert alert-error">Não foi possível remover a conta. Tente novamente.</div>
        </c:if>
        <c:if test="${not empty param.sucesso && param.sucesso != 'dadosAtualizados'}">
            <div class="alert alert-success">${fn:escapeXml(param.sucesso)}</div>
        </c:if>
        <c:if test="${not empty param.erro && param.erro != 'emailInvalido' && param.erro != 'atualizacao' && param.erro != 'remocao'}">
            <div class="alert alert-error">${fn:escapeXml(param.erro)}</div>
        </c:if>

        <section id="page-dashboard" class="page active">
            <h2>Dashboard</h2>
            <div class="cards-row">
                <div class="info-card">
                    <span class="card-label">Pedidos hoje</span>
                    <span class="card-value">${totalHoje}</span>
                </div>
                <div class="info-card">
                    <span class="card-label">Receita do mês</span>
                    <span class="card-value"><fmt:formatNumber value="${receitaMes}" type="currency" currencySymbol="R$" /></span>
                </div>
                <div class="info-card">
                    <span class="card-label">Produtos</span>
                    <span class="card-value">${totalProdutos}</span>
                </div>
                <div class="info-card">
                    <span class="card-label">Usuários</span>
                    <span class="card-value">${totalUsuarios}</span>
                </div>
            </div>

            <div class="table-box">
                <h3>Pedidos recentes</h3>
                <table>
                    <thead>
                        <tr><th>Cliente</th><th>Total</th><th>Status</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="v" items="${pedidosRecentes}">
                            <tr>
                                <td>${v.usuario.nome}</td>
                                <td><fmt:formatNumber value="${v.valorTotal}" type="currency" currencySymbol="R$" /></td>
                                <td><span class="badge ${v.status == 'PAGO' || v.status == 'ENVIADO' ? 'ok' : (v.status == 'PENDENTE' ? 'pend' : 'cancel')}">${v.status}</span></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>

        <section id="page-produtos" class="page">
            <h2>Produtos</h2>

            <c:if test="${not empty param.sucesso}">
                <div class="alert alert-success">${fn:escapeXml(param.sucesso)}</div>
            </c:if>
            <c:if test="${not empty param.erro}">
                <div class="alert alert-error">${fn:escapeXml(param.erro)}</div>
            </c:if>

            <div class="section-top">
                <input type="text" id="busca-produto" placeholder="Buscar produto..." oninput="filtrar('busca-produto', 'tb-produtos', 0)">
                <button class="btn-primary" type="button" onclick="novoProduto()">+ Novo produto</button>
            </div>

            <div id="form-produto" class="form-box hidden">
                <h3 id="form-produto-titulo">Novo produto</h3>
                <form id="produto-form" method="POST"
                      action="${pageContext.request.contextPath}/admin/produto/cadastrar"
                      enctype="multipart/form-data"
                      data-cadastrar-url="${pageContext.request.contextPath}/admin/produto/cadastrar"
                      data-atualizar-url="${pageContext.request.contextPath}/admin/produto/atualizar">

                    <input type="hidden" name="id" id="p-id" value="">

                    <div class="form-row">
                        <div class="input-group">
                            <label for="p-nome">Nome</label>
                            <input type="text" id="p-nome" name="nome" placeholder="Ex: Café com leite" required>
                        </div>
                        <div class="input-group">
                            <label for="p-preco">Preço (R$)</label>
                            <input type="number" id="p-preco" name="preco" step="0.01" min="0" placeholder="0,00" required>
                        </div>
                        <div class="input-group">
                            <label for="p-quantidade">Estoque</label>
                            <input type="number" id="p-quantidade" name="quantidade" min="0" value="0">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="input-group">
                            <label for="p-cat">Categoria</label>
                            <select id="p-cat" name="id_categoria" required>
                                <option value="">Selecione...</option>
                                <c:forEach var="cat" items="${categorias}">
                                    <option value="${cat.id}">${fn:escapeXml(cat.nome)}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="input-group">
                            <label for="p-foto">Foto do produto</label>
                            <input type="file" id="p-foto" name="foto" accept="image/*">
                            <span id="p-foto-atual" class="foto-atual-hint"></span>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="input-group" style="flex-basis: 100%;">
                            <label for="p-descricao">Descrição</label>
                            <input type="text" id="p-descricao" name="descricao" placeholder="Breve descrição do produto">
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="button" class="btn-secondary" onclick="toggleForm('form-produto')">Cancelar</button>
                        <button type="submit" class="btn-primary">Salvar</button>
                    </div>
                </form>
            </div>

            <c:if test="${empty categorias}">
                <div class="alert alert-error">
                    Nenhuma categoria cadastrada ainda. Cadastre ao menos uma categoria antes de criar produtos.
                </div>
            </c:if>

            <div class="table-box">
                <table id="tb-produtos">
                    <thead>
                        <tr><th>Nome</th><th>Categoria</th><th>Preço</th><th>Status</th><th>Ações</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${produtos}">
                            <tr
                                data-id="${p.id}"
                                data-nome="${fn:escapeXml(p.nome)}"
                                data-preco="${p.preco}"
                                data-quantidade="${p.quantidade}"
                                data-categoria="${p.categoria.id}"
                                data-descricao="${fn:escapeXml(empty p.descricao ? '' : p.descricao)}"
                                data-foto="${fn:escapeXml(empty p.foto ? '' : p.foto)}">
                                <td>${fn:escapeXml(p.nome)}</td>
                                <td>${fn:escapeXml(p.categoria.nome)}</td>
                                <td><fmt:formatNumber value="${p.preco}" type="currency" currencySymbol="R$" /></td>
                                <td>
                                    <span class="badge ${p.quantidade > 0 ? 'ok' : 'cancel'}">
                                        ${p.quantidade > 0 ? 'Ativo' : 'Esgotado'}
                                    </span>
                                </td>
                                <td>
                                    <div class="acoes-cell">
                                        <button type="button" class="btn-sm" onclick="editarProduto(this)">Editar</button>
                                        <form method="POST" action="${pageContext.request.contextPath}/admin/produto/remover"
                                              onsubmit="return confirm('Tem certeza que deseja remover este produto?');">
                                            <input type="hidden" name="id" value="${p.id}">
                                            <button type="submit" class="btn-sm btn-danger">Excluir</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty produtos}">
                            <tr><td colspan="5">Nenhum produto cadastrado ainda.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </section>

        <section id="page-categorias" class="page">
            <h2>Categorias</h2>

            <c:if test="${not empty param.sucesso}">
                <div class="alert alert-success">${fn:escapeXml(param.sucesso)}</div>
            </c:if>
            <c:if test="${not empty param.erro}">
                <div class="alert alert-error">${fn:escapeXml(param.erro)}</div>
            </c:if>

            <div class="section-top">
                <input type="text" id="busca-categoria" placeholder="Buscar categoria..." oninput="filtrar('busca-categoria', 'tb-categorias', 0)">
                <button class="btn-primary" type="button" onclick="novaCategoria()">+ Nova categoria</button>
            </div>

            <div id="form-categoria" class="form-box hidden">
                <h3 id="form-categoria-titulo">Nova categoria</h3>
                <form id="categoria-form" method="POST"
                      action="${pageContext.request.contextPath}/admin/categoria/cadastrar"
                      data-cadastrar-url="${pageContext.request.contextPath}/admin/categoria/cadastrar"
                      data-atualizar-url="${pageContext.request.contextPath}/admin/categoria/atualizar">

                    <input type="hidden" name="id" id="c-id" value="">

                    <div class="form-row">
                        <div class="input-group">
                            <label for="c-nome">Nome</label>
                            <input type="text" id="c-nome" name="nome" placeholder="Ex: Cafés" required>
                        </div>
                        <div class="input-group">
                            <label for="c-descricao">Descrição</label>
                            <input type="text" id="c-descricao" name="descricao" placeholder="Breve descrição da categoria">
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="button" class="btn-secondary" onclick="toggleForm('form-categoria')">Cancelar</button>
                        <button type="submit" class="btn-primary">Salvar</button>
                    </div>
                </form>
            </div>

            <div class="table-box">
                <table id="tb-categorias">
                    <thead>
                        <tr><th>Nome</th><th>Descrição</th><th>Ações</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cat" items="${categorias}">
                            <tr
                                data-id="${cat.id}"
                                data-nome="${fn:escapeXml(cat.nome)}"
                                data-descricao="${fn:escapeXml(empty cat.descricao ? '' : cat.descricao)}">
                                <td>${fn:escapeXml(cat.nome)}</td>
                                <td>${fn:escapeXml(empty cat.descricao ? '-' : cat.descricao)}</td>
                                <td>
                                    <div class="acoes-cell">
                                        <button type="button" class="btn-sm" onclick="editarCategoria(this)">Editar</button>
                                        <form method="POST" action="${pageContext.request.contextPath}/admin/categoria/remover"
                                              onsubmit="return confirm('Tem certeza que deseja remover esta categoria?');">
                                            <input type="hidden" name="id" value="${cat.id}">
                                            <button type="submit" class="btn-sm btn-danger">Excluir</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty categorias}">
                            <tr><td colspan="3">Nenhuma categoria cadastrada ainda.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </section>

        <section id="page-pedidos" class="page">
            <h2>Pedidos</h2>
            <div class="table-box">
                <table id="tb-pedidos">
                    <thead>
                        <tr><th>ID</th><th>Cliente</th><th>Produtos</th><th>Total</th><th>Status</th><th>Ações</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="v" items="${todasVendas}">
                            <tr>
                                <td>#${v.id}</td>
                                <td>${v.usuario.nome}</td>
                                <td>
                                    <c:forEach var="itemVenda" items="${produtosPorVenda[v.id]}" varStatus="loop">
                                        ${fn:escapeXml(itemVenda.produto.nome)} (${itemVenda.quantidade})<c:if test="${not loop.last}">, </c:if>
                                    </c:forEach>
                                </td>
                                <td><fmt:formatNumber value="${v.valorTotal}" type="currency" currencySymbol="R$" /></td>
                                <td><span class="badge ${v.status == 'PAGO' || v.status == 'ENVIADO' ? 'ok' : 'pend'}">${v.status}</span></td>
                                <td>
                                    <div class="acoes-cell">
                                        <form method="POST" action="${pageContext.request.contextPath}/admin/venda/atualizar-status">
                                            <input type="hidden" name="id" value="${v.id}">
                                            <input type="hidden" name="status" value="PAGO">
                                            <button class="btn-sm" type="submit">Confirmar</button>
                                        </form>
                                        <form method="POST" action="${pageContext.request.contextPath}/admin/venda/atualizar-status">
                                            <input type="hidden" name="id" value="${v.id}">
                                            <input type="hidden" name="status" value="ENVIADO">
                                            <button class="btn-sm" type="submit">Enviar</button>
                                        </form>
                                        <form method="POST" action="${pageContext.request.contextPath}/admin/venda/remover"
                                              onsubmit="return confirm('Tem certeza que deseja remover este pedido?');">
                                            <input type="hidden" name="id" value="${v.id}">
                                            <button class="btn-sm btn-danger" type="submit">Excluir</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty todasVendas}">
                            <tr><td colspan="6">Nenhum pedido cadastrado ainda.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </section>

        <section id="page-usuarios" class="page">
            <h2>Usuários</h2>
            <div class="table-box">
                <table id="tb-usuarios">
                    <thead>
                        <tr><th>Nome</th><th>E-mail</th><th>Tipo</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="u" items="${usuarios}">
                            <tr>
                                <td>${u.nome}</td>
                                <td>${u.email}</td>
                                <td><span class="badge ${u.tipo ? 'pend' : 'ok'}">${u.tipo ? 'Admin' : 'Cliente'}</span></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>

        <section id="page-perfil-admin" class="page">
            <h2>Meus Dados</h2>

            <div class="account-grid">
                <form class="form-box account-panel" action="${pageContext.request.contextPath}/admin/atualizar" method="post">
                    <h3>Dados pessoais</h3>

                    <div class="form-row">
                        <div class="input-group">
                            <label for="admin-perfil-nome">Nome</label>
                            <input type="text" id="admin-perfil-nome" name="nome" value="${sessionScope.adminLogado.nome}" required>
                        </div>
                        <div class="input-group">
                            <label for="admin-perfil-email">E-mail</label>
                            <input type="email" id="admin-perfil-email" name="email" value="${sessionScope.adminLogado.email}" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="input-group">
                            <label for="admin-perfil-endereco">Endereço</label>
                            <input type="text" id="admin-perfil-endereco" name="endereco" value="${sessionScope.adminLogado.endereco}">
                        </div>
                        <div class="input-group">
                            <label for="admin-perfil-senha">Nova senha</label>
                            <input type="password" id="admin-perfil-senha" name="senha" placeholder="Deixe em branco para manter a atual">
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn-primary">Salvar alterações</button>
                    </div>
                </form>

                <div class="form-box account-panel danger-panel">
                    <h3>Excluir cadastro</h3>
                    <p>Ao confirmar, seu acesso administrativo será encerrado e o cadastro será removido.</p>
                    <div class="form-actions">
                        <button type="button" class="btn-deletar" id="btn-abrir-modal-deletar-admin" onclick="abrirModalDeletarAdmin()">Excluir meu cadastro</button>
                    </div>
                </div>
            </div>
        </section>

    </main>
</div>

<div id="modal-deletar-admin" class="modal-overlay" style="display:none;" onclick="fecharModalDeletarAdminFora(event)">
    <div class="modal-box">
        <h3>⚠️ Confirmar exclusão</h3>
        <p>Tem certeza que deseja excluir seu cadastro? Esta ação é <strong>irreversível</strong>.</p>
        <div class="modal-acoes">
            <button type="button" id="btn-cancelar-deletar-admin" class="btn-secondary" onclick="fecharModalDeletarAdmin()">Cancelar</button>
            <form action="${pageContext.request.contextPath}/admin/remover" method="post">
                <button type="submit" class="btn-deletar">Sim, excluir</button>
            </form>
        </div>
    </div>
</div>

<script>
    function abrirModalDeletarAdmin() {
        document.getElementById('modal-deletar-admin').style.display = 'flex';
    }

    function fecharModalDeletarAdmin() {
        document.getElementById('modal-deletar-admin').style.display = 'none';
    }

    function fecharModalDeletarAdminFora(event) {
        if (event.target.id === 'modal-deletar-admin') {
            fecharModalDeletarAdmin();
        }
    }
</script>
<script src="${pageContext.request.contextPath}/views_admin/admin_page/admin.js?v=perfil-admin-2"></script>
</body>
</html>
