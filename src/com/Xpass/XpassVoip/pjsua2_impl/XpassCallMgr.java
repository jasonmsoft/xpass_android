package com.Xpass.XpassVoip.pjsua2_impl;

import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.OnIncomingCallParam;
import org.pjsip.pjsua2.pjsip_status_code;

import java.util.ArrayList;

/**
 * Created by cdmaji1 on 2015/6/16.
 */
public class XpassCallMgr implements  XpassCallObserver {
	private ArrayList<XpassCall> mCallLst = new ArrayList<XpassCall>();
	private XpassCallObserver mOb = null;
	public XpassCallMgr(XpassCallObserver ob)
	{
		mOb = ob;
	}

	//return call id, if error return -1
	public int makeCall(String callee, String accName, XpassAccMgr accMgr)
	{
		XpassAccount acc = accMgr.getAccount(accName);
		XpassCall call = new XpassCall(acc, XpassCall.INVALID_CALL_ID, this);
		CallOpParam prm = new CallOpParam(true);
		try
		{
			call.makeCall(callee, prm);
			mCallLst.add(call);
			return call.getId();
		}
		catch (Exception e)
		{
			XpassUtil.log("call(" + callee + ") exception :" + e.getStackTrace());
			call.delete();
			return XpassCall.INVALID_CALL_ID;
		}
	}

	protected XpassCall getCall(int callId)
	{
		for(int i = 0; i < mCallLst.size(); i++)
		{
			if(mCallLst.get(i).getId() == callId)
			{
				return mCallLst.get(i);
			}
		}
		return null;
	}

	public void hangupCall(int callId)
	{
		getCall(callId).hangupCall();
	}

	public void cancelCall(int callId)
	{
		getCall(callId).cancelCall();
	}

	public void answerCall(int callId)
	{
		getCall(callId).answerCall();
	}

	public void ringCall(int callId)
	{
		getCall(callId).ringCall();
	}


	public int createCall(String accName, XpassAccMgr accMgr, OnIncomingCallParam prm,  XpassCallObserver ob)
	{
		XpassCall call = new XpassCall(accMgr.getAccount(accName), prm.getCallId(), ob);
		mCallLst.add(call);
		return call.getId();
	}

	protected void removeCall(int callId)
	{
		for(int i = 0; i < mCallLst.size(); i++)
		{
			if(callId == mCallLst.get(i).getId())
			{
				mCallLst.remove(i);
				return;
			}
		}
	}

	public String getCalleeUri(int callId)
	{
		return getCall(callId).getCalleeUri();
	}


	public void notifyCallState(int callId, int state)
	{
		if(state == XpassCall.CALL_STATE_CLOSED)
		{
			removeCall(callId);
		}

		if(mOb != null)
		{
			mOb.notifyCallState(callId, state);
		}

	}


    public void notifyCallMediaState(int callId, int state)
    {

    }

}
