/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class GaussianBlurBuilder<B extends GaussianBlurBuilder<B>>
/*    */   implements Builder<GaussianBlur>
/*    */ {
/*    */   private int __set;
/*    */   private Effect input;
/*    */   private double radius;
/*    */ 
/*    */   public static GaussianBlurBuilder<?> create()
/*    */   {
/* 15 */     return new GaussianBlurBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(GaussianBlur paramGaussianBlur)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramGaussianBlur.setInput(this.input);
/* 22 */     if ((i & 0x2) != 0) paramGaussianBlur.setRadius(this.radius);
/*    */   }
/*    */ 
/*    */   public B input(Effect paramEffect)
/*    */   {
/* 31 */     this.input = paramEffect;
/* 32 */     this.__set |= 1;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public B radius(double paramDouble)
/*    */   {
/* 42 */     this.radius = paramDouble;
/* 43 */     this.__set |= 2;
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   public GaussianBlur build()
/*    */   {
/* 51 */     GaussianBlur localGaussianBlur = new GaussianBlur();
/* 52 */     applyTo(localGaussianBlur);
/* 53 */     return localGaussianBlur;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.GaussianBlurBuilder
 * JD-Core Version:    0.6.2
 */