/*      */ package com.sun.prism.impl.ps;
/*      */ 
/*      */ import com.sun.prism.impl.VertexBuffer;
/*      */ import com.sun.prism.util.tess.TessellatorCallbackAdapter;
/*      */ import java.io.PrintStream;
/*      */ 
/*      */ class AATessCallbacks extends TessellatorCallbackAdapter
/*      */ {
/*  956 */   private int nPrims = 0;
/*  957 */   private int nVerts = 0;
/*      */   private VertexBuffer vb;
/*      */ 
/*      */   public void setVertexBuffer(VertexBuffer paramVertexBuffer)
/*      */   {
/*  961 */     this.vb = paramVertexBuffer;
/*  962 */     this.nPrims = 0;
/*  963 */     this.nVerts = 0;
/*      */   }
/*      */ 
/*      */   public int getNumVerts() {
/*  967 */     return this.nVerts;
/*      */   }
/*      */ 
/*      */   public void begin(int paramInt)
/*      */   {
/*  972 */     if (paramInt != 4) {
/*  973 */       throw new InternalError("Only GL_TRIANGLES is supported");
/*      */     }
/*  975 */     this.nVerts = 0;
/*      */   }
/*      */ 
/*      */   public void end()
/*      */   {
/*  980 */     this.nPrims += 1;
/*      */   }
/*      */ 
/*      */   public void vertex(Object paramObject)
/*      */   {
/*  985 */     double[] arrayOfDouble = (double[])paramObject;
/*  986 */     this.vb.addVert((float)arrayOfDouble[0], (float)arrayOfDouble[1]);
/*  987 */     this.nVerts += 1;
/*      */   }
/*      */ 
/*      */   public void edgeFlag(boolean paramBoolean)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void combine(double[] paramArrayOfDouble, Object[] paramArrayOfObject1, float[] paramArrayOfFloat, Object[] paramArrayOfObject2)
/*      */   {
/*  998 */     double[] arrayOfDouble = new double[3];
/*  999 */     for (int i = 0; i < 3; i++) {
/* 1000 */       arrayOfDouble[i] = paramArrayOfDouble[i];
/*      */     }
/* 1002 */     paramArrayOfObject2[0] = arrayOfDouble;
/*      */   }
/*      */ 
/*      */   public void error(int paramInt)
/*      */   {
/* 1007 */     System.err.println("Tesselation error " + paramInt);
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.AATessCallbacks
 * JD-Core Version:    0.6.2
 */