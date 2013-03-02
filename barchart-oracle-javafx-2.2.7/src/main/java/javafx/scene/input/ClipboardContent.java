/*     */ package javafx.scene.input;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javafx.scene.image.Image;
/*     */ 
/*     */ public class ClipboardContent extends HashMap<DataFormat, Object>
/*     */ {
/*     */   public final boolean hasString()
/*     */   {
/*  45 */     return containsKey(DataFormat.PLAIN_TEXT);
/*     */   }
/*     */ 
/*     */   public final boolean putString(String paramString)
/*     */   {
/*  57 */     if (paramString == null) {
/*  58 */       throw new NullPointerException("Null string put on ClipboardContent");
/*     */     }
/*  60 */     return put(DataFormat.PLAIN_TEXT, paramString) == paramString;
/*     */   }
/*     */ 
/*     */   public final String getString()
/*     */   {
/*  72 */     return (String)get(DataFormat.PLAIN_TEXT);
/*     */   }
/*     */ 
/*     */   public final boolean hasUrl()
/*     */   {
/*  81 */     return containsKey(DataFormat.URL);
/*     */   }
/*     */ 
/*     */   public final boolean putUrl(String paramString)
/*     */   {
/*  93 */     if (paramString == null) {
/*  94 */       throw new NullPointerException("Null URL put on ClipboardContent");
/*     */     }
/*  96 */     return put(DataFormat.URL, paramString) == paramString;
/*     */   }
/*     */ 
/*     */   public final String getUrl()
/*     */   {
/* 108 */     return (String)get(DataFormat.URL);
/*     */   }
/*     */ 
/*     */   public final boolean hasHtml()
/*     */   {
/* 117 */     return containsKey(DataFormat.HTML);
/*     */   }
/*     */ 
/*     */   public final boolean putHtml(String paramString)
/*     */   {
/* 129 */     if (paramString == null) {
/* 130 */       throw new NullPointerException("Null HTML put on ClipboardContent");
/*     */     }
/* 132 */     return put(DataFormat.HTML, paramString) == paramString;
/*     */   }
/*     */ 
/*     */   public final String getHtml()
/*     */   {
/* 144 */     return (String)get(DataFormat.HTML);
/*     */   }
/*     */ 
/*     */   public final boolean hasRtf()
/*     */   {
/* 153 */     return containsKey(DataFormat.RTF);
/*     */   }
/*     */ 
/*     */   public final boolean putRtf(String paramString)
/*     */   {
/* 165 */     if (paramString == null) {
/* 166 */       throw new NullPointerException("Null RTF put on ClipboardContent");
/*     */     }
/* 168 */     return put(DataFormat.RTF, paramString) == paramString;
/*     */   }
/*     */ 
/*     */   public final String getRtf()
/*     */   {
/* 180 */     return (String)get(DataFormat.RTF);
/*     */   }
/*     */ 
/*     */   public final boolean hasImage()
/*     */   {
/* 189 */     return containsKey(DataFormat.IMAGE);
/*     */   }
/*     */ 
/*     */   public final boolean putImage(Image paramImage)
/*     */   {
/* 204 */     if (paramImage == null) {
/* 205 */       throw new NullPointerException("Null image put on ClipboardContent");
/*     */     }
/* 207 */     return put(DataFormat.IMAGE, paramImage) == paramImage;
/*     */   }
/*     */ 
/*     */   public final Image getImage()
/*     */   {
/* 219 */     return (Image)get(DataFormat.IMAGE);
/*     */   }
/*     */ 
/*     */   public final boolean hasFiles()
/*     */   {
/* 228 */     return containsKey(DataFormat.FILES);
/*     */   }
/*     */ 
/*     */   public final boolean putFiles(List<File> paramList)
/*     */   {
/* 240 */     if (paramList == null) {
/* 241 */       throw new NullPointerException("Null reference to files put on ClipboardContent");
/*     */     }
/*     */ 
/* 244 */     return put(DataFormat.FILES, paramList) == paramList;
/*     */   }
/*     */ 
/*     */   public final boolean putFilesByPath(List<String> paramList)
/*     */   {
/* 257 */     ArrayList localArrayList = new ArrayList(paramList.size());
/* 258 */     for (String str : paramList) {
/* 259 */       localArrayList.add(new File(str));
/*     */     }
/* 261 */     return putFiles(localArrayList);
/*     */   }
/*     */ 
/*     */   public final List<File> getFiles()
/*     */   {
/* 273 */     return (List)get(DataFormat.FILES);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.ClipboardContent
 * JD-Core Version:    0.6.2
 */