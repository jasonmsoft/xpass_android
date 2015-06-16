package com.Xpass.XpassVoip.pjsua2_impl;

/**
 * Created by huangle on 2015/6/9.
 */
public class XpassPjsipAppUtil {
    // "huanghuan" <sip:18328327042@voip.Xpass.com>;tag=KKvD3U6QZ56mS
    public static  XpassSipUri phraseUri(String sipUri){
        if(!sipUri.isEmpty()) {
            String tmpUri = new String(sipUri);
            try {
                int leftPos = tmpUri.indexOf("<");
                String name = tmpUri.substring(0, leftPos ).trim();
                int quotLeft = name.indexOf("\"");
                int quotRight = name.lastIndexOf("\"");
                if(quotLeft <  0 || quotRight < 0){
                    name = "";
                }else{
                    name = name.substring(quotLeft + 1, quotRight);
                }
                int rightPos = tmpUri.indexOf(">");
                String url = tmpUri.substring(leftPos + 1, rightPos).trim();
                int userLeft = url.indexOf("sip:");
                int userRight = url.indexOf("@");
                String user = url.substring(userLeft+4,userRight);
                String domain = url.substring(userRight+1);
                XpassSipUri XpassSipUri = new XpassSipUri(name,user,domain);
                return XpassSipUri;
            }catch (Exception e){
                return null;
            }
        }
        return null;
    }
}
