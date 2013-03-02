/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class ProgressBar extends ProgressIndicator
/*     */ {
/*     */   private static final String DEFAULT_STYLE_CLASS = "progress-bar";
/*     */ 
/*     */   public ProgressBar()
/*     */   {
/*  67 */     this(-1.0D);
/*     */   }
/*     */ 
/*     */   public ProgressBar(double paramDouble)
/*     */   {
/*  78 */     StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty(focusTraversableProperty());
/*  79 */     localStyleableProperty.set(this, Boolean.FALSE);
/*  80 */     setProgress(paramDouble);
/*  81 */     getStyleClass().setAll(new String[] { "progress-bar" });
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Boolean impl_cssGetFocusTraversableInitialValue()
/*     */   {
/* 107 */     return Boolean.FALSE;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ProgressBar
 * JD-Core Version:    0.6.2
 */