/*     */ package com.sun.javafx.geom.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Vec3d;
/*     */ 
/*     */ public class TransformHelper
/*     */ {
/*     */   public static BaseBounds general3dBoundsTransform(CanTransformVec3d paramCanTransformVec3d, BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2, Vec3d paramVec3d)
/*     */   {
/*  24 */     if (paramVec3d == null) {
/*  25 */       paramVec3d = new Vec3d();
/*     */     }
/*     */ 
/*  28 */     double d1 = paramBaseBounds1.getMinX();
/*  29 */     double d2 = paramBaseBounds1.getMinY();
/*  30 */     double d3 = paramBaseBounds1.getMinZ();
/*  31 */     double d4 = paramBaseBounds1.getMaxX();
/*  32 */     double d5 = paramBaseBounds1.getMaxY();
/*  33 */     double d6 = paramBaseBounds1.getMaxZ();
/*     */ 
/*  36 */     paramVec3d.set(d4, d5, d6);
/*  37 */     paramVec3d = paramCanTransformVec3d.transform(paramVec3d, paramVec3d);
/*  38 */     double d7 = paramVec3d.x;
/*  39 */     double d8 = paramVec3d.y;
/*  40 */     double d9 = paramVec3d.z;
/*  41 */     double d10 = paramVec3d.x;
/*  42 */     double d11 = paramVec3d.y;
/*  43 */     double d12 = paramVec3d.z;
/*     */ 
/*  45 */     paramVec3d.set(d1, d5, d6);
/*  46 */     paramVec3d = paramCanTransformVec3d.transform(paramVec3d, paramVec3d);
/*  47 */     if (paramVec3d.x > d10) d10 = paramVec3d.x;
/*  48 */     if (paramVec3d.y > d11) d11 = paramVec3d.y;
/*  49 */     if (paramVec3d.z > d12) d12 = paramVec3d.z;
/*  50 */     if (paramVec3d.x < d7) d7 = paramVec3d.x;
/*  51 */     if (paramVec3d.y < d8) d8 = paramVec3d.y;
/*  52 */     if (paramVec3d.z < d9) d9 = paramVec3d.z;
/*     */ 
/*  54 */     paramVec3d.set(d1, d2, d6);
/*  55 */     paramVec3d = paramCanTransformVec3d.transform(paramVec3d, paramVec3d);
/*  56 */     if (paramVec3d.x > d10) d10 = paramVec3d.x;
/*  57 */     if (paramVec3d.y > d11) d11 = paramVec3d.y;
/*  58 */     if (paramVec3d.z > d12) d12 = paramVec3d.z;
/*  59 */     if (paramVec3d.x < d7) d7 = paramVec3d.x;
/*  60 */     if (paramVec3d.y < d8) d8 = paramVec3d.y;
/*  61 */     if (paramVec3d.z < d9) d9 = paramVec3d.z;
/*     */ 
/*  63 */     paramVec3d.set(d4, d2, d6);
/*  64 */     paramVec3d = paramCanTransformVec3d.transform(paramVec3d, paramVec3d);
/*  65 */     if (paramVec3d.x > d10) d10 = paramVec3d.x;
/*  66 */     if (paramVec3d.y > d11) d11 = paramVec3d.y;
/*  67 */     if (paramVec3d.z > d12) d12 = paramVec3d.z;
/*  68 */     if (paramVec3d.x < d7) d7 = paramVec3d.x;
/*  69 */     if (paramVec3d.y < d8) d8 = paramVec3d.y;
/*  70 */     if (paramVec3d.z < d9) d9 = paramVec3d.z;
/*     */ 
/*  72 */     paramVec3d.set(d1, d5, d3);
/*  73 */     paramVec3d = paramCanTransformVec3d.transform(paramVec3d, paramVec3d);
/*  74 */     if (paramVec3d.x > d10) d10 = paramVec3d.x;
/*  75 */     if (paramVec3d.y > d11) d11 = paramVec3d.y;
/*  76 */     if (paramVec3d.z > d12) d12 = paramVec3d.z;
/*  77 */     if (paramVec3d.x < d7) d7 = paramVec3d.x;
/*  78 */     if (paramVec3d.y < d8) d8 = paramVec3d.y;
/*  79 */     if (paramVec3d.z < d9) d9 = paramVec3d.z;
/*     */ 
/*  81 */     paramVec3d.set(d4, d5, d3);
/*  82 */     paramVec3d = paramCanTransformVec3d.transform(paramVec3d, paramVec3d);
/*  83 */     if (paramVec3d.x > d10) d10 = paramVec3d.x;
/*  84 */     if (paramVec3d.y > d11) d11 = paramVec3d.y;
/*  85 */     if (paramVec3d.z > d12) d12 = paramVec3d.z;
/*  86 */     if (paramVec3d.x < d7) d7 = paramVec3d.x;
/*  87 */     if (paramVec3d.y < d8) d8 = paramVec3d.y;
/*  88 */     if (paramVec3d.z < d9) d9 = paramVec3d.z;
/*     */ 
/*  90 */     paramVec3d.set(d1, d2, d3);
/*  91 */     paramVec3d = paramCanTransformVec3d.transform(paramVec3d, paramVec3d);
/*  92 */     if (paramVec3d.x > d10) d10 = paramVec3d.x;
/*  93 */     if (paramVec3d.y > d11) d11 = paramVec3d.y;
/*  94 */     if (paramVec3d.z > d12) d12 = paramVec3d.z;
/*  95 */     if (paramVec3d.x < d7) d7 = paramVec3d.x;
/*  96 */     if (paramVec3d.y < d8) d8 = paramVec3d.y;
/*  97 */     if (paramVec3d.z < d9) d9 = paramVec3d.z;
/*     */ 
/*  99 */     paramVec3d.set(d4, d2, d3);
/* 100 */     paramVec3d = paramCanTransformVec3d.transform(paramVec3d, paramVec3d);
/* 101 */     if (paramVec3d.x > d10) d10 = paramVec3d.x;
/* 102 */     if (paramVec3d.y > d11) d11 = paramVec3d.y;
/* 103 */     if (paramVec3d.z > d12) d12 = paramVec3d.z;
/* 104 */     if (paramVec3d.x < d7) d7 = paramVec3d.x;
/* 105 */     if (paramVec3d.y < d8) d8 = paramVec3d.y;
/* 106 */     if (paramVec3d.z < d9) d9 = paramVec3d.z;
/*     */ 
/* 108 */     return paramBaseBounds2.deriveWithNewBounds((float)d7, (float)d8, (float)d9, (float)d10, (float)d11, (float)d12);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.transform.TransformHelper
 * JD-Core Version:    0.6.2
 */