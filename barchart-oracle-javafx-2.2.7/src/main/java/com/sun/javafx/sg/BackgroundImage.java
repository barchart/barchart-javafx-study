/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ public class BackgroundImage
/*    */ {
/*    */   public final Object image;
/*    */   public final Repeat repeatX;
/*    */   public final Repeat repeatY;
/*    */   public final float top;
/*    */   public final float left;
/*    */   public final float bottom;
/*    */   public final float right;
/*    */   public final float width;
/*    */   public final float height;
/*    */   public final boolean contain;
/*    */   public final boolean cover;
/*    */   public boolean proportionalHPos;
/*    */   public boolean proportionalVPos;
/*    */   public boolean proportionalWidth;
/*    */   public boolean proportionalHeight;
/*    */ 
/*    */   public BackgroundImage(Object paramObject, Repeat paramRepeat1, Repeat paramRepeat2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*    */   {
/* 24 */     this.image = paramObject;
/* 25 */     this.repeatX = paramRepeat1;
/* 26 */     this.repeatY = paramRepeat2;
/* 27 */     this.top = paramFloat1;
/* 28 */     this.left = paramFloat2;
/* 29 */     this.bottom = paramFloat3;
/* 30 */     this.right = paramFloat4;
/* 31 */     this.proportionalHPos = paramBoolean1;
/* 32 */     this.proportionalVPos = paramBoolean2;
/* 33 */     this.width = paramFloat5;
/* 34 */     this.height = paramFloat6;
/* 35 */     this.contain = paramBoolean5;
/* 36 */     this.cover = paramBoolean6;
/* 37 */     this.proportionalWidth = paramBoolean3;
/* 38 */     this.proportionalHeight = paramBoolean4;
/*    */   }
/*    */   public Object getImage() {
/* 41 */     return this.image; } 
/* 42 */   public Repeat getRepeatX() { return this.repeatX; } 
/* 43 */   public Repeat getRepeatY() { return this.repeatY; } 
/* 44 */   public float getTop() { return this.top; } 
/* 45 */   public float getLeft() { return this.left; } 
/* 46 */   public float getBottom() { return this.bottom; } 
/* 47 */   public float getRight() { return this.right; } 
/* 48 */   public float getWidth() { return this.width; } 
/* 49 */   public float getHeight() { return this.height; } 
/* 50 */   public boolean getProportionalHPos() { return this.proportionalHPos; } 
/* 51 */   public boolean getProportionalVPos() { return this.proportionalVPos; } 
/* 52 */   public boolean getProportionalWidth() { return this.proportionalWidth; } 
/* 53 */   public boolean getProportionalHeight() { return this.proportionalHeight; } 
/* 54 */   public boolean getContain() { return this.contain; } 
/* 55 */   public boolean getCover() { return this.cover; }
/*    */ 
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.BackgroundImage
 * JD-Core Version:    0.6.2
 */