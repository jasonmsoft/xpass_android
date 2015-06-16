package com.Xpass.XpassVoip.pjsua2_impl;

/**
 * Created by huangle on 2015/6/5.
 */
public class AccStatusMsg {
    private String accName;
    private XpassAccStatus accStatus;
    private String accReason;
    public AccStatusMsg(String accName,XpassAccStatus accStatus,String accReason){
        this.accName   = accName;
        this.accStatus = accStatus;
        this.accReason = accReason;
    }
    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public XpassAccStatus getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(XpassAccStatus accStatus) {
        this.accStatus = accStatus;
    }

    public String getAccReason() {
        return accReason;
    }

    public void setAccReason(String accReason) {
        this.accReason = accReason;
    }
}
