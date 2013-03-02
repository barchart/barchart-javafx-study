/*    */ package com.sun.javafx.scene.text;
/*    */ 
/*    */ public class HitInfo
/*    */ {
/*    */   private int charIndex;
/*    */   private boolean leading;
/*    */ 
/*    */   public int getCharIndex()
/*    */   {
/* 37 */     return this.charIndex; } 
/* 38 */   public void setCharIndex(int paramInt) { this.charIndex = paramInt; }
/*    */ 
/*    */ 
/*    */   public boolean isLeading()
/*    */   {
/* 45 */     return this.leading; } 
/* 46 */   public void setLeading(boolean paramBoolean) { this.leading = paramBoolean; }
/*    */ 
/*    */ 
/*    */   public int getInsertionIndex()
/*    */   {
/* 52 */     return this.leading ? this.charIndex : this.charIndex + 1;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 56 */     return "charIndex: " + this.charIndex + ", isLeading: " + this.leading;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.text.HitInfo
 * JD-Core Version:    0.6.2
 */