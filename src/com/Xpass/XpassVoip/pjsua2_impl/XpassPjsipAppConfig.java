package com.Xpass.XpassVoip.pjsua2_impl;

import org.pjsip.pjsua2.pj_log_decoration;

/**
 * Created by huangle on 2015/6/8.
 */
public class XpassPjsipAppConfig {
    public XpassPjsipAppConfig(){
        initDefaultConfig();
        initConfigFromDB();
    }

    public String getUaAgent() {
        return uaAgent;
    }

    public void setUaAgent(String uaAgent) {
        this.uaAgent = uaAgent;
    }

    public long getSipUdpPort() {
        return sipUdpPort;
    }

    public void setSipUdpPort(long sipUdpPort) {
        this.sipUdpPort = sipUdpPort;
    }

    public long getSipTcpPort() {
        return sipTcpPort;
    }

    public void setSipTcpPort(long sipTcpPort) {
        this.sipTcpPort = sipTcpPort;
    }

    public boolean isOwnWorkThread() {
        return ownWorkThread;
    }

    public void setOwnWorkThread(boolean ownWorkThread) {
        this.ownWorkThread = ownWorkThread;
    }

    public long getIlbcMode() {
        return ilbcMode;
    }

    public void setIlbcMode(long ilbcMode) {
        this.ilbcMode = ilbcMode;
    }

    public long getRxDropPct() {
        return rxDropPct;
    }

    public void setRxDropPct(long rxDropPct) {
        this.rxDropPct = rxDropPct;
    }

    public long getEcOptions() {
        return ecOptions;
    }

    public void setEcOptions(long ecOptions) {
        this.ecOptions = ecOptions;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public long getLogdecor() {
        return logdecor;
    }

    public void setLogdecor(long logdecor) {
        this.logdecor = logdecor;
    }

    public long getEcTailLen() {
        return ecTailLen;
    }

    public void setEcTailLen(long ecTailLen) {
        this.ecTailLen = ecTailLen;
    }

    private void initDefaultConfig(){
        uaAgent = "XpassSip Agent";
        sipUdpPort = 5060;
        sipTcpPort = 5060;
        ownWorkThread = false;
        ilbcMode = 30;
        rxDropPct = 0;
        ecOptions = 1;
        ecTailLen = 200;
        logLevel = 4;
        logdecor =  pj_log_decoration.PJ_LOG_HAS_LEVEL_TEXT.swigValue() | pj_log_decoration.PJ_LOG_HAS_CR.swigValue() | pj_log_decoration.PJ_LOG_HAS_YEAR.swigValue()
                | pj_log_decoration.PJ_LOG_HAS_MONTH.swigValue() | pj_log_decoration.PJ_LOG_HAS_DAY_OF_MON.swigValue() | pj_log_decoration.PJ_LOG_HAS_NEWLINE.swigValue()
                | pj_log_decoration.PJ_LOG_HAS_TIME.swigValue() | pj_log_decoration.PJ_LOG_HAS_MICRO_SEC.swigValue();;
    }
    private void initConfigFromDB(){

    }

    private String uaAgent;
    private long sipUdpPort;
    private long sipTcpPort;
    private boolean ownWorkThread;
    private long ilbcMode;
    private long rxDropPct;
    private long ecOptions;
    private long ecTailLen;
    private int logLevel;
    private long logdecor;


}
