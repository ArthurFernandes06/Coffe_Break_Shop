package com.coffeebreak;

import Categoria.*;
import Produto.Produto;
import Produto.ProdutoDAO;
import Usuario.*;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String args[]) throws SQLException {
        ProdutoDAO pdao = new ProdutoDAO();
        List<Produto> r = pdao.obterPorCategoria(2);
        System.out.println(r);
    }

}
