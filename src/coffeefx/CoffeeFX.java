/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeefx;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author gus
 */
public class CoffeeFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        
        /*CoffeePane cp = new CoffeePane();
        cp.setOnMouseClick(e->{
            System.out.println("Wahhh Came from Outside!");
        });
        root.getChildren().addAll(cp);
        */
        URL fxml = getClass().getResource("/fxml/tela.fxml");
        Parent fxmlParent = null;
        try {
            fxmlParent = (Parent) FXMLLoader.load(fxml);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        if (fxmlParent != null) {
            Scene scene = new Scene(fxmlParent);

            primaryStage.setTitle("CoffeeFX v0.01");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
