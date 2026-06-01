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

@WebServlet("/usuario/remover")
public class RemoverUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

        UsuarioDAO dao = new UsuarioDAO();
        boolean removido = dao.removerUsuario(usuarioLogado.getId());

        if (removido) {
           
            sessao.invalidate();
            response.sendRedirect(request.getContextPath() + "/login?conta=removida");
        } else {
            request.setAttribute("erro", "Não foi possível remover a conta. Tente novamente.");
            request.getRequestDispatcher("/views_private/usuario/pagina_usuario.jsp")
                    .forward(request, response);
        }
    }
}