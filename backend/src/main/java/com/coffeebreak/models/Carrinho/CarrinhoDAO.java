package Carrinho;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.Conexao;
import Usuario.*;

public class CarrinhoDAO {
    private Carrinho mapearCarrinho(ResultSet rs) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obterPeloId(rs.getInt("id_usuario"));

        return new Carrinho(rs.getInt("id"), rs.getString("status"), rs.getTimestamp("data_criacao"), usuario);
    }

    public boolean criarCarrinho(Carrinho carrinho) {
        String sql = "INSERT INTO carrinho(id_usuario, status) VALUES(?,?)";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carrinho.getUsuario().getId());
            ps.setString(2, carrinho.getStatus());

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Carrinho obterPeloId(int id) {
        String sql = "SELECT * FROM carrinho WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearCarrinho(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Carrinho obterCarrinhoAtivo(int idUsuario) {
        String sql = "SELECT * FROM carrinho WHERE id_usuario = ? AND status = 'ATIVO'";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearCarrinho(rs);
        }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        return null;
    }

    public List<Carrinho> obterPorStatus(String status) {
        List<Carrinho> resultado = new ArrayList<Carrinho>();
        String sql = "SELECT * FROM carrinho WHERE status = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultado.add(mapearCarrinho(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean atualizarStatus(int id, String status) {
        String sql = "UPDATE carrinho SET status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
