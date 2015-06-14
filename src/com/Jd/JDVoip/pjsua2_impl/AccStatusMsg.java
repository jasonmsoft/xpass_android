package com.Jd.JDVoip.pjsua2_impl;

/**
 * Created by huangle on 2015/6/5.
 */
public class AccStatusMsg {
    private String accName;
    private JdAccStatus accStatus;
    private String accReason;
    public AccStatusMsg(String accName,JdAccStatus accStatus,String accReason){
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

    public JdAccStatus getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(JdAccStatus accStatus) {
        this.accStatus = accStatus;
    }

    public String getAccReason() {
        return accReason;
    }

    public void setAccReason(String accReason) {
        this.accReason = accReason;
    }
}
