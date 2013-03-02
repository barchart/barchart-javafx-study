/*     */ package com.sun.prism.impl.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.PathIterator;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.impl.VertexBuffer;
/*     */ import com.sun.prism.util.tess.Tess;
/*     */ import com.sun.prism.util.tess.Tessellator;
/*     */ 
/*     */ public final class TesselatorImpl
/*     */ {
/*     */   private final TessCallbacks listener;
/*     */   private final Tessellator tess;
/*  22 */   private final float[] coords = new float[6];
/*     */ 
/*     */   public TesselatorImpl() {
/*  25 */     this.listener = new TessCallbacks();
/*  26 */     this.tess = Tess.newTess();
/*  27 */     Tess.tessCallback(this.tess, 100100, this.listener);
/*  28 */     Tess.tessCallback(this.tess, 100101, this.listener);
/*  29 */     Tess.tessCallback(this.tess, 100102, this.listener);
/*  30 */     Tess.tessCallback(this.tess, 100105, this.listener);
/*  31 */     Tess.tessCallback(this.tess, 100103, this.listener);
/*     */ 
/*  34 */     Tess.tessCallback(this.tess, 100104, this.listener);
/*     */   }
/*     */ 
/*     */   public int generate(Shape paramShape, VertexBuffer paramVertexBuffer)
/*     */   {
/*  41 */     this.listener.setVertexBuffer(paramVertexBuffer);
/*     */ 
/*  43 */     BaseTransform localBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*  44 */     PathIterator localPathIterator = paramShape.getPathIterator(localBaseTransform, 1.0F);
/*  45 */     Tess.tessProperty(this.tess, 100140, localPathIterator.getWindingRule() == 1 ? 100131.0D : 100130.0D);
/*     */ 
/*  50 */     int i = 0;
/*  51 */     Tess.tessBeginPolygon(this.tess, null);
/*  52 */     while (!localPathIterator.isDone()) {
/*  53 */       int j = localPathIterator.currentSegment(this.coords);
/*     */       double[] arrayOfDouble;
/*  54 */       switch (j) {
/*     */       case 0:
/*  56 */         if (i != 0) {
/*  57 */           Tess.tessEndContour(this.tess);
/*     */         }
/*  59 */         Tess.tessBeginContour(this.tess);
/*  60 */         i = 1;
/*     */ 
/*  62 */         arrayOfDouble = new double[] { this.coords[0], this.coords[1], 0.0D };
/*  63 */         Tess.tessVertex(this.tess, arrayOfDouble, 0, arrayOfDouble);
/*  64 */         break;
/*     */       case 1:
/*  66 */         if (!hasInfOrNaN(this.coords, 2)) {
/*  67 */           i = 1;
/*  68 */           arrayOfDouble = new double[] { this.coords[0], this.coords[1], 0.0D };
/*  69 */           Tess.tessVertex(this.tess, arrayOfDouble, 0, arrayOfDouble); } break;
/*     */       case 4:
/*  73 */         if (i != 0) {
/*  74 */           Tess.tessEndContour(this.tess);
/*  75 */           i = 0; } break;
/*     */       case 2:
/*     */       case 3:
/*     */       default:
/*  79 */         throw new InternalError("Path must be flattened");
/*     */       }
/*  81 */       localPathIterator.next();
/*     */     }
/*  83 */     if (i != 0) {
/*  84 */       Tess.tessEndContour(this.tess);
/*     */     }
/*  86 */     Tess.tessEndPolygon(this.tess);
/*     */ 
/*  88 */     return this.listener.getNumVerts();
/*     */   }
/*     */ 
/*     */   private static boolean hasInfOrNaN(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/*  99 */     for (int i = 0; i < paramInt; i++) {
/* 100 */       if ((Float.isInfinite(paramArrayOfFloat[i])) || (Float.isNaN(paramArrayOfFloat[i]))) {
/* 101 */         return true;
/*     */       }
/*     */     }
/* 104 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.shape.TesselatorImpl
 * JD-Core Version:    0.6.2
 */