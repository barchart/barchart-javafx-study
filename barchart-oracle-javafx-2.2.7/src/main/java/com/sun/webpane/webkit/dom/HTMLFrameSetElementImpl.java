/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.events.EventListener;
/*     */ import org.w3c.dom.html.HTMLFrameSetElement;
/*     */ 
/*     */ public class HTMLFrameSetElementImpl extends HTMLElementImpl
/*     */   implements HTMLFrameSetElement
/*     */ {
/*     */   HTMLFrameSetElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  10 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLFrameSetElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  14 */     return (HTMLFrameSetElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getCols()
/*     */   {
/*  20 */     return getColsImpl(getPeer());
/*     */   }
/*     */   static native String getColsImpl(long paramLong);
/*     */ 
/*     */   public void setCols(String value) {
/*  25 */     setColsImpl(getPeer(), value);
/*     */   }
/*     */   static native void setColsImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getRows() {
/*  30 */     return getRowsImpl(getPeer());
/*     */   }
/*     */   static native String getRowsImpl(long paramLong);
/*     */ 
/*     */   public void setRows(String value) {
/*  35 */     setRowsImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRowsImpl(long paramLong, String paramString);
/*     */ 
/*     */   public EventListener getOnbeforeunload() {
/*  40 */     return EventListenerImpl.getImpl(getOnbeforeunloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnbeforeunloadImpl(long paramLong);
/*     */ 
/*     */   public void setOnbeforeunload(EventListener value) {
/*  45 */     setOnbeforeunloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnbeforeunloadImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnhashchange() {
/*  50 */     return EventListenerImpl.getImpl(getOnhashchangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnhashchangeImpl(long paramLong);
/*     */ 
/*     */   public void setOnhashchange(EventListener value) {
/*  55 */     setOnhashchangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnhashchangeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnmessage() {
/*  60 */     return EventListenerImpl.getImpl(getOnmessageImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnmessageImpl(long paramLong);
/*     */ 
/*     */   public void setOnmessage(EventListener value) {
/*  65 */     setOnmessageImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnmessageImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnoffline() {
/*  70 */     return EventListenerImpl.getImpl(getOnofflineImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnofflineImpl(long paramLong);
/*     */ 
/*     */   public void setOnoffline(EventListener value) {
/*  75 */     setOnofflineImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnofflineImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnonline() {
/*  80 */     return EventListenerImpl.getImpl(getOnonlineImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnonlineImpl(long paramLong);
/*     */ 
/*     */   public void setOnonline(EventListener value) {
/*  85 */     setOnonlineImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnonlineImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnpopstate() {
/*  90 */     return EventListenerImpl.getImpl(getOnpopstateImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnpopstateImpl(long paramLong);
/*     */ 
/*     */   public void setOnpopstate(EventListener value) {
/*  95 */     setOnpopstateImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnpopstateImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnresize() {
/* 100 */     return EventListenerImpl.getImpl(getOnresizeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnresizeImpl(long paramLong);
/*     */ 
/*     */   public void setOnresize(EventListener value) {
/* 105 */     setOnresizeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnresizeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnstorage() {
/* 110 */     return EventListenerImpl.getImpl(getOnstorageImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnstorageImpl(long paramLong);
/*     */ 
/*     */   public void setOnstorage(EventListener value) {
/* 115 */     setOnstorageImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnstorageImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnunload() {
/* 120 */     return EventListenerImpl.getImpl(getOnunloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnunloadImpl(long paramLong);
/*     */ 
/*     */   public void setOnunload(EventListener value) {
/* 125 */     setOnunloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnunloadImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnblur() {
/* 130 */     return EventListenerImpl.getImpl(getOnblurImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnblurImpl(long paramLong);
/*     */ 
/*     */   public void setOnblur(EventListener value) {
/* 135 */     setOnblurImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnblurImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnerror() {
/* 140 */     return EventListenerImpl.getImpl(getOnerrorImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnerrorImpl(long paramLong);
/*     */ 
/*     */   public void setOnerror(EventListener value) {
/* 145 */     setOnerrorImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnerrorImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnfocus() {
/* 150 */     return EventListenerImpl.getImpl(getOnfocusImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnfocusImpl(long paramLong);
/*     */ 
/*     */   public void setOnfocus(EventListener value) {
/* 155 */     setOnfocusImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnfocusImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnload() {
/* 160 */     return EventListenerImpl.getImpl(getOnloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnloadImpl(long paramLong);
/*     */ 
/*     */   public void setOnload(EventListener value) {
/* 165 */     setOnloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */ 
/*     */   static native void setOnloadImpl(long paramLong1, long paramLong2);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLFrameSetElementImpl
 * JD-Core Version:    0.6.2
 */