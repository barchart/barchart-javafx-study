/*    */ package com.sun.scenario.effect.impl.state;
/*    */ 
/*    */ import com.sun.scenario.effect.Effect;
/*    */ 
/*    */ public class AccessHelper
/*    */ {
/*    */   private static StateAccessor theStateAccessor;
/*    */ 
/*    */   public static void setStateAccessor(StateAccessor paramStateAccessor)
/*    */   {
/* 41 */     if (theStateAccessor != null) {
/* 42 */       throw new InternalError("EffectAccessor already initialized");
/*    */     }
/* 44 */     theStateAccessor = paramStateAccessor;
/*    */   }
/*    */ 
/*    */   public static Object getState(Effect paramEffect) {
/* 48 */     if (paramEffect == null) {
/* 49 */       return null;
/*    */     }
/* 51 */     return theStateAccessor.getState(paramEffect);
/*    */   }
/*    */ 
/*    */   public static abstract interface StateAccessor
/*    */   {
/*    */     public abstract Object getState(Effect paramEffect);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.state.AccessHelper
 * JD-Core Version:    0.6.2
 */