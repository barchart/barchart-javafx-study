/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.Path2D.CornerPrefix;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.Affine2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.sg.BackgroundFill;
/*     */ import com.sun.javafx.sg.BackgroundImage;
/*     */ import com.sun.javafx.sg.BaseEffectFilter;
/*     */ import com.sun.javafx.sg.Border;
/*     */ import com.sun.javafx.sg.BorderStyle;
/*     */ import com.sun.javafx.sg.ImageBorder;
/*     */ import com.sun.javafx.sg.PGRegion;
/*     */ import com.sun.javafx.sg.PGShape;
/*     */ import com.sun.javafx.sg.PGShape.StrokeLineCap;
/*     */ import com.sun.javafx.sg.PGShape.StrokeLineJoin;
/*     */ import com.sun.javafx.sg.PGShape.StrokeType;
/*     */ import com.sun.javafx.sg.Repeat;
/*     */ import com.sun.javafx.sg.StrokedBorder;
/*     */ import com.sun.prism.BasicStroke;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.ImagePattern;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import com.sun.scenario.effect.Offset;
/*     */ 
/*     */ public class NGRegion extends NGGroup
/*     */   implements PGRegion
/*     */ {
/*     */   private float x1;
/*     */   private float y1;
/*     */   private float x2;
/*     */   private float y2;
/*     */   private Shape shape;
/*     */   private float width;
/*     */   private float height;
/*     */   private BackgroundFill[] backgroundFills;
/*     */   private Shape[] backgroundShapeCache;
/*     */   private BackgroundImage[] backgroundImages;
/*     */   private Border[] borders;
/*     */   private Shape[] borderShapeCache;
/*     */   private boolean resizeShape;
/*     */   private boolean positionShape;
/*  37 */   private static Offset nopEffect = new Offset(0, 0, null);
/*     */   private BaseEffectFilter nopEffectFilter;
/* 655 */   private static final float[] SIN_VALS = { 1.0F, 0.0F, -1.0F, 0.0F, 1.0F, 0.0F };
/*     */ 
/*     */   public void setBorders(Border[] paramArrayOfBorder)
/*     */   {
/*  41 */     this.borders = paramArrayOfBorder;
/*  42 */     updateGeom();
/*     */   }
/*     */ 
/*     */   public void setBackgroundImages(BackgroundImage[] paramArrayOfBackgroundImage) {
/*  46 */     this.backgroundImages = paramArrayOfBackgroundImage;
/*  47 */     updateGeom();
/*     */   }
/*     */ 
/*     */   public void setBackgroundFills(BackgroundFill[] paramArrayOfBackgroundFill) {
/*  51 */     this.backgroundFills = paramArrayOfBackgroundFill;
/*  52 */     updateGeom();
/*     */   }
/*     */ 
/*     */   public void setShape(PGShape paramPGShape) {
/*  56 */     this.shape = (paramPGShape == null ? null : ((NGShape)paramPGShape).getShape());
/*  57 */     updateGeom();
/*     */   }
/*     */ 
/*     */   public void setResizeShape(boolean paramBoolean) {
/*  61 */     if (this.resizeShape != paramBoolean) {
/*  62 */       this.resizeShape = paramBoolean;
/*  63 */       updateGeom();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setPositionShape(boolean paramBoolean) {
/*  68 */     if (this.positionShape != paramBoolean) {
/*  69 */       this.positionShape = paramBoolean;
/*  70 */       updateGeom();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setSize(float paramFloat1, float paramFloat2) {
/*  75 */     if ((this.width != paramFloat1) || (this.height != paramFloat2)) {
/*  76 */       this.width = paramFloat1;
/*  77 */       this.height = paramFloat2;
/*  78 */       updateGeom();
/*     */     }
/*     */   }
/*     */ 
/*     */   private float getReducingRatio(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/*  85 */     float f = 1.0F;
/*     */ 
/*  87 */     if (paramFloat3 + paramFloat4 > paramFloat1) {
/*  88 */       f = Math.min(f, paramFloat1 / (paramFloat3 + paramFloat4));
/*     */     }
/*  90 */     if (paramFloat4 + paramFloat6 > paramFloat2) {
/*  91 */       f = Math.min(f, paramFloat2 / (paramFloat4 + paramFloat6));
/*     */     }
/*  93 */     if (paramFloat6 + paramFloat5 > paramFloat1) {
/*  94 */       f = Math.min(f, paramFloat1 / (paramFloat6 + paramFloat5));
/*     */     }
/*  96 */     if (paramFloat5 + paramFloat3 > paramFloat2) {
/*  97 */       f = Math.min(f, paramFloat2 / (paramFloat5 + paramFloat3));
/*     */     }
/*  99 */     return f;
/*     */   }
/*     */ 
/*     */   private void updateGeom()
/*     */   {
/*     */     float f1;
/*     */     float f2;
/*     */     float f3;
/*     */     float f4;
/* 105 */     if ((this.shape != null) && (!this.resizeShape)) {
/* 106 */       RectBounds localRectBounds = this.shape.getBounds();
/* 107 */       if (this.positionShape) {
/* 108 */         f1 = (this.width - localRectBounds.getWidth()) / 2.0F;
/* 109 */         f2 = (this.height - localRectBounds.getHeight()) / 2.0F;
/* 110 */         f3 = f1 + localRectBounds.getWidth();
/* 111 */         f4 = f2 + localRectBounds.getHeight();
/*     */       } else {
/* 113 */         f1 = localRectBounds.getMinX();
/* 114 */         f2 = localRectBounds.getMinY();
/* 115 */         f3 = localRectBounds.getMaxX();
/* 116 */         f4 = localRectBounds.getMaxY();
/*     */       }
/*     */     } else {
/* 119 */       f1 = f2 = 0.0F;
/* 120 */       f3 = this.width;
/* 121 */       f4 = this.height;
/*     */     }
/*     */ 
/* 124 */     float f5 = f1; float f6 = f2; float f7 = f3; float f8 = f4;
/*     */     Object localObject3;
/* 125 */     if (this.backgroundFills != null) {
/* 126 */       for (localObject3 : this.backgroundFills) {
/* 127 */         f5 = Math.min(f5, f1 + localObject3.leftOffset);
/* 128 */         f6 = Math.min(f6, f2 + localObject3.topOffset);
/* 129 */         f7 = Math.max(f7, f3 - localObject3.rightOffset);
/* 130 */         f8 = Math.max(f8, f4 - localObject3.bottomOffset);
/*     */       }
/*     */     }
/* 133 */     if (this.backgroundImages != null) {
/* 134 */       for (localObject3 : this.backgroundImages) {
/* 135 */         f5 = Math.min(f5, f1 + localObject3.left);
/* 136 */         f6 = Math.min(f6, f2 + localObject3.top);
/* 137 */         f7 = Math.max(f7, f3 - localObject3.right);
/* 138 */         f8 = Math.max(f8, f4 - localObject3.bottom);
/*     */       }
/*     */     }
/* 141 */     if (this.borders != null) {
/* 142 */       for (localObject3 : this.borders)
/*     */       {
/* 144 */         f5 = Math.min(f5, f1 + localObject3.leftOffset - localObject3.leftWidth / 2.0F);
/* 145 */         f6 = Math.min(f6, f2 + localObject3.topOffset - localObject3.topWidth / 2.0F);
/* 146 */         f7 = Math.max(f7, f3 - localObject3.rightOffset + localObject3.rightWidth / 2.0F);
/* 147 */         f8 = Math.max(f8, f4 - localObject3.bottomOffset + localObject3.bottomWidth / 2.0F);
/*     */       }
/*     */     }
/*     */ 
/* 151 */     if ((this.x1 != f5) || (this.y1 != f6) || (this.x2 != f7) || (this.y2 != f8)) {
/* 152 */       this.x1 = f5;
/* 153 */       this.x2 = f7;
/* 154 */       this.y1 = f6;
/* 155 */       this.y2 = f8;
/* 156 */       geometryChanged();
/*     */     } else {
/* 158 */       visualsChanged();
/*     */     }
/*     */ 
/* 161 */     if (this.shape == null) {
/* 162 */       this.backgroundShapeCache = null;
/* 163 */       this.borderShapeCache = null;
/*     */     } else {
/* 165 */       this.backgroundShapeCache = new Shape[this.backgroundFills.length];
/*     */       Object localObject2;
/*     */       float f9;
/*     */       float f10;
/*     */       float f11;
/*     */       float f12;
/* 166 */       for (int i = 0; i < this.backgroundFills.length; i++) {
/* 167 */         localObject2 = this.backgroundFills[i];
/* 168 */         f9 = ((BackgroundFill)localObject2).topOffset; f10 = ((BackgroundFill)localObject2).leftOffset; f11 = ((BackgroundFill)localObject2).bottomOffset; f12 = ((BackgroundFill)localObject2).rightOffset;
/* 169 */         this.backgroundShapeCache[i] = createShape(this.shape, f9, f10, f11, f12);
/*     */       }
/* 171 */       this.borderShapeCache = new Shape[this.borders.length];
/* 172 */       for (i = 0; i < this.borders.length; i++)
/* 173 */         if ((this.borders[i] instanceof StrokedBorder)) {
/* 174 */           localObject2 = (StrokedBorder)this.borders[i];
/* 175 */           f9 = ((StrokedBorder)localObject2).topOffset + ((StrokedBorder)localObject2).topWidth / 2.0F;
/* 176 */           f10 = ((StrokedBorder)localObject2).leftOffset + ((StrokedBorder)localObject2).leftWidth / 2.0F;
/* 177 */           f11 = ((StrokedBorder)localObject2).bottomOffset + ((StrokedBorder)localObject2).bottomWidth / 2.0F;
/* 178 */           f12 = ((StrokedBorder)localObject2).rightOffset + ((StrokedBorder)localObject2).rightWidth / 2.0F;
/* 179 */           this.borderShapeCache[i] = createShape(this.shape, f9, f10, f11, f12);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void renderContent(Graphics paramGraphics)
/*     */   {
/* 196 */     if ((!paramGraphics.getTransformNoClone().is2D()) && (isContentBounds2D())) {
/* 197 */       assert (getEffectFilter() == null);
/*     */ 
/* 202 */       if (this.nopEffectFilter == null) {
/* 203 */         this.nopEffectFilter = createEffectFilter(nopEffect);
/*     */       }
/* 205 */       ((NGNode.EffectFilter)this.nopEffectFilter).render(paramGraphics);
/*     */ 
/* 207 */       return;
/*     */     }
/*     */ 
/* 211 */     if (this.backgroundFills != null)
/* 212 */       for (int i = 0; i < this.backgroundFills.length; i++) {
/* 213 */         BackgroundFill localBackgroundFill = this.backgroundFills[i];
/* 214 */         paramGraphics.setPaint((Paint)localBackgroundFill.fill);
/* 215 */         fillShape(paramGraphics, localBackgroundFill, i);
/*     */       }
/*     */     Image localImage1;
/*     */     Object localObject3;
/* 219 */     if (this.backgroundImages != null) {
/* 220 */       for (localImage1 : this.backgroundImages) {
/* 221 */         localObject3 = (Image)localImage1.getImage();
/* 222 */         if (localObject3 != null)
/*     */         {
/*     */           float f3;
/* 228 */           if (localImage1.getProportionalWidth())
/* 229 */             f3 = localImage1.getWidth() * this.width;
/*     */           else
/* 231 */             f3 = localImage1.getWidth();
/*     */           float f4;
/* 234 */           if (localImage1.getProportionalHeight())
/* 235 */             f4 = localImage1.getHeight() * this.height;
/*     */           else
/* 237 */             f4 = localImage1.getHeight();
/*     */           float f7;
/*     */           float f1;
/*     */           float f2;
/* 241 */           if ((localImage1.getCover()) || (localImage1.getContain())) {
/* 242 */             f5 = this.width / ((Image)localObject3).getWidth();
/* 243 */             f6 = this.height / ((Image)localObject3).getHeight();
/* 244 */             f7 = localImage1.getContain() ? Math.min(f5, f6) : Math.max(f5, f6);
/* 245 */             f1 = (float)Math.ceil(f7 * ((Image)localObject3).getWidth());
/* 246 */             f2 = (float)Math.ceil(f7 * ((Image)localObject3).getHeight());
/*     */           }
/* 248 */           else if ((localImage1.getWidth() > 0.0F) && (localImage1.getHeight() > 0.0F))
/*     */           {
/* 250 */             f1 = f3;
/* 251 */             f2 = f4;
/* 252 */           } else if (f3 > 0.0F) {
/* 253 */             f1 = f3;
/* 254 */             f5 = f1 / ((Image)localObject3).getWidth();
/* 255 */             f2 = ((Image)localObject3).getHeight() * f5;
/* 256 */           } else if (f4 > 0.0F) {
/* 257 */             f2 = f4;
/* 258 */             f5 = f2 / ((Image)localObject3).getHeight();
/* 259 */             f1 = ((Image)localObject3).getWidth() * f5;
/*     */           } else {
/* 261 */             f1 = ((Image)localObject3).getWidth();
/* 262 */             f2 = ((Image)localObject3).getHeight();
/*     */           }
/*     */ 
/* 267 */           float f5 = 0.0F; float f6 = 0.0F;
/*     */           float f8;
/* 271 */           if (localImage1.getProportionalHPos()) {
/* 272 */             f7 = localImage1.getLeft() * (this.width - f1);
/* 273 */             f8 = localImage1.getRight() * (this.width - f1);
/*     */           } else {
/* 275 */             f7 = localImage1.getLeft();
/* 276 */             f8 = localImage1.getRight();
/*     */           }
/*     */           float f9;
/*     */           float f10;
/* 279 */           if (localImage1.getProportionalVPos()) {
/* 280 */             f9 = localImage1.getTop() * (this.height - f2);
/* 281 */             f10 = localImage1.getBottom() * (this.height - f2);
/*     */           } else {
/* 283 */             f9 = localImage1.getTop();
/* 284 */             f10 = localImage1.getBottom();
/*     */           }
/*     */ 
/* 288 */           if (f7 != 0.0F)
/* 289 */             f5 = f7;
/* 290 */           else if (f8 != 0.0F) {
/* 291 */             f5 = this.width - f8;
/*     */           }
/* 293 */           if (f9 != 0.0F)
/* 294 */             f6 = f9;
/* 295 */           else if (f10 != 0.0F) {
/* 296 */             f6 = this.height - f10;
/*     */           }
/*     */ 
/* 300 */           paintTiles(paramGraphics, (Image)localObject3, localImage1.getRepeatX(), localImage1.getRepeatY(), 0.0F, 0.0F, this.width, this.height, 0, 0, -1, -1, f5, f6, f1, f2);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 308 */     if (this.borders != null)
/*     */     {
/*     */       int i2;
/*     */       int i3;
/*     */       int i4;
/*     */       int i5;
/*     */       int i6;
/*     */       int i7;
/* 309 */       for (localImage1 : this.borders) {
/* 310 */         if (((localImage1 instanceof ImageBorder)) && (((ImageBorder)localImage1).fillCenter))
/*     */         {
/* 312 */           localObject3 = (ImageBorder)localImage1;
/* 313 */           Image localImage2 = (Image)((ImageBorder)localObject3).image;
/* 314 */           if (localImage2 != null)
/*     */           {
/* 316 */             i2 = Math.round(((ImageBorder)localObject3).leftOffset) + Math.round(((ImageBorder)localObject3).leftWidth);
/* 317 */             i3 = Math.round(((ImageBorder)localObject3).topOffset) + Math.round(((ImageBorder)localObject3).topWidth);
/* 318 */             i4 = Math.round(this.width) - Math.round(((ImageBorder)localObject3).rightOffset) - Math.round(((ImageBorder)localObject3).rightWidth) - i2;
/* 319 */             i5 = Math.round(this.height) - Math.round(((ImageBorder)localObject3).bottomOffset) - Math.round(((ImageBorder)localObject3).bottomWidth) - i3;
/* 320 */             i6 = ((ImageBorder)localObject3).repeatX == Repeat.NO_REPEAT ? i4 : -1;
/* 321 */             i7 = ((ImageBorder)localObject3).repeatY == Repeat.NO_REPEAT ? i5 : -1;
/* 322 */             paintTiles(paramGraphics, localImage2, ((ImageBorder)localObject3).repeatX, ((ImageBorder)localObject3).repeatY, i2, i3, i4, i5, (int)((ImageBorder)localObject3).leftSlice, (int)((ImageBorder)localObject3).topSlice, (int)(localImage2.getWidth() - ((ImageBorder)localObject3).leftSlice - ((ImageBorder)localObject3).rightSlice), (int)(localImage2.getHeight() - ((ImageBorder)localObject3).topSlice - ((ImageBorder)localObject3).bottomSlice), 0.0F, 0.0F, i6, i7);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 332 */       for (int j = 0; j < this.borders.length; j++) {
/* 333 */         Border localBorder = this.borders[j];
/*     */         Object localObject2;
/* 334 */         if ((localBorder instanceof StrokedBorder)) {
/* 335 */           localObject2 = (StrokedBorder)localBorder;
/* 336 */           fillShape(paramGraphics, (StrokedBorder)localObject2, j);
/*     */         } else {
/* 338 */           localObject2 = (ImageBorder)localBorder;
/* 339 */           localImage1 = (Image)((ImageBorder)localObject2).image;
/* 340 */           if (localImage1 != null)
/*     */           {
/* 342 */             int n = Math.round(((ImageBorder)localObject2).leftWidth);
/* 343 */             int i1 = Math.round(((ImageBorder)localObject2).rightWidth);
/* 344 */             i2 = Math.round(((ImageBorder)localObject2).topWidth);
/* 345 */             i3 = Math.round(((ImageBorder)localObject2).bottomWidth);
/* 346 */             i4 = Math.round(((ImageBorder)localObject2).leftOffset);
/* 347 */             i5 = Math.round(((ImageBorder)localObject2).rightOffset);
/* 348 */             i6 = Math.round(((ImageBorder)localObject2).topOffset);
/* 349 */             i7 = Math.round(((ImageBorder)localObject2).bottomOffset);
/* 350 */             int i8 = Math.round(((ImageBorder)localObject2).leftSlice);
/* 351 */             int i9 = Math.round(((ImageBorder)localObject2).rightSlice);
/* 352 */             int i10 = Math.round(((ImageBorder)localObject2).topSlice);
/* 353 */             int i11 = Math.round(((ImageBorder)localObject2).bottomSlice);
/*     */ 
/* 355 */             if ((i4 + n + i5 + i1 <= this.width) && (i6 + i2 + i7 + i3 <= this.height))
/*     */             {
/* 360 */               int i12 = i4 + n;
/* 361 */               int i13 = i6 + i2;
/* 362 */               int i14 = Math.round(this.width) - i5 - i1 - i12;
/* 363 */               int i15 = Math.round(this.height) - i7 - i3 - i13;
/* 364 */               int i16 = i14 + i12;
/* 365 */               int i17 = i15 + i13;
/*     */ 
/* 367 */               float f11 = ((ImageBorder)localObject2).repeatX == Repeat.NO_REPEAT ? i14 : -1.0F;
/* 368 */               float f12 = ((ImageBorder)localObject2).repeatY == Repeat.NO_REPEAT ? i15 : -1.0F;
/*     */ 
/* 370 */               paintTiles(paramGraphics, localImage1, Repeat.NO_REPEAT, Repeat.NO_REPEAT, i4, i6, n, i2, 0, 0, i8, i10, 0.0F, 0.0F, n, i2);
/*     */ 
/* 375 */               paintTiles(paramGraphics, localImage1, ((ImageBorder)localObject2).repeatX, Repeat.NO_REPEAT, i12, i6, i14, i2, i8, 0, localImage1.getWidth() - i8 - i9, i10, 0.0F, 0.0F, f11, i2);
/*     */ 
/* 382 */               paintTiles(paramGraphics, localImage1, Repeat.NO_REPEAT, Repeat.NO_REPEAT, i16, i6, i1, i2, localImage1.getWidth() - i9, 0, i9, i10, 0.0F, 0.0F, i1, i2);
/*     */ 
/* 387 */               paintTiles(paramGraphics, localImage1, Repeat.NO_REPEAT, ((ImageBorder)localObject2).repeatY, i4, i13, n, i15, 0, i10, i8, localImage1.getHeight() - i10 - i11, 0.0F, 0.0F, n, f12);
/*     */ 
/* 393 */               paintTiles(paramGraphics, localImage1, Repeat.NO_REPEAT, ((ImageBorder)localObject2).repeatY, i16, i13, i1, i15, localImage1.getWidth() - i9, i10, i9, localImage1.getHeight() - i10 - i11, 0.0F, 0.0F, i1, f12);
/*     */ 
/* 399 */               paintTiles(paramGraphics, localImage1, Repeat.NO_REPEAT, Repeat.NO_REPEAT, i4, i17, n, i3, 0, localImage1.getHeight() - i11, i8, i11, 0.0F, 0.0F, n, i3);
/*     */ 
/* 404 */               paintTiles(paramGraphics, localImage1, ((ImageBorder)localObject2).repeatX, Repeat.NO_REPEAT, i12, i17, i14, i3, i8, localImage1.getHeight() - i11, localImage1.getWidth() - i8 - i9, i11, 0.0F, 0.0F, f11, i3);
/*     */ 
/* 411 */               paintTiles(paramGraphics, localImage1, Repeat.NO_REPEAT, Repeat.NO_REPEAT, i16, i17, i1, i3, localImage1.getWidth() - i9, localImage1.getHeight() - i11, i9, i11, 0.0F, 0.0F, i1, i3);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 420 */     super.renderContent(paramGraphics);
/*     */   }
/*     */ 
/*     */   public BasicStroke createStroke(BorderStyle paramBorderStyle, float paramFloat)
/*     */   {
/*     */     int i;
/* 425 */     if (paramBorderStyle.lineCap == PGShape.StrokeLineCap.BUTT)
/* 426 */       i = 0;
/* 427 */     else if (paramBorderStyle.lineCap == PGShape.StrokeLineCap.SQUARE)
/* 428 */       i = 2;
/*     */     else
/* 430 */       i = 1;
/*     */     int j;
/* 434 */     if (paramBorderStyle.lineJoin == PGShape.StrokeLineJoin.BEVEL)
/* 435 */       j = 2;
/* 436 */     else if (paramBorderStyle.lineJoin == PGShape.StrokeLineJoin.MITER)
/* 437 */       j = 0;
/*     */     else {
/* 439 */       j = 1;
/*     */     }
/*     */ 
/* 442 */     if (paramBorderStyle.strokeType != PGShape.StrokeType.CENTERED)
/* 443 */       paramFloat *= 2.0F;
/*     */     BasicStroke localBasicStroke;
/* 447 */     if (paramBorderStyle.strokeDashArray.length > 0) {
/* 448 */       localBasicStroke = new BasicStroke(paramFloat, i, j, paramBorderStyle.strokeMiterLimit, paramBorderStyle.strokeDashArray, paramBorderStyle.strokeDashOffset);
/*     */     }
/*     */     else
/*     */     {
/* 452 */       localBasicStroke = new BasicStroke(paramFloat, i, j, paramBorderStyle.strokeMiterLimit);
/*     */     }
/*     */ 
/* 456 */     return localBasicStroke;
/*     */   }
/*     */ 
/*     */   private void fillShape(Graphics paramGraphics, StrokedBorder paramStrokedBorder, int paramInt) {
/* 460 */     float f1 = paramStrokedBorder.topOffset + paramStrokedBorder.topWidth / 2.0F;
/* 461 */     float f2 = paramStrokedBorder.leftOffset + paramStrokedBorder.leftWidth / 2.0F;
/* 462 */     float f3 = paramStrokedBorder.bottomOffset + paramStrokedBorder.bottomWidth / 2.0F;
/* 463 */     float f4 = paramStrokedBorder.rightOffset + paramStrokedBorder.rightWidth / 2.0F;
/* 464 */     float f5 = paramStrokedBorder.topLeftRadius; float f6 = paramStrokedBorder.topRightRadius; float f7 = paramStrokedBorder.bottomLeftRadius; float f8 = paramStrokedBorder.bottomRightRadius;
/* 465 */     int i = (f5 == f6) && (f5 == f7) && (f5 == f8) ? 1 : 0;
/* 466 */     int j = ((paramStrokedBorder.topFill == null) && (paramStrokedBorder.rightFill == null) && (paramStrokedBorder.bottomFill == null) && (paramStrokedBorder.leftFill == null)) || ((paramStrokedBorder.leftFill != null) && (paramStrokedBorder.leftFill.equals(paramStrokedBorder.topFill)) && (paramStrokedBorder.leftFill.equals(paramStrokedBorder.rightFill)) && (paramStrokedBorder.leftFill.equals(paramStrokedBorder.bottomFill))) ? 1 : 0;
/*     */ 
/* 468 */     int k = (paramStrokedBorder.leftWidth == paramStrokedBorder.topWidth) && (paramStrokedBorder.leftWidth == paramStrokedBorder.rightWidth) && (paramStrokedBorder.leftWidth == paramStrokedBorder.bottomWidth) ? 1 : 0;
/* 469 */     int m = (paramStrokedBorder.topStyle == paramStrokedBorder.bottomStyle) && (paramStrokedBorder.topStyle == paramStrokedBorder.leftStyle) && (paramStrokedBorder.topStyle == paramStrokedBorder.rightStyle) ? 1 : 0;
/*     */ 
/* 471 */     if (this.shape != null) {
/* 472 */       setBorderStyle(paramGraphics, paramStrokedBorder);
/* 473 */       paramGraphics.draw(this.borderShapeCache[paramInt]);
/* 474 */     } else if ((j != 0) && (k != 0) && (m != 0)) {
/* 475 */       float f9 = this.width - f2 - f4;
/* 476 */       float f10 = this.height - f1 - f3;
/* 477 */       if ((f9 >= 0.0F) && (f10 >= 0.0F)) {
/* 478 */         setBorderStyle(paramGraphics, paramStrokedBorder);
/* 479 */         if ((i != 0) && (f5 == 0.0F)) {
/* 480 */           paramGraphics.drawRect(f2, f1, f9, f10);
/* 481 */         } else if (i != 0) {
/* 482 */           float f11 = f5 + f5;
/* 483 */           if (f11 > f9) f11 = f9;
/* 484 */           if (f11 > f10) f11 = f10;
/* 485 */           paramGraphics.drawRoundRect(f2, f1, f9, f10, f11, f11);
/*     */         } else {
/* 487 */           paramGraphics.draw(createPath(f1, f2, f3, f4, f5, f6, f7, f8));
/*     */         }
/*     */       }
/* 490 */     } else if ((i != 0) && (f5 == 0.0F)) {
/* 491 */       if ((paramStrokedBorder.topStyle != null) && (paramStrokedBorder.topFill != null) && (paramStrokedBorder.topFill != Color.TRANSPARENT)) {
/* 492 */         paramGraphics.setPaint((Paint)paramStrokedBorder.topFill);
/* 493 */         if (BorderStyle.SOLID == paramStrokedBorder.topStyle) {
/* 494 */           paramGraphics.fillRect(paramStrokedBorder.leftOffset, paramStrokedBorder.topOffset, this.width - paramStrokedBorder.leftOffset - paramStrokedBorder.rightOffset, paramStrokedBorder.topWidth);
/*     */         }
/*     */         else
/*     */         {
/* 498 */           paramGraphics.setStroke(createStroke(paramStrokedBorder.topStyle, paramStrokedBorder.topWidth));
/* 499 */           paramGraphics.drawLine(f2, f1, this.width - f4, f1);
/*     */         }
/*     */       }
/*     */ 
/* 503 */       if ((paramStrokedBorder.bottomStyle != null) && (paramStrokedBorder.bottomFill != null) && (paramStrokedBorder.bottomFill != Color.TRANSPARENT)) {
/* 504 */         paramGraphics.setPaint((Paint)paramStrokedBorder.bottomFill);
/* 505 */         if (BorderStyle.SOLID == paramStrokedBorder.bottomStyle) {
/* 506 */           paramGraphics.fillRect(paramStrokedBorder.leftOffset, this.height - paramStrokedBorder.bottomOffset - paramStrokedBorder.bottomWidth, this.width - paramStrokedBorder.leftOffset - paramStrokedBorder.rightOffset, paramStrokedBorder.bottomWidth);
/*     */         }
/*     */         else
/*     */         {
/* 511 */           paramGraphics.setStroke(createStroke(paramStrokedBorder.bottomStyle, paramStrokedBorder.bottomWidth));
/* 512 */           paramGraphics.drawLine(f2, this.height - f3, this.width - f4, this.height - f3);
/*     */         }
/*     */       }
/*     */ 
/* 516 */       if ((paramStrokedBorder.rightStyle != null) && (paramStrokedBorder.rightFill != null) && (paramStrokedBorder.rightFill != Color.TRANSPARENT)) {
/* 517 */         paramGraphics.setPaint((Paint)paramStrokedBorder.rightFill);
/* 518 */         if (BorderStyle.SOLID == paramStrokedBorder.rightStyle) {
/* 519 */           paramGraphics.fillRect(this.width - paramStrokedBorder.rightOffset - paramStrokedBorder.rightWidth, paramStrokedBorder.topOffset, paramStrokedBorder.rightWidth, this.height - paramStrokedBorder.topOffset - paramStrokedBorder.bottomOffset);
/*     */         }
/*     */         else
/*     */         {
/* 524 */           paramGraphics.setStroke(createStroke(paramStrokedBorder.rightStyle, paramStrokedBorder.rightWidth));
/* 525 */           paramGraphics.drawLine(this.width - f4, paramStrokedBorder.topOffset, this.width - f4, this.height - paramStrokedBorder.bottomOffset);
/*     */         }
/*     */       }
/*     */ 
/* 529 */       if ((paramStrokedBorder.leftStyle != null) && (paramStrokedBorder.leftFill != null) && (paramStrokedBorder.leftFill != Color.TRANSPARENT)) {
/* 530 */         paramGraphics.setPaint((Paint)paramStrokedBorder.leftFill);
/* 531 */         if (BorderStyle.SOLID == paramStrokedBorder.leftStyle) {
/* 532 */           paramGraphics.fillRect(paramStrokedBorder.leftOffset, paramStrokedBorder.topOffset, paramStrokedBorder.leftWidth, this.height - paramStrokedBorder.topOffset - paramStrokedBorder.bottomOffset);
/*     */         }
/*     */         else
/*     */         {
/* 537 */           paramGraphics.setStroke(createStroke(paramStrokedBorder.leftStyle, paramStrokedBorder.leftWidth));
/* 538 */           paramGraphics.drawLine(f2, paramStrokedBorder.topOffset, f2, this.height - paramStrokedBorder.bottomOffset);
/*     */         }
/*     */       }
/*     */     } else {
/* 542 */       Path2D[] arrayOfPath2D = createPaths(f1, f2, f3, f4, f5, f6, f7, f8);
/* 543 */       if (paramStrokedBorder.topStyle != null) {
/* 544 */         paramGraphics.setStroke(createStroke(paramStrokedBorder.topStyle, paramStrokedBorder.topWidth));
/* 545 */         paramGraphics.setPaint((Paint)paramStrokedBorder.topFill);
/* 546 */         paramGraphics.draw(arrayOfPath2D[0]);
/*     */       }
/* 548 */       if (paramStrokedBorder.rightStyle != null) {
/* 549 */         paramGraphics.setStroke(createStroke(paramStrokedBorder.rightStyle, paramStrokedBorder.rightWidth));
/* 550 */         paramGraphics.setPaint((Paint)paramStrokedBorder.rightFill);
/* 551 */         paramGraphics.draw(arrayOfPath2D[1]);
/*     */       }
/* 553 */       if (paramStrokedBorder.bottomStyle != null) {
/* 554 */         paramGraphics.setStroke(createStroke(paramStrokedBorder.bottomStyle, paramStrokedBorder.bottomWidth));
/* 555 */         paramGraphics.setPaint((Paint)paramStrokedBorder.bottomFill);
/* 556 */         paramGraphics.draw(arrayOfPath2D[2]);
/*     */       }
/* 558 */       if (paramStrokedBorder.leftStyle != null) {
/* 559 */         paramGraphics.setStroke(createStroke(paramStrokedBorder.leftStyle, paramStrokedBorder.leftWidth));
/* 560 */         paramGraphics.setPaint((Paint)paramStrokedBorder.leftFill);
/* 561 */         paramGraphics.draw(arrayOfPath2D[3]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setBorderStyle(Graphics paramGraphics, StrokedBorder paramStrokedBorder)
/*     */   {
/* 569 */     BorderStyle localBorderStyle = paramStrokedBorder.topStyle;
/* 570 */     float f = paramStrokedBorder.topWidth;
/* 571 */     Object localObject = paramStrokedBorder.topFill;
/* 572 */     if (localBorderStyle == null) {
/* 573 */       localBorderStyle = paramStrokedBorder.leftStyle;
/* 574 */       f = paramStrokedBorder.leftWidth;
/* 575 */       localObject = paramStrokedBorder.leftFill;
/* 576 */       if (localBorderStyle == null) {
/* 577 */         localBorderStyle = paramStrokedBorder.bottomStyle;
/* 578 */         f = paramStrokedBorder.bottomWidth;
/* 579 */         localObject = paramStrokedBorder.bottomFill;
/* 580 */         if (localBorderStyle == null) {
/* 581 */           localBorderStyle = paramStrokedBorder.rightStyle;
/* 582 */           f = paramStrokedBorder.rightWidth;
/* 583 */           localObject = paramStrokedBorder.rightFill;
/*     */         }
/*     */       }
/*     */     }
/* 587 */     if (localBorderStyle == null) {
/* 588 */       return;
/*     */     }
/* 590 */     paramGraphics.setStroke(createStroke(localBorderStyle, f));
/* 591 */     paramGraphics.setPaint((Paint)localObject);
/*     */   }
/*     */ 
/*     */   private void fillShape(Graphics paramGraphics, BackgroundFill paramBackgroundFill, int paramInt)
/*     */   {
/* 596 */     if (this.shape != null) {
/* 597 */       paramGraphics.fill(this.backgroundShapeCache[paramInt]);
/*     */     } else {
/* 599 */       float f1 = paramBackgroundFill.topOffset; float f2 = paramBackgroundFill.leftOffset; float f3 = paramBackgroundFill.bottomOffset; float f4 = paramBackgroundFill.rightOffset;
/* 600 */       float f5 = paramBackgroundFill.topLeftRadius; float f6 = paramBackgroundFill.topRightRadius; float f7 = paramBackgroundFill.bottomLeftRadius; float f8 = paramBackgroundFill.bottomRightRadius;
/* 601 */       float f9 = this.width - f2 - f4;
/* 602 */       float f10 = this.height - f1 - f3;
/* 603 */       if ((f9 > 0.0F) && (f10 > 0.0F))
/* 604 */         if ((f5 == f6) && (f5 == f7) && (f5 == f8)) {
/* 605 */           if (f5 == 0.0F) {
/* 606 */             paramGraphics.fillRect(f2, f1, f9, f10);
/*     */           } else {
/* 608 */             float f11 = f5 + f5;
/* 609 */             if (f11 > f9) f11 = f9;
/* 610 */             if (f11 > f10) f11 = f10;
/* 611 */             paramGraphics.fillRoundRect(f2, f1, f9, f10, f11, f11);
/*     */           }
/*     */         }
/* 614 */         else paramGraphics.fill(createPath(f1, f2, f3, f4, f5, f6, f7, f8));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doCorner(Path2D paramPath2D, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt)
/*     */   {
/* 658 */     if (paramFloat3 > 0.0F) {
/* 659 */       float f1 = paramFloat3 * SIN_VALS[(paramInt + 1)];
/* 660 */       float f2 = paramFloat3 * SIN_VALS[paramInt];
/* 661 */       paramPath2D.appendOvalQuadrant(paramFloat1 + f1, paramFloat2 + f2, paramFloat1, paramFloat2, paramFloat1 + f2, paramFloat2 - f1, 0.0F, 1.0F, paramInt == 0 ? Path2D.CornerPrefix.MOVE_THEN_CORNER : Path2D.CornerPrefix.LINE_THEN_CORNER);
/*     */     }
/* 665 */     else if (paramInt == 0) {
/* 666 */       paramPath2D.moveTo(paramFloat1, paramFloat2);
/*     */     } else {
/* 668 */       paramPath2D.lineTo(paramFloat1, paramFloat2);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Path2D createPath(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 676 */     float f1 = this.width - paramFloat4;
/* 677 */     float f2 = this.height - paramFloat3;
/* 678 */     float f3 = getReducingRatio(f1 - paramFloat2, f2 - paramFloat1, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/* 679 */     if (f3 < 1.0F) {
/* 680 */       paramFloat5 *= f3;
/* 681 */       paramFloat6 *= f3;
/* 682 */       paramFloat7 *= f3;
/* 683 */       paramFloat8 *= f3;
/*     */     }
/* 685 */     Path2D localPath2D = new Path2D();
/* 686 */     doCorner(localPath2D, paramFloat2, paramFloat1, paramFloat5, 0);
/* 687 */     doCorner(localPath2D, f1, paramFloat1, paramFloat6, 1);
/* 688 */     doCorner(localPath2D, f1, f2, paramFloat8, 2);
/* 689 */     doCorner(localPath2D, paramFloat2, f2, paramFloat7, 3);
/* 690 */     localPath2D.closePath();
/* 691 */     return localPath2D;
/*     */   }
/*     */ 
/*     */   private Path2D makeRoundedEdge(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt)
/*     */   {
/* 697 */     Path2D localPath2D = new Path2D();
/*     */     float f1;
/*     */     float f2;
/* 698 */     if (paramFloat5 > 0.0F) {
/* 699 */       f1 = paramFloat5 * SIN_VALS[(paramInt + 1)];
/* 700 */       f2 = paramFloat5 * SIN_VALS[paramInt];
/* 701 */       localPath2D.appendOvalQuadrant(paramFloat1 + f1, paramFloat2 + f2, paramFloat1, paramFloat2, paramFloat1 + f2, paramFloat2 - f1, 0.5F, 1.0F, Path2D.CornerPrefix.MOVE_THEN_CORNER);
/*     */     }
/*     */     else {
/* 704 */       localPath2D.moveTo(paramFloat1, paramFloat2);
/*     */     }
/* 706 */     if (paramFloat6 > 0.0F) {
/* 707 */       f1 = paramFloat6 * SIN_VALS[(paramInt + 2)];
/* 708 */       f2 = paramFloat6 * SIN_VALS[(paramInt + 1)];
/* 709 */       localPath2D.appendOvalQuadrant(paramFloat3 + f1, paramFloat4 + f2, paramFloat3, paramFloat4, paramFloat3 + f2, paramFloat4 - f1, 0.0F, 0.5F, Path2D.CornerPrefix.LINE_THEN_CORNER);
/*     */     }
/*     */     else {
/* 712 */       localPath2D.lineTo(paramFloat3, paramFloat4);
/*     */     }
/* 714 */     return localPath2D;
/*     */   }
/*     */ 
/*     */   private Path2D[] createPaths(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 725 */     float f1 = this.width - paramFloat4;
/* 726 */     float f2 = this.height - paramFloat3;
/* 727 */     float f3 = getReducingRatio(f1 - paramFloat2, f2 - paramFloat1, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/* 728 */     if (f3 < 1.0F) {
/* 729 */       paramFloat5 *= f3;
/* 730 */       paramFloat6 *= f3;
/* 731 */       paramFloat7 *= f3;
/* 732 */       paramFloat8 *= f3;
/*     */     }
/* 734 */     return new Path2D[] { makeRoundedEdge(paramFloat2, paramFloat1, f1, paramFloat1, paramFloat5, paramFloat6, 0), makeRoundedEdge(f1, paramFloat1, f1, f2, paramFloat6, paramFloat8, 1), makeRoundedEdge(f1, f2, paramFloat2, f2, paramFloat8, paramFloat7, 2), makeRoundedEdge(paramFloat2, f2, paramFloat2, paramFloat1, paramFloat7, paramFloat5, 3) };
/*     */   }
/*     */ 
/*     */   private Shape createShape(Shape paramShape, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 748 */     float f1 = this.width - paramFloat2 - paramFloat4;
/* 749 */     float f2 = this.height - paramFloat1 - paramFloat3;
/* 750 */     Affine2D localAffine2D = new Affine2D();
/* 751 */     RectBounds localRectBounds = paramShape.getBounds();
/* 752 */     if (this.resizeShape)
/*     */     {
/* 754 */       localAffine2D.translate(paramFloat2, paramFloat1);
/* 755 */       localAffine2D.scale(f1 / localRectBounds.getWidth(), f2 / localRectBounds.getHeight());
/* 756 */       if (this.positionShape)
/* 757 */         localAffine2D.translate(-localRectBounds.getMinX(), -localRectBounds.getMinY());
/*     */     }
/*     */     else
/*     */     {
/*     */       float f3;
/*     */       float f4;
/* 759 */       if (this.positionShape)
/*     */       {
/* 761 */         if ((paramFloat1 != 0.0F) || (paramFloat4 != 0.0F) || (paramFloat3 != 0.0F) || (paramFloat2 != 0.0F))
/*     */         {
/* 763 */           f3 = localRectBounds.getWidth() - paramFloat2 - paramFloat4;
/* 764 */           f4 = localRectBounds.getHeight() - paramFloat1 - paramFloat3;
/*     */ 
/* 766 */           localAffine2D.translate((this.width - f3) / 2.0F, (this.height - f4) / 2.0F);
/* 767 */           localAffine2D.scale(f3 / localRectBounds.getWidth(), f4 / localRectBounds.getHeight());
/* 768 */           localAffine2D.translate(-localRectBounds.getMinX(), -localRectBounds.getMinY());
/*     */         } else {
/* 770 */           localAffine2D.translate(-localRectBounds.getMinX(), -localRectBounds.getMinY());
/* 771 */           localAffine2D.translate((this.width - localRectBounds.getWidth()) / 2.0F, (this.height - localRectBounds.getHeight()) / 2.0F);
/*     */         }
/* 773 */       } else if ((paramFloat1 != 0.0F) || (paramFloat4 != 0.0F) || (paramFloat3 != 0.0F) || (paramFloat2 != 0.0F))
/*     */       {
/* 775 */         f3 = localRectBounds.getWidth() - paramFloat2 - paramFloat4;
/* 776 */         f4 = localRectBounds.getHeight() - paramFloat1 - paramFloat3;
/* 777 */         localAffine2D.scale(f3 / localRectBounds.getWidth(), f4 / localRectBounds.getHeight());
/*     */       }
/*     */     }
/* 779 */     return localAffine2D.isIdentity() ? paramShape : localAffine2D.createTransformedShape(paramShape);
/*     */   }
/*     */ 
/*     */   protected boolean hasOverlappingContents()
/*     */   {
/* 790 */     return true;
/*     */   }
/*     */ 
/*     */   private void paintTiles(Graphics paramGraphics, Image paramImage, Repeat paramRepeat1, Repeat paramRepeat2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 822 */     if ((paramFloat3 == 0.0F) || (paramFloat4 == 0.0F) || (paramInt3 == 0) || (paramInt4 == 0))
/*     */     {
/* 824 */       return;
/*     */     }
/* 826 */     BaseTransform localBaseTransform = paramGraphics.getTransformNoClone().copy();
/* 827 */     paramGraphics.translate(paramFloat1, paramFloat2);
/*     */ 
/* 829 */     if (paramInt1 < 0) {
/* 830 */       paramInt1 = 0;
/*     */     }
/* 832 */     if (paramInt2 < 0) {
/* 833 */       paramInt2 = 0;
/*     */     }
/* 835 */     if (paramInt3 < 0) {
/* 836 */       paramInt3 = paramImage.getWidth();
/*     */     }
/* 838 */     if (paramInt4 < 0) {
/* 839 */       paramInt4 = paramImage.getHeight();
/*     */     }
/*     */ 
/* 842 */     float f1 = paramInt3 > 0 ? paramInt3 : paramFloat7 > 0.0F ? paramFloat7 : paramImage.getWidth();
/* 843 */     float f2 = paramInt4 > 0 ? paramInt4 : paramFloat8 > 0.0F ? paramFloat8 : paramImage.getHeight();
/*     */ 
/* 845 */     if ((paramRepeat1 == Repeat.SPACE) && (paramFloat3 < f1 * 2.0F)) {
/* 846 */       paramRepeat1 = Repeat.NO_REPEAT;
/*     */     }
/* 848 */     if ((paramRepeat2 == Repeat.SPACE) && (paramFloat4 < f2 * 2.0F)) {
/* 849 */       paramRepeat2 = Repeat.NO_REPEAT;
/*     */     }
/*     */ 
/* 853 */     float f3 = 0.0F; float f4 = 0.0F;
/*     */ 
/* 856 */     int k = 0; int m = 0;
/*     */     int i;
/*     */     float f7;
/*     */     float f5;
/* 857 */     if (paramRepeat1 == Repeat.REPEAT) {
/* 858 */       f1 = Math.round(f1);
/* 859 */       f3 = paramFloat5 == 0.0F ? 0.0F : paramFloat5 % f1 - f1;
/* 860 */       i = (int)Math.max(1.0D, Math.ceil((paramFloat3 + Math.abs(f3)) / f1));
/* 861 */       f5 = f7 = f1;
/* 862 */     } else if (paramRepeat1 == Repeat.SPACE) {
/* 863 */       i = (int)Math.max(1.0D, Math.floor(paramFloat3 / f1));
/* 864 */       f5 = i == 1 ? 0.0F : (paramFloat3 - i * f1) / (i - 1) + f1;
/* 865 */       f7 = f1;
/* 866 */     } else if (paramRepeat1 == Repeat.ROUND) {
/* 867 */       i = (int)Math.max(1.0D, Math.ceil(paramFloat3 / f1));
/* 868 */       f5 = f7 = (float)Math.ceil(paramFloat3 / i);
/* 869 */       k = (int)Math.abs(Math.floor(paramFloat3 - f7 * i));
/*     */     } else {
/* 871 */       i = 1;
/* 872 */       f3 = paramFloat5;
/* 873 */       f5 = f7 = f1;
/*     */     }
/*     */     int j;
/*     */     float f8;
/*     */     float f6;
/* 875 */     if (paramRepeat2 == Repeat.REPEAT) {
/* 876 */       f2 = Math.round(f2);
/* 877 */       f4 = paramFloat6 == 0.0F ? 0.0F : paramFloat6 % f2 - f2;
/* 878 */       j = (int)Math.max(1.0D, Math.ceil((paramFloat4 + Math.abs(f4)) / f2));
/* 879 */       f6 = f8 = f2;
/* 880 */     } else if (paramRepeat2 == Repeat.SPACE) {
/* 881 */       j = (int)Math.floor(paramFloat4 / f2);
/* 882 */       f6 = j == 1 ? 0.0F : (paramFloat4 - j * f2) / (j - 1) + f2;
/* 883 */       f8 = f2;
/* 884 */     } else if (paramRepeat2 == Repeat.ROUND) {
/* 885 */       j = (int)Math.max(1.0D, Math.ceil(paramFloat4 / f2));
/* 886 */       f6 = f8 = (float)Math.ceil(paramFloat4 / j);
/* 887 */       m = (int)Math.abs(Math.floor(paramFloat4 - f8 * j));
/*     */     } else {
/* 889 */       j = 1;
/* 890 */       f4 = paramFloat6;
/* 891 */       f6 = f8 = f2;
/*     */     }
/*     */ 
/* 894 */     if ((paramRepeat1 == Repeat.REPEAT) && (paramRepeat2 == Repeat.REPEAT)) {
/* 895 */       if ((paramInt1 != 0) || (paramInt2 != 0) || (paramInt3 != paramImage.getWidth()) || (paramInt4 != paramImage.getHeight())) {
/* 896 */         paramImage = paramImage.createSubImage(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */       }
/* 898 */       paramGraphics.setPaint(new ImagePattern(paramImage, paramFloat5, paramFloat6, f1, f2, false));
/* 899 */       paramGraphics.fillRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */     }
/*     */     else {
/* 902 */       paramGraphics.translate(f3, f4);
/* 903 */       Texture localTexture = paramGraphics.getResourceFactory().getCachedTexture(paramImage);
/* 904 */       float f9 = f4;
/* 905 */       float f10 = f8;
/* 906 */       for (int n = 0; n < j; n++) {
/* 907 */         int i1 = n < m ? -1 : 0;
/* 908 */         if (f9 + f10 > paramFloat4) {
/* 909 */           f10 = paramFloat4 - f9;
/* 910 */           paramInt4 = (int)(paramInt4 * (f10 / f8));
/*     */         }
/*     */ 
/* 913 */         float f11 = f3;
/* 914 */         float f12 = f7;
/* 915 */         float f13 = paramInt3;
/* 916 */         for (int i2 = 0; i2 < i; i2++) {
/* 917 */           int i3 = i2 < k ? -1 : 0;
/* 918 */           if (f11 + f12 > paramFloat3) {
/* 919 */             f12 = paramFloat3 - f11;
/* 920 */             f13 *= f12 / f7;
/*     */           }
/*     */ 
/* 924 */           paramGraphics.drawTexture(localTexture, 0.0F, 0.0F, (int)f12, (int)f10, paramInt1, paramInt2, paramInt1 + f13 + i3, paramInt2 + paramInt4 + i1);
/*     */ 
/* 927 */           paramGraphics.translate(f5 + i3, 0.0F);
/* 928 */           f11 += f5 + i3;
/*     */         }
/* 930 */         paramGraphics.translate(-(i * f5) + k, f6 + i1);
/* 931 */         f9 += f6 + i1;
/*     */       }
/*     */     }
/*     */ 
/* 935 */     paramGraphics.setTransform(localBaseTransform);
/*     */   }
/*     */ 
/*     */   protected boolean hasVisuals()
/*     */   {
/* 940 */     return ((this.borders != null) && (this.borders.length > 0)) || ((this.backgroundImages != null) && (this.backgroundImages.length > 0)) || ((this.backgroundFills != null) && (this.backgroundFills.length > 0));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGRegion
 * JD-Core Version:    0.6.2
 */