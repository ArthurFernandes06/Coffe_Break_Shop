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


@WebServlet("/admin/produto/cadastrar")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB
public class CadastroProdutoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("adminLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/loginAdmin");
            return;
        }

        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");
        String descricao = request.getParameter("descricao");
        String quantidadeStr = request.getParameter("quantidade");
        String categoriaStr = request.getParameter("id_categoria");

        if (nome == null || nome.isBlank()
                || precoStr == null || precoStr.isBlank()
                || categoriaStr == null || categoriaStr.isBlank()) {
            redirecionar(request, response, false, "Preencha nome, preço e categoria do produto.");
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr.replace(",", "."));
            int quantidade = (quantidadeStr == null || quantidadeStr.isBlank())
                    ? 0 : Integer.parseInt(quantidadeStr);
            int idCategoria = Integer.parseInt(categoriaStr);

            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Categoria categoria = categoriaDAO.obterPeloId(idCategoria);
            if (categoria == null) {
                redirecionar(request, response, false, "Categoria inválida.");
                return;
            }

            Part fotoPart = request.getPart("foto");
            String nomeArquivo = UploadUtil.salvarImagem(fotoPart);

            Produto produto = new Produto(nome, preco, nomeArquivo, descricao, quantidade, categoria);

            ProdutoDAO produtoDAO = new ProdutoDAO();
            boolean criado = produtoDAO.criarProduto(produto);

            if (criado) {
                redirecionar(request, response, true, "Produto cadastrado com sucesso!");
            } else {
                redirecionar(request, response, false, "Não foi possível cadastrar o produto. Tente novamente.");
            }

        } catch (NumberFormatException e) {
            redirecionar(request, response, false, "Preço ou quantidade inválidos.");
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
