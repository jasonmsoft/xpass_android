/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class OnRegStartedParam {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected OnRegStartedParam(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(OnRegStartedParam obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_OnRegStartedParam(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setRenew(boolean value) {
    pjsua2JNI.OnRegStartedParam_renew_set(swigCPtr, this, value);
  }

  public boolean getRenew() {
    return pjsua2JNI.OnRegStartedParam_renew_get(swigCPtr, this);
  }

  public OnRegStartedParam() {
    this(pjsua2JNI.new_OnRegStartedParam(), true);
  }

}
