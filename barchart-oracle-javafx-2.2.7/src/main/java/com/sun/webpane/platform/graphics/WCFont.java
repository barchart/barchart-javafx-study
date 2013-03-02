/*    */ package com.sun.webpane.platform.graphics;
/*    */ 
/*    */ public abstract class WCFont extends Ref
/*    */ {
/*    */   public abstract Object getPlatformFont();
/*    */ 
/*    */   public abstract int getOffsetForPosition(String paramString, int paramInt, float paramFloat1, float paramFloat2);
/*    */ 
/*    */   public abstract int[] getGlyphCodes(int[] paramArrayOfInt);
/*    */ 
/*    */   public abstract double getXHeight();
/*    */ 
/*    */   public abstract double getGlyphWidth(int paramInt);
/*    */ 
/*    */   public abstract double[] getStringBounds(String paramString, int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2);
/*    */ 
/*    */   public abstract double getStringLength(String paramString, float paramFloat1, float paramFloat2);
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 36 */     Object localObject = getPlatformFont();
/* 37 */     return localObject != null ? localObject.hashCode() : 0;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 52 */     if ((paramObject instanceof WCFont)) {
/* 53 */       Object localObject1 = getPlatformFont();
/* 54 */       Object localObject2 = ((WCFont)paramObject).getPlatformFont();
/* 55 */       return localObject1 == null ? false : localObject2 == null ? true : localObject1.equals(localObject2);
/*    */     }
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   public abstract int getAscent();
/*    */ 
/*    */   public abstract int getDescent();
/*    */ 
/*    */   public abstract int getHeight();
/*    */ 
/*    */   public abstract boolean hasUniformLineMetrics();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCFont
 * JD-Core Version:    0.6.2
 */