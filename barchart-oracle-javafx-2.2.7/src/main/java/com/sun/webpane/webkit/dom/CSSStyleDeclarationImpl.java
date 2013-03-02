/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.css.CSSRule;
/*     */ import org.w3c.dom.css.CSSStyleDeclaration;
/*     */ import org.w3c.dom.css.CSSValue;
/*     */ 
/*     */ public class CSSStyleDeclarationImpl
/*     */   implements CSSStyleDeclaration
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */ 
/*     */   CSSStyleDeclarationImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  24 */     this.peer = peer;
/*  25 */     this.contextPeer = contextPeer;
/*  26 */     this.rootPeer = rootPeer;
/*  27 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static CSSStyleDeclaration create(long peer, long contextPeer, long rootPeer) {
/*  31 */     if (peer == 0L) return null;
/*  32 */     return new CSSStyleDeclarationImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  40 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(CSSStyleDeclaration arg) {
/*  44 */     return arg == null ? 0L : ((CSSStyleDeclarationImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  48 */     return ((that instanceof CSSStyleDeclarationImpl)) && (this.peer == ((CSSStyleDeclarationImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  52 */     long p = this.peer;
/*  53 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static CSSStyleDeclaration getImpl(long peer, long contextPeer, long rootPeer) {
/*  59 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getCssText()
/*     */   {
/*  65 */     return getCssTextImpl(getPeer());
/*     */   }
/*     */   static native String getCssTextImpl(long paramLong);
/*     */ 
/*     */   public void setCssText(String value) throws DOMException {
/*  70 */     setCssTextImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCssTextImpl(long paramLong, String paramString);
/*     */ 
/*     */   public int getLength() {
/*  75 */     return getLengthImpl(getPeer());
/*     */   }
/*     */   static native int getLengthImpl(long paramLong);
/*     */ 
/*     */   public CSSRule getParentRule() {
/*  80 */     return CSSRuleImpl.getImpl(getParentRuleImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getParentRuleImpl(long paramLong);
/*     */ 
/*     */   public String getPropertyValue(String propertyName)
/*     */   {
/*  88 */     return getPropertyValueImpl(getPeer(), propertyName);
/*     */   }
/*     */ 
/*     */   static native String getPropertyValueImpl(long paramLong, String paramString);
/*     */ 
/*     */   public CSSValue getPropertyCSSValue(String propertyName)
/*     */   {
/*  97 */     return CSSValueImpl.getImpl(getPropertyCSSValueImpl(getPeer(), propertyName), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getPropertyCSSValueImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String removeProperty(String propertyName)
/*     */     throws DOMException
/*     */   {
/* 106 */     return removePropertyImpl(getPeer(), propertyName);
/*     */   }
/*     */ 
/*     */   static native String removePropertyImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getPropertyPriority(String propertyName)
/*     */   {
/* 115 */     return getPropertyPriorityImpl(getPeer(), propertyName);
/*     */   }
/*     */ 
/*     */   static native String getPropertyPriorityImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void setProperty(String propertyName, String value, String priority)
/*     */     throws DOMException
/*     */   {
/* 126 */     setPropertyImpl(getPeer(), propertyName, value, priority);
/*     */   }
/*     */ 
/*     */   static native void setPropertyImpl(long paramLong, String paramString1, String paramString2, String paramString3);
/*     */ 
/*     */   public String item(int index)
/*     */   {
/* 139 */     return itemImpl(getPeer(), index);
/*     */   }
/*     */ 
/*     */   static native String itemImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getPropertyShorthand(String propertyName)
/*     */   {
/* 148 */     return getPropertyShorthandImpl(getPeer(), propertyName);
/*     */   }
/*     */ 
/*     */   static native String getPropertyShorthandImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean isPropertyImplicit(String propertyName)
/*     */   {
/* 157 */     return isPropertyImplicitImpl(getPeer(), propertyName);
/*     */   }
/*     */ 
/*     */   static native boolean isPropertyImplicitImpl(long paramLong, String paramString);
/*     */ 
/*     */   static class SelfDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */ 
/*     */     SelfDisposer(long peer)
/*     */     {
/*  16 */       this.peer = peer;
/*     */     }
/*     */     public void dispose() {
/*  19 */       CSSStyleDeclarationImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSStyleDeclarationImpl
 * JD-Core Version:    0.6.2
 */