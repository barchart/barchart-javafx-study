/*    */ package com.sun.glass.ui.mac;
/*    */ 
/*    */ public class MacDnDClipboard extends MacSystemClipboard
/*    */ {
/* 28 */   protected int dragButton = 0;
/*    */ 
/*    */   public MacDnDClipboard(String name)
/*    */   {
/* 11 */     super(name);
/*    */   }
/*    */ 
/*    */   static MacDnDClipboard getInstance()
/*    */   {
/* 18 */     return (MacDnDClipboard)get("DND");
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 22 */     return "Mac DnD Clipboard";
/*    */   }
/*    */ 
/*    */   public int getDragButton()
/*    */   {
/* 31 */     return this.dragButton;
/*    */   }
/*    */ 
/*    */   private void setDragButton(int dragButton)
/*    */   {
/* 38 */     this.dragButton = dragButton;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacDnDClipboard
 * JD-Core Version:    0.6.2
 */