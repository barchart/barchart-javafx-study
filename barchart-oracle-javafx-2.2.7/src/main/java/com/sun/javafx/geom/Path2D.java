/*      */ package com.sun.javafx.geom;
/*      */ 
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ 
/*      */ public class Path2D extends Shape
/*      */   implements PathConsumer2D
/*      */ {
/*   67 */   protected static final int[] curvecoords = { 2, 2, 4, 6, 0 };
/*      */   public static final int WIND_EVEN_ODD = 0;
/*      */   public static final int WIND_NON_ZERO = 1;
/*      */   private static final byte SEG_MOVETO = 0;
/*      */   private static final byte SEG_LINETO = 1;
/*      */   private static final byte SEG_QUADTO = 2;
/*      */   private static final byte SEG_CUBICTO = 3;
/*      */   private static final byte SEG_CLOSE = 4;
/*      */   byte[] pointTypes;
/*      */   int numTypes;
/*      */   int numCoords;
/*      */   int windingRule;
/*      */   static final int INIT_SIZE = 20;
/*      */   static final int EXPAND_MAX = 500;
/*      */   float[] floatCoords;
/*      */   float moveX;
/*      */   float moveY;
/*      */   float prevX;
/*      */   float prevY;
/*      */   float currX;
/*      */   float currY;
/*      */ 
/*      */   public Path2D()
/*      */   {
/*  119 */     this(1, 20);
/*      */   }
/*      */ 
/*      */   public Path2D(int paramInt)
/*      */   {
/*  133 */     this(paramInt, 20);
/*      */   }
/*      */ 
/*      */   public Path2D(int paramInt1, int paramInt2)
/*      */   {
/*  152 */     setWindingRule(paramInt1);
/*  153 */     this.pointTypes = new byte[paramInt2];
/*  154 */     this.floatCoords = new float[paramInt2 * 2];
/*      */   }
/*      */ 
/*      */   public Path2D(Shape paramShape)
/*      */   {
/*  167 */     this(paramShape, null);
/*      */   }
/*      */ 
/*      */   public Path2D(Shape paramShape, BaseTransform paramBaseTransform)
/*      */   {
/*      */     Object localObject;
/*  183 */     if ((paramShape instanceof Path2D)) {
/*  184 */       localObject = (Path2D)paramShape;
/*  185 */       setWindingRule(((Path2D)localObject).windingRule);
/*  186 */       this.numTypes = ((Path2D)localObject).numTypes;
/*      */ 
/*  189 */       this.pointTypes = copyOf(((Path2D)localObject).pointTypes, ((Path2D)localObject).pointTypes.length);
/*      */ 
/*  191 */       this.numCoords = ((Path2D)localObject).numCoords;
/*  192 */       if ((paramBaseTransform == null) || (paramBaseTransform.isIdentity())) {
/*  193 */         this.floatCoords = copyOf(((Path2D)localObject).floatCoords, this.numCoords);
/*  194 */         this.moveX = ((Path2D)localObject).moveX;
/*  195 */         this.moveY = ((Path2D)localObject).moveY;
/*  196 */         this.prevX = ((Path2D)localObject).prevX;
/*  197 */         this.prevY = ((Path2D)localObject).prevY;
/*  198 */         this.currX = ((Path2D)localObject).currX;
/*  199 */         this.currY = ((Path2D)localObject).currY;
/*      */       } else {
/*  201 */         this.floatCoords = new float[this.numCoords + 6];
/*  202 */         paramBaseTransform.transform(((Path2D)localObject).floatCoords, 0, this.floatCoords, 0, this.numCoords / 2);
/*  203 */         this.floatCoords[(this.numCoords + 0)] = this.moveX;
/*  204 */         this.floatCoords[(this.numCoords + 1)] = this.moveY;
/*  205 */         this.floatCoords[(this.numCoords + 2)] = this.prevX;
/*  206 */         this.floatCoords[(this.numCoords + 3)] = this.prevY;
/*  207 */         this.floatCoords[(this.numCoords + 4)] = this.currX;
/*  208 */         this.floatCoords[(this.numCoords + 5)] = this.currY;
/*  209 */         paramBaseTransform.transform(this.floatCoords, this.numCoords, this.floatCoords, this.numCoords, 3);
/*  210 */         this.moveX = this.floatCoords[(this.numCoords + 0)];
/*  211 */         this.moveY = this.floatCoords[(this.numCoords + 1)];
/*  212 */         this.prevX = this.floatCoords[(this.numCoords + 2)];
/*  213 */         this.prevY = this.floatCoords[(this.numCoords + 3)];
/*  214 */         this.currX = this.floatCoords[(this.numCoords + 4)];
/*  215 */         this.currY = this.floatCoords[(this.numCoords + 5)];
/*      */       }
/*      */     } else {
/*  218 */       localObject = paramShape.getPathIterator(paramBaseTransform);
/*  219 */       setWindingRule(((PathIterator)localObject).getWindingRule());
/*  220 */       this.pointTypes = new byte[20];
/*  221 */       this.floatCoords = new float[40];
/*  222 */       append((PathIterator)localObject, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Path2D(int paramInt1, byte[] paramArrayOfByte, int paramInt2, float[] paramArrayOfFloat, int paramInt3)
/*      */   {
/*  240 */     this.windingRule = paramInt1;
/*  241 */     this.pointTypes = paramArrayOfByte;
/*  242 */     this.numTypes = paramInt2;
/*  243 */     this.floatCoords = paramArrayOfFloat;
/*  244 */     this.numCoords = paramInt3;
/*      */   }
/*      */ 
/*      */   Point2D getPoint(int paramInt) {
/*  248 */     return new Point2D(this.floatCoords[paramInt], this.floatCoords[(paramInt + 1)]);
/*      */   }
/*      */ 
/*      */   void needRoom(boolean paramBoolean, int paramInt)
/*      */   {
/*  253 */     if ((paramBoolean) && (this.numTypes == 0)) {
/*  254 */       throw new IllegalPathStateException("missing initial moveto in path definition");
/*      */     }
/*      */ 
/*  257 */     int i = this.pointTypes.length;
/*      */     int j;
/*  258 */     if (i == 0) {
/*  259 */       this.pointTypes = new byte[2];
/*  260 */     } else if (this.numTypes >= i) {
/*  261 */       j = i;
/*  262 */       if (j > 500) {
/*  263 */         j = 500;
/*      */       }
/*      */ 
/*  266 */       this.pointTypes = copyOf(this.pointTypes, i + j);
/*      */     }
/*  268 */     i = this.floatCoords.length;
/*  269 */     if (this.numCoords + paramInt > i) {
/*  270 */       j = i;
/*  271 */       if (j > 1000) {
/*  272 */         j = 1000;
/*      */       }
/*  274 */       if (j < paramInt) {
/*  275 */         j = paramInt;
/*      */       }
/*      */ 
/*  278 */       this.floatCoords = copyOf(this.floatCoords, i + j);
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void moveTo(float paramFloat1, float paramFloat2)
/*      */   {
/*  290 */     if ((this.numTypes > 0) && (this.pointTypes[(this.numTypes - 1)] == 0))
/*      */     {
/*      */       float tmp43_40 = (this.prevX = this.currX = paramFloat1); this.moveX = tmp43_40; this.floatCoords[(this.numCoords - 2)] = tmp43_40;
/*      */       float tmp70_67 = (this.prevY = this.currY = paramFloat2); this.moveY = tmp70_67; this.floatCoords[(this.numCoords - 1)] = tmp70_67;
/*      */     } else {
/*  294 */       needRoom(false, 2);
/*  295 */       this.pointTypes[(this.numTypes++)] = 0;
/*      */       float tmp128_125 = (this.prevX = this.currX = paramFloat1); this.moveX = tmp128_125; this.floatCoords[(this.numCoords++)] = tmp128_125;
/*      */       float tmp160_157 = (this.prevY = this.currY = paramFloat2); this.moveY = tmp160_157; this.floatCoords[(this.numCoords++)] = tmp160_157;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void moveToRel(float paramFloat1, float paramFloat2)
/*      */   {
/*  310 */     if ((this.numTypes > 0) && (this.pointTypes[(this.numTypes - 1)] == 0))
/*      */     {
/*      */       float tmp48_45 = (this.prevX = this.currX += paramFloat1); this.moveX = tmp48_45; this.floatCoords[(this.numCoords - 2)] = tmp48_45;
/*      */       float tmp80_77 = (this.prevY = this.currY += paramFloat2); this.moveY = tmp80_77; this.floatCoords[(this.numCoords - 1)] = tmp80_77;
/*      */     } else {
/*  314 */       needRoom(true, 2);
/*  315 */       this.pointTypes[(this.numTypes++)] = 0;
/*      */       float tmp143_140 = (this.prevX = this.currX += paramFloat1); this.moveX = tmp143_140; this.floatCoords[(this.numCoords++)] = tmp143_140;
/*      */       float tmp180_177 = (this.prevY = this.currY += paramFloat2); this.moveY = tmp180_177; this.floatCoords[(this.numCoords++)] = tmp180_177;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void lineTo(float paramFloat1, float paramFloat2)
/*      */   {
/*  329 */     needRoom(true, 2);
/*  330 */     this.pointTypes[(this.numTypes++)] = 1;
/*      */     float tmp45_42 = (this.currX = paramFloat1); this.prevX = tmp45_42; this.floatCoords[(this.numCoords++)] = tmp45_42;
/*      */     float tmp72_69 = (this.currY = paramFloat2); this.prevY = tmp72_69; this.floatCoords[(this.numCoords++)] = tmp72_69;
/*      */   }
/*      */ 
/*      */   public final void lineToRel(float paramFloat1, float paramFloat2)
/*      */   {
/*  345 */     needRoom(true, 2);
/*  346 */     this.pointTypes[(this.numTypes++)] = 1;
/*      */     float tmp50_47 = (this.currX += paramFloat1); this.prevX = tmp50_47; this.floatCoords[(this.numCoords++)] = tmp50_47;
/*      */     float tmp82_79 = (this.currY += paramFloat2); this.prevY = tmp82_79; this.floatCoords[(this.numCoords++)] = tmp82_79;
/*      */   }
/*      */ 
/*      */   public final void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  366 */     needRoom(true, 4);
/*  367 */     this.pointTypes[(this.numTypes++)] = 2;
/*      */     float tmp40_39 = paramFloat1; this.prevX = tmp40_39; this.floatCoords[(this.numCoords++)] = tmp40_39;
/*      */     float tmp62_61 = paramFloat2; this.prevY = tmp62_61; this.floatCoords[(this.numCoords++)] = tmp62_61;
/*      */     float tmp84_83 = paramFloat3; this.currX = tmp84_83; this.floatCoords[(this.numCoords++)] = tmp84_83;
/*      */     float tmp107_105 = paramFloat4; this.currY = tmp107_105; this.floatCoords[(this.numCoords++)] = tmp107_105;
/*      */   }
/*      */ 
/*      */   public final void quadToRel(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  396 */     needRoom(true, 4);
/*  397 */     this.pointTypes[(this.numTypes++)] = 2;
/*      */     float tmp45_44 = (this.currX + paramFloat1); this.prevX = tmp45_44; this.floatCoords[(this.numCoords++)] = tmp45_44;
/*      */     float tmp72_71 = (this.currY + paramFloat2); this.prevY = tmp72_71; this.floatCoords[(this.numCoords++)] = tmp72_71;
/*      */     Path2D tmp93_92 = this;
/*      */     float tmp99_98 = (tmp93_92.currX + paramFloat3); tmp93_92.currX = tmp99_98; this.floatCoords[(this.numCoords++)] = tmp99_98;
/*      */     Path2D tmp120_119 = this;
/*      */     float tmp127_126 = (tmp120_119.currY + paramFloat4); tmp120_119.currY = tmp127_126; this.floatCoords[(this.numCoords++)] = tmp127_126;
/*      */   }
/*      */ 
/*      */   public final void quadToSmooth(float paramFloat1, float paramFloat2)
/*      */   {
/*  425 */     needRoom(true, 4);
/*  426 */     this.pointTypes[(this.numTypes++)] = 2;
/*      */     long tmp50_49 = (this.currX * 2.0F - this.prevX); this.prevX = tmp50_49; this.floatCoords[(this.numCoords++)] = tmp50_49;
/*      */     long tmp82_81 = (this.currY * 2.0F - this.prevY); this.prevY = tmp82_81; this.floatCoords[(this.numCoords++)] = tmp82_81;
/*      */     float tmp104_103 = paramFloat1; this.currX = tmp104_103; this.floatCoords[(this.numCoords++)] = tmp104_103;
/*      */     float tmp126_125 = paramFloat2; this.currY = tmp126_125; this.floatCoords[(this.numCoords++)] = tmp126_125;
/*      */   }
/*      */ 
/*      */   public final void quadToSmoothRel(float paramFloat1, float paramFloat2)
/*      */   {
/*  455 */     needRoom(true, 4);
/*  456 */     this.pointTypes[(this.numTypes++)] = 2;
/*      */     long tmp50_49 = (this.currX * 2.0F - this.prevX); this.prevX = tmp50_49; this.floatCoords[(this.numCoords++)] = tmp50_49;
/*      */     long tmp82_81 = (this.currY * 2.0F - this.prevY); this.prevY = tmp82_81; this.floatCoords[(this.numCoords++)] = tmp82_81;
/*      */     Path2D tmp103_102 = this;
/*      */     float tmp109_108 = (tmp103_102.currX + paramFloat1); tmp103_102.currX = tmp109_108; this.floatCoords[(this.numCoords++)] = tmp109_108;
/*      */     Path2D tmp130_129 = this;
/*      */     float tmp136_135 = (tmp130_129.currY + paramFloat2); tmp130_129.currY = tmp136_135; this.floatCoords[(this.numCoords++)] = tmp136_135;
/*      */   }
/*      */ 
/*      */   public final void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  482 */     needRoom(true, 6);
/*  483 */     this.pointTypes[(this.numTypes++)] = 3;
/*  484 */     this.floatCoords[(this.numCoords++)] = paramFloat1;
/*  485 */     this.floatCoords[(this.numCoords++)] = paramFloat2;
/*      */     float tmp75_74 = paramFloat3; this.prevX = tmp75_74; this.floatCoords[(this.numCoords++)] = tmp75_74;
/*      */     float tmp98_96 = paramFloat4; this.prevY = tmp98_96; this.floatCoords[(this.numCoords++)] = tmp98_96;
/*      */     float tmp121_119 = paramFloat5; this.currX = tmp121_119; this.floatCoords[(this.numCoords++)] = tmp121_119;
/*      */     float tmp144_142 = paramFloat6; this.currY = tmp144_142; this.floatCoords[(this.numCoords++)] = tmp144_142;
/*      */   }
/*      */ 
/*      */   public final void curveToRel(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  518 */     needRoom(true, 6);
/*  519 */     this.pointTypes[(this.numTypes++)] = 3;
/*  520 */     this.floatCoords[(this.numCoords++)] = (this.currX + paramFloat1);
/*  521 */     this.floatCoords[(this.numCoords++)] = (this.currY + paramFloat2);
/*      */     float tmp90_89 = (this.currX + paramFloat3); this.prevX = tmp90_89; this.floatCoords[(this.numCoords++)] = tmp90_89;
/*      */     float tmp118_117 = (this.currY + paramFloat4); this.prevY = tmp118_117; this.floatCoords[(this.numCoords++)] = tmp118_117;
/*      */     Path2D tmp139_138 = this;
/*      */     float tmp146_145 = (tmp139_138.currX + paramFloat5); tmp139_138.currX = tmp146_145; this.floatCoords[(this.numCoords++)] = tmp146_145;
/*      */     Path2D tmp167_166 = this;
/*      */     float tmp174_173 = (tmp167_166.currY + paramFloat6); tmp167_166.currY = tmp174_173; this.floatCoords[(this.numCoords++)] = tmp174_173;
/*      */   }
/*      */ 
/*      */   public final void curveToSmooth(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  554 */     needRoom(true, 6);
/*  555 */     this.pointTypes[(this.numTypes++)] = 3;
/*  556 */     this.floatCoords[(this.numCoords++)] = (this.currX * 2.0F - this.prevX);
/*  557 */     this.floatCoords[(this.numCoords++)] = (this.currY * 2.0F - this.prevY);
/*      */     float tmp95_94 = paramFloat1; this.prevX = tmp95_94; this.floatCoords[(this.numCoords++)] = tmp95_94;
/*      */     float tmp117_116 = paramFloat2; this.prevY = tmp117_116; this.floatCoords[(this.numCoords++)] = tmp117_116;
/*      */     float tmp139_138 = paramFloat3; this.currX = tmp139_138; this.floatCoords[(this.numCoords++)] = tmp139_138;
/*      */     float tmp162_160 = paramFloat4; this.currY = tmp162_160; this.floatCoords[(this.numCoords++)] = tmp162_160;
/*      */   }
/*      */ 
/*      */   public final void curveToSmoothRel(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  592 */     needRoom(true, 6);
/*  593 */     this.pointTypes[(this.numTypes++)] = 3;
/*  594 */     this.floatCoords[(this.numCoords++)] = (this.currX * 2.0F - this.prevX);
/*  595 */     this.floatCoords[(this.numCoords++)] = (this.currY * 2.0F - this.prevY);
/*      */     float tmp100_99 = (this.currX + paramFloat1); this.prevX = tmp100_99; this.floatCoords[(this.numCoords++)] = tmp100_99;
/*      */     float tmp127_126 = (this.currY + paramFloat2); this.prevY = tmp127_126; this.floatCoords[(this.numCoords++)] = tmp127_126;
/*      */     Path2D tmp148_147 = this;
/*      */     float tmp154_153 = (tmp148_147.currX + paramFloat3); tmp148_147.currX = tmp154_153; this.floatCoords[(this.numCoords++)] = tmp154_153;
/*      */     Path2D tmp175_174 = this;
/*      */     float tmp182_181 = (tmp175_174.currY + paramFloat4); tmp175_174.currY = tmp182_181; this.floatCoords[(this.numCoords++)] = tmp182_181;
/*      */   }
/*      */ 
/*      */   public final void ovalQuadrantTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  668 */     if (this.numTypes < 1) {
/*  669 */       throw new IllegalPathStateException("missing initial moveto in path definition");
/*      */     }
/*      */ 
/*  672 */     appendOvalQuadrant(this.currX, this.currY, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, CornerPrefix.CORNER_ONLY);
/*      */   }
/*      */ 
/*      */   public final void appendOvalQuadrant(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, CornerPrefix paramCornerPrefix)
/*      */   {
/*  772 */     if ((paramFloat7 < 0.0F) || (paramFloat7 > paramFloat8) || (paramFloat8 > 1.0F)) {
/*  773 */       throw new IllegalArgumentException("0 <= tfrom <= tto <= 1 required");
/*      */     }
/*  775 */     float f1 = (float)(paramFloat1 + (paramFloat3 - paramFloat1) * 0.5522847498307933D);
/*  776 */     float f2 = (float)(paramFloat2 + (paramFloat4 - paramFloat2) * 0.5522847498307933D);
/*  777 */     float f3 = (float)(paramFloat5 + (paramFloat3 - paramFloat5) * 0.5522847498307933D);
/*  778 */     float f4 = (float)(paramFloat6 + (paramFloat4 - paramFloat6) * 0.5522847498307933D);
/*  779 */     if (paramFloat8 < 1.0F) {
/*  780 */       float f5 = 1.0F - paramFloat8;
/*  781 */       paramFloat5 += (f3 - paramFloat5) * f5;
/*  782 */       paramFloat6 += (f4 - paramFloat6) * f5;
/*  783 */       f3 += (f1 - f3) * f5;
/*  784 */       f4 += (f2 - f4) * f5;
/*  785 */       f1 += (paramFloat1 - f1) * f5;
/*  786 */       f2 += (paramFloat2 - f2) * f5;
/*  787 */       paramFloat5 += (f3 - paramFloat5) * f5;
/*  788 */       paramFloat6 += (f4 - paramFloat6) * f5;
/*  789 */       f3 += (f1 - f3) * f5;
/*  790 */       f4 += (f2 - f4) * f5;
/*  791 */       paramFloat5 += (f3 - paramFloat5) * f5;
/*  792 */       paramFloat6 += (f4 - paramFloat6) * f5;
/*      */     }
/*  794 */     if (paramFloat7 > 0.0F) {
/*  795 */       if (paramFloat8 < 1.0F) {
/*  796 */         paramFloat7 /= paramFloat8;
/*      */       }
/*  798 */       paramFloat1 += (f1 - paramFloat1) * paramFloat7;
/*  799 */       paramFloat2 += (f2 - paramFloat2) * paramFloat7;
/*  800 */       f1 += (f3 - f1) * paramFloat7;
/*  801 */       f2 += (f4 - f2) * paramFloat7;
/*  802 */       f3 += (paramFloat5 - f3) * paramFloat7;
/*  803 */       f4 += (paramFloat6 - f4) * paramFloat7;
/*  804 */       paramFloat1 += (f1 - paramFloat1) * paramFloat7;
/*  805 */       paramFloat2 += (f2 - paramFloat2) * paramFloat7;
/*  806 */       f1 += (f3 - f1) * paramFloat7;
/*  807 */       f2 += (f4 - f2) * paramFloat7;
/*  808 */       paramFloat1 += (f1 - paramFloat1) * paramFloat7;
/*  809 */       paramFloat2 += (f2 - paramFloat2) * paramFloat7;
/*      */     }
/*  811 */     if (paramCornerPrefix == CornerPrefix.MOVE_THEN_CORNER)
/*      */     {
/*  813 */       moveTo(paramFloat1, paramFloat2);
/*  814 */     } else if ((paramCornerPrefix == CornerPrefix.LINE_THEN_CORNER) && (
/*  815 */       (this.numTypes == 1) || (paramFloat1 != this.currX) || (paramFloat2 != this.currY)))
/*      */     {
/*  819 */       lineTo(paramFloat1, paramFloat2);
/*      */     }
/*      */ 
/*  822 */     if ((paramFloat7 == paramFloat8) || ((paramFloat1 == f1) && (f1 == f3) && (f3 == paramFloat5) && (paramFloat2 == f2) && (f2 == f4) && (f4 == paramFloat6)))
/*      */     {
/*  826 */       if (paramCornerPrefix != CornerPrefix.LINE_THEN_CORNER)
/*  827 */         lineTo(paramFloat5, paramFloat6);
/*      */     }
/*      */     else
/*  830 */       curveTo(f1, f2, f3, f4, paramFloat5, paramFloat6);
/*      */   }
/*      */ 
/*      */   public void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, float paramFloat4, float paramFloat5)
/*      */   {
/*  885 */     if (this.numTypes < 1) {
/*  886 */       throw new IllegalPathStateException("missing initial moveto in path definition");
/*      */     }
/*      */ 
/*  905 */     double d1 = Math.abs(paramFloat1);
/*  906 */     double d2 = Math.abs(paramFloat2);
/*  907 */     if ((d1 == 0.0D) || (d2 == 0.0D)) {
/*  908 */       lineTo(paramFloat4, paramFloat5);
/*  909 */       return;
/*      */     }
/*  911 */     double d3 = this.currX;
/*  912 */     double d4 = this.currY;
/*  913 */     double d5 = paramFloat4;
/*  914 */     double d6 = paramFloat5;
/*  915 */     if ((d3 == d5) && (d4 == d6))
/*      */       return;
/*      */     double d7;
/*      */     double d8;
/*  919 */     if (paramFloat3 == 0.0D) {
/*  920 */       d7 = 1.0D;
/*  921 */       d8 = 0.0D;
/*      */     } else {
/*  923 */       d7 = Math.cos(paramFloat3);
/*  924 */       d8 = Math.sin(paramFloat3);
/*      */     }
/*  926 */     double d9 = (d3 + d5) / 2.0D;
/*  927 */     double d10 = (d4 + d6) / 2.0D;
/*  928 */     double d11 = d3 - d9;
/*  929 */     double d12 = d4 - d10;
/*  930 */     double d13 = (d7 * d11 + d8 * d12) / d1;
/*  931 */     double d14 = (d7 * d12 - d8 * d11) / d2;
/*      */ 
/*  945 */     double d15 = d13 * d13 + d14 * d14;
/*  946 */     if (d15 >= 1.0D)
/*      */     {
/*  960 */       d16 = d14 * d1;
/*  961 */       d17 = d13 * d2;
/*  962 */       if (paramBoolean2) d16 = -d16; else d17 = -d17;
/*  963 */       d18 = d7 * d16 - d8 * d17;
/*  964 */       d19 = d7 * d17 + d8 * d16;
/*  965 */       d20 = d9 + d18;
/*  966 */       d21 = d10 + d19;
/*  967 */       d22 = d3 + d18;
/*  968 */       double d23 = d4 + d19;
/*  969 */       appendOvalQuadrant((float)d3, (float)d4, (float)d22, (float)d23, (float)d20, (float)d21, 0.0F, 1.0F, CornerPrefix.CORNER_ONLY);
/*      */ 
/*  973 */       d22 = d5 + d18;
/*  974 */       d23 = d6 + d19;
/*  975 */       appendOvalQuadrant((float)d20, (float)d21, (float)d22, (float)d23, (float)d5, (float)d6, 0.0F, 1.0F, CornerPrefix.CORNER_ONLY);
/*      */ 
/*  979 */       return;
/*      */     }
/*      */ 
/*  998 */     double d16 = Math.sqrt((1.0D - d15) / d15);
/*      */ 
/* 1002 */     double d17 = d16 * d14;
/* 1003 */     double d18 = d16 * d13;
/*      */ 
/* 1006 */     if (paramBoolean1 == paramBoolean2) d17 = -d17; else d18 = -d18;
/* 1007 */     d9 += d7 * d17 * d1 - d8 * d18 * d2;
/* 1008 */     d10 += d7 * d18 * d2 + d8 * d17 * d1;
/*      */ 
/* 1013 */     double d19 = d13 - d17;
/* 1014 */     double d20 = d14 - d18;
/*      */ 
/* 1017 */     double d21 = -(d13 + d17);
/* 1018 */     double d22 = -(d14 + d18);
/*      */ 
/* 1021 */     int i = 0;
/* 1022 */     float f = 1.0F;
/* 1023 */     int j = 0;
/*      */     do
/*      */     {
/* 1026 */       double d24 = d20;
/* 1027 */       double d25 = d19;
/* 1028 */       if (paramBoolean2) d24 = -d24; else d25 = -d25;
/*      */ 
/* 1030 */       if (d24 * d21 + d25 * d22 > 0.0D)
/*      */       {
/* 1033 */         d26 = d19 * d21 + d20 * d22;
/* 1034 */         if (d26 >= 0.0D)
/*      */         {
/* 1039 */           f = (float)(Math.acos(d26) / 1.570796326794897D);
/* 1040 */           i = 1;
/*      */         }
/*      */ 
/* 1044 */         j = 1; } else {
/* 1045 */         if (j != 0)
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1059 */       double d26 = d7 * d24 * d1 - d8 * d25 * d2;
/* 1060 */       double d27 = d7 * d25 * d2 + d8 * d24 * d1;
/* 1061 */       double d28 = d9 + d26;
/* 1062 */       double d29 = d10 + d27;
/* 1063 */       double d30 = d3 + d26;
/* 1064 */       double d31 = d4 + d27;
/* 1065 */       appendOvalQuadrant((float)d3, (float)d4, (float)d30, (float)d31, (float)d28, (float)d29, 0.0F, f, CornerPrefix.CORNER_ONLY);
/*      */ 
/* 1069 */       d3 = d28;
/* 1070 */       d4 = d29;
/* 1071 */       d19 = d24;
/* 1072 */       d20 = d25;
/* 1073 */     }while (i == 0);
/*      */   }
/*      */ 
/*      */   public void arcToRel(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, float paramFloat4, float paramFloat5)
/*      */   {
/* 1102 */     arcTo(paramFloat1, paramFloat2, paramFloat3, paramBoolean1, paramBoolean2, this.currX + paramFloat4, this.currY + paramFloat5);
/*      */   }
/*      */ 
/*      */   int pointCrossings(float paramFloat1, float paramFloat2)
/*      */   {
/* 1109 */     float[] arrayOfFloat = this.floatCoords;
/*      */     float f1;
/* 1110 */     float f3 = f1 = arrayOfFloat[0];
/*      */     float f2;
/* 1111 */     float f4 = f2 = arrayOfFloat[1];
/* 1112 */     int i = 0;
/* 1113 */     int j = 2;
/* 1114 */     for (int k = 1; k < this.numTypes; k++)
/*      */     {
/*      */       float f5;
/*      */       float f6;
/* 1115 */       switch (this.pointTypes[k]) {
/*      */       case 0:
/* 1117 */         if (f4 != f2) {
/* 1118 */           i += Shape.pointCrossingsForLine(paramFloat1, paramFloat2, f3, f4, f1, f2);
/*      */         }
/*      */ 
/* 1123 */         f1 = f3 = arrayOfFloat[(j++)];
/* 1124 */         f2 = f4 = arrayOfFloat[(j++)];
/* 1125 */         break;
/*      */       case 1:
/* 1127 */         i += Shape.pointCrossingsForLine(paramFloat1, paramFloat2, f3, f4, f5 = arrayOfFloat[(j++)], f6 = arrayOfFloat[(j++)]);
/*      */ 
/* 1132 */         f3 = f5;
/* 1133 */         f4 = f6;
/* 1134 */         break;
/*      */       case 2:
/* 1136 */         i += Shape.pointCrossingsForQuad(paramFloat1, paramFloat2, f3, f4, arrayOfFloat[(j++)], arrayOfFloat[(j++)], f5 = arrayOfFloat[(j++)], f6 = arrayOfFloat[(j++)], 0);
/*      */ 
/* 1144 */         f3 = f5;
/* 1145 */         f4 = f6;
/* 1146 */         break;
/*      */       case 3:
/* 1148 */         i += Shape.pointCrossingsForCubic(paramFloat1, paramFloat2, f3, f4, arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], f5 = arrayOfFloat[(j++)], f6 = arrayOfFloat[(j++)], 0);
/*      */ 
/* 1158 */         f3 = f5;
/* 1159 */         f4 = f6;
/* 1160 */         break;
/*      */       case 4:
/* 1162 */         if (f4 != f2) {
/* 1163 */           i += Shape.pointCrossingsForLine(paramFloat1, paramFloat2, f3, f4, f1, f2);
/*      */         }
/*      */ 
/* 1168 */         f3 = f1;
/* 1169 */         f4 = f2;
/*      */       }
/*      */     }
/*      */ 
/* 1173 */     if (f4 != f2) {
/* 1174 */       i += Shape.pointCrossingsForLine(paramFloat1, paramFloat2, f3, f4, f1, f2);
/*      */     }
/*      */ 
/* 1179 */     return i;
/*      */   }
/*      */ 
/*      */   int rectCrossings(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/* 1185 */     float[] arrayOfFloat = this.floatCoords;
/*      */     float f3;
/* 1187 */     float f1 = f3 = arrayOfFloat[0];
/*      */     float f4;
/* 1188 */     float f2 = f4 = arrayOfFloat[1];
/* 1189 */     int i = 0;
/* 1190 */     int j = 2;
/* 1191 */     for (int k = 1; 
/* 1192 */       (i != -2147483648) && (k < this.numTypes); 
/* 1193 */       k++)
/*      */     {
/*      */       float f5;
/*      */       float f6;
/* 1195 */       switch (this.pointTypes[k]) {
/*      */       case 0:
/* 1197 */         if ((f1 != f3) || (f2 != f4)) {
/* 1198 */           i = Shape.rectCrossingsForLine(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, f3, f4);
/*      */         }
/*      */ 
/* 1207 */         f3 = f1 = arrayOfFloat[(j++)];
/* 1208 */         f4 = f2 = arrayOfFloat[(j++)];
/* 1209 */         break;
/*      */       case 1:
/* 1211 */         i = Shape.rectCrossingsForLine(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, f5 = arrayOfFloat[(j++)], f6 = arrayOfFloat[(j++)]);
/*      */ 
/* 1218 */         f1 = f5;
/* 1219 */         f2 = f6;
/* 1220 */         break;
/*      */       case 2:
/* 1222 */         i = Shape.rectCrossingsForQuad(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, arrayOfFloat[(j++)], arrayOfFloat[(j++)], f5 = arrayOfFloat[(j++)], f6 = arrayOfFloat[(j++)], 0);
/*      */ 
/* 1232 */         f1 = f5;
/* 1233 */         f2 = f6;
/* 1234 */         break;
/*      */       case 3:
/* 1236 */         i = Shape.rectCrossingsForCubic(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], arrayOfFloat[(j++)], f5 = arrayOfFloat[(j++)], f6 = arrayOfFloat[(j++)], 0);
/*      */ 
/* 1248 */         f1 = f5;
/* 1249 */         f2 = f6;
/* 1250 */         break;
/*      */       case 4:
/* 1252 */         if ((f1 != f3) || (f2 != f4)) {
/* 1253 */           i = Shape.rectCrossingsForLine(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, f3, f4);
/*      */         }
/*      */ 
/* 1260 */         f1 = f3;
/* 1261 */         f2 = f4;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1267 */     if ((i != -2147483648) && ((f1 != f3) || (f2 != f4)))
/*      */     {
/* 1270 */       i = Shape.rectCrossingsForLine(i, paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2, f3, f4);
/*      */     }
/*      */ 
/* 1279 */     return i;
/*      */   }
/*      */ 
/*      */   public final void append(PathIterator paramPathIterator, boolean paramBoolean)
/*      */   {
/* 1287 */     float[] arrayOfFloat = new float[6];
/* 1288 */     while (!paramPathIterator.isDone()) {
/* 1289 */       switch (paramPathIterator.currentSegment(arrayOfFloat)) {
/*      */       case 0:
/* 1291 */         if ((!paramBoolean) || (this.numTypes < 1) || (this.numCoords < 1)) {
/* 1292 */           moveTo(arrayOfFloat[0], arrayOfFloat[1]);
/*      */         }
/*      */         else {
/* 1295 */           if ((this.pointTypes[(this.numTypes - 1)] != 4) && (this.floatCoords[(this.numCoords - 2)] == arrayOfFloat[0]) && (this.floatCoords[(this.numCoords - 1)] == arrayOfFloat[1]))
/*      */           {
/*      */             break;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 1:
/* 1304 */         lineTo(arrayOfFloat[0], arrayOfFloat[1]);
/* 1305 */         break;
/*      */       case 2:
/* 1307 */         quadTo(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*      */ 
/* 1309 */         break;
/*      */       case 3:
/* 1311 */         curveTo(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
/*      */ 
/* 1314 */         break;
/*      */       case 4:
/* 1316 */         closePath();
/*      */       }
/*      */ 
/* 1319 */       paramPathIterator.next();
/* 1320 */       paramBoolean = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void transform(BaseTransform paramBaseTransform)
/*      */   {
/* 1328 */     if (this.numCoords == 0) return;
/* 1329 */     needRoom(false, 6);
/* 1330 */     this.floatCoords[(this.numCoords + 0)] = this.moveX;
/* 1331 */     this.floatCoords[(this.numCoords + 1)] = this.moveY;
/* 1332 */     this.floatCoords[(this.numCoords + 2)] = this.prevX;
/* 1333 */     this.floatCoords[(this.numCoords + 3)] = this.prevY;
/* 1334 */     this.floatCoords[(this.numCoords + 4)] = this.currX;
/* 1335 */     this.floatCoords[(this.numCoords + 5)] = this.currY;
/* 1336 */     paramBaseTransform.transform(this.floatCoords, 0, this.floatCoords, 0, this.numCoords / 2 + 3);
/* 1337 */     this.moveX = this.floatCoords[(this.numCoords + 0)];
/* 1338 */     this.moveY = this.floatCoords[(this.numCoords + 1)];
/* 1339 */     this.prevX = this.floatCoords[(this.numCoords + 2)];
/* 1340 */     this.prevY = this.floatCoords[(this.numCoords + 3)];
/* 1341 */     this.currX = this.floatCoords[(this.numCoords + 4)];
/* 1342 */     this.currY = this.floatCoords[(this.numCoords + 5)];
/*      */   }
/*      */ 
/*      */   public final RectBounds getBounds()
/*      */   {
/* 1350 */     int i = this.numCoords;
/*      */     float f4;
/*      */     float f2;
/*      */     float f3;
/* 1351 */     if (i > 0) {
/* 1352 */       f2 = f4 = this.floatCoords[(--i)];
/* 1353 */       f1 = f3 = this.floatCoords[(--i)];
/* 1354 */       while (i > 0) {
/* 1355 */         float f5 = this.floatCoords[(--i)];
/* 1356 */         float f6 = this.floatCoords[(--i)];
/* 1357 */         if (f6 < f1) f1 = f6;
/* 1358 */         if (f5 < f2) f2 = f5;
/* 1359 */         if (f6 > f3) f3 = f6;
/* 1360 */         if (f5 > f4) f4 = f5;
/*      */       }
/*      */     }
/* 1363 */     float f1 = f2 = f3 = f4 = 0.0F;
/*      */ 
/* 1365 */     return new RectBounds(f1, f2, f3, f4);
/*      */   }
/*      */ 
/*      */   public final int getNumCommands()
/*      */   {
/* 1371 */     return this.numTypes;
/*      */   }
/*      */   public final byte[] getCommandsNoClone() {
/* 1374 */     return this.pointTypes;
/*      */   }
/*      */   public final float[] getFloatCoordsNoClone() {
/* 1377 */     return this.floatCoords;
/*      */   }
/*      */ 
/*      */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*      */   {
/* 1392 */     if (paramBaseTransform == null) {
/* 1393 */       return new CopyIterator(this);
/*      */     }
/* 1395 */     return new TxIterator(this, paramBaseTransform);
/*      */   }
/*      */ 
/*      */   public final void closePath()
/*      */   {
/* 1466 */     if ((this.numTypes == 0) || (this.pointTypes[(this.numTypes - 1)] != 4)) {
/* 1467 */       needRoom(true, 0);
/* 1468 */       this.pointTypes[(this.numTypes++)] = 4;
/* 1469 */       this.prevX = (this.currX = this.moveX);
/* 1470 */       this.prevY = (this.currY = this.moveY);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void pathDone()
/*      */   {
/*      */   }
/*      */ 
/*      */   public final void append(Shape paramShape, boolean paramBoolean)
/*      */   {
/* 1500 */     append(paramShape.getPathIterator(null), paramBoolean);
/*      */   }
/*      */ 
/*      */   public final void appendSVGPath(String paramString)
/*      */   {
/* 1653 */     SVGParser localSVGParser = new SVGParser(paramString);
/* 1654 */     localSVGParser.allowcomma = false;
/* 1655 */     while (!localSVGParser.isDone()) {
/* 1656 */       localSVGParser.allowcomma = false;
/* 1657 */       char c = localSVGParser.getChar();
/* 1658 */       switch (c) {
/*      */       case 'M':
/* 1660 */         moveTo(localSVGParser.f(), localSVGParser.f());
/*      */       case 'm':
/*      */       case 'L':
/*      */       case 'l':
/*      */       case 'H':
/*      */       case 'h':
/*      */       case 'V':
/*      */       case 'v':
/*      */       case 'Q':
/*      */       case 'q':
/*      */       case 'T':
/*      */       case 't':
/*      */       case 'C':
/*      */       case 'c':
/*      */       case 'S':
/*      */       case 's':
/*      */       case 'A':
/*      */       case 'a':
/*      */       case 'Z':
/*      */       case 'z':
/*      */       case 'B':
/*      */       case 'D':
/*      */       case 'E':
/*      */       case 'F':
/*      */       case 'G':
/*      */       case 'I':
/*      */       case 'J':
/*      */       case 'K':
/*      */       case 'N':
/*      */       case 'O':
/*      */       case 'P':
/*      */       case 'R':
/*      */       case 'U':
/*      */       case 'W':
/*      */       case 'X':
/*      */       case 'Y':
/*      */       case '[':
/*      */       case '\\':
/*      */       case ']':
/*      */       case '^':
/*      */       case '_':
/*      */       case '`':
/*      */       case 'b':
/*      */       case 'd':
/*      */       case 'e':
/*      */       case 'f':
/*      */       case 'g':
/*      */       case 'i':
/*      */       case 'j':
/*      */       case 'k':
/*      */       case 'n':
/*      */       case 'o':
/*      */       case 'p':
/*      */       case 'r':
/*      */       case 'u':
/*      */       case 'w':
/*      */       case 'x':
/* 1661 */       case 'y': } while (localSVGParser.nextIsNumber()) {
/* 1662 */         lineTo(localSVGParser.f(), localSVGParser.f()); continue;
/*      */ 
/* 1666 */         if (this.numTypes > 0)
/* 1667 */           moveToRel(localSVGParser.f(), localSVGParser.f());
/*      */         else {
/* 1669 */           moveTo(localSVGParser.f(), localSVGParser.f());
/*      */         }
/* 1671 */         while (localSVGParser.nextIsNumber()) {
/* 1672 */           lineToRel(localSVGParser.f(), localSVGParser.f()); continue;
/*      */           do
/*      */           {
/* 1677 */             lineTo(localSVGParser.f(), localSVGParser.f());
/* 1678 */           }while (localSVGParser.nextIsNumber());
/* 1679 */           break;
/*      */           do
/*      */           {
/* 1682 */             lineToRel(localSVGParser.f(), localSVGParser.f());
/* 1683 */           }while (localSVGParser.nextIsNumber());
/* 1684 */           break;
/*      */           do
/*      */           {
/* 1687 */             lineTo(localSVGParser.f(), this.currY);
/* 1688 */           }while (localSVGParser.nextIsNumber());
/* 1689 */           break;
/*      */           do
/*      */           {
/* 1692 */             lineToRel(localSVGParser.f(), 0.0F);
/* 1693 */           }while (localSVGParser.nextIsNumber());
/* 1694 */           break;
/*      */           do
/*      */           {
/* 1697 */             lineTo(this.currX, localSVGParser.f());
/* 1698 */           }while (localSVGParser.nextIsNumber());
/* 1699 */           break;
/*      */           do
/*      */           {
/* 1702 */             lineToRel(0.0F, localSVGParser.f());
/* 1703 */           }while (localSVGParser.nextIsNumber());
/* 1704 */           break;
/*      */           do
/*      */           {
/* 1707 */             quadTo(localSVGParser.f(), localSVGParser.f(), localSVGParser.f(), localSVGParser.f());
/* 1708 */           }while (localSVGParser.nextIsNumber());
/* 1709 */           break;
/*      */           do
/*      */           {
/* 1712 */             quadToRel(localSVGParser.f(), localSVGParser.f(), localSVGParser.f(), localSVGParser.f());
/* 1713 */           }while (localSVGParser.nextIsNumber());
/* 1714 */           break;
/*      */           do
/*      */           {
/* 1717 */             quadToSmooth(localSVGParser.f(), localSVGParser.f());
/* 1718 */           }while (localSVGParser.nextIsNumber());
/* 1719 */           break;
/*      */           do
/*      */           {
/* 1722 */             quadToSmoothRel(localSVGParser.f(), localSVGParser.f());
/* 1723 */           }while (localSVGParser.nextIsNumber());
/* 1724 */           break;
/*      */           do
/*      */           {
/* 1727 */             curveTo(localSVGParser.f(), localSVGParser.f(), localSVGParser.f(), localSVGParser.f(), localSVGParser.f(), localSVGParser.f());
/* 1728 */           }while (localSVGParser.nextIsNumber());
/* 1729 */           break;
/*      */           do
/*      */           {
/* 1732 */             curveToRel(localSVGParser.f(), localSVGParser.f(), localSVGParser.f(), localSVGParser.f(), localSVGParser.f(), localSVGParser.f());
/* 1733 */           }while (localSVGParser.nextIsNumber());
/* 1734 */           break;
/*      */           do
/*      */           {
/* 1737 */             curveToSmooth(localSVGParser.f(), localSVGParser.f(), localSVGParser.f(), localSVGParser.f());
/* 1738 */           }while (localSVGParser.nextIsNumber());
/* 1739 */           break;
/*      */           do
/*      */           {
/* 1742 */             curveToSmoothRel(localSVGParser.f(), localSVGParser.f(), localSVGParser.f(), localSVGParser.f());
/* 1743 */           }while (localSVGParser.nextIsNumber());
/* 1744 */           break;
/*      */           do
/*      */           {
/* 1747 */             arcTo(localSVGParser.f(), localSVGParser.f(), localSVGParser.a(), localSVGParser.b(), localSVGParser.b(), localSVGParser.f(), localSVGParser.f());
/* 1748 */           }while (localSVGParser.nextIsNumber());
/* 1749 */           break;
/*      */           do
/*      */           {
/* 1752 */             arcToRel(localSVGParser.f(), localSVGParser.f(), localSVGParser.a(), localSVGParser.b(), localSVGParser.b(), localSVGParser.f(), localSVGParser.f());
/* 1753 */           }while (localSVGParser.nextIsNumber());
/* 1754 */           break;
/* 1755 */           closePath(); break;
/*      */ 
/* 1757 */           throw new IllegalArgumentException("invalid command (" + c + ") in SVG path at pos=" + localSVGParser.pos);
/*      */         }
/*      */       }
/* 1760 */       localSVGParser.allowcomma = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final int getWindingRule()
/*      */   {
/* 1774 */     return this.windingRule;
/*      */   }
/*      */ 
/*      */   public final void setWindingRule(int paramInt)
/*      */   {
/* 1790 */     if ((paramInt != 0) && (paramInt != 1)) {
/* 1791 */       throw new IllegalArgumentException("winding rule must be WIND_EVEN_ODD or WIND_NON_ZERO");
/*      */     }
/*      */ 
/* 1795 */     this.windingRule = paramInt;
/*      */   }
/*      */ 
/*      */   public final Point2D getCurrentPoint()
/*      */   {
/* 1806 */     if (this.numTypes < 1) {
/* 1807 */       return null;
/*      */     }
/* 1809 */     return new Point2D(this.currX, this.currY);
/*      */   }
/*      */ 
/*      */   public final float getCurrentX() {
/* 1813 */     if (this.numTypes < 1) {
/* 1814 */       throw new IllegalPathStateException("no current point in empty path");
/*      */     }
/* 1816 */     return this.currX;
/*      */   }
/*      */ 
/*      */   public final float getCurrentY() {
/* 1820 */     if (this.numTypes < 1) {
/* 1821 */       throw new IllegalPathStateException("no current point in empty path");
/*      */     }
/* 1823 */     return this.currY;
/*      */   }
/*      */ 
/*      */   public final void reset()
/*      */   {
/* 1832 */     this.numTypes = (this.numCoords = 0);
/* 1833 */     this.moveX = (this.moveY = this.prevX = this.prevY = this.currX = this.currY = 0.0F);
/*      */   }
/*      */ 
/*      */   public final Shape createTransformedShape(BaseTransform paramBaseTransform)
/*      */   {
/* 1854 */     return new Path2D(this, paramBaseTransform);
/*      */   }
/*      */ 
/*      */   public Path2D copy()
/*      */   {
/* 1859 */     return new Path2D(this);
/*      */   }
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1873 */     if (paramObject == this) {
/* 1874 */       return true;
/*      */     }
/* 1876 */     if ((paramObject instanceof Path2D)) {
/* 1877 */       Path2D localPath2D = (Path2D)paramObject;
/* 1878 */       if ((localPath2D.numTypes == this.numTypes) && (localPath2D.numCoords == this.numCoords) && (localPath2D.windingRule == this.windingRule))
/*      */       {
/* 1882 */         for (int i = 0; i < this.numTypes; i++) {
/* 1883 */           if (localPath2D.pointTypes[i] != this.pointTypes[i]) {
/* 1884 */             return false;
/*      */           }
/*      */         }
/* 1887 */         for (i = 0; i < this.numCoords; i++) {
/* 1888 */           if (localPath2D.floatCoords[i] != this.floatCoords[i]) {
/* 1889 */             return false;
/*      */           }
/*      */         }
/* 1892 */         return true;
/*      */       }
/*      */     }
/* 1895 */     return false;
/*      */   }
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1900 */     int i = 7;
/* 1901 */     i = 11 * i + this.numTypes;
/* 1902 */     i = 11 * i + this.numCoords;
/* 1903 */     i = 11 * i + this.windingRule;
/* 1904 */     for (int j = 0; j < this.numTypes; j++) {
/* 1905 */       i = 11 * i + this.pointTypes[j];
/*      */     }
/* 1907 */     for (j = 0; j < this.numCoords; j++) {
/* 1908 */       i = 11 * i + Float.floatToIntBits(this.floatCoords[j]);
/*      */     }
/* 1910 */     return i;
/*      */   }
/*      */ 
/*      */   public static boolean contains(PathIterator paramPathIterator, float paramFloat1, float paramFloat2)
/*      */   {
/* 1929 */     if (paramFloat1 * 0.0F + paramFloat2 * 0.0F == 0.0F)
/*      */     {
/* 1933 */       int i = paramPathIterator.getWindingRule() == 1 ? -1 : 1;
/* 1934 */       int j = Shape.pointCrossingsForPath(paramPathIterator, paramFloat1, paramFloat2);
/* 1935 */       return (j & i) != 0;
/*      */     }
/*      */ 
/* 1942 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean contains(PathIterator paramPathIterator, Point2D paramPoint2D)
/*      */   {
/* 1961 */     return contains(paramPathIterator, paramPoint2D.x, paramPoint2D.y);
/*      */   }
/*      */ 
/*      */   public final boolean contains(float paramFloat1, float paramFloat2)
/*      */   {
/* 1969 */     if (paramFloat1 * 0.0F + paramFloat2 * 0.0F == 0.0F)
/*      */     {
/* 1973 */       if (this.numTypes < 2) {
/* 1974 */         return false;
/*      */       }
/* 1976 */       int i = this.windingRule == 1 ? -1 : 1;
/* 1977 */       return (pointCrossings(paramFloat1, paramFloat2) & i) != 0;
/*      */     }
/*      */ 
/* 1984 */     return false;
/*      */   }
/*      */ 
/*      */   public final boolean contains(Point2D paramPoint2D)
/*      */   {
/* 1994 */     return contains(paramPoint2D.x, paramPoint2D.y);
/*      */   }
/*      */ 
/*      */   public static boolean contains(PathIterator paramPathIterator, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/* 2031 */     if ((Float.isNaN(paramFloat1 + paramFloat3)) || (Float.isNaN(paramFloat2 + paramFloat4)))
/*      */     {
/* 2040 */       return false;
/*      */     }
/* 2042 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/* 2043 */       return false;
/*      */     }
/* 2045 */     int i = paramPathIterator.getWindingRule() == 1 ? -1 : 2;
/* 2046 */     int j = Shape.rectCrossingsForPath(paramPathIterator, paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
/* 2047 */     return (j != -2147483648) && ((j & i) != 0);
/*      */   }
/*      */ 
/*      */   public final boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/* 2071 */     if ((Float.isNaN(paramFloat1 + paramFloat3)) || (Float.isNaN(paramFloat2 + paramFloat4)))
/*      */     {
/* 2080 */       return false;
/*      */     }
/* 2082 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/* 2083 */       return false;
/*      */     }
/* 2085 */     int i = this.windingRule == 1 ? -1 : 2;
/* 2086 */     int j = rectCrossings(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
/* 2087 */     return (j != -2147483648) && ((j & i) != 0);
/*      */   }
/*      */ 
/*      */   public static boolean intersects(PathIterator paramPathIterator, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/* 2126 */     if ((Float.isNaN(paramFloat1 + paramFloat3)) || (Float.isNaN(paramFloat2 + paramFloat4)))
/*      */     {
/* 2135 */       return false;
/*      */     }
/* 2137 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/* 2138 */       return false;
/*      */     }
/* 2140 */     int i = paramPathIterator.getWindingRule() == 1 ? -1 : 2;
/* 2141 */     int j = Shape.rectCrossingsForPath(paramPathIterator, paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
/* 2142 */     return (j == -2147483648) || ((j & i) != 0);
/*      */   }
/*      */ 
/*      */   public final boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/* 2165 */     if ((Float.isNaN(paramFloat1 + paramFloat3)) || (Float.isNaN(paramFloat2 + paramFloat4)))
/*      */     {
/* 2174 */       return false;
/*      */     }
/* 2176 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/* 2177 */       return false;
/*      */     }
/* 2179 */     int i = this.windingRule == 1 ? -1 : 2;
/* 2180 */     int j = rectCrossings(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
/* 2181 */     return (j == -2147483648) || ((j & i) != 0);
/*      */   }
/*      */ 
/*      */   public PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat)
/*      */   {
/* 2199 */     return new FlatteningPathIterator(getPathIterator(paramBaseTransform), paramFloat);
/*      */   }
/*      */ 
/*      */   static byte[] copyOf(byte[] paramArrayOfByte, int paramInt)
/*      */   {
/* 2227 */     byte[] arrayOfByte = new byte[paramInt];
/* 2228 */     System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, Math.min(paramArrayOfByte.length, paramInt));
/*      */ 
/* 2230 */     return arrayOfByte;
/*      */   }
/*      */   static float[] copyOf(float[] paramArrayOfFloat, int paramInt) {
/* 2233 */     float[] arrayOfFloat = new float[paramInt];
/* 2234 */     System.arraycopy(paramArrayOfFloat, 0, arrayOfFloat, 0, Math.min(paramArrayOfFloat.length, paramInt));
/*      */ 
/* 2236 */     return arrayOfFloat;
/*      */   }
/*      */ 
/*      */   public void setTo(Path2D paramPath2D) {
/* 2240 */     this.numTypes = paramPath2D.numTypes;
/* 2241 */     this.numCoords = paramPath2D.numCoords;
/* 2242 */     if (this.numTypes > this.pointTypes.length) {
/* 2243 */       this.pointTypes = new byte[this.numTypes];
/*      */     }
/* 2245 */     System.arraycopy(paramPath2D.pointTypes, 0, this.pointTypes, 0, this.numTypes);
/* 2246 */     if (this.numCoords > this.floatCoords.length) {
/* 2247 */       this.floatCoords = new float[this.numCoords];
/*      */     }
/* 2249 */     System.arraycopy(paramPath2D.floatCoords, 0, this.floatCoords, 0, this.numCoords);
/* 2250 */     this.windingRule = paramPath2D.windingRule;
/* 2251 */     this.moveX = paramPath2D.moveX;
/* 2252 */     this.moveY = paramPath2D.moveY;
/* 2253 */     this.prevX = paramPath2D.prevX;
/* 2254 */     this.prevY = paramPath2D.prevY;
/* 2255 */     this.currX = paramPath2D.currX;
/* 2256 */     this.currY = paramPath2D.currY;
/*      */   }
/*      */ 
/*      */   static abstract class Iterator
/*      */     implements PathIterator
/*      */   {
/*      */     int typeIdx;
/*      */     int pointIdx;
/*      */     Path2D path;
/*      */ 
/*      */     Iterator(Path2D paramPath2D)
/*      */     {
/* 2208 */       this.path = paramPath2D;
/*      */     }
/*      */ 
/*      */     public int getWindingRule() {
/* 2212 */       return this.path.getWindingRule();
/*      */     }
/*      */ 
/*      */     public boolean isDone() {
/* 2216 */       return this.typeIdx >= this.path.numTypes;
/*      */     }
/*      */ 
/*      */     public void next() {
/* 2220 */       int i = this.path.pointTypes[(this.typeIdx++)];
/* 2221 */       this.pointIdx += Path2D.curvecoords[i];
/*      */     }
/*      */   }
/*      */ 
/*      */   static class SVGParser
/*      */   {
/*      */     final String svgpath;
/*      */     final int len;
/*      */     int pos;
/*      */     boolean allowcomma;
/*      */ 
/*      */     public SVGParser(String paramString)
/*      */     {
/* 1510 */       this.svgpath = paramString;
/* 1511 */       this.len = paramString.length();
/*      */     }
/*      */ 
/*      */     public boolean isDone() {
/* 1515 */       return toNextNonWsp() >= this.len;
/*      */     }
/*      */ 
/*      */     public char getChar() {
/* 1519 */       return this.svgpath.charAt(this.pos++);
/*      */     }
/*      */ 
/*      */     public boolean nextIsNumber() {
/* 1523 */       if (toNextNonWsp() < this.len)
/* 1524 */         switch (this.svgpath.charAt(this.pos)) { case '+':
/*      */         case '-':
/*      */         case '.':
/*      */         case '0':
/*      */         case '1':
/*      */         case '2':
/*      */         case '3':
/*      */         case '4':
/*      */         case '5':
/*      */         case '6':
/*      */         case '7':
/*      */         case '8':
/*      */         case '9':
/* 1530 */           return true;
/*      */         case ',':
/*      */         case '/': }
/* 1533 */       return false;
/*      */     }
/*      */ 
/*      */     public float f() {
/* 1537 */       return getFloat();
/*      */     }
/*      */ 
/*      */     public float a() {
/* 1541 */       return (float)Math.toRadians(getFloat());
/*      */     }
/*      */ 
/*      */     public float getFloat() {
/* 1545 */       int i = toNextNonWsp();
/* 1546 */       this.allowcomma = true;
/* 1547 */       int j = toNumberEnd();
/* 1548 */       if (i < j) {
/* 1549 */         String str = this.svgpath.substring(i, j);
/*      */         try {
/* 1551 */           return Float.parseFloat(str);
/*      */         }
/*      */         catch (NumberFormatException localNumberFormatException) {
/* 1554 */           throw new IllegalArgumentException("invalid float (" + str + ") in path at pos=" + i);
/*      */         }
/*      */       }
/* 1557 */       throw new IllegalArgumentException("end of path looking for float");
/*      */     }
/*      */ 
/*      */     public boolean b() {
/* 1561 */       toNextNonWsp();
/* 1562 */       this.allowcomma = true;
/* 1563 */       if (this.pos < this.len) {
/* 1564 */         char c = this.svgpath.charAt(this.pos);
/* 1565 */         switch (c) { case '0':
/* 1566 */           this.pos += 1; return false;
/*      */         case '1':
/* 1567 */           this.pos += 1; return true;
/*      */         }
/* 1569 */         throw new IllegalArgumentException("invalid boolean flag (" + c + ") in path at pos=" + this.pos);
/*      */       }
/*      */ 
/* 1572 */       throw new IllegalArgumentException("end of path looking for boolean");
/*      */     }
/*      */ 
/*      */     private int toNextNonWsp() {
/* 1576 */       boolean bool = this.allowcomma;
/* 1577 */       while (this.pos < this.len) {
/* 1578 */         switch (this.svgpath.charAt(this.pos)) {
/*      */         case ',':
/* 1580 */           if (!bool) {
/* 1581 */             return this.pos;
/*      */           }
/* 1583 */           bool = false;
/* 1584 */           break;
/*      */         case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/* 1589 */           break;
/*      */         default:
/* 1591 */           return this.pos;
/*      */         }
/* 1593 */         this.pos += 1;
/*      */       }
/* 1595 */       return this.pos;
/*      */     }
/*      */ 
/*      */     private int toNumberEnd() {
/* 1599 */       int i = 1;
/* 1600 */       int j = 0;
/* 1601 */       int k = 0;
/* 1602 */       while (this.pos < this.len) {
/* 1603 */         switch (this.svgpath.charAt(this.pos)) {
/*      */         case '+':
/*      */         case '-':
/* 1606 */           if (i == 0) return this.pos;
/* 1607 */           i = 0;
/* 1608 */           break;
/*      */         case '0':
/*      */         case '1':
/*      */         case '2':
/*      */         case '3':
/*      */         case '4':
/*      */         case '5':
/*      */         case '6':
/*      */         case '7':
/*      */         case '8':
/*      */         case '9':
/* 1611 */           i = 0;
/* 1612 */           break;
/*      */         case 'E':
/*      */         case 'e':
/* 1614 */           if (j != 0) return this.pos;
/* 1615 */           j = i = 1;
/* 1616 */           break;
/*      */         case '.':
/* 1618 */           if ((j != 0) || (k != 0)) return this.pos;
/* 1619 */           k = 1;
/* 1620 */           i = 0;
/* 1621 */           break;
/*      */         case ',':
/*      */         case '/':
/*      */         case ':':
/*      */         case ';':
/*      */         case '<':
/*      */         case '=':
/*      */         case '>':
/*      */         case '?':
/*      */         case '@':
/*      */         case 'A':
/*      */         case 'B':
/*      */         case 'C':
/*      */         case 'D':
/*      */         case 'F':
/*      */         case 'G':
/*      */         case 'H':
/*      */         case 'I':
/*      */         case 'J':
/*      */         case 'K':
/*      */         case 'L':
/*      */         case 'M':
/*      */         case 'N':
/*      */         case 'O':
/*      */         case 'P':
/*      */         case 'Q':
/*      */         case 'R':
/*      */         case 'S':
/*      */         case 'T':
/*      */         case 'U':
/*      */         case 'V':
/*      */         case 'W':
/*      */         case 'X':
/*      */         case 'Y':
/*      */         case 'Z':
/*      */         case '[':
/*      */         case '\\':
/*      */         case ']':
/*      */         case '^':
/*      */         case '_':
/*      */         case '`':
/*      */         case 'a':
/*      */         case 'b':
/*      */         case 'c':
/*      */         case 'd':
/*      */         default:
/* 1623 */           return this.pos;
/*      */         }
/* 1625 */         this.pos += 1;
/*      */       }
/* 1627 */       return this.pos;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class TxIterator extends Path2D.Iterator
/*      */   {
/*      */     float[] floatCoords;
/*      */     BaseTransform transform;
/*      */ 
/*      */     TxIterator(Path2D paramPath2D, BaseTransform paramBaseTransform)
/*      */     {
/* 1434 */       super();
/* 1435 */       this.floatCoords = paramPath2D.floatCoords;
/* 1436 */       this.transform = paramBaseTransform;
/*      */     }
/*      */ 
/*      */     public int currentSegment(float[] paramArrayOfFloat) {
/* 1440 */       int i = this.path.pointTypes[this.typeIdx];
/* 1441 */       int j = Path2D.curvecoords[i];
/* 1442 */       if (j > 0) {
/* 1443 */         this.transform.transform(this.floatCoords, this.pointIdx, paramArrayOfFloat, 0, j / 2);
/*      */       }
/*      */ 
/* 1446 */       return i;
/*      */     }
/*      */ 
/*      */     public int currentSegment(double[] paramArrayOfDouble) {
/* 1450 */       int i = this.path.pointTypes[this.typeIdx];
/* 1451 */       int j = Path2D.curvecoords[i];
/* 1452 */       if (j > 0) {
/* 1453 */         this.transform.transform(this.floatCoords, this.pointIdx, paramArrayOfDouble, 0, j / 2);
/*      */       }
/*      */ 
/* 1456 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class CopyIterator extends Path2D.Iterator
/*      */   {
/*      */     float[] floatCoords;
/*      */ 
/*      */     CopyIterator(Path2D paramPath2D)
/*      */     {
/* 1403 */       super();
/* 1404 */       this.floatCoords = paramPath2D.floatCoords;
/*      */     }
/*      */ 
/*      */     public int currentSegment(float[] paramArrayOfFloat) {
/* 1408 */       int i = this.path.pointTypes[this.typeIdx];
/* 1409 */       int j = Path2D.curvecoords[i];
/* 1410 */       if (j > 0) {
/* 1411 */         System.arraycopy(this.floatCoords, this.pointIdx, paramArrayOfFloat, 0, j);
/*      */       }
/*      */ 
/* 1414 */       return i;
/*      */     }
/*      */ 
/*      */     public int currentSegment(double[] paramArrayOfDouble) {
/* 1418 */       int i = this.path.pointTypes[this.typeIdx];
/* 1419 */       int j = Path2D.curvecoords[i];
/* 1420 */       if (j > 0) {
/* 1421 */         for (int k = 0; k < j; k++) {
/* 1422 */           paramArrayOfDouble[k] = this.floatCoords[(this.pointIdx + k)];
/*      */         }
/*      */       }
/* 1425 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static enum CornerPrefix
/*      */   {
/*   70 */     CORNER_ONLY, 
/*   71 */     MOVE_THEN_CORNER, 
/*   72 */     LINE_THEN_CORNER;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Path2D
 * JD-Core Version:    0.6.2
 */