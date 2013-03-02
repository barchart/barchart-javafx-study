/*     */ package com.sun.scenario.effect.impl.state;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ public class MotionBlurState extends LinearConvolveKernel
/*     */ {
/*     */   private float radius;
/*     */   private float angle;
/*     */   private FloatBuffer weights;
/*     */ 
/*     */   public float getRadius()
/*     */   {
/*  26 */     return this.radius;
/*     */   }
/*     */ 
/*     */   public void setRadius(float paramFloat) {
/*  30 */     if ((paramFloat < 0.0F) || (paramFloat > 63.0F)) {
/*  31 */       throw new IllegalArgumentException("Radius must be in the range [1,63]");
/*     */     }
/*  33 */     this.radius = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getAngle() {
/*  37 */     return this.angle;
/*     */   }
/*     */ 
/*     */   public void setAngle(float paramFloat) {
/*  41 */     this.angle = paramFloat;
/*     */   }
/*     */ 
/*     */   public int getHPad() {
/*  45 */     return (int)Math.ceil(Math.abs(Math.cos(this.angle)) * this.radius);
/*     */   }
/*     */ 
/*     */   public int getVPad() {
/*  49 */     return (int)Math.ceil(Math.abs(Math.sin(this.angle)) * this.radius);
/*     */   }
/*     */ 
/*     */   public int getNumberOfPasses()
/*     */   {
/*  54 */     return 1;
/*     */   }
/*     */ 
/*     */   public boolean isNop()
/*     */   {
/*  59 */     return this.radius == 0.0F;
/*     */   }
/*     */ 
/*     */   public boolean isNop(int paramInt)
/*     */   {
/*  64 */     return (paramInt > 0) || (this.radius == 0.0F);
/*     */   }
/*     */ 
/*     */   public int getKernelSize(int paramInt)
/*     */   {
/*  69 */     return (int)Math.ceil(this.radius) * 2 + 1;
/*     */   }
/*     */ 
/*     */   public final Rectangle getResultBounds(Rectangle paramRectangle, int paramInt)
/*     */   {
/*  74 */     Rectangle localRectangle = new Rectangle(paramRectangle);
/*  75 */     localRectangle.grow(getHPad(), getVPad());
/*  76 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   public float[] getVector(Rectangle paramRectangle, BaseTransform paramBaseTransform, int paramInt)
/*     */   {
/*  85 */     float[] arrayOfFloat = new float[4];
/*  86 */     float f1 = (float)Math.cos(this.angle);
/*  87 */     float f2 = (float)Math.sin(this.angle);
/*  88 */     if (!paramBaseTransform.isTranslateOrIdentity()) {
/*  89 */       arrayOfFloat[0] = f1;
/*  90 */       arrayOfFloat[1] = f2;
/*     */       try {
/*  92 */         paramBaseTransform.inverseDeltaTransform(arrayOfFloat, 0, arrayOfFloat, 0, 1);
/*  93 */         f1 = arrayOfFloat[0];
/*  94 */         f2 = arrayOfFloat[1];
/*     */       } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/*  96 */         f2 = f1 = 0.0F;
/*     */       }
/*     */     }
/*  99 */     float f3 = f1 / paramRectangle.width;
/* 100 */     float f4 = f2 / paramRectangle.height;
/* 101 */     int i = getScaledKernelSize(paramInt);
/* 102 */     int j = i / 2;
/* 103 */     arrayOfFloat[0] = f3;
/* 104 */     arrayOfFloat[1] = f4;
/* 105 */     arrayOfFloat[2] = (-j * f3);
/* 106 */     arrayOfFloat[3] = (-j * f4);
/* 107 */     return arrayOfFloat;
/*     */   }
/*     */ 
/*     */   public FloatBuffer getWeights(int paramInt)
/*     */   {
/* 112 */     int i = (int)Math.ceil(this.radius);
/* 113 */     this.weights = GaussianBlurState.getGaussianWeights(this.weights, i, this.radius, 0.0F);
/* 114 */     return this.weights;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.state.MotionBlurState
 * JD-Core Version:    0.6.2
 */