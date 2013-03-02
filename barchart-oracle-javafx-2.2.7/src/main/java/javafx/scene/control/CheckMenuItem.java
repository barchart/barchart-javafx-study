/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class CheckMenuItem extends MenuItem
/*     */ {
/*     */   private BooleanProperty selected;
/*     */   private static final String DEFAULT_STYLE_CLASS = "check-menu-item";
/*     */   private static final String STYLE_CLASS_SELECTED = "selected";
/*     */ 
/*     */   public CheckMenuItem()
/*     */   {
/*  96 */     this(null, null);
/*     */   }
/*     */ 
/*     */   public CheckMenuItem(String paramString)
/*     */   {
/* 103 */     this(paramString, null);
/*     */   }
/*     */ 
/*     */   public CheckMenuItem(String paramString, Node paramNode)
/*     */   {
/* 111 */     super(paramString, paramNode);
/* 112 */     getStyleClass().add("check-menu-item");
/*     */   }
/*     */ 
/*     */   public final void setSelected(boolean paramBoolean)
/*     */   {
/* 131 */     selectedProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isSelected() {
/* 135 */     return this.selected == null ? false : this.selected.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty selectedProperty() {
/* 139 */     if (this.selected == null) {
/* 140 */       this.selected = new BooleanPropertyBase()
/*     */       {
/*     */         protected void invalidated() {
/* 143 */           get();
/*     */ 
/* 146 */           if (CheckMenuItem.this.isSelected())
/* 147 */             CheckMenuItem.this.getStyleClass().add("selected");
/*     */           else
/* 149 */             CheckMenuItem.this.getStyleClass().remove("selected");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 155 */           return CheckMenuItem.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 160 */           return "selected";
/*     */         }
/*     */       };
/*     */     }
/* 164 */     return this.selected;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.CheckMenuItem
 * JD-Core Version:    0.6.2
 */