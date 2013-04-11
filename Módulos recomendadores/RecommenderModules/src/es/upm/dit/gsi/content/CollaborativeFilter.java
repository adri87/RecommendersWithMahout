package es.upm.dit.gsi.content;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.content.Calcules;
import es.upm.dit.gsi.content.Configuration;
import es.upm.dit.gsi.logger.Logger;

public class CollaborativeFilter {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("content.CollaborativeFilter");
	
	/**
	 * public static int getRecomendationsCF(int user)
	 * Calculate the object most indicated for user.
	 *  
	 * @param user, the user to recommended
	 * @return itemRecom, recommended object
	 */
	public static int getRecomendationCF(int user){
		int item = 0;
		double punt = 0; // rating for a product
		double maxPunt = 0; // the product that had more rating
		int itemRecom = 0;
		
		try {
			String selectStatement  = "SELECT DISTINCT id_item FROM cnttable";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		item = res.getInt("id_item");
        		if (!Calcules.haveGot(user, item)){
        			punt = predProd(user, item); 
        			if (punt > maxPunt){ //See if the product score is higher than the previous most recommended
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
	
	/**
	 * public static double predProd (int user, int item)
	 * Obtained the rating for a product that the user don´t have it.
	 * 
	 * @param user
	 * @param item
	 * @return the prediction of the rating for the product
	 */
	public static double predProd (int user, int item){
		double predRating = 0;
		double num = 0;
		double den = 0;
		int otherUser;
		
		try {
			String selectStatement = "SELECT DISTINCT id_user FROM cnttable";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			ResultSet res = prepStmt.executeQuery();
			while (res.next()){
				otherUser = res.getInt("id_user");       	// La suma siguiente esta hecha con todo el conjunto de usuarios
				if (otherUser != user) {					// Deberiamos hacerla con solo los k usuarios mas similares al nuestro
					num += similarityUsers(user, otherUser)*(Calcules.getRatingUserForProduct(otherUser, item) - Calcules.averageRatingUser(otherUser));
					den += Math.abs(similarityUsers(user, otherUser)); // sum of absolute value of the similarity between all users and our user.
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
	
	/**
	 * public static double similarityUsers(int user1, int user2) throws SQLException
	 * The similarity between two users, in fuction of their ratings.
	 * 
	 * @param user1
	 * @param user2
	 * @return sim, How similar are the two users
	 * @throws SQLException 
	 */
	public static double similarityUsers(int user1, int user2) throws SQLException{
		double sim = 0;
		int item = 0;
		
		if (user1 == user2){
			sim=1;
		} else {
			if (Datos.hasRated(user1) == false | Datos.hasRated(user2)== false){
				sim = 0;
			} else {
				try{
					String selectStatement = "SELECT DISTINCT id_item FROM cnttable";
					PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
					ResultSet res = prepStmt.executeQuery();
					while (res.next()){
						item = res.getInt("id_item");
						sim += sumNum(item, user1, user2);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.warning("Error");
				}
				sim = sim/(varUser(user1)*varUser(user2));
			}
		}
		sim = (sim+Relations.sim(user1, user2))/2; // Ponderation between the similarity caused by the products´ ratings and the relations similarity
		return sim;
	}
	
	/**
	 * public static double sumNum(int item, int user1, int user2)
	 * Implements the numerator sum of the similarity algorithm.
	 * 
	 * @param item
	 * @param user1
	 * @param user2
	 * @return
	 */
	public static double sumNum(int item, int user1, int user2){
		
		double ratUser1 = Calcules.getRatingUserForProduct(user1, item);
		double ratUser2 = Calcules.getRatingUserForProduct(user2, item);
		double sum = (ratUser1 - Calcules.averageRatingUser(user1))*(ratUser2 - Calcules.averageRatingUser(user2));
		
		return sum;
	}
	
	/**
	 * public static double varUser(int user)
	 * Obtain the user´s rating variance.
	 * 
	 * @param user
	 * @return user´s variance
	 */
	public static double varUser(int user){
		int item;
		double sum = 0;
		
		try{
        	String selectStatement = "SELECT DISTINCT id_item FROM cnttable";
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
