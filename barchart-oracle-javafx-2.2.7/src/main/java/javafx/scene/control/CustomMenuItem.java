/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class CustomMenuItem extends MenuItem
/*     */ {
/*     */   private ObjectProperty<Node> content;
/*     */   private BooleanProperty hideOnClick;
/*     */   private static final String DEFAULT_STYLE_CLASS = "custom-menu-item";
/*     */ 
/*     */   public CustomMenuItem()
/*     */   {
/*  79 */     this(null, true);
/*     */   }
/*     */ 
/*     */   public CustomMenuItem(Node paramNode)
/*     */   {
/*  87 */     this(paramNode, true);
/*     */   }
/*     */ 
/*     */   public CustomMenuItem(Node paramNode, boolean paramBoolean)
/*     */   {
/*  96 */     getStyleClass().add("custom-menu-item");
/*     */ 
/*  98 */     setContent(paramNode);
/*  99 */     setHideOnClick(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final void setContent(Node paramNode)
/*     */   {
/* 116 */     contentProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getContent() {
/* 120 */     return this.content == null ? null : (Node)this.content.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> contentProperty() {
/* 124 */     if (this.content == null) {
/* 125 */       this.content = new SimpleObjectProperty(this, "content");
/*     */     }
/* 127 */     return this.content;
/*     */   }
/*     */ 
/*     */   public final void setHideOnClick(boolean paramBoolean)
/*     */   {
/* 140 */     hideOnClickProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isHideOnClick() {
/* 144 */     return this.hideOnClick == null ? true : this.hideOnClick.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty hideOnClickProperty() {
/* 148 */     if (this.hideOnClick == null) {
/* 149 */       this.hideOnClick = new SimpleBooleanProperty(this, "hideOnClick", true);
/*     */     }
/* 151 */     return this.hideOnClick;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.CustomMenuItem
 * JD-Core Version:    0.6.2
 */