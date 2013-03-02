/*    */ package com.sun.glass.ui.mac;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.Cursor;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.ui.Size;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ final class MacCursor extends Cursor
/*    */ {
/*    */   protected MacCursor(int type)
/*    */   {
/* 21 */     super(type);
/*    */   }
/*    */ 
/*    */   protected MacCursor(int x, int y, Pixels pixels) {
/* 25 */     super(x, y, pixels);
/*    */   }
/*    */ 
/*    */   private static native void _initIDs();
/*    */ 
/*    */   protected native long _createCursor(int paramInt1, int paramInt2, Pixels paramPixels);
/*    */ 
/*    */   void set()
/*    */   {
/* 43 */     int type = getType();
/* 44 */     setVisible(type != -1);
/*    */ 
/* 46 */     switch (type) {
/*    */     case -1:
/* 48 */       break;
/*    */     case 0:
/* 50 */       _setCustom(getNativeCursor());
/* 51 */       break;
/*    */     default:
/* 53 */       _set(type);
/*    */     }
/*    */   }
/*    */ 
/*    */   private native void _set(int paramInt);
/*    */ 
/*    */   private native void _setCustom(long paramLong);
/*    */ 
/*    */   private static native void _setVisible(boolean paramBoolean);
/*    */ 
/*    */   private static native Size _getBestSize(int paramInt1, int paramInt2);
/*    */ 
/* 65 */   static void setVisible_impl(boolean visible) { _setVisible(visible); }
/*    */ 
/*    */   static Size getBestSize_impl(int width, int height)
/*    */   {
/* 69 */     return _getBestSize(width, height);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 30 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 33 */         Application.loadNativeLibrary();
/* 34 */         return null;
/*    */       }
/*    */     });
/* 37 */     _initIDs();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacCursor
 * JD-Core Version:    0.6.2
 */