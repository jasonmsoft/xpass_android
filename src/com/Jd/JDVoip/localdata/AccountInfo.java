package com.Jd.JDVoip.localdata;

import android.app.Activity;
import android.content.Context;
import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by tujiankai on 2015/6/10.
 */
public class AccountInfo {
    private String username;
    private String password;
    private String proxy;
    private String domain;
    //private Context context;

    private static final String cfgName = "voipAccount.json";

//    public AccountInfo(Context context) {
//        this.context = context;
//    }

    public boolean readAccount(String appDir){
        String jsonStr = readJson(appDir);
        if (!jsonStr.isEmpty()){
            if (parseJson(jsonStr))
                return true;
            else
                return false;
        }else {
            return false;
        }
    }

    private String readJson(String appDir){
        String configPath = appDir + "/" + cfgName;
        String jsonStr = "";
        try{
            FileInputStream fin =new FileInputStream(configPath);// context.openFileInput(cfgName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            jsonStr = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }
        catch(Exception e){
            //e.printStackTrace();
        }
        return jsonStr;
    }

    private boolean parseJson(String jsonStr){
        JSONTokener jsonParser = new JSONTokener(jsonStr);
        try {
            JSONObject person = (JSONObject) jsonParser.nextValue();
            username = person.getString("username");
            password = person.getString("password");
            proxy = person.getString("proxy");
            domain = person.getString("domain");
            return true;
        } catch (JSONException e) {
            //e.printStackTrace();
        }
        return false;
    }


    public void writeAccount(String appDir, String username, String password, String proxy, String domain) {
        this.username = username;
        this.password = password;
        this.proxy = proxy;
        this.domain = domain;

        String jsonStr = getJsonString();
        if (!jsonStr.isEmpty()){
            writeJson(appDir, jsonStr);
        }
    }

    private void writeJson(String appDir, String jsonStr){
        String configPath = appDir + "/" + cfgName;
        try {
            FileOutputStream fout =new FileOutputStream(configPath, false);// context.openFileOutput(cfgName, Activity.MODE_PRIVATE);
            byte [] bytes = jsonStr.getBytes("UTF-8");
            fout.write(bytes);
            fout.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private String getJsonString(){
        String jsonStr = "";
        try {
            JSONStringer jsonText = new JSONStringer();
            jsonText.object();
            jsonText.key("username");
            jsonText.value(username);
            jsonText.key("password");
            jsonText.value(password);
            jsonText.key("proxy");
            jsonText.value(proxy);
            jsonText.key("domain");
            jsonText.value(domain);
            jsonText.endObject();
            jsonStr = jsonText.toString();

        } catch (JSONException e) {
            //e.printStackTrace();
        }
        return jsonStr;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }
    public String getDomain()
    {
        return domain;
    }
}
