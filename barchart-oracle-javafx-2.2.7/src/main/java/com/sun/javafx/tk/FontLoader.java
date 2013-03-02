/*    */ package com.sun.javafx.tk;
/*    */ 
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.util.List;
/*    */ import javafx.scene.text.Font;
/*    */ import javafx.scene.text.FontPosture;
/*    */ import javafx.scene.text.FontWeight;
/*    */ 
/*    */ public abstract class FontLoader
/*    */ {
/*    */   public abstract void loadFont(Font paramFont);
/*    */ 
/*    */   public abstract List<String> getFamilies();
/*    */ 
/*    */   public abstract List<String> getFontNames();
/*    */ 
/*    */   public abstract List<String> getFontNames(String paramString);
/*    */ 
/*    */   public abstract Font font(String paramString, FontWeight paramFontWeight, FontPosture paramFontPosture, float paramFloat);
/*    */ 
/*    */   public abstract Font font(Object paramObject, float paramFloat);
/*    */ 
/*    */   public abstract FontMetrics getFontMetrics(Font paramFont);
/*    */ 
/*    */   public abstract float computeStringWidth(String paramString, Font paramFont);
/*    */ 
/*    */   public abstract float getSystemFontSize();
/*    */ 
/*    */   public Font loadFont(InputStream paramInputStream, double paramDouble)
/*    */   {
/* 54 */     return font(paramInputStream, (float)paramDouble);
/*    */   }
/*    */ 
/*    */   public Font loadFont(String paramString, double paramDouble)
/*    */   {
/* 61 */     FileInputStream localFileInputStream = null;
/* 62 */     Font localFont = null;
/*    */     try {
/* 64 */       localFileInputStream = new FileInputStream(paramString);
/* 65 */       localFont = font(localFileInputStream, (float)paramDouble);
/*    */     } catch (Exception localException2) {
/*    */     } finally {
/* 68 */       if (localFileInputStream != null)
/*    */         try {
/* 70 */           localFileInputStream.close();
/*    */         }
/*    */         catch (Exception localException4)
/*    */         {
/*    */         }
/*    */     }
/* 76 */     return localFont;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.FontLoader
 * JD-Core Version:    0.6.2
 */