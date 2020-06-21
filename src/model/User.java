/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author boba
 */
public class User implements Serializable{
    String userName;
    String password;
    int number;

    public User() {
    }

    public User(String userName, String password, int number) {
        this.userName = userName;
        this.password = password;
        this.number = number;
    }
    
    
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        return "User{" + "userName=" + userName + ", password=" + password + ", number=" + number + '}';
=======
        return userName;
>>>>>>> c92d16d29dc0438b769e9f666556c3a6e4e54a4d
    }
    
    
    
}
