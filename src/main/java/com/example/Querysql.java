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
        rs.close();
        ps.close();
        return list;
    }

    /////////////////////
    //// Liczba gier ////
    /////////////////////
    public int getNumGames(int userId) throws SQLException{
        String sql = "SELECT COUNT(user_id) as liczba FROM game_history WHERE user_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Integer result = rs.getInt("liczba");
            ps.close();
            return result;
        }
        ps.close();
        return 0;
    }
    ////////////////////////
    //// Wygrane gry ////
    ////////////////////////
    public int getNumWinGames(int userId) throws SQLException{
        String sql = "SELECT COUNT(`user_id`) as liczba FROM game_history WHERE user_id=? and balance_change>0";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Integer result = rs.getInt("liczba");
            ps.close();
            return result;
        }
        ps.close();
        return 0;
    }
    


    ////////////////////////
    //// Przegrane gry ////
    /////////////////////////
    public int getNumLoseGames(int userId) throws SQLException{
        String sql = "SELECT COUNT(`user_id`) as liczba FROM game_history WHERE user_id=? and balance_change<0";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Integer result = rs.getInt("liczba");
            ps.close();
            return result;
        }
        ps.close();
        return 0;
    }
    


    ///////////////////////////
    //// Ostanita aktywność////
    ///////////////////////////
    public String getLastGames(int userId) throws SQLException{
        String sql = "SELECT id, game_name from game_history WHERE user_id=? ORDER BY id DESC";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String result = rs.getString("game_name");
            ps.close();
            return result;
        }
        ps.close();
        return null;
    }
    


    /////////
    // User//
    /////////

    public void changePassword(User user) throws SQLException {
            String query = "UPDATE users SET password = ? WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setString(1, user.GetPasswordHash()); 
            ps.setString(2, user.GetUsername());
            ps.executeUpdate();
            conn.close();

    }
    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users(login,password,username,balance) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user.GetLogin());
        ps.setString(2, user.GetPasswordHash());
        ps.setString(3, user.GetUsername());
        ps.setInt(4, user.GetBalance());
        ps.executeUpdate();
        ps.close();
    }
    public User findByLogin(String login) throws SQLException {
        String sql = "SELECT * from users WHERE login = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            Integer id = rs.getInt("id");
            String usernameFromDb = rs.getString("username");
            String loginFromDb = rs.getString("login");
            String password = rs.getString("password");
            Integer balance = rs.getInt("balance");
            User user = new User(id, loginFromDb, password, usernameFromDb, balance);
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
    public void updateUsersUsername(String username, Integer id) throws SQLException {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }
    public boolean checkLogin(String login) throws SQLException {
        String sql = "SELECT * FROM users WHERE login=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            ps.close();
            return true;
        }
        ps.close();
        return false;
    }
    public boolean checkUsername(String username) throws SQLException {
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

    
}
