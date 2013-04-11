package es.upm.dit.gsi.collaborativeFilter;

import es.upm.dit.gsi.logger.Logger;

public class CollaborativeFilterRecommender {
	
	private static final Logger log = Logger.getLogger("collaborativeFilter.CollaborativeFilterRecommender");
	
	public static void getRecomendation(int user, String string){
		int recom = 0;
		double value = 0;
		if (string.equals("UserBased")){
			recom = UserBased.getRecomendationUB(user);
			value = UserBased.predProd(user, recom);
		} else if (string.equals("ItemBased")) {
			recom = ItemBased.getRecomendationIB(user);
			value = ItemBased.predProd(user, recom);
		} else {
			log.info("Se debe especificiar en que nos basamos. ERROR");
		}
		
		log.info("La recomendacion para el "+user+" es el objeto "+recom+" cuyo valor es "+value);
	}
	
	public static void main(String[] args) throws Exception {
		Datos.cargaDatos();
		getRecomendation(5, "UserBased");
		getRecomendation(5, "ItemBased");
	}

}
