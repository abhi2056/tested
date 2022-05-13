import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

public class CustomFileWriterTest {
	

	@Test
	public void writeReadTest_success() throws FileNotFoundException {
		CustomFileWriter.writeToFile("test", "D:/test.txt");
		String resp=CustomFileWriter.getFromFile("D:/test.txt");
		assertTrue("test".equals(resp));
	}
	
}
