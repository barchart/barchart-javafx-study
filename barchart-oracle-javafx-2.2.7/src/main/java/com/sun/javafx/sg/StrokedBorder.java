/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ public class StrokedBorder extends Border
/*    */ {
/*    */   public final float topLeftRadius;
/*    */   public final float topRightRadius;
/*    */   public final float bottomLeftRadius;
/*    */   public final float bottomRightRadius;
/*    */   public final Object topFill;
/*    */   public final Object leftFill;
/*    */   public final Object bottomFill;
/*    */   public final Object rightFill;
/*    */   public final BorderStyle topStyle;
/*    */   public final BorderStyle rightStyle;
/*    */   public final BorderStyle bottomStyle;
/*    */   public final BorderStyle leftStyle;
/*    */ 
/*    */   public StrokedBorder(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, BorderStyle paramBorderStyle1, BorderStyle paramBorderStyle2, BorderStyle paramBorderStyle3, BorderStyle paramBorderStyle4)
/*    */   {
/* 19 */     super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*    */ 
/* 21 */     this.topLeftRadius = paramFloat9;
/* 22 */     this.topRightRadius = paramFloat10;
/* 23 */     this.bottomLeftRadius = paramFloat11;
/* 24 */     this.bottomRightRadius = paramFloat12;
/* 25 */     this.topFill = paramObject1;
/* 26 */     this.leftFill = paramObject2;
/* 27 */     this.bottomFill = paramObject3;
/* 28 */     this.rightFill = paramObject4;
/* 29 */     this.topStyle = paramBorderStyle1;
/* 30 */     this.leftStyle = paramBorderStyle2;
/* 31 */     this.bottomStyle = paramBorderStyle3;
/* 32 */     this.rightStyle = paramBorderStyle4;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.StrokedBorder
 * JD-Core Version:    0.6.2
 */