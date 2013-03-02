/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ public final class Style
/*    */ {
/*    */   private final Selector selector;
/*    */   private final Declaration declaration;
/*    */ 
/*    */   public Selector getSelector()
/*    */   {
/* 39 */     return this.selector;
/*    */   }
/*    */ 
/*    */   public Declaration getDeclaration()
/*    */   {
/* 48 */     return this.declaration;
/*    */   }
/*    */ 
/*    */   public Style(Selector paramSelector, Declaration paramDeclaration) {
/* 52 */     this.selector = paramSelector;
/* 53 */     this.declaration = paramDeclaration;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 58 */     if (paramObject == this) {
/* 59 */       return true;
/*    */     }
/* 61 */     if (paramObject == null) {
/* 62 */       return false;
/*    */     }
/* 64 */     if (getClass() != paramObject.getClass()) {
/* 65 */       return false;
/*    */     }
/* 67 */     Style localStyle = (Style)paramObject;
/* 68 */     if ((this.selector != localStyle.selector) && ((this.selector == null) || (!this.selector.equals(localStyle.selector)))) {
/* 69 */       return false;
/*    */     }
/* 71 */     if ((this.declaration != localStyle.declaration) && ((this.declaration == null) || (!this.declaration.equals(localStyle.declaration)))) {
/* 72 */       return false;
/*    */     }
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 79 */     StringBuilder localStringBuilder = new StringBuilder().append(String.valueOf(this.selector)).append(" { ").append(String.valueOf(this.declaration)).append(" } ");
/*    */ 
/* 84 */     return localStringBuilder.toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.Style
 * JD-Core Version:    0.6.2
 */