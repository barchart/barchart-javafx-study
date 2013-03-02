/*    */ package com.sun.webpane.platform.event;
/*    */ 
/*    */ public class WCChangeEvent
/*    */ {
/*    */   private Object source;
/*    */ 
/*    */   public WCChangeEvent(Object paramObject)
/*    */   {
/* 10 */     if (paramObject == null) {
/* 11 */       throw new IllegalArgumentException("null source");
/*    */     }
/* 13 */     this.source = paramObject;
/*    */   }
/*    */ 
/*    */   public Object getSource() {
/* 17 */     return this.source;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 22 */     return getClass().getName() + "[source=" + this.source + "]";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.event.WCChangeEvent
 * JD-Core Version:    0.6.2
 */