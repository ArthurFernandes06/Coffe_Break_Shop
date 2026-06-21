package com.coffeebreak.controllers.Admin;

import com.coffeebreak.models.Categoria.Categoria;
import com.coffeebreak.models.Categoria.CategoriaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/categorias")
public class CategoriaServlet extends HttpServlet {

    private boolean naoEstaLogado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null || session.getAttribute("adminLogado") == null;
    }

    // READ — lista todas as categorias (GET /admin/categorias)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (naoEstaLogado(request)) {
            response.sendRedirect(request.getContextPath() + "/loginAdmin");
            return;
        }

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Categoria> categorias = categoriaDAO.obterTodos();

        request.setAttribute("categorias", categorias);
        request.getRequestDispatcher("/WEB-INF/view/admin_page/admin.jsp").forward(request, response);
    }

    // CREATE / UPDATE / DELETE — diferenciados pelo parâmetro "action"
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (naoEstaLogado(request)) {
            response.sendRedirect(request.getContextPath() + "/loginAdmin");
            return;
        }

        String action = request.getParameter("action");
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        if ("criar".equals(action)) {
            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");

            Categoria novaCategoria = new Categoria(nome, descricao);
            categoriaDAO.criarCategoria(novaCategoria);

        } else if ("atualizar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");

            Categoria categoria = new Categoria(id, nome, descricao);
            categoriaDAO.atualizarCategoria(categoria);

        } else if ("remover".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            categoriaDAO.removerCategoria(id);
        }

        response.sendRedirect(request.getContextPath() + "/admin/categorias");
    }
}
