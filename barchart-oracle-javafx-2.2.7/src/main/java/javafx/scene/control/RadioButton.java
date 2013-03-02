/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Pos;
/*     */ 
/*     */ public class RadioButton extends ToggleButton
/*     */ {
/*     */   private static final String DEFAULT_STYLE_CLASS = "radio-button";
/*     */ 
/*     */   public RadioButton()
/*     */   {
/*  71 */     initialize();
/*     */   }
/*     */ 
/*     */   public RadioButton(String paramString)
/*     */   {
/*  80 */     setText(paramString);
/*  81 */     initialize();
/*     */   }
/*     */ 
/*     */   private void initialize() {
/*  85 */     getStyleClass().setAll(new String[] { "radio-button" });
/*     */ 
/*  90 */     StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty(alignmentProperty());
/*  91 */     localStyleableProperty.set(this, Pos.CENTER_LEFT);
/*     */   }
/*     */ 
/*     */   public void fire()
/*     */   {
/* 106 */     if ((getToggleGroup() == null) || (!isSelected()))
/* 107 */       super.fire();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Pos impl_cssGetAlignmentInitialValue()
/*     */   {
/* 128 */     return Pos.CENTER_LEFT;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.RadioButton
 * JD-Core Version:    0.6.2
 */