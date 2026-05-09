package com.example;


import org.mindrot.jbcrypt.BCrypt;

public class User {
    private Integer id;
    private String login;
    private String username;
    private String password;
    private Integer balance;


    // Do rejestracji
    public User(String login, String plainPassword, String username) {
        this.id = 0;
        this.login = login;
        this.password = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        this.username = username;
        this.balance = 1000;
    }

    // Z bazy danych
    public User(Integer id, String login, String passwordHash, String username, Integer balance) {
        this.id = id;
        this.login = login;
        this.username = username;
        this.password = passwordHash;
        this.balance = balance;
    }

    // GETTERY
    public Integer GetId() {
        return id;
    }
    public String GetLogin() {
        return login;
    }
    public String GetUsername() {
        return username;
    }
    public String GetPasswordHash() {
        return password;
    }
    public Integer GetBalance() {
        return balance;
    }
    // SETTER
    public void SetNewPassword(String plainPassword) {
        this.password = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Funkcje
    public void addBalance(Integer amount) {
        this.balance += amount;
    }
    public void UpdateUsername(String username) {
        this.username = username;
    }
    public boolean CheckPassword(String plainpassword) {
        return BCrypt.checkpw(plainpassword, this.password);
    }
}
