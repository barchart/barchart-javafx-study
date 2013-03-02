/*     */ package com.sun.glass.ui.win;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.SystemClipboard;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class WinSystemClipboard extends SystemClipboard
/*     */ {
/*  30 */   protected long ptr = 0L;
/*     */ 
/*  51 */   static final byte[] terminator = { 0, 0 };
/*     */   static final String defaultCharset = "UTF-16LE";
/*     */   static final String RTFCharset = "US-ASCII";
/*     */ 
/*     */   static native void initIDs();
/*     */ 
/*     */   protected WinSystemClipboard(String name)
/*     */   {
/*  32 */     super(name);
/*  33 */     create();
/*     */   }
/*     */ 
/*     */   protected native boolean isOwner();
/*     */ 
/*     */   protected native void create();
/*     */ 
/*     */   protected native void dispose();
/*     */ 
/*     */   protected native void push(Object[] paramArrayOfObject, int paramInt);
/*     */ 
/*     */   protected native boolean pop();
/*     */ 
/*     */   protected byte[] fosSerialize(String mime, long index)
/*     */   {
/*  56 */     Object data = getLocalData(mime);
/*  57 */     if ((data instanceof ByteBuffer)) {
/*  58 */       byte[] b = ((ByteBuffer)data).array();
/*  59 */       if ("text/html".equals(mime)) {
/*  60 */         b = WinHTMLCodec.encode(b);
/*     */       }
/*  62 */       return b;
/*  63 */     }if ((data instanceof String)) {
/*  64 */       String st = ((String)data).replace("\n", "\r\n");
/*  65 */       if ("text/html".equals(mime))
/*     */         try
/*     */         {
/*  68 */           byte[] bytes = st.getBytes("UTF-8");
/*  69 */           ByteBuffer ba = ByteBuffer.allocate(bytes.length + 1);
/*  70 */           ba.put(bytes);
/*  71 */           ba.put((byte)0);
/*     */ 
/*  73 */           return WinHTMLCodec.encode(ba.array());
/*     */         }
/*     */         catch (UnsupportedEncodingException ex) {
/*  76 */           return null;
/*     */         }
/*  78 */       if ("text/rtf".equals(mime)) {
/*     */         try
/*     */         {
/*  81 */           byte[] bytes = st.getBytes("US-ASCII");
/*  82 */           ByteBuffer ba = ByteBuffer.allocate(bytes.length + 1);
/*  83 */           ba.put(bytes);
/*  84 */           ba.put((byte)0);
/*  85 */           return ba.array();
/*     */         }
/*     */         catch (UnsupportedEncodingException ex) {
/*  88 */           return null;
/*     */         }
/*     */       }
/*  91 */       ByteBuffer ba = ByteBuffer.allocate((st.length() + 1) * 2);
/*     */       try {
/*  93 */         ba.put(st.getBytes("UTF-16LE"));
/*     */       }
/*     */       catch (UnsupportedEncodingException ex) {
/*     */       }
/*  97 */       ba.put(terminator);
/*  98 */       return ba.array();
/*     */     }
/* 100 */     if ("application/x-java-file-list".equals(mime)) {
/* 101 */       String[] ast = (String[])data;
/* 102 */       if ((ast != null) && (ast.length > 0)) {
/* 103 */         int size = 0;
/* 104 */         for (String st : ast) {
/* 105 */           size += (st.length() + 1) * 2;
/*     */         }
/* 107 */         size += 2;
/*     */         try {
/* 109 */           ByteBuffer ba = ByteBuffer.allocate(size);
/* 110 */           for (String st : ast) {
/* 111 */             ba.put(st.getBytes("UTF-16LE"));
/* 112 */             ba.put(terminator);
/*     */           }
/* 114 */           ba.put(terminator);
/* 115 */           return ba.array();
/*     */         } catch (UnsupportedEncodingException ex) {
/*     */         }
/*     */       }
/*     */     }
/* 120 */     else if ("application/x-java-rawimage".equals(mime)) {
/* 121 */       Pixels pxls = (Pixels)data;
/* 122 */       if (pxls != null) {
/* 123 */         ByteBuffer ba = ByteBuffer.allocate(pxls.getWidth() * pxls.getHeight() * 4 + 8);
/*     */ 
/* 125 */         ba.putInt(pxls.getWidth());
/* 126 */         ba.putInt(pxls.getHeight());
/* 127 */         ba.put(pxls.asByteBuffer());
/* 128 */         return ba.array();
/*     */       }
/*     */     }
/*     */ 
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */   protected void pushToSystem(HashMap<String, Object> cacheData, int supportedActions)
/*     */   {
/* 201 */     Set mimes = cacheData.keySet();
/* 202 */     Set mimesForSystem = new HashSet();
/* 203 */     MimeTypeParser parser = new MimeTypeParser();
/* 204 */     for (String mime : mimes) {
/* 205 */       parser.parse(mime);
/* 206 */       if (!parser.isInMemoryFile())
/*     */       {
/* 210 */         mimesForSystem.add(mime);
/*     */       }
/*     */     }
/* 213 */     push(mimesForSystem.toArray(), supportedActions);
/*     */   }
/*     */ 
/*     */   protected native byte[] popBytes(String paramString, long paramLong);
/*     */ 
/*     */   protected Object popFromSystem(String mimeFull)
/*     */   {
/* 222 */     if (!pop()) {
/* 223 */       return null;
/*     */     }
/*     */ 
/* 226 */     MimeTypeParser parser = new MimeTypeParser(mimeFull);
/* 227 */     String mime = parser.getMime();
/* 228 */     byte[] data = popBytes(mime, parser.getIndex());
/* 229 */     if (data != null) {
/* 230 */       if (("text/plain".equals(mime)) || ("text/uri-list".equals(mime)))
/*     */       {
/*     */         try {
/* 233 */           return new String(data, 0, data.length - 2, "UTF-16LE");
/*     */         } catch (UnsupportedEncodingException ex) {
/*     */         }
/*     */       }
/* 237 */       else if ("text/html".equals(mime)) {
/*     */         try {
/* 239 */           data = WinHTMLCodec.decode(data);
/* 240 */           return new String(data, 0, data.length, "UTF-8");
/*     */         } catch (UnsupportedEncodingException ex) {
/*     */         }
/*     */       }
/* 244 */       else if ("text/rtf".equals(mime)) {
/*     */         try {
/* 246 */           return new String(data, 0, data.length, "US-ASCII");
/*     */         } catch (UnsupportedEncodingException ex) {
/*     */         }
/*     */       }
/* 250 */       else if ("application/x-java-file-list".equals(mime)) {
/*     */         try {
/* 252 */           String st = new String(data, 0, data.length, "UTF-16LE");
/* 253 */           return st.split("");
/*     */         } catch (UnsupportedEncodingException ex) {
/*     */         }
/*     */       } else {
/* 257 */         if ("application/x-java-rawimage".equals(mime)) {
/* 258 */           ByteBuffer size = ByteBuffer.wrap(data, 0, 8);
/* 259 */           return Application.GetApplication().createPixels(size.getInt(), size.getInt(), ByteBuffer.wrap(data, 8, data.length - 8));
/*     */         }
/* 261 */         return ByteBuffer.wrap(data);
/*     */       }
/*     */ 
/*     */     }
/* 265 */     else if ("text/uri-list".equals(mime)) {
/* 266 */       String[] ret = (String[])popFromSystem("application/x-java-file-list");
/* 267 */       if (ret != null) {
/* 268 */         StringBuilder out = new StringBuilder();
/*     */ 
/* 270 */         for (int i = 0; i < ret.length; i++) {
/* 271 */           String fileName = ret[i];
/* 272 */           fileName = fileName.replace("\\", "/");
/*     */ 
/* 274 */           if (out.length() > 0) {
/* 275 */             out.append("\r\n");
/*     */           }
/* 277 */           out.append("file:/").append(fileName);
/*     */         }
/* 279 */         return out.toString();
/*     */       }
/*     */     }
/*     */ 
/* 283 */     return null;
/*     */   }
/*     */ 
/*     */   protected native String[] popMimesFromSystem();
/*     */ 
/*     */   protected String[] mimesFromSystem()
/*     */   {
/* 290 */     if (!pop()) {
/* 291 */       return null;
/*     */     }
/* 293 */     return popMimesFromSystem();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 297 */     return "Windows System Clipboard";
/*     */   }
/*     */ 
/*     */   protected void close() {
/* 301 */     dispose();
/* 302 */     this.ptr = 0L;
/*     */   }
/*     */ 
/*     */   public long getPtr()
/*     */   {
/* 309 */     return this.ptr;
/*     */   }
/*     */   protected native void pushTargetActionToSystem(int paramInt);
/*     */ 
/*     */   protected native int popSupportedSourceActions();
/*     */ 
/*     */   protected int supportedSourceActionsFromSystem() {
/* 316 */     if (!pop()) {
/* 317 */       return 0;
/*     */     }
/* 319 */     return popSupportedSourceActions();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  20 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  23 */         Application.loadNativeLibrary();
/*  24 */         return null;
/*     */       }
/*     */     });
/*  27 */     initIDs();
/*     */   }
/*     */ 
/*     */   private static final class MimeTypeParser
/*     */   {
/*     */     protected static final String externalBodyMime = "message/external-body";
/*     */     protected String mime;
/*     */     protected boolean bInMemoryFile;
/*     */     protected int index;
/*     */ 
/*     */     public MimeTypeParser()
/*     */     {
/* 142 */       parse("");
/*     */     }
/*     */ 
/*     */     public MimeTypeParser(String mimeFull) {
/* 146 */       parse(mimeFull);
/*     */     }
/*     */ 
/*     */     public void parse(String mimeFull) {
/* 150 */       this.mime = mimeFull;
/* 151 */       this.bInMemoryFile = false;
/* 152 */       this.index = -1;
/*     */ 
/* 155 */       if (mimeFull.startsWith("message/external-body")) {
/* 156 */         String[] mimeParts = mimeFull.split(";");
/* 157 */         String accessType = "";
/* 158 */         int indexValue = -1;
/*     */ 
/* 160 */         for (int i = 1; i < mimeParts.length; i++) {
/* 161 */           String[] params = mimeParts[i].split("=");
/* 162 */           if (params.length == 2) {
/* 163 */             if (params[0].trim().equalsIgnoreCase("index"))
/*     */             {
/* 166 */               indexValue = Integer.parseInt(params[1].trim());
/* 167 */             } else if (params[0].trim().equalsIgnoreCase("access-type")) {
/* 168 */               accessType = params[1].trim();
/*     */             }
/*     */           }
/* 171 */           if ((indexValue != -1) && (!accessType.isEmpty()))
/*     */           {
/*     */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 179 */         if (accessType.equalsIgnoreCase("clipboard")) {
/* 180 */           this.bInMemoryFile = true;
/* 181 */           this.mime = mimeParts[0];
/* 182 */           this.index = indexValue;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public String getMime() {
/* 188 */       return this.mime;
/*     */     }
/*     */ 
/*     */     public int getIndex() {
/* 192 */       return this.index;
/*     */     }
/*     */ 
/*     */     public boolean isInMemoryFile() {
/* 196 */       return this.bInMemoryFile;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinSystemClipboard
 * JD-Core Version:    0.6.2
 */