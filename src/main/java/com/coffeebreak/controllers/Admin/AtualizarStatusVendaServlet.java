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

@WebServlet("/admin/venda/atualizar-status")
public class AtualizarStatusVendaServlet extends HttpServlet {

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
            String status = request.getParameter("status");
            if (status != null) {
                status = status.toUpperCase();
            }

            if (!statusValido(status)) {
                redirecionar(response, request, "erro", "Status inválido.");
                return;
            }

            VendaDAO vendaDAO = new VendaDAO();
            if (vendaDAO.atualizarStatus(id, status)) {
                redirecionar(response, request, "sucesso", "Status do pedido atualizado.");
            } else {
                redirecionar(response, request, "erro", "Não foi possível atualizar o pedido.");
            }
        } catch (NumberFormatException ex) {
            redirecionar(response, request, "erro", "Pedido inválido.");
        }
    }

    private boolean statusValido(String status) {
        return "PENDENTE".equals(status) || "PAGO".equals(status)
                || "ENVIADO".equals(status) || "CANCELADO".equals(status);
    }

    private void redirecionar(HttpServletResponse response, HttpServletRequest request, String tipo, String mensagem)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/admin/dashboard?secao=pedidos&" + tipo + "="
                + URLEncoder.encode(mensagem, StandardCharsets.UTF_8));
    }
}
