/*      */ package javafx.stage;
/*      */ 
/*      */ import com.sun.javafx.WeakReferenceQueue;
/*      */ import com.sun.javafx.css.StyleManager;
/*      */ import com.sun.javafx.event.EventHandlerManager;
/*      */ import com.sun.javafx.stage.WindowBoundsAccessor;
/*      */ import com.sun.javafx.stage.WindowEventDispatcher;
/*      */ import com.sun.javafx.stage.WindowPeerListener;
/*      */ import com.sun.javafx.tk.TKPulseListener;
/*      */ import com.sun.javafx.tk.TKScene;
/*      */ import com.sun.javafx.tk.TKStage;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Iterator;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.DoublePropertyBase;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ObjectPropertyBase;
/*      */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*      */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*      */ import javafx.beans.property.ReadOnlyDoubleWrapper;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*      */ import javafx.beans.property.SimpleObjectProperty;
/*      */ import javafx.event.Event;
/*      */ import javafx.event.EventDispatchChain;
/*      */ import javafx.event.EventDispatcher;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.event.EventTarget;
/*      */ import javafx.event.EventType;
/*      */ import javafx.geometry.Rectangle2D;
/*      */ import javafx.scene.Parent;
/*      */ import javafx.scene.Scene;
/*      */ 
/*      */ public class Window
/*      */   implements EventTarget
/*      */ {
/*   78 */   private static WeakReferenceQueue<Window> windowQueue = new WeakReferenceQueue();
/*      */ 
/*   85 */   private static final WindowBoundsAccessor BOUNDS_ACCESSOR = new WindowBoundsAccessor()
/*      */   {
/*      */     public void setLocation(Window paramAnonymousWindow, double paramAnonymousDouble1, double paramAnonymousDouble2)
/*      */     {
/*   89 */       paramAnonymousWindow.x.set(paramAnonymousDouble1);
/*   90 */       paramAnonymousWindow.y.set(paramAnonymousDouble2);
/*      */     }
/*      */ 
/*      */     public void setSize(Window paramAnonymousWindow, double paramAnonymousDouble1, double paramAnonymousDouble2)
/*      */     {
/*   96 */       paramAnonymousWindow.width.set(paramAnonymousDouble1);
/*   97 */       paramAnonymousWindow.height.set(paramAnonymousDouble2);
/*      */     }
/*   85 */   };
/*      */ 
/*  121 */   private final AccessControlContext acc = AccessController.getContext();
/*      */ 
/*      */   @Deprecated
/*      */   protected WindowPeerListener peerListener;
/*      */ 
/*      */   @Deprecated
/*      */   protected TKStage impl_peer;
/*  152 */   private TKBoundsConfigurator peerBoundsConfigurator = new TKBoundsConfigurator();
/*      */   private static final float CENTER_ON_SCREEN_X_FRACTION = 0.5F;
/*      */   private static final float CENTER_ON_SCREEN_Y_FRACTION = 0.3333333F;
/*  244 */   private boolean xExplicit = false;
/*      */ 
/*  252 */   private ReadOnlyDoubleWrapper x = new ReadOnlyDoubleWrapper(this, "x", (0.0D / 0.0D));
/*      */ 
/*  263 */   private boolean yExplicit = false;
/*      */ 
/*  271 */   private ReadOnlyDoubleWrapper y = new ReadOnlyDoubleWrapper(this, "y", (0.0D / 0.0D));
/*      */ 
/*  282 */   private boolean widthExplicit = false;
/*      */ 
/*  297 */   private ReadOnlyDoubleWrapper width = new ReadOnlyDoubleWrapper(this, "width", (0.0D / 0.0D));
/*      */ 
/*  308 */   private boolean heightExplicit = false;
/*      */ 
/*  322 */   private ReadOnlyDoubleWrapper height = new ReadOnlyDoubleWrapper(this, "height", (0.0D / 0.0D));
/*      */ 
/*  342 */   private ReadOnlyBooleanWrapper focused = new ReadOnlyBooleanWrapper() {
/*      */     protected void invalidated() {
/*  344 */       Window.this.focusChanged(get());
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  349 */       return Window.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  354 */       return "focused";
/*      */     }
/*  342 */   };
/*      */ 
/*  385 */   private SceneModel scene = new SceneModel(null);
/*      */   private DoubleProperty opacity;
/*      */   private ObjectProperty<EventHandler<WindowEvent>> onCloseRequest;
/*      */   private ObjectProperty<EventHandler<WindowEvent>> onShowing;
/*      */   private ObjectProperty<EventHandler<WindowEvent>> onShown;
/*      */   private ObjectProperty<EventHandler<WindowEvent>> onHiding;
/*      */   private ObjectProperty<EventHandler<WindowEvent>> onHidden;
/*  676 */   private ReadOnlyBooleanWrapper showing = new ReadOnlyBooleanWrapper() {
/*      */     private boolean oldVisible;
/*      */ 
/*      */     protected void invalidated() {
/*  680 */       boolean bool = get();
/*  681 */       if (this.oldVisible == bool) {
/*  682 */         return;
/*      */       }
/*      */ 
/*  685 */       if ((!this.oldVisible) && (bool))
/*  686 */         Window.this.fireEvent(new WindowEvent(Window.this, WindowEvent.WINDOW_SHOWING));
/*      */       else {
/*  688 */         Window.this.fireEvent(new WindowEvent(Window.this, WindowEvent.WINDOW_HIDING));
/*      */       }
/*      */ 
/*  691 */       this.oldVisible = bool;
/*  692 */       Window.this.impl_visibleChanging(bool);
/*  693 */       if (bool) {
/*  694 */         Window.this.hasBeenVisible = true;
/*  695 */         Window.windowQueue.add(Window.this);
/*      */       } else {
/*  697 */         Window.windowQueue.remove(Window.this);
/*      */       }
/*  699 */       Toolkit localToolkit = Toolkit.getToolkit();
/*  700 */       if (Window.this.impl_peer != null) {
/*  701 */         if (bool) {
/*  702 */           Window.this.impl_peer.setSecurityContext(Window.this.acc);
/*      */ 
/*  704 */           if (Window.this.peerListener == null) {
/*  705 */             Window.this.peerListener = new WindowPeerListener(Window.this);
/*      */           }
/*      */ 
/*  708 */           Window.this.peerListener.setBoundsAccessor(Window.BOUNDS_ACCESSOR);
/*      */ 
/*  711 */           Window.this.impl_peer.setTKStageListener(Window.this.peerListener);
/*      */ 
/*  713 */           localToolkit.addStageTkPulseListener(Window.this.peerBoundsConfigurator);
/*      */ 
/*  715 */           if (Window.this.getScene() != null) {
/*  716 */             Window.this.getScene().impl_initPeer();
/*  717 */             Window.this.impl_peer.setScene(Window.this.getScene().impl_getPeer());
/*  718 */             Window.this.getScene().impl_preferredSize();
/*      */           }
/*      */ 
/*  722 */           if ((Window.this.getScene() != null) && ((!Window.this.widthExplicit) || (!Window.this.heightExplicit)))
/*  723 */             Window.this.adjustSize(true);
/*      */           else {
/*  725 */             Window.this.peerBoundsConfigurator.setSize(Window.this.getWidth(), Window.this.getHeight(), -1.0D, -1.0D);
/*      */           }
/*      */ 
/*  729 */           if ((!Window.this.xExplicit) && (!Window.this.yExplicit))
/*  730 */             Window.this.centerOnScreen();
/*      */           else {
/*  732 */             Window.this.peerBoundsConfigurator.setLocation(Window.this.getX(), Window.this.getY(), 0.0F, 0.0F);
/*      */           }
/*      */ 
/*  737 */           Window.this.applyBounds();
/*      */ 
/*  739 */           Window.this.impl_peer.setOpacity((float)Window.this.getOpacity());
/*      */ 
/*  741 */           Window.this.impl_peer.setVisible(true);
/*  742 */           Window.this.fireEvent(new WindowEvent(Window.this, WindowEvent.WINDOW_SHOWN));
/*      */         } else {
/*  744 */           Window.this.impl_peer.setVisible(false);
/*      */ 
/*  747 */           Window.this.fireEvent(new WindowEvent(Window.this, WindowEvent.WINDOW_HIDDEN));
/*      */ 
/*  749 */           if (Window.this.getScene() != null) {
/*  750 */             Window.this.impl_peer.setScene(null);
/*  751 */             Window.this.getScene().impl_disposePeer();
/*      */           }
/*      */ 
/*  755 */           localToolkit.removeStageTkPulseListener(Window.this.peerBoundsConfigurator);
/*      */ 
/*  757 */           Window.this.impl_peer.setTKStageListener(null);
/*      */ 
/*  760 */           Window.this.impl_peer.close();
/*      */         }
/*      */       }
/*  763 */       if (bool) {
/*  764 */         localToolkit.requestNextPulse();
/*      */       }
/*  766 */       Window.this.impl_visibleChanged(bool);
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  771 */       return Window.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  776 */       return "showing";
/*      */     }
/*  676 */   };
/*      */ 
/*  787 */   boolean hasBeenVisible = false;
/*      */   private ObjectProperty<EventDispatcher> eventDispatcher;
/*      */   private WindowEventDispatcher internalEventDispatcher;
/*      */   private int focusGrabCounter;
/*      */ 
/*      */   @Deprecated
/*      */   public static Iterator<Window> impl_getWindows()
/*      */   {
/*  111 */     Iterator localIterator = (Iterator)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Iterator run() {
/*  114 */         return Window.windowQueue.iterator();
/*      */       }
/*      */     });
/*  118 */     return localIterator;
/*      */   }
/*      */ 
/*      */   protected Window()
/*      */   {
/*  125 */     initializeInternalEventDispatcher();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public TKStage impl_getPeer()
/*      */   {
/*  163 */     return this.impl_peer;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public String impl_getMXWindowType()
/*      */   {
/*  172 */     return getClass().getSimpleName();
/*      */   }
/*      */ 
/*      */   public void sizeToScene()
/*      */   {
/*  180 */     if ((getScene() != null) && (this.impl_peer != null)) {
/*  181 */       getScene().impl_preferredSize();
/*  182 */       adjustSize(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void adjustSize(boolean paramBoolean) {
/*  187 */     if (getScene() == null) {
/*  188 */       return;
/*      */     }
/*  190 */     if (this.impl_peer != null) {
/*  191 */       double d1 = getScene().getWidth();
/*  192 */       double d2 = d1 > 0.0D ? d1 : -1.0D;
/*  193 */       double d3 = -1.0D;
/*  194 */       if ((paramBoolean) && (this.widthExplicit))
/*  195 */         d3 = getWidth();
/*  196 */       else if (d2 <= 0.0D)
/*  197 */         d3 = this.widthExplicit ? getWidth() : -1.0D;
/*      */       else {
/*  199 */         this.widthExplicit = false;
/*      */       }
/*  201 */       double d4 = getScene().getHeight();
/*  202 */       double d5 = d4 > 0.0D ? d4 : -1.0D;
/*  203 */       double d6 = -1.0D;
/*  204 */       if ((paramBoolean) && (this.heightExplicit))
/*  205 */         d6 = getHeight();
/*  206 */       else if (d5 <= 0.0D)
/*  207 */         d6 = this.heightExplicit ? getHeight() : -1.0D;
/*      */       else {
/*  209 */         this.heightExplicit = false;
/*      */       }
/*      */ 
/*  212 */       this.peerBoundsConfigurator.setSize(d3, d6, d2, d5);
/*  213 */       applyBounds();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void centerOnScreen()
/*      */   {
/*  224 */     this.xExplicit = false;
/*  225 */     this.yExplicit = false;
/*  226 */     if (this.impl_peer != null) {
/*  227 */       Rectangle2D localRectangle2D = Screen.getPrimary().getVisualBounds();
/*  228 */       double d1 = localRectangle2D.getMinX() + (localRectangle2D.getWidth() - getWidth()) * 0.5D;
/*      */ 
/*  231 */       double d2 = localRectangle2D.getMinY() + (localRectangle2D.getHeight() - getHeight()) * 0.333333343267441D;
/*      */ 
/*  235 */       this.x.set(d1);
/*  236 */       this.y.set(d2);
/*  237 */       this.peerBoundsConfigurator.setLocation(d1, d2, 0.5F, 0.3333333F);
/*      */ 
/*  240 */       applyBounds();
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void setX(double paramDouble)
/*      */   {
/*  256 */     this.x.set(paramDouble);
/*  257 */     this.peerBoundsConfigurator.setX(paramDouble, 0.0F);
/*  258 */     this.xExplicit = true;
/*      */   }
/*  260 */   public final double getX() { return this.x.get(); } 
/*  261 */   public final ReadOnlyDoubleProperty xProperty() { return this.x.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   public final void setY(double paramDouble)
/*      */   {
/*  275 */     this.y.set(paramDouble);
/*  276 */     this.peerBoundsConfigurator.setY(paramDouble, 0.0F);
/*  277 */     this.yExplicit = true;
/*      */   }
/*  279 */   public final double getY() { return this.y.get(); } 
/*  280 */   public final ReadOnlyDoubleProperty yProperty() { return this.y.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   public final void setWidth(double paramDouble)
/*      */   {
/*  301 */     this.width.set(paramDouble);
/*  302 */     this.peerBoundsConfigurator.setWindowWidth(paramDouble);
/*  303 */     this.widthExplicit = true;
/*      */   }
/*  305 */   public final double getWidth() { return this.width.get(); } 
/*  306 */   public final ReadOnlyDoubleProperty widthProperty() { return this.width.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   public final void setHeight(double paramDouble)
/*      */   {
/*  326 */     this.height.set(paramDouble);
/*  327 */     this.peerBoundsConfigurator.setWindowHeight(paramDouble);
/*  328 */     this.heightExplicit = true;
/*      */   }
/*  330 */   public final double getHeight() { return this.height.get(); } 
/*  331 */   public final ReadOnlyDoubleProperty heightProperty() { return this.height.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public final void setFocused(boolean paramBoolean)
/*      */   {
/*  363 */     this.focused.set(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final void requestFocus()
/*      */   {
/*  368 */     setFocused(true); } 
/*  369 */   public final boolean isFocused() { return this.focused.get(); } 
/*  370 */   public final ReadOnlyBooleanProperty focusedProperty() { return this.focused.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   protected void setScene(Scene paramScene)
/*      */   {
/*  386 */     this.scene.set(paramScene); } 
/*  387 */   public final Scene getScene() { return (Scene)this.scene.get(); } 
/*  388 */   public final ReadOnlyObjectProperty<Scene> sceneProperty() { return this.scene.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   public final void setOpacity(double paramDouble)
/*      */   {
/*  483 */     opacityProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getOpacity() {
/*  487 */     return this.opacity == null ? 1.0D : this.opacity.get();
/*      */   }
/*      */ 
/*      */   public final DoubleProperty opacityProperty() {
/*  491 */     if (this.opacity == null) {
/*  492 */       this.opacity = new DoublePropertyBase(1.0D)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  496 */           if (Window.this.impl_peer != null)
/*  497 */             Window.this.impl_peer.setOpacity((float)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  503 */           return Window.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  508 */           return "opacity";
/*      */         }
/*      */       };
/*      */     }
/*  512 */     return this.opacity;
/*      */   }
/*      */ 
/*      */   public final void setOnCloseRequest(EventHandler<WindowEvent> paramEventHandler)
/*      */   {
/*  522 */     onCloseRequestProperty().set(paramEventHandler);
/*      */   }
/*      */   public final EventHandler<WindowEvent> getOnCloseRequest() {
/*  525 */     return this.onCloseRequest != null ? (EventHandler)this.onCloseRequest.get() : null;
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WindowEvent>> onCloseRequestProperty() {
/*  529 */     if (this.onCloseRequest == null) {
/*  530 */       this.onCloseRequest = new ObjectPropertyBase() {
/*      */         protected void invalidated() {
/*  532 */           Window.this.setEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  537 */           return Window.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  542 */           return "onCloseRequest";
/*      */         }
/*      */       };
/*      */     }
/*  546 */     return this.onCloseRequest;
/*      */   }
/*      */ 
/*      */   public final void setOnShowing(EventHandler<WindowEvent> paramEventHandler)
/*      */   {
/*  553 */     onShowingProperty().set(paramEventHandler);
/*      */   }
/*  555 */   public final EventHandler<WindowEvent> getOnShowing() { return this.onShowing == null ? null : (EventHandler)this.onShowing.get(); }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WindowEvent>> onShowingProperty() {
/*  558 */     if (this.onShowing == null) {
/*  559 */       this.onShowing = new ObjectPropertyBase() {
/*      */         protected void invalidated() {
/*  561 */           Window.this.setEventHandler(WindowEvent.WINDOW_SHOWING, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  566 */           return Window.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  571 */           return "onShowing";
/*      */         }
/*      */       };
/*      */     }
/*  575 */     return this.onShowing;
/*      */   }
/*      */ 
/*      */   public final void setOnShown(EventHandler<WindowEvent> paramEventHandler)
/*      */   {
/*  582 */     onShownProperty().set(paramEventHandler);
/*      */   }
/*  584 */   public final EventHandler<WindowEvent> getOnShown() { return this.onShown == null ? null : (EventHandler)this.onShown.get(); }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WindowEvent>> onShownProperty() {
/*  587 */     if (this.onShown == null) {
/*  588 */       this.onShown = new ObjectPropertyBase() {
/*      */         protected void invalidated() {
/*  590 */           Window.this.setEventHandler(WindowEvent.WINDOW_SHOWN, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  595 */           return Window.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  600 */           return "onShown";
/*      */         }
/*      */       };
/*      */     }
/*  604 */     return this.onShown;
/*      */   }
/*      */ 
/*      */   public final void setOnHiding(EventHandler<WindowEvent> paramEventHandler)
/*      */   {
/*  611 */     onHidingProperty().set(paramEventHandler);
/*      */   }
/*  613 */   public final EventHandler<WindowEvent> getOnHiding() { return this.onHiding == null ? null : (EventHandler)this.onHiding.get(); }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WindowEvent>> onHidingProperty() {
/*  616 */     if (this.onHiding == null) {
/*  617 */       this.onHiding = new ObjectPropertyBase() {
/*      */         protected void invalidated() {
/*  619 */           Window.this.setEventHandler(WindowEvent.WINDOW_HIDING, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  624 */           return Window.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  629 */           return "onHiding";
/*      */         }
/*      */       };
/*      */     }
/*  633 */     return this.onHiding;
/*      */   }
/*      */ 
/*      */   public final void setOnHidden(EventHandler<WindowEvent> paramEventHandler)
/*      */   {
/*  643 */     onHiddenProperty().set(paramEventHandler);
/*      */   }
/*  645 */   public final EventHandler<WindowEvent> getOnHidden() { return this.onHidden == null ? null : (EventHandler)this.onHidden.get(); }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WindowEvent>> onHiddenProperty() {
/*  648 */     if (this.onHidden == null) {
/*  649 */       this.onHidden = new ObjectPropertyBase() {
/*      */         protected void invalidated() {
/*  651 */           Window.this.setEventHandler(WindowEvent.WINDOW_HIDDEN, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  656 */           return Window.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  661 */           return "onHidden";
/*      */         }
/*      */       };
/*      */     }
/*  665 */     return this.onHidden;
/*      */   }
/*      */ 
/*      */   private void setShowing(boolean paramBoolean)
/*      */   {
/*  780 */     Toolkit.getToolkit().checkFxUserThread();
/*  781 */     this.showing.set(paramBoolean);
/*      */   }
/*  783 */   public final boolean isShowing() { return this.showing.get(); } 
/*  784 */   public final ReadOnlyBooleanProperty showingProperty() { return this.showing.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   protected void show()
/*      */   {
/*  796 */     setShowing(true);
/*      */   }
/*      */ 
/*      */   public void hide()
/*      */   {
/*  806 */     setShowing(false);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected void impl_visibleChanging(boolean paramBoolean)
/*      */   {
/*  816 */     if ((paramBoolean) && (getScene() != null)) {
/*  817 */       StyleManager.getInstance().updateStylesheets(getScene());
/*  818 */       getScene().getRoot().impl_reapplyCSS();
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected void impl_visibleChanged(boolean paramBoolean)
/*      */   {
/*      */   }
/*      */ 
/*      */   public final void setEventDispatcher(EventDispatcher paramEventDispatcher)
/*      */   {
/*  842 */     eventDispatcherProperty().set(paramEventDispatcher);
/*      */   }
/*      */ 
/*      */   public final EventDispatcher getEventDispatcher() {
/*  846 */     return (EventDispatcher)eventDispatcherProperty().get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventDispatcher> eventDispatcherProperty() {
/*  850 */     initializeInternalEventDispatcher();
/*  851 */     return this.eventDispatcher;
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void addEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/*  870 */     getInternalEventDispatcher().getEventHandlerManager().addEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void removeEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/*  889 */     getInternalEventDispatcher().getEventHandlerManager().removeEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void addEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/*  908 */     getInternalEventDispatcher().getEventHandlerManager().addEventFilter(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void removeEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/*  927 */     getInternalEventDispatcher().getEventHandlerManager().removeEventFilter(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   protected final <T extends Event> void setEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/*  944 */     getInternalEventDispatcher().getEventHandlerManager().setEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   WindowEventDispatcher getInternalEventDispatcher()
/*      */   {
/*  949 */     initializeInternalEventDispatcher();
/*  950 */     return this.internalEventDispatcher;
/*      */   }
/*      */ 
/*      */   private void initializeInternalEventDispatcher() {
/*  954 */     if (this.internalEventDispatcher == null) {
/*  955 */       this.internalEventDispatcher = createInternalEventDispatcher();
/*  956 */       this.eventDispatcher = new SimpleObjectProperty(this, "eventDispatcher", this.internalEventDispatcher);
/*      */     }
/*      */   }
/*      */ 
/*      */   WindowEventDispatcher createInternalEventDispatcher()
/*      */   {
/*  964 */     return new WindowEventDispatcher(this);
/*      */   }
/*      */ 
/*      */   public final void fireEvent(Event paramEvent)
/*      */   {
/*  975 */     Event.fireEvent(this, paramEvent);
/*      */   }
/*      */ 
/*      */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*      */   {
/*  988 */     return this.eventDispatcher != null ? paramEventDispatchChain.prepend((EventDispatcher)this.eventDispatcher.get()) : paramEventDispatchChain;
/*      */   }
/*      */ 
/*      */   void increaseFocusGrabCounter()
/*      */   {
/*  995 */     if ((++this.focusGrabCounter == 1) && (this.impl_peer != null) && (isFocused()))
/*  996 */       this.impl_peer.grabFocus();
/*      */   }
/*      */ 
/*      */   void decreaseFocusGrabCounter()
/*      */   {
/* 1001 */     if ((--this.focusGrabCounter == 0) && (this.impl_peer != null))
/* 1002 */       this.impl_peer.ungrabFocus();
/*      */   }
/*      */ 
/*      */   private void focusChanged(boolean paramBoolean)
/*      */   {
/* 1007 */     if ((this.focusGrabCounter > 0) && (this.impl_peer != null) && (paramBoolean))
/* 1008 */       this.impl_peer.grabFocus();
/*      */   }
/*      */ 
/*      */   final void applyBounds()
/*      */   {
/* 1013 */     this.peerBoundsConfigurator.apply();
/*      */   }
/*      */   private final class TKBoundsConfigurator implements TKPulseListener { private double x;
/*      */     private double y;
/*      */     private float xGravity;
/*      */     private float yGravity;
/*      */     private double windowWidth;
/*      */     private double windowHeight;
/*      */     private double clientWidth;
/*      */     private double clientHeight;
/*      */     private boolean dirty;
/*      */ 
/* 1033 */     public TKBoundsConfigurator() { reset(); }
/*      */ 
/*      */     public void setX(double paramDouble, float paramFloat)
/*      */     {
/* 1037 */       this.x = paramDouble;
/* 1038 */       this.xGravity = paramFloat;
/* 1039 */       setDirty();
/*      */     }
/*      */ 
/*      */     public void setY(double paramDouble, float paramFloat) {
/* 1043 */       this.y = paramDouble;
/* 1044 */       this.yGravity = paramFloat;
/* 1045 */       setDirty();
/*      */     }
/*      */ 
/*      */     public void setWindowWidth(double paramDouble) {
/* 1049 */       this.windowWidth = paramDouble;
/* 1050 */       setDirty();
/*      */     }
/*      */ 
/*      */     public void setWindowHeight(double paramDouble) {
/* 1054 */       this.windowHeight = paramDouble;
/* 1055 */       setDirty();
/*      */     }
/*      */ 
/*      */     public void setClientWidth(double paramDouble) {
/* 1059 */       this.clientWidth = paramDouble;
/* 1060 */       setDirty();
/*      */     }
/*      */ 
/*      */     public void setClientHeight(double paramDouble) {
/* 1064 */       this.clientHeight = paramDouble;
/* 1065 */       setDirty();
/*      */     }
/*      */ 
/*      */     public void setLocation(double paramDouble1, double paramDouble2, float paramFloat1, float paramFloat2)
/*      */     {
/* 1072 */       this.x = paramDouble1;
/* 1073 */       this.y = paramDouble2;
/* 1074 */       this.xGravity = paramFloat1;
/* 1075 */       this.yGravity = paramFloat2;
/* 1076 */       setDirty();
/*      */     }
/*      */ 
/*      */     public void setSize(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */     {
/* 1083 */       this.windowWidth = paramDouble1;
/* 1084 */       this.windowHeight = paramDouble2;
/* 1085 */       this.clientWidth = paramDouble3;
/* 1086 */       this.clientHeight = paramDouble4;
/* 1087 */       setDirty();
/*      */     }
/*      */ 
/*      */     public void apply() {
/* 1091 */       if (this.dirty) {
/* 1092 */         Window.this.impl_peer.setBounds((float)(Double.isNaN(this.x) ? 0.0D : this.x), (float)(Double.isNaN(this.y) ? 0.0D : this.y), !Double.isNaN(this.x), !Double.isNaN(this.y), (float)this.windowWidth, (float)this.windowHeight, (float)this.clientWidth, (float)this.clientHeight, this.xGravity, this.yGravity);
/*      */ 
/* 1102 */         reset();
/*      */       }
/*      */     }
/*      */ 
/*      */     public void pulse()
/*      */     {
/* 1108 */       apply();
/*      */     }
/*      */ 
/*      */     private void reset() {
/* 1112 */       this.x = (0.0D / 0.0D);
/* 1113 */       this.y = (0.0D / 0.0D);
/* 1114 */       this.xGravity = 0.0F;
/* 1115 */       this.yGravity = 0.0F;
/* 1116 */       this.windowWidth = -1.0D;
/* 1117 */       this.windowHeight = -1.0D;
/* 1118 */       this.clientWidth = -1.0D;
/* 1119 */       this.clientHeight = -1.0D;
/* 1120 */       this.dirty = false;
/*      */     }
/*      */ 
/*      */     private void setDirty() {
/* 1124 */       if (!this.dirty) {
/* 1125 */         Toolkit.getToolkit().requestNextPulse();
/* 1126 */         this.dirty = true;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private final class SceneModel extends ReadOnlyObjectWrapper<Scene>
/*      */   {
/*      */     private Scene oldScene;
/*      */ 
/*      */     private SceneModel()
/*      */     {
/*      */     }
/*      */ 
/*      */     protected void invalidated()
/*      */     {
/*  394 */       Scene localScene = (Scene)get();
/*  395 */       if (this.oldScene != localScene) {
/*  396 */         Toolkit.getToolkit().checkFxUserThread();
/*      */ 
/*  399 */         if (this.oldScene != null) {
/*  400 */           this.oldScene.impl_setWindow(null);
/*      */         }
/*  402 */         if (localScene != null) {
/*  403 */           Window localWindow = localScene.getWindow();
/*  404 */           if (localWindow != null)
/*      */           {
/*  409 */             localWindow.scene.notifySceneLost();
/*      */           }
/*      */ 
/*  414 */           localScene.impl_setWindow(Window.this);
/*      */ 
/*  416 */           updatePeerStage(localScene.impl_getPeer());
/*      */ 
/*  421 */           if (Window.this.isShowing()) {
/*  422 */             StyleManager.getInstance().updateStylesheets(localScene);
/*  423 */             localScene.getRoot().impl_reapplyCSS();
/*  424 */             Window.this.getScene().impl_preferredSize();
/*      */ 
/*  426 */             if ((!Window.this.widthExplicit) || (!Window.this.heightExplicit))
/*  427 */               Window.this.adjustSize(true);
/*      */           }
/*      */         }
/*      */         else {
/*  431 */           updatePeerStage(null);
/*      */         }
/*      */ 
/*  434 */         this.oldScene = localScene;
/*      */       }
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  440 */       return Window.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  445 */       return "scene";
/*      */     }
/*      */ 
/*      */     public void notifySceneLost()
/*      */     {
/*  451 */       if (isBound()) {
/*  452 */         unbind();
/*      */       }
/*      */ 
/*  456 */       this.oldScene = null;
/*  457 */       set(null);
/*  458 */       updatePeerStage(null);
/*      */     }
/*      */ 
/*      */     private void updatePeerStage(TKScene paramTKScene) {
/*  462 */       if (Window.this.impl_peer != null)
/*      */       {
/*  464 */         Window.this.impl_peer.setScene(paramTKScene);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.Window
 * JD-Core Version:    0.6.2
 */