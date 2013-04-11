package es.upm.dit.gsi.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;
import es.upm.dit.gsi.connection.Configuration;

public class Authors {
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();

	private static final Logger LOGGER = Logger.getLogger("jdbc.Authors");
	
	/**
	 * Introduce un nuevo autor en la base de datos y le asigna un
	 * identificador con el cuál se le asociará a partir de ahora.
	 * También obtiene el identificar si ya esta registrado.
	 * 
	 * @param gesforAuthorId 
	 * @return authorId
	 */
	public static long authorIdentifier (String gesforAuthorId){
		Long authorId=null;
		
		try {

			String selectStatement = "SELECT id FROM authors WHERE identifier = ? ";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
	    	prepStmt.setString(1, gesforAuthorId);
	    	ResultSet res = prepStmt.executeQuery();
	    	
	    	// Si el autor ha sido ya introducido en la base de datos.
	    	if (res.next()){
	    		authorId = res.getLong("id");
	    		LOGGER.info("El identificador asociado al autor " +gesforAuthorId+ " es: " + authorId);
	    	// Si es la primera vez que aparece el artículo.	
	    	} else {
	    		selectStatement = "INSERT INTO authors (identifier) VALUES (?)";
				prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
		    	prepStmt.setString(1, gesforAuthorId);
		    	prepStmt.executeUpdate();
	    		LOGGER.info("Se ha introducido un nuevo autor");
	    		
	    		selectStatement = "SELECT id FROM authors WHERE identifier = ? ";
				prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
		    	prepStmt.setString(1, gesforAuthorId);
		    	res = prepStmt.executeQuery();
		    	if (res.next()){
		    		authorId = res.getLong("id");
		    		LOGGER.info("El identificador asociado al autor " +gesforAuthorId+ " es: " + authorId);
		    	} else {
		    		LOGGER.severe("No deberíamos llegar aqui FALLO");
		    	}
	    	}
	    
		} catch (Exception e) {
	    	e.printStackTrace();
		}
		return authorId;
	}
	
	/**
	 * Nos devuelve el identificador asociado a un autor ya registrado.
	 * 
	 * @param gesforAuthorId
	 * @return authorId
	 */
	
	public static Long getAuthorId (String gesforAuthorId) {
		Long authorId=null;
		
		try {
			String selectStatement = "SELECT id FROM authors WHERE identifier = ? ";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
	    	prepStmt.setString(1, gesforAuthorId);
	    	ResultSet res = prepStmt.executeQuery();
	    	
	    	if (res.next()){
	    		authorId = res.getLong("id");
	    		LOGGER.info("El identificador asociado al autor " +gesforAuthorId+ " es: " + authorId);
	    	} else {
	    		LOGGER.warning("No existe el autor seleccionado");
	    	}
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return authorId;
	}
	
	/**
	 * Nos devuelve el nombre de un determinado autor a 
	 * partir de su identificador.
	 * 
	 * @param authorId
	 * @return gesforAuthorId
	 */
	public static String getGesforAuthorId (Long authorId) {
		String gesforAuthorId="";

		try {
			String selectStatement = "SELECT identifier FROM authors WHERE id = ? ";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
	    	prepStmt.setLong(1, authorId);
	    	ResultSet res = prepStmt.executeQuery();
	    	
	    	if (res.next()){
	    		gesforAuthorId = res.getString("identifier");
	     	} else {
	    		LOGGER.warning("No existe ningún autor con este identificador");
	    	}
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }	
		return gesforAuthorId;
	}
}