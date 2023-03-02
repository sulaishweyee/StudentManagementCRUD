package studentmanagement.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailValidator {
	public static boolean isValid(String email) {
		
		if(email == null) {
			return false;
		}
		//Regular Expression   
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; 
        
        //Compile regular expression to get the pattern  
        Pattern pattern = Pattern.compile(regex); 
        
       //Create instance of matcher   
        Matcher matcher = pattern.matcher(email);  
        
        return matcher.matches();
	}

}
