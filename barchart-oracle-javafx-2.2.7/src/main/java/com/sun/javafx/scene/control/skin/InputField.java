/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import javafx.beans.property.BooleanProperty;
/*    */ import javafx.beans.property.IntegerProperty;
/*    */ import javafx.beans.property.IntegerPropertyBase;
/*    */ import javafx.beans.property.ObjectProperty;
/*    */ import javafx.beans.property.ObjectPropertyBase;
/*    */ import javafx.beans.property.SimpleBooleanProperty;
/*    */ import javafx.beans.property.StringProperty;
/*    */ import javafx.beans.property.StringPropertyBase;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.scene.control.Control;
/*    */ 
/*    */ class InputField extends Control
/*    */ {
/*    */   public static final int DEFAULT_PREF_COLUMN_COUNT = 12;
/* 22 */   private BooleanProperty editable = new SimpleBooleanProperty(this, "editable", true);
/*    */ 
/* 31 */   private StringProperty promptText = new StringPropertyBase("")
/*    */   {
/*    */     protected void invalidated() {
/* 34 */       String str = get();
/* 35 */       if ((str != null) && (str.contains("\n"))) {
/* 36 */         str = str.replace("\n", "");
/* 37 */         set(str);
/*    */       }
/*    */     }
/*    */ 
/* 41 */     public Object getBean() { return InputField.this; } 
/* 42 */     public String getName() { return "promptText"; }
/*    */ 
/* 31 */   };
/*    */ 
/* 53 */   private IntegerProperty prefColumnCount = new IntegerPropertyBase(12) {
/*    */     public void set(int paramAnonymousInt) {
/* 55 */       if (paramAnonymousInt < 0) {
/* 56 */         throw new IllegalArgumentException("value cannot be negative.");
/*    */       }
/*    */ 
/* 59 */       super.set(paramAnonymousInt);
/*    */     }
/*    */     public Object getBean() {
/* 62 */       return InputField.this; } 
/* 63 */     public String getName() { return "prefColumnCount"; }
/*    */ 
/* 53 */   };
/*    */ 
/* 75 */   private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase() {
/*    */     protected void invalidated() {
/* 77 */       InputField.this.setEventHandler(ActionEvent.ACTION, (EventHandler)get());
/*    */     }
/*    */     public Object getBean() {
/* 80 */       return InputField.this; } 
/* 81 */     public String getName() { return "onAction"; }
/*    */ 
/* 75 */   };
/*    */ 
/*    */   public final boolean isEditable()
/*    */   {
/* 23 */     return this.editable.getValue().booleanValue(); } 
/* 24 */   public final void setEditable(boolean paramBoolean) { this.editable.setValue(Boolean.valueOf(paramBoolean)); } 
/* 25 */   public final BooleanProperty editableProperty() { return this.editable; }
/*    */ 
/*    */ 
/*    */   public final StringProperty promptTextProperty()
/*    */   {
/* 44 */     return this.promptText; } 
/* 45 */   public final String getPromptText() { return (String)this.promptText.get(); } 
/* 46 */   public final void setPromptText(String paramString) { this.promptText.set(paramString); }
/*    */ 
/*    */ 
/*    */   public final IntegerProperty prefColumnCountProperty()
/*    */   {
/* 65 */     return this.prefColumnCount; } 
/* 66 */   public final int getPrefColumnCount() { return this.prefColumnCount.getValue().intValue(); } 
/* 67 */   public final void setPrefColumnCount(int paramInt) { this.prefColumnCount.setValue(Integer.valueOf(paramInt)); }
/*    */ 
/*    */ 
/*    */   public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty()
/*    */   {
/* 83 */     return this.onAction; } 
/* 84 */   public final EventHandler<ActionEvent> getOnAction() { return (EventHandler)onActionProperty().get(); } 
/* 85 */   public final void setOnAction(EventHandler<ActionEvent> paramEventHandler) { onActionProperty().set(paramEventHandler); }
/*    */ 
/*    */ 
/*    */   public InputField()
/*    */   {
/* 91 */     getStyleClass().setAll(new String[] { "input-field" });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.InputField
 * JD-Core Version:    0.6.2
 */