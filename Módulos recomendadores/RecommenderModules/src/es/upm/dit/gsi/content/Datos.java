package es.upm.dit.gsi.content;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;
import es.upm.dit.gsi.content.Configuration;

public class Datos {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("content.Datos");
	
	/**
	 * public static void CargaDatos()
	 * Load rating data users about items and friendly relations between them.
	 *
	 * @throws SQLException
	 */
	public static void cargaDatos() throws SQLException {
		
		log.info("Fill the preference table");
		//Fill the preference database
		rellBasDat(1, 101, 5.0); 
		rellBasDat(1, 102, 3.0); 
		rellBasDat(1, 103, 2.5); 
		rellBasDat(2, 101, 2.0); 
		rellBasDat(2, 102, 2.5); 
		rellBasDat(2, 103, 5.0); 
		rellBasDat(2, 104, 2.0); 
		rellBasDat(3, 101, 2.5); 
		rellBasDat(3, 104, 4.0); 
		rellBasDat(3, 105, 4.5); 
		rellBasDat(3, 107, 5.0); 
		rellBasDat(4, 101, 5.0); 
		rellBasDat(4, 103, 3.0); 
		rellBasDat(4, 104, 4.5); 
		rellBasDat(4, 106, 4.0); 
		rellBasDat(5, 101, 4.0);
		rellBasDat(5, 102, 3.0); 
		rellBasDat(5, 103, 2.0); 
		rellBasDat(5, 104, 4.0); 
		rellBasDat(5, 105, 3.5);
		rellBasDat(5, 106, 4.0); 
		rellBasDat(6, 101, 3.0);
		rellBasDat(6, 104, 5.0);
		
		log.info("Fill the relations table");
		// Fill the relations database
		rellBasRel(0,12);
    	rellBasRel(1, 2);
    	rellBasRel(1, 11);
    	rellBasRel(2, 1);
    	rellBasRel(2, 3);
    	rellBasRel(2, 8);
    	rellBasRel(3, 2);
    	rellBasRel(3, 10);
    	rellBasRel(4, 5);
    	rellBasRel(4, 6);
    	rellBasRel(5, 4);
        rellBasRel(5, 7);
        rellBasRel(5, 11);
        rellBasRel(6, 4);
        rellBasRel(6, 7);
        rellBasRel(6, 10);
        rellBasRel(7, 5);
        rellBasRel(7, 6);
        rellBasRel(7, 8);
        rellBasRel(8, 2);
        rellBasRel(8, 7);
        rellBasRel(9, 10);
        rellBasRel(10, 3);
        rellBasRel(10, 6);
        rellBasRel(10, 9);
        rellBasRel(11, 1);
        rellBasRel(11, 5);
        rellBasRel(12, 0);
        
        log.info("Fill the similarity table");
        rellBasSimDist();
        rellBasSim();
        
    	log.info("The data has been introduced correctly");
	}
	
	/**
	 * public static void rellBasDat (int user, int item, double rating)
	 * Filled the preference database.
	 * 
	 * @param user
	 * @param item
	 * @param rating
	 * @throws SQLException
	 */
	public static void rellBasDat (int user, int item, double rating) throws SQLException{
		String insertStatement = "INSERT INTO cnttable (id_user, id_item, rating) VALUES (?, ?, ?)";
		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(insertStatement);
    	prepStmt.setInt(1, user);
    	prepStmt.setInt(2, item);
    	prepStmt.setDouble(3, rating);
    	prepStmt.executeUpdate();
	}
	
	/**
	 * public static void rellBasRel (int user, int friend)
	 * Filled the relations database.
	 * 
	 * @param user
	 * @param friend
	 * @throws SQLException
	 */
	public static void rellBasRel (int user, int friend) throws SQLException {
		String insertStatement = "INSERT INTO relationstable (id_user, id_friend) VALUES (?, ?)";
		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(insertStatement);
    	prepStmt.setInt(1, user);
    	prepStmt.setInt(2, friend);
    	prepStmt.executeUpdate();
	}
	
