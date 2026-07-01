package com.coffeebreak.controllers.Carrinho;

import com.coffeebreak.models.Carrinho.Carrinho;
import com.coffeebreak.models.Carrinho.CarrinhoDAO;
import com.coffeebreak.models.Produto.Produto;
import com.coffeebreak.models.Produto.ProdutoDAO;
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

@WebServlet("/carrinho/atualizar")
public class AtualizarCarrinhoServlet extends HttpServlet {

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
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));

            Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
            Carrinho carrinho = new CarrinhoDAO().obterCarrinhoAtivo(usuarioLogado.getId());
            Produto produto = new ProdutoDAO().obterPeloId(produtoId);

            if (carrinho == null || produto == null) {
                redirecionar(request, response, false, "Item do carrinho não encontrado.");
                return;
            }

            CarrinhoProdutoDAO carrinhoProdutoDAO = new CarrinhoProdutoDAO();
            boolean atualizado;

            if (quantidade <= 0) {
                atualizado = carrinhoProdutoDAO.removerProdutoDoCarrinho(carrinho.getId(), produtoId);
            } else {
                if (quantidade > produto.getQuantidade()) {
                    quantidade = produto.getQuantidade();
                }
                atualizado = carrinhoProdutoDAO.atualizarQuantidade(carrinho.getId(), produtoId, quantidade);
            }

            if (atualizado) {
                redirecionar(request, response, true, "Carrinho atualizado.");
            } else {
                redirecionar(request, response, false, "Não foi possível atualizar o carrinho.");
            }
        } catch (NumberFormatException e) {
            redirecionar(request, response, false, "Dados inválidos.");
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
