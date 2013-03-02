/*     */ package javafx.stage;
/*     */ 
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.io.File;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.property.SimpleStringProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ 
/*     */ public final class DirectoryChooser
/*     */ {
/*     */   private StringProperty title;
/*     */   private ObjectProperty<File> initialDirectory;
/*     */ 
/*     */   public final void setTitle(String paramString)
/*     */   {
/*  50 */     titleProperty().set(paramString);
/*     */   }
/*     */ 
/*     */   public final String getTitle() {
/*  54 */     return this.title != null ? (String)this.title.get() : null;
/*     */   }
/*     */ 
/*     */   public final StringProperty titleProperty() {
/*  58 */     if (this.title == null) {
/*  59 */       this.title = new SimpleStringProperty(this, "title");
/*     */     }
/*     */ 
/*  62 */     return this.title;
/*     */   }
/*     */ 
/*     */   public final void setInitialDirectory(File paramFile)
/*     */   {
/*  71 */     initialDirectoryProperty().set(paramFile);
/*     */   }
/*     */ 
/*     */   public final File getInitialDirectory() {
/*  75 */     return this.initialDirectory != null ? (File)this.initialDirectory.get() : null;
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<File> initialDirectoryProperty() {
/*  79 */     if (this.initialDirectory == null) {
/*  80 */       this.initialDirectory = new SimpleObjectProperty(this, "initialDirectory");
/*     */     }
/*     */ 
/*  84 */     return this.initialDirectory;
/*     */   }
/*     */ 
/*     */   public File showDialog(Window paramWindow)
/*     */   {
/* 100 */     return Toolkit.getToolkit().showDirectoryChooser(paramWindow != null ? paramWindow.impl_getPeer() : null, getTitle(), getInitialDirectory());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.DirectoryChooser
 * JD-Core Version:    0.6.2
 */