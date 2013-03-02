/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.css.CSSPrimitiveValue;
/*     */ import org.w3c.dom.css.Counter;
/*     */ import org.w3c.dom.css.RGBColor;
/*     */ import org.w3c.dom.css.Rect;
/*     */ 
/*     */ public class CSSPrimitiveValueImpl extends CSSValueImpl
/*     */   implements CSSPrimitiveValue
/*     */ {
/*     */   public static final int CSS_UNKNOWN = 0;
/*     */   public static final int CSS_NUMBER = 1;
/*     */   public static final int CSS_PERCENTAGE = 2;
/*     */   public static final int CSS_EMS = 3;
/*     */   public static final int CSS_EXS = 4;
/*     */   public static final int CSS_PX = 5;
/*     */   public static final int CSS_CM = 6;
/*     */   public static final int CSS_MM = 7;
/*     */   public static final int CSS_IN = 8;
/*     */   public static final int CSS_PT = 9;
/*     */   public static final int CSS_PC = 10;
/*     */   public static final int CSS_DEG = 11;
/*     */   public static final int CSS_RAD = 12;
/*     */   public static final int CSS_GRAD = 13;
/*     */   public static final int CSS_MS = 14;
/*     */   public static final int CSS_S = 15;
/*     */   public static final int CSS_HZ = 16;
/*     */   public static final int CSS_KHZ = 17;
/*     */   public static final int CSS_DIMENSION = 18;
/*     */   public static final int CSS_STRING = 19;
/*     */   public static final int CSS_URI = 20;
/*     */   public static final int CSS_IDENT = 21;
/*     */   public static final int CSS_ATTR = 22;
/*     */   public static final int CSS_COUNTER = 23;
/*     */   public static final int CSS_RECT = 24;
/*     */   public static final int CSS_RGBCOLOR = 25;
/*     */ 
/*     */   CSSPrimitiveValueImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  13 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static CSSPrimitiveValue getImpl(long peer, long contextPeer, long rootPeer) {
/*  17 */     return (CSSPrimitiveValue)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public short getPrimitiveType()
/*     */   {
/*  51 */     return getPrimitiveTypeImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native short getPrimitiveTypeImpl(long paramLong);
/*     */ 
/*     */   public void setFloatValue(short unitType, float floatValue)
/*     */     throws DOMException
/*     */   {
/*  60 */     setFloatValueImpl(getPeer(), unitType, floatValue);
/*     */   }
/*     */ 
/*     */   static native void setFloatValueImpl(long paramLong, short paramShort, float paramFloat);
/*     */ 
/*     */   public float getFloatValue(short unitType)
/*     */     throws DOMException
/*     */   {
/*  71 */     return getFloatValueImpl(getPeer(), unitType);
/*     */   }
/*     */ 
/*     */   static native float getFloatValueImpl(long paramLong, short paramShort);
/*     */ 
/*     */   public void setStringValue(short stringType, String stringValue)
/*     */     throws DOMException
/*     */   {
/*  81 */     setStringValueImpl(getPeer(), stringType, stringValue);
/*     */   }
/*     */ 
/*     */   static native void setStringValueImpl(long paramLong, short paramShort, String paramString);
/*     */ 
/*     */   public String getStringValue()
/*     */     throws DOMException
/*     */   {
/*  92 */     return getStringValueImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native String getStringValueImpl(long paramLong);
/*     */ 
/*     */   public Counter getCounterValue() throws DOMException
/*     */   {
/*  99 */     return CounterImpl.getImpl(getCounterValueImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getCounterValueImpl(long paramLong);
/*     */ 
/*     */   public Rect getRectValue() throws DOMException
/*     */   {
/* 106 */     return RectImpl.getImpl(getRectValueImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getRectValueImpl(long paramLong);
/*     */ 
/*     */   public RGBColor getRGBColorValue() throws DOMException
/*     */   {
/* 113 */     return RGBColorImpl.getImpl(getRGBColorValueImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getRGBColorValueImpl(long paramLong);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSPrimitiveValueImpl
 * JD-Core Version:    0.6.2
 */