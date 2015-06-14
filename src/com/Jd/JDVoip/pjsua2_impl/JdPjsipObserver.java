package com.Jd.JDVoip.pjsua2_impl;

import android.os.Handler;

/**
 * Created by huangle on 2015/6/5.
 */
public interface JdPjsipObserver {
    void OnAccStatus(String accName, JdAccStatus status, String reason);
}
