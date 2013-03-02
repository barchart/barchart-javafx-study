/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.DocumentFragment;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ranges.Range;
/*     */ import org.w3c.dom.ranges.RangeException;
/*     */ 
/*     */ public class RangeImpl
/*     */   implements Range
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */   public static final int START_TO_START = 0;
/*     */   public static final int START_TO_END = 1;
/*     */   public static final int END_TO_END = 2;
/*     */   public static final int END_TO_START = 3;
/*     */   public static final int NODE_BEFORE = 0;
/*     */   public static final int NODE_AFTER = 1;
/*     */   public static final int NODE_BEFORE_AND_AFTER = 2;
/*     */   public static final int NODE_INSIDE = 3;
/*     */ 
/*     */   RangeImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  25 */     this.peer = peer;
/*  26 */     this.contextPeer = contextPeer;
/*  27 */     this.rootPeer = rootPeer;
/*  28 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static Range create(long peer, long contextPeer, long rootPeer) {
/*  32 */     if (peer == 0L) return null;
/*  33 */     return new RangeImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  41 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(Range arg) {
/*  45 */     return arg == null ? 0L : ((RangeImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  49 */     return ((that instanceof RangeImpl)) && (this.peer == ((RangeImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  53 */     long p = this.peer;
/*  54 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static Range getImpl(long peer, long contextPeer, long rootPeer) {
/*  60 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public Node getStartContainer()
/*     */     throws DOMException
/*     */   {
/*  76 */     return NodeImpl.getImpl(getStartContainerImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getStartContainerImpl(long paramLong);
/*     */ 
/*     */   public int getStartOffset() throws DOMException {
/*  81 */     return getStartOffsetImpl(getPeer());
/*     */   }
/*     */   static native int getStartOffsetImpl(long paramLong);
/*     */ 
/*     */   public Node getEndContainer() throws DOMException {
/*  86 */     return NodeImpl.getImpl(getEndContainerImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getEndContainerImpl(long paramLong);
/*     */ 
/*     */   public int getEndOffset() throws DOMException {
/*  91 */     return getEndOffsetImpl(getPeer());
/*     */   }
/*     */   static native int getEndOffsetImpl(long paramLong);
/*     */ 
/*     */   public boolean getCollapsed() throws DOMException {
/*  96 */     return getCollapsedImpl(getPeer());
/*     */   }
/*     */   static native boolean getCollapsedImpl(long paramLong);
/*     */ 
/*     */   public Node getCommonAncestorContainer() throws DOMException {
/* 101 */     return NodeImpl.getImpl(getCommonAncestorContainerImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getCommonAncestorContainerImpl(long paramLong);
/*     */ 
/*     */   public String getText() {
/* 106 */     return getTextImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native String getTextImpl(long paramLong);
/*     */ 
/*     */   public void setStart(Node refNode, int offset)
/*     */     throws RangeException, DOMException
/*     */   {
/* 115 */     setStartImpl(getPeer(), NodeImpl.getPeer(refNode), offset);
/*     */   }
/*     */ 
/*     */   static native void setStartImpl(long paramLong1, long paramLong2, int paramInt);
/*     */ 
/*     */   public void setEnd(Node refNode, int offset)
/*     */     throws RangeException, DOMException
/*     */   {
/* 127 */     setEndImpl(getPeer(), NodeImpl.getPeer(refNode), offset);
/*     */   }
/*     */ 
/*     */   static native void setEndImpl(long paramLong1, long paramLong2, int paramInt);
/*     */ 
/*     */   public void setStartBefore(Node refNode)
/*     */     throws RangeException, DOMException
/*     */   {
/* 138 */     setStartBeforeImpl(getPeer(), NodeImpl.getPeer(refNode));
/*     */   }
/*     */ 
/*     */   static native void setStartBeforeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public void setStartAfter(Node refNode)
/*     */     throws RangeException, DOMException
/*     */   {
/* 147 */     setStartAfterImpl(getPeer(), NodeImpl.getPeer(refNode));
/*     */   }
/*     */ 
/*     */   static native void setStartAfterImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public void setEndBefore(Node refNode)
/*     */     throws RangeException, DOMException
/*     */   {
/* 156 */     setEndBeforeImpl(getPeer(), NodeImpl.getPeer(refNode));
/*     */   }
/*     */ 
/*     */   static native void setEndBeforeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public void setEndAfter(Node refNode)
/*     */     throws RangeException, DOMException
/*     */   {
/* 165 */     setEndAfterImpl(getPeer(), NodeImpl.getPeer(refNode));
/*     */   }
/*     */ 
/*     */   static native void setEndAfterImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public void collapse(boolean toStart)
/*     */     throws DOMException
/*     */   {
/* 174 */     collapseImpl(getPeer(), toStart);
/*     */   }
/*     */ 
/*     */   static native void collapseImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public void selectNode(Node refNode)
/*     */     throws RangeException, DOMException
/*     */   {
/* 183 */     selectNodeImpl(getPeer(), NodeImpl.getPeer(refNode));
/*     */   }
/*     */ 
/*     */   static native void selectNodeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public void selectNodeContents(Node refNode)
/*     */     throws RangeException, DOMException
/*     */   {
/* 192 */     selectNodeContentsImpl(getPeer(), NodeImpl.getPeer(refNode));
/*     */   }
/*     */ 
/*     */   static native void selectNodeContentsImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public short compareBoundaryPoints(short how, Range sourceRange)
/*     */     throws DOMException
/*     */   {
/* 202 */     return compareBoundaryPointsImpl(getPeer(), how, getPeer(sourceRange));
/*     */   }
/*     */ 
/*     */   static native short compareBoundaryPointsImpl(long paramLong1, short paramShort, long paramLong2);
/*     */ 
/*     */   public void deleteContents()
/*     */     throws DOMException
/*     */   {
/* 213 */     deleteContentsImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void deleteContentsImpl(long paramLong);
/*     */ 
/*     */   public DocumentFragment extractContents() throws DOMException
/*     */   {
/* 220 */     return DocumentFragmentImpl.getImpl(extractContentsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long extractContentsImpl(long paramLong);
/*     */ 
/*     */   public DocumentFragment cloneContents() throws DOMException
/*     */   {
/* 227 */     return DocumentFragmentImpl.getImpl(cloneContentsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long cloneContentsImpl(long paramLong);
/*     */ 
/*     */   public void insertNode(Node newNode) throws DOMException, RangeException
/*     */   {
/* 234 */     insertNodeImpl(getPeer(), NodeImpl.getPeer(newNode));
/*     */   }
/*     */ 
/*     */   static native void insertNodeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public void surroundContents(Node newParent)
/*     */     throws DOMException, RangeException
/*     */   {
/* 243 */     surroundContentsImpl(getPeer(), NodeImpl.getPeer(newParent));
/*     */   }
/*     */ 
/*     */   static native void surroundContentsImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public Range cloneRange()
/*     */     throws DOMException
/*     */   {
/* 252 */     return getImpl(cloneRangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long cloneRangeImpl(long paramLong);
/*     */ 
/*     */   public String toString() throws DOMException
/*     */   {
/* 259 */     return toStringImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native String toStringImpl(long paramLong);
/*     */ 
/*     */   public void detach() throws DOMException
/*     */   {
/* 266 */     detachImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void detachImpl(long paramLong);
/*     */ 
/*     */   public DocumentFragment createContextualFragment(String html) throws DOMException
/*     */   {
/* 273 */     return DocumentFragmentImpl.getImpl(createContextualFragmentImpl(getPeer(), html), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long createContextualFragmentImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean intersectsNode(Node refNode)
/*     */     throws RangeException, DOMException
/*     */   {
/* 282 */     return intersectsNodeImpl(getPeer(), NodeImpl.getPeer(refNode));
/*     */   }
/*     */ 
/*     */   static native boolean intersectsNodeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public short compareNode(Node refNode)
/*     */     throws RangeException, DOMException
/*     */   {
/* 291 */     return compareNodeImpl(getPeer(), NodeImpl.getPeer(refNode));
/*     */   }
/*     */ 
/*     */   static native short compareNodeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public short comparePoint(Node refNode, int offset)
/*     */     throws RangeException, DOMException
/*     */   {
/* 301 */     return comparePointImpl(getPeer(), NodeImpl.getPeer(refNode), offset);
/*     */   }
/*     */ 
/*     */   static native short comparePointImpl(long paramLong1, long paramLong2, int paramInt);
/*     */ 
/*     */   public boolean isPointInRange(Node refNode, int offset)
/*     */     throws RangeException, DOMException
/*     */   {
/* 313 */     return isPointInRangeImpl(getPeer(), NodeImpl.getPeer(refNode), offset);
/*     */   }
/*     */ 
/*     */   static native boolean isPointInRangeImpl(long paramLong1, long paramLong2, int paramInt);
/*     */ 
/*     */   public void expand(String unit)
/*     */     throws RangeException, DOMException
/*     */   {
/* 324 */     expandImpl(getPeer(), unit);
/*     */   }
/*     */ 
/*     */   static native void expandImpl(long paramLong, String paramString);
/*     */ 
/*     */   static class SelfDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */ 
/*     */     SelfDisposer(long peer)
/*     */     {
/*  17 */       this.peer = peer;
/*     */     }
/*     */     public void dispose() {
/*  20 */       RangeImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.RangeImpl
 * JD-Core Version:    0.6.2
 */