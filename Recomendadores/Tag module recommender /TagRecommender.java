package es.upm.dit.gsi.tag;

import java.sql.Connection;
import java.sql.ResultSet;

import com.mysql.jdbc.PreparedStatement;

import es.upm.dit.gsi.logger.Logger;

public class TagRecommender {
	
	private static Configuration conf = Configuration.getConfiguration();
	private static Connection con = conf.getDbCon();
	
	private static final Logger log = Logger.getLogger("tag.TagRecommender");
	
	
	/*static int usuarioUno [] = new int [4];
	static int usuarioDos [] = new int [4];
	static int usuarioTres [] = new int [4];
	static int usuarioCuatro [] = new int [4];
	
	static int tagUno [] = new int [9];
	static int tagDos [] = new int [9];
	static int tagTres [] = new int [9];
	static int tagCuatro [] = new int [9];*/
	
	public static int usuarios [][];
	public static int tags [][];
	//static int usuarios [][] = {usuarioUno, usuarioDos, usuarioTres, usuarioCuatro};
	//static int tags [][] = {tagUno, tagDos, tagTres, tagCuatro};
	
	public static double rankProd (int vectorUsuario[], String user, String prod){
		
		double simTag = 0;
		double simUsr = 0;
		double rankProd = 0;
		String tag = "";
		String tag2 = "";
	
		for (int i=0; i<usuarios.length ; i++){
			if (vectorUsuario != usuarios[i]){
				try {
					//tag = Datos.getTag(getUsuario(usuarios[i]), prod);
					tag = Datos.getTag(Users.getUser(i+1), prod);
					//System.out.println(tag);
					if ((tag == null) || (tag.isEmpty())) continue;
					String selectStatement = "SELECT DISTINCT tag FROM tagtable WHERE user = ?";
					PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
					prepStmt.setString(1, user);
					ResultSet res = prepStmt.executeQuery();
					while (res.next()){
						tag2 = res.getString("tag");
						//simTag += Afinidad.afinidad(tags[getArraysTag(tag), getArrayTag(tag2));
						simTag += Afinidad.afinidad(tags[Tags.getIdTag(tag)-1], tags[Tags.getIdTag(tag2)-1]);
						//System.out.println("tag usuario"+tag2);
					} 
					//System.out.println("Termina usuario");
				}catch (Exception e){System.out.println(tag);
						e.printStackTrace();
						log.warning("Error");
				}
				simUsr = Afinidad.afinidad(vectorUsuario, usuarios[i])+1;
				System.out.println(simUsr);
			}
			rankProd += simUsr*simTag;
			//System.out.println("r"+rankProd);
		}
		
		//log.info("Puntuacion " +rankProd);
		return rankProd;
	}
	
	/*public static void getRecommendation (int vector[]){
		
		double newRank = 0;
		double maxRank = 0;
		String user = "";
		String prod;
		String prodRecom = "";
		
		if (vector == usuarioUno) user = "Usuario1";
		if (vector == usuarioDos) user = "Usuario2";
		if (vector == usuarioTres) user = "Usuario3";
		if (vector == usuarioCuatro) user = "Usuario4";
		
		try{
			String selectStatement = "SELECT DISTINCT product FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		prod = res.getString("product");
        		log.info(prod);
        		//tag = Datos.getTag(user, prod); // Suponiendo que solo un tag de usuario por cada producto (implemnetar mejora)
        		//vectorTag = getArrayTag(tag);
        		newRank = rankProd (vector, user, prod);
        		if (haveGot(user,prod)){
        			if (newRank > maxRank){
        				maxRank = newRank;
        				System.out.println(prod);
        				prodRecom = prod;
        				log.info("recomendado"+prodRecom);
        			}
        		}
        	}
        } catch (Exception e) {
    	    	e.printStackTrace();
    	    	log.warning("Error");
    	}
		
		System.out.println("El producto recomendado para el usuario " + user + " es el " + prodRecom);
	}*/
	
	public static void getRecommendation (String user){
		double newRank = 0;
		double maxRank = 0;
		String prod;
		String prodRecom = "";
		int [] vector = usuarios[Users.getIdUser(user)-1];
		
		try{
			String selectStatement = "SELECT DISTINCT product FROM tagtable";
    		PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(selectStatement);	
        	ResultSet res = prepStmt.executeQuery();
        	while (res.next()){
        		prod = res.getString("product");
        		//log.info(prod);
        		/*tag = Datos.getTag(user, prod); // Suponiendo que solo un tag de usuario por cada producto (implemnetar mejora)
        		vectorTag = getArrayTag(tag);*/
        		newRank = rankProd (vector, user, prod);
        		System.out.println(user);
        		System.out.println(prod);
        		if (!haveGot(user,prod)){
        			if (newRank > maxRank){
        				maxRank = newRank;
        				//System.out.println(prod);
        				prodRecom = prod;
        				//log.info("recomendado"+prodRecom);
        			}
        		}
        	}
        } catch (Exception e) {
    	    	e.printStackTrace();
    	    	log.warning("Error");
    	}
		
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
				//System.out.println(us);
				if (us.equals(user)) {
					log.info("YES");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.warning("Error");
		}
		
		log.info("NO");
		return false;
	}
	
