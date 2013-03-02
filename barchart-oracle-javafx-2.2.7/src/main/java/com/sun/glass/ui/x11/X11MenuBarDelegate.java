/*    */ package com.sun.glass.ui.x11;
/*    */ 
/*    */ import com.sun.glass.ui.delegate.MenuBarDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuDelegate;
/*    */ 
/*    */ public class X11MenuBarDelegate
/*    */   implements MenuBarDelegate
/*    */ {
/*    */   public boolean createMenuBar()
/*    */   {
/* 14 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean insert(MenuDelegate menu, int pos)
/*    */   {
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean remove(MenuDelegate menu, int pos)
/*    */   {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   public long getNativeMenu()
/*    */   {
/* 29 */     return 0L;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11MenuBarDelegate
 * JD-Core Version:    0.6.2
 */