/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.Clipboard;
/*    */ import com.sun.glass.ui.delegate.ClipboardDelegate;
/*    */ 
/*    */ class GtkClipboardDelegate
/*    */   implements ClipboardDelegate
/*    */ {
/*    */   public Clipboard createClipboard(String clipboardName)
/*    */   {
/* 20 */     if ("SYSTEM".equals(clipboardName)) {
/* 21 */       return new GtkSystemClipboard();
/*    */     }
/* 23 */     if ("DND".equals(clipboardName)) {
/* 24 */       return new GtkDnDClipboard();
/*    */     }
/* 26 */     return null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkClipboardDelegate
 * JD-Core Version:    0.6.2
 */