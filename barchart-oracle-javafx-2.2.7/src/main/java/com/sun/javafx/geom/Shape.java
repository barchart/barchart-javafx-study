/*      */ package com.sun.javafx.geom;
/*      */ 
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ 
/*      */ public abstract class Shape
/*      */ {
/*      */   public static final int RECT_INTERSECTS = -2147483648;
/*      */   public static final int OUT_LEFT = 1;
/*      */   public static final int OUT_TOP = 2;
/*      */   public static final int OUT_RIGHT = 4;
/*      */   public static final int OUT_BOTTOM = 8;
/*      */ 
/*      */   public abstract RectBounds getBounds();
/*      */ 
/*      */   public abstract boolean contains(float paramFloat1, float paramFloat2);
/*      */ 
/*      */   public boolean contains(Point2D paramPoint2D)
/*      */   {
/*  105 */     return contains(paramPoint2D.x, paramPoint2D.y);
/*      */   }
/*      */ 
/*      */   public abstract boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*      */ 
/*      */   public boolean intersects(RectBounds paramRectBounds)
/*      */   {
/*  183 */     float f1 = paramRectBounds.getMinX();
/*  184 */     float f2 = paramRectBounds.getMinY();
/*  185 */     float f3 = paramRectBounds.getMaxX() - f1;
/*  186 */     float f4 = paramRectBounds.getMaxY() - f2;
/*  187 */     return intersects(f1, f2, f3, f4);
/*      */   }
/*      */ 
/*      */   public abstract boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*      */ 
/*      */   public boolean contains(RectBounds paramRectBounds)
/*      */   {
/*  264 */     float f1 = paramRectBounds.getMinX();
/*  265 */     float f2 = paramRectBounds.getMinY();
/*  266 */     float f3 = paramRectBounds.getMaxX() - f1;
/*  267 */     float f4 = paramRectBounds.getMaxY() - f2;
/*  268 */     return contains(f1, f2, f3, f4);
/*      */   }
/*      */ 
/*      */   public abstract PathIterator getPathIterator(BaseTransform paramBaseTransform);
/*      */ 
/*      */   public abstract PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat);
/*      */ 
/*      */   public abstract Shape copy();
/*      */ 
/*      */   public static int pointCrossingsForPath(PathIterator paramPathIterator, float paramFloat1, float paramFloat2)
/*      */   {
/*  362 */     if (paramPathIterator.isDone()) {
/*  363 */       return 0;
/*      */     }
/*  365 */     float[] arrayOfFloat = new float[6];
/*  366 */     if (paramPathIterator.currentSegment(arrayOfFloat) != 0) {
/*  367 */       throw new IllegalPathStateException("missing initial moveto in path definition");
/*      */     }
/*      */ 
/*  370 */     paramPathIterator.next();
/*  371 */     float f1 = arrayOfFloat[0];
/*  372 */     float f2 = arrayOfFloat[1];
/*  373 */     float f3 = f1;
/*  374 */     float f4 = f2;
/*      */ 
/*  376 */     int i = 0;
/*  377 */     while (!paramPathIterator.isDone())
/*      */     {
/*      */       float f5;
/*      */       float f6;
/*  378 */       switch (paramPathIterator.currentSegment(arrayOfFloat)) {
/*      */       case 0:
/*  380 */         if (f4 != f2) {
/*  381 */           i += pointCrossingsForLine(paramFloat1, paramFloat2, f3, f4, f1, f2);
/*      */         }
/*      */ 
/*  385 */         f1 = f3 = arrayOfFloat[0];
/*  386 */         f2 = f4 = arrayOfFloat[1];
/*  387 */         break;
/*      */       case 1:
/*  389 */         f5 = arrayOfFloat[0];
/*  390 */         f6 = arrayOfFloat[1];
/*  391 */         i += pointCrossingsForLine(paramFloat1, paramFloat2, f3, f4, f5, f6);
/*      */ 
/*  394 */         f3 = f5;
/*  395 */         f4 = f6;
/*  396 */         break;
/*      */       case 2:
/*  398 */         f5 = arrayOfFloat[2];
/*  399 */         f6 = arrayOfFloat[3];
/*  400 */         i += pointCrossingsForQuad(paramFloat1, paramFloat2, f3, f4, arrayOfFloat[0], arrayOfFloat[1], f5, f6, 0);
/*      */ 
/*  404 */         f3 = f5;
/*  405 */         f4 = f6;
/*  406 */         break;
/*      */       case 3:
/*  408 */         f5 = arrayOfFloat[4];
/*  409 */         f6 = arrayOfFloat[5];
/*  410 */         i += pointCrossingsForCubic(paramFloat1, paramFloat2, f3, f4, arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], f5, f6, 0);
/*      */ 
/*  415 */         f3 = f5;
/*  416 */         f4 = f6;
/*  417 */         break;
/*      */       case 4:
/*  419 */         if (f4 != f2) {
/*  420 */           i += pointCrossingsForLine(paramFloat1, paramFloat2, f3, f4, f1, f2);
/*      */         }
/*      */ 
/*  424 */         f3 = f1;
/*  425 */         f4 = f2;
/*      */       }
/*      */ 
/*  428 */       paramPathIterator.next();
/*      */     }
/*  430 */     if (f4 != f2) {
/*  431 */       i += pointCrossingsForLine(paramFloat1, paramFloat2, f3, f4, f1, f2);
/*      */     }
/*      */ 
/*  435 */     return i;
/*      */   }
/*      */ 
/*      */   public static int pointCrossingsForLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  449 */     if ((paramFloat2 < paramFloat4) && (paramFloat2 < paramFloat6)) return 0;
/*  450 */     if ((paramFloat2 >= paramFloat4) && (paramFloat2 >= paramFloat6)) return 0;
/*      */ 
/*  452 */     if ((paramFloat1 >= paramFloat3) && (paramFloat1 >= paramFloat5)) return 0;
/*  453 */     if ((paramFloat1 < paramFloat3) && (paramFloat1 < paramFloat5)) return paramFloat4 < paramFloat6 ? 1 : -1;
/*  454 */     float f = paramFloat3 + (paramFloat2 - paramFloat4) * (paramFloat5 - paramFloat3) / (paramFloat6 - paramFloat4);
/*  455 */     if (paramFloat1 >= f) return 0;
/*  456 */     return paramFloat4 < paramFloat6 ? 1 : -1;
/*      */   }
/*      */ 
/*      */   public static int pointCrossingsForQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt)
/*      */   {
/*  474 */     if ((paramFloat2 < paramFloat4) && (paramFloat2 < paramFloat6) && (paramFloat2 < paramFloat8)) return 0;
/*  475 */     if ((paramFloat2 >= paramFloat4) && (paramFloat2 >= paramFloat6) && (paramFloat2 >= paramFloat8)) return 0;
/*      */ 
/*  477 */     if ((paramFloat1 >= paramFloat3) && (paramFloat1 >= paramFloat5) && (paramFloat1 >= paramFloat7)) return 0;
/*  478 */     if ((paramFloat1 < paramFloat3) && (paramFloat1 < paramFloat5) && (paramFloat1 < paramFloat7)) {
/*  479 */       if (paramFloat2 >= paramFloat4) {
/*  480 */         if (paramFloat2 < paramFloat8) return 1;
/*      */ 
/*      */       }
/*  483 */       else if (paramFloat2 >= paramFloat8) return -1;
/*      */ 
/*  486 */       return 0;
/*      */     }
/*      */ 
/*  489 */     if (paramInt > 52) return pointCrossingsForLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat7, paramFloat8);
/*  490 */     float f1 = (paramFloat3 + paramFloat5) / 2.0F;
/*  491 */     float f2 = (paramFloat4 + paramFloat6) / 2.0F;
/*  492 */     float f3 = (paramFloat5 + paramFloat7) / 2.0F;
/*  493 */     float f4 = (paramFloat6 + paramFloat8) / 2.0F;
/*  494 */     paramFloat5 = (f1 + f3) / 2.0F;
/*  495 */     paramFloat6 = (f2 + f4) / 2.0F;
/*  496 */     if ((Float.isNaN(paramFloat5)) || (Float.isNaN(paramFloat6)))
/*      */     {
/*  500 */       return 0;
/*      */     }
/*  502 */     return pointCrossingsForQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, paramFloat5, paramFloat6, paramInt + 1) + pointCrossingsForQuad(paramFloat1, paramFloat2, paramFloat5, paramFloat6, f3, f4, paramFloat7, paramFloat8, paramInt + 1);
/*      */   }
/*      */ 
/*      */   public static int pointCrossingsForCubic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, int paramInt)
/*      */   {
/*  526 */     if ((paramFloat2 < paramFloat4) && (paramFloat2 < paramFloat6) && (paramFloat2 < paramFloat8) && (paramFloat2 < paramFloat10)) return 0;
/*  527 */     if ((paramFloat2 >= paramFloat4) && (paramFloat2 >= paramFloat6) && (paramFloat2 >= paramFloat8) && (paramFloat2 >= paramFloat10)) return 0;
/*      */ 
/*  529 */     if ((paramFloat1 >= paramFloat3) && (paramFloat1 >= paramFloat5) && (paramFloat1 >= paramFloat7) && (paramFloat1 >= paramFloat9)) return 0;
/*  530 */     if ((paramFloat1 < paramFloat3) && (paramFloat1 < paramFloat5) && (paramFloat1 < paramFloat7) && (paramFloat1 < paramFloat9)) {
/*  531 */       if (paramFloat2 >= paramFloat4) {
/*  532 */         if (paramFloat2 < paramFloat10) return 1;
/*      */ 
/*      */       }
/*  535 */       else if (paramFloat2 >= paramFloat10) return -1;
/*      */ 
/*  538 */       return 0;
/*      */     }
/*      */ 
/*  541 */     if (paramInt > 52) return pointCrossingsForLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat9, paramFloat10);
/*  542 */     float f1 = (paramFloat5 + paramFloat7) / 2.0F;
/*  543 */     float f2 = (paramFloat6 + paramFloat8) / 2.0F;
/*  544 */     paramFloat5 = (paramFloat3 + paramFloat5) / 2.0F;
/*  545 */     paramFloat6 = (paramFloat4 + paramFloat6) / 2.0F;
/*  546 */     paramFloat7 = (paramFloat7 + paramFloat9) / 2.0F;
/*  547 */     paramFloat8 = (paramFloat8 + paramFloat10) / 2.0F;
/*  548 */     float f3 = (paramFloat5 + f1) / 2.0F;
/*  549 */     float f4 = (paramFloat6 + f2) / 2.0F;
/*  550 */     float f5 = (f1 + paramFloat7) / 2.0F;
/*  551 */     float f6 = (f2 + paramFloat8) / 2.0F;
/*  552 */     f1 = (f3 + f5) / 2.0F;
/*  553 */     f2 = (f4 + f6) / 2.0F;
/*  554 */     if ((Float.isNaN(f1)) || (Float.isNaN(f2)))
/*      */     {
/*  558 */       return 0;
/*      */     }
/*  560 */     return pointCrossingsForCubic(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, f3, f4, f1, f2, paramInt + 1) + pointCrossingsForCubic(paramFloat1, paramFloat2, f1, f2, f5, f6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramInt + 1);
/*      */   }
/*      */ 
/*      */   public static int rectCrossingsForPath(PathIterator paramPathIterator, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  616 */     if ((paramFloat3 <= paramFloat1) || (paramFloat4 <= paramFloat2)) {
/*  617 */       return 0;
/*      */     }
/*  619 */     if (paramPathIterator.isDone()) {
/*  620 */       return 0;
/*      */     }
/*  622 */     float[] arrayOfFloat = new float[6];
/*  623 */     if (paramPathIterator.currentSegment(arrayOfFloat) != 0) {
/*  624 */       throw new IllegalPathStateException("missing initial moveto in path definition");
/*      */     }
/*      */ 
/*  627 */     paramPathIterator.next();
/*      */     float f3;
/*  629 */     float f1 = f3 = arrayOfFloat[0];
/*      */     float f4;
/*  630 */     float f2 = f4 = arrayOfFloat[1];
/*  631 */     int i = 0;
/*  632 */     while ((i != -2147483648) && (!paramPathIterator.isDone()))
/*      */     {
/*      */       float f5;
/*      */       float f6;
/*  633 */       switch (paramPathIterator.currentSegment(arrayOfFloat)) {
/*      */       case 0:
/*  635 */         if ((f1 != f3) || (f2 != f4)) {
/*  636 */           i = rectCrossingsForLine(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, f3, f4);
/*      */         }
/*      */ 
/*  644 */         f3 = f1 = arrayOfFloat[0];
/*  645 */         f4 = f2 = arrayOfFloat[1];
/*  646 */         break;
/*      */       case 1:
/*  648 */         f5 = arrayOfFloat[0];
/*  649 */         f6 = arrayOfFloat[1];
/*  650 */         i = rectCrossingsForLine(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, f5, f6);
/*      */ 
/*  655 */         f1 = f5;
/*  656 */         f2 = f6;
/*  657 */         break;
/*      */       case 2:
/*  659 */         f5 = arrayOfFloat[2];
/*  660 */         f6 = arrayOfFloat[3];
/*  661 */         i = rectCrossingsForQuad(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, arrayOfFloat[0], arrayOfFloat[1], f5, f6, 0);
/*      */ 
/*  667 */         f1 = f5;
/*  668 */         f2 = f6;
/*  669 */         break;
/*      */       case 3:
/*  671 */         f5 = arrayOfFloat[4];
/*  672 */         f6 = arrayOfFloat[5];
/*  673 */         i = rectCrossingsForCubic(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], f5, f6, 0);
/*      */ 
/*  680 */         f1 = f5;
/*  681 */         f2 = f6;
/*  682 */         break;
/*      */       case 4:
/*  684 */         if ((f1 != f3) || (f2 != f4)) {
/*  685 */           i = rectCrossingsForLine(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, f3, f4);
/*      */         }
/*      */ 
/*  691 */         f1 = f3;
/*  692 */         f2 = f4;
/*      */       }
/*      */ 
/*  697 */       paramPathIterator.next();
/*      */     }
/*  699 */     if ((i != -2147483648) && ((f1 != f3) || (f2 != f4))) {
/*  700 */       i = rectCrossingsForLine(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, f3, f4);
/*      */     }
/*      */ 
/*  708 */     return i;
/*      */   }
/*      */ 
/*      */   public static int rectCrossingsForLine(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/*  722 */     if ((paramFloat6 >= paramFloat4) && (paramFloat8 >= paramFloat4)) return paramInt;
/*  723 */     if ((paramFloat6 <= paramFloat2) && (paramFloat8 <= paramFloat2)) return paramInt;
/*  724 */     if ((paramFloat5 <= paramFloat1) && (paramFloat7 <= paramFloat1)) return paramInt;
/*  725 */     if ((paramFloat5 >= paramFloat3) && (paramFloat7 >= paramFloat3))
/*      */     {
/*  731 */       if (paramFloat6 < paramFloat8)
/*      */       {
/*  734 */         if (paramFloat6 <= paramFloat2) paramInt++;
/*  735 */         if (paramFloat8 >= paramFloat4) paramInt++; 
/*      */       }
/*  736 */       else if (paramFloat8 < paramFloat6)
/*      */       {
/*  739 */         if (paramFloat8 <= paramFloat2) paramInt--;
/*  740 */         if (paramFloat6 >= paramFloat4) paramInt--;
/*      */       }
/*  742 */       return paramInt;
/*      */     }
/*      */ 
/*  748 */     if (((paramFloat5 > paramFloat1) && (paramFloat5 < paramFloat3) && (paramFloat6 > paramFloat2) && (paramFloat6 < paramFloat4)) || ((paramFloat7 > paramFloat1) && (paramFloat7 < paramFloat3) && (paramFloat8 > paramFloat2) && (paramFloat8 < paramFloat4)))
/*      */     {
/*  751 */       return -2147483648;
/*      */     }
/*      */ 
/*  755 */     float f1 = paramFloat5;
/*  756 */     if (paramFloat6 < paramFloat2)
/*  757 */       f1 += (paramFloat2 - paramFloat6) * (paramFloat7 - paramFloat5) / (paramFloat8 - paramFloat6);
/*  758 */     else if (paramFloat6 > paramFloat4) {
/*  759 */       f1 += (paramFloat4 - paramFloat6) * (paramFloat7 - paramFloat5) / (paramFloat8 - paramFloat6);
/*      */     }
/*  761 */     float f2 = paramFloat7;
/*  762 */     if (paramFloat8 < paramFloat2)
/*  763 */       f2 += (paramFloat2 - paramFloat8) * (paramFloat5 - paramFloat7) / (paramFloat6 - paramFloat8);
/*  764 */     else if (paramFloat8 > paramFloat4) {
/*  765 */       f2 += (paramFloat4 - paramFloat8) * (paramFloat5 - paramFloat7) / (paramFloat6 - paramFloat8);
/*      */     }
/*  767 */     if ((f1 <= paramFloat1) && (f2 <= paramFloat1)) return paramInt;
/*  768 */     if ((f1 >= paramFloat3) && (f2 >= paramFloat3)) {
/*  769 */       if (paramFloat6 < paramFloat8)
/*      */       {
/*  772 */         if (paramFloat6 <= paramFloat2) paramInt++;
/*  773 */         if (paramFloat8 >= paramFloat4) paramInt++; 
/*      */       }
/*  774 */       else if (paramFloat8 < paramFloat6)
/*      */       {
/*  777 */         if (paramFloat8 <= paramFloat2) paramInt--;
/*  778 */         if (paramFloat6 >= paramFloat4) paramInt--;
/*      */       }
/*  780 */       return paramInt;
/*      */     }
/*  782 */     return -2147483648;
/*      */   }
/*      */ 
/*      */   public static int rectCrossingsForQuad(int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, int paramInt2)
/*      */   {
/*  798 */     if ((paramFloat6 >= paramFloat4) && (paramFloat8 >= paramFloat4) && (paramFloat10 >= paramFloat4)) return paramInt1;
/*  799 */     if ((paramFloat6 <= paramFloat2) && (paramFloat8 <= paramFloat2) && (paramFloat10 <= paramFloat2)) return paramInt1;
/*  800 */     if ((paramFloat5 <= paramFloat1) && (paramFloat7 <= paramFloat1) && (paramFloat9 <= paramFloat1)) return paramInt1;
/*  801 */     if ((paramFloat5 >= paramFloat3) && (paramFloat7 >= paramFloat3) && (paramFloat9 >= paramFloat3))
/*      */     {
/*  810 */       if (paramFloat6 < paramFloat10)
/*      */       {
/*  812 */         if ((paramFloat6 <= paramFloat2) && (paramFloat10 > paramFloat2)) paramInt1++;
/*  813 */         if ((paramFloat6 < paramFloat4) && (paramFloat10 >= paramFloat4)) paramInt1++; 
/*      */       }
/*  814 */       else if (paramFloat10 < paramFloat6)
/*      */       {
/*  816 */         if ((paramFloat10 <= paramFloat2) && (paramFloat6 > paramFloat2)) paramInt1--;
/*  817 */         if ((paramFloat10 < paramFloat4) && (paramFloat6 >= paramFloat4)) paramInt1--;
/*      */       }
/*  819 */       return paramInt1;
/*      */     }
/*      */ 
/*  824 */     if (((paramFloat5 < paramFloat3) && (paramFloat5 > paramFloat1) && (paramFloat6 < paramFloat4) && (paramFloat6 > paramFloat2)) || ((paramFloat9 < paramFloat3) && (paramFloat9 > paramFloat1) && (paramFloat10 < paramFloat4) && (paramFloat10 > paramFloat2)))
/*      */     {
/*  827 */       return -2147483648;
/*      */     }
/*      */ 
/*  831 */     if (paramInt2 > 52) {
/*  832 */       return rectCrossingsForLine(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat9, paramFloat10);
/*      */     }
/*      */ 
/*  836 */     float f1 = (paramFloat5 + paramFloat7) / 2.0F;
/*  837 */     float f2 = (paramFloat6 + paramFloat8) / 2.0F;
/*  838 */     float f3 = (paramFloat7 + paramFloat9) / 2.0F;
/*  839 */     float f4 = (paramFloat8 + paramFloat10) / 2.0F;
/*  840 */     paramFloat7 = (f1 + f3) / 2.0F;
/*  841 */     paramFloat8 = (f2 + f4) / 2.0F;
/*  842 */     if ((Float.isNaN(paramFloat7)) || (Float.isNaN(paramFloat8)))
/*      */     {
/*  846 */       return 0;
/*      */     }
/*  848 */     paramInt1 = rectCrossingsForQuad(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, f1, f2, paramFloat7, paramFloat8, paramInt2 + 1);
/*      */ 
/*  852 */     if (paramInt1 != -2147483648) {
/*  853 */       paramInt1 = rectCrossingsForQuad(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat7, paramFloat8, f3, f4, paramFloat9, paramFloat10, paramInt2 + 1);
/*      */     }
/*      */ 
/*  858 */     return paramInt1;
/*      */   }
/*      */ 
/*      */   public static int rectCrossingsForCubic(int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, int paramInt2)
/*      */   {
/*  875 */     if ((paramFloat6 >= paramFloat4) && (paramFloat8 >= paramFloat4) && (paramFloat10 >= paramFloat4) && (paramFloat12 >= paramFloat4)) {
/*  876 */       return paramInt1;
/*      */     }
/*  878 */     if ((paramFloat6 <= paramFloat2) && (paramFloat8 <= paramFloat2) && (paramFloat10 <= paramFloat2) && (paramFloat12 <= paramFloat2)) {
/*  879 */       return paramInt1;
/*      */     }
/*  881 */     if ((paramFloat5 <= paramFloat1) && (paramFloat7 <= paramFloat1) && (paramFloat9 <= paramFloat1) && (paramFloat11 <= paramFloat1)) {
/*  882 */       return paramInt1;
/*      */     }
/*  884 */     if ((paramFloat5 >= paramFloat3) && (paramFloat7 >= paramFloat3) && (paramFloat9 >= paramFloat3) && (paramFloat11 >= paramFloat3))
/*      */     {
/*  893 */       if (paramFloat6 < paramFloat12)
/*      */       {
/*  895 */         if ((paramFloat6 <= paramFloat2) && (paramFloat12 > paramFloat2)) paramInt1++;
/*  896 */         if ((paramFloat6 < paramFloat4) && (paramFloat12 >= paramFloat4)) paramInt1++; 
/*      */       }
/*  897 */       else if (paramFloat12 < paramFloat6)
/*      */       {
/*  899 */         if ((paramFloat12 <= paramFloat2) && (paramFloat6 > paramFloat2)) paramInt1--;
/*  900 */         if ((paramFloat12 < paramFloat4) && (paramFloat6 >= paramFloat4)) paramInt1--;
/*      */       }
/*  902 */       return paramInt1;
/*      */     }
/*      */ 
/*  907 */     if (((paramFloat5 > paramFloat1) && (paramFloat5 < paramFloat3) && (paramFloat6 > paramFloat2) && (paramFloat6 < paramFloat4)) || ((paramFloat11 > paramFloat1) && (paramFloat11 < paramFloat3) && (paramFloat12 > paramFloat2) && (paramFloat12 < paramFloat4)))
/*      */     {
/*  910 */       return -2147483648;
/*      */     }
/*      */ 
/*  914 */     if (paramInt2 > 52) {
/*  915 */       return rectCrossingsForLine(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat11, paramFloat12);
/*      */     }
/*      */ 
/*  919 */     float f1 = (paramFloat7 + paramFloat9) / 2.0F;
/*  920 */     float f2 = (paramFloat8 + paramFloat10) / 2.0F;
/*  921 */     paramFloat7 = (paramFloat5 + paramFloat7) / 2.0F;
/*  922 */     paramFloat8 = (paramFloat6 + paramFloat8) / 2.0F;
/*  923 */     paramFloat9 = (paramFloat9 + paramFloat11) / 2.0F;
/*  924 */     paramFloat10 = (paramFloat10 + paramFloat12) / 2.0F;
/*  925 */     float f3 = (paramFloat7 + f1) / 2.0F;
/*  926 */     float f4 = (paramFloat8 + f2) / 2.0F;
/*  927 */     float f5 = (f1 + paramFloat9) / 2.0F;
/*  928 */     float f6 = (f2 + paramFloat10) / 2.0F;
/*  929 */     f1 = (f3 + f5) / 2.0F;
/*  930 */     f2 = (f4 + f6) / 2.0F;
/*  931 */     if ((Float.isNaN(f1)) || (Float.isNaN(f2)))
/*      */     {
/*  935 */       return 0;
/*      */     }
/*  937 */     paramInt1 = rectCrossingsForCubic(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, f3, f4, f1, f2, paramInt2 + 1);
/*      */ 
/*  941 */     if (paramInt1 != -2147483648) {
/*  942 */       paramInt1 = rectCrossingsForCubic(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, f5, f6, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramInt2 + 1);
/*      */     }
/*      */ 
/*  947 */     return paramInt1;
/*      */   }
/*      */ 
/*      */   static boolean intersectsLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/*      */     int j;
/*  958 */     if ((j = outcode(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat7, paramFloat8)) == 0)
/*  959 */       return true;
/*      */     int i;
/*  961 */     while ((i = outcode(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6)) != 0) {
/*  962 */       if ((i & j) != 0) {
/*  963 */         return false;
/*      */       }
/*  965 */       if ((i & 0x5) != 0) {
/*  966 */         if ((i & 0x4) != 0) {
/*  967 */           paramFloat1 += paramFloat3;
/*      */         }
/*  969 */         paramFloat6 += (paramFloat1 - paramFloat5) * (paramFloat8 - paramFloat6) / (paramFloat7 - paramFloat5);
/*  970 */         paramFloat5 = paramFloat1;
/*      */       } else {
/*  972 */         if ((i & 0x8) != 0) {
/*  973 */           paramFloat2 += paramFloat4;
/*      */         }
/*  975 */         paramFloat5 += (paramFloat2 - paramFloat6) * (paramFloat7 - paramFloat5) / (paramFloat8 - paramFloat6);
/*  976 */         paramFloat6 = paramFloat2;
/*      */       }
/*      */     }
/*  979 */     return true;
/*      */   }
/*      */ 
/*      */   static int outcode(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  996 */     int i = 0;
/*  997 */     if (paramFloat3 <= 0.0F)
/*  998 */       i |= 5;
/*  999 */     else if (paramFloat5 < paramFloat1)
/* 1000 */       i |= 1;
/* 1001 */     else if (paramFloat5 > paramFloat1 + paramFloat3) {
/* 1002 */       i |= 4;
/*      */     }
/* 1004 */     if (paramFloat4 <= 0.0F)
/* 1005 */       i |= 10;
/* 1006 */     else if (paramFloat6 < paramFloat2)
/* 1007 */       i |= 2;
/* 1008 */     else if (paramFloat6 > paramFloat2 + paramFloat4) {
/* 1009 */       i |= 8;
/*      */     }
/* 1011 */     return i;
/*      */   }
/*      */ 
/*      */   public static void accumulate(float[] paramArrayOfFloat, Shape paramShape, BaseTransform paramBaseTransform)
/*      */   {
/* 1056 */     PathIterator localPathIterator = paramShape.getPathIterator(paramBaseTransform);
/* 1057 */     float[] arrayOfFloat = new float[6];
/* 1058 */     float f1 = 0.0F; float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F;
/* 1059 */     while (!localPathIterator.isDone())
/*      */     {
/*      */       float f5;
/*      */       float f6;
/* 1060 */       switch (localPathIterator.currentSegment(arrayOfFloat)) {
/*      */       case 0:
/* 1062 */         f1 = arrayOfFloat[0];
/* 1063 */         f2 = arrayOfFloat[1];
/*      */       case 1:
/* 1066 */         f3 = arrayOfFloat[0];
/* 1067 */         f4 = arrayOfFloat[1];
/* 1068 */         if (paramArrayOfFloat[0] > f3) paramArrayOfFloat[0] = f3;
/* 1069 */         if (paramArrayOfFloat[1] > f4) paramArrayOfFloat[1] = f4;
/* 1070 */         if (paramArrayOfFloat[2] < f3) paramArrayOfFloat[2] = f3;
/* 1071 */         if (paramArrayOfFloat[3] < f4) paramArrayOfFloat[3] = f4; break;
/*      */       case 2:
/* 1074 */         f5 = arrayOfFloat[2];
/* 1075 */         f6 = arrayOfFloat[3];
/* 1076 */         if (paramArrayOfFloat[0] > f5) paramArrayOfFloat[0] = f5;
/* 1077 */         if (paramArrayOfFloat[1] > f6) paramArrayOfFloat[1] = f6;
/* 1078 */         if (paramArrayOfFloat[2] < f5) paramArrayOfFloat[2] = f5;
/* 1079 */         if (paramArrayOfFloat[3] < f6) paramArrayOfFloat[3] = f6;
/* 1080 */         if ((paramArrayOfFloat[0] > arrayOfFloat[0]) || (paramArrayOfFloat[2] < arrayOfFloat[0])) {
/* 1081 */           accumulateQuad(paramArrayOfFloat, 0, f3, arrayOfFloat[0], f5);
/*      */         }
/* 1083 */         if ((paramArrayOfFloat[1] > arrayOfFloat[1]) || (paramArrayOfFloat[3] < arrayOfFloat[1])) {
/* 1084 */           accumulateQuad(paramArrayOfFloat, 1, f4, arrayOfFloat[1], f6);
/*      */         }
/* 1086 */         f3 = f5;
/* 1087 */         f4 = f6;
/* 1088 */         break;
/*      */       case 3:
/* 1090 */         f5 = arrayOfFloat[4];
/* 1091 */         f6 = arrayOfFloat[5];
/* 1092 */         if (paramArrayOfFloat[0] > f5) paramArrayOfFloat[0] = f5;
/* 1093 */         if (paramArrayOfFloat[1] > f6) paramArrayOfFloat[1] = f6;
/* 1094 */         if (paramArrayOfFloat[2] < f5) paramArrayOfFloat[2] = f5;
/* 1095 */         if (paramArrayOfFloat[3] < f6) paramArrayOfFloat[3] = f6;
/* 1096 */         if ((paramArrayOfFloat[0] > arrayOfFloat[0]) || (paramArrayOfFloat[2] < arrayOfFloat[0]) || (paramArrayOfFloat[0] > arrayOfFloat[2]) || (paramArrayOfFloat[2] < arrayOfFloat[2]))
/*      */         {
/* 1099 */           accumulateCubic(paramArrayOfFloat, 0, f3, arrayOfFloat[0], arrayOfFloat[2], f5);
/*      */         }
/* 1101 */         if ((paramArrayOfFloat[1] > arrayOfFloat[1]) || (paramArrayOfFloat[3] < arrayOfFloat[1]) || (paramArrayOfFloat[1] > arrayOfFloat[3]) || (paramArrayOfFloat[3] < arrayOfFloat[3]))
/*      */         {
/* 1104 */           accumulateCubic(paramArrayOfFloat, 1, f4, arrayOfFloat[1], arrayOfFloat[3], f6);
/*      */         }
/* 1106 */         f3 = f5;
/* 1107 */         f4 = f6;
/* 1108 */         break;
/*      */       case 4:
/* 1110 */         f3 = f1;
/* 1111 */         f4 = f2;
/*      */       }
/*      */ 
/* 1114 */       localPathIterator.next();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void accumulateQuad(float[] paramArrayOfFloat, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
/*      */   {
/* 1133 */     float f1 = paramFloat1 - paramFloat2;
/* 1134 */     float f2 = paramFloat3 - paramFloat2 + f1;
/* 1135 */     if (f2 != 0.0F) {
/* 1136 */       float f3 = f1 / f2;
/* 1137 */       if ((f3 > 0.0F) && (f3 < 1.0F)) {
/* 1138 */         float f4 = 1.0F - f3;
/* 1139 */         float f5 = paramFloat1 * f4 * f4 + 2.0F * paramFloat2 * f3 * f4 + paramFloat3 * f3 * f3;
/* 1140 */         if (paramArrayOfFloat[paramInt] > f5) paramArrayOfFloat[paramInt] = f5;
/* 1141 */         if (paramArrayOfFloat[(paramInt + 2)] < f5) paramArrayOfFloat[(paramInt + 2)] = f5;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void accumulateCubic(float[] paramArrayOfFloat, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/* 1162 */     float f1 = paramFloat2 - paramFloat1;
/* 1163 */     float f2 = 2.0F * (paramFloat3 - paramFloat2 - f1);
/* 1164 */     float f3 = paramFloat4 - paramFloat3 - f2 - f1;
/* 1165 */     if (f3 == 0.0F)
/*      */     {
/* 1167 */       if (f2 == 0.0F)
/*      */       {
/* 1169 */         return;
/*      */       }
/* 1171 */       accumulateCubic(paramArrayOfFloat, paramInt, -f1 / f2, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */     }
/*      */     else {
/* 1174 */       float f4 = f2 * f2 - 4.0F * f3 * f1;
/* 1175 */       if (f4 < 0.0F)
/*      */       {
/* 1177 */         return;
/*      */       }
/* 1179 */       f4 = (float)Math.sqrt(f4);
/*      */ 
/* 1185 */       if (f2 < 0.0F) {
/* 1186 */         f4 = -f4;
/*      */       }
/* 1188 */       float f5 = (f2 + f4) / -2.0F;
/*      */ 
/* 1190 */       accumulateCubic(paramArrayOfFloat, paramInt, f5 / f3, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/* 1191 */       if (f5 != 0.0F)
/* 1192 */         accumulateCubic(paramArrayOfFloat, paramInt, f1 / f5, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void accumulateCubic(float[] paramArrayOfFloat, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*      */   {
/* 1200 */     if ((paramFloat1 > 0.0F) && (paramFloat1 < 1.0F)) {
/* 1201 */       float f1 = 1.0F - paramFloat1;
/* 1202 */       float f2 = paramFloat2 * f1 * f1 * f1 + 3.0F * paramFloat3 * paramFloat1 * f1 * f1 + 3.0F * paramFloat4 * paramFloat1 * paramFloat1 * f1 + paramFloat5 * paramFloat1 * paramFloat1 * paramFloat1;
/*      */ 
/* 1206 */       if (paramArrayOfFloat[paramInt] > f2) paramArrayOfFloat[paramInt] = f2;
/* 1207 */       if (paramArrayOfFloat[(paramInt + 2)] < f2) paramArrayOfFloat[(paramInt + 2)] = f2;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Shape
 * JD-Core Version:    0.6.2
 */