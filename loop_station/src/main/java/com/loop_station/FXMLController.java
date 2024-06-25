package com.loop_station;
/*
Put header here


 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable; // Add this import statement
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class FXMLController implements Initializable {
    
    @FXML
    private Label lblOut;
    
    @FXML
    private void btnClickAction(ActionEvent event) {
        lblOut.setText("Hello World!");
    }

    void setLbl(String s){
        lblOut.setText(s);
    }
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case Y:
                System.out.println("Y pressed");
                break;
            case N: 
                System.out.println("N pressed");
                break;
            default: {
                System.out.println("Key pressed: " + event.getCode());
            }
        }
    }

    public void setScene(Scene scene) {
        // Add key event handler to switch to the game screen
        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // handleKeyPressed add to listener

/*
        var pi4j = Pi4J.newAutoContext();  
        final var button1 = new SimpleButton(pi4j, PIN.D17, Boolean.FALSE);
        final var button2 = new SimpleButton(pi4j, PIN.D27, Boolean.FALSE);

        button1.onDown(() -> {
            //setLbl("Button 1 pressed");
            Platform.runLater(() -> {
                setLbl("Button 1 pressed");
                handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "Y", "Y", javafx.scene.input.KeyCode.Y, false, false, false, false));
            });
            System.out.println("Button 1 pressed");
            System.out.flush();
            // make KeyEvent of Y
            
        });
        button1.onUp(() -> {
            //setLbl("Button 1 released");
            Platform.runLater(() -> {
                setLbl("Button 1 released");
            });
            System.out.println("Button 1 released");
            System.out.flush();
        });
        button1.whilePressed(() -> {
            //setLbl("Button 1 is down");
            Platform.runLater(() -> {
                setLbl("Button 1 is down");
            });
            System.out.println("Button 1 is down");
            System.out.flush();
        }, Duration.ofSeconds(1));

        button2.onDown(() -> {
            //setLbl("Button 2 pressed");
            Platform.runLater(() -> {
                setLbl("Button 2 pressed");
                handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "N", "N", javafx.scene.input.KeyCode.N, false, false, false, false));
            });
            System.out.println("Button 2 pressed");
            System.out.flush();
            // make KeyEvent of N
            
        });
        button2.onUp(() -> {
            //setLbl("Button 2 released");
            Platform.runLater(() -> {
                setLbl("Button 2 released");
            });
            System.out.println("Button 2 released");
            System.out.flush();
        });
        button2.whilePressed(() -> {
            //setLbl("Button 2 is down");
            Platform.runLater(() -> {
                setLbl("Button 2 is down");
            });
            System.out.println("Button 2 is down");
            System.out.flush();
        }, Duration.ofSeconds(1));*/
    }    
}
