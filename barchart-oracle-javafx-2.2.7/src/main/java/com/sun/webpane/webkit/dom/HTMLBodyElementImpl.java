/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.events.EventListener;
/*     */ import org.w3c.dom.html.HTMLBodyElement;
/*     */ 
/*     */ public class HTMLBodyElementImpl extends HTMLElementImpl
/*     */   implements HTMLBodyElement
/*     */ {
/*     */   HTMLBodyElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  10 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLBodyElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  14 */     return (HTMLBodyElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getALink()
/*     */   {
/*  20 */     return getALinkImpl(getPeer());
/*     */   }
/*     */   static native String getALinkImpl(long paramLong);
/*     */ 
/*     */   public void setALink(String value) {
/*  25 */     setALinkImpl(getPeer(), value);
/*     */   }
/*     */   static native void setALinkImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getBackground() {
/*  30 */     return getBackgroundImpl(getPeer());
/*     */   }
/*     */   static native String getBackgroundImpl(long paramLong);
/*     */ 
/*     */   public void setBackground(String value) {
/*  35 */     setBackgroundImpl(getPeer(), value);
/*     */   }
/*     */   static native void setBackgroundImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getBgColor() {
/*  40 */     return getBgColorImpl(getPeer());
/*     */   }
/*     */   static native String getBgColorImpl(long paramLong);
/*     */ 
/*     */   public void setBgColor(String value) {
/*  45 */     setBgColorImpl(getPeer(), value);
/*     */   }
/*     */   static native void setBgColorImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getLink() {
/*  50 */     return getLinkImpl(getPeer());
/*     */   }
/*     */   static native String getLinkImpl(long paramLong);
/*     */ 
/*     */   public void setLink(String value) {
/*  55 */     setLinkImpl(getPeer(), value);
/*     */   }
/*     */   static native void setLinkImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getText() {
/*  60 */     return getTextImpl(getPeer());
/*     */   }
/*     */   static native String getTextImpl(long paramLong);
/*     */ 
/*     */   public void setText(String value) {
/*  65 */     setTextImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTextImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getVLink() {
/*  70 */     return getVLinkImpl(getPeer());
/*     */   }
/*     */   static native String getVLinkImpl(long paramLong);
/*     */ 
/*     */   public void setVLink(String value) {
/*  75 */     setVLinkImpl(getPeer(), value);
/*     */   }
/*     */   static native void setVLinkImpl(long paramLong, String paramString);
/*     */ 
/*     */   public EventListener getOnbeforeunload() {
/*  80 */     return EventListenerImpl.getImpl(getOnbeforeunloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnbeforeunloadImpl(long paramLong);
/*     */ 
/*     */   public void setOnbeforeunload(EventListener value) {
/*  85 */     setOnbeforeunloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnbeforeunloadImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnhashchange() {
/*  90 */     return EventListenerImpl.getImpl(getOnhashchangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnhashchangeImpl(long paramLong);
/*     */ 
/*     */   public void setOnhashchange(EventListener value) {
/*  95 */     setOnhashchangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnhashchangeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnmessage() {
/* 100 */     return EventListenerImpl.getImpl(getOnmessageImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnmessageImpl(long paramLong);
/*     */ 
/*     */   public void setOnmessage(EventListener value) {
/* 105 */     setOnmessageImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnmessageImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnoffline() {
/* 110 */     return EventListenerImpl.getImpl(getOnofflineImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnofflineImpl(long paramLong);
/*     */ 
/*     */   public void setOnoffline(EventListener value) {
/* 115 */     setOnofflineImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnofflineImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnonline() {
/* 120 */     return EventListenerImpl.getImpl(getOnonlineImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnonlineImpl(long paramLong);
/*     */ 
/*     */   public void setOnonline(EventListener value) {
/* 125 */     setOnonlineImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnonlineImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnpopstate() {
/* 130 */     return EventListenerImpl.getImpl(getOnpopstateImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnpopstateImpl(long paramLong);
/*     */ 
/*     */   public void setOnpopstate(EventListener value) {
/* 135 */     setOnpopstateImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnpopstateImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnresize() {
/* 140 */     return EventListenerImpl.getImpl(getOnresizeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnresizeImpl(long paramLong);
/*     */ 
/*     */   public void setOnresize(EventListener value) {
/* 145 */     setOnresizeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnresizeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnstorage() {
/* 150 */     return EventListenerImpl.getImpl(getOnstorageImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnstorageImpl(long paramLong);
/*     */ 
/*     */   public void setOnstorage(EventListener value) {
/* 155 */     setOnstorageImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnstorageImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnunload() {
/* 160 */     return EventListenerImpl.getImpl(getOnunloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnunloadImpl(long paramLong);
/*     */ 
/*     */   public void setOnunload(EventListener value) {
/* 165 */     setOnunloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnunloadImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnblur() {
/* 170 */     return EventListenerImpl.getImpl(getOnblurImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnblurImpl(long paramLong);
/*     */ 
/*     */   public void setOnblur(EventListener value) {
/* 175 */     setOnblurImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnblurImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnerror() {
/* 180 */     return EventListenerImpl.getImpl(getOnerrorImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnerrorImpl(long paramLong);
/*     */ 
/*     */   public void setOnerror(EventListener value) {
/* 185 */     setOnerrorImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnerrorImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnfocus() {
/* 190 */     return EventListenerImpl.getImpl(getOnfocusImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnfocusImpl(long paramLong);
/*     */ 
/*     */   public void setOnfocus(EventListener value) {
/* 195 */     setOnfocusImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnfocusImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnload() {
/* 200 */     return EventListenerImpl.getImpl(getOnloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnloadImpl(long paramLong);
/*     */ 
/*     */   public void setOnload(EventListener value) {
/* 205 */     setOnloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */ 
/*     */   static native void setOnloadImpl(long paramLong1, long paramLong2);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLBodyElementImpl
 * JD-Core Version:    0.6.2
 */