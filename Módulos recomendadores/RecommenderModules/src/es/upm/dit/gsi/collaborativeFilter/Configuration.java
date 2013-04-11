package es.upm.dit.gsi.collaborativeFilter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import es.upm.dit.gsi.logger.Logger;

public class Configuration {
	private Connection dbCon;
	private MysqlDataSource dataSource;
	private static Configuration conf;
	public static String url = "jdbc:mysql://localhost:3306/cfmodule";
	public static String driver = "com.mysql.jdbc.Driver";
	public static String servername = "localhost";
	public static String dbname = "cfmodule";
	public static String user = "cfu";
	public static String pass = "cfp";
	
	private static final Logger log = Logger.getLogger("collaborativeFilter.Configuration");
	
	/**
	 * Configuration class constructor that opens the connection
	 * and starts the default connection both with
	 * data base as a member of our dataSource object. 
	 */
	private Configuration(){
		dataSource = new MysqlDataSource();
		dataSource.setServerName(servername);
		dataSource.setDatabaseName(dbname);
		dataSource.setUser(user);
		dataSource.setPassword(pass);
		log.info("Establecida conexión de datos a través de Mysql");
		
		try {
			Class.forName(driver).newInstance();
		} catch (Exception e) {
			System.out.println("Driver MySQL not load");
		}
		setDbCon(url, user, pass);
		log.info("Se ha abierto la conexión con la base de datos");
		log.info("La base de datos es " + dataSource.getDatabaseName() + " " + dataSource.getUser() +
				" " + dataSource.getServerName());
	}
	
	
	/**
	 * Return the object that contains configuration data
	 * of the database.
	 * 
	 * @return conf Configuration of the database
	 */
	 public static Configuration getConfiguration(){
	      if (conf == null)
	          conf = new Configuration();
	      return conf;
	 }


	/**
	 * Return the dataSource object of our recommender.
	 * 
	 * @return dataSource
	 */
	public MysqlDataSource getDataSource(){
		return dataSource;
	}
	
	/**
	 * Configure the values of our connection with the data base.
	 * 
	 */
	public void setDbCon(String url, String user, String pass) {
		try {
			dbCon= DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return our connection object associated with the base
	 * data that we use.
	 * 
	 * @return dbCon
	 * @throws SQLException 
	 */
	public Connection getDbCon() {
		return dbCon;
	}
	
}

