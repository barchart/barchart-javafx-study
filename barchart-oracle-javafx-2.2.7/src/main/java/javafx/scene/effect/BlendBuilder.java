/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class BlendBuilder<B extends BlendBuilder<B>>
/*    */   implements Builder<Blend>
/*    */ {
/*    */   private int __set;
/*    */   private Effect bottomInput;
/*    */   private BlendMode mode;
/*    */   private double opacity;
/*    */   private Effect topInput;
/*    */ 
/*    */   public static BlendBuilder<?> create()
/*    */   {
/* 15 */     return new BlendBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Blend paramBlend)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramBlend.setBottomInput(this.bottomInput);
/* 22 */     if ((i & 0x2) != 0) paramBlend.setMode(this.mode);
/* 23 */     if ((i & 0x4) != 0) paramBlend.setOpacity(this.opacity);
/* 24 */     if ((i & 0x8) != 0) paramBlend.setTopInput(this.topInput);
/*    */   }
/*    */ 
/*    */   public B bottomInput(Effect paramEffect)
/*    */   {
/* 33 */     this.bottomInput = paramEffect;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B mode(BlendMode paramBlendMode)
/*    */   {
/* 44 */     this.mode = paramBlendMode;
/* 45 */     this.__set |= 2;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   public B opacity(double paramDouble)
/*    */   {
/* 55 */     this.opacity = paramDouble;
/* 56 */     this.__set |= 4;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   public B topInput(Effect paramEffect)
/*    */   {
/* 66 */     this.topInput = paramEffect;
/* 67 */     this.__set |= 8;
/* 68 */     return this;
/*    */   }
/*    */ 
/*    */   public Blend build()
/*    */   {
/* 75 */     Blend localBlend = new Blend();
/* 76 */     applyTo(localBlend);
/* 77 */     return localBlend;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.BlendBuilder
 * JD-Core Version:    0.6.2
 */