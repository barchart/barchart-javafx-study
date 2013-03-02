/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.CommonDialogs.ExtensionFilter;
/*    */ import com.sun.glass.ui.Window;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ final class WinCommonDialogs
/*    */ {
/*    */   private static native void _initIDs();
/*    */ 
/*    */   private static native String[] _showFileChooser(long paramLong, String paramString1, String paramString2, int paramInt, boolean paramBoolean, CommonDialogs.ExtensionFilter[] paramArrayOfExtensionFilter);
/*    */ 
/*    */   private static native String _showFolderChooser(long paramLong, String paramString1, String paramString2);
/*    */ 
/*    */   static String[] showFileChooser_impl(Window owner, String folder, String title, int type, boolean multipleMode, CommonDialogs.ExtensionFilter[] extensionFilters)
/*    */   {
/* 37 */     return _showFileChooser(owner != null ? owner.getNativeWindow() : 0L, folder, title, type, multipleMode, extensionFilters);
/*    */   }
/*    */ 
/*    */   static String showFolderChooser_impl(Window owner, String folder, String title)
/*    */   {
/* 42 */     return _showFolderChooser(owner != null ? owner.getNativeWindow() : 0L, folder, title);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 20 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 23 */         Application.loadNativeLibrary();
/* 24 */         return null;
/*    */       }
/*    */     });
/* 27 */     _initIDs();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinCommonDialogs
 * JD-Core Version:    0.6.2
 */