	/*public static int [] crearDatos (String string){
		// Intentar mejorarlo para que creee tantos arrays como usuarios y tags hay en la tabla
		int usuarioUno [] = new int [4];
		int usuarioDos [] = new int [4];
		int usuarioTres [] = new int [4];
		int usuarioCuatro [] = new int [4];
		
		int tagUno [] = new int [9];
		int tagDos [] = new int [9];
		int tagTres [] = new int [9];
		int tagCuatro [] = new int [9];
		
		Datos.rellenaArrayUsuario(usuarioUno, "Usuario1");
		Datos.rellenaArrayUsuario(usuarioDos, "Usuario2");
		Datos.rellenaArrayUsuario(usuarioTres, "Usuario3");
		Datos.rellenaArrayUsuario(usuarioCuatro, "Usuario4");
	
		Datos.rellenaArrayTag(tagUno, "Tag1");
		Datos.rellenaArrayTag(tagDos, "Tag2");
		Datos.rellenaArrayTag(tagTres, "Tag3");
		Datos.rellenaArrayTag(tagCuatro, "Tag4");
		
		int usuarios [][] = {usuarioUno, usuarioDos, usuarioTres, usuarioCuatro};
		int tags [][] = {tagUno, tagDos, tagTres, tagCuatro};
		
		if (string.equals("Usuario1")) return usuarioUno;
		if (string.equals("Usuario2")) return usuarioDos;
		if (string.equals("Usuario3")) return usuarioTres;
		if (string.equals("Usuario4")) return usuarioCuatro;
		if (string.equals("Tag1")) return tagUno;
		if (string.equals("Tag2")) return tagDos;
		if (string.equals("Tag3")) return tagTres;
		if (string.equals("Usuario1")) return tagCuatro;
	}*/

	public static void main(String[] args) throws Exception {
		
		//haveGot("Usuario1","Producto4");

		// Cargamos las bases de datos
		Datos.cargaDatos();

		// Vamos a rellenar a los arrays de usuarios de la siguiente forma
		/*static int usuaruioUno [] = {0, 0, 0, 3};
		static int usuarioDos [] = {1, 2, 4, 1};
		static int usuarioTres [] = {0, 0, 5, 2};
		static int usuarioCuatro [] = {3, 2, 1, 4};*/
		/*Datos.rellenaArrayUsuario(usuarioUno, "Usuario1");
		Datos.rellenaArrayUsuario(usuarioDos, "Usuario2");
		Datos.rellenaArrayUsuario(usuarioTres, "Usuario3");
		Datos.rellenaArrayUsuario(usuarioCuatro, "Usuario4");
	
		Datos.rellenaArrayTag(tagUno, "Tag1");
		Datos.rellenaArrayTag(tagDos, "Tag2");
		Datos.rellenaArrayTag(tagTres, "Tag3");
		Datos.rellenaArrayTag(tagCuatro, "Tag4");*/
		
		
		// Prueba de que funciona bien rankProd	public static String getUsuario (int vector[]){
		/*String user = "";
		
		if (vector == usuarioUno) user = "Usuario1";
		if (vector == usuarioDos) user = "Usuario2";
		if (vector == usuarioTres) user = "Usuario3";
		if (vector == usuarioCuatro) user = "Usuario4";*/
		
		
		
		/*System.out.println("afinidad" + Afinidad.afinidad(usuarioUno, usuarioDos));
		System.out.println("afinidad" + Afinidad.afinidad(usuarioUno, usuarioTres));
		System.out.println("afinidad" + Afinidad.afinidad(usuarioUno, usuarioCuatro));
		System.out.println("afinidad" + Afinidad.afinidad(tagUno, tagDos));
		System.out.println("afinidad" + Afinidad.afinidad(tagUno, tagTres));
		System.out.println("afinidad" + Afinidad.afinidad(tagUno, tagCuatro));
		
		rankProd(usuarioUno, tagCuatro);*/
		
		/*String tag = "";
		tag = Datos.getTag("Usuario3", "Producto5");
		System.out.println(tag);*/
		
		getRecommendation("Usuario4"); // necesario hacer el carga datos a la vez porque sino usuarios y tags no tienen nada
	
		
		// Prueba de que funciona bien getArrayTag
		/* int vector [] = new int [9];
		String tag = "";
		tag = "Tag1";
		System.out.println(tag);
		vector = getArrayTag(tag);rtag == null en
		System.out.println("posicion" + vector[0]);
		System.out.println("posicion" + vector[1]);
		System.out.println("posicion" + vector[2]);
		System.out.println("posicion" + vector[3]);
		System.out.println("posicion" + vector[4]);
		System.out.println("posicion" + vector[5]);
		System.out.println("posicion" + vector[6]);
		System.out.println("posicion" + vector[7]);
		System.out.println("posicion" + vector[8]);*/
		
		// Prueba para ver 
		/*System.out.println("posicion" + usuarios[Users.getIdUser("Usuario1")-1][0]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario1")-1][1]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario1")-1][2]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario1")-1][3]);
		System.out.println("");
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario2")-1][0]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario2")-1][1]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario2")-1][2]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario2")-1][3]);
		System.out.println("");
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario3")-1][0]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario3")-1][1]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario3")-1][2]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario3")-1][3]);
		System.out.println("");
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario4")-1][0]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario4")-1][1]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario4")-1][2]);
		System.out.println("posicion" + usuarios[Users.getIdUser("Usuario4")-1][3]);
		System.out.println("");*/
	}
}
