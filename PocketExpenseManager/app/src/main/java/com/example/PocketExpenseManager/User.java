package com.example.PocketExpenseManager;

import java.io.Serializable;

/**
 * Created by Rama Vamshi Krishna on 10/31/2016.
 */
public class User implements Serializable{
    String name,email,password;
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
