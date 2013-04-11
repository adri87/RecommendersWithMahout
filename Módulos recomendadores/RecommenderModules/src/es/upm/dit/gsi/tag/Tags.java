package es.upm.dit.gsi.tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;

public class Tags {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("tag.Tags");
	
	public static void setTag(String tag) throws SQLException{
			String insertStatement = "INSERT INTO tags (tag) VALUES (?)";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(insertStatement);
	    	prepStmt.setString(1, tag);
	    	prepStmt.executeUpdate();
	    	log.info("Tag metido");
	}
	
	public static int getIdTag(String tag){
		int id = 0;
		
		try {
			String selectStatement = "SELECT id FROM tags WHERE tag = ?";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			prepStmt.setString(1, tag);
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
	
	public static String getTag (int id){
		String tag = "";
		
		try {
			String selectStatement = "SELECT tag FROM tags WHERE id = ?";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			prepStmt.setInt(1, id);
			ResultSet res = prepStmt.executeQuery();
			while (res.next()){
        		tag = res.getString("tag");	
        	}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("Error");
		}
		
		return tag;
	}
	
	public static void setTags(){
		try{
			String selectStatement = "SELECT DISTINCT tag FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		String tag = res.getString("tag");
        		setTag(tag);
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
	}

}
