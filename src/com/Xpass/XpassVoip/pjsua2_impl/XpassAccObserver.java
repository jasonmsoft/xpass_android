package com.Xpass.XpassVoip.pjsua2_impl;

import org.pjsip.pjsua2.*;

/**
 * Created by cdmaji1 on 2015/6/5.
 */
public interface XpassAccObserver {
	abstract void notifyRegStateChanged(String accName, XpassAccStatus status, String reason);
	abstract void notifyCallIncoming(String accName, OnIncomingCallParam prm);
}
