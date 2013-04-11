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
		//String tag = "";prod
		String selectStatement = "SELECT user FROM tagtable WHERE tag = ?";
		
		for (int j=0; j<vector1.length; j++) {
			/*if (j==0) tag = "Tag1";
			if (j==1) tag = "Tag2";
			if (j==2) tag = "Tag3";
			if (j==3) tag = "Tag4";*/
			
			try {
				PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
				prepStmt.setString(1, Tags.getTag(j+1));
				ResultSet res = prepStmt.executeQuery();
	    		while (res.next()){
	    			String user = res.getString("user");
	    			if (user.equals(us))
	    				i++;
	    		}
	    		vector1[j] = i;
	    		i=0;
	    		//log.info("El " + us + " usa el " + tag + ": " + vector1[j] +" veces"); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void rellenaArrayTag (int vector1[], String tag){
		int i = 0;
		//String product = "";
		String selectStatement = "SELECT tag FROM tagtable WHERE product = ?";
		
		
		
		for (int j=0; j<vector1.length; j++) {
			/*if (j==0) product = "Producto1";
			if (j==1) product = "Producto2";
			if (j==2) product = "Producto3";
			if (j==3) product = "Producto4";
			if (j==4) product = "Producto5";
			if (j==5) product = "Producto6";
			if (j==6) product = "Producto7";
			if (j==7) product = "Producto8";
			if (j==8) product = "Producto9";*/
			
			try {
				PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
				prepStmt.setString(1, Products.getProduct(j+1));
				ResultSet res = prepStmt.executeQuery();
	    		while (res.next()){
	    			String tg = res.getString("tag");
	    			if (tg.equals(tag))
	    				i++;
	    		}
	    		vector1[j] = i;
	    		i=0;
	    		//log.info("El " + tag + " es usado para el " + product + ": " + vector1[j] +" veces"); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getTag (String user, String product){
    	String tag = "";
		try{
        	String selectStatement = "SELECT tag FROM tagtable WHERE user= ? AND product = ?";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
    		prepStmt.setString(1, user);
    		prepStmt.setString(2, product);
        	ResultSet res = prepStmt.executeQuery();
        		while (res.next()){
        			tag = res.getString("tag");
        			//log.info("El tag es " + tag);
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
		
		try{
			String selectStatement = "SELECT DISTINCT tag FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		tag = res.getString("tag");
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
		String producto;
		
		try{
			String selectStatement = "SELECT DISTINCT product FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		producto = res.getString("product");
        		Products.setProduct(producto);
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
		String user;
		
		try{
			String selectStatement = "SELECT DISTINCT user FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		user = res.getString("user");
        		Users.setUser(user);
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
		String tag;
		
		try{
			String selectStatement = "SELECT DISTINCT tag FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		tag = res.getString("tag");
        		Tags.setTag(tag);
        		num++;
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
		
		return num;
	}
}
