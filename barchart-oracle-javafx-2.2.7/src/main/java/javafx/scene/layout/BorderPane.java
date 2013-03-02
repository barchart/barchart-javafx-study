/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class BorderPane extends Pane
/*     */ {
/*     */   private static final String MARGIN = "borderpane-margin";
/*     */   private static final String ALIGNMENT = "borderpane-alignment";
/*     */   private ObjectProperty<Node> center;
/*     */   private ObjectProperty<Node> top;
/*     */   private ObjectProperty<Node> bottom;
/*     */   private ObjectProperty<Node> left;
/*     */   private ObjectProperty<Node> right;
/*     */ 
/*     */   public static void setAlignment(Node paramNode, Pos paramPos)
/*     */   {
/* 154 */     setConstraint(paramNode, "borderpane-alignment", paramPos);
/*     */   }
/*     */ 
/*     */   public static Pos getAlignment(Node paramNode)
/*     */   {
/* 163 */     return (Pos)getConstraint(paramNode, "borderpane-alignment");
/*     */   }
/*     */ 
/*     */   public static void setMargin(Node paramNode, Insets paramInsets)
/*     */   {
/* 174 */     setConstraint(paramNode, "borderpane-margin", paramInsets);
/*     */   }
/*     */ 
/*     */   public static Insets getMargin(Node paramNode)
/*     */   {
/* 183 */     return (Insets)getConstraint(paramNode, "borderpane-margin");
/*     */   }
/*     */ 
/*     */   private static Insets getNodeMargin(Node paramNode)
/*     */   {
/* 188 */     Insets localInsets = getMargin(paramNode);
/* 189 */     return localInsets != null ? localInsets : Insets.EMPTY;
/*     */   }
/*     */ 
/*     */   public static void clearConstraints(Node paramNode)
/*     */   {
/* 197 */     setAlignment(paramNode, null);
/* 198 */     setMargin(paramNode, null);
/*     */   }
/*     */ 
/*     */   private ObjectProperty<Node> createObjectPropertyModelImpl(final String paramString)
/*     */   {
/* 213 */     return new ObjectPropertyBase()
/*     */     {
/* 215 */       Node oldValue = null;
/*     */ 
/*     */       protected void invalidated()
/*     */       {
/* 219 */         Node localNode = (Node)get();
/* 220 */         if (this.oldValue != null) {
/* 221 */           BorderPane.this.getChildren().remove(this.oldValue);
/*     */         }
/* 223 */         this.oldValue = localNode;
/* 224 */         if (localNode != null)
/* 225 */           BorderPane.this.getChildren().add(localNode);
/*     */       }
/*     */ 
/*     */       public Object getBean()
/*     */       {
/* 231 */         return BorderPane.this;
/*     */       }
/*     */ 
/*     */       public String getName()
/*     */       {
/* 236 */         return paramString;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> centerProperty()
/*     */   {
/* 250 */     if (this.center == null) {
/* 251 */       this.center = createObjectPropertyModelImpl("center");
/*     */     }
/* 253 */     return this.center;
/*     */   }
/*     */   public final void setCenter(Node paramNode) {
/* 256 */     centerProperty().set(paramNode); } 
/* 257 */   public final Node getCenter() { return this.center == null ? null : (Node)this.center.get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<Node> topProperty()
/*     */   {
/* 268 */     if (this.top == null) {
/* 269 */       this.top = createObjectPropertyModelImpl("top");
/*     */     }
/* 271 */     return this.top;
/*     */   }
/*     */   public final void setTop(Node paramNode) {
/* 274 */     topProperty().set(paramNode); } 
/* 275 */   public final Node getTop() { return this.top == null ? null : (Node)this.top.get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<Node> bottomProperty()
/*     */   {
/* 286 */     if (this.bottom == null) {
/* 287 */       this.bottom = createObjectPropertyModelImpl("bottom");
/*     */     }
/* 289 */     return this.bottom;
/*     */   }
/*     */   public final void setBottom(Node paramNode) {
/* 292 */     bottomProperty().set(paramNode); } 
/* 293 */   public final Node getBottom() { return this.bottom == null ? null : (Node)this.bottom.get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<Node> leftProperty()
/*     */   {
/* 304 */     if (this.left == null) {
/* 305 */       this.left = createObjectPropertyModelImpl("left");
/*     */     }
/* 307 */     return this.left;
/*     */   }
/*     */   public final void setLeft(Node paramNode) {
/* 310 */     leftProperty().set(paramNode); } 
/* 311 */   public final Node getLeft() { return this.left == null ? null : (Node)this.left.get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<Node> rightProperty()
/*     */   {
/* 322 */     if (this.right == null) {
/* 323 */       this.right = createObjectPropertyModelImpl("right");
/*     */     }
/* 325 */     return this.right;
/*     */   }
/*     */   public final void setRight(Node paramNode) {
/* 328 */     rightProperty().set(paramNode); } 
/* 329 */   public final Node getRight() { return this.right == null ? null : (Node)this.right.get(); }
/*     */ 
/*     */ 
/*     */   public Orientation getContentBias()
/*     */   {
/* 335 */     if ((getCenter() != null) && (getCenter().isManaged()) && (getCenter().getContentBias() != null))
/* 336 */       return getCenter().getContentBias();
/* 337 */     if ((getRight() != null) && (getRight().isManaged()) && (getRight().getContentBias() != null))
/* 338 */       return getRight().getContentBias();
/* 339 */     if ((getBottom() != null) && (getBottom().isManaged()) && (getBottom().getContentBias() != null))
/* 340 */       return getBottom().getContentBias();
/* 341 */     if ((getLeft() != null) && (getLeft().isManaged()) && (getLeft().getContentBias() != null))
/* 342 */       return getLeft().getContentBias();
/* 343 */     if ((getTop() != null) && (getTop().isManaged()) && (getTop().getContentBias() != null)) {
/* 344 */       return getTop().getContentBias();
/*     */     }
/* 346 */     return null;
/*     */   }
/*     */ 
/*     */   protected double computeMinWidth(double paramDouble)
/*     */   {
/*     */     double d1;
/*     */     double d2;
/*     */     double d3;
/*     */     double d4;
/*     */     double d5;
/* 356 */     if (getContentBias() == Orientation.VERTICAL) {
/* 357 */       double[] arrayOfDouble = adjustAreaHeight(paramDouble, -1.0D);
/*     */ 
/* 359 */       d1 = getAreaWidth(getTop(), arrayOfDouble[0], true);
/* 360 */       d2 = getAreaWidth(getLeft(), arrayOfDouble[1], true);
/* 361 */       d3 = getAreaWidth(getCenter(), arrayOfDouble[2], true);
/* 362 */       d4 = getAreaWidth(getRight(), arrayOfDouble[3], true);
/* 363 */       d5 = getAreaWidth(getBottom(), arrayOfDouble[4], true);
/*     */     } else {
/* 365 */       d1 = getTop() != null ? computeChildMinAreaWidth(getTop(), getMargin(getTop())) : 0.0D;
/* 366 */       d2 = getLeft() != null ? computeChildMinAreaWidth(getLeft(), getMargin(getLeft())) : 0.0D;
/* 367 */       d3 = getCenter() != null ? computeChildMinAreaWidth(getCenter(), getMargin(getCenter())) : 0.0D;
/* 368 */       d4 = getRight() != null ? computeChildMinAreaWidth(getRight(), getMargin(getRight())) : 0.0D;
/* 369 */       d5 = getBottom() != null ? computeChildMinAreaWidth(getBottom(), getMargin(getBottom())) : 0.0D;
/*     */     }
/* 371 */     return getInsets().getLeft() + Math.max(d2 + d3 + d4, Math.max(d1, d5)) + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computeMinHeight(double paramDouble)
/*     */   {
/* 377 */     double d1 = 0.0D;
/* 378 */     double d2 = 0.0D;
/* 379 */     double d3 = 0.0D;
/* 380 */     double d4 = 0.0D;
/* 381 */     double d5 = 0.0D;
/*     */ 
/* 383 */     if (getContentBias() == Orientation.HORIZONTAL) {
/* 384 */       double[] arrayOfDouble = adjustAreaWidth(paramDouble, -1.0D);
/*     */ 
/* 386 */       d1 = getAreaHeight(getTop(), paramDouble, true);
/* 387 */       d3 = getAreaHeight(getLeft(), arrayOfDouble[0], true);
/* 388 */       d4 = getAreaHeight(getCenter(), arrayOfDouble[1], true);
/* 389 */       d5 = getAreaHeight(getRight(), arrayOfDouble[2], true);
/* 390 */       d2 = getAreaHeight(getBottom(), paramDouble, true);
/*     */     } else {
/* 392 */       d1 = getTop() != null ? computeChildMinAreaHeight(getTop(), getMargin(getTop())) : 0.0D;
/* 393 */       d3 = getLeft() != null ? computeChildMinAreaHeight(getLeft(), getMargin(getLeft())) : 0.0D;
/* 394 */       d4 = getCenter() != null ? computeChildMinAreaHeight(getCenter(), getMargin(getCenter())) : 0.0D;
/* 395 */       d5 = getRight() != null ? computeChildMinAreaHeight(getRight(), getMargin(getRight())) : 0.0D;
/* 396 */       d2 = getBottom() != null ? computeChildMinAreaHeight(getBottom(), getMargin(getBottom())) : 0.0D;
/*     */     }
/* 398 */     return getInsets().getTop() + d1 + Math.max(d4, Math.max(d5, d3)) + d2 + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 404 */     double d1 = 0.0D;
/* 405 */     double d2 = 0.0D;
/* 406 */     double d3 = 0.0D;
/* 407 */     double d4 = 0.0D;
/* 408 */     double d5 = 0.0D;
/*     */ 
/* 410 */     if (getContentBias() == Orientation.VERTICAL) {
/* 411 */       double[] arrayOfDouble = adjustAreaHeight(paramDouble, -1.0D);
/*     */ 
/* 413 */       d1 = getAreaWidth(getTop(), arrayOfDouble[0], false);
/* 414 */       d2 = getAreaWidth(getLeft(), arrayOfDouble[1], false);
/* 415 */       d3 = getAreaWidth(getCenter(), arrayOfDouble[2], false);
/* 416 */       d4 = getAreaWidth(getRight(), arrayOfDouble[3], false);
/* 417 */       d5 = getAreaWidth(getBottom(), arrayOfDouble[4], false);
/*     */     } else {
/* 419 */       double d6 = getCenter() != null ? computeChildPrefAreaHeight(getCenter(), getMargin(getCenter())) : 0.0D;
/* 420 */       double d7 = getLeft() != null ? computeChildPrefAreaHeight(getLeft(), getMargin(getLeft())) : 0.0D;
/* 421 */       double d8 = getRight() != null ? computeChildPrefAreaHeight(getRight(), getMargin(getRight())) : 0.0D;
/*     */ 
/* 423 */       double d9 = Math.max(d6, Math.max(d8, d7));
/*     */ 
/* 425 */       d2 = getLeft() != null ? computeChildPrefAreaWidth(getLeft(), getMargin(getLeft()), d9) : 0.0D;
/* 426 */       d4 = getRight() != null ? computeChildPrefAreaWidth(getRight(), getMargin(getRight()), d9) : 0.0D;
/* 427 */       d3 = getCenter() != null ? computeChildPrefAreaWidth(getCenter(), getMargin(getCenter()), d9) : 0.0D;
/*     */ 
/* 429 */       d1 = getTop() != null ? computeChildPrefAreaWidth(getTop(), getMargin(getTop())) : 0.0D;
/* 430 */       d5 = getBottom() != null ? computeChildPrefAreaWidth(getBottom(), getMargin(getBottom())) : 0.0D;
/*     */     }
/* 432 */     return getInsets().getLeft() + Math.max(d2 + d3 + d4, Math.max(d1, d5)) + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 438 */     double d1 = 0.0D;
/* 439 */     double d2 = 0.0D;
/* 440 */     double d3 = 0.0D;
/* 441 */     double d4 = 0.0D;
/* 442 */     double d5 = 0.0D;
/* 443 */     double d6 = 0.0D;
/*     */ 
/* 445 */     if (getContentBias() == Orientation.HORIZONTAL) {
/* 446 */       double[] arrayOfDouble = adjustAreaWidth(paramDouble, -1.0D);
/*     */ 
/* 448 */       d1 = getAreaHeight(getTop(), paramDouble, false);
/* 449 */       d3 = getAreaHeight(getLeft(), arrayOfDouble[0], false);
/* 450 */       d4 = getAreaHeight(getCenter(), arrayOfDouble[1], false);
/* 451 */       d5 = getAreaHeight(getRight(), arrayOfDouble[2], false);
/* 452 */       d2 = getAreaHeight(getBottom(), paramDouble, false);
/*     */ 
/* 454 */       d6 = Math.max(d4, Math.max(d5, d3));
/*     */     } else {
/* 456 */       d4 = getCenter() != null ? computeChildPrefAreaHeight(getCenter(), getMargin(getCenter())) : 0.0D;
/* 457 */       d3 = getLeft() != null ? computeChildPrefAreaHeight(getLeft(), getMargin(getLeft())) : 0.0D;
/* 458 */       d5 = getRight() != null ? computeChildPrefAreaHeight(getRight(), getMargin(getRight())) : 0.0D;
/*     */ 
/* 460 */       d6 = Math.max(d4, Math.max(d5, d3));
/*     */ 
/* 462 */       double d7 = getLeft() != null ? computeChildPrefAreaWidth(getLeft(), getMargin(getLeft()), d6) : 0.0D;
/* 463 */       double d8 = getRight() != null ? computeChildPrefAreaWidth(getRight(), getMargin(getRight()), d6) : 0.0D;
/* 464 */       double d9 = getCenter() != null ? computeChildPrefAreaWidth(getCenter(), getMargin(getCenter()), d6) : 0.0D;
/*     */ 
/* 466 */       double d10 = getTop() != null ? computeChildPrefAreaWidth(getTop(), getMargin(getTop())) : 0.0D;
/* 467 */       double d11 = getBottom() != null ? computeChildPrefAreaWidth(getBottom(), getMargin(getBottom())) : 0.0D;
/*     */ 
/* 469 */       double d12 = getInsets().getLeft() + Math.max(d7 + d9 + d8, Math.max(d10, d11)) + getInsets().getRight();
/*     */ 
/* 473 */       d1 = getTop() != null ? computeChildPrefAreaHeight(getTop(), getMargin(getTop()), d12) : 0.0D;
/* 474 */       d2 = getBottom() != null ? computeChildPrefAreaHeight(getBottom(), getMargin(getBottom()), d12) : 0.0D;
/*     */     }
/*     */ 
/* 477 */     return getInsets().getTop() + d1 + d6 + d2 + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 481 */     double d1 = getWidth();
/* 482 */     double d2 = getHeight();
/* 483 */     double d3 = getInsets().getLeft();
/* 484 */     double d4 = getInsets().getTop();
/* 485 */     double d5 = d1 - d3 - getInsets().getRight();
/* 486 */     double d6 = d2 - d4 - getInsets().getBottom();
/*     */ 
/* 488 */     double[] arrayOfDouble1 = adjustAreaWidth(d1, d2);
/* 489 */     double[] arrayOfDouble2 = adjustAreaHeight(d2, d1);
/*     */ 
/* 491 */     double d7 = 0.0D;
/* 492 */     Insets localInsets1 = null;
/* 493 */     if (getTop() != null) {
/* 494 */       localInsets1 = getNodeMargin(getTop());
/* 495 */       if (getContentBias() == Orientation.VERTICAL)
/* 496 */         d7 = arrayOfDouble2[0] == -1.0D ? getTop().prefHeight(-1.0D) : arrayOfDouble2[0];
/*     */       else {
/* 498 */         d7 = snapSize(localInsets1.getTop() + getTop().prefHeight(d5 - localInsets1.getLeft() - localInsets1.getRight()) + localInsets1.getBottom());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 503 */     double d8 = 0.0D;
/* 504 */     Insets localInsets2 = null;
/* 505 */     if (getBottom() != null) {
/* 506 */       localInsets2 = getNodeMargin(getBottom());
/* 507 */       if (getContentBias() == Orientation.VERTICAL)
/* 508 */         d8 = arrayOfDouble2[4] == -1.0D ? getBottom().prefHeight(-1.0D) : arrayOfDouble2[4];
/*     */       else {
/* 510 */         d8 = snapSize(localInsets2.getTop() + getBottom().prefHeight(d5 - localInsets2.getLeft() - localInsets2.getRight()) + localInsets2.getBottom());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 515 */     double d9 = 0.0D;
/* 516 */     Insets localInsets3 = null;
/* 517 */     if (getLeft() != null) {
/* 518 */       localInsets3 = getNodeMargin(getLeft());
/* 519 */       if (getContentBias() == Orientation.HORIZONTAL)
/* 520 */         d9 = arrayOfDouble1[0] == -1.0D ? getLeft().prefWidth(-1.0D) : arrayOfDouble1[0];
/*     */       else {
/* 522 */         d9 = snapSize(localInsets3.getLeft() + getLeft().prefWidth(d6 - d7 - d8 - localInsets3.getTop() - localInsets3.getBottom()) + localInsets3.getRight());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 527 */     double d10 = 0.0D;
/* 528 */     Insets localInsets4 = null;
/* 529 */     if (getRight() != null) {
/* 530 */       localInsets4 = getNodeMargin(getRight());
/* 531 */       if (getContentBias() == Orientation.HORIZONTAL)
/* 532 */         d10 = arrayOfDouble1[2] == -1.0D ? getRight().prefWidth(-1.0D) : arrayOfDouble1[2];
/*     */       else
/* 534 */         d10 = snapSize(localInsets4.getLeft() + getRight().prefWidth(d6 - d7 - d8 - localInsets4.getTop() - localInsets4.getBottom()) + localInsets4.getRight());
/*     */     }
/*     */     Pos localPos;
/* 540 */     if (getTop() != null) {
/* 541 */       localPos = getAlignment(getTop());
/* 542 */       d7 = Math.min(d7, d6);
/* 543 */       layoutInArea(getTop(), d3, d4, d5, d7, 0.0D, localInsets1, localPos != null ? localPos.getHpos() : HPos.LEFT, localPos != null ? localPos.getVpos() : VPos.TOP);
/*     */     }
/*     */ 
/* 548 */     if (getBottom() != null) {
/* 549 */       localPos = getAlignment(getBottom());
/* 550 */       d8 = Math.min(d8, d6 - d7);
/* 551 */       layoutInArea(getBottom(), d3, d4 + d6 - d8, d5, d8, 0.0D, localInsets2, localPos != null ? localPos.getHpos() : HPos.LEFT, localPos != null ? localPos.getVpos() : VPos.BOTTOM);
/*     */     }
/*     */ 
/* 558 */     if (getLeft() != null) {
/* 559 */       localPos = getAlignment(getLeft());
/* 560 */       d9 = Math.min(d9, d5);
/* 561 */       layoutInArea(getLeft(), d3, d4 + d7, d9, d6 - d7 - d8, 0.0D, localInsets3, localPos != null ? localPos.getHpos() : HPos.LEFT, localPos != null ? localPos.getVpos() : VPos.TOP);
/*     */     }
/*     */ 
/* 567 */     if (getRight() != null) {
/* 568 */       localPos = getAlignment(getRight());
/* 569 */       d10 = Math.min(d10, d5 - d9);
/* 570 */       layoutInArea(getRight(), d3 + d5 - d10, d4 + d7, d10, d6 - d7 - d8, 0.0D, localInsets4, localPos != null ? localPos.getHpos() : HPos.RIGHT, localPos != null ? localPos.getVpos() : VPos.TOP);
/*     */     }
/*     */ 
/* 577 */     if (getCenter() != null) {
/* 578 */       localPos = getAlignment(getCenter());
/* 579 */       layoutInArea(getCenter(), d3 + d9, d4 + d7, d5 - d9 - d10, d6 - d7 - d8, 0.0D, getNodeMargin(getCenter()), localPos != null ? localPos.getHpos() : HPos.CENTER, localPos != null ? localPos.getVpos() : VPos.CENTER);
/*     */     }
/*     */   }
/*     */ 
/*     */   private double getAreaWidth(Node paramNode, double paramDouble, boolean paramBoolean)
/*     */   {
/* 589 */     if ((paramNode != null) && (paramNode.isManaged())) {
/* 590 */       Insets localInsets = getNodeMargin(paramNode);
/* 591 */       return paramBoolean ? computeChildMinAreaWidth(paramNode, localInsets, paramDouble) : computeChildPrefAreaWidth(paramNode, localInsets, paramDouble);
/*     */     }
/*     */ 
/* 594 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   private double getAreaHeight(Node paramNode, double paramDouble, boolean paramBoolean) {
/* 598 */     if ((paramNode != null) && (paramNode.isManaged())) {
/* 599 */       Insets localInsets = getNodeMargin(paramNode);
/* 600 */       return paramBoolean ? computeChildMinAreaHeight(paramNode, localInsets, paramDouble) : computeChildPrefAreaHeight(paramNode, localInsets, paramDouble);
/*     */     }
/*     */ 
/* 603 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   private boolean childHasContentBias(Node paramNode, Orientation paramOrientation) {
/* 607 */     if ((paramNode != null) && (paramNode.isManaged())) {
/* 608 */       return paramNode.getContentBias() == paramOrientation;
/*     */     }
/* 610 */     return false;
/*     */   }
/*     */ 
/*     */   private double getAreaLimitWidth(Node paramNode, boolean paramBoolean, double paramDouble) {
/* 614 */     if ((paramNode != null) && (paramNode.isManaged())) {
/* 615 */       Insets localInsets = getNodeMargin(paramNode);
/* 616 */       return paramBoolean ? computeChildMinAreaWidth(paramNode, localInsets, paramDouble) : computeChildMaxAreaWidth(paramNode, localInsets, paramDouble);
/*     */     }
/*     */ 
/* 619 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   private double[] adjustAreaWidth(double paramDouble1, double paramDouble2) {
/* 623 */     double[] arrayOfDouble1 = new double[3];
/* 624 */     arrayOfDouble1[0] = getAreaWidth(getLeft(), -1.0D, false);
/* 625 */     arrayOfDouble1[1] = getAreaWidth(getCenter(), -1.0D, false);
/* 626 */     arrayOfDouble1[2] = getAreaWidth(getRight(), -1.0D, false);
/*     */ 
/* 628 */     double d1 = arrayOfDouble1[0] + arrayOfDouble1[1] + arrayOfDouble1[2];
/* 629 */     double d2 = paramDouble1 - d1;
/* 630 */     boolean bool = d2 < 0.0D;
/*     */ 
/* 632 */     boolean[] arrayOfBoolean = new boolean[3];
/* 633 */     arrayOfBoolean[0] = childHasContentBias(getLeft(), Orientation.HORIZONTAL);
/* 634 */     arrayOfBoolean[1] = childHasContentBias(getCenter(), Orientation.HORIZONTAL);
/* 635 */     arrayOfBoolean[2] = childHasContentBias(getRight(), Orientation.HORIZONTAL);
/*     */ 
/* 637 */     double[] arrayOfDouble2 = new double[3];
/* 638 */     arrayOfDouble2[0] = getAreaLimitWidth(getLeft(), bool, paramDouble2);
/* 639 */     arrayOfDouble2[1] = getAreaLimitWidth(getCenter(), bool, paramDouble2);
/* 640 */     arrayOfDouble2[2] = getAreaLimitWidth(getRight(), bool, paramDouble2);
/*     */ 
/* 642 */     double d3 = paramDouble1;
/* 643 */     double[] arrayOfDouble3 = { -1.0D, -1.0D, -1.0D };
/* 644 */     int i = arrayOfDouble3.length;
/* 645 */     if ((paramDouble1 != -1.0D) && (getContentBias() == Orientation.HORIZONTAL)) {
/* 646 */       for (int j = 0; j < arrayOfDouble3.length; j++) {
/* 647 */         if (arrayOfBoolean[j] == 0) {
/* 648 */           arrayOfDouble3[j] = -1.0D;
/* 649 */           i--;
/* 650 */           if (bool) {
/* 651 */             d3 -= arrayOfDouble1[j];
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 656 */       d2 /= i;
/* 657 */       for (j = 0; j < arrayOfDouble3.length; j++) {
/* 658 */         if (!bool) {
/* 659 */           if (arrayOfBoolean[j] != 0) {
/* 660 */             double d4 = arrayOfDouble1[j] + d2;
/* 661 */             if (d4 < arrayOfDouble2[j])
/* 662 */               arrayOfDouble3[j] = d4;
/*     */             else {
/* 664 */               arrayOfDouble3[j] = arrayOfDouble2[j];
/*     */             }
/*     */           }
/*     */         }
/* 668 */         else if ((d3 > 0.0D) && 
/* 669 */           (arrayOfBoolean[j] != 0)) {
/* 670 */           if (d3 > arrayOfDouble2[j])
/* 671 */             arrayOfDouble3[j] = (d3 / i);
/*     */           else {
/* 673 */             arrayOfDouble3[j] = arrayOfDouble2[j];
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 680 */     return arrayOfDouble3;
/*     */   }
/*     */ 
/*     */   private double getAreaLimitHeight(Node paramNode, boolean paramBoolean, double paramDouble) {
/* 684 */     if ((paramNode != null) && (paramNode.isManaged())) {
/* 685 */       Insets localInsets = getNodeMargin(paramNode);
/* 686 */       return paramBoolean ? computeChildMinAreaHeight(paramNode, localInsets, paramDouble) : computeChildMaxAreaHeight(paramNode, localInsets, paramDouble);
/*     */     }
/*     */ 
/* 689 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   private double[] adjustAreaHeight(double paramDouble1, double paramDouble2) {
/* 693 */     double[] arrayOfDouble1 = new double[5];
/* 694 */     arrayOfDouble1[0] = getAreaHeight(getTop(), -1.0D, false);
/* 695 */     arrayOfDouble1[1] = getAreaHeight(getLeft(), -1.0D, false);
/* 696 */     arrayOfDouble1[2] = getAreaHeight(getCenter(), -1.0D, false);
/* 697 */     arrayOfDouble1[3] = getAreaHeight(getRight(), -1.0D, false);
/* 698 */     arrayOfDouble1[4] = getAreaHeight(getBottom(), -1.0D, false);
/*     */ 
/* 700 */     double d1 = Math.max(arrayOfDouble1[1], Math.max(arrayOfDouble1[2], arrayOfDouble1[3]));
/* 701 */     d1 += arrayOfDouble1[0] + arrayOfDouble1[4];
/*     */ 
/* 703 */     double d2 = paramDouble1 - d1;
/* 704 */     boolean bool = d2 < 0.0D;
/*     */ 
/* 706 */     boolean[] arrayOfBoolean = new boolean[5];
/* 707 */     arrayOfBoolean[0] = childHasContentBias(getTop(), Orientation.VERTICAL);
/* 708 */     arrayOfBoolean[1] = childHasContentBias(getLeft(), Orientation.VERTICAL);
/* 709 */     arrayOfBoolean[2] = childHasContentBias(getCenter(), Orientation.VERTICAL);
/* 710 */     arrayOfBoolean[3] = childHasContentBias(getRight(), Orientation.VERTICAL);
/* 711 */     arrayOfBoolean[4] = childHasContentBias(getBottom(), Orientation.VERTICAL);
/*     */ 
/* 713 */     double[] arrayOfDouble2 = new double[5];
/* 714 */     arrayOfDouble2[0] = getAreaLimitHeight(getTop(), bool, paramDouble2);
/* 715 */     arrayOfDouble2[1] = getAreaLimitHeight(getLeft(), bool, paramDouble2);
/* 716 */     arrayOfDouble2[2] = getAreaLimitHeight(getCenter(), bool, paramDouble2);
/* 717 */     arrayOfDouble2[3] = getAreaLimitHeight(getRight(), bool, paramDouble2);
/* 718 */     arrayOfDouble2[4] = getAreaLimitHeight(getBottom(), bool, paramDouble2);
/*     */ 
/* 720 */     double d3 = paramDouble1;
/* 721 */     double[] arrayOfDouble3 = { -1.0D, -1.0D, -1.0D, -1.0D, -1.0D };
/* 722 */     int i = arrayOfDouble3.length;
/*     */ 
/* 724 */     if ((paramDouble1 != -1.0D) && (getContentBias() == Orientation.VERTICAL))
/*     */     {
/* 726 */       double d4 = 0.0D;
/* 727 */       for (int j = 1; j < 4; j++) {
/* 728 */         if (arrayOfBoolean[j] == 0) {
/* 729 */           d4 = Math.max(d4, arrayOfDouble1[j]);
/*     */         }
/*     */       }
/* 732 */       for (j = 0; j < arrayOfDouble3.length; j++) {
/* 733 */         if (arrayOfBoolean[j] == 0) {
/* 734 */           arrayOfDouble3[j] = -1.0D;
/* 735 */           i--;
/* 736 */           if (bool) {
/* 737 */             if ((j < 1) || (j > 3))
/* 738 */               d3 -= arrayOfDouble1[j];
/* 739 */             else if (j == 1)
/*     */             {
/* 741 */               d3 -= d4;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 747 */       d2 /= i;
/* 748 */       for (j = 0; j < arrayOfDouble3.length; j++) {
/* 749 */         if (!bool) {
/* 750 */           if (arrayOfBoolean[j] != 0) {
/* 751 */             double d5 = arrayOfDouble1[j] + d2;
/* 752 */             if (d5 < arrayOfDouble2[j])
/* 753 */               arrayOfDouble3[j] = d5;
/*     */             else {
/* 755 */               arrayOfDouble3[j] = arrayOfDouble2[j];
/*     */             }
/*     */           }
/*     */         }
/* 759 */         else if ((d3 > 0.0D) && 
/* 760 */           (arrayOfBoolean[j] != 0)) {
/* 761 */           if (d3 > arrayOfDouble2[j])
/* 762 */             arrayOfDouble3[j] = (d3 / i);
/*     */           else {
/* 764 */             arrayOfDouble3[j] = arrayOfDouble2[j];
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 771 */     return arrayOfDouble3;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.BorderPane
 * JD-Core Version:    0.6.2
 */