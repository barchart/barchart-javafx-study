/*     */ package javafx.scene.input;
/*     */ 
/*     */ import com.sun.javafx.tk.TKClipboard;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javafx.scene.image.Image;
/*     */ import javafx.util.Pair;
/*     */ 
/*     */ public class Clipboard
/*     */ {
/* 117 */   private boolean contentPut = false;
/*     */ 
/* 154 */   private static Clipboard systemClipboard = null;
/*     */   TKClipboard peer;
/*     */ 
/*     */   public static Clipboard getSystemClipboard()
/*     */   {
/* 162 */     if (systemClipboard == null) {
/* 163 */       systemClipboard = new Clipboard(Toolkit.getToolkit().getSystemClipboard());
/*     */     }
/* 165 */     return systemClipboard;
/*     */   }
/*     */ 
/*     */   Clipboard(TKClipboard paramTKClipboard)
/*     */   {
/* 172 */     Toolkit.getToolkit().checkFxUserThread();
/* 173 */     if (paramTKClipboard == null) {
/* 174 */       throw new NullPointerException();
/*     */     }
/* 176 */     paramTKClipboard.initSecurityContext();
/* 177 */     this.peer = paramTKClipboard;
/*     */   }
/*     */ 
/*     */   public final void clear()
/*     */   {
/* 186 */     setContent(null);
/*     */   }
/*     */ 
/*     */   public final Set<DataFormat> getContentTypes()
/*     */   {
/* 198 */     return this.peer.getContentTypes();
/*     */   }
/*     */ 
/*     */   public final boolean setContent(Map<DataFormat, Object> paramMap)
/*     */   {
/* 215 */     Toolkit.getToolkit().checkFxUserThread();
/* 216 */     if (paramMap == null) {
/* 217 */       this.contentPut = false;
/* 218 */       this.peer.putContent(new Pair[0]);
/* 219 */       return true;
/*     */     }
/* 221 */     Pair[] arrayOfPair = new Pair[paramMap.size()];
/* 222 */     int i = 0;
/* 223 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 224 */       arrayOfPair[(i++)] = new Pair(localEntry.getKey(), localEntry.getValue());
/*     */     }
/* 226 */     this.contentPut = this.peer.putContent(arrayOfPair);
/* 227 */     return this.contentPut;
/*     */   }
/*     */ 
/*     */   public final Object getContent(DataFormat paramDataFormat)
/*     */   {
/* 237 */     Toolkit.getToolkit().checkFxUserThread();
/* 238 */     return this.peer.getContent(paramDataFormat);
/*     */   }
/*     */ 
/*     */   public final boolean hasContent(DataFormat paramDataFormat)
/*     */   {
/* 246 */     Toolkit.getToolkit().checkFxUserThread();
/* 247 */     return this.peer.hasContent(paramDataFormat);
/*     */   }
/*     */ 
/*     */   public final boolean hasString()
/*     */   {
/* 256 */     return hasContent(DataFormat.PLAIN_TEXT);
/*     */   }
/*     */ 
/*     */   public final String getString()
/*     */   {
/* 268 */     return (String)getContent(DataFormat.PLAIN_TEXT);
/*     */   }
/*     */ 
/*     */   public final boolean hasUrl()
/*     */   {
/* 277 */     return hasContent(DataFormat.URL);
/*     */   }
/*     */ 
/*     */   public final String getUrl()
/*     */   {
/* 289 */     return (String)getContent(DataFormat.URL);
/*     */   }
/*     */ 
/*     */   public final boolean hasHtml()
/*     */   {
/* 298 */     return hasContent(DataFormat.HTML);
/*     */   }
/*     */ 
/*     */   public final String getHtml()
/*     */   {
/* 310 */     return (String)getContent(DataFormat.HTML);
/*     */   }
/*     */ 
/*     */   public final boolean hasRtf()
/*     */   {
/* 319 */     return hasContent(DataFormat.RTF);
/*     */   }
/*     */ 
/*     */   public final String getRtf()
/*     */   {
/* 331 */     return (String)getContent(DataFormat.RTF);
/*     */   }
/*     */ 
/*     */   public final boolean hasImage()
/*     */   {
/* 340 */     return hasContent(DataFormat.IMAGE);
/*     */   }
/*     */ 
/*     */   public final Image getImage()
/*     */   {
/* 352 */     return (Image)getContent(DataFormat.IMAGE);
/*     */   }
/*     */ 
/*     */   public final boolean hasFiles()
/*     */   {
/* 361 */     return hasContent(DataFormat.FILES);
/*     */   }
/*     */ 
/*     */   public final List<File> getFiles()
/*     */   {
/* 373 */     return (List)getContent(DataFormat.FILES);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public boolean impl_contentPut()
/*     */   {
/* 382 */     return this.contentPut;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.Clipboard
 * JD-Core Version:    0.6.2
 */