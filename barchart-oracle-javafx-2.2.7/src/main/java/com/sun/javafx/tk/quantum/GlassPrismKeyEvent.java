/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ public class GlassPrismKeyEvent
/*    */   implements PrismEvent
/*    */ {
/*    */   ViewScene view;
/*    */   int type;
/*    */   int key;
/*    */   char[] characters;
/*    */   int modifiers;
/*    */ 
/*    */   protected GlassPrismKeyEvent(ViewScene paramViewScene, int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3)
/*    */   {
/* 20 */     this.view = paramViewScene;
/* 21 */     this.type = paramInt1;
/* 22 */     this.key = paramInt2;
/* 23 */     this.characters = paramArrayOfChar;
/* 24 */     this.modifiers = paramInt3;
/*    */   }
/*    */ 
/*    */   protected ViewScene view() {
/* 28 */     return this.view;
/*    */   }
/*    */ 
/*    */   protected int type() {
/* 32 */     return this.type;
/*    */   }
/*    */ 
/*    */   protected int key() {
/* 36 */     return this.key;
/*    */   }
/*    */ 
/*    */   protected char[] characters() {
/* 40 */     return this.characters;
/*    */   }
/*    */ 
/*    */   protected int modifiers() {
/* 44 */     return this.modifiers;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassPrismKeyEvent
 * JD-Core Version:    0.6.2
 */