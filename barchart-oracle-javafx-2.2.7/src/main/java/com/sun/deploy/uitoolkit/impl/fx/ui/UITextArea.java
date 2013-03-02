/*    */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*    */ 
/*    */ import javafx.scene.control.Label;
/*    */ 
/*    */ public class UITextArea extends Label
/*    */ {
/* 11 */   double preferred_width = 360.0D;
/*    */ 
/*    */   public UITextArea(String text)
/*    */   {
/* 32 */     setText(text);
/* 33 */     setPrefWidth(this.preferred_width);
/*    */   }
/*    */ 
/*    */   public UITextArea(double my_width)
/*    */   {
/* 41 */     this.preferred_width = my_width;
/* 42 */     setPrefWidth(this.preferred_width);
/* 43 */     setMinSize((-1.0D / 0.0D), (-1.0D / 0.0D));
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.UITextArea
 * JD-Core Version:    0.6.2
 */