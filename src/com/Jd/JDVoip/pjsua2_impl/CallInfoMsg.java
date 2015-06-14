package com.Jd.JDVoip.pjsua2_impl;

/**
 * Created by huangle on 2015/6/5.
 */

public class CallInfoMsg {
    private String callUser;
    private String callName;
    private String callDomain;
    private String callFromUser;
    private String callFromName;
    private String callFromDomain;


    public String getCallUser() {
        return callUser;
    }

    public void setCallUser(String callUser) {
        this.callUser = callUser;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getCallDomain() {
        return callDomain;
    }

    public void setCallDomain(String callDomain) {
        this.callDomain = callDomain;
    }

    public String getCallFromUser() {
        return callFromUser;
    }

    public void setCallFromUser(String callFromUser) {
        this.callFromUser = callFromUser;
    }

    public String getCallFromName() {
        return callFromName;
    }

    public void setCallFromName(String callFromName) {
        this.callFromName = callFromName;
    }

    public String getCallFromDomain() {
        return callFromDomain;
    }

    public void setCallFromDomain(String callFromDomain) {
        this.callFromDomain = callFromDomain;
    }
}
