package com.coffeebreak.controllers.Categoria;

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


@WebServlet("/admin/categoria/remover")
public class RemoverCategoriaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("adminLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/loginAdmin");
            return;
        }

        String idStr = request.getParameter("id");
        String chave;
        String mensagem;

        try {
            int id = Integer.parseInt(idStr);
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            boolean removida = categoriaDAO.removerCategoria(id);

            if (removida) {
                chave = "sucesso";
                mensagem = "Categoria removida com sucesso!";
            } else {
                chave = "erro";
                mensagem = "Não foi possível remover a categoria. Ela pode estar vinculada a produtos existentes.";
            }
        } catch (NumberFormatException e) {
            chave = "erro";
            mensagem = "Categoria inválida.";
        }

        String url = request.getContextPath() + "/admin/dashboard?secao=categorias&" + chave
                + "=" + URLEncoder.encode(mensagem, StandardCharsets.UTF_8);
        response.sendRedirect(url);
    }
}
