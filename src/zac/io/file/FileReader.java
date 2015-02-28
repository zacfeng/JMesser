package zac.io.file;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {


	static public List<String> toList(String filePath) throws IOException {

		List<String> list = new ArrayList<String>();

		File dir = new File(filePath);

		if (dir.exists()) {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "UTF8"));

			String line;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
			reader.close();
			reader = null;
		}

		return list;
	}

	static public String toString(String filePath) throws IOException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}
}
