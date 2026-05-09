package com.example;


import org.mindrot.jbcrypt.BCrypt;

public class User {
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private Integer balance;


    /// Do rejestracji  ////
    public User(String Username, String plainPassword, String Nickname){
        this.id = 0;
        this.username = Username;
        this.password = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        this.nickname = Nickname;
        this.balance = 1000;
    }
    
    /// Z bazy danych  ////
    public User(Integer id, String Username, String passwordHash, String Nickname, Integer Balance){
        this.id=id;
        this.username = Username;
        this.password = passwordHash;
        this.nickname = Nickname;
        this.balance = Balance;
    }

    // GETTERY ////
    public Integer GetId(){
        return id;
    }
    public String GetUsername(){
        return username;
    }
    public String GetPasswordHash(){
        return password;
    }
    public Integer GetBalance(){
        return balance;
    }
    public String GetNickname(){
        return nickname;
    }
    /// SETTER ////
    public void SetNewPassword(String plainPassword){
        this.password = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /// Funkcje ///
    public void addBalance(Integer amount){
        this.balance +=amount;
    }
    public void UpdateNickname(String Nickname){
        this.nickname=Nickname;
    }
    public boolean CheckPassword(String plainpassword){
        return BCrypt.checkpw(plainpassword, this.password);
    }
    
}
