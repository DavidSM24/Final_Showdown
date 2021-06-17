package utils;

import models.P_User.UserDAO;

public class CodeGenerator {
	

	public static String getCode() {
		String result=null;
		
		while(result==null) {
			result="";
			while(result.length()<5) {
				int type= (int) Math.floor(Math.random()*2+1);
				int random=0;
				char c='a';
				switch(type) {
				case 1:
					random=(int) Math.floor(Math.random()*(90-65)+65);
					c=(char) random;
					result+=c;
					break;
									
				case 2:
					random=(int) Math.floor(Math.random()*9);
					result+=random+"";
					break;			
				}
			}	
			
			UserDAO.loadAllUsers();
			if(UserDAO.getUserByUserCode(result)!=null) {
				result=null;
			}
		}
		return result;
	}
}
