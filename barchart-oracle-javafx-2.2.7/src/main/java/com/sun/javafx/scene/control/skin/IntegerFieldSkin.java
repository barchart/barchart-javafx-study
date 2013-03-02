/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import javafx.application.Platform;
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.Observable;
/*    */ import javafx.beans.property.IntegerProperty;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.control.TextField;
/*    */ 
/*    */ public class IntegerFieldSkin extends InputFieldSkin
/*    */ {
/*    */   private InvalidationListener integerFieldValueListener;
/*    */ 
/*    */   public IntegerFieldSkin(IntegerField paramIntegerField)
/*    */   {
/* 18 */     super(paramIntegerField);
/*    */ 
/* 23 */     paramIntegerField.valueProperty().addListener(this.integerFieldValueListener = new InvalidationListener() {
/*    */       public void invalidated(Observable paramAnonymousObservable) {
/* 25 */         IntegerFieldSkin.this.updateText();
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public IntegerField getSkinnable() {
/* 31 */     return (IntegerField)this.control;
/*    */   }
/*    */ 
/*    */   public Node getNode() {
/* 35 */     return getTextField();
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/* 48 */     ((IntegerField)this.control).valueProperty().removeListener(this.integerFieldValueListener);
/* 49 */     super.dispose();
/*    */   }
/*    */ 
/*    */   protected boolean accept(String paramString) {
/* 53 */     if (paramString.length() == 0) return true;
/* 54 */     if (paramString.matches("[0-9]*"))
/*    */       try {
/* 56 */         Integer.parseInt(paramString);
/* 57 */         int i = Integer.parseInt(paramString);
/* 58 */         int j = ((IntegerField)this.control).getMaxValue();
/* 59 */         return i <= j;
/*    */       } catch (NumberFormatException localNumberFormatException) {
/*    */       }
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */   protected void updateText() {
/* 66 */     getTextField().setText("" + ((IntegerField)this.control).getValue());
/*    */   }
/*    */ 
/*    */   protected void updateValue() {
/* 70 */     int i = ((IntegerField)this.control).getValue();
/*    */ 
/* 72 */     String str = getTextField().getText() == null ? "" : getTextField().getText().trim();
/*    */     try {
/* 74 */       int j = Integer.parseInt(str);
/* 75 */       if (j != i)
/* 76 */         ((IntegerField)this.control).setValue(j);
/*    */     }
/*    */     catch (NumberFormatException localNumberFormatException)
/*    */     {
/* 80 */       ((IntegerField)this.control).setValue(0);
/* 81 */       Platform.runLater(new Runnable() {
/*    */         public void run() {
/* 83 */           IntegerFieldSkin.this.getTextField().positionCaret(1);
/*    */         }
/*    */       });
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.IntegerFieldSkin
 * JD-Core Version:    0.6.2
 */