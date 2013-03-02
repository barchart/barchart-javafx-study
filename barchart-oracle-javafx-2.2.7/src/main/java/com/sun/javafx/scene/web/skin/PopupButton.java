/*    */ package com.sun.javafx.scene.web.skin;
/*    */ 
/*    */ import com.sun.javafx.css.StyleManager;
/*    */ import javafx.beans.property.ObjectProperty;
/*    */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*    */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*    */ import javafx.beans.property.SimpleObjectProperty;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.control.Labeled;
/*    */ 
/*    */ public class PopupButton extends Labeled
/*    */ {
/* 27 */   private ReadOnlyBooleanWrapper showing = new ReadOnlyBooleanWrapper() {
/*    */     protected void invalidated() {
/* 29 */       PopupButton.this.impl_pseudoClassStateChanged("showing");
/*    */     }
/*    */ 
/*    */     public Object getBean()
/*    */     {
/* 34 */       return PopupButton.this;
/*    */     }
/*    */ 
/*    */     public String getName()
/*    */     {
/* 39 */       return "showing";
/*    */     }
/* 27 */   };
/*    */ 
/* 45 */   private ObjectProperty<Node> content = new SimpleObjectProperty(this, "content");
/*    */ 
/* 65 */   private final long OPENVERTICALLY_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("openvertically");
/*    */ 
/*    */   public PopupButton()
/*    */   {
/* 24 */     getStyleClass().setAll(new String[] { "menu-button", "popup-button" });
/*    */   }
/*    */ 
/*    */   public final boolean isShowing()
/*    */   {
/* 42 */     return this.showing.get(); } 
/* 43 */   public final ReadOnlyBooleanProperty showingProperty() { return this.showing.getReadOnlyProperty(); }
/*    */ 
/*    */   public final void setContent(Node paramNode) {
/* 46 */     this.content.set(paramNode); } 
/* 47 */   public final Node getContent() { return (Node)this.content.get(); } 
/* 48 */   public final ObjectProperty<Node> contentProperty() { return this.content; }
/*    */ 
/*    */   public void show()
/*    */   {
/* 52 */     if (!isDisabled()) this.showing.set(true); 
/*    */   }
/*    */ 
/*    */   public void hide()
/*    */   {
/* 56 */     this.showing.set(false);
/*    */   }
/*    */ 
/*    */   public long impl_getPseudoClassState()
/*    */   {
/* 69 */     return super.impl_getPseudoClassState() | this.OPENVERTICALLY_PSEUDOCLASS_STATE;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.web.skin.PopupButton
 * JD-Core Version:    0.6.2
 */