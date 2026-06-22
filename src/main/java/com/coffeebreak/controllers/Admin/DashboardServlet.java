package com.coffeebreak.controllers.Admin;

import com.coffeebreak.models.Categoria.Categoria;
import com.coffeebreak.models.Categoria.CategoriaDAO;
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
        // Buscando os dados
        int totalHoje = vendaDAO.contarVendasHoje();
        double receitaMes = vendaDAO.calcularReceitaMes();
        List<Venda> pedidosRecentes = vendaDAO.listarRecentesComUsuario(5);

        // Enviando para o JSP
        // Dentro do seu DashboardServlet.java
        request.setAttribute("totalHoje", vendaDAO.contarVendasHoje());
        request.setAttribute("receitaMes", vendaDAO.calcularReceitaMes());
        request.setAttribute("totalProdutos", produtoDAO.contarTodos()); // Apenas o número
        request.setAttribute("totalUsuarios", usuarioDAO.contarTodos()); // Apenas o número
        request.setAttribute("pedidosRecentes", vendaDAO.listarRecentesComUsuario(5));

        // Dados para a aba de Produtos (listagem + formulário de cadastro/edição)
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Produto> produtos = produtoDAO.obterTodos();
        List<Categoria> categorias = categoriaDAO.obterTodos();
        request.setAttribute("produtos", produtos);
        request.setAttribute("categorias", categorias);

        request.getRequestDispatcher("/WEB-INF/view/admin_page/admin.jsp").forward(request, response);
    }
}