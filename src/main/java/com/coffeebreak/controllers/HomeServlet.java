package com.coffeebreak.controllers;

import com.coffeebreak.models.Produto.Produto;
import com.coffeebreak.models.Produto.ProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"", "/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> produtosEmEstoque = produtoDAO.obterEmEstoque();

        request.setAttribute("produtosEmEstoque", produtosEmEstoque);
        request.getRequestDispatcher("/views_public/pagina_inicial/index.jsp").forward(request, response);
    }
}
