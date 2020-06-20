/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author agaub
 */
public class Message implements Serializable{
    String text;
    int to;
    int from;
    String date;

    public Message( String text, int to, int from, String date) {
        this.text = text;
        this.to = to;
        this.from = from;
        this.date = date;
    }

    public Message() {
    }

    

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" + "text=" + text + ", to=" + to + ", from=" + from + ", date=" + date + '}';
    }

    
    
           
}
