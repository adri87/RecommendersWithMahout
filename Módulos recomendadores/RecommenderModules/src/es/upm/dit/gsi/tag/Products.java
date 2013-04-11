package es.upm.dit.gsi.tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;

public class Products {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("tag.Products");
	
	public static void setProduct(String product) throws SQLException{
			String insertStatement = "INSERT INTO products (product) VALUES (?)";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(insertStatement);
	    	prepStmt.setString(1, product);
	    	prepStmt.executeUpdate();
	    	log.info("Producto metido");
	}
	
	public static int getIdProduct(String product){
		int id = 0;
		
		try {
			String selectStatement = "SELECT id FROM products WHERE product = ?";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			prepStmt.setString(1, product);
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
	
	public static String getProduct (int id){
		String product = "";
		
		try {
			String selectStatement = "SELECT product FROM products WHERE id = ?";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			prepStmt.setInt(1, id);
			ResultSet res = prepStmt.executeQuery();
			while (res.next()){
        		product = res.getString("product");	
        	}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("Error");
		}
		
		return product;
	}
	
	public static void setProducts(){
		try{
			String selectStatement = "SELECT DISTINCT product FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		String product = res.getString("product");
        		setProduct(product);
        	}
		} catch (Exception e) {
    	   	e.printStackTrace();
    	   	log.warning("Error");
    	}
	}

}
