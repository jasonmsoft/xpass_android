package com.Xpass.XpassVoip.pjsua2_impl;

import org.pjsip.pjsua2.*;

/**
 * Created by cdmaji1 on 2015/6/5.
 */
public class XpassAccConfig extends AccountConfig
{
	private String mUsername = XpassAccUtils.EMPTY_STRING;

	public void setUsername(String username)
	{
		mUsername = username;
		return;
	}

	public String getUsername()
	{
		return mUsername;
	}
}
