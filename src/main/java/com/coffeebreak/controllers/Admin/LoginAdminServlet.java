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

@WebServlet("/loginAdmin")
public class LoginAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views_admin/login_admin/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioDoBanco = usuarioDAO.obterPeloEmail(email);

        if (usuarioDoBanco != null &&
                usuarioDoBanco.getSenha().equals(senha) &&
                usuarioDoBanco.isTipo()) {

            HttpSession sessao = request.getSession();
            sessao.setAttribute("adminLogado", usuarioDoBanco);
            sessao.setAttribute("adminId", usuarioDoBanco.getId());

            response.sendRedirect(request.getContextPath() + "/admin/dashboard");

        } else {
            response.sendRedirect(request.getContextPath() + "/views_admin/login_admin/index.jsp");
        }
    }
}