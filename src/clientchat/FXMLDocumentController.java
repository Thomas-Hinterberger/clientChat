/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
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
    private TextArea fxTextAreaChat;
    @FXML
    private TextField fxTextFieldText;

    private User user;
    @FXML
    private Tab fxTabChat;
    @FXML
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
    HashMap<User, Message> hashPersonen;
    @FXML
    private TableView<Message> fxTabViewChat;
    @FXML
    private TableColumn<Message, String> fxTabViewColumnDate;
    @FXML
    private TableColumn<Message, Integer> fxTabViewColumnFrom;
    @FXML
    private TableColumn<Message, String> fxTabViewColumnText;

    ArrayList<Message> listBackUp;

    User sendTo;
    ArrayList<User> arrpersonen;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fxTabViewColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            fxTabViewColumnFrom.setCellValueFactory(new PropertyValueFactory<>("from"));
            fxTabViewColumnText.setCellValueFactory(new PropertyValueFactory<>("text"));
            listBackUp = new ArrayList<>();
            fxTabChat.setDisable(true);
            fxTabViewChat.setEditable(false);
            DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 5000);
            String ip = socket.getLocalAddress().getHostAddress();
            Socket s = new Socket(ip, 5003);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            arrpersonen = new ArrayList<>();

            arrpersonen = (ArrayList<User>) ois.readObject();
            ois.close();
            try {
                XMLDecoder dec = new XMLDecoder(new FileInputStream(new File("Chat.xml")));
                listBackUp = (ArrayList<Message>) dec.readObject();
                dec.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArrayIndexOutOfBoundsException ex) {

            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        registetUser.setText(arrpersonen.size() + " registered people");
    }

    @FXML
    private void handleOnMouseClickedSignIn(MouseEvent event) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 5000);
            String ip = socket.getLocalAddress().getHostAddress();
            Socket s = new Socket(ip, 5000);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            user = new User(fxTextFieldSignInUser.getText(), fxTextFieldSignInPassword.getText(), 00);
            oos.writeObject(user);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            User ustmp = (User) ois.readObject();
            user.setNumber(ustmp.getNumber());
            fxTabChat.setDisable(false);
            fxLabelLogedInAs.setText("loged in as " + user.getUserName());
            Task tk = new Task() {
                @Override
                protected Object call() throws Exception {

                    while (true) {
                        ServerSocket ss = new ServerSocket(user.getNumber());
                        Socket s = ss.accept();
                        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                        Message me = (Message) ois.readObject();
                        fxTabViewChat.getItems().add(me);
                        try {
                            listBackUp.add(me);
                            XMLEncoder enc = new XMLEncoder(new FileOutputStream(new File("Chat.xml")));
                            enc.writeObject(listBackUp);
                            enc.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

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
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 5000);
            String ip = socket.getLocalAddress().getHostAddress();
            Socket s = new Socket(ip, 5003);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ArrayList<User> arrpersonen = new ArrayList<>();

            arrpersonen = (ArrayList<User>) ois.readObject();
            ois.close();
            for (int i = 0; i < arrpersonen.size(); i++) {
                if (arrpersonen.get(i).getUserName().equals(user.getUserName())) {
                    arrpersonen.remove(i);
                    break;
                }
            }
            comboUser.getItems().addAll(arrpersonen);
            currentAmmount = arrpersonen.size();
            registetUser.setText(arrpersonen.size() + " registered people");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        fxTextFieldSignInUser.setText("");
        fxTextFieldSignInPassword.setText("");
        pane.getSelectionModel().select(fxTabChat);

    }

    @FXML
    private void handleOnMouseClickedSignUp(MouseEvent event) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 5000);
            String ip = socket.getLocalAddress().getHostAddress();
            Socket s = new Socket(ip, 5002);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new User(fxTextFieldSignUpUser.getText(), fxTextFieldSignUpPassword.getText(), 0));
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            user = (User) ois.readObject();
            if (user == null) {
                fxLabelError.setText("error");
            } else {
                fxLabelLogedInAs.setText("loged in as " + user.getUserName());
            }
            s.close();
            ois.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < arrpersonen.size(); i++) {
            if (arrpersonen.get(i) == user) {
                arrpersonen.remove(i);
                break;
            }
        }
        comboUser.getItems().addAll(arrpersonen);

    }

    @FXML
    private void handleOnMouseClickedSend(MouseEvent event) {

        try {
            DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 5000);
            String ip = socket.getLocalAddress().getHostAddress();
            Socket s = new Socket(ip, 5001);
            ObjectOutputStream dout = new ObjectOutputStream(s.getOutputStream());
            Message me = new Message(fxTextFieldText.getText(), comboUser.getSelectionModel().getSelectedItem().getNumber(), user.getNumber(), LocalDate.now().toString());
            dout.writeObject(me);
            fxTabViewChat.getItems().add(me);
            s.close();
            dout.close();
            try {
                listBackUp.add(me);
                XMLEncoder enc = new XMLEncoder(new FileOutputStream(new File("Chat.xml")));
                enc.writeObject(listBackUp);
                enc.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArrayIndexOutOfBoundsException ex) {

            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleOnSelectionChanged(Event event) {

    }

    @FXML
    private void logoutOnAction(ActionEvent event) {
        pane.getSelectionModel().select(tabA);
        fxTabChat.setDisable(true);
    }

    @FXML
    private void handleOnMouseClickedCombo(MouseEvent event) {

        if (sendTo != comboUser.getValue()) {
            sendTo = comboUser.getValue();
            fxTabViewChat.getItems().clear();
            for (int i = 0; i < listBackUp.size(); i++) {
                if (listBackUp.get(i).getFrom() == sendTo.getNumber() || listBackUp.get(i).getTo() == sendTo.getNumber()) {
                    fxTabViewChat.getItems().add(listBackUp.get(i));
                }
            }
        }

    }
}
