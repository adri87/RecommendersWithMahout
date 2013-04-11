package mia.recommender.ch02;


import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import  org.apache.mahout.cf.taste.model.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


public class DataBase {
	public static void main (String[] args) throws TasteException{
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setUser("pruebau");
		dataSource.setPassword("pruebap");
		JDBCDataModel dataModel = new MySQLJDBCDataModel(
				dataSource, "my_prefs_table", "my_user_column",
				"my_item_column", "my_pref_value_column");
		//dataModel.setPreference(1L, 101L, 5.0f);	
		//dataModel.setPreference(1L, 102L, 3.0f);
		//dataModel.setPreference(1L, 102L, 2.5f);
		//dataModel.setPreference(2L, 101L, 2.0f);
		//dataModel.setPreference(2L, 102L, 2.5f);
		//dataModel.setPreference(2L, 103L, 5.0f);
		//dataModel.setPreference(2L, 104L, 2.0f);
	}
}
