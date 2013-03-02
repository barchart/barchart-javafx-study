/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import javafx.application.Platform;
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.Observable;
/*    */ import javafx.beans.property.DoubleProperty;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.control.TextField;
/*    */ 
/*    */ public class DoubleFieldSkin extends InputFieldSkin
/*    */ {
/*    */   private InvalidationListener doubleFieldValueListener;
/*    */ 
/*    */   public DoubleFieldSkin(DoubleField paramDoubleField)
/*    */   {
/* 18 */     super(paramDoubleField);
/*    */ 
/* 23 */     paramDoubleField.valueProperty().addListener(this.doubleFieldValueListener = new InvalidationListener() {
/*    */       public void invalidated(Observable paramAnonymousObservable) {
/* 25 */         DoubleFieldSkin.this.updateText();
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public DoubleField getSkinnable() {
/* 31 */     return (DoubleField)this.control;
/*    */   }
/*    */ 
/*    */   public Node getNode() {
/* 35 */     return getTextField();
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/* 48 */     ((DoubleField)this.control).valueProperty().removeListener(this.doubleFieldValueListener);
/* 49 */     super.dispose();
/*    */   }
/*    */ 
/*    */   protected boolean accept(String paramString) {
/* 53 */     if (paramString.length() == 0) return true;
/* 54 */     if (paramString.matches("[0-9\\.]*"))
/*    */       try {
/* 56 */         Double.parseDouble(paramString);
/* 57 */         return true;
/*    */       } catch (NumberFormatException localNumberFormatException) {
/*    */       }
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   protected void updateText() {
/* 64 */     getTextField().setText("" + ((DoubleField)this.control).getValue());
/*    */   }
/*    */ 
/*    */   protected void updateValue() {
/* 68 */     double d1 = ((DoubleField)this.control).getValue();
/*    */ 
/* 70 */     String str = getTextField().getText() == null ? "" : getTextField().getText().trim();
/*    */     try {
/* 72 */       double d2 = Double.parseDouble(str);
/* 73 */       if (d2 != d1)
/* 74 */         ((DoubleField)this.control).setValue(d2);
/*    */     }
/*    */     catch (NumberFormatException localNumberFormatException)
/*    */     {
/* 78 */       ((DoubleField)this.control).setValue(0.0D);
/* 79 */       Platform.runLater(new Runnable() {
/*    */         public void run() {
/* 81 */           DoubleFieldSkin.this.getTextField().positionCaret(1);
/*    */         }
/*    */       });
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.DoubleFieldSkin
 * JD-Core Version:    0.6.2
 */