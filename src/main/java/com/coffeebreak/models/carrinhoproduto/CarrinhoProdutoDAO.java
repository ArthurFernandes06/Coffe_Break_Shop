package com.coffeebreak.models.carrinhoproduto;

import com.coffeebreak.util.Conexao;
import com.coffeebreak.models.Carrinho.*;
import com.coffeebreak.models.Produto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoProdutoDAO {
    private CarrinhoProduto mapearCarrinhoProduto(ResultSet rs) throws SQLException {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        Carrinho carrinho = carrinhoDAO.obterPeloId(rs.getInt("id_carrinho"));
        Produto produto = produtoDAO.obterPeloId(rs.getInt("id_produto"));

        return new CarrinhoProduto(carrinho, produto, rs.getInt("quantidade"));
    }

    public boolean adicionarProdutoNoCarrinho(CarrinhoProduto carrinhoProduto) {
        String sql = "INSERT INTO carrinho_produto(id_carrinho, id_produto, quantidade) VALUES(?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carrinhoProduto.getCarrinho().getId());
            ps.setInt(2, carrinhoProduto.getProduto().getId());
            ps.setInt(3, carrinhoProduto.getQuantidade());

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean removerProdutoDoCarrinho(int idCarrinho, int idProduto) {
        String sql = "DELETE FROM carrinho_produto WHERE id_carrinho = ? AND id_produto = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarrinho);
            ps.setInt(2, idProduto);

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<CarrinhoProduto> obterProdutosDoCarrinho(int idCarrinho) {
        List<CarrinhoProduto> resultado = new ArrayList<CarrinhoProduto>();
        String sql = "SELECT * FROM carrinho_produto WHERE id_carrinho = ?";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarrinho);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultado.add(mapearCarrinhoProduto(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public CarrinhoProduto obterPorCarrinhoEProduto(int idCarrinho, int idProduto) {
        String sql = "SELECT * FROM carrinho_produto WHERE id_carrinho = ? AND id_produto = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCarrinho);
            ps.setInt(2, idProduto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearCarrinhoProduto(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean atualizarQuantidade(int idCarrinho, int idProduto, int quantidade) {
        String sql = "UPDATE carrinho_produto SET quantidade = ? WHERE id_carrinho = ? AND id_produto = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantidade);
            ps.setInt(2, idCarrinho);
            ps.setInt(3, idProduto);

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
