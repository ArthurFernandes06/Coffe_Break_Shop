package Categoria;

import util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private Categoria mapearCategoria(ResultSet rs) throws SQLException {
        return new Categoria(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));
    }
    public List<Categoria> obterTodos() {
        List<Categoria> resultado = new ArrayList<Categoria>();
        try {
            Connection conn = Conexao.getConexao();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from categoria");
            while (resultSet.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultSet.getInt("id"));
                categoria.setNome(resultSet.getString("nome"));
                categoria.setDescricao(resultSet.getString("descricao"));
                resultado.add(categoria);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return resultado;
    }
    public Categoria obterPeloId(int id) {
        Categoria categoria = null;
        try {
            Connection conn = Conexao.getConexao();

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM categoria WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                categoria = new Categoria();
                categoria.setId(resultSet.getInt("id"));
                categoria.setNome(resultSet.getString("nome"));
                categoria.setDescricao(resultSet.getString("descricao"));
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return categoria;
    }
    public boolean criarCategoria(String nome, String descricao) {
        boolean sucesso = false;
        try {
            Connection conn = Conexao.getConexao();

            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO categoria (nome, descricao) VALUES (?, ?)");
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, descricao);

            sucesso = (preparedStatement.executeUpdate() == 1);
            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

    public boolean atualizarCategoria(int id, String nome, String descricao) {
        boolean sucesso = false;
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE categoria set nome = ?, descricao = ? where id = ?");
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, descricao);
            preparedStatement.setInt(3, id);

            sucesso = (preparedStatement.executeUpdate() == 1);

            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

    public boolean removerCategoria(int id) {
        boolean sucesso = false;
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE from categoria where id = ?");
            preparedStatement.setInt(1, id);

            sucesso = (preparedStatement.executeUpdate() == 1);

            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }
}