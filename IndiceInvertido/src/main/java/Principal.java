import java.util.Arrays;

import logica.Indice;

public class Principal {

	public static void main(String[] args) {
		
        String[] data = new String[]{
        		"A brilliant, festive study of JS Bach uses literature and painting to illuminate his 'dance-impregnated' music, writes Peter Conrad",
                "Fatima Bhutto on Malala Yousafzai's fearless and still-controversial memoir",
                "Grisham's sequel to A Time to Kill is a solid courtroom drama about racial prejudice marred by a flawless white hero, writes John O'Connell",
                "This strange repackaging of bits and pieces does the Man Booker winner no favours, says Sam Leith",
                "Another book with music related content"
            };
		
        // Creacion de indice invertido
        Indice invertedIndex = new Indice(data); 
        
// Consulta Nº1:
        
        String[] results = invertedIndex.get("music");
        
        // Debe mostrar 2
        System.out.println("Cantidad por la consulta 'music': " + results.length);   
            
        // Debe mostrar [".... music, writes Peter Conrad","Another book with music related ..."]
        System.out.println(Arrays.toString(results)); 	

// Consulta Nº2:
        results = invertedIndex.get("and");

        // Debe mostrar 3
        System.out.println("Cantidad por la consulta 'and': " + results.length);   
            
        // Debe mostrar [A brilliant, festive study of JS Bach uses literature and painting to illuminate his 'dance-impregnated' music, writes Peter Conrad, Fatima Bhutto on Malala Yousafzai's fearless and still-controversial memoir, This strange repackaging of bits and pieces does the Man Booker winner no favours, says Sam Leith]
        System.out.println(Arrays.toString(results)); 	

	}
}