/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public final class pjsua_vid_req_keyframe_method {
  public final static pjsua_vid_req_keyframe_method PJSUA_VID_REQ_KEYFRAME_SIP_INFO = new pjsua_vid_req_keyframe_method("PJSUA_VID_REQ_KEYFRAME_SIP_INFO", pjsua2JNI.PJSUA_VID_REQ_KEYFRAME_SIP_INFO_get());
  public final static pjsua_vid_req_keyframe_method PJSUA_VID_REQ_KEYFRAME_RTCP_PLI = new pjsua_vid_req_keyframe_method("PJSUA_VID_REQ_KEYFRAME_RTCP_PLI", pjsua2JNI.PJSUA_VID_REQ_KEYFRAME_RTCP_PLI_get());

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static pjsua_vid_req_keyframe_method swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + pjsua_vid_req_keyframe_method.class + " with value " + swigValue);
  }

  private pjsua_vid_req_keyframe_method(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private pjsua_vid_req_keyframe_method(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private pjsua_vid_req_keyframe_method(String swigName, pjsua_vid_req_keyframe_method swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static pjsua_vid_req_keyframe_method[] swigValues = { PJSUA_VID_REQ_KEYFRAME_SIP_INFO, PJSUA_VID_REQ_KEYFRAME_RTCP_PLI };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

