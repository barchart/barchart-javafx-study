/*    */ package com.sun.prism.util.tess.impl.tess;
/*    */ 
/*    */ class GLUhalfEdge
/*    */ {
/*    */   public GLUhalfEdge next;
/*    */   public GLUhalfEdge Sym;
/*    */   public GLUhalfEdge Onext;
/*    */   public GLUhalfEdge Lnext;
/*    */   public GLUvertex Org;
/*    */   public GLUface Lface;
/*    */   public ActiveRegion activeRegion;
/*    */   public int winding;
/*    */   public boolean first;
/*    */ 
/*    */   public GLUhalfEdge(boolean paramBoolean)
/*    */   {
/* 69 */     this.first = paramBoolean;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.GLUhalfEdge
 * JD-Core Version:    0.6.2
 */