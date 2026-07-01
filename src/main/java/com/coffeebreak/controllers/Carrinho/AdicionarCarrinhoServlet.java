package com.coffeebreak.controllers.Carrinho;

import com.coffeebreak.models.Carrinho.Carrinho;
import com.coffeebreak.models.Carrinho.CarrinhoDAO;
import com.coffeebreak.models.Produto.Produto;
import com.coffeebreak.models.Produto.ProdutoDAO;
import com.coffeebreak.models.Usuario.Usuario;
import com.coffeebreak.models.carrinhoproduto.CarrinhoProduto;
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

@WebServlet("/carrinho/adicionar")
public class AdicionarCarrinhoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String produtoIdStr = request.getParameter("produto_id");
        String quantidadeStr = request.getParameter("quantidade");

        try {
            int produtoId = Integer.parseInt(produtoIdStr);
            int quantidade = (quantidadeStr == null || quantidadeStr.isBlank())
                    ? 1 : Integer.parseInt(quantidadeStr);

            if (quantidade <= 0) {
                redirecionar(request, response, false, "Quantidade inválida.");
                return;
            }

            ProdutoDAO produtoDAO = new ProdutoDAO();
            Produto produto = produtoDAO.obterPeloId(produtoId);
            if (produto == null || produto.getQuantidade() <= 0) {
                redirecionar(request, response, false, "Produto indisponível.");
                return;
            }

            Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
            CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
            Carrinho carrinho = carrinhoDAO.obterOuCriarCarrinhoAtivo(usuarioLogado);

            if (carrinho == null) {
                redirecionar(request, response, false, "Não foi possível criar o carrinho.");
                return;
            }

            CarrinhoProdutoDAO carrinhoProdutoDAO = new CarrinhoProdutoDAO();
            CarrinhoProduto itemExistente = carrinhoProdutoDAO.obterPorCarrinhoEProduto(carrinho.getId(), produtoId);

            int novaQuantidade = quantidade;
            boolean salvo;
            if (itemExistente != null) {
                novaQuantidade += itemExistente.getQuantidade();
                if (novaQuantidade > produto.getQuantidade()) {
                    novaQuantidade = produto.getQuantidade();
                }
                salvo = carrinhoProdutoDAO.atualizarQuantidade(carrinho.getId(), produtoId, novaQuantidade);
            } else {
                if (novaQuantidade > produto.getQuantidade()) {
                    novaQuantidade = produto.getQuantidade();
                }
                salvo = carrinhoProdutoDAO.adicionarProdutoNoCarrinho(new CarrinhoProduto(carrinho, produto, novaQuantidade));
            }

            if (salvo) {
                redirecionar(request, response, true, "Produto adicionado ao carrinho.");
            } else {
                redirecionar(request, response, false, "Não foi possível adicionar o produto ao carrinho.");
            }
        } catch (NumberFormatException e) {
            redirecionar(request, response, false, "Produto ou quantidade inválidos.");
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
