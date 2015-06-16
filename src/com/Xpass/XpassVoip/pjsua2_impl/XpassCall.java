package com.Xpass.XpassVoip.pjsua2_impl;

import org.pjsip.pjsua2.*;


/**
 * Created by yanxiaoyong on 2015/6/5.
 */
public class XpassCall extends Call{

    private XpassCallObserver   observer;

    public  XpassCall(XpassAccount Xpass_Acc, int call_id, XpassCallObserver obr)
    {
        super(Xpass_Acc, call_id);
        observer = obr;
    }

    @Override
    public void onCallState(OnCallStateParam prm)
    {
        if(observer != null)
            observer.notifyCallState(this);

        try {
            CallInfo cf = getInfo();
            if(cf.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED)
            {
                this.delete();
            }
        }
        catch (Exception e)
        {
            return;
        }
    }

    @Override
    public void onCallMediaState(OnCallMediaStateParam prm)
    {
        CallInfo cf;
        try {
            cf = getInfo();
        }
        catch (Exception e)
        {
            return;
        }

       CallMediaInfoVector cmiv = cf.getMedia();

        for (int i = 0; i < cmiv.size(); i++) {
            CallMediaInfo cmi = cmiv.get(i);
            if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_AUDIO &&
                    (cmi.getStatus() ==
                            pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE ||
                            cmi.getStatus() ==
                                    pjsua_call_media_status.PJSUA_CALL_MEDIA_REMOTE_HOLD))
            {
                // unfortunately, on Java too, the returned Media cannot be
                // downcasted to AudioMedia
               Media m = getMedia(i);
                AudioMedia am = AudioMedia.typecastFromMedia(m);

                // connect ports
                try {
                    XpassPjsipApp.getInstance().getEndpoint().audDevManager().getCaptureDevMedia().
                            startTransmit(am);
                    am.startTransmit(XpassPjsipApp.getInstance().getEndpoint().audDevManager().
                            getPlaybackDevMedia());
                } catch (Exception e) {
                    continue;
                }
            } else if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_VIDEO &&
                    cmi.getStatus() ==
                            pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE &&
                    cmi.getVideoIncomingWindowId() != pjsua2.INVALID_ID)
            {
              //  vidWin = new VideoWindow(cmi.getVideoIncomingWindowId());
            }
        }

        observer.notifyCallMediaState(this);
    }
}
