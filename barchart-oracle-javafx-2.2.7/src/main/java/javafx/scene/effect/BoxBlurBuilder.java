/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class BoxBlurBuilder<B extends BoxBlurBuilder<B>>
/*    */   implements Builder<BoxBlur>
/*    */ {
/*    */   private int __set;
/*    */   private double height;
/*    */   private Effect input;
/*    */   private int iterations;
/*    */   private double width;
/*    */ 
/*    */   public static BoxBlurBuilder<?> create()
/*    */   {
/* 15 */     return new BoxBlurBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(BoxBlur paramBoxBlur)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramBoxBlur.setHeight(this.height);
/* 22 */     if ((i & 0x2) != 0) paramBoxBlur.setInput(this.input);
/* 23 */     if ((i & 0x4) != 0) paramBoxBlur.setIterations(this.iterations);
/* 24 */     if ((i & 0x8) != 0) paramBoxBlur.setWidth(this.width);
/*    */   }
/*    */ 
/*    */   public B height(double paramDouble)
/*    */   {
/* 33 */     this.height = paramDouble;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B input(Effect paramEffect)
/*    */   {
/* 44 */     this.input = paramEffect;
/* 45 */     this.__set |= 2;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   public B iterations(int paramInt)
/*    */   {
/* 55 */     this.iterations = paramInt;
/* 56 */     this.__set |= 4;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   public B width(double paramDouble)
/*    */   {
/* 66 */     this.width = paramDouble;
/* 67 */     this.__set |= 8;
/* 68 */     return this;
/*    */   }
/*    */ 
/*    */   public BoxBlur build()
/*    */   {
/* 75 */     BoxBlur localBoxBlur = new BoxBlur();
/* 76 */     applyTo(localBoxBlur);
/* 77 */     return localBoxBlur;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.BoxBlurBuilder
 * JD-Core Version:    0.6.2
 */