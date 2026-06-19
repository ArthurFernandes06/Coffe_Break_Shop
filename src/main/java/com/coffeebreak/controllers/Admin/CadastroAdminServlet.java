package com.coffeebreak.controllers.Admin;

import com.coffeebreak.models.Usuario.Usuario;
import com.coffeebreak.models.Usuario.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cadastroAdmin")
public class CadastroAdminServlet extends HttpServlet {

    private static final String TOKEN_SECRETO = "CAFE_SECRETO_123";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("BATEU NO SERVLET DE CADASTRO ADMIN!");
        // Exibe a tela de cadastro do admin
        request.getRequestDispatcher("/views_admin/cadastro_admin/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("username");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String tokenDigitado = request.getParameter("token_seguranca");

        if (tokenDigitado == null || !tokenDigitado.equals(TOKEN_SECRETO)) {
            response.sendRedirect(request.getContextPath() + "/cadastroAdmin");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido");
            return;
        }

        Usuario novoAdmin = new Usuario(nome, email, "Sede Corporativa", senha, true);
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        usuarioDAO.criarUsuario(novoAdmin);

        response.sendRedirect(request.getContextPath() + "/loginAdmin");
    }
}