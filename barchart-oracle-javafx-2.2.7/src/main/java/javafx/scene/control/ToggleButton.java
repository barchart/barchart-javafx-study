/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class ToggleButton extends ButtonBase
/*     */   implements Toggle
/*     */ {
/*     */   private BooleanProperty selected;
/*     */   private ObjectProperty<ToggleGroup> toggleGroup;
/*     */   private static final String DEFAULT_STYLE_CLASS = "toggle-button";
/*     */   private static final String PSEUDO_CLASS_SELECTED = "selected";
/* 246 */   private static final long SELECTED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("selected");
/*     */ 
/*     */   public ToggleButton()
/*     */   {
/*  98 */     initialize();
/*     */   }
/*     */ 
/*     */   public ToggleButton(String paramString)
/*     */   {
/* 107 */     setText(paramString);
/* 108 */     initialize();
/*     */   }
/*     */ 
/*     */   public ToggleButton(String paramString, Node paramNode)
/*     */   {
/* 118 */     setText(paramString);
/* 119 */     setGraphic(paramNode);
/* 120 */     initialize();
/*     */   }
/*     */ 
/*     */   private void initialize() {
/* 124 */     getStyleClass().setAll(new String[] { "toggle-button" });
/*     */ 
/* 129 */     StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty(alignmentProperty());
/* 130 */     localStyleableProperty.set(this, Pos.CENTER);
/* 131 */     setMnemonicParsing(true);
/*     */   }
/*     */ 
/*     */   public final void setSelected(boolean paramBoolean)
/*     */   {
/* 144 */     selectedProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isSelected() {
/* 148 */     return this.selected == null ? false : this.selected.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty selectedProperty() {
/* 152 */     if (this.selected == null) {
/* 153 */       this.selected = new BooleanPropertyBase() {
/*     */         protected void invalidated() {
/* 155 */           if (ToggleButton.this.getToggleGroup() != null) {
/* 156 */             if (get())
/* 157 */               ToggleButton.this.getToggleGroup().selectToggle(ToggleButton.this);
/* 158 */             else if (ToggleButton.this.getToggleGroup().getSelectedToggle() == ToggleButton.this) {
/* 159 */               ToggleButton.this.getToggleGroup().clearSelectedToggle();
/*     */             }
/*     */           }
/* 162 */           ToggleButton.this.impl_pseudoClassStateChanged("selected");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 167 */           return ToggleButton.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 172 */           return "selected";
/*     */         }
/*     */       };
/*     */     }
/* 176 */     return this.selected;
/*     */   }
/*     */ 
/*     */   public final void setToggleGroup(ToggleGroup paramToggleGroup)
/*     */   {
/* 186 */     toggleGroupProperty().set(paramToggleGroup);
/*     */   }
/*     */ 
/*     */   public final ToggleGroup getToggleGroup() {
/* 190 */     return this.toggleGroup == null ? null : (ToggleGroup)this.toggleGroup.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<ToggleGroup> toggleGroupProperty() {
/* 194 */     if (this.toggleGroup == null) {
/* 195 */       this.toggleGroup = new ObjectPropertyBase() {
/*     */         private ToggleGroup old;
/*     */ 
/* 198 */         protected void invalidated() { ToggleGroup localToggleGroup = (ToggleGroup)get();
/* 199 */           if ((localToggleGroup != null) && (!localToggleGroup.getToggles().contains(ToggleButton.this))) {
/* 200 */             if (this.old != null) {
/* 201 */               this.old.getToggles().remove(ToggleButton.this);
/*     */             }
/* 203 */             localToggleGroup.getToggles().add(ToggleButton.this);
/* 204 */           } else if (localToggleGroup == null) {
/* 205 */             this.old.getToggles().remove(ToggleButton.this);
/*     */           }
/* 207 */           this.old = localToggleGroup;
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 212 */           return ToggleButton.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 217 */           return "toggleGroup";
/*     */         }
/*     */       };
/*     */     }
/* 221 */     return this.toggleGroup;
/*     */   }
/*     */ 
/*     */   public void fire()
/*     */   {
/* 233 */     setSelected(!isSelected());
/* 234 */     fireEvent(new ActionEvent());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 253 */     long l = super.impl_getPseudoClassState();
/* 254 */     if (isSelected()) l |= SELECTED_PSEUDOCLASS_STATE;
/* 255 */     return l;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Pos impl_cssGetAlignmentInitialValue()
/*     */   {
/* 265 */     return Pos.CENTER;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ToggleButton
 * JD-Core Version:    0.6.2
 */