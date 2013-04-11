package es.upm.dit.gsi.content;

public class Localication {

	/**
	 * public static double distForm(double lat1, double lng1, double lat2, double lng2)
	 * Calculates the distance between two places by Haversine formula.
	 * 
	 * @param lat1 latitude of the first place
	 * @param lng1 latitude of the second place
	 * @param lat2 longitude of the first place
	 * @param lng2 longitude of the second place
	 * @return dist, distance in metres
	 */
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) { 
	    double earthRadius = 6371; 
	    double dLat = Math.toRadians(lat2-lat1); 
	    double dLng = Math.toRadians(lng2-lng1); 
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng/2) * Math.sin(dLng/2); 
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
	    double dist = earthRadius * c; 
	    return dist*1000;  
    } 	
}
