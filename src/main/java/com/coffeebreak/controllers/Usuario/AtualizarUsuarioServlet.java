package com.coffeebreak.controllers.Usuario;

import com.coffeebreak.models.Usuario.Usuario;
import com.coffeebreak.models.Usuario.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/usuario/atualizar")
public class AtualizarUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        // Proteção: redireciona se não estiver logado
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

        String nome     = request.getParameter("nome");
        String email    = request.getParameter("email");
        String endereco = request.getParameter("endereco");
        String senha    = request.getParameter("senha");


        if (nome     == null || nome.isBlank())     nome     = usuarioLogado.getNome();
        if (email    == null || email.isBlank())    email    = usuarioLogado.getEmail();
        if (endereco == null || endereco.isBlank()) endereco = usuarioLogado.getEndereco();
        if (senha    == null || senha.isBlank())    senha    = usuarioLogado.getSenha();

        if (!email.contains("@")) {
            request.setAttribute("erro", "E-mail inválido. As alterações não foram salvas.");
            request.getRequestDispatcher("/views_private/usuario/pagina_usuario.jsp")
                    .forward(request, response);
            return;
        }

        usuarioLogado.setNome(nome);
        usuarioLogado.setEmail(email);
        usuarioLogado.setEndereco(endereco);
        usuarioLogado.setSenha(senha);

        UsuarioDAO dao = new UsuarioDAO();
        boolean atualizado = dao.atualizarUsuario(usuarioLogado);

        if (atualizado) {

            sessao.setAttribute("usuarioLogado", usuarioLogado);
            request.setAttribute("sucesso", "Dados atualizados com sucesso!");
        } else {
            request.setAttribute("erro", "Não foi possível atualizar os dados. Tente novamente.");
        }

        request.getRequestDispatcher("/views_private/usuario/pagina_usuario.jsp")
                .forward(request, response);
    }
}