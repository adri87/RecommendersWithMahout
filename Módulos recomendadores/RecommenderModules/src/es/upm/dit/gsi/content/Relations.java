package es.upm.dit.gsi.content;

import java.sql.SQLException;
import java.util.Vector;

public class Relations {
	
	/**
	 * public static double sim(int user1, int user2)
	 * Calculate the relation of friendly between two users
	 * 
	 * @param user1
	 * @param user2
	 * @return the realation of friendly
	 * @throws SQLException 
	 */
	public static double sim (int user1, int user2) throws SQLException{
		double num=0; 
		int den = Datos.numUsers();
		//if (dist(user1,user2)>=99999999){  // the users don´t have any relationship
		if (Datos.getDistance(user1, user2)>=99999999){
			return 0.0;
		}
		for (int j =1; j<=den; j++){
			//num += Math.pow(2, (Math.min(dist(user1,j), dist(user2,j)-Math.max(dist(user1,j), dist(user2,j)))));
			num += Math.pow(2, (Math.min(Datos.getDistance(user1,j), Datos.getDistance(user2,j)-Math.max(Datos.getDistance(user1,j), Datos.getDistance(user2,j)))));
		}
		return num/den;
	}
	
	/**
	 * public static int dist(int user1, int user2)
	 * Calculate the "distance" between two users. In other words, the distance is the number minimum 
	 * the users between them.
	 * 
	 * @param user1
	 * @param user2
	 * @return distance
	 */
	public static int dist(int user1, int user2) {
		Vector<String> vector = new Vector<String>();
		vector.add("dist("+user1+","+user2+")");
		return dist(user1, user2, vector); // Invoke the auxiliar method
	}
    
	
	/**
	 * public static int dist(int user1, int user2, Vector<String> vector)
	 * Auxiliar method to calculate the "distance" between two users. 
	 * The method include a vector that contains the distances used
	 *
	 * @param user1
	 * @param user2
	 * @param vector, contains the distances used.
	 * @return distance
	 */
	public static int dist(int user1, int user2, Vector<String> vector){
		int d = 99999999, da=99999999, dmin = 99999999;  // Variables
		if (Datos.isFriend(user1,user2)) return d=1;  // if the users are friends
		else{
			int [] friendsUser1 = Datos.getFriends(user1);
			for (int friend: friendsUser1) {
				if (vector.contains("dist("+friend+","+user2+")")){  // The distances used are descarted
					d=dmin;
				} else { 
					Vector<String> vector2 = new Vector<String>(vector);
					vector2.add("dist("+friend+","+user2+")"); // Save the distance used in the vector
					da = 1 + dist(friend, user2, vector2);	// We invoke the method recursively until that users are friends direct 
				}
				if (da < dmin){
					dmin = da;
					d = dmin;
				}
			}
		} 
		return d;
	}

}
