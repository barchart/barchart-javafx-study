/*    */ package com.sun.glass.ui.mac;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.Screen;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ final class MacScreen
/*    */ {
/*    */   private static native void _initIDs();
/*    */ 
/*    */   private static native Screen _getDeepestScreen(Screen paramScreen);
/*    */ 
/*    */   private static native Screen _getMainScreen(Screen paramScreen);
/*    */ 
/*    */   private static native Screen _getScreenForLocation(Screen paramScreen, int paramInt1, int paramInt2);
/*    */ 
/*    */   private static native Screen _getScreenForPtr(Screen paramScreen, long paramLong);
/*    */ 
/*    */   private static native List<Screen> _getScreens(ArrayList<Screen> paramArrayList);
/*    */ 
/*    */   static Screen getDeepestScreen_impl()
/*    */   {
/* 39 */     return _getDeepestScreen(new Screen());
/*    */   }
/*    */ 
/*    */   static Screen getMainScreen_impl() {
/* 43 */     return _getMainScreen(new Screen());
/*    */   }
/*    */ 
/*    */   static Screen getScreenForLocation_impl(int x, int y) {
/* 47 */     return _getScreenForLocation(new Screen(), x, y);
/*    */   }
/*    */ 
/*    */   static Screen getScreenForPtr_impl(long screenPtr) {
/* 51 */     return _getScreenForPtr(new Screen(), screenPtr);
/*    */   }
/*    */ 
/*    */   static List<Screen> getScreens_impl() {
/* 55 */     return _getScreens(new ArrayList());
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 22 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 25 */         Application.loadNativeLibrary();
/* 26 */         return null;
/*    */       }
/*    */     });
/* 29 */     _initIDs();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacScreen
 * JD-Core Version:    0.6.2
 */