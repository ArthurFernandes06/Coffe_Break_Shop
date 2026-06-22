package com.coffeebreak.controllers.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/logout")
public class LogoffAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Busca a sessão atual sem criar uma nova sessão caso o admin já esteja deslogado.
        HttpSession sessao = request.getSession(false);

        // Encerra a sessão para remover os dados do admin logado do servidor.
        if (sessao != null) {
            sessao.invalidate();
        }

        // Depois do logout, envia o admin de volta para a tela de login administrativo.
        response.sendRedirect(request.getContextPath() + "/loginAdmin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Permite que o logout funcione tanto por GET quanto por POST, reaproveitando a mesma lógica.
        doGet(request, response);
    }
}
