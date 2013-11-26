package logica;

import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * Clase encargada de todo el manejo interno del Indice Invertido. 
 * Recibe como parametro un String[], a partir del cual se crea la estructura para crear el indice a traves de una HashTable. 
 * 
 * @author Pablo
 */

public class Indice {

// Variables de la clase
	
	// Hashtable donde se almacenaran las palabras cmo clave 
	private Hashtable<String, Vector<Integer>> indice;
	private String[] data;
	
	/**
	 * Metodo constructor, recibe como parametro data, el cual sirve para construir el indice. 
	 * Se guarda una copia, para despues, al hacer la consulta, poder devolver la/s frase/s que hace/n matching con la consulta.
	 * El indice se construye llamando a un metodo privado que recibe un String[] y llena una estructura de HASH con los tokens.
	 * @param data : Es un String[] que contiene las frases
	 */
	
	public Indice(String[] data) {
		this.data = data;
		indice = new Hashtable<String, Vector<Integer>>();
		crearIndice(data);
	}

	/**
	 * Metodo privado que se encarga de crear el Indice.
	 * Toma una frase, recorre palabra por palabra y la va agregando al indice, utilizando el metodo
	 * privado 'AgregarAHash'.
	 * @param data: Es un String[] que contiene las frases
	 */
	
	private void crearIndice(String[] data) {
		// Recorro data y vos haciendo tokens por palabra y agregandola a la tabla de hash
		// En caso de que ya exista, se actualizara la informacion
		int tamanio = data.length; 
		
		String linea;
		// Cantidad de palabras que tiene una linea despues de separarla por tokens "espacio"
		int cantPalabras;
		
		for (int i=0; i< tamanio; i++){
			 linea = data[i];
			 // Parseo la linea
			 String[] cadenaDeTokens = linea.split(" ");
			 // Pregunto la cantidad de tokens que tengo
			 cantPalabras = cadenaDeTokens.length;
			 for (int j=0; j < cantPalabras; j++)
				 // Antes de enviarle la palabra, reemplazo las comas por nada
				 // Esto es para normalizar el tokens
				 // Se podria hacer tambien con otros caracteres, EJ: ' ó el -
				 // Lo mismo que pasar todo a mayuscula o minuscula, pero si se quiere buscar una palabra exacta, se pierde la sintaxis
				 AgregarAHash(cadenaDeTokens[j].replace(",",""),i);
		}
	}

	/**
	 * Metodo privado que se encarga de agregar la palabra a la HASH.
	 * En caso de que no exista, la agrega.
	 * Si ya existe, actualiza el vector que guarda la lista de las frases que contienen dicha palabra
	 * @param palabra: palabra que debe hacer matching
	 * @param pos: lo utilizo para saber que frase es la que contiene esa palabra
	 */
	
	private void AgregarAHash(String palabra, int pos) {
		Vector<Integer> nrolinea;
		// Si no esta la palabra en la Hash
		if (!indice.containsKey(palabra)){
			// Creo el vector donde se va a guardar las lineas que contienen a la palabra
			nrolinea = new Vector<Integer>();
			// Agrego el nro de la linea en donde esta esa palabra
			nrolinea.addElement(pos);
			// Agrego la palabra a la tabla de HASH
			indice.put(palabra, nrolinea);
		}
		else {
			// Obtengo el vector de la palabra
			nrolinea = (Vector<Integer>) indice.get(palabra);
			// Si no esta ese numero "pos" en el vector, lo agrego 
			if (!nrolinea.contains(pos)){
				// Borro la clave
				indice.remove(palabra);
				// Agrego "pos" al vector
				nrolinea.addElement(pos);
				// Lo vuelvo a agregar a la tabla de HASH, pero con el vector actualizado
				indice.put(palabra, nrolinea);
			}
		}
	}

	/**
	 * Metodo privado que se encarga de construir un vector que contiene los numeros de las frases que contienen
	 * la palabra de la consulta.	
	 * @param palabra: palabra que debe hacer matching
	 * @return Vector de enteros
	 */
	private Vector<Integer> obtenerListaNroLinea(String palabra){
		Vector<Integer> numero = new Vector<Integer>();
		if (indice.containsKey(palabra))
			numero = (Vector<Integer>) indice.get(palabra);
		return numero;
	}
	
	
	/**
	 * Metodo que recibe como parametro una palabra y devuelve un arreglo de String que contiene la lista de las frases 
	 * que hacen matching con la palabra.
	 * Este metodo utiliza el metodo privado 'obtenerListaNroLinea'.
	 * 
	 * @param palabra: palabra que se quiere buscar y conseguir las frases
	 * @return la lista de frases que contienen la palabra pasada como parametro
	 */
	
	public String[] get(String palabra) {
		// Obtengo el vector desde la tabla de HASH con KEY = palabra
		Vector<Integer> lista = obtenerListaNroLinea(palabra);
		// String que se sirve para retornar el resultado de la consulta
		String[] resultado = new String[lista.size()];
		// Recorro la lista y voy agregando a resultado, las lineas originales
		for (int i=0; i< lista.size(); i++){
			int pos = (Integer) lista.elementAt(i);
			// Obtengo la frase que contiene la palabra pasada por parametro
			String aux = "\"" + data[pos] + "\"";
			resultado[i] = aux;
		}
		return resultado;
	}

}
