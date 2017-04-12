/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.pojo;

import java.util.Date;

/**
 *
 * @author gus
 */
public class Registro {
    private int id;
    private Date data;
    private double mlIngerido;

    public Registro() {
        this.id = 0;
        this.data = null;
        this.mlIngerido = 0d;
    }
    
    public Registro(int id, Date data, double mlIngerido) {
        this.id = id;
        this.data = data;
        this.mlIngerido = mlIngerido;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getMlIngerido() {
        return mlIngerido;
    }

    public void setMlIngerido(double mlIngerido) {
        this.mlIngerido = mlIngerido;
    }
}
