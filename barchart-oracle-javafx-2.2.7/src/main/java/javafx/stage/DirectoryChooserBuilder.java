/*    */ package javafx.stage;
/*    */ 
/*    */ import java.io.File;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class DirectoryChooserBuilder
/*    */   implements Builder<DirectoryChooser>
/*    */ {
/*    */   private int __set;
/*    */   private File initialDirectory;
/*    */   private String title;
/*    */ 
/*    */   public static DirectoryChooserBuilder create()
/*    */   {
/* 15 */     return new DirectoryChooserBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(DirectoryChooser paramDirectoryChooser)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramDirectoryChooser.setInitialDirectory(this.initialDirectory);
/* 22 */     if ((i & 0x2) != 0) paramDirectoryChooser.setTitle(this.title);
/*    */   }
/*    */ 
/*    */   public DirectoryChooserBuilder initialDirectory(File paramFile)
/*    */   {
/* 30 */     this.initialDirectory = paramFile;
/* 31 */     this.__set |= 1;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public DirectoryChooserBuilder title(String paramString)
/*    */   {
/* 40 */     this.title = paramString;
/* 41 */     this.__set |= 2;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   public DirectoryChooser build()
/*    */   {
/* 49 */     DirectoryChooser localDirectoryChooser = new DirectoryChooser();
/* 50 */     applyTo(localDirectoryChooser);
/* 51 */     return localDirectoryChooser;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.DirectoryChooserBuilder
 * JD-Core Version:    0.6.2
 */