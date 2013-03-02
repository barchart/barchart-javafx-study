/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ public class ImageBorder extends Border
/*    */ {
/*    */   public final Object image;
/*    */   public final float topSlice;
/*    */   public final float leftSlice;
/*    */   public final float bottomSlice;
/*    */   public final float rightSlice;
/*    */   public final Repeat repeatX;
/*    */   public final Repeat repeatY;
/*    */   public final boolean fillCenter;
/*    */ 
/*    */   public ImageBorder(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, Object paramObject, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, Repeat paramRepeat1, Repeat paramRepeat2, boolean paramBoolean)
/*    */   {
/* 18 */     super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*    */ 
/* 20 */     this.topSlice = paramFloat9;
/* 21 */     this.leftSlice = paramFloat10;
/* 22 */     this.bottomSlice = paramFloat11;
/* 23 */     this.rightSlice = paramFloat12;
/*    */ 
/* 25 */     this.image = paramObject;
/* 26 */     this.repeatX = paramRepeat1;
/* 27 */     this.repeatY = paramRepeat2;
/* 28 */     this.fillCenter = paramBoolean;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.ImageBorder
 * JD-Core Version:    0.6.2
 */