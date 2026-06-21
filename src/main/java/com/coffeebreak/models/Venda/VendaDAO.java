package com.coffeebreak.models.Venda;

import com.coffeebreak.models.Usuario.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.coffeebreak.util.Conexao;

public class VendaDAO {
    private Venda mapearVenda(ResultSet rs) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obterPeloId(rs.getInt("id_usuario"));
        return new Venda(rs.getInt("id"), rs.getTimestamp("data_hora"), rs.getDouble("valor_total"), rs.getString("status_pedido"), usuario);
    }

    public List<Venda> obterTodas() {
        List<Venda> resultado = new ArrayList<Venda>();
        String sql = "SELECT * FROM venda";

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                resultado.add(mapearVenda(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    public boolean criarVenda(Venda venda) {
        String sql = "INSERT INTO venda(id_usuario, valor_total, status_pedido) values(?, ?, ?)";
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
        String sql = "UPDATE venda SET status_pedido = ? WHERE id = ?";
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
        String sql = "SELECT * FROM venda WHERE status_pedido = ?";
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
    public List<Venda> listarRecentes(int limite) {
        List<Venda> resultado = new ArrayList<>();
        String sql = "SELECT * FROM venda ORDER BY data_hora DESC LIMIT ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limite);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapearVenda(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    public List<Venda> listarRecentesComUsuario(int limite) {
        List<Venda> lista = new ArrayList<>();
        String sql = "SELECT v.id AS id_venda, v.data_hora, v.valor_total, v.status_pedido, " +
                "u.id AS id_usuario, u.nome, u.email, u.endereco, u.senha, u.tipo " +
                "FROM venda v JOIN usuario u ON v.id_usuario = u.id " +
                "ORDER BY v.data_hora DESC LIMIT ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario(rs.getInt("id_usuario"), rs.getString("nome"), rs.getString("email"), rs.getString("endereco"), rs.getString("senha"), rs.getBoolean("tipo"));
                    Venda v = new Venda(rs.getInt("id_venda"), rs.getTimestamp("data_hora"), rs.getDouble("valor_total"), rs.getString("status_pedido"), u);
                    lista.add(v);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public int contarVendasHoje() {
        String sql = "SELECT COUNT(*) as total FROM venda WHERE DATE(data_hora) = CURRENT_DATE";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public double calcularReceitaMes() {
        String sql = "SELECT SUM(valor_total) AS total_receita " +
                "FROM venda " +
                "WHERE EXTRACT(MONTH FROM data_hora) = EXTRACT(MONTH FROM CURRENT_DATE) " +
                "AND EXTRACT(YEAR FROM data_hora) = EXTRACT(YEAR FROM CURRENT_DATE)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total_receita");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }
}
