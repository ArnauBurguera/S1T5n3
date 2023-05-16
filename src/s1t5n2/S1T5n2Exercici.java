package s1t5n2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class S1T5n2Exercici {

	static SecretKeySpec secretKey;
	static byte[] key;

	public static void main(String[] args) {
		Properties propietats = new Properties();
		InputStream entrada = null;
		String nomDirectori = "";
		String arxiuALlegir = "";
		String arxiuAEscriure = "";
		String infoEncriptada = "";
		String infoDesencriptada = "";

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
			
			//Encriptar
			infoEncriptada = encriptar(tractament1.llistaToString(), "Informació classificada");
			//Desencriptar
			infoDesencriptada = desencriptar(infoEncriptada, "Informació classificada");
			
			System.out.println("\n\nText encriptat: \n" + infoEncriptada);
			System.out.println("\n\nText desencriptat: \n" + infoDesencriptada);

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

	public static void crearKey(String keyIntroduida) {
		try {
			key = keyIntroduida.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String encriptar(String stringAEncriptar, String keyRebuda) {
		try {
			crearKey(keyRebuda);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(stringAEncriptar.getBytes("UTF-8")));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String desencriptar(String stringADesencriptar, String keyRebuda) {
		try {
			crearKey(keyRebuda);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(stringADesencriptar)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}





