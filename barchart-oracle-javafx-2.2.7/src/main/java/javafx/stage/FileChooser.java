/*     */ package javafx.stage;
/*     */ 
/*     */ import com.sun.javafx.tk.FileChooserType;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.io.File;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.property.SimpleStringProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public final class FileChooser
/*     */ {
/*     */   private StringProperty title;
/*     */   private ObjectProperty<File> initialDirectory;
/* 211 */   private ObservableList<ExtensionFilter> extensionFilters = FXCollections.observableArrayList();
/*     */ 
/*     */   public final void setTitle(String paramString)
/*     */   {
/* 171 */     titleProperty().set(paramString);
/*     */   }
/*     */ 
/*     */   public final String getTitle() {
/* 175 */     return this.title != null ? (String)this.title.get() : null;
/*     */   }
/*     */ 
/*     */   public final StringProperty titleProperty() {
/* 179 */     if (this.title == null) {
/* 180 */       this.title = new SimpleStringProperty(this, "title");
/*     */     }
/*     */ 
/* 183 */     return this.title;
/*     */   }
/*     */ 
/*     */   public final void setInitialDirectory(File paramFile)
/*     */   {
/* 192 */     initialDirectoryProperty().set(paramFile);
/*     */   }
/*     */ 
/*     */   public final File getInitialDirectory() {
/* 196 */     return this.initialDirectory != null ? (File)this.initialDirectory.get() : null;
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<File> initialDirectoryProperty() {
/* 200 */     if (this.initialDirectory == null) {
/* 201 */       this.initialDirectory = new SimpleObjectProperty(this, "initialDirectory");
/*     */     }
/*     */ 
/* 205 */     return this.initialDirectory;
/*     */   }
/*     */ 
/*     */   public ObservableList<ExtensionFilter> getExtensionFilters()
/*     */   {
/* 219 */     return this.extensionFilters;
/*     */   }
/*     */ 
/*     */   public File showOpenDialog(Window paramWindow)
/*     */   {
/* 234 */     List localList = showDialog(paramWindow, FileChooserType.OPEN);
/*     */ 
/* 237 */     return (localList != null) && (localList.size() > 0) ? (File)localList.get(0) : null;
/*     */   }
/*     */ 
/*     */   public List<File> showOpenMultipleDialog(Window paramWindow)
/*     */   {
/* 257 */     List localList = showDialog(paramWindow, FileChooserType.OPEN_MULTIPLE);
/*     */ 
/* 260 */     return (localList != null) && (localList.size() > 0) ? Collections.unmodifiableList(localList) : null;
/*     */   }
/*     */ 
/*     */   public File showSaveDialog(Window paramWindow)
/*     */   {
/* 276 */     List localList = showDialog(paramWindow, FileChooserType.SAVE);
/*     */ 
/* 279 */     return (localList != null) && (localList.size() > 0) ? (File)localList.get(0) : null;
/*     */   }
/*     */ 
/*     */   private List<File> showDialog(Window paramWindow, FileChooserType paramFileChooserType)
/*     */   {
/* 285 */     return Toolkit.getToolkit().showFileChooser(paramWindow != null ? paramWindow.impl_getPeer() : null, getTitle(), getInitialDirectory(), paramFileChooserType, this.extensionFilters);
/*     */   }
/*     */ 
/*     */   public static final class ExtensionFilter
/*     */   {
/*     */     private final String description;
/*     */     private final List<String> extensions;
/*     */ 
/*     */     public ExtensionFilter(String paramString, String[] paramArrayOfString)
/*     */     {
/*  75 */       validateArgs(paramString, paramArrayOfString);
/*     */ 
/*  77 */       this.description = paramString;
/*  78 */       this.extensions = Collections.unmodifiableList(Arrays.asList((Object[])paramArrayOfString.clone()));
/*     */     }
/*     */ 
/*     */     public ExtensionFilter(String paramString, List<String> paramList)
/*     */     {
/*  98 */       String[] arrayOfString = paramList != null ? (String[])paramList.toArray(new String[paramList.size()]) : null;
/*     */ 
/* 102 */       validateArgs(paramString, arrayOfString);
/*     */ 
/* 104 */       this.description = paramString;
/* 105 */       this.extensions = Collections.unmodifiableList(Arrays.asList(arrayOfString));
/*     */     }
/*     */ 
/*     */     public String getDescription()
/*     */     {
/* 115 */       return this.description;
/*     */     }
/*     */ 
/*     */     public List<String> getExtensions()
/*     */     {
/* 128 */       return this.extensions;
/*     */     }
/*     */ 
/*     */     private static void validateArgs(String paramString, String[] paramArrayOfString)
/*     */     {
/* 133 */       if (paramString == null) {
/* 134 */         throw new NullPointerException("Description must not be null");
/*     */       }
/*     */ 
/* 137 */       if (paramString.isEmpty()) {
/* 138 */         throw new IllegalArgumentException("Description must not be empty");
/*     */       }
/*     */ 
/* 142 */       if (paramArrayOfString == null) {
/* 143 */         throw new NullPointerException("Extensions must not be null");
/*     */       }
/*     */ 
/* 146 */       if (paramArrayOfString.length == 0) {
/* 147 */         throw new IllegalArgumentException("At least one extension must be defined");
/*     */       }
/*     */ 
/* 151 */       for (String str : paramArrayOfString) {
/* 152 */         if (str == null) {
/* 153 */           throw new NullPointerException("Extension must not be null");
/*     */         }
/*     */ 
/* 157 */         if (str.isEmpty())
/* 158 */           throw new IllegalArgumentException("Extension must not be empty");
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.FileChooser
 * JD-Core Version:    0.6.2
 */