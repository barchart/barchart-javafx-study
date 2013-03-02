/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class BloomBuilder<B extends BloomBuilder<B>>
/*    */   implements Builder<Bloom>
/*    */ {
/*    */   private int __set;
/*    */   private Effect input;
/*    */   private double threshold;
/*    */ 
/*    */   public static BloomBuilder<?> create()
/*    */   {
/* 15 */     return new BloomBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Bloom paramBloom)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramBloom.setInput(this.input);
/* 22 */     if ((i & 0x2) != 0) paramBloom.setThreshold(this.threshold);
/*    */   }
/*    */ 
/*    */   public B input(Effect paramEffect)
/*    */   {
/* 31 */     this.input = paramEffect;
/* 32 */     this.__set |= 1;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public B threshold(double paramDouble)
/*    */   {
/* 42 */     this.threshold = paramDouble;
/* 43 */     this.__set |= 2;
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   public Bloom build()
/*    */   {
/* 51 */     Bloom localBloom = new Bloom();
/* 52 */     applyTo(localBloom);
/* 53 */     return localBloom;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.BloomBuilder
 * JD-Core Version:    0.6.2
 */