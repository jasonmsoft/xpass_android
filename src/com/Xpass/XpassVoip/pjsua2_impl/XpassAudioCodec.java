package com.Xpass.XpassVoip.pjsua2_impl;

/**
 * Created by huangle on 2015/6/8.
 */
public class XpassAudioCodec {
    public String getCodecId() {
        return codecId;
    }

    public void setCodecId(String codecId) {
        this.codecId = codecId;
    }

    public String codecId;

    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public short priority;
}
