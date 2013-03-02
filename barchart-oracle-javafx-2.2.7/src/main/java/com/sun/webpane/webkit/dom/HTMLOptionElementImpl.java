/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLFormElement;
/*    */ import org.w3c.dom.html.HTMLOptionElement;
/*    */ 
/*    */ public class HTMLOptionElementImpl extends HTMLElementImpl
/*    */   implements HTMLOptionElement
/*    */ {
/*    */   HTMLOptionElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLOptionElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (HTMLOptionElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public HTMLFormElement getForm()
/*    */   {
/* 20 */     return HTMLFormElementImpl.getImpl(getFormImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getFormImpl(long paramLong);
/*    */ 
/*    */   public boolean getDefaultSelected() {
/* 25 */     return getDefaultSelectedImpl(getPeer());
/*    */   }
/*    */   static native boolean getDefaultSelectedImpl(long paramLong);
/*    */ 
/*    */   public void setDefaultSelected(boolean value) {
/* 30 */     setDefaultSelectedImpl(getPeer(), value);
/*    */   }
/*    */   static native void setDefaultSelectedImpl(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   public String getText() {
/* 35 */     return getTextImpl(getPeer());
/*    */   }
/*    */   static native String getTextImpl(long paramLong);
/*    */ 
/*    */   public int getIndex() {
/* 40 */     return getIndexImpl(getPeer());
/*    */   }
/*    */   static native int getIndexImpl(long paramLong);
/*    */ 
/*    */   public boolean getDisabled() {
/* 45 */     return getDisabledImpl(getPeer());
/*    */   }
/*    */   static native boolean getDisabledImpl(long paramLong);
/*    */ 
/*    */   public void setDisabled(boolean value) {
/* 50 */     setDisabledImpl(getPeer(), value);
/*    */   }
/*    */   static native void setDisabledImpl(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   public String getLabel() {
/* 55 */     return getLabelImpl(getPeer());
/*    */   }
/*    */   static native String getLabelImpl(long paramLong);
/*    */ 
/*    */   public void setLabel(String value) {
/* 60 */     setLabelImpl(getPeer(), value);
/*    */   }
/*    */   static native void setLabelImpl(long paramLong, String paramString);
/*    */ 
/*    */   public boolean getSelected() {
/* 65 */     return getSelectedImpl(getPeer());
/*    */   }
/*    */   static native boolean getSelectedImpl(long paramLong);
/*    */ 
/*    */   public void setSelected(boolean value) {
/* 70 */     setSelectedImpl(getPeer(), value);
/*    */   }
/*    */   static native void setSelectedImpl(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   public String getValue() {
/* 75 */     return getValueImpl(getPeer());
/*    */   }
/*    */   static native String getValueImpl(long paramLong);
/*    */ 
/*    */   public void setValue(String value) {
/* 80 */     setValueImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setValueImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLOptionElementImpl
 * JD-Core Version:    0.6.2
 */