/*    */ package javafx.scene;
/*    */ 
/*    */ import javafx.scene.image.Image;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ImageCursorBuilder<B extends ImageCursorBuilder<B>>
/*    */   implements Builder<ImageCursor>
/*    */ {
/*    */   private double hotspotX;
/*    */   private double hotspotY;
/*    */   private Image image;
/*    */ 
/*    */   public static ImageCursorBuilder<?> create()
/*    */   {
/* 15 */     return new ImageCursorBuilder();
/*    */   }
/*    */ 
/*    */   public B hotspotX(double paramDouble)
/*    */   {
/* 24 */     this.hotspotX = paramDouble;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B hotspotY(double paramDouble)
/*    */   {
/* 34 */     this.hotspotY = paramDouble;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B image(Image paramImage)
/*    */   {
/* 44 */     this.image = paramImage;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public ImageCursor build()
/*    */   {
/* 52 */     ImageCursor localImageCursor = new ImageCursor(this.image, this.hotspotX, this.hotspotY);
/* 53 */     return localImageCursor;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.ImageCursorBuilder
 * JD-Core Version:    0.6.2
 */