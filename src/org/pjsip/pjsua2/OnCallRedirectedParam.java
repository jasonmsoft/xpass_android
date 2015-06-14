/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class OnCallRedirectedParam {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected OnCallRedirectedParam(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(OnCallRedirectedParam obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_OnCallRedirectedParam(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTargetUri(String value) {
    pjsua2JNI.OnCallRedirectedParam_targetUri_set(swigCPtr, this, value);
  }

  public String getTargetUri() {
    return pjsua2JNI.OnCallRedirectedParam_targetUri_get(swigCPtr, this);
  }

  public void setE(SipEvent value) {
    pjsua2JNI.OnCallRedirectedParam_e_set(swigCPtr, this, SipEvent.getCPtr(value), value);
  }

  public SipEvent getE() {
    long cPtr = pjsua2JNI.OnCallRedirectedParam_e_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SipEvent(cPtr, false);
  }

  public OnCallRedirectedParam() {
    this(pjsua2JNI.new_OnCallRedirectedParam(), true);
  }

}
