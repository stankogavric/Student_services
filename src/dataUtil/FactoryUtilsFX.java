package dataUtil;

public class FactoryUtilsFX {
	public static String checkInput(String s){
		if (!s.matches("[a-zA-Z\\s]+") || s.trim().equals("")){
			return null;
		}
		return s;
	} 
	
	public static String checkInputString(String s){
		if (s.equals("")){
			return null;
		}
		return s;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static int checkInputInt(String s){
		if(!isInteger(s)){
			return 0;
		}
		int i = Integer.parseInt(s);
		return i;
	}
	
	public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static double checkInputDouble(String s){
		if(!isDouble(s)){
			return -1;
		}
		double i = Double.parseDouble(s);
		return i;
	}
}
