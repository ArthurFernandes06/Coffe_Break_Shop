package com.coffeebreak.models.Usuario;

import com.coffeebreak.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), rs.getString("endereco"), rs.getString("senha"), rs.getBoolean("tipo"));
    }

    public List<Usuario> obterTodos() {
        List<Usuario> resultado = new ArrayList<Usuario>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                resultado.add(mapearUsuario(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public Usuario obterPeloId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearUsuario(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public Usuario obterPeloEmail(String email) {
        String sql = "SELECT id, nome, email, endereco, senha, tipo FROM usuario WHERE email = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearUsuario(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public boolean criarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario(nome, email, endereco, senha, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getEndereco());
            ps.setString(4, usuario.getSenha());
            ps.setBoolean(5, usuario.isTipo());

            return ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, endereco = ?, senha = ?, tipo = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getEndereco());
            ps.setString(4, usuario.getSenha());
            ps.setBoolean(5, usuario.isTipo());
            ps.setInt(6, usuario.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean removerUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public int contarTodos() {
        String sql = "SELECT COUNT(*) as total FROM usuario";

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
}
