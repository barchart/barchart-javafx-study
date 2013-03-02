/*    */ package com.sun.javafx.collections.transformation;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract interface FilterableList<E> extends List<E>
/*    */ {
/*    */   public abstract void filter();
/*    */ 
/*    */   public abstract void setMode(FilterMode paramFilterMode);
/*    */ 
/*    */   public abstract FilterMode getMode();
/*    */ 
/*    */   public abstract void setMatcher(Matcher<? super E> paramMatcher);
/*    */ 
/*    */   public abstract Matcher<? super E> getMatcher();
/*    */ 
/*    */   public static enum FilterMode
/*    */   {
/* 50 */     BATCH, 
/*    */ 
/* 54 */     LIVE;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.transformation.FilterableList
 * JD-Core Version:    0.6.2
 */