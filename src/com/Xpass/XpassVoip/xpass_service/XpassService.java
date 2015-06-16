package com.Xpass.XpassVoip.xpass_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.Xpass.XpassVoip.pjsua2_impl.XpassPjsipApp;
import com.Xpass.XpassVoip.xpass_service.XpassSrvUtil;

/**
 * Created by cdmaji1 on 2015/6/14.
 */
public class XpassService extends Service{

private static XpassPjsipApp mApp = null;
private boolean threadDisable = false;
private int count = 0;
@Override
public IBinder onBind(Intent intent) {
	return null;
}


@Override
public void onCreate()
{
	XpassSrvUtil.info("app initialize finished");
	if(mApp == null)
	{
		//mApp = XpassPjsipApp.getInstance();
		//mApp.init();
		XpassSrvUtil.info("app initialize finished");
	}

	new Thread(new Runnable() {
		public void run() {
			while (!threadDisable) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

				}
				count++;
				//XpassSrvUtil.info("Count is:" + count);
			}
		}
	}).start();
}



@Override
public int onStartCommand(Intent intent, int flags, int startId)
{
	XpassSrvUtil.info("start command xpass service!");
	return 0;
}

@Override
public void onDestroy()
{
	XpassSrvUtil.info("destroy xpass service!");
}

}
