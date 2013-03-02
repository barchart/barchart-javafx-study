/*      */ package javafx.scene.web;
/*      */ 
/*      */ import com.sun.javafx.scene.web.Debugger;
/*      */ import com.sun.javafx.tk.TKPulseListener;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import com.sun.webpane.platform.Disposer;
/*      */ import com.sun.webpane.platform.DisposerRecord;
/*      */ import com.sun.webpane.platform.InspectorClient;
/*      */ import com.sun.webpane.platform.LoadListenerClient;
/*      */ import com.sun.webpane.platform.WebPage;
/*      */ import com.sun.webpane.sg.Accessor;
/*      */ import com.sun.webpane.sg.Accessor.PageAccessor;
/*      */ import com.sun.webpane.sg.ImplementationManager;
/*      */ import com.sun.webpane.sg.PGWebView;
/*      */ import com.sun.webpane.webkit.Timer;
/*      */ import com.sun.webpane.webkit.Timer.Mode;
/*      */ import com.sun.webpane.webkit.network.URLs;
/*      */ import com.sun.webpane.webkit.network.Util;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import javafx.animation.AnimationTimer;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.BooleanPropertyBase;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*      */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*      */ import javafx.beans.property.ReadOnlyDoubleWrapper;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectPropertyBase;
/*      */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*      */ import javafx.beans.property.ReadOnlyStringProperty;
/*      */ import javafx.beans.property.ReadOnlyStringWrapper;
/*      */ import javafx.beans.property.SimpleObjectProperty;
/*      */ import javafx.beans.property.StringProperty;
/*      */ import javafx.beans.property.StringPropertyBase;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.concurrent.Worker;
/*      */ import javafx.concurrent.Worker.State;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.geometry.Rectangle2D;
/*      */ import javafx.scene.Node;
/*      */ import javafx.util.Callback;
/*      */ import org.w3c.dom.Document;
/*      */ import sun.misc.BASE64Encoder;
/*      */ 
/*      */ public final class WebEngine
/*      */ {
/*  291 */   private static int instanceCount = 0;
/*      */ 
/*  298 */   private final ObjectProperty<WebView> view = new SimpleObjectProperty(this, "view");
/*      */ 
/*  303 */   private final LoadWorker loadWorker = new LoadWorker(null);
/*      */   private final WebPage page;
/*  313 */   private final DebuggerImpl debugger = new DebuggerImpl(null);
/*      */ 
/*  327 */   private final DocumentProperty document = new DocumentProperty(null);
/*      */ 
/*  347 */   private final ReadOnlyStringWrapper location = new ReadOnlyStringWrapper(this, "location");
/*      */ 
/*  371 */   private final ReadOnlyStringWrapper title = new ReadOnlyStringWrapper(this, "title");
/*      */   private BooleanProperty javaScriptEnabled;
/*      */   private StringProperty userStyleSheetLocation;
/*  496 */   private ObjectProperty<EventHandler<WebEvent<String>>> onAlert = new SimpleObjectProperty(this, "onAlert");
/*      */ 
/*  520 */   private ObjectProperty<EventHandler<WebEvent<String>>> onStatusChanged = new SimpleObjectProperty(this, "onStatusChanged");
/*      */ 
/*  544 */   private ObjectProperty<EventHandler<WebEvent<Rectangle2D>>> onResized = new SimpleObjectProperty(this, "onResized");
/*      */ 
/*  569 */   private ObjectProperty<EventHandler<WebEvent<Boolean>>> onVisibilityChanged = new SimpleObjectProperty(this, "onVisibilityChanged");
/*      */ 
/*  594 */   private ObjectProperty<Callback<PopupFeatures, WebEngine>> createPopupHandler = new SimpleObjectProperty(this, "createPopupHandler", new Callback()
/*      */   {
/*      */     public WebEngine call(PopupFeatures paramAnonymousPopupFeatures)
/*      */     {
/*  598 */       return WebEngine.this;
/*      */     }
/*      */   });
/*      */ 
/*  632 */   private ObjectProperty<Callback<String, Boolean>> confirmHandler = new SimpleObjectProperty(this, "confirmHandler");
/*      */ 
/*  658 */   private ObjectProperty<Callback<PromptData, String>> promptHandler = new SimpleObjectProperty(this, "promptHandler");
/*      */   private WebHistory history;
/*      */ 
/*      */   public final Worker<Void> getLoadWorker()
/*      */   {
/*  320 */     return this.loadWorker;
/*      */   }
/*      */ 
/*      */   public final Document getDocument()
/*      */   {
/*  333 */     return (Document)this.document.getValue();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<Document> documentProperty()
/*      */   {
/*  340 */     return this.document;
/*      */   }
/*      */ 
/*      */   public final String getLocation()
/*      */   {
/*  353 */     return this.location.getValue();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyStringProperty locationProperty()
/*      */   {
/*  359 */     return this.location.getReadOnlyProperty();
/*      */   }
/*      */   private void updateLocation(String paramString) {
/*  362 */     this.location.set(paramString);
/*  363 */     this.document.invalidate(false);
/*  364 */     this.title.set(null);
/*      */   }
/*      */ 
/*      */   public final String getTitle()
/*      */   {
/*  377 */     return this.title.getValue();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyStringProperty titleProperty()
/*      */   {
/*  383 */     return this.title.getReadOnlyProperty();
/*      */   }
/*      */   private void updateTitle() {
/*  386 */     this.title.set(this.page.getTitle(this.page.getMainFrame()));
/*      */   }
/*      */ 
/*      */   public final void setJavaScriptEnabled(boolean paramBoolean)
/*      */   {
/*  401 */     javaScriptEnabledProperty().set(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final boolean isJavaScriptEnabled() {
/*  405 */     return this.javaScriptEnabled == null ? true : this.javaScriptEnabled.get();
/*      */   }
/*      */ 
/*      */   public final BooleanProperty javaScriptEnabledProperty() {
/*  409 */     if (this.javaScriptEnabled == null) {
/*  410 */       this.javaScriptEnabled = new BooleanPropertyBase(true) {
/*      */         public void invalidated() {
/*  412 */           WebEngine.checkThread();
/*  413 */           WebEngine.this.page.setJavaScriptEnabled(get());
/*      */         }
/*      */ 
/*      */         public Object getBean() {
/*  417 */           return WebEngine.this;
/*      */         }
/*      */ 
/*      */         public String getName() {
/*  421 */           return "javaScriptEnabled";
/*      */         }
/*      */       };
/*      */     }
/*  425 */     return this.javaScriptEnabled;
/*      */   }
/*      */ 
/*      */   public final void setUserStyleSheetLocation(String paramString)
/*      */   {
/*  441 */     userStyleSheetLocationProperty().set(paramString);
/*      */   }
/*      */ 
/*      */   public final String getUserStyleSheetLocation()
/*      */   {
/*  446 */     return this.userStyleSheetLocation == null ? null : (String)this.userStyleSheetLocation.get();
/*      */   }
/*      */ 
/*      */   public final StringProperty userStyleSheetLocationProperty() {
/*  450 */     if (this.userStyleSheetLocation == null) {
/*  451 */       this.userStyleSheetLocation = new StringPropertyBase(null) {
/*      */         private static final String DATA_PREFIX = "data:text/css;charset=utf-8;base64,";
/*      */ 
/*      */         public void invalidated() {
/*  455 */           WebEngine.checkThread();
/*  456 */           String str1 = get();
/*      */           String str2;
/*  458 */           if ((str1 == null) || (str1.length() <= 0))
/*  459 */             str2 = null;
/*  460 */           else if (str1.startsWith("data:text/css;charset=utf-8;base64,"))
/*  461 */             str2 = str1;
/*  462 */           else if ((str1.startsWith("file:")) || (str1.startsWith("jar:")) || (str1.startsWith("data:")))
/*      */           {
/*      */             try
/*      */             {
/*  467 */               URLConnection localURLConnection = URLs.newURL(str1).openConnection();
/*  468 */               localURLConnection.connect();
/*      */ 
/*  470 */               BufferedInputStream localBufferedInputStream = new BufferedInputStream(localURLConnection.getInputStream());
/*      */ 
/*  472 */               ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/*  473 */               new BASE64Encoder().encodeBuffer(localBufferedInputStream, localByteArrayOutputStream);
/*  474 */               str2 = "data:text/css;charset=utf-8;base64," + localByteArrayOutputStream.toString();
/*      */             } catch (IOException localIOException) {
/*  476 */               throw new RuntimeException(localIOException);
/*      */             }
/*      */           }
/*  479 */           else throw new IllegalArgumentException("Invalid stylesheet URL");
/*      */ 
/*  481 */           WebEngine.this.page.setUserStyleSheetLocation(str2);
/*      */         }
/*      */ 
/*      */         public Object getBean() {
/*  485 */           return WebEngine.this;
/*      */         }
/*      */ 
/*      */         public String getName() {
/*  489 */           return "userStyleSheetLocation";
/*      */         }
/*      */       };
/*      */     }
/*  493 */     return this.userStyleSheetLocation;
/*      */   }
/*      */ 
/*      */   public final EventHandler<WebEvent<String>> getOnAlert()
/*      */   {
/*  504 */     return (EventHandler)this.onAlert.get();
/*      */   }
/*      */ 
/*      */   public final void setOnAlert(EventHandler<WebEvent<String>> paramEventHandler)
/*      */   {
/*  511 */     this.onAlert.set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WebEvent<String>>> onAlertProperty()
/*      */   {
/*  517 */     return this.onAlert;
/*      */   }
/*      */ 
/*      */   public final EventHandler<WebEvent<String>> getOnStatusChanged()
/*      */   {
/*  528 */     return (EventHandler)this.onStatusChanged.get();
/*      */   }
/*      */ 
/*      */   public final void setOnStatusChanged(EventHandler<WebEvent<String>> paramEventHandler)
/*      */   {
/*  535 */     this.onStatusChanged.set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WebEvent<String>>> onStatusChangedProperty()
/*      */   {
/*  541 */     return this.onStatusChanged;
/*      */   }
/*      */ 
/*      */   public final EventHandler<WebEvent<Rectangle2D>> getOnResized()
/*      */   {
/*  552 */     return (EventHandler)this.onResized.get();
/*      */   }
/*      */ 
/*      */   public final void setOnResized(EventHandler<WebEvent<Rectangle2D>> paramEventHandler)
/*      */   {
/*  559 */     this.onResized.set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WebEvent<Rectangle2D>>> onResizedProperty()
/*      */   {
/*  566 */     return this.onResized;
/*      */   }
/*      */ 
/*      */   public final EventHandler<WebEvent<Boolean>> getOnVisibilityChanged()
/*      */   {
/*  577 */     return (EventHandler)this.onVisibilityChanged.get();
/*      */   }
/*      */ 
/*      */   public final void setOnVisibilityChanged(EventHandler<WebEvent<Boolean>> paramEventHandler)
/*      */   {
/*  584 */     this.onVisibilityChanged.set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WebEvent<Boolean>>> onVisibilityChangedProperty()
/*      */   {
/*  591 */     return this.onVisibilityChanged;
/*      */   }
/*      */ 
/*      */   public final Callback<PopupFeatures, WebEngine> getCreatePopupHandler()
/*      */   {
/*  607 */     return (Callback)this.createPopupHandler.get();
/*      */   }
/*      */ 
/*      */   public final void setCreatePopupHandler(Callback<PopupFeatures, WebEngine> paramCallback)
/*      */   {
/*  615 */     this.createPopupHandler.set(paramCallback);
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Callback<PopupFeatures, WebEngine>> createPopupHandlerProperty()
/*      */   {
/*  629 */     return this.createPopupHandler;
/*      */   }
/*      */ 
/*      */   public final Callback<String, Boolean> getConfirmHandler()
/*      */   {
/*  640 */     return (Callback)this.confirmHandler.get();
/*      */   }
/*      */ 
/*      */   public final void setConfirmHandler(Callback<String, Boolean> paramCallback)
/*      */   {
/*  647 */     this.confirmHandler.set(paramCallback);
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Callback<String, Boolean>> confirmHandlerProperty()
/*      */   {
/*  655 */     return this.confirmHandler;
/*      */   }
/*      */ 
/*      */   public final Callback<PromptData, String> getPromptHandler()
/*      */   {
/*  667 */     return (Callback)this.promptHandler.get();
/*      */   }
/*      */ 
/*      */   public final void setPromptHandler(Callback<PromptData, String> paramCallback)
/*      */   {
/*  675 */     this.promptHandler.set(paramCallback);
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Callback<PromptData, String>> promptHandlerProperty()
/*      */   {
/*  685 */     return this.promptHandler;
/*      */   }
/*      */ 
/*      */   public WebEngine()
/*      */   {
/*  691 */     this(null);
/*      */   }
/*      */ 
/*      */   public WebEngine(String paramString)
/*      */   {
/*  698 */     if ((instanceCount == 0) && (Timer.getMode() == Timer.Mode.PLATFORM_TICKS))
/*      */     {
/*  701 */       PulseTimer.start();
/*      */     }
/*  703 */     AccessorImpl localAccessorImpl = new AccessorImpl(this);
/*  704 */     this.page = ImplementationManager.createPage(localAccessorImpl);
/*  705 */     this.page.addLoadListenerClient(new PageLoadListener(this));
/*  706 */     this.page.setInspectorClient(new InspectorClientImpl(this));
/*      */ 
/*  708 */     this.history = new WebHistory(this.page);
/*      */ 
/*  710 */     Disposer.addRecord(this, new SelfDisposer(this.page));
/*      */ 
/*  712 */     load(paramString);
/*      */ 
/*  714 */     instanceCount += 1;
/*      */   }
/*      */ 
/*      */   public void load(String paramString)
/*      */   {
/*  723 */     checkThread();
/*  724 */     this.loadWorker.cancelAndReset();
/*      */ 
/*  726 */     if (paramString == null) {
/*  727 */       paramString = "";
/*      */     }
/*      */     else {
/*      */       try
/*      */       {
/*  732 */         paramString = Util.adjustUrlForWebKit(paramString);
/*      */       } catch (MalformedURLException localMalformedURLException) {
/*  734 */         this.loadWorker.dispatchLoadEvent(getMainFrame(), 0, paramString, null, 0.0D, 0);
/*      */ 
/*  736 */         this.loadWorker.dispatchLoadEvent(getMainFrame(), 5, paramString, null, 0.0D, 2);
/*      */       }
/*      */     }
/*      */ 
/*  740 */     this.page.open(this.page.getMainFrame(), paramString);
/*      */   }
/*      */ 
/*      */   public void loadContent(String paramString)
/*      */   {
/*  750 */     loadContent(paramString, "text/html");
/*      */   }
/*      */ 
/*      */   public void loadContent(String paramString1, String paramString2)
/*      */   {
/*  762 */     checkThread();
/*  763 */     this.loadWorker.cancelAndReset();
/*  764 */     this.page.load(this.page.getMainFrame(), paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   public void reload()
/*      */   {
/*  773 */     checkThread();
/*  774 */     this.page.refresh(this.page.getMainFrame());
/*      */   }
/*      */ 
/*      */   public WebHistory getHistory()
/*      */   {
/*  786 */     return this.history;
/*      */   }
/*      */ 
/*      */   public Object executeScript(String paramString)
/*      */   {
/*  810 */     checkThread();
/*  811 */     return this.page.executeScript(this.page.getMainFrame(), paramString);
/*      */   }
/*      */ 
/*      */   private long getMainFrame() {
/*  815 */     return this.page.getMainFrame();
/*      */   }
/*      */ 
/*      */   WebPage getPage() {
/*  819 */     return this.page;
/*      */   }
/*      */ 
/*      */   void setView(WebView paramWebView) {
/*  823 */     this.view.setValue(paramWebView);
/*      */   }
/*      */ 
/*      */   private void stop() {
/*  827 */     checkThread();
/*  828 */     this.page.stop(this.page.getMainFrame());
/*      */   }
/*      */ 
/*      */   static void checkThread()
/*      */   {
/*  931 */     Toolkit.getToolkit().checkFxUserThread();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public Debugger impl_getDebugger()
/*      */   {
/* 1207 */     return this.debugger;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  280 */     Accessor.setPageAccessor(new Accessor.PageAccessor() {
/*      */       public WebPage getPage(WebEngine paramAnonymousWebEngine) {
/*  282 */         return paramAnonymousWebEngine == null ? null : paramAnonymousWebEngine.getPage();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private static class InspectorClientImpl
/*      */     implements InspectorClient
/*      */   {
/*      */     private final WeakReference<WebEngine> engine;
/*      */ 
/*      */     InspectorClientImpl(WebEngine paramWebEngine)
/*      */     {
/* 1276 */       this.engine = new WeakReference(paramWebEngine);
/*      */     }
/*      */ 
/*      */     public boolean sendMessageToFrontend(String paramString)
/*      */     {
/* 1282 */       boolean bool = false;
/* 1283 */       WebEngine localWebEngine = (WebEngine)this.engine.get();
/* 1284 */       if (localWebEngine != null) {
/* 1285 */         Callback localCallback = WebEngine.DebuggerImpl.access$1600(localWebEngine.debugger);
/*      */ 
/* 1287 */         if (localCallback != null) {
/* 1288 */           localCallback.call(paramString);
/* 1289 */           bool = true;
/*      */         }
/*      */       }
/* 1292 */       return bool;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class DebuggerImpl
/*      */     implements Debugger
/*      */   {
/*      */     private boolean enabled;
/*      */     private Callback<String, Void> messageCallback;
/*      */ 
/*      */     private DebuggerImpl()
/*      */     {
/*      */     }
/*      */ 
/*      */     public boolean isEnabled()
/*      */     {
/* 1221 */       WebEngine.checkThread();
/* 1222 */       return this.enabled;
/*      */     }
/*      */ 
/*      */     public void setEnabled(boolean paramBoolean)
/*      */     {
/* 1227 */       WebEngine.checkThread();
/* 1228 */       if (paramBoolean != this.enabled) {
/* 1229 */         if (paramBoolean) {
/* 1230 */           WebEngine.this.page.setDeveloperExtrasEnabled(true);
/* 1231 */           WebEngine.this.page.connectInspectorFrontend();
/*      */         } else {
/* 1233 */           WebEngine.this.page.disconnectInspectorFrontend();
/* 1234 */           WebEngine.this.page.setDeveloperExtrasEnabled(false);
/*      */         }
/* 1236 */         this.enabled = paramBoolean;
/*      */       }
/*      */     }
/*      */ 
/*      */     public void sendMessage(String paramString)
/*      */     {
/* 1242 */       WebEngine.checkThread();
/* 1243 */       if (!this.enabled) {
/* 1244 */         throw new IllegalStateException("Debugger is not enabled");
/*      */       }
/* 1246 */       if (paramString == null) {
/* 1247 */         throw new NullPointerException("message is null");
/*      */       }
/* 1249 */       WebEngine.this.page.dispatchInspectorMessageFromFrontend(paramString);
/*      */     }
/*      */ 
/*      */     public Callback<String, Void> getMessageCallback()
/*      */     {
/* 1254 */       WebEngine.checkThread();
/* 1255 */       return this.messageCallback;
/*      */     }
/*      */ 
/*      */     public void setMessageCallback(Callback<String, Void> paramCallback)
/*      */     {
/* 1260 */       WebEngine.checkThread();
/* 1261 */       this.messageCallback = paramCallback;
/*      */     }
/*      */   }
/*      */ 
/*      */   private final class DocumentProperty extends ReadOnlyObjectPropertyBase<Document>
/*      */   {
/*      */     private boolean available;
/*      */     private Document document;
/*      */ 
/*      */     private DocumentProperty()
/*      */     {
/*      */     }
/*      */ 
/*      */     private void invalidate(boolean paramBoolean)
/*      */     {
/* 1159 */       if ((this.available) || (paramBoolean)) {
/* 1160 */         this.available = paramBoolean;
/* 1161 */         this.document = null;
/* 1162 */         fireValueChangedEvent();
/*      */       }
/*      */     }
/*      */ 
/*      */     public Document get() {
/* 1167 */       if (!this.available) {
/* 1168 */         return null;
/*      */       }
/* 1170 */       if (this.document == null) {
/* 1171 */         this.document = WebEngine.this.page.getDocument(WebEngine.this.page.getMainFrame());
/* 1172 */         if (this.document == null) {
/* 1173 */           this.available = false;
/*      */         }
/*      */       }
/* 1176 */       return this.document;
/*      */     }
/*      */ 
/*      */     public Object getBean() {
/* 1180 */       return WebEngine.this;
/*      */     }
/*      */ 
/*      */     public String getName() {
/* 1184 */       return "document";
/*      */     }
/*      */   }
/*      */ 
/*      */   private class LoadWorker
/*      */     implements Worker<Void>
/*      */   {
/*  970 */     private ReadOnlyObjectWrapper<Worker.State> state = new ReadOnlyObjectWrapper(this, "state", Worker.State.READY);
/*      */ 
/*  982 */     private ReadOnlyObjectWrapper<Void> value = new ReadOnlyObjectWrapper(this, "value", null);
/*      */ 
/*  989 */     private ReadOnlyObjectWrapper<Throwable> exception = new ReadOnlyObjectWrapper(this, "exception");
/*      */ 
/*  996 */     private ReadOnlyDoubleWrapper workDone = new ReadOnlyDoubleWrapper(this, "workDone", -1.0D);
/*      */ 
/* 1003 */     private ReadOnlyDoubleWrapper totalWorkToBeDone = new ReadOnlyDoubleWrapper(this, "totalWork", -1.0D);
/*      */ 
/* 1010 */     private ReadOnlyDoubleWrapper progress = new ReadOnlyDoubleWrapper(this, "progress", -1.0D);
/*      */ 
/* 1022 */     private ReadOnlyBooleanWrapper running = new ReadOnlyBooleanWrapper(this, "running", false);
/*      */ 
/* 1029 */     private ReadOnlyStringWrapper message = new ReadOnlyStringWrapper(this, "message", "");
/*      */ 
/* 1036 */     private ReadOnlyStringWrapper title = new ReadOnlyStringWrapper(this, "title", "WebEngine Loader");
/*      */ 
/*      */     private LoadWorker()
/*      */     {
/*      */     }
/*      */ 
/*      */     public final Worker.State getState()
/*      */     {
/*  971 */       WebEngine.checkThread(); return (Worker.State)this.state.get(); } 
/*  972 */     public final ReadOnlyObjectProperty<Worker.State> stateProperty() { WebEngine.checkThread(); return this.state.getReadOnlyProperty(); } 
/*      */     private final void updateState(Worker.State paramState) {
/*  974 */       WebEngine.checkThread();
/*  975 */       this.state.set(paramState);
/*  976 */       this.running.set((paramState == Worker.State.SCHEDULED) || (paramState == Worker.State.RUNNING));
/*      */     }
/*      */ 
/*      */     public final Void getValue()
/*      */     {
/*  983 */       WebEngine.checkThread(); return (Void)this.value.get(); } 
/*  984 */     public final ReadOnlyObjectProperty<Void> valueProperty() { WebEngine.checkThread(); return this.value.getReadOnlyProperty();
/*      */     }
/*      */ 
/*      */     public final Throwable getException()
/*      */     {
/*  990 */       WebEngine.checkThread(); return (Throwable)this.exception.get(); } 
/*  991 */     public final ReadOnlyObjectProperty<Throwable> exceptionProperty() { WebEngine.checkThread(); return this.exception.getReadOnlyProperty();
/*      */     }
/*      */ 
/*      */     public final double getWorkDone()
/*      */     {
/*  997 */       WebEngine.checkThread(); return this.workDone.get(); } 
/*  998 */     public final ReadOnlyDoubleProperty workDoneProperty() { WebEngine.checkThread(); return this.workDone.getReadOnlyProperty();
/*      */     }
/*      */ 
/*      */     public final double getTotalWork()
/*      */     {
/* 1004 */       WebEngine.checkThread(); return this.totalWorkToBeDone.get(); } 
/* 1005 */     public final ReadOnlyDoubleProperty totalWorkProperty() { WebEngine.checkThread(); return this.totalWorkToBeDone.getReadOnlyProperty();
/*      */     }
/*      */ 
/*      */     public final double getProgress()
/*      */     {
/* 1011 */       WebEngine.checkThread(); return this.progress.get(); } 
/* 1012 */     public final ReadOnlyDoubleProperty progressProperty() { WebEngine.checkThread(); return this.progress.getReadOnlyProperty(); } 
/*      */     private void updateProgress(double paramDouble) {
/* 1014 */       this.totalWorkToBeDone.set(100.0D);
/* 1015 */       this.workDone.set(paramDouble * 100.0D);
/* 1016 */       this.progress.set(paramDouble);
/*      */     }
/*      */ 
/*      */     public final boolean isRunning()
/*      */     {
/* 1023 */       WebEngine.checkThread(); return this.running.get(); } 
/* 1024 */     public final ReadOnlyBooleanProperty runningProperty() { WebEngine.checkThread(); return this.running.getReadOnlyProperty();
/*      */     }
/*      */ 
/*      */     public final String getMessage()
/*      */     {
/* 1030 */       return this.message.get(); } 
/* 1031 */     public final ReadOnlyStringProperty messageProperty() { return this.message.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */     public final String getTitle()
/*      */     {
/* 1037 */       return this.title.get(); } 
/* 1038 */     public final ReadOnlyStringProperty titleProperty() { return this.title.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */     public boolean cancel()
/*      */     {
/* 1045 */       if (isRunning()) {
/* 1046 */         WebEngine.this.stop();
/* 1047 */         return true;
/*      */       }
/* 1049 */       return false;
/*      */     }
/*      */ 
/*      */     private void cancelAndReset()
/*      */     {
/* 1054 */       cancel();
/* 1055 */       this.exception.set(null);
/* 1056 */       this.message.set("");
/* 1057 */       this.totalWorkToBeDone.set(-1.0D);
/* 1058 */       this.workDone.set(-1.0D);
/* 1059 */       this.progress.set(-1.0D);
/* 1060 */       updateState(Worker.State.READY);
/* 1061 */       this.running.set(false);
/*      */     }
/*      */ 
/*      */     private void dispatchLoadEvent(long paramLong, int paramInt1, String paramString1, String paramString2, double paramDouble, int paramInt2)
/*      */     {
/* 1067 */       if (paramLong != WebEngine.this.getMainFrame()) {
/* 1068 */         return;
/*      */       }
/*      */ 
/* 1071 */       switch (paramInt1) {
/*      */       case 0:
/* 1073 */         this.message.set("Loading " + paramString1);
/* 1074 */         WebEngine.this.updateLocation(paramString1);
/* 1075 */         updateProgress(0.0D);
/* 1076 */         updateState(Worker.State.SCHEDULED);
/* 1077 */         updateState(Worker.State.RUNNING);
/* 1078 */         break;
/*      */       case 2:
/* 1080 */         this.message.set("Loading " + paramString1);
/* 1081 */         WebEngine.this.updateLocation(paramString1);
/* 1082 */         break;
/*      */       case 1:
/* 1084 */         this.message.set("Loading complete");
/* 1085 */         updateProgress(1.0D);
/* 1086 */         updateState(Worker.State.SUCCEEDED);
/* 1087 */         break;
/*      */       case 5:
/* 1089 */         this.message.set("Loading failed");
/* 1090 */         this.exception.set(describeError(paramInt2));
/* 1091 */         updateState(Worker.State.FAILED);
/* 1092 */         break;
/*      */       case 6:
/* 1094 */         this.message.set("Loading stopped");
/* 1095 */         updateState(Worker.State.CANCELLED);
/* 1096 */         break;
/*      */       case 30:
/* 1098 */         updateProgress(paramDouble);
/* 1099 */         break;
/*      */       case 11:
/* 1101 */         WebEngine.this.updateTitle();
/* 1102 */         break;
/*      */       case 14:
/* 1104 */         WebEngine.this.document.invalidate(true);
/*      */       }
/*      */     }
/*      */ 
/*      */     Throwable describeError(int paramInt)
/*      */     {
/* 1110 */       String str = "Unknown error";
/*      */ 
/* 1112 */       switch (paramInt) {
/*      */       case 1:
/* 1114 */         str = "Unknown host";
/* 1115 */         break;
/*      */       case 2:
/* 1117 */         str = "Malformed URL";
/* 1118 */         break;
/*      */       case 3:
/* 1120 */         str = "SSL handshake failed";
/* 1121 */         break;
/*      */       case 4:
/* 1123 */         str = "Connection refused by server";
/* 1124 */         break;
/*      */       case 5:
/* 1126 */         str = "Connection reset by server";
/* 1127 */         break;
/*      */       case 6:
/* 1129 */         str = "No route to host";
/* 1130 */         break;
/*      */       case 7:
/* 1132 */         str = "Connection timed out";
/* 1133 */         break;
/*      */       case 8:
/* 1135 */         str = "Permission denied";
/* 1136 */         break;
/*      */       case 9:
/* 1138 */         str = "Invalid response from server";
/* 1139 */         break;
/*      */       case 10:
/* 1141 */         str = "Too many redirects";
/* 1142 */         break;
/*      */       case 11:
/* 1144 */         str = "File not found";
/*      */       }
/*      */ 
/* 1147 */       return new Throwable(str);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class PageLoadListener
/*      */     implements LoadListenerClient
/*      */   {
/*      */     private WeakReference<WebEngine> engine;
/*      */ 
/*      */     PageLoadListener(WebEngine paramWebEngine)
/*      */     {
/*  946 */       this.engine = new WeakReference(paramWebEngine);
/*      */     }
/*      */ 
/*      */     public void dispatchLoadEvent(long paramLong, int paramInt1, String paramString1, String paramString2, double paramDouble, int paramInt2)
/*      */     {
/*  953 */       WebEngine localWebEngine = (WebEngine)this.engine.get();
/*  954 */       if (localWebEngine != null)
/*  955 */         localWebEngine.loadWorker.dispatchLoadEvent(paramLong, paramInt1, paramString1, paramString2, paramDouble, paramInt2);
/*      */     }
/*      */ 
/*      */     public void dispatchResourceLoadEvent(long paramLong, int paramInt1, String paramString1, String paramString2, double paramDouble, int paramInt2)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class PulseTimer
/*      */   {
/*  903 */     private static AnimationTimer animation = new AnimationTimer() {
/*  903 */       public void handle(long paramAnonymousLong) {  }  } ;
/*      */ 
/*  908 */     private static TKPulseListener listener = new TKPulseListener()
/*      */     {
/*      */       public void pulse()
/*      */       {
/*  915 */         Timer.getTimer().notifyTick();
/*      */       }
/*  908 */     };
/*      */ 
/*      */     public static void start()
/*      */     {
/*  920 */       Toolkit.getToolkit().addSceneTkPulseListener(listener);
/*  921 */       animation.start();
/*      */     }
/*      */ 
/*      */     public static void stop() {
/*  925 */       Toolkit.getToolkit().removeSceneTkPulseListener(listener);
/*  926 */       animation.stop();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class AccessorImpl extends Accessor
/*      */   {
/*      */     private WeakReference<WebEngine> engine;
/*      */ 
/*      */     public AccessorImpl(WebEngine paramWebEngine)
/*      */     {
/*  853 */       this.engine = new WeakReference(paramWebEngine);
/*      */     }
/*      */ 
/*      */     public WebEngine getEngine() {
/*  857 */       return (WebEngine)this.engine.get();
/*      */     }
/*      */ 
/*      */     public WebPage getPage() {
/*  861 */       WebEngine localWebEngine = getEngine();
/*  862 */       return localWebEngine == null ? null : localWebEngine.page;
/*      */     }
/*      */ 
/*      */     public WebView getView() {
/*  866 */       WebEngine localWebEngine = getEngine();
/*  867 */       return localWebEngine == null ? null : (WebView)localWebEngine.view.get();
/*      */     }
/*      */ 
/*      */     public PGWebView getPGView() {
/*  871 */       WebView localWebView = getView();
/*  872 */       return localWebView == null ? null : localWebView.getPGWebView();
/*      */     }
/*      */ 
/*      */     public void addChild(Node paramNode) {
/*  876 */       WebView localWebView = getView();
/*  877 */       if (localWebView != null)
/*  878 */         localWebView.getChildren().add(paramNode);
/*      */     }
/*      */ 
/*      */     public void removeChild(Node paramNode)
/*      */     {
/*  883 */       WebView localWebView = getView();
/*  884 */       if (localWebView != null)
/*  885 */         localWebView.getChildren().remove(paramNode);
/*      */     }
/*      */ 
/*      */     public void addViewListener(InvalidationListener paramInvalidationListener)
/*      */     {
/*  890 */       WebEngine localWebEngine = getEngine();
/*  891 */       if (localWebEngine != null)
/*  892 */         localWebEngine.view.addListener(paramInvalidationListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SelfDisposer
/*      */     implements DisposerRecord
/*      */   {
/*      */     WebPage page;
/*      */ 
/*      */     SelfDisposer(WebPage paramWebPage)
/*      */     {
/*  835 */       this.page = paramWebPage;
/*      */     }
/*      */ 
/*      */     public void dispose() {
/*  839 */       this.page.dispose();
/*  840 */       WebEngine.access$710();
/*  841 */       if ((WebEngine.instanceCount == 0) && (Timer.getMode() == Timer.Mode.PLATFORM_TICKS))
/*      */       {
/*  844 */         WebEngine.PulseTimer.stop();
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.web.WebEngine
 * JD-Core Version:    0.6.2
 */