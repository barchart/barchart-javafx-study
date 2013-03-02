/*      */ package com.sun.webpane.webkit.dom;
/*      */ 
/*      */ import com.sun.webpane.platform.Disposer.WeakDisposerRecord;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.css.CSSStyleDeclaration;
/*      */ import org.w3c.dom.events.Event;
/*      */ import org.w3c.dom.events.EventException;
/*      */ import org.w3c.dom.events.EventListener;
/*      */ import org.w3c.dom.events.EventTarget;
/*      */ import org.w3c.dom.views.AbstractView;
/*      */ import org.w3c.dom.views.DocumentView;
/*      */ 
/*      */ public class DOMWindowImpl extends JSObject
/*      */   implements AbstractView, EventTarget
/*      */ {
/*   24 */   private static SelfDisposer[] hashTable = new SelfDisposer[64];
/*      */   private static int hashCount;
/*      */   protected final long peer;
/*      */ 
/*      */   private static int hashPeer(long peer)
/*      */   {
/*   28 */     return (int)(peer ^ 0xFFFFFFFF ^ peer >> 7) & hashTable.length - 1;
/*      */   }
/*      */ 
/*      */   static AbstractView getImpl(long peer, long contextPeer, long rootPeer, long jsPeer) {
/*   32 */     if (peer == 0L)
/*   33 */       return null;
/*   34 */     int hash = hashPeer(peer);
/*   35 */     SelfDisposer head = hashTable[hash];
/*   36 */     SelfDisposer prev = null;
/*   37 */     for (SelfDisposer disposer = head; disposer != null; ) {
/*   38 */       SelfDisposer next = disposer.next;
/*   39 */       if (disposer.peer == peer) {
/*   40 */         DOMWindowImpl node = (DOMWindowImpl)disposer.get();
/*   41 */         if (node != null)
/*      */         {
/*   43 */           dispose(peer);
/*   44 */           return node;
/*      */         }
/*   46 */         if (prev != null) {
/*   47 */           prev.next = next; break;
/*      */         }
/*   49 */         hashTable[hash] = next;
/*   50 */         break;
/*      */       }
/*   52 */       prev = disposer;
/*   53 */       disposer = next;
/*      */     }
/*   55 */     DOMWindowImpl node = (DOMWindowImpl)createInterface(peer, contextPeer, rootPeer);
/*   56 */     SelfDisposer disposer = new SelfDisposer(node, peer);
/*   57 */     disposer.next = head;
/*   58 */     hashTable[hash] = disposer;
/*   59 */     if (3 * hashCount >= 2 * hashTable.length)
/*   60 */       rehash();
/*   61 */     hashCount += 1;
/*   62 */     node.jsPeer = jsPeer;
/*   63 */     return node;
/*      */   }
/*      */ 
/*      */   private static void rehash() {
/*   67 */     SelfDisposer[] oldTable = hashTable;
/*   68 */     int oldLength = oldTable.length;
/*   69 */     SelfDisposer[] newTable = new SelfDisposer[2 * oldLength];
/*   70 */     hashTable = newTable;
/*   71 */     int i = oldLength;
/*      */     while (true) { i--; if (i < 0) break;
/*   72 */       SelfDisposer disposer = oldTable[i];
/*   73 */       while (disposer != null) {
/*   74 */         SelfDisposer next = disposer.next;
/*   75 */         int hash = hashPeer(disposer.peer);
/*   76 */         disposer.next = newTable[hash];
/*   77 */         newTable[hash] = disposer;
/*   78 */         disposer = next;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected long getJSPeer()
/*      */   {
/*  114 */     if (this.jsPeer == 0L)
/*  115 */       this.jsPeer = getJSPeerImpl(this.peer, this.contextPeer, this.rootPeer);
/*  116 */     return this.jsPeer;
/*      */   }
/*      */   static native long getJSPeerImpl(long paramLong1, long paramLong2, long paramLong3);
/*      */ 
/*      */   DOMWindowImpl(long peer, long contextPeer, long rootPeer) {
/*  121 */     super(0L, contextPeer, rootPeer);
/*  122 */     this.peer = peer;
/*      */   }
/*      */ 
/*      */   static AbstractView createInterface(long peer, long contextPeer, long rootPeer) {
/*  126 */     if (peer == 0L) return null;
/*  127 */     return new DOMWindowImpl(peer, contextPeer, rootPeer);
/*      */   }
/*      */ 
/*      */   static AbstractView create(long peer, long contextPeer, long rootPeer) {
/*  131 */     return getImpl(peer, contextPeer, rootPeer, 0L);
/*      */   }
/*      */ 
/*      */   long getPeer()
/*      */   {
/*  137 */     return this.peer;
/*      */   }
/*      */ 
/*      */   static long getPeer(AbstractView arg) {
/*  141 */     return arg == null ? 0L : ((DOMWindowImpl)arg).getPeer();
/*      */   }
/*      */ 
/*      */   public boolean equals(Object that) {
/*  145 */     return ((that instanceof DOMWindowImpl)) && (this.peer == ((DOMWindowImpl)that).peer);
/*      */   }
/*      */ 
/*      */   public int hashCode() {
/*  149 */     long p = this.peer;
/*  150 */     return (int)(p ^ p >> 17);
/*      */   }
/*      */ 
/*      */   private static native void dispose(long paramLong);
/*      */ 
/*      */   static AbstractView getImpl(long peer, long contextPeer, long rootPeer) {
/*  156 */     return create(peer, contextPeer, rootPeer);
/*      */   }
/*      */ 
/*      */   public Element getFrameElement()
/*      */   {
/*  162 */     return ElementImpl.getImpl(getFrameElementImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getFrameElementImpl(long paramLong);
/*      */ 
/*      */   public boolean getOffscreenBuffering() {
/*  167 */     return getOffscreenBufferingImpl(getPeer());
/*      */   }
/*      */   static native boolean getOffscreenBufferingImpl(long paramLong);
/*      */ 
/*      */   public int getOuterHeight() {
/*  172 */     return getOuterHeightImpl(getPeer());
/*      */   }
/*      */   static native int getOuterHeightImpl(long paramLong);
/*      */ 
/*      */   public int getOuterWidth() {
/*  177 */     return getOuterWidthImpl(getPeer());
/*      */   }
/*      */   static native int getOuterWidthImpl(long paramLong);
/*      */ 
/*      */   public int getInnerHeight() {
/*  182 */     return getInnerHeightImpl(getPeer());
/*      */   }
/*      */   static native int getInnerHeightImpl(long paramLong);
/*      */ 
/*      */   public int getInnerWidth() {
/*  187 */     return getInnerWidthImpl(getPeer());
/*      */   }
/*      */   static native int getInnerWidthImpl(long paramLong);
/*      */ 
/*      */   public int getScreenX() {
/*  192 */     return getScreenXImpl(getPeer());
/*      */   }
/*      */   static native int getScreenXImpl(long paramLong);
/*      */ 
/*      */   public int getScreenY() {
/*  197 */     return getScreenYImpl(getPeer());
/*      */   }
/*      */   static native int getScreenYImpl(long paramLong);
/*      */ 
/*      */   public int getScreenLeft() {
/*  202 */     return getScreenLeftImpl(getPeer());
/*      */   }
/*      */   static native int getScreenLeftImpl(long paramLong);
/*      */ 
/*      */   public int getScreenTop() {
/*  207 */     return getScreenTopImpl(getPeer());
/*      */   }
/*      */   static native int getScreenTopImpl(long paramLong);
/*      */ 
/*      */   public int getScrollX() {
/*  212 */     return getScrollXImpl(getPeer());
/*      */   }
/*      */   static native int getScrollXImpl(long paramLong);
/*      */ 
/*      */   public int getScrollY() {
/*  217 */     return getScrollYImpl(getPeer());
/*      */   }
/*      */   static native int getScrollYImpl(long paramLong);
/*      */ 
/*      */   public int getPageXOffset() {
/*  222 */     return getPageXOffsetImpl(getPeer());
/*      */   }
/*      */   static native int getPageXOffsetImpl(long paramLong);
/*      */ 
/*      */   public int getPageYOffset() {
/*  227 */     return getPageYOffsetImpl(getPeer());
/*      */   }
/*      */   static native int getPageYOffsetImpl(long paramLong);
/*      */ 
/*      */   public boolean getClosed() {
/*  232 */     return getClosedImpl(getPeer());
/*      */   }
/*      */   static native boolean getClosedImpl(long paramLong);
/*      */ 
/*      */   public int getLength() {
/*  237 */     return getLengthImpl(getPeer());
/*      */   }
/*      */   static native int getLengthImpl(long paramLong);
/*      */ 
/*      */   public String getName() {
/*  242 */     return getNameImpl(getPeer());
/*      */   }
/*      */   static native String getNameImpl(long paramLong);
/*      */ 
/*      */   public void setName(String value) {
/*  247 */     setNameImpl(getPeer(), value);
/*      */   }
/*      */   static native void setNameImpl(long paramLong, String paramString);
/*      */ 
/*      */   public String getStatus() {
/*  252 */     return getStatusImpl(getPeer());
/*      */   }
/*      */   static native String getStatusImpl(long paramLong);
/*      */ 
/*      */   public void setStatus(String value) {
/*  257 */     setStatusImpl(getPeer(), value);
/*      */   }
/*      */   static native void setStatusImpl(long paramLong, String paramString);
/*      */ 
/*      */   public String getDefaultStatus() {
/*  262 */     return getDefaultStatusImpl(getPeer());
/*      */   }
/*      */   static native String getDefaultStatusImpl(long paramLong);
/*      */ 
/*      */   public void setDefaultStatus(String value) {
/*  267 */     setDefaultStatusImpl(getPeer(), value);
/*      */   }
/*      */   static native void setDefaultStatusImpl(long paramLong, String paramString);
/*      */ 
/*      */   public AbstractView getSelf() {
/*  272 */     return getImpl(getSelfImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getSelfImpl(long paramLong);
/*      */ 
/*      */   public AbstractView getWindow() {
/*  277 */     return getImpl(getWindowImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getWindowImpl(long paramLong);
/*      */ 
/*      */   public AbstractView getFrames() {
/*  282 */     return getImpl(getFramesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getFramesImpl(long paramLong);
/*      */ 
/*      */   public AbstractView getOpener() {
/*  287 */     return getImpl(getOpenerImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOpenerImpl(long paramLong);
/*      */ 
/*      */   public AbstractView getParent() {
/*  292 */     return getImpl(getParentImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getParentImpl(long paramLong);
/*      */ 
/*      */   public AbstractView getTop() {
/*  297 */     return getImpl(getTopImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getTopImpl(long paramLong);
/*      */ 
/*      */   public Document getDocumentEx() {
/*  302 */     return DocumentImpl.getImpl(getDocumentExImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getDocumentExImpl(long paramLong);
/*      */ 
/*      */   public double getDevicePixelRatio() {
/*  307 */     return getDevicePixelRatioImpl(getPeer());
/*      */   }
/*      */   static native double getDevicePixelRatioImpl(long paramLong);
/*      */ 
/*      */   public EventListener getOnabort() {
/*  312 */     return EventListenerImpl.getImpl(getOnabortImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnabortImpl(long paramLong);
/*      */ 
/*      */   public void setOnabort(EventListener value) {
/*  317 */     setOnabortImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnabortImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnbeforeunload() {
/*  322 */     return EventListenerImpl.getImpl(getOnbeforeunloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnbeforeunloadImpl(long paramLong);
/*      */ 
/*      */   public void setOnbeforeunload(EventListener value) {
/*  327 */     setOnbeforeunloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnbeforeunloadImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnblur() {
/*  332 */     return EventListenerImpl.getImpl(getOnblurImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnblurImpl(long paramLong);
/*      */ 
/*      */   public void setOnblur(EventListener value) {
/*  337 */     setOnblurImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnblurImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOncanplay() {
/*  342 */     return EventListenerImpl.getImpl(getOncanplayImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOncanplayImpl(long paramLong);
/*      */ 
/*      */   public void setOncanplay(EventListener value) {
/*  347 */     setOncanplayImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOncanplayImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOncanplaythrough() {
/*  352 */     return EventListenerImpl.getImpl(getOncanplaythroughImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOncanplaythroughImpl(long paramLong);
/*      */ 
/*      */   public void setOncanplaythrough(EventListener value) {
/*  357 */     setOncanplaythroughImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOncanplaythroughImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnchange() {
/*  362 */     return EventListenerImpl.getImpl(getOnchangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnchangeImpl(long paramLong);
/*      */ 
/*      */   public void setOnchange(EventListener value) {
/*  367 */     setOnchangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnchangeImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnclick() {
/*  372 */     return EventListenerImpl.getImpl(getOnclickImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnclickImpl(long paramLong);
/*      */ 
/*      */   public void setOnclick(EventListener value) {
/*  377 */     setOnclickImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnclickImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOncontextmenu() {
/*  382 */     return EventListenerImpl.getImpl(getOncontextmenuImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOncontextmenuImpl(long paramLong);
/*      */ 
/*      */   public void setOncontextmenu(EventListener value) {
/*  387 */     setOncontextmenuImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOncontextmenuImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndblclick() {
/*  392 */     return EventListenerImpl.getImpl(getOndblclickImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndblclickImpl(long paramLong);
/*      */ 
/*      */   public void setOndblclick(EventListener value) {
/*  397 */     setOndblclickImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndblclickImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndrag() {
/*  402 */     return EventListenerImpl.getImpl(getOndragImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragImpl(long paramLong);
/*      */ 
/*      */   public void setOndrag(EventListener value) {
/*  407 */     setOndragImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndragend() {
/*  412 */     return EventListenerImpl.getImpl(getOndragendImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragendImpl(long paramLong);
/*      */ 
/*      */   public void setOndragend(EventListener value) {
/*  417 */     setOndragendImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragendImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndragenter() {
/*  422 */     return EventListenerImpl.getImpl(getOndragenterImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragenterImpl(long paramLong);
/*      */ 
/*      */   public void setOndragenter(EventListener value) {
/*  427 */     setOndragenterImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragenterImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndragleave() {
/*  432 */     return EventListenerImpl.getImpl(getOndragleaveImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragleaveImpl(long paramLong);
/*      */ 
/*      */   public void setOndragleave(EventListener value) {
/*  437 */     setOndragleaveImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragleaveImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndragover() {
/*  442 */     return EventListenerImpl.getImpl(getOndragoverImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragoverImpl(long paramLong);
/*      */ 
/*      */   public void setOndragover(EventListener value) {
/*  447 */     setOndragoverImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragoverImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndragstart() {
/*  452 */     return EventListenerImpl.getImpl(getOndragstartImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragstartImpl(long paramLong);
/*      */ 
/*      */   public void setOndragstart(EventListener value) {
/*  457 */     setOndragstartImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragstartImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndrop() {
/*  462 */     return EventListenerImpl.getImpl(getOndropImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndropImpl(long paramLong);
/*      */ 
/*      */   public void setOndrop(EventListener value) {
/*  467 */     setOndropImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndropImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndurationchange() {
/*  472 */     return EventListenerImpl.getImpl(getOndurationchangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndurationchangeImpl(long paramLong);
/*      */ 
/*      */   public void setOndurationchange(EventListener value) {
/*  477 */     setOndurationchangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndurationchangeImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnemptied() {
/*  482 */     return EventListenerImpl.getImpl(getOnemptiedImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnemptiedImpl(long paramLong);
/*      */ 
/*      */   public void setOnemptied(EventListener value) {
/*  487 */     setOnemptiedImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnemptiedImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnended() {
/*  492 */     return EventListenerImpl.getImpl(getOnendedImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnendedImpl(long paramLong);
/*      */ 
/*      */   public void setOnended(EventListener value) {
/*  497 */     setOnendedImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnendedImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnerror() {
/*  502 */     return EventListenerImpl.getImpl(getOnerrorImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnerrorImpl(long paramLong);
/*      */ 
/*      */   public void setOnerror(EventListener value) {
/*  507 */     setOnerrorImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnerrorImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnfocus() {
/*  512 */     return EventListenerImpl.getImpl(getOnfocusImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnfocusImpl(long paramLong);
/*      */ 
/*      */   public void setOnfocus(EventListener value) {
/*  517 */     setOnfocusImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnfocusImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnhashchange() {
/*  522 */     return EventListenerImpl.getImpl(getOnhashchangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnhashchangeImpl(long paramLong);
/*      */ 
/*      */   public void setOnhashchange(EventListener value) {
/*  527 */     setOnhashchangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnhashchangeImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOninput() {
/*  532 */     return EventListenerImpl.getImpl(getOninputImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOninputImpl(long paramLong);
/*      */ 
/*      */   public void setOninput(EventListener value) {
/*  537 */     setOninputImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOninputImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOninvalid() {
/*  542 */     return EventListenerImpl.getImpl(getOninvalidImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOninvalidImpl(long paramLong);
/*      */ 
/*      */   public void setOninvalid(EventListener value) {
/*  547 */     setOninvalidImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOninvalidImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnkeydown() {
/*  552 */     return EventListenerImpl.getImpl(getOnkeydownImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnkeydownImpl(long paramLong);
/*      */ 
/*      */   public void setOnkeydown(EventListener value) {
/*  557 */     setOnkeydownImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnkeydownImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnkeypress() {
/*  562 */     return EventListenerImpl.getImpl(getOnkeypressImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnkeypressImpl(long paramLong);
/*      */ 
/*      */   public void setOnkeypress(EventListener value) {
/*  567 */     setOnkeypressImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnkeypressImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnkeyup() {
/*  572 */     return EventListenerImpl.getImpl(getOnkeyupImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnkeyupImpl(long paramLong);
/*      */ 
/*      */   public void setOnkeyup(EventListener value) {
/*  577 */     setOnkeyupImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnkeyupImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnload() {
/*  582 */     return EventListenerImpl.getImpl(getOnloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnloadImpl(long paramLong);
/*      */ 
/*      */   public void setOnload(EventListener value) {
/*  587 */     setOnloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnloadImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnloadeddata() {
/*  592 */     return EventListenerImpl.getImpl(getOnloadeddataImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnloadeddataImpl(long paramLong);
/*      */ 
/*      */   public void setOnloadeddata(EventListener value) {
/*  597 */     setOnloadeddataImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnloadeddataImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnloadedmetadata() {
/*  602 */     return EventListenerImpl.getImpl(getOnloadedmetadataImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnloadedmetadataImpl(long paramLong);
/*      */ 
/*      */   public void setOnloadedmetadata(EventListener value) {
/*  607 */     setOnloadedmetadataImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnloadedmetadataImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnloadstart() {
/*  612 */     return EventListenerImpl.getImpl(getOnloadstartImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnloadstartImpl(long paramLong);
/*      */ 
/*      */   public void setOnloadstart(EventListener value) {
/*  617 */     setOnloadstartImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnloadstartImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmessage() {
/*  622 */     return EventListenerImpl.getImpl(getOnmessageImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmessageImpl(long paramLong);
/*      */ 
/*      */   public void setOnmessage(EventListener value) {
/*  627 */     setOnmessageImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmessageImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmousedown() {
/*  632 */     return EventListenerImpl.getImpl(getOnmousedownImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmousedownImpl(long paramLong);
/*      */ 
/*      */   public void setOnmousedown(EventListener value) {
/*  637 */     setOnmousedownImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmousedownImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmousemove() {
/*  642 */     return EventListenerImpl.getImpl(getOnmousemoveImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmousemoveImpl(long paramLong);
/*      */ 
/*      */   public void setOnmousemove(EventListener value) {
/*  647 */     setOnmousemoveImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmousemoveImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmouseout() {
/*  652 */     return EventListenerImpl.getImpl(getOnmouseoutImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmouseoutImpl(long paramLong);
/*      */ 
/*      */   public void setOnmouseout(EventListener value) {
/*  657 */     setOnmouseoutImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmouseoutImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmouseover() {
/*  662 */     return EventListenerImpl.getImpl(getOnmouseoverImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmouseoverImpl(long paramLong);
/*      */ 
/*      */   public void setOnmouseover(EventListener value) {
/*  667 */     setOnmouseoverImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmouseoverImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmouseup() {
/*  672 */     return EventListenerImpl.getImpl(getOnmouseupImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmouseupImpl(long paramLong);
/*      */ 
/*      */   public void setOnmouseup(EventListener value) {
/*  677 */     setOnmouseupImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmouseupImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmousewheel() {
/*  682 */     return EventListenerImpl.getImpl(getOnmousewheelImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmousewheelImpl(long paramLong);
/*      */ 
/*      */   public void setOnmousewheel(EventListener value) {
/*  687 */     setOnmousewheelImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmousewheelImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnoffline() {
/*  692 */     return EventListenerImpl.getImpl(getOnofflineImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnofflineImpl(long paramLong);
/*      */ 
/*      */   public void setOnoffline(EventListener value) {
/*  697 */     setOnofflineImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnofflineImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnonline() {
/*  702 */     return EventListenerImpl.getImpl(getOnonlineImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnonlineImpl(long paramLong);
/*      */ 
/*      */   public void setOnonline(EventListener value) {
/*  707 */     setOnonlineImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnonlineImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnpagehide() {
/*  712 */     return EventListenerImpl.getImpl(getOnpagehideImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnpagehideImpl(long paramLong);
/*      */ 
/*      */   public void setOnpagehide(EventListener value) {
/*  717 */     setOnpagehideImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnpagehideImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnpageshow() {
/*  722 */     return EventListenerImpl.getImpl(getOnpageshowImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnpageshowImpl(long paramLong);
/*      */ 
/*      */   public void setOnpageshow(EventListener value) {
/*  727 */     setOnpageshowImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnpageshowImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnpause() {
/*  732 */     return EventListenerImpl.getImpl(getOnpauseImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnpauseImpl(long paramLong);
/*      */ 
/*      */   public void setOnpause(EventListener value) {
/*  737 */     setOnpauseImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnpauseImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnplay() {
/*  742 */     return EventListenerImpl.getImpl(getOnplayImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnplayImpl(long paramLong);
/*      */ 
/*      */   public void setOnplay(EventListener value) {
/*  747 */     setOnplayImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnplayImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnplaying() {
/*  752 */     return EventListenerImpl.getImpl(getOnplayingImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnplayingImpl(long paramLong);
/*      */ 
/*      */   public void setOnplaying(EventListener value) {
/*  757 */     setOnplayingImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnplayingImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnpopstate() {
/*  762 */     return EventListenerImpl.getImpl(getOnpopstateImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnpopstateImpl(long paramLong);
/*      */ 
/*      */   public void setOnpopstate(EventListener value) {
/*  767 */     setOnpopstateImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnpopstateImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnprogress() {
/*  772 */     return EventListenerImpl.getImpl(getOnprogressImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnprogressImpl(long paramLong);
/*      */ 
/*      */   public void setOnprogress(EventListener value) {
/*  777 */     setOnprogressImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnprogressImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnratechange() {
/*  782 */     return EventListenerImpl.getImpl(getOnratechangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnratechangeImpl(long paramLong);
/*      */ 
/*      */   public void setOnratechange(EventListener value) {
/*  787 */     setOnratechangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnratechangeImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnresize() {
/*  792 */     return EventListenerImpl.getImpl(getOnresizeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnresizeImpl(long paramLong);
/*      */ 
/*      */   public void setOnresize(EventListener value) {
/*  797 */     setOnresizeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnresizeImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnscroll() {
/*  802 */     return EventListenerImpl.getImpl(getOnscrollImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnscrollImpl(long paramLong);
/*      */ 
/*      */   public void setOnscroll(EventListener value) {
/*  807 */     setOnscrollImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnscrollImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnseeked() {
/*  812 */     return EventListenerImpl.getImpl(getOnseekedImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnseekedImpl(long paramLong);
/*      */ 
/*      */   public void setOnseeked(EventListener value) {
/*  817 */     setOnseekedImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnseekedImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnseeking() {
/*  822 */     return EventListenerImpl.getImpl(getOnseekingImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnseekingImpl(long paramLong);
/*      */ 
/*      */   public void setOnseeking(EventListener value) {
/*  827 */     setOnseekingImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnseekingImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnselect() {
/*  832 */     return EventListenerImpl.getImpl(getOnselectImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnselectImpl(long paramLong);
/*      */ 
/*      */   public void setOnselect(EventListener value) {
/*  837 */     setOnselectImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnselectImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnstalled() {
/*  842 */     return EventListenerImpl.getImpl(getOnstalledImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnstalledImpl(long paramLong);
/*      */ 
/*      */   public void setOnstalled(EventListener value) {
/*  847 */     setOnstalledImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnstalledImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnstorage() {
/*  852 */     return EventListenerImpl.getImpl(getOnstorageImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnstorageImpl(long paramLong);
/*      */ 
/*      */   public void setOnstorage(EventListener value) {
/*  857 */     setOnstorageImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnstorageImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnsubmit() {
/*  862 */     return EventListenerImpl.getImpl(getOnsubmitImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnsubmitImpl(long paramLong);
/*      */ 
/*      */   public void setOnsubmit(EventListener value) {
/*  867 */     setOnsubmitImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnsubmitImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnsuspend() {
/*  872 */     return EventListenerImpl.getImpl(getOnsuspendImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnsuspendImpl(long paramLong);
/*      */ 
/*      */   public void setOnsuspend(EventListener value) {
/*  877 */     setOnsuspendImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnsuspendImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOntimeupdate() {
/*  882 */     return EventListenerImpl.getImpl(getOntimeupdateImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOntimeupdateImpl(long paramLong);
/*      */ 
/*      */   public void setOntimeupdate(EventListener value) {
/*  887 */     setOntimeupdateImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOntimeupdateImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnunload() {
/*  892 */     return EventListenerImpl.getImpl(getOnunloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnunloadImpl(long paramLong);
/*      */ 
/*      */   public void setOnunload(EventListener value) {
/*  897 */     setOnunloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnunloadImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnvolumechange() {
/*  902 */     return EventListenerImpl.getImpl(getOnvolumechangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnvolumechangeImpl(long paramLong);
/*      */ 
/*      */   public void setOnvolumechange(EventListener value) {
/*  907 */     setOnvolumechangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnvolumechangeImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnwaiting() {
/*  912 */     return EventListenerImpl.getImpl(getOnwaitingImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnwaitingImpl(long paramLong);
/*      */ 
/*      */   public void setOnwaiting(EventListener value) {
/*  917 */     setOnwaitingImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnwaitingImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnreset() {
/*  922 */     return EventListenerImpl.getImpl(getOnresetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnresetImpl(long paramLong);
/*      */ 
/*      */   public void setOnreset(EventListener value) {
/*  927 */     setOnresetImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnresetImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnsearch() {
/*  932 */     return EventListenerImpl.getImpl(getOnsearchImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnsearchImpl(long paramLong);
/*      */ 
/*      */   public void setOnsearch(EventListener value) {
/*  937 */     setOnsearchImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnsearchImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnwebkitanimationend() {
/*  942 */     return EventListenerImpl.getImpl(getOnwebkitanimationendImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnwebkitanimationendImpl(long paramLong);
/*      */ 
/*      */   public void setOnwebkitanimationend(EventListener value) {
/*  947 */     setOnwebkitanimationendImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnwebkitanimationendImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnwebkitanimationiteration() {
/*  952 */     return EventListenerImpl.getImpl(getOnwebkitanimationiterationImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnwebkitanimationiterationImpl(long paramLong);
/*      */ 
/*      */   public void setOnwebkitanimationiteration(EventListener value) {
/*  957 */     setOnwebkitanimationiterationImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnwebkitanimationiterationImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnwebkitanimationstart() {
/*  962 */     return EventListenerImpl.getImpl(getOnwebkitanimationstartImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnwebkitanimationstartImpl(long paramLong);
/*      */ 
/*      */   public void setOnwebkitanimationstart(EventListener value) {
/*  967 */     setOnwebkitanimationstartImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnwebkitanimationstartImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnwebkittransitionend() {
/*  972 */     return EventListenerImpl.getImpl(getOnwebkittransitionendImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnwebkittransitionendImpl(long paramLong);
/*      */ 
/*      */   public void setOnwebkittransitionend(EventListener value) {
/*  977 */     setOnwebkittransitionendImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnwebkittransitionendImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOntouchstart() {
/*  982 */     return EventListenerImpl.getImpl(getOntouchstartImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOntouchstartImpl(long paramLong);
/*      */ 
/*      */   public void setOntouchstart(EventListener value) {
/*  987 */     setOntouchstartImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOntouchstartImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOntouchmove() {
/*  992 */     return EventListenerImpl.getImpl(getOntouchmoveImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOntouchmoveImpl(long paramLong);
/*      */ 
/*      */   public void setOntouchmove(EventListener value) {
/*  997 */     setOntouchmoveImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOntouchmoveImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOntouchend() {
/* 1002 */     return EventListenerImpl.getImpl(getOntouchendImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOntouchendImpl(long paramLong);
/*      */ 
/*      */   public void setOntouchend(EventListener value) {
/* 1007 */     setOntouchendImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOntouchendImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOntouchcancel() {
/* 1012 */     return EventListenerImpl.getImpl(getOntouchcancelImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOntouchcancelImpl(long paramLong);
/*      */ 
/*      */   public void setOntouchcancel(EventListener value) {
/* 1017 */     setOntouchcancelImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */ 
/*      */   static native void setOntouchcancelImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public DOMSelectionImpl getSelection()
/*      */   {
/* 1025 */     return DOMSelectionImpl.getImpl(getSelectionImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long getSelectionImpl(long paramLong);
/*      */ 
/*      */   public void focus()
/*      */   {
/* 1032 */     focusImpl(getPeer());
/*      */   }
/*      */ 
/*      */   static native void focusImpl(long paramLong);
/*      */ 
/*      */   public void blur()
/*      */   {
/* 1039 */     blurImpl(getPeer());
/*      */   }
/*      */ 
/*      */   static native void blurImpl(long paramLong);
/*      */ 
/*      */   public void close()
/*      */   {
/* 1046 */     closeImpl(getPeer());
/*      */   }
/*      */ 
/*      */   static native void closeImpl(long paramLong);
/*      */ 
/*      */   public void print()
/*      */   {
/* 1053 */     printImpl(getPeer());
/*      */   }
/*      */ 
/*      */   static native void printImpl(long paramLong);
/*      */ 
/*      */   public void stop()
/*      */   {
/* 1060 */     stopImpl(getPeer());
/*      */   }
/*      */ 
/*      */   static native void stopImpl(long paramLong);
/*      */ 
/*      */   public void alert(String message)
/*      */   {
/* 1067 */     alertImpl(getPeer(), message);
/*      */   }
/*      */ 
/*      */   static native void alertImpl(long paramLong, String paramString);
/*      */ 
/*      */   public boolean confirm(String message)
/*      */   {
/* 1076 */     return confirmImpl(getPeer(), message);
/*      */   }
/*      */ 
/*      */   static native boolean confirmImpl(long paramLong, String paramString);
/*      */ 
/*      */   public String prompt(String message, String defaultValue)
/*      */   {
/* 1086 */     return promptImpl(getPeer(), message, defaultValue);
/*      */   }
/*      */ 
/*      */   static native String promptImpl(long paramLong, String paramString1, String paramString2);
/*      */ 
/*      */   public boolean find(String string, boolean caseSensitive, boolean backwards, boolean wrap, boolean wholeWord, boolean searchInFrames, boolean showDialog)
/*      */   {
/* 1103 */     return findImpl(getPeer(), string, caseSensitive, backwards, wrap, wholeWord, searchInFrames, showDialog);
/*      */   }
/*      */ 
/*      */   static native boolean findImpl(long paramLong, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6);
/*      */ 
/*      */   public void scrollBy(int x, int y)
/*      */   {
/* 1125 */     scrollByImpl(getPeer(), x, y);
/*      */   }
/*      */ 
/*      */   static native void scrollByImpl(long paramLong, int paramInt1, int paramInt2);
/*      */ 
/*      */   public void scrollTo(int x, int y)
/*      */   {
/* 1137 */     scrollToImpl(getPeer(), x, y);
/*      */   }
/*      */ 
/*      */   static native void scrollToImpl(long paramLong, int paramInt1, int paramInt2);
/*      */ 
/*      */   public void scroll(int x, int y)
/*      */   {
/* 1149 */     scrollImpl(getPeer(), x, y);
/*      */   }
/*      */ 
/*      */   static native void scrollImpl(long paramLong, int paramInt1, int paramInt2);
/*      */ 
/*      */   public void moveBy(float x, float y)
/*      */   {
/* 1161 */     moveByImpl(getPeer(), x, y);
/*      */   }
/*      */ 
/*      */   static native void moveByImpl(long paramLong, float paramFloat1, float paramFloat2);
/*      */ 
/*      */   public void moveTo(float x, float y)
/*      */   {
/* 1173 */     moveToImpl(getPeer(), x, y);
/*      */   }
/*      */ 
/*      */   static native void moveToImpl(long paramLong, float paramFloat1, float paramFloat2);
/*      */ 
/*      */   public void resizeBy(float x, float y)
/*      */   {
/* 1185 */     resizeByImpl(getPeer(), x, y);
/*      */   }
/*      */ 
/*      */   static native void resizeByImpl(long paramLong, float paramFloat1, float paramFloat2);
/*      */ 
/*      */   public void resizeTo(float width, float height)
/*      */   {
/* 1197 */     resizeToImpl(getPeer(), width, height);
/*      */   }
/*      */ 
/*      */   static native void resizeToImpl(long paramLong, float paramFloat1, float paramFloat2);
/*      */ 
/*      */   public CSSStyleDeclaration getComputedStyle(Element element, String pseudoElement)
/*      */   {
/* 1209 */     return CSSStyleDeclarationImpl.getImpl(getComputedStyleImpl(getPeer(), ElementImpl.getPeer(element), pseudoElement), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long getComputedStyleImpl(long paramLong1, long paramLong2, String paramString);
/*      */ 
/*      */   public void clearTimeout(int handle)
/*      */   {
/* 1220 */     clearTimeoutImpl(getPeer(), handle);
/*      */   }
/*      */ 
/*      */   static native void clearTimeoutImpl(long paramLong, int paramInt);
/*      */ 
/*      */   public void clearInterval(int handle)
/*      */   {
/* 1229 */     clearIntervalImpl(getPeer(), handle);
/*      */   }
/*      */ 
/*      */   static native void clearIntervalImpl(long paramLong, int paramInt);
/*      */ 
/*      */   public String atob(String string)
/*      */     throws DOMException
/*      */   {
/* 1238 */     return atobImpl(getPeer(), string);
/*      */   }
/*      */ 
/*      */   static native String atobImpl(long paramLong, String paramString);
/*      */ 
/*      */   public String btoa(String string)
/*      */     throws DOMException
/*      */   {
/* 1247 */     return btoaImpl(getPeer(), string);
/*      */   }
/*      */ 
/*      */   static native String btoaImpl(long paramLong, String paramString);
/*      */ 
/*      */   public void addEventListener(String type, EventListener listener, boolean useCapture)
/*      */   {
/* 1258 */     addEventListenerImpl(getPeer(), type, EventListenerImpl.getPeer(listener, this.contextPeer, this.rootPeer), useCapture);
/*      */   }
/*      */ 
/*      */   static native void addEventListenerImpl(long paramLong1, String paramString, long paramLong2, boolean paramBoolean);
/*      */ 
/*      */   public void removeEventListener(String type, EventListener listener, boolean useCapture)
/*      */   {
/* 1273 */     removeEventListenerImpl(getPeer(), type, EventListenerImpl.getPeer(listener, this.contextPeer, this.rootPeer), useCapture);
/*      */   }
/*      */ 
/*      */   static native void removeEventListenerImpl(long paramLong1, String paramString, long paramLong2, boolean paramBoolean);
/*      */ 
/*      */   public boolean dispatchEvent(Event evt)
/*      */     throws EventException
/*      */   {
/* 1286 */     return dispatchEventImpl(getPeer(), EventImpl.getPeer(evt));
/*      */   }
/*      */ 
/*      */   static native boolean dispatchEventImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public void captureEvents()
/*      */   {
/* 1295 */     captureEventsImpl(getPeer());
/*      */   }
/*      */ 
/*      */   static native void captureEventsImpl(long paramLong);
/*      */ 
/*      */   public void releaseEvents()
/*      */   {
/* 1302 */     releaseEventsImpl(getPeer());
/*      */   }
/*      */ 
/*      */   static native void releaseEventsImpl(long paramLong);
/*      */ 
/*      */   public DocumentView getDocument()
/*      */   {
/* 1310 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */   static class SelfDisposer extends Disposer.WeakDisposerRecord
/*      */   {
/*      */     private final long peer;
/*      */     SelfDisposer next;
/*      */ 
/*      */     SelfDisposer(Object referent, long _peer)
/*      */     {
/*   87 */       super();
/*   88 */       this.peer = _peer;
/*      */     }
/*      */ 
/*      */     public void dispose() {
/*   92 */       int hash = DOMWindowImpl.hashPeer(this.peer);
/*   93 */       SelfDisposer head = DOMWindowImpl.hashTable[hash];
/*   94 */       SelfDisposer prev = null;
/*   95 */       for (SelfDisposer disposer = head; disposer != null; ) {
/*   96 */         SelfDisposer next = disposer.next;
/*   97 */         if (disposer.peer == this.peer) {
/*   98 */           disposer.clear();
/*   99 */           if (prev != null)
/*  100 */             prev.next = next;
/*      */           else
/*  102 */             DOMWindowImpl.hashTable[hash] = next;
/*  103 */           DOMWindowImpl.access$310();
/*  104 */           break;
/*      */         }
/*  106 */         prev = disposer;
/*  107 */         disposer = next;
/*      */       }
/*  109 */       DOMWindowImpl.dispose(this.peer);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.DOMWindowImpl
 * JD-Core Version:    0.6.2
 */