/*    */ package com.sun.prism.paint;
/*    */ 
/*    */ import com.sun.prism.Image;
/*    */ import com.sun.prism.PixelFormat;
/*    */ 
/*    */ public final class ImagePattern extends Paint
/*    */ {
/*    */   private final Image image;
/*    */   private final float x;
/*    */   private final float y;
/*    */   private final float width;
/*    */   private final float height;
/*    */ 
/*    */   public ImagePattern(Image paramImage, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean)
/*    */   {
/* 21 */     super(Paint.Type.IMAGE_PATTERN, paramBoolean);
/* 22 */     if (paramImage == null) {
/* 23 */       throw new IllegalArgumentException("Image must be non-null");
/*    */     }
/* 25 */     this.image = paramImage;
/* 26 */     this.x = paramFloat1;
/* 27 */     this.y = paramFloat2;
/* 28 */     this.width = paramFloat3;
/* 29 */     this.height = paramFloat4;
/*    */   }
/*    */ 
/*    */   public Image getImage() {
/* 33 */     return this.image;
/*    */   }
/*    */ 
/*    */   public float getX() {
/* 37 */     return this.x;
/*    */   }
/*    */ 
/*    */   public float getY() {
/* 41 */     return this.y;
/*    */   }
/*    */ 
/*    */   public float getWidth() {
/* 45 */     return this.width;
/*    */   }
/*    */ 
/*    */   public float getHeight() {
/* 49 */     return this.height;
/*    */   }
/*    */ 
/*    */   public boolean isOpaque() {
/* 53 */     return this.image.getPixelFormat().isOpaque();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.paint.ImagePattern
 * JD-Core Version:    0.6.2
 */