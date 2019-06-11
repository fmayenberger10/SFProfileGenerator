package back;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Profile {

	private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> mapaTagsOrig;
	private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> mapaTagsCarga;
	private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> mapaTagsFinal;
	private LinkedHashMap<String, LinkedHashMap<String, Boolean>> mapaTagsCambios;
	private LinkedHashMap<String, LinkedHashMap<String, Boolean>> mapaTagsCambiosAceptados;
	public String name;
	private Queue<String> qOtros;
	public static final int OLD = 1;
	public static final int NEW = 2;
	public static final int CHANGES = 3;
	
	public Profile(String name,
			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> mapaTagsOrig, 
			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> mapaTagsCarga,
			LinkedHashMap<String, LinkedHashMap<String, Boolean>> mapaTagsCambios,
			Queue<String> otros) {
		this.name = name;
		this.mapaTagsOrig = mapaTagsOrig;
		this.mapaTagsCarga = mapaTagsCarga;
		this.mapaTagsCambios = mapaTagsCambios;
		this.mapaTagsFinal = new LinkedHashMap<>();
		this.qOtros = otros;
		
		mapaTagsCambiosAceptados = new LinkedHashMap<>();
		for(String type: mapaTagsCambios.keySet()) {
			for(String key: mapaTagsCambios.get(type).keySet()) {
				if(mapaTagsCambiosAceptados.containsKey(type)) {
					mapaTagsCambiosAceptados.get(type).put(key, false);
				} else {
					LinkedHashMap<String, Boolean> temp = new LinkedHashMap<>();
					temp.put(key, false);
					mapaTagsCambiosAceptados.put(type, temp);
				}
			}
		}
	}
	
	public LinkedHashMap<String, LinkedHashMap<String, String>> getType(String type, int version) {
		LinkedHashMap<String, LinkedHashMap<String, String>> ret;
		switch(version) {
		case OLD:
			ret = mapaTagsOrig.get(type);
			if(ret != null) {
				return ret;
			}
			break;
		case NEW:
			ret = mapaTagsCarga.get(type);
			if(ret != null) {
				return ret;
			}
			break;
		}
		return null;
	}
	
	public LinkedHashMap<String, String> getTag(String type, String name, int version) {
		LinkedHashMap<String, String> ret;
		switch(version) {
		case OLD:
			ret = mapaTagsOrig.get(type).get(name);
			if(ret != null) {
				return ret;
			}
			break;
		case NEW:
			ret = mapaTagsCarga.get(type).get(name);
			if(ret != null) {
				return ret;
			}
			break;
		}
		return null;
	}
	
	public ArrayList<String> getKeys() {
		ArrayList<String> keys = new ArrayList<>();
		for(String key : mapaTagsCambios.keySet()) {
			keys.add(key);
		}
		return keys;
	}
	
	public LinkedHashMap<String, ArrayList<String>> getChanges() {
		LinkedHashMap<String, ArrayList<String>> changes = new LinkedHashMap<>();
		for(String tipo : mapaTagsCambios.keySet()) {
			for(String nombre : mapaTagsCambios.get(tipo).keySet()) {
				if(mapaTagsOrig.get(tipo).get(nombre) != null) {
					if(!changes.containsKey(tipo)) {
						changes.put(tipo, new ArrayList<>());
					}
					changes.get(tipo).add(nombre);
				}
			}
		}
		return changes;
	}
	
	public LinkedHashMap<String, ArrayList<String>> getInserts() {
		LinkedHashMap<String, ArrayList<String>> changes = new LinkedHashMap<>();
		for(String tipo : mapaTagsCambios.keySet()) {
			for(String nombre : mapaTagsCambios.get(tipo).keySet()) {
				if(mapaTagsOrig.get(tipo).get(nombre) == null) {
					if(!changes.containsKey(tipo)) {
						changes.put(tipo, new ArrayList<>());
					}
					changes.get(tipo).add(nombre);
				}
			}
		}
		return changes;
	}
	
	public void acceptAll() {
		for(LinkedHashMap<String, Boolean> mapa : mapaTagsCambiosAceptados.values()) {
			for(String key : mapa.keySet()) {
				mapa.put(key, true);
			}
		}
	}
	
	private void generateNewMap() {
		for(String type: mapaTagsOrig.keySet()) {
			if(!mapaTagsFinal.containsKey(type)) {
				mapaTagsFinal.put(type, new LinkedHashMap<>());
			}
			for(String name : mapaTagsOrig.get(type).keySet()) {
				if(mapaTagsCambiosAceptados.get(type) != null 
						&& mapaTagsCambiosAceptados.get(type).get(name) != null 
						&& mapaTagsCambiosAceptados.get(type).get(name)) {
					mapaTagsFinal.get(type).put(name, mapaTagsCarga.get(type).get(name));
				} else {
					mapaTagsFinal.get(type).put(name, mapaTagsOrig.get(type).get(name));
				}
			}
		}
	}
	
	public boolean generateFile(String fileDir) {
		Queue<String> otros = new LinkedList<>(qOtros);
		generateNewMap();
		
		FileWriter fw = null;
        PrintWriter pw = null;
        try {
        	
            fw = new FileWriter(fileDir);
            pw = new PrintWriter(fw);
            pw.println(otros.remove());
            pw.println(otros.remove());
            String tipoAnt = " ";
    		for(String tipo : mapaTagsFinal.keySet()) {
    			String line = otros.peek();
    			if(!line.contains("/Profile") 
    					&& line.substring(line.indexOf('<') + 1, line.indexOf('>')).compareTo(tipoAnt) > 0 
    					&& line.substring(line.indexOf('<') + 1, line.indexOf('>')).compareTo(tipo) < 0) {
    				pw.println("\t" + otros.remove().trim());
    			}
    			for(String nombre : mapaTagsFinal.get(tipo).keySet()) {
    				pw.println("\t<" + tipo + ">");
    				for(String val : mapaTagsFinal.get(tipo).get(nombre).keySet()) {
    					pw.print("\t\t");
    					pw.print("<" + val + ">");
    					pw.print(mapaTagsFinal.get(tipo).get(nombre).get(val));
    					pw.println("</" + val + ">");
    				}
    				pw.println("\t</" + tipo + ">");
        			}
            }
    		while(!otros.peek().contains("/Profile")) {
            	pw.println("\t" + otros.remove().trim());
            }
    		pw.println(otros.remove().trim());
    		return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
           try {
	           if (null != fw) {
	        	   pw.close();
	        	   fw.close();
	           }
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
}
