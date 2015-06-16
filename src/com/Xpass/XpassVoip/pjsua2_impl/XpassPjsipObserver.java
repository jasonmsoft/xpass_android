package com.Xpass.XpassVoip.pjsua2_impl;

import android.os.Handler;

/**
 * Created by huangle on 2015/6/5.
 */
public interface XpassPjsipObserver {
    void OnAccStatus(String accName, XpassAccStatus status, String reason);
}
