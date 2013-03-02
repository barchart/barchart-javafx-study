/*    */ package com.sun.javafx.iio.common;
/*    */ 
/*    */ public class ScalerFactory
/*    */ {
/*    */   public static PushbroomScaler createScaler(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
/*    */   {
/* 30 */     if ((paramInt1 <= 0) || (paramInt2 <= 0) || (paramInt3 <= 0) || (paramInt4 <= 0) || (paramInt5 <= 0))
/*    */     {
/* 32 */       throw new IllegalArgumentException();
/*    */     }
/*    */ 
/* 35 */     Object localObject = null;
/*    */ 
/* 37 */     int i = (paramInt4 > paramInt1) || (paramInt5 > paramInt2) ? 1 : 0;
/*    */ 
/* 39 */     if (i != 0) {
/* 40 */       if (paramBoolean)
/*    */       {
/* 42 */         localObject = new RoughScaler(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*    */       }
/*    */       else {
/* 45 */         localObject = new RoughScaler(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*    */       }
/*    */ 
/*    */     }
/* 49 */     else if (paramBoolean) {
/* 50 */       localObject = new SmoothMinifier(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*    */     }
/*    */     else {
/* 53 */       localObject = new RoughScaler(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*    */     }
/*    */ 
/* 58 */     return localObject;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.common.ScalerFactory
 * JD-Core Version:    0.6.2
 */