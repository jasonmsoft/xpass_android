package com.Jd.JDVoip.pjsua2_impl;

import org.pjsip.pjsua2.*;

import java.util.ArrayList;

/**
 * Created by cdmaji1 on 2015/6/5.
 */
public class JdAccMgr  implements JdAccObserver {
	private ArrayList<JdAccount> mAccList = new ArrayList<JdAccount>();
	private JdAccObserver mAccObserver = null;

	public JdAccMgr(JdAccObserver ob)
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


	public void notifyRegStateChanged(String accName, JdAccStatus status, String reason)
	{
		if(mAccObserver != null)
		{
			if(isValidAccount(accName))
			{
				mAccObserver.notifyRegStateChanged(accName, status, reason);
			}
			else
			{
				JdAccUtils.log("unknown account :"+accName + " fire register event");
			}
		}
		else
		{
			JdAccUtils.log("account observer is not set, not fire event");
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
				JdAccUtils.log("unknown account :"+accName + " fire call coming event");
			}
		}
		else
		{
			JdAccUtils.log("account observer is not set, not fire event");
		}
		return;
	}

	//password is plain text
	public String addAccount(String username, String password, String domain, String proxy)
	{
		JdAccount acc = new JdAccount(username, password, domain, proxy);
		if(!acc.init())
		{
			JdAccUtils.log("create account failed.");
			return JdAccUtils.EMPTY_STRING;
		}
		acc.setObserver(this);
		mAccList.add(acc);
		return acc.getAccName();
	}

	public void delAccount(String accName)
	{
		JdAccount account = null;
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

	public JdAccount getAccount(String accName)
	{
		JdAccount account = null;
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
		JdAccount account = null;
		for(int i =0; i < mAccList.size(); i++)
		{
			account = mAccList.get(i);
			if(accName.equals(account.getAccName()))
			{
				return account.register();
			}
		}
		JdAccUtils.log("not found this account by name:"+accName);
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
				JdAccUtils.log("register acc:"+mAccList.get(i).getAccName()+" failed");
			}
		}
		return;
	}


	public void unRegisterAccount(String accName)
	{
		JdAccount account = null;
		for(int i =0; i < mAccList.size(); i++)
		{
			account = mAccList.get(i);
			if(accName.equals(account.getAccName()))
			{
				account.unRegister();
			}
		}
		JdAccUtils.log("not found this account by name:"+accName);
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
				JdAccUtils.log("unregister acc:"+mAccList.get(i).getAccName()+" failed");
			}
		}
		return;
	}

}
