package dataUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class StringUtils {

	public static String[] getPersonData(String line) {
		return Arrays.copyOfRange(line.split("\\|"), 0, 5);
	}
	
	public static ArrayList<Integer> parseIds(String line) {
		ArrayList<Integer> ret = new ArrayList<>();
		line = line.substring(line.indexOf("{") + 1, line.lastIndexOf("}"));
		String[] parsed = line.split(",");
		for (String s : parsed)
			ret.add(Integer.parseInt(s.trim()));
		return ret;
	}

}
