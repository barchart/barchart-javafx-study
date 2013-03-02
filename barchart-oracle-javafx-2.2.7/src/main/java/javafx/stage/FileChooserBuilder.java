/*    */ package javafx.stage;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class FileChooserBuilder
/*    */   implements Builder<FileChooser>
/*    */ {
/*    */   private int __set;
/*    */   private Collection<? extends FileChooser.ExtensionFilter> extensionFilters;
/*    */   private File initialDirectory;
/*    */   private String title;
/*    */ 
/*    */   public static FileChooserBuilder create()
/*    */   {
/* 15 */     return new FileChooserBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(FileChooser paramFileChooser)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramFileChooser.getExtensionFilters().addAll(this.extensionFilters);
/* 22 */     if ((i & 0x2) != 0) paramFileChooser.setInitialDirectory(this.initialDirectory);
/* 23 */     if ((i & 0x4) != 0) paramFileChooser.setTitle(this.title);
/*    */   }
/*    */ 
/*    */   public FileChooserBuilder extensionFilters(Collection<? extends FileChooser.ExtensionFilter> paramCollection)
/*    */   {
/* 31 */     this.extensionFilters = paramCollection;
/* 32 */     this.__set |= 1;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public FileChooserBuilder extensionFilters(FileChooser.ExtensionFilter[] paramArrayOfExtensionFilter)
/*    */   {
/* 40 */     return extensionFilters(Arrays.asList(paramArrayOfExtensionFilter));
/*    */   }
/*    */ 
/*    */   public FileChooserBuilder initialDirectory(File paramFile)
/*    */   {
/* 48 */     this.initialDirectory = paramFile;
/* 49 */     this.__set |= 2;
/* 50 */     return this;
/*    */   }
/*    */ 
/*    */   public FileChooserBuilder title(String paramString)
/*    */   {
/* 58 */     this.title = paramString;
/* 59 */     this.__set |= 4;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   public FileChooser build()
/*    */   {
/* 67 */     FileChooser localFileChooser = new FileChooser();
/* 68 */     applyTo(localFileChooser);
/* 69 */     return localFileChooser;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.FileChooserBuilder
 * JD-Core Version:    0.6.2
 */