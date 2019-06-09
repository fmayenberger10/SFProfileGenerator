package back;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ProfileGeneratorConsole {
	
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> mapaTagsOrig;
	public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> mapaTagsCarga;
	public static Queue<String> otros;
	
	public static void main(String[] args) {
		
		String rutaOrig = "/home/franz/Documents/Sulamerica/salesforce-ic/profiles/";
		String rutaCarga = "/home/franz/Documents/Sulamerica/profiles/profiles commit 1 vfin/";
		String rutaNuevos = "/home/franz/Documents/Sulamerica/profiles/nuevos/";
		
		ArrayList<String> perfiles = new ArrayList<>();
		
		perfiles.add("Admin.profile");
		perfiles.add("Junior GEAMM.profile");
		perfiles.add("Pleno GEAMM.profile");
		perfiles.add("Sênior GEAMM.profile");
		perfiles.add("Gerente GEAMM.profile");
		perfiles.add("Saúde%2FPrestador Comunidade.profile");
		
		for(String perfil : perfiles) {
			System.out.println("PERFIL -----------> " + perfil);
			mapaTagsOrig = new LinkedHashMap<>();
			mapaTagsCarga = new LinkedHashMap<>();
			otros = new LinkedList<>();
			
			File fileOrig = new File(rutaOrig + perfil);
			File fileCarga = new File(rutaCarga + perfil);
			leerArchivoOrig(fileOrig);
			leerArchivoCarga(fileCarga);
			compare();
	        otros.add("</Profile>");
	        escribirArchivo(rutaNuevos + perfil);
		}
	}
	
	public static void leerArchivoOrig(File file) {
		System.out.println("------------- STAGE --------------");
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
	        String linea;
	        linea = br.readLine();
	        otros.add(linea);
	        linea = br.readLine();
	        otros.add(linea);
	        while(!(linea=br.readLine()).contains("/Profile")) {
	        	String tmp;
	        	if(linea.contains("<")) {
	        		
	        		if(linea.contains("<!--")) {
	        			linea = br.readLine();
	        			while(!linea.contains("-->")) {
	        				linea = br.readLine();
	        			}
	        			linea = br.readLine();
	        		}
		        	if(linea.indexOf('<') == linea.lastIndexOf('<')) {
		        		
		        		tmp = linea.substring(linea.indexOf('<') + 1, linea.indexOf('>'));
		        		if(!mapaTagsOrig.containsKey(tmp)) {
		        			mapaTagsOrig.put(tmp, new LinkedHashMap<>());
		        			System.out.println("---> " + tmp);
		        		}
		        		
		        		boolean acabo = false;
		        		
		        		LinkedHashMap<String, String> vals = new LinkedHashMap<>();
		        		
		        		while(!acabo) {
		        			linea=br.readLine();
		        			if(linea != null) {
			        			if(linea.contains("</" + tmp)) {
			        				acabo = true;
			        			} else {
			        				//System.out.println(linea);
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
			e.printStackTrace();
		}
	}
	
	public static void leerArchivoCarga(File file) {
		System.out.println("------------- CARGA --------------");
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
	        String linea;
	        linea = br.readLine();
	        linea = br.readLine();
	        while(!(linea=br.readLine()).contains("/Profile")) {
	        	String tmp;
	        	if(linea.contains("<")) {
		        	if(linea.indexOf('<') == linea.lastIndexOf('<')) {
		        		
		        		tmp = linea.substring(linea.indexOf('<') + 1, linea.indexOf('>'));
		        		if(!mapaTagsCarga.containsKey(tmp)) {
		        			mapaTagsCarga.put(tmp, new LinkedHashMap<>());
		        			System.out.println("---> " + tmp);
		        		}
		        		
		        		boolean acabo = false;
		        		
		        		LinkedHashMap<String, String> vals = new LinkedHashMap<>();
		        		
		        		while(!acabo) {
		        			linea=br.readLine();
		        			if(linea != null) {
			        			if(linea.contains("</" + tmp)) {
			        				acabo = true;
			        			} else {
				        			String tag = linea.substring(linea.indexOf('<') + 1, linea.indexOf('>'));
				        			String val = linea.substring(linea.indexOf('>') + 1, linea.lastIndexOf('<'));
				        			//System.out.println(tag + ": "+  val);
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
			e.printStackTrace();
		}
	}
	
	public static void compare() {
		int encontrado = 0;
		int nuevo = 0;
		ArrayList<String> outInsertadas = new ArrayList<>();
		ArrayList<String> outEncontradas = new ArrayList<>();
		for(String tipo : mapaTagsCarga.keySet()) {
			for(String nombre : mapaTagsCarga.get(tipo).keySet()) {
				
				LinkedHashMap<String, String> newVals = mapaTagsCarga.get(tipo).get(nombre);
				
				if(mapaTagsOrig.get(tipo).get(nombre) == null) {
					mapaTagsOrig.get(tipo).put(nombre, newVals);
					//System.out.println("no existia " + tipo + ": " + nombre + " en Orig");
					outInsertadas.add("INSERTO: " + tipo + ": " + nombre + " en Orig. " + newVals);
					nuevo++;
				} else {
					LinkedHashMap<String, String> oldVals = mapaTagsOrig.get(tipo).get(nombre);
					//System.out.println("encontrado " + tipo + ": " + nombre);
					encontrado++;
					for(String tag : newVals.keySet()) {
						if(!oldVals.get(tag).equals(newVals.get(tag))) {
							outEncontradas.add("ENCONTRO: <" + tipo + "> " + nombre + " era " + oldVals.get(tag) + " ---> " + newVals.get(tag));
							oldVals.put(tag, newVals.get(tag));
						}
					}
				}
			}
		}
		System.out.println(encontrado + " configuraciones encontradas");
		for(String out : outEncontradas) System.out.println(out);
		System.out.println(nuevo + " configuraciones nuevas");
		for(String out : outInsertadas) System.out.println(out);
	}
	
	public static void escribirArchivo(String fileDir) {
		FileWriter fw = null;
        PrintWriter pw = null;
        try {
        	
            fw = new FileWriter(fileDir);
            pw = new PrintWriter(fw);
            pw.println(otros.remove());
            pw.println(otros.remove());
            String tipoAnt = " ";
    		for(String tipo : mapaTagsOrig.keySet()) {
    			String line = otros.peek();
    			if(!line.contains("/Profile") 
    					&& line.substring(line.indexOf('<') + 1, line.indexOf('>')).compareTo(tipoAnt) > 0 
    					&& line.substring(line.indexOf('<') + 1, line.indexOf('>')).compareTo(tipo) < 0) {
    				pw.println("\t" + otros.remove().trim());
    			}
    			for(String nombre : mapaTagsOrig.get(tipo).keySet()) {
    				pw.println("\t<" + tipo + ">");
    				for(String val : mapaTagsOrig.get(tipo).get(nombre).keySet()) {
    					pw.print("\t\t");
    					pw.print("<" + val + ">");
    					pw.print(mapaTagsOrig.get(tipo).get(nombre).get(val));
    					pw.println("</" + val + ">");
    				}
    				pw.println("\t</" + tipo + ">");
        			}
            }
    		while(!otros.peek().contains("/Profile")) {
            	pw.println("\t" + otros.remove().trim());
            }
    		pw.println(otros.remove().trim());
        } catch (Exception e) {
            e.printStackTrace();
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
