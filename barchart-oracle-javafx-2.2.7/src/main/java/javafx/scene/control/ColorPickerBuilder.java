/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.paint.Color;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ColorPickerBuilder<B extends ColorPickerBuilder<B>> extends ComboBoxBaseBuilder<Color, B>
/*    */   implements Builder<ColorPicker>
/*    */ {
/*    */   private boolean __set;
/*    */   private Collection<? extends Color> customColors;
/*    */ 
/*    */   public static ColorPickerBuilder<?> create()
/*    */   {
/* 15 */     return new ColorPickerBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ColorPicker paramColorPicker)
/*    */   {
/* 20 */     super.applyTo(paramColorPicker);
/* 21 */     if (this.__set) paramColorPicker.getCustomColors().addAll(this.customColors);
/*    */   }
/*    */ 
/*    */   public B customColors(Collection<? extends Color> paramCollection)
/*    */   {
/* 30 */     this.customColors = paramCollection;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public B customColors(Color[] paramArrayOfColor)
/*    */   {
/* 39 */     return customColors(Arrays.asList(paramArrayOfColor));
/*    */   }
/*    */ 
/*    */   public ColorPicker build()
/*    */   {
/* 46 */     ColorPicker localColorPicker = new ColorPicker();
/* 47 */     applyTo(localColorPicker);
/* 48 */     return localColorPicker;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ColorPickerBuilder
 * JD-Core Version:    0.6.2
 */