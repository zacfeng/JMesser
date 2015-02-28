package zac.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	static public List<String> tokenToList(String tokenString, String regex)
			throws IOException {

		if(tokenString == null){
			return null;
		}
		
		String[] tokens = tokenString.split(regex);
		List<String> list = new ArrayList<String>();

		if (tokenString.isEmpty()) {
			for (String token : tokens) {
				list.add(token);
			}
		}

		return list;
	}
	
}
