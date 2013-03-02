/*    */ package com.sun.prism.util.tess.impl.tess;
/*    */ 
/*    */ class GLUvertex
/*    */ {
/*    */   public GLUvertex next;
/*    */   public GLUvertex prev;
/*    */   public GLUhalfEdge anEdge;
/*    */   public Object data;
/* 62 */   public double[] coords = new double[3];
/*    */   public double s;
/*    */   public double t;
/*    */   public int pqHandle;
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.GLUvertex
 * JD-Core Version:    0.6.2
 */