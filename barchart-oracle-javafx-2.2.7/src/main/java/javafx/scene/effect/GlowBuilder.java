/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class GlowBuilder<B extends GlowBuilder<B>>
/*    */   implements Builder<Glow>
/*    */ {
/*    */   private int __set;
/*    */   private Effect input;
/*    */   private double level;
/*    */ 
/*    */   public static GlowBuilder<?> create()
/*    */   {
/* 15 */     return new GlowBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Glow paramGlow)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramGlow.setInput(this.input);
/* 22 */     if ((i & 0x2) != 0) paramGlow.setLevel(this.level);
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
/*    */   public Glow build()
/*    */   {
/* 51 */     Glow localGlow = new Glow();
/* 52 */     applyTo(localGlow);
/* 53 */     return localGlow;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.GlowBuilder
 * JD-Core Version:    0.6.2
 */