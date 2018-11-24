package com.example.theninetails.firebaseminitests;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by theninetails on 28/12/17.
 */

public class Users {
    HashMap<String,User> users;

    public Users() {
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }
}
