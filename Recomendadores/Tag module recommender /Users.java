package es.upm.dit.gsi.tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;

public class Users {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("tag.Users");
	
	public static void setUser(String user) throws SQLException{
			String insertStatement = "INSERT INTO users (user) VALUES (?)";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(insertStatement);
	    	prepStmt.setString(1, user);
	    	prepStmt.executeUpdate();
	    	log.info("Usuario metido");
	}
	
	public static int getIdUser(String user){
		int id = 0;
		
		try {
			String selectStatement = "SELECT id FROM users WHERE user = ?";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			prepStmt.setString(1, user);
			ResultSet res = prepStmt.executeQuery();
			while (res.next()){
        		id = res.getInt("id");
        	}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("Error");
		}
		
		return id;
	}
	
	public static String getUser (int id){
		String user = "";
		
		try {
			String selectStatement = "SELECT user FROM users WHERE id = ?";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			prepStmt.setInt(1, id);
			ResultSet res = prepStmt.executeQuery();
			while (res.next()){
        		user = res.getString("user");	
        	}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("Error");
		}
		
		return user;
	}

}
