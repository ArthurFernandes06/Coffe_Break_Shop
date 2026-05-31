package com.coffeebreak.models.Categoria;

import com.coffeebreak.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private Categoria mapearCategoria(ResultSet rs) throws SQLException {
        return new Categoria(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));
    }

    public List<Categoria> obterTodos() {
        List<Categoria> resultado = new ArrayList<Categoria>();
        String sql = "SELECT * FROM categoria";
        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                resultado.add(mapearCategoria(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    public Categoria obterPeloId(int id) {
        Categoria categoria = null;
        String sql = "SELECT * FROM categoria WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearCategoria(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public boolean criarCategoria(Categoria categoria)  {
        String sql = "INSERT INTO categoria(nome, descricao) VALUES (?, ?)";
        try (Connection conn = Conexao.getConexao();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getDescricao());

            return ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean atualizarCategoria(Categoria categoria) {
        String sql = "UPDATE categoria SET nome = ?, descricao = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getDescricao());
            ps.setInt(3, categoria.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean removerCategoria(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}