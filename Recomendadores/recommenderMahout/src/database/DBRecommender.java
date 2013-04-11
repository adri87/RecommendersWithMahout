package database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBRecommender {
	public static void main(String[] args) throws IOException, TasteException {
			
		int userID = 2;
		int numOfRecomendations = 10;

		MysqlDataSource dataSource =new MysqlDataSource();        
	    dataSource.setServerName("localhost");
	    dataSource.setDatabaseName("prueba");
	    dataSource.setUser("pruebau");
	    dataSource.setPassword("pruebap");
	    
	    Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        try {
            System.out.println("Creating connection.");
            conn = (Connection) dataSource.getConnection();
            System.out.println("Creating statement.");
            stmt = (Statement) conn.createStatement();
            System.out.println("Executing statement.");
            rset = stmt.executeQuery(args[1]);
            System.out.println("Results:");
            int numcols = rset.getMetaData().getColumnCount();
            while(rset.next()) {
                for(int i=1;i<=numcols;i++) {
                    System.out.print("\t" + rset.getString(i));
                }
                System.out.println("");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rset != null) rset.close(); } catch(Exception e) { }
            try { if (stmt != null) stmt.close(); } catch(Exception e) { }
            try { if (conn != null) conn.close(); } catch(Exception e) { }
        }

		    
	    JDBCDataModel model = new MySQLJDBCDataModel (dataSource, "preferences", "user_id","item_id","rating");
	    UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
	    UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
	    Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
	    List<RecommendedItem> recommendations = recommender.recommend(userID, numOfRecomendations); 
	    for (RecommendedItem recommendation : recommendations) {
	   		System.out.println(recommendation);
	    }
	}	

}
