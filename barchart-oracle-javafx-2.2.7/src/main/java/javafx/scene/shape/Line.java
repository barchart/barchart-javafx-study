/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Line2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.PGLine;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.PGShape.Mode;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.paint.Paint;
/*     */ 
/*     */ public class Line extends Shape
/*     */ {
/*  68 */   private final Line2D shape = new Line2D();
/*     */   private DoubleProperty startX;
/*     */   private DoubleProperty startY;
/*     */   private DoubleProperty endX;
/*     */   private DoubleProperty endY;
/*     */ 
/*     */   public Line()
/*     */   {
/*  74 */     StyleableProperty localStyleableProperty1 = StyleableProperty.getStyleableProperty(fillProperty());
/*  75 */     localStyleableProperty1.set(this, null);
/*  76 */     StyleableProperty localStyleableProperty2 = StyleableProperty.getStyleableProperty(strokeProperty());
/*  77 */     localStyleableProperty2.set(this, Color.BLACK);
/*     */   }
/*     */ 
/*     */   public Line(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  74 */     StyleableProperty localStyleableProperty1 = StyleableProperty.getStyleableProperty(fillProperty());
/*  75 */     localStyleableProperty1.set(this, null);
/*  76 */     StyleableProperty localStyleableProperty2 = StyleableProperty.getStyleableProperty(strokeProperty());
/*  77 */     localStyleableProperty2.set(this, Color.BLACK);
/*     */ 
/*  94 */     setStartX(paramDouble1);
/*  95 */     setStartY(paramDouble2);
/*  96 */     setEndX(paramDouble3);
/*  97 */     setEndY(paramDouble4);
/*     */   }
/*     */ 
/*     */   public final void setStartX(double paramDouble)
/*     */   {
/* 109 */     if ((this.startX != null) || (paramDouble != 0.0D))
/* 110 */       startXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getStartX()
/*     */   {
/* 115 */     return this.startX == null ? 0.0D : this.startX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty startXProperty() {
/* 119 */     if (this.startX == null) {
/* 120 */       this.startX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 124 */           Line.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 125 */           Line.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 130 */           return Line.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 135 */           return "startX";
/*     */         }
/*     */       };
/*     */     }
/* 139 */     return this.startX;
/*     */   }
/*     */ 
/*     */   public final void setStartY(double paramDouble)
/*     */   {
/* 152 */     if ((this.startY != null) || (paramDouble != 0.0D))
/* 153 */       startYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getStartY()
/*     */   {
/* 158 */     return this.startY == null ? 0.0D : this.startY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty startYProperty() {
/* 162 */     if (this.startY == null) {
/* 163 */       this.startY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 167 */           Line.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 168 */           Line.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 173 */           return Line.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 178 */           return "startY";
/*     */         }
/*     */       };
/*     */     }
/* 182 */     return this.startY;
/*     */   }
/*     */ 
/*     */   public final void setEndX(double paramDouble)
/*     */   {
/* 195 */     if ((this.endX != null) || (paramDouble != 0.0D))
/* 196 */       endXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getEndX()
/*     */   {
/* 201 */     return this.endX == null ? 0.0D : this.endX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty endXProperty() {
/* 205 */     if (this.endX == null) {
/* 206 */       this.endX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 210 */           Line.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 211 */           Line.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 216 */           return Line.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 221 */           return "endX";
/*     */         }
/*     */       };
/*     */     }
/* 225 */     return this.endX;
/*     */   }
/*     */ 
/*     */   public final void setEndY(double paramDouble)
/*     */   {
/* 236 */     if ((this.endY != null) || (paramDouble != 0.0D))
/* 237 */       endYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getEndY()
/*     */   {
/* 242 */     return this.endY == null ? 0.0D : this.endY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty endYProperty() {
/* 246 */     if (this.endY == null) {
/* 247 */       this.endY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 251 */           Line.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 252 */           Line.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 257 */           return Line.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 262 */           return "endY";
/*     */         }
/*     */       };
/*     */     }
/* 266 */     return this.endY;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 276 */     return Toolkit.getToolkit().createPGLine();
/*     */   }
/*     */ 
/*     */   PGLine getPGLine() {
/* 280 */     return (PGLine)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_computeGeomBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*     */   {
/* 293 */     if ((this.impl_mode == PGShape.Mode.FILL) || (this.impl_mode == PGShape.Mode.EMPTY) || (getStrokeType() == StrokeType.INSIDE))
/*     */     {
/* 296 */       return paramBaseBounds.makeEmpty();
/*     */     }
/*     */ 
/* 299 */     double d1 = getStartX();
/* 300 */     double d2 = getEndX();
/* 301 */     double d3 = getStartY();
/* 302 */     double d4 = getEndY();
/*     */ 
/* 304 */     double d5 = getStrokeWidth();
/* 305 */     if (getStrokeType() == StrokeType.CENTERED)
/* 306 */       d5 /= 2.0D;
/*     */     double d9;
/* 309 */     if (paramBaseTransform.isTranslateOrIdentity())
/*     */     {
/* 312 */       d5 = Math.max(d5, 0.5D);
/* 313 */       if (paramBaseTransform.getType() == 1) {
/* 314 */         d8 = paramBaseTransform.getMxt();
/* 315 */         d9 = paramBaseTransform.getMyt();
/* 316 */         d1 += d8;
/* 317 */         d3 += d9;
/* 318 */         d2 += d8;
/* 319 */         d4 += d9;
/*     */       }
/* 321 */       if ((d3 == d4) && (d1 != d2)) {
/* 322 */         d7 = d5;
/* 323 */         d6 = getStrokeLineCap() == StrokeLineCap.BUTT ? 0.0D : d5;
/* 324 */       } else if ((d1 == d2) && (d3 != d4)) {
/* 325 */         d6 = d5;
/* 326 */         d7 = getStrokeLineCap() == StrokeLineCap.BUTT ? 0.0D : d5;
/*     */       } else {
/* 328 */         if (getStrokeLineCap() == StrokeLineCap.SQUARE) {
/* 329 */           d5 *= Math.sqrt(2.0D);
/*     */         }
/* 331 */         d6 = d7 = d5;
/*     */       }
/* 333 */       if (d1 > d2) { d8 = d1; d1 = d2; d2 = d8; }
/* 334 */       if (d3 > d4) { d8 = d3; d3 = d4; d4 = d8;
/*     */       }
/* 336 */       d1 -= d6;
/* 337 */       d3 -= d7;
/* 338 */       d2 += d6;
/* 339 */       d4 += d7;
/* 340 */       paramBaseBounds = paramBaseBounds.deriveWithNewBounds((float)d1, (float)d3, 0.0F, (float)d2, (float)d4, 0.0F);
/*     */ 
/* 342 */       return paramBaseBounds;
/*     */     }
/*     */ 
/* 345 */     double d6 = d2 - d1;
/* 346 */     double d7 = d4 - d3;
/* 347 */     double d8 = Math.sqrt(d6 * d6 + d7 * d7);
/* 348 */     if (d8 == 0.0D) {
/* 349 */       d6 = d5;
/* 350 */       d7 = 0.0D;
/*     */     } else {
/* 352 */       d6 = d5 * d6 / d8;
/* 353 */       d7 = d5 * d7 / d8;
/*     */     }
/*     */     double d10;
/* 357 */     if (getStrokeLineCap() != StrokeLineCap.BUTT) {
/* 358 */       d9 = d6;
/* 359 */       d10 = d7;
/*     */     } else {
/* 361 */       d9 = d10 = 0.0D;
/*     */     }
/* 363 */     double[] arrayOfDouble = { d1 - d7 - d9, d3 + d6 - d10, d1 + d7 - d9, d3 - d6 - d10, d2 + d7 + d9, d4 - d6 + d10, d2 - d7 + d9, d4 + d6 + d10 };
/*     */ 
/* 368 */     paramBaseTransform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 4);
/* 369 */     d1 = Math.min(Math.min(arrayOfDouble[0], arrayOfDouble[2]), Math.min(arrayOfDouble[4], arrayOfDouble[6]));
/*     */ 
/* 371 */     d3 = Math.min(Math.min(arrayOfDouble[1], arrayOfDouble[3]), Math.min(arrayOfDouble[5], arrayOfDouble[7]));
/*     */ 
/* 373 */     d2 = Math.max(Math.max(arrayOfDouble[0], arrayOfDouble[2]), Math.max(arrayOfDouble[4], arrayOfDouble[6]));
/*     */ 
/* 375 */     d4 = Math.max(Math.max(arrayOfDouble[1], arrayOfDouble[3]), Math.max(arrayOfDouble[5], arrayOfDouble[7]));
/*     */ 
/* 377 */     d1 -= 0.5D;
/* 378 */     d3 -= 0.5D;
/* 379 */     d2 += 0.5D;
/* 380 */     d4 += 0.5D;
/* 381 */     paramBaseBounds = paramBaseBounds.deriveWithNewBounds((float)d1, (float)d3, 0.0F, (float)d2, (float)d4, 0.0F);
/*     */ 
/* 383 */     return paramBaseBounds;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Line2D impl_configShape()
/*     */   {
/* 393 */     this.shape.setLine((float)getStartX(), (float)getStartY(), (float)getEndX(), (float)getEndY());
/* 394 */     return this.shape;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 404 */     super.impl_updatePG();
/*     */ 
/* 406 */     if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
/* 407 */       PGLine localPGLine = getPGLine();
/* 408 */       localPGLine.updateLine((float)getStartX(), (float)getStartY(), (float)getEndX(), (float)getEndY());
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Paint impl_cssGetFillInitialValue()
/*     */   {
/* 430 */     return null;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Paint impl_cssGetStrokeInitialValue()
/*     */   {
/* 442 */     return Color.BLACK;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static List<StyleableProperty> impl_CSS_STYLEABLES()
/*     */   {
/* 452 */     return Shape.impl_CSS_STYLEABLES();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public List<StyleableProperty> impl_getStyleableProperties()
/*     */   {
/* 462 */     return impl_CSS_STYLEABLES();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.Line
 * JD-Core Version:    0.6.2
 */