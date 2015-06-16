package com.Xpass.XpassVoip.xpass_service;

import android.util.Log;

/**
 * Created by cdmaji1 on 2015/6/16.
 */
public class XpassSrvUtil {
	private static final String mDebugTag = "XPASS_SRV_D:";
	private static final String mInfoTag = "XPASS_SRV_I:";
	private static final String mErrTag = "XPASS_SRV_E:";

	static public void debug(String log)
	{
		Log.d(mDebugTag, log);
	}

	static public void info(String log)
	{
		Log.d(mInfoTag, log);
	}

	static public void error(String log)
	{
		Log.d(mErrTag, log);
	}

}
