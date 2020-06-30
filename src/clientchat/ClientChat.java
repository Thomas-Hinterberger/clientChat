/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat;

import static clientchat.FXMLDocumentController.listBackUp;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Message;
import model.User;

/**
 *
 * @author boba
 */
public class ClientChat extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        FXMLDocumentController con = new FXMLDocumentController();
        ArrayList<Message> arrpersonen = con.listBackUp;
        stage.setOnCloseRequest((event) -> {
            try {
                XMLEncoder enc = new XMLEncoder(new FileOutputStream(new File("Chat.xml")));
                enc.writeObject(listBackUp);
                enc.flush();
                enc.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
