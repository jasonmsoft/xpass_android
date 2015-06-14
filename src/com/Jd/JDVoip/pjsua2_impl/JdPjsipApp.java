package com.Jd.JDVoip.pjsua2_impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.pjsip.pjsua2.*;

import java.util.List;

/**
 * Created by yanxiaoyong on 2015/6/4.
 */
public class JdPjsipApp implements JdAccObserver, JdCallObserver {
    static {
        try {
            System.loadLibrary("pjsua2");
        } catch (UnsatisfiedLinkError e) {
        }
    }

    private static JdPjsipApp app = null;
    private JdAccMgr accMgr = null;
    private JdCall currentCall = null;
    private JdCodecMgr codecMgr = null;
    private Endpoint ep = null;
    private boolean bInit = false;
    private static final String TAG = JdPjsipApp.class.getName();
    private Handler messageHandler = null;
    private JdPjsipObserver jdPjsipObserver = null;
    private JdPjsipAppConfig jdPjsipAppConfig = null;

    private JdPjsipApp() {
        accMgr = new JdAccMgr(this);
        jdPjsipAppConfig = new JdPjsipAppConfig();
    }

    public static JdPjsipApp getInstance() {
        if (app == null)
            app = new JdPjsipApp();

        return app;
    }

    public Endpoint getEndpoint() {
        return ep;
    }

