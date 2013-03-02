/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class AnchorPane extends Pane
/*     */ {
/*     */   private static final String TOP_ANCHOR = "pane-top-anchor";
/*     */   private static final String LEFT_ANCHOR = "pane-left-anchor";
/*     */   private static final String BOTTOM_ANCHOR = "pane-bottom-anchor";
/*     */   private static final String RIGHT_ANCHOR = "pane-right-anchor";
/*     */ 
/*     */   public static void setTopAnchor(Node paramNode, Double paramDouble)
/*     */   {
/* 129 */     setConstraint(paramNode, "pane-top-anchor", paramDouble);
/*     */   }
/*     */ 
/*     */   public static Double getTopAnchor(Node paramNode)
/*     */   {
/* 138 */     return (Double)getConstraint(paramNode, "pane-top-anchor");
/*     */   }
/*     */ 
/*     */   public static void setLeftAnchor(Node paramNode, Double paramDouble)
/*     */   {
/* 151 */     setConstraint(paramNode, "pane-left-anchor", paramDouble);
/*     */   }
/*     */ 
/*     */   public static Double getLeftAnchor(Node paramNode)
/*     */   {
/* 160 */     return (Double)getConstraint(paramNode, "pane-left-anchor");
/*     */   }
/*     */ 
/*     */   public static void setBottomAnchor(Node paramNode, Double paramDouble)
/*     */   {
/* 173 */     setConstraint(paramNode, "pane-bottom-anchor", paramDouble);
/*     */   }
/*     */ 
/*     */   public static Double getBottomAnchor(Node paramNode)
/*     */   {
/* 182 */     return (Double)getConstraint(paramNode, "pane-bottom-anchor");
/*     */   }
/*     */ 
/*     */   public static void setRightAnchor(Node paramNode, Double paramDouble)
/*     */   {
/* 195 */     setConstraint(paramNode, "pane-right-anchor", paramDouble);
/*     */   }
/*     */ 
/*     */   public static Double getRightAnchor(Node paramNode)
/*     */   {
/* 204 */     return (Double)getConstraint(paramNode, "pane-right-anchor");
/*     */   }
/*     */ 
/*     */   public static void clearConstraints(Node paramNode)
/*     */   {
/* 212 */     setTopAnchor(paramNode, null);
/* 213 */     setRightAnchor(paramNode, null);
/* 214 */     setBottomAnchor(paramNode, null);
/* 215 */     setLeftAnchor(paramNode, null);
/*     */   }
/*     */ 
/*     */   protected double computeMinWidth(double paramDouble)
/*     */   {
/* 230 */     return computeWidth(true, paramDouble);
/*     */   }
/*     */ 
/*     */   protected double computeMinHeight(double paramDouble) {
/* 234 */     return computeHeight(true, paramDouble);
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/* 238 */     return computeWidth(false, paramDouble);
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/* 242 */     return computeHeight(false, paramDouble);
/*     */   }
/*     */ 
/*     */   private double computeWidth(boolean paramBoolean, double paramDouble) {
/* 246 */     double d1 = 0.0D;
/* 247 */     for (int i = 0; i < getChildren().size(); i++) {
/* 248 */       Node localNode = (Node)getChildren().get(i);
/* 249 */       if (localNode.isManaged()) {
/* 250 */         Double localDouble1 = getLeftAnchor(localNode);
/* 251 */         Double localDouble2 = getRightAnchor(localNode);
/*     */ 
/* 253 */         double d2 = localDouble2 != null ? 0.0D : localDouble1 != null ? localDouble1.doubleValue() : localNode.getLayoutBounds().getMinX() + localNode.getLayoutX();
/*     */ 
/* 255 */         double d3 = localDouble2 != null ? localDouble2.doubleValue() : 0.0D;
/* 256 */         if (localNode.getContentBias() == Orientation.VERTICAL) {
/* 257 */           paramDouble = paramBoolean ? localNode.minHeight(-1.0D) : localNode.prefHeight(-1.0D);
/*     */         }
/* 259 */         d1 = Math.max(d1, d2 + (paramBoolean ? localNode.minWidth(paramDouble) : localNode.prefWidth(paramDouble)) + d3);
/*     */       }
/*     */     }
/* 262 */     return getInsets().getLeft() + d1 + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   private double computeHeight(boolean paramBoolean, double paramDouble) {
/* 266 */     double d1 = 0.0D;
/* 267 */     for (int i = 0; i < getChildren().size(); i++) {
/* 268 */       Node localNode = (Node)getChildren().get(i);
/* 269 */       if (localNode.isManaged()) {
/* 270 */         Double localDouble1 = getTopAnchor(localNode);
/* 271 */         Double localDouble2 = getBottomAnchor(localNode);
/*     */ 
/* 273 */         double d2 = localDouble2 != null ? 0.0D : localDouble1 != null ? localDouble1.doubleValue() : localNode.getLayoutBounds().getMinY() + localNode.getLayoutY();
/*     */ 
/* 275 */         double d3 = localDouble2 != null ? localDouble2.doubleValue() : 0.0D;
/* 276 */         if (localNode.getContentBias() == Orientation.HORIZONTAL) {
/* 277 */           paramDouble = paramBoolean ? localNode.minWidth(-1.0D) : localNode.prefWidth(-1.0D);
/*     */         }
/* 279 */         d1 = Math.max(d1, d2 + (paramBoolean ? localNode.minHeight(paramDouble) : localNode.prefHeight(paramDouble)) + d3);
/*     */       }
/*     */     }
/* 282 */     return getInsets().getTop() + d1 + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   private double computeChildWidth(Node paramNode, Double paramDouble1, Double paramDouble2, double paramDouble) {
/* 286 */     if ((paramDouble1 != null) && (paramDouble2 != null) && (paramNode.isResizable())) {
/* 287 */       return getWidth() - getInsets().getLeft() - getInsets().getRight() - paramDouble1.doubleValue() - paramDouble2.doubleValue();
/*     */     }
/* 289 */     return computeChildPrefAreaWidth(paramNode, Insets.EMPTY, paramDouble);
/*     */   }
/*     */ 
/*     */   private double computeChildHeight(Node paramNode, Double paramDouble1, Double paramDouble2, double paramDouble) {
/* 293 */     if ((paramDouble1 != null) && (paramDouble2 != null) && (paramNode.isResizable())) {
/* 294 */       return getHeight() - getInsets().getTop() - getInsets().getBottom() - paramDouble1.doubleValue() - paramDouble2.doubleValue();
/*     */     }
/* 296 */     return computeChildPrefAreaHeight(paramNode, Insets.EMPTY, paramDouble);
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 300 */     Insets localInsets = getInsets();
/*     */ 
/* 302 */     for (int i = 0; i < getChildren().size(); i++) {
/* 303 */       Node localNode = (Node)getChildren().get(i);
/* 304 */       if (localNode.isManaged()) {
/* 305 */         Double localDouble1 = getTopAnchor(localNode);
/* 306 */         Double localDouble2 = getBottomAnchor(localNode);
/* 307 */         Double localDouble3 = getLeftAnchor(localNode);
/* 308 */         Double localDouble4 = getRightAnchor(localNode);
/*     */ 
/* 310 */         double d1 = localNode.getLayoutX() + localNode.getLayoutBounds().getMinX();
/* 311 */         double d2 = localNode.getLayoutY() + localNode.getLayoutBounds().getMinY();
/*     */ 
/* 313 */         double d3 = -1.0D;
/* 314 */         double d4 = -1.0D;
/* 315 */         Orientation localOrientation = localNode.getContentBias();
/* 316 */         if (localOrientation == Orientation.VERTICAL)
/*     */         {
/* 318 */           d4 = computeChildHeight(localNode, localDouble1, localDouble2, -1.0D);
/* 319 */           d3 = computeChildWidth(localNode, localDouble3, localDouble4, d4);
/*     */         }
/* 321 */         else if (localOrientation == Orientation.HORIZONTAL)
/*     */         {
/* 323 */           d3 = computeChildWidth(localNode, localDouble3, localDouble4, -1.0D);
/* 324 */           d4 = computeChildHeight(localNode, localDouble1, localDouble2, d3);
/*     */         } else {
/* 326 */           d3 = computeChildWidth(localNode, localDouble3, localDouble4, -1.0D);
/* 327 */           d4 = computeChildHeight(localNode, localDouble1, localDouble2, -1.0D);
/*     */         }
/*     */ 
/* 330 */         if (localDouble3 != null) {
/* 331 */           d1 = localInsets.getLeft() + localDouble3.doubleValue();
/*     */         }
/* 333 */         else if (localDouble4 != null) {
/* 334 */           d1 = getWidth() - localInsets.getRight() - localDouble4.doubleValue() - d3;
/*     */         }
/* 336 */         if (localDouble1 != null) {
/* 337 */           d2 = localInsets.getTop() + localDouble1.doubleValue();
/*     */         }
/* 339 */         else if (localDouble2 != null) {
/* 340 */           d2 = getHeight() - localInsets.getBottom() - localDouble2.doubleValue() - d4;
/*     */         }
/* 342 */         localNode.resizeRelocate(d1, d2, d3, d4);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.AnchorPane
 * JD-Core Version:    0.6.2
 */