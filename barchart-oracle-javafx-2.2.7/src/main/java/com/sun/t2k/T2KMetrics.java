/*    */ package com.sun.t2k;
/*    */ 
/*    */ import com.sun.javafx.font.FontStrike.Metrics;
/*    */ 
/*    */ class T2KMetrics
/*    */   implements FontStrike.Metrics
/*    */ {
/*    */   float ascent;
/*    */   float descent;
/*    */   float linegap;
/*    */   float xHeight;
/*    */ 
/*    */   T2KMetrics(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*    */   {
/* 17 */     this.ascent = paramFloat1;
/* 18 */     this.descent = paramFloat2;
/* 19 */     this.linegap = paramFloat3;
/* 20 */     this.xHeight = paramFloat4;
/*    */   }
/*    */ 
/*    */   public float getAscent() {
/* 24 */     return this.ascent;
/*    */   }
/*    */ 
/*    */   public float getDescent() {
/* 28 */     return this.descent;
/*    */   }
/*    */ 
/*    */   public float getLineGap() {
/* 32 */     return this.linegap;
/*    */   }
/*    */ 
/*    */   public float getLineHeight()
/*    */   {
/* 39 */     return -this.ascent + this.descent + this.linegap;
/*    */   }
/*    */ 
/*    */   public float getXHeight() {
/* 43 */     return this.xHeight;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 47 */     return "ascent = " + getAscent() + " descent = " + getDescent() + " linegap = " + getLineGap() + " lineheight = " + getLineHeight();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.T2KMetrics
 * JD-Core Version:    0.6.2
 */