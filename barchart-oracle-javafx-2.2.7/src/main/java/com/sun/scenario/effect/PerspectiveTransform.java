/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.state.PerspectiveTransformState;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PerspectiveTransform extends CoreEffect
/*     */ {
/*  38 */   private float[][] tx = new float[3][3];
/*     */   private float ulx;
/*     */   private float uly;
/*     */   private float urx;
/*     */   private float ury;
/*     */   private float lrx;
/*     */   private float lry;
/*     */   private float llx;
/*     */   private float lly;
/*  40 */   private float[] devcoords = new float[8];
/*  41 */   private final PerspectiveTransformState state = new PerspectiveTransformState();
/*     */ 
/*     */   public PerspectiveTransform() {
/*  44 */     this(DefaultInput);
/*     */   }
/*     */ 
/*     */   public PerspectiveTransform(Effect paramEffect) {
/*  48 */     super(paramEffect);
/*  49 */     setQuadMapping(0.0F, 0.0F, 100.0F, 0.0F, 100.0F, 100.0F, 0.0F, 100.0F);
/*  50 */     updatePeerKey("PerspectiveTransform");
/*     */   }
/*     */ 
/*     */   Object getState()
/*     */   {
/*  55 */     return this.state;
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
/*     */   private void setUnitQuadMapping(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 107 */     float f1 = paramFloat1 - paramFloat3 + paramFloat5 - paramFloat7;
/* 108 */     float f2 = paramFloat2 - paramFloat4 + paramFloat6 - paramFloat8;
/*     */ 
/* 110 */     this.tx[2][2] = 1.0F;
/*     */ 
/* 112 */     if ((f1 == 0.0F) && (f2 == 0.0F)) {
/* 113 */       this.tx[0][0] = (paramFloat3 - paramFloat1);
/* 114 */       this.tx[0][1] = (paramFloat5 - paramFloat3);
/* 115 */       this.tx[0][2] = paramFloat1;
/* 116 */       this.tx[1][0] = (paramFloat4 - paramFloat2);
/* 117 */       this.tx[1][1] = (paramFloat6 - paramFloat4);
/* 118 */       this.tx[1][2] = paramFloat2;
/* 119 */       this.tx[2][0] = 0.0F;
/* 120 */       this.tx[2][1] = 0.0F;
/*     */     } else {
/* 122 */       float f3 = paramFloat3 - paramFloat5;
/* 123 */       float f4 = paramFloat4 - paramFloat6;
/* 124 */       float f5 = paramFloat7 - paramFloat5;
/* 125 */       float f6 = paramFloat8 - paramFloat6;
/*     */ 
/* 127 */       float f7 = 1.0F / (f3 * f6 - f5 * f4);
/* 128 */       this.tx[2][0] = ((f1 * f6 - f5 * f2) * f7);
/* 129 */       this.tx[2][1] = ((f3 * f2 - f1 * f4) * f7);
/* 130 */       this.tx[0][0] = (paramFloat3 - paramFloat1 + this.tx[2][0] * paramFloat3);
/* 131 */       this.tx[0][1] = (paramFloat7 - paramFloat1 + this.tx[2][1] * paramFloat7);
/* 132 */       this.tx[0][2] = paramFloat1;
/* 133 */       this.tx[1][0] = (paramFloat4 - paramFloat2 + this.tx[2][0] * paramFloat4);
/* 134 */       this.tx[1][1] = (paramFloat8 - paramFloat2 + this.tx[2][1] * paramFloat8);
/* 135 */       this.tx[1][2] = paramFloat2;
/*     */     }
/* 137 */     this.state.updateTx(this.tx);
/*     */   }
/*     */ 
/*     */   public final void setQuadMapping(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 145 */     this.ulx = paramFloat1;
/* 146 */     this.uly = paramFloat2;
/* 147 */     this.urx = paramFloat3;
/* 148 */     this.ury = paramFloat4;
/* 149 */     this.lrx = paramFloat5;
/* 150 */     this.lry = paramFloat6;
/* 151 */     this.llx = paramFloat7;
/* 152 */     this.lly = paramFloat8;
/*     */ 
/* 155 */     firePropertyChange("quadMapping", null, Float.valueOf(paramFloat8));
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 162 */     setupDevCoords(paramBaseTransform);
/*     */     float f3;
/* 165 */     float f1 = f3 = this.devcoords[0];
/*     */     float f4;
/* 166 */     float f2 = f4 = this.devcoords[1];
/* 167 */     for (int i = 2; i < this.devcoords.length; i += 2) {
/* 168 */       if (f1 > this.devcoords[i]) f1 = this.devcoords[i];
/* 169 */       else if (f3 < this.devcoords[i]) f3 = this.devcoords[i];
/* 170 */       if (f2 > this.devcoords[(i + 1)]) f2 = this.devcoords[(i + 1)];
/* 171 */       else if (f4 < this.devcoords[(i + 1)]) f4 = this.devcoords[(i + 1)];
/*     */     }
/* 173 */     return new RectBounds(f1, f2, f3, f4);
/*     */   }
/*     */ 
/*     */   private void setupDevCoords(BaseTransform paramBaseTransform) {
/* 177 */     this.devcoords[0] = this.ulx;
/* 178 */     this.devcoords[1] = this.uly;
/* 179 */     this.devcoords[2] = this.urx;
/* 180 */     this.devcoords[3] = this.ury;
/* 181 */     this.devcoords[4] = this.lrx;
/* 182 */     this.devcoords[5] = this.lry;
/* 183 */     this.devcoords[6] = this.llx;
/* 184 */     this.devcoords[7] = this.lly;
/* 185 */     paramBaseTransform.transform(this.devcoords, 0, this.devcoords, 0, 4);
/*     */   }
/*     */ 
/*     */   public boolean operatesInUserSpace()
/*     */   {
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */   public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*     */   {
/* 200 */     setupTransforms(paramBaseTransform);
/* 201 */     Effect localEffect = getDefaultedInput(0, paramEffect);
/* 202 */     Rectangle localRectangle = getInputClip(0, paramBaseTransform, paramRectangle);
/* 203 */     ImageData localImageData1 = localEffect.filter(paramFilterContext, BaseTransform.IDENTITY_TRANSFORM, localRectangle, null, paramEffect);
/*     */ 
/* 206 */     if (!localImageData1.validate(paramFilterContext)) {
/* 207 */       localImageData1.unref();
/* 208 */       return new ImageData(paramFilterContext, null, localImageData1.getUntransformedBounds());
/*     */     }
/* 210 */     ImageData localImageData2 = filterImageDatas(paramFilterContext, paramBaseTransform, paramRectangle, new ImageData[] { localImageData1 });
/* 211 */     localImageData1.unref();
/* 212 */     return localImageData2;
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 220 */     Rectangle localRectangle = new Rectangle(getBounds(paramBaseTransform, null));
/* 221 */     localRectangle.intersectWith(paramRectangle);
/* 222 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 227 */     setupTransforms(BaseTransform.IDENTITY_TRANSFORM);
/* 228 */     Effect localEffect = getDefaultedInput(0, paramEffect);
/* 229 */     paramPoint2D = localEffect.transform(paramPoint2D, paramEffect);
/* 230 */     BaseBounds localBaseBounds = localEffect.getBounds(BaseTransform.IDENTITY_TRANSFORM, paramEffect);
/* 231 */     float f1 = (paramPoint2D.x - localBaseBounds.getMinX()) / localBaseBounds.getWidth();
/* 232 */     float f2 = (paramPoint2D.y - localBaseBounds.getMinY()) / localBaseBounds.getHeight();
/* 233 */     float f3 = this.tx[0][0] * f1 + this.tx[0][1] * f2 + this.tx[0][2];
/* 234 */     float f4 = this.tx[1][0] * f1 + this.tx[1][1] * f2 + this.tx[1][2];
/* 235 */     float f5 = this.tx[2][0] * f1 + this.tx[2][1] * f2 + this.tx[2][2];
/* 236 */     paramPoint2D = new Point2D(f3 / f5, f4 / f5);
/* 237 */     return paramPoint2D;
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 242 */     setupTransforms(BaseTransform.IDENTITY_TRANSFORM);
/* 243 */     Effect localEffect = getDefaultedInput(0, paramEffect);
/* 244 */     float f1 = paramPoint2D.x;
/* 245 */     float f2 = paramPoint2D.y;
/* 246 */     float[][] arrayOfFloat = this.state.getITX();
/* 247 */     float f3 = arrayOfFloat[0][0] * f1 + arrayOfFloat[0][1] * f2 + arrayOfFloat[0][2];
/* 248 */     float f4 = arrayOfFloat[1][0] * f1 + arrayOfFloat[1][1] * f2 + arrayOfFloat[1][2];
/* 249 */     float f5 = arrayOfFloat[2][0] * f1 + arrayOfFloat[2][1] * f2 + arrayOfFloat[2][2];
/* 250 */     BaseBounds localBaseBounds = localEffect.getBounds(BaseTransform.IDENTITY_TRANSFORM, paramEffect);
/* 251 */     paramPoint2D = new Point2D(localBaseBounds.getMinX() + f3 / f5 * localBaseBounds.getWidth(), localBaseBounds.getMinY() + f4 / f5 * localBaseBounds.getHeight());
/*     */ 
/* 253 */     paramPoint2D = getDefaultedInput(0, paramEffect).untransform(paramPoint2D, paramEffect);
/* 254 */     return paramPoint2D;
/*     */   }
/*     */ 
/*     */   private void setupTransforms(BaseTransform paramBaseTransform) {
/* 258 */     setupDevCoords(paramBaseTransform);
/* 259 */     setUnitQuadMapping(this.devcoords[0], this.devcoords[1], this.devcoords[2], this.devcoords[3], this.devcoords[4], this.devcoords[5], this.devcoords[6], this.devcoords[7]);
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 273 */     return null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.PerspectiveTransform
 * JD-Core Version:    0.6.2
 */