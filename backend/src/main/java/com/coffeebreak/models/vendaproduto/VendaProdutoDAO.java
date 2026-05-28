package vendaproduto;

import Venda.*;
import Produto.*;

import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaProdutoDAO {
    private VendaProduto mapearVendaProduto(ResultSet rs) throws SQLException {
        VendaDAO vendaDAO = new VendaDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        Venda venda = vendaDAO.obterPeloId(rs.getInt("id_venda"));
        Produto produto = produtoDAO.obterPeloId(rs.getInt("id_produto"));

        return new VendaProduto(venda, produto, rs.getInt("quantidade"), rs.getDouble("preco_unitario"));
    }

    public boolean adicionarProdutoNaVenda(VendaProduto vendaProduto) {
        String sql = "INSERT INTO venda_produto(id_venda, id_produto, quantidade, preco_unitario) VALUES(?,?,?,?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vendaProduto.getVenda().getId());
            ps.setInt(2, vendaProduto.getProduto().getId());
            ps.setInt(3, vendaProduto.getQuantidade());
            ps.setDouble(4, vendaProduto.getPrecoUnitario());

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean removerProdutoDaVenda(int idVenda, int idProduto) {
        String sql = "DELETE FROM venda_produto WHERE id_venda = ? AND id_produto = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVenda);
            ps.setInt(2, idProduto);

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<VendaProduto> obterProdutosPorVenda(int idVenda) {
        List<VendaProduto> resultado = new ArrayList<VendaProduto>();
        String sql = "SELECT * FROM venda_produto WHERE id_venda = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVenda);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultado.add(mapearVendaProduto(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    return resultado;
    }

    public VendaProduto obterPorVendaEProduto(int idVenda, int idProduto) {
        String sql = "SELECT * FROM venda_produto WHERE id_venda = ? AND id_produto = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVenda);
            ps.setInt(2, idProduto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearVendaProduto(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean atualizarQuantidade(int idVenda, int idProduto, int quantidade) {
        String sql = "UPDATE venda_produto SET quantidade = ? WHERE id_venda = ? AND id_produto = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantidade);
            ps.setInt(2, idVenda);
            ps.setInt(3, idProduto);

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
