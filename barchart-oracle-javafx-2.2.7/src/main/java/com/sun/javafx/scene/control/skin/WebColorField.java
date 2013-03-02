/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import javafx.beans.property.ObjectProperty;
/*    */ import javafx.beans.property.SimpleObjectProperty;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.paint.Color;
/*    */ 
/*    */ class WebColorField extends InputField
/*    */ {
/* 16 */   private ObjectProperty<Color> value = new SimpleObjectProperty(this, "value");
/*    */ 
/* 17 */   public final Color getValue() { return (Color)this.value.get(); } 
/* 18 */   public final void setValue(Color paramColor) { this.value.set(paramColor); } 
/* 19 */   public final ObjectProperty<Color> valueProperty() { return this.value; }
/*    */ 
/*    */ 
/*    */   public WebColorField()
/*    */   {
/* 25 */     getStyleClass().setAll(new String[] { "webcolor-field" });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.WebColorField
 * JD-Core Version:    0.6.2
 */