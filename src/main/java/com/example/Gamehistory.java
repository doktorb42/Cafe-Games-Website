package com.example;

import java.sql.Timestamp;

public class Gamehistory {
    private Integer id;
    private Integer userId;
    private String gameName;
    private Integer bet;
    private String result;
    private Integer balanceChange;
    private Timestamp createdAt;
    
    public Gamehistory(Integer id, Integer userId, String gameName, Integer bet, String result, Integer balanceChange, Timestamp createAt){
        this.id=id;
        this.userId=userId;
        this.gameName=gameName;
        this.bet=bet;
        this.result=result;
        this.balanceChange=balanceChange;
        this.createdAt=createAt;
    }
    // //// GETTERY /////

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getGameName() {
        return gameName;
    }

    public Integer getBet() {
        return bet;
    }

    public String getResult() {
        return result;
    }

    public Integer getBalanceChange() {
        return balanceChange;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }


}

