package es.upm.dit.gsi.collaborativeFilter;

import java.sql.Connection;
import java.sql.ResultSet;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;

public class UserBased {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("collaborativeFilter.UserBased");
	
	public static int getRecomendationUB(int user){
		int item = 0;
		double punt = 0;
		double maxPunt = 0;
		int itemRecom = 0;
		
		try {
			String selectStatement  = "SELECT DISTINCT id_item FROM cftable";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		item = res.getInt("id_item");
        		if (!Calcules.haveGot(user, item)){
        			punt = predProd(user, item);
        			if (punt > maxPunt){
        				maxPunt = punt;
        				itemRecom = item;
        			}
        		}
        	}
		} catch (Exception e) {
			e.printStackTrace();
			log.warning("Error");
		}
		
		return itemRecom;
		
	}
	
	public static double predProd (int user, int item){
		double predRating = 0;
		double num = 0;
		double den = 0;
		int otherUser;
		
		try {
			String selectStatement = "SELECT DISTINCT id_user FROM cftable";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			ResultSet res = prepStmt.executeQuery();
			while (res.next()){
				otherUser = res.getInt("id_user");       	// La suma siguiente esta hecha con todo el conjunto de usuarios
				if (otherUser != user) {					// Deberiamos hacerla con solo los k usuarios mas similares al nuestro
					num += similarityUsers(user, otherUser)*(Calcules.getRatingUserForProduct(otherUser, item) - Calcules.averageRatingUser(otherUser));
					den += Math.abs(similarityUsers(user, otherUser));
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log.warning("Error");
		}
		
		predRating = Calcules.averageRatingUser(user) + (num/den);
		log.info("ITEM: "+item+" VALUE: "+predRating);
		
		return predRating;
		
	}
	
	public static double similarityUsers(int user1, int user2){
		double sim = 0;
		int item = 0;
		
		try{
        	String selectStatement = "SELECT DISTINCT id_item FROM cftable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		item = res.getInt("id_item");
        		// si no se ha usado antes este item
        		sim += sumaNumerador(item, user1, user2);
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		
		sim = sim/(varUser(user1)*varUser(user2));
		
		return sim;
	}
	
	public static double sumaNumerador(int item, int user1, int user2){
		
		double ratUser1 = Calcules.getRatingUserForProduct(user1, item);
		double ratUser2 = Calcules.getRatingUserForProduct(user2, item);
		double sum = (ratUser1 - Calcules.averageRatingUser(user1))*(ratUser2 - Calcules.averageRatingUser(user2));
		
		return sum;
	}
	
	public static double varUser(int user){
		int item;
		double sum = 0;
		
		try{
        	String selectStatement = "SELECT DISTINCT id_item FROM cftable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		item = res.getInt("id_item");
        		sum +=  Math.pow(Calcules.getRatingUserForProduct(user, item) - Calcules.averageRatingUser(user), 2);
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		
		return Math.sqrt(sum);
	}
}
