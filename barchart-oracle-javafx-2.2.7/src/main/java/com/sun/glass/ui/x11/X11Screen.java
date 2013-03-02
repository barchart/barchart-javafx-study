/*    */ package com.sun.glass.ui.x11;
/*    */ 
/*    */ import com.sun.glass.ui.Screen;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class X11Screen extends Screen
/*    */ {
/* 31 */   private static Screen mainScreen = null;
/*    */ 
/* 47 */   private static Vector<Screen> screens = null;
/*    */ 
/*    */   private static native Screen _getDeepestScreen(Screen paramScreen);
/*    */ 
/*    */   private static native Screen _getMainScreen(Screen paramScreen);
/*    */ 
/*    */   private static native Screen _getScreenForLocation(Screen paramScreen, int paramInt1, int paramInt2);
/*    */ 
/*    */   private static native Screen _getScreenForPtr(Screen paramScreen, long paramLong);
/*    */ 
/*    */   private static native Vector<Screen> _getScreens(Vector<Screen> paramVector);
/*    */ 
/*    */   static Screen getDeepestScreen_impl()
/*    */   {
/* 28 */     return _getDeepestScreen(new Screen());
/*    */   }
/*    */ 
/*    */   static Screen getMainScreen_impl()
/*    */   {
/* 34 */     if (mainScreen == null)
/* 35 */       mainScreen = _getMainScreen(new Screen());
/* 36 */     return mainScreen;
/*    */   }
/*    */ 
/*    */   static Screen getScreenForLocation_impl(int x, int y) {
/* 40 */     return _getScreenForLocation(new Screen(), x, y);
/*    */   }
/*    */ 
/*    */   static Screen getScreenForPtr_impl(long screenPtr) {
/* 44 */     return _getScreenForPtr(new Screen(), screenPtr);
/*    */   }
/*    */ 
/*    */   static Vector<Screen> getScreens_impl()
/*    */   {
/* 49 */     if (screens == null)
/* 50 */       screens = _getScreens(new Vector());
/* 51 */     return screens;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 18 */     X11Application.initLibrary();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11Screen
 * JD-Core Version:    0.6.2
 */