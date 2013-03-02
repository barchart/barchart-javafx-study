/*     */ package javafx.stage;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.event.DirectEvent;
/*     */ import com.sun.javafx.event.EventHandlerManager;
/*     */ import com.sun.javafx.event.EventRedirector;
/*     */ import com.sun.javafx.event.EventUtil;
/*     */ import com.sun.javafx.perf.PerformanceTracker;
/*     */ import com.sun.javafx.stage.FocusUngrabEvent;
/*     */ import com.sun.javafx.stage.PopupWindowPeerListener;
/*     */ import com.sun.javafx.stage.WindowCloseRequestHandler;
/*     */ import com.sun.javafx.stage.WindowEventDispatcher;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.Group;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.input.KeyCombination;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public abstract class PopupWindow extends Window
/*     */ {
/*  93 */   private final List<PopupWindow> children = new ArrayList();
/*     */ 
/* 100 */   private final InvalidationListener rootBoundsListener = new InvalidationListener()
/*     */   {
/*     */     public void invalidated(Observable paramAnonymousObservable)
/*     */     {
/* 104 */       PopupWindow.this.updateDimensions();
/*     */     }
/* 100 */   };
/*     */ 
/* 162 */   private ReadOnlyObjectWrapper<Window> ownerWindow = new ReadOnlyObjectWrapper(this, "ownerWindow");
/*     */ 
/* 177 */   private ReadOnlyObjectWrapper<Node> ownerNode = new ReadOnlyObjectWrapper(this, "ownerNode");
/*     */ 
/* 203 */   private BooleanProperty autoFix = new BooleanPropertyBase(true)
/*     */   {
/*     */     protected void invalidated()
/*     */     {
/* 207 */       PopupWindow.this.handleAutofixActivation(PopupWindow.this.isShowing(), get());
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 212 */       return PopupWindow.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 217 */       return "autoFix";
/*     */     }
/* 203 */   };
/*     */ 
/* 229 */   private BooleanProperty autoHide = new SimpleBooleanProperty(this, "autoHide");
/*     */ 
/* 238 */   private ObjectProperty<EventHandler<Event>> onAutoHide = new SimpleObjectProperty(this, "onAutoHide");
/*     */ 
/* 249 */   private BooleanProperty hideOnEscape = new SimpleBooleanProperty(this, "hideOnEscape", true);
/*     */ 
/* 262 */   private BooleanProperty consumeAutoHidingEvents = new SimpleBooleanProperty(this, "consumeAutoHidingEvents", true);
/*     */   private Window rootWindow;
/*     */   private ChangeListener<Boolean> ownerFocusedListener;
/*     */   private boolean autofixActive;
/*     */   private AutofixHandler autofixHandler;
/*     */ 
/*     */   public PopupWindow()
/*     */   {
/* 109 */     final Scene localScene = new Scene(null);
/* 110 */     localScene.setFill(null);
/* 111 */     super.setScene(localScene);
/*     */ 
/* 113 */     localScene.rootProperty().addListener(new InvalidationListener()
/*     */     {
/*     */       private Node oldRoot;
/*     */ 
/*     */       public void invalidated(Observable paramAnonymousObservable)
/*     */       {
/* 119 */         Parent localParent = localScene.getRoot();
/* 120 */         if (this.oldRoot != localParent) {
/* 121 */           if (this.oldRoot != null) {
/* 122 */             this.oldRoot.layoutBoundsProperty().removeListener(PopupWindow.this.rootBoundsListener);
/*     */           }
/*     */ 
/* 126 */           if (localParent != null) {
/* 127 */             localParent.layoutBoundsProperty().addListener(PopupWindow.this.rootBoundsListener);
/*     */           }
/*     */ 
/* 131 */           this.oldRoot = localParent;
/* 132 */           PopupWindow.this.updateDimensions();
/*     */         }
/*     */       }
/*     */     });
/* 136 */     localScene.setRoot(new Group());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected ObservableList<Node> getContent()
/*     */   {
/* 149 */     Parent localParent = getScene().getRoot();
/* 150 */     if (!(localParent instanceof Group)) {
/* 151 */       throw new IllegalStateException("The content of the Popup can't be accessed");
/*     */     }
/*     */ 
/* 155 */     return ((Group)localParent).getChildren();
/*     */   }
/*     */ 
/*     */   public final Window getOwnerWindow()
/*     */   {
/* 165 */     return (Window)this.ownerWindow.get();
/*     */   }
/*     */   public final ReadOnlyObjectProperty<Window> ownerWindowProperty() {
/* 168 */     return this.ownerWindow.getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   public final Node getOwnerNode()
/*     */   {
/* 180 */     return (Node)this.ownerNode.get();
/*     */   }
/*     */   public final ReadOnlyObjectProperty<Node> ownerNodeProperty() {
/* 183 */     return this.ownerNode.getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   protected final void setScene(Scene paramScene)
/*     */   {
/* 194 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public final void setAutoFix(boolean paramBoolean)
/*     */   {
/* 220 */     this.autoFix.set(paramBoolean); } 
/* 221 */   public final boolean isAutoFix() { return this.autoFix.get(); } 
/* 222 */   public final BooleanProperty autoFixProperty() { return this.autoFix; }
/*     */ 
/*     */ 
/*     */   public final void setAutoHide(boolean paramBoolean)
/*     */   {
/* 231 */     this.autoHide.set(paramBoolean); } 
/* 232 */   public final boolean isAutoHide() { return this.autoHide.get(); } 
/* 233 */   public final BooleanProperty autoHideProperty() { return this.autoHide; }
/*     */ 
/*     */ 
/*     */   public final void setOnAutoHide(EventHandler<Event> paramEventHandler)
/*     */   {
/* 240 */     this.onAutoHide.set(paramEventHandler); } 
/* 241 */   public final EventHandler<Event> getOnAutoHide() { return (EventHandler)this.onAutoHide.get(); } 
/* 242 */   public final ObjectProperty<EventHandler<Event>> onAutoHideProperty() { return this.onAutoHide; }
/*     */ 
/*     */ 
/*     */   public final void setHideOnEscape(boolean paramBoolean)
/*     */   {
/* 251 */     this.hideOnEscape.set(paramBoolean); } 
/* 252 */   public final boolean isHideOnEscape() { return this.hideOnEscape.get(); } 
/* 253 */   public final BooleanProperty hideOnEscapeProperty() { return this.hideOnEscape; }
/*     */ 
/*     */ 
/*     */   public final void setConsumeAutoHidingEvents(boolean paramBoolean)
/*     */   {
/* 267 */     this.consumeAutoHidingEvents.set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean getConsumeAutoHidingEvents() {
/* 271 */     return this.consumeAutoHidingEvents.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty consumeAutoHidingEventsProperty() {
/* 275 */     return this.consumeAutoHidingEvents;
/*     */   }
/*     */ 
/*     */   public void show(Window paramWindow)
/*     */   {
/* 286 */     validateOwnerWindow(paramWindow);
/* 287 */     showImpl(paramWindow);
/*     */   }
/*     */ 
/*     */   public void show(Node paramNode, double paramDouble1, double paramDouble2)
/*     */   {
/* 308 */     if (paramNode == null) {
/* 309 */       throw new NullPointerException("The owner node must not be null");
/*     */     }
/*     */ 
/* 312 */     Scene localScene = paramNode.getScene();
/* 313 */     if ((localScene == null) || (localScene.getWindow() == null))
/*     */     {
/* 315 */       throw new IllegalArgumentException("The owner node needs to be associated with a window");
/*     */     }
/*     */ 
/* 319 */     Window localWindow = localScene.getWindow();
/* 320 */     validateOwnerWindow(localWindow);
/*     */ 
/* 322 */     this.ownerNode.set(paramNode);
/*     */ 
/* 324 */     setX(paramDouble1);
/* 325 */     setY(paramDouble2);
/* 326 */     showImpl(localWindow);
/*     */   }
/*     */ 
/*     */   public void show(Window paramWindow, double paramDouble1, double paramDouble2)
/*     */   {
/* 341 */     validateOwnerWindow(paramWindow);
/*     */ 
/* 343 */     setX(paramDouble1);
/* 344 */     setY(paramDouble2);
/* 345 */     showImpl(paramWindow);
/*     */   }
/*     */ 
/*     */   private void showImpl(Window paramWindow) {
/* 349 */     if (isShowing()) {
/* 350 */       if (this.autofixHandler != null) {
/* 351 */         this.autofixHandler.adjustPosition();
/*     */       }
/* 353 */       return;
/*     */     }
/*     */ 
/* 357 */     this.ownerWindow.set(paramWindow);
/* 358 */     if ((paramWindow instanceof PopupWindow)) {
/* 359 */       ((PopupWindow)paramWindow).children.add(this);
/*     */     }
/*     */ 
/* 363 */     if (getRootWindow(paramWindow).isShowing())
/*     */     {
/* 368 */       show();
/* 369 */       if (!isAutoFix()) {
/* 370 */         Parent localParent = getScene().getRoot();
/* 371 */         if (localParent != null) {
/* 372 */           Bounds localBounds = localParent.getLayoutBounds();
/* 373 */           setX(getX() + localBounds.getMinX());
/* 374 */           setY(getY() + localBounds.getMinY());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void hide()
/*     */   {
/* 384 */     for (PopupWindow localPopupWindow : this.children) {
/* 385 */       if (localPopupWindow.isShowing()) {
/* 386 */         localPopupWindow.hide();
/*     */       }
/*     */     }
/* 389 */     this.children.clear();
/* 390 */     super.hide();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected void impl_visibleChanging(boolean paramBoolean)
/*     */   {
/* 399 */     super.impl_visibleChanging(paramBoolean);
/* 400 */     PerformanceTracker.logEvent("PopupWindow.storeVisible for [PopupWindow]");
/*     */ 
/* 402 */     Toolkit localToolkit = Toolkit.getToolkit();
/* 403 */     if ((paramBoolean) && (this.impl_peer == null))
/*     */     {
/* 405 */       this.impl_peer = localToolkit.createTKPopupStage(StageStyle.TRANSPARENT, getOwnerWindow().impl_getPeer());
/* 406 */       this.peerListener = new PopupWindowPeerListener(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected void impl_visibleChanged(boolean paramBoolean)
/*     */   {
/* 418 */     super.impl_visibleChanged(paramBoolean);
/* 419 */     if ((!paramBoolean) && (this.impl_peer != null)) {
/* 420 */       this.peerListener = null;
/* 421 */       this.impl_peer = null;
/*     */     }
/*     */ 
/* 424 */     Window localWindow = getOwnerWindow();
/* 425 */     if (paramBoolean) {
/* 426 */       this.rootWindow = getRootWindow(localWindow);
/*     */ 
/* 428 */       startMonitorOwnerEvents(localWindow);
/*     */ 
/* 434 */       bindOwnerFocusedProperty(localWindow);
/* 435 */       setFocused(localWindow.isFocused());
/* 436 */       handleAutofixActivation(true, isAutoFix());
/* 437 */       this.rootWindow.increaseFocusGrabCounter();
/*     */     } else {
/* 439 */       stopMonitorOwnerEvents(localWindow);
/* 440 */       unbindOwnerFocusedProperty(localWindow);
/* 441 */       setFocused(false);
/* 442 */       handleAutofixActivation(false, isAutoFix());
/* 443 */       this.rootWindow.decreaseFocusGrabCounter();
/* 444 */       this.rootWindow = null;
/*     */     }
/*     */ 
/* 447 */     PerformanceTracker.logEvent("PopupWindow.storeVisible for [PopupWindow] finished");
/*     */   }
/*     */ 
/*     */   private void updateDimensions() {
/* 451 */     Parent localParent = getScene().getRoot();
/* 452 */     if (localParent != null) {
/* 453 */       Bounds localBounds = localParent.getLayoutBounds();
/*     */ 
/* 456 */       setWidth(localBounds.getMaxX() - localBounds.getMinX());
/* 457 */       setHeight(localBounds.getMaxY() - localBounds.getMinY());
/*     */ 
/* 459 */       localParent.setTranslateX(-localBounds.getMinX());
/* 460 */       localParent.setTranslateY(-localBounds.getMinY());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static Window getRootWindow(Window paramWindow)
/*     */   {
/* 473 */     while ((paramWindow instanceof PopupWindow)) {
/* 474 */       paramWindow = ((PopupWindow)paramWindow).getOwnerWindow();
/*     */     }
/* 476 */     return paramWindow;
/*     */   }
/*     */ 
/*     */   void doAutoHide()
/*     */   {
/* 486 */     hide();
/* 487 */     if (getOnAutoHide() != null)
/* 488 */       getOnAutoHide().handle(new Event(this, this, Event.ANY));
/*     */   }
/*     */ 
/*     */   WindowEventDispatcher createInternalEventDispatcher()
/*     */   {
/* 495 */     return new WindowEventDispatcher(new PopupEventRedirector(this), new WindowCloseRequestHandler(this), new EventHandlerManager(this));
/*     */   }
/*     */ 
/*     */   private void startMonitorOwnerEvents(Window paramWindow)
/*     */   {
/* 502 */     EventRedirector localEventRedirector = paramWindow.getInternalEventDispatcher().getEventRedirector();
/*     */ 
/* 505 */     localEventRedirector.addEventDispatcher(getEventDispatcher());
/*     */   }
/*     */ 
/*     */   private void stopMonitorOwnerEvents(Window paramWindow) {
/* 509 */     EventRedirector localEventRedirector = paramWindow.getInternalEventDispatcher().getEventRedirector();
/*     */ 
/* 512 */     localEventRedirector.removeEventDispatcher(getEventDispatcher());
/*     */   }
/*     */ 
/*     */   private void bindOwnerFocusedProperty(Window paramWindow)
/*     */   {
/* 518 */     this.ownerFocusedListener = new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
/*     */       {
/* 524 */         PopupWindow.this.setFocused(paramAnonymousBoolean2.booleanValue());
/*     */       }
/*     */     };
/* 527 */     paramWindow.focusedProperty().addListener(this.ownerFocusedListener);
/*     */   }
/*     */ 
/*     */   private void unbindOwnerFocusedProperty(Window paramWindow) {
/* 531 */     paramWindow.focusedProperty().removeListener(this.ownerFocusedListener);
/* 532 */     this.ownerFocusedListener = null;
/*     */   }
/*     */ 
/*     */   private void handleAutofixActivation(boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 539 */     boolean bool = (paramBoolean1) && (paramBoolean2);
/* 540 */     if (this.autofixActive != bool) {
/* 541 */       this.autofixActive = bool;
/* 542 */       if (bool) {
/* 543 */         this.autofixHandler = new AutofixHandler(null);
/* 544 */         widthProperty().addListener(this.autofixHandler);
/* 545 */         heightProperty().addListener(this.autofixHandler);
/* 546 */         Screen.getScreens().addListener(this.autofixHandler);
/* 547 */         this.autofixHandler.adjustPosition();
/*     */       } else {
/* 549 */         widthProperty().removeListener(this.autofixHandler);
/* 550 */         heightProperty().removeListener(this.autofixHandler);
/* 551 */         Screen.getScreens().removeListener(this.autofixHandler);
/* 552 */         this.autofixHandler = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void validateOwnerWindow(Window paramWindow) {
/* 558 */     if (paramWindow == null) {
/* 559 */       throw new NullPointerException("Owner window must not be null");
/*     */     }
/*     */ 
/* 562 */     if (wouldCreateCycle(paramWindow, this)) {
/* 563 */       throw new IllegalArgumentException("Specified owner window would create cycle in the window hierarchy");
/*     */     }
/*     */ 
/* 568 */     if ((isShowing()) && (getOwnerWindow() != paramWindow))
/* 569 */       throw new IllegalStateException("Popup is already shown with different owner window");
/*     */   }
/*     */ 
/*     */   private static Window getOwnerWindow(Window paramWindow)
/*     */   {
/* 575 */     if ((paramWindow instanceof PopupWindow)) {
/* 576 */       return ((PopupWindow)paramWindow).getOwnerWindow();
/*     */     }
/*     */ 
/* 579 */     if ((paramWindow instanceof Stage)) {
/* 580 */       return ((Stage)paramWindow).getOwner();
/*     */     }
/*     */ 
/* 583 */     return null;
/*     */   }
/*     */ 
/*     */   private static boolean wouldCreateCycle(Window paramWindow1, Window paramWindow2) {
/* 587 */     while (paramWindow1 != null) {
/* 588 */       if (paramWindow1 == paramWindow2) {
/* 589 */         return true;
/*     */       }
/*     */ 
/* 592 */       paramWindow1 = getOwnerWindow(paramWindow1);
/*     */     }
/*     */ 
/* 595 */     return false;
/*     */   }
/*     */ 
/*     */   static class PopupEventRedirector extends EventRedirector
/*     */   {
/* 622 */     private static final KeyCombination ESCAPE_KEY_COMBINATION = KeyCombination.keyCombination("Esc");
/*     */     private final PopupWindow popupWindow;
/*     */ 
/*     */     public PopupEventRedirector(PopupWindow paramPopupWindow)
/*     */     {
/* 627 */       super();
/* 628 */       this.popupWindow = paramPopupWindow;
/*     */     }
/*     */ 
/*     */     protected void handleRedirectedEvent(Object paramObject, Event paramEvent)
/*     */     {
/* 634 */       if ((paramEvent instanceof KeyEvent)) {
/* 635 */         handleKeyEvent((KeyEvent)paramEvent);
/* 636 */         return;
/*     */       }
/*     */ 
/* 639 */       EventType localEventType = paramEvent.getEventType();
/*     */ 
/* 641 */       if (localEventType == MouseEvent.MOUSE_PRESSED) {
/* 642 */         handleMousePressedEvent(paramObject, paramEvent);
/* 643 */         return;
/*     */       }
/*     */ 
/* 646 */       if (localEventType == FocusUngrabEvent.FOCUS_UNGRAB) {
/* 647 */         handleFocusUngrabEvent();
/* 648 */         return;
/*     */       }
/*     */     }
/*     */ 
/*     */     private void handleKeyEvent(KeyEvent paramKeyEvent) {
/* 653 */       if (paramKeyEvent.isConsumed()) {
/* 654 */         return;
/*     */       }
/*     */ 
/* 657 */       Scene localScene1 = this.popupWindow.getScene();
/* 658 */       if (localScene1 != null) {
/* 659 */         Node localNode = localScene1.getFocusOwner();
/* 660 */         Scene localScene2 = localNode != null ? localNode : localScene1;
/*     */ 
/* 662 */         if (EventUtil.fireEvent(localScene2, new DirectEvent(paramKeyEvent)) == null)
/*     */         {
/* 664 */           paramKeyEvent.consume();
/* 665 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 669 */       if ((paramKeyEvent.getEventType() == KeyEvent.KEY_PRESSED) && (ESCAPE_KEY_COMBINATION.match(paramKeyEvent)))
/*     */       {
/* 671 */         handleEscapeKeyPressedEvent(paramKeyEvent);
/*     */       }
/*     */     }
/*     */ 
/*     */     private void handleEscapeKeyPressedEvent(Event paramEvent) {
/* 676 */       if (this.popupWindow.isHideOnEscape()) {
/* 677 */         this.popupWindow.doAutoHide();
/*     */ 
/* 679 */         if (this.popupWindow.getConsumeAutoHidingEvents())
/* 680 */           paramEvent.consume();
/*     */       }
/*     */     }
/*     */ 
/*     */     private void handleMousePressedEvent(Object paramObject, Event paramEvent)
/*     */     {
/* 691 */       if (this.popupWindow.getOwnerWindow() != paramObject) {
/* 692 */         return;
/*     */       }
/*     */ 
/* 695 */       if ((this.popupWindow.isAutoHide()) && (!isOwnerNodeEvent(paramEvent)))
/*     */       {
/* 698 */         Event.fireEvent(this.popupWindow, new FocusUngrabEvent());
/*     */ 
/* 700 */         this.popupWindow.doAutoHide();
/*     */ 
/* 702 */         if (this.popupWindow.getConsumeAutoHidingEvents())
/* 703 */           paramEvent.consume();
/*     */       }
/*     */     }
/*     */ 
/*     */     private void handleFocusUngrabEvent()
/*     */     {
/* 709 */       if (this.popupWindow.isAutoHide())
/* 710 */         this.popupWindow.doAutoHide();
/*     */     }
/*     */ 
/*     */     private boolean isOwnerNodeEvent(Event paramEvent)
/*     */     {
/* 715 */       Node localNode = this.popupWindow.getOwnerNode();
/* 716 */       if (localNode == null) {
/* 717 */         return false;
/*     */       }
/*     */ 
/* 720 */       EventTarget localEventTarget = paramEvent.getTarget();
/* 721 */       if (!(localEventTarget instanceof Node)) {
/* 722 */         return false;
/*     */       }
/*     */ 
/* 725 */       Object localObject = (Node)localEventTarget;
/*     */       do {
/* 727 */         if (localObject == localNode) {
/* 728 */           return true;
/*     */         }
/* 730 */         localObject = ((Node)localObject).getParent();
/* 731 */       }while (localObject != null);
/*     */ 
/* 733 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private final class AutofixHandler
/*     */     implements InvalidationListener
/*     */   {
/*     */     private AutofixHandler()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void invalidated(Observable paramObservable)
/*     */     {
/* 601 */       adjustPosition();
/*     */     }
/*     */ 
/*     */     public void adjustPosition() {
/* 605 */       Screen localScreen = Utils.getScreenForPoint(PopupWindow.this.getX(), PopupWindow.this.getY());
/*     */ 
/* 607 */       Rectangle2D localRectangle2D = Utils.hasFullScreenStage(localScreen) ? localScreen.getBounds() : localScreen.getVisualBounds();
/*     */ 
/* 611 */       double d1 = Math.min(PopupWindow.this.getX(), localRectangle2D.getMaxX() - PopupWindow.this.getWidth());
/* 612 */       double d2 = Math.min(PopupWindow.this.getY(), localRectangle2D.getMaxY() - PopupWindow.this.getHeight());
/* 613 */       d1 = Math.max(d1, localRectangle2D.getMinX());
/* 614 */       d2 = Math.max(d2, localRectangle2D.getMinY());
/* 615 */       PopupWindow.this.setX(d1);
/* 616 */       PopupWindow.this.setY(d2);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.PopupWindow
 * JD-Core Version:    0.6.2
 */