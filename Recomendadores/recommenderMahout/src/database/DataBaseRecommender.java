package database;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public final class DataBaseRecommender implements Recommender {

	private final Recommender recommender;
	
	/*public static void main(String[] args) throws IOException, TasteException {
		
		int userID = 2;
		int numOfRecomendations = 10;

	    MysqlDataSource dataSource =new MysqlDataSource();
	    dataSource.setServerName("localhost");
	    dataSource.setDatabaseName("prueba");
	    dataSource.setUser("pruebau");
	    dataSource.setPassword("pruebap");
	    
	    JDBCDataModel model = new MySQLJDBCDataModel (dataSource, "preferences", "user_id","item_id","rating");
	    UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
	    UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
	    Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
	    Recommender cachingRecommender = new CachingRecommender(recommender);
	    List<RecommendedItem> recommendations = cachingRecommender.recommend(userID, numOfRecomendations); 
	    	for (RecommendedItem recommendation : recommendations) {
	    		System.out.println(recommendation);
	    }
	}*/

	public DataBaseRecommender(DataModel dataModel) throws TasteException {
		recommender = new CachingRecommender (new SlopeOneRecommender(dataModel));
	}
	
	public List<RecommendedItem> recommend (long userID, int howMany) throws TasteException {
		return recommender.recommend(userID, howMany);
	}
	
	public List<RecommendedItem> recommend (long userID, int howMany, IDRescorer rescorer) throws TasteException {
		return recommender.recommend(userID, howMany, rescorer);
	}
	
	public float estimatePreference(long userID, long itemID) throws TasteException {
		return recommender.estimatePreference(userID, itemID);
	}

	public DataModel getDataModel() {
		return recommender.getDataModel();
	}

	public void removePreference(long userID, long itemID) throws TasteException {
		recommender.removePreference(userID, itemID);
	}

	public void setPreference(long userID, long itemID, float value) throws TasteException {
		recommender.setPreference(userID, itemID, value);
	}

	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		recommender.refresh(alreadyRefreshed);
	}
	
	public String toString() {
		return "DataBaseRecommender[recommender: " + recommender + ']';
	}
}