/*    */ package javafx.scene.paint;
/*    */ 
/*    */ import javafx.scene.image.Image;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class ImagePatternBuilder
/*    */   implements Builder<ImagePattern>
/*    */ {
/*    */   private double height;
/*    */   private Image image;
/*    */   private boolean proportional;
/*    */   private double width;
/*    */   private double x;
/*    */   private double y;
/*    */ 
/*    */   public static ImagePatternBuilder create()
/*    */   {
/* 15 */     return new ImagePatternBuilder();
/*    */   }
/*    */ 
/*    */   public ImagePatternBuilder height(double paramDouble)
/*    */   {
/* 23 */     this.height = paramDouble;
/* 24 */     return this;
/*    */   }
/*    */ 
/*    */   public ImagePatternBuilder image(Image paramImage)
/*    */   {
/* 32 */     this.image = paramImage;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public ImagePatternBuilder proportional(boolean paramBoolean)
/*    */   {
/* 41 */     this.proportional = paramBoolean;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   public ImagePatternBuilder width(double paramDouble)
/*    */   {
/* 50 */     this.width = paramDouble;
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   public ImagePatternBuilder x(double paramDouble)
/*    */   {
/* 59 */     this.x = paramDouble;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   public ImagePatternBuilder y(double paramDouble)
/*    */   {
/* 68 */     this.y = paramDouble;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public ImagePattern build()
/*    */   {
/* 76 */     ImagePattern localImagePattern = new ImagePattern(this.image, this.x, this.y, this.width, this.height, this.proportional);
/* 77 */     return localImagePattern;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.ImagePatternBuilder
 * JD-Core Version:    0.6.2
 */