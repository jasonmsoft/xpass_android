package com.Xpass.XpassVoip.pjsua2_impl;

/**
 * Created by huangle on 2015/6/9.
 */
public class XpassSipUri {
    public XpassSipUri(String name ,String user,String domain){
        this.name = name;
        this.user = user;
        this.domain = domain;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    private String user;
    private String name;
    private String domain;
}
