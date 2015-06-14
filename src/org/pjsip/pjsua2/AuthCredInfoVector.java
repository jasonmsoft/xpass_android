/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class AuthCredInfoVector {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected AuthCredInfoVector(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AuthCredInfoVector obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_AuthCredInfoVector(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public AuthCredInfoVector() {
    this(pjsua2JNI.new_AuthCredInfoVector__SWIG_0(), true);
  }

  public AuthCredInfoVector(long n) {
    this(pjsua2JNI.new_AuthCredInfoVector__SWIG_1(n), true);
  }

  public long size() {
    return pjsua2JNI.AuthCredInfoVector_size(swigCPtr, this);
  }

  public long capacity() {
    return pjsua2JNI.AuthCredInfoVector_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    pjsua2JNI.AuthCredInfoVector_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return pjsua2JNI.AuthCredInfoVector_isEmpty(swigCPtr, this);
  }

  public void clear() {
    pjsua2JNI.AuthCredInfoVector_clear(swigCPtr, this);
  }

  public void add(AuthCredInfo x) {
    pjsua2JNI.AuthCredInfoVector_add(swigCPtr, this, AuthCredInfo.getCPtr(x), x);
  }

  public AuthCredInfo get(int i) {
    return new AuthCredInfo(pjsua2JNI.AuthCredInfoVector_get(swigCPtr, this, i), false);
  }

  public void set(int i, AuthCredInfo val) {
    pjsua2JNI.AuthCredInfoVector_set(swigCPtr, this, i, AuthCredInfo.getCPtr(val), val);
  }

}