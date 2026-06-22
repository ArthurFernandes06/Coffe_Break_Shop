package com.coffeebreak.controllers.Categoria;

import com.coffeebreak.models.Categoria.Categoria;
import com.coffeebreak.models.Categoria.CategoriaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@WebServlet("/admin/categoria/cadastrar")
public class CadastroCategoriaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("adminLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/loginAdmin");
            return;
        }

        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");

        if (nome == null || nome.isBlank()) {
            redirecionar(request, response, false, "Preencha o nome da categoria.");
            return;
        }

        Categoria categoria = new Categoria(nome.trim(), descricao == null ? null : descricao.trim());
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        boolean criada = categoriaDAO.criarCategoria(categoria);

        if (criada) {
            redirecionar(request, response, true, "Categoria cadastrada com sucesso!");
        } else {
            redirecionar(request, response, false, "Não foi possível cadastrar a categoria. Tente novamente.");
        }
    }

    private void redirecionar(HttpServletRequest request, HttpServletResponse response,
                              boolean sucesso, String mensagem) throws IOException {
        String chave = sucesso ? "sucesso" : "erro";
        String url = request.getContextPath() + "/admin/dashboard?secao=categorias&" + chave
                + "=" + URLEncoder.encode(mensagem, StandardCharsets.UTF_8);
        response.sendRedirect(url);
    }
}
