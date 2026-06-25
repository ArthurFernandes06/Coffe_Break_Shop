package com.coffeebreak.controllers;

//Imports de produtos
import com.coffeebreak.models.Produto.Produto;
import com.coffeebreak.models.Produto.ProdutoDAO;

//Imports de categorias
import com.coffeebreak.models.Categoria.Categoria;
import com.coffeebreak.models.Categoria.CategoriaDAO;

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

        //Fazendo a listagem de produtos
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> produtosEmEstoque = produtoDAO.obterEmEstoque();

        //Fazendo a listagem de categorias
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Categoria> categoriasCadastradas = categoriaDAO.obterTodos();

        request.setAttribute("categoriasCadastradas", categoriasCadastradas);
        request.setAttribute("produtosEmEstoque", produtosEmEstoque);
        request.getRequestDispatcher("/views_public/pagina_inicial/index.jsp").forward(request, response);
    }
}
