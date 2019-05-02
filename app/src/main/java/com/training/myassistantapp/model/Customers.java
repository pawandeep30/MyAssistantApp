package com.training.myassistantapp.model;

import java.io.Serializable;

public class Customers implements Serializable {

    public int id;
    public String docId;
    public String name;
    public String phone;
    public String email;

    public Customers(){

    }

    public Customers(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Customers(int id, String docId, String name, String phone, String email) {
        this.id = id;
        this.docId = docId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }


    @Override
    public String toString() {
        String message = "Name: "+name+"\nPhone: "+phone+"\nEmail: "+email;
        return message;
    }
}
