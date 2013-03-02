/*    */ package com.sun.javafx.fxml.builder;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.AbstractMap;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class URLBuilder extends AbstractMap<String, Object>
/*    */   implements Builder<URL>
/*    */ {
/*    */   private ClassLoader classLoader;
/* 20 */   private Object value = null;
/*    */   public static final String VALUE_KEY = "value";
/*    */ 
/*    */   public URLBuilder(ClassLoader paramClassLoader)
/*    */   {
/* 25 */     this.classLoader = paramClassLoader;
/*    */   }
/*    */ 
/*    */   public Object put(String paramString, Object paramObject)
/*    */   {
/* 30 */     if (paramString == null) {
/* 31 */       throw new NullPointerException();
/*    */     }
/*    */ 
/* 34 */     if (paramString.equals("value"))
/* 35 */       this.value = paramObject;
/*    */     else {
/* 37 */       throw new IllegalArgumentException(paramString + " is not a valid property.");
/*    */     }
/*    */ 
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   public URL build()
/*    */   {
/* 45 */     if (this.value == null)
/* 46 */       throw new IllegalStateException();
/*    */     URL localURL;
/* 50 */     if ((this.value instanceof URL)) {
/* 51 */       localURL = (URL)this.value;
/*    */     } else {
/* 53 */       String str = this.value.toString();
/*    */ 
/* 55 */       if (str.startsWith("/"))
/* 56 */         localURL = this.classLoader.getResource(str);
/*    */       else {
/*    */         try {
/* 59 */           localURL = new URL(str);
/*    */         } catch (MalformedURLException localMalformedURLException) {
/* 61 */           throw new RuntimeException(localMalformedURLException);
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 66 */     return localURL;
/*    */   }
/*    */ 
/*    */   public Set<Map.Entry<String, Object>> entrySet()
/*    */   {
/* 71 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.builder.URLBuilder
 * JD-Core Version:    0.6.2
 */