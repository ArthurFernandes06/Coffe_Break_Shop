package com.coffeebreak.controllers.Admin;

import com.coffeebreak.models.Produto.Produto;
import com.coffeebreak.models.Produto.ProdutoDAO;
import com.coffeebreak.models.Usuario.Usuario;
import com.coffeebreak.models.Usuario.UsuarioDAO;
import com.coffeebreak.models.Venda.Venda;
import com.coffeebreak.models.Venda.VendaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

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
        ProdutoDAO produtoDAO = new ProdutoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        int totalHoje = vendaDAO.contarVendasHoje();
        double receitaMes = vendaDAO.calcularReceitaMes();
        List<Venda> pedidosRecentes = vendaDAO.listarRecentesComUsuario(5);
        List<Produto> listaProdutos = produtoDAO.obterTodos();
        List<Usuario> listaUsuarios = usuarioDAO.obterTodos();
        List<Venda> todasVendas = vendaDAO.obterTodas();

        request.setAttribute("totalHoje", totalHoje);
        request.setAttribute("receitaMes", receitaMes);
        request.setAttribute("pedidosRecentes", pedidosRecentes);
        request.setAttribute("produtos", listaProdutos);
        request.setAttribute("usuarios", listaUsuarios);
        request.setAttribute("todasVendas", todasVendas);
        request.setAttribute("totalProdutos", listaProdutos.size());
        request.setAttribute("totalUsuarios", listaUsuarios.size());

        request.getRequestDispatcher("/WEB-INF/view/admin_page/admin.jsp").forward(request, response);
    }
}