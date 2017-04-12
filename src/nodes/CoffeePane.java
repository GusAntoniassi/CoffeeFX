/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author gus
 */
public class CoffeePane extends Pane {
    private int width = 247;
    public int height = 267;
    public int pixelsPreenchidos = 0;
    public CoffeePane() {
        super();
        setPrefSize(width, height);
        setMaxSize(width, height);
//        setStyle("-fx-background-color: red;");
        double posY = ((Bounds) localToScene((Bounds) getBoundsInLocal())).getMinY();

        Image img = new Image("resources/img/copo.png");
        ImageView iv = new ImageView(img);
        iv.setOpacity(0.5);
        
        Rectangle cafeFill = new Rectangle(247, 0);
        cafeFill.setFill(Color.BROWN);
        
        getChildren().addAll(cafeFill, iv);
        
        List<EventType<MouseEvent>> mouseEvents = new ArrayList();
        mouseEvents.add(MouseEvent.MOUSE_DRAGGED);
        mouseEvents.add(MouseEvent.MOUSE_CLICKED);
        
        for (EventType<MouseEvent> me : mouseEvents) {
            addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
                double mouseY = e.getY();
                cafeFill.setY(mouseY);
                cafeFill.setHeight(height - (mouseY));
                pixelsPreenchidos = (int)(height - (e.getY()-posY));
            });   
        }
    }
}
