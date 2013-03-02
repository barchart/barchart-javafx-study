/*     */ package com.sun.javafx.scene.traversal;
/*     */ 
/*     */ import com.sun.javafx.Logging;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javafx.geometry.BoundingBox;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ 
/*     */ public class TraversalEngine
/*     */ {
/*     */   private final Algorithm algorithm;
/*     */   private final Parent root;
/*     */   private final boolean isScene;
/*     */   private final Bounds initialBounds;
/*     */   public final List<Node> registeredNodes;
/*     */   private final List<TraverseListener> listeners;
/*     */   PlatformLogger focusLogger;
/*     */ 
/*     */   protected boolean isScene()
/*     */   {
/*  58 */     return this.isScene;
/*     */   }
/*     */   protected Parent getRoot() {
/*  61 */     return this.root;
/*     */   }
/*     */ 
/*     */   public TraversalEngine(Parent paramParent, boolean paramBoolean) {
/*  65 */     this.root = paramParent;
/*  66 */     this.isScene = paramBoolean;
/*     */ 
/*  73 */     this.algorithm = new ContainerTabOrder();
/*  74 */     this.initialBounds = new BoundingBox(0.0D, 0.0D, 1.0D, 1.0D);
/*  75 */     this.registeredNodes = new ArrayList();
/*  76 */     this.listeners = new LinkedList();
/*  77 */     this.focusLogger = Logging.getFocusLogger();
/*  78 */     if (this.focusLogger.isLoggable(400))
/*  79 */       this.focusLogger.finer("TraversalEngine constructor");
/*     */   }
/*     */ 
/*     */   public void addTraverseListener(TraverseListener paramTraverseListener)
/*     */   {
/*  84 */     this.listeners.add(paramTraverseListener);
/*     */   }
/*     */ 
/*     */   public void removeTraverseListener(TraverseListener paramTraverseListener) {
/*  88 */     this.listeners.remove(paramTraverseListener);
/*     */   }
/*     */ 
/*     */   public void reg(Node paramNode) {
/*  92 */     this.registeredNodes.add(paramNode);
/*     */   }
/*     */ 
/*     */   public void unreg(Node paramNode) {
/*  96 */     this.registeredNodes.remove(paramNode);
/*     */   }
/*     */ 
/*     */   public void trav(Node paramNode, Direction paramDirection)
/*     */   {
/* 101 */     Node localNode = this.algorithm.traverse(paramNode, paramDirection, this);
/* 102 */     if (localNode == null) {
/* 103 */       if (this.focusLogger.isLoggable(500))
/* 104 */         this.focusLogger.fine("new node is null, focus not moved");
/*     */     }
/*     */     else {
/* 107 */       if (this.focusLogger.isLoggable(400)) {
/* 108 */         this.focusLogger.finer("new focus owner : " + localNode);
/*     */       }
/* 110 */       localNode.requestFocus();
/* 111 */       for (TraverseListener localTraverseListener : this.listeners)
/* 112 */         localTraverseListener.onTraverse(localNode, getBounds(localNode));
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTopLeftFocusableNode()
/*     */   {
/* 118 */     List localList = getTargetNodes();
/*     */ 
/* 120 */     Point2D localPoint2D = new Point2D(0.0D, 0.0D);
/*     */     Node localNode;
/* 122 */     if (localList.size() > 0)
/*     */     {
/* 124 */       localNode = (Node)localList.get(0);
/* 125 */       double d1 = localPoint2D.distance(getBounds((Node)localList.get(0)).getMinX(), getBounds((Node)localList.get(0)).getMinY());
/*     */ 
/* 128 */       for (int i = 1; i < localList.size(); i++)
/*     */       {
/* 130 */         if (this.focusLogger.isLoggable(300)) {
/* 131 */           this.focusLogger.finest("getTopLeftFocusableNode(), distance : " + localPoint2D.distance(getBounds((Node)localList.get(i)).getMinX(), getBounds((Node)localList.get(i)).getMinY()) + " to  : " + localList.get(i) + ". @ : " + getBounds((Node)localList.get(i)).getMinX() + ":" + getBounds((Node)localList.get(i)).getMinY());
/*     */         }
/* 133 */         double d2 = localPoint2D.distance(getBounds((Node)localList.get(i)).getMinX(), getBounds((Node)localList.get(i)).getMinY());
/* 134 */         if (d1 > d2) {
/* 135 */           d1 = d2;
/* 136 */           localNode = (Node)localList.get(i);
/*     */         }
/*     */       }
/*     */ 
/* 140 */       if (this.focusLogger.isLoggable(400)) {
/* 141 */         this.focusLogger.finer("getTopLeftFocusableNode(), nearest  : " + localNode + ", at : " + d1);
/*     */       }
/*     */ 
/* 144 */       localNode.requestFocus();
/* 145 */       for (TraverseListener localTraverseListener : this.listeners) {
/* 146 */         localTraverseListener.onTraverse(localNode, getBounds(localNode));
/*     */       }
/*     */     }
/*     */ 
/* 150 */     localList.clear();
/*     */ 
/* 152 */     return 0;
/*     */   }
/*     */ 
/*     */   protected List<Node> getTargetNodes()
/*     */   {
/* 159 */     ArrayList localArrayList = new ArrayList();
/* 160 */     for (Node localNode : this.registeredNodes) {
/* 161 */       if ((!localNode.isFocused()) && (localNode.impl_isTreeVisible()) && (!localNode.isDisabled())) {
/* 162 */         localArrayList.add(localNode);
/*     */       }
/*     */     }
/* 165 */     return localArrayList;
/*     */   }
/*     */ 
/*     */   protected List<Bounds> getTargetBounds(List<Node> paramList)
/*     */   {
/* 172 */     ArrayList localArrayList = new ArrayList();
/* 173 */     for (Node localNode : paramList) {
/* 174 */       localArrayList.add(getBounds(localNode));
/*     */     }
/* 176 */     return localArrayList;
/*     */   }
/*     */ 
/*     */   protected Bounds getBounds(Node paramNode)
/*     */   {
/*     */     Bounds localBounds;
/* 185 */     if (paramNode != null) {
/* 186 */       if (this.isScene)
/* 187 */         localBounds = paramNode.localToScene(paramNode.getLayoutBounds());
/*     */       else
/* 189 */         localBounds = this.root.sceneToLocal(paramNode.localToScene(paramNode.getLayoutBounds()));
/*     */     }
/*     */     else {
/* 192 */       localBounds = this.initialBounds;
/*     */     }
/* 194 */     return localBounds;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.traversal.TraversalEngine
 * JD-Core Version:    0.6.2
 */