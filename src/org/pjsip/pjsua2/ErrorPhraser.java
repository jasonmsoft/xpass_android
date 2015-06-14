package org.pjsip.pjsua2;


public class ErrorPhraser {
	private static String headerString = "java.lang.Exception:";
	private static String titleString = "Title:";
	private static String codeString = "Code:";
	private static String descriptionString = "Description:";
	private static String locationString = "Location:";
	public static ErrorInfo phraserException(String exceptingStr){
		if (exceptingStr.indexOf(ErrorPhraser.headerString) >= 0){
			exceptingStr = exceptingStr.substring(ErrorPhraser.headerString.length());
		}else{
			return null;
		}
		ErrorInfo info = new ErrorInfo();
		String[] ss = exceptingStr.split("\n");
		for(int i = 0; i < ss.length; i++){
			String exceptionContent = ss[i];
			checkAndSetField(exceptionContent,info);
		}
		return info;
	}
	private static void checkAndSetField(String content,ErrorInfo info){
		if(content.indexOf(ErrorPhraser.titleString) >= 0){
			String title = content.substring(ErrorPhraser.titleString.length() + 1).trim();
			info.setTitle(title.trim());
			
		}else if (content.indexOf(codeString) >= 0){
			String code = content.substring(ErrorPhraser.codeString.length() + 1).trim();
			Integer codeInteger = -1;
			try{
				codeInteger = Integer.parseInt(code);
			}catch(Exception e){
				
			}
			info.setCode(codeInteger);
			
		}else if (content.indexOf(descriptionString) >= 0){
			
			String desc = content.substring(ErrorPhraser.descriptionString.length() + 1).trim();
			info.setDescription(desc.trim());
			
		}else if (content.indexOf(locationString) >= 0){
			
			String location = content.substring(ErrorPhraser.locationString.length() + 1).trim();
			info.setLocation(location.trim());
		}else{
			
		}
	}
}
