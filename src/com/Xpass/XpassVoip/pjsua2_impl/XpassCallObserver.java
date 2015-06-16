package com.Xpass.XpassVoip.pjsua2_impl;

/**
 * Created by yanxiaoyong on 2015/6/8.
 */
public interface XpassCallObserver {
        abstract void notifyCallState(XpassCall call);
        abstract void notifyCallMediaState(XpassCall call);
}
