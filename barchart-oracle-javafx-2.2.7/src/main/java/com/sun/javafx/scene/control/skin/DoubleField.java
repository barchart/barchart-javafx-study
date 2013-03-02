/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import javafx.beans.property.DoubleProperty;
/*    */ import javafx.beans.property.SimpleDoubleProperty;
/*    */ import javafx.collections.ObservableList;
/*    */ 
/*    */ class DoubleField extends InputField
/*    */ {
/* 15 */   private DoubleProperty value = new SimpleDoubleProperty(this, "value");
/*    */ 
/* 16 */   public final double getValue() { return this.value.get(); } 
/* 17 */   public final void setValue(double paramDouble) { this.value.set(paramDouble); } 
/* 18 */   public final DoubleProperty valueProperty() { return this.value; }
/*    */ 
/*    */ 
/*    */   public DoubleField()
/*    */   {
/* 24 */     getStyleClass().setAll(new String[] { "double-field" });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.DoubleField
 * JD-Core Version:    0.6.2
 */