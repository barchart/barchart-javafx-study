/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ final class Match
/*    */   implements Comparable
/*    */ {
/*    */   final Selector selector;
/*    */   final List<String> pseudoclasses;
/*    */   final int idCount;
/*    */   final int styleClassCount;
/*    */   final int specificity;
/*    */ 
/*    */   public Match(Selector paramSelector, List<String> paramList, int paramInt1, int paramInt2)
/*    */   {
/* 49 */     assert (paramSelector != null);
/* 50 */     this.selector = paramSelector;
/* 51 */     this.pseudoclasses = paramList;
/* 52 */     this.idCount = paramInt1;
/* 53 */     this.styleClassCount = paramInt2;
/* 54 */     int i = paramList != null ? paramList.size() : 0;
/* 55 */     this.specificity = (paramInt1 << 8 | paramInt2 << 4 | i);
/*    */   }
/*    */ 
/*    */   private Match() {
/* 59 */     this(null, null, 0, 0);
/* 60 */     if (!$assertionsDisabled) throw new AssertionError("null arg ctor 'Match' called");
/*    */   }
/*    */ 
/*    */   public int compareTo(Object paramObject)
/*    */   {
/* 65 */     Match localMatch = (Match)paramObject;
/* 66 */     return this.specificity - localMatch.specificity;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     StringBuilder localStringBuilder = new StringBuilder();
/* 72 */     localStringBuilder.append(this.selector);
/* 73 */     for (String str : this.pseudoclasses) {
/* 74 */       localStringBuilder.append(":");
/* 75 */       localStringBuilder.append(str);
/*    */     }
/* 77 */     localStringBuilder.append(", ");
/* 78 */     localStringBuilder.append(this.idCount);
/* 79 */     localStringBuilder.append(", ");
/* 80 */     localStringBuilder.append(this.styleClassCount);
/* 81 */     localStringBuilder.append(", 0x");
/* 82 */     localStringBuilder.append(Integer.toHexString(this.specificity));
/*    */ 
/* 84 */     return localStringBuilder.toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.Match
 * JD-Core Version:    0.6.2
 */