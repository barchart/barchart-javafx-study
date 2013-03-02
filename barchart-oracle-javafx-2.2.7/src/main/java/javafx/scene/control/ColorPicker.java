/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.paint.Color;
/*     */ 
/*     */ public class ColorPicker extends ComboBoxBase<Color>
/*     */ {
/*     */   public static final String STYLE_CLASS_BUTTON = "button";
/*     */   public static final String STYLE_CLASS_SPLIT_BUTTON = "split-button";
/*  86 */   private ObservableList<Color> customColors = FXCollections.observableArrayList();
/*     */   private static final String DEFAULT_STYLE_CLASS = "color-picker";
/*     */ 
/*     */   public final ObservableList<Color> getCustomColors()
/*     */   {
/*  88 */     return this.customColors;
/*     */   }
/*     */ 
/*     */   public ColorPicker() {
/*  92 */     this(Color.WHITE);
/*     */   }
/*     */ 
/*     */   public ColorPicker(Color paramColor)
/*     */   {
/* 100 */     setValue(paramColor);
/* 101 */     getStyleClass().add("color-picker");
/*     */   }
/*     */ 
/*     */   void valueInvalidated()
/*     */   {
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ColorPicker
 * JD-Core Version:    0.6.2
 */