/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ import javafx.beans.property.IntegerPropertyBase;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ 
/*    */ public abstract class StyleableIntegerProperty extends IntegerPropertyBase
/*    */   implements Property<Integer>
/*    */ {
/* 51 */   Stylesheet.Origin origin = null;
/*    */ 
/*    */   public StyleableIntegerProperty()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StyleableIntegerProperty(int paramInt)
/*    */   {
/* 48 */     super(paramInt);
/*    */   }
/*    */ 
/*    */   public Stylesheet.Origin getOrigin()
/*    */   {
/* 54 */     return this.origin;
/*    */   }
/*    */ 
/*    */   public void applyStyle(Stylesheet.Origin paramOrigin, Integer paramInteger) {
/* 58 */     setValue(paramInteger);
/* 59 */     this.origin = paramOrigin;
/*    */   }
/*    */ 
/*    */   public void bind(ObservableValue<? extends Number> paramObservableValue)
/*    */   {
/* 64 */     super.bind(paramObservableValue);
/* 65 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ 
/*    */   public void set(int paramInt)
/*    */   {
/* 70 */     super.set(paramInt);
/* 71 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StyleableIntegerProperty
 * JD-Core Version:    0.6.2
 */