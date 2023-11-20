package com.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.model.Scans;


public class ScanDAO {
    private static final String SELECT_BY_HASH = "SELECT * FROM scans WHERE sha256=?";
    private static final String INSERT_INTO_TABLE = "INSERT INTO scans VALUES (?,?,?,?,?,?)";
    private static final String COPY_SCAN = "INSERT INTO scans (fileName, uploadedBy, sha256, analysis, scanDate, json) SELECT ?, ?, sha256, analysis, ?, json FROM scans WHERE sha256 = ? LIMIT 1";
    private static final String SELECT_ALL_SCANS = "SELECT * FROM scans";
    private static final String SELECT_ALL_SCANS_BY_NAME = "SELECT * FROM scans WHERE uploadedBy=?";
    private static final String SELECT_JSON = "SELECT json FROM scans WHERE sha256=?";
    private static final String DELETE_BY_DATE = "DELETE FROM scans where scanDate=?";
    private static final String INSERT_FILE = "INSERT INTO files VALUES(?,?)";

    
    public static void copyScan(String hash, String uploadedBy, String fileName) throws SQLException {
    	Connection con = DatabaseConnection.getConnection();
    	PreparedStatement psmt = con.prepareStatement(COPY_SCAN);
    	 LocalDateTime now = LocalDateTime.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         Timestamp currDate = Timestamp.valueOf(formatter.format(now));
         psmt.setString(1, fileName);
         psmt.setString(2, uploadedBy);
         psmt.setTimestamp(3, currDate);
         psmt.setString(4, hash);
         int rowsInserted = psmt.executeUpdate();
         if (rowsInserted > 0) {
             System.out.println("Inserted!");
         }
    }
    
    
    public  static void  insertScan(String fileName, String uploadedBy, String hash, String analysis,String json) throws SQLException, IOException {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement psmt = con.prepareStatement(INSERT_INTO_TABLE);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp currDate = Timestamp.valueOf(formatter.format(now));
        psmt.setString(1, fileName);
        psmt.setString(2, uploadedBy);
        psmt.setString(3, hash);
        psmt.setString(4, analysis);
        psmt.setTimestamp(5, currDate);
        psmt.setString(6, json);
        int rowsInserted = psmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Inserted!");
        }
    }

    public  static boolean fetchResult(String hash) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement psmt = con.prepareStatement(SELECT_BY_HASH);
        psmt.setString(1, hash);
        System.out.println("Searching in DB for hash");
        ResultSet rs = psmt.executeQuery();
        return rs.next();
    }

    public  static String fetchJSON(String hash) throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement psmt = con.prepareStatement(SELECT_JSON);
        psmt.setString(1, hash);
        ResultSet rs = psmt.executeQuery();
        String data = null;
        if (rs.next()) {
            data = rs.getString("json");
        }
        return data;
    }

    public static void deleteScan(String scanDate) throws SQLException{
    	  Connection con = DatabaseConnection.getConnection();
    	  PreparedStatement psmt = con.prepareStatement(DELETE_BY_DATE);
    	  psmt.setString(1, scanDate);
    	  psmt.executeUpdate();
    	}
    
    public static void uploadFile(String uploadedBy, String fileName) throws SQLException {
    	Connection con = DatabaseConnection.getConnection();
    	PreparedStatement psmt = con.prepareStatement(INSERT_FILE);
    	psmt.setString(1, fileName);
    	psmt.setString(2, uploadedBy);
    	 int rowsInserted = psmt.executeUpdate();
         if (rowsInserted > 0) {
             System.out.println("Inserted File!");
         }
     }
    public static List<Scans> listScans() throws SQLException{
    	Connection con = DatabaseConnection.getConnection();
    	List<Scans> scanList = new ArrayList<>();
    	PreparedStatement psmt = con.prepareStatement(SELECT_ALL_SCANS);
    	ResultSet rs = psmt.executeQuery();
    	while(rs.next()) {
    		String fileName = rs.getString("fileName");
    		String uploadedBy = rs.getString("uploadedBy");
    		String sha256 = rs.getString("sha256");
    		String analysis = rs.getString("analysis");
    		Timestamp scanDate = rs.getTimestamp("scanDate");
    		Scans scan = new Scans(fileName, uploadedBy, sha256, analysis, scanDate);
    		scanList.add(scan);
    	}
    	rs.close();
    	psmt.close();
    	
		return scanList;
    }
		public static List<Scans> listScans(String name ) throws SQLException{
	    	Connection con = DatabaseConnection.getConnection();
	    	List<Scans> scanList = new ArrayList<>();
	    	PreparedStatement psmt = con.prepareStatement(SELECT_ALL_SCANS_BY_NAME);
	    	psmt.setString(1, name);
	    	ResultSet rs = psmt.executeQuery();
	    	while(rs.next()) {
	    		String fileName = rs.getString("fileName");
	    		String uploadedBy = rs.getString("uploadedBy");
	    		String sha256 = rs.getString("sha256");
	    		String analysis = rs.getString("analysis");
	    		Timestamp scanDate = rs.getTimestamp("scanDate");
	    		Scans scan = new Scans(fileName, uploadedBy, sha256, analysis, scanDate);
	    		scanList.add(scan);
	    	}
	    	rs.close();
	    	psmt.close();
	    	
			return scanList;
    }
}
