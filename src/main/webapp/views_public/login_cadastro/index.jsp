<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <link rel="shortcut icon" href="../imgs/xicara-fav-icon.png" type="image/png">
    <title>Coffe Break Shop — Entrar / Cadastrar</title>
</head>
<body>

    <!-- HEADER -->
    <header>
        <a href="../pagina_inicial/index.html">
            <img id="img_logo" src="../imgs/pagina_inicial/header/xicara-logo.png" alt="Icone logo Coffe Break">
        </a>
        <nav>
            <a href="../pagina_inicial/index.html#produtos">Produtos</a>
            <a href="../pagina_inicial/index.html#categorias">Categorias</a>
            <a href="../pagina_inicial/index.html#ofertas">Ofertas</a>
            <a href="../pagina_inicial/index.html#sobre">Sobre Nós</a>
        </nav>
        <div id="div_imgs_menu">
            <img class="img_header" src="../imgs/pagina_inicial/header/carrinho.png" alt="Icone da Sacola">
            <img class="img_header" src="../imgs/pagina_inicial/header/user.png" alt="Icone User">
        </div>
    </header>

    <!-- MAIN -->
    <main>
        <div id="auth-wrapper">

            <!-- Card container -->
            <div id="auth-card">

                <!-- Logo / título do card -->
                <div id="card-logo">
                    <img src="../imgs/pagina_inicial/header/xicara-logo.png" alt="Logo Coffe Break">
                    <h1>Coffe Break Shop</h1>
                </div>

                <!-- Abas -->
                <div id="tabs">
                    <button class="tab-btn active" id="tab-login" onclick="switchTab('login')">Entrar</button>
                    <button class="tab-btn" id="tab-cadastro" onclick="switchTab('cadastro')">Cadastrar</button>
                </div>

                <!-- ========== FORMULÁRIO LOGIN ========== -->
                <form id="form-login" class="auth-form" action="#" method="POST" novalidate>

                    <div class="input-group">
                        <label for="login-email">E-mail</label>
                        <input type="email" id="login-email" name="email"
                               placeholder="seu@email.com" required autocomplete="email">
                    </div>

                    <div class="input-group">
                        <label for="login-senha">Senha</label>
                        <div class="input-with-icon">
                            <input type="password" id="login-senha" name="senha"
                                   placeholder="••••••••" required autocomplete="current-password">
                            <button type="button" class="toggle-pw" onclick="togglePw('login-senha', this)" aria-label="Mostrar/ocultar senha">👁</button>
                        </div>
                    </div>


                    <button type="submit" class="btn-primary">Entrar</button>

                    <p class="form-footer">
                        Ainda não tem conta?
                        <button type="button" class="link-btn" onclick="switchTab('cadastro')">Cadastre-se</button>
                    </p>
                </form>

                <!-- ========== FORMULÁRIO CADASTRO ========== -->
                <form id="form-cadastro" class="auth-form hidden" action="#" method="POST" novalidate>

                    <div class="input-group">
                        <label for="cad-username">Nome de usuário</label>
                        <input type="text" id="cad-username" name="username"
                               placeholder="seu_usuario" required autocomplete="username">
                    </div>

                    <div class="input-group">
                        <label for="cad-email">E-mail</label>
                        <input type="email" id="cad-email" name="email"
                               placeholder="seu@email.com" required autocomplete="email">
                    </div>

                    <div class="input-group">
                        <label for="cad-senha">Senha</label>
                        <div class="input-with-icon">
                            <input type="password" id="cad-senha" name="senha"
                                   placeholder="••••••••" required autocomplete="new-password">
                            <button type="button" class="toggle-pw" onclick="togglePw('cad-senha', this)" aria-label="Mostrar/ocultar senha">👁</button>
                        </div>
                    </div>

                    <!-- Endereço -->
                    <fieldset id="fieldset-endereco">
                        <legend>Endereço</legend>

                        <div class="input-group">
                            <label for="cad-rua">Rua</label>
                            <input type="text" id="cad-rua" name="rua"
                                   placeholder="Ex: Rua das Flores" required>
                        </div>

                        <div class="input-row">
                            <div class="input-group">
                                <label for="cad-numero">Número</label>
                                <input type="text" id="cad-numero" name="numero"
                                       placeholder="Ex: 123" required>
                            </div>
                            <div class="input-group">
                                <label for="cad-complemento">Complemento</label>
                                <input type="text" id="cad-complemento" name="complemento"
                                       placeholder="Ex: Apto 4B">
                            </div>
                        </div>
                    </fieldset>

                    <button type="submit" class="btn-primary">Criar conta</button>

                    <p class="form-footer">
                        Já tem uma conta?
                        <button type="button" class="link-btn" onclick="switchTab('login')">Entre aqui</button>
                    </p>
                </form>

            </div><!-- /auth-card -->
        </div><!-- /auth-wrapper -->
    </main>

    <script src="script.js"></script>
</body>
</html>
