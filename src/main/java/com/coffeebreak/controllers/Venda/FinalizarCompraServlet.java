package com.coffeebreak.controllers.Venda;

import com.coffeebreak.models.Carrinho.Carrinho;
import com.coffeebreak.models.Carrinho.CarrinhoDAO;
import com.coffeebreak.models.Usuario.Usuario;
import com.coffeebreak.models.Venda.Venda;
import com.coffeebreak.models.Venda.VendaDAO;
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
import java.util.List;

@WebServlet("/compra/finalizar")
public class FinalizarCompraServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        Carrinho carrinho = carrinhoDAO.obterCarrinhoAtivo(usuarioLogado.getId());

        if (carrinho == null) {
            redirecionarCarrinho(response, request, "erro", "Carrinho vazio.");
            return;
        }

        CarrinhoProdutoDAO carrinhoProdutoDAO = new CarrinhoProdutoDAO();
        List<CarrinhoProduto> itens = carrinhoProdutoDAO.obterProdutosDoCarrinho(carrinho.getId());

        if (itens.isEmpty()) {
            redirecionarCarrinho(response, request, "erro", "Carrinho vazio.");
            return;
        }

        VendaDAO vendaDAO = new VendaDAO();
        Venda venda = vendaDAO.finalizarCompra(carrinho, itens);

        if (venda == null) {
            redirecionarCarrinho(response, request, "erro", "Não foi possível finalizar a compra. Verifique o estoque dos produtos.");
            return;
        }

        String mensagem = "Compra finalizada com sucesso. Pedido #" + venda.getId() + " criado.";
        response.sendRedirect(request.getContextPath() + "/usuario?secao=pedidos&sucesso=" + codificar(mensagem));
    }

    private void redirecionarCarrinho(HttpServletResponse response, HttpServletRequest request, String tipo, String mensagem)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/carrinho?" + tipo + "=" + codificar(mensagem));
    }

    private String codificar(String valor) {
        return URLEncoder.encode(valor, StandardCharsets.UTF_8);
    }
}