    public void setMessgaeHandler(Handler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void setJdPjsipObserver(JdPjsipObserver jdPjsipObserver) {
        this.jdPjsipObserver = jdPjsipObserver;
    }

    public void setCurrentCall(JdCall call) { currentCall = call; }

    public void init() {
        if (!bInit) {
            if (ep == null) {
                ep = new Endpoint();
                codecMgr = new JdCodecMgr(ep);
                try {
                    ep.libCreate();
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                    return;
                }
                EpConfig epConfig = new EpConfig();
                UaConfig uaConfig = epConfig.getUaConfig();
                LogConfig logConfig = epConfig.getLogConfig();
                MediaConfig mediaConfig = epConfig.getMedConfig();

                uaConfig.setUserAgent(jdPjsipAppConfig.getUaAgent());
                uaConfig.getStunServer().clear();
                if (jdPjsipAppConfig.isOwnWorkThread()) {
                    uaConfig.setThreadCnt(0);
                    uaConfig.setMainThreadOnly(true);
                }

                logConfig.setLevel(jdPjsipAppConfig.getLogLevel());
                logConfig.setConsoleLevel(jdPjsipAppConfig.getLogLevel());
                //logConfig.setWriter(new JdPjsipLogger());
                //logConfig.setDecor(logConfig.getDecor() & (~jdPjsipAppConfig.getLogdecor()));

                mediaConfig.setEcOptions(jdPjsipAppConfig.getEcOptions());
                mediaConfig.setEcTailLen(jdPjsipAppConfig.getEcTailLen());
                mediaConfig.setIlbcMode(jdPjsipAppConfig.getIlbcMode());
                mediaConfig.setRxDropPct(jdPjsipAppConfig.getRxDropPct());

                try {
                    ep.libInit(epConfig);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                    destory();
                    return;
                }

                boolean bCreateTransportSuccess = false;
                TransportConfig sipTransportConfig = new TransportConfig();
                sipTransportConfig.setPort(5060);
                try {
                    ep.transportCreate(pjsip_transport_type_e.PJSIP_TRANSPORT_UDP,
                            sipTransportConfig);
                    bCreateTransportSuccess = true;
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
//                try {
//                    ep.transportCreate(pjsip_transport_type_e.PJSIP_TRANSPORT_TCP,
//                            sipTransportConfig);
//                    bCreateTransportSuccess = true;
//                } catch (Exception e) {
//                    Log.d(TAG, e.toString());
//                }
                if (!bCreateTransportSuccess) {
                    destory();
                }
                try {
                    ep.libStart();
                    bInit = true;
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                    destory();
                }
            }
        }
    }

    public void unInit() {
        if (bInit) {
            destory();
            bInit = false;
        }
    }

    /*Account Interface*/
    public String addAccount(String username, String password, String domain, String proxy) {
        if (bInit && accMgr != null) {
            return accMgr.addAccount(username, password, domain, proxy);
        } else {
            return "";
        }
    }

    public boolean delAccount(String accName) {
        if (bInit && accMgr != null) {
            accMgr.delAccount(accName);
        } else {
            return false;
        }
        return true;
    }

    public JdAccount getAccount(String accName) {
        if (bInit && accMgr != null) {
            return accMgr.getAccount(accName);
        }
        return null;
    }

    public boolean registerAccount(String accName) {
        if (bInit && accMgr != null) {
            return accMgr.registerAccount(accName);
        }
        return false;
    }

    public void unRegisterAccount(String accName) {
        if (bInit && accMgr != null) {
            accMgr.unRegisterAccount(accName);
        }
    }

    private void destory() {
        if (ep != null) {
            try {
                ep.libDestroy();
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
            ep.delete();
            bInit = false;
        }
    }

    /*app init config*/
    public JdPjsipAppConfig getInitConfig() {
        return jdPjsipAppConfig;
    }

    /*Audio Codec Interface*/
    public boolean setOnlyAudioCodec(String codecId) {
        if (bInit && codecMgr != null) {
            return codecMgr.setOnlyAudioCodec(codecId);
        }
        return false;
    }

    public List<JdAudioCodec> getAudioCodecInfo() {
        if (bInit && codecMgr != null) {
            return codecMgr.getAudioCodec();
        }
        return null;
    }

    public String getCalleeUri(String callee, String domain)
    {
        String uri = "";
        if(!callee.startsWith("sip:"))
        {

            if(callee.indexOf("@") == -1)
            {
                uri = "sip:" + callee + "@" + domain;
            }
            else
            {
                uri = "sip:" + callee;
            }
        }
        else
        {
            if(callee.indexOf("@") >= 0)
            {
                uri = callee;
            }
            else
            {
                uri = callee + "@" + domain;
            }
        }
        return uri;
    }

    /*observer inteface implements*/
    public void MakeCall(String callee, String accName) {
        JdAccount account = accMgr.getAccount(accName);
        JdCall call = new JdCall(account, -1, this);
        CallOpParam prm = new CallOpParam(true);
        try {
            call.makeCall(callee, prm);
        } catch (Exception e) {
            System.out.println("call("+callee +") exception :"+e.getStackTrace());
            call.delete();
            return;
        }

        currentCall = call;

    }

    public void Hangup(CallOpParam prm)
    {
        if(currentCall == null)
            return;

        try {
            currentCall.hangup(prm);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public boolean Accept()
    {
        if(currentCall == null)
            return false;

        CallOpParam prm = new CallOpParam();
        prm.setStatusCode(pjsip_status_code.PJSIP_SC_OK);
        try {
            currentCall.answer(prm);
        }catch (Exception e)
        {
            System.out.println(e);
            return  false;
        }

        return  true;
    }

    @Override
    public void notifyRegStateChanged(String accName, JdAccStatus status, String reason) {
        if (bInit && jdPjsipObserver != null) {
            jdPjsipObserver.OnAccStatus(accName, status, reason);
        }
        if (messageHandler != null) {
            AccStatusMsg amsg = new AccStatusMsg(accName, status, reason);
            Message msg = messageHandler.obtainMessage(JdPjsipMessageType.JD_PJSIP_MESSAGE_TYPE_REG_STATUS, amsg);
            msg.sendToTarget();
        }
    }

    @Override
    public void notifyCallIncoming(String accName, OnIncomingCallParam prm) {
        if (bInit) {

            if (currentCall != null)  //����ͨ��
            {
                return;
            }

            JdAccount acc = accMgr.getAccount(accName);
            JdCall call = new JdCall(acc, prm.getCallId(), this);

            if (jdPjsipObserver != null) {
            }
            if (messageHandler != null) {
               Message msg =  messageHandler.obtainMessage(JdPjsipMessageType.JD_PJSIP_MESSAGE_TYPE_IN_COMING_CALL, call);
                msg.sendToTarget();
            }


        }
    }


    @Override
    public void notifyCallState(JdCall call) {
        if (currentCall == null || call.getId() != currentCall.getId())
            return;

        CallInfo ci;
        try {
            ci = call.getInfo();
        } catch (Exception e) {
            ci = null;
        }

        //֪ͨ�ϲ�
        Message m = messageHandler.obtainMessage(JdPjsipMessageType.JD_PJSIP_MESSAGE_TYPE_CALL_STATE, ci);
        m.sendToTarget();



        if (ci != null &&
                ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {
            currentCall = null;
        }
    }

    @Override
    public void notifyCallMediaState(JdCall call) {

    }


}