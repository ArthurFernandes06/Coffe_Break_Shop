<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views_admin/login_admin/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/views_public/imgs/xicara-fav-icon.png" type="image/png">
    <title>Coffee Break — Acesso Admin</title>
</head>
<body>

    <header class="header-admin">
        <div class="logo-container">
            <a href="${pageContext.request.contextPath}/views_public/pagina_inicial/index.html">
                <img id="img_logo" src="${pageContext.request.contextPath}/views_public/imgs/pagina_inicial/header/xicara-logo.png" alt="Logo Coffee Break">
            </a>
            <span class="admin-badge">PORTAL ADMINISTRATIVO</span>
        </div>
    </header>

    <main>
        <div id="auth-wrapper">
            <div id="auth-card" class="card-admin">

                <div id="card-logo">
                    <h1>Acesso Restrito</h1>
                    <p class="subtitle">Insira suas credenciais corporativas</p>
                </div>

                <form id="form-admin-login" class="auth-form" action="${pageContext.request.contextPath}/loginAdmin" method="POST">

                    <div class="input-group">
                        <label for="login-email">E-mail Corporativo</label>
                        <input type="email" id="login-email" name="email" placeholder="admin@coffeebreak.com" required>
                    </div>

                    <div class="input-group">
                        <label for="login-senha">Senha</label>
                        <div class="input-with-icon">
                            <input type="password" id="login-senha" name="senha" placeholder="••••••••" required>
                            <button type="button" class="toggle-pw" onclick="togglePw('login-senha', this)">👁</button>
                        </div>
                    </div>

                    <button type="submit" class="btn-primary btn-admin">Entrar no Sistema</button>

                    <div style="text-align: center; margin-top: 15px;">
                        <a href="${pageContext.request.contextPath}/views_admin/cadastro_admin/index.jsp"
                           style="color: var(--cor0); font-size: 13px; text-decoration: underline; opacity: 0.7;">
                           Novo colaborador? Cadastre-se aqui
                        </a>
                    </div>

                </form>

            </div>
        </div>
    </main>

    <script src="${pageContext.request.contextPath}/views_admin/login_admin/script.js"></script>
</body>
</html>