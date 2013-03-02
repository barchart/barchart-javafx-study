/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.Cursor;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.ui.Size;
/*    */ 
/*    */ public class GtkCursor extends Cursor
/*    */ {
/*    */   GtkCursor(int type)
/*    */   {
/* 18 */     super(type);
/*    */   }
/*    */ 
/*    */   GtkCursor(int x, int y, Pixels pixels) {
/* 22 */     super(x, y, pixels);
/*    */   }
/*    */ 
/*    */   protected native long _createCursor(int paramInt1, int paramInt2, Pixels paramPixels);
/*    */ 
/*    */   static native Size _getBestSize(int paramInt1, int paramInt2);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkCursor
 * JD-Core Version:    0.6.2
 */