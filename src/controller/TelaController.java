/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db.dao.DaoRegistro;
import db.pojo.Registro;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import nodes.CoffeePane;

/**
 *
 * @author gus
 */
public class TelaController {
    @FXML Pane parentCoffeePane;
    @FXML TextField medidaCopo;
    @FXML Label labelInfoIngestao;
    @FXML Label labelDataAtual;
    
    CoffeePane coffeePane;
    int coposBebeu = 0;
    double tamCopo = 0d;
    
    Registro registro;
    DaoRegistro daoRegistro;
    
    @FXML
    public void initialize() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        labelDataAtual.setText(sdf.format(new Date()));
    
        coffeePane = new CoffeePane();
        parentCoffeePane.getChildren().add(coffeePane);
        
        pegarValoresBanco();
        
        atualizarTamanhoCopo();
        atualizarCoposBebeu();
        atualizarLabelIngestao();
    }
    
    @FXML
    public void confirmar() {
        if (!medidaCopo.getText().isEmpty()) {
            atualizarTamanhoCopo();
            double qtdIngerida = tamCopo * (coffeePane.pixelsPreenchidos / (double) coffeePane.height);
            
            registro.setMlIngerido(registro.getMlIngerido() + qtdIngerida);
            daoRegistro.merge(registro);
            
            atualizarCoposBebeu();
            atualizarLabelIngestao();
            
            List<Registro> list = DaoRegistro.findAll();
            for (Registro reg : list) {
                System.out.println("id: " + reg.getId() + ", mlIngerido: " + reg.getMlIngerido() + ", data: " + reg.getData());
            }
        }
    }
    
    @FXML
    public void atualizarTamanhoCopo() {
        tamCopo = Double.parseDouble(medidaCopo.getText().replaceAll("[^\\d.]", ""));
    }
    
    private void atualizarCoposBebeu() {
        atualizarTamanhoCopo();
        coposBebeu = (int) Math.floor(registro.getMlIngerido() / tamCopo);
    }
    
    private void atualizarLabelIngestao() {
        double totalIngestao = registro.getMlIngerido();
        String unidadeMedidaIngestao = "ml";
        if (totalIngestao > 1000) {
            totalIngestao /= 1000;
            unidadeMedidaIngestao = "L";
        } else {
            totalIngestao = (int)totalIngestao;
        }
        labelInfoIngestao.setText(String.format("%1$d copo(s) de %2$dml | %3$.1f%4$s no total", (int)coposBebeu, (int)tamCopo, totalIngestao, unidadeMedidaIngestao));
    }
    
    private void pegarValoresBanco() {
        registro = DaoRegistro.find(new Date());
        if (registro.getId() == 0) {
            registro.setData(new Date());
            registro.setId(daoRegistro.merge(registro));            
        }
        Registro reg = registro;
        System.out.println("id: " + reg.getId() + ", mlIngerido: " + reg.getMlIngerido() + ", data: " + reg.getData());
    }
}
