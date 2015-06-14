/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class ToneDigitMapVector {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected ToneDigitMapVector(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ToneDigitMapVector obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_ToneDigitMapVector(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public ToneDigitMapVector() {
    this(pjsua2JNI.new_ToneDigitMapVector__SWIG_0(), true);
  }

  public ToneDigitMapVector(long n) {
    this(pjsua2JNI.new_ToneDigitMapVector__SWIG_1(n), true);
  }

  public long size() {
    return pjsua2JNI.ToneDigitMapVector_size(swigCPtr, this);
  }

  public long capacity() {
    return pjsua2JNI.ToneDigitMapVector_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    pjsua2JNI.ToneDigitMapVector_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return pjsua2JNI.ToneDigitMapVector_isEmpty(swigCPtr, this);
  }

  public void clear() {
    pjsua2JNI.ToneDigitMapVector_clear(swigCPtr, this);
  }

  public void add(ToneDigitMapDigit x) {
    pjsua2JNI.ToneDigitMapVector_add(swigCPtr, this, ToneDigitMapDigit.getCPtr(x), x);
  }

  public ToneDigitMapDigit get(int i) {
    return new ToneDigitMapDigit(pjsua2JNI.ToneDigitMapVector_get(swigCPtr, this, i), false);
  }

  public void set(int i, ToneDigitMapDigit val) {
    pjsua2JNI.ToneDigitMapVector_set(swigCPtr, this, i, ToneDigitMapDigit.getCPtr(val), val);
  }

}
