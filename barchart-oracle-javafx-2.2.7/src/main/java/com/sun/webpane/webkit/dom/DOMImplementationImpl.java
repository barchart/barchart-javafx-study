/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.css.CSSStyleSheet;
/*     */ import org.w3c.dom.html.HTMLDocument;
/*     */ 
/*     */ public class DOMImplementationImpl
/*     */   implements DOMImplementation
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */ 
/*     */   DOMImplementationImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  26 */     this.peer = peer;
/*  27 */     this.contextPeer = contextPeer;
/*  28 */     this.rootPeer = rootPeer;
/*  29 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static DOMImplementation create(long peer, long contextPeer, long rootPeer) {
/*  33 */     if (peer == 0L) return null;
/*  34 */     return new DOMImplementationImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  42 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(DOMImplementation arg) {
/*  46 */     return arg == null ? 0L : ((DOMImplementationImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  50 */     return ((that instanceof DOMImplementationImpl)) && (this.peer == ((DOMImplementationImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  54 */     long p = this.peer;
/*  55 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static DOMImplementation getImpl(long peer, long contextPeer, long rootPeer) {
/*  61 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public boolean hasFeature(String feature, String version)
/*     */   {
/*  69 */     return hasFeatureImpl(getPeer(), feature, version);
/*     */   }
/*     */ 
/*     */   static native boolean hasFeatureImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId)
/*     */     throws DOMException
/*     */   {
/*  82 */     return DocumentTypeImpl.getImpl(createDocumentTypeImpl(getPeer(), qualifiedName, publicId, systemId), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long createDocumentTypeImpl(long paramLong, String paramString1, String paramString2, String paramString3);
/*     */ 
/*     */   public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype)
/*     */     throws DOMException
/*     */   {
/*  97 */     return DocumentImpl.getImpl(createDocumentImpl(getPeer(), namespaceURI, qualifiedName, DocumentTypeImpl.getPeer(doctype)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long createDocumentImpl(long paramLong1, String paramString1, String paramString2, long paramLong2);
/*     */ 
/*     */   public CSSStyleSheet createCSSStyleSheet(String title, String media)
/*     */     throws DOMException
/*     */   {
/* 111 */     return CSSStyleSheetImpl.getImpl(createCSSStyleSheetImpl(getPeer(), title, media), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long createCSSStyleSheetImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public HTMLDocument createHTMLDocument(String title)
/*     */   {
/* 122 */     return HTMLDocumentImpl.getImpl(createHTMLDocumentImpl(getPeer(), title), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long createHTMLDocumentImpl(long paramLong, String paramString);
/*     */ 
/*     */   public Object getFeature(String feature, String version)
/*     */   {
/* 132 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   static class SelfDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */ 
/*     */     SelfDisposer(long peer)
/*     */     {
/*  18 */       this.peer = peer;
/*     */     }
/*     */     public void dispose() {
/*  21 */       DOMImplementationImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.DOMImplementationImpl
 * JD-Core Version:    0.6.2
 */