package com.coffeebreak.controllers.Produto;

import com.coffeebreak.models.Produto.ProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Servlet responsável por remover um produto existente.
 * Acesso restrito ao administrador logado (sessão "adminLogado").
 */
@WebServlet("/admin/produto/remover")
public class RemoverProdutoServlet extends HttpServlet {

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
            ProdutoDAO produtoDAO = new ProdutoDAO();
            boolean removido = produtoDAO.removerProduto(id);

            if (removido) {
                chave = "sucesso";
                mensagem = "Produto removido com sucesso!";
            } else {
                chave = "erro";
                mensagem = "Não foi possível remover o produto. Ele pode estar vinculado a pedidos existentes.";
            }
        } catch (NumberFormatException e) {
            chave = "erro";
            mensagem = "Produto inválido.";
        }

        String url = request.getContextPath() + "/admin/dashboard?secao=produtos&" + chave
                + "=" + URLEncoder.encode(mensagem, StandardCharsets.UTF_8);
        response.sendRedirect(url);
    }
}