package example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class Example_2 {

	//Usar en la beca:
	public static final String fileRatingsGSI="/home/ric/dev/eclipse/TasteWeb/WebContent/WEB-INF/ratings.dat";
	public static final String fileMoviesGSI="/home/ric/dev/eclipse/TasteWeb/WebContent/WEB-INF/movies.dat";
	
	public static DataModel model;
	
	public static void main(String[] args) throws IOException, TasteException{
		
		int userID=345;
		int numOfRecommendations=5;
		
		model = new GroupLensDataModel((new File(fileRatingsGSI)));
		
		/**
		// Construct the list of pre-compted correlations
        Collection<GenericItemSimilarity.ItemItemSimilarity> correlations;
        ItemSimilarity itemSimilarity = new GenericItemSimilarity(correlations);
        */
		
		ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(model);
        Recommender recommender = new GenericItemBasedRecommender(model, itemSimilarity);
        Recommender cachingRecommender = new CachingRecommender(recommender);
        List<RecommendedItem> recommendations = cachingRecommender.recommend(userID, numOfRecommendations);
        
        System.out.print("\n");
	    System.out.println("EJEMPLO DE RECOMENDACIÓN ITEM-BASED: "+" para usuario número: "+userID);
	    System.out.println("*****************************************************");
	    System.out.print("\n");
	    System.out.println("Las 10 películas favoritas del usuario número "+userID+" son:");
	    System.out.print("\n");
	    obtieneFavoritas(userID);
	    System.out.print("\n");
	    System.out.println("Las "+numOfRecommendations+" películas a recomendar al usuario número "+userID+" son:");
	    System.out.print("\n");
	    for (RecommendedItem rec: recommendations){
	    	imprimePelicula(rec.getItemID());
	    }
        
        
	}
	public static void imprimePelicula(long itemId) throws FileNotFoundException, IOException{
    	try{
    		BufferedReader bf = new BufferedReader(new FileReader(fileMoviesGSI));
    		String movie="";
        	for( long i=0; i<itemId-1; i++){
        		movie=bf.readLine();
        	}
        	int a=movie.indexOf("::");
        	int b=movie.indexOf("::", a+2);
        	System.out.println(movie.substring(a+2,b));
    	}catch(Exception e){
    		System.out.println("Imposible hacer más recomendaciones.");
    	}    	
    }
	
	public static void obtieneFavoritas(int userID) throws TasteException, FileNotFoundException, IOException{
		PreferenceArray rawPrefs = model.getPreferencesFromUser(userID);
	    int length = rawPrefs.length();
	    PreferenceArray sortedPrefs = rawPrefs.clone();
	    sortedPrefs.sortByValueReversed();
	    // Cap this at NUM_TOP_PREFERENCES just to be brief
	    int max = Math.min(10, length);
	    for (int i = 0; i < max; i++) {
	      Preference pref = sortedPrefs.get(i);
	      imprimePelicula(pref.getItemID());
	    }
	}
}
