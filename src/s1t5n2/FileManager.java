package s1t5n2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FileManager{

	private File directori;
	private ArrayList<String> llista;

	public FileManager(File directori) {
		super();
		this.directori = directori;
		this.llista = new ArrayList<String>();
	}

	public File getDirectori() {
		return this.directori;
	}

	public ArrayList<String> getLlista() {
		return this.llista;
	}

	public void ordenarILlistar() {
		File ruta = this.directori;
		String[] continguts = ruta.list();	
		recurssioSubcarpetes(ruta,continguts);
	}

	public void recurssioSubcarpetes(File filePare, String[] contingutsFilePare) {
		
		Collections.sort(Arrays.asList(contingutsFilePare));
		
		for(String dirOrFile : contingutsFilePare) {

			File fileFill = new File(filePare.getAbsolutePath(),dirOrFile);
			if(fileFill.isDirectory() && fileFill.list().length > 0) {
				
				getLlista().add("(D)" + dirOrFile + lastModifiedDate(fileFill));
				recurssioSubcarpetes(fileFill,fileFill.list());
				
			}else if(fileFill.isDirectory() && fileFill.list().length == 0){
				getLlista().add("(D)" + dirOrFile + lastModifiedDate(fileFill));
			}else {
				getLlista().add("(F)" + dirOrFile + lastModifiedDate(fileFill));
			}
		}
	}

	public String lastModifiedDate(File file) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return " -- Última moficicació: " + sdf.format(file.lastModified());
	}
	
	public void addToLlista(ArrayList<String> subLlista) {
		for(String element : subLlista) {
			getLlista().add(element);
		}
	}
	
	public void mostratLlista() {
		for(String element: getLlista()) {
			System.out.println(element);
		}
	}
	
	public void guardarLlistaTXT(String fileName) {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			
			for(String element: getLlista()) {
				writer.write(element + "\n");
			}
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
