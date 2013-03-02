/*    */ package com.sun.glass.ui;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class Pen
/*    */ {
/*    */   private View view;
/*    */ 
/*    */   protected void setView(View view)
/*    */   {
/* 13 */     this.view = view;
/*    */   }
/*    */ 
/*    */   public View getView() {
/* 17 */     return this.view;
/*    */   }
/*    */   public abstract Map getCapabilities();
/*    */ 
/*    */   public Map addCapability(Map capabilities, Object key, Object value) {
/* 22 */     if (capabilities == null) {
/* 23 */       capabilities = new HashMap();
/*    */     }
/* 25 */     capabilities.put(key, value);
/* 26 */     return capabilities;
/*    */   }
/*    */ 
/*    */   public abstract void paint(long paramLong, int paramInt1, int paramInt2);
/*    */ 
/*    */   public void begin() {
/* 32 */     getView().lock();
/*    */   }
/*    */ 
/*    */   public void end() {
/* 36 */     getView().unlock(true);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Pen
 * JD-Core Version:    0.6.2
 */