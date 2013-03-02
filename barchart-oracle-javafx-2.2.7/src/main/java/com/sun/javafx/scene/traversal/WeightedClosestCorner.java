/*     */ package com.sun.javafx.scene.traversal;
/*     */ 
/*     */ import com.sun.javafx.Logging;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import java.util.List;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class WeightedClosestCorner
/*     */   implements Algorithm
/*     */ {
/*     */   PlatformLogger focusLogger;
/*     */ 
/*     */   WeightedClosestCorner()
/*     */   {
/*  72 */     this.focusLogger = Logging.getFocusLogger();
/*     */   }
/*     */ 
/*     */   private boolean isOnAxis(Direction paramDirection, Bounds paramBounds1, Bounds paramBounds2)
/*     */   {
/*     */     double d1;
/*     */     double d2;
/*     */     double d3;
/*     */     double d4;
/*  79 */     if ((paramDirection == Direction.UP) || (paramDirection == Direction.DOWN)) {
/*  80 */       d1 = paramBounds1.getMinX();
/*  81 */       d2 = paramBounds1.getMaxX();
/*  82 */       d3 = paramBounds2.getMinX();
/*  83 */       d4 = paramBounds2.getMaxX();
/*     */     }
/*     */     else {
/*  86 */       d1 = paramBounds1.getMinY();
/*  87 */       d2 = paramBounds1.getMaxY();
/*  88 */       d3 = paramBounds2.getMinY();
/*  89 */       d4 = paramBounds2.getMaxY();
/*     */     }
/*     */ 
/*  92 */     return (d3 <= d2) && (d4 >= d1);
/*     */   }
/*     */ 
/*     */   private double outDistance(Direction paramDirection, Bounds paramBounds1, Bounds paramBounds2)
/*     */   {
/*     */     double d;
/* 103 */     if (paramDirection == Direction.UP) {
/* 104 */       d = paramBounds1.getMinY() - paramBounds2.getMaxY();
/*     */     }
/* 106 */     else if (paramDirection == Direction.DOWN) {
/* 107 */       d = paramBounds2.getMinY() - paramBounds1.getMaxY();
/*     */     }
/* 109 */     else if (paramDirection == Direction.LEFT) {
/* 110 */       d = paramBounds1.getMinX() - paramBounds2.getMaxX();
/*     */     }
/*     */     else {
/* 113 */       d = paramBounds2.getMinX() - paramBounds1.getMaxX();
/*     */     }
/*     */ 
/* 116 */     return d;
/*     */   }
/*     */ 
/*     */   private double centerSideDistance(Direction paramDirection, Bounds paramBounds1, Bounds paramBounds2)
/*     */   {
/*     */     double d1;
/*     */     double d2;
/* 128 */     if ((paramDirection == Direction.UP) || (paramDirection == Direction.DOWN)) {
/* 129 */       d1 = paramBounds1.getMinX() + paramBounds1.getWidth() / 2.0D;
/* 130 */       d2 = paramBounds2.getMinX() + paramBounds2.getWidth() / 2.0D;
/*     */     }
/*     */     else {
/* 133 */       d1 = paramBounds1.getMinY() + paramBounds1.getHeight() / 2.0D;
/* 134 */       d2 = paramBounds2.getMinY() + paramBounds2.getHeight() / 2.0D;
/*     */     }
/*     */ 
/* 137 */     return Math.abs(d2 - d1);
/*     */   }
/*     */ 
/*     */   private double cornerSideDistance(Direction paramDirection, Bounds paramBounds1, Bounds paramBounds2)
/*     */   {
/*     */     double d;
/* 149 */     if ((paramDirection == Direction.UP) || (paramDirection == Direction.DOWN))
/*     */     {
/* 151 */       if (paramBounds2.getMinX() > paramBounds1.getMaxX())
/*     */       {
/* 153 */         d = paramBounds2.getMinX() - paramBounds1.getMaxX();
/*     */       }
/*     */       else
/*     */       {
/* 157 */         d = paramBounds1.getMinX() - paramBounds2.getMaxX();
/*     */       }
/*     */ 
/*     */     }
/* 162 */     else if (paramBounds2.getMinY() > paramBounds1.getMaxY())
/*     */     {
/* 164 */       d = paramBounds2.getMinY() - paramBounds1.getMaxY();
/*     */     }
/*     */     else
/*     */     {
/* 168 */       d = paramBounds1.getMinY() - paramBounds2.getMaxY();
/*     */     }
/*     */ 
/* 171 */     return d;
/*     */   }
/*     */ 
/*     */   public Node traverse(Node paramNode, Direction paramDirection, TraversalEngine paramTraversalEngine) {
/* 175 */     Node localNode = null;
/* 176 */     List localList1 = paramTraversalEngine.getTargetNodes();
/* 177 */     List localList2 = paramTraversalEngine.getTargetBounds(localList1);
/*     */ 
/* 179 */     if (this.focusLogger.isLoggable(400)) {
/* 180 */       this.focusLogger.finer("old focus owner : " + paramNode + ", bounds : " + paramTraversalEngine.getBounds(paramNode));
/*     */     }
/*     */ 
/* 183 */     int i = traverse(paramTraversalEngine.getBounds(paramNode), paramDirection, localList2);
/* 184 */     if (i != -1) {
/* 185 */       localNode = (Node)localList1.get(i);
/* 186 */       if (this.focusLogger.isLoggable(400)) {
/* 187 */         this.focusLogger.finer("new focus owner : " + localNode + ", bounds : " + paramTraversalEngine.getBounds(localNode));
/*     */       }
/*     */ 
/*     */     }
/* 191 */     else if (this.focusLogger.isLoggable(400)) {
/* 192 */       this.focusLogger.finer("no focus transfer");
/*     */     }
/*     */ 
/* 196 */     localList1.clear();
/* 197 */     localList2.clear();
/*     */ 
/* 199 */     return localNode;
/*     */   }
/*     */ 
/*     */   public int traverse(Bounds paramBounds, Direction paramDirection, List<Bounds> paramList)
/*     */   {
/*     */     int i;
/* 206 */     if ((paramDirection == Direction.NEXT) || (paramDirection == Direction.PREVIOUS))
/* 207 */       i = trav1D(paramBounds, paramDirection, paramList);
/*     */     else {
/* 209 */       i = trav2D(paramBounds, paramDirection, paramList);
/*     */     }
/*     */ 
/* 212 */     return i;
/*     */   }
/*     */ 
/*     */   private int trav2D(Bounds paramBounds, Direction paramDirection, List<Bounds> paramList)
/*     */   {
/* 217 */     Object localObject = null;
/* 218 */     double d1 = 0.0D;
/* 219 */     int i = -1;
/*     */ 
/* 221 */     for (int j = 0; j < paramList.size(); j++) {
/* 222 */       Bounds localBounds = (Bounds)paramList.get(j);
/* 223 */       double d2 = outDistance(paramDirection, paramBounds, localBounds);
/*     */       double d3;
/* 226 */       if (isOnAxis(paramDirection, paramBounds, localBounds)) {
/* 227 */         d3 = d2 + centerSideDistance(paramDirection, paramBounds, localBounds) / 100.0D;
/*     */       }
/*     */       else {
/* 230 */         double d4 = cornerSideDistance(paramDirection, paramBounds, localBounds);
/* 231 */         d3 = 100000.0D + d2 * d2 + 9.0D * d4 * d4;
/*     */       }
/*     */ 
/* 234 */       if (d2 >= 0.0D)
/*     */       {
/* 238 */         if ((localObject == null) || (d3 < d1)) {
/* 239 */           localObject = localBounds;
/* 240 */           d1 = d3;
/* 241 */           i = j;
/*     */         }
/*     */       }
/*     */     }
/* 245 */     return i;
/*     */   }
/*     */ 
/*     */   private int compare1D(Bounds paramBounds1, Bounds paramBounds2)
/*     */   {
/* 254 */     int i = 0;
/*     */ 
/* 257 */     double d1 = (paramBounds1.getMinY() + paramBounds1.getMaxY()) / 2.0D;
/* 258 */     double d2 = (paramBounds2.getMinY() + paramBounds2.getMaxY()) / 2.0D;
/* 259 */     double d3 = (paramBounds1.getMinX() + paramBounds1.getMaxX()) / 2.0D;
/* 260 */     double d4 = (paramBounds2.getMinX() + paramBounds2.getMaxX()) / 2.0D;
/* 261 */     double d5 = paramBounds1.hashCode();
/* 262 */     double d6 = paramBounds2.hashCode();
/*     */ 
/* 264 */     if (d1 < d2) {
/* 265 */       i = -1;
/*     */     }
/* 267 */     else if (d1 > d2) {
/* 268 */       i = 1;
/*     */     }
/* 270 */     else if (d3 < d4) {
/* 271 */       i = -1;
/*     */     }
/* 273 */     else if (d3 > d4) {
/* 274 */       i = 1;
/*     */     }
/* 276 */     else if (d5 < d6) {
/* 277 */       i = -1;
/*     */     }
/* 279 */     else if (d5 > d6) {
/* 280 */       i = 1;
/*     */     }
/*     */ 
/* 283 */     return i;
/*     */   }
/*     */ 
/*     */   private int compare1D(Bounds paramBounds1, Bounds paramBounds2, Direction paramDirection)
/*     */   {
/* 288 */     return paramDirection == Direction.NEXT ? compare1D(paramBounds1, paramBounds2) : -compare1D(paramBounds1, paramBounds2);
/*     */   }
/*     */ 
/*     */   private int trav1D(Bounds paramBounds, Direction paramDirection, List<Bounds> paramList) {
/* 292 */     int i = -1;
/* 293 */     int j = -1;
/*     */ 
/* 295 */     for (int k = 0; k < paramList.size(); k++) {
/* 296 */       if ((j == -1) || (compare1D((Bounds)paramList.get(k), (Bounds)paramList.get(j), paramDirection) < 0))
/*     */       {
/* 298 */         j = k;
/*     */       }
/*     */ 
/* 301 */       if (compare1D((Bounds)paramList.get(k), paramBounds, paramDirection) >= 0)
/*     */       {
/* 305 */         if ((i == -1) || (compare1D((Bounds)paramList.get(k), (Bounds)paramList.get(i), paramDirection) < 0))
/*     */         {
/* 307 */           i = k;
/*     */         }
/*     */       }
/*     */     }
/* 311 */     return i == -1 ? j : i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.traversal.WeightedClosestCorner
 * JD-Core Version:    0.6.2
 */