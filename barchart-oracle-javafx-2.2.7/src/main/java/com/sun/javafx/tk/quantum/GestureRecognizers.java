/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Vector;
/*    */ 
/*    */ class GestureRecognizers
/*    */   implements GestureRecognizer
/*    */ {
/* 11 */   private Collection<GestureRecognizer> recognizers = new Vector();
/*    */   private GestureRecognizer[] workList;
/*    */ 
/*    */   void add(GestureRecognizer paramGestureRecognizer)
/*    */   {
/* 15 */     if (!contains(paramGestureRecognizer)) {
/* 16 */       this.recognizers.add(paramGestureRecognizer);
/* 17 */       this.workList = null;
/*    */     }
/*    */   }
/*    */ 
/*    */   void remove(GestureRecognizer paramGestureRecognizer) {
/* 22 */     if (contains(paramGestureRecognizer)) {
/* 23 */       this.recognizers.remove(paramGestureRecognizer);
/* 24 */       this.workList = null;
/*    */     }
/*    */   }
/*    */ 
/*    */   boolean contains(GestureRecognizer paramGestureRecognizer) {
/* 29 */     return this.recognizers.contains(paramGestureRecognizer);
/*    */   }
/*    */ 
/*    */   private GestureRecognizer[] synchWorkList() {
/* 33 */     if (this.workList == null) {
/* 34 */       this.workList = ((GestureRecognizer[])this.recognizers.toArray(new GestureRecognizer[0]));
/*    */     }
/* 36 */     return this.workList;
/*    */   }
/*    */ 
/*    */   public void notifyBeginTouchEvent(long paramLong, int paramInt1, boolean paramBoolean, int paramInt2) {
/* 40 */     GestureRecognizer[] arrayOfGestureRecognizer = synchWorkList();
/* 41 */     for (int i = 0; i != arrayOfGestureRecognizer.length; i++)
/* 42 */       arrayOfGestureRecognizer[i].notifyBeginTouchEvent(paramLong, paramInt1, paramBoolean, paramInt2);
/*    */   }
/*    */ 
/*    */   public void notifyNextTouchEvent(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*    */   {
/* 49 */     GestureRecognizer[] arrayOfGestureRecognizer = synchWorkList();
/* 50 */     for (int i = 0; i != arrayOfGestureRecognizer.length; i++)
/* 51 */       arrayOfGestureRecognizer[i].notifyNextTouchEvent(paramLong1, paramInt1, paramLong2, paramInt2, paramInt3, paramInt4, paramInt5);
/*    */   }
/*    */ 
/*    */   public void notifyEndTouchEvent(long paramLong)
/*    */   {
/* 56 */     GestureRecognizer[] arrayOfGestureRecognizer = synchWorkList();
/* 57 */     for (int i = 0; i != arrayOfGestureRecognizer.length; i++)
/* 58 */       arrayOfGestureRecognizer[i].notifyEndTouchEvent(paramLong);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GestureRecognizers
 * JD-Core Version:    0.6.2
 */