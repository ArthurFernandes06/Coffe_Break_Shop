package com.coffeebreak.controllers.Carrinho;

import com.coffeebreak.models.Carrinho.Carrinho;
import com.coffeebreak.models.Carrinho.CarrinhoDAO;
import com.coffeebreak.models.Usuario.Usuario;
import com.coffeebreak.models.Venda.Venda;
import com.coffeebreak.models.Venda.VendaDAO;
import com.coffeebreak.models.carrinhoproduto.CarrinhoProduto;
import com.coffeebreak.models.carrinhoproduto.CarrinhoProdutoDAO;
import com.coffeebreak.models.vendaproduto.VendaProduto;
import com.coffeebreak.models.vendaproduto.VendaProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        Carrinho carrinho = carrinhoDAO.obterOuCriarCarrinhoAtivo(usuarioLogado);

        List<CarrinhoProduto> itens = new ArrayList<>();
        double total = 0.0;

        if (carrinho != null) {
            CarrinhoProdutoDAO carrinhoProdutoDAO = new CarrinhoProdutoDAO();
            itens = carrinhoProdutoDAO.obterProdutosDoCarrinho(carrinho.getId());

            for (CarrinhoProduto item : itens) {
                total += item.getProduto().getPreco() * item.getQuantidade();
            }
        }

        VendaDAO vendaDAO = new VendaDAO();
        VendaProdutoDAO vendaProdutoDAO = new VendaProdutoDAO();
        List<Venda> vendas = vendaDAO.obterPorUsuario(usuarioLogado.getId());
        Map<Integer, List<VendaProduto>> produtosPorVenda = new HashMap<>();

        for (Venda venda : vendas) {
            produtosPorVenda.put(venda.getId(), vendaProdutoDAO.obterProdutosPorVenda(venda.getId()));
        }

        request.setAttribute("activeSection", "carrinho");
        request.setAttribute("carrinho", carrinho);
        request.setAttribute("itensCarrinho", itens);
        request.setAttribute("totalCarrinho", total);
        request.setAttribute("vendasUsuario", vendas);
        request.setAttribute("produtosPorVenda", produtosPorVenda);
        request.getRequestDispatcher("/views_public/pagina_do_usuario/index.jsp").forward(request, response);
    }
}
