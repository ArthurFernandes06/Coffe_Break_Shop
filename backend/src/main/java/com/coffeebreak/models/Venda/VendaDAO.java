package Venda;

import Usuario.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Conexao;

public class VendaDAO {
    private Venda mapearVenda(ResultSet rs) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obterPeloId(rs.getInt("id_usuario"));
        return new Venda(rs.getInt("id"), rs.getTimestamp("data_hora"), rs.getDouble("valor_total"), rs.getString("status"), usuario);
    }

    public boolean criarVenda(Venda venda) {
        String sql = "INSERT INTO venda(id_usuario, valor_total, status) values(?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, venda.getUsuario().getId());
            ps.setDouble(2, venda.getValorTotal());
            ps.setString(3, venda.getStatus());

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Venda obterPeloId(int id) {
        String sql = "SELECT * FROM venda WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearVenda(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean atualizarStatus(int id, String status) {
        String sql = "UPDATE venda SET status = ? WHERE id = ?";
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

    public List<Venda> obterPorUsuario(int idUsuario) {
        List<Venda> resultado = new ArrayList<Venda>();
        String sql = "SELECT * FROM venda WHERE id_usuario = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultado.add(mapearVenda(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public List<Venda> obterPorStatus(String status) {
        List<Venda> resultado = new ArrayList<Venda>();
        String sql = "SELECT * FROM venda WHERE status = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultado.add(mapearVenda(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
}
