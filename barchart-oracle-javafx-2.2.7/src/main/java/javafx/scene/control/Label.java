/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class Label extends Labeled
/*     */ {
/*  98 */   private ChangeListener<Boolean> mnemonicStateListener = new ChangeListener()
/*     */   {
/*     */     public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 101 */       Label.this.impl_showMnemonicsProperty().setValue(paramAnonymousBoolean2);
/*     */     }
/*  98 */   };
/*     */   private ObjectProperty<Node> labelFor;
/*     */ 
/*     */   public Label()
/*     */   {
/*  60 */     initialize();
/*     */   }
/*     */ 
/*     */   public Label(String paramString)
/*     */   {
/*  68 */     super(paramString);
/*  69 */     initialize();
/*     */   }
/*     */ 
/*     */   public Label(String paramString, Node paramNode)
/*     */   {
/*  78 */     super(paramString, paramNode);
/*  79 */     initialize();
/*     */   }
/*     */ 
/*     */   private void initialize() {
/*  83 */     getStyleClass().setAll(new String[] { "label" });
/*     */ 
/*  89 */     StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty(focusTraversableProperty());
/*  90 */     localStyleableProperty.set(this, Boolean.FALSE);
/*     */   }
/*     */ 
/*     */   public ObjectProperty<Node> labelForProperty()
/*     */   {
/* 112 */     if (this.labelFor == null) {
/* 113 */       this.labelFor = new ObjectPropertyBase() {
/* 114 */         Node oldValue = null;
/*     */ 
/* 116 */         protected void invalidated() { if (this.oldValue != null) {
/* 117 */             this.oldValue.impl_showMnemonicsProperty().removeListener(Label.this.mnemonicStateListener);
/*     */           }
/* 119 */           Node localNode = (Node)get();
/* 120 */           if (localNode != null) {
/* 121 */             localNode.impl_showMnemonicsProperty().addListener(Label.this.mnemonicStateListener);
/* 122 */             Label.this.impl_setShowMnemonics(localNode.impl_isShowMnemonics());
/*     */           } else {
/* 124 */             Label.this.impl_setShowMnemonics(false);
/*     */           }
/* 126 */           this.oldValue = localNode; }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 130 */           return Label.this;
/*     */         }
/*     */ 
/*     */         public String getName() {
/* 134 */           return "labelFor";
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/* 139 */     return this.labelFor;
/*     */   }
/*     */ 
/*     */   public final void setLabelFor(Node paramNode) {
/* 143 */     labelForProperty().setValue(paramNode); } 
/* 144 */   public final Node getLabelFor() { return this.labelFor == null ? null : (Node)this.labelFor.getValue(); }
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected Boolean impl_cssGetFocusTraversableInitialValue()
/*     */   {
/* 161 */     return Boolean.FALSE;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.Label
 * JD-Core Version:    0.6.2
 */