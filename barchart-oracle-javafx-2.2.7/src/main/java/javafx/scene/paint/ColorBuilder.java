/*    */ package javafx.scene.paint;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ColorBuilder<B extends ColorBuilder<B>>
/*    */   implements Builder<Color>
/*    */ {
/*    */   private double blue;
/*    */   private double green;
/* 38 */   private double opacity = 1.0D;
/*    */   private double red;
/*    */ 
/*    */   public static ColorBuilder<?> create()
/*    */   {
/* 15 */     return new ColorBuilder();
/*    */   }
/*    */ 
/*    */   public B blue(double paramDouble)
/*    */   {
/* 24 */     this.blue = paramDouble;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B green(double paramDouble)
/*    */   {
/* 34 */     this.green = paramDouble;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B opacity(double paramDouble)
/*    */   {
/* 44 */     this.opacity = paramDouble;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B red(double paramDouble)
/*    */   {
/* 54 */     this.red = paramDouble;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public Color build()
/*    */   {
/* 62 */     Color localColor = new Color(this.red, this.green, this.blue, this.opacity);
/* 63 */     return localColor;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.ColorBuilder
 * JD-Core Version:    0.6.2
 */