/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ import javafx.beans.property.LongPropertyBase;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ 
/*    */ public abstract class StyleableLongProperty extends LongPropertyBase
/*    */   implements Property<Long>
/*    */ {
/* 51 */   Stylesheet.Origin origin = null;
/*    */ 
/*    */   public StyleableLongProperty()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StyleableLongProperty(long paramLong)
/*    */   {
/* 48 */     super(paramLong);
/*    */   }
/*    */ 
/*    */   public Stylesheet.Origin getOrigin()
/*    */   {
/* 54 */     return this.origin;
/*    */   }
/*    */ 
/*    */   public void applyStyle(Stylesheet.Origin paramOrigin, Long paramLong) {
/* 58 */     setValue(paramLong);
/* 59 */     this.origin = paramOrigin;
/*    */   }
/*    */ 
/*    */   public void bind(ObservableValue<? extends Number> paramObservableValue)
/*    */   {
/* 64 */     super.bind(paramObservableValue);
/* 65 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ 
/*    */   public void set(long paramLong)
/*    */   {
/* 70 */     super.set(paramLong);
/* 71 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StyleableLongProperty
 * JD-Core Version:    0.6.2
 */