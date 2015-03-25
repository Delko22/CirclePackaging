import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Reader {
	
	final static Charset ENCODING = StandardCharsets.UTF_8;

	//"/home/katrijne/git/CirclePackaging/CirclePackaging/src/testInstances/NR10_1-10.txt"
	
	public Reader() {
	}
	
	public List<Double> readRadii(String filename) {
		try {
			List<String> lines = readSmallTextFile(filename);
			List<Double> radii = extractRadius(lines);
			return radii;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<String> readSmallTextFile(String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    return Files.readAllLines(path, ENCODING);
	 }
	
	private List<Double> extractRadius(List<String> lines) {
		List<Double> radii = new ArrayList<Double>();
		for(String line : lines) {
			String[] numbers = line.split(" ");
			radii.add( Double.parseDouble(numbers[numbers.length - 1]) );
		}
		return radii;
	}
	
}
