package com.mywebsite.dto;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String role;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public User(String username, String password, String role){
        setUsername(username);
        setPassword(password);
        setRole(role);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + "Hash = " + hashCode() + ", id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", status=" + status + ", serialVersionUID=" + serialVersionUID + "]";
    }
}