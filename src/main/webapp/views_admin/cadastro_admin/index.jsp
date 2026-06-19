<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views_admin/cadastro_admin/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/views_public/imgs/xicara-fav-icon.png" type="image/png">
    <title>Coffee Break — Portal Admin</title>
</head>
<body>

    <header class="header-admin">
        <div class="logo-container">
            <img id="img_logo" src="${pageContext.request.contextPath}/views_public/imgs/pagina_inicial/header/xicara-logo.png" alt="Logo Coffee Break">
            <span class="admin-badge">PORTAL ADMINISTRATIVO</span>
        </div>
    </header>

    <main>
        <div id="auth-wrapper">
            <div id="auth-card" class="card-admin">

                <div id="card-logo">
                    <h1>Cadastrar Novo Admin</h1>
                    <p class="subtitle">Acesso restrito a colaboradores</p>
                </div>

                <form id="form-admin" class="auth-form" action="${pageContext.request.contextPath}/cadastroAdmin" method="POST">

                    <div class="input-group">
                        <label for="admin-nome">Nome Completo</label>
                        <input type="text" id="admin-nome" name="username" placeholder="Nome do colaborador" required>
                    </div>

                    <div class="input-group">
                        <label for="admin-email">E-mail Corporativo</label>
                        <input type="email" id="admin-email" name="email" placeholder="admin@coffeebreak.com" required>
                    </div>

                    <div class="input-group">
                        <label for="admin-senha">Senha de Acesso</label>
                        <div class="input-with-icon">
                            <input type="password" id="admin-senha" name="senha" placeholder="••••••••" required>
                            <button type="button" class="toggle-pw" onclick="togglePw('admin-senha', this)">👁</button>
                        </div>
                    </div>

                    <div class="input-group token-group">
                        <label for="admin-token">Chave de Segurança (Token)</label>
                        <input type="password" id="admin-token" name="token_seguranca" placeholder="Insira o código fornecido pela gerência" required>
                    </div>

                    <button type="submit" class="btn-primary btn-admin">Registrar Administrador</button>

                </form>

            </div>
        </div>
    </main>

    <script src="${pageContext.request.contextPath}/views_admin/cadastro_admin/script.js"></script>
</body>
</html>