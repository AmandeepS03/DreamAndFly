package utils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HelperClass {
	  
	  private HelperClass() {
	  }
	  private static Logger logger = Logger.getAnonymousLogger();
	  
	  public static String toHash(String password) {
	    StringBuilder sb = new StringBuilder();
	        try {
	            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
	            if(password != null) {
	              byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
	              for (int i = 0; i < hash.length; i++) {
	                sb.append(Integer.toHexString( 
	                                  (hash[i] & 0xFF) | 0x100 
	                              ).toLowerCase().substring(1,3));
	              }
	            }
	        } catch (java.security.NoSuchAlgorithmException e) {
	          logger.log(Level.WARNING, "Problema hash pswd!");
	        }
	        return sb.toString();
	    }

	  public static String filter(String input) {
		    
		    StringBuilder filtered = new StringBuilder();
		    HashMap<Character, String> characterMap = new HashMap<>();
		    // qui andrebbero aggiunti anche le entry per 'ò', 'ì' ecc.
		        characterMap.put('<', "&lt;");
		        characterMap.put('>', "&gt;");
		        characterMap.put('&', "&amp;");
		        characterMap.put('"', "&quot;");
		        characterMap.put('á', "&aacute;");
		        characterMap.put('é',"&eacute;");

		    char c;
		    int i = 0;
		    while (i < input.length()) {
		        c = input.charAt(i);

		        // Filtra il carattere desiderato
		        String replacement = characterMap.get(c);
		        if (replacement != null) {
		            filtered.append(replacement);
		        } else if (c == 'Ã') { // questo perchè non riesco a riconoscere normalmente 'è' e 'à'
		            i++; // Incrementa il contatore per passare al carattere successivo
		            if (i < input.length()) {
		                c = input.charAt(i);
		                if (c == '¨') {
		                    filtered.append("&egrave");
		                } else {
		                    filtered.append("&agrave");
		                }
		            }
		        } else {
		            filtered.append(c);
		        }

		        i++; // Incrementa il contatore per passare al prossimo carattere del ciclo principale
		    } // fine while

		    return filtered.toString();
	  }
//	  public class MyClass {
//		    private static final SecureRandom rand = new SecureRandom();
//		    private MyClass() {
//		        // Costruttore privato
//		    }
//
//		    public static List<Prodotto> estraiElementiCasuali(List<Prodotto> lista, int n) {
//		        List<Prodotto> risultato = new ArrayList<>();
//
//		        // Se la lista non contiene abbastanza elementi, imposta n uguale alla dimensione della lista
//		        if (n > lista.size()) {
//		            n = lista.size();
//		        }
//
//		        for (int i = 0; i < n; i++) {
//		        	  if (!lista.isEmpty()) {
//		                  int index = rand.nextInt(lista.size());
//		                  risultato.add(lista.get(index));
//		                  lista.remove(index);
//		              } else {
//		                  // La lista è vuota, non ci sono più elementi da estrarre
//		                  break;
//		              }
//		        }
//
//		        return risultato;
//		    }
//
//	}
	}
