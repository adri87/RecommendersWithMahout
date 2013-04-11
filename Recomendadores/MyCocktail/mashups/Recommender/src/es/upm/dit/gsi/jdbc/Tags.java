package es.upm.dit.gsi.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;
import es.upm.dit.gsi.connection.Configuration;

public class Tags {
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();

	private static final Logger LOGGER = Logger.getLogger("jdbc.Tags");
	
	/**
	 * Introduce una nueva etiqueta en la base de datos y le asigna un
	 * identificador con el cuál se le asociará a partir de ahora.
	 * También obtiene el identificar si ya esta registrado.
	 * 
	 * @param gesforTagId 
	 * @return tagId
	 */
	public static long tagIdentifier (String gesforTagId){
		Long tagId=null;
		
		try {

			String selectStatement = "SELECT id FROM tags WHERE identifier = ? ";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
	    	prepStmt.setString(1, gesforTagId);
	    	ResultSet res = prepStmt.executeQuery();
	    	
	    	// Si la etiqueta ha sido ya introducida en la base de datos.
	    	if (res.next()){
	    		tagId = res.getLong("id");
	    		LOGGER.info("El identificador asociada a la etiqueta " +gesforTagId+ " es: " + tagId);
	    	// Si es la primera vez que aparece la etiqueta.	
	    	} else {
	    		selectStatement = "INSERT INTO tags (identifier) VALUES (?)";
				prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
		    	prepStmt.setString(1, gesforTagId);
		    	prepStmt.executeUpdate();
	    		LOGGER.info("Se ha introducidoa un nueva etiqueta");
	    		
	    		selectStatement = "SELECT id FROM tags WHERE identifier = ? ";
				prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
		    	prepStmt.setString(1, gesforTagId);
		    	res = prepStmt.executeQuery();
		    	if (res.next()){
		    		tagId = res.getLong("id");
		    		LOGGER.info("El identificador asociado a la etiqueta " +gesforTagId+ " es: " + tagId);
		    	} else {
		    		LOGGER.severe("No deberíamos llegar aqui FALLO");
		    	}
	    	}
	    
		} catch (Exception e) {
	    	e.printStackTrace();
		}
		return tagId;
	}
	
	/**
	 * Nos devuelve el identificador asociado a una etiqueta ya registrada.
	 * 
	 * @param gesforTagId
	 * @return tagId
	 */
	
	public static Long getTagId (String gesforTagId) {
		Long tagId=null;
		
		try {
			String selectStatement = "SELECT id FROM tags WHERE identifier = ? ";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
	    	prepStmt.setString(1, gesforTagId);
	    	ResultSet res = prepStmt.executeQuery();
	    	
	    	if (res.next()){
	    		tagId = res.getLong("id");
	    		LOGGER.info("El identificador asociado a la etiqueta " +gesforTagId+ " es: " + tagId);
	    	} else {
	    		LOGGER.warning("No existe la etiqueta seleccionada");
	    	}
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return tagId;
	}
	
	/**
	 * Nos devuelve el nombre de una determinada etiqueta a 
	 * partir de su identificador.
	 * 
	 * @param tagId
	 * @return gesforTagId
	 */
	public static String getGesforTagId (Long tagId) {
		String gesforTagId="";

		try {
			String selectStatement = "SELECT identifier FROM tags WHERE id = ? ";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
	    	prepStmt.setLong(1, tagId);
	    	ResultSet res = prepStmt.executeQuery();
	    	
	    	if (res.next()){
	    		gesforTagId = res.getString("identifier");
	       	} else {
	    		LOGGER.warning("No existe ninguna etiqueta con este identificador");
	    	}
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }	
		return gesforTagId;
	}
}