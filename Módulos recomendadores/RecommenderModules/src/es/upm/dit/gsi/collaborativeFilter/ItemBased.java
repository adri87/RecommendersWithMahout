package es.upm.dit.gsi.collaborativeFilter;

import java.sql.Connection;
import java.sql.ResultSet;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;

public class ItemBased {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("collaborativeFilter.ItemBased");
	
	public static int getRecomendationIB(int user){
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
		int otherItem;
		
		try {
			String selectStatement = "SELECT DISTINCT id_item FROM cftable";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			ResultSet res = prepStmt.executeQuery();
			while (res.next()){
				otherItem = res.getInt("id_item");       	// La suma siguiente esta hecha con todo el conjunto de usuarios
				if (otherItem != item) {					// Deberiamos hacerla con solo los k objetos mas similares al nuestro
					num += similarityItems(item, otherItem)*Calcules.getRatingUserForProduct(user, otherItem);
					den += similarityItems(item,otherItem);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log.warning("Error");
		}
		
		predRating = num/den;
		log.info("ITEM: "+item+" VALUE: "+predRating);
		
		return predRating;
		
	}
	
	public static double similarityItems(int item1, int item2){
		double sim = 0;
		int user = 0;
		double varItem1 = 0;
		double varItem2 = 0;
		
		try{
        	String selectStatement = "SELECT DISTINCT id_user FROM cftable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		user = res.getInt("id_user");
        		// si no se ha usado antes este item
        		sim += sumaNumerador(user, item1, item2);
        		varItem1 += varUser(user,item1);
        		varItem2 += varUser(user, item2);
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		
		varItem1 = Math.sqrt(varItem1);
		varItem2 = Math.sqrt(varItem2);
		sim = sim/(varItem1*varItem2);
		
		return sim;
	}
	
	public static double sumaNumerador(int user, int item1, int item2){
		
		double ratItem1 = Calcules.getRatingUserForProduct(user, item1);
		double ratItem2 = Calcules.getRatingUserForProduct(user, item2);
		double sum = (ratItem1 - Calcules.averageRatingUser(user))*(ratItem2 - Calcules.averageRatingUser(user));
		
		return sum;
	}
	
	public static double varUser(int user, int item){
		
		double aux =  Math.pow(Calcules.getRatingUserForProduct(user, item) - Calcules.averageRatingUser(user), 2);
		
		return aux;
	}
}
