package com.example;


import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Querysql {
    private Connection conn;
    public Querysql(Connection conn){
        this.conn = conn;
    }
    /////////
    // Gamehistory//
    /////////
    public void saveGameHistory(User user, String gameName, Integer bet, String result, Integer balanceChange) throws SQLException{
        String sql = "INSERT INTO game_history(user_id, game_name, bet, result, balance_change) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, user.GetId());
        ps.setString(2, gameName);
        ps.setInt(3, bet);
        ps.setString(4, result);
        ps.setInt(5, balanceChange);
        ps.executeUpdate();
        ps.close();
    
    
    }
    public List<Gamehistory> getUsersGameHistory(int userId) throws SQLException {

        List<Gamehistory> list = new ArrayList<>();

        String sql = "SELECT * FROM game_history WHERE user_id = ? ORDER BY created_at DESC LIMIT 50";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String gameName = rs.getString("game_name");
            Integer userIdFromDb = rs.getInt("user_id");
            Integer bet = rs.getInt("bet");
            String result = rs.getString("result");
            Integer balanceChange = rs.getInt("balance_change");
            Timestamp createdAt = rs.getTimestamp("created_at");
            Gamehistory game = new Gamehistory(id,userIdFromDb,gameName,bet,result,balanceChange,createdAt);

            list.add(game);
        }
        ps.close();
        return list;
    }
    /////////
    // User//
    /////////


    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username,password,nickname,balance) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user.GetUsername());
        ps.setString(2, user.GetPasswordHash());
        ps.setString(3, user.GetNickname());
        ps.setInt(4, user.GetBalance());
        ps.executeUpdate();
        ps.close();
    }
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * from users WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            Integer id = rs.getInt("id");
            String nickname = rs.getString("nickname");
            String usernameFromDb = rs.getString("username");
            String password = rs.getString("password");
            Integer balance = rs.getInt("balance");
            User user = new User(id,usernameFromDb,password,nickname,balance);
            ps.close();
            return user;
        }
        ps.close();
        return null;
    }
    public void updateUsersBalance(Integer ammount, Integer id) throws SQLException{
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, ammount);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }
    public void updateUsersNickname(String Nickname, Integer id) throws SQLException{
        String sql = "UPDATE users SET nickname = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, Nickname);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }
    public boolean checkUsername(String username) throws SQLException{
        String sql = "SELECT * FROM users WHERE username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            ps.close();
            return true;
        }
        ps.close();
        return false;
    }
    public boolean checkNickname(String nickname) throws SQLException{
        String sql = "SELECT * FROM users WHERE nickname=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nickname);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            ps.close();
            return true;
        }
        ps.close();
        return false;
    }

    
}
