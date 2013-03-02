/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ import javafx.beans.property.ObjectPropertyBase;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ 
/*    */ public abstract class StyleableObjectProperty<T> extends ObjectPropertyBase<T>
/*    */   implements Property<T>
/*    */ {
/* 51 */   Stylesheet.Origin origin = null;
/*    */ 
/*    */   public StyleableObjectProperty()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StyleableObjectProperty(T paramT)
/*    */   {
/* 48 */     super(paramT);
/*    */   }
/*    */ 
/*    */   public Stylesheet.Origin getOrigin()
/*    */   {
/* 54 */     return this.origin;
/*    */   }
/*    */ 
/*    */   public void applyStyle(Stylesheet.Origin paramOrigin, T paramT) {
/* 58 */     set(paramT);
/* 59 */     this.origin = paramOrigin;
/*    */   }
/*    */ 
/*    */   public void bind(ObservableValue<? extends T> paramObservableValue)
/*    */   {
/* 64 */     super.bind(paramObservableValue);
/* 65 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ 
/*    */   public void set(T paramT)
/*    */   {
/* 70 */     super.set(paramT);
/* 71 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StyleableObjectProperty
 * JD-Core Version:    0.6.2
 */