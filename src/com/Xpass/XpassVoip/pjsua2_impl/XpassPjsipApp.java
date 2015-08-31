package com.Xpass.XpassVoip.pjsua2_impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.pjsip.pjsua2.*;

import java.util.List;

/**
 * Created by yanxiaoyong on 2015/6/4.
 */
public class XpassPjsipApp implements XpassAccObserver, XpassCallObserver
{
	private static final String TAG = XpassPjsipApp.class.getName();
	private static XpassPjsipApp app = null;

	static
	{
		try
		{
			System.loadLibrary("pjsua2");
		}
		catch (UnsatisfiedLinkError e)
		{
			XpassUtil.log("load pjsua2 library failed");
		}
	}

	private XpassAccMgr accMgr = null;
	private int currentCall = XpassCall.INVALID_CALL_ID;
	private XpassCallMgr callMgr = null;
	private XpassCodecMgr codecMgr = null;
	private Endpoint ep = null;
	private boolean bInit = false;
	private Handler messageHandler = null;
	private XpassPjsipObserver XpassPjsipObserver = null;
	private XpassPjsipAppConfig XpassPjsipAppConfig = null;

	private XpassPjsipApp()
	{
		accMgr = new XpassAccMgr(this);
		callMgr = new XpassCallMgr(this);
		XpassPjsipAppConfig = new XpassPjsipAppConfig();
	}

	public static XpassPjsipApp getInstance()
	{
		if (app == null)
			app = new XpassPjsipApp();

		return app;
	}

	public Endpoint getEndpoint()
	{
		return ep;
	}

	public void setMessgaeHandler(Handler messageHandler)
	{
		this.messageHandler = messageHandler;
	}

	public void setXpassPjsipObserver(XpassPjsipObserver XpassPjsipObserver)
	{
		this.XpassPjsipObserver = XpassPjsipObserver;
	}

	public void setCurrentCall(int  callId)
	{
		currentCall = callId;
	}

	public void init()
	{
		if (!bInit)
		{
			if (ep == null)
			{
				ep = new Endpoint();
				codecMgr = new XpassCodecMgr(ep);
				try
				{
					ep.libCreate();
				}
				catch (Exception e)
				{
					Log.d(TAG, e.toString());
					return;
				}
				EpConfig epConfig = new EpConfig();
				UaConfig uaConfig = epConfig.getUaConfig();
				LogConfig logConfig = epConfig.getLogConfig();
				MediaConfig mediaConfig = epConfig.getMedConfig();

				uaConfig.setUserAgent(XpassPjsipAppConfig.getUaAgent());
				uaConfig.getStunServer().clear();
				if (XpassPjsipAppConfig.isOwnWorkThread())
				{
					uaConfig.setThreadCnt(0);
					uaConfig.setMainThreadOnly(true);
				}

				logConfig.setLevel(XpassPjsipAppConfig.getLogLevel());
				logConfig.setConsoleLevel(XpassPjsipAppConfig.getLogLevel());
				//logConfig.setWriter(new XpassPjsipLogger());
				//logConfig.setDecor(logConfig.getDecor() & (~XpassPjsipAppConfig.getLogdecor()));

				mediaConfig.setEcOptions(XpassPjsipAppConfig.getEcOptions());
				mediaConfig.setEcTailLen(XpassPjsipAppConfig.getEcTailLen());
				mediaConfig.setIlbcMode(XpassPjsipAppConfig.getIlbcMode());
				mediaConfig.setRxDropPct(XpassPjsipAppConfig.getRxDropPct());
				mediaConfig.setNoVad(true);
				try
				{
					ep.libInit(epConfig);
				}
				catch (Exception e)
				{
					Log.d(TAG, e.toString());
					destory();
					return;
				}

				boolean bCreateTransportSuccess = false;
				TransportConfig sipTransportConfig = new TransportConfig();
				sipTransportConfig.setPort(5060);
				try
				{
					ep.transportCreate(pjsip_transport_type_e.PJSIP_TRANSPORT_UDP,
					                   sipTransportConfig);
					bCreateTransportSuccess = true;
				}
				catch (Exception e)
				{
					Log.d(TAG, e.toString());
				}

				if (!bCreateTransportSuccess)
				{
					destory();
				}
				try
				{
					ep.libStart();
					bInit = true;
				}
				catch (Exception e)
				{
					Log.d(TAG, e.toString());
					destory();
				}
			}
		}
	}

	public void unInit()
	{
		if (bInit)
		{
			destory();
			bInit = false;
		}
	}

