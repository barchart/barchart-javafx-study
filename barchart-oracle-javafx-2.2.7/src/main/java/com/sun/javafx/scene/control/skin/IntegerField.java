/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import javafx.beans.property.IntegerProperty;
/*    */ import javafx.beans.property.SimpleIntegerProperty;
/*    */ import javafx.collections.ObservableList;
/*    */ 
/*    */ class IntegerField extends InputField
/*    */ {
/* 14 */   private IntegerProperty value = new SimpleIntegerProperty(this, "value");
/*    */ 
/* 19 */   private IntegerProperty maxValue = new SimpleIntegerProperty(this, "maxValue", -1);
/*    */ 
/*    */   public final int getValue()
/*    */   {
/* 15 */     return this.value.get(); } 
/* 16 */   public final void setValue(int paramInt) { this.value.set(paramInt); } 
/* 17 */   public final IntegerProperty valueProperty() { return this.value; }
/*    */ 
/*    */   public final int getMaxValue() {
/* 20 */     return this.maxValue.get(); } 
/* 21 */   public final void setMaxValue(int paramInt) { this.maxValue.set(paramInt); } 
/* 22 */   public final IntegerProperty maxValueProperty() { return this.maxValue; }
/*    */ 
/*    */ 
/*    */   public IntegerField()
/*    */   {
/* 27 */     this(-1);
/*    */   }
/*    */   public IntegerField(int paramInt) {
/* 30 */     getStyleClass().setAll(new String[] { "integer-field" });
/* 31 */     setMaxValue(paramInt);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.IntegerField
 * JD-Core Version:    0.6.2
 */