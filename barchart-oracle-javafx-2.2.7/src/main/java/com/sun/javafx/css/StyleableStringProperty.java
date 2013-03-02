/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ import javafx.beans.property.StringPropertyBase;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ 
/*    */ public abstract class StyleableStringProperty extends StringPropertyBase
/*    */   implements Property<String>
/*    */ {
/* 51 */   Stylesheet.Origin origin = null;
/*    */ 
/*    */   public StyleableStringProperty()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StyleableStringProperty(String paramString)
/*    */   {
/* 48 */     super(paramString);
/*    */   }
/*    */ 
/*    */   public Stylesheet.Origin getOrigin()
/*    */   {
/* 54 */     return this.origin;
/*    */   }
/*    */ 
/*    */   public void applyStyle(Stylesheet.Origin paramOrigin, String paramString)
/*    */   {
/* 59 */     set(paramString);
/* 60 */     this.origin = paramOrigin;
/*    */   }
/*    */ 
/*    */   public void bind(ObservableValue<? extends String> paramObservableValue)
/*    */   {
/* 65 */     super.bind(paramObservableValue);
/* 66 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ 
/*    */   public void set(String paramString)
/*    */   {
/* 71 */     super.set(paramString);
/* 72 */     this.origin = Stylesheet.Origin.USER;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StyleableStringProperty
 * JD-Core Version:    0.6.2
 */