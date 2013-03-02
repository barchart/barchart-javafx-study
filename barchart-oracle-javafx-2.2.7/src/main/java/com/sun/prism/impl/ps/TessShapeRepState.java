/*     */ package com.sun.prism.impl.ps;
/*     */ 
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.prism.BasicStroke;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.impl.BaseGraphics;
/*     */ import com.sun.prism.impl.VertexBuffer;
/*     */ import com.sun.prism.impl.shape.TesselatorImpl;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import com.sun.prism.paint.Paint.Type;
/*     */ 
/*     */ class TessShapeRepState
/*     */ {
/*  87 */   private static final TesselatorImpl tess = new TesselatorImpl();
/*     */   private boolean valid;
/*     */   private VertexBuffer vb;
/*     */   private int numVerts;
/*     */   private Paint lastPaint;
/*  93 */   private float lastExtraAlpha = -1.0F;
/*     */ 
/*     */   void invalidate() {
/*  96 */     this.valid = false;
/*  97 */     this.lastPaint = null;
/*  98 */     this.lastExtraAlpha = -1.0F;
/*     */   }
/*     */ 
/*     */   void render(Graphics paramGraphics, Shape paramShape, BasicStroke paramBasicStroke, float paramFloat1, float paramFloat2) {
/* 102 */     BaseGraphics localBaseGraphics = (BaseGraphics)paramGraphics;
/* 103 */     Paint localPaint = localBaseGraphics.getPaint();
/* 104 */     float f1 = localBaseGraphics.getExtraAlpha();
/* 105 */     if (this.vb == null) {
/* 106 */       this.vb = paramGraphics.getResourceFactory().createVertexBuffer(16);
/*     */     }
/* 108 */     int i = 0;
/* 109 */     if (!this.valid) {
/* 110 */       this.numVerts = generate(paramShape, paramBasicStroke, this.vb);
/* 111 */       this.valid = true;
/* 112 */       i = 1;
/*     */     }
/* 114 */     if ((i != 0) || (localPaint != this.lastPaint) || (f1 != this.lastExtraAlpha)) {
/* 115 */       if (localPaint.getType() == Paint.Type.COLOR) {
/* 116 */         this.vb.setPerVertexColor((Color)localPaint, f1);
/* 117 */         this.vb.updateVertexColors(this.numVerts);
/*     */       } else {
/* 119 */         this.vb.setPerVertexColor(f1);
/* 120 */         this.vb.updateVertexColors(this.numVerts);
/*     */       }
/*     */ 
/* 123 */       this.lastPaint = localPaint;
/* 124 */       this.lastExtraAlpha = f1;
/*     */     }
/*     */ 
/* 127 */     float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F; float f5 = 0.0F;
/* 128 */     if (localPaint.isProportional()) {
/* 129 */       RectBounds localRectBounds = paramShape.getBounds();
/* 130 */       f2 = localRectBounds.getMinX();
/* 131 */       f3 = localRectBounds.getMinY();
/* 132 */       f4 = localRectBounds.getWidth();
/* 133 */       f5 = localRectBounds.getHeight();
/*     */     }
/*     */ 
/* 138 */     localBaseGraphics.translate(paramFloat1, paramFloat2);
/* 139 */     localBaseGraphics.fillTriangles(this.vb, this.numVerts, f2, f3, f4, f5);
/* 140 */     localBaseGraphics.translate(-paramFloat1, -paramFloat2);
/*     */   }
/*     */ 
/*     */   private int generate(Shape paramShape, BasicStroke paramBasicStroke, VertexBuffer paramVertexBuffer) {
/* 144 */     if (paramBasicStroke != null) {
/* 145 */       paramShape = paramBasicStroke.createStrokedShape(paramShape);
/*     */     }
/* 147 */     paramVertexBuffer.rewind();
/* 148 */     return tess.generate(paramShape, paramVertexBuffer);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.TessShapeRepState
 * JD-Core Version:    0.6.2
 */