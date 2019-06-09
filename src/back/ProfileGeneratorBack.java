package back;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ProfileGeneratorBack {
	
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> mapaTagsOrig;
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> mapaTagsCarga;
	public static Queue<String> otros;
	private static ArrayList<Profile> objProfiles;
	
	public static ArrayList<Profile> start(ArrayList<String> perfiles, String rutaOrig, String rutaCarga) throws Exception {
		
		objProfiles = new ArrayList<>();
		for(String perfil : perfiles) {
			mapaTagsOrig = new LinkedHashMap<>();
			mapaTagsCarga = new LinkedHashMap<>();
			otros = new LinkedList<>();
			
			File fileOrig = new File(rutaOrig + perfil + ".profile");
			File fileCarga = new File(rutaCarga + perfil + ".profile");
			leerArchivoOrig(fileOrig);
			leerArchivoCarga(fileCarga);
			compare(perfil);
	        otros.add("</Profile>");
		}
		return objProfiles;
	}
	
	public static void leerArchivoOrig(File file) throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		int lineNum = 0;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
	        String linea;
	        lineNum++;
	        linea = br.readLine();
	        otros.add(linea);
	        lineNum++;
	        linea = br.readLine();
	        otros.add(linea);
	        while(!(linea=br.readLine()).contains("/Profile")) {
		        lineNum++;
	        	String tmp;
	        	if(linea.contains("<")) {
	        		
	        		if(linea.contains("<!--")) {
	        	        lineNum++;
	        			linea = br.readLine();
	        			while(!linea.contains("-->")) {
	        		        lineNum++;
	        				linea = br.readLine();
	        			}
	        	        lineNum++;
	        			linea = br.readLine();
	        		}
		        	if(linea.indexOf('<') == linea.lastIndexOf('<')) {
		        		
		        		tmp = linea.substring(linea.indexOf('<') + 1, linea.indexOf('>'));
		        		if(!mapaTagsOrig.containsKey(tmp)) {
		        			mapaTagsOrig.put(tmp, new LinkedHashMap<>());
		        		}
		        		
		        		boolean acabo = false;
		        		
		        		LinkedHashMap<String, String> vals = new LinkedHashMap<>();
		        		
		        		while(!acabo) {
		        	        lineNum++;
		        			linea=br.readLine();
		        			if(linea != null) {
			        			if(linea.contains("</" + tmp)) {
			        				acabo = true;
			        			} else {
				        			String tag = linea.substring(linea.indexOf('<') + 1, linea.indexOf('>'));
				        			String val = linea.substring(linea.indexOf('>') + 1, linea.lastIndexOf('<'));
				        			vals.put(tag, val);
			        			}
		        			}
		        		}
		        		switch (tmp) {
		        		case "applicationVisibilities" :
		        			mapaTagsOrig.get(tmp).put(vals.get("application"), vals);
		        			break;
		        		case "classAccesses" :
		        			mapaTagsOrig.get(tmp).put(vals.get("apexClass"), vals);
		        			break;
		        		case "fieldPermissions" :
		        			mapaTagsOrig.get(tmp).put(vals.get("field"), vals);
		        			break;
		        		case "layoutAssignments" :
		        			String key = "l|" + vals.get("layout") + "|r|" + vals.get("recordType");
		        			mapaTagsOrig.get(tmp).put(key, vals);
		        			break;
		        		case "objectPermissions" :
		        			mapaTagsOrig.get(tmp).put(vals.get("object"), vals);
		        			break;
		        		case "pageAccesses" :
		        			mapaTagsOrig.get(tmp).put(vals.get("apexPage"), vals);
		        			break;
		        		case "recordTypeVisibilities" :
		        			mapaTagsOrig.get(tmp).put(vals.get("recordType"), vals);
		        			break;
		        		case "tabVisibilities" :
		        			mapaTagsOrig.get(tmp).put(vals.get("tab"), vals);
		        			break;
		        		}
		        	} else {
		        		otros.add(linea);
		        	}
	        	}
	        }
			br.close();
			fr.close();
		} catch (Exception e) {
			if(lineNum == 0) throw new Exception(file.getName() + " origin file not found");
			throw new Exception(file.getName() + " origin file integrity error on line " + (lineNum - 1));
		}
	}
	
	public static void leerArchivoCarga(File file) throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		int lineNum = 0;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
	        String linea;
	        lineNum++;
	        linea = br.readLine();
	        lineNum++;
	        linea = br.readLine();
	        while(!(linea=br.readLine()).contains("/Profile")) {
		        lineNum++;
	        	String tmp;
	        	if(linea.contains("<")) {
		        	if(linea.indexOf('<') == linea.lastIndexOf('<')) {
		        		
		        		tmp = linea.substring(linea.indexOf('<') + 1, linea.indexOf('>'));
		        		if(!mapaTagsCarga.containsKey(tmp)) {
		        			mapaTagsCarga.put(tmp, new LinkedHashMap<>());
		        		}
		        		
		        		boolean acabo = false;
		        		
		        		LinkedHashMap<String, String> vals = new LinkedHashMap<>();
		        		
		        		while(!acabo) {
		        	        lineNum++;
		        			linea=br.readLine();
		        			if(linea != null) {
			        			if(linea.contains("</" + tmp)) {
			        				acabo = true;
			        			} else {
				        			String tag = linea.substring(linea.indexOf('<') + 1, linea.indexOf('>'));
				        			String val = linea.substring(linea.indexOf('>') + 1, linea.lastIndexOf('<'));
				        			vals.put(tag, val);
			        			}
		        			}
		        		}
		        		switch (tmp) {
		        		case "applicationVisibilities" :
		        			mapaTagsCarga.get(tmp).put(vals.get("application"), vals);
		        			break;
		        		case "classAccesses" :
		        			mapaTagsCarga.get(tmp).put(vals.get("apexClass"), vals);
		        			break;
		        		case "fieldPermissions" :
		        			mapaTagsCarga.get(tmp).put(vals.get("field"), vals);
		        			break;
		        		case "layoutAssignments" :
		        			mapaTagsCarga.get(tmp).put("l|" + vals.get("layout") + "|r|" + vals.get("recordType"), vals);
		        			break;
		        		case "objectPermissions" :
		        			mapaTagsCarga.get(tmp).put(vals.get("object"), vals);
		        			break;
		        		case "pageAccesses" :
		        			mapaTagsCarga.get(tmp).put(vals.get("apexPage"), vals);
		        			break;
		        		case "recordTypeVisibilities" :
		        			mapaTagsCarga.get(tmp).put(vals.get("recordType"), vals);
		        			break;
		        		case "tabVisibilities" :
		        			mapaTagsCarga.get(tmp).put(vals.get("tab"), vals);
		        			break;
		        		}
		        	}
	        	}
	        }
			br.close();
			fr.close();
		} catch (Exception e) {
			if(lineNum == 0) throw new Exception(file.getName() + " upload file not found");
			throw new Exception(file.getName() + " upload file integrity error on line " + (lineNum - 1));
		}
	}

	public static void compare(String name) {
		LinkedHashMap<String, LinkedHashMap<String, Boolean>> mapaTagsCambios = new LinkedHashMap<>();
		for(String tipo : mapaTagsCarga.keySet()) {
			for(String nombre : mapaTagsCarga.get(tipo).keySet()) {
				
				LinkedHashMap<String, String> newVals = mapaTagsCarga.get(tipo).get(nombre);
				
				if(mapaTagsOrig.get(tipo).get(nombre) == null) {
					//es insercion
					if(mapaTagsCambios.containsKey(tipo)) {
						mapaTagsCambios.get(tipo).put(nombre, true);
					} else {
						LinkedHashMap<String, Boolean> tmp = new LinkedHashMap<>();
						tmp.put(nombre, true);
						mapaTagsCambios.put(tipo, tmp);
					}
				} else {
					//encontro
					LinkedHashMap<String, String> oldVals = mapaTagsOrig.get(tipo).get(nombre);
					boolean changed = false;
					for(String tag : newVals.keySet()) {
						if(!oldVals.get(tag).equals(newVals.get(tag))) {
							//es cambio
							changed = true;
						}
					}
					if(changed) {
						if(mapaTagsCambios.containsKey(tipo)) {
							mapaTagsCambios.get(tipo).put(nombre, true);
						} else {
							LinkedHashMap<String, Boolean> tmp = new LinkedHashMap<>();
							tmp.put(nombre, true);
							mapaTagsCambios.put(tipo, tmp);
						}
					}
				}
			}
		}
		objProfiles.add(new Profile(name, mapaTagsOrig, mapaTagsCarga, mapaTagsCambios, otros));
	}
}
