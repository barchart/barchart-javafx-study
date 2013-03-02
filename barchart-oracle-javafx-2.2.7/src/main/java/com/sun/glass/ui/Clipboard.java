/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import com.sun.glass.ui.delegate.ClipboardDelegate;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Clipboard
/*     */ {
/*     */   public static final String TEXT_TYPE = "text/plain";
/*     */   public static final String HTML_TYPE = "text/html";
/*     */   public static final String RTF_TYPE = "text/rtf";
/*     */   public static final String URI_TYPE = "text/uri-list";
/*     */   public static final String FILE_LIST_TYPE = "application/x-java-file-list";
/*     */   public static final String RAW_IMAGE_TYPE = "application/x-java-rawimage";
/*     */   public static final int ACTION_NONE = 0;
/*     */   public static final int ACTION_COPY = 1;
/*     */   public static final int ACTION_MOVE = 2;
/*     */   public static final int ACTION_REFERENCE = 1073741824;
/*     */   public static final int ACTION_COPY_OR_MOVE = 3;
/*     */   public static final int ACTION_ANY = 1342177279;
/*     */   public static final String DND = "DND";
/*     */   public static final String SYSTEM = "SYSTEM";
/*     */   public static final String SELECTION = "SELECTION";
/*  43 */   private static final Map<String, Clipboard> clipboards = new HashMap();
/*  44 */   private static final ClipboardDelegate delegate = PlatformFactory.getPlatformFactory().createClipboardDelegate();
/*     */ 
/*  46 */   private final HashSet<ClipboardAssistance> assistants = new HashSet();
/*     */   private final String name;
/*  48 */   private final Object localDataProtector = new Object();
/*     */   private HashMap<String, Object> localSharedData;
/*     */   private ClipboardAssistance dataSource;
/*  55 */   protected int supportedActions = 1;
/*     */ 
/*     */   protected Clipboard(String name) {
/*  58 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public void add(ClipboardAssistance assistant) {
/*  62 */     synchronized (this.assistants) {
/*  63 */       this.assistants.add(assistant);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void remove(ClipboardAssistance assistant) {
/*  68 */     synchronized (this.localDataProtector) {
/*  69 */       if (assistant == this.dataSource)
/*  70 */         this.dataSource = null;
/*     */     }
/*     */     boolean needClose;
/*  74 */     synchronized (this.assistants) {
/*  75 */       this.assistants.remove(assistant);
/*  76 */       needClose = this.assistants.isEmpty();
/*     */     }
/*     */ 
/*  79 */     if (needClose) {
/*  80 */       synchronized (clipboards) {
/*  81 */         clipboards.remove(this.name);
/*     */       }
/*  83 */       close();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setSharedData(ClipboardAssistance dataSource, HashMap<String, Object> cacheData, int supportedActions)
/*     */   {
/*  92 */     synchronized (this.localDataProtector) {
/*  93 */       this.localSharedData = ((HashMap)cacheData.clone());
/*  94 */       this.supportedActions = supportedActions;
/*  95 */       this.dataSource = dataSource;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void flush(ClipboardAssistance dataSource, HashMap<String, Object> cacheData, int supportedActions)
/*     */   {
/* 109 */     setSharedData(dataSource, cacheData, supportedActions);
/* 110 */     contentChanged();
/*     */   }
/*     */ 
/*     */   public int getSupportedSourceActions() {
/* 114 */     return this.supportedActions;
/*     */   }
/*     */ 
/*     */   public void setTargetAction(int actionDone) {
/* 118 */     actionPerformed(actionDone);
/*     */   }
/*     */ 
/*     */   public void contentChanged()
/*     */   {
/*     */     HashSet _assistants;
/* 123 */     synchronized (this.assistants) {
/* 124 */       _assistants = (HashSet)this.assistants.clone();
/*     */     }
/* 126 */     for (ClipboardAssistance assistant : _assistants)
/* 127 */       assistant.contentChanged();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(int action)
/*     */   {
/* 137 */     synchronized (this.localDataProtector) {
/* 138 */       if (null != this.dataSource)
/* 139 */         this.dataSource.actionPerformed(action);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getData(String mimeType)
/*     */   {
/* 146 */     synchronized (this.localDataProtector) {
/* 147 */       if (this.localSharedData == null) {
/* 148 */         return null;
/*     */       }
/* 150 */       Object ret = this.localSharedData.get(mimeType);
/* 151 */       return (ret instanceof DelayedCallback) ? ((DelayedCallback)ret).providedData() : ret;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String[] getMimeTypes()
/*     */   {
/* 158 */     synchronized (this.localDataProtector) {
/* 159 */       if (this.localSharedData == null) {
/* 160 */         return null;
/*     */       }
/* 162 */       Set mimes = this.localSharedData.keySet();
/* 163 */       String[] ret = new String[mimes.size()];
/* 164 */       int i = 0;
/* 165 */       for (String mime : mimes) {
/* 166 */         ret[(i++)] = mime;
/*     */       }
/* 168 */       return ret;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static Clipboard get(String clipboardName)
/*     */   {
/* 177 */     synchronized (clipboards) {
/* 178 */       if (!clipboards.keySet().contains(clipboardName)) {
/* 179 */         Clipboard newClipboard = delegate.createClipboard(clipboardName);
/* 180 */         if (newClipboard == null) {
/* 181 */           newClipboard = new Clipboard(clipboardName);
/*     */         }
/* 183 */         clipboards.put(clipboardName, newClipboard);
/*     */       }
/* 185 */       return (Clipboard)clipboards.get(clipboardName);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Pixels getPixelsForRawImage(byte[] rawimage) {
/* 190 */     ByteBuffer size = ByteBuffer.wrap(rawimage, 0, 8);
/* 191 */     int width = size.getInt();
/* 192 */     int height = size.getInt();
/*     */ 
/* 194 */     ByteBuffer pixels = ByteBuffer.wrap(rawimage, 8, rawimage.length - 8);
/* 195 */     return Application.GetApplication().createPixels(width, height, pixels.slice());
/*     */   }
/*     */ 
/*     */   public byte[] getRawImageForPixels(Pixels pixels) {
/* 199 */     int width = pixels.getWidth();
/* 200 */     int height = pixels.getHeight();
/*     */ 
/* 202 */     ByteBuffer bytes = ByteBuffer.allocateDirect(8 + width * height * 4);
/* 203 */     bytes.putInt(width);
/* 204 */     bytes.putInt(height);
/*     */ 
/* 206 */     ByteBuffer rawPixels = bytes.duplicate().slice();
/* 207 */     pixels.asByteBuffer(rawPixels);
/*     */ 
/* 209 */     bytes.rewind();
/* 210 */     return bytes.array();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 214 */     return "Clipboard: " + this.name + "@" + hashCode();
/*     */   }
/*     */ 
/*     */   protected void close() {
/* 218 */     synchronized (this.localDataProtector) {
/* 219 */       this.dataSource = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 224 */     return this.name;
/*     */   }
/*     */ 
/*     */   public static String getActionString(int action) {
/* 228 */     StringBuilder ret = new StringBuilder("");
/* 229 */     int[] test = { 1, 2, 1073741824 };
/*     */ 
/* 233 */     String[] canDo = { "copy", "move", "link" };
/*     */ 
/* 237 */     for (int i = 0; i < 3; i++) {
/* 238 */       if ((test[i] & action) > 0) {
/* 239 */         if (ret.length() > 0) {
/* 240 */           ret.append(",");
/*     */         }
/* 242 */         ret.append(canDo[i]);
/*     */       }
/*     */     }
/* 245 */     return ret.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Clipboard
 * JD-Core Version:    0.6.2
 */