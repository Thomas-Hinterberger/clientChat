/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
<<<<<<< HEAD
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
=======
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d
import model.Message;
import model.User;

/**
 *
 * @author boba
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField fxTextFieldSignInUser;
    @FXML
    private TextField fxTextFieldSignInPassword;
    @FXML
    private TextField fxTextFieldSignUpUser;
    @FXML
    private TextField fxTextFieldSignUpPassword;
    @FXML
    private TextArea fxTextAreaChat;
    @FXML
    private TextField fxTextFieldText;

    private User user;
    @FXML
    private Tab fxTabChat;
    @FXML
<<<<<<< HEAD
    private TextField fxTextFieldTo;
    @FXML
    private Label fxLabelError;
    @FXML
    private Label fxLabelLogedInAs;
=======
    private Label fxLabelError;
    @FXML
    private Label fxLabelLogedInAs;
    @FXML
    private ComboBox<User> comboUser;
    @FXML
    private Label registetUser;
    @FXML
    private Button logout;
    @FXML
    private TabPane pane;
    @FXML
    private Tab tabA;
    @FXML
    private Rectangle tabAcc;
    int currentAmmount = 0;
>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fxTabChat.setDisable(true);
        fxTextAreaChat.setEditable(false);
<<<<<<< HEAD

=======
        try {
            Socket s = new Socket("localhost", 5003);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ArrayList<User> arrpersonen = new ArrayList<>();
            arrpersonen = (ArrayList<User>) ois.readObject();
            comboUser.getItems().addAll(arrpersonen);
            currentAmmount = arrpersonen.size();
            registetUser.setText(arrpersonen.size()+" registered people");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d
    }

    @FXML
    private void handleOnMouseClickedSignIn(MouseEvent event) {
        try {
            Socket s = new Socket("localhost", 5000);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            user = new User(fxTextFieldSignInUser.getText(), fxTextFieldSignInPassword.getText(), 00);
            oos.writeObject(user);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            User ustmp = (User) ois.readObject();
            user.setNumber(ustmp.getNumber());
            fxTabChat.setDisable(false);
            fxLabelLogedInAs.setText("loged in as "+user.getUserName());
            Task tk = new Task() {
                @Override
                protected Object call() throws Exception {
                    ServerSocket ss = new ServerSocket(user.getNumber());
                    while (true) {
                        Socket s = ss.accept();
                        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                        Message me = (Message) ois.readObject();
                        String a = fxTextAreaChat.getText() + "\n" + me.getDate() + ":" + me.getFrom()+":"+me.getText();
                        fxTextAreaChat.setText(a);
                    }
<<<<<<< HEAD
=======
                    
                    
>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d

                }

            };
            Thread th = new Thread(tk);
            th.start();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {

        }
<<<<<<< HEAD

=======
         fxTextFieldSignInUser.setText("");
         fxTextFieldSignInPassword.setText("");
         pane.getSelectionModel().select(fxTabChat);
         
>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d
    }

    @FXML
    private void handleOnMouseClickedSignUp(MouseEvent event) {
        try {
            Socket s = new Socket("localhost",5002);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new User(fxTextFieldSignUpUser.getText(), fxTextFieldSignUpPassword.getText(), 0));
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            user = (User) ois.readObject();
            if (user == null){
                fxLabelError.setText("error");
            }else{
                fxLabelLogedInAs.setText("loged in as "+user.getUserName());
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
<<<<<<< HEAD
=======
        fxTextFieldSignUpUser.setText("");
        fxTextFieldSignUpPassword.setText("");
        currentAmmount++;
        registetUser.setText(currentAmmount+" registered people");
>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d
    }

    @FXML
    private void handleOnMouseClickedSend(MouseEvent event) {

        try {
            Socket s = new Socket("localhost", 5001);
            ObjectOutputStream dout = new ObjectOutputStream(s.getOutputStream());
<<<<<<< HEAD
            Message me = new Message(fxTextFieldText.getText(), Integer.parseInt(fxTextFieldTo.getText()), user.getNumber(), LocalDate.now().toString());
=======
            Message me = new Message(fxTextFieldText.getText(), comboUser.getSelectionModel().getSelectedItem().getNumber(), user.getNumber(), LocalDate.now().toString());
>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d
            dout.writeObject(me);
            String a = fxTextAreaChat.getText() + "\n" + me.getDate() + ":" + me.getTo()+":"+me.getText();
            fxTextAreaChat.setText(a);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
<<<<<<< HEAD

=======
         fxTextFieldText.setText("");
>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d
    }

    @FXML
    private void handleOnSelectionChanged(Event event) {
        
    }

<<<<<<< HEAD
=======
    @FXML
    private void logoutOnAction(ActionEvent event) {
        pane.getSelectionModel().select(tabA);
        fxTabChat.setDisable(true);
        
    }

>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d
}
