/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ public abstract interface PGArc extends PGShape
/*    */ {
/*    */   public abstract void updateArc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, ArcType paramArcType);
/*    */ 
/*    */   public static enum ArcType
/*    */   {
/* 11 */     OPEN, CHORD, ROUND;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.PGArc
 * JD-Core Version:    0.6.2
 */