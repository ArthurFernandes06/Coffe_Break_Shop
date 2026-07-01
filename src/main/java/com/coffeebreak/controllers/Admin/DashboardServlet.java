package com.coffeebreak.controllers.Admin;

import com.coffeebreak.models.Categoria.Categoria;
import com.coffeebreak.models.Categoria.CategoriaDAO;
import com.coffeebreak.models.Produto.Produto;
import com.coffeebreak.models.Produto.ProdutoDAO;
import com.coffeebreak.models.Usuario.Usuario;
import com.coffeebreak.models.Usuario.UsuarioDAO;
import com.coffeebreak.models.Venda.Venda;
import com.coffeebreak.models.Venda.VendaDAO;
import com.coffeebreak.models.vendaproduto.VendaProduto;
import com.coffeebreak.models.vendaproduto.VendaProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/loginAdmin");
            return;
        }
        VendaDAO vendaDAO = new VendaDAO();
        VendaProdutoDAO vendaProdutoDAO = new VendaProdutoDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        request.setAttribute("totalHoje", vendaDAO.contarVendasHoje());
        request.setAttribute("receitaMes", vendaDAO.calcularReceitaMes());
        request.setAttribute("totalProdutos", produtoDAO.contarTodos());
        request.setAttribute("totalUsuarios", usuarioDAO.contarTodos());
        request.setAttribute("pedidosRecentes", vendaDAO.listarRecentesComUsuario(5));

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Produto> produtos = produtoDAO.obterTodos();
        List<Categoria> categorias = categoriaDAO.obterTodos();
        List<Usuario> usuarios = usuarioDAO.obterTodos();
        List<Venda> todasVendas = vendaDAO.obterTodas();
        Map<Integer, List<VendaProduto>> produtosPorVenda = new HashMap<>();

        for (Venda venda : todasVendas) {
            produtosPorVenda.put(venda.getId(), vendaProdutoDAO.obterProdutosPorVenda(venda.getId()));
        }

        request.setAttribute("produtos", produtos);
        request.setAttribute("categorias", categorias);
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("todasVendas", todasVendas);
        request.setAttribute("produtosPorVenda", produtosPorVenda);

        request.getRequestDispatcher("/WEB-INF/view/admin_page/admin.jsp").forward(request, response);
    }
}
