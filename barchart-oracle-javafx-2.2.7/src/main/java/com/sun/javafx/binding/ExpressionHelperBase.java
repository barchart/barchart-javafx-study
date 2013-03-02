/*    */ package com.sun.javafx.binding;
/*    */ 
/*    */ import javafx.beans.WeakListener;
/*    */ 
/*    */ public class ExpressionHelperBase
/*    */ {
/*    */   protected static int trim(int paramInt, Object[] paramArrayOfObject)
/*    */   {
/* 14 */     for (int i = 0; i < paramInt; i++) {
/* 15 */       Object localObject = paramArrayOfObject[i];
/* 16 */       if (((localObject instanceof WeakListener)) && 
/* 17 */         (((WeakListener)localObject).wasGarbageCollected())) {
/* 18 */         int j = paramInt - i - 1;
/* 19 */         if (j > 0)
/* 20 */           System.arraycopy(paramArrayOfObject, i + 1, paramArrayOfObject, i, j);
/* 21 */         paramArrayOfObject[(--paramInt)] = null;
/* 22 */         i--;
/*    */       }
/*    */     }
/*    */ 
/* 26 */     return paramInt;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.ExpressionHelperBase
 * JD-Core Version:    0.6.2
 */