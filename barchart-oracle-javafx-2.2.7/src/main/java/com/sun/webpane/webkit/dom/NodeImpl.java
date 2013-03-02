/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer.WeakDisposerRecord;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.UserDataHandler;
/*     */ import org.w3c.dom.events.Event;
/*     */ import org.w3c.dom.events.EventException;
/*     */ import org.w3c.dom.events.EventListener;
/*     */ import org.w3c.dom.events.EventTarget;
/*     */ 
/*     */ public class NodeImpl extends JSObject
/*     */   implements Node, EventTarget
/*     */ {
/*  25 */   private static SelfDisposer[] hashTable = new SelfDisposer[64];
/*     */   private static int hashCount;
/*     */   protected final long peer;
/*     */   public static final int ELEMENT_NODE = 1;
/*     */   public static final int ATTRIBUTE_NODE = 2;
/*     */   public static final int TEXT_NODE = 3;
/*     */   public static final int CDATA_SECTION_NODE = 4;
/*     */   public static final int ENTITY_REFERENCE_NODE = 5;
/*     */   public static final int ENTITY_NODE = 6;
/*     */   public static final int PROCESSING_INSTRUCTION_NODE = 7;
/*     */   public static final int COMMENT_NODE = 8;
/*     */   public static final int DOCUMENT_NODE = 9;
/*     */   public static final int DOCUMENT_TYPE_NODE = 10;
/*     */   public static final int DOCUMENT_FRAGMENT_NODE = 11;
/*     */   public static final int NOTATION_NODE = 12;
/*     */   public static final int DOCUMENT_POSITION_DISCONNECTED = 1;
/*     */   public static final int DOCUMENT_POSITION_PRECEDING = 2;
/*     */   public static final int DOCUMENT_POSITION_FOLLOWING = 4;
/*     */   public static final int DOCUMENT_POSITION_CONTAINS = 8;
/*     */   public static final int DOCUMENT_POSITION_CONTAINED_BY = 16;
/*     */   public static final int DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;
/*     */ 
/*     */   private static int hashPeer(long peer)
/*     */   {
/*  29 */     return (int)(peer ^ 0xFFFFFFFF ^ peer >> 7) & hashTable.length - 1;
/*     */   }
/*     */ 
/*     */   static Node getImpl(long peer, long contextPeer, long rootPeer, long jsPeer) {
/*  33 */     if (peer == 0L)
/*  34 */       return null;
/*  35 */     int hash = hashPeer(peer);
/*  36 */     SelfDisposer head = hashTable[hash];
/*  37 */     SelfDisposer prev = null;
/*  38 */     for (SelfDisposer disposer = head; disposer != null; ) {
/*  39 */       SelfDisposer next = disposer.next;
/*  40 */       if (disposer.peer == peer) {
/*  41 */         NodeImpl node = (NodeImpl)disposer.get();
/*  42 */         if (node != null)
/*     */         {
/*  44 */           dispose(peer);
/*  45 */           return node;
/*     */         }
/*  47 */         if (prev != null) {
/*  48 */           prev.next = next; break;
/*     */         }
/*  50 */         hashTable[hash] = next;
/*  51 */         break;
/*     */       }
/*  53 */       prev = disposer;
/*  54 */       disposer = next;
/*     */     }
/*  56 */     NodeImpl node = (NodeImpl)createInterface(peer, contextPeer, rootPeer);
/*  57 */     SelfDisposer disposer = new SelfDisposer(node, peer);
/*  58 */     disposer.next = head;
/*  59 */     hashTable[hash] = disposer;
/*  60 */     if (3 * hashCount >= 2 * hashTable.length)
/*  61 */       rehash();
/*  62 */     hashCount += 1;
/*  63 */     node.jsPeer = jsPeer;
/*  64 */     return node;
/*     */   }
/*     */ 
/*     */   private static void rehash() {
/*  68 */     SelfDisposer[] oldTable = hashTable;
/*  69 */     int oldLength = oldTable.length;
/*  70 */     SelfDisposer[] newTable = new SelfDisposer[2 * oldLength];
/*  71 */     hashTable = newTable;
/*  72 */     int i = oldLength;
/*     */     while (true) { i--; if (i < 0) break;
/*  73 */       SelfDisposer disposer = oldTable[i];
/*  74 */       while (disposer != null) {
/*  75 */         SelfDisposer next = disposer.next;
/*  76 */         int hash = hashPeer(disposer.peer);
/*  77 */         disposer.next = newTable[hash];
/*  78 */         newTable[hash] = disposer;
/*  79 */         disposer = next;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected long getJSPeer()
/*     */   {
/* 115 */     if (this.jsPeer == 0L)
/* 116 */       this.jsPeer = getJSPeerImpl(this.peer, this.contextPeer, this.rootPeer);
/* 117 */     return this.jsPeer;
/*     */   }
/*     */   static native long getJSPeerImpl(long paramLong1, long paramLong2, long paramLong3);
/*     */ 
/*     */   NodeImpl(long peer, long contextPeer, long rootPeer) {
/* 122 */     super(0L, contextPeer, rootPeer);
/* 123 */     this.peer = peer;
/*     */   }
/*     */ 
/*     */   static Node createInterface(long peer, long contextPeer, long rootPeer) {
/* 127 */     if (peer == 0L) return null;
/* 128 */     switch (getNodeTypeImpl(peer)) {
/*     */     case 1:
/* 130 */       if (!ElementImpl.isHTMLElementImpl(peer)) {
/* 131 */         return new ElementImpl(peer, contextPeer, rootPeer);
/*     */       }
/* 133 */       String tagName = ElementImpl.getTagNameImpl(peer).toUpperCase();
/* 134 */       if ("A".equals(tagName)) return new HTMLAnchorElementImpl(peer, contextPeer, rootPeer);
/* 135 */       if ("APPLET".equals(tagName)) return new HTMLAppletElementImpl(peer, contextPeer, rootPeer);
/* 136 */       if ("AREA".equals(tagName)) return new HTMLAreaElementImpl(peer, contextPeer, rootPeer);
/* 137 */       if ("BASE".equals(tagName)) return new HTMLBaseElementImpl(peer, contextPeer, rootPeer);
/* 138 */       if ("BASEFONT".equals(tagName)) return new HTMLBaseFontElementImpl(peer, contextPeer, rootPeer);
/* 139 */       if ("BODY".equals(tagName)) return new HTMLBodyElementImpl(peer, contextPeer, rootPeer);
/* 140 */       if ("BR".equals(tagName)) return new HTMLBRElementImpl(peer, contextPeer, rootPeer);
/* 141 */       if ("BUTTON".equals(tagName)) return new HTMLButtonElementImpl(peer, contextPeer, rootPeer);
/* 142 */       if ("DIR".equals(tagName)) return new HTMLDirectoryElementImpl(peer, contextPeer, rootPeer);
/* 143 */       if ("DIV".equals(tagName)) return new HTMLDivElementImpl(peer, contextPeer, rootPeer);
/* 144 */       if ("DL".equals(tagName)) return new HTMLDListElementImpl(peer, contextPeer, rootPeer);
/* 145 */       if ("FIELDSET".equals(tagName)) return new HTMLFieldSetElementImpl(peer, contextPeer, rootPeer);
/* 146 */       if ("FONT".equals(tagName)) return new HTMLFontElementImpl(peer, contextPeer, rootPeer);
/* 147 */       if ("FORM".equals(tagName)) return new HTMLFormElementImpl(peer, contextPeer, rootPeer);
/* 148 */       if ("FRAME".equals(tagName)) return new HTMLFrameElementImpl(peer, contextPeer, rootPeer);
/* 149 */       if ("FRAMESET".equals(tagName)) return new HTMLFrameSetElementImpl(peer, contextPeer, rootPeer);
/* 150 */       if ("HEAD".equals(tagName)) return new HTMLHeadElementImpl(peer, contextPeer, rootPeer);
/* 151 */       if ((tagName.length() == 2) && (tagName.charAt(0) == 'H') && (tagName.charAt(1) >= '1') && (tagName.charAt(1) <= '6')) return new HTMLHeadingElementImpl(peer, contextPeer, rootPeer);
/* 152 */       if ("HR".equals(tagName)) return new HTMLHRElementImpl(peer, contextPeer, rootPeer);
/* 153 */       if ("IFRAME".equals(tagName)) return new HTMLIFrameElementImpl(peer, contextPeer, rootPeer);
/* 154 */       if ("IMG".equals(tagName)) return new HTMLImageElementImpl(peer, contextPeer, rootPeer);
/* 155 */       if ("INPUT".equals(tagName)) return new HTMLInputElementImpl(peer, contextPeer, rootPeer);
/* 156 */       if ("LABEL".equals(tagName)) return new HTMLLabelElementImpl(peer, contextPeer, rootPeer);
/* 157 */       if ("LEGEND".equals(tagName)) return new HTMLLegendElementImpl(peer, contextPeer, rootPeer);
/* 158 */       if ("LI".equals(tagName)) return new HTMLLIElementImpl(peer, contextPeer, rootPeer);
/* 159 */       if ("LINK".equals(tagName)) return new HTMLLinkElementImpl(peer, contextPeer, rootPeer);
/* 160 */       if ("MAP".equals(tagName)) return new HTMLMapElementImpl(peer, contextPeer, rootPeer);
/* 161 */       if ("MENU".equals(tagName)) return new HTMLMenuElementImpl(peer, contextPeer, rootPeer);
/* 162 */       if ("META".equals(tagName)) return new HTMLMetaElementImpl(peer, contextPeer, rootPeer);
/* 163 */       if (("INS".equals(tagName)) || ("DEL".equals(tagName))) return new HTMLModElementImpl(peer, contextPeer, rootPeer);
/* 164 */       if ("OBJECT".equals(tagName)) return new HTMLObjectElementImpl(peer, contextPeer, rootPeer);
/* 165 */       if ("OL".equals(tagName)) return new HTMLOListElementImpl(peer, contextPeer, rootPeer);
/* 166 */       if ("OPTGROUP".equals(tagName)) return new HTMLOptGroupElementImpl(peer, contextPeer, rootPeer);
/* 167 */       if ("OPTION".equals(tagName)) return new HTMLOptionElementImpl(peer, contextPeer, rootPeer);
/* 168 */       if ("P".equals(tagName)) return new HTMLParagraphElementImpl(peer, contextPeer, rootPeer);
/* 169 */       if ("PARAM".equals(tagName)) return new HTMLParamElementImpl(peer, contextPeer, rootPeer);
/* 170 */       if ("PRE".equals(tagName)) return new HTMLPreElementImpl(peer, contextPeer, rootPeer);
/* 171 */       if ("Q".equals(tagName)) return new HTMLQuoteElementImpl(peer, contextPeer, rootPeer);
/* 172 */       if ("SCRIPT".equals(tagName)) return new HTMLScriptElementImpl(peer, contextPeer, rootPeer);
/* 173 */       if ("SELECT".equals(tagName)) return new HTMLSelectElementImpl(peer, contextPeer, rootPeer);
/* 174 */       if ("STYLE".equals(tagName)) return new HTMLStyleElementImpl(peer, contextPeer, rootPeer);
/* 175 */       if ("CAPTION".equals(tagName)) return new HTMLTableCaptionElementImpl(peer, contextPeer, rootPeer);
/* 176 */       if ("TD".equals(tagName)) return new HTMLTableCellElementImpl(peer, contextPeer, rootPeer);
/* 177 */       if ("COL".equals(tagName)) return new HTMLTableColElementImpl(peer, contextPeer, rootPeer);
/* 178 */       if ("TABLE".equals(tagName)) return new HTMLTableElementImpl(peer, contextPeer, rootPeer);
/* 179 */       if ("TR".equals(tagName)) return new HTMLTableRowElementImpl(peer, contextPeer, rootPeer);
/* 180 */       if (("THEAD".equals(tagName)) || ("TFOOT".equals(tagName)) || ("TBODY".equals(tagName))) return new HTMLTableSectionElementImpl(peer, contextPeer, rootPeer);
/* 181 */       if ("TEXTAREA".equals(tagName)) return new HTMLTextAreaElementImpl(peer, contextPeer, rootPeer);
/* 182 */       if ("TITLE".equals(tagName)) return new HTMLTitleElementImpl(peer, contextPeer, rootPeer);
/* 183 */       if ("UL".equals(tagName)) return new HTMLUListElementImpl(peer, contextPeer, rootPeer);
/*     */ 
/* 185 */       return new HTMLElementImpl(peer, contextPeer, rootPeer);
/*     */     case 2:
/* 186 */       return new AttrImpl(peer, contextPeer, rootPeer);
/*     */     case 3:
/* 187 */       return new TextImpl(peer, contextPeer, rootPeer);
/*     */     case 4:
/* 188 */       return new CDATASectionImpl(peer, contextPeer, rootPeer);
/*     */     case 5:
/* 189 */       return new EntityReferenceImpl(peer, contextPeer, rootPeer);
/*     */     case 6:
/* 190 */       return new EntityImpl(peer, contextPeer, rootPeer);
/*     */     case 7:
/* 191 */       return new ProcessingInstructionImpl(peer, contextPeer, rootPeer);
/*     */     case 8:
/* 192 */       return new CommentImpl(peer, contextPeer, rootPeer);
/*     */     case 9:
/* 194 */       if (DocumentImpl.isHTMLDocumentImpl(peer))
/* 195 */         return new HTMLDocumentImpl(peer, contextPeer, rootPeer);
/* 196 */       return new DocumentImpl(peer, contextPeer, rootPeer);
/*     */     case 10:
/* 197 */       return new DocumentTypeImpl(peer, contextPeer, rootPeer);
/*     */     case 11:
/* 198 */       return new DocumentFragmentImpl(peer, contextPeer, rootPeer);
/*     */     case 12:
/* 199 */       return new NotationImpl(peer, contextPeer, rootPeer);
/*     */     }
/* 201 */     return new NodeImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static Node create(long peer, long contextPeer, long rootPeer) {
/* 205 */     return getImpl(peer, contextPeer, rootPeer, 0L);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/* 211 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(Node arg) {
/* 215 */     return arg == null ? 0L : ((NodeImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/* 219 */     return ((that instanceof NodeImpl)) && (this.peer == ((NodeImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 223 */     long p = this.peer;
/* 224 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static Node getImpl(long peer, long contextPeer, long rootPeer) {
/* 230 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/* 256 */     return getNodeNameImpl(getPeer());
/*     */   }
/*     */   static native String getNodeNameImpl(long paramLong);
/*     */ 
/*     */   public String getNodeValue() {
/* 261 */     return getNodeValueImpl(getPeer());
/*     */   }
/*     */   static native String getNodeValueImpl(long paramLong);
/*     */ 
/*     */   public void setNodeValue(String value) throws DOMException {
/* 266 */     setNodeValueImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNodeValueImpl(long paramLong, String paramString);
/*     */ 
/*     */   public short getNodeType() {
/* 271 */     return getNodeTypeImpl(getPeer());
/*     */   }
/*     */   static native short getNodeTypeImpl(long paramLong);
/*     */ 
/*     */   public Node getParentNode() {
/* 276 */     return getImpl(getParentNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getParentNodeImpl(long paramLong);
/*     */ 
/*     */   public NodeList getChildNodes() {
/* 281 */     return NodeListImpl.getImpl(getChildNodesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getChildNodesImpl(long paramLong);
/*     */ 
/*     */   public Node getFirstChild() {
/* 286 */     return getImpl(getFirstChildImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getFirstChildImpl(long paramLong);
/*     */ 
/*     */   public Node getLastChild() {
/* 291 */     return getImpl(getLastChildImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getLastChildImpl(long paramLong);
/*     */ 
/*     */   public Node getPreviousSibling() {
/* 296 */     return getImpl(getPreviousSiblingImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getPreviousSiblingImpl(long paramLong);
/*     */ 
/*     */   public Node getNextSibling() {
/* 301 */     return getImpl(getNextSiblingImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getNextSiblingImpl(long paramLong);
/*     */ 
/*     */   public NamedNodeMap getAttributes() {
/* 306 */     return NamedNodeMapImpl.getImpl(getAttributesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getAttributesImpl(long paramLong);
/*     */ 
/*     */   public Document getOwnerDocument() {
/* 311 */     return DocumentImpl.getImpl(getOwnerDocumentImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOwnerDocumentImpl(long paramLong);
/*     */ 
/*     */   public String getNamespaceURI() {
/* 316 */     return getNamespaceURIImpl(getPeer());
/*     */   }
/*     */   static native String getNamespaceURIImpl(long paramLong);
/*     */ 
/*     */   public String getPrefix() {
/* 321 */     return getPrefixImpl(getPeer());
/*     */   }
/*     */   static native String getPrefixImpl(long paramLong);
/*     */ 
/*     */   public void setPrefix(String value) throws DOMException {
/* 326 */     setPrefixImpl(getPeer(), value);
/*     */   }
/*     */   static native void setPrefixImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getLocalName() {
/* 331 */     return getLocalNameImpl(getPeer());
/*     */   }
/*     */   static native String getLocalNameImpl(long paramLong);
/*     */ 
/*     */   public String getBaseURI() {
/* 336 */     return getBaseURIImpl(getPeer());
/*     */   }
/*     */   static native String getBaseURIImpl(long paramLong);
/*     */ 
/*     */   public String getTextContent() {
/* 341 */     return getTextContentImpl(getPeer());
/*     */   }
/*     */   static native String getTextContentImpl(long paramLong);
/*     */ 
/*     */   public void setTextContent(String value) throws DOMException {
/* 346 */     setTextContentImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTextContentImpl(long paramLong, String paramString);
/*     */ 
/*     */   public Element getParentElement() {
/* 351 */     return ElementImpl.getImpl(getParentElementImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getParentElementImpl(long paramLong);
/*     */ 
/*     */   public Node insertBefore(Node newChild, Node refChild)
/*     */     throws DOMException
/*     */   {
/* 360 */     return getImpl(insertBeforeImpl(getPeer(), getPeer(newChild), getPeer(refChild)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long insertBeforeImpl(long paramLong1, long paramLong2, long paramLong3);
/*     */ 
/*     */   public Node replaceChild(Node newChild, Node oldChild)
/*     */     throws DOMException
/*     */   {
/* 372 */     return getImpl(replaceChildImpl(getPeer(), getPeer(newChild), getPeer(oldChild)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long replaceChildImpl(long paramLong1, long paramLong2, long paramLong3);
/*     */ 
/*     */   public Node removeChild(Node oldChild)
/*     */     throws DOMException
/*     */   {
/* 383 */     return getImpl(removeChildImpl(getPeer(), getPeer(oldChild)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long removeChildImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public Node appendChild(Node newChild)
/*     */     throws DOMException
/*     */   {
/* 392 */     return getImpl(appendChildImpl(getPeer(), getPeer(newChild)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long appendChildImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public boolean hasChildNodes()
/*     */   {
/* 401 */     return hasChildNodesImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean hasChildNodesImpl(long paramLong);
/*     */ 
/*     */   public Node cloneNode(boolean deep)
/*     */   {
/* 408 */     return getImpl(cloneNodeImpl(getPeer(), deep), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long cloneNodeImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public void normalize()
/*     */   {
/* 417 */     normalizeImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void normalizeImpl(long paramLong);
/*     */ 
/*     */   public boolean isSupported(String feature, String version)
/*     */   {
/* 425 */     return isSupportedImpl(getPeer(), feature, version);
/*     */   }
/*     */ 
/*     */   static native boolean isSupportedImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public boolean hasAttributes()
/*     */   {
/* 436 */     return hasAttributesImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean hasAttributesImpl(long paramLong);
/*     */ 
/*     */   public boolean isSameNode(Node other)
/*     */   {
/* 443 */     return isSameNodeImpl(getPeer(), getPeer(other));
/*     */   }
/*     */ 
/*     */   static native boolean isSameNodeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public boolean isEqualNode(Node other)
/*     */   {
/* 452 */     return isEqualNodeImpl(getPeer(), getPeer(other));
/*     */   }
/*     */ 
/*     */   static native boolean isEqualNodeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public String lookupPrefix(String namespaceURI)
/*     */   {
/* 461 */     return lookupPrefixImpl(getPeer(), namespaceURI);
/*     */   }
/*     */ 
/*     */   static native String lookupPrefixImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean isDefaultNamespace(String namespaceURI)
/*     */   {
/* 470 */     return isDefaultNamespaceImpl(getPeer(), namespaceURI);
/*     */   }
/*     */ 
/*     */   static native boolean isDefaultNamespaceImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String lookupNamespaceURI(String prefix)
/*     */   {
/* 479 */     return lookupNamespaceURIImpl(getPeer(), prefix);
/*     */   }
/*     */ 
/*     */   static native String lookupNamespaceURIImpl(long paramLong, String paramString);
/*     */ 
/*     */   public short compareDocumentPosition(Node other)
/*     */   {
/* 488 */     return compareDocumentPositionImpl(getPeer(), getPeer(other));
/*     */   }
/*     */ 
/*     */   static native short compareDocumentPositionImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public boolean contains(Node other)
/*     */   {
/* 497 */     return containsImpl(getPeer(), getPeer(other));
/*     */   }
/*     */ 
/*     */   static native boolean containsImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public void addEventListener(String type, EventListener listener, boolean useCapture)
/*     */   {
/* 508 */     addEventListenerImpl(getPeer(), type, EventListenerImpl.getPeer(listener, this.contextPeer, this.rootPeer), useCapture);
/*     */   }
/*     */ 
/*     */   static native void addEventListenerImpl(long paramLong1, String paramString, long paramLong2, boolean paramBoolean);
/*     */ 
/*     */   public void removeEventListener(String type, EventListener listener, boolean useCapture)
/*     */   {
/* 523 */     removeEventListenerImpl(getPeer(), type, EventListenerImpl.getPeer(listener, this.contextPeer, this.rootPeer), useCapture);
/*     */   }
/*     */ 
/*     */   static native void removeEventListenerImpl(long paramLong1, String paramString, long paramLong2, boolean paramBoolean);
/*     */ 
/*     */   public boolean dispatchEvent(Event event)
/*     */     throws EventException
/*     */   {
/* 536 */     return dispatchEventImpl(getPeer(), EventImpl.getPeer(event));
/*     */   }
/*     */ 
/*     */   static native boolean dispatchEventImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public Object getUserData(String key)
/*     */   {
/* 546 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   public Object setUserData(String key, Object data, UserDataHandler handler) {
/* 549 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   public Object getFeature(String feature, String version) {
/* 552 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   static class SelfDisposer extends Disposer.WeakDisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */     SelfDisposer next;
/*     */ 
/*     */     SelfDisposer(Object referent, long _peer)
/*     */     {
/*  88 */       super();
/*  89 */       this.peer = _peer;
/*     */     }
/*     */ 
/*     */     public void dispose() {
/*  93 */       int hash = NodeImpl.hashPeer(this.peer);
/*  94 */       SelfDisposer head = NodeImpl.hashTable[hash];
/*  95 */       SelfDisposer prev = null;
/*  96 */       for (SelfDisposer disposer = head; disposer != null; ) {
/*  97 */         SelfDisposer next = disposer.next;
/*  98 */         if (disposer.peer == this.peer) {
/*  99 */           disposer.clear();
/* 100 */           if (prev != null)
/* 101 */             prev.next = next;
/*     */           else
/* 103 */             NodeImpl.hashTable[hash] = next;
/* 104 */           NodeImpl.access$310();
/* 105 */           break;
/*     */         }
/* 107 */         prev = disposer;
/* 108 */         disposer = next;
/*     */       }
/* 110 */       NodeImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.NodeImpl
 * JD-Core Version:    0.6.2
 */