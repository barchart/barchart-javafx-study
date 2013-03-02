/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ import com.sun.javafx.geom.PathIterator;
/*    */ 
/*    */ public class PathIteratorHelper
/*    */ {
/*    */   private PathIterator itr;
/* 19 */   private float[] f = new float[6];
/*    */ 
/*    */   public PathIteratorHelper(PathIterator paramPathIterator) {
/* 22 */     this.itr = paramPathIterator;
/*    */   }
/*    */ 
/*    */   public int getWindingRule()
/*    */   {
/* 30 */     return this.itr.getWindingRule();
/*    */   }
/*    */ 
/*    */   public boolean isDone()
/*    */   {
/* 39 */     return this.itr.isDone();
/*    */   }
/*    */ 
/*    */   public void next()
/*    */   {
/* 48 */     this.itr.next();
/*    */   }
/*    */ 
/*    */   public int currentSegment(Struct paramStruct)
/*    */   {
/* 73 */     int i = this.itr.currentSegment(this.f);
/* 74 */     paramStruct.f0 = this.f[0];
/* 75 */     paramStruct.f1 = this.f[1];
/* 76 */     paramStruct.f2 = this.f[2];
/* 77 */     paramStruct.f3 = this.f[3];
/* 78 */     paramStruct.f4 = this.f[4];
/* 79 */     paramStruct.f5 = this.f[5];
/* 80 */     return i;
/*    */   }
/*    */ 
/*    */   public static final class Struct
/*    */   {
/*    */     float f0;
/*    */     float f1;
/*    */     float f2;
/*    */     float f3;
/*    */     float f4;
/*    */     float f5;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.PathIteratorHelper
 * JD-Core Version:    0.6.2
 */