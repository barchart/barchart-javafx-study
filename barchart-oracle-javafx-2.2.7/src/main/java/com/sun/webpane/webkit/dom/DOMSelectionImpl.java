/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ranges.Range;
/*     */ 
/*     */ public class DOMSelectionImpl
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */ 
/*     */   DOMSelectionImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  23 */     this.peer = peer;
/*  24 */     this.contextPeer = contextPeer;
/*  25 */     this.rootPeer = rootPeer;
/*  26 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static DOMSelectionImpl create(long peer, long contextPeer, long rootPeer) {
/*  30 */     if (peer == 0L) return null;
/*  31 */     return new DOMSelectionImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  39 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(DOMSelectionImpl arg) {
/*  43 */     return arg == null ? 0L : arg.getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  47 */     return ((that instanceof DOMSelectionImpl)) && (this.peer == ((DOMSelectionImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  51 */     long p = this.peer;
/*  52 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static DOMSelectionImpl getImpl(long peer, long contextPeer, long rootPeer) {
/*  58 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public Node getAnchorNode()
/*     */   {
/*  64 */     return NodeImpl.getImpl(getAnchorNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getAnchorNodeImpl(long paramLong);
/*     */ 
/*     */   public int getAnchorOffset() {
/*  69 */     return getAnchorOffsetImpl(getPeer());
/*     */   }
/*     */   static native int getAnchorOffsetImpl(long paramLong);
/*     */ 
/*     */   public Node getFocusNode() {
/*  74 */     return NodeImpl.getImpl(getFocusNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getFocusNodeImpl(long paramLong);
/*     */ 
/*     */   public int getFocusOffset() {
/*  79 */     return getFocusOffsetImpl(getPeer());
/*     */   }
/*     */   static native int getFocusOffsetImpl(long paramLong);
/*     */ 
/*     */   public boolean getIsCollapsed() {
/*  84 */     return getIsCollapsedImpl(getPeer());
/*     */   }
/*     */   static native boolean getIsCollapsedImpl(long paramLong);
/*     */ 
/*     */   public int getRangeCount() {
/*  89 */     return getRangeCountImpl(getPeer());
/*     */   }
/*     */   static native int getRangeCountImpl(long paramLong);
/*     */ 
/*     */   public Node getBaseNode() {
/*  94 */     return NodeImpl.getImpl(getBaseNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getBaseNodeImpl(long paramLong);
/*     */ 
/*     */   public int getBaseOffset() {
/*  99 */     return getBaseOffsetImpl(getPeer());
/*     */   }
/*     */   static native int getBaseOffsetImpl(long paramLong);
/*     */ 
/*     */   public Node getExtentNode() {
/* 104 */     return NodeImpl.getImpl(getExtentNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getExtentNodeImpl(long paramLong);
/*     */ 
/*     */   public int getExtentOffset() {
/* 109 */     return getExtentOffsetImpl(getPeer());
/*     */   }
/*     */   static native int getExtentOffsetImpl(long paramLong);
/*     */ 
/*     */   public String getType() {
/* 114 */     return getTypeImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native String getTypeImpl(long paramLong);
/*     */ 
/*     */   public void collapse(Node node, int index)
/*     */     throws DOMException
/*     */   {
/* 123 */     collapseImpl(getPeer(), NodeImpl.getPeer(node), index);
/*     */   }
/*     */ 
/*     */   static native void collapseImpl(long paramLong1, long paramLong2, int paramInt);
/*     */ 
/*     */   public void collapseToEnd()
/*     */     throws DOMException
/*     */   {
/* 134 */     collapseToEndImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void collapseToEndImpl(long paramLong);
/*     */ 
/*     */   public void collapseToStart() throws DOMException
/*     */   {
/* 141 */     collapseToStartImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void collapseToStartImpl(long paramLong);
/*     */ 
/*     */   public void deleteFromDocument()
/*     */   {
/* 148 */     deleteFromDocumentImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void deleteFromDocumentImpl(long paramLong);
/*     */ 
/*     */   public boolean containsNode(Node node, boolean allowPartial)
/*     */   {
/* 156 */     return containsNodeImpl(getPeer(), NodeImpl.getPeer(node), allowPartial);
/*     */   }
/*     */ 
/*     */   static native boolean containsNodeImpl(long paramLong1, long paramLong2, boolean paramBoolean);
/*     */ 
/*     */   public void selectAllChildren(Node node)
/*     */     throws DOMException
/*     */   {
/* 167 */     selectAllChildrenImpl(getPeer(), NodeImpl.getPeer(node));
/*     */   }
/*     */ 
/*     */   static native void selectAllChildrenImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public void extend(Node node, int offset)
/*     */     throws DOMException
/*     */   {
/* 177 */     extendImpl(getPeer(), NodeImpl.getPeer(node), offset);
/*     */   }
/*     */ 
/*     */   static native void extendImpl(long paramLong1, long paramLong2, int paramInt);
/*     */ 
/*     */   public Range getRangeAt(int index)
/*     */     throws DOMException
/*     */   {
/* 188 */     return RangeImpl.getImpl(getRangeAtImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getRangeAtImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public void removeAllRanges()
/*     */   {
/* 197 */     removeAllRangesImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void removeAllRangesImpl(long paramLong);
/*     */ 
/*     */   public void addRange(Range range)
/*     */   {
/* 204 */     addRangeImpl(getPeer(), RangeImpl.getPeer(range));
/*     */   }
/*     */ 
/*     */   static native void addRangeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public void modify(String alter, String direction, String granularity)
/*     */   {
/* 215 */     modifyImpl(getPeer(), alter, direction, granularity);
/*     */   }
/*     */ 
/*     */   static native void modifyImpl(long paramLong, String paramString1, String paramString2, String paramString3);
/*     */ 
/*     */   public void setBaseAndExtent(Node baseNode, int baseOffset, Node extentNode, int extentOffset)
/*     */     throws DOMException
/*     */   {
/* 231 */     setBaseAndExtentImpl(getPeer(), NodeImpl.getPeer(baseNode), baseOffset, NodeImpl.getPeer(extentNode), extentOffset);
/*     */   }
/*     */ 
/*     */   static native void setBaseAndExtentImpl(long paramLong1, long paramLong2, int paramInt1, long paramLong3, int paramInt2);
/*     */ 
/*     */   public void setPosition(Node node, int offset)
/*     */     throws DOMException
/*     */   {
/* 247 */     setPositionImpl(getPeer(), NodeImpl.getPeer(node), offset);
/*     */   }
/*     */ 
/*     */   static native void setPositionImpl(long paramLong1, long paramLong2, int paramInt);
/*     */ 
/*     */   public void empty()
/*     */   {
/* 258 */     emptyImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void emptyImpl(long paramLong);
/*     */ 
/*     */   static class SelfDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */ 
/*     */     SelfDisposer(long peer)
/*     */     {
/*  15 */       this.peer = peer;
/*     */     }
/*     */     public void dispose() {
/*  18 */       DOMSelectionImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.DOMSelectionImpl
 * JD-Core Version:    0.6.2
 */