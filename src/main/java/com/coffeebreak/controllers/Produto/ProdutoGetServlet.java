package com.coffeebreak.controllers.Produto;

import com.coffeebreak.models.Produto.Produto;
import com.coffeebreak.models.Produto.ProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/produto")
public class ProdutoGetServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        //Pega o ID enviado na URL no formato de query (ex: /produto?id=3)
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int produtoId = Integer.parseInt(idParam);
                
                
                ProdutoDAO dao = new ProdutoDAO();
                Produto produto = dao.obterPeloId(produtoId);
                
                if (produto != null) {
                    //Guarda o produto na requisição com o nome "produto"
                    request.setAttribute("produto", produto);
                    request.getRequestDispatcher("/views_public/pagina_produto_unica/index.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // ID inválido 
            }
        }
        
        // Se não achar o produto, manda erro 404
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}