package es.upm.dit.gsi.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;
import es.upm.dit.gsi.connection.Configuration;

public class Books {
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();

	private static final Logger LOGGER = Logger.getLogger("jdbc.Books");
	
	/**
	 * Introduce un nuevo libro en la base de datos y le asigna un
	 * identificador con el cuál se le asociará a partir de ahora.
	 * También obtiene el identificar si ya esta registrado.
	 * 
	 * @param gesforBookId 
	 * @return bookId
	 */
	public static long bookIdentifier (String gesforBookId){
		Long bookId=null;
		
		try {

			String selectStatement = "SELECT id FROM books WHERE identifier = ? ";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
	    	prepStmt.setString(1, gesforBookId);
	    	ResultSet res = prepStmt.executeQuery();
	    	
	    	// Si el libro ha sido ya introducido en la base de datos.
	    	if (res.next()){
	    		bookId = res.getLong("id");
	    		LOGGER.info("El identificador asociado al libro " +gesforBookId+ " es: " + bookId);
	    	// Si es la primera vez que aparece el libro.	
	    	} else {
	    		selectStatement = "INSERT INTO books (identifier) VALUES (?)";
				prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
		    	prepStmt.setString(1, gesforBookId);
		    	prepStmt.executeUpdate();
	    		LOGGER.info("Se ha introducido un nuevo libro");
	    		
	    		selectStatement = "SELECT id FROM books WHERE identifier = ? ";
				prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
		    	prepStmt.setString(1, gesforBookId);
		    	res = prepStmt.executeQuery();
		    	if (res.next()){
		    		bookId = res.getLong("id");
		    		LOGGER.info("El identificador asociado al libro " +gesforBookId+ " es: " + bookId);
		    	} else {
		    		LOGGER.severe("No deberíamos llegar aqui FALLO");
		    	}
	    	}
	    
		} catch (Exception e) {
	    	e.printStackTrace();
		}
		return bookId;
	}
	
	/**
	 * Nos devuelve el identificador asociado a un libro ya registrado.
	 * 
	 * @param gesforBookId
	 * @return bookId
	 */
	
	public static Long getBookId (String gesforBookId) {
		Long bookId=null;
		
		try {
			String selectStatement = "SELECT id FROM books WHERE identifier = ? ";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
	    	prepStmt.setString(1, gesforBookId);
	    	ResultSet res = prepStmt.executeQuery();
	    	
	    	if (res.next()){
	    		bookId = res.getLong("id");
	    		LOGGER.info("El identificador asociado al libro " +gesforBookId+ " es: " + bookId);
	    	} else {
	    		LOGGER.warning("No existe el libro seleccionado");
	    	}
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return bookId;
	}
	
	/**
	 * Nos devuelve el nombre de un determinado libro a 
	 * partir de su identificador.
	 * 
	 * @param bookId
	 * @return gesforBookId
	 */
	public static String getGesforBookId (Long bookId) {
		String gesforBookId="";

		try {
			String selectStatement = "SELECT identifier FROM books WHERE id = ? ";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
	    	prepStmt.setLong(1, bookId);
	    	ResultSet res = prepStmt.executeQuery();
	    	
	    	if (res.next()){
	    		gesforBookId = res.getString("identifier");
	    	} else {
	    		LOGGER.warning("No existe ningún libro con este identificador");
	    	}
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }	
		return gesforBookId;
	}
}