/*    */ package com.sun.prism.util.tess.impl.tess;
/*    */ 
/*    */ abstract class PriorityQ
/*    */ {
/*    */   public static final int INIT_SIZE = 32;
/*    */ 
/*    */   public static boolean LEQ(Leq paramLeq, Object paramObject1, Object paramObject2)
/*    */   {
/* 79 */     return Geom.VertLeq((GLUvertex)paramObject1, (GLUvertex)paramObject2);
/*    */   }
/*    */ 
/*    */   static PriorityQ pqNewPriorityQ(Leq paramLeq) {
/* 83 */     return new PriorityQSort(paramLeq);
/*    */   }
/*    */ 
/*    */   abstract void pqDeletePriorityQ();
/*    */ 
/*    */   abstract boolean pqInit();
/*    */ 
/*    */   abstract int pqInsert(Object paramObject);
/*    */ 
/*    */   abstract Object pqExtractMin();
/*    */ 
/*    */   abstract void pqDelete(int paramInt);
/*    */ 
/*    */   abstract Object pqMinimum();
/*    */ 
/*    */   abstract boolean pqIsEmpty();
/*    */ 
/*    */   public static abstract interface Leq
/*    */   {
/*    */     public abstract boolean leq(Object paramObject1, Object paramObject2);
/*    */   }
/*    */ 
/*    */   public static class PQhandleElem
/*    */   {
/*    */     Object key;
/*    */     int node;
/*    */   }
/*    */ 
/*    */   public static class PQnode
/*    */   {
/*    */     int handle;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.PriorityQ
 * JD-Core Version:    0.6.2
 */