<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<!-- HEADER -->
<header>
    <div id="header-logo">
        <img src="${pageContext.request.contextPath}/../imgs/pagina_inicial/header/xicara-logo.png" alt="Logo Coffe Break">
        <span>Coffe Break <strong>Admin</strong></span>
    </div>
    <span id="admin-name">👤 Administrador</span>
</header>

<div id="layout">

    <!-- SIDEBAR -->
    <nav id="sidebar">
        <button class="nav-btn active" onclick="showPage('dashboard', this)">📊 Dashboard</button>
        <button class="nav-btn" onclick="showPage('produtos', this)">☕ Produtos</button>
        <button class="nav-btn" onclick="showPage('pedidos', this)">🛒 Pedidos</button>
        <button class="nav-btn" onclick="showPage('usuarios', this)">👥 Usuários</button>
    </nav>

    <!-- CONTEÚDO -->
    <main id="content">

        <!-- DASHBOARD -->
        <section id="page-dashboard" class="page active">
            <h2>Dashboard</h2>

            <div class="cards-row">
                <div class="info-card">
                    <span class="card-label">Pedidos hoje</span>
                    <span class="card-value">34</span>
                </div>
                <div class="info-card">
                    <span class="card-label">Receita do mês</span>
                    <span class="card-value">R$ 4.820</span>
                </div>
                <div class="info-card">
                    <span class="card-label">Produtos</span>
                    <span class="card-value">18</span>
                </div>
                <div class="info-card">
                    <span class="card-label">Usuários</span>
                    <span class="card-value">142</span>
                </div>
            </div>

            <div class="table-box">
                <h3>Pedidos recentes</h3>
                <table>
                    <thead>
                    <tr>
                        <th>Cliente</th>
                        <th>Total</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr><td>Ana Souza</td><td>R$ 42,00</td><td><span class="badge ok">Entregue</span></td></tr>
                    <tr><td>Bruno Lima</td><td>R$ 27,50</td><td><span class="badge pend">Pendente</span></td></tr>
                    <tr><td>Carla Melo</td><td>R$ 58,00</td><td><span class="badge ok">Entregue</span></td></tr>
                    <tr><td>Diego Rios</td><td>R$ 19,90</td><td><span class="badge cancel">Cancelado</span></td></tr>
                    </tbody>
                </table>
            </div>
        </section>

        <!-- PRODUTOS -->
        <section id="page-produtos" class="page">
            <h2>Produtos</h2>

            <div class="section-top">
                <input type="text" id="busca-produto" placeholder="Buscar produto..."
                       oninput="filtrar('busca-produto', 'tb-produtos', 0)">
                <button class="btn-primary" onclick="toggleForm('form-produto')">+ Novo produto</button>
            </div>

            <div id="form-produto" class="form-box hidden">
                <h3>Novo produto</h3>
                <div class="form-row">
                    <div class="input-group">
                        <label for="p-nome">Nome</label>
                        <input type="text" id="p-nome" placeholder="Ex: Cappuccino">
                    </div>
                    <div class="input-group">
                        <label for="p-preco">Preço (R$)</label>
                        <input type="number" id="p-preco" placeholder="0,00" step="0.01">
                    </div>
                    <div class="input-group">
                        <label for="p-cat">Categoria</label>
                        <select id="p-cat">
                            <option>Café</option>
                            <option>Café Gelado</option>
                            <option>Alimentação</option>
                            <option>Acessórios</option>
                        </select>
                    </div>
                </div>
                <div class="form-actions">
                    <button class="btn-secondary" onclick="toggleForm('form-produto')">Cancelar</button>
                    <button class="btn-primary" onclick="salvarProduto()">Salvar</button>
                </div>
            </div>

            <div class="table-box">
                <table id="tb-produtos">
                    <thead>
                    <tr><th>Nome</th><th>Categoria</th><th>Preço</th><th>Status</th></tr>
                    </thead>
                    <tbody>
                    <tr><td>Espresso Duplo</td><td>Café</td><td>R$ 9,90</td><td><span class="badge ok">Ativo</span></td></tr>
                    <tr><td>Cappuccino</td><td>Café</td><td>R$ 12,50</td><td><span class="badge ok">Ativo</span></td></tr>
                    <tr><td>Cold Brew</td><td>Café Gelado</td><td>R$ 14,00</td><td><span class="badge ok">Ativo</span></td></tr>
                    <tr><td>Bolo de Café</td><td>Alimentação</td><td>R$ 8,50</td><td><span class="badge pend">Baixo estoque</span></td></tr>
                    <tr><td>Xícara Artesanal</td><td>Acessórios</td><td>R$ 45,00</td><td><span class="badge cancel">Inativo</span></td></tr>
                    </tbody>
                </table>
            </div>
        </section>

        <!-- PEDIDOS -->
        <section id="page-pedidos" class="page">
            <h2>Pedidos</h2>

            <div class="section-top">
                <input type="text" id="busca-pedido" placeholder="Buscar cliente..."
                       oninput="filtrar('busca-pedido', 'tb-pedidos', 1)">
                <select id="filtro-status" onchange="filtrarStatus()">
                    <option value="">Todos os status</option>
                    <option value="Entregue">Entregue</option>
                    <option value="Pendente">Pendente</option>
                    <option value="Cancelado">Cancelado</option>
                </select>
            </div>

            <div class="table-box">
                <table id="tb-pedidos">
                    <thead>
                    <tr><th>ID</th><th>Cliente</th><th>Total</th><th>Status</th><th>Ação</th></tr>
                    </thead>
                    <tbody>
                    <tr><td>#1041</td><td>Ana Souza</td><td>R$ 19,80</td><td><span class="badge ok">Entregue</span></td><td>—</td></tr>
                    <tr><td>#1042</td><td>Bruno Lima</td><td>R$ 21,00</td><td><span class="badge pend">Pendente</span></td><td><button class="btn-sm" onclick="confirmar(this)">Confirmar</button></td></tr>
                    <tr><td>#1043</td><td>Carla Melo</td><td>R$ 56,00</td><td><span class="badge ok">Entregue</span></td><td>—</td></tr>
                    <tr><td>#1044</td><td>Diego Rios</td><td>R$ 13,00</td><td><span class="badge cancel">Cancelado</span></td><td>—</td></tr>
                    <tr><td>#1045</td><td>Eva Ferreira</td><td>R$ 57,00</td><td><span class="badge pend">Pendente</span></td><td><button class="btn-sm" onclick="confirmar(this)">Confirmar</button></td></tr>
                    </tbody>
                </table>
            </div>
        </section>

        <!-- USUÁRIOS -->
        <section id="page-usuarios" class="page">
            <h2>Usuários</h2>

            <div class="section-top">
                <input type="text" id="busca-usuario" placeholder="Buscar usuário..."
                       oninput="filtrar('busca-usuario', 'tb-usuarios', 0)">
            </div>

            <div class="table-box">
                <table id="tb-usuarios">
                    <thead>
                    <tr><th>Nome</th><th>E-mail</th><th>Tipo</th></tr>
                    </thead>
                    <tbody>
                    <tr><td>Ana Souza</td><td>ana@email.com</td><td><span class="badge ok">Cliente</span></td></tr>
                    <tr><td>Bruno Lima</td><td>bruno@email.com</td><td><span class="badge ok">Cliente</span></td></tr>
                    <tr><td>Carla Melo</td><td>carla@email.com</td><td><span class="badge ok">Cliente</span></td></tr>
                    <tr><td>Admin Principal</td><td>admin@coffebreak.com</td><td><span class="badge pend">Admin</span></td></tr>
                    <tr><td>Eva Ferreira</td><td>eva@email.com</td><td><span class="badge ok">Cliente</span></td></tr>
                    </tbody>
                </table>
            </div>
        </section>

    </main>
</div>

<script src="${pageContext.request.contextPath}/views_admin/admin_page//admin.js"></script>
</body>
</html>
