/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class ToneDigitVector {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected ToneDigitVector(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ToneDigitVector obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_ToneDigitVector(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public ToneDigitVector() {
    this(pjsua2JNI.new_ToneDigitVector__SWIG_0(), true);
  }

  public ToneDigitVector(long n) {
    this(pjsua2JNI.new_ToneDigitVector__SWIG_1(n), true);
  }

  public long size() {
    return pjsua2JNI.ToneDigitVector_size(swigCPtr, this);
  }

  public long capacity() {
    return pjsua2JNI.ToneDigitVector_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    pjsua2JNI.ToneDigitVector_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return pjsua2JNI.ToneDigitVector_isEmpty(swigCPtr, this);
  }

  public void clear() {
    pjsua2JNI.ToneDigitVector_clear(swigCPtr, this);
  }

  public void add(ToneDigit x) {
    pjsua2JNI.ToneDigitVector_add(swigCPtr, this, ToneDigit.getCPtr(x), x);
  }

  public ToneDigit get(int i) {
    return new ToneDigit(pjsua2JNI.ToneDigitVector_get(swigCPtr, this, i), false);
  }

  public void set(int i, ToneDigit val) {
    pjsua2JNI.ToneDigitVector_set(swigCPtr, this, i, ToneDigit.getCPtr(val), val);
  }

}
