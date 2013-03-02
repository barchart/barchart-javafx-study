/*    */ package com.sun.javafx.cursor;
/*    */ 
/*    */ public final class ImageCursorFrame extends CursorFrame
/*    */ {
/*    */   private final Object platformImage;
/*    */   private final double width;
/*    */   private final double height;
/*    */   private final double hotspotX;
/*    */   private final double hotspotY;
/*    */ 
/*    */   public ImageCursorFrame(Object paramObject, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*    */   {
/* 40 */     this.platformImage = paramObject;
/* 41 */     this.width = paramDouble1;
/* 42 */     this.height = paramDouble2;
/* 43 */     this.hotspotX = paramDouble3;
/* 44 */     this.hotspotY = paramDouble4;
/*    */   }
/*    */ 
/*    */   public CursorType getCursorType()
/*    */   {
/* 49 */     return CursorType.IMAGE;
/*    */   }
/*    */ 
/*    */   public Object getPlatformImage() {
/* 53 */     return this.platformImage;
/*    */   }
/*    */ 
/*    */   public double getWidth() {
/* 57 */     return this.width;
/*    */   }
/*    */ 
/*    */   public double getHeight() {
/* 61 */     return this.height;
/*    */   }
/*    */ 
/*    */   public double getHotspotX() {
/* 65 */     return this.hotspotX;
/*    */   }
/*    */ 
/*    */   public double getHotspotY() {
/* 69 */     return this.hotspotY;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.cursor.ImageCursorFrame
 * JD-Core Version:    0.6.2
 */