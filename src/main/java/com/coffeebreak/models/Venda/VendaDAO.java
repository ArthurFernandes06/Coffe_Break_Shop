package com.coffeebreak.models.Venda;

import com.coffeebreak.models.Usuario.*;
import com.coffeebreak.models.Carrinho.Carrinho;
import com.coffeebreak.models.carrinhoproduto.CarrinhoProduto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.coffeebreak.util.Conexao;

public class VendaDAO {
    private Venda mapearVenda(ResultSet rs) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obterPeloId(rs.getInt("id_usuario"));
        return new Venda(rs.getInt("id"), rs.getTimestamp("data_hora"), rs.getDouble("valor_total"), rs.getString("status"), usuario);
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

    public Venda criarVendaERetornar(Venda venda) {
        String sql = "INSERT INTO venda(id_usuario, valor_total, status) values(?, ?, ?) RETURNING *";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, venda.getUsuario().getId());
            ps.setDouble(2, venda.getValorTotal());
            ps.setString(3, venda.getStatus());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearVenda(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Venda finalizarCompra(Carrinho carrinho, List<CarrinhoProduto> itens) {
        if (carrinho == null || itens == null || itens.isEmpty()) {
            return null;
        }

        String buscarProduto = "SELECT quantidade, preco FROM produto WHERE id = ? FOR UPDATE";
        String criarVenda = "INSERT INTO venda(id_usuario, valor_total, status) VALUES(?, ?, 'PENDENTE') RETURNING *";
        String adicionarItem = "INSERT INTO venda_produto(id_venda, id_produto, quantidade, preco_unitario) VALUES(?, ?, ?, ?)";
        String baixarEstoque = "UPDATE produto SET quantidade = quantidade - ? WHERE id = ?";
        String limparCarrinho = "DELETE FROM carrinho_produto WHERE id_carrinho = ?";
        String inativarCarrinho = "UPDATE carrinho SET status = 'INATIVO' WHERE id = ?";

        try (Connection conn = Conexao.getConexao()) {
            conn.setAutoCommit(false);

            try {
                double valorTotal = 0.0;

                for (CarrinhoProduto item : itens) {
                    try (PreparedStatement ps = conn.prepareStatement(buscarProduto)) {
                        ps.setInt(1, item.getProduto().getId());

                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next() || rs.getInt("quantidade") < item.getQuantidade()) {
                                conn.rollback();
                                return null;
                            }
                            valorTotal += rs.getDouble("preco") * item.getQuantidade();
                        }
                    }
                }

                Venda vendaCriada;
                try (PreparedStatement ps = conn.prepareStatement(criarVenda)) {
                    ps.setInt(1, carrinho.getUsuario().getId());
                    ps.setDouble(2, valorTotal);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return null;
                        }
                        vendaCriada = mapearVenda(rs);
                    }
                }

                for (CarrinhoProduto item : itens) {
                    try (PreparedStatement ps = conn.prepareStatement(adicionarItem)) {
                        ps.setInt(1, vendaCriada.getId());
                        ps.setInt(2, item.getProduto().getId());
                        ps.setInt(3, item.getQuantidade());
                        ps.setDouble(4, item.getProduto().getPreco());
                        ps.executeUpdate();
                    }

                    try (PreparedStatement ps = conn.prepareStatement(baixarEstoque)) {
                        ps.setInt(1, item.getQuantidade());
                        ps.setInt(2, item.getProduto().getId());
                        ps.executeUpdate();
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement(limparCarrinho)) {
                    ps.setInt(1, carrinho.getId());
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(inativarCarrinho)) {
                    ps.setInt(1, carrinho.getId());
                    ps.executeUpdate();
                }

                conn.commit();
                return vendaCriada;
            } catch (SQLException ex) {
                conn.rollback();
                ex.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
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

    public boolean removerVenda(int id) {
        String removerItens = "DELETE FROM venda_produto WHERE id_venda = ?";
        String removerVenda = "DELETE FROM venda WHERE id = ?";

        try (Connection conn = Conexao.getConexao()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psItens = conn.prepareStatement(removerItens);
                 PreparedStatement psVenda = conn.prepareStatement(removerVenda)) {
                psItens.setInt(1, id);
                psItens.executeUpdate();

                psVenda.setInt(1, id);
                boolean removida = psVenda.executeUpdate() == 1;
                conn.commit();
                return removida;
            } catch (SQLException ex) {
                conn.rollback();
                ex.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Venda> obterPorUsuario(int idUsuario) {
        List<Venda> resultado = new ArrayList<Venda>();
        String sql = "SELECT * FROM venda WHERE id_usuario = ? ORDER BY data_hora DESC";
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
        String sql = "SELECT v.id AS id_venda, v.data_hora, v.valor_total, v.status, " +
                "u.id AS id_usuario, u.nome, u.email, u.endereco, u.senha, u.tipo " +
                "FROM venda v JOIN usuario u ON v.id_usuario = u.id " +
                "ORDER BY v.data_hora DESC LIMIT ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario(rs.getInt("id_usuario"), rs.getString("nome"), rs.getString("email"), rs.getString("endereco"), rs.getString("senha"), rs.getBoolean("tipo"));
                    Venda v = new Venda(rs.getInt("id_venda"), rs.getTimestamp("data_hora"), rs.getDouble("valor_total"), rs.getString("status"), u);
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
