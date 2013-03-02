/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.scene.image.Image;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ImageInputBuilder<B extends ImageInputBuilder<B>>
/*    */   implements Builder<ImageInput>
/*    */ {
/*    */   private int __set;
/*    */   private Image source;
/*    */   private double x;
/*    */   private double y;
/*    */ 
/*    */   public static ImageInputBuilder<?> create()
/*    */   {
/* 15 */     return new ImageInputBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ImageInput paramImageInput)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramImageInput.setSource(this.source);
/* 22 */     if ((i & 0x2) != 0) paramImageInput.setX(this.x);
/* 23 */     if ((i & 0x4) != 0) paramImageInput.setY(this.y);
/*    */   }
/*    */ 
/*    */   public B source(Image paramImage)
/*    */   {
/* 32 */     this.source = paramImage;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B x(double paramDouble)
/*    */   {
/* 43 */     this.x = paramDouble;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B y(double paramDouble)
/*    */   {
/* 54 */     this.y = paramDouble;
/* 55 */     this.__set |= 4;
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   public ImageInput build()
/*    */   {
/* 63 */     ImageInput localImageInput = new ImageInput();
/* 64 */     applyTo(localImageInput);
/* 65 */     return localImageInput;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.ImageInputBuilder
 * JD-Core Version:    0.6.2
 */