/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author gus
 */
public class Conexao {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    
    private static Connection conexao = null;
    
    private Conexao() {} // Bloquear instâncias
    
    public static Connection getConexao() {
        if (conexao != null) {
            return conexao;
        } else {
            try {
                Class.forName(DB_DRIVER);
            } catch (ClassNotFoundException e) {
                System.err.println("Não foi possível encontrar a classe de conexão ao banco de dados " + DB_DRIVER);
                e.printStackTrace();
                System.exit(1);
            }

            try {
                conexao = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                conexao.setAutoCommit(false);
                return conexao;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            // Nunca chegará aqui
            return conexao;
        }
    }
    
    public static java.sql.Date converteParaBanco(java.util.Date data) {
        return new java.sql.Date(data.getTime());
    }
    
    public static java.util.Date converteDoBanco(java.sql.Date data) {
        return new java.sql.Date(data.getTime());
    }
}
