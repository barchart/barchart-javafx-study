/*      */ package com.sun.webpane.webkit.dom;
/*      */ 
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.CDATASection;
/*      */ import org.w3c.dom.Comment;
/*      */ import org.w3c.dom.DOMConfiguration;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.DOMImplementation;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.DocumentFragment;
/*      */ import org.w3c.dom.DocumentType;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.EntityReference;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.w3c.dom.ProcessingInstruction;
/*      */ import org.w3c.dom.Text;
/*      */ import org.w3c.dom.css.CSSStyleDeclaration;
/*      */ import org.w3c.dom.events.DocumentEvent;
/*      */ import org.w3c.dom.events.Event;
/*      */ import org.w3c.dom.events.EventListener;
/*      */ import org.w3c.dom.events.EventTarget;
/*      */ import org.w3c.dom.html.HTMLCollection;
/*      */ import org.w3c.dom.html.HTMLElement;
/*      */ import org.w3c.dom.html.HTMLHeadElement;
/*      */ import org.w3c.dom.ranges.Range;
/*      */ import org.w3c.dom.stylesheets.StyleSheetList;
/*      */ import org.w3c.dom.traversal.NodeFilter;
/*      */ import org.w3c.dom.traversal.NodeIterator;
/*      */ import org.w3c.dom.traversal.TreeWalker;
/*      */ import org.w3c.dom.views.AbstractView;
/*      */ import org.w3c.dom.views.DocumentView;
/*      */ import org.w3c.dom.xpath.XPathEvaluator;
/*      */ import org.w3c.dom.xpath.XPathExpression;
/*      */ import org.w3c.dom.xpath.XPathNSResolver;
/*      */ import org.w3c.dom.xpath.XPathResult;
/*      */ 
/*      */ public class DocumentImpl extends NodeImpl
/*      */   implements Document, XPathEvaluator, DocumentView, DocumentEvent
/*      */ {
/*      */   DocumentImpl(long peer, long contextPeer, long rootPeer)
/*      */   {
/*   42 */     super(peer, contextPeer, rootPeer);
/*      */   }
/*      */ 
/*      */   static Document getImpl(long peer, long contextPeer, long rootPeer) {
/*   46 */     return (DocumentEvent)create(peer, contextPeer, rootPeer);
/*      */   }
/*      */ 
/*      */   static native boolean isHTMLDocumentImpl(long paramLong);
/*      */ 
/*      */   public Object evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result) throws DOMException {
/*   52 */     return evaluate(expression, contextNode, resolver, type, (XPathResult)result);
/*      */   }
/*      */ 
/*      */   public DocumentType getDoctype()
/*      */   {
/*   58 */     return DocumentTypeImpl.getImpl(getDoctypeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getDoctypeImpl(long paramLong);
/*      */ 
/*      */   public DOMImplementation getImplementation() {
/*   63 */     return DOMImplementationImpl.getImpl(getImplementationImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getImplementationImpl(long paramLong);
/*      */ 
/*      */   public Element getDocumentElement() {
/*   68 */     return ElementImpl.getImpl(getDocumentElementImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getDocumentElementImpl(long paramLong);
/*      */ 
/*      */   public String getInputEncoding() {
/*   73 */     return getInputEncodingImpl(getPeer());
/*      */   }
/*      */   static native String getInputEncodingImpl(long paramLong);
/*      */ 
/*      */   public String getXmlEncoding() {
/*   78 */     return getXmlEncodingImpl(getPeer());
/*      */   }
/*      */   static native String getXmlEncodingImpl(long paramLong);
/*      */ 
/*      */   public String getXmlVersion() {
/*   83 */     return getXmlVersionImpl(getPeer());
/*      */   }
/*      */   static native String getXmlVersionImpl(long paramLong);
/*      */ 
/*      */   public void setXmlVersion(String value) throws DOMException {
/*   88 */     setXmlVersionImpl(getPeer(), value);
/*      */   }
/*      */   static native void setXmlVersionImpl(long paramLong, String paramString);
/*      */ 
/*      */   public boolean getXmlStandalone() {
/*   93 */     return getXmlStandaloneImpl(getPeer());
/*      */   }
/*      */   static native boolean getXmlStandaloneImpl(long paramLong);
/*      */ 
/*      */   public void setXmlStandalone(boolean value) throws DOMException {
/*   98 */     setXmlStandaloneImpl(getPeer(), value);
/*      */   }
/*      */   static native void setXmlStandaloneImpl(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   public String getDocumentURI() {
/*  103 */     return getDocumentURIImpl(getPeer());
/*      */   }
/*      */   static native String getDocumentURIImpl(long paramLong);
/*      */ 
/*      */   public void setDocumentURI(String value) {
/*  108 */     setDocumentURIImpl(getPeer(), value);
/*      */   }
/*      */   static native void setDocumentURIImpl(long paramLong, String paramString);
/*      */ 
/*      */   public AbstractView getDefaultView() {
/*  113 */     return DOMWindowImpl.getImpl(getDefaultViewImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getDefaultViewImpl(long paramLong);
/*      */ 
/*      */   public StyleSheetList getStyleSheets() {
/*  118 */     return StyleSheetListImpl.getImpl(getStyleSheetsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getStyleSheetsImpl(long paramLong);
/*      */ 
/*      */   public String getTitle() {
/*  123 */     return getTitleImpl(getPeer());
/*      */   }
/*      */   static native String getTitleImpl(long paramLong);
/*      */ 
/*      */   public void setTitle(String value) {
/*  128 */     setTitleImpl(getPeer(), value);
/*      */   }
/*      */   static native void setTitleImpl(long paramLong, String paramString);
/*      */ 
/*      */   public String getReferrer() {
/*  133 */     return getReferrerImpl(getPeer());
/*      */   }
/*      */   static native String getReferrerImpl(long paramLong);
/*      */ 
/*      */   public String getDomain() {
/*  138 */     return getDomainImpl(getPeer());
/*      */   }
/*      */   static native String getDomainImpl(long paramLong);
/*      */ 
/*      */   public String getURL() {
/*  143 */     return getURLImpl(getPeer());
/*      */   }
/*      */   static native String getURLImpl(long paramLong);
/*      */ 
/*      */   public String getCookie() throws DOMException {
/*  148 */     return getCookieImpl(getPeer());
/*      */   }
/*      */   static native String getCookieImpl(long paramLong);
/*      */ 
/*      */   public void setCookie(String value) throws DOMException {
/*  153 */     setCookieImpl(getPeer(), value);
/*      */   }
/*      */   static native void setCookieImpl(long paramLong, String paramString);
/*      */ 
/*      */   public HTMLElement getBody() {
/*  158 */     return HTMLElementImpl.getImpl(getBodyImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getBodyImpl(long paramLong);
/*      */ 
/*      */   public void setBody(HTMLElement value) throws DOMException {
/*  163 */     setBodyImpl(getPeer(), HTMLElementImpl.getPeer(value));
/*      */   }
/*      */   static native void setBodyImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public HTMLHeadElement getHead() {
/*  168 */     return HTMLHeadElementImpl.getImpl(getHeadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getHeadImpl(long paramLong);
/*      */ 
/*      */   public HTMLCollection getImages() {
/*  173 */     return HTMLCollectionImpl.getImpl(getImagesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getImagesImpl(long paramLong);
/*      */ 
/*      */   public HTMLCollection getApplets() {
/*  178 */     return HTMLCollectionImpl.getImpl(getAppletsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getAppletsImpl(long paramLong);
/*      */ 
/*      */   public HTMLCollection getLinks() {
/*  183 */     return HTMLCollectionImpl.getImpl(getLinksImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getLinksImpl(long paramLong);
/*      */ 
/*      */   public HTMLCollection getForms() {
/*  188 */     return HTMLCollectionImpl.getImpl(getFormsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getFormsImpl(long paramLong);
/*      */ 
/*      */   public HTMLCollection getAnchors() {
/*  193 */     return HTMLCollectionImpl.getImpl(getAnchorsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getAnchorsImpl(long paramLong);
/*      */ 
/*      */   public String getLastModified() {
/*  198 */     return getLastModifiedImpl(getPeer());
/*      */   }
/*      */   static native String getLastModifiedImpl(long paramLong);
/*      */ 
/*      */   public String getCharset() {
/*  203 */     return getCharsetImpl(getPeer());
/*      */   }
/*      */   static native String getCharsetImpl(long paramLong);
/*      */ 
/*      */   public void setCharset(String value) {
/*  208 */     setCharsetImpl(getPeer(), value);
/*      */   }
/*      */   static native void setCharsetImpl(long paramLong, String paramString);
/*      */ 
/*      */   public String getDefaultCharset() {
/*  213 */     return getDefaultCharsetImpl(getPeer());
/*      */   }
/*      */   static native String getDefaultCharsetImpl(long paramLong);
/*      */ 
/*      */   public String getReadyState() {
/*  218 */     return getReadyStateImpl(getPeer());
/*      */   }
/*      */   static native String getReadyStateImpl(long paramLong);
/*      */ 
/*      */   public String getCharacterSet() {
/*  223 */     return getCharacterSetImpl(getPeer());
/*      */   }
/*      */   static native String getCharacterSetImpl(long paramLong);
/*      */ 
/*      */   public String getPreferredStylesheetSet() {
/*  228 */     return getPreferredStylesheetSetImpl(getPeer());
/*      */   }
/*      */   static native String getPreferredStylesheetSetImpl(long paramLong);
/*      */ 
/*      */   public String getSelectedStylesheetSet() {
/*  233 */     return getSelectedStylesheetSetImpl(getPeer());
/*      */   }
/*      */   static native String getSelectedStylesheetSetImpl(long paramLong);
/*      */ 
/*      */   public void setSelectedStylesheetSet(String value) {
/*  238 */     setSelectedStylesheetSetImpl(getPeer(), value);
/*      */   }
/*      */   static native void setSelectedStylesheetSetImpl(long paramLong, String paramString);
/*      */ 
/*      */   public String getCompatMode() {
/*  243 */     return getCompatModeImpl(getPeer());
/*      */   }
/*      */   static native String getCompatModeImpl(long paramLong);
/*      */ 
/*      */   public EventListener getOnabort() {
/*  248 */     return EventListenerImpl.getImpl(getOnabortImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnabortImpl(long paramLong);
/*      */ 
/*      */   public void setOnabort(EventListener value) {
/*  253 */     setOnabortImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnabortImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnblur() {
/*  258 */     return EventListenerImpl.getImpl(getOnblurImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnblurImpl(long paramLong);
/*      */ 
/*      */   public void setOnblur(EventListener value) {
/*  263 */     setOnblurImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnblurImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnchange() {
/*  268 */     return EventListenerImpl.getImpl(getOnchangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnchangeImpl(long paramLong);
/*      */ 
/*      */   public void setOnchange(EventListener value) {
/*  273 */     setOnchangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnchangeImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnclick() {
/*  278 */     return EventListenerImpl.getImpl(getOnclickImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnclickImpl(long paramLong);
/*      */ 
/*      */   public void setOnclick(EventListener value) {
/*  283 */     setOnclickImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnclickImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOncontextmenu() {
/*  288 */     return EventListenerImpl.getImpl(getOncontextmenuImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOncontextmenuImpl(long paramLong);
/*      */ 
/*      */   public void setOncontextmenu(EventListener value) {
/*  293 */     setOncontextmenuImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOncontextmenuImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndblclick() {
/*  298 */     return EventListenerImpl.getImpl(getOndblclickImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndblclickImpl(long paramLong);
/*      */ 
/*      */   public void setOndblclick(EventListener value) {
/*  303 */     setOndblclickImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndblclickImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndrag() {
/*  308 */     return EventListenerImpl.getImpl(getOndragImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragImpl(long paramLong);
/*      */ 
/*      */   public void setOndrag(EventListener value) {
/*  313 */     setOndragImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndragend() {
/*  318 */     return EventListenerImpl.getImpl(getOndragendImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragendImpl(long paramLong);
/*      */ 
/*      */   public void setOndragend(EventListener value) {
/*  323 */     setOndragendImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragendImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndragenter() {
/*  328 */     return EventListenerImpl.getImpl(getOndragenterImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragenterImpl(long paramLong);
/*      */ 
/*      */   public void setOndragenter(EventListener value) {
/*  333 */     setOndragenterImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragenterImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndragleave() {
/*  338 */     return EventListenerImpl.getImpl(getOndragleaveImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragleaveImpl(long paramLong);
/*      */ 
/*      */   public void setOndragleave(EventListener value) {
/*  343 */     setOndragleaveImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragleaveImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndragover() {
/*  348 */     return EventListenerImpl.getImpl(getOndragoverImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragoverImpl(long paramLong);
/*      */ 
/*      */   public void setOndragover(EventListener value) {
/*  353 */     setOndragoverImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragoverImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndragstart() {
/*  358 */     return EventListenerImpl.getImpl(getOndragstartImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndragstartImpl(long paramLong);
/*      */ 
/*      */   public void setOndragstart(EventListener value) {
/*  363 */     setOndragstartImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndragstartImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOndrop() {
/*  368 */     return EventListenerImpl.getImpl(getOndropImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOndropImpl(long paramLong);
/*      */ 
/*      */   public void setOndrop(EventListener value) {
/*  373 */     setOndropImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOndropImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnerror() {
/*  378 */     return EventListenerImpl.getImpl(getOnerrorImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnerrorImpl(long paramLong);
/*      */ 
/*      */   public void setOnerror(EventListener value) {
/*  383 */     setOnerrorImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnerrorImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnfocus() {
/*  388 */     return EventListenerImpl.getImpl(getOnfocusImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnfocusImpl(long paramLong);
/*      */ 
/*      */   public void setOnfocus(EventListener value) {
/*  393 */     setOnfocusImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnfocusImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOninput() {
/*  398 */     return EventListenerImpl.getImpl(getOninputImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOninputImpl(long paramLong);
/*      */ 
/*      */   public void setOninput(EventListener value) {
/*  403 */     setOninputImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOninputImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOninvalid() {
/*  408 */     return EventListenerImpl.getImpl(getOninvalidImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOninvalidImpl(long paramLong);
/*      */ 
/*      */   public void setOninvalid(EventListener value) {
/*  413 */     setOninvalidImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOninvalidImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnkeydown() {
/*  418 */     return EventListenerImpl.getImpl(getOnkeydownImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnkeydownImpl(long paramLong);
/*      */ 
/*      */   public void setOnkeydown(EventListener value) {
/*  423 */     setOnkeydownImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnkeydownImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnkeypress() {
/*  428 */     return EventListenerImpl.getImpl(getOnkeypressImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnkeypressImpl(long paramLong);
/*      */ 
/*      */   public void setOnkeypress(EventListener value) {
/*  433 */     setOnkeypressImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnkeypressImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnkeyup() {
/*  438 */     return EventListenerImpl.getImpl(getOnkeyupImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnkeyupImpl(long paramLong);
/*      */ 
/*      */   public void setOnkeyup(EventListener value) {
/*  443 */     setOnkeyupImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnkeyupImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnload() {
/*  448 */     return EventListenerImpl.getImpl(getOnloadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnloadImpl(long paramLong);
/*      */ 
/*      */   public void setOnload(EventListener value) {
/*  453 */     setOnloadImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnloadImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmousedown() {
/*  458 */     return EventListenerImpl.getImpl(getOnmousedownImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmousedownImpl(long paramLong);
/*      */ 
/*      */   public void setOnmousedown(EventListener value) {
/*  463 */     setOnmousedownImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmousedownImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmousemove() {
/*  468 */     return EventListenerImpl.getImpl(getOnmousemoveImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmousemoveImpl(long paramLong);
/*      */ 
/*      */   public void setOnmousemove(EventListener value) {
/*  473 */     setOnmousemoveImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmousemoveImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmouseout() {
/*  478 */     return EventListenerImpl.getImpl(getOnmouseoutImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmouseoutImpl(long paramLong);
/*      */ 
/*      */   public void setOnmouseout(EventListener value) {
/*  483 */     setOnmouseoutImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmouseoutImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmouseover() {
/*  488 */     return EventListenerImpl.getImpl(getOnmouseoverImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmouseoverImpl(long paramLong);
/*      */ 
/*      */   public void setOnmouseover(EventListener value) {
/*  493 */     setOnmouseoverImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmouseoverImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmouseup() {
/*  498 */     return EventListenerImpl.getImpl(getOnmouseupImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmouseupImpl(long paramLong);
/*      */ 
/*      */   public void setOnmouseup(EventListener value) {
/*  503 */     setOnmouseupImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmouseupImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnmousewheel() {
/*  508 */     return EventListenerImpl.getImpl(getOnmousewheelImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnmousewheelImpl(long paramLong);
/*      */ 
/*      */   public void setOnmousewheel(EventListener value) {
/*  513 */     setOnmousewheelImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnmousewheelImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnreadystatechange() {
/*  518 */     return EventListenerImpl.getImpl(getOnreadystatechangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnreadystatechangeImpl(long paramLong);
/*      */ 
/*      */   public void setOnreadystatechange(EventListener value) {
/*  523 */     setOnreadystatechangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnreadystatechangeImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnscroll() {
/*  528 */     return EventListenerImpl.getImpl(getOnscrollImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnscrollImpl(long paramLong);
/*      */ 
/*      */   public void setOnscroll(EventListener value) {
/*  533 */     setOnscrollImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnscrollImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnselect() {
/*  538 */     return EventListenerImpl.getImpl(getOnselectImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnselectImpl(long paramLong);
/*      */ 
/*      */   public void setOnselect(EventListener value) {
/*  543 */     setOnselectImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnselectImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnsubmit() {
/*  548 */     return EventListenerImpl.getImpl(getOnsubmitImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnsubmitImpl(long paramLong);
/*      */ 
/*      */   public void setOnsubmit(EventListener value) {
/*  553 */     setOnsubmitImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnsubmitImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnbeforecut() {
/*  558 */     return EventListenerImpl.getImpl(getOnbeforecutImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnbeforecutImpl(long paramLong);
/*      */ 
/*      */   public void setOnbeforecut(EventListener value) {
/*  563 */     setOnbeforecutImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnbeforecutImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOncut() {
/*  568 */     return EventListenerImpl.getImpl(getOncutImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOncutImpl(long paramLong);
/*      */ 
/*      */   public void setOncut(EventListener value) {
/*  573 */     setOncutImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOncutImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnbeforecopy() {
/*  578 */     return EventListenerImpl.getImpl(getOnbeforecopyImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnbeforecopyImpl(long paramLong);
/*      */ 
/*      */   public void setOnbeforecopy(EventListener value) {
/*  583 */     setOnbeforecopyImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnbeforecopyImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOncopy() {
/*  588 */     return EventListenerImpl.getImpl(getOncopyImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOncopyImpl(long paramLong);
/*      */ 
/*      */   public void setOncopy(EventListener value) {
/*  593 */     setOncopyImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOncopyImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnbeforepaste() {
/*  598 */     return EventListenerImpl.getImpl(getOnbeforepasteImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnbeforepasteImpl(long paramLong);
/*      */ 
/*      */   public void setOnbeforepaste(EventListener value) {
/*  603 */     setOnbeforepasteImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnbeforepasteImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnpaste() {
/*  608 */     return EventListenerImpl.getImpl(getOnpasteImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnpasteImpl(long paramLong);
/*      */ 
/*      */   public void setOnpaste(EventListener value) {
/*  613 */     setOnpasteImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnpasteImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnreset() {
/*  618 */     return EventListenerImpl.getImpl(getOnresetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnresetImpl(long paramLong);
/*      */ 
/*      */   public void setOnreset(EventListener value) {
/*  623 */     setOnresetImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnresetImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnsearch() {
/*  628 */     return EventListenerImpl.getImpl(getOnsearchImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnsearchImpl(long paramLong);
/*      */ 
/*      */   public void setOnsearch(EventListener value) {
/*  633 */     setOnsearchImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnsearchImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnselectstart() {
/*  638 */     return EventListenerImpl.getImpl(getOnselectstartImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnselectstartImpl(long paramLong);
/*      */ 
/*      */   public void setOnselectstart(EventListener value) {
/*  643 */     setOnselectstartImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnselectstartImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOnselectionchange() {
/*  648 */     return EventListenerImpl.getImpl(getOnselectionchangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOnselectionchangeImpl(long paramLong);
/*      */ 
/*      */   public void setOnselectionchange(EventListener value) {
/*  653 */     setOnselectionchangeImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOnselectionchangeImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOntouchstart() {
/*  658 */     return EventListenerImpl.getImpl(getOntouchstartImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOntouchstartImpl(long paramLong);
/*      */ 
/*      */   public void setOntouchstart(EventListener value) {
/*  663 */     setOntouchstartImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOntouchstartImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOntouchmove() {
/*  668 */     return EventListenerImpl.getImpl(getOntouchmoveImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOntouchmoveImpl(long paramLong);
/*      */ 
/*      */   public void setOntouchmove(EventListener value) {
/*  673 */     setOntouchmoveImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOntouchmoveImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOntouchend() {
/*  678 */     return EventListenerImpl.getImpl(getOntouchendImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOntouchendImpl(long paramLong);
/*      */ 
/*      */   public void setOntouchend(EventListener value) {
/*  683 */     setOntouchendImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOntouchendImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public EventListener getOntouchcancel() {
/*  688 */     return EventListenerImpl.getImpl(getOntouchcancelImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */   static native long getOntouchcancelImpl(long paramLong);
/*      */ 
/*      */   public void setOntouchcancel(EventListener value) {
/*  693 */     setOntouchcancelImpl(getPeer(), EventListenerImpl.getPeer(value, this.contextPeer, this.rootPeer));
/*      */   }
/*      */   static native void setOntouchcancelImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public String getWebkitVisibilityState() {
/*  698 */     return getWebkitVisibilityStateImpl(getPeer());
/*      */   }
/*      */   static native String getWebkitVisibilityStateImpl(long paramLong);
/*      */ 
/*      */   public boolean getWebkitHidden() {
/*  703 */     return getWebkitHiddenImpl(getPeer());
/*      */   }
/*      */ 
/*      */   static native boolean getWebkitHiddenImpl(long paramLong);
/*      */ 
/*      */   public Element createElement(String tagName)
/*      */     throws DOMException
/*      */   {
/*  711 */     return ElementImpl.getImpl(createElementImpl(getPeer(), tagName), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createElementImpl(long paramLong, String paramString);
/*      */ 
/*      */   public DocumentFragment createDocumentFragment()
/*      */   {
/*  720 */     return DocumentFragmentImpl.getImpl(createDocumentFragmentImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createDocumentFragmentImpl(long paramLong);
/*      */ 
/*      */   public Text createTextNode(String data)
/*      */   {
/*  727 */     return TextImpl.getImpl(createTextNodeImpl(getPeer(), data), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createTextNodeImpl(long paramLong, String paramString);
/*      */ 
/*      */   public Comment createComment(String data)
/*      */   {
/*  736 */     return CommentImpl.getImpl(createCommentImpl(getPeer(), data), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createCommentImpl(long paramLong, String paramString);
/*      */ 
/*      */   public CDATASection createCDATASection(String data)
/*      */     throws DOMException
/*      */   {
/*  745 */     return CDATASectionImpl.getImpl(createCDATASectionImpl(getPeer(), data), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createCDATASectionImpl(long paramLong, String paramString);
/*      */ 
/*      */   public ProcessingInstruction createProcessingInstruction(String target, String data)
/*      */     throws DOMException
/*      */   {
/*  755 */     return ProcessingInstructionImpl.getImpl(createProcessingInstructionImpl(getPeer(), target, data), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createProcessingInstructionImpl(long paramLong, String paramString1, String paramString2);
/*      */ 
/*      */   public Attr createAttribute(String name)
/*      */     throws DOMException
/*      */   {
/*  766 */     return AttrImpl.getImpl(createAttributeImpl(getPeer(), name), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createAttributeImpl(long paramLong, String paramString);
/*      */ 
/*      */   public EntityReference createEntityReference(String name)
/*      */     throws DOMException
/*      */   {
/*  775 */     return EntityReferenceImpl.getImpl(createEntityReferenceImpl(getPeer(), name), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createEntityReferenceImpl(long paramLong, String paramString);
/*      */ 
/*      */   public NodeList getElementsByTagName(String tagname)
/*      */   {
/*  784 */     return NodeListImpl.getImpl(getElementsByTagNameImpl(getPeer(), tagname), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long getElementsByTagNameImpl(long paramLong, String paramString);
/*      */ 
/*      */   public Node importNode(Node importedNode, boolean deep)
/*      */     throws DOMException
/*      */   {
/*  794 */     return NodeImpl.getImpl(importNodeImpl(getPeer(), NodeImpl.getPeer(importedNode), deep), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long importNodeImpl(long paramLong1, long paramLong2, boolean paramBoolean);
/*      */ 
/*      */   public Element createElementNS(String namespaceURI, String qualifiedName)
/*      */     throws DOMException
/*      */   {
/*  806 */     return ElementImpl.getImpl(createElementNSImpl(getPeer(), namespaceURI, qualifiedName), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createElementNSImpl(long paramLong, String paramString1, String paramString2);
/*      */ 
/*      */   public Attr createAttributeNS(String namespaceURI, String qualifiedName)
/*      */     throws DOMException
/*      */   {
/*  818 */     return AttrImpl.getImpl(createAttributeNSImpl(getPeer(), namespaceURI, qualifiedName), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createAttributeNSImpl(long paramLong, String paramString1, String paramString2);
/*      */ 
/*      */   public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
/*      */   {
/*  830 */     return NodeListImpl.getImpl(getElementsByTagNameNSImpl(getPeer(), namespaceURI, localName), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long getElementsByTagNameNSImpl(long paramLong, String paramString1, String paramString2);
/*      */ 
/*      */   public Element getElementById(String elementId)
/*      */   {
/*  841 */     return ElementImpl.getImpl(getElementByIdImpl(getPeer(), elementId), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long getElementByIdImpl(long paramLong, String paramString);
/*      */ 
/*      */   public Node adoptNode(Node source)
/*      */     throws DOMException
/*      */   {
/*  850 */     return NodeImpl.getImpl(adoptNodeImpl(getPeer(), NodeImpl.getPeer(source)), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long adoptNodeImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public Event createEvent(String eventType)
/*      */     throws DOMException
/*      */   {
/*  859 */     return EventImpl.getImpl(createEventImpl(getPeer(), eventType), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createEventImpl(long paramLong, String paramString);
/*      */ 
/*      */   public Range createRange()
/*      */   {
/*  868 */     return RangeImpl.getImpl(createRangeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createRangeImpl(long paramLong);
/*      */ 
/*      */   public NodeIterator createNodeIterator(Node root, int whatToShow, NodeFilter filter, boolean expandEntityReferences)
/*      */     throws DOMException
/*      */   {
/*  878 */     return NodeIteratorImpl.getImpl(createNodeIteratorImpl(getPeer(), NodeImpl.getPeer(root), whatToShow, NodeFilterImpl.getPeer(filter), expandEntityReferences), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createNodeIteratorImpl(long paramLong1, long paramLong2, int paramInt, long paramLong3, boolean paramBoolean);
/*      */ 
/*      */   public TreeWalker createTreeWalker(Node root, int whatToShow, NodeFilter filter, boolean expandEntityReferences)
/*      */     throws DOMException
/*      */   {
/*  896 */     return TreeWalkerImpl.getImpl(createTreeWalkerImpl(getPeer(), NodeImpl.getPeer(root), whatToShow, NodeFilterImpl.getPeer(filter), expandEntityReferences), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createTreeWalkerImpl(long paramLong1, long paramLong2, int paramInt, long paramLong3, boolean paramBoolean);
/*      */ 
/*      */   public CSSStyleDeclaration getOverrideStyle(Element element, String pseudoElement)
/*      */   {
/*  912 */     return CSSStyleDeclarationImpl.getImpl(getOverrideStyleImpl(getPeer(), ElementImpl.getPeer(element), pseudoElement), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long getOverrideStyleImpl(long paramLong1, long paramLong2, String paramString);
/*      */ 
/*      */   public XPathExpression createExpression(String expression, XPathNSResolver resolver)
/*      */     throws DOMException
/*      */   {
/*  924 */     return XPathExpressionImpl.getImpl(createExpressionImpl(getPeer(), expression, XPathNSResolverImpl.getPeer(resolver)), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createExpressionImpl(long paramLong1, String paramString, long paramLong2);
/*      */ 
/*      */   public XPathNSResolver createNSResolver(Node nodeResolver)
/*      */   {
/*  935 */     return XPathNSResolverImpl.getImpl(createNSResolverImpl(getPeer(), NodeImpl.getPeer(nodeResolver)), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createNSResolverImpl(long paramLong1, long paramLong2);
/*      */ 
/*      */   public XPathResult evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, XPathResult inResult)
/*      */     throws DOMException
/*      */   {
/*  948 */     return XPathResultImpl.getImpl(evaluateImpl(getPeer(), expression, NodeImpl.getPeer(contextNode), XPathNSResolverImpl.getPeer(resolver), type, XPathResultImpl.getPeer(inResult)), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long evaluateImpl(long paramLong1, String paramString, long paramLong2, long paramLong3, short paramShort, long paramLong4);
/*      */ 
/*      */   public boolean execCommand(String command, boolean userInterface, String value)
/*      */   {
/*  967 */     return execCommandImpl(getPeer(), command, userInterface, value);
/*      */   }
/*      */ 
/*      */   static native boolean execCommandImpl(long paramLong, String paramString1, boolean paramBoolean, String paramString2);
/*      */ 
/*      */   public boolean queryCommandEnabled(String command)
/*      */   {
/*  980 */     return queryCommandEnabledImpl(getPeer(), command);
/*      */   }
/*      */ 
/*      */   static native boolean queryCommandEnabledImpl(long paramLong, String paramString);
/*      */ 
/*      */   public boolean queryCommandIndeterm(String command)
/*      */   {
/*  989 */     return queryCommandIndetermImpl(getPeer(), command);
/*      */   }
/*      */ 
/*      */   static native boolean queryCommandIndetermImpl(long paramLong, String paramString);
/*      */ 
/*      */   public boolean queryCommandState(String command)
/*      */   {
/*  998 */     return queryCommandStateImpl(getPeer(), command);
/*      */   }
/*      */ 
/*      */   static native boolean queryCommandStateImpl(long paramLong, String paramString);
/*      */ 
/*      */   public boolean queryCommandSupported(String command)
/*      */   {
/* 1007 */     return queryCommandSupportedImpl(getPeer(), command);
/*      */   }
/*      */ 
/*      */   static native boolean queryCommandSupportedImpl(long paramLong, String paramString);
/*      */ 
/*      */   public String queryCommandValue(String command)
/*      */   {
/* 1016 */     return queryCommandValueImpl(getPeer(), command);
/*      */   }
/*      */ 
/*      */   static native String queryCommandValueImpl(long paramLong, String paramString);
/*      */ 
/*      */   public NodeList getElementsByName(String elementName)
/*      */   {
/* 1025 */     return NodeListImpl.getImpl(getElementsByNameImpl(getPeer(), elementName), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long getElementsByNameImpl(long paramLong, String paramString);
/*      */ 
/*      */   public Element elementFromPoint(int x, int y)
/*      */   {
/* 1035 */     return ElementImpl.getImpl(elementFromPointImpl(getPeer(), x, y), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long elementFromPointImpl(long paramLong, int paramInt1, int paramInt2);
/*      */ 
/*      */   public Range caretRangeFromPoint(int x, int y)
/*      */   {
/* 1047 */     return RangeImpl.getImpl(caretRangeFromPointImpl(getPeer(), x, y), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long caretRangeFromPointImpl(long paramLong, int paramInt1, int paramInt2);
/*      */ 
/*      */   public CSSStyleDeclaration createCSSStyleDeclaration()
/*      */   {
/* 1058 */     return CSSStyleDeclarationImpl.getImpl(createCSSStyleDeclarationImpl(getPeer()), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createCSSStyleDeclarationImpl(long paramLong);
/*      */ 
/*      */   public NodeList getElementsByClassName(String tagname)
/*      */   {
/* 1065 */     return NodeListImpl.getImpl(getElementsByClassNameImpl(getPeer(), tagname), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long getElementsByClassNameImpl(long paramLong, String paramString);
/*      */ 
/*      */   public Element querySelector(String selectors)
/*      */     throws DOMException
/*      */   {
/* 1074 */     return ElementImpl.getImpl(querySelectorImpl(getPeer(), selectors), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long querySelectorImpl(long paramLong, String paramString);
/*      */ 
/*      */   public NodeList querySelectorAll(String selectors)
/*      */     throws DOMException
/*      */   {
/* 1083 */     return NodeListImpl.getImpl(querySelectorAllImpl(getPeer(), selectors), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long querySelectorAllImpl(long paramLong, String paramString);
/*      */ 
/*      */   public TouchImpl createTouch(AbstractView window, EventTarget target, int identifier, int pageX, int pageY, int screenX, int screenY, int webkitRadiusX, int webkitRadiusY, float webkitRotationAngle, float webkitForce)
/*      */     throws DOMException
/*      */   {
/* 1102 */     return TouchImpl.getImpl(createTouchImpl(getPeer(), DOMWindowImpl.getPeer(window), NodeImpl.getPeer((NodeImpl)target), identifier, pageX, pageY, screenX, screenY, webkitRadiusX, webkitRadiusY, webkitRotationAngle, webkitForce), this.contextPeer, this.rootPeer);
/*      */   }
/*      */ 
/*      */   static native long createTouchImpl(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat1, float paramFloat2);
/*      */ 
/*      */   public boolean getStrictErrorChecking()
/*      */   {
/* 1132 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */   public void setStrictErrorChecking(boolean strictErrorChecking) {
/* 1135 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */   public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
/* 1138 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */   public DOMConfiguration getDomConfig() {
/* 1141 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */   public void normalizeDocument() {
/* 1144 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.DocumentImpl
 * JD-Core Version:    0.6.2
 */