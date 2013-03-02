/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ import java.util.Deque;
/*    */ import java.util.LinkedList;
/*    */ 
/*    */ public final class DirtyRegionPool
/*    */ {
/*    */   private static final int POOL_SIZE_MIN = 4;
/*    */   private static final int EXPIRATION_TIME = 3000;
/*    */   private static final int COUNT_BETWEEN_EXPIRATION_CHECK = 90;
/*    */   private final int containerSize;
/* 27 */   private int clearCounter = 90;
/*    */   private final Deque<DirtyRegionContainer> fixed;
/*    */   private final Deque<PoolItem> unlocked;
/*    */   private final Deque<PoolItem> locked;
/*    */ 
/*    */   public DirtyRegionPool(int paramInt)
/*    */   {
/* 33 */     this.containerSize = paramInt;
/* 34 */     this.fixed = new LinkedList();
/* 35 */     this.unlocked = new LinkedList();
/* 36 */     this.locked = new LinkedList();
/* 37 */     for (int i = 0; i < 4; i++)
/* 38 */       this.fixed.add(new DirtyRegionContainer(paramInt));
/*    */   }
/*    */ 
/*    */   public DirtyRegionContainer checkOut()
/*    */   {
/* 43 */     clearExpired();
/* 44 */     if (!this.fixed.isEmpty()) {
/* 45 */       return (DirtyRegionContainer)this.fixed.pop();
/*    */     }
/* 47 */     if (!this.unlocked.isEmpty()) {
/* 48 */       localObject = (PoolItem)this.unlocked.pop();
/* 49 */       this.locked.push(localObject);
/* 50 */       return ((PoolItem)localObject).container;
/*    */     }
/* 52 */     Object localObject = new DirtyRegionContainer(this.containerSize);
/* 53 */     this.locked.push(new PoolItem(null, -1L));
/* 54 */     return localObject;
/*    */   }
/*    */ 
/*    */   public void checkIn(DirtyRegionContainer paramDirtyRegionContainer) {
/* 58 */     paramDirtyRegionContainer.reset();
/* 59 */     if (this.locked.isEmpty()) {
/* 60 */       this.fixed.push(paramDirtyRegionContainer);
/*    */     } else {
/* 62 */       PoolItem localPoolItem = (PoolItem)this.locked.pop();
/* 63 */       localPoolItem.container = paramDirtyRegionContainer;
/* 64 */       localPoolItem.timeStamp = System.currentTimeMillis();
/* 65 */       this.unlocked.push(localPoolItem);
/*    */     }
/*    */   }
/*    */ 
/*    */   private void clearExpired() {
/* 70 */     if (this.unlocked.isEmpty()) {
/* 71 */       return;
/*    */     }
/* 73 */     if (this.clearCounter-- == 0) {
/* 74 */       this.clearCounter = 90;
/* 75 */       PoolItem localPoolItem = (PoolItem)this.unlocked.peekLast();
/* 76 */       long l = System.currentTimeMillis();
/* 77 */       while ((localPoolItem != null) && (localPoolItem.timeStamp + 3000L < l)) {
/* 78 */         this.unlocked.removeLast();
/* 79 */         localPoolItem = (PoolItem)this.unlocked.peekLast();
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   private class PoolItem
/*    */   {
/*    */     DirtyRegionContainer container;
/*    */     long timeStamp;
/*    */ 
/*    */     public PoolItem(DirtyRegionContainer paramLong, long arg3)
/*    */     {
/* 19 */       this.container = paramLong;
/*    */       Object localObject;
/* 20 */       this.timeStamp = localObject;
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.DirtyRegionPool
 * JD-Core Version:    0.6.2
 */