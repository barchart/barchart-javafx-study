/*    */ package com.sun.webpane.platform.graphics;
/*    */ 
/*    */ public abstract class WCGradient<G>
/*    */ {
/*    */   public static final int PAD = 1;
/*    */   public static final int REFLECT = 2;
/*    */   public static final int REPEAT = 3;
/* 15 */   private int spreadMethod = 1;
/*    */   private boolean proportional;
/*    */ 
/*    */   public void setSpreadMethod(int paramInt)
/*    */   {
/* 19 */     if ((paramInt != 2) && (paramInt != 3)) {
/* 20 */       paramInt = 1;
/*    */     }
/* 22 */     this.spreadMethod = paramInt;
/*    */   }
/*    */ 
/*    */   public int getSpreadMethod() {
/* 26 */     return this.spreadMethod;
/*    */   }
/*    */ 
/*    */   public void setProportional(boolean paramBoolean) {
/* 30 */     this.proportional = paramBoolean;
/*    */   }
/*    */ 
/*    */   public boolean isProportional() {
/* 34 */     return this.proportional;
/*    */   }
/*    */ 
/*    */   public abstract void addStop(int paramInt, float paramFloat);
/*    */ 
/*    */   public abstract G getPlatformGradient();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCGradient
 * JD-Core Version:    0.6.2
 */