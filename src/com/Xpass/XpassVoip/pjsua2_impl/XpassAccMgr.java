package com.Xpass.XpassVoip.pjsua2_impl;

import org.pjsip.pjsua2.*;

import java.util.ArrayList;

/**
 * Created by cdmaji1 on 2015/6/5.
 */
public class XpassAccMgr  implements XpassAccObserver {
	private ArrayList<XpassAccount> mAccList = new ArrayList<XpassAccount>();
	private XpassAccObserver mAccObserver = null;

	public XpassAccMgr(XpassAccObserver ob)
	{
		mAccObserver = ob;
	}

	protected boolean isValidAccount(String accName)
	{
		for(int i = 0; i < mAccList.size(); i++)
		{
			if(mAccList.get(i).getAccName().equals(accName))
			{
				return true;
			}
		}
		return false;
	}


	public void notifyRegStateChanged(String accName, XpassAccStatus status, String reason)
	{
		if(mAccObserver != null)
		{
			if(isValidAccount(accName))
			{
				mAccObserver.notifyRegStateChanged(accName, status, reason);
			}
			else
			{
				XpassAccUtils.log("unknown account :"+accName + " fire register event");
			}
		}
		else
		{
			XpassAccUtils.log("account observer is not set, not fire event");
		}
	}

	public  void notifyCallIncoming(String accName, OnIncomingCallParam prm)
	{
		if(mAccObserver != null)
		{
			if(isValidAccount(accName))
			{
				mAccObserver.notifyCallIncoming(accName, prm);
			}
			else
			{
				XpassAccUtils.log("unknown account :"+accName + " fire call coming event");
			}
		}
		else
		{
			XpassAccUtils.log("account observer is not set, not fire event");
		}
		return;
	}

	//password is plain text
	public String addAccount(String username, String password, String domain, String proxy)
	{
		XpassAccount acc = new XpassAccount(username, password, domain, proxy);
		if(!acc.init())
		{
			XpassAccUtils.log("create account failed.");
			return XpassAccUtils.EMPTY_STRING;
		}
		acc.setObserver(this);
		mAccList.add(acc);
		return acc.getAccName();
	}

	public void delAccount(String accName)
	{
		XpassAccount account = null;
		for(int i =0; i < mAccList.size(); i++)
		{
			account = mAccList.get(i);
			if(account.getAccName().equals(accName))
			{
				mAccList.remove(i);
				break;
			}
		}
		account.delete();
		return;
	}

	public XpassAccount getAccount(String accName)
	{
		XpassAccount account = null;
		for(int i =0; i < mAccList.size(); i++)
		{
			account = mAccList.get(i);
			if(account.getAccName().equals(accName))
			{
				return account;
			}
		}
		return null;
	}

	public boolean registerAccount(String accName)
	{
		XpassAccount account = null;
		for(int i =0; i < mAccList.size(); i++)
		{
			account = mAccList.get(i);
			if(accName.equals(account.getAccName()))
			{
				return account.register();
			}
		}
		XpassAccUtils.log("not found this account by name:"+accName);
		return false;
	}

	public void registerAll()
	{
		boolean ret = false;
		for(int i =0; i < mAccList.size(); i++)
		{
			ret = mAccList.get(i).register();
			if(!ret)
			{
				XpassAccUtils.log("register acc:"+mAccList.get(i).getAccName()+" failed");
			}
		}
		return;
	}


	public void unRegisterAccount(String accName)
	{
		XpassAccount account = null;
		for(int i =0; i < mAccList.size(); i++)
		{
			account = mAccList.get(i);
			if(accName.equals(account.getAccName()))
			{
				account.unRegister();
			}
		}
		XpassAccUtils.log("not found this account by name:"+accName);
		return ;
	}

	public void unRegisterAll()
	{
		boolean ret = false;
		for(int i =0; i < mAccList.size(); i++)
		{
			ret = mAccList.get(i).unRegister();
			if(!ret)
			{
				XpassAccUtils.log("unregister acc:"+mAccList.get(i).getAccName()+" failed");
			}
		}
		return;
	}

}
