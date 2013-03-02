/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ import com.sun.glass.ui.Clipboard;
/*    */ import com.sun.glass.ui.delegate.ClipboardDelegate;
/*    */ 
/*    */ public class WinClipboardDelegate
/*    */   implements ClipboardDelegate
/*    */ {
/*    */   public Clipboard createClipboard(String clipboardName)
/*    */   {
/* 11 */     if ("SYSTEM".equals(clipboardName))
/* 12 */       return new WinSystemClipboard(clipboardName);
/* 13 */     if ("DND".equals(clipboardName)) {
/* 14 */       return new WinDnDClipboard(clipboardName);
/*    */     }
/* 16 */     return null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinClipboardDelegate
 * JD-Core Version:    0.6.2
 */