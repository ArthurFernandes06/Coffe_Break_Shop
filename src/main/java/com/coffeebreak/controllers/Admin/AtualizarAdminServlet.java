package com.coffeebreak.controllers.Admin;

import com.coffeebreak.models.Usuario.Usuario;
import com.coffeebreak.models.Usuario.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/atualizar")
public class AtualizarAdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        if (sessao == null || sessao.getAttribute("adminLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/loginAdmin");
            return;
        }

        Usuario adminLogado = (Usuario) sessao.getAttribute("adminLogado");

        String nome     = request.getParameter("nome");
        String email    = request.getParameter("email");
        String endereco = request.getParameter("endereco");
        String senha    = request.getParameter("senha");

        if (nome != null) nome = nome.trim();
        if (email != null) email = email.trim();
        if (endereco != null) endereco = endereco.trim();
        if (senha != null) senha = senha.trim();

        if (nome     == null || nome.isBlank())     nome     = adminLogado.getNome();
        if (email    == null || email.isBlank())    email    = adminLogado.getEmail();
        if (endereco == null || endereco.isBlank()) endereco = adminLogado.getEndereco();
        if (senha    == null || senha.isBlank())    senha    = adminLogado.getSenha();

        if (!email.contains("@")) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?erro=emailInvalido");
            return;
        }

        adminLogado.setTipo(true);
        adminLogado.setNome(nome);
        adminLogado.setEmail(email);
        adminLogado.setEndereco(endereco);
        adminLogado.setSenha(senha);

        UsuarioDAO dao = new UsuarioDAO();
        boolean atualizado = dao.atualizarUsuario(adminLogado);

        if (atualizado) {

            sessao.setAttribute("adminLogado", adminLogado);
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?sucesso=dadosAtualizados");
            return;
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?erro=atualizacao");
            return;
        }
    }
}
