package com.Xpass.XpassVoip.pjsua2_impl;

/**
 * Created by yanxiaoyong on 2015/6/8.
 */
public interface XpassCallObserver {
        abstract void notifyCallState(int callId, int callState);
        abstract void notifyCallMediaState(int callId, int mediaState);
}



