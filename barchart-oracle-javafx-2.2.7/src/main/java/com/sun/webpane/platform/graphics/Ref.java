/*    */ package com.sun.webpane.platform.graphics;
/*    */ 
/*    */ public class Ref
/*    */ {
/*    */   final int id;
/*    */   int count;
/*    */ 
/*    */   public Ref()
/*    */   {
/* 11 */     this.count = 0;
/* 12 */     this.id = WCGraphicsManager.getGraphicsManager().createID();
/*    */   }
/*    */ 
/*    */   public synchronized void ref() {
/* 16 */     if (this.count == 0) {
/* 17 */       WCGraphicsManager.getGraphicsManager().ref(this);
/*    */     }
/* 19 */     this.count += 1;
/*    */   }
/*    */ 
/*    */   public synchronized void deref() {
/* 23 */     if (this.count == 0) {
/* 24 */       throw new IllegalStateException("Object  " + this.id + " has no references.");
/*    */     }
/* 26 */     this.count -= 1;
/* 27 */     if (this.count == 0)
/* 28 */       WCGraphicsManager.getGraphicsManager().deref(this);
/*    */   }
/*    */ 
/*    */   public boolean hasRefs()
/*    */   {
/* 33 */     return this.count > 0;
/*    */   }
/*    */ 
/*    */   public int getID() {
/* 37 */     return this.id;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.Ref
 * JD-Core Version:    0.6.2
 */