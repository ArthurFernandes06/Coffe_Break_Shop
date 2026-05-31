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

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/views_public/login_cadastro/index.jsp").forward(request, response);
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String nome = request.getParameter("username");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String endereco = request.getParameter("rua") + "," + request.getParameter("numero") + "," + request.getParameter("complemento");
        
        Usuario novoUser = new Usuario(nome,email, endereco, senha, false);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.criarUsuario(novoUser);
        
            
        response.sendRedirect(request.getContextPath() + "/login");
       
    }
}