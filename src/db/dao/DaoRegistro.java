/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.Conexao;
import db.pojo.Registro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gus
 */
public class DaoRegistro {
    private static boolean inicializado = false;
    private static String createTableQuery = "CREATE TABLE IF NOT EXISTS registro ("
            + "id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "ml_ingerido DECIMAL(15,2) NOT NULL DEFAULT 0, "
            + "data DATE NOT NULL UNIQUE)";
    private static String insertQuery = "INSERT INTO registro (ml_ingerido, data) VALUES (?, ?)";
    private static String updateQuery = "UPDATE registro SET ml_ingerido = ?, data = ? WHERE id = ?";
    private static String selectQuery = "SELECT * FROM registro";
    private static String selectByIdQuery = "SELECT * FROM registro WHERE id = ?";
    private static String selectByDateQuery = "SELECT * FROM registro WHERE data = ?";
    
    private DaoRegistro() {}
    
    public static void init() {
        try {
            Statement st = Conexao.getConexao().createStatement();
            st.executeUpdate(createTableQuery);
            inicializado = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int merge(Registro registro) {
        if (!inicializado) { init(); }
        
        if (registro.getId() > 0) { // Fazer update
            try {
                PreparedStatement ps = Conexao.getConexao().prepareStatement(updateQuery);
                ps.setDouble(1, registro.getMlIngerido());
                ps.setDate(2, Conexao.converteParaBanco(registro.getData()));
                ps.setInt(3, registro.getId());
                ps.executeUpdate();
                Conexao.getConexao().commit();
                return registro.getId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { // Fazer insert
            try {
                PreparedStatement ps = Conexao.getConexao().prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setDouble(1, registro.getMlIngerido());
                ps.setDate(2, Conexao.converteParaBanco(registro.getData()));
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                Conexao.getConexao().commit();
                int id = 0;
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                return id;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return 0;
    }
    
    public static Registro find(int id) {
        if (!inicializado) { init(); }
        
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(selectByIdQuery);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            Registro registro = new Registro();
            if (rs.next()) {
                registro.setId(rs.getInt(1));
                registro.setMlIngerido(rs.getDouble(2));
                registro.setData(Conexao.converteDoBanco(rs.getDate(3)));
            }
            return registro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Registro find(Date date) {
        if (!inicializado) { init(); }
        
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(selectByDateQuery);
            ps.setDate(1, Conexao.converteParaBanco(date));
            ResultSet rs = ps.executeQuery();
            
            Registro registro = new Registro();
            if (rs.next()) {
                registro.setId(rs.getInt(1));
                registro.setMlIngerido(rs.getDouble(2));
                registro.setData(Conexao.converteDoBanco(rs.getDate(3)));
            }
            return registro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<Registro> findAll() {
        if (!inicializado) { init(); }
        
        List<Registro> list = new ArrayList();
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(selectQuery);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Registro registro = new Registro();
                registro.setId(rs.getInt(1));
                registro.setMlIngerido(rs.getDouble(2));
                registro.setData(Conexao.converteDoBanco(rs.getDate(3)));
                list.add(registro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
}
