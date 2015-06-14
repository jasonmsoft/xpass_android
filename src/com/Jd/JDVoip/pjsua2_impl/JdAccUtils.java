package com.Jd.JDVoip.pjsua2_impl;

import android.provider.Settings;
import android.util.Log;

import java.util.Random;

/**
 * Created by cdmaji1 on 2015/6/5.
 */
public class JdAccUtils {
	private static final String SIP_PREFIX = "sip:";
	private static final String SIP_SEP = "@";
	private static final String LOG_TAG = "[JDACC]";
	private static final String AUTH_DIGEST = "digest";
	private static final String ACC_NAME_PREFIX = "JDACC:";
	private static final int AUTH_PWD_TYPE_PLAINTEXT = 0;
	public static final String EMPTY_STRING = "";




	static public void log(String msg)
	{
		Log.d(LOG_TAG, msg);
		return;
	}

	static public String createRegisterUri(String proxy)
	{
		if(proxy.startsWith(SIP_PREFIX))
		{
			return proxy;
		}
		else
		{
			return SIP_PREFIX + proxy;
		}
	}

	static public String createIdUri(String username, String domain)
	{
		return SIP_PREFIX + username + SIP_SEP + domain;
	}

	static public String createProxyUri(String proxy)
	{
		return createRegisterUri(proxy);
	}

	static public String createAccName(String username, String domain)
	{
		String AccName = ACC_NAME_PREFIX + username + SIP_SEP +domain;
		Random random = new Random(System.currentTimeMillis());
		int rand = random.nextInt(1000);
		String strRand = Integer.toString(rand);
		return AccName + strRand;
	}

	static public String getAuthSchemeDigest()
	{
		return AUTH_DIGEST;
	}


	static public int getAuthPwdTypePlainText()
	{
		return AUTH_PWD_TYPE_PLAINTEXT;
	}


}
