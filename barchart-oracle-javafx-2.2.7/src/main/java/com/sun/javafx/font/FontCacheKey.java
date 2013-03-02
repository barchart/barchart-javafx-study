/*    */ package com.sun.javafx.font;
/*    */ 
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ 
/*    */ public class FontCacheKey
/*    */ {
/*    */   PGFont font;
/*    */   BaseTransform xform;
/*    */   int hash;
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 16 */     if (this.hash != 0) {
/* 17 */       return this.hash;
/*    */     }
/* 19 */     this.hash = (this.font.hashCode() + this.xform.hashCode());
/*    */ 
/* 21 */     return this.hash;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 26 */     FontCacheKey localFontCacheKey = (FontCacheKey)paramObject;
/* 27 */     return (this.font.equals(localFontCacheKey.font)) && (this.xform.equals(localFontCacheKey.xform));
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.font.FontCacheKey
 * JD-Core Version:    0.6.2
 */