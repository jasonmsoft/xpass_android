package com.Jd.JDVoip.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.Jd.JDVoip.R;
import android.os.Handler;
import com.Jd.JDVoip.pjsua2_impl.JdAudioMgr;
import com.Jd.JDVoip.pjsua2_impl.JdPjsipApp;
import com.Jd.JDVoip.pjsua2_impl.JdPjsipMessageType;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_status_code;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by yanxiaoyong on 2015/6/8.
 */
public class CallActivity extends Activity
        implements Handler.Callback{

    public enum CALL_STATE{
        CALL_STATE_UNKNOWN,
        CS_CALLER,  //主叫
        CS_CALLED,   //被叫
        CS_CALLING,  //正在通话
        CS_HANGUP,  //被挂断
    }

    public final  static int JD_CALL_TIME_UPDATE = 9999;

    public static Handler handler_ = null;
    private final Handler handler = new Handler(this);
    private final int SPEAKER_STATE_ON = 1;
    private final int SPEAKER_STATE_OFF = 2;
    private int     speakerState = SPEAKER_STATE_OFF;
    private JdAudioMgr   audioMgr = null;

    TextView    textState = null;
    Button      btnHangup = null;
    Button      btnAccept = null;
    TextView    textName = null;
    Button      btnSpeaker = null;
    TextView    textTime = null;




    private  CALL_STATE callState = CALL_STATE.CALL_STATE_UNKNOWN;

    long    startTime = 0;
    boolean  bStopThread = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callactivity);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        callState = CALL_STATE.values()[intent.getIntExtra("state",0)];

        textState = (TextView)findViewById(R.id.textState);
        btnHangup = (Button)findViewById(R.id.btn_hangup);
        textName = (TextView)findViewById(R.id.text_name);
        btnAccept = (Button)findViewById(R.id.btn_accept);
        btnSpeaker = (Button)findViewById(R.id.btn_speaker);
        textTime = (TextView)findViewById(R.id.text_time);

        if(callState == CALL_STATE.CS_CALLER)
        {
            btnAccept.setVisibility(View.GONE);
        }
        else
        {
            btnHangup.setText(R.string.hungup);
            textState.setText(R.string.have_call);
        }


        textName.setText(name);

        audioMgr = new JdAudioMgr(this);
        audioMgr.setRingtoneMode();
        if(audioMgr.isSpeakerOn())
        {
            speakerState = SPEAKER_STATE_ON;
            btnSpeaker.setText(R.string.speaker_state_on);
        }
        else
        {
            speakerState = SPEAKER_STATE_OFF;
            btnSpeaker.setText(R.string.speaker_state_off);
        }
        if(callState == CALL_STATE.CS_CALLED)
        {
            audioMgr.startPlay();
        }
        handler_ = handler;
    }

    @Override
    public boolean handleMessage(Message msg)
    {
        if(msg.what == JdPjsipMessageType.JD_PJSIP_MESSAGE_TYPE_CALL_STATE)
        {
            CallInfo ci = (CallInfo) msg.obj;
            if (ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED)
            {
               StartCall();
            }

            else if (ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED)
            {
                System.out.println("call is disconnected");
                EndCall();
                if (textState != null)
                {
                    textState.setText(R.string.called_text);
                    btnHangup.setText(R.string.finished);
                }
                if(callState == CALL_STATE.CS_CALLED)
                    audioMgr.stopPlay();
                callState = CALL_STATE.CS_HANGUP;
                finish();

            }
        }
        else if(msg.what == JD_CALL_TIME_UPDATE)  //更新时间
        {
            long callTime = System.currentTimeMillis() - startTime;
            String strTime = showTimeCount(callTime);
            textTime.setText(strTime);
        }
        return false;
    }

    public  void onHangup(View view)
    {
        EndCall();
        handler_ = null;
        finish();
        if(callState == CALL_STATE.CS_CALLED)
            audioMgr.stopPlay();


        if(callState == CALL_STATE.CS_CALLER || callState == CALL_STATE.CS_CALLING )
        {
            CallOpParam prm = new CallOpParam();
            prm.setStatusCode(pjsip_status_code.PJSIP_SC_DECLINE);
            JdPjsipApp.getInstance().Hangup(prm);
        }
        callState = CALL_STATE.CALL_STATE_UNKNOWN;
    }

    public  void onAccept(View view)
    {
        if(callState == CALL_STATE.CS_CALLED)
            audioMgr.stopPlay();
        audioMgr.setCommunicationMode();
        if(JdPjsipApp.getInstance().Accept())
        {
            StartCall();
        }
    }


    public void onSpeaker(View view)
    {
        if(speakerState == SPEAKER_STATE_ON)
        {
            btnSpeaker.setText(R.string.speaker_state_off);
            speakerState = SPEAKER_STATE_OFF;
            audioMgr.closeSpeaker();
        }
        else if(speakerState == SPEAKER_STATE_OFF)
        {
            btnSpeaker.setText(R.string.speaker_state_on);
            speakerState = SPEAKER_STATE_ON;
            audioMgr.openSpeaker();
        }
        else
        {
            System.out.println("unknown speaker state: "+ speakerState);
        }
    }


    public void onVolumeLouder(View view)
    {
        audioMgr.adjustVoiceVolumeLouder(1);
        return;
    }

    public void onVolumeLower(View view)
    {
        audioMgr.adjustVoiceVolumeLower(1);
        return;
    }



    private  void StartCall()
    {
        StartTime();
        btnAccept.setVisibility(View.GONE);
        textState.setText(R.string.calling_text);
        callState = CALL_STATE.CS_CALLING;
    }

    private  void EndCall()
    {
        textState.setText(R.string.called_text);
        btnHangup.setText(R.string.finished);
        bStopThread = true;
    }

    private void StartTime()
    {
        startTime = System.currentTimeMillis();
        //开始线程
        new TimeThread().start();
    }

    public class TimeThread extends Thread
    {
        @Override
        public void run()
        {
            while (!bStopThread)
            {
                try
                {
                    Thread.sleep(1000);
                    if(handler_ != null)
                    {
                        Message msg = new Message();
                        msg.what = JD_CALL_TIME_UPDATE;
                        handler_.sendMessage(msg);
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println("timeThread exit!");
            return;
        }
    }

    //时间计数器，最多只能到99小时，如需要更大小时数需要改改方法
    public String showTimeCount(long time) {
        if(time >= 360000000){
            return "00:00:00";
        }
        String timeCount = "";
        long hourc = time/3600000;
        String hour = "0" + hourc;
        hour = hour.substring(hour.length()-2, hour.length());

        long minuec = (time-hourc*3600000)/(60000);
        String minue = "0" + minuec;
        minue = minue.substring(minue.length()-2, minue.length());

        long secc = (time-hourc*3600000-minuec*60000)/1000;
        String sec = "0" + secc;
        sec = sec.substring(sec.length()-2, sec.length());
        timeCount = hour + ":" + minue + ":" + sec;
        return timeCount;
    }

}