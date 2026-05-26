package com.coffeebreak;

import Categoria.*;
import Usuario.*;

import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
        CategoriaDAO dao = new CategoriaDAO();
        UsuarioDAO dao2 = new UsuarioDAO();
        Categoria nova = new Categoria("Teste", "blablabla");

        System.out.println("Criar " + dao2.obterTodos());
    }

}
