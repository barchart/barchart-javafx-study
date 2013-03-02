/*      */ package javafx.scene;
/*      */ 
/*      */ import com.sun.javafx.Logging;
/*      */ import com.sun.javafx.Utils;
/*      */ import com.sun.javafx.beans.event.AbstractNotifyListener;
/*      */ import com.sun.javafx.collections.TrackableObservableList;
/*      */ import com.sun.javafx.css.StyleManager;
/*      */ import com.sun.javafx.cursor.CursorFrame;
/*      */ import com.sun.javafx.event.EventHandlerManager;
/*      */ import com.sun.javafx.event.EventQueue;
/*      */ import com.sun.javafx.geom.PickRay;
/*      */ import com.sun.javafx.geom.transform.Affine2D;
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import com.sun.javafx.logging.PlatformLogger;
/*      */ import com.sun.javafx.perf.PerformanceTracker;
/*      */ import com.sun.javafx.perf.PerformanceTracker.SceneAccessor;
/*      */ import com.sun.javafx.robot.impl.FXRobotHelper;
/*      */ import com.sun.javafx.robot.impl.FXRobotHelper.FXRobotSceneAccessor;
/*      */ import com.sun.javafx.runtime.SystemProperties;
/*      */ import com.sun.javafx.scene.CSSFlags;
/*      */ import com.sun.javafx.scene.DirtyBits;
/*      */ import com.sun.javafx.scene.KeyboardShortcutsHandler;
/*      */ import com.sun.javafx.scene.SceneEventDispatcher;
/*      */ import com.sun.javafx.scene.traversal.Direction;
/*      */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*      */ import com.sun.javafx.tk.TKDragGestureListener;
/*      */ import com.sun.javafx.tk.TKDragSourceListener;
/*      */ import com.sun.javafx.tk.TKDropTargetListener;
/*      */ import com.sun.javafx.tk.TKPulseListener;
/*      */ import com.sun.javafx.tk.TKScene;
/*      */ import com.sun.javafx.tk.TKSceneListener;
/*      */ import com.sun.javafx.tk.TKScenePaintListener;
/*      */ import com.sun.javafx.tk.TKStage;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import com.sun.javafx.tk.Toolkit.ImageRenderingContext;
/*      */ import com.sun.javafx.tk.Toolkit.SceneAccessor;
/*      */ import com.sun.javafx.tk.Toolkit.WritableImageAccessor;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.EnumMap;
/*      */ import java.util.EnumSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javafx.animation.KeyFrame;
/*      */ import javafx.animation.KeyValue;
/*      */ import javafx.animation.Timeline;
/*      */ import javafx.application.Platform;
/*      */ import javafx.beans.DefaultProperty;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ObjectPropertyBase;
/*      */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*      */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*      */ import javafx.beans.property.ReadOnlyDoubleWrapper;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*      */ import javafx.beans.property.SimpleObjectProperty;
/*      */ import javafx.beans.value.ObservableBooleanValue;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.event.Event;
/*      */ import javafx.event.EventDispatchChain;
/*      */ import javafx.event.EventDispatcher;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.event.EventTarget;
/*      */ import javafx.event.EventType;
/*      */ import javafx.geometry.Bounds;
/*      */ import javafx.geometry.Orientation;
/*      */ import javafx.geometry.Point2D;
/*      */ import javafx.scene.image.WritableImage;
/*      */ import javafx.scene.input.ContextMenuEvent;
/*      */ import javafx.scene.input.DragEvent;
/*      */ import javafx.scene.input.Dragboard;
/*      */ import javafx.scene.input.GestureEvent;
/*      */ import javafx.scene.input.InputMethodEvent;
/*      */ import javafx.scene.input.InputMethodRequests;
/*      */ import javafx.scene.input.KeyCode;
/*      */ import javafx.scene.input.KeyCombination;
/*      */ import javafx.scene.input.KeyEvent;
/*      */ import javafx.scene.input.Mnemonic;
/*      */ import javafx.scene.input.MouseButton;
/*      */ import javafx.scene.input.MouseDragEvent;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.input.RotateEvent;
/*      */ import javafx.scene.input.ScrollEvent;
/*      */ import javafx.scene.input.ScrollEvent.HorizontalTextScrollUnits;
/*      */ import javafx.scene.input.ScrollEvent.VerticalTextScrollUnits;
/*      */ import javafx.scene.input.SwipeEvent;
/*      */ import javafx.scene.input.TouchEvent;
/*      */ import javafx.scene.input.TouchPoint;
/*      */ import javafx.scene.input.TouchPoint.State;
/*      */ import javafx.scene.input.TransferMode;
/*      */ import javafx.scene.input.ZoomEvent;
/*      */ import javafx.scene.paint.Color;
/*      */ import javafx.scene.paint.Paint;
/*      */ import javafx.stage.Stage;
/*      */ import javafx.stage.StageStyle;
/*      */ import javafx.stage.Window;
/*      */ import javafx.util.Callback;
/*      */ import javafx.util.Duration;
/*      */ 
/*      */ @DefaultProperty("root")
/*      */ public class Scene
/*      */   implements EventTarget
/*      */ {
/*  172 */   private double widthSetByUser = -1.0D;
/*  173 */   private double heightSetByUser = -1.0D;
/*  174 */   private boolean sizeInitialized = false;
/*  175 */   private boolean depthBuffer = false;
/*      */   private int dirtyBits;
/*  179 */   private final AccessControlContext acc = AccessController.getContext();
/*      */   private static final int MIN_DIRTY_CAPACITY = 30;
/*  337 */   private static boolean inSynchronizer = false;
/*  338 */   private static boolean inMousePick = false;
/*  339 */   private static boolean allowPGAccess = false;
/*  340 */   private static int pgAccessCount = 0;
/*      */ 
/*  343 */   private static boolean paused = false;
/*      */   private static final boolean PLATFORM_DRAG_GESTURE_INITIATION = false;
/*      */   private Node[] dirtyNodes;
/*      */   private int dirtyNodesSize;
/*  457 */   private Set<Parent> dirtyLayoutRootsA = new LinkedHashSet(10);
/*  458 */   private Set<Parent> dirtyLayoutRootsB = new LinkedHashSet(10);
/*  459 */   private Set<Parent> dirtyLayoutRoots = this.dirtyLayoutRootsA;
/*      */ 
/*      */   @Deprecated
/*      */   private TKScene impl_peer;
/*  549 */   ScenePulseListener scenePulseListener = new ScenePulseListener();
/*      */   private ReadOnlyObjectWrapper<Window> window;
/*  688 */   DnDGesture dndGesture = null;
/*      */   DragGestureListener dragGestureListener;
/*      */   private ReadOnlyDoubleWrapper x;
/*      */   private ReadOnlyDoubleWrapper y;
/*      */   private ReadOnlyDoubleWrapper width;
/*      */   private ReadOnlyDoubleWrapper height;
/*      */   private Camera pickingCamera;
/*  833 */   private TargetWrapper tmpTargetWrapper = new TargetWrapper(null);
/*      */   private ObjectProperty<Camera> camera;
/*      */   private Camera oldCamera;
/*  887 */   private final AbstractNotifyListener cameraChangeListener = new AbstractNotifyListener()
/*      */   {
/*      */     public void invalidated(Observable paramAnonymousObservable)
/*      */     {
/*  891 */       if (Scene.this.getCamera().isDirty())
/*  892 */         Scene.this.markDirty(Scene.DirtyBits.CAMERA_DIRTY);
/*      */     }
/*  887 */   };
/*      */   private ObjectProperty<Paint> fill;
/*      */   private ObjectProperty<Parent> root;
/*      */   Parent oldRoot;
/* 1181 */   private static TKPulseListener snapshotPulseListener = null;
/*      */   private static List<Runnable> snapshotRunnableListA;
/*      */   private static List<Runnable> snapshotRunnableListB;
/*      */   private static List<Runnable> snapshotRunnableList;
/* 1340 */   boolean initialized = false;
/*      */   private ObjectProperty<Cursor> cursor;
/* 1382 */   private final ObservableList<String> stylesheets = new TrackableObservableList()
/*      */   {
/*      */     protected void onChanged(ListChangeListener.Change<String> paramAnonymousChange) {
/* 1385 */       StyleManager.getInstance().updateStylesheets(Scene.this);
/*      */ 
/* 1388 */       while (paramAnonymousChange.next()) {
/* 1389 */         if (paramAnonymousChange.wasRemoved()) {
/* 1390 */           Scene.this.getRoot().impl_cssResetInitialValues();
/*      */         }
/*      */       }
/* 1393 */       Scene.this.getRoot().impl_reapplyCSS();
/*      */     }
/* 1382 */   };
/*      */   private PerformanceTracker tracker;
/* 1520 */   private static final Object trackerMonitor = new Object();
/*      */   private MouseHandler mouseHandler;
/*      */   private ClickGenerator clickGenerator;
/*      */   private Point2D cursorScreenPos;
/*      */   private Point2D cursorScenePos;
/* 1537 */   private final TouchGesture scrollGesture = new TouchGesture(null);
/* 1538 */   private final TouchGesture zoomGesture = new TouchGesture(null);
/* 1539 */   private final TouchGesture rotateGesture = new TouchGesture(null);
/* 1540 */   private final TouchGesture swipeGesture = new TouchGesture(null);
/*      */ 
/* 1543 */   private TouchMap touchMap = new TouchMap(null);
/* 1544 */   private TouchEvent nextTouchEvent = null;
/* 1545 */   private TouchPoint[] touchPoints = null;
/* 1546 */   private int touchEventSetId = 0;
/* 1547 */   private int touchPointIndex = 0;
/* 1548 */   private Map<Integer, EventTarget> touchTargets = new HashMap();
/*      */ 
/* 1775 */   private KeyHandler keyHandler = null;
/*      */ 
/* 1787 */   private boolean focusDirty = true;
/*      */   Map traversalRegistry;
/*      */   private Node oldFocusOwner;
/* 1936 */   private ReadOnlyObjectWrapper<Node> focusOwner = new ReadOnlyObjectWrapper(this, "focusOwner")
/*      */   {
/*      */     protected void invalidated()
/*      */     {
/* 1940 */       if (Scene.this.oldFocusOwner != null) {
/* 1941 */         ((Node.FocusedProperty)Scene.this.oldFocusOwner.focusedProperty()).store(false);
/*      */       }
/* 1943 */       Node localNode = (Node)get();
/* 1944 */       if (localNode != null) {
/* 1945 */         ((Node.FocusedProperty)localNode.focusedProperty()).store(Scene.access$2600(Scene.this).windowFocused);
/* 1946 */         if (localNode != Scene.this.oldFocusOwner) {
/* 1947 */           localNode.getScene().impl_enableInputMethodEvents((localNode.getInputMethodRequests() != null) && (localNode.getOnInputMethodTextChanged() != null));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1952 */       if (Scene.this.oldFocusOwner != null) {
/* 1953 */         ((Node.FocusedProperty)Scene.this.oldFocusOwner.focusedProperty()).notifyListeners();
/*      */       }
/* 1955 */       if (localNode != null) {
/* 1956 */         ((Node.FocusedProperty)localNode.focusedProperty()).notifyListeners();
/*      */       }
/* 1958 */       PlatformLogger localPlatformLogger = Logging.getFocusLogger();
/* 1959 */       if (localPlatformLogger.isLoggable(500)) {
/* 1960 */         localPlatformLogger.fine("Changed focus from " + Scene.this.oldFocusOwner + " to " + localNode);
/*      */       }
/*      */ 
/* 1963 */       Scene.this.oldFocusOwner = localNode;
/*      */     }
/* 1936 */   };
/*      */ 
/* 2018 */   Runnable testPulseListener = null;
/*      */   private ObjectProperty<EventDispatcher> eventDispatcher;
/*      */   private SceneEventDispatcher internalEventDispatcher;
/*      */   private ObjectProperty<EventHandler<? super ContextMenuEvent>> onContextMenuRequested;
/*      */   private ObjectProperty<EventHandler<? super MouseEvent>> onMouseClicked;
/*      */   private ObjectProperty<EventHandler<? super MouseEvent>> onMouseDragged;
/*      */   private ObjectProperty<EventHandler<? super MouseEvent>> onMouseEntered;
/*      */   private ObjectProperty<EventHandler<? super MouseEvent>> onMouseExited;
/*      */   private ObjectProperty<EventHandler<? super MouseEvent>> onMouseMoved;
/*      */   private ObjectProperty<EventHandler<? super MouseEvent>> onMousePressed;
/*      */   private ObjectProperty<EventHandler<? super MouseEvent>> onMouseReleased;
/*      */   private ObjectProperty<EventHandler<? super MouseEvent>> onDragDetected;
/*      */   private ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragOver;
/*      */   private ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragReleased;
/*      */   private ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragEntered;
/*      */   private ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragExited;
/*      */   private ObjectProperty<EventHandler<? super ScrollEvent>> onScrollStarted;
/*      */   private ObjectProperty<EventHandler<? super ScrollEvent>> onScroll;
/*      */   private ObjectProperty<EventHandler<? super ScrollEvent>> onScrollFinished;
/*      */   private ObjectProperty<EventHandler<? super RotateEvent>> onRotationStarted;
/*      */   private ObjectProperty<EventHandler<? super RotateEvent>> onRotate;
/*      */   private ObjectProperty<EventHandler<? super RotateEvent>> onRotationFinished;
/*      */   private ObjectProperty<EventHandler<? super ZoomEvent>> onZoomStarted;
/*      */   private ObjectProperty<EventHandler<? super ZoomEvent>> onZoom;
/*      */   private ObjectProperty<EventHandler<? super ZoomEvent>> onZoomFinished;
/*      */   private ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeUp;
/*      */   private ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeDown;
/*      */   private ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeLeft;
/*      */   private ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeRight;
/*      */   private ObjectProperty<EventHandler<? super TouchEvent>> onTouchPressed;
/*      */   private ObjectProperty<EventHandler<? super TouchEvent>> onTouchMoved;
/*      */   private ObjectProperty<EventHandler<? super TouchEvent>> onTouchReleased;
/*      */   private ObjectProperty<EventHandler<? super TouchEvent>> onTouchStationary;
/*      */   private ObjectProperty<EventHandler<? super DragEvent>> onDragEntered;
/*      */   private ObjectProperty<EventHandler<? super DragEvent>> onDragExited;
/*      */   private ObjectProperty<EventHandler<? super DragEvent>> onDragOver;
/*      */   private ObjectProperty<EventHandler<? super DragEvent>> onDragDropped;
/*      */   private ObjectProperty<EventHandler<? super DragEvent>> onDragDone;
/*      */   private ObjectProperty<EventHandler<? super KeyEvent>> onKeyPressed;
/*      */   private ObjectProperty<EventHandler<? super KeyEvent>> onKeyReleased;
/*      */   private ObjectProperty<EventHandler<? super KeyEvent>> onKeyTyped;
/*      */   private ObjectProperty<EventHandler<? super InputMethodEvent>> onInputMethodTextChanged;
/*      */ 
/*      */   public Scene(Parent paramParent)
/*      */   {
/*  198 */     this(paramParent, -1.0D, -1.0D, Color.WHITE, false);
/*      */   }
/*      */ 
/*      */   public Scene(Parent paramParent, double paramDouble1, double paramDouble2)
/*      */   {
/*  229 */     this(paramParent, paramDouble1, paramDouble2, Color.WHITE, false);
/*      */   }
/*      */ 
/*      */   public Scene(Parent paramParent, Paint paramPaint)
/*      */   {
/*  243 */     this(paramParent, -1.0D, -1.0D, paramPaint, false);
/*      */   }
/*      */ 
/*      */   public Scene(Parent paramParent, double paramDouble1, double paramDouble2, Paint paramPaint)
/*      */   {
/*  260 */     this(paramParent, paramDouble1, paramDouble2, paramPaint, false);
/*      */   }
/*      */ 
/*      */   public Scene(Parent paramParent, double paramDouble1, double paramDouble2, boolean paramBoolean)
/*      */   {
/*  284 */     this(paramParent, paramDouble1, paramDouble2, Color.WHITE, paramBoolean);
/*      */   }
/*      */ 
/*      */   private Scene(Parent paramParent, double paramDouble1, double paramDouble2, Paint paramPaint, boolean paramBoolean)
/*      */   {
/*  290 */     Toolkit.getToolkit().checkFxUserThread();
/*  291 */     setRoot(paramParent);
/*  292 */     init(paramDouble1, paramDouble2, paramBoolean);
/*  293 */     setFill(paramPaint);
/*      */   }
/*      */ 
/*      */   static boolean isPGAccessAllowed()
/*      */   {
/*  352 */     return (inSynchronizer) || (inMousePick) || (allowPGAccess);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public static void impl_setAllowPGAccess(boolean paramBoolean)
/*      */   {
/*  361 */     if (Utils.assertionEnabled())
/*  362 */       if (paramBoolean) {
/*  363 */         pgAccessCount += 1;
/*  364 */         allowPGAccess = true;
/*      */       }
/*      */       else {
/*  367 */         if (pgAccessCount <= 0) {
/*  368 */           throw new AssertionError("*** pgAccessCount underflow");
/*      */         }
/*  370 */         if (--pgAccessCount == 0)
/*  371 */           allowPGAccess = false;
/*      */       }
/*      */   }
/*      */ 
/*      */   void addToDirtyList(Node paramNode)
/*      */   {
/*  408 */     if (((this.dirtyNodes == null) || (this.dirtyNodesSize == 0)) && 
/*  409 */       (this.impl_peer != null)) {
/*  410 */       Toolkit.getToolkit().requestNextPulse();
/*      */     }
/*      */ 
/*  414 */     if (this.dirtyNodes != null) {
/*  415 */       if (this.dirtyNodesSize == this.dirtyNodes.length) {
/*  416 */         Node[] arrayOfNode = new Node[this.dirtyNodesSize + (this.dirtyNodesSize >> 1)];
/*  417 */         System.arraycopy(this.dirtyNodes, 0, arrayOfNode, 0, this.dirtyNodesSize);
/*  418 */         this.dirtyNodes = arrayOfNode;
/*      */       }
/*  420 */       this.dirtyNodes[(this.dirtyNodesSize++)] = paramNode;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doCSSPass() {
/*  425 */     Parent localParent = getRoot();
/*      */ 
/*  441 */     if (localParent.cssFlag != CSSFlags.CLEAN)
/*      */     {
/*  445 */       localParent.impl_clearDirty(DirtyBits.NODE_CSS);
/*  446 */       localParent.processCSS();
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void addToDirtyLayoutList(Parent paramParent)
/*      */   {
/*  470 */     if (this.dirtyLayoutRoots.isEmpty()) {
/*  471 */       Toolkit.getToolkit().requestNextPulse();
/*      */     }
/*      */ 
/*  474 */     this.dirtyLayoutRoots.add(paramParent);
/*      */   }
/*      */ 
/*      */   void removeFromDirtyLayoutList(Parent paramParent)
/*      */   {
/*  481 */     this.dirtyLayoutRoots.remove(paramParent);
/*      */   }
/*      */ 
/*      */   private void doLayoutPass()
/*      */   {
/*  487 */     layoutDirtyRoots();
/*  488 */     layoutDirtyRoots();
/*      */ 
/*  492 */     if (this.dirtyLayoutRoots.size() > 0) {
/*  493 */       PlatformLogger localPlatformLogger = Logging.getLayoutLogger();
/*  494 */       if (localPlatformLogger.isLoggable(400)) {
/*  495 */         localPlatformLogger.finer("after layout pass, " + this.dirtyLayoutRoots.size() + " layout root nodes still dirty");
/*      */       }
/*  497 */       Toolkit.getToolkit().requestNextPulse();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void layoutDirtyRoots() {
/*  502 */     if (this.dirtyLayoutRoots.size() > 0) {
/*  503 */       PlatformLogger localPlatformLogger = Logging.getLayoutLogger();
/*  504 */       Set localSet = this.dirtyLayoutRoots;
/*  505 */       if (this.dirtyLayoutRoots == this.dirtyLayoutRootsA)
/*  506 */         this.dirtyLayoutRoots = this.dirtyLayoutRootsB;
/*      */       else {
/*  508 */         this.dirtyLayoutRoots = this.dirtyLayoutRootsA;
/*      */       }
/*      */ 
/*  511 */       for (Parent localParent : localSet) {
/*  512 */         if ((localParent.getScene() == this) && (localParent.isNeedsLayout())) {
/*  513 */           if (localPlatformLogger.isLoggable(500)) {
/*  514 */             localPlatformLogger.fine("<<< START >>> root = " + localParent.toString());
/*      */           }
/*  516 */           localParent.layout();
/*  517 */           if (localPlatformLogger.isLoggable(500)) {
/*  518 */             localPlatformLogger.fine("<<<  END  >>> root = " + localParent.toString());
/*      */           }
/*      */         }
/*      */       }
/*  522 */       localSet.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public TKScene impl_getPeer()
/*      */   {
/*  543 */     return this.impl_peer;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public TKPulseListener impl_getScenePulseListener()
/*      */   {
/*  557 */     if (SystemProperties.isDebug()) {
/*  558 */       return this.scenePulseListener;
/*      */     }
/*  560 */     return null;
/*      */   }
/*      */ 
/*      */   private void setWindow(Window paramWindow)
/*      */   {
/*  569 */     windowPropertyImpl().set(paramWindow);
/*      */   }
/*      */ 
/*      */   public final Window getWindow() {
/*  573 */     return this.window == null ? null : (Window)this.window.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<Window> windowProperty() {
/*  577 */     return windowPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyObjectWrapper<Window> windowPropertyImpl() {
/*  581 */     if (this.window == null) {
/*  582 */       this.window = new ReadOnlyObjectWrapper() {
/*      */         private Window oldWindow;
/*      */ 
/*      */         protected void invalidated() {
/*  586 */           Window localWindow = (Window)get();
/*  587 */           Scene.access$500(Scene.this).windowForSceneChanged(this.oldWindow, localWindow);
/*  588 */           if (this.oldWindow != null) {
/*  589 */             Scene.this.impl_disposePeer();
/*      */           }
/*  591 */           if (localWindow != null) {
/*  592 */             Scene.this.impl_initPeer();
/*      */           }
/*      */ 
/*  595 */           this.oldWindow = localWindow;
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  600 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  605 */           return "window";
/*      */         }
/*      */       };
/*      */     }
/*  609 */     return this.window;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_setWindow(Window paramWindow)
/*      */   {
/*  618 */     setWindow(paramWindow);
/*  619 */     if (this.impl_peer != null)
/*  620 */       this.impl_peer.markDirty();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_initPeer()
/*      */   {
/*  630 */     if (this.impl_peer != null) {
/*  631 */       return;
/*      */     }
/*  633 */     PerformanceTracker.logEvent("Scene.initPeer started");
/*  634 */     Toolkit localToolkit = Toolkit.getToolkit();
/*  635 */     if (getWindow() == null) {
/*  636 */       return;
/*      */     }
/*  638 */     TKStage localTKStage = getWindow().impl_getPeer();
/*  639 */     if (localTKStage == null) {
/*  640 */       return;
/*      */     }
/*      */ 
/*  643 */     impl_setAllowPGAccess(true);
/*      */ 
/*  645 */     this.impl_peer = localTKStage.createTKScene(isDepthBuffer());
/*  646 */     PerformanceTracker.logEvent("Scene.initPeer TKScene created");
/*  647 */     this.impl_peer.setSecurityContext(this.acc);
/*  648 */     this.impl_peer.setTKSceneListener(new ScenePeerListener());
/*  649 */     this.impl_peer.setTKScenePaintListener(new ScenePeerPaintListener(null));
/*  650 */     this.impl_peer.setScene(this);
/*  651 */     PerformanceTracker.logEvent("Scene.initPeer TKScene set");
/*  652 */     this.impl_peer.setRoot(getRoot().impl_getPGNode());
/*  653 */     this.impl_peer.setFillPaint(getFill() == null ? null : localToolkit.getPaint(getFill()));
/*  654 */     this.impl_peer.setCamera(getCamera() == null ? null : getCamera().getPlatformCamera());
/*  655 */     this.pickingCamera = getCamera();
/*      */ 
/*  657 */     impl_setAllowPGAccess(false);
/*      */ 
/*  659 */     PerformanceTracker.logEvent("Scene.initPeer TKScene initialized");
/*  660 */     localToolkit.addSceneTkPulseListener(this.scenePulseListener);
/*      */ 
/*  668 */     localToolkit.enableDrop(this.impl_peer, new DropTargetListener());
/*  669 */     localToolkit.installInputMethodRequests(this.impl_peer, new InputMethodRequestsDelegate());
/*  670 */     PerformanceTracker.logEvent("Scene.initPeer finished");
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_disposePeer()
/*      */   {
/*  679 */     if (this.impl_peer == null) {
/*  680 */       return;
/*      */     }
/*  682 */     Toolkit localToolkit = Toolkit.getToolkit();
/*  683 */     localToolkit.removeSceneTkPulseListener(this.scenePulseListener);
/*  684 */     this.impl_peer.setScene(null);
/*  685 */     this.impl_peer = null;
/*      */   }
/*      */ 
/*      */   private final void setX(double paramDouble)
/*      */   {
/*  696 */     xPropertyImpl().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getX() {
/*  700 */     return this.x == null ? 0.0D : this.x.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyDoubleProperty xProperty() {
/*  704 */     return xPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyDoubleWrapper xPropertyImpl() {
/*  708 */     if (this.x == null) {
/*  709 */       this.x = new ReadOnlyDoubleWrapper(this, "x");
/*      */     }
/*  711 */     return this.x;
/*      */   }
/*      */ 
/*      */   private final void setY(double paramDouble)
/*      */   {
/*  720 */     yPropertyImpl().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getY() {
/*  724 */     return this.y == null ? 0.0D : this.y.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyDoubleProperty yProperty() {
/*  728 */     return yPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyDoubleWrapper yPropertyImpl() {
/*  732 */     if (this.y == null) {
/*  733 */       this.y = new ReadOnlyDoubleWrapper(this, "y");
/*      */     }
/*  735 */     return this.y;
/*      */   }
/*      */ 
/*      */   private final void setWidth(double paramDouble)
/*      */   {
/*  744 */     widthPropertyImpl().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getWidth() {
/*  748 */     return this.width == null ? 0.0D : this.width.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyDoubleProperty widthProperty() {
/*  752 */     return widthPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyDoubleWrapper widthPropertyImpl() {
/*  756 */     if (this.width == null) {
/*  757 */       this.width = new ReadOnlyDoubleWrapper()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  761 */           Parent localParent = Scene.this.getRoot();
/*  762 */           if (localParent.isResizable())
/*  763 */             localParent.resize(get() - localParent.getLayoutX() - localParent.getTranslateX(), localParent.getLayoutBounds().getHeight());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  769 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  774 */           return "width";
/*      */         }
/*      */       };
/*      */     }
/*  778 */     return this.width;
/*      */   }
/*      */ 
/*      */   private final void setHeight(double paramDouble)
/*      */   {
/*  787 */     heightPropertyImpl().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getHeight() {
/*  791 */     return this.height == null ? 0.0D : this.height.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyDoubleProperty heightProperty() {
/*  795 */     return heightPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyDoubleWrapper heightPropertyImpl() {
/*  799 */     if (this.height == null) {
/*  800 */       this.height = new ReadOnlyDoubleWrapper()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  804 */           Parent localParent = Scene.this.getRoot();
/*  805 */           if (localParent.isResizable())
/*  806 */             localParent.resize(localParent.getLayoutBounds().getWidth(), get() - localParent.getLayoutY() - localParent.getTranslateY());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  812 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  817 */           return "height";
/*      */         }
/*      */       };
/*      */     }
/*  821 */     return this.height;
/*      */   }
/*      */ 
/*      */   public final void setCamera(Camera paramCamera)
/*      */   {
/*  849 */     cameraProperty().set(paramCamera);
/*      */   }
/*      */ 
/*      */   public final Camera getCamera() {
/*  853 */     return this.camera == null ? null : (Camera)this.camera.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Camera> cameraProperty()
/*      */   {
/*  858 */     if (this.camera == null) {
/*  859 */       this.camera = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  863 */           if (Scene.this.oldCamera != null) {
/*  864 */             Scene.this.oldCamera.dirtyProperty().removeListener(Scene.this.cameraChangeListener.getWeakListener());
/*      */           }
/*  866 */           Scene.this.oldCamera = ((Camera)get());
/*  867 */           if (get() != null) {
/*  868 */             ((Camera)get()).dirtyProperty().addListener(Scene.this.cameraChangeListener.getWeakListener());
/*      */           }
/*  870 */           Scene.this.markDirty(Scene.DirtyBits.CAMERA_DIRTY);
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  875 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  880 */           return "camera";
/*      */         }
/*      */       };
/*      */     }
/*  884 */     return this.camera;
/*      */   }
/*      */ 
/*      */   public final void setFill(Paint paramPaint)
/*      */   {
/*  908 */     fillProperty().set(paramPaint);
/*      */   }
/*      */ 
/*      */   public final Paint getFill() {
/*  912 */     return this.fill == null ? Color.WHITE : (Paint)this.fill.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Paint> fillProperty() {
/*  916 */     if (this.fill == null) {
/*  917 */       this.fill = new ObjectPropertyBase(Color.WHITE)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  921 */           Scene.this.markDirty(Scene.DirtyBits.FILL_DIRTY);
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  926 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  931 */           return "fill";
/*      */         }
/*      */       };
/*      */     }
/*  935 */     return this.fill;
/*      */   }
/*      */ 
/*      */   public final void setRoot(Parent paramParent)
/*      */   {
/*  953 */     rootProperty().set(paramParent);
/*      */   }
/*      */ 
/*      */   public final Parent getRoot() {
/*  957 */     return this.root == null ? null : (Parent)this.root.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Parent> rootProperty()
/*      */   {
/*  962 */     if (this.root == null) {
/*  963 */       this.root = new ObjectPropertyBase()
/*      */       {
/*      */         private void forceUnbind() {
/*  966 */           System.err.println("Unbinding illegal root.");
/*  967 */           unbind();
/*      */         }
/*      */ 
/*      */         protected void invalidated()
/*      */         {
/*  972 */           Parent localParent = (Parent)get();
/*      */ 
/*  974 */           if (localParent == null) {
/*  975 */             if (isBound()) forceUnbind();
/*  976 */             throw new NullPointerException("Scene's root cannot be null");
/*      */           }
/*      */ 
/*  979 */           if (localParent.getParent() != null) {
/*  980 */             if (isBound()) forceUnbind();
/*  981 */             throw new IllegalArgumentException(localParent + "is already inside a scene-graph and cannot be set as root");
/*      */           }
/*      */ 
/*  984 */           if (localParent.getClipParent() != null) {
/*  985 */             if (isBound()) forceUnbind();
/*  986 */             throw new IllegalArgumentException(localParent + "is set as a clip on another node, so cannot be set as root");
/*      */           }
/*      */ 
/*  989 */           if ((localParent.isSceneRoot()) && (localParent.getScene() != Scene.this)) {
/*  990 */             if (isBound()) forceUnbind();
/*  991 */             throw new IllegalArgumentException(localParent + "is already set as root of another scene");
/*      */           }
/*      */ 
/*  995 */           if (Scene.this.oldRoot != null) {
/*  996 */             Scene.this.oldRoot.setScene(null);
/*  997 */             Scene.this.oldRoot.setImpl_traversalEngine(null);
/*      */           }
/*  999 */           Scene.this.oldRoot = localParent;
/* 1000 */           if (localParent.getImpl_traversalEngine() == null) {
/* 1001 */             localParent.setImpl_traversalEngine(new TraversalEngine(localParent, true));
/*      */           }
/* 1003 */           localParent.getStyleClass().add(0, "root");
/* 1004 */           localParent.setScene(Scene.this);
/* 1005 */           Scene.this.markDirty(Scene.DirtyBits.ROOT_DIRTY);
/* 1006 */           localParent.resize(Scene.this.getWidth(), Scene.this.getHeight());
/* 1007 */           localParent.requestLayout();
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 1012 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 1017 */           return "root";
/*      */         }
/*      */       };
/*      */     }
/* 1021 */     return this.root;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public Object renderToImage(Object paramObject)
/*      */   {
/* 1038 */     return renderToImage(paramObject, 1.0F);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public Object renderToImage(Object paramObject, float paramFloat)
/*      */   {
/* 1058 */     if (!paused) {
/* 1059 */       Toolkit.getToolkit().checkFxUserThread();
/*      */     }
/*      */ 
/* 1066 */     return doSnapshot(null, paramFloat).impl_getPlatformImage();
/*      */   }
/*      */ 
/*      */   private void doLayoutPassWithoutPulse(int paramInt) {
/* 1070 */     for (int i = 0; (this.dirtyLayoutRoots.size() > 0) && (i != paramInt); i++)
/* 1071 */       layoutDirtyRoots();
/*      */   }
/*      */ 
/*      */   void setNeedsRepaint()
/*      */   {
/* 1076 */     if (this.impl_peer != null)
/* 1077 */       this.impl_peer.entireSceneNeedsRepaint();
/*      */   }
/*      */ 
/*      */   void doCSSLayoutSyncForSnapshot(Node paramNode)
/*      */   {
/* 1085 */     if (!this.sizeInitialized)
/* 1086 */       preferredSize();
/*      */     else {
/* 1088 */       doCSSPass();
/*      */     }
/*      */ 
/* 1093 */     doLayoutPassWithoutPulse(3);
/*      */ 
/* 1095 */     if (!paused)
/* 1096 */       this.scenePulseListener.synchronizeSceneNodes();
/*      */   }
/*      */ 
/*      */   static WritableImage doSnapshot(Scene paramScene, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, Node paramNode, BaseTransform paramBaseTransform, boolean paramBoolean, Paint paramPaint, Camera paramCamera, WritableImage paramWritableImage)
/*      */   {
/* 1108 */     Toolkit localToolkit = Toolkit.getToolkit();
/* 1109 */     Toolkit.ImageRenderingContext localImageRenderingContext = new Toolkit.ImageRenderingContext();
/*      */ 
/* 1111 */     int i = (int)Math.floor(paramDouble1);
/* 1112 */     int j = (int)Math.floor(paramDouble2);
/* 1113 */     int k = (int)Math.ceil(paramDouble1 + paramDouble3);
/* 1114 */     int m = (int)Math.ceil(paramDouble2 + paramDouble4);
/* 1115 */     int n = Math.max(k - i, 1);
/* 1116 */     int i1 = Math.max(m - j, 1);
/* 1117 */     if (paramWritableImage == null) {
/* 1118 */       paramWritableImage = new WritableImage(n, i1);
/*      */     } else {
/* 1120 */       n = (int)paramWritableImage.getWidth();
/* 1121 */       i1 = (int)paramWritableImage.getHeight();
/*      */     }
/*      */ 
/* 1124 */     impl_setAllowPGAccess(true);
/* 1125 */     localImageRenderingContext.x = i;
/* 1126 */     localImageRenderingContext.y = j;
/* 1127 */     localImageRenderingContext.width = n;
/* 1128 */     localImageRenderingContext.height = i1;
/* 1129 */     localImageRenderingContext.transform = paramBaseTransform;
/* 1130 */     localImageRenderingContext.depthBuffer = paramBoolean;
/* 1131 */     localImageRenderingContext.root = paramNode.impl_getPGNode();
/* 1132 */     localImageRenderingContext.platformPaint = (paramPaint == null ? null : localToolkit.getPaint(paramPaint));
/* 1133 */     if (paramCamera != null) {
/* 1134 */       paramCamera.update();
/* 1135 */       localImageRenderingContext.camera = paramCamera.getPlatformCamera();
/*      */     } else {
/* 1137 */       localImageRenderingContext.camera = null;
/*      */     }
/*      */ 
/* 1140 */     Toolkit.WritableImageAccessor localWritableImageAccessor = Toolkit.getWritableImageAccessor();
/* 1141 */     localImageRenderingContext.platformImage = localWritableImageAccessor.getTkImageLoader(paramWritableImage);
/* 1142 */     impl_setAllowPGAccess(false);
/* 1143 */     Object localObject = localToolkit.renderToImage(localImageRenderingContext);
/* 1144 */     localWritableImageAccessor.loadTkImage(paramWritableImage, localObject);
/*      */ 
/* 1149 */     if ((paramScene != null) && (paramScene.impl_peer != null)) {
/* 1150 */       paramScene.setNeedsRepaint();
/*      */     }
/*      */ 
/* 1153 */     return paramWritableImage;
/*      */   }
/*      */ 
/*      */   private WritableImage doSnapshot(WritableImage paramWritableImage, float paramFloat)
/*      */   {
/* 1162 */     doCSSLayoutSyncForSnapshot(getRoot());
/*      */ 
/* 1164 */     double d1 = getWidth();
/* 1165 */     double d2 = getHeight();
/* 1166 */     Object localObject = BaseTransform.IDENTITY_TRANSFORM;
/* 1167 */     if (paramFloat != 1.0F) {
/* 1168 */       Affine2D localAffine2D = new Affine2D();
/* 1169 */       localAffine2D.scale(paramFloat, paramFloat);
/* 1170 */       localObject = localAffine2D;
/* 1171 */       d1 *= paramFloat;
/* 1172 */       d2 *= paramFloat;
/*      */     }
/*      */ 
/* 1175 */     return doSnapshot(this, 0.0D, 0.0D, d1, d2, getRoot(), (BaseTransform)localObject, isDepthBuffer(), getFill(), getCamera(), paramWritableImage);
/*      */   }
/*      */ 
/*      */   static void addSnapshotRunnable(Runnable paramRunnable)
/*      */   {
/* 1188 */     Toolkit.getToolkit().checkFxUserThread();
/*      */ 
/* 1190 */     if (snapshotPulseListener == null) {
/* 1191 */       snapshotRunnableListA = new ArrayList();
/* 1192 */       snapshotRunnableListB = new ArrayList();
/* 1193 */       snapshotRunnableList = snapshotRunnableListA;
/*      */ 
/* 1195 */       snapshotPulseListener = new TKPulseListener() {
/*      */         public void pulse() {
/* 1197 */           if (Scene.snapshotRunnableList.size() > 0) {
/* 1198 */             List localList = Scene.snapshotRunnableList;
/* 1199 */             if (Scene.snapshotRunnableList == Scene.snapshotRunnableListA)
/* 1200 */               Scene.access$1302(Scene.snapshotRunnableListB);
/*      */             else {
/* 1202 */               Scene.access$1302(Scene.snapshotRunnableListA);
/*      */             }
/* 1204 */             for (Runnable localRunnable : localList) {
/*      */               try {
/* 1206 */                 localRunnable.run();
/*      */               } catch (Throwable localThrowable) {
/* 1208 */                 System.err.println("Exception in snapshot runnable");
/* 1209 */                 localThrowable.printStackTrace(System.err);
/*      */               }
/*      */             }
/* 1212 */             localList.clear();
/*      */           }
/*      */         }
/*      */       };
/* 1219 */       Toolkit.getToolkit().addPostSceneTkPulseListener(snapshotPulseListener);
/*      */     }
/* 1221 */     snapshotRunnableList.add(paramRunnable);
/* 1222 */     Toolkit.getToolkit().requestNextPulse();
/*      */   }
/*      */ 
/*      */   public WritableImage snapshot(WritableImage paramWritableImage)
/*      */   {
/* 1258 */     if (!paused) {
/* 1259 */       Toolkit.getToolkit().checkFxUserThread();
/*      */     }
/*      */ 
/* 1262 */     return doSnapshot(paramWritableImage, 1.0F);
/*      */   }
/*      */ 
/*      */   public void snapshot(Callback<SnapshotResult, Void> paramCallback, WritableImage paramWritableImage)
/*      */   {
/* 1311 */     Toolkit.getToolkit().checkFxUserThread();
/* 1312 */     if (paramCallback == null) {
/* 1313 */       throw new NullPointerException("The callback must not be null");
/*      */     }
/*      */ 
/* 1316 */     final Callback<SnapshotResult, Void> localCallback = paramCallback;
/* 1317 */     final WritableImage localWritableImage = paramWritableImage;
/*      */ 
/* 1322 */     Runnable local12 = new Runnable() {
/*      */       public void run() {
/* 1324 */         WritableImage localWritableImage = Scene.this.doSnapshot(localWritableImage, 1.0F);
/*      */ 
/* 1326 */         SnapshotResult localSnapshotResult = new SnapshotResult(localWritableImage, Scene.this, null);
/*      */         try {
/* 1328 */           Void localVoid = (Void)localCallback.call(localSnapshotResult);
/*      */         } catch (Throwable localThrowable) {
/* 1330 */           System.err.println("Exception in snapshot callback");
/* 1331 */           localThrowable.printStackTrace(System.err);
/*      */         }
/*      */       }
/*      */     };
/* 1336 */     addSnapshotRunnable(local12);
/*      */   }
/*      */ 
/*      */   public final void setCursor(Cursor paramCursor)
/*      */   {
/* 1349 */     cursorProperty().set(paramCursor);
/*      */   }
/*      */ 
/*      */   public final Cursor getCursor() {
/* 1353 */     return this.cursor == null ? null : (Cursor)this.cursor.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Cursor> cursorProperty() {
/* 1357 */     if (this.cursor == null) {
/* 1358 */       this.cursor = new SimpleObjectProperty(this, "cursor");
/*      */     }
/* 1360 */     return this.cursor;
/*      */   }
/*      */ 
/*      */   public Node lookup(String paramString)
/*      */   {
/* 1374 */     return getRoot().lookup(paramString);
/*      */   }
/*      */ 
/*      */   public final ObservableList<String> getStylesheets()
/*      */   {
/* 1405 */     return this.stylesheets;
/*      */   }
/*      */ 
/*      */   public final boolean isDepthBuffer()
/*      */   {
/* 1412 */     return this.depthBuffer;
/*      */   }
/*      */ 
/*      */   private void init(double paramDouble1, double paramDouble2, boolean paramBoolean) {
/* 1416 */     if (paramDouble1 >= 0.0D) {
/* 1417 */       this.widthSetByUser = paramDouble1;
/* 1418 */       setWidth((float)paramDouble1);
/*      */     }
/* 1420 */     if (paramDouble2 >= 0.0D) {
/* 1421 */       this.heightSetByUser = paramDouble2;
/* 1422 */       setHeight((float)paramDouble2);
/*      */     }
/* 1424 */     this.sizeInitialized = ((this.widthSetByUser >= 0.0D) && (this.heightSetByUser >= 0.0D));
/* 1425 */     this.depthBuffer = paramBoolean;
/* 1426 */     init();
/*      */   }
/*      */ 
/*      */   private void init() {
/* 1430 */     if (PerformanceTracker.isLoggingEnabled()) {
/* 1431 */       PerformanceTracker.logEvent("Scene.init for [" + this + "]");
/*      */     }
/* 1433 */     this.mouseHandler = new MouseHandler();
/* 1434 */     this.clickGenerator = new ClickGenerator();
/*      */ 
/* 1436 */     this.initialized = true;
/*      */ 
/* 1438 */     if (PerformanceTracker.isLoggingEnabled())
/* 1439 */       PerformanceTracker.logEvent("Scene.init for [" + this + "] - finished");
/*      */   }
/*      */ 
/*      */   private void preferredSize()
/*      */   {
/* 1444 */     Parent localParent = getRoot();
/*      */ 
/* 1449 */     doCSSPass();
/*      */ 
/* 1451 */     int i = 0;
/* 1452 */     int j = 0;
/*      */ 
/* 1454 */     double d1 = this.widthSetByUser;
/* 1455 */     double d2 = this.heightSetByUser;
/*      */ 
/* 1457 */     if (this.widthSetByUser < 0.0D) {
/* 1458 */       d1 = localParent.prefWidth(this.heightSetByUser >= 0.0D ? this.heightSetByUser : -1.0D);
/* 1459 */       d1 = localParent.boundedSize(d1, localParent.minWidth(this.heightSetByUser >= 0.0D ? this.heightSetByUser : -1.0D), localParent.maxWidth(this.heightSetByUser >= 0.0D ? this.heightSetByUser : -1.0D));
/*      */ 
/* 1462 */       i = 1;
/*      */     }
/* 1464 */     if (this.heightSetByUser < 0.0D) {
/* 1465 */       d2 = localParent.prefHeight(this.widthSetByUser >= 0.0D ? this.widthSetByUser : -1.0D);
/* 1466 */       d2 = localParent.boundedSize(d2, localParent.minHeight(this.widthSetByUser >= 0.0D ? this.widthSetByUser : -1.0D), localParent.maxHeight(this.widthSetByUser >= 0.0D ? this.widthSetByUser : -1.0D));
/*      */ 
/* 1469 */       j = 1;
/*      */     }
/* 1471 */     if (localParent.getContentBias() == Orientation.HORIZONTAL) {
/* 1472 */       if (this.heightSetByUser < 0.0D) {
/* 1473 */         d2 = localParent.boundedSize(localParent.prefHeight(d1), localParent.minHeight(d1), localParent.maxHeight(d1));
/*      */ 
/* 1477 */         j = 1;
/*      */       }
/* 1479 */     } else if ((localParent.getContentBias() == Orientation.VERTICAL) && 
/* 1480 */       (this.widthSetByUser < 0.0D)) {
/* 1481 */       d1 = localParent.boundedSize(localParent.prefWidth(d2), localParent.minWidth(d2), localParent.maxWidth(d2));
/*      */ 
/* 1485 */       i = 1;
/*      */     }
/*      */ 
/* 1488 */     localParent.resize(d1, d2);
/* 1489 */     doLayoutPass();
/*      */ 
/* 1491 */     if (i != 0) {
/* 1492 */       setWidth(localParent.isResizable() ? localParent.getLayoutX() + localParent.getTranslateX() + localParent.getLayoutBounds().getWidth() : localParent.getBoundsInParent().getMaxX());
/*      */     }
/*      */     else {
/* 1495 */       setWidth(this.widthSetByUser);
/*      */     }
/*      */ 
/* 1498 */     if (j != 0) {
/* 1499 */       setHeight(localParent.isResizable() ? localParent.getLayoutY() + localParent.getTranslateY() + localParent.getLayoutBounds().getHeight() : localParent.getBoundsInParent().getMaxY());
/*      */     }
/*      */     else {
/* 1502 */       setHeight(this.heightSetByUser);
/*      */     }
/*      */ 
/* 1505 */     this.sizeInitialized = ((getWidth() > 0.0D) && (getHeight() > 0.0D));
/*      */ 
/* 1507 */     PerformanceTracker.logEvent("Scene preferred bounds computation complete");
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_preferredSize()
/*      */   {
/* 1516 */     preferredSize();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_processMouseEvent(MouseEvent paramMouseEvent)
/*      */   {
/* 1558 */     if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED)
/*      */     {
/* 1561 */       return;
/*      */     }
/* 1563 */     this.mouseHandler.process(paramMouseEvent);
/*      */   }
/*      */ 
/*      */   private void processMenuEvent(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean)
/*      */   {
/* 1568 */     if (!paramBoolean) inMousePick = true;
/*      */     Object localObject2;
/*      */     Object localObject1;
/* 1569 */     if (paramBoolean) {
/* 1570 */       localObject2 = getFocusOwner();
/*      */ 
/* 1573 */       double d1 = paramDouble3 - paramDouble1;
/* 1574 */       double d2 = paramDouble4 - paramDouble2;
/* 1575 */       if (localObject2 != null) {
/* 1576 */         Bounds localBounds = ((Node)localObject2).localToScene(((Node)localObject2).getBoundsInLocal());
/*      */ 
/* 1578 */         paramDouble1 = localBounds.getMinX() + localBounds.getWidth() / 4.0D;
/* 1579 */         paramDouble2 = localBounds.getMinY() + localBounds.getHeight() / 2.0D;
/* 1580 */         localObject1 = localObject2;
/*      */       } else {
/* 1582 */         paramDouble1 = getWidth() / 4.0D;
/* 1583 */         paramDouble2 = getWidth() / 2.0D;
/* 1584 */         localObject1 = this;
/*      */       }
/*      */ 
/* 1587 */       paramDouble3 = paramDouble1 + d1;
/* 1588 */       paramDouble4 = paramDouble2 + d2;
/*      */     }
/*      */     else {
/* 1591 */       localObject1 = pick(paramDouble1, paramDouble2);
/*      */     }
/* 1593 */     if (localObject1 != null) {
/* 1594 */       localObject2 = ContextMenuEvent.impl_contextEvent(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramBoolean, ContextMenuEvent.CONTEXT_MENU_REQUESTED);
/*      */ 
/* 1596 */       Event.fireEvent((EventTarget)localObject1, (Event)localObject2);
/*      */     }
/* 1598 */     if (!paramBoolean) inMousePick = false; 
/*      */   }
/*      */ 
/*      */   private void processGestureEvent(GestureEvent paramGestureEvent, TouchGesture paramTouchGesture)
/*      */   {
/* 1602 */     EventTarget localEventTarget = null;
/*      */ 
/* 1604 */     inMousePick = true;
/*      */ 
/* 1606 */     if ((paramGestureEvent.getEventType() == ZoomEvent.ZOOM_STARTED) || (paramGestureEvent.getEventType() == RotateEvent.ROTATION_STARTED) || (paramGestureEvent.getEventType() == ScrollEvent.SCROLL_STARTED))
/*      */     {
/* 1609 */       paramTouchGesture.target = null;
/* 1610 */       paramTouchGesture.finished = false;
/*      */     }
/*      */ 
/* 1613 */     if ((paramTouchGesture.target != null) && ((!paramTouchGesture.finished) || (paramGestureEvent.isInertia())))
/* 1614 */       localEventTarget = paramTouchGesture.target;
/*      */     else {
/* 1616 */       localEventTarget = pick(paramGestureEvent.getX(), paramGestureEvent.getY());
/*      */     }
/*      */ 
/* 1619 */     if ((paramGestureEvent.getEventType() == ZoomEvent.ZOOM_STARTED) || (paramGestureEvent.getEventType() == RotateEvent.ROTATION_STARTED) || (paramGestureEvent.getEventType() == ScrollEvent.SCROLL_STARTED))
/*      */     {
/* 1622 */       paramTouchGesture.target = localEventTarget;
/*      */     }
/* 1624 */     if ((paramGestureEvent.getEventType() != ZoomEvent.ZOOM_FINISHED) && (paramGestureEvent.getEventType() != RotateEvent.ROTATION_FINISHED) && (paramGestureEvent.getEventType() != ScrollEvent.SCROLL_FINISHED) && (!paramGestureEvent.isInertia()))
/*      */     {
/* 1628 */       paramTouchGesture.sceneCoords = new Point2D(paramGestureEvent.getSceneX(), paramGestureEvent.getSceneY());
/* 1629 */       paramTouchGesture.screenCoords = new Point2D(paramGestureEvent.getScreenX(), paramGestureEvent.getScreenY());
/*      */     }
/*      */ 
/* 1632 */     if (localEventTarget != null) {
/* 1633 */       Event.fireEvent(localEventTarget, paramGestureEvent);
/*      */     }
/*      */ 
/* 1636 */     if ((paramGestureEvent.getEventType() == ZoomEvent.ZOOM_FINISHED) || (paramGestureEvent.getEventType() == RotateEvent.ROTATION_FINISHED) || (paramGestureEvent.getEventType() == ScrollEvent.SCROLL_FINISHED))
/*      */     {
/* 1639 */       paramTouchGesture.finished = true;
/*      */     }
/*      */ 
/* 1642 */     inMousePick = false;
/*      */   }
/*      */ 
/*      */   private void processTouchEvent(TouchEvent paramTouchEvent, TouchPoint[] paramArrayOfTouchPoint) {
/* 1646 */     inMousePick = true;
/*      */     Object localObject3;
/* 1648 */     for (Object localObject2 : paramArrayOfTouchPoint) {
/* 1649 */       localObject3 = (EventTarget)this.touchTargets.get(Integer.valueOf(localObject2.getId()));
/* 1650 */       if (localObject3 == null)
/* 1651 */         localObject3 = pick(localObject2.getX(), localObject2.getY());
/*      */       else {
/* 1653 */         localObject2.grab((EventTarget)localObject3);
/*      */       }
/*      */ 
/* 1656 */       if (localObject2.getState() == TouchPoint.State.PRESSED) {
/* 1657 */         localObject2.grab((EventTarget)localObject3);
/* 1658 */         this.touchTargets.put(Integer.valueOf(localObject2.getId()), localObject3);
/* 1659 */       } else if (localObject2.getState() == TouchPoint.State.RELEASED) {
/* 1660 */         this.touchTargets.remove(Integer.valueOf(localObject2.getId()));
/*      */       }
/*      */ 
/* 1663 */       localObject2.impl_setTarget((EventTarget)localObject3);
/*      */     }
/*      */ 
/* 1666 */     this.touchEventSetId += 1;
/*      */ 
/* 1668 */     ??? = Arrays.asList(paramArrayOfTouchPoint);
/*      */     Object localObject4;
/* 1671 */     for (localObject3 : paramArrayOfTouchPoint) {
/* 1672 */       if (((TouchPoint)localObject3).getTarget() != null) {
/* 1673 */         localObject4 = null;
/* 1674 */         switch (54.$SwitchMap$javafx$scene$input$TouchPoint$State[localObject3.getState().ordinal()]) {
/*      */         case 1:
/* 1676 */           localObject4 = TouchEvent.TOUCH_MOVED;
/* 1677 */           break;
/*      */         case 2:
/* 1679 */           localObject4 = TouchEvent.TOUCH_PRESSED;
/* 1680 */           break;
/*      */         case 3:
/* 1682 */           localObject4 = TouchEvent.TOUCH_RELEASED;
/* 1683 */           break;
/*      */         case 4:
/* 1685 */           localObject4 = TouchEvent.TOUCH_STATIONARY;
/*      */         }
/*      */ 
/* 1689 */         for (Object localObject6 : paramArrayOfTouchPoint) {
/* 1690 */           localObject6.impl_reset();
/*      */         }
/*      */ 
/* 1693 */         ??? = TouchEvent.impl_touchEvent((EventType)localObject4, (TouchPoint)localObject3, (List)???, this.touchEventSetId, paramTouchEvent.isShiftDown(), paramTouchEvent.isControlDown(), paramTouchEvent.isAltDown(), paramTouchEvent.isMetaDown());
/*      */ 
/* 1697 */         Event.fireEvent(((TouchPoint)localObject3).getTarget(), (Event)???);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1702 */     for (localObject3 : paramArrayOfTouchPoint) {
/* 1703 */       localObject4 = ((TouchPoint)localObject3).getGrabbed();
/* 1704 */       if (localObject4 != null) {
/* 1705 */         this.touchTargets.put(Integer.valueOf(((TouchPoint)localObject3).getId()), localObject4);
/*      */       }
/*      */ 
/* 1708 */       if ((localObject4 == null) || (((TouchPoint)localObject3).getState() == TouchPoint.State.RELEASED)) {
/* 1709 */         this.touchTargets.remove(Integer.valueOf(((TouchPoint)localObject3).getId()));
/*      */       }
/*      */     }
/*      */ 
/* 1713 */     inMousePick = false;
/*      */   }
/*      */ 
/*      */   Node test_pick(double paramDouble1, double paramDouble2)
/*      */   {
/* 1720 */     inMousePick = true;
/* 1721 */     Node localNode = this.mouseHandler.pickNode(paramDouble1, paramDouble2);
/* 1722 */     inMousePick = false;
/* 1723 */     return localNode;
/*      */   }
/*      */ 
/*      */   private EventTarget pick(double paramDouble1, double paramDouble2) {
/* 1727 */     pick(this.tmpTargetWrapper, paramDouble1, paramDouble2);
/* 1728 */     return this.tmpTargetWrapper.getEventTarget();
/*      */   }
/*      */ 
/*      */   private void pick(TargetWrapper paramTargetWrapper, double paramDouble1, double paramDouble2) {
/* 1732 */     Node localNode = null;
/*      */     Object localObject;
/* 1734 */     if ((this.pickingCamera instanceof PerspectiveCamera)) {
/* 1735 */       localObject = new PickRay();
/* 1736 */       this.impl_peer.computePickRay((float)paramDouble1, (float)paramDouble2, (PickRay)localObject);
/*      */ 
/* 1738 */       localNode = this.mouseHandler.pickNode((PickRay)localObject);
/*      */     } else {
/* 1740 */       localNode = this.mouseHandler.pickNode(paramDouble1, paramDouble2);
/*      */     }
/*      */ 
/* 1743 */     if (localNode != null) {
/* 1744 */       paramTargetWrapper.setNode(localNode);
/* 1745 */     } else if ((paramDouble1 >= 0.0D) && (paramDouble2 >= 0.0D) && (paramDouble1 <= getWidth()) && (paramDouble2 <= getHeight()))
/*      */     {
/* 1750 */       localObject = getWindow();
/* 1751 */       if (((localObject instanceof Stage)) && (((Stage)localObject).getStyle() == StageStyle.TRANSPARENT) && (getFill() == null))
/*      */       {
/* 1754 */         paramTargetWrapper.clear();
/*      */       }
/*      */ 
/* 1757 */       paramTargetWrapper.setScene(this);
/*      */     } else {
/* 1759 */       paramTargetWrapper.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   private KeyHandler getKeyHandler()
/*      */   {
/* 1777 */     if (this.keyHandler == null) {
/* 1778 */       this.keyHandler = new KeyHandler();
/*      */     }
/* 1780 */     return this.keyHandler;
/*      */   }
/*      */ 
/*      */   final void setFocusDirty(boolean paramBoolean)
/*      */   {
/* 1790 */     if (!this.focusDirty) {
/* 1791 */       Toolkit.getToolkit().requestNextPulse();
/*      */     }
/* 1793 */     this.focusDirty = paramBoolean;
/*      */   }
/*      */ 
/*      */   final boolean isFocusDirty() {
/* 1797 */     return this.focusDirty;
/*      */   }
/*      */ 
/*      */   private TraversalEngine lookupTraversalEngine(Node paramNode)
/*      */   {
/* 1814 */     Parent localParent = paramNode.getParent();
/*      */ 
/* 1816 */     while (localParent != null) {
/* 1817 */       if (localParent.getImpl_traversalEngine() != null) {
/* 1818 */         return localParent.getImpl_traversalEngine();
/*      */       }
/* 1820 */       localParent = localParent.getParent();
/*      */     }
/*      */ 
/* 1828 */     return getRoot().getImpl_traversalEngine();
/*      */   }
/*      */ 
/*      */   void registerTraversable(Node paramNode)
/*      */   {
/* 1836 */     TraversalEngine localTraversalEngine = lookupTraversalEngine(paramNode);
/* 1837 */     if (localTraversalEngine != null) {
/* 1838 */       if (this.traversalRegistry == null) {
/* 1839 */         this.traversalRegistry = new HashMap();
/*      */       }
/* 1841 */       this.traversalRegistry.put(paramNode, localTraversalEngine);
/* 1842 */       localTraversalEngine.reg(paramNode);
/*      */     }
/*      */   }
/*      */ 
/*      */   void unregisterTraversable(Node paramNode)
/*      */   {
/* 1850 */     TraversalEngine localTraversalEngine = (TraversalEngine)this.traversalRegistry.remove(paramNode);
/* 1851 */     if (localTraversalEngine != null)
/* 1852 */       localTraversalEngine.unreg(paramNode);
/*      */   }
/*      */ 
/*      */   void traverse(Node paramNode, Direction paramDirection)
/*      */   {
/* 1864 */     if (this.traversalRegistry != null) {
/* 1865 */       TraversalEngine localTraversalEngine = (TraversalEngine)this.traversalRegistry.get(paramNode);
/* 1866 */       if (localTraversalEngine == null) {
/* 1867 */         localTraversalEngine = lookupTraversalEngine(paramNode);
/*      */       }
/* 1869 */       localTraversalEngine.trav(paramNode, paramDirection);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void focusInitial()
/*      */   {
/* 1879 */     getRoot().getImpl_traversalEngine().getTopLeftFocusableNode();
/*      */   }
/*      */ 
/*      */   private void focusIneligible(Node paramNode)
/*      */   {
/* 1889 */     traverse(paramNode, Direction.NEXT);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_processKeyEvent(KeyEvent paramKeyEvent)
/*      */   {
/* 1898 */     if ((this.dndGesture != null) && 
/* 1899 */       (!this.dndGesture.processKey(paramKeyEvent))) {
/* 1900 */       this.dndGesture = null;
/*      */     }
/*      */ 
/* 1904 */     getKeyHandler().process(paramKeyEvent);
/*      */ 
/* 1907 */     if ((!paramKeyEvent.isConsumed()) && (paramKeyEvent.getCode() == KeyCode.DIGIT8) && (paramKeyEvent.getEventType() == KeyEvent.KEY_PRESSED) && (paramKeyEvent.isControlDown()) && (paramKeyEvent.isShiftDown()))
/*      */       try
/*      */       {
/* 1910 */         Class localClass = Class.forName("com.javafx.experiments.scenicview.ScenicView");
/* 1911 */         Class[] arrayOfClass = new Class[1];
/* 1912 */         arrayOfClass[0] = Scene.class;
/* 1913 */         Method localMethod = localClass.getDeclaredMethod("show", arrayOfClass);
/* 1914 */         localMethod.invoke(null, new Object[] { this });
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */   }
/*      */ 
/*      */   void requestFocus(Node paramNode)
/*      */   {
/* 1925 */     getKeyHandler().requestFocus(paramNode);
/*      */   }
/*      */ 
/*      */   public final Node getFocusOwner()
/*      */   {
/* 1968 */     return (Node)this.focusOwner.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<Node> focusOwnerProperty() {
/* 1972 */     return this.focusOwner.getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   void focusCleanup()
/*      */   {
/* 1977 */     this.scenePulseListener.focusCleanup();
/*      */   }
/*      */ 
/*      */   private void processInputMethodEvent(InputMethodEvent paramInputMethodEvent) {
/* 1981 */     Node localNode = getFocusOwner();
/* 1982 */     if (localNode != null)
/* 1983 */       localNode.fireEvent(paramInputMethodEvent);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_enableInputMethodEvents(boolean paramBoolean)
/*      */   {
/* 1993 */     if (this.impl_peer != null)
/* 1994 */       this.impl_peer.enableInputMethodEvents(paramBoolean);
/*      */   }
/*      */ 
/*      */   boolean isQuiescent()
/*      */   {
/* 2007 */     return (!isFocusDirty()) && (getRoot().cssFlag == CSSFlags.CLEAN) && (this.dirtyLayoutRoots.isEmpty());
/*      */   }
/*      */ 
/*      */   private void markDirty(DirtyBits paramDirtyBits)
/*      */   {
/* 2024 */     setDirty(paramDirtyBits);
/* 2025 */     if (this.impl_peer != null)
/* 2026 */       Toolkit.getToolkit().requestNextPulse();
/*      */   }
/*      */ 
/*      */   private void setDirty(DirtyBits paramDirtyBits)
/*      */   {
/* 2034 */     this.dirtyBits |= paramDirtyBits.getMask();
/*      */   }
/*      */ 
/*      */   private boolean isDirty(DirtyBits paramDirtyBits)
/*      */   {
/* 2041 */     return (this.dirtyBits & paramDirtyBits.getMask()) != 0;
/*      */   }
/*      */ 
/*      */   private boolean isDirtyEmpty()
/*      */   {
/* 2048 */     return this.dirtyBits == 0;
/*      */   }
/*      */ 
/*      */   private void clearDirty()
/*      */   {
/* 2055 */     this.dirtyBits = 0;
/*      */   }
/*      */ 
/*      */   public final void setEventDispatcher(EventDispatcher paramEventDispatcher)
/*      */   {
/* 3544 */     eventDispatcherProperty().set(paramEventDispatcher);
/*      */   }
/*      */ 
/*      */   public final EventDispatcher getEventDispatcher() {
/* 3548 */     return (EventDispatcher)eventDispatcherProperty().get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventDispatcher> eventDispatcherProperty()
/*      */   {
/* 3553 */     initializeInternalEventDispatcher();
/* 3554 */     return this.eventDispatcher;
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void addEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/* 3622 */     getInternalEventDispatcher().getEventHandlerManager().addEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void removeEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/* 3641 */     getInternalEventDispatcher().getEventHandlerManager().removeEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void addEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/* 3660 */     getInternalEventDispatcher().getEventHandlerManager().addEventFilter(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void removeEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/* 3679 */     getInternalEventDispatcher().getEventHandlerManager().removeEventFilter(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   protected final <T extends Event> void setEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/* 3697 */     getInternalEventDispatcher().getEventHandlerManager().setEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   private SceneEventDispatcher getInternalEventDispatcher()
/*      */   {
/* 3702 */     initializeInternalEventDispatcher();
/* 3703 */     return this.internalEventDispatcher;
/*      */   }
/*      */ 
/*      */   private void initializeInternalEventDispatcher() {
/* 3707 */     if (this.internalEventDispatcher == null) {
/* 3708 */       this.internalEventDispatcher = createInternalEventDispatcher();
/* 3709 */       this.eventDispatcher = new SimpleObjectProperty(this, "eventDispatcher", this.internalEventDispatcher);
/*      */     }
/*      */   }
/*      */ 
/*      */   private SceneEventDispatcher createInternalEventDispatcher()
/*      */   {
/* 3717 */     return new SceneEventDispatcher(this);
/*      */   }
/*      */ 
/*      */   public void addMnemonic(Mnemonic paramMnemonic)
/*      */   {
/* 3726 */     getInternalEventDispatcher().getKeyboardShortcutsHandler().addMnemonic(paramMnemonic);
/*      */   }
/*      */ 
/*      */   public void removeMnemonic(Mnemonic paramMnemonic)
/*      */   {
/* 3737 */     getInternalEventDispatcher().getKeyboardShortcutsHandler().removeMnemonic(paramMnemonic);
/*      */   }
/*      */ 
/*      */   public ObservableMap<KeyCombination, ObservableList<Mnemonic>> getMnemonics()
/*      */   {
/* 3747 */     return getInternalEventDispatcher().getKeyboardShortcutsHandler().getMnemonics();
/*      */   }
/*      */ 
/*      */   public ObservableMap<KeyCombination, Runnable> getAccelerators()
/*      */   {
/* 3757 */     return getInternalEventDispatcher().getKeyboardShortcutsHandler().getAccelerators();
/*      */   }
/*      */ 
/*      */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*      */   {
/* 3772 */     if (this.eventDispatcher != null) {
/* 3773 */       paramEventDispatchChain = paramEventDispatchChain.prepend((EventDispatcher)this.eventDispatcher.get());
/*      */     }
/*      */ 
/* 3776 */     if (getWindow() != null) {
/* 3777 */       paramEventDispatchChain = getWindow().buildEventDispatchChain(paramEventDispatchChain);
/*      */     }
/*      */ 
/* 3780 */     return paramEventDispatchChain;
/*      */   }
/*      */ 
/*      */   public final void setOnContextMenuRequested(EventHandler<? super ContextMenuEvent> paramEventHandler)
/*      */   {
/* 3797 */     onContextMenuRequestedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super ContextMenuEvent> getOnContextMenuRequested() {
/* 3801 */     return this.onContextMenuRequested == null ? null : (EventHandler)this.onContextMenuRequested.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super ContextMenuEvent>> onContextMenuRequestedProperty() {
/* 3805 */     if (this.onContextMenuRequested == null) {
/* 3806 */       this.onContextMenuRequested = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 3810 */           Scene.this.setEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 3815 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 3820 */           return "onContextMenuRequested";
/*      */         }
/*      */       };
/*      */     }
/* 3824 */     return this.onContextMenuRequested;
/*      */   }
/*      */ 
/*      */   public final void setOnMouseClicked(EventHandler<? super MouseEvent> paramEventHandler)
/*      */   {
/* 3840 */     onMouseClickedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseEvent> getOnMouseClicked() {
/* 3844 */     return this.onMouseClicked == null ? null : (EventHandler)this.onMouseClicked.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseClickedProperty() {
/* 3848 */     if (this.onMouseClicked == null) {
/* 3849 */       this.onMouseClicked = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 3853 */           Scene.this.setEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 3858 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 3863 */           return "onMouseClicked";
/*      */         }
/*      */       };
/*      */     }
/* 3867 */     return this.onMouseClicked;
/*      */   }
/*      */ 
/*      */   public final void setOnMouseDragged(EventHandler<? super MouseEvent> paramEventHandler)
/*      */   {
/* 3877 */     onMouseDraggedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseEvent> getOnMouseDragged() {
/* 3881 */     return this.onMouseDragged == null ? null : (EventHandler)this.onMouseDragged.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseDraggedProperty() {
/* 3885 */     if (this.onMouseDragged == null) {
/* 3886 */       this.onMouseDragged = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 3890 */           Scene.this.setEventHandler(MouseEvent.MOUSE_DRAGGED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 3895 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 3900 */           return "onMouseDragged";
/*      */         }
/*      */       };
/*      */     }
/* 3904 */     return this.onMouseDragged;
/*      */   }
/*      */ 
/*      */   public final void setOnMouseEntered(EventHandler<? super MouseEvent> paramEventHandler)
/*      */   {
/* 3913 */     onMouseEnteredProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseEvent> getOnMouseEntered() {
/* 3917 */     return this.onMouseEntered == null ? null : (EventHandler)this.onMouseEntered.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseEnteredProperty() {
/* 3921 */     if (this.onMouseEntered == null) {
/* 3922 */       this.onMouseEntered = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 3926 */           Scene.this.setEventHandler(MouseEvent.MOUSE_ENTERED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 3931 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 3936 */           return "onMouseEntered";
/*      */         }
/*      */       };
/*      */     }
/* 3940 */     return this.onMouseEntered;
/*      */   }
/*      */ 
/*      */   public final void setOnMouseExited(EventHandler<? super MouseEvent> paramEventHandler)
/*      */   {
/* 3949 */     onMouseExitedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseEvent> getOnMouseExited() {
/* 3953 */     return this.onMouseExited == null ? null : (EventHandler)this.onMouseExited.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseExitedProperty() {
/* 3957 */     if (this.onMouseExited == null) {
/* 3958 */       this.onMouseExited = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 3962 */           Scene.this.setEventHandler(MouseEvent.MOUSE_EXITED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 3967 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 3972 */           return "onMouseExited";
/*      */         }
/*      */       };
/*      */     }
/* 3976 */     return this.onMouseExited;
/*      */   }
/*      */ 
/*      */   public final void setOnMouseMoved(EventHandler<? super MouseEvent> paramEventHandler)
/*      */   {
/* 3986 */     onMouseMovedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseEvent> getOnMouseMoved() {
/* 3990 */     return this.onMouseMoved == null ? null : (EventHandler)this.onMouseMoved.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseMovedProperty() {
/* 3994 */     if (this.onMouseMoved == null) {
/* 3995 */       this.onMouseMoved = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 3999 */           Scene.this.setEventHandler(MouseEvent.MOUSE_MOVED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4004 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4009 */           return "onMouseMoved";
/*      */         }
/*      */       };
/*      */     }
/* 4013 */     return this.onMouseMoved;
/*      */   }
/*      */ 
/*      */   public final void setOnMousePressed(EventHandler<? super MouseEvent> paramEventHandler)
/*      */   {
/* 4023 */     onMousePressedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseEvent> getOnMousePressed() {
/* 4027 */     return this.onMousePressed == null ? null : (EventHandler)this.onMousePressed.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseEvent>> onMousePressedProperty() {
/* 4031 */     if (this.onMousePressed == null) {
/* 4032 */       this.onMousePressed = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4036 */           Scene.this.setEventHandler(MouseEvent.MOUSE_PRESSED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4041 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4046 */           return "onMousePressed";
/*      */         }
/*      */       };
/*      */     }
/* 4050 */     return this.onMousePressed;
/*      */   }
/*      */ 
/*      */   public final void setOnMouseReleased(EventHandler<? super MouseEvent> paramEventHandler)
/*      */   {
/* 4060 */     onMouseReleasedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseEvent> getOnMouseReleased() {
/* 4064 */     return this.onMouseReleased == null ? null : (EventHandler)this.onMouseReleased.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseReleasedProperty() {
/* 4068 */     if (this.onMouseReleased == null) {
/* 4069 */       this.onMouseReleased = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4073 */           Scene.this.setEventHandler(MouseEvent.MOUSE_RELEASED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4078 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4083 */           return "onMouseReleased";
/*      */         }
/*      */       };
/*      */     }
/* 4087 */     return this.onMouseReleased;
/*      */   }
/*      */ 
/*      */   public final void setOnDragDetected(EventHandler<? super MouseEvent> paramEventHandler)
/*      */   {
/* 4097 */     onDragDetectedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseEvent> getOnDragDetected() {
/* 4101 */     return this.onDragDetected == null ? null : (EventHandler)this.onDragDetected.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseEvent>> onDragDetectedProperty() {
/* 4105 */     if (this.onDragDetected == null) {
/* 4106 */       this.onDragDetected = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4110 */           Scene.this.setEventHandler(MouseEvent.DRAG_DETECTED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4115 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4120 */           return "onDragDetected";
/*      */         }
/*      */       };
/*      */     }
/* 4124 */     return this.onDragDetected;
/*      */   }
/*      */ 
/*      */   public final void setOnMouseDragOver(EventHandler<? super MouseDragEvent> paramEventHandler)
/*      */   {
/* 4134 */     onMouseDragOverProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseDragEvent> getOnMouseDragOver() {
/* 4138 */     return this.onMouseDragOver == null ? null : (EventHandler)this.onMouseDragOver.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragOverProperty() {
/* 4142 */     if (this.onMouseDragOver == null) {
/* 4143 */       this.onMouseDragOver = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4147 */           Scene.this.setEventHandler(MouseDragEvent.MOUSE_DRAG_OVER, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4152 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4157 */           return "onMouseDragOver";
/*      */         }
/*      */       };
/*      */     }
/* 4161 */     return this.onMouseDragOver;
/*      */   }
/*      */ 
/*      */   public final void setOnMouseDragReleased(EventHandler<? super MouseDragEvent> paramEventHandler)
/*      */   {
/* 4171 */     onMouseDragReleasedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseDragEvent> getOnMouseDragReleased() {
/* 4175 */     return this.onMouseDragReleased == null ? null : (EventHandler)this.onMouseDragReleased.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragReleasedProperty() {
/* 4179 */     if (this.onMouseDragReleased == null) {
/* 4180 */       this.onMouseDragReleased = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4184 */           Scene.this.setEventHandler(MouseDragEvent.MOUSE_DRAG_RELEASED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4189 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4194 */           return "onMouseDragReleased";
/*      */         }
/*      */       };
/*      */     }
/* 4198 */     return this.onMouseDragReleased;
/*      */   }
/*      */ 
/*      */   public final void setOnMouseDragEntered(EventHandler<? super MouseDragEvent> paramEventHandler)
/*      */   {
/* 4208 */     onMouseDragEnteredProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseDragEvent> getOnMouseDragEntered() {
/* 4212 */     return this.onMouseDragEntered == null ? null : (EventHandler)this.onMouseDragEntered.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragEnteredProperty() {
/* 4216 */     if (this.onMouseDragEntered == null) {
/* 4217 */       this.onMouseDragEntered = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4221 */           Scene.this.setEventHandler(MouseDragEvent.MOUSE_DRAG_ENTERED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4226 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4231 */           return "onMouseDragEntered";
/*      */         }
/*      */       };
/*      */     }
/* 4235 */     return this.onMouseDragEntered;
/*      */   }
/*      */ 
/*      */   public final void setOnMouseDragExited(EventHandler<? super MouseDragEvent> paramEventHandler)
/*      */   {
/* 4245 */     onMouseDragExitedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super MouseDragEvent> getOnMouseDragExited() {
/* 4249 */     return this.onMouseDragExited == null ? null : (EventHandler)this.onMouseDragExited.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragExitedProperty() {
/* 4253 */     if (this.onMouseDragExited == null) {
/* 4254 */       this.onMouseDragExited = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4258 */           Scene.this.setEventHandler(MouseDragEvent.MOUSE_DRAG_EXITED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4263 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4268 */           return "onMouseDragExited";
/*      */         }
/*      */       };
/*      */     }
/* 4272 */     return this.onMouseDragExited;
/*      */   }
/*      */ 
/*      */   public final void setOnScrollStarted(EventHandler<? super ScrollEvent> paramEventHandler)
/*      */   {
/* 4289 */     onScrollStartedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super ScrollEvent> getOnScrollStarted() {
/* 4293 */     return this.onScrollStarted == null ? null : (EventHandler)this.onScrollStarted.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super ScrollEvent>> onScrollStartedProperty() {
/* 4297 */     if (this.onScrollStarted == null) {
/* 4298 */       this.onScrollStarted = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4302 */           Scene.this.setEventHandler(ScrollEvent.SCROLL_STARTED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4307 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4312 */           return "onScrollStarted";
/*      */         }
/*      */       };
/*      */     }
/* 4316 */     return this.onScrollStarted;
/*      */   }
/*      */ 
/*      */   public final void setOnScroll(EventHandler<? super ScrollEvent> paramEventHandler)
/*      */   {
/* 4325 */     onScrollProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super ScrollEvent> getOnScroll() {
/* 4329 */     return this.onScroll == null ? null : (EventHandler)this.onScroll.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super ScrollEvent>> onScrollProperty() {
/* 4333 */     if (this.onScroll == null) {
/* 4334 */       this.onScroll = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4338 */           Scene.this.setEventHandler(ScrollEvent.SCROLL, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4343 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4348 */           return "onScroll";
/*      */         }
/*      */       };
/*      */     }
/* 4352 */     return this.onScroll;
/*      */   }
/*      */ 
/*      */   public final void setOnScrollFinished(EventHandler<? super ScrollEvent> paramEventHandler)
/*      */   {
/* 4362 */     onScrollFinishedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super ScrollEvent> getOnScrollFinished() {
/* 4366 */     return this.onScrollFinished == null ? null : (EventHandler)this.onScrollFinished.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super ScrollEvent>> onScrollFinishedProperty() {
/* 4370 */     if (this.onScrollFinished == null) {
/* 4371 */       this.onScrollFinished = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4375 */           Scene.this.setEventHandler(ScrollEvent.SCROLL_FINISHED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4380 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4385 */           return "onScrollFinished";
/*      */         }
/*      */       };
/*      */     }
/* 4389 */     return this.onScrollFinished;
/*      */   }
/*      */ 
/*      */   public final void setOnRotationStarted(EventHandler<? super RotateEvent> paramEventHandler)
/*      */   {
/* 4399 */     onRotationStartedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super RotateEvent> getOnRotationStarted() {
/* 4403 */     return this.onRotationStarted == null ? null : (EventHandler)this.onRotationStarted.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super RotateEvent>> onRotationStartedProperty() {
/* 4407 */     if (this.onRotationStarted == null) {
/* 4408 */       this.onRotationStarted = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4412 */           Scene.this.setEventHandler(RotateEvent.ROTATION_STARTED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4417 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4422 */           return "onRotationStarted";
/*      */         }
/*      */       };
/*      */     }
/* 4426 */     return this.onRotationStarted;
/*      */   }
/*      */ 
/*      */   public final void setOnRotate(EventHandler<? super RotateEvent> paramEventHandler)
/*      */   {
/* 4436 */     onRotateProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super RotateEvent> getOnRotate() {
/* 4440 */     return this.onRotate == null ? null : (EventHandler)this.onRotate.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super RotateEvent>> onRotateProperty() {
/* 4444 */     if (this.onRotate == null) {
/* 4445 */       this.onRotate = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4449 */           Scene.this.setEventHandler(RotateEvent.ROTATE, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4454 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4459 */           return "onRotate";
/*      */         }
/*      */       };
/*      */     }
/* 4463 */     return this.onRotate;
/*      */   }
/*      */ 
/*      */   public final void setOnRotationFinished(EventHandler<? super RotateEvent> paramEventHandler)
/*      */   {
/* 4473 */     onRotationFinishedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super RotateEvent> getOnRotationFinished() {
/* 4477 */     return this.onRotationFinished == null ? null : (EventHandler)this.onRotationFinished.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super RotateEvent>> onRotationFinishedProperty() {
/* 4481 */     if (this.onRotationFinished == null) {
/* 4482 */       this.onRotationFinished = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4486 */           Scene.this.setEventHandler(RotateEvent.ROTATION_FINISHED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4491 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4496 */           return "onRotationFinished";
/*      */         }
/*      */       };
/*      */     }
/* 4500 */     return this.onRotationFinished;
/*      */   }
/*      */ 
/*      */   public final void setOnZoomStarted(EventHandler<? super ZoomEvent> paramEventHandler)
/*      */   {
/* 4510 */     onZoomStartedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super ZoomEvent> getOnZoomStarted() {
/* 4514 */     return this.onZoomStarted == null ? null : (EventHandler)this.onZoomStarted.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super ZoomEvent>> onZoomStartedProperty() {
/* 4518 */     if (this.onZoomStarted == null) {
/* 4519 */       this.onZoomStarted = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4523 */           Scene.this.setEventHandler(ZoomEvent.ZOOM_STARTED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4528 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4533 */           return "onZoomStarted";
/*      */         }
/*      */       };
/*      */     }
/* 4537 */     return this.onZoomStarted;
/*      */   }
/*      */ 
/*      */   public final void setOnZoom(EventHandler<? super ZoomEvent> paramEventHandler)
/*      */   {
/* 4547 */     onZoomProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super ZoomEvent> getOnZoom() {
/* 4551 */     return this.onZoom == null ? null : (EventHandler)this.onZoom.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super ZoomEvent>> onZoomProperty() {
/* 4555 */     if (this.onZoom == null) {
/* 4556 */       this.onZoom = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4560 */           Scene.this.setEventHandler(ZoomEvent.ZOOM, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4565 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4570 */           return "onZoom";
/*      */         }
/*      */       };
/*      */     }
/* 4574 */     return this.onZoom;
/*      */   }
/*      */ 
/*      */   public final void setOnZoomFinished(EventHandler<? super ZoomEvent> paramEventHandler)
/*      */   {
/* 4584 */     onZoomFinishedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super ZoomEvent> getOnZoomFinished() {
/* 4588 */     return this.onZoomFinished == null ? null : (EventHandler)this.onZoomFinished.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super ZoomEvent>> onZoomFinishedProperty() {
/* 4592 */     if (this.onZoomFinished == null) {
/* 4593 */       this.onZoomFinished = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4597 */           Scene.this.setEventHandler(ZoomEvent.ZOOM_FINISHED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4602 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4607 */           return "onZoomFinished";
/*      */         }
/*      */       };
/*      */     }
/* 4611 */     return this.onZoomFinished;
/*      */   }
/*      */ 
/*      */   public final void setOnSwipeUp(EventHandler<? super SwipeEvent> paramEventHandler)
/*      */   {
/* 4622 */     onSwipeUpProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super SwipeEvent> getOnSwipeUp() {
/* 4626 */     return this.onSwipeUp == null ? null : (EventHandler)this.onSwipeUp.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeUpProperty() {
/* 4630 */     if (this.onSwipeUp == null) {
/* 4631 */       this.onSwipeUp = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4635 */           Scene.this.setEventHandler(SwipeEvent.SWIPE_UP, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4640 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4645 */           return "onSwipeUp";
/*      */         }
/*      */       };
/*      */     }
/* 4649 */     return this.onSwipeUp;
/*      */   }
/*      */ 
/*      */   public final void setOnSwipeDown(EventHandler<? super SwipeEvent> paramEventHandler)
/*      */   {
/* 4660 */     onSwipeDownProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super SwipeEvent> getOnSwipeDown() {
/* 4664 */     return this.onSwipeDown == null ? null : (EventHandler)this.onSwipeDown.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeDownProperty() {
/* 4668 */     if (this.onSwipeDown == null) {
/* 4669 */       this.onSwipeDown = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4673 */           Scene.this.setEventHandler(SwipeEvent.SWIPE_DOWN, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4678 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4683 */           return "onSwipeDown";
/*      */         }
/*      */       };
/*      */     }
/* 4687 */     return this.onSwipeDown;
/*      */   }
/*      */ 
/*      */   public final void setOnSwipeLeft(EventHandler<? super SwipeEvent> paramEventHandler)
/*      */   {
/* 4698 */     onSwipeLeftProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super SwipeEvent> getOnSwipeLeft() {
/* 4702 */     return this.onSwipeLeft == null ? null : (EventHandler)this.onSwipeLeft.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeLeftProperty() {
/* 4706 */     if (this.onSwipeLeft == null) {
/* 4707 */       this.onSwipeLeft = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4711 */           Scene.this.setEventHandler(SwipeEvent.SWIPE_LEFT, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4716 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4721 */           return "onSwipeLeft";
/*      */         }
/*      */       };
/*      */     }
/* 4725 */     return this.onSwipeLeft;
/*      */   }
/*      */ 
/*      */   public final void setOnSwipeRight(EventHandler<? super SwipeEvent> paramEventHandler)
/*      */   {
/* 4736 */     onSwipeRightProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super SwipeEvent> getOnSwipeRight() {
/* 4740 */     return this.onSwipeRight == null ? null : (EventHandler)this.onSwipeRight.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeRightProperty() {
/* 4744 */     if (this.onSwipeRight == null) {
/* 4745 */       this.onSwipeRight = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4749 */           Scene.this.setEventHandler(SwipeEvent.SWIPE_RIGHT, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4754 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4759 */           return "onSwipeRight";
/*      */         }
/*      */       };
/*      */     }
/* 4763 */     return this.onSwipeRight;
/*      */   }
/*      */ 
/*      */   public final void setOnTouchPressed(EventHandler<? super TouchEvent> paramEventHandler)
/*      */   {
/* 4779 */     onTouchPressedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super TouchEvent> getOnTouchPressed() {
/* 4783 */     return this.onTouchPressed == null ? null : (EventHandler)this.onTouchPressed.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super TouchEvent>> onTouchPressedProperty() {
/* 4787 */     if (this.onTouchPressed == null) {
/* 4788 */       this.onTouchPressed = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4792 */           Scene.this.setEventHandler(TouchEvent.TOUCH_PRESSED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4797 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4802 */           return "onTouchPressed";
/*      */         }
/*      */       };
/*      */     }
/* 4806 */     return this.onTouchPressed;
/*      */   }
/*      */ 
/*      */   public final void setOnTouchMoved(EventHandler<? super TouchEvent> paramEventHandler)
/*      */   {
/* 4816 */     onTouchMovedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super TouchEvent> getOnTouchMoved() {
/* 4820 */     return this.onTouchMoved == null ? null : (EventHandler)this.onTouchMoved.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super TouchEvent>> onTouchMovedProperty() {
/* 4824 */     if (this.onTouchMoved == null) {
/* 4825 */       this.onTouchMoved = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4829 */           Scene.this.setEventHandler(TouchEvent.TOUCH_MOVED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4834 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4839 */           return "onTouchMoved";
/*      */         }
/*      */       };
/*      */     }
/* 4843 */     return this.onTouchMoved;
/*      */   }
/*      */ 
/*      */   public final void setOnTouchReleased(EventHandler<? super TouchEvent> paramEventHandler)
/*      */   {
/* 4853 */     onTouchReleasedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super TouchEvent> getOnTouchReleased() {
/* 4857 */     return this.onTouchReleased == null ? null : (EventHandler)this.onTouchReleased.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super TouchEvent>> onTouchReleasedProperty() {
/* 4861 */     if (this.onTouchReleased == null) {
/* 4862 */       this.onTouchReleased = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4866 */           Scene.this.setEventHandler(TouchEvent.TOUCH_RELEASED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4871 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4876 */           return "onTouchReleased";
/*      */         }
/*      */       };
/*      */     }
/* 4880 */     return this.onTouchReleased;
/*      */   }
/*      */ 
/*      */   public final void setOnTouchStationary(EventHandler<? super TouchEvent> paramEventHandler)
/*      */   {
/* 4891 */     onTouchStationaryProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super TouchEvent> getOnTouchStationary() {
/* 4895 */     return this.onTouchStationary == null ? null : (EventHandler)this.onTouchStationary.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super TouchEvent>> onTouchStationaryProperty() {
/* 4899 */     if (this.onTouchStationary == null) {
/* 4900 */       this.onTouchStationary = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 4904 */           Scene.this.setEventHandler(TouchEvent.TOUCH_STATIONARY, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 4909 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 4914 */           return "onTouchStationary";
/*      */         }
/*      */       };
/*      */     }
/* 4918 */     return this.onTouchStationary;
/*      */   }
/*      */ 
/*      */   public final void setOnDragEntered(EventHandler<? super DragEvent> paramEventHandler)
/*      */   {
/* 5013 */     onDragEnteredProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super DragEvent> getOnDragEntered() {
/* 5017 */     return this.onDragEntered == null ? null : (EventHandler)this.onDragEntered.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super DragEvent>> onDragEnteredProperty()
/*      */   {
/* 5025 */     if (this.onDragEntered == null) {
/* 5026 */       this.onDragEntered = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 5030 */           Scene.this.setEventHandler(DragEvent.DRAG_ENTERED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 5035 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 5040 */           return "onDragEntered";
/*      */         }
/*      */       };
/*      */     }
/* 5044 */     return this.onDragEntered;
/*      */   }
/*      */ 
/*      */   public final void setOnDragExited(EventHandler<? super DragEvent> paramEventHandler)
/*      */   {
/* 5050 */     onDragExitedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super DragEvent> getOnDragExited() {
/* 5054 */     return this.onDragExited == null ? null : (EventHandler)this.onDragExited.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super DragEvent>> onDragExitedProperty()
/*      */   {
/* 5062 */     if (this.onDragExited == null) {
/* 5063 */       this.onDragExited = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 5067 */           Scene.this.setEventHandler(DragEvent.DRAG_EXITED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 5072 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 5077 */           return "onDragExited";
/*      */         }
/*      */       };
/*      */     }
/* 5081 */     return this.onDragExited;
/*      */   }
/*      */ 
/*      */   public final void setOnDragOver(EventHandler<? super DragEvent> paramEventHandler)
/*      */   {
/* 5087 */     onDragOverProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super DragEvent> getOnDragOver() {
/* 5091 */     return this.onDragOver == null ? null : (EventHandler)this.onDragOver.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super DragEvent>> onDragOverProperty()
/*      */   {
/* 5099 */     if (this.onDragOver == null) {
/* 5100 */       this.onDragOver = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 5104 */           Scene.this.setEventHandler(DragEvent.DRAG_OVER, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 5109 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 5114 */           return "onDragOver";
/*      */         }
/*      */       };
/*      */     }
/* 5118 */     return this.onDragOver;
/*      */   }
/*      */ 
/*      */   public final void setOnDragDropped(EventHandler<? super DragEvent> paramEventHandler)
/*      */   {
/* 5155 */     onDragDroppedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super DragEvent> getOnDragDropped() {
/* 5159 */     return this.onDragDropped == null ? null : (EventHandler)this.onDragDropped.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super DragEvent>> onDragDroppedProperty()
/*      */   {
/* 5169 */     if (this.onDragDropped == null) {
/* 5170 */       this.onDragDropped = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 5174 */           Scene.this.setEventHandler(DragEvent.DRAG_DROPPED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 5179 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 5184 */           return "onDragDropped";
/*      */         }
/*      */       };
/*      */     }
/* 5188 */     return this.onDragDropped;
/*      */   }
/*      */ 
/*      */   public final void setOnDragDone(EventHandler<? super DragEvent> paramEventHandler)
/*      */   {
/* 5194 */     onDragDoneProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super DragEvent> getOnDragDone() {
/* 5198 */     return this.onDragDone == null ? null : (EventHandler)this.onDragDone.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super DragEvent>> onDragDoneProperty()
/*      */   {
/* 5213 */     if (this.onDragDone == null) {
/* 5214 */       this.onDragDone = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 5218 */           Scene.this.setEventHandler(DragEvent.DRAG_DONE, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 5223 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 5228 */           return "onDragDone";
/*      */         }
/*      */       };
/*      */     }
/* 5232 */     return this.onDragDone;
/*      */   }
/*      */ 
/*      */   public Dragboard startDragAndDrop(TransferMode[] paramArrayOfTransferMode)
/*      */   {
/* 5253 */     return startDragAndDrop(this, paramArrayOfTransferMode);
/*      */   }
/*      */ 
/*      */   public void startFullDrag()
/*      */   {
/* 5269 */     startFullDrag(this);
/*      */   }
/*      */ 
/*      */   Dragboard startDragAndDrop(EventTarget paramEventTarget, TransferMode[] paramArrayOfTransferMode)
/*      */   {
/* 5276 */     if (this.dndGesture.dragDetected != DragDetectedState.PROCESSING) {
/* 5277 */       throw new IllegalStateException("Cannot start drag and drop outside of DRAG_DETECTED event handler");
/*      */     }
/*      */ 
/* 5281 */     if (this.dndGesture != null) {
/* 5282 */       EnumSet localEnumSet = EnumSet.noneOf(TransferMode.class);
/* 5283 */       for (TransferMode localTransferMode : paramArrayOfTransferMode) {
/* 5284 */         localEnumSet.add(localTransferMode);
/*      */       }
/* 5286 */       return this.dndGesture.startDrag(paramEventTarget, localEnumSet);
/*      */     }
/*      */ 
/* 5289 */     throw new IllegalStateException("Cannot start drag and drop when mouse button is not pressed");
/*      */   }
/*      */ 
/*      */   void startFullDrag(EventTarget paramEventTarget)
/*      */   {
/* 5295 */     if (this.dndGesture.dragDetected != DragDetectedState.PROCESSING) {
/* 5296 */       throw new IllegalStateException("Cannot start full drag outside of DRAG_DETECTED event handler");
/*      */     }
/*      */ 
/* 5300 */     if (this.dndGesture != null) {
/* 5301 */       this.dndGesture.startFullPDR(paramEventTarget);
/* 5302 */       return;
/*      */     }
/*      */ 
/* 5305 */     throw new IllegalStateException("Cannot start full drag when mouse button is not pressed");
/*      */   }
/*      */ 
/*      */   public final void setOnKeyPressed(EventHandler<? super KeyEvent> paramEventHandler)
/*      */   {
/* 5324 */     onKeyPressedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super KeyEvent> getOnKeyPressed() {
/* 5328 */     return this.onKeyPressed == null ? null : (EventHandler)this.onKeyPressed.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super KeyEvent>> onKeyPressedProperty() {
/* 5332 */     if (this.onKeyPressed == null) {
/* 5333 */       this.onKeyPressed = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 5337 */           Scene.this.setEventHandler(KeyEvent.KEY_PRESSED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 5342 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 5347 */           return "onKeyPressed";
/*      */         }
/*      */       };
/*      */     }
/* 5351 */     return this.onKeyPressed;
/*      */   }
/*      */ 
/*      */   public final void setOnKeyReleased(EventHandler<? super KeyEvent> paramEventHandler)
/*      */   {
/* 5363 */     onKeyReleasedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super KeyEvent> getOnKeyReleased() {
/* 5367 */     return this.onKeyReleased == null ? null : (EventHandler)this.onKeyReleased.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super KeyEvent>> onKeyReleasedProperty() {
/* 5371 */     if (this.onKeyReleased == null) {
/* 5372 */       this.onKeyReleased = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 5376 */           Scene.this.setEventHandler(KeyEvent.KEY_RELEASED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 5381 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 5386 */           return "onKeyReleased";
/*      */         }
/*      */       };
/*      */     }
/* 5390 */     return this.onKeyReleased;
/*      */   }
/*      */ 
/*      */   public final void setOnKeyTyped(EventHandler<? super KeyEvent> paramEventHandler)
/*      */   {
/* 5403 */     onKeyTypedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super KeyEvent> getOnKeyTyped()
/*      */   {
/* 5409 */     return this.onKeyTyped == null ? null : (EventHandler)this.onKeyTyped.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super KeyEvent>> onKeyTypedProperty()
/*      */   {
/* 5414 */     if (this.onKeyTyped == null) {
/* 5415 */       this.onKeyTyped = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 5419 */           Scene.this.setEventHandler(KeyEvent.KEY_TYPED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 5424 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 5429 */           return "onKeyTyped";
/*      */         }
/*      */       };
/*      */     }
/* 5433 */     return this.onKeyTyped;
/*      */   }
/*      */ 
/*      */   public final void setOnInputMethodTextChanged(EventHandler<? super InputMethodEvent> paramEventHandler)
/*      */   {
/* 5456 */     onInputMethodTextChangedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<? super InputMethodEvent> getOnInputMethodTextChanged() {
/* 5460 */     return this.onInputMethodTextChanged == null ? null : (EventHandler)this.onInputMethodTextChanged.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<? super InputMethodEvent>> onInputMethodTextChangedProperty() {
/* 5464 */     if (this.onInputMethodTextChanged == null) {
/* 5465 */       this.onInputMethodTextChanged = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 5469 */           Scene.this.setEventHandler(InputMethodEvent.INPUT_METHOD_TEXT_CHANGED, (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 5474 */           return Scene.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 5479 */           return "onInputMethodTextChanged";
/*      */         }
/*      */       };
/*      */     }
/* 5483 */     return this.onInputMethodTextChanged;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  297 */     PerformanceTracker.setSceneAccessor(new PerformanceTracker.SceneAccessor() {
/*      */       public void setPerfTracker(Scene paramAnonymousScene, PerformanceTracker paramAnonymousPerformanceTracker) {
/*  299 */         synchronized (Scene.trackerMonitor) {
/*  300 */           paramAnonymousScene.tracker = paramAnonymousPerformanceTracker;
/*      */         }
/*      */       }
/*      */ 
/*  304 */       public PerformanceTracker getPerfTracker(Scene paramAnonymousScene) { synchronized (Scene.trackerMonitor) {
/*  305 */           return paramAnonymousScene.tracker;
/*      */         }
/*      */       }
/*      */     });
/*  309 */     FXRobotHelper.setSceneAccessor(new FXRobotHelper.FXRobotSceneAccessor() {
/*      */       public void processKeyEvent(Scene paramAnonymousScene, KeyEvent paramAnonymousKeyEvent) {
/*  311 */         paramAnonymousScene.impl_processKeyEvent(paramAnonymousKeyEvent);
/*      */       }
/*      */       public void processMouseEvent(Scene paramAnonymousScene, MouseEvent paramAnonymousMouseEvent) {
/*  314 */         paramAnonymousScene.impl_processMouseEvent(paramAnonymousMouseEvent);
/*      */       }
/*      */       public void processScrollEvent(Scene paramAnonymousScene, ScrollEvent paramAnonymousScrollEvent) {
/*  317 */         paramAnonymousScene.processGestureEvent(paramAnonymousScrollEvent, paramAnonymousScene.scrollGesture);
/*      */       }
/*      */       public ObservableList<Node> getChildren(Parent paramAnonymousParent) {
/*  320 */         return paramAnonymousParent.getChildren();
/*      */       }
/*      */       public Object renderToImage(Scene paramAnonymousScene, Object paramAnonymousObject) {
/*  323 */         return paramAnonymousScene.renderToImage(paramAnonymousObject);
/*      */       }
/*      */     });
/*  326 */     Toolkit.setSceneAccessor(new Toolkit.SceneAccessor() {
/*      */       public void setPaused(boolean paramAnonymousBoolean) {
/*  328 */         Scene.access$402(paramAnonymousBoolean);
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private static class TargetWrapper
/*      */   {
/*      */     private Scene scene;
/*      */     private Node node;
/*      */ 
/*      */     public void fillHierarchy(List<EventTarget> paramList)
/*      */     {
/* 5499 */       paramList.clear();
/* 5500 */       Object localObject = this.node;
/* 5501 */       while (localObject != null) {
/* 5502 */         paramList.add(localObject);
/* 5503 */         localObject = ((Node)localObject).getParent();
/*      */       }
/*      */ 
/* 5506 */       if (this.scene != null)
/* 5507 */         paramList.add(this.scene);
/*      */     }
/*      */ 
/*      */     public EventTarget getEventTarget()
/*      */     {
/* 5512 */       return this.node != null ? this.node : this.scene;
/*      */     }
/*      */ 
/*      */     public Cursor getCursor() {
/* 5516 */       Cursor localCursor = null;
/* 5517 */       if (this.node != null) {
/* 5518 */         localCursor = this.node.getCursor();
/* 5519 */         Parent localParent = this.node.getParent();
/* 5520 */         while ((localCursor == null) && (localParent != null)) {
/* 5521 */           localCursor = localParent.getCursor();
/* 5522 */           localParent = localParent.getParent();
/*      */         }
/*      */       }
/* 5525 */       return localCursor;
/*      */     }
/*      */ 
/*      */     public void clear() {
/* 5529 */       set(null, null);
/*      */     }
/*      */ 
/*      */     public void setScene(Scene paramScene) {
/* 5533 */       set(null, paramScene);
/*      */     }
/*      */ 
/*      */     public void setNode(Node paramNode) {
/* 5537 */       set(paramNode, paramNode.getScene());
/*      */     }
/*      */ 
/*      */     public void copy(TargetWrapper paramTargetWrapper) {
/* 5541 */       this.node = paramTargetWrapper.node;
/* 5542 */       this.scene = paramTargetWrapper.scene;
/*      */     }
/*      */ 
/*      */     private void set(Node paramNode, Scene paramScene) {
/* 5546 */       this.node = paramNode;
/* 5547 */       this.scene = paramScene;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class TouchMap
/*      */   {
/*      */     private static final int FAST_THRESHOLD = 10;
/* 4936 */     int[] fastMap = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/* 4937 */     Map<Long, Integer> slowMap = new HashMap();
/* 4938 */     List<Integer> order = new LinkedList();
/* 4939 */     List<Long> removed = new ArrayList(10);
/* 4940 */     int counter = 0;
/* 4941 */     int active = 0;
/*      */ 
/*      */     public int add(long paramLong) {
/* 4944 */       this.counter += 1;
/* 4945 */       this.active += 1;
/* 4946 */       if (paramLong < 10L)
/* 4947 */         this.fastMap[((int)paramLong)] = this.counter;
/*      */       else {
/* 4949 */         this.slowMap.put(Long.valueOf(paramLong), Integer.valueOf(this.counter));
/*      */       }
/* 4951 */       this.order.add(Integer.valueOf(this.counter));
/* 4952 */       return this.counter;
/*      */     }
/*      */ 
/*      */     public void remove(long paramLong)
/*      */     {
/* 4958 */       this.removed.add(Long.valueOf(paramLong));
/*      */     }
/*      */ 
/*      */     public int get(long paramLong) {
/* 4962 */       if (paramLong < 10L) {
/* 4963 */         int i = this.fastMap[((int)paramLong)];
/* 4964 */         if (i == 0) {
/* 4965 */           throw new RuntimeException("Platform reported wrong touch point ID");
/*      */         }
/*      */ 
/* 4968 */         return i;
/*      */       }
/*      */       try {
/* 4971 */         return ((Integer)this.slowMap.get(Long.valueOf(paramLong))).intValue(); } catch (NullPointerException localNullPointerException) {
/*      */       }
/* 4973 */       throw new RuntimeException("Platform reported wrong touch point ID");
/*      */     }
/*      */ 
/*      */     public int getOrder(int paramInt)
/*      */     {
/* 4980 */       return this.order.indexOf(Integer.valueOf(paramInt));
/*      */     }
/*      */ 
/*      */     public boolean cleanup()
/*      */     {
/* 4985 */       for (Iterator localIterator = this.removed.iterator(); localIterator.hasNext(); ) { long l = ((Long)localIterator.next()).longValue();
/* 4986 */         this.active -= 1;
/* 4987 */         this.order.remove(Integer.valueOf(get(l)));
/* 4988 */         if (l < 10L)
/* 4989 */           this.fastMap[((int)l)] = 0;
/*      */         else {
/* 4991 */           this.slowMap.remove(Long.valueOf(l));
/*      */         }
/* 4993 */         if (this.active == 0)
/*      */         {
/* 4995 */           this.counter = 0;
/*      */         }
/*      */       }
/* 4998 */       this.removed.clear();
/* 4999 */       return this.active == 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   class InputMethodRequestsDelegate
/*      */     implements InputMethodRequests
/*      */   {
/*      */     InputMethodRequestsDelegate()
/*      */     {
/*      */     }
/*      */ 
/*      */     public Point2D getTextLocation(int paramInt)
/*      */     {
/* 3564 */       InputMethodRequests localInputMethodRequests = getClientRequests();
/* 3565 */       if (localInputMethodRequests != null) {
/* 3566 */         return localInputMethodRequests.getTextLocation(paramInt);
/*      */       }
/* 3568 */       return new Point2D(0.0D, 0.0D);
/*      */     }
/*      */ 
/*      */     public int getLocationOffset(int paramInt1, int paramInt2)
/*      */     {
/* 3574 */       InputMethodRequests localInputMethodRequests = getClientRequests();
/* 3575 */       if (localInputMethodRequests != null) {
/* 3576 */         return localInputMethodRequests.getLocationOffset(paramInt1, paramInt2);
/*      */       }
/* 3578 */       return 0;
/*      */     }
/*      */ 
/*      */     public void cancelLatestCommittedText()
/*      */     {
/* 3584 */       InputMethodRequests localInputMethodRequests = getClientRequests();
/* 3585 */       if (localInputMethodRequests != null)
/* 3586 */         localInputMethodRequests.cancelLatestCommittedText();
/*      */     }
/*      */ 
/*      */     public String getSelectedText()
/*      */     {
/* 3592 */       InputMethodRequests localInputMethodRequests = getClientRequests();
/* 3593 */       if (localInputMethodRequests != null) {
/* 3594 */         return localInputMethodRequests.getSelectedText();
/*      */       }
/* 3596 */       return null;
/*      */     }
/*      */ 
/*      */     private InputMethodRequests getClientRequests() {
/* 3600 */       Node localNode = Scene.this.getFocusOwner();
/* 3601 */       if (localNode != null) {
/* 3602 */         return localNode.getInputMethodRequests();
/*      */       }
/* 3604 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   class KeyHandler
/*      */   {
/*      */     private boolean windowFocused;
/* 3499 */     private final InvalidationListener sceneWindowFocusedListener = new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/* 3501 */         Scene.KeyHandler.this.setWindowFocused(((ReadOnlyBooleanProperty)paramAnonymousObservable).get());
/*      */       }
/* 3499 */     };
/*      */ 
/*      */     KeyHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     private void setFocusOwner(Node paramNode)
/*      */     {
/* 3474 */       Scene.this.focusOwner.set(paramNode);
/*      */     }
/*      */ 
/*      */     protected boolean isWindowFocused() {
/* 3478 */       return this.windowFocused;
/*      */     }
/* 3480 */     protected void setWindowFocused(boolean paramBoolean) { this.windowFocused = paramBoolean;
/* 3481 */       if (Scene.this.getFocusOwner() != null)
/* 3482 */         Scene.this.getFocusOwner().setFocused(this.windowFocused);
/*      */     }
/*      */ 
/*      */     private void windowForSceneChanged(Window paramWindow1, Window paramWindow2)
/*      */     {
/* 3487 */       if (paramWindow1 != null) {
/* 3488 */         paramWindow1.focusedProperty().removeListener(this.sceneWindowFocusedListener);
/*      */       }
/*      */ 
/* 3491 */       if (paramWindow2 != null) {
/* 3492 */         paramWindow2.focusedProperty().addListener(this.sceneWindowFocusedListener);
/* 3493 */         setWindowFocused(paramWindow2.isFocused());
/*      */       } else {
/* 3495 */         setWindowFocused(false);
/*      */       }
/*      */     }
/*      */ 
/*      */     private void process(KeyEvent paramKeyEvent)
/*      */     {
/* 3506 */       Node localNode = Scene.this.getFocusOwner();
/* 3507 */       Scene localScene = localNode != null ? localNode : Scene.this;
/*      */ 
/* 3513 */       Event.fireEvent(localScene, paramKeyEvent);
/*      */     }
/*      */ 
/*      */     private void requestFocus(Node paramNode) {
/* 3517 */       if ((Scene.this.getFocusOwner() == paramNode) || ((paramNode != null) && (!paramNode.isCanReceiveFocus()))) {
/* 3518 */         return;
/*      */       }
/* 3520 */       setFocusOwner(paramNode);
/*      */ 
/* 3522 */       if ((Scene.this.getFocusOwner() != null) && 
/* 3523 */         (Scene.this.impl_peer != null))
/* 3524 */         Scene.this.impl_peer.requestFocus();
/*      */     }
/*      */   }
/*      */ 
/*      */   class MouseHandler
/*      */   {
/* 3124 */     private Scene.TargetWrapper pdrEventTarget = new Scene.TargetWrapper(null);
/* 3125 */     private boolean pdrInProgress = false;
/* 3126 */     private boolean fullPDREntered = false;
/*      */ 
/* 3128 */     private EventTarget currentEventTarget = null;
/*      */     private MouseEvent lastEvent;
/* 3130 */     private boolean hover = false;
/*      */ 
/* 3132 */     private boolean primaryButtonDown = false;
/* 3133 */     private boolean secondaryButtonDown = false;
/* 3134 */     private boolean middleButtonDown = false;
/*      */ 
/* 3136 */     private EventTarget fullPDRSource = null;
/* 3137 */     private Scene.TargetWrapper fullPDRTmpTargetWrapper = new Scene.TargetWrapper(null);
/*      */ 
/* 3140 */     private final List<EventTarget> pdrEventTargets = new ArrayList();
/* 3141 */     private final List<EventTarget> currentEventTargets = new ArrayList();
/* 3142 */     private final List<EventTarget> newEventTargets = new ArrayList();
/*      */ 
/* 3144 */     private final List<EventTarget> fullPDRCurrentEventTargets = new ArrayList();
/* 3145 */     private final List<EventTarget> fullPDRNewEventTargets = new ArrayList();
/* 3146 */     private EventTarget fullPDRCurrentTarget = null;
/*      */     private Cursor currCursor;
/*      */     private CursorFrame currCursorFrame;
/* 3150 */     private EventQueue queue = new EventQueue();
/*      */ 
/* 3152 */     private Runnable pickProcess = new Runnable()
/*      */     {
/*      */       public void run()
/*      */       {
/* 3156 */         Scene.MouseHandler.this.process(Scene.MouseHandler.this.lastEvent, true);
/*      */       }
/* 3152 */     };
/*      */ 
/*      */     MouseHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     private void pulse()
/*      */     {
/* 3161 */       if ((this.hover) && (this.lastEvent != null))
/*      */       {
/* 3163 */         Platform.runLater(this.pickProcess);
/*      */       }
/*      */     }
/*      */ 
/*      */     private void process(MouseEvent paramMouseEvent) {
/* 3168 */       process(paramMouseEvent, false);
/*      */     }
/*      */ 
/*      */     private void clearPDREventTargets() {
/* 3172 */       this.pdrInProgress = false;
/* 3173 */       this.currentEventTarget = (this.currentEventTargets.size() > 0 ? (EventTarget)this.currentEventTargets.get(0) : null);
/*      */ 
/* 3175 */       this.pdrEventTarget.clear();
/*      */     }
/*      */ 
/*      */     public void enterFullPDR(EventTarget paramEventTarget) {
/* 3179 */       this.fullPDREntered = true;
/* 3180 */       this.fullPDRSource = paramEventTarget;
/* 3181 */       this.fullPDRCurrentTarget = null;
/* 3182 */       this.fullPDRCurrentEventTargets.clear();
/*      */     }
/*      */ 
/*      */     public void exitFullPDR(MouseEvent paramMouseEvent) {
/* 3186 */       if (!this.fullPDREntered) {
/* 3187 */         return;
/*      */       }
/* 3189 */       this.fullPDREntered = false;
/* 3190 */       for (int i = this.fullPDRCurrentEventTargets.size() - 1; i >= 0; i--) {
/* 3191 */         EventTarget localEventTarget = (EventTarget)this.fullPDRCurrentEventTargets.get(i);
/* 3192 */         Event.fireEvent(localEventTarget, MouseDragEvent.impl_copy(localEventTarget, localEventTarget, this.fullPDRSource, paramMouseEvent, MouseDragEvent.MOUSE_DRAG_EXITED_TARGET));
/*      */       }
/*      */ 
/* 3196 */       this.fullPDRSource = null;
/* 3197 */       this.fullPDRCurrentEventTargets.clear();
/* 3198 */       this.fullPDRCurrentTarget = null;
/*      */     }
/*      */ 
/*      */     private void handleEnterExit(MouseEvent paramMouseEvent, Scene.TargetWrapper paramTargetWrapper) {
/* 3202 */       if ((paramTargetWrapper.getEventTarget() != this.currentEventTarget) || (paramMouseEvent.getEventType() == MouseEvent.MOUSE_EXITED))
/*      */       {
/* 3205 */         if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_EXITED)
/* 3206 */           this.newEventTargets.clear();
/*      */         else {
/* 3208 */           paramTargetWrapper.fillHierarchy(this.newEventTargets);
/*      */         }
/*      */ 
/* 3211 */         int i = this.newEventTargets.size();
/* 3212 */         int j = this.currentEventTargets.size() - 1;
/* 3213 */         int k = i - 1;
/* 3214 */         int m = this.pdrEventTargets.size() - 1;
/*      */ 
/* 3216 */         while ((j >= 0) && (k >= 0) && (this.currentEventTargets.get(j) == this.newEventTargets.get(k))) {
/* 3217 */           j--;
/* 3218 */           k--;
/* 3219 */           m--;
/*      */         }
/*      */ 
/* 3222 */         int n = m;
/*      */         EventTarget localEventTarget;
/* 3223 */         for (; j >= 0; m--) {
/* 3224 */           localEventTarget = (EventTarget)this.currentEventTargets.get(j);
/* 3225 */           if ((this.pdrInProgress) && ((m < 0) || (localEventTarget != this.pdrEventTargets.get(m))))
/*      */           {
/*      */             break;
/*      */           }
/* 3229 */           this.queue.postEvent(MouseEvent.impl_copy(localEventTarget, localEventTarget, paramMouseEvent, MouseEvent.MOUSE_EXITED_TARGET));
/*      */ 
/* 3223 */           j--;
/*      */         }
/*      */ 
/* 3234 */         for (m = n; 
/* 3235 */           k >= 0; m--) {
/* 3236 */           localEventTarget = (EventTarget)this.newEventTargets.get(k);
/* 3237 */           if ((this.pdrInProgress) && ((m < 0) || (localEventTarget != this.pdrEventTargets.get(m))))
/*      */           {
/*      */             break;
/*      */           }
/* 3241 */           this.queue.postEvent(MouseEvent.impl_copy(localEventTarget, localEventTarget, paramMouseEvent, MouseEvent.MOUSE_ENTERED_TARGET));
/*      */ 
/* 3235 */           k--;
/*      */         }
/*      */ 
/* 3246 */         this.currentEventTarget = paramTargetWrapper.getEventTarget();
/* 3247 */         this.currentEventTargets.clear();
/* 3248 */         for (k++; k < i; k++) {
/* 3249 */           this.currentEventTargets.add(this.newEventTargets.get(k));
/*      */         }
/*      */       }
/* 3252 */       this.queue.fire();
/*      */     }
/*      */ 
/*      */     private void process(MouseEvent paramMouseEvent, boolean paramBoolean) {
/* 3256 */       Toolkit.getToolkit().checkFxUserThread();
/* 3257 */       Scene.access$8202(true);
/*      */ 
/* 3259 */       Scene.this.cursorScreenPos = new Point2D(paramMouseEvent.getScreenX(), paramMouseEvent.getScreenY());
/* 3260 */       Scene.this.cursorScenePos = new Point2D(paramMouseEvent.getSceneX(), paramMouseEvent.getSceneY());
/*      */ 
/* 3262 */       int i = 0;
/* 3263 */       if (!paramBoolean) {
/* 3264 */         if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
/* 3265 */           if ((!this.primaryButtonDown) && (!this.secondaryButtonDown) && (!this.middleButtonDown))
/*      */           {
/* 3267 */             i = 1;
/*      */ 
/* 3269 */             Scene.this.dndGesture = new Scene.DnDGesture(Scene.this);
/*      */ 
/* 3271 */             clearPDREventTargets();
/*      */           }
/* 3273 */         } else if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_MOVED)
/*      */         {
/* 3275 */           clearPDREventTargets();
/* 3276 */         } else if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED)
/* 3277 */           this.hover = true;
/* 3278 */         else if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
/* 3279 */           this.hover = false;
/*      */         }
/*      */ 
/* 3282 */         this.primaryButtonDown = paramMouseEvent.isPrimaryButtonDown();
/* 3283 */         this.secondaryButtonDown = paramMouseEvent.isSecondaryButtonDown();
/* 3284 */         this.middleButtonDown = paramMouseEvent.isMiddleButtonDown();
/*      */       }
/*      */ 
/* 3287 */       if (paramMouseEvent.getEventType() != MouseEvent.MOUSE_EXITED)
/* 3288 */         Scene.this.pick(Scene.this.tmpTargetWrapper, paramMouseEvent.getX(), paramMouseEvent.getY());
/*      */       else
/* 3290 */         Scene.this.tmpTargetWrapper.clear();
/*      */       Scene.TargetWrapper localTargetWrapper;
/* 3294 */       if (this.pdrInProgress)
/* 3295 */         localTargetWrapper = this.pdrEventTarget;
/*      */       else {
/* 3297 */         localTargetWrapper = Scene.this.tmpTargetWrapper;
/*      */       }
/*      */ 
/* 3300 */       if (i != 0) {
/* 3301 */         this.pdrEventTarget.copy(localTargetWrapper);
/* 3302 */         this.pdrEventTarget.fillHierarchy(this.pdrEventTargets);
/*      */       }
/*      */ 
/* 3305 */       if (!paramBoolean) {
/* 3306 */         Scene.ClickGenerator.access$8400(Scene.this.clickGenerator, paramMouseEvent);
/*      */       }
/*      */ 
/* 3310 */       handleEnterExit(paramMouseEvent, Scene.this.tmpTargetWrapper);
/*      */ 
/* 3312 */       Cursor localCursor = localTargetWrapper.getCursor();
/*      */ 
/* 3316 */       if (Scene.this.dndGesture != null) {
/* 3317 */         Scene.DnDGesture.access$8500(Scene.this.dndGesture, paramMouseEvent);
/*      */       }
/*      */ 
/* 3320 */       if ((this.fullPDREntered) && (paramMouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED)) {
/* 3321 */         processFullPDR(paramMouseEvent, paramBoolean);
/*      */       }
/*      */ 
/* 3324 */       if ((localTargetWrapper.getEventTarget() != null) && 
/* 3325 */         (paramMouseEvent.getEventType() != MouseEvent.MOUSE_ENTERED) && (paramMouseEvent.getEventType() != MouseEvent.MOUSE_EXITED) && (!paramBoolean))
/*      */       {
/* 3328 */         Event.fireEvent(localTargetWrapper.getEventTarget(), paramMouseEvent);
/*      */       }
/*      */ 
/* 3332 */       if ((this.fullPDREntered) && (paramMouseEvent.getEventType() != MouseEvent.MOUSE_RELEASED)) {
/* 3333 */         processFullPDR(paramMouseEvent, paramBoolean);
/*      */       }
/*      */ 
/* 3336 */       if (!paramBoolean) {
/* 3337 */         Scene.ClickGenerator.access$8600(Scene.this.clickGenerator, paramMouseEvent, localTargetWrapper, Scene.this.tmpTargetWrapper);
/*      */       }
/*      */ 
/* 3342 */       if ((!paramBoolean) && 
/* 3343 */         (Scene.this.dndGesture != null) && 
/* 3344 */         (!Scene.DnDGesture.access$8700(Scene.this.dndGesture, paramMouseEvent, localTargetWrapper.getEventTarget()))) {
/* 3345 */         Scene.this.dndGesture = null;
/*      */       }
/*      */ 
/* 3351 */       if ((localCursor == null) && (this.hover)) {
/* 3352 */         localCursor = Scene.this.getCursor();
/*      */       }
/*      */ 
/* 3355 */       updateCursor(localCursor);
/* 3356 */       updateCursorFrame();
/*      */ 
/* 3358 */       if (i != 0) {
/* 3359 */         this.pdrInProgress = true;
/*      */       }
/*      */ 
/* 3362 */       if ((this.pdrInProgress) && (!this.primaryButtonDown) && (!this.secondaryButtonDown) && (!this.middleButtonDown))
/*      */       {
/* 3364 */         clearPDREventTargets();
/* 3365 */         exitFullPDR(paramMouseEvent);
/* 3366 */         handleEnterExit(paramMouseEvent, Scene.this.tmpTargetWrapper);
/*      */       }
/*      */ 
/* 3369 */       this.lastEvent = paramMouseEvent;
/* 3370 */       Scene.access$8202(false);
/*      */     }
/*      */ 
/*      */     private void processFullPDR(MouseEvent paramMouseEvent, boolean paramBoolean)
/*      */     {
/* 3375 */       Scene.this.pick(this.fullPDRTmpTargetWrapper, paramMouseEvent.getX(), paramMouseEvent.getY());
/*      */ 
/* 3377 */       EventTarget localEventTarget1 = this.fullPDRTmpTargetWrapper.getEventTarget();
/*      */ 
/* 3380 */       if (localEventTarget1 != this.fullPDRCurrentTarget)
/*      */       {
/* 3382 */         this.fullPDRTmpTargetWrapper.fillHierarchy(this.fullPDRNewEventTargets);
/*      */ 
/* 3384 */         int i = this.fullPDRNewEventTargets.size();
/* 3385 */         int j = this.fullPDRCurrentEventTargets.size() - 1;
/* 3386 */         int k = i - 1;
/*      */ 
/* 3389 */         while ((j >= 0) && (k >= 0) && (this.fullPDRCurrentEventTargets.get(j) == this.fullPDRNewEventTargets.get(k))) {
/* 3390 */           j--;
/* 3391 */           k--;
/*      */         }
/*      */         EventTarget localEventTarget2;
/* 3394 */         for (; j >= 0; j--) {
/* 3395 */           localEventTarget2 = (EventTarget)this.fullPDRCurrentEventTargets.get(j);
/* 3396 */           Event.fireEvent(localEventTarget2, MouseDragEvent.impl_copy(localEventTarget2, localEventTarget2, this.fullPDRSource, paramMouseEvent, MouseDragEvent.MOUSE_DRAG_EXITED_TARGET));
/*      */         }
/*      */ 
/* 3401 */         for (; k >= 0; k--) {
/* 3402 */           localEventTarget2 = (EventTarget)this.fullPDRNewEventTargets.get(k);
/* 3403 */           Event.fireEvent(localEventTarget2, MouseDragEvent.impl_copy(localEventTarget2, localEventTarget2, this.fullPDRSource, paramMouseEvent, MouseDragEvent.MOUSE_DRAG_ENTERED_TARGET));
/*      */         }
/*      */ 
/* 3408 */         this.fullPDRCurrentTarget = localEventTarget1;
/* 3409 */         this.fullPDRCurrentEventTargets.clear();
/* 3410 */         this.fullPDRCurrentEventTargets.addAll(this.fullPDRNewEventTargets);
/*      */       }
/*      */ 
/* 3415 */       if ((localEventTarget1 != null) && (!paramBoolean)) {
/* 3416 */         if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
/* 3417 */           Event.fireEvent(localEventTarget1, MouseDragEvent.impl_copy(localEventTarget1, localEventTarget1, this.fullPDRSource, paramMouseEvent, MouseDragEvent.MOUSE_DRAG_OVER));
/*      */         }
/*      */ 
/* 3421 */         if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED)
/* 3422 */           Event.fireEvent(localEventTarget1, MouseDragEvent.impl_copy(localEventTarget1, localEventTarget1, this.fullPDRSource, paramMouseEvent, MouseDragEvent.MOUSE_DRAG_RELEASED));
/*      */       }
/*      */     }
/*      */ 
/*      */     private void updateCursor(Cursor paramCursor)
/*      */     {
/* 3430 */       if (this.currCursor != paramCursor) {
/* 3431 */         if (this.currCursor != null) {
/* 3432 */           this.currCursor.deactivate();
/*      */         }
/*      */ 
/* 3435 */         if (paramCursor != null) {
/* 3436 */           paramCursor.activate();
/*      */         }
/*      */ 
/* 3439 */         this.currCursor = paramCursor;
/*      */       }
/*      */     }
/*      */ 
/*      */     public void updateCursorFrame() {
/* 3444 */       CursorFrame localCursorFrame = this.currCursor != null ? this.currCursor.getCurrentFrame() : Cursor.DEFAULT.getCurrentFrame();
/*      */ 
/* 3448 */       if (this.currCursorFrame != localCursorFrame) {
/* 3449 */         if (Scene.this.impl_peer != null) {
/* 3450 */           Scene.this.impl_peer.setCursor(localCursorFrame);
/*      */         }
/*      */ 
/* 3453 */         this.currCursorFrame = localCursorFrame;
/*      */       }
/*      */     }
/*      */ 
/*      */     private Node pickNode(double paramDouble1, double paramDouble2) {
/* 3458 */       return Scene.this.getRoot().impl_pickNode(paramDouble1, paramDouble2);
/*      */     }
/*      */ 
/*      */     private Node pickNode(PickRay paramPickRay) {
/* 3462 */       return Scene.this.getRoot().impl_pickNode(paramPickRay);
/*      */     }
/*      */   }
/*      */ 
/*      */   static class ClickGenerator
/*      */   {
/* 3056 */     private Scene.ClickCounter lastPress = null;
/*      */ 
/* 3058 */     private Map<MouseButton, Scene.ClickCounter> counters = new EnumMap(MouseButton.class);
/*      */ 
/* 3060 */     private List<EventTarget> pressedTargets = new ArrayList();
/* 3061 */     private List<EventTarget> releasedTargets = new ArrayList();
/*      */ 
/*      */     public ClickGenerator() {
/* 3064 */       for (MouseButton localMouseButton : MouseButton.values())
/* 3065 */         if (localMouseButton != MouseButton.NONE)
/* 3066 */           this.counters.put(localMouseButton, new Scene.ClickCounter());
/*      */     }
/*      */ 
/*      */     private void preProcess(MouseEvent paramMouseEvent)
/*      */     {
/* 3072 */       for (Object localObject = this.counters.values().iterator(); ((Iterator)localObject).hasNext(); ) { Scene.ClickCounter localClickCounter = (Scene.ClickCounter)((Iterator)localObject).next();
/* 3073 */         Scene.ClickCounter.access$7300(localClickCounter, paramMouseEvent.getSceneX(), paramMouseEvent.getSceneY());
/*      */       }
/*      */ 
/* 3076 */       localObject = (Scene.ClickCounter)this.counters.get(paramMouseEvent.getButton());
/* 3077 */       boolean bool = this.lastPress != null ? Scene.ClickCounter.access$7400(this.lastPress) : false;
/*      */ 
/* 3079 */       if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED)
/*      */       {
/* 3081 */         if (!paramMouseEvent.isPrimaryButtonDown()) Scene.ClickCounter.access$7500((Scene.ClickCounter)this.counters.get(MouseButton.PRIMARY));
/* 3082 */         if (!paramMouseEvent.isSecondaryButtonDown()) Scene.ClickCounter.access$7500((Scene.ClickCounter)this.counters.get(MouseButton.SECONDARY));
/* 3083 */         if (!paramMouseEvent.isMiddleButtonDown()) Scene.ClickCounter.access$7500((Scene.ClickCounter)this.counters.get(MouseButton.MIDDLE));
/*      */ 
/* 3085 */         Scene.ClickCounter.access$7600((Scene.ClickCounter)localObject);
/* 3086 */         Scene.ClickCounter.access$7700((Scene.ClickCounter)localObject);
/* 3087 */         Scene.ClickCounter.access$7800((Scene.ClickCounter)localObject, paramMouseEvent.getSceneX(), paramMouseEvent.getSceneY());
/* 3088 */         this.lastPress = ((Scene.ClickCounter)localObject);
/*      */       }
/*      */ 
/* 3091 */       paramMouseEvent.impl_setClickParams((localObject != null) && (paramMouseEvent.getEventType() != MouseEvent.MOUSE_MOVED) ? Scene.ClickCounter.access$7900((Scene.ClickCounter)localObject) : 0, bool);
/*      */     }
/*      */ 
/*      */     private void postProcess(MouseEvent paramMouseEvent, Scene.TargetWrapper paramTargetWrapper1, Scene.TargetWrapper paramTargetWrapper2)
/*      */     {
/* 3098 */       if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
/* 3099 */         Scene.ClickCounter localClickCounter = (Scene.ClickCounter)this.counters.get(paramMouseEvent.getButton());
/*      */ 
/* 3101 */         paramTargetWrapper1.fillHierarchy(this.pressedTargets);
/* 3102 */         paramTargetWrapper2.fillHierarchy(this.releasedTargets);
/* 3103 */         int i = this.pressedTargets.size() - 1;
/* 3104 */         int j = this.releasedTargets.size() - 1;
/*      */ 
/* 3106 */         EventTarget localEventTarget = null;
/* 3107 */         while ((i >= 0) && (j >= 0) && (this.pressedTargets.get(i) == this.releasedTargets.get(j))) {
/* 3108 */           localEventTarget = (EventTarget)this.pressedTargets.get(i);
/* 3109 */           i--;
/* 3110 */           j--;
/*      */         }
/*      */ 
/* 3113 */         if (localEventTarget != null) {
/* 3114 */           MouseEvent localMouseEvent = MouseEvent.impl_copy(null, localEventTarget, paramMouseEvent, MouseEvent.MOUSE_CLICKED);
/*      */ 
/* 3116 */           localMouseEvent.impl_setClickParams(Scene.ClickCounter.access$7900(localClickCounter), Scene.ClickCounter.access$7400(this.lastPress));
/* 3117 */           Event.fireEvent(localEventTarget, localMouseEvent);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   static class ClickCounter
/*      */   {
/* 2990 */     Toolkit toolkit = Toolkit.getToolkit();
/*      */     private int count;
/*      */     private boolean out;
/*      */     private boolean still;
/*      */     private Timeline timeout;
/*      */     private double pressedX;
/*      */     private double pressedY;
/*      */ 
/*      */     private void inc()
/*      */     {
/* 2997 */       this.count += 1; } 
/* 2998 */     private int get() { return this.count; } 
/* 2999 */     private boolean isStill() { return this.still; }
/*      */ 
/*      */     private void clear() {
/* 3002 */       this.count = 0;
/* 3003 */       stopTimeout();
/*      */     }
/*      */ 
/*      */     private void out() {
/* 3007 */       this.out = true;
/* 3008 */       stopTimeout();
/*      */     }
/*      */ 
/*      */     private void applyOut() {
/* 3012 */       if (this.out) clear();
/* 3013 */       this.out = false;
/*      */     }
/*      */ 
/*      */     private void moved(double paramDouble1, double paramDouble2) {
/* 3017 */       if ((Math.abs(paramDouble1 - this.pressedX) > this.toolkit.getMultiClickMaxX()) || (Math.abs(paramDouble2 - this.pressedY) > this.toolkit.getMultiClickMaxY()))
/*      */       {
/* 3019 */         out();
/* 3020 */         this.still = false;
/*      */       }
/*      */     }
/*      */ 
/*      */     private void start(double paramDouble1, double paramDouble2) {
/* 3025 */       this.pressedX = paramDouble1;
/* 3026 */       this.pressedY = paramDouble2;
/* 3027 */       this.out = false;
/*      */ 
/* 3029 */       if (this.timeout != null) {
/* 3030 */         this.timeout.stop();
/*      */       }
/* 3032 */       this.timeout = new Timeline();
/* 3033 */       this.timeout.getKeyFrames().add(new KeyFrame(new Duration(this.toolkit.getMultiClickTime()), new EventHandler()
/*      */       {
/*      */         public void handle(ActionEvent paramAnonymousActionEvent)
/*      */         {
/* 3038 */           Scene.ClickCounter.this.out = true;
/* 3039 */           Scene.ClickCounter.this.timeout = null;
/*      */         }
/*      */       }
/*      */       , new KeyValue[0]));
/*      */ 
/* 3043 */       this.timeout.play();
/* 3044 */       this.still = true;
/*      */     }
/*      */ 
/*      */     private void stopTimeout() {
/* 3048 */       if (this.timeout != null) {
/* 3049 */         this.timeout.stop();
/* 3050 */         this.timeout = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   class DragSourceListener
/*      */     implements TKDragSourceListener
/*      */   {
/*      */     DragSourceListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void dropActionChanged(Object paramObject)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void dragDropEnd(Object paramObject)
/*      */     {
/* 2974 */       if (Scene.this.dndGesture != null) {
/* 2975 */         Scene.DnDGesture.access$7000(Scene.this.dndGesture, Toolkit.getToolkit().convertDragSourceEventToFX(paramObject, Scene.DnDGesture.access$6000(Scene.this.dndGesture)));
/* 2976 */         Scene.this.dndGesture = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static enum DragDetectedState
/*      */   {
/* 2960 */     NOT_YET, 
/* 2961 */     PROCESSING, 
/* 2962 */     DONE;
/*      */   }
/*      */ 
/*      */   class DnDGesture
/*      */   {
/* 2608 */     private final double hysteresisSizeX = Toolkit.getToolkit().getMultiClickMaxX();
/*      */ 
/* 2610 */     private final double hysteresisSizeY = Toolkit.getToolkit().getMultiClickMaxY();
/*      */ 
/* 2613 */     private EventTarget source = null;
/* 2614 */     private Set<TransferMode> sourceTransferModes = null;
/* 2615 */     private TransferMode acceptedTransferMode = null;
/* 2616 */     private Dragboard dragboard = null;
/* 2617 */     private EventTarget potentialTarget = null;
/* 2618 */     private EventTarget target = null;
/* 2619 */     private Scene.DragDetectedState dragDetected = Scene.DragDetectedState.NOT_YET;
/*      */     private double pressedX;
/*      */     private double pressedY;
/* 2622 */     private List<EventTarget> currentTargets = new ArrayList();
/* 2623 */     private List<EventTarget> newTargets = new ArrayList();
/* 2624 */     private EventTarget fullPDRSource = null;
/*      */ 
/*      */     DnDGesture() {
/*      */     }
/*      */ 
/*      */     private void fireEvent(EventTarget paramEventTarget, Event paramEvent) {
/* 2630 */       if (paramEventTarget != null)
/* 2631 */         Event.fireEvent(paramEventTarget, paramEvent);
/*      */     }
/*      */ 
/*      */     private void processingDragDetected()
/*      */     {
/* 2640 */       this.dragDetected = Scene.DragDetectedState.PROCESSING;
/*      */     }
/*      */ 
/*      */     private void dragDetectedProcessed()
/*      */     {
/* 2647 */       this.dragDetected = Scene.DragDetectedState.DONE;
/* 2648 */       int i = (this.dragboard != null) && (this.dragboard.impl_contentPut()) ? 1 : 0;
/*      */ 
/* 2650 */       if (i != 0)
/*      */       {
/* 2652 */         Toolkit.getToolkit().startDrag(Scene.this.impl_peer, this.sourceTransferModes, new Scene.DragSourceListener(Scene.this), this.dragboard);
/*      */       }
/* 2656 */       else if (this.fullPDRSource != null)
/*      */       {
/* 2658 */         Scene.this.mouseHandler.enterFullPDR(this.fullPDRSource);
/*      */       }
/*      */ 
/* 2661 */       this.fullPDRSource = null;
/*      */     }
/*      */ 
/*      */     private void processDragDetection(MouseEvent paramMouseEvent)
/*      */     {
/* 2669 */       if (this.dragDetected != Scene.DragDetectedState.NOT_YET) {
/* 2670 */         paramMouseEvent.setDragDetect(false);
/* 2671 */         return;
/*      */       }
/*      */ 
/* 2674 */       if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
/* 2675 */         this.pressedX = paramMouseEvent.getSceneX();
/* 2676 */         this.pressedY = paramMouseEvent.getSceneY();
/*      */ 
/* 2678 */         paramMouseEvent.setDragDetect(false);
/*      */       }
/* 2680 */       else if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)
/*      */       {
/* 2682 */         double d1 = Math.abs(paramMouseEvent.getSceneX() - this.pressedX);
/* 2683 */         double d2 = Math.abs(paramMouseEvent.getSceneY() - this.pressedY);
/* 2684 */         paramMouseEvent.setDragDetect((d1 > this.hysteresisSizeX) || (d2 > this.hysteresisSizeY));
/*      */       }
/*      */     }
/*      */ 
/*      */     private boolean process(MouseEvent paramMouseEvent, EventTarget paramEventTarget)
/*      */     {
/* 2695 */       boolean bool = true;
/*      */ 
/* 2698 */       if ((this.dragDetected != Scene.DragDetectedState.DONE) && ((paramMouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) || (paramMouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED)) && (paramMouseEvent.isDragDetect()))
/*      */       {
/* 2703 */         processingDragDetected();
/*      */ 
/* 2705 */         if (paramEventTarget != null) {
/* 2706 */           MouseEvent localMouseEvent = MouseEvent.impl_copy(paramMouseEvent.getSource(), paramEventTarget, paramMouseEvent, MouseEvent.DRAG_DETECTED);
/*      */ 
/* 2710 */           fireEvent(paramEventTarget, localMouseEvent);
/*      */         }
/*      */ 
/* 2713 */         dragDetectedProcessed();
/*      */       }
/*      */ 
/* 2716 */       if (paramMouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
/* 2717 */         bool = false;
/*      */       }
/*      */ 
/* 2720 */       return bool;
/*      */     }
/*      */ 
/*      */     private boolean processRecognized(EventTarget paramEventTarget, DragEvent paramDragEvent)
/*      */     {
/* 2729 */       MouseEvent localMouseEvent = MouseEvent.impl_mouseEvent(paramDragEvent.getX(), paramDragEvent.getY(), paramDragEvent.getSceneX(), paramDragEvent.getScreenY(), MouseButton.PRIMARY, 1, false, false, false, false, false, true, false, false, false, MouseEvent.DRAG_DETECTED);
/*      */ 
/* 2734 */       processingDragDetected();
/*      */ 
/* 2736 */       fireEvent(paramEventTarget, localMouseEvent);
/*      */ 
/* 2738 */       dragDetectedProcessed();
/*      */ 
/* 2740 */       boolean bool = (this.dragboard != null) && (!this.dragboard.getContentTypes().isEmpty());
/*      */ 
/* 2742 */       return bool;
/*      */     }
/*      */ 
/*      */     private void processDropEnd(DragEvent paramDragEvent) {
/* 2746 */       if (this.source == null) {
/* 2747 */         System.out.println("Scene.DnDGesture.processDropEnd() - UNEXPECTD - source is NULL");
/* 2748 */         return;
/*      */       }
/*      */ 
/* 2751 */       paramDragEvent = DragEvent.impl_copy(paramDragEvent.getSource(), this.source, this.source, this.target, paramDragEvent, DragEvent.DRAG_DONE);
/*      */ 
/* 2754 */       Event.fireEvent(this.source, paramDragEvent);
/*      */ 
/* 2756 */       Scene.this.tmpTargetWrapper.clear();
/* 2757 */       handleExitEnter(paramDragEvent, Scene.this.tmpTargetWrapper);
/*      */ 
/* 2761 */       Toolkit.getToolkit().stopDrag(this.dragboard);
/*      */     }
/*      */ 
/*      */     private TransferMode processTargetEnterOver(DragEvent paramDragEvent) {
/* 2765 */       Scene.this.pick(Scene.this.tmpTargetWrapper, paramDragEvent.getX(), paramDragEvent.getY());
/* 2766 */       EventTarget localEventTarget = Scene.this.tmpTargetWrapper.getEventTarget();
/*      */ 
/* 2768 */       if (this.dragboard == null) {
/* 2769 */         this.dragboard = createDragboard(paramDragEvent);
/*      */       }
/*      */ 
/* 2772 */       paramDragEvent = DragEvent.impl_copy(paramDragEvent.getSource(), localEventTarget, this.source, this.potentialTarget, this.dragboard, paramDragEvent);
/*      */ 
/* 2775 */       handleExitEnter(paramDragEvent, Scene.this.tmpTargetWrapper);
/*      */ 
/* 2777 */       paramDragEvent = DragEvent.impl_copy(paramDragEvent.getSource(), localEventTarget, this.source, this.potentialTarget, paramDragEvent, DragEvent.DRAG_OVER);
/*      */ 
/* 2780 */       fireEvent(localEventTarget, paramDragEvent);
/*      */ 
/* 2782 */       Object localObject = paramDragEvent.impl_getAcceptingObject();
/* 2783 */       this.potentialTarget = ((localObject instanceof EventTarget) ? (EventTarget)localObject : null);
/*      */ 
/* 2785 */       this.acceptedTransferMode = paramDragEvent.getAcceptedTransferMode();
/* 2786 */       return this.acceptedTransferMode;
/*      */     }
/*      */ 
/*      */     private void processTargetActionChanged(DragEvent paramDragEvent)
/*      */     {
/*      */     }
/*      */ 
/*      */     private void processTargetExit(DragEvent paramDragEvent)
/*      */     {
/* 2806 */       if (this.currentTargets.size() > 0) {
/* 2807 */         this.potentialTarget = null;
/* 2808 */         Scene.this.tmpTargetWrapper.clear();
/* 2809 */         handleExitEnter(paramDragEvent, Scene.this.tmpTargetWrapper);
/*      */       }
/*      */     }
/*      */ 
/*      */     private TransferMode processTargetDrop(DragEvent paramDragEvent) {
/* 2814 */       Scene.this.pick(Scene.this.tmpTargetWrapper, paramDragEvent.getX(), paramDragEvent.getY());
/* 2815 */       EventTarget localEventTarget = Scene.this.tmpTargetWrapper.getEventTarget();
/*      */ 
/* 2817 */       paramDragEvent = DragEvent.impl_copy(paramDragEvent.getSource(), localEventTarget, this.source, this.potentialTarget, this.acceptedTransferMode, paramDragEvent, DragEvent.DRAG_DROPPED);
/*      */ 
/* 2821 */       if (this.dragboard == null) {
/* 2822 */         this.dragboard = createDragboard(paramDragEvent);
/*      */       }
/*      */ 
/* 2825 */       handleExitEnter(paramDragEvent, Scene.this.tmpTargetWrapper);
/*      */ 
/* 2827 */       fireEvent(localEventTarget, paramDragEvent);
/*      */ 
/* 2829 */       Object localObject = paramDragEvent.impl_getAcceptingObject();
/* 2830 */       this.potentialTarget = ((localObject instanceof EventTarget) ? (EventTarget)localObject : null);
/*      */ 
/* 2832 */       this.target = this.potentialTarget;
/*      */ 
/* 2834 */       TransferMode localTransferMode = paramDragEvent.isDropCompleted() ? paramDragEvent.getAcceptedTransferMode() : null;
/*      */ 
/* 2837 */       Scene.this.tmpTargetWrapper.clear();
/* 2838 */       handleExitEnter(paramDragEvent, Scene.this.tmpTargetWrapper);
/*      */ 
/* 2840 */       return localTransferMode;
/*      */     }
/*      */ 
/*      */     private void handleExitEnter(DragEvent paramDragEvent, Scene.TargetWrapper paramTargetWrapper) {
/* 2844 */       Object localObject1 = this.currentTargets.size() > 0 ? (EventTarget)this.currentTargets.get(0) : null;
/*      */ 
/* 2847 */       if (paramTargetWrapper.getEventTarget() != localObject1)
/*      */       {
/* 2849 */         paramTargetWrapper.fillHierarchy(this.newTargets);
/*      */ 
/* 2851 */         int i = this.currentTargets.size() - 1;
/* 2852 */         int j = this.newTargets.size() - 1;
/*      */ 
/* 2854 */         while ((i >= 0) && (j >= 0) && (this.currentTargets.get(i) == this.newTargets.get(j))) {
/* 2855 */           i--;
/* 2856 */           j--;
/*      */         }
/*      */         EventTarget localEventTarget;
/* 2859 */         for (; i >= 0; i--) {
/* 2860 */           localEventTarget = (EventTarget)this.currentTargets.get(i);
/* 2861 */           if (this.potentialTarget == localEventTarget) {
/* 2862 */             this.potentialTarget = null;
/*      */           }
/* 2864 */           paramDragEvent = DragEvent.impl_copy(paramDragEvent.getSource(), localEventTarget, this.source, this.potentialTarget, paramDragEvent, DragEvent.DRAG_EXITED_TARGET);
/*      */ 
/* 2866 */           Event.fireEvent(localEventTarget, paramDragEvent);
/*      */         }
/*      */ 
/* 2869 */         this.potentialTarget = null;
/* 2870 */         for (; j >= 0; j--) {
/* 2871 */           localEventTarget = (EventTarget)this.newTargets.get(j);
/* 2872 */           paramDragEvent = DragEvent.impl_copy(paramDragEvent.getSource(), localEventTarget, this.source, this.potentialTarget, paramDragEvent, DragEvent.DRAG_ENTERED_TARGET);
/*      */ 
/* 2874 */           Object localObject2 = paramDragEvent.impl_getAcceptingObject();
/* 2875 */           if ((localObject2 instanceof EventTarget)) {
/* 2876 */             this.potentialTarget = ((EventTarget)localObject2);
/*      */           }
/* 2878 */           Event.fireEvent(localEventTarget, paramDragEvent);
/*      */         }
/*      */ 
/* 2881 */         this.currentTargets.clear();
/* 2882 */         this.currentTargets.addAll(this.newTargets);
/*      */       }
/*      */     }
/*      */ 
/*      */     private boolean processKey(KeyEvent paramKeyEvent)
/*      */     {
/* 2897 */       if ((paramKeyEvent.getEventType() == KeyEvent.KEY_PRESSED) && (paramKeyEvent.getCode() == KeyCode.ESCAPE))
/*      */       {
/* 2900 */         DragEvent localDragEvent = DragEvent.impl_create(DragEvent.DRAG_DONE, this.source, this.source, this.source, null, 0.0D, 0.0D, 0.0D, 0.0D, null, this.dragboard, null);
/*      */ 
/* 2904 */         if (this.source != null) {
/* 2905 */           Event.fireEvent(this.source, localDragEvent);
/*      */         }
/*      */ 
/* 2908 */         Scene.this.tmpTargetWrapper.clear();
/* 2909 */         handleExitEnter(localDragEvent, Scene.this.tmpTargetWrapper);
/*      */ 
/* 2911 */         return false;
/*      */       }
/* 2913 */       return true;
/*      */     }
/*      */ 
/*      */     private Dragboard startDrag(EventTarget paramEventTarget, Set<TransferMode> paramSet)
/*      */     {
/* 2921 */       if (this.dragDetected != Scene.DragDetectedState.PROCESSING) {
/* 2922 */         throw new IllegalStateException("Cannot start drag and drop outside of DRAG_DETECTED event handler");
/*      */       }
/*      */ 
/* 2926 */       if (paramSet.isEmpty())
/* 2927 */         this.dragboard = null;
/* 2928 */       else if (this.dragboard == null) {
/* 2929 */         this.dragboard = createDragboard(null);
/*      */       }
/* 2931 */       this.source = paramEventTarget;
/* 2932 */       this.potentialTarget = paramEventTarget;
/* 2933 */       this.sourceTransferModes = paramSet;
/* 2934 */       return this.dragboard;
/*      */     }
/*      */ 
/*      */     private void startFullPDR(EventTarget paramEventTarget)
/*      */     {
/* 2941 */       this.fullPDRSource = paramEventTarget;
/*      */     }
/*      */ 
/*      */     private Dragboard createDragboard(DragEvent paramDragEvent) {
/* 2945 */       Dragboard localDragboard = null;
/* 2946 */       if (paramDragEvent != null) {
/* 2947 */         localDragboard = paramDragEvent.getDragboard();
/* 2948 */         if (localDragboard != null) {
/* 2949 */           return localDragboard;
/*      */         }
/*      */       }
/* 2952 */       return Scene.this.impl_peer.createDragboard();
/*      */     }
/*      */   }
/*      */ 
/*      */   class DragGestureListener
/*      */     implements TKDragGestureListener
/*      */   {
/*      */     DragGestureListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void dragGestureRecognized(Object paramObject)
/*      */     {
/* 2592 */       Scene.this.dndGesture = new Scene.DnDGesture(Scene.this);
/* 2593 */       DragEvent localDragEvent = Toolkit.getToolkit().convertDragRecognizedEventToFX(paramObject, null);
/* 2594 */       EventTarget localEventTarget = Scene.this.pick(localDragEvent.getX(), localDragEvent.getY());
/* 2595 */       Scene.this.dndGesture.dragboard = localDragEvent.impl_getPlatformDragboard();
/*      */ 
/* 2597 */       Scene.this.dndGesture.processRecognized(localEventTarget, localDragEvent);
/*      */ 
/* 2599 */       Scene.this.dndGesture = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DropTargetListener
/*      */     implements TKDropTargetListener
/*      */   {
/*      */     DropTargetListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public TransferMode dragEnter(Object paramObject)
/*      */     {
/* 2520 */       if (Scene.this.dndGesture == null) {
/* 2521 */         Scene.this.dndGesture = new Scene.DnDGesture(Scene.this);
/*      */       }
/* 2523 */       return Scene.this.dndGesture.processTargetEnterOver(Toolkit.getToolkit().convertDropTargetEventToFX(paramObject, Scene.this.dndGesture.dragboard));
/*      */     }
/*      */ 
/*      */     public TransferMode dragOver(Object paramObject)
/*      */     {
/* 2530 */       if (Scene.this.dndGesture == null) {
/* 2531 */         System.out.println("GOT A dragOver when dndGesture is null!");
/* 2532 */         return null;
/*      */       }
/* 2534 */       return Scene.this.dndGesture.processTargetEnterOver(Toolkit.getToolkit().convertDropTargetEventToFX(paramObject, Scene.this.dndGesture.dragboard));
/*      */     }
/*      */ 
/*      */     public void dropActionChanged(Object paramObject)
/*      */     {
/* 2542 */       if (Scene.this.dndGesture == null)
/* 2543 */         System.out.println("GOT A dropActionChanged when dndGesture is null!");
/*      */       else
/* 2545 */         Scene.this.dndGesture.processTargetActionChanged(Toolkit.getToolkit().convertDropTargetEventToFX(paramObject, Scene.this.dndGesture.dragboard));
/*      */     }
/*      */ 
/*      */     public void dragExit(Object paramObject)
/*      */     {
/* 2554 */       if (Scene.this.dndGesture == null) {
/* 2555 */         System.out.println("GOT A dragExit when dndGesture is null!");
/*      */       } else {
/* 2557 */         Scene.this.dndGesture.processTargetExit(Toolkit.getToolkit().convertDropTargetEventToFX(paramObject, Scene.this.dndGesture.dragboard));
/*      */ 
/* 2561 */         if (Scene.this.dndGesture.source == null)
/* 2562 */           Scene.this.dndGesture = null;
/*      */       }
/*      */     }
/*      */ 
/*      */     public TransferMode drop(Object paramObject)
/*      */     {
/* 2570 */       if (Scene.this.dndGesture == null) {
/* 2571 */         System.out.println("GOT A drop when dndGesture is null!");
/* 2572 */         return null;
/*      */       }
/* 2574 */       TransferMode localTransferMode = Scene.this.dndGesture.processTargetDrop(Toolkit.getToolkit().convertDropTargetEventToFX(paramObject, Scene.this.dndGesture.dragboard));
/*      */ 
/* 2578 */       if (Scene.this.dndGesture.source == null) {
/* 2579 */         Scene.this.dndGesture = null;
/*      */       }
/*      */ 
/* 2582 */       return localTransferMode;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class ScenePeerPaintListener
/*      */     implements TKScenePaintListener
/*      */   {
/*      */     private ScenePeerPaintListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void frameRendered()
/*      */     {
/* 2499 */       synchronized (Scene.trackerMonitor) {
/* 2500 */         if (Scene.this.tracker != null)
/* 2501 */           Scene.this.tracker.frameRendered();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   class ScenePeerListener
/*      */     implements TKSceneListener
/*      */   {
/*      */     ScenePeerListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void changedLocation(float paramFloat1, float paramFloat2)
/*      */     {
/* 2253 */       if (paramFloat1 != Scene.this.getX()) Scene.this.setX(paramFloat1);
/* 2254 */       if (paramFloat2 != Scene.this.getY()) Scene.this.setY(paramFloat2);
/*      */     }
/*      */ 
/*      */     public void changedSize(float paramFloat1, float paramFloat2)
/*      */     {
/* 2259 */       if (paramFloat1 != Scene.this.getWidth()) Scene.this.setWidth(paramFloat1);
/* 2260 */       if (paramFloat2 != Scene.this.getHeight()) Scene.this.setHeight(paramFloat2);
/*      */     }
/*      */ 
/*      */     public void mouseEvent(Object paramObject)
/*      */     {
/* 2265 */       Scene.this.impl_processMouseEvent(Toolkit.getToolkit().convertMouseEventToFX(paramObject));
/*      */     }
/*      */ 
/*      */     public void keyEvent(Object paramObject)
/*      */     {
/* 2270 */       Scene.this.impl_processKeyEvent(Toolkit.getToolkit().convertKeyEventToFX(paramObject));
/*      */     }
/*      */ 
/*      */     public void inputMethodEvent(Object paramObject)
/*      */     {
/* 2275 */       Scene.this.processInputMethodEvent(Toolkit.getToolkit().convertInputMethodEventToFX(paramObject));
/*      */     }
/*      */ 
/*      */     public void menuEvent(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean)
/*      */     {
/* 2280 */       Scene.this.processMenuEvent(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramBoolean);
/*      */     }
/*      */ 
/*      */     public void scrollEvent(EventType<ScrollEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*      */     {
/* 2297 */       ScrollEvent.HorizontalTextScrollUnits localHorizontalTextScrollUnits = paramInt2 > 0 ? ScrollEvent.HorizontalTextScrollUnits.CHARACTERS : ScrollEvent.HorizontalTextScrollUnits.NONE;
/*      */ 
/* 2301 */       double d1 = paramInt2 < 0 ? 0.0D : paramInt2 * paramDouble1;
/*      */ 
/* 2303 */       ScrollEvent.VerticalTextScrollUnits localVerticalTextScrollUnits = paramInt3 < 0 ? ScrollEvent.VerticalTextScrollUnits.PAGES : paramInt3 > 0 ? ScrollEvent.VerticalTextScrollUnits.LINES : ScrollEvent.VerticalTextScrollUnits.NONE;
/*      */ 
/* 2309 */       double d2 = paramInt3 < 0 ? paramDouble2 : paramInt3 * paramDouble2;
/*      */ 
/* 2311 */       paramDouble5 = (paramInt4 > 0) && (paramInt2 >= 0) ? Math.round(paramDouble5 * paramInt2 / paramInt4) : paramDouble5;
/*      */ 
/* 2315 */       paramDouble6 = (paramInt5 > 0) && (paramInt3 >= 0) ? Math.round(paramDouble6 * paramInt3 / paramInt5) : paramDouble6;
/*      */ 
/* 2319 */       if (paramEventType == ScrollEvent.SCROLL_FINISHED) {
/* 2320 */         paramDouble7 = Scene.this.scrollGesture.sceneCoords.getX();
/* 2321 */         paramDouble8 = Scene.this.scrollGesture.sceneCoords.getY();
/* 2322 */         paramDouble9 = Scene.this.scrollGesture.screenCoords.getX();
/* 2323 */         paramDouble10 = Scene.this.scrollGesture.screenCoords.getY();
/* 2324 */       } else if ((Double.isNaN(paramDouble7)) || (Double.isNaN(paramDouble8)) || (Double.isNaN(paramDouble9)) || (Double.isNaN(paramDouble10)))
/*      */       {
/* 2326 */         if ((Scene.this.cursorScenePos == null) || (Scene.this.cursorScreenPos == null)) {
/* 2327 */           return;
/*      */         }
/* 2329 */         paramDouble7 = Scene.this.cursorScenePos.getX();
/* 2330 */         paramDouble8 = Scene.this.cursorScenePos.getY();
/* 2331 */         paramDouble9 = Scene.this.cursorScreenPos.getX();
/* 2332 */         paramDouble10 = Scene.this.cursorScreenPos.getY();
/*      */       }
/*      */ 
/* 2335 */       Scene.this.processGestureEvent(ScrollEvent.impl_scrollEvent(paramEventType, paramDouble1 * paramDouble5, paramDouble2 * paramDouble6, paramDouble3 * paramDouble5, paramDouble4 * paramDouble6, localHorizontalTextScrollUnits, d1, localVerticalTextScrollUnits, d2, paramInt1, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6), Scene.this.scrollGesture);
/*      */     }
/*      */ 
/*      */     public void zoomEvent(EventType<ZoomEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*      */     {
/* 2356 */       if (paramEventType == ZoomEvent.ZOOM_FINISHED) {
/* 2357 */         paramDouble3 = Scene.this.zoomGesture.sceneCoords.getX();
/* 2358 */         paramDouble4 = Scene.this.zoomGesture.sceneCoords.getY();
/* 2359 */         paramDouble5 = Scene.this.zoomGesture.screenCoords.getX();
/* 2360 */         paramDouble6 = Scene.this.zoomGesture.screenCoords.getY();
/* 2361 */       } else if ((Double.isNaN(paramDouble3)) || (Double.isNaN(paramDouble4)) || (Double.isNaN(paramDouble5)) || (Double.isNaN(paramDouble6)))
/*      */       {
/* 2363 */         if ((Scene.this.cursorScenePos == null) || (Scene.this.cursorScreenPos == null)) {
/* 2364 */           return;
/*      */         }
/* 2366 */         paramDouble3 = Scene.this.cursorScenePos.getX();
/* 2367 */         paramDouble4 = Scene.this.cursorScenePos.getY();
/* 2368 */         paramDouble5 = Scene.this.cursorScreenPos.getX();
/* 2369 */         paramDouble6 = Scene.this.cursorScreenPos.getY();
/*      */       }
/*      */ 
/* 2372 */       Scene.this.processGestureEvent(ZoomEvent.impl_zoomEvent(paramEventType, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6), Scene.this.zoomGesture);
/*      */     }
/*      */ 
/*      */     public void rotateEvent(EventType<RotateEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*      */     {
/* 2388 */       if (paramEventType == RotateEvent.ROTATION_FINISHED) {
/* 2389 */         paramDouble3 = Scene.this.rotateGesture.sceneCoords.getX();
/* 2390 */         paramDouble4 = Scene.this.rotateGesture.sceneCoords.getY();
/* 2391 */         paramDouble5 = Scene.this.rotateGesture.screenCoords.getX();
/* 2392 */         paramDouble6 = Scene.this.rotateGesture.screenCoords.getY();
/* 2393 */       } else if ((Double.isNaN(paramDouble3)) || (Double.isNaN(paramDouble4)) || (Double.isNaN(paramDouble5)) || (Double.isNaN(paramDouble6)))
/*      */       {
/* 2395 */         if ((Scene.this.cursorScenePos == null) || (Scene.this.cursorScreenPos == null)) {
/* 2396 */           return;
/*      */         }
/* 2398 */         paramDouble3 = Scene.this.cursorScenePos.getX();
/* 2399 */         paramDouble4 = Scene.this.cursorScenePos.getY();
/* 2400 */         paramDouble5 = Scene.this.cursorScreenPos.getX();
/* 2401 */         paramDouble6 = Scene.this.cursorScreenPos.getY();
/*      */       }
/*      */ 
/* 2404 */       Scene.this.processGestureEvent(RotateEvent.impl_rotateEvent(paramEventType, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6), Scene.this.rotateGesture);
/*      */     }
/*      */ 
/*      */     public void swipeEvent(EventType<SwipeEvent> paramEventType, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
/*      */     {
/* 2419 */       if ((Double.isNaN(paramDouble1)) || (Double.isNaN(paramDouble2)) || (Double.isNaN(paramDouble3)) || (Double.isNaN(paramDouble4)))
/*      */       {
/* 2421 */         if ((Scene.this.cursorScenePos == null) || (Scene.this.cursorScreenPos == null)) {
/* 2422 */           return;
/*      */         }
/* 2424 */         paramDouble1 = Scene.this.cursorScenePos.getX();
/* 2425 */         paramDouble2 = Scene.this.cursorScenePos.getY();
/* 2426 */         paramDouble3 = Scene.this.cursorScreenPos.getX();
/* 2427 */         paramDouble4 = Scene.this.cursorScreenPos.getY();
/*      */       }
/*      */ 
/* 2430 */       Scene.this.processGestureEvent(SwipeEvent.impl_swipeEvent(paramEventType, paramInt, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5), Scene.this.swipeGesture);
/*      */     }
/*      */ 
/*      */     public void touchEventBegin(long paramLong, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
/*      */     {
/* 2442 */       Scene.this.nextTouchEvent = TouchEvent.impl_touchEvent(TouchEvent.ANY, null, null, 0, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5);
/*      */ 
/* 2445 */       Scene.this.nextTouchEvent.impl_setDirect(paramBoolean1);
/* 2446 */       if ((Scene.this.touchPoints == null) || (Scene.this.touchPoints.length != paramInt)) {
/* 2447 */         Scene.this.touchPoints = new TouchPoint[paramInt];
/*      */       }
/* 2449 */       Scene.this.touchPointIndex = 0;
/*      */     }
/*      */ 
/*      */     public void touchEventNext(TouchPoint.State paramState, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     {
/* 2457 */       Scene.access$5608(Scene.this);
/* 2458 */       int i = paramState == TouchPoint.State.PRESSED ? Scene.this.touchMap.add(paramLong) : Scene.this.touchMap.get(paramLong);
/*      */ 
/* 2460 */       if (paramState == TouchPoint.State.RELEASED) {
/* 2461 */         Scene.this.touchMap.remove(paramLong);
/*      */       }
/* 2463 */       int j = Scene.this.touchMap.getOrder(i);
/*      */ 
/* 2465 */       if (!Scene.this.nextTouchEvent.impl_isDirect()) {
/* 2466 */         j = Scene.this.touchPointIndex - 1;
/*      */       }
/*      */ 
/* 2469 */       if (j >= Scene.this.touchPoints.length) {
/* 2470 */         throw new RuntimeException("Too many touch points reported");
/*      */       }
/*      */ 
/* 2473 */       Scene.this.touchPoints[j] = TouchPoint.impl_touchPoint(i, paramState, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */ 
/*      */     public void touchEventEnd()
/*      */     {
/* 2479 */       if (Scene.this.touchPointIndex != Scene.this.touchPoints.length) {
/* 2480 */         throw new RuntimeException("Wrong number of touch points reported");
/*      */       }
/*      */ 
/* 2484 */       if (Scene.this.nextTouchEvent.impl_isDirect()) {
/* 2485 */         Scene.this.processTouchEvent(Scene.this.nextTouchEvent, Scene.this.touchPoints);
/*      */       }
/*      */ 
/* 2488 */       if (Scene.this.touchMap.cleanup())
/*      */       {
/* 2490 */         Scene.this.touchEventSetId = 0;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   class ScenePulseListener
/*      */     implements TKPulseListener
/*      */   {
/* 2082 */     private boolean firstPulse = true;
/*      */ 
/*      */     ScenePulseListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     private void synchronizeSceneNodes()
/*      */     {
/* 2090 */       Toolkit.getToolkit().checkFxUserThread();
/*      */ 
/* 2092 */       Scene.access$2902(true);
/*      */ 
/* 2097 */       if (Scene.this.dirtyNodes == null)
/*      */       {
/* 2099 */         syncAll(Scene.this.getRoot());
/* 2100 */         Scene.this.dirtyNodes = new Node[30];
/*      */       }
/*      */       else
/*      */       {
/* 2105 */         for (int i = 0; i < Scene.this.dirtyNodesSize; i++) {
/* 2106 */           Node localNode = Scene.this.dirtyNodes[i];
/* 2107 */           Scene.this.dirtyNodes[i] = null;
/* 2108 */           if (localNode.getScene() == Scene.this) {
/* 2109 */             localNode.impl_syncPGNode();
/*      */           }
/*      */         }
/* 2112 */         Scene.this.dirtyNodesSize = 0;
/*      */       }
/*      */ 
/* 2115 */       Scene.access$2902(false);
/*      */     }
/*      */ 
/*      */     private int syncAll(Node paramNode)
/*      */     {
/* 2123 */       paramNode.impl_syncPGNode();
/* 2124 */       int i = 1;
/* 2125 */       if ((paramNode instanceof Parent)) {
/* 2126 */         Parent localParent = (Parent)paramNode;
/* 2127 */         int j = localParent.getChildren().size();
/*      */ 
/* 2129 */         for (int k = 0; k < j; k++) {
/* 2130 */           Node localNode = (Node)localParent.getChildren().get(k);
/* 2131 */           if (localNode != null) {
/* 2132 */             i += syncAll(localNode);
/*      */           }
/*      */         }
/*      */       }
/* 2136 */       if (paramNode.getClip() != null) {
/* 2137 */         i += syncAll(paramNode.getClip());
/*      */       }
/*      */ 
/* 2140 */       return i;
/*      */     }
/*      */ 
/*      */     private void synchronizeSceneProperties() {
/* 2144 */       Scene.access$2902(true);
/* 2145 */       if (Scene.this.isDirty(Scene.DirtyBits.ROOT_DIRTY)) {
/* 2146 */         Scene.this.impl_peer.setRoot(Scene.this.getRoot().impl_getPGNode());
/*      */       }
/*      */ 
/* 2149 */       if (Scene.this.isDirty(Scene.DirtyBits.FILL_DIRTY)) {
/* 2150 */         Toolkit localToolkit = Toolkit.getToolkit();
/* 2151 */         Scene.this.impl_peer.setFillPaint(Scene.this.getFill() == null ? null : localToolkit.getPaint(Scene.this.getFill()));
/*      */       }
/*      */ 
/* 2155 */       if (Scene.this.isDirty(Scene.DirtyBits.CAMERA_DIRTY)) {
/* 2156 */         if (Scene.this.getCamera() != null) {
/* 2157 */           Scene.this.getCamera().update();
/* 2158 */           Scene.this.impl_peer.setCamera(Scene.this.getCamera().getPlatformCamera());
/* 2159 */           Scene.this.pickingCamera = Scene.this.getCamera();
/*      */         } else {
/* 2161 */           Scene.this.impl_peer.setCamera(null);
/* 2162 */           Scene.this.pickingCamera = null;
/*      */         }
/*      */       }
/*      */ 
/* 2166 */       Scene.this.clearDirty();
/* 2167 */       Scene.access$2902(false);
/*      */     }
/*      */ 
/*      */     private void focusCleanup()
/*      */     {
/* 2178 */       if (Scene.this.isFocusDirty()) {
/* 2179 */         Node localNode = Scene.this.getFocusOwner();
/* 2180 */         if (localNode == null) {
/* 2181 */           Scene.this.focusInitial();
/* 2182 */         } else if (localNode.getScene() != Scene.this) {
/* 2183 */           Scene.this.requestFocus(null);
/* 2184 */           Scene.this.focusInitial();
/* 2185 */         } else if (!localNode.isCanReceiveFocus()) {
/* 2186 */           Scene.this.requestFocus(null);
/* 2187 */           Scene.this.focusIneligible(localNode);
/*      */         }
/* 2189 */         Scene.this.setFocusDirty(false);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void pulse()
/*      */     {
/* 2195 */       if (Scene.this.tracker != null) {
/* 2196 */         Scene.this.tracker.pulse();
/*      */       }
/* 2198 */       if (this.firstPulse) {
/* 2199 */         PerformanceTracker.logEvent("Scene - first repaint");
/*      */       }
/*      */ 
/* 2202 */       Scene.this.doCSSPass();
/* 2203 */       Scene.this.doLayoutPass();
/*      */ 
/* 2205 */       int i = (Scene.this.dirtyNodes == null) || (Scene.this.dirtyNodesSize != 0) || (!Scene.this.isDirtyEmpty()) ? 1 : 0;
/* 2206 */       if (i != 0)
/*      */       {
/* 2208 */         synchronizeSceneProperties();
/*      */ 
/* 2210 */         synchronizeSceneNodes();
/* 2211 */         Scene.this.mouseHandler.pulse();
/*      */ 
/* 2213 */         if (Scene.this.impl_peer != null) {
/* 2214 */           Scene.this.impl_peer.markDirty();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2219 */       Scene.this.mouseHandler.updateCursorFrame();
/*      */ 
/* 2221 */       focusCleanup();
/*      */ 
/* 2223 */       if (this.firstPulse) {
/* 2224 */         if (PerformanceTracker.isLoggingEnabled()) {
/* 2225 */           PerformanceTracker.logEvent("Scene - first repaint - layout complete");
/* 2226 */           AccessController.doPrivileged(new PrivilegedAction() {
/*      */             public Object run() {
/* 2228 */               if (System.getProperty("sun.perflog.fx.firstpaintflush") != null) {
/* 2229 */                 PerformanceTracker.outputLog();
/*      */               }
/* 2231 */               return null;
/*      */             }
/*      */           });
/*      */         }
/* 2235 */         this.firstPulse = false;
/*      */       }
/*      */ 
/* 2238 */       if (Scene.this.testPulseListener != null)
/* 2239 */         Scene.this.testPulseListener.run();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static enum DirtyBits
/*      */   {
/* 2059 */     FILL_DIRTY, 
/* 2060 */     ROOT_DIRTY, 
/* 2061 */     CAMERA_DIRTY;
/*      */ 
/*      */     private int mask;
/*      */ 
/*      */     private DirtyBits() {
/* 2066 */       this.mask = (1 << ordinal());
/*      */     }
/*      */     public final int getMask() {
/* 2069 */       return this.mask;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class TouchGesture
/*      */   {
/*      */     EventTarget target;
/*      */     Point2D sceneCoords;
/*      */     Point2D screenCoords;
/*      */     boolean finished;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.Scene
 * JD-Core Version:    0.6.2
 */