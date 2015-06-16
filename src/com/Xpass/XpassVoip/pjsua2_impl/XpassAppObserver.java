package com.Xpass.XpassVoip.pjsua2_impl;

/**
 * Created by yanxiaoyong on 2015/6/5.
 */
interface XpassAppObserver {
    abstract void notifyCallState(XpassCall call);
    abstract void notifyCallMediaState(XpassCall call);
}
