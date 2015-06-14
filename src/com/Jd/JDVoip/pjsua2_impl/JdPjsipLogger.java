package com.Jd.JDVoip.pjsua2_impl;

import android.util.Log;
import org.pjsip.pjsua2.LogEntry;
import org.pjsip.pjsua2.LogWriter;

/**
 * Created by huangle on 2015/6/9.
 */
public class JdPjsipLogger extends LogWriter{
    private static final String TAG = "PJSIP";
    @Override
    public void write(LogEntry entry) {
        Log.v(TAG, entry.getMsg());
    }
}
