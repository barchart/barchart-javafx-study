/*      */ package com.sun.openpisces;
/*      */ 
/*      */ import com.sun.javafx.geom.PathConsumer2D;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ public final class Stroker
/*      */   implements PathConsumer2D
/*      */ {
/*      */   private static final int MOVE_TO = 0;
/*      */   private static final int DRAWING_OP_TO = 1;
/*      */   private static final int CLOSE = 2;
/*      */   public static final int JOIN_MITER = 0;
/*      */   public static final int JOIN_ROUND = 1;
/*      */   public static final int JOIN_BEVEL = 2;
/*      */   public static final int CAP_BUTT = 0;
/*      */   public static final int CAP_ROUND = 1;
/*      */   public static final int CAP_SQUARE = 2;
/*      */   private PathConsumer2D out;
/*      */   private int capStyle;
/*      */   private int joinStyle;
/*      */   private float lineWidth2;
/*   78 */   private final float[][] offset = new float[3][2];
/*   79 */   private final float[] miter = new float[2];
/*      */   private float miterLimitSq;
/*      */   private int prev;
/*      */   private float sx0;
/*      */   private float sy0;
/*      */   private float sdx;
/*      */   private float sdy;
/*      */   private float cx0;
/*      */   private float cy0;
/*      */   private float cdx;
/*      */   private float cdy;
/*      */   private float smx;
/*      */   private float smy;
/*      */   private float cmx;
/*      */   private float cmy;
/*   96 */   private final PolyStack reverse = new PolyStack();
/*      */   private static final float ROUND_JOIN_THRESHOLD = 0.01525879F;
/*  807 */   private float[] middle = new float[88];
/*  808 */   private float[] lp = new float[8];
/*  809 */   private float[] rp = new float[8];
/*      */   private static final int MAX_N_CURVES = 11;
/*  811 */   private float[] subdivTs = new float[10];
/*      */ 
/*  925 */   private static Curve c = new Curve();
/*      */ 
/*      */   public Stroker(PathConsumer2D paramPathConsumer2D, float paramFloat1, int paramInt1, int paramInt2, float paramFloat2)
/*      */   {
/*  117 */     this(paramPathConsumer2D);
/*      */ 
/*  119 */     reset(paramFloat1, paramInt1, paramInt2, paramFloat2);
/*      */   }
/*      */ 
/*      */   public Stroker(PathConsumer2D paramPathConsumer2D) {
/*  123 */     setConsumer(paramPathConsumer2D);
/*      */   }
/*      */ 
/*      */   public void setConsumer(PathConsumer2D paramPathConsumer2D) {
/*  127 */     this.out = paramPathConsumer2D;
/*      */   }
/*      */ 
/*      */   public void reset(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2)
/*      */   {
/*  132 */     this.lineWidth2 = (paramFloat1 / 2.0F);
/*  133 */     this.capStyle = paramInt1;
/*  134 */     this.joinStyle = paramInt2;
/*      */ 
/*  136 */     float f = paramFloat2 * this.lineWidth2;
/*  137 */     this.miterLimitSq = (f * f);
/*      */ 
/*  139 */     this.prev = 2;
/*      */   }
/*      */ 
/*      */   private static void computeOffset(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOfFloat)
/*      */   {
/*  145 */     float f = (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/*  146 */     if (f == 0.0F)
/*      */     {
/*      */       float tmp26_25 = 0.0F; paramArrayOfFloat[1] = tmp26_25; paramArrayOfFloat[0] = tmp26_25;
/*      */     } else {
/*  149 */       paramArrayOfFloat[0] = (paramFloat2 * paramFloat3 / f);
/*  150 */       paramArrayOfFloat[1] = (-(paramFloat1 * paramFloat3) / f);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static boolean isCW(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  165 */     return paramFloat1 * paramFloat4 <= paramFloat2 * paramFloat3;
/*      */   }
/*      */ 
/*      */   private void drawRoundJoin(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean, float paramFloat7)
/*      */   {
/*  178 */     if (((paramFloat3 == 0.0F) && (paramFloat4 == 0.0F)) || ((paramFloat5 == 0.0F) && (paramFloat6 == 0.0F))) {
/*  179 */       return;
/*      */     }
/*      */ 
/*  182 */     float f1 = paramFloat3 - paramFloat5;
/*  183 */     float f2 = paramFloat4 - paramFloat6;
/*  184 */     float f3 = f1 * f1 + f2 * f2;
/*  185 */     if (f3 < paramFloat7) {
/*  186 */       return;
/*      */     }
/*      */ 
/*  189 */     if (paramBoolean) {
/*  190 */       paramFloat3 = -paramFloat3;
/*  191 */       paramFloat4 = -paramFloat4;
/*  192 */       paramFloat5 = -paramFloat5;
/*  193 */       paramFloat6 = -paramFloat6;
/*      */     }
/*  195 */     drawRoundJoin(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramBoolean);
/*      */   }
/*      */ 
/*      */   private void drawRoundJoin(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean)
/*      */   {
/*  206 */     double d = paramFloat3 * paramFloat5 + paramFloat4 * paramFloat6;
/*      */ 
/*  210 */     int i = d >= 0.0D ? 1 : 2;
/*      */ 
/*  212 */     switch (i) {
/*      */     case 1:
/*  214 */       drawBezApproxForArc(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramBoolean);
/*  215 */       break;
/*      */     case 2:
/*  231 */       float f1 = paramFloat6 - paramFloat4; float f2 = paramFloat3 - paramFloat5;
/*  232 */       float f3 = (float)Math.sqrt(f1 * f1 + f2 * f2);
/*  233 */       float f4 = this.lineWidth2 / f3;
/*  234 */       float f5 = f1 * f4; float f6 = f2 * f4;
/*      */ 
/*  239 */       if (paramBoolean) {
/*  240 */         f5 = -f5;
/*  241 */         f6 = -f6;
/*      */       }
/*  243 */       drawBezApproxForArc(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f5, f6, paramBoolean);
/*  244 */       drawBezApproxForArc(paramFloat1, paramFloat2, f5, f6, paramFloat5, paramFloat6, paramBoolean);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void drawBezApproxForArc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean)
/*      */   {
/*  255 */     float f1 = (paramFloat3 * paramFloat5 + paramFloat4 * paramFloat6) / (2.0F * this.lineWidth2 * this.lineWidth2);
/*      */ 
/*  261 */     float f2 = (float)(1.333333333333333D * Math.sqrt(0.5D - f1) / (1.0D + Math.sqrt(f1 + 0.5D)));
/*      */ 
/*  264 */     if (paramBoolean) {
/*  265 */       f2 = -f2;
/*      */     }
/*  267 */     float f3 = paramFloat1 + paramFloat3;
/*  268 */     float f4 = paramFloat2 + paramFloat4;
/*  269 */     float f5 = f3 - f2 * paramFloat4;
/*  270 */     float f6 = f4 + f2 * paramFloat3;
/*      */ 
/*  272 */     float f7 = paramFloat1 + paramFloat5;
/*  273 */     float f8 = paramFloat2 + paramFloat6;
/*  274 */     float f9 = f7 + f2 * paramFloat6;
/*  275 */     float f10 = f8 - f2 * paramFloat5;
/*      */ 
/*  277 */     emitCurveTo(f3, f4, f5, f6, f9, f10, f7, f8, paramBoolean);
/*      */   }
/*      */ 
/*      */   private void drawRoundCap(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  287 */     emitCurveTo(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4, paramFloat1 + paramFloat3 - 0.5522848F * paramFloat4, paramFloat2 + paramFloat4 + 0.5522848F * paramFloat3, paramFloat1 - paramFloat4 + 0.5522848F * paramFloat3, paramFloat2 + paramFloat3 + 0.5522848F * paramFloat4, paramFloat1 - paramFloat4, paramFloat2 + paramFloat3, false);
/*      */ 
/*  292 */     emitCurveTo(paramFloat1 - paramFloat4, paramFloat2 + paramFloat3, paramFloat1 - paramFloat4 - 0.5522848F * paramFloat3, paramFloat2 + paramFloat3 - 0.5522848F * paramFloat4, paramFloat1 - paramFloat3 - 0.5522848F * paramFloat4, paramFloat2 - paramFloat4 + 0.5522848F * paramFloat3, paramFloat1 - paramFloat3, paramFloat2 - paramFloat4, false);
/*      */   }
/*      */ 
/*      */   private void computeMiter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float[] paramArrayOfFloat, int paramInt)
/*      */   {
/*  307 */     float f1 = paramFloat3 - paramFloat1;
/*  308 */     float f2 = paramFloat4 - paramFloat2;
/*  309 */     float f3 = paramFloat7 - paramFloat5;
/*  310 */     float f4 = paramFloat8 - paramFloat6;
/*      */ 
/*  321 */     float f5 = f1 * f4 - f3 * f2;
/*  322 */     float f6 = f3 * (paramFloat2 - paramFloat6) - f4 * (paramFloat1 - paramFloat5);
/*  323 */     f6 /= f5;
/*  324 */     paramArrayOfFloat[(paramInt++)] = (paramFloat1 + f6 * f1);
/*  325 */     paramArrayOfFloat[paramInt] = (paramFloat2 + f6 * f2);
/*      */   }
/*      */ 
/*      */   private void safecomputeMiter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float[] paramArrayOfFloat, int paramInt)
/*      */   {
/*  336 */     float f1 = paramFloat3 - paramFloat1;
/*  337 */     float f2 = paramFloat4 - paramFloat2;
/*  338 */     float f3 = paramFloat7 - paramFloat5;
/*  339 */     float f4 = paramFloat8 - paramFloat6;
/*      */ 
/*  350 */     float f5 = f1 * f4 - f3 * f2;
/*  351 */     if (f5 == 0.0F) {
/*  352 */       paramArrayOfFloat[(paramInt++)] = ((paramFloat1 + paramFloat5) / 2.0F);
/*  353 */       paramArrayOfFloat[paramInt] = ((paramFloat2 + paramFloat6) / 2.0F);
/*  354 */       return;
/*      */     }
/*  356 */     float f6 = f3 * (paramFloat2 - paramFloat6) - f4 * (paramFloat1 - paramFloat5);
/*  357 */     f6 /= f5;
/*  358 */     paramArrayOfFloat[(paramInt++)] = (paramFloat1 + f6 * f1);
/*  359 */     paramArrayOfFloat[paramInt] = (paramFloat2 + f6 * f2);
/*      */   }
/*      */ 
/*      */   private void drawMiter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, boolean paramBoolean)
/*      */   {
/*  368 */     if (((paramFloat9 == paramFloat7) && (paramFloat10 == paramFloat8)) || ((paramFloat1 == 0.0F) && (paramFloat2 == 0.0F)) || ((paramFloat5 == 0.0F) && (paramFloat6 == 0.0F)))
/*      */     {
/*  371 */       return;
/*      */     }
/*      */ 
/*  374 */     if (paramBoolean) {
/*  375 */       paramFloat7 = -paramFloat7;
/*  376 */       paramFloat8 = -paramFloat8;
/*  377 */       paramFloat9 = -paramFloat9;
/*  378 */       paramFloat10 = -paramFloat10;
/*      */     }
/*      */ 
/*  381 */     computeMiter(paramFloat3 - paramFloat1 + paramFloat7, paramFloat4 - paramFloat2 + paramFloat8, paramFloat3 + paramFloat7, paramFloat4 + paramFloat8, paramFloat5 + paramFloat3 + paramFloat9, paramFloat6 + paramFloat4 + paramFloat10, paramFloat3 + paramFloat9, paramFloat4 + paramFloat10, this.miter, 0);
/*      */ 
/*  385 */     float f = (this.miter[0] - paramFloat3) * (this.miter[0] - paramFloat3) + (this.miter[1] - paramFloat4) * (this.miter[1] - paramFloat4);
/*      */ 
/*  387 */     if (f < this.miterLimitSq)
/*  388 */       emitLineTo(this.miter[0], this.miter[1], paramBoolean);
/*      */   }
/*      */ 
/*      */   public void moveTo(float paramFloat1, float paramFloat2)
/*      */   {
/*  393 */     if (this.prev == 1) {
/*  394 */       finish();
/*      */     }
/*  396 */     this.sx0 = (this.cx0 = paramFloat1);
/*  397 */     this.sy0 = (this.cy0 = paramFloat2);
/*  398 */     this.cdx = (this.sdx = 1.0F);
/*  399 */     this.cdy = (this.sdy = 0.0F);
/*  400 */     this.prev = 0;
/*      */   }
/*      */ 
/*      */   public void lineTo(float paramFloat1, float paramFloat2) {
/*  404 */     float f1 = paramFloat1 - this.cx0;
/*  405 */     float f2 = paramFloat2 - this.cy0;
/*  406 */     if ((f1 == 0.0F) && (f2 == 0.0F)) {
/*  407 */       f1 = 1.0F;
/*      */     }
/*  409 */     computeOffset(f1, f2, this.lineWidth2, this.offset[0]);
/*  410 */     float f3 = this.offset[0][0];
/*  411 */     float f4 = this.offset[0][1];
/*      */ 
/*  413 */     drawJoin(this.cdx, this.cdy, this.cx0, this.cy0, f1, f2, this.cmx, this.cmy, f3, f4);
/*      */ 
/*  415 */     emitLineTo(this.cx0 + f3, this.cy0 + f4);
/*  416 */     emitLineTo(paramFloat1 + f3, paramFloat2 + f4);
/*      */ 
/*  418 */     emitLineTo(this.cx0 - f3, this.cy0 - f4, true);
/*  419 */     emitLineTo(paramFloat1 - f3, paramFloat2 - f4, true);
/*      */ 
/*  421 */     this.cmx = f3;
/*  422 */     this.cmy = f4;
/*  423 */     this.cdx = f1;
/*  424 */     this.cdy = f2;
/*  425 */     this.cx0 = paramFloat1;
/*  426 */     this.cy0 = paramFloat2;
/*  427 */     this.prev = 1;
/*      */   }
/*      */ 
/*      */   public void closePath() {
/*  431 */     if (this.prev != 1) {
/*  432 */       if (this.prev == 2) {
/*  433 */         return;
/*      */       }
/*  435 */       emitMoveTo(this.cx0, this.cy0 - this.lineWidth2);
/*  436 */       this.cmx = (this.smx = 0.0F);
/*  437 */       this.cmy = (this.smy = -this.lineWidth2);
/*  438 */       this.cdx = (this.sdx = 1.0F);
/*  439 */       this.cdy = (this.sdy = 0.0F);
/*  440 */       finish();
/*  441 */       return;
/*      */     }
/*      */ 
/*  444 */     if ((this.cx0 != this.sx0) || (this.cy0 != this.sy0)) {
/*  445 */       lineTo(this.sx0, this.sy0);
/*      */     }
/*      */ 
/*  448 */     drawJoin(this.cdx, this.cdy, this.cx0, this.cy0, this.sdx, this.sdy, this.cmx, this.cmy, this.smx, this.smy);
/*      */ 
/*  450 */     emitLineTo(this.sx0 + this.smx, this.sy0 + this.smy);
/*      */ 
/*  452 */     emitMoveTo(this.sx0 - this.smx, this.sy0 - this.smy);
/*  453 */     emitReverse();
/*      */ 
/*  455 */     this.prev = 2;
/*  456 */     emitClose();
/*      */   }
/*      */ 
/*      */   private void emitReverse() {
/*  460 */     while (!this.reverse.isEmpty())
/*  461 */       this.reverse.pop(this.out);
/*      */   }
/*      */ 
/*      */   public void pathDone()
/*      */   {
/*  466 */     if (this.prev == 1) {
/*  467 */       finish();
/*      */     }
/*      */ 
/*  470 */     this.out.pathDone();
/*      */ 
/*  473 */     this.prev = 2;
/*      */   }
/*      */ 
/*      */   private void finish() {
/*  477 */     if (this.capStyle == 1) {
/*  478 */       drawRoundCap(this.cx0, this.cy0, this.cmx, this.cmy);
/*  479 */     } else if (this.capStyle == 2) {
/*  480 */       emitLineTo(this.cx0 - this.cmy + this.cmx, this.cy0 + this.cmx + this.cmy);
/*  481 */       emitLineTo(this.cx0 - this.cmy - this.cmx, this.cy0 + this.cmx - this.cmy);
/*      */     }
/*      */ 
/*  484 */     emitReverse();
/*      */ 
/*  486 */     if (this.capStyle == 1) {
/*  487 */       drawRoundCap(this.sx0, this.sy0, -this.smx, -this.smy);
/*  488 */     } else if (this.capStyle == 2) {
/*  489 */       emitLineTo(this.sx0 + this.smy - this.smx, this.sy0 - this.smx - this.smy);
/*  490 */       emitLineTo(this.sx0 + this.smy + this.smx, this.sy0 - this.smx + this.smy);
/*      */     }
/*      */ 
/*  493 */     emitClose();
/*      */   }
/*      */ 
/*      */   private void emitMoveTo(float paramFloat1, float paramFloat2) {
/*  497 */     this.out.moveTo(paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   private void emitLineTo(float paramFloat1, float paramFloat2) {
/*  501 */     this.out.lineTo(paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   private void emitLineTo(float paramFloat1, float paramFloat2, boolean paramBoolean)
/*      */   {
/*  507 */     if (paramBoolean)
/*  508 */       this.reverse.pushLine(paramFloat1, paramFloat2);
/*      */     else
/*  510 */       emitLineTo(paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   private void emitQuadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean)
/*      */   {
/*  518 */     if (paramBoolean)
/*  519 */       this.reverse.pushQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */     else
/*  521 */       this.out.quadTo(paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*      */   }
/*      */ 
/*      */   private void emitCurveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, boolean paramBoolean)
/*      */   {
/*  530 */     if (paramBoolean)
/*  531 */       this.reverse.pushCubic(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*      */     else
/*  533 */       this.out.curveTo(paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*      */   }
/*      */ 
/*      */   private void emitClose()
/*      */   {
/*  538 */     this.out.closePath();
/*      */   }
/*      */ 
/*      */   private void drawJoin(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10)
/*      */   {
/*  547 */     if (this.prev != 1) {
/*  548 */       emitMoveTo(paramFloat3 + paramFloat9, paramFloat4 + paramFloat10);
/*  549 */       this.sdx = paramFloat5;
/*  550 */       this.sdy = paramFloat6;
/*  551 */       this.smx = paramFloat9;
/*  552 */       this.smy = paramFloat10;
/*      */     } else {
/*  554 */       boolean bool = isCW(paramFloat1, paramFloat2, paramFloat5, paramFloat6);
/*  555 */       if (this.joinStyle == 0)
/*  556 */         drawMiter(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, bool);
/*  557 */       else if (this.joinStyle == 1) {
/*  558 */         drawRoundJoin(paramFloat3, paramFloat4, paramFloat7, paramFloat8, paramFloat9, paramFloat10, bool, 0.01525879F);
/*      */       }
/*      */ 
/*  563 */       emitLineTo(paramFloat3, paramFloat4, !bool);
/*      */     }
/*  565 */     this.prev = 1;
/*      */   }
/*      */ 
/*      */   private static boolean within(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*      */   {
/*  572 */     assert (paramFloat5 > 0.0F) : "";
/*      */ 
/*  575 */     return (Helpers.within(paramFloat1, paramFloat3, paramFloat5)) && (Helpers.within(paramFloat2, paramFloat4, paramFloat5));
/*      */   }
/*      */ 
/*      */   private void getLineOffsets(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*      */   {
/*  582 */     computeOffset(paramFloat3 - paramFloat1, paramFloat4 - paramFloat2, this.lineWidth2, this.offset[0]);
/*  583 */     paramArrayOfFloat1[0] = (paramFloat1 + this.offset[0][0]);
/*  584 */     paramArrayOfFloat1[1] = (paramFloat2 + this.offset[0][1]);
/*  585 */     paramArrayOfFloat1[2] = (paramFloat3 + this.offset[0][0]);
/*  586 */     paramArrayOfFloat1[3] = (paramFloat4 + this.offset[0][1]);
/*  587 */     paramArrayOfFloat2[0] = (paramFloat1 - this.offset[0][0]);
/*  588 */     paramArrayOfFloat2[1] = (paramFloat2 - this.offset[0][1]);
/*  589 */     paramArrayOfFloat2[2] = (paramFloat3 - this.offset[0][0]);
/*  590 */     paramArrayOfFloat2[3] = (paramFloat4 - this.offset[0][1]);
/*      */   }
/*      */ 
/*      */   private int computeOffsetCubic(float[] paramArrayOfFloat1, int paramInt, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3)
/*      */   {
/*  603 */     float f1 = paramArrayOfFloat1[(paramInt + 0)]; float f2 = paramArrayOfFloat1[(paramInt + 1)];
/*  604 */     float f3 = paramArrayOfFloat1[(paramInt + 2)]; float f4 = paramArrayOfFloat1[(paramInt + 3)];
/*  605 */     float f5 = paramArrayOfFloat1[(paramInt + 4)]; float f6 = paramArrayOfFloat1[(paramInt + 5)];
/*  606 */     float f7 = paramArrayOfFloat1[(paramInt + 6)]; float f8 = paramArrayOfFloat1[(paramInt + 7)];
/*      */ 
/*  608 */     float f9 = f7 - f5;
/*  609 */     float f10 = f8 - f6;
/*  610 */     float f11 = f3 - f1;
/*  611 */     float f12 = f4 - f2;
/*      */ 
/*  615 */     boolean bool1 = within(f1, f2, f3, f4, 6.0F * Math.ulp(f4));
/*  616 */     boolean bool2 = within(f5, f6, f7, f8, 6.0F * Math.ulp(f8));
/*  617 */     if ((bool1) && (bool2)) {
/*  618 */       getLineOffsets(f1, f2, f7, f8, paramArrayOfFloat2, paramArrayOfFloat3);
/*  619 */       return 4;
/*  620 */     }if (bool1) {
/*  621 */       f11 = f5 - f1;
/*  622 */       f12 = f6 - f2;
/*  623 */     } else if (bool2) {
/*  624 */       f9 = f7 - f3;
/*  625 */       f10 = f8 - f4;
/*      */     }
/*      */ 
/*  629 */     float f13 = f11 * f9 + f12 * f10;
/*  630 */     f13 *= f13;
/*  631 */     float f14 = f11 * f11 + f12 * f12; float f15 = f9 * f9 + f10 * f10;
/*  632 */     if (Helpers.within(f13, f14 * f15, 4.0F * Math.ulp(f13))) {
/*  633 */       getLineOffsets(f1, f2, f7, f8, paramArrayOfFloat2, paramArrayOfFloat3);
/*  634 */       return 4;
/*      */     }
/*      */ 
/*  684 */     float f16 = 0.125F * (f1 + 3.0F * (f3 + f5) + f7);
/*  685 */     float f17 = 0.125F * (f2 + 3.0F * (f4 + f6) + f8);
/*      */ 
/*  688 */     float f18 = f5 + f7 - f1 - f3; float f19 = f6 + f8 - f2 - f4;
/*      */ 
/*  693 */     computeOffset(f11, f12, this.lineWidth2, this.offset[0]);
/*  694 */     computeOffset(f18, f19, this.lineWidth2, this.offset[1]);
/*  695 */     computeOffset(f9, f10, this.lineWidth2, this.offset[2]);
/*  696 */     float f20 = f1 + this.offset[0][0];
/*  697 */     float f21 = f2 + this.offset[0][1];
/*  698 */     float f22 = f16 + this.offset[1][0];
/*  699 */     float f23 = f17 + this.offset[1][1];
/*  700 */     float f24 = f7 + this.offset[2][0];
/*  701 */     float f25 = f8 + this.offset[2][1];
/*      */ 
/*  703 */     float f26 = 4.0F / (3.0F * (f11 * f10 - f12 * f9));
/*      */ 
/*  705 */     float f27 = 2.0F * f22 - f20 - f24;
/*  706 */     float f28 = 2.0F * f23 - f21 - f25;
/*  707 */     float f29 = f26 * (f10 * f27 - f9 * f28);
/*  708 */     float f30 = f26 * (f11 * f28 - f12 * f27);
/*      */ 
/*  711 */     float f31 = f20 + f29 * f11;
/*  712 */     float f32 = f21 + f29 * f12;
/*  713 */     float f33 = f24 + f30 * f9;
/*  714 */     float f34 = f25 + f30 * f10;
/*      */ 
/*  716 */     paramArrayOfFloat2[0] = f20; paramArrayOfFloat2[1] = f21;
/*  717 */     paramArrayOfFloat2[2] = f31; paramArrayOfFloat2[3] = f32;
/*  718 */     paramArrayOfFloat2[4] = f33; paramArrayOfFloat2[5] = f34;
/*  719 */     paramArrayOfFloat2[6] = f24; paramArrayOfFloat2[7] = f25;
/*      */ 
/*  721 */     f20 = f1 - this.offset[0][0]; f21 = f2 - this.offset[0][1];
/*  722 */     f22 -= 2.0F * this.offset[1][0]; f23 -= 2.0F * this.offset[1][1];
/*  723 */     f24 = f7 - this.offset[2][0]; f25 = f8 - this.offset[2][1];
/*      */ 
/*  725 */     f27 = 2.0F * f22 - f20 - f24;
/*  726 */     f28 = 2.0F * f23 - f21 - f25;
/*  727 */     f29 = f26 * (f10 * f27 - f9 * f28);
/*  728 */     f30 = f26 * (f11 * f28 - f12 * f27);
/*      */ 
/*  730 */     f31 = f20 + f29 * f11;
/*  731 */     f32 = f21 + f29 * f12;
/*  732 */     f33 = f24 + f30 * f9;
/*  733 */     f34 = f25 + f30 * f10;
/*      */ 
/*  735 */     paramArrayOfFloat3[0] = f20; paramArrayOfFloat3[1] = f21;
/*  736 */     paramArrayOfFloat3[2] = f31; paramArrayOfFloat3[3] = f32;
/*  737 */     paramArrayOfFloat3[4] = f33; paramArrayOfFloat3[5] = f34;
/*  738 */     paramArrayOfFloat3[6] = f24; paramArrayOfFloat3[7] = f25;
/*  739 */     return 8;
/*      */   }
/*      */ 
/*      */   private int computeOffsetQuad(float[] paramArrayOfFloat1, int paramInt, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3)
/*      */   {
/*  748 */     float f1 = paramArrayOfFloat1[(paramInt + 0)]; float f2 = paramArrayOfFloat1[(paramInt + 1)];
/*  749 */     float f3 = paramArrayOfFloat1[(paramInt + 2)]; float f4 = paramArrayOfFloat1[(paramInt + 3)];
/*  750 */     float f5 = paramArrayOfFloat1[(paramInt + 4)]; float f6 = paramArrayOfFloat1[(paramInt + 5)];
/*      */ 
/*  752 */     float f7 = f5 - f3;
/*  753 */     float f8 = f6 - f4;
/*  754 */     float f9 = f3 - f1;
/*  755 */     float f10 = f4 - f2;
/*      */ 
/*  767 */     boolean bool1 = within(f1, f2, f3, f4, 6.0F * Math.ulp(f4));
/*  768 */     boolean bool2 = within(f3, f4, f5, f6, 6.0F * Math.ulp(f6));
/*  769 */     if ((bool1) || (bool2)) {
/*  770 */       getLineOffsets(f1, f2, f5, f6, paramArrayOfFloat2, paramArrayOfFloat3);
/*  771 */       return 4;
/*      */     }
/*      */ 
/*  775 */     float f11 = f9 * f7 + f10 * f8;
/*  776 */     f11 *= f11;
/*  777 */     float f12 = f9 * f9 + f10 * f10; float f13 = f7 * f7 + f8 * f8;
/*  778 */     if (Helpers.within(f11, f12 * f13, 4.0F * Math.ulp(f11))) {
/*  779 */       getLineOffsets(f1, f2, f5, f6, paramArrayOfFloat2, paramArrayOfFloat3);
/*  780 */       return 4;
/*      */     }
/*      */ 
/*  786 */     computeOffset(f9, f10, this.lineWidth2, this.offset[0]);
/*  787 */     computeOffset(f7, f8, this.lineWidth2, this.offset[1]);
/*  788 */     float f14 = f1 + this.offset[0][0];
/*  789 */     float f15 = f2 + this.offset[0][1];
/*  790 */     float f16 = f5 + this.offset[1][0];
/*  791 */     float f17 = f6 + this.offset[1][1];
/*      */ 
/*  793 */     safecomputeMiter(f14, f15, f14 + f9, f15 + f10, f16, f17, f16 - f7, f17 - f8, paramArrayOfFloat2, 2);
/*  794 */     paramArrayOfFloat2[0] = f14; paramArrayOfFloat2[1] = f15;
/*  795 */     paramArrayOfFloat2[4] = f16; paramArrayOfFloat2[5] = f17;
/*  796 */     f14 = f1 - this.offset[0][0]; f15 = f2 - this.offset[0][1];
/*  797 */     f16 = f5 - this.offset[1][0]; f17 = f6 - this.offset[1][1];
/*  798 */     safecomputeMiter(f14, f15, f14 + f9, f15 + f10, f16, f17, f16 - f7, f17 - f8, paramArrayOfFloat3, 2);
/*  799 */     paramArrayOfFloat3[0] = f14; paramArrayOfFloat3[1] = f15;
/*  800 */     paramArrayOfFloat3[4] = f16; paramArrayOfFloat3[5] = f17;
/*  801 */     return 6;
/*      */   }
/*      */ 
/*      */   private static int findSubdivPoints(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int paramInt, float paramFloat)
/*      */   {
/*  929 */     float f1 = paramArrayOfFloat1[2] - paramArrayOfFloat1[0];
/*  930 */     float f2 = paramArrayOfFloat1[3] - paramArrayOfFloat1[1];
/*      */ 
/*  933 */     if ((f2 != 0.0F) && (f1 != 0.0F))
/*      */     {
/*  937 */       float f3 = (float)Math.sqrt(f1 * f1 + f2 * f2);
/*  938 */       float f4 = f1 / f3;
/*  939 */       float f5 = f2 / f3;
/*  940 */       float f6 = f4 * paramArrayOfFloat1[0] + f5 * paramArrayOfFloat1[1];
/*  941 */       float f7 = f4 * paramArrayOfFloat1[1] - f5 * paramArrayOfFloat1[0];
/*  942 */       float f8 = f4 * paramArrayOfFloat1[2] + f5 * paramArrayOfFloat1[3];
/*  943 */       float f9 = f4 * paramArrayOfFloat1[3] - f5 * paramArrayOfFloat1[2];
/*  944 */       float f10 = f4 * paramArrayOfFloat1[4] + f5 * paramArrayOfFloat1[5];
/*  945 */       float f11 = f4 * paramArrayOfFloat1[5] - f5 * paramArrayOfFloat1[4];
/*  946 */       switch (paramInt) {
/*      */       case 8:
/*  948 */         float f12 = f4 * paramArrayOfFloat1[6] + f5 * paramArrayOfFloat1[7];
/*  949 */         float f13 = f4 * paramArrayOfFloat1[7] - f5 * paramArrayOfFloat1[6];
/*  950 */         c.set(f6, f7, f8, f9, f10, f11, f12, f13);
/*  951 */         break;
/*      */       case 6:
/*  953 */         c.set(f6, f7, f8, f9, f10, f11);
/*      */       }
/*      */     }
/*      */     else {
/*  957 */       c.set(paramArrayOfFloat1, paramInt);
/*      */     }
/*      */ 
/*  960 */     int i = 0;
/*      */ 
/*  963 */     i += c.dxRoots(paramArrayOfFloat2, i);
/*  964 */     i += c.dyRoots(paramArrayOfFloat2, i);
/*      */ 
/*  966 */     if (paramInt == 8)
/*      */     {
/*  968 */       i += c.infPoints(paramArrayOfFloat2, i);
/*      */     }
/*      */ 
/*  973 */     i += c.rootsOfROCMinusW(paramArrayOfFloat2, i, paramFloat, 1.0E-04F);
/*      */ 
/*  975 */     i = Helpers.filterOutNotInAB(paramArrayOfFloat2, 0, i, 1.0E-04F, 0.9999F);
/*  976 */     Helpers.isort(paramArrayOfFloat2, 0, i);
/*  977 */     return i;
/*      */   }
/*      */ 
/*      */   public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  984 */     this.middle[0] = this.cx0; this.middle[1] = this.cy0;
/*  985 */     this.middle[2] = paramFloat1; this.middle[3] = paramFloat2;
/*  986 */     this.middle[4] = paramFloat3; this.middle[5] = paramFloat4;
/*  987 */     this.middle[6] = paramFloat5; this.middle[7] = paramFloat6;
/*      */ 
/*  993 */     float f1 = this.middle[6]; float f2 = this.middle[7];
/*  994 */     float f3 = this.middle[2] - this.middle[0];
/*  995 */     float f4 = this.middle[3] - this.middle[1];
/*  996 */     float f5 = this.middle[6] - this.middle[4];
/*  997 */     float f6 = this.middle[7] - this.middle[5];
/*      */ 
/*  999 */     int i = (f3 == 0.0F) && (f4 == 0.0F) ? 1 : 0;
/* 1000 */     int j = (f5 == 0.0F) && (f6 == 0.0F) ? 1 : 0;
/* 1001 */     if (i != 0) {
/* 1002 */       f3 = this.middle[4] - this.middle[0];
/* 1003 */       f4 = this.middle[5] - this.middle[1];
/* 1004 */       if ((f3 == 0.0F) && (f4 == 0.0F)) {
/* 1005 */         f3 = this.middle[6] - this.middle[0];
/* 1006 */         f4 = this.middle[7] - this.middle[1];
/*      */       }
/*      */     }
/* 1009 */     if (j != 0) {
/* 1010 */       f5 = this.middle[6] - this.middle[2];
/* 1011 */       f6 = this.middle[7] - this.middle[3];
/* 1012 */       if ((f5 == 0.0F) && (f6 == 0.0F)) {
/* 1013 */         f5 = this.middle[6] - this.middle[0];
/* 1014 */         f6 = this.middle[7] - this.middle[1];
/*      */       }
/*      */     }
/* 1017 */     if ((f3 == 0.0F) && (f4 == 0.0F))
/*      */     {
/* 1019 */       lineTo(this.middle[0], this.middle[1]);
/* 1020 */       return;
/*      */     }
/*      */ 
/* 1025 */     if ((Math.abs(f3) < 0.1F) && (Math.abs(f4) < 0.1F)) {
/* 1026 */       f7 = (float)Math.sqrt(f3 * f3 + f4 * f4);
/* 1027 */       f3 /= f7;
/* 1028 */       f4 /= f7;
/*      */     }
/* 1030 */     if ((Math.abs(f5) < 0.1F) && (Math.abs(f6) < 0.1F)) {
/* 1031 */       f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
/* 1032 */       f5 /= f7;
/* 1033 */       f6 /= f7;
/*      */     }
/*      */ 
/* 1036 */     computeOffset(f3, f4, this.lineWidth2, this.offset[0]);
/* 1037 */     float f7 = this.offset[0][0];
/* 1038 */     float f8 = this.offset[0][1];
/* 1039 */     drawJoin(this.cdx, this.cdy, this.cx0, this.cy0, f3, f4, this.cmx, this.cmy, f7, f8);
/*      */ 
/* 1041 */     int k = findSubdivPoints(this.middle, this.subdivTs, 8, this.lineWidth2);
/* 1042 */     float f9 = 0.0F;
/* 1043 */     for (int m = 0; m < k; m++) {
/* 1044 */       float f10 = this.subdivTs[m];
/* 1045 */       Helpers.subdivideCubicAt((f10 - f9) / (1.0F - f9), this.middle, m * 6, this.middle, m * 6, this.middle, m * 6 + 6);
/*      */ 
/* 1049 */       f9 = f10;
/*      */     }
/*      */ 
/* 1052 */     m = 0;
/* 1053 */     for (int n = 0; n <= k; n++) {
/* 1054 */       m = computeOffsetCubic(this.middle, n * 6, this.lp, this.rp);
/* 1055 */       if (m != 0) {
/* 1056 */         emitLineTo(this.lp[0], this.lp[1]);
/* 1057 */         switch (m) {
/*      */         case 8:
/* 1059 */           emitCurveTo(this.lp[0], this.lp[1], this.lp[2], this.lp[3], this.lp[4], this.lp[5], this.lp[6], this.lp[7], false);
/* 1060 */           emitCurveTo(this.rp[0], this.rp[1], this.rp[2], this.rp[3], this.rp[4], this.rp[5], this.rp[6], this.rp[7], true);
/* 1061 */           break;
/*      */         case 4:
/* 1063 */           emitLineTo(this.lp[2], this.lp[3]);
/* 1064 */           emitLineTo(this.rp[0], this.rp[1], true);
/*      */         }
/*      */ 
/* 1067 */         emitLineTo(this.rp[(m - 2)], this.rp[(m - 1)], true);
/*      */       }
/*      */     }
/*      */ 
/* 1071 */     this.cmx = ((this.lp[(m - 2)] - this.rp[(m - 2)]) / 2.0F);
/* 1072 */     this.cmy = ((this.lp[(m - 1)] - this.rp[(m - 1)]) / 2.0F);
/* 1073 */     this.cdx = f5;
/* 1074 */     this.cdy = f6;
/* 1075 */     this.cx0 = f1;
/* 1076 */     this.cy0 = f2;
/* 1077 */     this.prev = 1;
/*      */   }
/*      */ 
/*      */   public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 1081 */     this.middle[0] = this.cx0; this.middle[1] = this.cy0;
/* 1082 */     this.middle[2] = paramFloat1; this.middle[3] = paramFloat2;
/* 1083 */     this.middle[4] = paramFloat3; this.middle[5] = paramFloat4;
/*      */ 
/* 1089 */     float f1 = this.middle[4]; float f2 = this.middle[5];
/* 1090 */     float f3 = this.middle[2] - this.middle[0];
/* 1091 */     float f4 = this.middle[3] - this.middle[1];
/* 1092 */     float f5 = this.middle[4] - this.middle[2];
/* 1093 */     float f6 = this.middle[5] - this.middle[3];
/* 1094 */     if (((f3 == 0.0F) && (f4 == 0.0F)) || ((f5 == 0.0F) && (f6 == 0.0F))) {
/* 1095 */       f3 = f5 = this.middle[4] - this.middle[0];
/* 1096 */       f4 = f6 = this.middle[5] - this.middle[1];
/*      */     }
/* 1098 */     if ((f3 == 0.0F) && (f4 == 0.0F))
/*      */     {
/* 1100 */       lineTo(this.middle[0], this.middle[1]);
/* 1101 */       return;
/*      */     }
/*      */ 
/* 1105 */     if ((Math.abs(f3) < 0.1F) && (Math.abs(f4) < 0.1F)) {
/* 1106 */       f7 = (float)Math.sqrt(f3 * f3 + f4 * f4);
/* 1107 */       f3 /= f7;
/* 1108 */       f4 /= f7;
/*      */     }
/* 1110 */     if ((Math.abs(f5) < 0.1F) && (Math.abs(f6) < 0.1F)) {
/* 1111 */       f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
/* 1112 */       f5 /= f7;
/* 1113 */       f6 /= f7;
/*      */     }
/*      */ 
/* 1116 */     computeOffset(f3, f4, this.lineWidth2, this.offset[0]);
/* 1117 */     float f7 = this.offset[0][0];
/* 1118 */     float f8 = this.offset[0][1];
/* 1119 */     drawJoin(this.cdx, this.cdy, this.cx0, this.cy0, f3, f4, this.cmx, this.cmy, f7, f8);
/*      */ 
/* 1121 */     int i = findSubdivPoints(this.middle, this.subdivTs, 6, this.lineWidth2);
/* 1122 */     float f9 = 0.0F;
/* 1123 */     for (int j = 0; j < i; j++) {
/* 1124 */       float f10 = this.subdivTs[j];
/* 1125 */       Helpers.subdivideQuadAt((f10 - f9) / (1.0F - f9), this.middle, j * 4, this.middle, j * 4, this.middle, j * 4 + 4);
/*      */ 
/* 1129 */       f9 = f10;
/*      */     }
/*      */ 
/* 1132 */     j = 0;
/* 1133 */     for (int k = 0; k <= i; k++) {
/* 1134 */       j = computeOffsetQuad(this.middle, k * 4, this.lp, this.rp);
/* 1135 */       if (j != 0) {
/* 1136 */         emitLineTo(this.lp[0], this.lp[1]);
/* 1137 */         switch (j) {
/*      */         case 6:
/* 1139 */           emitQuadTo(this.lp[0], this.lp[1], this.lp[2], this.lp[3], this.lp[4], this.lp[5], false);
/* 1140 */           emitQuadTo(this.rp[0], this.rp[1], this.rp[2], this.rp[3], this.rp[4], this.rp[5], true);
/* 1141 */           break;
/*      */         case 4:
/* 1143 */           emitLineTo(this.lp[2], this.lp[3]);
/* 1144 */           emitLineTo(this.rp[0], this.rp[1], true);
/*      */         }
/*      */ 
/* 1147 */         emitLineTo(this.rp[(j - 2)], this.rp[(j - 1)], true);
/*      */       }
/*      */     }
/*      */ 
/* 1151 */     this.cmx = ((this.lp[(j - 2)] - this.rp[(j - 2)]) / 2.0F);
/* 1152 */     this.cmy = ((this.lp[(j - 1)] - this.rp[(j - 1)]) / 2.0F);
/* 1153 */     this.cdx = f5;
/* 1154 */     this.cdy = f6;
/* 1155 */     this.cx0 = f1;
/* 1156 */     this.cy0 = f2;
/* 1157 */     this.prev = 1;
/*      */   }
/*      */ 
/*      */   private static final class PolyStack
/*      */   {
/*      */     float[] curves;
/*      */     int end;
/*      */     int[] curveTypes;
/*      */     int numCurves;
/*      */     private static final int INIT_SIZE = 50;
/*      */ 
/*      */     PolyStack()
/*      */     {
/* 1175 */       this.curves = new float[400];
/* 1176 */       this.curveTypes = new int[50];
/* 1177 */       this.end = 0;
/* 1178 */       this.numCurves = 0;
/*      */     }
/*      */ 
/*      */     public boolean isEmpty() {
/* 1182 */       return this.numCurves == 0;
/*      */     }
/*      */ 
/*      */     private void ensureSpace(int paramInt)
/*      */     {
/*      */       int i;
/* 1186 */       if (this.end + paramInt >= this.curves.length) {
/* 1187 */         i = (this.end + paramInt) * 2;
/* 1188 */         this.curves = Arrays.copyOf(this.curves, i);
/*      */       }
/* 1190 */       if (this.numCurves >= this.curveTypes.length) {
/* 1191 */         i = this.numCurves * 2;
/* 1192 */         this.curveTypes = Arrays.copyOf(this.curveTypes, i);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void pushCubic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */     {
/* 1200 */       ensureSpace(6);
/* 1201 */       this.curveTypes[(this.numCurves++)] = 8;
/*      */ 
/* 1205 */       this.curves[(this.end++)] = paramFloat5; this.curves[(this.end++)] = paramFloat6;
/* 1206 */       this.curves[(this.end++)] = paramFloat3; this.curves[(this.end++)] = paramFloat4;
/* 1207 */       this.curves[(this.end++)] = paramFloat1; this.curves[(this.end++)] = paramFloat2;
/*      */     }
/*      */ 
/*      */     public void pushQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */     {
/* 1213 */       ensureSpace(4);
/* 1214 */       this.curveTypes[(this.numCurves++)] = 6;
/*      */ 
/* 1216 */       this.curves[(this.end++)] = paramFloat3; this.curves[(this.end++)] = paramFloat4;
/* 1217 */       this.curves[(this.end++)] = paramFloat1; this.curves[(this.end++)] = paramFloat2;
/*      */     }
/*      */ 
/*      */     public void pushLine(float paramFloat1, float paramFloat2) {
/* 1221 */       ensureSpace(2);
/* 1222 */       this.curveTypes[(this.numCurves++)] = 4;
/*      */ 
/* 1224 */       this.curves[(this.end++)] = paramFloat1; this.curves[(this.end++)] = paramFloat2;
/*      */     }
/*      */ 
/*      */     public int pop(float[] paramArrayOfFloat)
/*      */     {
/* 1229 */       int i = this.curveTypes[(this.numCurves - 1)];
/* 1230 */       this.numCurves -= 1;
/* 1231 */       this.end -= i - 2;
/* 1232 */       System.arraycopy(this.curves, this.end, paramArrayOfFloat, 0, i - 2);
/* 1233 */       return i;
/*      */     }
/*      */ 
/*      */     public void pop(PathConsumer2D paramPathConsumer2D) {
/* 1237 */       this.numCurves -= 1;
/* 1238 */       int i = this.curveTypes[this.numCurves];
/* 1239 */       this.end -= i - 2;
/* 1240 */       switch (i) {
/*      */       case 8:
/* 1242 */         paramPathConsumer2D.curveTo(this.curves[(this.end + 0)], this.curves[(this.end + 1)], this.curves[(this.end + 2)], this.curves[(this.end + 3)], this.curves[(this.end + 4)], this.curves[(this.end + 5)]);
/*      */ 
/* 1245 */         break;
/*      */       case 6:
/* 1247 */         paramPathConsumer2D.quadTo(this.curves[(this.end + 0)], this.curves[(this.end + 1)], this.curves[(this.end + 2)], this.curves[(this.end + 3)]);
/*      */ 
/* 1249 */         break;
/*      */       case 4:
/* 1251 */         paramPathConsumer2D.lineTo(this.curves[this.end], this.curves[(this.end + 1)]);
/*      */       case 5:
/*      */       case 7:
/*      */       }
/*      */     }
/*      */ 
/* 1257 */     public String toString() { String str = "";
/* 1258 */       int i = this.numCurves;
/* 1259 */       int j = this.end;
/* 1260 */       while (i > 0) {
/* 1261 */         i--;
/* 1262 */         int k = this.curveTypes[this.numCurves];
/* 1263 */         j -= k - 2;
/* 1264 */         switch (k) {
/*      */         case 8:
/* 1266 */           str = str + "cubic: ";
/* 1267 */           break;
/*      */         case 6:
/* 1269 */           str = str + "quad: ";
/* 1270 */           break;
/*      */         case 4:
/* 1272 */           str = str + "line: ";
/*      */         case 5:
/*      */         case 7:
/* 1275 */         }str = str + Arrays.toString(Arrays.copyOfRange(this.curves, j, j + k - 2)) + "\n";
/*      */       }
/* 1277 */       return str;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.openpisces.Stroker
 * JD-Core Version:    0.6.2
 */