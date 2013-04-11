package es.upm.dit.gsi.collaborativeFilter;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;
import es.upm.dit.gsi.collaborativeFilter.Configuration;

public class Datos {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("collaborativeFilter.Datos");
	
	public static void cargaDatos() throws SQLException {
		
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
		
    	
    	log.info("Los datos se han introducido correctamente");
	}
	
	public static void rellBasDat (int user, int item, double rating) throws SQLException{
		String insertStatement = "INSERT INTO cftable (id_user, id_item, rating) VALUES (?, ?, ?)";
		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(insertStatement);
    	prepStmt.setInt(1, user);
    	prepStmt.setInt(2, item);
    	prepStmt.setDouble(3, rating);
    	prepStmt.executeUpdate();
	}
}
