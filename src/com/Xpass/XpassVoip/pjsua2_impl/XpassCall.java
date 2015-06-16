package com.Xpass.XpassVoip.pjsua2_impl;

import org.pjsip.pjsua2.*;


/**
 * Created by yanxiaoyong on 2015/6/5.
 */
public class XpassCall extends Call
{

	private XpassCallObserver observer;
	public static final int CALL_STATE_CLOSED = 1;
	public static final int CALL_STATE_CONFIRMED = 2;
	public static final int INVALID_CALL_ID = -1;

	public XpassCall(XpassAccount xpass_Acc, int call_id, XpassCallObserver obr)
	{
		super(xpass_Acc, call_id);
		observer = obr;
	}



	@Override
	public void onCallState(OnCallStateParam prm)
	{

		try
		{
			CallInfo ci = getInfo();
			if (ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED)
			{
				XpassUtil.log("call is disconnected !!");
				if (observer != null)
					observer.notifyCallState(getId(), CALL_STATE_CLOSED);
				this.delete();
			}
			else if(ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED)
			{
				XpassUtil.log("call is confirmed !!");
				if (observer != null)
					observer.notifyCallState(getId(), CALL_STATE_CONFIRMED);
			}
		}
		catch (Exception e)
		{
			XpassUtil.log("on call state exception:" + e.getMessage());
			return;
		}
	}

	@Override
	public void onCallMediaState(OnCallMediaStateParam prm)
	{
		CallInfo cf;
		try
		{
			cf = getInfo();
		}
		catch (Exception e)
		{
			return;
		}

		CallMediaInfoVector cmiv = cf.getMedia();

		for (int i = 0; i < cmiv.size(); i++)
		{
			CallMediaInfo cmi = cmiv.get(i);
			if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_AUDIO &&
				(cmi.getStatus() == pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE ||
					cmi.getStatus() == pjsua_call_media_status.PJSUA_CALL_MEDIA_REMOTE_HOLD))
			{
				// unfortunately, on Java too, the returned Media cannot be
				// downcasted to AudioMedia
				Media m = getMedia(i);
				AudioMedia am = AudioMedia.typecastFromMedia(m);

				// connect ports
				try
				{
					XpassPjsipApp.getInstance().getEndpoint().audDevManager().getCaptureDevMedia().startTransmit(am);
					am.startTransmit(XpassPjsipApp.getInstance().getEndpoint().audDevManager().getPlaybackDevMedia());
				}
				catch (Exception e)
				{
					XpassUtil.log("media state exception:"+e.getMessage());
					continue;
				}
			}
			else if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_VIDEO &&
				cmi.getStatus() ==
					pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE &&
				cmi.getVideoIncomingWindowId() != pjsua2.INVALID_ID)
			{
				//  vidWin = new VideoWindow(cmi.getVideoIncomingWindowId());
				XpassUtil.log("video is not surpport");
			}
		}

		//observer.notifyCallMediaState(getId(), );
	}


	public  void hangupCall()
	{
		CallOpParam prm = new CallOpParam();
		prm.setStatusCode(pjsip_status_code.PJSIP_SC_DECLINE);
		try
		{
			hangup(prm);
		}
		catch (Exception e)
		{
			XpassUtil.log("hangup call exception: " + e.getMessage());
			e.printStackTrace();
		}
	}


	public void cancelCall()
	{
		CallOpParam prm = new CallOpParam();
		prm.setStatusCode(pjsip_status_code.PJSIP_SC_DECLINE);
		try
		{
			hangup(prm);
		}
		catch (Exception e)
		{
			XpassUtil.log("cancel call exception: " + e.getMessage());
			e.printStackTrace();
		}
	}


	public void answerCall()
	{
		CallOpParam prm = new CallOpParam();
		prm.setStatusCode(pjsip_status_code.PJSIP_SC_OK);
		try
		{
			answer(prm);
		}
		catch (Exception e)
		{
			XpassUtil.log("answer call exception :" + e.getMessage());
			e.printStackTrace();
		}
	}


	public void ringCall()
	{
		CallOpParam prm = new CallOpParam();
		prm.setStatusCode(pjsip_status_code.PJSIP_SC_RINGING);
		try
		{
			answer(prm);
		}
		catch (Exception e)
		{
			XpassUtil.log("ring call exception :" + e.getMessage());
			e.printStackTrace();
		}
	}


	public String getCalleeUri()
	{
		try
		{
			CallInfo ci = getInfo();
			return ci.getRemoteUri();
		}
		catch (Exception e)
		{
			XpassUtil.log("get callee info exception: "+ e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
}
