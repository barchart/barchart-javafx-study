/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.scene.paint.Color;
/*    */ 
/*    */ public abstract class LightBuilder<B extends LightBuilder<B>>
/*    */ {
/*    */   private boolean __set;
/*    */   private Color color;
/*    */ 
/*    */   public void applyTo(Light paramLight)
/*    */   {
/* 15 */     if (this.__set) paramLight.setColor(this.color);
/*    */   }
/*    */ 
/*    */   public B color(Color paramColor)
/*    */   {
/* 24 */     this.color = paramColor;
/* 25 */     this.__set = true;
/* 26 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.LightBuilder
 * JD-Core Version:    0.6.2
 */