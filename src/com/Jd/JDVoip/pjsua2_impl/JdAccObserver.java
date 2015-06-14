package com.Jd.JDVoip.pjsua2_impl;

import org.pjsip.pjsua2.*;

/**
 * Created by cdmaji1 on 2015/6/5.
 */
public interface JdAccObserver {
	abstract void notifyRegStateChanged(String accName, JdAccStatus status, String reason);
	abstract void notifyCallIncoming(String accName, OnIncomingCallParam prm);
}
