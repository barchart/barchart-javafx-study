/*    */ package com.sun.glass.ui.mac;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.CommonDialogs.ExtensionFilter;
/*    */ import com.sun.glass.ui.Window;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ final class MacCommonDialogs
/*    */ {
/*    */   private static native void _initIDs();
/*    */ 
/*    */   private static native String[] _showFileOpenChooser(long paramLong, String paramString1, String paramString2, boolean paramBoolean, Object[] paramArrayOfObject);
/*    */ 
/*    */   private static native String[] _showFileSaveChooser(long paramLong, String paramString1, String paramString2, Object[] paramArrayOfObject);
/*    */ 
/*    */   private static native String _showFolderChooser(String paramString1, String paramString2);
/*    */ 
/*    */   static String[] showFileChooser_impl(Window owner, String folder, String title, int type, boolean multipleMode, CommonDialogs.ExtensionFilter[] extensionFilters)
/*    */   {
/* 44 */     List list = new ArrayList();
/* 45 */     for (CommonDialogs.ExtensionFilter extension : extensionFilters) {
/* 46 */       for (String suffix : extension.getExtensions()) {
/* 47 */         list.add(suffix);
/*    */       }
/*    */     }
/* 50 */     Object[] extensions = list.toArray();
/*    */ 
/* 52 */     long ownerPtr = owner != null ? owner.getNativeWindow() : 0L;
/*    */ 
/* 54 */     if (type == 0)
/* 55 */       return _showFileOpenChooser(ownerPtr, folder, title, multipleMode, extensions);
/* 56 */     if (type == 1) {
/* 57 */       return _showFileSaveChooser(ownerPtr, folder, title, extensions);
/*    */     }
/* 59 */     return null;
/*    */   }
/*    */ 
/*    */   static String showFolderChooser_impl()
/*    */   {
/* 64 */     return _showFolderChooser(null, null);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 25 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 28 */         Application.loadNativeLibrary();
/* 29 */         return null;
/*    */       }
/*    */     });
/* 32 */     _initIDs();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacCommonDialogs
 * JD-Core Version:    0.6.2
 */