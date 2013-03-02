/*    */ package com.sun.javafx.fxml.builder;
/*    */ 
/*    */ import java.util.AbstractMap;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ import javafx.scene.image.Image;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class JavaFXImageBuilder extends AbstractMap<String, Object>
/*    */   implements Builder<Image>
/*    */ {
/* 18 */   private String url = "";
/* 19 */   private double requestedWidth = 0.0D;
/* 20 */   private double requestedHeight = 0.0D;
/* 21 */   private boolean preserveRatio = false;
/* 22 */   private boolean smooth = false;
/* 23 */   private boolean backgroundLoading = false;
/*    */ 
/*    */   public Image build()
/*    */   {
/* 27 */     return new Image(this.url, this.requestedWidth, this.requestedHeight, this.preserveRatio, this.smooth, this.backgroundLoading);
/*    */   }
/*    */ 
/*    */   public Object put(String paramString, Object paramObject)
/*    */   {
/* 32 */     if (paramObject != null) {
/* 33 */       String str = paramObject.toString();
/*    */ 
/* 35 */       if ("url".equals(paramString))
/* 36 */         this.url = str;
/* 37 */       else if ("requestedWidth".equals(paramString))
/* 38 */         this.requestedWidth = Double.parseDouble(str);
/* 39 */       else if ("requestedHeight".equals(paramString))
/* 40 */         this.requestedHeight = Double.parseDouble(str);
/* 41 */       else if ("preserveRatio".equals(paramString))
/* 42 */         this.preserveRatio = Boolean.parseBoolean(str);
/* 43 */       else if ("smooth".equals(paramString))
/* 44 */         this.smooth = Boolean.parseBoolean(str);
/* 45 */       else if (!"backgroundLoading".equals(paramString))
/*    */       {
/* 47 */         throw new IllegalArgumentException("Unknown Image property: " + paramString);
/*    */       }
/*    */     }
/*    */ 
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   public Set<Map.Entry<String, Object>> entrySet()
/*    */   {
/* 56 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.builder.JavaFXImageBuilder
 * JD-Core Version:    0.6.2
 */