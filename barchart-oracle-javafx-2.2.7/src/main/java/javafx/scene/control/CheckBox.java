/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.geometry.Pos;
/*     */ 
/*     */ public class CheckBox extends ButtonBase
/*     */ {
/*     */   private BooleanProperty indeterminate;
/*     */   private BooleanProperty selected;
/*     */   private BooleanProperty allowIndeterminate;
/*     */   private static final String DEFAULT_STYLE_CLASS = "check-box";
/*     */   private static final String PSEUDO_CLASS_DETERMINATE = "determinate";
/*     */   private static final String PSEUDO_CLASS_INDETERMINATE = "indeterminate";
/*     */   private static final String PSEUDO_CLASS_SELECTED = "selected";
/* 240 */   private static final long SELECTED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("selected");
/* 241 */   private static final long INDETERMINATE_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("indeterminate");
/* 242 */   private static final long DETERMINATE_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("determinate");
/*     */ 
/*     */   public CheckBox()
/*     */   {
/*  84 */     initialize();
/*     */   }
/*     */ 
/*     */   public CheckBox(String paramString)
/*     */   {
/*  93 */     setText(paramString);
/*  94 */     initialize();
/*     */   }
/*     */ 
/*     */   private void initialize() {
/*  98 */     getStyleClass().setAll(new String[] { "check-box" });
/*  99 */     setAlignment(Pos.CENTER_LEFT);
/* 100 */     setMnemonicParsing(true);
/*     */   }
/*     */ 
/*     */   public final void setIndeterminate(boolean paramBoolean)
/*     */   {
/* 113 */     indeterminateProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isIndeterminate() {
/* 117 */     return this.indeterminate == null ? false : this.indeterminate.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty indeterminateProperty() {
/* 121 */     if (this.indeterminate == null) {
/* 122 */       this.indeterminate = new BooleanPropertyBase(false) {
/*     */         protected void invalidated() {
/* 124 */           CheckBox.this.impl_pseudoClassStateChanged("determinate");
/* 125 */           CheckBox.this.impl_pseudoClassStateChanged("indeterminate");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 130 */           return CheckBox.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 135 */           return "indeterminate";
/*     */         }
/*     */       };
/*     */     }
/* 139 */     return this.indeterminate;
/*     */   }
/*     */ 
/*     */   public final void setSelected(boolean paramBoolean)
/*     */   {
/* 146 */     selectedProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isSelected() {
/* 150 */     return this.selected == null ? false : this.selected.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty selectedProperty() {
/* 154 */     if (this.selected == null) {
/* 155 */       this.selected = new BooleanPropertyBase() {
/*     */         protected void invalidated() {
/* 157 */           CheckBox.this.impl_pseudoClassStateChanged("selected");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 162 */           return CheckBox.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 167 */           return "selected";
/*     */         }
/*     */       };
/*     */     }
/* 171 */     return this.selected;
/*     */   }
/*     */ 
/*     */   public final void setAllowIndeterminate(boolean paramBoolean)
/*     */   {
/* 183 */     allowIndeterminateProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isAllowIndeterminate() {
/* 187 */     return this.allowIndeterminate == null ? false : this.allowIndeterminate.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty allowIndeterminateProperty() {
/* 191 */     if (this.allowIndeterminate == null) {
/* 192 */       this.allowIndeterminate = new SimpleBooleanProperty(this, "allowIndeterminate");
/*     */     }
/*     */ 
/* 195 */     return this.allowIndeterminate;
/*     */   }
/*     */ 
/*     */   public void fire()
/*     */   {
/* 213 */     if (isAllowIndeterminate()) {
/* 214 */       if ((!isSelected()) && (!isIndeterminate())) {
/* 215 */         setIndeterminate(true);
/* 216 */       } else if ((isSelected()) && (!isIndeterminate())) {
/* 217 */         setSelected(false);
/* 218 */       } else if (isIndeterminate()) {
/* 219 */         setSelected(true);
/* 220 */         setIndeterminate(false);
/*     */       }
/*     */     } else {
/* 223 */       setSelected(!isSelected());
/* 224 */       setIndeterminate(false);
/*     */     }
/* 226 */     fireEvent(new ActionEvent());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 249 */     long l = super.impl_getPseudoClassState();
/* 250 */     if (isSelected()) l |= SELECTED_PSEUDOCLASS_STATE;
/* 251 */     l |= (isIndeterminate() ? INDETERMINATE_PSEUDOCLASS_STATE : DETERMINATE_PSEUDOCLASS_STATE);
/* 252 */     return l;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.CheckBox
 * JD-Core Version:    0.6.2
 */