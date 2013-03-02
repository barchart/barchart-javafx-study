/*      */ package javafx.scene;
/*      */ 
/*      */ import com.sun.javafx.Logging;
/*      */ import com.sun.javafx.TempState;
/*      */ import com.sun.javafx.Utils;
/*      */ import com.sun.javafx.collections.TrackableObservableList;
/*      */ import com.sun.javafx.collections.VetoableObservableList;
/*      */ import com.sun.javafx.css.Selector;
/*      */ import com.sun.javafx.css.StyleManager;
/*      */ import com.sun.javafx.geom.BaseBounds;
/*      */ import com.sun.javafx.geom.PickRay;
/*      */ import com.sun.javafx.geom.Point2D;
/*      */ import com.sun.javafx.geom.RectBounds;
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*      */ import com.sun.javafx.jmx.MXNodeAlgorithm;
/*      */ import com.sun.javafx.jmx.MXNodeAlgorithmContext;
/*      */ import com.sun.javafx.logging.PlatformLogger;
/*      */ import com.sun.javafx.scene.CSSFlags;
/*      */ import com.sun.javafx.scene.DirtyBits;
/*      */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*      */ import com.sun.javafx.sg.PGGroup;
/*      */ import com.sun.javafx.sg.PGNode;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*      */ import javafx.beans.property.SimpleObjectProperty;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.geometry.Bounds;
/*      */ 
/*      */ public abstract class Parent extends Node
/*      */ {
/*      */   private static final int DIRTY_CHILDREN_THRESHOLD = 10;
/*   88 */   private static final boolean warnOnAutoMove = PropertyHelper.getBooleanProperty("javafx.sg.warn");
/*      */   private static final int REMOVED_CHILDREN_THRESHOLD = 20;
/*   99 */   private boolean removedChildrenExceedsThreshold = false;
/*      */ 
/*  158 */   private final Set<Node> childSet = new HashSet();
/*      */ 
/*  161 */   private int startIdx = 0;
/*      */ 
/*  164 */   private int pgChildrenSize = 0;
/*      */ 
/*  205 */   private boolean ignoreChildrenTrigger = false;
/*      */ 
/*  211 */   private boolean childrenTriggerPermutation = false;
/*      */   private List<Node> removed;
/*  231 */   private final ObservableList<Node> children = new VetoableObservableList()
/*      */   {
/*      */     private boolean geomChanged;
/*      */     private boolean childrenModified;
/*      */ 
/*      */     protected void onProposedChange(List<Node> paramAnonymousList, int[] paramAnonymousArrayOfInt)
/*      */     {
/*  240 */       if (Parent.this.ignoreChildrenTrigger) {
/*  241 */         return;
/*      */       }
/*  243 */       if (Parent.this.getScene() != null)
/*      */       {
/*  245 */         Toolkit.getToolkit().checkFxUserThread();
/*      */       }
/*  247 */       this.geomChanged = false;
/*      */ 
/*  249 */       long l = Parent.this.children.size() + paramAnonymousList.size();
/*  250 */       int i = 0;
/*  251 */       for (int j = 0; j < paramAnonymousArrayOfInt.length; j += 2) {
/*  252 */         i += paramAnonymousArrayOfInt[(j + 1)] - paramAnonymousArrayOfInt[j];
/*      */       }
/*  254 */       l -= i;
/*      */ 
/*  258 */       if (Parent.this.childrenTriggerPermutation) {
/*  259 */         this.childrenModified = false;
/*  260 */         return;
/*      */       }
/*      */ 
/*  269 */       this.childrenModified = true;
/*  270 */       if (l == Parent.this.childSet.size()) {
/*  271 */         this.childrenModified = false;
/*  272 */         for (j = paramAnonymousList.size() - 1; j >= 0; j--) {
/*  273 */           Node localNode1 = (Node)paramAnonymousList.get(j);
/*  274 */           if (!Parent.this.childSet.contains(localNode1)) {
/*  275 */             this.childrenModified = true;
/*  276 */             break;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  298 */       for (j = 0; j < paramAnonymousArrayOfInt.length; j += 2) {
/*  299 */         for (int m = paramAnonymousArrayOfInt[j]; m < paramAnonymousArrayOfInt[(j + 1)]; m++) {
/*  300 */           Parent.this.childSet.remove(Parent.this.children.get(m));
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/*  305 */         Parent.this.childSet.addAll(paramAnonymousList);
/*  306 */         if (Parent.this.childSet.size() != l) {
/*  307 */           throw new IllegalArgumentException(constructExceptionMessage("duplicate children added", null));
/*      */         }
/*      */ 
/*  312 */         if (this.childrenModified) {
/*  313 */           for (j = paramAnonymousList.size() - 1; j >= 0; j--) {
/*  314 */             Node localNode2 = (Node)paramAnonymousList.get(j);
/*  315 */             if (localNode2 == null) {
/*  316 */               throw new NullPointerException(constructExceptionMessage("child node is null", null));
/*      */             }
/*      */ 
/*  320 */             if (localNode2.getClipParent() != null) {
/*  321 */               throw new IllegalArgumentException(constructExceptionMessage("node already used as a clip", localNode2));
/*      */             }
/*      */ 
/*  325 */             if (Parent.this.wouldCreateCycle(Parent.this, localNode2)) {
/*  326 */               throw new IllegalArgumentException(constructExceptionMessage("cycle detected", localNode2));
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (RuntimeException localRuntimeException)
/*      */       {
/*  334 */         Parent.this.childSet.clear();
/*  335 */         Parent.this.childSet.addAll(Parent.this.children);
/*      */ 
/*  338 */         throw localRuntimeException;
/*      */       }
/*      */ 
/*  343 */       if (!this.childrenModified) {
/*  344 */         return;
/*      */       }
/*      */ 
/*  350 */       if (Parent.this.removed == null) {
/*  351 */         Parent.this.removed = new ArrayList();
/*      */       }
/*  353 */       if (Parent.this.removed.size() + i > 20)
/*      */       {
/*  355 */         Parent.this.removedChildrenExceedsThreshold = true;
/*      */       }
/*  357 */       for (int k = 0; k < paramAnonymousArrayOfInt.length; k += 2)
/*  358 */         for (int n = paramAnonymousArrayOfInt[k]; n < paramAnonymousArrayOfInt[(k + 1)]; n++) {
/*  359 */           Node localNode3 = (Node)Parent.this.children.get(n);
/*  360 */           if (Parent.this.dirtyChildren != null) {
/*  361 */             Parent.this.dirtyChildren.remove(localNode3);
/*      */           }
/*  363 */           if (localNode3 != null)
/*      */           {
/*  367 */             if ((!this.geomChanged) && (localNode3.isVisible())) {
/*  368 */               this.geomChanged = Parent.this.childRemoved(localNode3);
/*      */             }
/*  370 */             if (localNode3.getParent() == Parent.this) {
/*  371 */               localNode3.setParent(null);
/*  372 */               localNode3.setScene(null);
/*      */             }
/*  374 */             if (!Parent.this.removedChildrenExceedsThreshold)
/*  375 */               Parent.this.removed.add(localNode3);
/*      */           }
/*      */         }
/*      */     }
/*      */ 
/*      */     protected void onChanged(ListChangeListener.Change<Node> paramAnonymousChange)
/*      */     {
/*  384 */       if (this.childrenModified) {
/*  385 */         int i = 0;
/*      */         int j;
/*      */         int k;
/*      */         Object localObject;
/*  387 */         while (paramAnonymousChange.next()) {
/*  388 */           j = paramAnonymousChange.getFrom();
/*  389 */           k = paramAnonymousChange.getTo();
/*  390 */           for (int m = j; m < k; m++) {
/*  391 */             Node localNode1 = (Node)Parent.this.children.get(m);
/*  392 */             if ((localNode1 != null) && (localNode1.getParent() != null) && (localNode1.getParent() != Parent.this)) {
/*  393 */               if (Parent.warnOnAutoMove) {
/*  394 */                 System.err.println("WARNING added to a new parent without first removing it from its current");
/*  395 */                 System.err.println("    parent. It will be automatically removed from its current parent.");
/*  396 */                 System.err.println(new StringBuilder().append("    node=").append(localNode1).append(" oldparent= ").append(localNode1.getParent()).append(" newparent=").append(this).toString());
/*      */               }
/*  398 */               localNode1.getParent().children.remove(localNode1);
/*  399 */               if (localNode1.isManaged()) {
/*  400 */                 i = 1;
/*      */               }
/*  402 */               if (Parent.warnOnAutoMove) {
/*  403 */                 Thread.dumpStack();
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/*  408 */           localObject = paramAnonymousChange.getRemoved();
/*  409 */           int n = ((List)localObject).size();
/*      */           Node localNode2;
/*  410 */           for (int i1 = 0; i1 < n; i1++) {
/*  411 */             localNode2 = (Node)((List)localObject).get(i1);
/*  412 */             if ((localNode2 != null) && (localNode2.isManaged())) {
/*  413 */               i = 1;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  418 */           for (i1 = j; i1 < k; i1++) {
/*  419 */             localNode2 = (Node)Parent.this.children.get(i1);
/*  420 */             if (localNode2 != null) {
/*  421 */               if (localNode2.isManaged()) {
/*  422 */                 i = 1;
/*      */               }
/*  424 */               localNode2.setParent(Parent.this);
/*  425 */               localNode2.setScene(Parent.this.getScene());
/*  426 */               if (Parent.this.dirtyChildren != null) {
/*  427 */                 Parent.this.dirtyChildren.add(localNode2);
/*      */               }
/*  429 */               if ((!this.geomChanged) && (localNode2.isVisible())) {
/*  430 */                 this.geomChanged = Parent.this.childAdded(localNode2);
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  440 */         if ((Parent.this.dirtyChildren == null) && (Parent.this.children.size() > 10)) {
/*  441 */           Parent.this.dirtyChildren = new LinkedHashSet(2 * Parent.this.children.size());
/*      */ 
/*  444 */           if (this.geomChanged) {
/*  445 */             j = Parent.this.children.size();
/*  446 */             for (k = 0; k < j; k++) {
/*  447 */               localObject = (Node)Parent.this.children.get(k);
/*  448 */               if ((((Node)localObject).isVisible()) && (((Node)localObject).boundsChanged)) {
/*  449 */                 Parent.this.dirtyChildren.add(localObject);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  455 */         if (this.geomChanged) {
/*  456 */           Parent.this.impl_geomChanged();
/*      */         }
/*      */ 
/*  478 */         if (i != 0) {
/*  479 */           Parent.this.requestLayout();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  485 */       paramAnonymousChange.reset();
/*  486 */       paramAnonymousChange.next();
/*  487 */       if (Parent.this.startIdx > paramAnonymousChange.getFrom()) {
/*  488 */         Parent.this.startIdx = paramAnonymousChange.getFrom();
/*      */       }
/*      */ 
/*  491 */       Parent.this.impl_markDirty(DirtyBits.PARENT_CHILDREN);
/*      */     }
/*      */ 
/*      */     private String constructExceptionMessage(String paramAnonymousString, Node paramAnonymousNode)
/*      */     {
/*  496 */       StringBuilder localStringBuilder = new StringBuilder("Children: ");
/*  497 */       localStringBuilder.append(paramAnonymousString);
/*  498 */       localStringBuilder.append(": parent = ").append(Parent.this);
/*  499 */       if (paramAnonymousNode != null) {
/*  500 */         localStringBuilder.append(", node = ").append(paramAnonymousNode);
/*      */       }
/*      */ 
/*  503 */       return localStringBuilder.toString();
/*      */     }
/*  231 */   };
/*      */   private ObjectProperty<TraversalEngine> impl_traversalEngine;
/*      */   private ReadOnlyBooleanWrapper needsLayout;
/*  804 */   boolean performingLayout = false;
/*      */ 
/*  806 */   private boolean sizeCacheClear = true;
/*  807 */   private double prefWidthCache = -1.0D;
/*  808 */   private double prefHeightCache = -1.0D;
/*  809 */   private double minWidthCache = -1.0D;
/*  810 */   private double minHeightCache = -1.0D;
/*      */ 
/* 1074 */   private final ObservableList<String> stylesheets = new TrackableObservableList()
/*      */   {
/*      */     protected void onChanged(ListChangeListener.Change<String> paramAnonymousChange) {
/* 1077 */       StyleManager.getInstance().parentStylesheetsChanged(Parent.this, paramAnonymousChange);
/*      */ 
/* 1080 */       while (paramAnonymousChange.next()) {
/* 1081 */         if (paramAnonymousChange.wasRemoved()) {
/* 1082 */           Parent.this.impl_cssResetInitialValues();
/*      */         }
/*      */       }
/* 1085 */       Parent.this.impl_reapplyCSS();
/*      */     }
/* 1074 */   };
/*      */ 
/* 1227 */   private BaseBounds tmp = new RectBounds();
/*      */ 
/* 1232 */   private BaseBounds cachedBounds = new RectBounds();
/*      */ 
/* 1241 */   private boolean cachedBoundsInvalid = true;
/*      */ 
/* 1251 */   private LinkedHashSet<Node> dirtyChildren = null;
/*      */   private Node top;
/*      */   private Node left;
/*      */   private Node bottom;
/*      */   private Node right;
/*      */   private Node near;
/*      */   private Node far;
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_updatePG()
/*      */   {
/*  107 */     super.impl_updatePG();
/*      */     Object localObject;
/*  109 */     if (Utils.assertionEnabled()) {
/*  110 */       localObject = getPGGroup().getChildren();
/*  111 */       if (((List)localObject).size() != this.pgChildrenSize) {
/*  112 */         System.err.println("*** pgnodes.size() [" + ((List)localObject).size() + "] != pgChildrenSize [" + this.pgChildrenSize + "]");
/*      */       }
/*      */     }
/*      */ 
/*  116 */     if (impl_isDirty(DirtyBits.PARENT_CHILDREN)) {
/*  117 */       localObject = getPGGroup();
/*      */ 
/*  123 */       ((PGGroup)localObject).clearFrom(this.startIdx);
/*  124 */       for (int i = this.startIdx; i < this.children.size(); i++) {
/*  125 */         Node localNode = (Node)this.children.get(i);
/*  126 */         if (localNode != null) {
/*  127 */           ((PGGroup)localObject).add(i, localNode.impl_getPGNode());
/*      */         }
/*      */       }
/*  130 */       if (this.removedChildrenExceedsThreshold) {
/*  131 */         ((PGGroup)localObject).markDirty();
/*  132 */         this.removedChildrenExceedsThreshold = false;
/*      */       }
/*  134 */       else if ((this.removed != null) && (!this.removed.isEmpty())) {
/*  135 */         for (i = 0; i < this.removed.size(); i++) {
/*  136 */           ((PGGroup)localObject).addToRemoved(((Node)this.removed.get(i)).impl_getPGNode());
/*      */         }
/*  138 */         this.removed.clear();
/*      */       }
/*      */ 
/*  141 */       this.pgChildrenSize = this.children.size();
/*  142 */       this.startIdx = this.pgChildrenSize;
/*      */     }
/*      */ 
/*  145 */     if (Utils.assertionEnabled()) validatePG();
/*      */   }
/*      */ 
/*      */   void validatePG()
/*      */   {
/*  167 */     int i = 0;
/*  168 */     List localList = getPGGroup().getChildren();
/*  169 */     if (localList.size() != this.children.size()) {
/*  170 */       System.err.println("*** pgnodes.size validatePG() [" + localList.size() + "] != children.size() [" + this.children.size() + "]");
/*  171 */       i = 1;
/*      */     } else {
/*  173 */       for (int j = 0; j < this.children.size(); j++) {
/*  174 */         Node localNode = (Node)this.children.get(j);
/*  175 */         if (localNode.getParent() != this) {
/*  176 */           System.err.println("*** this=" + this + " validatePG children[" + j + "].parent= " + localNode.getParent());
/*  177 */           i = 1;
/*      */         }
/*  179 */         if (localNode.impl_getPGNode() != localList.get(j)) {
/*  180 */           System.err.println("*** pgnodes[" + j + "] validatePG != children[" + j + "]");
/*  181 */           i = 1;
/*      */         }
/*      */       }
/*      */     }
/*  185 */     if (i != 0)
/*  186 */       throw new AssertionError("validation of PGGroup children failed");
/*      */   }
/*      */ 
/*      */   void printSeq(String paramString, List<Node> paramList)
/*      */   {
/*  192 */     String str = paramString;
/*  193 */     for (Node localNode : paramList) {
/*  194 */       str = str + localNode + " ";
/*      */     }
/*  196 */     System.out.println(str);
/*      */   }
/*      */ 
/*      */   protected ObservableList<Node> getChildren()
/*      */   {
/*  526 */     return this.children;
/*      */   }
/*      */ 
/*      */   public ObservableList<Node> getChildrenUnmodifiable()
/*      */   {
/*  537 */     return FXCollections.unmodifiableObservableList(this.children);
/*      */   }
/*      */ 
/*      */   protected <E extends Node> List<E> getManagedChildren()
/*      */   {
/*  547 */     ArrayList localArrayList = new ArrayList();
/*  548 */     for (Node localNode : getChildren()) {
/*  549 */       if ((localNode != null) && (localNode.isManaged())) {
/*  550 */         localArrayList.add(localNode);
/*      */       }
/*      */     }
/*  553 */     return localArrayList;
/*      */   }
/*      */ 
/*      */   final void impl_toFront(Node paramNode)
/*      */   {
/*  558 */     if ((Utils.assertionEnabled()) && 
/*  559 */       (!this.childSet.contains(paramNode))) {
/*  560 */       throw new AssertionError("specified node is not in the list of children");
/*      */     }
/*      */ 
/*  565 */     if (this.children.get(this.children.size() - 1) != paramNode) {
/*  566 */       this.childrenTriggerPermutation = true;
/*      */       try {
/*  568 */         this.children.remove(paramNode);
/*  569 */         this.children.add(paramNode);
/*      */       } finally {
/*  571 */         this.childrenTriggerPermutation = false;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   final void impl_toBack(Node paramNode)
/*      */   {
/*  578 */     if ((Utils.assertionEnabled()) && 
/*  579 */       (!this.childSet.contains(paramNode))) {
/*  580 */       throw new AssertionError("specified node is not in the list of children");
/*      */     }
/*      */ 
/*  585 */     if (this.children.get(0) != paramNode) {
/*  586 */       this.childrenTriggerPermutation = true;
/*      */       try {
/*  588 */         this.children.remove(paramNode);
/*  589 */         this.children.add(0, paramNode);
/*      */       } finally {
/*  591 */         this.childrenTriggerPermutation = false;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void computeDirtyScene(Scene paramScene) {
/*  597 */     for (Node localNode : getChildren()) {
/*  598 */       if (localNode != null) {
/*  599 */         localNode.setScene(getScene());
/*      */       }
/*      */     }
/*      */ 
/*  603 */     if ((isNeedsLayout()) && (paramScene != null)) {
/*  604 */       paramScene.removeFromDirtyLayoutList(this);
/*      */     }
/*  606 */     if ((getScene() != null) && (isNeedsLayout()) && (isLayoutRoot()))
/*  607 */       getScene().addToDirtyLayoutList(this);
/*      */   }
/*      */ 
/*      */   void sceneChanged(Scene paramScene)
/*      */   {
/*  612 */     computeDirtyScene(paramScene);
/*      */   }
/*      */ 
/*      */   Node getFirstChild()
/*      */   {
/*  618 */     if (this.children.size() > 0) {
/*  619 */       return (Node)this.children.get(0);
/*      */     }
/*  621 */     return null;
/*      */   }
/*      */ 
/*      */   Node getLastChild()
/*      */   {
/*  626 */     if (!this.children.isEmpty()) {
/*  627 */       return (Node)this.children.get(this.children.size() - 1);
/*      */     }
/*  629 */     return null;
/*      */   }
/*      */ 
/*      */   Node getNextChild(Node paramNode)
/*      */   {
/*  634 */     Object localObject = null;
/*  635 */     for (Node localNode : this.children) {
/*  636 */       if (paramNode == localObject) {
/*  637 */         return localNode;
/*      */       }
/*  639 */       localObject = localNode;
/*      */     }
/*  641 */     return null;
/*      */   }
/*      */ 
/*      */   Node getPreviousChild(Node paramNode) {
/*  645 */     Object localObject = null;
/*  646 */     for (int i = this.children.size() - 1; i >= 0; i--) {
/*  647 */       Node localNode = (Node)this.children.get(i);
/*  648 */       if (paramNode == localObject) {
/*  649 */         return localNode;
/*      */       }
/*  651 */       localObject = localNode;
/*      */     }
/*  653 */     return null;
/*      */   }
/*      */ 
/*      */   void setDerivedDepthTest(boolean paramBoolean)
/*      */   {
/*  658 */     super.setDerivedDepthTest(paramBoolean);
/*      */ 
/*  660 */     ObservableList localObservableList = getChildren();
/*  661 */     for (int i = 0; i < localObservableList.size(); i++) {
/*  662 */       Node localNode = (Node)localObservableList.get(i);
/*  663 */       localNode.computeDerivedDepthTest();
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected Node impl_pickNodeLocal(double paramDouble1, double paramDouble2)
/*      */   {
/*  673 */     if (containsBounds(paramDouble1, paramDouble2)) {
/*  674 */       for (int i = this.children.size() - 1; i >= 0; i--) {
/*  675 */         Node localNode = ((Node)this.children.get(i)).impl_pickNode(paramDouble1, paramDouble2);
/*  676 */         if (localNode != null) {
/*  677 */           return localNode;
/*      */         }
/*      */       }
/*      */     }
/*  681 */     return null;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected Node impl_pickNodeLocal(PickRay paramPickRay)
/*      */   {
/*  690 */     for (int i = this.children.size() - 1; i >= 0; i--) {
/*  691 */       Node localNode = ((Node)this.children.get(i)).impl_pickNode(paramPickRay);
/*      */ 
/*  693 */       if (localNode != null) {
/*  694 */         return localNode;
/*      */       }
/*      */     }
/*  697 */     return null;
/*      */   }
/*      */ 
/*      */   boolean isConnected() {
/*  701 */     return (super.isConnected()) || (isSceneRoot());
/*      */   }
/*      */ 
/*      */   public Node lookup(String paramString) {
/*  705 */     Node localNode = super.lookup(paramString);
/*  706 */     if (localNode == null) {
/*  707 */       int i = this.children.size();
/*  708 */       for (int j = 0; j < i; j++) {
/*  709 */         localNode = ((Node)this.children.get(j)).lookup(paramString);
/*  710 */         if (localNode != null) return localNode;
/*      */       }
/*      */     }
/*  713 */     return localNode;
/*      */   }
/*      */ 
/*      */   List<Node> lookupAll(Selector paramSelector, List<Node> paramList)
/*      */   {
/*  721 */     paramList = super.lookupAll(paramSelector, paramList);
/*  722 */     int i = this.children.size();
/*  723 */     for (int j = 0; j < i; j++) {
/*  724 */       paramList = ((Node)this.children.get(j)).lookupAll(paramSelector, paramList);
/*      */     }
/*  726 */     return paramList;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public final void setImpl_traversalEngine(TraversalEngine paramTraversalEngine)
/*      */   {
/*  740 */     impl_traversalEngineProperty().set(paramTraversalEngine);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public final TraversalEngine getImpl_traversalEngine()
/*      */   {
/*  749 */     return this.impl_traversalEngine == null ? null : (TraversalEngine)this.impl_traversalEngine.get();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public final ObjectProperty<TraversalEngine> impl_traversalEngineProperty()
/*      */   {
/*  758 */     if (this.impl_traversalEngine == null) {
/*  759 */       this.impl_traversalEngine = new SimpleObjectProperty(this, "impl_traversalEngine");
/*      */     }
/*      */ 
/*  763 */     return this.impl_traversalEngine;
/*      */   }
/*      */ 
/*      */   protected final void setNeedsLayout(boolean paramBoolean)
/*      */   {
/*  780 */     needsLayoutPropertyImpl().set(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final boolean isNeedsLayout() {
/*  784 */     return this.needsLayout == null ? true : this.needsLayout.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyBooleanProperty needsLayoutProperty() {
/*  788 */     return needsLayoutPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyBooleanWrapper needsLayoutPropertyImpl() {
/*  792 */     if (this.needsLayout == null) {
/*  793 */       this.needsLayout = new ReadOnlyBooleanWrapper(this, "needsLayout", true);
/*      */     }
/*  795 */     return this.needsLayout;
/*      */   }
/*      */ 
/*      */   public void requestLayout()
/*      */   {
/*  822 */     if (!isNeedsLayout()) {
/*  823 */       this.prefWidthCache = -1.0D;
/*  824 */       this.prefHeightCache = -1.0D;
/*  825 */       this.minWidthCache = -1.0D;
/*  826 */       this.minHeightCache = -1.0D;
/*  827 */       PlatformLogger localPlatformLogger = Logging.getLayoutLogger();
/*  828 */       if (localPlatformLogger.isLoggable(400)) {
/*  829 */         localPlatformLogger.finer(toString());
/*      */       }
/*      */ 
/*  832 */       setNeedsLayout(true);
/*  833 */       if (isLayoutRoot()) {
/*  834 */         if (getScene() != null) {
/*  835 */           if (localPlatformLogger.isLoggable(400)) {
/*  836 */             localPlatformLogger.finer(toString() + " layoutRoot added to scene dirty layout list");
/*      */           }
/*  838 */           getScene().addToDirtyLayoutList(this);
/*      */         }
/*  840 */       } else if (getParent() != null)
/*  841 */         getParent().requestLayout();
/*      */     }
/*      */     else {
/*  844 */       clearSizeCache();
/*      */     }
/*      */   }
/*      */ 
/*      */   void clearSizeCache() {
/*  849 */     if (this.sizeCacheClear) {
/*  850 */       return;
/*      */     }
/*  852 */     this.sizeCacheClear = true;
/*  853 */     this.prefWidthCache = -1.0D;
/*  854 */     this.prefHeightCache = -1.0D;
/*  855 */     this.minWidthCache = -1.0D;
/*  856 */     this.minHeightCache = -1.0D;
/*  857 */     if ((!isLayoutRoot()) && 
/*  858 */       (getParent() != null))
/*  859 */       getParent().clearSizeCache();
/*      */   }
/*      */ 
/*      */   public double prefWidth(double paramDouble)
/*      */   {
/*  865 */     if (paramDouble == -1.0D) {
/*  866 */       if (this.prefWidthCache == -1.0D) {
/*  867 */         this.prefWidthCache = computePrefWidth(-1.0D);
/*  868 */         this.sizeCacheClear = false;
/*      */       }
/*  870 */       return this.prefWidthCache;
/*      */     }
/*  872 */     return computePrefWidth(paramDouble);
/*      */   }
/*      */ 
/*      */   public double prefHeight(double paramDouble)
/*      */   {
/*  877 */     if (paramDouble == -1.0D) {
/*  878 */       if (this.prefHeightCache == -1.0D) {
/*  879 */         this.prefHeightCache = computePrefHeight(-1.0D);
/*  880 */         this.sizeCacheClear = false;
/*      */       }
/*  882 */       return this.prefHeightCache;
/*      */     }
/*  884 */     return computePrefHeight(paramDouble);
/*      */   }
/*      */ 
/*      */   public double minWidth(double paramDouble)
/*      */   {
/*  889 */     if (paramDouble == -1.0D) {
/*  890 */       if (this.minWidthCache == -1.0D) {
/*  891 */         this.minWidthCache = computeMinWidth(-1.0D);
/*  892 */         this.sizeCacheClear = false;
/*      */       }
/*  894 */       return this.minWidthCache;
/*      */     }
/*  896 */     return computeMinWidth(paramDouble);
/*      */   }
/*      */ 
/*      */   public double minHeight(double paramDouble)
/*      */   {
/*  901 */     if (paramDouble == -1.0D) {
/*  902 */       if (this.minHeightCache == -1.0D) {
/*  903 */         this.minHeightCache = computeMinHeight(-1.0D);
/*  904 */         this.sizeCacheClear = false;
/*      */       }
/*  906 */       return this.minHeightCache;
/*      */     }
/*  908 */     return computeMinHeight(paramDouble);
/*      */   }
/*      */ 
/*      */   protected double computePrefWidth(double paramDouble)
/*      */   {
/*  924 */     double d1 = 0.0D;
/*  925 */     double d2 = 0.0D;
/*  926 */     for (int i = 0; i < getChildren().size(); i++) {
/*  927 */       Node localNode = (Node)getChildren().get(i);
/*  928 */       if (localNode.isManaged()) {
/*  929 */         double d3 = localNode.getLayoutBounds().getMinX() + localNode.getLayoutX();
/*  930 */         d1 = Math.min(d1, d3);
/*  931 */         d2 = Math.max(d2, d3 + localNode.prefWidth(-1.0D));
/*      */       }
/*      */     }
/*  934 */     return d2 - d1;
/*      */   }
/*      */ 
/*      */   protected double computePrefHeight(double paramDouble)
/*      */   {
/*  949 */     double d1 = 0.0D;
/*  950 */     double d2 = 0.0D;
/*  951 */     for (int i = 0; i < getChildren().size(); i++) {
/*  952 */       Node localNode = (Node)getChildren().get(i);
/*  953 */       if (localNode.isManaged()) {
/*  954 */         double d3 = localNode.getLayoutBounds().getMinY() + localNode.getLayoutY();
/*  955 */         d1 = Math.min(d1, d3);
/*  956 */         d2 = Math.max(d2, d3 + localNode.prefHeight(-1.0D));
/*      */       }
/*      */     }
/*  959 */     return d2 - d1;
/*      */   }
/*      */ 
/*      */   protected double computeMinWidth(double paramDouble)
/*      */   {
/*  971 */     return prefWidth(paramDouble);
/*      */   }
/*      */ 
/*      */   protected double computeMinHeight(double paramDouble)
/*      */   {
/*  984 */     return prefHeight(paramDouble);
/*      */   }
/*      */ 
/*      */   public double getBaselineOffset()
/*      */   {
/*  994 */     int i = this.children.size();
/*  995 */     for (int j = 0; j < i; j++) {
/*  996 */       Node localNode = (Node)this.children.get(j);
/*  997 */       if (localNode.isManaged()) {
/*  998 */         return localNode.getLayoutBounds().getMinY() + localNode.getLayoutY() + localNode.getBaselineOffset();
/*      */       }
/*      */     }
/* 1001 */     return super.getBaselineOffset();
/*      */   }
/*      */ 
/*      */   public final void layout()
/*      */   {
/* 1008 */     if (isNeedsLayout()) {
/* 1009 */       this.performingLayout = true;
/*      */ 
/* 1011 */       PlatformLogger localPlatformLogger = Logging.getLayoutLogger();
/* 1012 */       if (localPlatformLogger.isLoggable(500)) {
/* 1013 */         localPlatformLogger.fine(this + " size: " + getLayoutBounds().getWidth() + " x " + getLayoutBounds().getHeight());
/*      */       }
/*      */ 
/* 1018 */       layoutChildren();
/*      */ 
/* 1020 */       setNeedsLayout(false);
/*      */ 
/* 1023 */       ObservableList localObservableList = getChildren();
/* 1024 */       int i = localObservableList.size();
/* 1025 */       for (int j = 0; j < i; j++) {
/* 1026 */         Node localNode = (Node)localObservableList.get(j);
/* 1027 */         if ((localNode instanceof Parent)) {
/* 1028 */           ((Parent)localNode).layout();
/*      */         }
/*      */       }
/* 1031 */       this.performingLayout = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void layoutChildren()
/*      */   {
/* 1044 */     int i = this.children.size();
/* 1045 */     for (int j = 0; j < i; j++) {
/* 1046 */       Node localNode = (Node)this.children.get(j);
/* 1047 */       if ((localNode.isResizable()) && (localNode.isManaged()))
/* 1048 */         localNode.autosize();
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean isSceneRoot()
/*      */   {
/* 1054 */     return getScene().getRoot() == this;
/*      */   }
/*      */ 
/*      */   boolean isLayoutRoot() {
/* 1058 */     return (!isManaged()) || (isSceneRoot());
/*      */   }
/*      */ 
/*      */   public final ObservableList<String> getStylesheets()
/*      */   {
/* 1097 */     return this.stylesheets;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public List<String> impl_getAllParentStylesheets()
/*      */   {
/* 1114 */     Object localObject = null;
/* 1115 */     Parent localParent = getParent();
/* 1116 */     if (localParent != null)
/*      */     {
/* 1124 */       localObject = localParent.impl_getAllParentStylesheets();
/*      */     }
/*      */ 
/* 1127 */     if ((this.stylesheets != null) && (!this.stylesheets.isEmpty())) {
/* 1128 */       if (localObject == null) localObject = new ArrayList(this.stylesheets.size());
/* 1129 */       int i = 0; for (int j = this.stylesheets.size(); i < j; i++) {
/* 1130 */         ((List)localObject).add(this.stylesheets.get(i));
/*      */       }
/*      */     }
/* 1133 */     return localObject;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_processCSS(boolean paramBoolean)
/*      */   {
/* 1144 */     boolean bool = (paramBoolean) || (this.cssFlag == CSSFlags.REAPPLY);
/*      */ 
/* 1146 */     super.impl_processCSS(bool);
/*      */ 
/* 1148 */     ObservableList localObservableList = getChildren();
/* 1149 */     int i = localObservableList.size();
/* 1150 */     for (int j = 0; j < i; j++) {
/* 1151 */       Node localNode = (Node)localObservableList.get(j);
/* 1152 */       if (localNode != null)
/* 1153 */         localNode.impl_processCSS(bool);
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_cssResetInitialValues()
/*      */   {
/* 1167 */     super.impl_cssResetInitialValues();
/*      */ 
/* 1169 */     ObservableList localObservableList = getChildren();
/* 1170 */     int i = localObservableList.size();
/* 1171 */     for (int j = 0; j < i; j++) {
/* 1172 */       Node localNode = (Node)localObservableList.get(j);
/* 1173 */       if (localNode != null)
/* 1174 */         localNode.impl_cssResetInitialValues();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected Parent()
/*      */   {
/* 1192 */     computeDirtyScene(null);
/* 1193 */     requestLayout();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected PGNode impl_createPGNode()
/*      */   {
/* 1203 */     return Toolkit.getToolkit().createPGGroup();
/*      */   }
/*      */ 
/*      */   PGGroup getPGGroup() {
/* 1207 */     return (PGGroup)impl_getPGNode();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public BaseBounds impl_computeGeomBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*      */   {
/* 1267 */     if (this.children.isEmpty()) {
/* 1268 */       return paramBaseBounds.makeEmpty();
/*      */     }
/*      */ 
/* 1271 */     if (paramBaseTransform.isTranslateOrIdentity())
/*      */     {
/* 1275 */       if (this.cachedBoundsInvalid) recomputeBounds();
/* 1276 */       if (!paramBaseTransform.isIdentity()) {
/* 1277 */         paramBaseBounds = paramBaseBounds.deriveWithNewBounds((float)(this.cachedBounds.getMinX() + paramBaseTransform.getMxt()), (float)(this.cachedBounds.getMinY() + paramBaseTransform.getMyt()), (float)(this.cachedBounds.getMinZ() + paramBaseTransform.getMzt()), (float)(this.cachedBounds.getMaxX() + paramBaseTransform.getMxt()), (float)(this.cachedBounds.getMaxY() + paramBaseTransform.getMyt()), (float)(this.cachedBounds.getMaxZ() + paramBaseTransform.getMzt()));
/*      */       }
/*      */       else
/*      */       {
/* 1284 */         paramBaseBounds = paramBaseBounds.deriveWithNewBounds(this.cachedBounds);
/*      */       }
/* 1286 */       if (this.dirtyChildren != null) this.dirtyChildren.clear();
/* 1287 */       return paramBaseBounds;
/*      */     }
/*      */ 
/* 1291 */     double d1 = 1.7976931348623157E+308D; double d2 = 1.7976931348623157E+308D; double d3 = 1.7976931348623157E+308D;
/* 1292 */     double d4 = 4.9E-324D; double d5 = 4.9E-324D; double d6 = 4.9E-324D;
/* 1293 */     int i = 1;
/* 1294 */     for (Node localNode : getChildren()) {
/* 1295 */       if (localNode.isVisible()) {
/* 1296 */         paramBaseBounds = localNode.getTransformedBounds(paramBaseBounds, paramBaseTransform);
/*      */ 
/* 1299 */         if (!paramBaseBounds.isEmpty()) {
/* 1300 */           if (i != 0) {
/* 1301 */             d1 = paramBaseBounds.getMinX();
/* 1302 */             d2 = paramBaseBounds.getMinY();
/* 1303 */             d3 = paramBaseBounds.getMinZ();
/* 1304 */             d4 = paramBaseBounds.getMaxX();
/* 1305 */             d5 = paramBaseBounds.getMaxY();
/* 1306 */             d6 = paramBaseBounds.getMaxZ();
/* 1307 */             i = 0;
/*      */           } else {
/* 1309 */             d1 = Math.min(paramBaseBounds.getMinX(), d1);
/* 1310 */             d2 = Math.min(paramBaseBounds.getMinY(), d2);
/* 1311 */             d3 = Math.min(paramBaseBounds.getMinZ(), d3);
/* 1312 */             d4 = Math.max(paramBaseBounds.getMaxX(), d4);
/* 1313 */             d5 = Math.max(paramBaseBounds.getMaxY(), d5);
/* 1314 */             d6 = Math.max(paramBaseBounds.getMaxZ(), d6);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1321 */     if (i != 0)
/* 1322 */       paramBaseBounds.makeEmpty();
/*      */     else {
/* 1324 */       paramBaseBounds = paramBaseBounds.deriveWithNewBounds((float)d1, (float)d2, (float)d3, (float)d4, (float)d5, (float)d6);
/*      */     }
/* 1326 */     if (this.dirtyChildren != null) this.dirtyChildren.clear();
/* 1327 */     return paramBaseBounds;
/*      */   }
/*      */ 
/*      */   boolean childAdded(Node paramNode)
/*      */   {
/* 1346 */     if ((this.top == null) || (this.bottom == null) || (this.left == null) || (this.right == null) || (this.near == null) || (this.far == null))
/*      */     {
/* 1348 */       this.cachedBounds.makeEmpty();
/* 1349 */       this.cachedBoundsInvalid = true;
/* 1350 */       return true;
/*      */     }
/*      */ 
/* 1361 */     boolean bool = false;
/*      */ 
/* 1364 */     if (paramNode.isVisible()) {
/* 1365 */       this.tmp = paramNode.getTransformedBounds(this.tmp, BaseTransform.IDENTITY_TRANSFORM);
/* 1366 */       if (!this.tmp.isEmpty()) {
/* 1367 */         paramNode.boundsChanged = false;
/* 1368 */         double d1 = this.tmp.getMinX();
/* 1369 */         double d2 = this.tmp.getMinY();
/* 1370 */         double d3 = this.tmp.getMinZ();
/* 1371 */         double d4 = this.tmp.getMaxX();
/* 1372 */         double d5 = this.tmp.getMaxY();
/* 1373 */         double d6 = this.tmp.getMaxZ();
/* 1374 */         double d7 = this.cachedBounds.getMinX();
/* 1375 */         double d8 = this.cachedBounds.getMinY();
/* 1376 */         double d9 = this.cachedBounds.getMinZ();
/* 1377 */         double d10 = this.cachedBounds.getMaxX();
/* 1378 */         double d11 = this.cachedBounds.getMaxY();
/* 1379 */         double d12 = this.cachedBounds.getMaxZ();
/*      */ 
/* 1381 */         double d13 = Math.min(d1, d7);
/* 1382 */         double d14 = Math.min(d2, d8);
/* 1383 */         double d15 = Math.min(d3, d9);
/* 1384 */         double d16 = Math.max(d4, d10);
/* 1385 */         double d17 = Math.max(d5, d11);
/* 1386 */         double d18 = Math.max(d6, d12);
/*      */ 
/* 1388 */         if (d2 < d8) { bool = true; this.top = paramNode; }
/* 1389 */         if (d1 < d7) { bool = true; this.left = paramNode; }
/* 1390 */         if (d3 < d9) { bool = true; this.near = paramNode; }
/* 1391 */         if (d5 > d11) { bool = true; this.bottom = paramNode; }
/* 1392 */         if (d4 > d10) { bool = true; this.right = paramNode; }
/* 1393 */         if (d6 > d12) { bool = true; this.far = paramNode;
/*      */         }
/*      */ 
/* 1396 */         this.cachedBounds = this.cachedBounds.deriveWithNewBounds((float)d13, (float)d14, (float)d15, (float)d16, (float)d17, (float)d18);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1402 */     return bool;
/*      */   }
/*      */ 
/*      */   boolean childRemoved(Node paramNode)
/*      */   {
/* 1411 */     paramNode.boundsChanged = false;
/*      */ 
/* 1425 */     if (paramNode == this.top) this.top = null;
/* 1426 */     if (paramNode == this.left) this.left = null;
/* 1427 */     if (paramNode == this.bottom) this.bottom = null;
/* 1428 */     if (paramNode == this.right) this.right = null;
/* 1429 */     if (paramNode == this.near) this.near = null;
/* 1430 */     if (paramNode == this.far) this.far = null;
/*      */ 
/* 1435 */     if ((this.top == null) || (this.bottom == null) || (this.left == null) || (this.right == null) || (this.near == null) || (this.far == null))
/*      */     {
/* 1437 */       this.cachedBounds.makeEmpty();
/* 1438 */       this.cachedBoundsInvalid = true;
/* 1439 */       this.top = (this.left = this.bottom = this.right = this.near = this.far = null);
/* 1440 */       return true;
/*      */     }
/*      */ 
/* 1444 */     return false;
/*      */   }
/*      */ 
/*      */   void recomputeBounds()
/*      */   {
/* 1452 */     if (this.children.isEmpty()) {
/* 1453 */       if (this.dirtyChildren != null) this.dirtyChildren.clear();
/* 1454 */       this.cachedBoundsInvalid = false;
/* 1455 */       this.cachedBounds.makeEmpty();
/* 1456 */       return;
/*      */     }
/*      */ 
/* 1460 */     if (this.children.size() == 1) {
/* 1461 */       if (this.dirtyChildren != null) this.dirtyChildren.clear();
/* 1462 */       this.cachedBoundsInvalid = false;
/* 1463 */       Node localNode1 = (Node)this.children.get(0);
/* 1464 */       if (localNode1 == null) return;
/*      */ 
/* 1466 */       if (localNode1.isVisible())
/* 1467 */         this.cachedBounds = localNode1.getTransformedBounds(this.cachedBounds, BaseTransform.IDENTITY_TRANSFORM);
/*      */       else {
/* 1469 */         this.cachedBounds.makeEmpty();
/*      */       }
/* 1471 */       localNode1.boundsChanged = false;
/*      */       return;
/*      */     }
/*      */     double d15;
/*      */     double d16;
/*      */     double d17;
/*      */     double d18;
/* 1479 */     if ((this.cachedBounds != null) && (!this.cachedBounds.isEmpty()) && (this.top != null) && (this.left != null) && (this.bottom != null) && (this.right != null) && (this.near != null) && (this.far != null))
/*      */     {
/* 1498 */       if ((this.top.isVisible()) && (this.left.isVisible()) && (this.bottom.isVisible()) && (this.right.isVisible()) && (this.near.isVisible()) && (this.far.isVisible()))
/*      */       {
/* 1504 */         int i = 0;
/*      */ 
/* 1507 */         double d2 = this.cachedBounds.getMinX();
/* 1508 */         double d4 = this.cachedBounds.getMinY();
/* 1509 */         double d6 = this.cachedBounds.getMinZ();
/* 1510 */         double d8 = this.cachedBounds.getMaxX();
/* 1511 */         double d10 = this.cachedBounds.getMaxY();
/* 1512 */         double d12 = this.cachedBounds.getMaxZ();
/*      */ 
/* 1517 */         double d13 = d2;
/* 1518 */         double d14 = d4;
/* 1519 */         d15 = d6;
/* 1520 */         d16 = d8;
/* 1521 */         d17 = d10;
/* 1522 */         d18 = d12;
/*      */         Object localObject;
/*      */         double d22;
/*      */         double d23;
/*      */         double d24;
/*      */         double d25;
/*      */         double d26;
/* 1525 */         if (this.dirtyChildren != null) {
/* 1526 */           for (localObject = this.dirtyChildren.iterator(); ((Iterator)localObject).hasNext(); ) { Node localNode3 = (Node)((Iterator)localObject).next();
/* 1527 */             if ((localNode3.isVisible()) && (localNode3.boundsChanged)) {
/* 1528 */               this.tmp = localNode3.getTransformedBounds(this.tmp, BaseTransform.IDENTITY_TRANSFORM);
/* 1529 */               if (this.tmp.isEmpty())
/*      */               {
/* 1533 */                 if (localNode3 == this.top) { i = 1; break; }
/* 1534 */                 if (localNode3 == this.left) { i = 1; break; }
/* 1535 */                 if (localNode3 == this.bottom) { i = 1; break; }
/* 1536 */                 if (localNode3 == this.right) { i = 1; break; }
/* 1537 */                 if (localNode3 == this.near) { i = 1; break; }
/* 1538 */                 if (localNode3 == this.far) { i = 1; break; }
/*      */               } else {
/* 1540 */                 double d20 = this.tmp.getMinX();
/* 1541 */                 d22 = this.tmp.getMinY();
/* 1542 */                 d23 = this.tmp.getMinZ();
/* 1543 */                 d24 = this.tmp.getMaxX();
/* 1544 */                 d25 = this.tmp.getMaxY();
/* 1545 */                 d26 = this.tmp.getMaxZ();
/*      */ 
/* 1550 */                 if ((localNode3 == this.top) && (d22 > d4)) { i = 1; break; }
/* 1551 */                 if ((localNode3 == this.left) && (d20 > d2)) { i = 1; break; }
/* 1552 */                 if ((localNode3 == this.near) && (d23 > d6)) { i = 1; break; }
/* 1553 */                 if ((localNode3 == this.bottom) && (d25 < d10)) { i = 1; break; }
/* 1554 */                 if ((localNode3 == this.right) && (d24 < d8)) { i = 1; break; }
/* 1555 */                 if ((localNode3 == this.far) && (d26 < d12)) { i = 1; break;
/*      */                 }
/*      */ 
/* 1559 */                 if (d22 < d14) { d14 = d22; this.top = localNode3; }
/* 1560 */                 if (d20 < d13) { d13 = d20; this.left = localNode3; }
/* 1561 */                 if (d23 < d15) { d15 = d23; this.near = localNode3; }
/* 1562 */                 if (d25 > d17) { d17 = d25; this.bottom = localNode3; }
/* 1563 */                 if (d24 > d16) { d16 = d24; this.right = localNode3; }
/* 1564 */                 if (d26 > d18) { d18 = d26; this.far = localNode3; }
/*      */               }
/*      */             }
/* 1567 */             localNode3.boundsChanged = false; }
/*      */         }
/*      */         else {
/* 1570 */           localObject = getChildren();
/* 1571 */           int n = ((ObservableList)localObject).size();
/* 1572 */           for (int i1 = 0; i1 < n; i1++) {
/* 1573 */             Node localNode4 = (Node)((ObservableList)localObject).get(i1);
/* 1574 */             if (localNode4 != null)
/*      */             {
/* 1576 */               if ((localNode4.isVisible()) && (localNode4.boundsChanged)) {
/* 1577 */                 this.tmp = localNode4.getTransformedBounds(this.tmp, BaseTransform.IDENTITY_TRANSFORM);
/* 1578 */                 if (this.tmp.isEmpty())
/*      */                 {
/* 1582 */                   if (localNode4 == this.top) { i = 1; break; }
/* 1583 */                   if (localNode4 == this.left) { i = 1; break; }
/* 1584 */                   if (localNode4 == this.bottom) { i = 1; break; }
/* 1585 */                   if (localNode4 == this.right) { i = 1; break; }
/* 1586 */                   if (localNode4 == this.near) { i = 1; break; }
/* 1587 */                   if (localNode4 == this.far) { i = 1; break; }
/*      */                 } else {
/* 1589 */                   d22 = this.tmp.getMinX();
/* 1590 */                   d23 = this.tmp.getMinY();
/* 1591 */                   d24 = this.tmp.getMinZ();
/* 1592 */                   d25 = this.tmp.getMaxX();
/* 1593 */                   d26 = this.tmp.getMaxY();
/* 1594 */                   double d27 = this.tmp.getMaxZ();
/*      */ 
/* 1599 */                   if ((localNode4 == this.top) && (d23 > d4)) { i = 1; break; }
/* 1600 */                   if ((localNode4 == this.left) && (d22 > d2)) { i = 1; break; }
/* 1601 */                   if ((localNode4 == this.near) && (d24 > d6)) { i = 1; break; }
/* 1602 */                   if ((localNode4 == this.bottom) && (d26 < d10)) { i = 1; break; }
/* 1603 */                   if ((localNode4 == this.right) && (d25 < d8)) { i = 1; break; }
/* 1604 */                   if ((localNode4 == this.far) && (d27 < d12)) { i = 1; break;
/*      */                   }
/*      */ 
/* 1608 */                   if (d23 < d14) { d14 = d23; this.top = localNode4; }
/* 1609 */                   if (d22 < d13) { d13 = d22; this.left = localNode4; }
/* 1610 */                   if (d24 < d15) { d15 = d24; this.near = localNode4; }
/* 1611 */                   if (d26 > d17) { d17 = d26; this.bottom = localNode4; }
/* 1612 */                   if (d25 > d16) { d16 = d25; this.right = localNode4; }
/* 1613 */                   if (d27 > d18) { d18 = d27; this.far = localNode4; }
/*      */                 }
/*      */               }
/* 1616 */               localNode4.boundsChanged = false;
/*      */             }
/*      */           }
/*      */         }
/* 1620 */         if (this.dirtyChildren != null) this.dirtyChildren.clear();
/*      */ 
/* 1622 */         if (i == 0)
/*      */         {
/* 1625 */           this.cachedBoundsInvalid = false;
/* 1626 */           this.cachedBounds = this.cachedBounds.deriveWithNewBounds((float)d13, (float)d14, (float)d15, (float)d16, (float)d17, (float)d18);
/*      */ 
/* 1628 */           return;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1635 */     double d1 = 0.0D;
/* 1636 */     double d3 = 0.0D;
/* 1637 */     double d5 = 0.0D;
/* 1638 */     double d7 = 0.0D;
/* 1639 */     double d9 = 0.0D;
/* 1640 */     double d11 = 0.0D;
/*      */ 
/* 1645 */     int j = 1;
/* 1646 */     ObservableList localObservableList = getChildren();
/* 1647 */     int k = localObservableList.size();
/* 1648 */     for (int m = 0; m < k; m++) {
/* 1649 */       Node localNode2 = (Node)localObservableList.get(m);
/* 1650 */       if (localNode2 != null)
/*      */       {
/* 1653 */         localNode2.boundsChanged = false;
/* 1654 */         if (localNode2.isVisible()) {
/* 1655 */           this.tmp = localNode2.getTransformedBounds(this.tmp, BaseTransform.IDENTITY_TRANSFORM);
/* 1656 */           if (!this.tmp.isEmpty()) {
/* 1657 */             d15 = this.tmp.getMinX();
/* 1658 */             d16 = this.tmp.getMinY();
/* 1659 */             d17 = this.tmp.getMinZ();
/* 1660 */             d18 = this.tmp.getMaxX();
/* 1661 */             double d19 = this.tmp.getMaxY();
/* 1662 */             double d21 = this.tmp.getMaxZ();
/*      */ 
/* 1664 */             if (j != 0) {
/* 1665 */               this.top = (this.left = this.bottom = this.right = this.near = this.far = localNode2);
/* 1666 */               d1 = d15;
/* 1667 */               d3 = d16;
/* 1668 */               d5 = d17;
/* 1669 */               d7 = d18;
/* 1670 */               d9 = d19;
/* 1671 */               d11 = d21;
/* 1672 */               j = 0;
/*      */             } else {
/* 1674 */               if (d16 < d3) { d3 = d16; this.top = localNode2; }
/* 1675 */               if (d15 < d1) { d1 = d15; this.left = localNode2; }
/* 1676 */               if (d17 < d5) { d5 = d17; this.near = localNode2; }
/* 1677 */               if (d19 > d9) { d9 = d19; this.bottom = localNode2; }
/* 1678 */               if (d18 > d7) { d7 = d18; this.right = localNode2; }
/* 1679 */               if (d21 > d11) { d11 = d21; this.far = localNode2; }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1685 */     if (this.dirtyChildren != null) this.dirtyChildren.clear();
/* 1686 */     this.cachedBoundsInvalid = false;
/* 1687 */     if (j != 0) {
/* 1688 */       this.top = (this.left = this.bottom = this.right = this.near = this.far = null);
/* 1689 */       this.cachedBounds.makeEmpty();
/*      */     } else {
/* 1691 */       this.cachedBounds = this.cachedBounds.deriveWithNewBounds((float)d1, (float)d3, (float)d5, (float)d7, (float)d9, (float)d11);
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected void impl_geomChanged()
/*      */   {
/* 1704 */     this.cachedBoundsInvalid = true;
/* 1705 */     super.impl_geomChanged();
/*      */   }
/*      */ 
/*      */   void childBoundsChanged(Node paramNode)
/*      */   {
/* 1712 */     if (!paramNode.isVisible())
/*      */     {
/* 1715 */       return;
/*      */     }
/*      */ 
/* 1722 */     if (!paramNode.boundsChanged) {
/* 1723 */       paramNode.boundsChanged = true;
/* 1724 */       if (this.dirtyChildren != null) this.dirtyChildren.add(paramNode);
/*      */ 
/*      */     }
/*      */ 
/* 1729 */     impl_geomChanged();
/*      */   }
/*      */ 
/*      */   void childVisibilityChanged(Node paramNode)
/*      */   {
/* 1737 */     this.cachedBounds.makeEmpty();
/* 1738 */     impl_geomChanged();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected boolean impl_computeContains(double paramDouble1, double paramDouble2)
/*      */   {
/* 1748 */     Point2D localPoint2D = TempState.getInstance().point;
/* 1749 */     for (Node localNode : getChildren()) {
/* 1750 */       localPoint2D.x = ((float)paramDouble1);
/* 1751 */       localPoint2D.y = ((float)paramDouble2);
/*      */       try {
/* 1753 */         localNode.parentToLocal(localPoint2D); } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/*      */       }
/* 1755 */       continue;
/*      */ 
/* 1757 */       if (localNode.contains(localPoint2D.x, localPoint2D.y)) {
/* 1758 */         return true;
/*      */       }
/*      */     }
/* 1761 */     return false;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public Object impl_processMXNode(MXNodeAlgorithm paramMXNodeAlgorithm, MXNodeAlgorithmContext paramMXNodeAlgorithmContext)
/*      */   {
/* 1770 */     return paramMXNodeAlgorithm.processContainerNode(this, paramMXNodeAlgorithmContext);
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.Parent
 * JD-Core Version:    0.6.2
 */