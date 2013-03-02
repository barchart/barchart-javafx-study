/*     */ package javafx.scene;
/*     */ 
/*     */ import javafx.beans.DefaultProperty;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Bounds;
/*     */ 
/*     */ @DefaultProperty("children")
/*     */ public class Group extends Parent
/*     */ {
/*     */   private BooleanProperty autoSizeChildren;
/*     */ 
/*     */   public Group()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Group(Node[] paramArrayOfNode)
/*     */   {
/*  87 */     getChildren().addAll(paramArrayOfNode);
/*     */   }
/*     */ 
/*     */   public final void setAutoSizeChildren(boolean paramBoolean)
/*     */   {
/* 105 */     autoSizeChildrenProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isAutoSizeChildren() {
/* 109 */     return this.autoSizeChildren == null ? true : this.autoSizeChildren.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty autoSizeChildrenProperty() {
/* 113 */     if (this.autoSizeChildren == null) {
/* 114 */       this.autoSizeChildren = new BooleanPropertyBase(true)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 118 */           Group.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 123 */           return Group.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 128 */           return "autoSizeChildren";
/*     */         }
/*     */       };
/*     */     }
/* 132 */     return this.autoSizeChildren;
/*     */   }
/*     */ 
/*     */   public ObservableList<Node> getChildren()
/*     */   {
/* 140 */     return super.getChildren();
/*     */   }
/*     */ 
/*     */   public double prefWidth(double paramDouble)
/*     */   {
/* 151 */     return getLayoutBounds().getWidth();
/*     */   }
/*     */ 
/*     */   public double prefHeight(double paramDouble) {
/* 155 */     return getLayoutBounds().getHeight();
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 159 */     if (isAutoSizeChildren())
/* 160 */       super.layoutChildren();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.Group
 * JD-Core Version:    0.6.2
 */