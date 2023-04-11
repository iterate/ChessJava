package fantasy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class FileReader {

	public static String readFile(String fileName) throws IOException {
		InputStream inputStream = FileReader.class.getResourceAsStream(fileName);
		if (inputStream == null) 
			inputStream = new FileInputStream(fileName);
		
//		if (inputStream.i) {
//			throw new IOException("File not found: " + fileName);
//		}
		try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
			return scanner.useDelimiter("\\A").next();
		}
	}
}