	/**
	 * public static void rellBasSim() throws SQLException
	 * Filled in the user´ similarity table, the column distance
	 * 
	 * @throws SQLException
	 */
	public static void rellBasSimDist () throws SQLException {
		String insertStatement = "INSERT INTO similarityTable (id_user1, id_user2, distance) VALUES (?, ?, ?)";
		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(insertStatement);
  		for (int i=0; i<numUsers(); i++){
  			for (int j=0; j<numUsers(); j++){
  				prepStmt.setInt(1, i);
  		    	prepStmt.setInt(2, j);
  		    	System.out.println("Usuarios "+i+" y "+j+", distancia: "+Relations.dist(i, j));
  		    	prepStmt.setDouble(3, Relations.dist(i, j));
  		    	prepStmt.executeUpdate();
  			}
  		}
		
	}
	
	/**
	 * public static void rellBasSim() throws SQLException
	 * Filled in the user´ similarity table, the column similarity
	 * 
	 * @throws SQLException
	 */
	public static void rellBasSim () throws SQLException {
		String insertStatement = "UPDATE similarityTable SET similarity = ? WHERE id_user1 = ? AND id_user2 = ?";
		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(insertStatement);
  		for (int i=0; i<numUsers(); i++){
  			for (int j=0; j<numUsers(); j++){
  				prepStmt.setInt(2, i);
  		    	prepStmt.setInt(3, j);
  		    	System.out.println("Usuarios "+i+" y "+j+", similitud: "+CollaborativeFilter.similarityUsers(i, j));
  		    	prepStmt.setDouble(1, CollaborativeFilter.similarityUsers(i, j));
  		    	prepStmt.executeUpdate();
  			}
  		}
		
	}
	
	/**
	 * public static int [] getFriends (int user)
	 * Return´s a user´s friends
	 * 
	 * @param user
	 * @return int [] friends
	 */
	public static int [] getFriends (int user) {
		int friends []= new int [numFriends(user)]; // Array of user´s friends
		int i = 0;
		try{
			String selectStatement = "SELECT DISTINCT id_friend FROM relationstable WHERE id_user = ?";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
    		prepStmt.setInt(1, user);
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		int j = res.getInt("id_friend");  // Get the id of friend
       			friends[i]=j;
       			i++;
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
		}
		return friends;
	}
	
	
	/**
	 * public static int numFriends(int user)
	 * Return´s the number of user´s friends 
	 * 
	 * @param user
	 * @return numFriends
	 */
	public static int numFriends(int user){
		int num_friends = 0;
		try{
			String selectStatement = "SELECT DISTINCT id_friend FROM relationstable WHERE id_user = ?";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
    		prepStmt.setInt(1, user);
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		num_friends++; // count the number of user´s friends
           	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
		}
		return num_friends;
	}
	
	/**
	 * public static int numUsers()
	 * Return´s the number of the users in the database
	 * 
	 * @return num_users
	 */
	public static int numUsers(){
		int num_users = 0;
		try{
			String selectStatement = "SELECT DISTINCT id_user FROM relationstable";
			//String selectStatement = "SELECT DISTINCT id_user FROM cnttable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		num_users++; // count the number of users
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
		}
		return num_users;
		
	}
	
	/**
	 * public static double getDistance(int user1, int user2)
	 * 
	 * @param user1
	 * @param user2
	 * @return distance, between two users
	 */
	public static double getDistance(int user1, int user2){
		double d = 0;
		try {
			String insertStatement = "SELECT distance FROM similarityTable WHERE id_user1=? AND id_user2=?";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(insertStatement);
			prepStmt.setInt(1, user1);
			prepStmt.setInt(2, user2);
			ResultSet res = prepStmt.executeQuery();
			while (res.next()){
				d = res.getDouble("distance");
			}
		}catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
		}
		return d;
	}
	
	/**
	 * public static boolean isFriend(int user1, int user2)
	 * Check that two users are friends
	 * 
	 * @param user1
	 * @param user2
	 * @return true if user1 is friend the user2
	 * @return false if the users don´t be friends
	 */
	public static boolean isFriend(int user1, int user2){
		int [] friendsUser1 = getFriends(user1);
		if (user1 == user2) return true;  // They are the same person
		for (int friend: friendsUser1){
			if (user2==friend) return true; // The users are friens
		}
		return false; // They aren´t friends
	}
	
	/**
	 * public static boolean hasRated(int user)
	 * Checks if a user has rated any product
	 * 
	 * @param user
	 * @return true, he has rated
	 * @return false, he hasn´t rated
	 */
	public static boolean hasRated(int user){
		boolean resp = false;
		try{
			String selectStatement = "SELECT id_item FROM cnttable WHERE id_user=?";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
    		prepStmt.setInt(1, user);
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		resp = true; 
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
		}
		return resp;
	}
	
}