/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.Observable;
/*    */ import javafx.beans.property.ObjectProperty;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.control.TextField;
/*    */ import javafx.scene.paint.Color;
/*    */ 
/*    */ class WebColorFieldSkin extends InputFieldSkin
/*    */ {
/*    */   private InvalidationListener integerFieldValueListener;
/* 12 */   private boolean noChangeInValue = false;
/*    */ 
/*    */   public WebColorFieldSkin(WebColorField paramWebColorField)
/*    */   {
/* 19 */     super(paramWebColorField);
/*    */ 
/* 24 */     paramWebColorField.valueProperty().addListener(this.integerFieldValueListener = new InvalidationListener() {
/*    */       public void invalidated(Observable paramAnonymousObservable) {
/* 26 */         WebColorFieldSkin.this.updateText();
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public WebColorField getSkinnable() {
/* 32 */     return (WebColorField)this.control;
/*    */   }
/*    */ 
/*    */   public Node getNode() {
/* 36 */     return getTextField();
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/* 48 */     ((WebColorField)this.control).valueProperty().removeListener(this.integerFieldValueListener);
/* 49 */     super.dispose();
/*    */   }
/*    */ 
/*    */   protected boolean accept(String paramString)
/*    */   {
/* 54 */     if (paramString.length() == 0) return true;
/* 55 */     if ((paramString.matches("#[a-fA-F0-9]{0,6}")) || (paramString.matches("[a-fA-F0-9]{0,6}"))) {
/* 56 */       return true;
/*    */     }
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   protected void updateText() {
/* 62 */     Color localColor = ((WebColorField)this.control).getValue();
/* 63 */     if (localColor == null) localColor = Color.BLACK;
/* 64 */     getTextField().setText(getWebColor(localColor));
/*    */   }
/*    */ 
/*    */   protected void updateValue() {
/* 68 */     if (this.noChangeInValue) return;
/* 69 */     Color localColor1 = ((WebColorField)this.control).getValue();
/* 70 */     String str = getTextField().getText() == null ? "" : getTextField().getText().trim().toUpperCase();
/* 71 */     if ((str.matches("#[A-F0-9]{6}")) || (str.matches("[A-F0-9]{6}")))
/*    */       try {
/* 73 */         Color localColor2 = str.charAt(0) == '#' ? Color.web(str) : Color.web("#" + str);
/* 74 */         if (!localColor2.equals(localColor1)) {
/* 75 */           ((WebColorField)this.control).setValue(localColor2);
/*    */         }
/*    */         else
/*    */         {
/* 79 */           this.noChangeInValue = true;
/* 80 */           getTextField().setText(getWebColor(localColor2));
/* 81 */           this.noChangeInValue = false;
/*    */         }
/*    */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 84 */         System.out.println("Failed to parse [" + str + "]");
/*    */       }
/*    */   }
/*    */ 
/*    */   private static String getWebColor(Color paramColor)
/*    */   {
/* 91 */     int i = (int)(paramColor.getRed() * 255.0D);
/* 92 */     int j = (int)(paramColor.getGreen() * 255.0D);
/* 93 */     int k = (int)(paramColor.getBlue() * 255.0D);
/* 94 */     return "#" + String.format("%02X", new Object[] { Integer.valueOf(i) }) + String.format("%02X", new Object[] { Integer.valueOf(j) }) + String.format("%02X", new Object[] { Integer.valueOf(k) });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.WebColorFieldSkin
 * JD-Core Version:    0.6.2
 */