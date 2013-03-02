/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ import javafx.beans.property.BooleanPropertyBase;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ 
/*    */ public abstract class StyleableBooleanProperty extends BooleanPropertyBase
/*    */   implements Property<Boolean>
/*    */ {
/* 51 */   Stylesheet.Origin origin = null;
/*    */ 
/*    */   public StyleableBooleanProperty()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StyleableBooleanProperty(boolean paramBoolean)
/*    */   {
/* 48 */     super(paramBoolean);
/*    */   }
/*    */ 
/*    */   public Stylesheet.Origin getOrigin()
/*    */   {
/* 54 */     return this.origin;
/*    */   }
/*    */ 
/*    */   public void applyStyle(Stylesheet.Origin paramOrigin, Boolean paramBoolean)
/*    */   {
/* 59 */     set(paramBoolean.booleanValue());
/* 60 */     this.origin = paramOrigin;
/*    */   }
/*    */ 
/*    */   public void bind(ObservableValue<? extends Boolean> paramObservableValue)
/*    */   {
/* 65 */     super.bind(paramObservableValue);
/* 66 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ 
/*    */   public void set(boolean paramBoolean)
/*    */   {
/* 71 */     super.set(paramBoolean);
/* 72 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StyleableBooleanProperty
 * JD-Core Version:    0.6.2
 */