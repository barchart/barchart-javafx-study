/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ColorAdjustBuilder<B extends ColorAdjustBuilder<B>>
/*    */   implements Builder<ColorAdjust>
/*    */ {
/*    */   private int __set;
/*    */   private double brightness;
/*    */   private double contrast;
/*    */   private double hue;
/*    */   private Effect input;
/*    */   private double saturation;
/*    */ 
/*    */   public static ColorAdjustBuilder<?> create()
/*    */   {
/* 15 */     return new ColorAdjustBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ColorAdjust paramColorAdjust)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramColorAdjust.setBrightness(this.brightness);
/* 22 */     if ((i & 0x2) != 0) paramColorAdjust.setContrast(this.contrast);
/* 23 */     if ((i & 0x4) != 0) paramColorAdjust.setHue(this.hue);
/* 24 */     if ((i & 0x8) != 0) paramColorAdjust.setInput(this.input);
/* 25 */     if ((i & 0x10) != 0) paramColorAdjust.setSaturation(this.saturation);
/*    */   }
/*    */ 
/*    */   public B brightness(double paramDouble)
/*    */   {
/* 34 */     this.brightness = paramDouble;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B contrast(double paramDouble)
/*    */   {
/* 45 */     this.contrast = paramDouble;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B hue(double paramDouble)
/*    */   {
/* 56 */     this.hue = paramDouble;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B input(Effect paramEffect)
/*    */   {
/* 67 */     this.input = paramEffect;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public B saturation(double paramDouble)
/*    */   {
/* 78 */     this.saturation = paramDouble;
/* 79 */     this.__set |= 16;
/* 80 */     return this;
/*    */   }
/*    */ 
/*    */   public ColorAdjust build()
/*    */   {
/* 87 */     ColorAdjust localColorAdjust = new ColorAdjust();
/* 88 */     applyTo(localColorAdjust);
/* 89 */     return localColorAdjust;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.ColorAdjustBuilder
 * JD-Core Version:    0.6.2
 */