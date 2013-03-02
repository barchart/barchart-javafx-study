/*     */ package com.sun.prism.impl.shape;
/*     */ 
/*     */ import com.sun.prism.impl.VertexBuffer;
/*     */ import com.sun.prism.util.tess.TessellatorCallbackAdapter;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ class TessCallbacks extends TessellatorCallbackAdapter
/*     */ {
/* 110 */   private int nPrims = 0;
/* 111 */   private int nVerts = 0;
/*     */   private VertexBuffer vb;
/*     */ 
/*     */   public void setVertexBuffer(VertexBuffer paramVertexBuffer)
/*     */   {
/* 115 */     this.vb = paramVertexBuffer;
/* 116 */     this.nPrims = 0;
/* 117 */     this.nVerts = 0;
/*     */   }
/*     */ 
/*     */   public int getNumVerts() {
/* 121 */     return this.nVerts;
/*     */   }
/*     */ 
/*     */   public void begin(int paramInt)
/*     */   {
/* 126 */     if (paramInt != 4) {
/* 127 */       throw new InternalError("Only GL_TRIANGLES is supported");
/*     */     }
/* 129 */     this.nVerts = 0;
/*     */   }
/*     */ 
/*     */   public void end()
/*     */   {
/* 134 */     this.nPrims += 1;
/*     */   }
/*     */ 
/*     */   public void vertex(Object paramObject)
/*     */   {
/* 140 */     double[] arrayOfDouble = (double[])paramObject;
/* 141 */     this.vb.addVert((float)arrayOfDouble[0], (float)arrayOfDouble[1]);
/* 142 */     this.nVerts += 1;
/*     */   }
/*     */ 
/*     */   public void edgeFlag(boolean paramBoolean)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void combine(double[] paramArrayOfDouble, Object[] paramArrayOfObject1, float[] paramArrayOfFloat, Object[] paramArrayOfObject2)
/*     */   {
/* 153 */     double[] arrayOfDouble = new double[3];
/* 154 */     for (int i = 0; i < 3; i++) {
/* 155 */       arrayOfDouble[i] = paramArrayOfDouble[i];
/*     */     }
/* 157 */     paramArrayOfObject2[0] = arrayOfDouble;
/*     */   }
/*     */ 
/*     */   public void error(int paramInt)
/*     */   {
/* 162 */     System.err.println("Tesselation error " + paramInt);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.shape.TessCallbacks
 * JD-Core Version:    0.6.2
 */