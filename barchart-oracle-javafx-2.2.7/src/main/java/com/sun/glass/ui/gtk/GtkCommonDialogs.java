/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.CommonDialogs.ExtensionFilter;
/*    */ import com.sun.glass.ui.Window;
/*    */ 
/*    */ public class GtkCommonDialogs
/*    */ {
/*    */   private static native String[] _showFileChooser(long paramLong, String paramString1, String paramString2, int paramInt, boolean paramBoolean, CommonDialogs.ExtensionFilter[] paramArrayOfExtensionFilter);
/*    */ 
/*    */   private static native String _showFolderChooser(long paramLong, String paramString1, String paramString2);
/*    */ 
/*    */   static String[] showFileChooser(Window owner, String folder, String title, int type, boolean multipleMode, CommonDialogs.ExtensionFilter[] extensionFilters)
/*    */   {
/* 27 */     if (owner != null) owner.setEnabled(false);
/* 28 */     String[] results = _showFileChooser(owner == null ? 0L : owner.getNativeHandle(), folder, title, type, multipleMode, extensionFilters);
/*    */ 
/* 30 */     if (owner != null) owner.setEnabled(true);
/* 31 */     return results;
/*    */   }
/*    */ 
/*    */   static String showFolderChooser(Window owner, String folder, String title) {
/* 35 */     if (owner != null)
/* 36 */       owner.setEnabled(false);
/*    */     try
/*    */     {
/* 39 */       return _showFolderChooser(owner != null ? owner.getNativeHandle() : 0L, folder, title);
/*    */     }
/*    */     finally
/*    */     {
/* 44 */       if (owner != null)
/* 45 */         owner.setEnabled(true);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkCommonDialogs
 * JD-Core Version:    0.6.2
 */