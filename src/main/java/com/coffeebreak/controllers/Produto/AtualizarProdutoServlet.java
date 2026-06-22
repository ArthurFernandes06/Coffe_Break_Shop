package com.coffeebreak.controllers.Produto;

import com.coffeebreak.models.Categoria.Categoria;
import com.coffeebreak.models.Categoria.CategoriaDAO;
import com.coffeebreak.models.Produto.Produto;
import com.coffeebreak.models.Produto.ProdutoDAO;
import com.coffeebreak.util.UploadUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Servlet responsável por atualizar um produto existente.
 * Campos em branco mantêm o valor atual do produto (mesma lógica usada em
 * AtualizarUsuarioServlet). Uma nova foto só substitui a atual se for enviada.
 * Acesso restrito ao administrador logado (sessão "adminLogado").
 */
@WebServlet("/admin/produto/atualizar")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB
public class AtualizarProdutoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("adminLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/loginAdmin");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            redirecionar(request, response, false, "Produto inválido.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            ProdutoDAO produtoDAO = new ProdutoDAO();
            Produto produtoExistente = produtoDAO.obterPeloId(id);

            if (produtoExistente == null) {
                redirecionar(request, response, false, "Produto não encontrado.");
                return;
            }

            String nome = request.getParameter("nome");
            String precoStr = request.getParameter("preco");
            String descricao = request.getParameter("descricao");
            String quantidadeStr = request.getParameter("quantidade");
            String categoriaStr = request.getParameter("id_categoria");

            if (nome != null && !nome.isBlank()) {
                produtoExistente.setNome(nome);
            }
            if (precoStr != null && !precoStr.isBlank()) {
                produtoExistente.setPreco(Double.parseDouble(precoStr.replace(",", ".")));
            }
            if (descricao != null && !descricao.isBlank()) {
                produtoExistente.setDescricao(descricao);
            }
            if (quantidadeStr != null && !quantidadeStr.isBlank()) {
                produtoExistente.setQuantidade(Integer.parseInt(quantidadeStr));
            }
            if (categoriaStr != null && !categoriaStr.isBlank()) {
                CategoriaDAO categoriaDAO = new CategoriaDAO();
                Categoria categoria = categoriaDAO.obterPeloId(Integer.parseInt(categoriaStr));
                if (categoria != null) {
                    produtoExistente.setCategoria(categoria);
                }
            }

            Part fotoPart = request.getPart("foto");
            String novoArquivo = UploadUtil.salvarImagem(fotoPart);
            if (novoArquivo != null) {
                produtoExistente.setFoto(novoArquivo);
            }

            boolean atualizado = produtoDAO.atualizarProduto(produtoExistente);

            if (atualizado) {
                redirecionar(request, response, true, "Produto atualizado com sucesso!");
            } else {
                redirecionar(request, response, false, "Não foi possível atualizar o produto.");
            }

        } catch (NumberFormatException e) {
            redirecionar(request, response, false, "Dados inválidos. Verifique preço e quantidade.");
        } catch (IOException e) {
            redirecionar(request, response, false, "Erro ao salvar a imagem do produto.");
        }
    }

    private void redirecionar(HttpServletRequest request, HttpServletResponse response,
                              boolean sucesso, String mensagem) throws IOException {
        String chave = sucesso ? "sucesso" : "erro";
        String url = request.getContextPath() + "/admin/dashboard?secao=produtos&" + chave
                + "=" + URLEncoder.encode(mensagem, StandardCharsets.UTF_8);
        response.sendRedirect(url);
    }
}
