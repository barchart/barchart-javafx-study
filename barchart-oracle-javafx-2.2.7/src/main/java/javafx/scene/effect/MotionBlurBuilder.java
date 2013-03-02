/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class MotionBlurBuilder<B extends MotionBlurBuilder<B>>
/*    */   implements Builder<MotionBlur>
/*    */ {
/*    */   private int __set;
/*    */   private double angle;
/*    */   private Effect input;
/*    */   private double radius;
/*    */ 
/*    */   public static MotionBlurBuilder<?> create()
/*    */   {
/* 15 */     return new MotionBlurBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(MotionBlur paramMotionBlur)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramMotionBlur.setAngle(this.angle);
/* 22 */     if ((i & 0x2) != 0) paramMotionBlur.setInput(this.input);
/* 23 */     if ((i & 0x4) != 0) paramMotionBlur.setRadius(this.radius);
/*    */   }
/*    */ 
/*    */   public B angle(double paramDouble)
/*    */   {
/* 32 */     this.angle = paramDouble;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B input(Effect paramEffect)
/*    */   {
/* 43 */     this.input = paramEffect;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B radius(double paramDouble)
/*    */   {
/* 54 */     this.radius = paramDouble;
/* 55 */     this.__set |= 4;
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   public MotionBlur build()
/*    */   {
/* 63 */     MotionBlur localMotionBlur = new MotionBlur();
/* 64 */     applyTo(localMotionBlur);
/* 65 */     return localMotionBlur;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.MotionBlurBuilder
 * JD-Core Version:    0.6.2
 */