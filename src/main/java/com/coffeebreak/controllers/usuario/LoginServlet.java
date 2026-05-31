package com.coffeebreak.controllers.usuario;

import com.coffeebreak.models.Usuario.Usuario;
import com.coffeebreak.models.Usuario.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioDoBanco = usuarioDAO.obterPeloEmail(email);
        if (usuarioDoBanco != null && usuarioDoBanco.getSenha().equals(senha)) {
            HttpSession sessao = request.getSession();
            sessao.setAttribute("usuarioLogado", usuarioDoBanco);
            sessao.setAttribute("usuarioId", usuarioDoBanco.getId());
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp?erro=1");
        }
    }
}