/*    */ package com.sun.glass.ui.x11;
/*    */ 
/*    */ import com.sun.glass.ui.Cursor;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.ui.Size;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class X11Cursor extends Cursor
/*    */ {
/*    */   protected X11Cursor(int type)
/*    */   {
/* 15 */     super(type);
/*    */   }
/*    */ 
/*    */   protected X11Cursor(int x, int y, Pixels pixels) {
/* 19 */     super(x, y, pixels);
/*    */   }
/*    */ 
/*    */   protected long _createCursor(int x, int y, Pixels pixels)
/*    */   {
/* 28 */     System.err.println("WARNING: X11Cursor._createCursor not implemented");
/* 29 */     return 0L;
/*    */   }
/*    */ 
/*    */   static void setVisible_impl(boolean visible) {
/* 33 */     System.err.println("WARNING: X11Cursor._setVisible_impl() not implemented");
/*    */   }
/*    */ 
/*    */   static Size getBestSize_impl(int width, int height) {
/* 37 */     return new Size(10, 10);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 23 */     X11Application.initLibrary();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11Cursor
 * JD-Core Version:    0.6.2
 */