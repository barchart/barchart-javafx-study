/*     */ package com.sun.javafx.scene.traversal;
/*     */ 
/*     */ import com.sun.javafx.Logging;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import java.util.List;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.scene.Group;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ 
/*     */ public class ContainerTabOrder
/*     */   implements Algorithm
/*     */ {
/*     */   PlatformLogger focusLogger;
/*     */ 
/*     */   ContainerTabOrder()
/*     */   {
/*  50 */     this.focusLogger = Logging.getFocusLogger();
/*     */   }
/*     */ 
/*     */   public Node traverse(Node paramNode, Direction paramDirection, TraversalEngine paramTraversalEngine) {
/*  54 */     Node localNode = null;
/*  55 */     int i = -1;
/*     */ 
/*  57 */     if (this.focusLogger.isLoggable(400)) {
/*  58 */       this.focusLogger.finer("old focus owner : " + paramNode + ", bounds : " + paramTraversalEngine.getBounds(paramNode));
/*     */     }
/*     */ 
/*  61 */     if (Direction.NEXT.equals(paramDirection)) {
/*  62 */       localNode = findNextFocusablePeer(paramNode);
/*     */     }
/*  64 */     else if (Direction.PREVIOUS.equals(paramDirection)) {
/*  65 */       localNode = findPreviousFocusablePeer(paramNode);
/*     */     }
/*  67 */     else if ((Direction.UP.equals(paramDirection)) || (Direction.DOWN.equals(paramDirection)) || (Direction.LEFT.equals(paramDirection)) || (Direction.RIGHT.equals(paramDirection))) {
/*  68 */       List localList1 = paramTraversalEngine.getTargetNodes();
/*  69 */       List localList2 = paramTraversalEngine.getTargetBounds(localList1);
/*     */ 
/*  71 */       int j = trav2D(paramTraversalEngine.getBounds(paramNode), paramDirection, localList2);
/*  72 */       if (j != -1) {
/*  73 */         localNode = (Node)localList1.get(j);
/*     */       }
/*  75 */       localList1.clear();
/*  76 */       localList2.clear();
/*     */     }
/*     */ 
/*  80 */     if (this.focusLogger.isLoggable(400)) {
/*  81 */       if (localNode != null) {
/*  82 */         this.focusLogger.finer("new focus owner : " + localNode + ", bounds : " + paramTraversalEngine.getBounds(localNode));
/*     */       }
/*     */       else {
/*  85 */         this.focusLogger.finer("no focus transfer");
/*     */       }
/*     */     }
/*     */ 
/*  89 */     return localNode;
/*     */   }
/*     */ 
/*     */   private Node findNextFocusablePeer(Node paramNode) {
/*  93 */     Object localObject1 = paramNode;
/*  94 */     Node localNode = null;
/*  95 */     Object localObject2 = findPeers((Node)localObject1);
/*  96 */     if (localObject2 == null) {
/*  97 */       if (this.focusLogger.isLoggable(400)) {
/*  98 */         this.focusLogger.finer("can't find peers for a node without a parent");
/*     */       }
/* 100 */       return null;
/*     */     }
/*     */ 
/* 103 */     int i = ((List)localObject2).indexOf(localObject1);
/*     */ 
/* 105 */     if (i == -1) {
/* 106 */       if (this.focusLogger.isLoggable(400)) {
/* 107 */         this.focusLogger.finer("index not founds, no focus transfer");
/*     */       }
/* 109 */       return null;
/*     */     }
/*     */ 
/* 112 */     localNode = findNextFocusableInList((List)localObject2, i + 1);
/*     */     Object localObject3;
/* 118 */     while ((localNode == null) && (localObject1 != null))
/*     */     {
/* 122 */       Parent localParent2 = ((Node)localObject1).getParent();
/* 123 */       if (localParent2 != null) {
/* 124 */         localObject3 = findPeers(localParent2);
/* 125 */         if (localObject3 != null) {
/* 126 */           int j = ((List)localObject3).indexOf(localParent2);
/* 127 */           localNode = findNextFocusableInList((List)localObject3, j + 1);
/*     */         }
/*     */       }
/* 130 */       localObject1 = localParent2;
/*     */     }
/*     */ 
/* 133 */     if (localNode == null)
/*     */     {
/* 137 */       localObject3 = null;
/* 138 */       Parent localParent1 = paramNode.getParent();
/* 139 */       while (localParent1 != null) {
/* 140 */         localObject3 = localParent1;
/* 141 */         localParent1 = localParent1.getParent();
/*     */       }
/* 143 */       localObject2 = ((Parent)localObject3).getChildrenUnmodifiable();
/* 144 */       localNode = findNextFocusableInList((List)localObject2, 0);
/*     */     }
/*     */ 
/* 147 */     return localNode;
/*     */   }
/*     */ 
/*     */   private Node findNextParent(Node paramNode) {
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */   private Node findNextFocusableInList(List<Node> paramList, int paramInt) {
/* 155 */     Object localObject = null;
/*     */ 
/* 157 */     for (int i = paramInt; i < paramList.size(); i++)
/*     */     {
/* 159 */       Node localNode = (Node)paramList.get(i);
/* 160 */       if ((localNode.isFocusTraversable() == true) && (!localNode.isDisabled()) && (localNode.impl_isTreeVisible() == true)) {
/* 161 */         localObject = localNode;
/*     */       }
/* 164 */       else if ((localNode instanceof Parent)) {
/* 165 */         ObservableList localObservableList = ((Parent)localNode).getChildrenUnmodifiable();
/* 166 */         if (localObservableList.size() > 0) {
/* 167 */           localObject = findNextFocusableInList(localObservableList, 0);
/* 168 */           if (localObject != null) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 174 */     return localObject;
/*     */   }
/*     */ 
/*     */   private Node findPreviousFocusablePeer(Node paramNode) {
/* 178 */     Object localObject1 = paramNode;
/* 179 */     Node localNode = null;
/* 180 */     Object localObject2 = findPeers((Node)localObject1);
/*     */ 
/* 182 */     int i = ((List)localObject2).indexOf(localObject1);
/*     */ 
/* 184 */     if (i == -1) {
/* 185 */       if (this.focusLogger.isLoggable(400)) {
/* 186 */         this.focusLogger.finer("index not founds, no focus transfer");
/*     */       }
/* 188 */       return null;
/*     */     }
/*     */ 
/* 191 */     localNode = findPreviousFocusableInList((List)localObject2, i - 1);
/*     */     Object localObject3;
/* 197 */     while ((localNode == null) && (localObject1 != null))
/*     */     {
/* 201 */       Parent localParent2 = ((Node)localObject1).getParent();
/* 202 */       if (localParent2 != null) {
/* 203 */         localObject3 = findPeers(localParent2);
/* 204 */         if (localObject3 != null) {
/* 205 */           int j = ((List)localObject3).indexOf(localParent2);
/* 206 */           localNode = findPreviousFocusableInList((List)localObject3, j - 1);
/*     */         }
/*     */       }
/* 209 */       localObject1 = localParent2;
/*     */     }
/*     */ 
/* 212 */     if (localNode == null)
/*     */     {
/* 216 */       localObject3 = null;
/* 217 */       Parent localParent1 = paramNode.getParent();
/* 218 */       while (localParent1 != null) {
/* 219 */         localObject3 = localParent1;
/* 220 */         localParent1 = localParent1.getParent();
/*     */       }
/*     */ 
/* 223 */       localObject2 = ((Parent)localObject3).getChildrenUnmodifiable();
/* 224 */       localNode = findPreviousFocusableInList((List)localObject2, ((List)localObject2).size() - 1);
/*     */     }
/*     */ 
/* 227 */     return localNode;
/*     */   }
/*     */ 
/*     */   private Node findPreviousFocusableInList(List<Node> paramList, int paramInt)
/*     */   {
/* 232 */     Object localObject = null;
/*     */ 
/* 234 */     for (int i = paramInt; i >= 0; i--) {
/* 235 */       Node localNode = (Node)paramList.get(i);
/* 236 */       if ((localNode.isFocusTraversable() == true) && (!localNode.isDisabled()) && (localNode.impl_isTreeVisible() == true)) {
/* 237 */         localObject = localNode;
/*     */       }
/* 240 */       else if ((localNode instanceof Parent)) {
/* 241 */         ObservableList localObservableList = ((Parent)localNode).getChildrenUnmodifiable();
/* 242 */         if (localObservableList.size() > 0) {
/* 243 */           localObject = findPreviousFocusableInList(localObservableList, localObservableList.size() - 1);
/* 244 */           if (localObject != null) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 250 */     return localObject;
/*     */   }
/*     */ 
/*     */   private List<Node> findPeers(Node paramNode)
/*     */   {
/* 255 */     ObservableList localObservableList = null;
/* 256 */     Parent localParent = paramNode.getParent();
/*     */ 
/* 260 */     if (localParent != null) {
/* 261 */       localObservableList = localParent.getChildrenUnmodifiable();
/*     */     }
/* 263 */     return localObservableList;
/*     */   }
/*     */ 
/*     */   private static Parent getParent(Node paramNode) {
/* 267 */     return (paramNode.getParent() instanceof Group) ? paramNode.getParent().getParent() : paramNode.getParent();
/*     */   }
/*     */ 
/*     */   private int trav2D(Bounds paramBounds, Direction paramDirection, List<Bounds> paramList)
/*     */   {
/* 272 */     Object localObject = null;
/* 273 */     double d1 = 0.0D;
/* 274 */     int i = -1;
/*     */ 
/* 276 */     for (int j = 0; j < paramList.size(); j++) {
/* 277 */       Bounds localBounds = (Bounds)paramList.get(j);
/* 278 */       double d2 = outDistance(paramDirection, paramBounds, localBounds);
/*     */       double d3;
/* 281 */       if (isOnAxis(paramDirection, paramBounds, localBounds)) {
/* 282 */         d3 = d2 + centerSideDistance(paramDirection, paramBounds, localBounds) / 100.0D;
/*     */       }
/*     */       else {
/* 285 */         double d4 = cornerSideDistance(paramDirection, paramBounds, localBounds);
/* 286 */         d3 = 100000.0D + d2 * d2 + 9.0D * d4 * d4;
/*     */       }
/*     */ 
/* 289 */       if (d2 >= 0.0D)
/*     */       {
/* 293 */         if ((localObject == null) || (d3 < d1)) {
/* 294 */           localObject = localBounds;
/* 295 */           d1 = d3;
/* 296 */           i = j;
/*     */         }
/*     */       }
/*     */     }
/* 300 */     return i;
/*     */   }
/*     */ 
/*     */   private boolean isOnAxis(Direction paramDirection, Bounds paramBounds1, Bounds paramBounds2)
/*     */   {
/*     */     double d1;
/*     */     double d2;
/*     */     double d3;
/*     */     double d4;
/* 307 */     if ((paramDirection == Direction.UP) || (paramDirection == Direction.DOWN)) {
/* 308 */       d1 = paramBounds1.getMinX();
/* 309 */       d2 = paramBounds1.getMaxX();
/* 310 */       d3 = paramBounds2.getMinX();
/* 311 */       d4 = paramBounds2.getMaxX();
/*     */     }
/*     */     else {
/* 314 */       d1 = paramBounds1.getMinY();
/* 315 */       d2 = paramBounds1.getMaxY();
/* 316 */       d3 = paramBounds2.getMinY();
/* 317 */       d4 = paramBounds2.getMaxY();
/*     */     }
/*     */ 
/* 320 */     return (d3 <= d2) && (d4 >= d1);
/*     */   }
/*     */ 
/*     */   private double outDistance(Direction paramDirection, Bounds paramBounds1, Bounds paramBounds2)
/*     */   {
/*     */     double d;
/* 331 */     if (paramDirection == Direction.UP) {
/* 332 */       d = paramBounds1.getMinY() - paramBounds2.getMaxY();
/*     */     }
/* 334 */     else if (paramDirection == Direction.DOWN) {
/* 335 */       d = paramBounds2.getMinY() - paramBounds1.getMaxY();
/*     */     }
/* 337 */     else if (paramDirection == Direction.LEFT) {
/* 338 */       d = paramBounds1.getMinX() - paramBounds2.getMaxX();
/*     */     }
/*     */     else {
/* 341 */       d = paramBounds2.getMinX() - paramBounds1.getMaxX();
/*     */     }
/*     */ 
/* 344 */     return d;
/*     */   }
/*     */ 
/*     */   private double centerSideDistance(Direction paramDirection, Bounds paramBounds1, Bounds paramBounds2)
/*     */   {
/*     */     double d1;
/*     */     double d2;
/* 356 */     if ((paramDirection == Direction.UP) || (paramDirection == Direction.DOWN)) {
/* 357 */       d1 = paramBounds1.getMinX() + paramBounds1.getWidth() / 2.0D;
/* 358 */       d2 = paramBounds2.getMinX() + paramBounds2.getWidth() / 2.0D;
/*     */     }
/*     */     else {
/* 361 */       d1 = paramBounds1.getMinY() + paramBounds1.getHeight() / 2.0D;
/* 362 */       d2 = paramBounds2.getMinY() + paramBounds2.getHeight() / 2.0D;
/*     */     }
/*     */ 
/* 365 */     return Math.abs(d2 - d1);
/*     */   }
/*     */ 
/*     */   private double cornerSideDistance(Direction paramDirection, Bounds paramBounds1, Bounds paramBounds2)
/*     */   {
/*     */     double d;
/* 377 */     if ((paramDirection == Direction.UP) || (paramDirection == Direction.DOWN))
/*     */     {
/* 379 */       if (paramBounds2.getMinX() > paramBounds1.getMaxX())
/*     */       {
/* 381 */         d = paramBounds2.getMinX() - paramBounds1.getMaxX();
/*     */       }
/*     */       else
/*     */       {
/* 385 */         d = paramBounds1.getMinX() - paramBounds2.getMaxX();
/*     */       }
/*     */ 
/*     */     }
/* 390 */     else if (paramBounds2.getMinY() > paramBounds1.getMaxY())
/*     */     {
/* 392 */       d = paramBounds2.getMinY() - paramBounds1.getMaxY();
/*     */     }
/*     */     else
/*     */     {
/* 396 */       d = paramBounds1.getMinY() - paramBounds2.getMaxY();
/*     */     }
/*     */ 
/* 399 */     return d;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.traversal.ContainerTabOrder
 * JD-Core Version:    0.6.2
 */