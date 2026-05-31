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
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.obterTodos();

        Usuario usuarioAutenticado = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                usuarioAutenticado = usuario;
                break;
            }
        }

        if (usuarioAutenticado != null) {
            HttpSession sessao = request.getSession();
            sessao.setAttribute("usuarioLogado", usuarioAutenticado);
            sessao.setAttribute("usuarioId", usuarioAutenticado.getId());
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp?erro=1");
        }
    }
}
