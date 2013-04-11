package es.upm.dit.gsi.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import es.upm.dit.gsi.logger.Logger;

public class Configuration {
	private Connection dbCon;
	private MysqlDataSource dataSource;
	private static Configuration conf;
	public static String url;
	public static String driver;
	public static String servername;
	public static String dbname;
	public static String user;
	public static String pass;
	
	
	private static final Logger LOGGER = Logger.getLogger("connection.Configuration");
	
	/**
	 * Constructor de la clase Configuration que abre la conexión 
	 * e inicia los valores por defecto tanto de la conexión con la 
	 * base de de datos como de nuesto objeto dataSource.
	 *  
	 */
	private Configuration(){
		try{
			File archivo = new File("./config/Configuracion.txt");
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			url = br.readLine();
			driver = br.readLine();
			servername = br.readLine();
			dbname = br.readLine();
			user = br.readLine();
			pass = br.readLine();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		dataSource = new MysqlDataSource();
		dataSource.setServerName(servername);
		dataSource.setDatabaseName(dbname);
		dataSource.setUser(user);
		dataSource.setPassword(pass);
		LOGGER.info("Establecida conexión de datos a través de Mysql");
		
		try {
			Class.forName(driver).newInstance();
		} catch (Exception e) {
			System.out.println("Driver MySQL not load");
		}
		setDbCon(url, user, pass);
		LOGGER.info("Se ha abierto la conexión con la base de datos");
	}
	
	/**
	 * Nos devuelve el objeto configuración que contiene los datos
	 * de la base de datos que estamos utilizando.
	 * 
	 * @return conf
	 */
	 public static Configuration getConfiguration(){
	      if (conf == null)
	          conf = new Configuration();
	      return conf;
	 }
	 
	
	/**
	 * Nos devuelve el objeto dataSource de nuestro recomendador.
	 * 
	 * @return dataSource
	 */
	public MysqlDataSource getDataSource(){
		return dataSource;
	}
	
	/**
	 * Configura los valores de nuestra conexión con la base de datos.
	 * 
	 */
	public void setDbCon(String url, String user, String pass){
		try {
			dbCon= DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Nos devuelve nuestro objeto conexión asociado a la base 
	 * de datos que estamos usando.
	 * 
	 * @return dbCon
	 */
	public Connection getDbCon(){
		return dbCon;
	}
	
}
