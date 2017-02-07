package xenoframium.transitoreality.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class FileUtil {
	public static File getClasspathFile(String path) throws IOException{
		InputStream inputStream = FileUtil.class.getResourceAsStream(path);
		File file = File.createTempFile(String.valueOf(inputStream.hashCode()), null);
		FileOutputStream outputStream = new FileOutputStream(file);
		
		byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        
        inputStream.close();
        outputStream.close();
        
        return file;
	}
	
	public static String getWorkingDir() {
		return System.getProperty("user.dir");
	}
	
	public static File getWorkingDirFile(String path) {
		return new File(getWorkingDir() + "/" + path);
	}
	
	public static String readFileToString(File file) throws IOException {
		List<String> lines = Files.readAllLines(file.toPath());
		StringBuilder stringBuilder = new StringBuilder();
		
		for (String line : lines) {
			stringBuilder.append(line);
		}
		
		return stringBuilder.toString();
	}
}
