/*    */ package com.sun.javafx.effect;
/*    */ 
/*    */ public enum EffectDirtyBits
/*    */ {
/* 32 */   EFFECT_DIRTY, 
/*    */ 
/* 36 */   BOUNDS_CHANGED;
/*    */ 
/*    */   private int mask;
/*    */ 
/*    */   private EffectDirtyBits() {
/* 41 */     this.mask = (1 << ordinal());
/*    */   }
/*    */ 
/*    */   public final int getMask() {
/* 45 */     return this.mask;
/*    */   }
/*    */ 
/*    */   public static boolean isSet(int paramInt, EffectDirtyBits paramEffectDirtyBits) {
/* 49 */     return (paramInt & paramEffectDirtyBits.getMask()) != 0;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.effect.EffectDirtyBits
 * JD-Core Version:    0.6.2
 */