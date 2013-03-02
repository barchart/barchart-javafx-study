/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.Translate2D;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Offset extends Effect
/*     */ {
/*     */   private int xoff;
/*     */   private int yoff;
/*     */ 
/*     */   public Offset(int paramInt1, int paramInt2, Effect paramEffect)
/*     */   {
/*  53 */     super(paramEffect);
/*  54 */     this.xoff = paramInt1;
/*  55 */     this.yoff = paramInt2;
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/*  64 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/*  75 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public int getX()
/*     */   {
/*  84 */     return this.xoff;
/*     */   }
/*     */ 
/*     */   public void setX(int paramInt)
/*     */   {
/*  99 */     int i = this.xoff;
/* 100 */     this.xoff = paramInt;
/* 101 */     firePropertyChange("x", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public int getY()
/*     */   {
/* 110 */     return this.yoff;
/*     */   }
/*     */ 
/*     */   public void setY(int paramInt)
/*     */   {
/* 125 */     float f = this.yoff;
/* 126 */     this.yoff = paramInt;
/* 127 */     firePropertyChange("y", Float.valueOf(f), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   static BaseTransform getOffsetTransform(BaseTransform paramBaseTransform, double paramDouble1, double paramDouble2)
/*     */   {
/* 133 */     if ((paramBaseTransform == null) || (paramBaseTransform.isIdentity())) {
/* 134 */       return Translate2D.getInstance(paramDouble1, paramDouble2);
/*     */     }
/* 136 */     return paramBaseTransform.copy().deriveWithTranslation(paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 144 */     BaseTransform localBaseTransform = getOffsetTransform(paramBaseTransform, this.xoff, this.yoff);
/* 145 */     Effect localEffect = getDefaultedInput(0, paramEffect);
/* 146 */     return localEffect.getBounds(localBaseTransform, paramEffect);
/*     */   }
/*     */ 
/*     */   public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*     */   {
/* 156 */     BaseTransform localBaseTransform = getOffsetTransform(paramBaseTransform, this.xoff, this.yoff);
/*     */ 
/* 160 */     Effect localEffect = getDefaultedInput(0, paramEffect);
/* 161 */     return localEffect.filter(paramFilterContext, localBaseTransform, paramRectangle, paramObject, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 166 */     paramPoint2D = getDefaultedInput(0, paramEffect).transform(paramPoint2D, paramEffect);
/* 167 */     float f1 = paramPoint2D.x + this.xoff;
/* 168 */     float f2 = paramPoint2D.y + this.yoff;
/* 169 */     paramPoint2D = new Point2D(f1, f2);
/* 170 */     return paramPoint2D;
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 175 */     float f1 = paramPoint2D.x - this.xoff;
/* 176 */     float f2 = paramPoint2D.y - this.yoff;
/* 177 */     paramPoint2D = new Point2D(f1, f2);
/* 178 */     paramPoint2D = getDefaultedInput(0, paramEffect).untransform(paramPoint2D, paramEffect);
/* 179 */     return paramPoint2D;
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */   {
/* 184 */     return ((Effect)getInputs().get(0)).getAccelType(paramFilterContext);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Offset
 * JD-Core Version:    0.6.2
 */