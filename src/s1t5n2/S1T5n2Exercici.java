package s1t5n2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class S1T5n2Exercici {

	public static void main(String[] args) {
		Properties propietats = new Properties();
		InputStream entrada = null;
		String nomDirectori = "";
		String arxiuALlegir = "";
		String arxiuAEscriure = "";

		try {
			entrada = new FileInputStream("dades.properties");
			propietats.load(entrada);
			
			nomDirectori = propietats.getProperty("nomDirectori");
			arxiuALlegir = propietats.getProperty("arxiuALlegir");
			arxiuAEscriure = propietats.getProperty("arxiuAEscriure");
			
			FileManager tractament1 = new FileManager(new File(nomDirectori));
			tractament1.ordenarILlistar();
			tractament1.guardarLlistaTXT(arxiuAEscriure);
			llegirTXT(new File(arxiuALlegir));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void llegirTXT(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String linea = "";

			while((linea = reader.readLine()) != null) {
				System.out.println(linea);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}





