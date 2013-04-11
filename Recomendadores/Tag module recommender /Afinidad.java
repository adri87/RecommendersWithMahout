package es.upm.dit.gsi.tag;

public class Afinidad {
	
	public static double productoVectores (int vector1 [], int vector2 []){
		double resultado = 0;
		for (int i=0; i<vector1.length; i++)
			resultado += vector1[i]*vector2[i];
		return resultado;
		
	}
	
	public static double moduloVector (int vector[]){
		double modulo = Math.sqrt(productoVectores(vector, vector));
		return modulo;
	}
		
	public static double afinidad (int vector1[], int vector2[]){
		double afinidad = 0;
		double prod = productoVectores(vector1, vector2);
		double moduloPrimero = moduloVector (vector1);
		double moduloSegundo = moduloVector (vector2);
		afinidad = prod/(moduloPrimero*moduloSegundo);
		return afinidad;
	}
	
}
