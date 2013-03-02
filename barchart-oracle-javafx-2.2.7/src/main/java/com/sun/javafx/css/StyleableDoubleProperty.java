/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ import javafx.beans.property.DoublePropertyBase;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ 
/*    */ public abstract class StyleableDoubleProperty extends DoublePropertyBase
/*    */   implements Property<Double>
/*    */ {
/* 51 */   Stylesheet.Origin origin = null;
/*    */ 
/*    */   public StyleableDoubleProperty()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StyleableDoubleProperty(double paramDouble)
/*    */   {
/* 48 */     super(paramDouble);
/*    */   }
/*    */ 
/*    */   public Stylesheet.Origin getOrigin()
/*    */   {
/* 54 */     return this.origin;
/*    */   }
/*    */ 
/*    */   public void applyStyle(Stylesheet.Origin paramOrigin, Double paramDouble) {
/* 58 */     setValue(paramDouble);
/* 59 */     this.origin = paramOrigin;
/*    */   }
/*    */ 
/*    */   public void bind(ObservableValue<? extends Number> paramObservableValue)
/*    */   {
/* 64 */     super.bind(paramObservableValue);
/* 65 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ 
/*    */   public void set(double paramDouble)
/*    */   {
/* 70 */     super.set(paramDouble);
/* 71 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StyleableDoubleProperty
 * JD-Core Version:    0.6.2
 */