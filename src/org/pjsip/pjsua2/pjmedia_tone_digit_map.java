/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class pjmedia_tone_digit_map {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected pjmedia_tone_digit_map(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(pjmedia_tone_digit_map obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_pjmedia_tone_digit_map(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setCount(long value) {
    pjsua2JNI.pjmedia_tone_digit_map_count_set(swigCPtr, this, value);
  }

  public long getCount() {
    return pjsua2JNI.pjmedia_tone_digit_map_count_get(swigCPtr, this);
  }

  public pjmedia_tone_digit_map() {
    this(pjsua2JNI.new_pjmedia_tone_digit_map(), true);
  }

}
