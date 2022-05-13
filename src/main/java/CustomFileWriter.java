import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

class CustomFileWriter {
				 
		 
	static void writeToFile(String str,String filePath) throws FileNotFoundException {
				try(FileOutputStream fo=new FileOutputStream(new File(filePath))){
	        	fo.write(str.getBytes());
				}
				catch(Exception ex) {
					
				}
	     }
		 
		 
		 
	static String getFromFile(String filePath) throws FileNotFoundException {
			 byte[] bytes=null;	
			 try(FileInputStream fo=new FileInputStream(new File(filePath))){
	        	bytes=fo.readAllBytes();
	        	
				}
				catch(Exception ex) {
					
				}
				return new String(bytes);
	     }
		
	}