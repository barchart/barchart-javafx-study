/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ import javafx.beans.property.FloatPropertyBase;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ 
/*    */ public abstract class StyleableFloatProperty extends FloatPropertyBase
/*    */   implements Property<Float>
/*    */ {
/* 51 */   Stylesheet.Origin origin = null;
/*    */ 
/*    */   public StyleableFloatProperty()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StyleableFloatProperty(float paramFloat)
/*    */   {
/* 48 */     super(paramFloat);
/*    */   }
/*    */ 
/*    */   public Stylesheet.Origin getOrigin()
/*    */   {
/* 54 */     return this.origin;
/*    */   }
/*    */ 
/*    */   public void applyStyle(Stylesheet.Origin paramOrigin, Float paramFloat) {
/* 58 */     setValue(paramFloat);
/* 59 */     this.origin = paramOrigin;
/*    */   }
/*    */ 
/*    */   public void bind(ObservableValue<? extends Number> paramObservableValue)
/*    */   {
/* 64 */     super.bind(paramObservableValue);
/* 65 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ 
/*    */   public void set(float paramFloat)
/*    */   {
/* 70 */     super.set(paramFloat);
/* 71 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StyleableFloatProperty
 * JD-Core Version:    0.6.2
 */