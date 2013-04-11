package es.upm.dit.gsi.tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;

public class Datos extends TagRecommender {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("tag.Datos");
	
	public static void cargaDatos() throws SQLException {
		
		rellBasDat ("Usuario1", "Tag1", "Producto1");
		rellBasDat ("Usuario1", "Tag1", "Producto2");
		rellBasDat ("Usuario1", "Tag1", "Producto3");
		rellBasDat ("Usuario2", "Tag1", "Producto2");
		rellBasDat ("Usuario2", "Tag2", "Producto4");
		rellBasDat ("Usuario2", "Tag2", "Producto7");
		rellBasDat ("Usuario2", "Tag2", "Producto9");
		rellBasDat ("Usuario2", "Tag2", "Producto1");
		rellBasDat ("Usuario2", "Tag3", "Producto6");
		rellBasDat ("Usuario2", "Tag3", "Producto3");
		rellBasDat ("Usuario2", "Tag4", "Producto5");
		rellBasDat ("Usuario3", "Tag1", "Producto1");
		rellBasDat ("Usuario3", "Tag1", "Producto5");
		rellBasDat ("Usuario3", "Tag2", "Producto1");
		rellBasDat ("Usuario3", "Tag2", "Producto5");
		rellBasDat ("Usuario3", "Tag2", "Producto7");
		rellBasDat ("Usuario3", "Tag2", "Producto9");
		rellBasDat ("Usuario3", "Tag2", "Producto4");
		rellBasDat ("Usuario4", "Tag1", "Producto1");
		rellBasDat ("Usuario4", "Tag1", "Producto3");
		rellBasDat ("Usuario4", "Tag1", "Producto5");
		rellBasDat ("Usuario4", "Tag1", "Producto2");
		rellBasDat ("Usuario4", "Tag2", "Producto4");
		rellBasDat ("Usuario4", "Tag3", "Producto2");
		rellBasDat ("Usuario4", "Tag3", "Producto6");
		rellBasDat ("Usuario4", "Tag4", "Producto5");
		rellBasDat ("Usuario4", "Tag4", "Producto2");
		rellBasDat ("Usuario4", "Tag4", "Producto7");
		
		Users.setUsers();
		Products.setProducts();
		Tags.setTags();
    	
		dataModel();
    	log.info("Los datos se han introducido correctamente");
    	
	}
	
	public static void rellBasDat (String user, String tag, String product) throws SQLException{
		String insertStatement = "INSERT INTO tagtable (user, tag, product) VALUES (?, ?, ?)";
		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(insertStatement);
    	prepStmt.setString(1, user);
    	prepStmt.setString(2, tag);
    	prepStmt.setString(3, product);
    	prepStmt.executeUpdate();
	}
	
	public static void rellenaArrayUsuario (int vector1[], String us){
		int i = 0;
		String selectStatement = "SELECT user FROM tagtable WHERE tag = ?";
		
		for (int j=0; j<vector1.length; j++) {			
			try {
				PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
				prepStmt.setString(1, Tags.getTag(hashIndexToTags.get(j)));
				ResultSet res = prepStmt.executeQuery();
	    		while (res.next()){
	    			String user = res.getString("user");
	    			if (user.equals(us))
	    				i++;
	    		}
	    		vector1[j] = i;
	    		i=0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void rellenaArrayTag (int vector1[], String tag){
		int i = 0;
		
		for (int j=0; j<vector1.length; j++) {			
			try {
				String selectStatement = "SELECT tag FROM tagtable WHERE product = ?";
				PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
				prepStmt.setString(1, Products.getProduct(hashIndexToProduct.get(j)));
				ResultSet res = prepStmt.executeQuery();
	    		while (res.next()){
	    			String tg = res.getString("tag");
	    			if (tg.equals(tag))
	    				i++;
	    		}
	    		vector1[j] = i;
	    		i=0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static String getTag (String user, String product){
    	String tag = "";
    	int i =0;
    	
		try{
        	String selectStatement = "SELECT tag FROM tagtable WHERE user= ? AND product = ?";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
    		prepStmt.setString(1, user);
    		prepStmt.setString(2, product);
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		tag = res.getString("tag");
        		i++;
        	}
        } catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		return tag;
	}
	
	
	public static void dataModel (){
		int numTag = numTags();
		usuarios = new int [numUsers()][numTag];
		tags = new int [numTag][numProducts()];
		int i = 0;
		String user;
		String tag;
		String product;
		
		try{
			String selectStatement = "SELECT DISTINCT product FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		product = res.getString("product");
        		hashIndexToProduct.put(i, Products.getIdProduct(product));
        		i++;
        	}
        	i = 0;
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		
		try{
			String selectStatement = "SELECT DISTINCT tag FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		tag = res.getString("tag");
        		hashTagsToIndex.put(Tags.getIdTag(tag), i);
        		hashIndexToTags.put(i, Tags.getIdTag(tag));
        		rellenaArrayTag (tags[i], tag);
        		i++;
        	}
        	i = 0;
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		
		try{
			String selectStatement = "SELECT DISTINCT user FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		user = res.getString("user");
        		hashUsersToIndex.put(Users.getIdUser(user), i);
        		hashIndexToUsers.put(i, Users.getIdUser(user));
        		rellenaArrayUsuario (usuarios[i], user);
        		i++;
        	}
        	i=0;
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
	}
	
	public static int numProducts(){
		int num = 0;
		
		try{
			String selectStatement = "SELECT product FROM products";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		num++;
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		
		return num;
	}
	
	public static int numUsers(){
		int num = 0;
		
		try{
			String selectStatement = "SELECT user FROM users";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		num++;
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		
		return num;
	}
	
	public static int numTags(){
		int num = 0;
		
		try{
			String selectStatement = "SELECT tag FROM tags";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		num++;
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		
		return num;
	}
	
	public static void delBasDat() throws SQLException{
		//Borra en la base de datos la tabla principal
		String selectStatement = "DELETE FROM tagtable";
		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
		prepStmt.executeUpdate();
		
		// Borra en la base de datos la tabla de usuarios
		String selectStatement2 = "DELETE FROM users";
		PreparedStatement prepStmt2 = (PreparedStatement) con.prepareStatement(selectStatement2);	
		prepStmt2.executeUpdate();
		
		// Borra en la base de datos la tabla de productos
		String selectStatement3 = "DELETE FROM products";
		PreparedStatement prepStmt3 = (PreparedStatement) con.prepareStatement(selectStatement3);	
		prepStmt3.executeUpdate();
		
		// Borra en la base de datos la tabla de tags
		String selectStatement4 = "DELETE FROM tags";
		PreparedStatement prepStmt4 = (PreparedStatement) con.prepareStatement(selectStatement4);	
		prepStmt4.executeUpdate();
		
		log.info("Bases de datos borradas");
	}
		
}
