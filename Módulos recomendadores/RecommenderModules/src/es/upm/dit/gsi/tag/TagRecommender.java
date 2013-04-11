package es.upm.dit.gsi.tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;

public class TagRecommender {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("tag.TagRecommender");
	
	public static int usuarios [][];
	public static int tags [][];
	
	public static HashMap<Integer, Integer>  hashUsersToIndex = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer>  hashTagsToIndex = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer>  hashIndexToUsers = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer>  hashIndexToTags = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer>  hashIndexToProduct = new HashMap<Integer, Integer>();


	
	public static double rankProd (int vectorUsuario[], String user, String prod){
		
		double simTag = 0;
		double simUsr = 0;
		double rankProd = 0;
		String tag = ""; // tag de los usuarios al producto en evaluación
		String tag2 = ""; // cojunto de tags que nuestro usuario ha usado	
	
		for (int i=0; i<usuarios.length ; i++){
			if (vectorUsuario != usuarios[i]){
				try {
					tag = Datos.getTag(Users.getUser(hashIndexToUsers.get(i)), prod); 
					if ((tag == null) || (tag.isEmpty())) continue;
					String selectStatement = "SELECT DISTINCT tag FROM tagtable WHERE user = ?";
					PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
					prepStmt.setString(1, user);
					ResultSet res = prepStmt.executeQuery();
					while (res.next()){
						tag2 = res.getString("tag");
						simTag += Afinidad.afinidad(tags[hashTagsToIndex.get(Tags.getIdTag(tag))], tags[hashTagsToIndex.get(Tags.getIdTag(tag2))]); // Calculamos la similitud entre tags
					} 
				}catch (Exception e){System.out.println(tag);
						e.printStackTrace();
						log.warning("Error");
				}
				simUsr = Afinidad.afinidad(vectorUsuario, usuarios[i])+1; // Calculamos la similitud entre usuarios y le sumamos unos
			}
			rankProd += simUsr*simTag; // Obtenemos la predición de puntuación para un producto como el sumatorio de producto de similitudes de usuarios y tags

		}
		return rankProd;
	}
	
	public static void getRecommendation (String user){
		double newRank = 0;
		double maxRank = 0;
		String prod;
		String prodRecom = "";
		int [] vector = usuarios[hashUsersToIndex.get(Users.getIdUser(user))];
		
		try{
			String selectStatement = "SELECT DISTINCT product FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		prod = res.getString("product");
        		newRank = rankProd (vector, user, prod); // Calculamos la predicción de la puntuación que le pondría el usuario a un determinado producto
        		if (!haveGot(user,prod)){ // Comprobamos si el usuario tiene el producto, si no lo tiene vemos si es más recomendable que el anterior
        			if (newRank > maxRank){
        				System.out.println("Dime la puntuación del producto: "+prod+". La puntuación es: "+newRank);
        				maxRank = newRank;
        				prodRecom = prod;
        				log.info("recomendado"+prodRecom);
        			}
        		}
        	}
        } catch (Exception e) {
    	    	e.printStackTrace();
    	    	log.warning("Error");
    	}
		
		if (prodRecom.equals(""))
			System.out.println("No hay recomendaciones disponibles para el usuario " + user);
		else
			System.out.println("El producto recomendado para el usuario " + user + " es el " + prodRecom);
	}
	
	public static boolean haveGot(String user, String product) {
		String us = "";
		
		try {
			String selectStatement = "SELECT user FROM tagtable WHERE product = ?";
			PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);
			prepStmt.setString(1, product);
			ResultSet res = prepStmt.executeQuery();
			while (res.next()){
				us = res.getString("user");
				if (us.equals(user)) {;
					return true; // Devuelve true si el usuario tiene el producto
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.warning("Error");
		}
		return false; // Devuelve false si no lo tiene.
	}
	
	public static void main(String[] args) throws Exception {

		// Cargamos las bases de datos
		Datos.cargaDatos();	
		
		// Método al que llamamos diciendo para que usuario queremos la recomendación
		getRecommendation("Usuario4"); 
		
		// Tras terminar la ejecuación y obtener la recomendación eliminamos la base de datos
		Datos.delBasDat(); 

	}
}
