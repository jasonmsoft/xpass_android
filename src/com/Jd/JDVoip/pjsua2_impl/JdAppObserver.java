package com.Jd.JDVoip.pjsua2_impl;

/**
 * Created by yanxiaoyong on 2015/6/5.
 */
interface JdAppObserver {
    abstract void notifyCallState(JdCall call);
    abstract void notifyCallMediaState(JdCall call);
}
