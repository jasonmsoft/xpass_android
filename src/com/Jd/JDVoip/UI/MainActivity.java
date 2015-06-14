package com.Jd.JDVoip.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import com.Jd.JDVoip.R;
import com.Jd.JDVoip.localdata.AccountInfo;
import com.Jd.JDVoip.pjsua2_impl.*;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.pjsip_status_code;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    public static JdPjsipApp app = null;
    private String accName;

    private EditText    m_EtPin;
    private EditText    m_EtPwd;
    private EditText    m_EtProxy;
    private EditText    m_EtCallPin;
    private EditText    m_EtDomain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        m_EtPin = (EditText)findViewById(R.id.et_pin);
        m_EtPwd = (EditText)findViewById(R.id.et_pwd);
        m_EtProxy = (EditText)findViewById(R.id.et_proxy);
        m_EtCallPin = (EditText)findViewById(R.id.et_call_pin);
        m_EtDomain = (EditText) findViewById(R.id.et_domain);

        AccountInfo accountInfo = new AccountInfo();
        if (accountInfo.readAccount(getFilesDir().getAbsolutePath())){
            m_EtPin.setText(accountInfo.getUsername());
            m_EtPwd.setText(accountInfo.getPassword());
            m_EtProxy.setText(accountInfo.getProxy());
            m_EtDomain.setText(accountInfo.getDomain());

        }else {
            m_EtPin.setText("testname");
            m_EtPwd.setText("123456");
            m_EtProxy.setText("10.28.163.203");
            m_EtDomain.setText("voip.com");
            m_EtCallPin.setText("10086");
        }

        JdPjsipApp.getInstance().init();
        if (handler == null){
            handler = new MainActivityHandler();
            JdPjsipApp.getInstance().setMessgaeHandler(handler);
        }

    }

    protected String getUsername()
    {
        TextView tvUsername = (TextView)findViewById(R.id.et_pin);
        return tvUsername.getText().toString();
    }

    protected String getPassword()
    {
        TextView tvPassword = (TextView) findViewById(R.id.et_pwd);
        return tvPassword.getText().toString();
    }


    protected String getProxy()
    {
        TextView tvProxy = (TextView) findViewById(R.id.et_proxy);
        return tvProxy.getText().toString();
    }


    protected String getDomain()
    {
        EditText etDomain = (EditText) findViewById(R.id.et_domain);
        return etDomain.getText().toString();
    }

    protected boolean validate(String username, String password, String proxy, String domain)
    {
        if(username.isEmpty() || password.isEmpty() || proxy.isEmpty() || domain.isEmpty())
        {
            return false;
        }
        return true;
    }

    public void onRegister(View view)
    {
        String username = getUsername();
        String password = getPassword();
        String proxy = getProxy();
        String domain = getDomain();
        if(!validate(username,password,proxy, domain))
        {
            Toast.makeText(this, R.string.user_tip, Toast.LENGTH_SHORT).show();
            return;
        }
//        if (accName == null || accName.isEmpty()) {
//            accName = JdPjsipApp.getInstance().addAccount(username, password, "voip.jd.com", proxy);
//        }else{
//            JdPjsipApp.getInstance().registerAccount(accName);
//        }
        if (accName != null && !accName.isEmpty()){
            JdPjsipApp.getInstance().delAccount(accName);
        }
        accName = JdPjsipApp.getInstance().addAccount(username, password, domain, proxy);
    }

    private void ShowCallActivity(String name, CallActivity.CALL_STATE state)
    {
        Intent intent = new Intent(this, CallActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("name", name);
        intent.putExtra("state", state.ordinal());
        startActivity(intent);
    }

    public void onCall(View view)
    {
        String callee = m_EtCallPin.getText().toString().trim();
        if(callee.isEmpty() || accName==null || accName.isEmpty())
            return;
        String calleeUri = JdPjsipApp.getInstance().getCalleeUri(callee, getDomain());
        JdPjsipApp.getInstance().MakeCall(calleeUri, accName);
        ShowCallActivity(callee, CallActivity.CALL_STATE.CS_CALLER); //主叫
    }

    private MainActivityHandler handler = null;
    private class MainActivityHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            switch (msg.what) {
                case JdPjsipMessageType.JD_PJSIP_MESSAGE_TYPE_IN_COMING_CALL: {
                    JdCall call = (JdCall) msg.obj;
                    CallOpParam prm = new CallOpParam();
                    try {
                        prm.setStatusCode(pjsip_status_code.PJSIP_SC_RINGING);
                        call.answer(prm);

                        JdPjsipApp.getInstance().setCurrentCall(call);

                        String uri = call.getInfo().getRemoteUri();
                        JdSipUri jdUri = JdPjsipAppUtil.phraseUri(uri);

                        ShowCallActivity(jdUri.getName(), CallActivity.CALL_STATE.CS_CALLED);

                    } catch (Exception e) {
                        call.delete();
                        JdPjsipApp.getInstance().setCurrentCall(null);
                        return;
                    }
                }
                break;
                case JdPjsipMessageType.JD_PJSIP_MESSAGE_TYPE_CALL_STATE: {
                    CallInfo ci = (CallInfo) msg.obj;

                    if (CallActivity.handler_ != null) {
                        Message m = Message.obtain(CallActivity.handler_, JdPjsipMessageType.JD_PJSIP_MESSAGE_TYPE_CALL_STATE, ci);
                        m.sendToTarget();
                    }
                }
                break;
                case JdPjsipMessageType.JD_PJSIP_MESSAGE_TYPE_REG_STATUS:
                    AccStatusMsg statusMsg = (AccStatusMsg) msg.obj;
                    if (statusMsg != null) {
                        TextView textView = (TextView) findViewById(R.id.tv_reg_status);
                        if (textView != null) {
                            textView.setText(statusMsg.getAccReason());
                        }
                        if (JdAccStatus.JD_ACC_REG_STATUS_OK == statusMsg.getAccStatus()){
                            com.Jd.JDVoip.localdata.AccountInfo accountInfo = new AccountInfo();
                            accountInfo.writeAccount(getFilesDir().getAbsolutePath(), getUsername(), getPassword(), getProxy(), getDomain());
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
