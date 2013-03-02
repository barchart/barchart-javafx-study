/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class ProgressIndicator extends Control
/*     */ {
/*     */   public static final double INDETERMINATE_PROGRESS = -1.0D;
/*     */   private ReadOnlyBooleanWrapper indeterminate;
/*     */   private DoubleProperty progress;
/*     */   private static final String DEFAULT_STYLE_CLASS = "progress-indicator";
/*     */   private static final String PSEUDO_CLASS_DETERMINATE = "determinate";
/*     */   private static final String PSEUDO_CLASS_INDETERMINATE = "indeterminate";
/* 211 */   private static final long INDETERMINATE_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("indeterminate");
/* 212 */   private static final long DETERMINATE_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("determinate");
/*     */ 
/*     */   public ProgressIndicator()
/*     */   {
/*  81 */     this(-1.0D);
/*     */   }
/*     */ 
/*     */   public ProgressIndicator(double paramDouble)
/*     */   {
/*  92 */     StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty(focusTraversableProperty());
/*  93 */     localStyleableProperty.set(this, Boolean.FALSE);
/*  94 */     setProgress(paramDouble);
/*  95 */     getStyleClass().setAll(new String[] { "progress-indicator" });
/*     */   }
/*     */ 
/*     */   private void setIndeterminate(boolean paramBoolean)
/*     */   {
/* 110 */     indeterminatePropertyImpl().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isIndeterminate() {
/* 114 */     return this.indeterminate == null ? true : this.indeterminate.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyBooleanProperty indeterminateProperty() {
/* 118 */     return indeterminatePropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyBooleanWrapper indeterminatePropertyImpl() {
/* 122 */     if (this.indeterminate == null) {
/* 123 */       this.indeterminate = new ReadOnlyBooleanWrapper(true) {
/*     */         protected void invalidated() {
/* 125 */           ProgressIndicator.this.impl_pseudoClassStateChanged("indeterminate");
/* 126 */           ProgressIndicator.this.impl_pseudoClassStateChanged("determinate");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 131 */           return ProgressIndicator.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 136 */           return "indeterminate";
/*     */         }
/*     */       };
/*     */     }
/* 140 */     return this.indeterminate;
/*     */   }
/*     */ 
/*     */   public final void setProgress(double paramDouble)
/*     */   {
/* 150 */     progressProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getProgress() {
/* 154 */     return this.progress == null ? -1.0D : this.progress.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty progressProperty() {
/* 158 */     if (this.progress == null) {
/* 159 */       this.progress = new DoublePropertyBase(-1.0D) {
/*     */         protected void invalidated() {
/* 161 */           ProgressIndicator.this.setIndeterminate(ProgressIndicator.this.getProgress() < 0.0D);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 166 */           return ProgressIndicator.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 171 */           return "progress";
/*     */         }
/*     */       };
/*     */     }
/* 175 */     return this.progress;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 219 */     long l = super.impl_getPseudoClassState();
/* 220 */     l |= (isIndeterminate() ? INDETERMINATE_PSEUDOCLASS_STATE : DETERMINATE_PSEUDOCLASS_STATE);
/* 221 */     return l;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Boolean impl_cssGetFocusTraversableInitialValue()
/*     */   {
/* 234 */     return Boolean.FALSE;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ProgressIndicator
 * JD-Core Version:    0.6.2
 */