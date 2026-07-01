package com.coffeebreak.controllers.Admin;

import com.coffeebreak.models.Venda.VendaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/admin/venda/remover")
public class RemoverVendaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/loginAdmin");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            VendaDAO vendaDAO = new VendaDAO();

            if (vendaDAO.removerVenda(id)) {
                redirecionar(response, request, "sucesso", "Pedido removido.");
            } else {
                redirecionar(response, request, "erro", "Não foi possível remover o pedido.");
            }
        } catch (NumberFormatException ex) {
            redirecionar(response, request, "erro", "Pedido inválido.");
        }
    }

    private void redirecionar(HttpServletResponse response, HttpServletRequest request, String tipo, String mensagem)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/admin/dashboard?secao=pedidos&" + tipo + "="
                + URLEncoder.encode(mensagem, StandardCharsets.UTF_8));
    }
}
