/*     */ package javafx.scene.paint;
/*     */ 
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.scene.image.Image;
/*     */ 
/*     */ public final class ImagePattern extends Paint
/*     */ {
/*     */   private Image image;
/*     */   private double x;
/*     */   private double y;
/* 169 */   private double width = 1.0D;
/*     */ 
/* 182 */   private double height = 1.0D;
/*     */ 
/* 195 */   private boolean proportional = true;
/*     */   private Object platformPaint;
/*     */ 
/*     */   public final Image getImage()
/*     */   {
/* 140 */     return this.image;
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/* 153 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 165 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final double getWidth()
/*     */   {
/* 178 */     return this.width;
/*     */   }
/*     */ 
/*     */   public final double getHeight()
/*     */   {
/* 191 */     return this.height;
/*     */   }
/*     */ 
/*     */   public final boolean isProportional()
/*     */   {
/* 209 */     return this.proportional;
/*     */   }
/*     */ 
/*     */   public ImagePattern(Image paramImage)
/*     */   {
/* 224 */     if (paramImage == null)
/* 225 */       throw new NullPointerException("Image must be non-null.");
/* 226 */     if (paramImage.getProgress() < 1.0D) {
/* 227 */       throw new IllegalArgumentException("Image not yet loaded");
/*     */     }
/* 229 */     this.image = paramImage;
/*     */   }
/*     */ 
/*     */   public ImagePattern(Image paramImage, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean)
/*     */   {
/* 249 */     if (paramImage == null)
/* 250 */       throw new NullPointerException("Image must be non-null.");
/* 251 */     if (paramImage.getProgress() < 1.0D) {
/* 252 */       throw new IllegalArgumentException("Image not yet loaded");
/*     */     }
/* 254 */     this.image = paramImage;
/* 255 */     this.x = paramDouble1;
/* 256 */     this.y = paramDouble2;
/* 257 */     this.width = paramDouble3;
/* 258 */     this.height = paramDouble4;
/* 259 */     this.proportional = paramBoolean;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Object impl_getPlatformPaint()
/*     */   {
/* 268 */     if (this.platformPaint == null) {
/* 269 */       this.platformPaint = Toolkit.getToolkit().getPaint(this);
/*     */     }
/* 271 */     return this.platformPaint;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.ImagePattern
 * JD-Core Version:    0.6.2
 */