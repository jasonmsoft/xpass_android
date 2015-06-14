package com.Jd.JDVoip.pjsua2_impl;


import org.pjsip.pjsua2.*;

/**
 * Created by yanxiaoyong on 2015/6/4.
 */
public class JdAccount extends Account {
	private String mUsername = JdAccUtils.EMPTY_STRING;
	private String mPassword = JdAccUtils.EMPTY_STRING;
	private String mDomain = JdAccUtils.EMPTY_STRING;
	private String mProxy = JdAccUtils.EMPTY_STRING;
	private String mAccName = JdAccUtils.EMPTY_STRING;
	private JdAccConfig mAccConfig = new JdAccConfig();
	private JdAccObserver mObserver = null;

	public JdAccount(String username, String password, String domain, String proxy)
	{
		mUsername = username;
		mPassword = password;
		mDomain = domain;
		mProxy = proxy;
		mAccName = JdAccUtils.createAccName(mUsername, mDomain);
	}


	private boolean isReady()
	{
		if(mUsername.isEmpty() || mPassword.isEmpty() || mDomain.isEmpty() || mProxy.isEmpty())
		{
			return false;
		}
		return true;
	}

	private String getRegisterUri()
	{
		if(!isReady())
		{
			return JdAccUtils.EMPTY_STRING;
		}
		return JdAccUtils.createRegisterUri(mProxy);
	}

	private String getIdUri()
	{
		if(!isReady())
		{
			return JdAccUtils.EMPTY_STRING;
		}
		return JdAccUtils.createIdUri(mUsername, mDomain);

	}

	private String getProxyUri()
	{
		if(!isReady())
		{
			return JdAccUtils.EMPTY_STRING;
		}
		return JdAccUtils.createProxyUri(mProxy);
	}

	private String getAuthDigest()
	{
		return JdAccUtils.getAuthSchemeDigest();
	}

	private String getRealm()
	{
		return mDomain;
	}

	private int getPwdPlainTxtType()
	{
		return JdAccUtils.getAuthPwdTypePlainText();
	}

	private String getPwd()
	{
		return mPassword;
	}

	private String getUsername()
	{
		return mUsername;
	}


	public boolean init()
	{
		String regUri = getRegisterUri();
		String accIdUri = getIdUri();
		StringVector proxyUriVector = new StringVector();
		String proxyUri = getProxyUri();
		AuthCredInfo authInfo = new AuthCredInfo();

		if(regUri.isEmpty() || accIdUri.isEmpty() || proxyUri.isEmpty())
		{
			JdAccUtils.log("account info is not ready");
			return false;
		}

		mAccConfig.setUsername(getUsername());
		mAccConfig.setIdUri(accIdUri);
		mAccConfig.getRegConfig().setRegistrarUri(regUri);
		proxyUriVector.add(proxyUri);
		mAccConfig.getSipConfig().setProxies(proxyUriVector);
		authInfo.setUsername(getUsername());
		authInfo.setScheme(getAuthDigest());
		authInfo.setRealm(getRealm());
		authInfo.setDataType(getPwdPlainTxtType());
		authInfo.setData(getPwd());
		mAccConfig.getSipConfig().getAuthCreds().add(authInfo);

		try
		{
			super.create(mAccConfig);
		}
		catch (Exception e)
		{
			JdAccUtils.log("create account exception:"+e.getMessage());
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void setObserver(JdAccObserver observer)
	{
		mObserver = observer;
		return;
	}


	public boolean register()
	{
		if(!isReady())
		{
			return false;
		}

		try
		{
			super.setRegistration(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JdAccUtils.log("account register exception:"+e.getMessage());
			return false;
		}
		return true;
	}

	public boolean unRegister()
	{
		if(!isReady())
		{
			return false;
		}

		try
		{
			super.setRegistration(false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JdAccUtils.log("account unregister exception:"+e.getMessage());
			return false;
		}
		return true;
	}

	public String getAccName()
	{
		return mAccName;
	}


	@Override
	public void onRegState(OnRegStateParam prm)
	{
		pjsip_status_code code = prm.getCode();
		int expire = prm.getExpiration();
		String reason = prm.getReason();
		int status = prm.getStatus();

		if(mObserver == null)
		{
			JdAccUtils.log("observer is null, can't fire event");
			return;
		}

		JdAccUtils.log("register status code:"+code.toString() + " expire:"+expire+" reason:"+reason+" status: "+status);
		if(code.swigValue() == pjsip_status_code.PJSIP_SC_OK.swigValue())
		{
			mObserver.notifyRegStateChanged(getAccName(), JdAccStatus.JD_ACC_REG_STATUS_OK, reason);
		}
		else if(code.swigValue() >= 300)
		{
			mObserver.notifyRegStateChanged(getAccName(), JdAccStatus.JD_ACC_REG_STATUS_FAILED, reason);
		}
	}

	@Override
	public void onIncomingCall(OnIncomingCallParam prm)
	{
		if(mObserver == null)
		{
			JdAccUtils.log("observer is null, can't fire event");
			return;
		}
		mObserver.notifyCallIncoming(getAccName(), prm);
		return;
	}
}

