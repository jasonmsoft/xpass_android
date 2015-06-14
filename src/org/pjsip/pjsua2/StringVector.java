/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class StringVector {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected StringVector(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(StringVector obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_StringVector(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public StringVector() {
    this(pjsua2JNI.new_StringVector__SWIG_0(), true);
  }

  public StringVector(long n) {
    this(pjsua2JNI.new_StringVector__SWIG_1(n), true);
  }

  public long size() {
    return pjsua2JNI.StringVector_size(swigCPtr, this);
  }

  public long capacity() {
    return pjsua2JNI.StringVector_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    pjsua2JNI.StringVector_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return pjsua2JNI.StringVector_isEmpty(swigCPtr, this);
  }

  public void clear() {
    pjsua2JNI.StringVector_clear(swigCPtr, this);
  }

  public void add(String x) {
    pjsua2JNI.StringVector_add(swigCPtr, this, x);
  }

  public String get(int i) {
    return pjsua2JNI.StringVector_get(swigCPtr, this, i);
  }

  public void set(int i, String val) {
    pjsua2JNI.StringVector_set(swigCPtr, this, i, val);
  }

}
