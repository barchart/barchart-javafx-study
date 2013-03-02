/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class CommonDialogs
/*     */ {
/*     */   public static List<File> showFileChooser(Window owner, File folder, String title, int type, boolean multipleMode, List<ExtensionFilter> extensionFilters)
/*     */   {
/* 105 */     String _folder = convertFolder(folder);
/*     */ 
/* 107 */     if ((type != 0) && (type != 1)) {
/* 108 */       throw new IllegalArgumentException("Type parameter must be equal to one of the constants from Type");
/*     */     }
/*     */ 
/* 111 */     ExtensionFilter[] _extensionFilters = null;
/* 112 */     if (extensionFilters != null) {
/* 113 */       _extensionFilters = (ExtensionFilter[])extensionFilters.toArray(new ExtensionFilter[0]);
/*     */     }
/*     */ 
/* 116 */     String[] _files = Application.GetApplication().staticCommonDialogs_showFileChooser(owner, _folder, convertTitle(title), type, multipleMode, _extensionFilters);
/*     */ 
/* 119 */     List files = new ArrayList();
/* 120 */     for (String file : _files) {
/* 121 */       files.add(new File(file));
/*     */     }
/* 123 */     return files;
/*     */   }
/*     */ 
/*     */   public static File showFolderChooser(Window owner, File folder, String title)
/*     */   {
/* 137 */     String selectedFolder = Application.GetApplication().staticCommonDialogs_showFolderChooser(owner, convertFolder(folder), convertTitle(title));
/* 138 */     return selectedFolder != null ? new File(selectedFolder) : null;
/*     */   }
/*     */ 
/*     */   private static String convertFolder(File folder) {
/* 142 */     if (folder != null) {
/* 143 */       if (folder.isDirectory()) {
/* 144 */         return folder.getAbsolutePath();
/*     */       }
/* 146 */       throw new IllegalArgumentException("Folder parameter must be a valid folder");
/*     */     }
/*     */ 
/* 150 */     return "";
/*     */   }
/*     */ 
/*     */   private static String convertTitle(String title) {
/* 154 */     return title != null ? title : "";
/*     */   }
/*     */ 
/*     */   public static class ExtensionFilter
/*     */   {
/*     */     private final String description;
/*     */     private final List<String> extensions;
/*     */ 
/*     */     public ExtensionFilter(String description, List<String> extensions)
/*     */     {
/*  55 */       if ((description == null) || (description.length() == 0)) {
/*  56 */         throw new IllegalArgumentException("Description parameter must be non-null and not empty");
/*     */       }
/*     */ 
/*  59 */       if ((extensions == null) || (extensions.isEmpty())) {
/*  60 */         throw new IllegalArgumentException("Extensions parameter must be non-null and not empty");
/*     */       }
/*     */ 
/*  63 */       for (String extension : extensions) {
/*  64 */         if ((extension == null) || (extension.length() == 0)) {
/*  65 */           throw new IllegalArgumentException("Each extension must be non-null and not empty");
/*     */         }
/*     */       }
/*     */ 
/*  69 */       this.description = description;
/*  70 */       this.extensions = extensions;
/*     */     }
/*     */ 
/*     */     public String getDescription() {
/*  74 */       return this.description;
/*     */     }
/*     */ 
/*     */     public List<String> getExtensions() {
/*  78 */       return this.extensions;
/*     */     }
/*     */ 
/*     */     public String[] extensionsToArray() {
/*  82 */       return (String[])this.extensions.toArray(new String[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class Type
/*     */   {
/*     */     public static final int OPEN = 0;
/*     */     public static final int SAVE = 1;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.CommonDialogs
 * JD-Core Version:    0.6.2
 */