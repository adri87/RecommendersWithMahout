package es.upm.dit.gsi.collaborativeFilter;

import java.sql.Connection;
import java.sql.ResultSet;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;

public class Calcules {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("collaborativeFilter.Calcules");
	
	public static double averageRatingUser (int user){
		double av = 0;
		int contador = 0;
		double aux = 0;
		
		try{
        	String selectStatement = "SELECT rating FROM cftable WHERE id_user= ?";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
    		prepStmt.setInt(1, user);
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		aux += res.getInt("rating");
        		contador++;
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		
		av = aux/contador; 
		
		return av;
	}
	
	public static double getRatingUserForProduct(int user, int item){
		double rating = 0;
		
		try {
			String selectStatement  = "SELECT rating FROM cftable WHERE id_user= ? AND id_item = ?";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
			prepStmt.setInt(1, user);
			prepStmt.setInt(2, item);
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		rating = res.getDouble("rating");
        	}
		} catch (Exception e){
			e.printStackTrace();
			log.warning("Error");
		}
		
		return rating;
	}
	
	public static boolean haveGot(int user, int item) {
		int product;
		
		try {
			String selectStatement = "SELECT id_item FROM cftable WHERE id_user = ?";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			prepStmt.setInt(1, user);
			ResultSet res = prepStmt.executeQuery();
			while (res.next()){
				product = res.getInt("id_item");
				if (product == item) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.warning("Error");
		}
		return false;
	}

}
