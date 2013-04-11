package es.upm.dit.gsi.content;

import es.upm.dit.gsi.logger.Logger;

public class Recommender {
	
	private static final Logger log = Logger.getLogger("content.Recommender");

	public static void getRecommendation (int user){
		int product = CollaborativeFilter.getRecomendationCF(user);	
		log.info("");
		System.out.println("El producto recomendador para el usuario "+user+" es: "+product);
	}
	
	public static void main(String[] args) throws Exception {
		  	//Datos.cargaDatos();
			//Datos.rellBasSim();
			//System.out.println(CollaborativeFilter.getRecomendationCF(7));
			//Datos.rellBasSim();
	}
	
	
}
