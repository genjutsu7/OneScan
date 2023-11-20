package com.dao;

import com.model.User;
import com.scanner.ScanUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static final String DELETE_BY_NAME = "DELETE FROM users WHERE name=?";
    public static final String INSERT_USER = "INSERT INTO users VALUES (?, ?, ?, ?)";
    public static final String UPDATE_USER_BY_ID = "";
    public static final String VALIDATE_USER= "SELECT * FROM users WHERE (name=? OR email=?) AND password=?";
    public static final String SELECT_ALL = "SELECT * FROM users";
    
    public static void registerUser(User user) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement psmt = con.prepareStatement(INSERT_USER);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp currDate = Timestamp.valueOf(formatter.format(now));
        psmt.setString(1, user.getName());
        psmt.setString(2, user.getEmail());
        psmt.setString(3, ScanUtils.buildHash(user.getPassword()));
        psmt.setTimestamp(4, currDate);
        psmt.executeUpdate();
    }

    public static void deleteUser(String name) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement psmt = con.prepareStatement(DELETE_BY_NAME);
        psmt.setString(1, name);
        psmt.executeUpdate();
    }

    public static boolean validateLogin(User user) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement psmt = con.prepareStatement(VALIDATE_USER);
        psmt.setString(1, user.getName());
        psmt.setString(2, user.getEmail());
        psmt.setString(3, ScanUtils.buildHash(user.getPassword()));
        ResultSet rs = psmt.executeQuery();
        return rs.next();
    }
    public static List<User> listUsers() throws SQLException {
    	Connection con = DatabaseConnection.getConnection();
    	List<User> userList = new ArrayList<>();
    	PreparedStatement psmt = con.prepareStatement(SELECT_ALL);
    	ResultSet rs  = psmt.executeQuery();
		while(rs.next()) {
			String name = rs.getString("name");
			String email = rs.getString("email");
			String password = rs.getString("password");
			Timestamp creationDate = rs.getTimestamp("creationDate");
			User user = new User(name, email, password, creationDate);
			userList.add(user);
		}
		return userList;
    }
}
