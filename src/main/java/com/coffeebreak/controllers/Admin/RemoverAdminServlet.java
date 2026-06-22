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

@WebServlet("/admin/remover")
public class RemoverAdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        if (sessao == null || sessao.getAttribute("adminLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/loginAdmin");
            return;
        }

        Usuario adminLogado = (Usuario) sessao.getAttribute("adminLogado");

        UsuarioDAO dao = new UsuarioDAO();
        boolean removido = dao.removerUsuario(adminLogado.getId());

        if (removido) {

            sessao.invalidate();
            response.sendRedirect(request.getContextPath() + "/loginAdmin?conta=removida");
            return;
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?erro=remocao");
            return;
        }
    }
}
