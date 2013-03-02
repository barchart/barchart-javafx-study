/*    */ package com.sun.t2k;
/*    */ 
/*    */ import com.sun.javafx.font.FontResource;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Map;
/*    */ 
/*    */ class T2KStrikeDisposer
/*    */   implements DisposerRecord
/*    */ {
/*    */   FontResource fontResource;
/*    */   T2KFontStrikeDesc desc;
/* 14 */   long pScalerContext = 0L;
/* 15 */   boolean disposed = false;
/*    */ 
/*    */   public T2KStrikeDisposer(FontResource paramFontResource, T2KFontStrikeDesc paramT2KFontStrikeDesc, long paramLong)
/*    */   {
/* 21 */     this.fontResource = paramFontResource;
/* 22 */     this.desc = paramT2KFontStrikeDesc;
/* 23 */     this.pScalerContext = paramLong;
/*    */   }
/*    */ 
/*    */   public synchronized void dispose() {
/* 27 */     if (!this.disposed)
/*    */     {
/* 31 */       WeakReference localWeakReference = (WeakReference)this.fontResource.getStrikeMap().get(this.desc);
/* 32 */       if (localWeakReference != null) {
/* 33 */         Object localObject = localWeakReference.get();
/* 34 */         if (localObject == null) {
/* 35 */           this.fontResource.getStrikeMap().remove(this.desc);
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 40 */       if (this.pScalerContext != 0L) {
/* 41 */         T2KFontFile.freePointer(this.pScalerContext);
/*    */       }
/* 43 */       this.disposed = true;
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.T2KStrikeDisposer
 * JD-Core Version:    0.6.2
 */