/*    */ package com.sun.glass.ui.x11;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.CommonDialogs.ExtensionFilter;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ final class X11CommonDialogs
/*    */ {
/*    */   private static native String[] _showFileChooser(String paramString1, String paramString2, int paramInt, boolean paramBoolean, CommonDialogs.ExtensionFilter[] paramArrayOfExtensionFilter);
/*    */ 
/*    */   private static native String _showFolderChooser();
/*    */ 
/*    */   static String[] showFileChooser_impl(String folder, String title, int type, boolean multipleMode, CommonDialogs.ExtensionFilter[] extensionFilters)
/*    */   {
/* 34 */     return _showFileChooser(folder, title, type, multipleMode, extensionFilters);
/*    */   }
/*    */ 
/*    */   static String showFolderChooser_impl() {
/* 38 */     return _showFolderChooser();
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 19 */     AccessController.doPrivileged(new PrivilegedAction() {
/*    */       public Void run() {
/* 21 */         Application.loadNativeLibrary();
/* 22 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11CommonDialogs
 * JD-Core Version:    0.6.2
 */