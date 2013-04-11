
package mia.recommender.ch02;

import  org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import  org.apache.mahout.cf.taste.impl.neighborhood.*;
import  org.apache.mahout.cf.taste.impl.recommender.*;
import  org.apache.mahout.cf.taste.impl.similarity.*;
import  org.apache.mahout.cf.taste.model.*;
import  org.apache.mahout.cf.taste.neighborhood.*;
import  org.apache.mahout.cf.taste.recommender.*;
import  org.apache.mahout.cf.taste.similarity.*;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import  java.io.*;
import  java.util.*;

class RecommenderIntro {
	public static void main(String[] args) throws Exception {
    MysqlDataSource dataSource =new MysqlDataSource();
    dataSource.setServerName("localhost");
    dataSource.setDatabaseName("prueba");
    dataSource.setUser("pruebau");
    dataSource.setPassword("pruebap");
    JDBCDataModel model = new MySQLJDBCDataModel (dataSource, "preferences", "user_id","item_id","rating");
	//DataModel model = new FileDataModel(new File("intro.csv")); 
    UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
    UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
    Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity); 
    List<RecommendedItem> recommendations = recommender.recommend(1, 1); 
    	for (RecommendedItem recommendation : recommendations) {
    		System.out.println(recommendation);
    	}
	}
}