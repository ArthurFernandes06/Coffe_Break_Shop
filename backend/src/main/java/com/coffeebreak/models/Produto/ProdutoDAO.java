package Produto;

import Categoria.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;

public class ProdutoDAO {

    private Produto mapearProduto(ResultSet rs) throws SQLException {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Categoria categoria = categoriaDAO.obterPeloId(rs.getInt("id_categoria"));

        return new Produto(rs.getInt("id"), rs.getString("nome"),
                rs.getDouble("preco"), rs.getString("foto"),
                rs.getString("descricao"), rs.getInt("quantidade"), categoria);
    }

    public List<Produto> obterTodos() {
        List<Produto> resultado = new ArrayList<Produto>();
        String sql = "SELECT * FROM produto";

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                resultado.add(mapearProduto(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public Produto obterPeloId(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearProduto(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean criarProduto(Produto produto) {
        String sql = "INSERT INTO produto(nome, preco, foto, descricao, quantidade, id_categoria) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setString(3, produto.getFoto());
            ps.setString(4, produto.getDescricao());
            ps.setInt(5, produto.getQuantidade());
            ps.setInt(6, produto.getCategoria().getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean atualizarProduto(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, foto = ?, descricao = ?, quantidade = ?, id_categoria = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setString(3, produto.getFoto());
            ps.setString(4, produto.getDescricao());
            ps.setInt(5, produto.getQuantidade());
            ps.setInt(6, produto.getCategoria().getId());
            ps.setInt(7, produto.getId());

            return ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } return false;
    }

    public boolean removerProduto(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Produto> obterPorCategoria(int id_categoria) {
        List<Produto> resultado = new ArrayList<Produto>();
        String sql = "SELECT * FROM produto WHERE id_categoria = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_categoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultado.add(mapearProduto(rs));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
}