	/*Account Interface*/
	public String addAccount(String username, String password, String domain, String proxy)
	{
		if (bInit && accMgr != null)
		{
			return accMgr.addAccount(username, password, domain, proxy);
		}
		else
		{
			return "";
		}
	}

	public boolean delAccount(String accName)
	{
		if (bInit && accMgr != null)
		{
			accMgr.delAccount(accName);
		}
		else
		{
			return false;
		}
		return true;
	}

	public XpassAccount getAccount(String accName)
	{
		if (bInit && accMgr != null)
		{
			return accMgr.getAccount(accName);
		}
		return null;
	}

	public boolean registerAccount(String accName)
	{
		if (bInit && accMgr != null)
		{
			return accMgr.registerAccount(accName);
		}
		return false;
	}

	public void unRegisterAccount(String accName)
	{
		if (bInit && accMgr != null)
		{
			accMgr.unRegisterAccount(accName);
		}
	}

	private void destory()
	{
		if (ep != null)
		{
			try
			{
				ep.libDestroy();
			}
			catch (Exception e)
			{
				Log.d(TAG, e.toString());
			}
			ep.delete();
			bInit = false;
		}
	}

	/*app init config*/
	public XpassPjsipAppConfig getInitConfig()
	{
		return XpassPjsipAppConfig;
	}

	/*Audio Codec Interface*/
	public boolean setOnlyAudioCodec(String codecId)
	{
		if (bInit && codecMgr != null)
		{
			return codecMgr.setOnlyAudioCodec(codecId);
		}
		return false;
	}

	public List<XpassAudioCodec> getAudioCodecInfo()
	{
		if (bInit && codecMgr != null)
		{
			return codecMgr.getAudioCodec();
		}
		return null;
	}

	public String getCalleeUri(String callee, String domain)
	{
		String uri = "";
		if (!callee.startsWith("sip:"))
		{

			if (callee.indexOf("@") == -1)
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
			if (callee.indexOf("@") >= 0)
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
	public void MakeCall(String callee, String accName)
	{
		XpassAccount account = accMgr.getAccount(accName);
		currentCall = callMgr.makeCall(callee, accName, accMgr);
	}

	public void Hangup()
	{
		if (currentCall == XpassCall.INVALID_CALL_ID)
			return;
		callMgr.hangupCall(currentCall);
	}

	public boolean Accept()
	{
		if (currentCall == XpassCall.INVALID_CALL_ID)
			return false;
		callMgr.answerCall(currentCall);
		return true;
	}

	public void ringCall(int callId)
	{
		callMgr.ringCall(callId);
	}

	public void answerCall(int callId)
	{
		callMgr.answerCall(callId);
	}

	public String getCalleeUri(int callId)
	{
		return callMgr.getCalleeUri(callId);
	}


	@Override
	public void notifyRegStateChanged(String accName, XpassAccStatus status, String reason)
	{
		if (bInit && XpassPjsipObserver != null)
		{
			XpassPjsipObserver.OnAccStatus(accName, status, reason);
		}
		if (messageHandler != null)
		{
			AccStatusMsg amsg = new AccStatusMsg(accName, status, reason);
			Message msg = messageHandler.obtainMessage(XpassPjsipMessageType.Xpass_PJSIP_MESSAGE_TYPE_REG_STATUS, amsg);
			msg.sendToTarget();
		}
	}

	@Override
	public void notifyCallIncoming(String accName, OnIncomingCallParam prm)
	{
		if (bInit)
		{

			if (currentCall != XpassCall.INVALID_CALL_ID)  //����ͨ��
			{
				return;
			}
			currentCall = callMgr.createCall(accName, accMgr, prm, this);
			if (XpassPjsipObserver != null)
			{
			}
			if (messageHandler != null)
			{
				Message msg = messageHandler.obtainMessage(XpassPjsipMessageType.Xpass_PJSIP_MESSAGE_TYPE_IN_COMING_CALL, currentCall, 0);
				msg.sendToTarget();
			}


		}
	}


	@Override
	public void notifyCallState(int callId, int callState)
	{
		if (currentCall == XpassCall.INVALID_CALL_ID || callId != currentCall)
			return;


		Message m = messageHandler.obtainMessage(XpassPjsipMessageType.Xpass_PJSIP_MESSAGE_TYPE_CALL_STATE, callId, callState);
		m.sendToTarget();

		if (callState == XpassCall.CALL_STATE_CLOSED)
		{
			currentCall = XpassCall.INVALID_CALL_ID;
		}
	}

	@Override
	public void notifyCallMediaState(int callId, int mediaState)
	{

	}


}