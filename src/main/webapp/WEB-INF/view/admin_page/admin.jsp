<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views_admin/admin_page/admin_style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/imgs/xicara-fav-icon.png" type="image/png">
    <title>Coffe Break Shop — Admin</title>
</head>
<body>

<header>
    <div id="header-logo">
        <img src="${pageContext.request.contextPath}/../imgs/pagina_inicial/header/xicara-logo.png" alt="Logo Coffe Break">
        <span>Coffe Break <strong>Admin</strong></span>
    </div>
    <span id="admin-name">👤 Administrador</span>
</header>

<div id="layout">
    <nav id="sidebar">
        <button class="nav-btn active" onclick="showPage('dashboard', this)">📊 Dashboard</button>
        <button class="nav-btn" onclick="showPage('produtos', this)">☕ Produtos</button>
        <button class="nav-btn" onclick="showPage('pedidos', this)">🛒 Pedidos</button>
        <button class="nav-btn" onclick="showPage('usuarios', this)">👥 Usuários</button>
    </nav>

    <main id="content">

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
                                <td><span class="badge ${v.status == 'Entregue' ? 'ok' : (v.status == 'Pendente' ? 'pend' : 'cancel')}">${v.status}</span></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>

        <section id="page-produtos" class="page">
            <h2>Produtos</h2>
            <div class="section-top">
                <input type="text" id="busca-produto" placeholder="Buscar produto..." oninput="filtrar('busca-produto', 'tb-produtos', 0)">
                <button class="btn-primary" onclick="toggleForm('form-produto')">+ Novo produto</button>
            </div>
            <div class="table-box">
                <table id="tb-produtos">
                    <thead>
                        <tr><th>Nome</th><th>Categoria</th><th>Preço</th><th>Status</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${produtos}">
                            <tr>
                                <td>${p.nome}</td>
                                <td>${p.categoria.nome}</td>
                                <td><fmt:formatNumber value="${p.preco}" type="currency" currencySymbol="R$" /></td>
                                <td>
                                    <span class="badge ${p.quantidade > 0 ? 'ok' : 'cancel'}">
                                        ${p.quantidade > 0 ? 'Ativo' : 'Esgotado'}
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>

        <section id="page-pedidos" class="page">
            <h2>Pedidos</h2>
            <div class="table-box">
                <table id="tb-pedidos">
                    <thead>
                        <tr><th>ID</th><th>Cliente</th><th>Total</th><th>Status</th><th>Ação</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="v" items="${todasVendas}">
                            <tr>
                                <td>#${v.id}</td>
                                <td>${v.usuario.nome}</td>
                                <td><fmt:formatNumber value="${v.valorTotal}" type="currency" currencySymbol="R$" /></td>
                                <td><span class="badge ${v.status == 'Entregue' ? 'ok' : 'pend'}">${v.status}</span></td>
                                <td><button class="btn-sm" onclick="confirmar(this)">Confirmar</button></td>
                            </tr>
                        </c:forEach>
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

    </main>
</div>

<script src="${pageContext.request.contextPath}/views_admin/admin_page/admin.js"></script>
</body>
</html>