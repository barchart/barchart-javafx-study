/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ReflectionBuilder<B extends ReflectionBuilder<B>>
/*    */   implements Builder<Reflection>
/*    */ {
/*    */   private int __set;
/*    */   private double bottomOpacity;
/*    */   private double fraction;
/*    */   private Effect input;
/*    */   private double topOffset;
/*    */   private double topOpacity;
/*    */ 
/*    */   public static ReflectionBuilder<?> create()
/*    */   {
/* 15 */     return new ReflectionBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Reflection paramReflection)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramReflection.setBottomOpacity(this.bottomOpacity);
/* 22 */     if ((i & 0x2) != 0) paramReflection.setFraction(this.fraction);
/* 23 */     if ((i & 0x4) != 0) paramReflection.setInput(this.input);
/* 24 */     if ((i & 0x8) != 0) paramReflection.setTopOffset(this.topOffset);
/* 25 */     if ((i & 0x10) != 0) paramReflection.setTopOpacity(this.topOpacity);
/*    */   }
/*    */ 
/*    */   public B bottomOpacity(double paramDouble)
/*    */   {
/* 34 */     this.bottomOpacity = paramDouble;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B fraction(double paramDouble)
/*    */   {
/* 45 */     this.fraction = paramDouble;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B input(Effect paramEffect)
/*    */   {
/* 56 */     this.input = paramEffect;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B topOffset(double paramDouble)
/*    */   {
/* 67 */     this.topOffset = paramDouble;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public B topOpacity(double paramDouble)
/*    */   {
/* 78 */     this.topOpacity = paramDouble;
/* 79 */     this.__set |= 16;
/* 80 */     return this;
/*    */   }
/*    */ 
/*    */   public Reflection build()
/*    */   {
/* 87 */     Reflection localReflection = new Reflection();
/* 88 */     applyTo(localReflection);
/* 89 */     return localReflection;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.ReflectionBuilder
 * JD-Core Version:    0.6.2
 */