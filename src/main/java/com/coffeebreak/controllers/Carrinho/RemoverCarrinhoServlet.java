package com.coffeebreak.controllers.Carrinho;

import com.coffeebreak.models.Carrinho.Carrinho;
import com.coffeebreak.models.Carrinho.CarrinhoDAO;
import com.coffeebreak.models.Usuario.Usuario;
import com.coffeebreak.models.carrinhoproduto.CarrinhoProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/carrinho/remover")
public class RemoverCarrinhoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int produtoId = Integer.parseInt(request.getParameter("produto_id"));
            Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
            Carrinho carrinho = new CarrinhoDAO().obterCarrinhoAtivo(usuarioLogado.getId());

            if (carrinho == null) {
                redirecionar(request, response, false, "Carrinho não encontrado.");
                return;
            }

            boolean removido = new CarrinhoProdutoDAO().removerProdutoDoCarrinho(carrinho.getId(), produtoId);
            if (removido) {
                redirecionar(request, response, true, "Produto removido do carrinho.");
            } else {
                redirecionar(request, response, false, "Não foi possível remover o produto.");
            }
        } catch (NumberFormatException e) {
            redirecionar(request, response, false, "Produto inválido.");
        }
    }

    private void redirecionar(HttpServletRequest request, HttpServletResponse response,
                              boolean sucesso, String mensagem) throws IOException {
        String chave = sucesso ? "sucesso" : "erro";
        String url = request.getContextPath() + "/carrinho?" + chave
                + "=" + URLEncoder.encode(mensagem, StandardCharsets.UTF_8);
        response.sendRedirect(url);
    }
}
