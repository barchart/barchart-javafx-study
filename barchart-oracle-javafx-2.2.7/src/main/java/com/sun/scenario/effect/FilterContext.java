/*    */ package com.sun.scenario.effect;
/*    */ 
/*    */ public class FilterContext
/*    */ {
/*    */   private Object referent;
/*    */ 
/*    */   protected FilterContext(Object paramObject)
/*    */   {
/* 34 */     if (paramObject == null) {
/* 35 */       throw new IllegalArgumentException("Referent must be non-null");
/*    */     }
/* 37 */     this.referent = paramObject;
/*    */   }
/*    */ 
/*    */   public final Object getReferent() {
/* 41 */     return this.referent;
/*    */   }
/*    */ 
/*    */   public final int hashCode()
/*    */   {
/* 46 */     return this.referent.hashCode();
/*    */   }
/*    */ 
/*    */   public final boolean equals(Object paramObject)
/*    */   {
/* 51 */     if (!(paramObject instanceof FilterContext)) {
/* 52 */       return false;
/*    */     }
/* 54 */     FilterContext localFilterContext = (FilterContext)paramObject;
/* 55 */     return this.referent.equals(localFilterContext.referent);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.FilterContext
 * JD-Core Version:    0.6.2
 */