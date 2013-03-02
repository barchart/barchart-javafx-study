/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class RadioMenuItem extends MenuItem
/*     */   implements Toggle
/*     */ {
/*     */   private ObjectProperty<ToggleGroup> toggleGroup;
/*     */   private BooleanProperty selected;
/*     */   private static final String DEFAULT_STYLE_CLASS = "radio-menu-item";
/*     */   private static final String STYLE_CLASS_SELECTED = "selected";
/*     */ 
/*     */   private RadioMenuItem()
/*     */   {
/* 104 */     this(null, null);
/*     */   }
/*     */ 
/*     */   public RadioMenuItem(String paramString)
/*     */   {
/* 111 */     this(paramString, null);
/*     */   }
/*     */ 
/*     */   public RadioMenuItem(String paramString, Node paramNode)
/*     */   {
/* 119 */     super(paramString, paramNode);
/* 120 */     getStyleClass().add("radio-menu-item");
/*     */   }
/*     */ 
/*     */   public final void setToggleGroup(ToggleGroup paramToggleGroup)
/*     */   {
/* 134 */     toggleGroupProperty().set(paramToggleGroup);
/*     */   }
/*     */ 
/*     */   public final ToggleGroup getToggleGroup() {
/* 138 */     return this.toggleGroup == null ? null : (ToggleGroup)this.toggleGroup.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<ToggleGroup> toggleGroupProperty() {
/* 142 */     if (this.toggleGroup == null) {
/* 143 */       this.toggleGroup = new ObjectPropertyBase() {
/*     */         private ToggleGroup old;
/*     */ 
/* 146 */         protected void invalidated() { if (this.old != null) {
/* 147 */             this.old.getToggles().remove(RadioMenuItem.this);
/*     */           }
/* 149 */           this.old = ((ToggleGroup)get());
/* 150 */           if (get() != null)
/* 151 */             ((ToggleGroup)get()).getToggles().add(RadioMenuItem.this);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 157 */           return RadioMenuItem.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 162 */           return "toggleGroup";
/*     */         }
/*     */       };
/*     */     }
/* 166 */     return this.toggleGroup;
/*     */   }
/*     */ 
/*     */   public final void setSelected(boolean paramBoolean)
/*     */   {
/* 173 */     selectedProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isSelected() {
/* 177 */     return this.selected == null ? false : this.selected.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty selectedProperty() {
/* 181 */     if (this.selected == null) {
/* 182 */       this.selected = new BooleanPropertyBase() {
/*     */         protected void invalidated() {
/* 184 */           if (RadioMenuItem.this.getToggleGroup() != null) {
/* 185 */             if (get())
/* 186 */               RadioMenuItem.this.getToggleGroup().selectToggle(RadioMenuItem.this);
/* 187 */             else if (RadioMenuItem.this.getToggleGroup().getSelectedToggle() == RadioMenuItem.this) {
/* 188 */               RadioMenuItem.this.getToggleGroup().selectToggle(null);
/*     */             }
/*     */           }
/*     */ 
/* 192 */           if (RadioMenuItem.this.isSelected())
/* 193 */             RadioMenuItem.this.getStyleClass().add("selected");
/*     */           else
/* 195 */             RadioMenuItem.this.getStyleClass().remove("selected");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 201 */           return RadioMenuItem.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 206 */           return "selected";
/*     */         }
/*     */       };
/*     */     }
/* 210 */     return this.selected;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.RadioMenuItem
 * JD-Core Version:    0.6.2
 */