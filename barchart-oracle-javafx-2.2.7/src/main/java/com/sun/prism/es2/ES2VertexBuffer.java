/*    */ package com.sun.prism.es2;
/*    */ 
/*    */ import com.sun.prism.es2.gl.GLContext;
/*    */ import com.sun.prism.impl.VertexBuffer;
/*    */ 
/*    */ class ES2VertexBuffer extends VertexBuffer
/*    */ {
/*    */   private GLContext glCtx;
/*    */   protected static final int BYTES_PER_FLOAT = 4;
/*    */ 
/*    */   ES2VertexBuffer(int paramInt)
/*    */   {
/* 13 */     super(paramInt);
/*    */   }
/*    */ 
/*    */   void enableVertexAttributes(GLContext paramGLContext)
/*    */   {
/* 31 */     this.glCtx = paramGLContext;
/*    */ 
/* 33 */     paramGLContext.enableVertexAttributes();
/*    */   }
/*    */ 
/*    */   protected void drawQuads(int paramInt)
/*    */   {
/* 38 */     this.glCtx.drawIndexedQuads(this.coordArray, this.colorArray, paramInt);
/*    */   }
/*    */ 
/*    */   protected void drawTriangles(int paramInt, float[] paramArrayOfFloat, byte[] paramArrayOfByte)
/*    */   {
/* 43 */     this.glCtx.drawTriangleList(paramInt, paramArrayOfFloat, paramArrayOfByte);
/*    */   }
/*    */ 
/*    */   public static short[] getQuadIndices16bit(int paramInt)
/*    */   {
/* 49 */     short[] arrayOfShort = new short[paramInt * 6];
/*    */ 
/* 51 */     for (int i = 0; i != paramInt; i++) {
/* 52 */       int j = i * 4;
/* 53 */       int k = i * 6;
/* 54 */       arrayOfShort[(k + 0)] = ((short)(j + 0));
/* 55 */       arrayOfShort[(k + 1)] = ((short)(j + 1));
/* 56 */       arrayOfShort[(k + 2)] = ((short)(j + 2));
/*    */ 
/* 58 */       arrayOfShort[(k + 3)] = ((short)(j + 2));
/* 59 */       arrayOfShort[(k + 4)] = ((short)(j + 1));
/* 60 */       arrayOfShort[(k + 5)] = ((short)(j + 3));
/*    */     }
/*    */ 
/* 63 */     return arrayOfShort;
/*    */   }
/*    */ 
/*    */   public int genQuadsIndexBuffer(int paramInt) {
/* 67 */     if (paramInt * 6 > 65536) {
/* 68 */       throw new IllegalArgumentException("vertex indices overflow");
/*    */     }
/* 70 */     return this.glCtx.createIndexBuffer16(getQuadIndices16bit(paramInt));
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.ES2VertexBuffer
 * JD-Core Version:    0.6.2
 */