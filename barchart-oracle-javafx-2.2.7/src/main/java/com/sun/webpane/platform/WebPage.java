/*      */ package com.sun.webpane.platform;
/*      */ 
/*      */ import com.sun.javafx.runtime.NativeLibLoader;
/*      */ import com.sun.webpane.platform.event.WCFocusEvent;
/*      */ import com.sun.webpane.platform.event.WCInputMethodEvent;
/*      */ import com.sun.webpane.platform.event.WCKeyEvent;
/*      */ import com.sun.webpane.platform.event.WCMouseEvent;
/*      */ import com.sun.webpane.platform.event.WCMouseWheelEvent;
/*      */ import com.sun.webpane.platform.event.WCTouchEvent;
/*      */ import com.sun.webpane.platform.graphics.RenderTheme;
/*      */ import com.sun.webpane.platform.graphics.ScrollBarTheme;
/*      */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*      */ import com.sun.webpane.platform.graphics.WCImage;
/*      */ import com.sun.webpane.platform.graphics.WCPageBackBuffer;
/*      */ import com.sun.webpane.platform.graphics.WCPoint;
/*      */ import com.sun.webpane.platform.graphics.WCRectangle;
/*      */ import com.sun.webpane.platform.graphics.WCRenderQueue;
/*      */ import com.sun.webpane.platform.graphics.WCSize;
/*      */ import com.sun.webpane.webkit.WCFrameView;
/*      */ import com.sun.webpane.webkit.WCWidget;
/*      */ import com.sun.webpane.webkit.network.CookieManager;
/*      */ import com.sun.webpane.webkit.network.URLs;
/*      */ import java.net.CookieHandler;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import netscape.javascript.JSException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ 
/*      */ public class WebPage
/*      */ {
/*   65 */   private static final Logger log = Logger.getLogger(WebPage.class.getName());
/*   66 */   private static final Logger paintLog = Logger.getLogger(new StringBuilder().append(WebPage.class.getName()).append(".paint").toString());
/*   67 */   private static final Logger paintDirtyLog = Logger.getLogger(new StringBuilder().append(WebPage.class.getName()).append(".paint.dirty").toString());
/*      */ 
/*   70 */   private long pPage = 0L;
/*      */ 
/*   74 */   private boolean isDisposed = false;
/*      */   private int width;
/*      */   private int height;
/*      */   private int fontSmoothingType;
/*      */   private WCFrameView hostWindow;
/*   83 */   private Set<Long> frames = new HashSet();
/*      */   private final AccessControlContext accessControlContext;
/*   89 */   private Map<Integer, String> requestURLs = new HashMap();
/*      */ 
/*   93 */   private Set<Integer> requestStarted = new HashSet();
/*      */ 
/*   99 */   public static final ReentrantLock PAGE_LOCK = new ReentrantLock();
/*      */ 
/*  107 */   private List<WCRenderQueue> rqList = new LinkedList();
/*      */ 
/*  114 */   private int rqSize = 0;
/*      */   private WCPageBackBuffer backbuffer;
/*  247 */   private List<WCRectangle> dirtyRects = new LinkedList();
/*  248 */   private List<ScrollRequest> scrollRequests = new LinkedList();
/*      */   private WebPageClient pageClient;
/*      */   private UIClient uiClient;
/*      */   private PolicyClient policyClient;
/*      */   private InputMethodClient imClient;
/*  419 */   private List<LoadListenerClient> loadListenerClients = new LinkedList();
/*      */   private InspectorClient inspectorClient;
/*      */   private ThemeClient themeClient;
/*      */   private RenderTheme renderTheme;
/*      */   private ScrollBarTheme scrollbarTheme;
/*      */   public static final int DND_DST_ENTER = 0;
/*      */   public static final int DND_DST_OVER = 1;
/*      */   public static final int DND_DST_CHANGE = 2;
/*      */   public static final int DND_DST_EXIT = 3;
/*      */   public static final int DND_DST_DROP = 4;
/*      */   public static final int DND_SRC_ENTER = 100;
/*      */   public static final int DND_SRC_OVER = 101;
/*      */   public static final int DND_SRC_CHANGE = 102;
/*      */   public static final int DND_SRC_EXIT = 103;
/*      */   public static final int DND_SRC_DROP = 104;
/*      */ 
/*      */   public WebPage(WebPageClient paramWebPageClient, UIClient paramUIClient, PolicyClient paramPolicyClient, ThemeClient paramThemeClient, boolean paramBoolean)
/*      */   {
/*  189 */     Invoker.getInvoker().checkEventThread();
/*      */ 
/*  191 */     setPageClient(paramWebPageClient);
/*  192 */     setUIClient(paramUIClient);
/*  193 */     setPolicyClient(paramPolicyClient);
/*  194 */     setThemeClient(paramThemeClient);
/*      */ 
/*  196 */     this.accessControlContext = AccessController.getContext();
/*      */ 
/*  198 */     this.hostWindow = new WCFrameView(this);
/*  199 */     this.pPage = twkCreatePage(paramBoolean);
/*      */ 
/*  201 */     twkInit(this.pPage, ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Boolean run()
/*      */       {
/*  205 */         return Boolean.valueOf(Boolean.getBoolean("com.sun.swing.webpane.usePlugins"));
/*      */       }
/*      */     })).booleanValue());
/*      */ 
/*  210 */     if ((paramWebPageClient != null) && (paramWebPageClient.isBackBufferSupported())) {
/*  211 */       this.backbuffer = paramWebPageClient.createBackBuffer();
/*  212 */       this.backbuffer.ref();
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getPage() {
/*  217 */     return this.pPage;
/*      */   }
/*      */ 
/*      */   public WCWidget getHostWindow()
/*      */   {
/*  222 */     return this.hostWindow;
/*      */   }
/*      */ 
/*      */   public AccessControlContext getAccessControlContext()
/*      */   {
/*  231 */     return this.accessControlContext;
/*      */   }
/*      */ 
/*      */   public static boolean lockPage() {
/*  235 */     return Invoker.getInvoker().lock(PAGE_LOCK);
/*      */   }
/*      */ 
/*      */   public static boolean unlockPage() {
/*  239 */     return Invoker.getInvoker().unlock(PAGE_LOCK);
/*      */   }
/*      */ 
/*      */   private boolean addLastRenderQueue(WCRenderQueue paramWCRenderQueue)
/*      */   {
/*  251 */     this.rqSize += paramWCRenderQueue.getSize();
/*  252 */     this.rqList.add(paramWCRenderQueue);
/*      */ 
/*  255 */     if (this.rqSize > 524288) {
/*  256 */       log.log(Level.FINE, "RQ overflow {0}: Drop incremental update.", Integer.valueOf(this.rqSize));
/*      */ 
/*  258 */       clearRenderQueues();
/*      */ 
/*  262 */       markDirty();
/*      */ 
/*  264 */       return false;
/*      */     }
/*  266 */     return true;
/*      */   }
/*      */ 
/*      */   private WCRenderQueue removeFirstRenderQueue() {
/*  270 */     if (this.rqList.isEmpty()) {
/*  271 */       return null;
/*      */     }
/*  273 */     WCRenderQueue localWCRenderQueue = (WCRenderQueue)this.rqList.remove(0);
/*  274 */     this.rqSize -= localWCRenderQueue.getSize();
/*  275 */     return localWCRenderQueue;
/*      */   }
/*      */ 
/*      */   private void markDirty()
/*      */   {
/*  291 */     this.dirtyRects.clear();
/*  292 */     addDirtyRect(new WCRectangle(0.0F, 0.0F, this.width, this.height));
/*      */   }
/*      */ 
/*      */   private void addDirtyRect(WCRectangle paramWCRectangle) {
/*  296 */     for (Iterator localIterator = this.dirtyRects.iterator(); localIterator.hasNext(); ) {
/*  297 */       WCRectangle localWCRectangle1 = (WCRectangle)localIterator.next();
/*      */ 
/*  299 */       if (localWCRectangle1.contains(paramWCRectangle)) {
/*  300 */         return;
/*      */       }
/*      */ 
/*  303 */       if (paramWCRectangle.contains(localWCRectangle1)) {
/*  304 */         localIterator.remove();
/*      */       }
/*      */       else {
/*  307 */         WCRectangle localWCRectangle2 = localWCRectangle1.createUnion(paramWCRectangle);
/*      */ 
/*  309 */         if (localWCRectangle2.getIntWidth() * localWCRectangle2.getIntHeight() < localWCRectangle1.getIntWidth() * localWCRectangle1.getIntHeight() + paramWCRectangle.getIntWidth() * paramWCRectangle.getIntHeight())
/*      */         {
/*  313 */           localIterator.remove();
/*  314 */           paramWCRectangle = localWCRectangle2;
/*      */         }
/*      */       }
/*      */     }
/*  318 */     this.dirtyRects.add(paramWCRectangle);
/*      */   }
/*      */ 
/*      */   public boolean isDirty() {
/*  322 */     lockPage();
/*      */     try {
/*  324 */       return (!this.dirtyRects.isEmpty()) || (!this.scrollRequests.isEmpty());
/*      */     } finally {
/*  326 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateDirty(WCRectangle paramWCRectangle) {
/*  331 */     if ((this.isDisposed) || (this.width <= 0) || (this.height <= 0))
/*      */     {
/*  335 */       this.dirtyRects.clear();
/*  336 */       return;
/*      */     }
/*  338 */     if (paramWCRectangle == null) {
/*  339 */       paramWCRectangle = new WCRectangle(0.0F, 0.0F, this.width, this.height);
/*      */     }
/*  341 */     List localList = this.dirtyRects;
/*  342 */     this.dirtyRects = new LinkedList();
/*  343 */     while (!localList.isEmpty()) {
/*  344 */       localObject = ((WCRectangle)localList.remove(0)).intersection(paramWCRectangle);
/*  345 */       if ((((WCRectangle)localObject).getWidth() > 0.0F) && (((WCRectangle)localObject).getHeight() > 0.0F))
/*      */       {
/*  348 */         if (log.isLoggable(Level.FINEST)) {
/*  349 */           paintLog.finest(new StringBuilder().append("updating: ").append(localObject).toString());
/*      */         }
/*  351 */         WCRenderQueue localWCRenderQueue = new WCRenderQueue((WCRectangle)localObject);
/*  352 */         twkUpdateContent(getPage(), localWCRenderQueue, ((WCRectangle)localObject).getIntX() - 1, ((WCRectangle)localObject).getIntY() - 1, ((WCRectangle)localObject).getIntWidth() + 2, ((WCRectangle)localObject).getIntHeight() + 2);
/*      */ 
/*  354 */         if (!addLastRenderQueue(localWCRenderQueue))
/*      */         {
/*  357 */           return;
/*      */         }
/*      */       }
/*      */     }
/*  361 */     Object localObject = new WCRenderQueue(paramWCRectangle);
/*  362 */     twkDrawHighlight(getPage(), (WCRenderQueue)localObject);
/*  363 */     addLastRenderQueue((WCRenderQueue)localObject);
/*      */   }
/*      */ 
/*      */   private void updateScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  367 */     if (paintLog.isLoggable(Level.FINEST))
/*  368 */       paintLog.finest(new StringBuilder().append("rect=[").append(paramInt1).append(", ").append(paramInt2).append(" ").append(paramInt3).append("x").append(paramInt4).append("] delta=[").append(paramInt5).append(", ").append(paramInt6).append("]").toString());
/*      */     WCRectangle localWCRectangle1;
/*  371 */     if ((Math.abs(paramInt5) < paramInt3) && (Math.abs(paramInt6) < paramInt4)) {
/*  372 */       int i = paramInt5 >= 0 ? paramInt1 : paramInt1 - paramInt5;
/*  373 */       int j = paramInt6 >= 0 ? paramInt2 : paramInt2 - paramInt6;
/*  374 */       int k = paramInt5 == 0 ? paramInt3 : paramInt3 - Math.abs(paramInt5);
/*  375 */       int m = paramInt6 == 0 ? paramInt4 : paramInt4 - Math.abs(paramInt6);
/*      */ 
/*  377 */       WCRenderQueue localWCRenderQueue = new WCRenderQueue((WCRectangle)null);
/*  378 */       localWCRenderQueue.addBuffer(ByteBuffer.allocate(32).putInt(40).putInt(this.backbuffer.getID()).putInt(i).putInt(j).putInt(k).putInt(m).putInt(paramInt5).putInt(paramInt6));
/*      */ 
/*  381 */       if (!addLastRenderQueue(localWCRenderQueue))
/*      */       {
/*  384 */         return;
/*      */       }
/*      */ 
/*  388 */       if (!this.dirtyRects.isEmpty()) {
/*  389 */         localWCRectangle1 = new WCRectangle(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */ 
/*  391 */         for (WCRectangle localWCRectangle2 : this.dirtyRects) {
/*  392 */           if (localWCRectangle1.contains(localWCRectangle2)) {
/*  393 */             if (paintLog.isLoggable(Level.FINEST)) {
/*  394 */               paintLog.log(Level.FINEST, new StringBuilder().append("translating old dirty rect by the delta: ").append(localWCRectangle2).toString());
/*      */             }
/*  396 */             localWCRectangle2.translate(paramInt5, paramInt6);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  403 */     addDirtyRect(new WCRectangle(paramInt5 >= 0 ? paramInt1 : paramInt1 + paramInt3 + paramInt5 - 1, paramInt6 >= 0 ? paramInt2 : paramInt2 + paramInt4 + paramInt6 - 1, (paramInt5 == 0 ? paramInt3 : Math.abs(paramInt5)) + 1, (paramInt6 == 0 ? paramInt4 : Math.abs(paramInt6)) + 1));
/*      */   }
/*      */ 
/*      */   public void setPageClient(WebPageClient paramWebPageClient)
/*      */   {
/*  429 */     this.pageClient = paramWebPageClient;
/*      */   }
/*      */   public WebPageClient getPageClient() {
/*  432 */     return this.pageClient;
/*      */   }
/*      */ 
/*      */   public void setUIClient(UIClient paramUIClient) {
/*  436 */     this.uiClient = paramUIClient;
/*      */   }
/*      */ 
/*      */   public void setPolicyClient(PolicyClient paramPolicyClient) {
/*  440 */     this.policyClient = paramPolicyClient;
/*      */   }
/*      */ 
/*      */   public void setInputMethodClient(InputMethodClient paramInputMethodClient) {
/*  444 */     this.imClient = paramInputMethodClient;
/*      */   }
/*      */ 
/*      */   public InputMethodClient getInputMethodClient() {
/*  448 */     return this.imClient;
/*      */   }
/*      */ 
/*      */   public void setInputMethodState(boolean paramBoolean) {
/*  452 */     InputMethodClient localInputMethodClient = getInputMethodClient();
/*  453 */     if (localInputMethodClient != null)
/*      */     {
/*  459 */       localInputMethodClient.activateInputMethods(paramBoolean);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addLoadListenerClient(LoadListenerClient paramLoadListenerClient) {
/*  464 */     if (!this.loadListenerClients.contains(paramLoadListenerClient))
/*  465 */       this.loadListenerClients.add(paramLoadListenerClient);
/*      */   }
/*      */ 
/*      */   public void removeLoadListenerClient(LoadListenerClient paramLoadListenerClient) {
/*  469 */     this.loadListenerClients.remove(paramLoadListenerClient);
/*      */   }
/*      */ 
/*      */   public void setThemeClient(ThemeClient paramThemeClient) {
/*  473 */     this.themeClient = paramThemeClient;
/*  474 */     if (this.themeClient != null) {
/*  475 */       this.renderTheme = this.themeClient.createRenderTheme();
/*  476 */       this.scrollbarTheme = this.themeClient.createScrollBarTheme();
/*      */     }
/*      */   }
/*      */ 
/*      */   public RenderTheme getRenderTheme() {
/*  481 */     return this.renderTheme;
/*      */   }
/*      */ 
/*      */   private static RenderTheme getDefaultRenderTheme()
/*      */   {
/*  486 */     return ThemeClient.getDefaultRenderTheme();
/*      */   }
/*      */ 
/*      */   public ScrollBarTheme getScrollBarTheme() {
/*  490 */     return this.scrollbarTheme;
/*      */   }
/*      */ 
/*      */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*  498 */     lockPage();
/*      */     try {
/*  500 */       log.log(Level.FINE, new StringBuilder().append("setBounds: ").append(paramInt1).append(" ").append(paramInt2).append(" ").append(paramInt3).append(" ").append(paramInt4).toString());
/*  501 */       if (this.isDisposed) {
/*  502 */         log.log(Level.FINE, "setBounds() request for a disposed web page.");
/*      */       }
/*      */       else {
/*  505 */         this.width = paramInt3;
/*  506 */         this.height = paramInt4;
/*  507 */         twkSetBounds(getPage(), 0, 0, paramInt3, paramInt4);
/*      */ 
/*  525 */         repaintAll();
/*      */       }
/*      */     } finally {
/*  528 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setOpaque(long paramLong, boolean paramBoolean) {
/*  533 */     lockPage();
/*      */     try {
/*  535 */       log.log(Level.FINE, new StringBuilder().append("setOpaque: ").append(paramBoolean).toString());
/*  536 */       if (this.isDisposed) {
/*  537 */         log.log(Level.FINE, "setOpaque() request for a disposed web page.");
/*      */       }
/*      */       else {
/*  540 */         if (!this.frames.contains(Long.valueOf(paramLong))) {
/*      */           return;
/*      */         }
/*  543 */         twkSetTransparent(paramLong, !paramBoolean);
/*      */       }
/*      */     } finally {
/*  546 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setBackgroundColor(long paramLong, int paramInt) {
/*  551 */     lockPage();
/*      */     try {
/*  553 */       log.log(Level.FINE, new StringBuilder().append("setBackgroundColor: ").append(paramInt).toString());
/*  554 */       if (this.isDisposed) {
/*  555 */         log.log(Level.FINE, "setBackgroundColor() request for a disposed web page.");
/*      */       }
/*      */       else {
/*  558 */         if (!this.frames.contains(Long.valueOf(paramLong))) {
/*      */           return;
/*      */         }
/*  561 */         twkSetBackgroundColor(paramLong, paramInt);
/*      */       }
/*      */     } finally {
/*  564 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setBackgroundColor(int paramInt) {
/*  569 */     lockPage();
/*      */     try {
/*  571 */       log.log(Level.FINE, new StringBuilder().append("setBackgroundColor: ").append(paramInt).append(" for all frames").toString());
/*      */ 
/*  573 */       if (this.isDisposed) {
/*  574 */         log.log(Level.FINE, "setBackgroundColor() request for a disposed web page.");
/*      */       }
/*      */       else
/*      */       {
/*  578 */         for (localIterator = this.frames.iterator(); localIterator.hasNext(); ) { long l = ((Long)localIterator.next()).longValue();
/*  579 */           twkSetBackgroundColor(l, paramInt);
/*      */         }
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */       Iterator localIterator;
/*  583 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateContent(WCRectangle paramWCRectangle)
/*      */   {
/*  591 */     lockPage();
/*      */     try {
/*  593 */       if (log.isLoggable(Level.FINEST)) {
/*  594 */         paintLog.finest(new StringBuilder().append("updateContent: ").append(paramWCRectangle.getIntX()).append(" ").append(paramWCRectangle.getIntY()).append(" ").append(paramWCRectangle.getIntWidth()).append(" ").append(paramWCRectangle.getIntHeight()).toString());
/*      */       }
/*      */ 
/*  597 */       if (this.isDisposed) {
/*  598 */         paintLog.fine("updateContent() request for a disposed web page.");
/*      */       }
/*      */       else {
/*  601 */         while (!this.scrollRequests.isEmpty()) {
/*  602 */           ScrollRequest localScrollRequest = (ScrollRequest)this.scrollRequests.remove(0);
/*  603 */           updateScroll(localScrollRequest.x, localScrollRequest.y, localScrollRequest.w, localScrollRequest.h, localScrollRequest.dx, localScrollRequest.dy);
/*      */         }
/*  605 */         updateDirty(paramWCRectangle);
/*      */       }
/*      */     } finally {
/*  608 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isRepaintPending() {
/*  613 */     lockPage();
/*      */     try {
/*  615 */       return this.rqList.size() > 0;
/*      */     } finally {
/*  617 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paint(WCGraphicsContext paramWCGraphicsContext, WCRectangle paramWCRectangle)
/*      */   {
/*  625 */     lockPage();
/*      */     try {
/*  627 */       if ((this.pageClient != null) && (this.pageClient.isBackBufferSupported())) {
/*  628 */         if (!this.backbuffer.validate(this.width, this.height))
/*      */         {
/*  630 */           Invoker.getInvoker().invokeOnEventThread(new Runnable()
/*      */           {
/*      */             public void run() {
/*  633 */               WebPage.this.repaintAll();
/*      */             } } );
/*      */           return;
/*      */         }
/*  638 */         WCGraphicsContext localWCGraphicsContext = this.backbuffer.createGraphics();
/*      */         try {
/*  640 */           paint2GC(localWCGraphicsContext);
/*      */         } finally {
/*  642 */           this.backbuffer.disposeGraphics(localWCGraphicsContext);
/*      */         }
/*  644 */         this.backbuffer.flush(paramWCGraphicsContext, paramWCRectangle.getIntX(), paramWCRectangle.getIntY(), paramWCRectangle.getIntWidth(), paramWCRectangle.getIntHeight());
/*      */       }
/*      */       else {
/*  647 */         paint2GC(paramWCGraphicsContext);
/*      */       }
/*      */     } finally {
/*  650 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void paint2GC(WCGraphicsContext paramWCGraphicsContext) {
/*  655 */     paramWCGraphicsContext.setFontSmoothingType(this.fontSmoothingType);
/*  656 */     for (WCRenderQueue localWCRenderQueue = removeFirstRenderQueue(); localWCRenderQueue != null; localWCRenderQueue = removeFirstRenderQueue()) {
/*  657 */       paramWCGraphicsContext.saveState();
/*  658 */       if (localWCRenderQueue.getClip() != null) {
/*  659 */         paramWCGraphicsContext.setClip(localWCRenderQueue.getClip());
/*      */       }
/*  661 */       localWCRenderQueue.decode(paramWCGraphicsContext);
/*  662 */       paramWCGraphicsContext.restoreState();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void clearRenderQueues()
/*      */   {
/*  673 */     lockPage();
/*      */     try {
/*  675 */       for (WCRenderQueue localWCRenderQueue = removeFirstRenderQueue(); localWCRenderQueue != null; localWCRenderQueue = removeFirstRenderQueue())
/*  676 */         localWCRenderQueue.dispose();
/*      */     }
/*      */     finally {
/*  679 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void print(long paramLong, WCGraphicsContext paramWCGraphicsContext, WCRectangle paramWCRectangle)
/*      */   {
/*  685 */     lockPage();
/*      */     try {
/*  687 */       log.log(Level.FINEST, new StringBuilder().append("print: ").append(paramWCRectangle.getIntX()).append(" ").append(paramWCRectangle.getIntY()).append(" ").append(paramWCRectangle.getIntWidth()).append(" ").append(paramWCRectangle.getIntHeight()).toString());
/*      */ 
/*  690 */       if (this.isDisposed) {
/*  691 */         log.log(Level.FINE, "print() request for a disposed web page.");
/*      */       }
/*      */       else {
/*  694 */         if (!this.frames.contains(Long.valueOf(paramLong)))
/*      */         {
/*      */           return;
/*      */         }
/*  698 */         twkPrint(paramLong, paramWCGraphicsContext, paramWCRectangle.getIntX(), paramWCRectangle.getIntY(), paramWCRectangle.getIntWidth(), paramWCRectangle.getIntHeight());
/*      */       }
/*      */     }
/*      */     finally {
/*  702 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void dispatchFocusEvent(WCFocusEvent paramWCFocusEvent) {
/*  707 */     lockPage();
/*      */     try {
/*  709 */       log.log(Level.FINEST, new StringBuilder().append("dispatchFocusEvent: ").append(paramWCFocusEvent).toString());
/*  710 */       if (this.isDisposed) {
/*  711 */         log.log(Level.FINE, "Focus event for a disposed web page.");
/*      */       }
/*      */       else
/*  714 */         twkProcessFocusEvent(getPage(), paramWCFocusEvent.getID(), paramWCFocusEvent.getDirection());
/*      */     }
/*      */     finally {
/*  717 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean dispatchKeyEvent(WCKeyEvent paramWCKeyEvent) {
/*  722 */     lockPage();
/*      */     try {
/*  724 */       log.log(Level.FINEST, new StringBuilder().append("dispatchKeyEvent: ").append(paramWCKeyEvent).toString());
/*      */       boolean bool;
/*  725 */       if (this.isDisposed) {
/*  726 */         log.log(Level.FINE, "Key event for a disposed web page.");
/*  727 */         return false;
/*      */       }
/*  729 */       if (WCKeyEvent.filterEvent(paramWCKeyEvent)) {
/*  730 */         log.log(Level.FINEST, "filtered");
/*  731 */         return false;
/*      */       }
/*  733 */       return twkProcessKeyEvent(getPage(), paramWCKeyEvent.getType(), paramWCKeyEvent.getText(), paramWCKeyEvent.getKeyIdentifier(), paramWCKeyEvent.getWindowsVirtualKeyCode(), paramWCKeyEvent.isShiftDown(), paramWCKeyEvent.isCtrlDown(), paramWCKeyEvent.isAltDown(), paramWCKeyEvent.isMetaDown());
/*      */     }
/*      */     finally
/*      */     {
/*  739 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean dispatchMouseEvent(WCMouseEvent paramWCMouseEvent) {
/*  744 */     lockPage();
/*      */     try {
/*  746 */       log.log(Level.FINEST, new StringBuilder().append("dispatchMouseEvent: ").append(paramWCMouseEvent.getX()).append(",").append(paramWCMouseEvent.getY()).toString());
/*      */       boolean bool;
/*  747 */       if (this.isDisposed) {
/*  748 */         log.log(Level.FINE, "Mouse event for a disposed web page.");
/*  749 */         return false;
/*      */       }
/*  751 */       return twkProcessMouseEvent(getPage(), paramWCMouseEvent.getID(), paramWCMouseEvent.getButton(), paramWCMouseEvent.getClickCount(), paramWCMouseEvent.getX(), paramWCMouseEvent.getY(), paramWCMouseEvent.getScreenX(), paramWCMouseEvent.getScreenY(), paramWCMouseEvent.isShiftDown(), paramWCMouseEvent.isControlDown(), paramWCMouseEvent.isAltDown(), paramWCMouseEvent.isMetaDown(), paramWCMouseEvent.isPopupTrigger(), (float)paramWCMouseEvent.getWhen() / 1000.0F);
/*      */     }
/*      */     finally
/*      */     {
/*  757 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean dispatchMouseWheelEvent(WCMouseWheelEvent paramWCMouseWheelEvent) {
/*  762 */     lockPage();
/*      */     try {
/*  764 */       log.log(Level.FINEST, new StringBuilder().append("dispatchMouseWheelEvent: ").append(paramWCMouseWheelEvent).toString());
/*      */       boolean bool;
/*  765 */       if (this.isDisposed) {
/*  766 */         log.log(Level.FINE, "MouseWheel event for a disposed web page.");
/*  767 */         return false;
/*      */       }
/*  769 */       return twkProcessMouseWheelEvent(getPage(), paramWCMouseWheelEvent.getX(), paramWCMouseWheelEvent.getY(), paramWCMouseWheelEvent.getScreenX(), paramWCMouseWheelEvent.getScreenY(), paramWCMouseWheelEvent.getDeltaX(), paramWCMouseWheelEvent.getDeltaY(), paramWCMouseWheelEvent.isShiftDown(), paramWCMouseWheelEvent.isControlDown(), paramWCMouseWheelEvent.isAltDown(), paramWCMouseWheelEvent.isMetaDown(), (float)paramWCMouseWheelEvent.getWhen() / 1000.0F);
/*      */     }
/*      */     finally
/*      */     {
/*  775 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean dispatchTouchEvent(WCTouchEvent paramWCTouchEvent) {
/*  780 */     lockPage();
/*      */     try {
/*  782 */       log.log(Level.FINEST, "dispatchTouchEvent");
/*      */       boolean bool;
/*  783 */       if (this.isDisposed) {
/*  784 */         log.log(Level.FINE, "Touch event for a disposed web page.");
/*  785 */         return false;
/*      */       }
/*  787 */       return twkProcessTouchEvent(getPage(), paramWCTouchEvent.getID(), paramWCTouchEvent.getTouchData(), paramWCTouchEvent.isShiftDown(), paramWCTouchEvent.isControlDown(), paramWCTouchEvent.isAltDown(), paramWCTouchEvent.isMetaDown(), (float)paramWCTouchEvent.getWhen() / 1000.0F);
/*      */     }
/*      */     finally
/*      */     {
/*  791 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean dispatchInputMethodEvent(WCInputMethodEvent paramWCInputMethodEvent) {
/*  796 */     lockPage();
/*      */     try {
/*  798 */       log.log(Level.FINEST, new StringBuilder().append("dispatchInputMethodEvent: ").append(paramWCInputMethodEvent).toString());
/*      */       boolean bool;
/*  799 */       if (this.isDisposed) {
/*  800 */         log.log(Level.FINE, "InputMethod event for a disposed web page.");
/*  801 */         return false;
/*      */       }
/*  803 */       switch (paramWCInputMethodEvent.getID()) {
/*      */       case 0:
/*  805 */         return twkProcessInputTextChange(getPage(), paramWCInputMethodEvent.getComposed(), paramWCInputMethodEvent.getCommitted(), paramWCInputMethodEvent.getAttributes(), paramWCInputMethodEvent.getCaretPosition());
/*      */       case 1:
/*  810 */         return twkProcessCaretPositionChange(getPage(), paramWCInputMethodEvent.getCaretPosition());
/*      */       }
/*      */ 
/*  813 */       return false;
/*      */     }
/*      */     finally {
/*  816 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int dispatchDragOperation(int paramInt1, String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*      */   {
/*  839 */     lockPage();
/*      */     try {
/*  841 */       log.log(Level.FINEST, new StringBuilder().append("dispatchDragOperation: ").append(paramInt2).append(",").append(paramInt3).append(" dndCommand:").append(paramInt1).append(" dndAction").append(paramInt6).toString());
/*      */       int i;
/*  844 */       if (this.isDisposed) {
/*  845 */         log.log(Level.FINE, "DnD event for a disposed web page.");
/*  846 */         return 0;
/*      */       }
/*  848 */       return twkProcessDrag(getPage(), paramInt1, paramArrayOfString1, paramArrayOfString2, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */     }
/*      */     finally
/*      */     {
/*  855 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void confirmStartDrag() {
/*  860 */     if (this.uiClient != null)
/*  861 */       this.uiClient.confirmStartDrag();
/*      */   }
/*      */ 
/*      */   public boolean isDragConfirmed() {
/*  865 */     return this.uiClient != null ? this.uiClient.isDragConfirmed() : false;
/*      */   }
/*      */ 
/*      */   public int[] getClientTextLocation(int paramInt)
/*      */   {
/*  875 */     lockPage();
/*      */     try
/*      */     {
/*      */       int[] arrayOfInt;
/*  877 */       if (this.isDisposed) {
/*  878 */         log.log(Level.FINE, "getClientTextLocation() request for a disposed web page.");
/*  879 */         return new int[] { 0, 0, 0, 0 };
/*      */       }
/*  881 */       return twkGetTextLocation(getPage(), paramInt);
/*      */     }
/*      */     finally {
/*  884 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getClientLocationOffset(int paramInt1, int paramInt2) {
/*  889 */     lockPage();
/*      */     try
/*      */     {
/*      */       int i;
/*  891 */       if (this.isDisposed) {
/*  892 */         log.log(Level.FINE, "getClientLocationOffset() request for a disposed web page.");
/*  893 */         return 0;
/*      */       }
/*  895 */       return twkGetInsertPositionOffset(getPage());
/*      */     }
/*      */     finally {
/*  898 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getClientInsertPositionOffset() {
/*  903 */     lockPage();
/*      */     try
/*      */     {
/*      */       int i;
/*  905 */       if (this.isDisposed) {
/*  906 */         log.log(Level.FINE, "getClientInsertPositionOffset() request for a disposed web page.");
/*  907 */         return 0;
/*      */       }
/*  909 */       return twkGetInsertPositionOffset(getPage());
/*      */     }
/*      */     finally {
/*  912 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getClientCommittedTextLength() {
/*  917 */     lockPage();
/*      */     try
/*      */     {
/*      */       int i;
/*  919 */       if (this.isDisposed) {
/*  920 */         log.log(Level.FINE, "getClientCommittedTextOffset() request for a disposed web page.");
/*  921 */         return 0;
/*      */       }
/*  923 */       return twkGetCommittedTextLength(getPage());
/*      */     }
/*      */     finally {
/*  926 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getClientCommittedText() {
/*  931 */     lockPage();
/*      */     try
/*      */     {
/*      */       String str;
/*  933 */       if (this.isDisposed) {
/*  934 */         log.log(Level.FINE, "getClientCommittedText() request for a disposed web page.");
/*  935 */         return "";
/*      */       }
/*  937 */       return twkGetCommittedText(getPage());
/*      */     }
/*      */     finally {
/*  940 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getClientSelectedText() {
/*  945 */     lockPage();
/*      */     try
/*      */     {
/*      */       String str;
/*  947 */       if (this.isDisposed) {
/*  948 */         log.log(Level.FINE, "getClientSelectedText() request for a disposed web page.");
/*  949 */         return "";
/*      */       }
/*  951 */       return twkGetSelectedText(getPage());
/*      */     }
/*      */     finally {
/*  954 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void dispose()
/*      */   {
/*  963 */     lockPage();
/*      */     try {
/*  965 */       log.log(Level.FINER, "dispose");
/*      */ 
/*  967 */       stop();
/*  968 */       clearRenderQueues();
/*  969 */       this.isDisposed = true;
/*      */ 
/*  971 */       twkDestroyPage(this.pPage);
/*  972 */       this.pPage = 0L;
/*      */ 
/*  974 */       for (Iterator localIterator = this.frames.iterator(); localIterator.hasNext(); ) { long l = ((Long)localIterator.next()).longValue();
/*  975 */         log.log(Level.FINE, new StringBuilder().append("Undestroyed frame view: ").append(l).toString());
/*      */       }
/*  977 */       this.frames.clear();
/*      */ 
/*  979 */       if (this.backbuffer != null) {
/*  980 */         this.backbuffer.deref();
/*  981 */         this.backbuffer = null;
/*      */       }
/*      */     } finally {
/*  984 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getName(long paramLong) {
/*  989 */     lockPage();
/*      */     try {
/*  991 */       log.log(Level.FINE, new StringBuilder().append("Get Name: frame = ").append(paramLong).toString());
/*      */       String str;
/*  992 */       if (this.isDisposed) {
/*  993 */         log.log(Level.FINE, "getName() request for a disposed web page.");
/*  994 */         return null;
/*      */       }
/*  996 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/*  997 */         return null;
/*      */       }
/*  999 */       return twkGetName(paramLong);
/*      */     }
/*      */     finally {
/* 1002 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getURL(long paramLong) {
/* 1007 */     lockPage();
/*      */     try {
/* 1009 */       log.log(Level.FINE, new StringBuilder().append("Get URL: frame = ").append(paramLong).toString());
/*      */       String str;
/* 1010 */       if (this.isDisposed) {
/* 1011 */         log.log(Level.FINE, "getURL() request for a disposed web page.");
/* 1012 */         return null;
/*      */       }
/* 1014 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1015 */         return null;
/*      */       }
/* 1017 */       return twkGetURL(paramLong);
/*      */     }
/*      */     finally {
/* 1020 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getEncoding() {
/* 1025 */     lockPage();
/*      */     try {
/* 1027 */       log.log(Level.FINE, "Get encoding");
/*      */       String str;
/* 1028 */       if (this.isDisposed) {
/* 1029 */         log.log(Level.FINE, "getEncoding() request for a disposed web page.");
/* 1030 */         return null;
/*      */       }
/* 1032 */       return twkGetEncoding(getPage());
/*      */     }
/*      */     finally {
/* 1035 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setEncoding(String paramString) {
/* 1040 */     lockPage();
/*      */     try {
/* 1042 */       log.log(Level.FINE, new StringBuilder().append("Set encoding: encoding = ").append(paramString).toString());
/* 1043 */       if (this.isDisposed) {
/* 1044 */         log.log(Level.FINE, "setEncoding() request for a disposed web page.");
/*      */       }
/* 1047 */       else if ((paramString != null) && (!paramString.isEmpty()))
/* 1048 */         twkSetEncoding(getPage(), paramString);
/*      */     }
/*      */     finally
/*      */     {
/* 1052 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getInnerText(long paramLong)
/*      */   {
/* 1058 */     lockPage();
/*      */     try {
/* 1060 */       log.log(Level.FINE, new StringBuilder().append("Get inner text: frame = ").append(paramLong).toString());
/*      */       String str;
/* 1061 */       if (this.isDisposed) {
/* 1062 */         log.log(Level.FINE, "getInnerText() request for a disposed web page.");
/* 1063 */         return null;
/*      */       }
/* 1065 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1066 */         return null;
/*      */       }
/* 1068 */       return twkGetInnerText(paramLong);
/*      */     }
/*      */     finally {
/* 1071 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getRenderTree(long paramLong)
/*      */   {
/* 1077 */     lockPage();
/*      */     try {
/* 1079 */       log.log(Level.FINE, new StringBuilder().append("Get render tree: frame = ").append(paramLong).toString());
/*      */       String str;
/* 1080 */       if (this.isDisposed) {
/* 1081 */         log.log(Level.FINE, "getRenderTree() request for a disposed web page.");
/* 1082 */         return null;
/*      */       }
/* 1084 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1085 */         return null;
/*      */       }
/* 1087 */       return twkGetRenderTree(paramLong);
/*      */     }
/*      */     finally {
/* 1090 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getUnloadEventListenersCount(long paramLong)
/*      */   {
/* 1096 */     lockPage();
/*      */     try {
/* 1098 */       log.log(Level.FINE, new StringBuilder().append("frame: ").append(paramLong).toString());
/*      */       int i;
/* 1099 */       if (this.isDisposed) {
/* 1100 */         log.log(Level.FINE, "request for a disposed web page.");
/* 1101 */         return 0;
/*      */       }
/* 1103 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1104 */         return 0;
/*      */       }
/* 1106 */       return twkGetUnloadEventListenersCount(paramLong);
/*      */     }
/*      */     finally {
/* 1109 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getContentType(long paramLong) {
/* 1114 */     lockPage();
/*      */     try {
/* 1116 */       log.log(Level.FINE, new StringBuilder().append("Get content type: frame = ").append(paramLong).toString());
/*      */       String str;
/* 1117 */       if (this.isDisposed) {
/* 1118 */         log.log(Level.FINE, "getContentType() request for a disposed web page.");
/* 1119 */         return null;
/*      */       }
/* 1121 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1122 */         return null;
/*      */       }
/* 1124 */       return twkGetContentType(paramLong);
/*      */     }
/*      */     finally {
/* 1127 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getTitle(long paramLong) {
/* 1132 */     lockPage();
/*      */     try {
/* 1134 */       log.log(Level.FINE, new StringBuilder().append("Get title: frame = ").append(paramLong).toString());
/*      */       String str;
/* 1135 */       if (this.isDisposed) {
/* 1136 */         log.log(Level.FINE, "getTitle() request for a disposed web page.");
/* 1137 */         return null;
/*      */       }
/* 1139 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1140 */         return null;
/*      */       }
/* 1142 */       return twkGetTitle(paramLong);
/*      */     }
/*      */     finally {
/* 1145 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public WCImage getIcon(long paramLong) {
/* 1150 */     lockPage();
/*      */     try {
/* 1152 */       log.log(Level.FINE, new StringBuilder().append("Get icon: frame = ").append(paramLong).toString());
/* 1153 */       if (this.isDisposed) {
/* 1154 */         log.log(Level.FINE, "getIcon() request for a disposed web page.");
/* 1155 */         return null;
/*      */       }
/* 1157 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1158 */         return null;
/*      */       }
/* 1160 */       Object localObject1 = twkGetIconURL(paramLong);
/*      */       Utilities localUtilities;
/* 1162 */       if ((localObject1 != null) && (!((String)localObject1).isEmpty())) {
/* 1163 */         localUtilities = Utilities.getUtilities();
/* 1164 */         if (localUtilities != null) {
/* 1165 */           return localUtilities.getIconImage((String)localObject1);
/*      */         }
/*      */       }
/* 1168 */       return null;
/*      */     }
/*      */     finally {
/* 1171 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void open(long paramLong, String paramString) {
/* 1176 */     lockPage();
/*      */     try {
/* 1178 */       log.log(Level.FINE, new StringBuilder().append("Open URL: ").append(paramString).toString());
/* 1179 */       if (this.isDisposed) {
/* 1180 */         log.log(Level.FINE, "open() request for a disposed web page.");
/*      */       }
/*      */       else {
/* 1183 */         if (!this.frames.contains(Long.valueOf(paramLong))) {
/*      */           return;
/*      */         }
/* 1186 */         twkOpen(paramLong, paramString);
/*      */       }
/*      */     } finally {
/* 1189 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void load(long paramLong, String paramString1, String paramString2) {
/* 1194 */     lockPage();
/*      */     try {
/* 1196 */       log.log(Level.FINE, new StringBuilder().append("Load text: ").append(paramString1).toString());
/* 1197 */       if (paramString1 == null) {
/*      */         return;
/*      */       }
/* 1200 */       if (this.isDisposed) {
/* 1201 */         log.log(Level.FINE, "load() request for a disposed web page.");
/*      */       }
/*      */       else {
/* 1204 */         if (!this.frames.contains(Long.valueOf(paramLong)))
/*      */         {
/*      */           return;
/*      */         }
/* 1208 */         twkLoad(paramLong, paramString1, paramString2);
/*      */       }
/*      */     } finally {
/* 1211 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void stop(long paramLong) {
/* 1216 */     lockPage();
/*      */     try {
/* 1218 */       log.log(Level.FINE, new StringBuilder().append("Stop loading: frame = ").append(paramLong).toString());
/*      */ 
/* 1222 */       if (this.isDisposed) {
/* 1223 */         log.log(Level.FINE, "cancel() request for a disposed web page.");
/*      */       }
/*      */       else {
/* 1226 */         if (!this.frames.contains(Long.valueOf(paramLong))) {
/*      */           return;
/*      */         }
/* 1229 */         String str1 = twkGetURL(paramLong);
/* 1230 */         String str2 = twkGetContentType(paramLong);
/* 1231 */         twkStop(paramLong);
/*      */ 
/* 1234 */         fireLoadEvent(paramLong, 6, str1, str2, 1.0D, 0);
/*      */       }
/*      */     } finally {
/* 1237 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void stop()
/*      */   {
/* 1243 */     lockPage();
/*      */     try {
/* 1245 */       log.log(Level.FINE, "Stop loading sync");
/* 1246 */       if (this.isDisposed) {
/* 1247 */         log.log(Level.FINE, "stopAll() request for a disposed web page.");
/*      */       }
/*      */       else
/* 1250 */         twkStopAll(getPage());
/*      */     }
/*      */     finally {
/* 1253 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void refresh(long paramLong) {
/* 1258 */     lockPage();
/*      */     try {
/* 1260 */       log.log(Level.FINE, new StringBuilder().append("Refresh: frame = ").append(paramLong).toString());
/* 1261 */       if (this.isDisposed) {
/* 1262 */         log.log(Level.FINE, "refresh() request for a disposed web page.");
/*      */       }
/*      */       else {
/* 1265 */         if (!this.frames.contains(Long.valueOf(paramLong))) {
/*      */           return;
/*      */         }
/* 1268 */         twkRefresh(paramLong);
/*      */       }
/*      */     } finally {
/* 1271 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public BackForwardList createBackForwardList() {
/* 1276 */     return new BackForwardList(this);
/*      */   }
/*      */ 
/*      */   public boolean goBack() {
/* 1280 */     lockPage();
/*      */     try {
/* 1282 */       log.log(Level.FINE, "Go back");
/*      */       boolean bool;
/* 1283 */       if (this.isDisposed) {
/* 1284 */         log.log(Level.FINE, "goBack() request for a disposed web page.");
/* 1285 */         return false;
/*      */       }
/* 1287 */       return twkGoBackForward(getPage(), -1);
/*      */     }
/*      */     finally {
/* 1290 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean goForward() {
/* 1295 */     lockPage();
/*      */     try {
/* 1297 */       log.log(Level.FINE, "Go forward");
/*      */       boolean bool;
/* 1298 */       if (this.isDisposed) {
/* 1299 */         log.log(Level.FINE, "goForward() request for a disposed web page.");
/* 1300 */         return false;
/*      */       }
/* 1302 */       return twkGoBackForward(getPage(), 1);
/*      */     }
/*      */     finally {
/* 1305 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean copy() {
/* 1310 */     lockPage();
/*      */     try {
/* 1312 */       log.log(Level.FINE, "Copy");
/* 1313 */       if (this.isDisposed) {
/* 1314 */         log.log(Level.FINE, "copy() request for a disposed web page.");
/* 1315 */         return false;
/*      */       }
/* 1317 */       long l = getMainFrame();
/*      */       boolean bool2;
/* 1318 */       if (!this.frames.contains(Long.valueOf(l))) {
/* 1319 */         return false;
/*      */       }
/* 1321 */       return twkCopy(l);
/*      */     }
/*      */     finally {
/* 1324 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean find(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*      */   {
/* 1330 */     lockPage();
/*      */     try {
/* 1332 */       log.log(Level.FINE, new StringBuilder().append("Find in page: stringToFind = ").append(paramString).append(", ").append(paramBoolean1 ? "forward" : "backward").append(paramBoolean2 ? ", wrap" : "").append(paramBoolean3 ? ", matchCase" : "").toString());
/*      */       boolean bool;
/* 1334 */       if (this.isDisposed) {
/* 1335 */         log.log(Level.FINE, "find() request for a disposed web page.");
/* 1336 */         return false;
/*      */       }
/* 1338 */       return twkFindInPage(getPage(), paramString, paramBoolean1, paramBoolean2, paramBoolean3);
/*      */     }
/*      */     finally {
/* 1341 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean find(long paramLong, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*      */   {
/* 1349 */     lockPage();
/*      */     try {
/* 1351 */       log.log(Level.FINE, new StringBuilder().append("Find in frame: stringToFind = ").append(paramString).append(", ").append(paramBoolean1 ? "forward" : "backward").append(paramBoolean2 ? ", wrap" : "").append(paramBoolean3 ? ", matchCase" : "").toString());
/*      */       boolean bool;
/* 1353 */       if (this.isDisposed) {
/* 1354 */         log.log(Level.FINE, "find() request for a disposed web page.");
/* 1355 */         return false;
/*      */       }
/* 1357 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1358 */         return false;
/*      */       }
/* 1360 */       return twkFindInFrame(paramLong, paramString, paramBoolean1, paramBoolean2, paramBoolean3);
/*      */     }
/*      */     finally {
/* 1363 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public float getZoomFactor(boolean paramBoolean) {
/* 1368 */     lockPage();
/*      */     try {
/* 1370 */       log.log(Level.FINE, new StringBuilder().append("Get zoom factor, textOnly=").append(paramBoolean).toString());
/* 1371 */       if (this.isDisposed) {
/* 1372 */         log.log(Level.FINE, "getZoomFactor() request for a disposed web page.");
/* 1373 */         return 1.0F;
/*      */       }
/* 1375 */       long l = getMainFrame();
/*      */       float f2;
/* 1376 */       if (!this.frames.contains(Long.valueOf(l))) {
/* 1377 */         return 1.0F;
/*      */       }
/* 1379 */       return twkGetZoomFactor(l, paramBoolean);
/*      */     } finally {
/* 1381 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setZoomFactor(float paramFloat, boolean paramBoolean) {
/* 1386 */     lockPage();
/*      */     try {
/* 1388 */       log.fine(String.format("Set zoom factor %.2f, textOnly=%b", new Object[] { Float.valueOf(paramFloat), Boolean.valueOf(paramBoolean) }));
/* 1389 */       if (this.isDisposed) {
/* 1390 */         log.log(Level.FINE, "setZoomFactor() request for a disposed web page.");
/*      */       }
/*      */       else {
/* 1393 */         long l = getMainFrame();
/* 1394 */         if ((l == 0L) || (!this.frames.contains(Long.valueOf(l)))) {
/*      */           return;
/*      */         }
/* 1397 */         twkSetZoomFactor(l, paramFloat, paramBoolean);
/*      */       }
/*      */     } finally { unlockPage(); }
/*      */   }
/*      */ 
/*      */   public void setFontSmoothingType(int paramInt)
/*      */   {
/* 1404 */     this.fontSmoothingType = paramInt;
/* 1405 */     repaintAll();
/*      */   }
/*      */ 
/*      */   public void reset(long paramLong)
/*      */   {
/* 1410 */     lockPage();
/*      */     try {
/* 1412 */       log.log(Level.FINE, new StringBuilder().append("Reset: frame = ").append(paramLong).toString());
/* 1413 */       if (this.isDisposed) {
/* 1414 */         log.log(Level.FINE, "reset() request for a disposed web page.");
/*      */       }
/*      */       else {
/* 1417 */         if ((paramLong == 0L) || (!this.frames.contains(Long.valueOf(paramLong)))) {
/*      */           return;
/*      */         }
/* 1420 */         twkReset(paramLong);
/*      */       }
/*      */     } finally {
/* 1423 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object executeScript(long paramLong, String paramString) throws JSException {
/* 1428 */     lockPage();
/*      */     try {
/* 1430 */       log.log(Level.FINE, new StringBuilder().append("execute script: \"").append(paramString).append("\" in frame = ").append(paramLong).toString());
/*      */       Object localObject1;
/* 1431 */       if (this.isDisposed) {
/* 1432 */         log.log(Level.FINE, "executeScript() request for a disposed web page.");
/* 1433 */         return null;
/*      */       }
/* 1435 */       if ((paramLong == 0L) || (!this.frames.contains(Long.valueOf(paramLong)))) {
/* 1436 */         return null;
/*      */       }
/* 1438 */       return twkExecuteScript(paramLong, paramString);
/*      */     }
/*      */     finally {
/* 1441 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getMainFrame() {
/* 1446 */     lockPage();
/*      */     try {
/* 1448 */       log.log(Level.FINER, new StringBuilder().append("getMainFrame: page = ").append(this.pPage).toString());
/* 1449 */       if (this.isDisposed) {
/* 1450 */         log.log(Level.FINE, "getMainFrame() request for a disposed web page.");
/* 1451 */         return 0L;
/*      */       }
/* 1453 */       long l1 = twkGetMainFrame(getPage());
/* 1454 */       log.log(Level.FINER, new StringBuilder().append("Main frame = ").append(l1).toString());
/* 1455 */       this.frames.add(Long.valueOf(l1));
/* 1456 */       return l1;
/*      */     } finally {
/* 1458 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getParentFrame(long paramLong) {
/* 1463 */     lockPage();
/*      */     try {
/* 1465 */       log.log(Level.FINE, new StringBuilder().append("getParentFrame: child = ").append(paramLong).toString());
/*      */       long l;
/* 1466 */       if (this.isDisposed) {
/* 1467 */         log.log(Level.FINE, "getParentFrame() request for a disposed web page.");
/* 1468 */         return 0L;
/*      */       }
/* 1470 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1471 */         return 0L;
/*      */       }
/* 1473 */       return twkGetParentFrame(paramLong);
/*      */     } finally {
/* 1475 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<Long> getChildFrames(long paramLong) {
/* 1480 */     lockPage();
/*      */     try {
/* 1482 */       log.log(Level.FINE, new StringBuilder().append("getChildFrames: parent = ").append(paramLong).toString());
/* 1483 */       if (this.isDisposed) {
/* 1484 */         log.log(Level.FINE, "getChildFrames() request for a disposed web page.");
/* 1485 */         return null;
/*      */       }
/* 1487 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1488 */         return null;
/*      */       }
/* 1490 */       Object localObject1 = twkGetChildFrames(paramLong);
/* 1491 */       LinkedList localLinkedList = new LinkedList();
/* 1492 */       for (long l : localObject1) {
/* 1493 */         localLinkedList.add(Long.valueOf(l));
/*      */       }
/* 1495 */       return localLinkedList;
/*      */     } finally {
/* 1497 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public WCRectangle getVisibleRect(long paramLong) {
/* 1502 */     lockPage();
/*      */     try {
/* 1504 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1505 */         return null;
/*      */       }
/* 1507 */       Object localObject1 = twkGetVisibleRect(paramLong);
/*      */       WCRectangle localWCRectangle;
/* 1508 */       if (localObject1 != null) {
/* 1509 */         return new WCRectangle(localObject1[0], localObject1[1], localObject1[2], localObject1[3]);
/*      */       }
/* 1511 */       return null;
/*      */     } finally {
/* 1513 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void scrollToPosition(long paramLong, WCPoint paramWCPoint) {
/* 1518 */     lockPage();
/*      */     try {
/* 1520 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/*      */         return;
/*      */       }
/* 1523 */       twkScrollToPosition(paramLong, paramWCPoint.getIntX(), paramWCPoint.getIntY());
/*      */     } finally {
/* 1525 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public WCSize getContentSize(long paramLong) {
/* 1530 */     lockPage();
/*      */     try {
/* 1532 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1533 */         return null;
/*      */       }
/* 1535 */       Object localObject1 = twkGetContentSize(paramLong);
/*      */       WCSize localWCSize;
/* 1536 */       if (localObject1 != null) {
/* 1537 */         return new WCSize(localObject1[0], localObject1[1]);
/*      */       }
/* 1539 */       return null;
/*      */     } finally {
/* 1541 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public Document getDocument(long paramLong)
/*      */   {
/* 1548 */     lockPage();
/*      */     try {
/* 1550 */       log.log(Level.FINE, "getDocument");
/*      */       Document localDocument;
/* 1551 */       if (this.isDisposed) {
/* 1552 */         log.log(Level.FINE, "getDocument() request for a disposed web page.");
/* 1553 */         return null;
/*      */       }
/*      */ 
/* 1556 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1557 */         return null;
/*      */       }
/* 1559 */       return twkGetDocument(paramLong);
/*      */     } finally {
/* 1561 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public Element getOwnerElement(long paramLong) {
/* 1566 */     lockPage();
/*      */     try {
/* 1568 */       log.log(Level.FINE, "getOwnerElement");
/*      */       Element localElement;
/* 1569 */       if (this.isDisposed) {
/* 1570 */         log.log(Level.FINE, "getOwnerElement() request for a disposed web page.");
/* 1571 */         return null;
/*      */       }
/*      */ 
/* 1574 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1575 */         return null;
/*      */       }
/* 1577 */       return twkGetOwnerElement(paramLong);
/*      */     } finally {
/* 1579 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean executeCommand(String paramString1, String paramString2)
/*      */   {
/* 1586 */     lockPage();
/*      */     try {
/* 1588 */       if (log.isLoggable(Level.FINE)) {
/* 1589 */         log.log(Level.FINE, "command: [{0}], value: [{1}]", new Object[] { paramString1, paramString2 });
/*      */       }
/*      */ 
/* 1592 */       if (this.isDisposed) {
/* 1593 */         log.log(Level.FINE, "Web page is already disposed");
/* 1594 */         return false;
/*      */       }
/*      */ 
/* 1597 */       boolean bool1 = twkExecuteCommand(getPage(), paramString1, paramString2);
/*      */ 
/* 1599 */       log.log(Level.FINE, "result: [{0}]", Boolean.valueOf(bool1));
/* 1600 */       return bool1;
/*      */     } finally {
/* 1602 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean queryCommandEnabled(String paramString) {
/* 1607 */     lockPage();
/*      */     try {
/* 1609 */       log.log(Level.FINE, "command: [{0}]", paramString);
/* 1610 */       if (this.isDisposed) {
/* 1611 */         log.log(Level.FINE, "Web page is already disposed");
/* 1612 */         return false;
/*      */       }
/*      */ 
/* 1615 */       boolean bool1 = twkQueryCommandEnabled(getPage(), paramString);
/*      */ 
/* 1617 */       log.log(Level.FINE, "result: [{0}]", Boolean.valueOf(bool1));
/* 1618 */       return bool1;
/*      */     } finally {
/* 1620 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean queryCommandState(String paramString) {
/* 1625 */     lockPage();
/*      */     try {
/* 1627 */       log.log(Level.FINE, "command: [{0}]", paramString);
/* 1628 */       if (this.isDisposed) {
/* 1629 */         log.log(Level.FINE, "Web page is already disposed");
/* 1630 */         return false;
/*      */       }
/*      */ 
/* 1633 */       boolean bool1 = twkQueryCommandState(getPage(), paramString);
/*      */ 
/* 1635 */       log.log(Level.FINE, "result: [{0}]", Boolean.valueOf(bool1));
/* 1636 */       return bool1;
/*      */     } finally {
/* 1638 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String queryCommandValue(String paramString) {
/* 1643 */     lockPage();
/*      */     try {
/* 1645 */       log.log(Level.FINE, "command: [{0}]", paramString);
/* 1646 */       if (this.isDisposed) {
/* 1647 */         log.log(Level.FINE, "Web page is already disposed");
/* 1648 */         return null;
/*      */       }
/*      */ 
/* 1651 */       String str1 = twkQueryCommandValue(getPage(), paramString);
/*      */ 
/* 1653 */       log.log(Level.FINE, "result: [{0}]", str1);
/* 1654 */       return str1;
/*      */     } finally {
/* 1656 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isEditable() {
/* 1661 */     lockPage();
/*      */     try {
/* 1663 */       log.log(Level.FINE, "isEditable");
/*      */       boolean bool;
/* 1664 */       if (this.isDisposed) {
/* 1665 */         log.log(Level.FINE, "isEditable() request for a disposed web page.");
/* 1666 */         return false;
/*      */       }
/*      */ 
/* 1669 */       return twkIsEditable(getPage());
/*      */     } finally {
/* 1671 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setEditable(boolean paramBoolean) {
/* 1676 */     lockPage();
/*      */     try {
/* 1678 */       log.log(Level.FINE, "setEditable");
/* 1679 */       if (this.isDisposed) {
/* 1680 */         log.log(Level.FINE, "setEditable() request for a disposed web page.");
/*      */       }
/*      */       else
/*      */       {
/* 1684 */         twkSetEditable(getPage(), paramBoolean);
/*      */       }
/*      */     } finally { unlockPage(); }
/*      */ 
/*      */   }
/*      */ 
/*      */   public String getHtml(long paramLong)
/*      */   {
/* 1695 */     lockPage();
/*      */     try {
/* 1697 */       log.log(Level.FINE, "getHtml");
/*      */       String str;
/* 1698 */       if (this.isDisposed) {
/* 1699 */         log.log(Level.FINE, "getHtml() request for a disposed web page.");
/* 1700 */         return null;
/*      */       }
/* 1702 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1703 */         return null;
/*      */       }
/* 1705 */       return twkGetHtml(paramLong);
/*      */     } finally {
/* 1707 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int startPrinting(long paramLong, float paramFloat, boolean paramBoolean)
/*      */   {
/* 1714 */     lockPage();
/*      */     try {
/* 1716 */       log.log(Level.FINE, new StringBuilder().append("Start printing: width=").append(paramFloat).append(", scale=").append(paramBoolean).toString());
/* 1717 */       if (this.isDisposed) {
/* 1718 */         log.log(Level.FINE, "startPrinting() request for a disposed web page.");
/* 1719 */         return 0;
/*      */       }
/* 1721 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1722 */         return 0;
/*      */       }
/* 1724 */       int i = twkStartPrinting(paramLong, paramFloat, paramBoolean);
/* 1725 */       log.log(Level.FINE, new StringBuilder().append("Preferred width = ").append(i).toString());
/* 1726 */       return i;
/*      */     } finally {
/* 1728 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void endPrinting(long paramLong) {
/* 1733 */     lockPage();
/*      */     try {
/* 1735 */       log.log(Level.FINE, "End printing");
/* 1736 */       if (this.isDisposed) {
/* 1737 */         log.log(Level.FINE, "endPrinting() request for a disposed web page.");
/*      */       }
/*      */       else {
/* 1740 */         if (!this.frames.contains(Long.valueOf(paramLong))) {
/*      */           return;
/*      */         }
/* 1743 */         twkEndPrinting(paramLong);
/*      */       }
/*      */     } finally { unlockPage(); }
/*      */   }
/*      */ 
/*      */   public int getPageHeight()
/*      */   {
/* 1750 */     return getFrameHeight(getMainFrame());
/*      */   }
/*      */ 
/*      */   public int getFrameHeight(long paramLong) {
/* 1754 */     lockPage();
/*      */     try {
/* 1756 */       log.log(Level.FINE, "Get page height");
/* 1757 */       if (this.isDisposed) {
/* 1758 */         log.log(Level.FINE, "getFrameHeight() request for a disposed web page.");
/* 1759 */         return 0;
/*      */       }
/* 1761 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1762 */         return 0;
/*      */       }
/* 1764 */       int i = twkGetFrameHeight(paramLong);
/* 1765 */       log.log(Level.FINE, new StringBuilder().append("Height = ").append(i).toString());
/* 1766 */       return i;
/*      */     } finally {
/* 1768 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public float adjustFrameHeight(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3)
/*      */   {
/* 1775 */     lockPage();
/*      */     try {
/* 1777 */       log.log(Level.FINE, "Adjust page height");
/*      */       float f;
/* 1778 */       if (this.isDisposed) {
/* 1779 */         log.log(Level.FINE, "adjustFrameHeight() request for a disposed web page.");
/* 1780 */         return 0.0F;
/*      */       }
/* 1782 */       if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1783 */         return 0.0F;
/*      */       }
/* 1785 */       return twkAdjustFrameHeight(paramLong, paramFloat1, paramFloat2, paramFloat3);
/*      */     } finally {
/* 1787 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean getUsePageCache()
/*      */   {
/* 1799 */     lockPage();
/*      */     try {
/* 1801 */       return twkGetUsePageCache(getPage());
/*      */     } finally {
/* 1803 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setUsePageCache(boolean paramBoolean)
/*      */   {
/* 1813 */     lockPage();
/*      */     try {
/* 1815 */       twkSetUsePageCache(getPage(), paramBoolean);
/*      */     } finally {
/* 1817 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean getDeveloperExtrasEnabled() {
/* 1822 */     lockPage();
/*      */     try {
/* 1824 */       boolean bool1 = twkGetDeveloperExtrasEnabled(getPage());
/* 1825 */       log.log(Level.FINE, "Getting developerExtrasEnabled, result: [{0}]", Boolean.valueOf(bool1));
/*      */ 
/* 1828 */       return bool1;
/*      */     } finally {
/* 1830 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setDeveloperExtrasEnabled(boolean paramBoolean) {
/* 1835 */     lockPage();
/*      */     try {
/* 1837 */       log.log(Level.FINE, "Setting developerExtrasEnabled, value: [{0}]", Boolean.valueOf(paramBoolean));
/*      */ 
/* 1840 */       twkSetDeveloperExtrasEnabled(getPage(), paramBoolean);
/*      */     } finally {
/* 1842 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isJavaScriptEnabled() {
/* 1847 */     lockPage();
/*      */     try {
/* 1849 */       return twkIsJavaScriptEnabled(getPage());
/*      */     } finally {
/* 1851 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setJavaScriptEnabled(boolean paramBoolean) {
/* 1856 */     lockPage();
/*      */     try {
/* 1858 */       twkSetJavaScriptEnabled(getPage(), paramBoolean);
/*      */     } finally {
/* 1860 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isContextMenuEnabled() {
/* 1865 */     lockPage();
/*      */     try {
/* 1867 */       return twkIsContextMenuEnabled(getPage());
/*      */     } finally {
/* 1869 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setContextMenuEnabled(boolean paramBoolean) {
/* 1874 */     lockPage();
/*      */     try {
/* 1876 */       twkSetContextMenuEnabled(getPage(), paramBoolean);
/*      */     } finally {
/* 1878 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setUserStyleSheetLocation(String paramString) {
/* 1883 */     lockPage();
/*      */     try {
/* 1885 */       twkSetUserStyleSheetLocation(getPage(), paramString);
/*      */     } finally {
/* 1887 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void connectInspectorFrontend()
/*      */   {
/* 1894 */     lockPage();
/*      */     try {
/* 1896 */       log.log(Level.FINE, "Connecting inspector frontend");
/* 1897 */       twkConnectInspectorFrontend(getPage());
/*      */     } finally {
/* 1899 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void disconnectInspectorFrontend() {
/* 1904 */     lockPage();
/*      */     try {
/* 1906 */       log.log(Level.FINE, "Disconnecting inspector frontend");
/* 1907 */       twkDisconnectInspectorFrontend(getPage());
/*      */     } finally {
/* 1909 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void dispatchInspectorMessageFromFrontend(String paramString) {
/* 1914 */     lockPage();
/*      */     try {
/* 1916 */       if (log.isLoggable(Level.FINE)) {
/* 1917 */         log.log(Level.FINE, "Dispatching inspector message from frontend, message: [{0}]", paramString);
/*      */       }
/*      */ 
/* 1922 */       twkDispatchInspectorMessageFromFrontend(getPage(), paramString);
/*      */     } finally {
/* 1924 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public InspectorClient getInspectorClient() {
/* 1929 */     return this.inspectorClient;
/*      */   }
/*      */ 
/*      */   public void setInspectorClient(InspectorClient paramInspectorClient) {
/* 1933 */     this.inspectorClient = paramInspectorClient;
/*      */   }
/*      */ 
/*      */   private void fwkFrameCreated(long paramLong)
/*      */   {
/* 1941 */     log.log(Level.FINE, new StringBuilder().append("Frame created: frame = ").append(paramLong).toString());
/* 1942 */     if (this.frames.contains(Long.valueOf(paramLong))) {
/* 1943 */       log.log(Level.FINE, "Error in fwkFrameCreated: frame is already in frames");
/* 1944 */       return;
/*      */     }
/* 1946 */     this.frames.add(Long.valueOf(paramLong));
/*      */   }
/*      */ 
/*      */   private void fwkFrameDestroyed(long paramLong) {
/* 1950 */     log.log(Level.FINE, new StringBuilder().append("Frame destroyed: frame = ").append(paramLong).toString());
/* 1951 */     if (!this.frames.contains(Long.valueOf(paramLong))) {
/* 1952 */       log.log(Level.FINE, "Error in fwkFrameDestroyed: frame is not found in frames");
/* 1953 */       return;
/*      */     }
/* 1955 */     this.frames.remove(Long.valueOf(paramLong));
/*      */   }
/*      */ 
/*      */   private void fwkRepaint(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/* 1961 */     lockPage();
/*      */     try {
/* 1963 */       if (log.isLoggable(Level.FINEST)) {
/* 1964 */         paintLog.finest(new StringBuilder().append("fwkRepaint: ").append(paramInt1).append(" ").append(paramInt2).append(" ").append(paramInt3).append(" ").append(paramInt4).toString());
/*      */       }
/* 1966 */       if (this.pageClient != null) {
/* 1967 */         addDirtyRect(new WCRectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/* 1968 */         this.pageClient.repaint(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean1, paramBoolean2);
/*      */       }
/*      */     } finally {
/* 1971 */       unlockPage();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void fwkScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 1976 */     if (log.isLoggable(Level.FINEST)) {
/* 1977 */       paintLog.finest(new StringBuilder().append("Scroll: ").append(paramInt1).append(" ").append(paramInt2).append(" ").append(paramInt3).append(" ").append(paramInt4).append("  ").append(paramInt5).append(" ").append(paramInt6).toString());
/*      */     }
/* 1979 */     if ((this.pageClient == null) || (!this.pageClient.isBackBufferSupported())) {
/* 1980 */       paintLog.finest("blit scrolling is switched off");
/*      */ 
/* 1982 */       return;
/*      */     }
/* 1984 */     this.scrollRequests.add(new ScrollRequest(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/* 1985 */     this.pageClient.repaint(paramInt1, paramInt2, paramInt3, paramInt4, true, true);
/*      */   }
/*      */ 
/*      */   private void fwkTransferFocus(boolean paramBoolean) {
/* 1989 */     log.log(Level.FINER, new StringBuilder().append("Transfer focus ").append(paramBoolean ? "forward" : "backward").toString());
/*      */ 
/* 1991 */     if (this.pageClient != null)
/* 1992 */       this.pageClient.transferFocus(paramBoolean);
/*      */   }
/*      */ 
/*      */   private void fwkSetCursor(long paramLong)
/*      */   {
/* 1997 */     log.log(Level.FINER, new StringBuilder().append("Set cursor: ").append(paramLong).toString());
/*      */ 
/* 1999 */     if (this.pageClient != null)
/* 2000 */       this.pageClient.setCursor(paramLong);
/*      */   }
/*      */ 
/*      */   private void fwkSetFocus(boolean paramBoolean)
/*      */   {
/* 2005 */     log.log(Level.FINER, new StringBuilder().append("Set focus: ").append(paramBoolean ? "true" : "false").toString());
/*      */ 
/* 2007 */     if (this.pageClient != null)
/* 2008 */       this.pageClient.setFocus(paramBoolean);
/*      */   }
/*      */ 
/*      */   private void fwkSetTooltip(String paramString)
/*      */   {
/* 2013 */     log.log(Level.FINER, new StringBuilder().append("Set tooltip: ").append(paramString).toString());
/*      */ 
/* 2015 */     if (this.pageClient != null)
/* 2016 */       this.pageClient.setTooltip(paramString);
/*      */   }
/*      */ 
/*      */   private void fwkPrint()
/*      */   {
/* 2021 */     log.log(Level.FINER, "Print");
/*      */ 
/* 2023 */     if (this.uiClient != null)
/* 2024 */       this.uiClient.print();
/*      */   }
/*      */ 
/*      */   private void fwkSetRequestURL(long paramLong, int paramInt, String paramString)
/*      */   {
/* 2029 */     log.log(Level.FINER, new StringBuilder().append("Set request URL: id = ").append(paramInt).append(", url = ").append(paramString).toString());
/*      */ 
/* 2031 */     synchronized (this.requestURLs) {
/* 2032 */       this.requestURLs.put(Integer.valueOf(paramInt), paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void fwkRemoveRequestURL(long paramLong, int paramInt) {
/* 2037 */     log.log(Level.FINER, new StringBuilder().append("Set request URL: id = ").append(paramInt).toString());
/*      */ 
/* 2039 */     synchronized (this.requestURLs) {
/* 2040 */       this.requestURLs.remove(Integer.valueOf(paramInt));
/* 2041 */       this.requestStarted.remove(Integer.valueOf(paramInt));
/*      */     }
/*      */   }
/*      */ 
/*      */   private WebPage fwkCreateWindow(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*      */   {
/* 2047 */     log.log(Level.FINER, "Create window");
/*      */ 
/* 2049 */     if (this.uiClient != null) {
/* 2050 */       return this.uiClient.createPage(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
/*      */     }
/* 2052 */     return null;
/*      */   }
/*      */ 
/*      */   private void fwkShowWindow() {
/* 2056 */     log.log(Level.FINER, "Show window");
/*      */ 
/* 2058 */     if (this.uiClient != null)
/* 2059 */       this.uiClient.showView();
/*      */   }
/*      */ 
/*      */   private void fwkCloseWindow()
/*      */   {
/* 2064 */     log.log(Level.FINER, "Close window");
/*      */ 
/* 2066 */     if ((permitCloseWindowAction()) && 
/* 2067 */       (this.uiClient != null))
/* 2068 */       this.uiClient.closePage();
/*      */   }
/*      */ 
/*      */   private WCRectangle fwkGetWindowBounds()
/*      */   {
/* 2074 */     log.log(Level.FINE, "Get window bounds");
/*      */ 
/* 2076 */     if (this.uiClient != null) {
/* 2077 */       WCRectangle localWCRectangle = this.uiClient.getViewBounds();
/* 2078 */       if (localWCRectangle != null) {
/* 2079 */         return localWCRectangle;
/*      */       }
/*      */     }
/* 2082 */     return fwkGetPageBounds();
/*      */   }
/*      */ 
/*      */   private void fwkSetWindowBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 2086 */     log.log(Level.FINER, new StringBuilder().append("Set window bounds: ").append(paramInt1).append(" ").append(paramInt2).append(" ").append(paramInt3).append(" ").append(paramInt4).toString());
/*      */ 
/* 2088 */     if (this.uiClient != null)
/* 2089 */       this.uiClient.setViewBounds(new WCRectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */   }
/*      */ 
/*      */   private WCRectangle fwkGetPageBounds()
/*      */   {
/* 2094 */     log.log(Level.FINER, "Get page bounds");
/* 2095 */     return new WCRectangle(0.0F, 0.0F, this.width, this.height);
/*      */   }
/*      */ 
/*      */   private void fwkSetScrollbarsVisible(boolean paramBoolean)
/*      */   {
/*      */   }
/*      */ 
/*      */   private void fwkSetStatusbarText(String paramString) {
/* 2103 */     log.log(Level.FINER, new StringBuilder().append("Set statusbar text: ").append(paramString).toString());
/*      */ 
/* 2105 */     if (this.uiClient != null)
/* 2106 */       this.uiClient.setStatusbarText(paramString);
/*      */   }
/*      */ 
/*      */   private String fwkChooseFile(String paramString)
/*      */   {
/* 2111 */     log.log(Level.FINER, new StringBuilder().append("Choose file, initial=").append(paramString).toString());
/*      */ 
/* 2113 */     return this.uiClient != null ? this.uiClient.chooseFile(paramString) : null;
/*      */   }
/*      */ 
/*      */   private void fwkStartDrag(WCImage paramWCImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String[] paramArrayOfString, Object[] paramArrayOfObject)
/*      */   {
/* 2125 */     log.log(Level.FINER, "Start drag: ");
/*      */ 
/* 2127 */     if (this.uiClient != null)
/* 2128 */       this.uiClient.startDrag(paramWCImage, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   private WCPoint fwkScreenToWindow(WCPoint paramWCPoint)
/*      */   {
/* 2138 */     log.log(Level.FINER, "fwkScreenToWindow");
/*      */ 
/* 2140 */     if (this.pageClient != null) {
/* 2141 */       return this.pageClient.screenToWindow(paramWCPoint);
/*      */     }
/* 2143 */     return paramWCPoint;
/*      */   }
/*      */ 
/*      */   private WCPoint fwkWindowToScreen(WCPoint paramWCPoint) {
/* 2147 */     log.log(Level.FINER, "fwkWindowToScreen");
/*      */ 
/* 2149 */     if (this.pageClient != null) {
/* 2150 */       return this.pageClient.windowToScreen(paramWCPoint);
/*      */     }
/* 2152 */     return paramWCPoint;
/*      */   }
/*      */ 
/*      */   private void fwkAlert(String paramString)
/*      */   {
/* 2157 */     log.log(Level.FINE, new StringBuilder().append("JavaScript alert(): text = ").append(paramString).toString());
/*      */ 
/* 2159 */     if (this.uiClient != null)
/* 2160 */       this.uiClient.alert(paramString);
/*      */   }
/*      */ 
/*      */   private boolean fwkConfirm(String paramString)
/*      */   {
/* 2165 */     log.log(Level.FINE, new StringBuilder().append("JavaScript confirm(): text = ").append(paramString).toString());
/*      */ 
/* 2167 */     if (this.uiClient != null) {
/* 2168 */       return this.uiClient.confirm(paramString);
/*      */     }
/* 2170 */     return false;
/*      */   }
/*      */ 
/*      */   private String fwkPrompt(String paramString1, String paramString2) {
/* 2174 */     log.log(Level.FINE, new StringBuilder().append("JavaScript prompt(): text = ").append(paramString1).append(", default = ").append(paramString2).toString());
/*      */ 
/* 2176 */     if (this.uiClient != null) {
/* 2177 */       return this.uiClient.prompt(paramString1, paramString2);
/*      */     }
/* 2179 */     return null;
/*      */   }
/*      */ 
/*      */   private void fwkAddMessageToConsole(String paramString1, int paramInt, String paramString2)
/*      */   {
/* 2185 */     log.log(Level.FINE, new StringBuilder().append("fwkAddMessageToConsole(): message = ").append(paramString1).append(", lineNumber = ").append(paramInt).append(", sourceId = ").append(paramString2).toString());
/*      */ 
/* 2187 */     addMessageToConsole(paramString1, paramInt, paramString2);
/*      */   }
/*      */ 
/*      */   protected void addMessageToConsole(String paramString1, int paramInt, String paramString2)
/*      */   {
/*      */   }
/*      */ 
/*      */   private void fwkFireLoadEvent(long paramLong, int paramInt1, String paramString1, String paramString2, double paramDouble, int paramInt2)
/*      */   {
/* 2200 */     log.log(Level.FINER, new StringBuilder().append("Load event: pFrame = ").append(paramLong).append(", state = ").append(paramInt1).append(", url = ").append(paramString1).append(", contenttype=").append(paramString2).append(", progress = ").append(paramDouble).append(", error = ").append(paramInt2).toString());
/*      */ 
/* 2204 */     fireLoadEvent(paramLong, paramInt1, paramString1, paramString2, paramDouble, paramInt2);
/*      */   }
/*      */ 
/*      */   private void fwkFireResourceLoadEvent(long paramLong, int paramInt1, int paramInt2, String paramString, double paramDouble, int paramInt3)
/*      */   {
/* 2211 */     log.log(Level.FINER, new StringBuilder().append("Resource load event: pFrame = ").append(paramLong).append(", state = ").append(paramInt1).append(", id = ").append(paramInt2).append(", contenttype=").append(paramString).append(", progress = ").append(paramDouble).append(", error = ").append(paramInt3).toString());
/*      */ 
/* 2215 */     String str = (String)this.requestURLs.get(Integer.valueOf(paramInt2));
/* 2216 */     if (str == null) {
/* 2217 */       log.log(Level.FINE, new StringBuilder().append("Error in fwkFireResourceLoadEvent: unknown request id ").append(paramInt2).toString());
/* 2218 */       return;
/*      */     }
/*      */ 
/* 2221 */     int i = paramInt1;
/*      */ 
/* 2223 */     if (paramInt1 == 20) {
/* 2224 */       if (this.requestStarted.contains(Integer.valueOf(paramInt2)))
/* 2225 */         i = 21;
/*      */       else {
/* 2227 */         this.requestStarted.add(Integer.valueOf(paramInt2));
/*      */       }
/*      */     }
/*      */ 
/* 2231 */     fireResourceLoadEvent(paramLong, i, str, paramString, paramDouble, paramInt3);
/*      */   }
/*      */ 
/*      */   private boolean fwkPermitNavigateAction(long paramLong, String paramString) {
/* 2235 */     log.log(Level.FINE, new StringBuilder().append("Policy: permit NAVIGATE: pFrame = ").append(paramLong).append(", url = ").append(paramString).toString());
/*      */ 
/* 2237 */     if (this.policyClient != null) {
/* 2238 */       return this.policyClient.permitNavigateAction(paramLong, str2url(paramString));
/*      */     }
/* 2240 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean fwkPermitRedirectAction(long paramLong, String paramString) {
/* 2244 */     log.log(Level.FINE, new StringBuilder().append("Policy: permit REDIRECT: pFrame = ").append(paramLong).append(", url = ").append(paramString).toString());
/*      */ 
/* 2246 */     if (this.policyClient != null) {
/* 2247 */       return this.policyClient.permitRedirectAction(paramLong, str2url(paramString));
/*      */     }
/* 2249 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean fwkPermitAcceptResourceAction(long paramLong, String paramString) {
/* 2253 */     log.log(Level.FINE, new StringBuilder().append("Policy: permit ACCEPT_RESOURCE: pFrame + ").append(paramLong).append(", url = ").append(paramString).toString());
/*      */ 
/* 2255 */     if (this.policyClient != null) {
/* 2256 */       return this.policyClient.permitAcceptResourceAction(paramLong, str2url(paramString));
/*      */     }
/* 2258 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean fwkPermitSubmitDataAction(long paramLong, String paramString1, String paramString2, boolean paramBoolean)
/*      */   {
/* 2264 */     log.log(Level.FINE, new StringBuilder().append("Policy: permit ").append(paramBoolean ? "" : "RE").append("SUBMIT_DATA: pFrame = ").append(paramLong).append(", url = ").append(paramString1).append(", httpMethod = ").append(paramString2).toString());
/*      */ 
/* 2267 */     if (this.policyClient != null) {
/* 2268 */       if (paramBoolean) {
/* 2269 */         return this.policyClient.permitSubmitDataAction(paramLong, str2url(paramString1), paramString2);
/*      */       }
/* 2271 */       return this.policyClient.permitResubmitDataAction(paramLong, str2url(paramString1), paramString2);
/*      */     }
/*      */ 
/* 2274 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean fwkPermitEnableScriptsAction(long paramLong, String paramString) {
/* 2278 */     log.log(Level.FINE, new StringBuilder().append("Policy: permit ENABLE_SCRIPTS: pFrame + ").append(paramLong).append(", url = ").append(paramString).toString());
/*      */ 
/* 2280 */     if (this.policyClient != null) {
/* 2281 */       return this.policyClient.permitEnableScriptsAction(paramLong, str2url(paramString));
/*      */     }
/* 2283 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean fwkPermitNewWindowAction(long paramLong, String paramString) {
/* 2287 */     log.log(Level.FINE, new StringBuilder().append("Policy: permit NEW_PAGE: pFrame = ").append(paramLong).append(", url = ").append(paramString).toString());
/*      */ 
/* 2289 */     if (this.policyClient != null) {
/* 2290 */       return this.policyClient.permitNewPageAction(paramLong, str2url(paramString));
/*      */     }
/* 2292 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean permitCloseWindowAction()
/*      */   {
/* 2297 */     log.log(Level.FINE, "Policy: permit CLOSE_PAGE");
/*      */ 
/* 2299 */     if (this.policyClient != null)
/*      */     {
/* 2302 */       return this.policyClient.permitClosePageAction(getMainFrame());
/*      */     }
/* 2304 */     return true;
/*      */   }
/*      */ 
/*      */   private void fwkRepaintAll() {
/* 2308 */     log.log(Level.FINE, "Repainting the entire page");
/* 2309 */     repaintAll();
/*      */   }
/*      */ 
/*      */   private boolean fwkSendInspectorMessageToFrontend(String paramString) {
/* 2313 */     if (log.isLoggable(Level.FINE)) {
/* 2314 */       log.log(Level.FINE, "Sending inspector message to frontend, message: [{0}]", paramString);
/*      */     }
/*      */ 
/* 2318 */     boolean bool = false;
/* 2319 */     if (this.inspectorClient != null) {
/* 2320 */       log.log(Level.FINE, "Invoking inspector client");
/* 2321 */       bool = this.inspectorClient.sendMessageToFrontend(paramString);
/*      */     }
/* 2323 */     if (log.isLoggable(Level.FINE)) {
/* 2324 */       log.log(Level.FINE, "Result: [{0}]", Boolean.valueOf(bool));
/*      */     }
/* 2326 */     return bool;
/*      */   }
/*      */ 
/*      */   private void fwkDidClearWindowObject(long paramLong1, long paramLong2)
/*      */   {
/* 2332 */     didClearWindowObject(paramLong1, paramLong2);
/*      */   }
/*      */ 
/*      */   protected void didClearWindowObject(long paramLong1, long paramLong2)
/*      */   {
/*      */   }
/*      */ 
/*      */   private URL str2url(String paramString)
/*      */   {
/*      */     try
/*      */     {
/* 2345 */       return URLs.newURL(paramString);
/*      */     } catch (MalformedURLException localMalformedURLException) {
/* 2347 */       log.log(Level.FINE, new StringBuilder().append("Exception while converting \"").append(paramString).append("\" to URL").toString(), localMalformedURLException);
/*      */     }
/* 2349 */     return null;
/*      */   }
/*      */ 
/*      */   private void fireLoadEvent(long paramLong, int paramInt1, String paramString1, String paramString2, double paramDouble, int paramInt2)
/*      */   {
/* 2355 */     for (LoadListenerClient localLoadListenerClient : this.loadListenerClients)
/* 2356 */       localLoadListenerClient.dispatchLoadEvent(paramLong, paramInt1, paramString1, paramString2, paramDouble, paramInt2);
/*      */   }
/*      */ 
/*      */   private void fireResourceLoadEvent(long paramLong, int paramInt1, String paramString1, String paramString2, double paramDouble, int paramInt2)
/*      */   {
/* 2363 */     for (LoadListenerClient localLoadListenerClient : this.loadListenerClients)
/* 2364 */       localLoadListenerClient.dispatchResourceLoadEvent(paramLong, paramInt1, paramString1, paramString2, paramDouble, paramInt2);
/*      */   }
/*      */ 
/*      */   private void repaintAll()
/*      */   {
/* 2369 */     if (this.pageClient != null) {
/* 2370 */       markDirty();
/* 2371 */       this.pageClient.repaint(0, 0, this.width, this.height, true, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   private native long twkCreatePage(boolean paramBoolean);
/*      */ 
/*      */   private native void twkInit(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   private native void twkDestroyPage(long paramLong);
/*      */ 
/*      */   private native long twkGetMainFrame(long paramLong);
/*      */ 
/*      */   private native long twkGetParentFrame(long paramLong);
/*      */ 
/*      */   private native long[] twkGetChildFrames(long paramLong);
/*      */ 
/*      */   private native String twkGetName(long paramLong);
/*      */ 
/*      */   private native String twkGetURL(long paramLong);
/*      */ 
/*      */   private native String twkGetInnerText(long paramLong);
/*      */ 
/*      */   private native String twkGetRenderTree(long paramLong);
/*      */ 
/*      */   private native String twkGetContentType(long paramLong);
/*      */ 
/*      */   private native String twkGetTitle(long paramLong);
/*      */ 
/*      */   private native String twkGetIconURL(long paramLong);
/*      */ 
/*      */   private static native Document twkGetDocument(long paramLong);
/*      */ 
/*      */   private static native Element twkGetOwnerElement(long paramLong);
/*      */ 
/*      */   private native void twkOpen(long paramLong, String paramString);
/*      */ 
/*      */   private native void twkLoad(long paramLong, String paramString1, String paramString2);
/*      */ 
/*      */   private native void twkStop(long paramLong);
/*      */ 
/*      */   private native void twkStopAll(long paramLong);
/*      */ 
/*      */   private native void twkRefresh(long paramLong);
/*      */ 
/*      */   private native boolean twkGoBackForward(long paramLong, int paramInt);
/*      */ 
/*      */   private native boolean twkCopy(long paramLong);
/*      */ 
/*      */   private native boolean twkFindInPage(long paramLong, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
/*      */ 
/*      */   private native boolean twkFindInFrame(long paramLong, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
/*      */ 
/*      */   private native float twkGetZoomFactor(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   private native void twkSetZoomFactor(long paramLong, float paramFloat, boolean paramBoolean);
/*      */ 
/*      */   private native Object twkExecuteScript(long paramLong, String paramString);
/*      */ 
/*      */   private native void twkAddJavaScriptBinding(long paramLong, String paramString, Object paramObject, AccessControlContext paramAccessControlContext);
/*      */ 
/*      */   private native void twkReset(long paramLong);
/*      */ 
/*      */   private native int twkGetFrameHeight(long paramLong);
/*      */ 
/*      */   private native int twkStartPrinting(long paramLong, float paramFloat, boolean paramBoolean);
/*      */ 
/*      */   private native void twkEndPrinting(long paramLong);
/*      */ 
/*      */   private native float twkAdjustFrameHeight(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3);
/*      */ 
/*      */   private native int[] twkGetVisibleRect(long paramLong);
/*      */ 
/*      */   private native void twkScrollToPosition(long paramLong, int paramInt1, int paramInt2);
/*      */ 
/*      */   private native int[] twkGetContentSize(long paramLong);
/*      */ 
/*      */   private native void twkSetTransparent(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   private native void twkSetBackgroundColor(long paramLong, int paramInt);
/*      */ 
/*      */   private native void twkSetBounds(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*      */ 
/*      */   private native void twkPrint(long paramLong, WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*      */ 
/*      */   private native void twkUpdateContent(long paramLong, WCRenderQueue paramWCRenderQueue, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*      */ 
/*      */   private native void twkDrawHighlight(long paramLong, WCRenderQueue paramWCRenderQueue);
/*      */ 
/*      */   private native String twkGetEncoding(long paramLong);
/*      */ 
/*      */   private native void twkSetEncoding(long paramLong, String paramString);
/*      */ 
/*      */   private native void twkProcessFocusEvent(long paramLong, int paramInt1, int paramInt2);
/*      */ 
/*      */   private native boolean twkProcessKeyEvent(long paramLong, int paramInt1, String paramString1, String paramString2, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);
/*      */ 
/*      */   private native boolean twkProcessMouseEvent(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, float paramFloat);
/*      */ 
/*      */   private native boolean twkProcessMouseWheelEvent(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, float paramFloat3);
/*      */ 
/*      */   private native boolean twkProcessTouchEvent(long paramLong, int paramInt, ByteBuffer paramByteBuffer, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, float paramFloat);
/*      */ 
/*      */   private native boolean twkProcessInputTextChange(long paramLong, String paramString1, String paramString2, int[] paramArrayOfInt, int paramInt);
/*      */ 
/*      */   private native boolean twkProcessCaretPositionChange(long paramLong, int paramInt);
/*      */ 
/*      */   private native int[] twkGetTextLocation(long paramLong, int paramInt);
/*      */ 
/*      */   private native int twkGetInsertPositionOffset(long paramLong);
/*      */ 
/*      */   private native int twkGetCommittedTextLength(long paramLong);
/*      */ 
/*      */   private native String twkGetCommittedText(long paramLong);
/*      */ 
/*      */   private native String twkGetSelectedText(long paramLong);
/*      */ 
/*      */   private native int twkProcessDrag(long paramLong, int paramInt1, String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*      */ 
/*      */   private native boolean twkExecuteCommand(long paramLong, String paramString1, String paramString2);
/*      */ 
/*      */   private native boolean twkQueryCommandEnabled(long paramLong, String paramString);
/*      */ 
/*      */   private native boolean twkQueryCommandState(long paramLong, String paramString);
/*      */ 
/*      */   private native String twkQueryCommandValue(long paramLong, String paramString);
/*      */ 
/*      */   private native boolean twkIsEditable(long paramLong);
/*      */ 
/*      */   private native void twkSetEditable(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   private native String twkGetHtml(long paramLong);
/*      */ 
/*      */   private native boolean twkGetUsePageCache(long paramLong);
/*      */ 
/*      */   private native void twkSetUsePageCache(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   private native boolean twkGetDeveloperExtrasEnabled(long paramLong);
/*      */ 
/*      */   private native void twkSetDeveloperExtrasEnabled(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   private native boolean twkIsJavaScriptEnabled(long paramLong);
/*      */ 
/*      */   private native void twkSetJavaScriptEnabled(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   private native boolean twkIsContextMenuEnabled(long paramLong);
/*      */ 
/*      */   private native void twkSetContextMenuEnabled(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   private native void twkSetUserStyleSheetLocation(long paramLong, String paramString);
/*      */ 
/*      */   private native int twkGetUnloadEventListenersCount(long paramLong);
/*      */ 
/*      */   private native void twkConnectInspectorFrontend(long paramLong);
/*      */ 
/*      */   private native void twkDisconnectInspectorFrontend(long paramLong);
/*      */ 
/*      */   private native void twkDispatchInspectorMessageFromFrontend(long paramLong, String paramString);
/*      */ 
/*      */   static
/*      */   {
/*  118 */     AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Void run() {
/*  120 */         String str1 = System.getProperty("os.name");
/*  121 */         String str2 = System.getProperty("os.arch");
/*      */ 
/*  123 */         String str3 = null;
/*  124 */         if (str1.startsWith("Windows"))
/*  125 */           str3 = "windows";
/*  126 */         else if (str1.startsWith("Linux"))
/*  127 */           str3 = "linux";
/*  128 */         else if (str1.startsWith("SunOS")) {
/*  129 */           str3 = "solaris";
/*      */         }
/*      */ 
/*  132 */         if (str3 != null) {
/*  133 */           String str4 = ConfigManager.getProperty(str3 + ".libs");
/*  134 */           if (str4 != null) {
/*  135 */             String[] arrayOfString1 = str4.split("\\s*,\\s*");
/*      */             try
/*      */             {
/*  138 */               WebPage.log.finer("loading " + str1 + " native libraries ...");
/*  139 */               for (String str5 : arrayOfString1) {
/*  140 */                 NativeLibLoader.loadLibrary(str5);
/*  141 */                 WebPage.log.finer(str5 + " loaded");
/*      */               }
/*      */             } catch (UnsatisfiedLinkError localUnsatisfiedLinkError) {
/*  144 */               WebPage.log.finer("Cannot load libraries that webkit depends on. So, trying to load WebKitJava relying on system library path.");
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  149 */             WebPage.log.finer("No extra libraries to load.");
/*      */           }
/*      */         } else {
/*  152 */           WebPage.log.finer(str1 + " (" + str2 + ") is not supported. " + "Trying to load WebKitJava relying on " + "system library path.");
/*      */         }
/*      */ 
/*  156 */         NativeLibLoader.loadLibrary("jfxwebkit");
/*  157 */         WebPage.log.finer("jfxwebkit loaded");
/*      */ 
/*  159 */         return null;
/*      */       }
/*      */     });
/*  163 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Void run() {
/*  166 */         if (CookieHandler.getDefault() == null) {
/*  167 */           boolean bool = Boolean.valueOf(System.getProperty("com.sun.webpane.setDefaultCookieHandler", "true")).booleanValue();
/*      */ 
/*  170 */           if (bool) {
/*  171 */             CookieHandler.setDefault(new CookieManager());
/*      */           }
/*      */         }
/*  174 */         return null;
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private static class ScrollRequest
/*      */   {
/*      */     int x;
/*      */     int y;
/*      */     int w;
/*      */     int h;
/*      */     int dx;
/*      */     int dy;
/*      */ 
/*      */     ScrollRequest(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*      */     {
/*  281 */       this.x = paramInt1;
/*  282 */       this.y = paramInt2;
/*  283 */       this.w = paramInt3;
/*  284 */       this.h = paramInt4;
/*  285 */       this.dx = paramInt5;
/*  286 */       this.dy = paramInt6;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.WebPage
 * JD-Core Version:    0.6.2
 */