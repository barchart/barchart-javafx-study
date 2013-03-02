/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class Button extends ButtonBase
/*     */ {
/*     */   private BooleanProperty defaultButton;
/*     */   private BooleanProperty cancelButton;
/*     */   private static final String DEFAULT_STYLE_CLASS = "button";
/*     */   private static final String PSEUDO_CLASS_DEFAULT = "default";
/*     */   private static final String PSEUDO_CLASS_CANCEL = "cancel";
/* 198 */   private static final long PSEUDO_CLASS_DEFAULT_MASK = StyleManager.getInstance().getPseudoclassMask("default");
/*     */ 
/* 200 */   private static final long PSEUDO_CLASS_CANCEL_MASK = StyleManager.getInstance().getPseudoclassMask("cancel");
/*     */ 
/*     */   public Button()
/*     */   {
/*  68 */     initialize();
/*     */   }
/*     */ 
/*     */   public Button(String paramString)
/*     */   {
/*  77 */     super(paramString);
/*  78 */     initialize();
/*     */   }
/*     */ 
/*     */   public Button(String paramString, Node paramNode)
/*     */   {
/*  88 */     super(paramString, paramNode);
/*  89 */     initialize();
/*     */   }
/*     */ 
/*     */   private void initialize() {
/*  93 */     getStyleClass().setAll(new String[] { "button" });
/*  94 */     setMnemonicParsing(true);
/*     */   }
/*     */ 
/*     */   public final void setDefaultButton(boolean paramBoolean)
/*     */   {
/* 109 */     defaultButtonProperty().set(paramBoolean);
/*     */   }
/*     */   public final boolean isDefaultButton() {
/* 112 */     return this.defaultButton == null ? false : this.defaultButton.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty defaultButtonProperty() {
/* 116 */     if (this.defaultButton == null) {
/* 117 */       this.defaultButton = new BooleanPropertyBase(false) {
/*     */         protected void invalidated() {
/* 119 */           Button.this.impl_pseudoClassStateChanged("default");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 124 */           return Button.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 129 */           return "defaultButton";
/*     */         }
/*     */       };
/*     */     }
/* 133 */     return this.defaultButton;
/*     */   }
/*     */ 
/*     */   public final void setCancelButton(boolean paramBoolean)
/*     */   {
/* 143 */     cancelButtonProperty().set(paramBoolean);
/*     */   }
/*     */   public final boolean isCancelButton() {
/* 146 */     return this.cancelButton == null ? false : this.cancelButton.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty cancelButtonProperty() {
/* 150 */     if (this.cancelButton == null) {
/* 151 */       this.cancelButton = new BooleanPropertyBase(false) {
/*     */         protected void invalidated() {
/* 153 */           Button.this.impl_pseudoClassStateChanged("cancel");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 158 */           return Button.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 163 */           return "cancelButton";
/*     */         }
/*     */       };
/*     */     }
/* 167 */     return this.cancelButton;
/*     */   }
/*     */ 
/*     */   public void fire()
/*     */   {
/* 179 */     fireEvent(new ActionEvent());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 209 */     long l = super.impl_getPseudoClassState();
/* 210 */     if (isDefaultButton()) l |= PSEUDO_CLASS_DEFAULT_MASK;
/* 211 */     if (isCancelButton()) l |= PSEUDO_CLASS_CANCEL_MASK;
/* 212 */     return l;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.Button
 * JD-Core Version:    0.6.2
 */