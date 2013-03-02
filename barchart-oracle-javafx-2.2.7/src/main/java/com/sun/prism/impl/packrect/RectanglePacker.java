/*    */ package com.sun.prism.impl.packrect;
/*    */ 
/*    */ public class RectanglePacker
/*    */ {
/*    */   private BackingStoreManager manager;
/*    */   private Object backingStore;
/*    */   private LevelSet levels;
/*    */   private int initialWidth;
/*    */   private int initialHeight;
/*    */ 
/*    */   public RectanglePacker(BackingStoreManager paramBackingStoreManager, int paramInt1, int paramInt2)
/*    */   {
/* 61 */     this.manager = paramBackingStoreManager;
/* 62 */     this.levels = new LevelSet(paramInt1, paramInt2);
/* 63 */     this.initialWidth = paramInt1;
/* 64 */     this.initialHeight = paramInt2;
/*    */   }
/*    */ 
/*    */   public Object getBackingStore() {
/* 68 */     if (this.backingStore == null) {
/* 69 */       this.backingStore = this.manager.allocateBackingStore(this.levels.w(), this.levels.h());
/*    */     }
/* 71 */     return this.backingStore;
/*    */   }
/*    */ 
/*    */   public boolean add(Rect paramRect)
/*    */   {
/* 79 */     if (this.backingStore == null) {
/* 80 */       this.backingStore = this.manager.allocateBackingStore(this.levels.w(), this.levels.h());
/*    */     }
/* 82 */     return this.levels.add(paramRect);
/*    */   }
/*    */ 
/*    */   public void clear()
/*    */   {
/* 87 */     this.levels.clear();
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/* 94 */     if (this.backingStore != null)
/* 95 */       this.manager.deleteBackingStore(this.backingStore);
/* 96 */     this.backingStore = null;
/* 97 */     this.levels = null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.packrect.RectanglePacker
 * JD-Core Version:    0.6.2
 */