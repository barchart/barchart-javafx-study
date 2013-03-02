/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.TypeInfo;
/*     */ import org.w3c.dom.css.CSSStyleDeclaration;
/*     */ import org.w3c.dom.events.EventListener;
/*     */ 
/*     */ public class ElementImpl extends NodeImpl
/*     */   implements Element
/*     */ {
/*     */   ElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  15 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static Element getImpl(long peer, long contextPeer, long rootPeer) {
/*  19 */     return (Element)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static native boolean isHTMLElementImpl(long paramLong);
/*     */ 
/*     */   public String getTagName()
/*     */   {
/*  27 */     return getTagNameImpl(getPeer());
/*     */   }
/*     */   static native String getTagNameImpl(long paramLong);
/*     */ 
/*     */   public CSSStyleDeclaration getStyle() {
/*  32 */     return CSSStyleDeclarationImpl.getImpl(getStyleImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getStyleImpl(long paramLong);
/*     */ 
/*     */   public int getOffsetLeft() {
/*  37 */     return getOffsetLeftImpl(getPeer());
/*     */   }
/*     */   static native int getOffsetLeftImpl(long paramLong);
/*     */ 
/*     */   public int getOffsetTop() {
/*  42 */     return getOffsetTopImpl(getPeer());
/*     */   }
/*     */   static native int getOffsetTopImpl(long paramLong);
/*     */ 
/*     */   public int getOffsetWidth() {
/*  47 */     return getOffsetWidthImpl(getPeer());
/*     */   }
/*     */   static native int getOffsetWidthImpl(long paramLong);
/*     */ 
/*     */   public int getOffsetHeight() {
/*  52 */     return getOffsetHeightImpl(getPeer());
/*     */   }
/*     */   static native int getOffsetHeightImpl(long paramLong);
/*     */ 
/*     */   public Element getOffsetParent() {
/*  57 */     return getImpl(getOffsetParentImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOffsetParentImpl(long paramLong);
/*     */ 
/*     */   public int getClientLeft() {
/*  62 */     return getClientLeftImpl(getPeer());
/*     */   }
/*     */   static native int getClientLeftImpl(long paramLong);
/*     */ 
/*     */   public int getClientTop() {
/*  67 */     return getClientTopImpl(getPeer());
/*     */   }
/*     */   static native int getClientTopImpl(long paramLong);
/*     */ 
/*     */   public int getClientWidth() {
/*  72 */     return getClientWidthImpl(getPeer());
/*     */   }
/*     */   static native int getClientWidthImpl(long paramLong);
/*     */ 
/*     */   public int getClientHeight() {
/*  77 */     return getClientHeightImpl(getPeer());
/*     */   }
/*     */   static native int getClientHeightImpl(long paramLong);
/*     */ 
/*     */   public int getScrollLeft() {
/*  82 */     return getScrollLeftImpl(getPeer());
/*     */   }
/*     */   static native int getScrollLeftImpl(long paramLong);
/*     */ 
/*     */   public void setScrollLeft(int value) {
/*  87 */     setScrollLeftImpl(getPeer(), value);
/*     */   }
/*     */   static native void setScrollLeftImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public int getScrollTop() {
/*  92 */     return getScrollTopImpl(getPeer());
/*     */   }
/*     */   static native int getScrollTopImpl(long paramLong);
/*     */ 
/*     */   public void setScrollTop(int value) {
/*  97 */     setScrollTopImpl(getPeer(), value);
/*     */   }
/*     */   static native void setScrollTopImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public int getScrollWidth() {
/* 102 */     return getScrollWidthImpl(getPeer());
/*     */   }
/*     */   static native int getScrollWidthImpl(long paramLong);
/*     */ 
/*     */   public int getScrollHeight() {
/* 107 */     return getScrollHeightImpl(getPeer());
/*     */   }
/*     */   static native int getScrollHeightImpl(long paramLong);
/*     */ 
/*     */   public Element getFirstElementChild() {
/* 112 */     return getImpl(getFirstElementChildImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getFirstElementChildImpl(long paramLong);
/*     */ 
/*     */   public Element getLastElementChild() {
/* 117 */     return getImpl(getLastElementChildImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getLastElementChildImpl(long paramLong);
/*     */ 
/*     */   public Element getPreviousElementSibling() {
/* 122 */     return getImpl(getPreviousElementSiblingImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getPreviousElementSiblingImpl(long paramLong);
/*     */ 
/*     */   public Element getNextElementSibling() {
/* 127 */     return getImpl(getNextElementSiblingImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getNextElementSiblingImpl(long paramLong);
/*     */ 
/*     */   public int getChildElementCount() {
/* 132 */     return getChildElementCountImpl(getPeer());
/*     */   }
/*     */   static native int getChildElementCountImpl(long paramLong);
/*     */ 
/*     */   public EventListener getOnabort() {
/* 137 */     return EventListenerImpl.getImpl(getOnabortImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnabortImpl(long paramLong);
/*     */ 
/*     */   public void setOnabort(EventListener value) {
/* 142 */     setOnabortImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnabortImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnblur() {
/* 147 */     return EventListenerImpl.getImpl(getOnblurImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnblurImpl(long paramLong);
/*     */ 
/*     */   public void setOnblur(EventListener value) {
/* 152 */     setOnblurImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnblurImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnchange() {
/* 157 */     return EventListenerImpl.getImpl(getOnchangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnchangeImpl(long paramLong);
/*     */ 
/*     */   public void setOnchange(EventListener value) {
/* 162 */     setOnchangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnchangeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnclick() {
/* 167 */     return EventListenerImpl.getImpl(getOnclickImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnclickImpl(long paramLong);
/*     */ 
/*     */   public void setOnclick(EventListener value) {
/* 172 */     setOnclickImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnclickImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOncontextmenu() {
/* 177 */     return EventListenerImpl.getImpl(getOncontextmenuImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOncontextmenuImpl(long paramLong);
/*     */ 
/*     */   public void setOncontextmenu(EventListener value) {
/* 182 */     setOncontextmenuImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOncontextmenuImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOndblclick() {
/* 187 */     return EventListenerImpl.getImpl(getOndblclickImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOndblclickImpl(long paramLong);
/*     */ 
/*     */   public void setOndblclick(EventListener value) {
/* 192 */     setOndblclickImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOndblclickImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOndrag() {
/* 197 */     return EventListenerImpl.getImpl(getOndragImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOndragImpl(long paramLong);
/*     */ 
/*     */   public void setOndrag(EventListener value) {
/* 202 */     setOndragImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOndragImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOndragend() {
/* 207 */     return EventListenerImpl.getImpl(getOndragendImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOndragendImpl(long paramLong);
/*     */ 
/*     */   public void setOndragend(EventListener value) {
/* 212 */     setOndragendImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOndragendImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOndragenter() {
/* 217 */     return EventListenerImpl.getImpl(getOndragenterImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOndragenterImpl(long paramLong);
/*     */ 
/*     */   public void setOndragenter(EventListener value) {
/* 222 */     setOndragenterImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOndragenterImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOndragleave() {
/* 227 */     return EventListenerImpl.getImpl(getOndragleaveImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOndragleaveImpl(long paramLong);
/*     */ 
/*     */   public void setOndragleave(EventListener value) {
/* 232 */     setOndragleaveImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOndragleaveImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOndragover() {
/* 237 */     return EventListenerImpl.getImpl(getOndragoverImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOndragoverImpl(long paramLong);
/*     */ 
/*     */   public void setOndragover(EventListener value) {
/* 242 */     setOndragoverImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOndragoverImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOndragstart() {
/* 247 */     return EventListenerImpl.getImpl(getOndragstartImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOndragstartImpl(long paramLong);
/*     */ 
/*     */   public void setOndragstart(EventListener value) {
/* 252 */     setOndragstartImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOndragstartImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOndrop() {
/* 257 */     return EventListenerImpl.getImpl(getOndropImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOndropImpl(long paramLong);
/*     */ 
/*     */   public void setOndrop(EventListener value) {
/* 262 */     setOndropImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOndropImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnerror() {
/* 267 */     return EventListenerImpl.getImpl(getOnerrorImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnerrorImpl(long paramLong);
/*     */ 
/*     */   public void setOnerror(EventListener value) {
/* 272 */     setOnerrorImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnerrorImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnfocus() {
/* 277 */     return EventListenerImpl.getImpl(getOnfocusImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnfocusImpl(long paramLong);
/*     */ 
/*     */   public void setOnfocus(EventListener value) {
/* 282 */     setOnfocusImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnfocusImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOninput() {
/* 287 */     return EventListenerImpl.getImpl(getOninputImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOninputImpl(long paramLong);
/*     */ 
/*     */   public void setOninput(EventListener value) {
/* 292 */     setOninputImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOninputImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOninvalid() {
/* 297 */     return EventListenerImpl.getImpl(getOninvalidImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOninvalidImpl(long paramLong);
/*     */ 
/*     */   public void setOninvalid(EventListener value) {
/* 302 */     setOninvalidImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOninvalidImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnkeydown() {
/* 307 */     return EventListenerImpl.getImpl(getOnkeydownImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnkeydownImpl(long paramLong);
/*     */ 
/*     */   public void setOnkeydown(EventListener value) {
/* 312 */     setOnkeydownImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnkeydownImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnkeypress() {
/* 317 */     return EventListenerImpl.getImpl(getOnkeypressImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnkeypressImpl(long paramLong);
/*     */ 
/*     */   public void setOnkeypress(EventListener value) {
/* 322 */     setOnkeypressImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnkeypressImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnkeyup() {
/* 327 */     return EventListenerImpl.getImpl(getOnkeyupImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnkeyupImpl(long paramLong);
/*     */ 
/*     */   public void setOnkeyup(EventListener value) {
/* 332 */     setOnkeyupImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnkeyupImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnload() {
/* 337 */     return EventListenerImpl.getImpl(getOnloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnloadImpl(long paramLong);
/*     */ 
/*     */   public void setOnload(EventListener value) {
/* 342 */     setOnloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnloadImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnmousedown() {
/* 347 */     return EventListenerImpl.getImpl(getOnmousedownImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnmousedownImpl(long paramLong);
/*     */ 
/*     */   public void setOnmousedown(EventListener value) {
/* 352 */     setOnmousedownImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnmousedownImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnmousemove() {
/* 357 */     return EventListenerImpl.getImpl(getOnmousemoveImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnmousemoveImpl(long paramLong);
/*     */ 
/*     */   public void setOnmousemove(EventListener value) {
/* 362 */     setOnmousemoveImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnmousemoveImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnmouseout() {
/* 367 */     return EventListenerImpl.getImpl(getOnmouseoutImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnmouseoutImpl(long paramLong);
/*     */ 
/*     */   public void setOnmouseout(EventListener value) {
/* 372 */     setOnmouseoutImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnmouseoutImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnmouseover() {
/* 377 */     return EventListenerImpl.getImpl(getOnmouseoverImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnmouseoverImpl(long paramLong);
/*     */ 
/*     */   public void setOnmouseover(EventListener value) {
/* 382 */     setOnmouseoverImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnmouseoverImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnmouseup() {
/* 387 */     return EventListenerImpl.getImpl(getOnmouseupImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnmouseupImpl(long paramLong);
/*     */ 
/*     */   public void setOnmouseup(EventListener value) {
/* 392 */     setOnmouseupImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnmouseupImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnmousewheel() {
/* 397 */     return EventListenerImpl.getImpl(getOnmousewheelImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnmousewheelImpl(long paramLong);
/*     */ 
/*     */   public void setOnmousewheel(EventListener value) {
/* 402 */     setOnmousewheelImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnmousewheelImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnscroll() {
/* 407 */     return EventListenerImpl.getImpl(getOnscrollImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnscrollImpl(long paramLong);
/*     */ 
/*     */   public void setOnscroll(EventListener value) {
/* 412 */     setOnscrollImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnscrollImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnselect() {
/* 417 */     return EventListenerImpl.getImpl(getOnselectImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnselectImpl(long paramLong);
/*     */ 
/*     */   public void setOnselect(EventListener value) {
/* 422 */     setOnselectImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnselectImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnsubmit() {
/* 427 */     return EventListenerImpl.getImpl(getOnsubmitImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnsubmitImpl(long paramLong);
/*     */ 
/*     */   public void setOnsubmit(EventListener value) {
/* 432 */     setOnsubmitImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnsubmitImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnbeforecut() {
/* 437 */     return EventListenerImpl.getImpl(getOnbeforecutImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnbeforecutImpl(long paramLong);
/*     */ 
/*     */   public void setOnbeforecut(EventListener value) {
/* 442 */     setOnbeforecutImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnbeforecutImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOncut() {
/* 447 */     return EventListenerImpl.getImpl(getOncutImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOncutImpl(long paramLong);
/*     */ 
/*     */   public void setOncut(EventListener value) {
/* 452 */     setOncutImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOncutImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnbeforecopy() {
/* 457 */     return EventListenerImpl.getImpl(getOnbeforecopyImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnbeforecopyImpl(long paramLong);
/*     */ 
/*     */   public void setOnbeforecopy(EventListener value) {
/* 462 */     setOnbeforecopyImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnbeforecopyImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOncopy() {
/* 467 */     return EventListenerImpl.getImpl(getOncopyImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOncopyImpl(long paramLong);
/*     */ 
/*     */   public void setOncopy(EventListener value) {
/* 472 */     setOncopyImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOncopyImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnbeforepaste() {
/* 477 */     return EventListenerImpl.getImpl(getOnbeforepasteImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnbeforepasteImpl(long paramLong);
/*     */ 
/*     */   public void setOnbeforepaste(EventListener value) {
/* 482 */     setOnbeforepasteImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnbeforepasteImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnpaste() {
/* 487 */     return EventListenerImpl.getImpl(getOnpasteImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnpasteImpl(long paramLong);
/*     */ 
/*     */   public void setOnpaste(EventListener value) {
/* 492 */     setOnpasteImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnpasteImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnreset() {
/* 497 */     return EventListenerImpl.getImpl(getOnresetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnresetImpl(long paramLong);
/*     */ 
/*     */   public void setOnreset(EventListener value) {
/* 502 */     setOnresetImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnresetImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnsearch() {
/* 507 */     return EventListenerImpl.getImpl(getOnsearchImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnsearchImpl(long paramLong);
/*     */ 
/*     */   public void setOnsearch(EventListener value) {
/* 512 */     setOnsearchImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnsearchImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOnselectstart() {
/* 517 */     return EventListenerImpl.getImpl(getOnselectstartImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOnselectstartImpl(long paramLong);
/*     */ 
/*     */   public void setOnselectstart(EventListener value) {
/* 522 */     setOnselectstartImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOnselectstartImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOntouchstart() {
/* 527 */     return EventListenerImpl.getImpl(getOntouchstartImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOntouchstartImpl(long paramLong);
/*     */ 
/*     */   public void setOntouchstart(EventListener value) {
/* 532 */     setOntouchstartImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOntouchstartImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOntouchmove() {
/* 537 */     return EventListenerImpl.getImpl(getOntouchmoveImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOntouchmoveImpl(long paramLong);
/*     */ 
/*     */   public void setOntouchmove(EventListener value) {
/* 542 */     setOntouchmoveImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOntouchmoveImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOntouchend() {
/* 547 */     return EventListenerImpl.getImpl(getOntouchendImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOntouchendImpl(long paramLong);
/*     */ 
/*     */   public void setOntouchend(EventListener value) {
/* 552 */     setOntouchendImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */   static native void setOntouchendImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public EventListener getOntouchcancel() {
/* 557 */     return EventListenerImpl.getImpl(getOntouchcancelImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOntouchcancelImpl(long paramLong);
/*     */ 
/*     */   public void setOntouchcancel(EventListener value) {
/* 562 */     setOntouchcancelImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*     */   }
/*     */ 
/*     */   static native void setOntouchcancelImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public String getAttribute(String name)
/*     */   {
/* 570 */     return getAttributeImpl(getPeer(), name);
/*     */   }
/*     */ 
/*     */   static native String getAttributeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void setAttribute(String name, String value)
/*     */     throws DOMException
/*     */   {
/* 580 */     setAttributeImpl(getPeer(), name, value);
/*     */   }
/*     */ 
/*     */   static native void setAttributeImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public void removeAttribute(String name)
/*     */   {
/* 591 */     removeAttributeImpl(getPeer(), name);
/*     */   }
/*     */ 
/*     */   static native void removeAttributeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public Attr getAttributeNode(String name)
/*     */   {
/* 600 */     return AttrImpl.getImpl(getAttributeNodeImpl(getPeer(), name), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getAttributeNodeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public Attr setAttributeNode(Attr newAttr)
/*     */     throws DOMException
/*     */   {
/* 609 */     return AttrImpl.getImpl(setAttributeNodeImpl(getPeer(), AttrImpl.getPeer(newAttr)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long setAttributeNodeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public Attr removeAttributeNode(Attr oldAttr)
/*     */     throws DOMException
/*     */   {
/* 618 */     return AttrImpl.getImpl(removeAttributeNodeImpl(getPeer(), AttrImpl.getPeer(oldAttr)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long removeAttributeNodeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public NodeList getElementsByTagName(String name)
/*     */   {
/* 627 */     return NodeListImpl.getImpl(getElementsByTagNameImpl(getPeer(), name), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getElementsByTagNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAttributeNS(String namespaceURI, String localName)
/*     */   {
/* 637 */     return getAttributeNSImpl(getPeer(), namespaceURI, localName);
/*     */   }
/*     */ 
/*     */   static native String getAttributeNSImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public void setAttributeNS(String namespaceURI, String qualifiedName, String value)
/*     */     throws DOMException
/*     */   {
/* 650 */     setAttributeNSImpl(getPeer(), namespaceURI, qualifiedName, value);
/*     */   }
/*     */ 
/*     */   static native void setAttributeNSImpl(long paramLong, String paramString1, String paramString2, String paramString3);
/*     */ 
/*     */   public void removeAttributeNS(String namespaceURI, String localName)
/*     */   {
/* 664 */     removeAttributeNSImpl(getPeer(), namespaceURI, localName);
/*     */   }
/*     */ 
/*     */   static native void removeAttributeNSImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
/*     */   {
/* 676 */     return NodeListImpl.getImpl(getElementsByTagNameNSImpl(getPeer(), namespaceURI, localName), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getElementsByTagNameNSImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public Attr getAttributeNodeNS(String namespaceURI, String localName)
/*     */   {
/* 688 */     return AttrImpl.getImpl(getAttributeNodeNSImpl(getPeer(), namespaceURI, localName), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getAttributeNodeNSImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public Attr setAttributeNodeNS(Attr newAttr)
/*     */     throws DOMException
/*     */   {
/* 699 */     return AttrImpl.getImpl(setAttributeNodeNSImpl(getPeer(), AttrImpl.getPeer(newAttr)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long setAttributeNodeNSImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public boolean hasAttribute(String name)
/*     */   {
/* 708 */     return hasAttributeImpl(getPeer(), name);
/*     */   }
/*     */ 
/*     */   static native boolean hasAttributeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean hasAttributeNS(String namespaceURI, String localName)
/*     */   {
/* 718 */     return hasAttributeNSImpl(getPeer(), namespaceURI, localName);
/*     */   }
/*     */ 
/*     */   static native boolean hasAttributeNSImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public void focus()
/*     */   {
/* 729 */     focusImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void focusImpl(long paramLong);
/*     */ 
/*     */   public void blur()
/*     */   {
/* 736 */     blurImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void blurImpl(long paramLong);
/*     */ 
/*     */   public void scrollIntoView(boolean alignWithTop)
/*     */   {
/* 743 */     scrollIntoViewImpl(getPeer(), alignWithTop);
/*     */   }
/*     */ 
/*     */   static native void scrollIntoViewImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public void scrollIntoViewIfNeeded(boolean centerIfNeeded)
/*     */   {
/* 752 */     scrollIntoViewIfNeededImpl(getPeer(), centerIfNeeded);
/*     */   }
/*     */ 
/*     */   static native void scrollIntoViewIfNeededImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public void scrollByLines(int lines)
/*     */   {
/* 761 */     scrollByLinesImpl(getPeer(), lines);
/*     */   }
/*     */ 
/*     */   static native void scrollByLinesImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public void scrollByPages(int pages)
/*     */   {
/* 770 */     scrollByPagesImpl(getPeer(), pages);
/*     */   }
/*     */ 
/*     */   static native void scrollByPagesImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public NodeList getElementsByClassName(String name)
/*     */   {
/* 779 */     return NodeListImpl.getImpl(getElementsByClassNameImpl(getPeer(), name), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getElementsByClassNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public Element querySelector(String selectors)
/*     */     throws DOMException
/*     */   {
/* 788 */     return getImpl(querySelectorImpl(getPeer(), selectors), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long querySelectorImpl(long paramLong, String paramString);
/*     */ 
/*     */   public NodeList querySelectorAll(String selectors)
/*     */     throws DOMException
/*     */   {
/* 797 */     return NodeListImpl.getImpl(querySelectorAllImpl(getPeer(), selectors), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long querySelectorAllImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean webkitMatchesSelector(String selectors)
/*     */     throws DOMException
/*     */   {
/* 806 */     return webkitMatchesSelectorImpl(getPeer(), selectors);
/*     */   }
/*     */ 
/*     */   static native boolean webkitMatchesSelectorImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void setIdAttribute(String name, boolean isId)
/*     */     throws DOMException
/*     */   {
/* 816 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
/* 819 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   public TypeInfo getSchemaTypeInfo() {
/* 822 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
/* 825 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.ElementImpl
 * JD-Core Version:    0.6.2
 */