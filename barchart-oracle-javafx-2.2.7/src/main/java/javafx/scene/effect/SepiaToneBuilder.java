/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class SepiaToneBuilder<B extends SepiaToneBuilder<B>>
/*    */   implements Builder<SepiaTone>
/*    */ {
/*    */   private int __set;
/*    */   private Effect input;
/*    */   private double level;
/*    */ 
/*    */   public static SepiaToneBuilder<?> create()
/*    */   {
/* 15 */     return new SepiaToneBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(SepiaTone paramSepiaTone)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramSepiaTone.setInput(this.input);
/* 22 */     if ((i & 0x2) != 0) paramSepiaTone.setLevel(this.level);
/*    */   }
/*    */ 
/*    */   public B input(Effect paramEffect)
/*    */   {
/* 31 */     this.input = paramEffect;
/* 32 */     this.__set |= 1;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public B level(double paramDouble)
/*    */   {
/* 42 */     this.level = paramDouble;
/* 43 */     this.__set |= 2;
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   public SepiaTone build()
/*    */   {
/* 51 */     SepiaTone localSepiaTone = new SepiaTone();
/* 52 */     applyTo(localSepiaTone);
/* 53 */     return localSepiaTone;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.SepiaToneBuilder
 * JD-Core Version:    0.6.2
 */