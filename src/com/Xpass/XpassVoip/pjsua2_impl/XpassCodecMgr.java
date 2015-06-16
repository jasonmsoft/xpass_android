package com.Xpass.XpassVoip.pjsua2_impl;

import org.pjsip.pjsua2.CodecInfoVector;
import org.pjsip.pjsua2.Endpoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangle on 2015/6/8.
 */
public class XpassCodecMgr {
    public XpassCodecMgr(Endpoint ep){
        this.ep = ep;
    }
    private Endpoint ep = null;
    public List<XpassAudioCodec> getAudioCodec(){
        try {
            if (ep != null) {
                List<XpassAudioCodec> list = new ArrayList<XpassAudioCodec>();
                CodecInfoVector infos = ep.codecEnum();
                for (int i = 0 ; i < infos.size() ; i++ ){
                    XpassAudioCodec codeinfo = new XpassAudioCodec();
                    codeinfo.setCodecId(infos.get(i).getCodecId());
                    codeinfo.setPriority(infos.get(i).getPriority());
                    list.add(codeinfo);
                }
                return list;
            }
        }catch (Exception e){

        }
        return null;
    }
    public boolean setAudioCodec(List<XpassAudioCodec> infos){
        return false;
    }

    public void setAudioCodec(String codecId,short priority){
        try {
            if (ep != null) {
                CodecInfoVector infos = ep.codecEnum();
                for (int i = 0 ; i < infos.size() ; i++ ){
                    if (infos.get(i).getCodecId().equals(codecId)){
                        infos.get(i).setPriority(priority);
                    }
                }
            }
        }catch (Exception e){

        }
    }

    public boolean setOnlyAudioCodec(String codecId){
        try {
            if (ep != null) {
                CodecInfoVector infos = ep.codecEnum();
                for (int i = 0 ; i < infos.size() ; i++ ){
                    if (infos.get(i).getCodecId().equals(codecId)){
                        short priority = 255;
                        infos.get(i).setPriority(priority);
                    }else{
                        short priority = 0;
                        infos.get(i).setPriority(priority);
                    }
                }
            }
        }catch (Exception e){

        }
        return false;
    }

    public boolean hasAudioCodec(String codecId){
        try {
            if (ep != null) {
                CodecInfoVector infos = ep.codecEnum();
                for (int i = 0 ; i < infos.size() ; i++ ){
                    if (infos.get(i).getCodecId().equals(codecId)){
                        return true;
                    }
                }
            }
        }catch (Exception e){

        }
        return false;
    }
}
