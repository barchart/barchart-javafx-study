/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.scene.paint.Paint;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ColorInputBuilder<B extends ColorInputBuilder<B>>
/*    */   implements Builder<ColorInput>
/*    */ {
/*    */   private int __set;
/*    */   private double height;
/*    */   private Paint paint;
/*    */   private double width;
/*    */   private double x;
/*    */   private double y;
/*    */ 
/*    */   public static ColorInputBuilder<?> create()
/*    */   {
/* 15 */     return new ColorInputBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ColorInput paramColorInput)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramColorInput.setHeight(this.height);
/* 22 */     if ((i & 0x2) != 0) paramColorInput.setPaint(this.paint);
/* 23 */     if ((i & 0x4) != 0) paramColorInput.setWidth(this.width);
/* 24 */     if ((i & 0x8) != 0) paramColorInput.setX(this.x);
/* 25 */     if ((i & 0x10) != 0) paramColorInput.setY(this.y);
/*    */   }
/*    */ 
/*    */   public B height(double paramDouble)
/*    */   {
/* 34 */     this.height = paramDouble;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B paint(Paint paramPaint)
/*    */   {
/* 45 */     this.paint = paramPaint;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B width(double paramDouble)
/*    */   {
/* 56 */     this.width = paramDouble;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B x(double paramDouble)
/*    */   {
/* 67 */     this.x = paramDouble;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public B y(double paramDouble)
/*    */   {
/* 78 */     this.y = paramDouble;
/* 79 */     this.__set |= 16;
/* 80 */     return this;
/*    */   }
/*    */ 
/*    */   public ColorInput build()
/*    */   {
/* 87 */     ColorInput localColorInput = new ColorInput();
/* 88 */     applyTo(localColorInput);
/* 89 */     return localColorInput;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.ColorInputBuilder
 * JD-Core Version:    0.6.2
 */