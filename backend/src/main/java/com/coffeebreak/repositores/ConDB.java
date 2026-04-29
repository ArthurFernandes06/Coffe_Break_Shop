package com.coffeebreak.repositores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConDB {
    //Variáveis de conexão
    private static final String URL ="jdbc:postgresql://localhost:5432/coffeebreak";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "LACB@123";

    private Connection conexao;

    public boolean setConnection()
    {
        try
        {
            Class.forName("org.postgresql.Driver");

            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            return true;

        }catch(ClassNotFoundException ex)
        {
            System.err.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }   
        catch(SQLException ex)
        {
            System.err.println("Erro ao conectar: " + ex.getMessage());
            return false;

        }

    }

    public Connection getConnection()
    {
        return conexao;
    }
    public void closeConnection()
    {
        try
        {
            if(conexao != null && !conexao.isClosed())
            {
                conexao.close();
                conexao = null;
            }
                
        }catch(SQLException ex)
        {
            System.err.println("Erro ap tentar fechar conexxão: " + ex.getMessage());
        }
    }
    
}
