package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    static String JDBC_DRIVER = "org.postgresql.Driver";
    static String postgresSQLURL = "jdbc:postgresql://localhost:5432/coffeebreak";
    static String usuario = "postgres";
    static String senha = "LACB@123";

    private Conexao() {};

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(postgresSQLURL, usuario, senha);
        }
        catch (ClassNotFoundException | SQLException ex ) {
            throw new SQLException("Driver não encontrado", ex);
        }
    }
}