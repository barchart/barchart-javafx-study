/*     */ package com.sun.glass.ui.mac;
/*     */ 
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.SystemClipboard;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class MacSystemClipboard extends SystemClipboard
/*     */ {
/*     */   static final String FILE_SCHEME = "file";
/*     */   private static final String BAD_URI_MSG = "bad URI in com.sun.glass.ui.mac.MacSystemClipboard for file: ";
/*     */   private static final String BAD_URL_MSG = "bad URL in com.sun.glass.ui.mac.MacSystemClipboard for file: ";
/*     */   static final boolean SUPPORT_10_5_API = true;
/*     */   static final boolean SUPPORT_10_5_API_FORCE = false;
/*     */   static final boolean SUPPORT_10_6_API = false;
/*  35 */   long seed = 0L;
/*     */   final MacPasteboard pasteboard;
/* 408 */   private static HashMap utm = null;
/*     */ 
/* 427 */   private static HashMap mtu = null;
/*     */ 
/*     */   public MacSystemClipboard(String name)
/*     */   {
/*  39 */     super(name);
/*  40 */     if (name.equals("DND") == true)
/*  41 */       this.pasteboard = new MacPasteboard(2);
/*  42 */     else if (name.equals("SYSTEM") == true)
/*  43 */       this.pasteboard = new MacPasteboard(1);
/*     */     else
/*  45 */       this.pasteboard = new MacPasteboard(name);
/*     */   }
/*     */ 
/*     */   protected boolean isOwner()
/*     */   {
/*  51 */     return this.seed == this.pasteboard.getSeed();
/*     */   }
/*     */ 
/*     */   protected int supportedSourceActionsFromSystem()
/*     */   {
/*  56 */     return this.pasteboard.getAllowedOperation();
/*     */   }
/*     */ 
/*     */   protected void pushTargetActionToSystem(int actionDone)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void pushToSystem(HashMap<String, Object> data, int supportedActions)
/*     */   {
/*  67 */     HashMap itemFirst = null;
/*  68 */     HashMap[] itemList = null;
/*     */ 
/*  70 */     Set keys = data.keySet();
/*     */ 
/*  73 */     Iterator iterator = keys.iterator();
/*  74 */     while (iterator.hasNext() == true) {
/*  75 */       String mime = (String)iterator.next();
/*  76 */       Object object = data.get(mime);
/*     */ 
/*  78 */       if (object != null) {
/*  79 */         if (mime.equals("text/uri-list") == true)
/*     */         {
/*  81 */           String list = (String)object;
/*  82 */           String[] split = list.split("\n");
/*  83 */           int count = 0;
/*  84 */           for (int i = 0; i < split.length; i++) {
/*  85 */             String string = split[i];
/*  86 */             if (!string.startsWith("#"))
/*     */             {
/*  88 */               count++;
/*     */             }
/*     */           }
/*     */ 
/*  92 */           if (count > 0) {
/*  93 */             itemList = new HashMap[count];
/*  94 */             count = 0;
/*  95 */             for (int i = 0; i < split.length; i++) {
/*  96 */               String file = split[i];
/*  97 */               if (!file.startsWith("#"))
/*     */               {
/* 101 */                 URI uri = createUri(file, "bad URI in com.sun.glass.ui.mac.MacSystemClipboard for file: ");
/* 102 */                 String utf = "public.url";
/* 103 */                 if (uri.getScheme() == null)
/*     */                 {
/* 105 */                   utf = "public.file-url";
/* 106 */                   uri = createUri("file", uri.getPath(), "bad URI in com.sun.glass.ui.mac.MacSystemClipboard for file: ");
/*     */                 }
/* 108 */                 itemList[count] = new HashMap();
/* 109 */                 itemList[count].put(utf, uri.toASCIIString());
/* 110 */                 count++;
/*     */               }
/*     */             }
/*     */           }
/* 114 */         } else if (mime.equals("application/x-java-rawimage") == true) {
/* 115 */           Pixels pixels = (Pixels)object;
/* 116 */           if (itemFirst == null) {
/* 117 */             itemFirst = new HashMap();
/*     */           }
/* 119 */           itemFirst.put(mimeToUtf(mime), pixels);
/* 120 */         } else if ((mime.equals("text/plain") == true) || (mime.equals("text/html") == true) || (mime.equals("text/rtf") == true))
/*     */         {
/* 123 */           if ((object instanceof String)) {
/* 124 */             String string = (String)object;
/* 125 */             if (itemFirst == null) {
/* 126 */               itemFirst = new HashMap();
/*     */             }
/* 128 */             itemFirst.put(mimeToUtf(mime), string);
/*     */           }
/*     */           else
/*     */           {
/* 133 */             System.err.println("DelayedCallback not implemented yet: RT-14593");
/* 134 */             Thread.dumpStack();
/*     */           }
/* 136 */         } else if (mime.equals("application/x-java-file-list"))
/*     */         {
/* 139 */           String[] files = (String[])object;
/* 140 */           if (data.get("text/uri-list") == null)
/*     */           {
/* 142 */             itemList = new HashMap[files.length];
/* 143 */             for (int i = 0; i < files.length; i++) {
/* 144 */               String file = files[i];
/* 145 */               URI uri = createUri(file, "bad URI in com.sun.glass.ui.mac.MacSystemClipboard for file: ");
/* 146 */               String utf = "public.url";
/* 147 */               if (uri.getScheme() == null)
/*     */               {
/* 149 */                 utf = "public.file-url";
/* 150 */                 uri = createUri("file", uri.getPath(), "bad URI in com.sun.glass.ui.mac.MacSystemClipboard for file: ");
/*     */               }
/* 152 */               itemList[i] = new HashMap();
/* 153 */               itemList[i].put(utf, uri.toASCIIString());
/*     */             }
/*     */           }
/*     */           else {
/* 157 */             if (itemFirst == null) {
/* 158 */               itemFirst = new HashMap();
/*     */             }
/* 160 */             StringBuilder string = null;
/* 161 */             for (int i = 0; i < files.length; i++) {
/* 162 */               String file = files[i];
/* 163 */               URI uri = createUri(file, "bad URI in com.sun.glass.ui.mac.MacSystemClipboard for file: ");
/* 164 */               if (string == null) {
/* 165 */                 string = new StringBuilder();
/*     */               }
/* 167 */               string.append(uri.getPath());
/* 168 */               if (i < files.length - 1) {
/* 169 */                 string.append("\n");
/*     */               }
/*     */             }
/* 172 */             if ((string != null) && 
/* 173 */               (itemFirst.get("public.utf8-plain-text") == null)) {
/* 174 */               itemFirst.remove("public.utf8-plain-text");
/* 175 */               itemFirst.put("public.utf8-plain-text", string.toString());
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 182 */           if (itemFirst == null) {
/* 183 */             itemFirst = new HashMap();
/*     */           }
/* 185 */           itemFirst.put(mimeToUtf(mime), serialize(object));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 216 */     if (itemFirst != null) {
/* 217 */       if (itemList == null) {
/* 218 */         itemList = new HashMap[1];
/* 219 */         itemList[0] = itemFirst;
/*     */       } else {
/* 221 */         HashMap temp = itemList[0];
/* 222 */         itemList[0] = itemFirst;
/* 223 */         iterator = temp.keySet().iterator();
/* 224 */         while (iterator.hasNext() == true) {
/* 225 */           String utf = (String)iterator.next();
/* 226 */           Object object = temp.get(utf);
/* 227 */           itemList[0].put(utf, object);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 247 */     if (itemList != null)
/* 248 */       this.seed = this.pasteboard.putItems(itemList, supportedActions);
/*     */   }
/*     */ 
/*     */   protected Object popFromSystem(String mime)
/*     */   {
/* 255 */     Object object = null;
/*     */ 
/* 257 */     String[][] utfs = this.pasteboard.getUTFs();
/* 258 */     if (mime.equals("text/uri-list") == true) {
/* 259 */       if (utfs != null) {
/* 260 */         ArrayList list = new ArrayList();
/* 261 */         for (int i = 0; i < utfs.length; i++) {
/* 262 */           String url = this.pasteboard.getItemStringForUTF(i, mimeToUtf("text/uri-list"));
/*     */ 
/* 264 */           if (url != null) {
/* 265 */             list.add(url);
/*     */ 
/* 267 */             break;
/*     */           }
/*     */         }
/*     */ 
/* 271 */         if (list.size() > 0)
/*     */         {
/* 273 */           object = list.get(0);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 279 */     else if (mime.equals("application/x-java-rawimage") == true) {
/* 280 */       if (utfs != null) {
/* 281 */         ArrayList list = new ArrayList();
/* 282 */         for (int i = 0; i < utfs.length; i++) {
/* 283 */           Object data = this.pasteboard.getItemAsRawImage(i);
/* 284 */           if (data != null) {
/* 285 */             Pixels pixels = getPixelsForRawImage((byte[])data);
/* 286 */             list.add(pixels);
/*     */ 
/* 288 */             break;
/*     */           }
/*     */         }
/*     */ 
/* 292 */         if (list.size() > 0)
/*     */         {
/* 294 */           object = list.get(0);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 300 */     else if ((mime.equals("text/plain") == true) || (mime.equals("text/html") == true) || (mime.equals("text/rtf") == true))
/*     */     {
/* 303 */       if (utfs != null) {
/* 304 */         ArrayList list = new ArrayList();
/* 305 */         for (int i = 0; i < utfs.length; i++) {
/* 306 */           String item = this.pasteboard.getItemStringForUTF(i, mimeToUtf(mime));
/* 307 */           if (item != null) {
/* 308 */             list.add(item);
/*     */ 
/* 310 */             break;
/*     */           }
/*     */         }
/*     */ 
/* 314 */         if (list.size() > 0)
/*     */         {
/* 316 */           object = list.get(0);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 322 */     else if (mime.equals("application/x-java-file-list") == true)
/*     */     {
/* 324 */       if (utfs != null) {
/* 325 */         ArrayList list = new ArrayList();
/* 326 */         for (int i = 0; i < utfs.length; i++) {
/* 327 */           String file = this.pasteboard.getItemStringForUTF(i, "public.file-url");
/* 328 */           if (file != null) {
/* 329 */             URL url = createUrl(file, "bad URL in com.sun.glass.ui.mac.MacSystemClipboard for file: ");
/* 330 */             list.add(url.getPath());
/*     */           }
/*     */         }
/*     */ 
/* 334 */         if (list.size() > 0) {
/* 335 */           object = new String[list.size()];
/* 336 */           list.toArray((String[])object);
/*     */         }
/*     */       }
/*     */     }
/* 340 */     else if (utfs != null) {
/* 341 */       ArrayList list = new ArrayList();
/* 342 */       for (int i = 0; i < utfs.length; i++) {
/* 343 */         byte[] data = this.pasteboard.getItemBytesForUTF(i, mimeToUtf(mime));
/* 344 */         if (data != null)
/*     */         {
/* 347 */           ByteBuffer bb = ByteBuffer.wrap(data);
/* 348 */           list.add(bb);
/*     */ 
/* 350 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 354 */       if (list.size() > 0)
/*     */       {
/* 356 */         object = list.get(0);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 365 */     return object;
/*     */   }
/*     */ 
/*     */   protected String[] mimesFromSystem()
/*     */   {
/* 370 */     String[][] all = this.pasteboard.getUTFs();
/*     */ 
/* 372 */     ArrayList mimes = new ArrayList();
/*     */ 
/* 376 */     if (all != null) {
/* 377 */       for (int i = 0; i < all.length; i++) {
/* 378 */         String[] utfs = all[i];
/* 379 */         if (utfs != null) {
/* 380 */           for (int j = 0; j < utfs.length; j++) {
/* 381 */             String utf = utfs[j];
/*     */ 
/* 383 */             String mime = utfToMime(utf);
/*     */ 
/* 385 */             if ((mime != null) && (!mimes.contains(mime))) {
/* 386 */               mimes.add(mime);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 393 */     String[] strings = new String[mimes.size()];
/* 394 */     mimes.toArray(strings);
/*     */ 
/* 401 */     return strings;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 405 */     return "Mac OS X " + this.pasteboard.getName() + " Clipboard";
/*     */   }
/*     */ 
/*     */   private synchronized String utfToMime(String utf)
/*     */   {
/* 410 */     if (utm == null) {
/* 411 */       utm = new HashMap(6);
/* 412 */       utm.put("public.utf8-plain-text", "text/plain");
/* 413 */       utm.put("public.html", "text/html");
/* 414 */       utm.put("public.rtf", "text/rtf");
/* 415 */       utm.put("public.url", "text/uri-list");
/* 416 */       utm.put("public.file-url", "application/x-java-file-list");
/* 417 */       utm.put("public.tiff", "application/x-java-rawimage");
/* 418 */       utm.put("public.png", "application/x-java-rawimage");
/*     */     }
/* 420 */     if (utm.containsKey(utf) == true) {
/* 421 */       return (String)utm.get(utf);
/*     */     }
/* 423 */     return utf;
/*     */   }
/*     */ 
/*     */   private synchronized String mimeToUtf(String mime)
/*     */   {
/* 429 */     if (mtu == null) {
/* 430 */       mtu = new HashMap(4);
/* 431 */       mtu.put("text/plain", "public.utf8-plain-text");
/* 432 */       mtu.put("text/html", "public.html");
/* 433 */       mtu.put("text/rtf", "public.rtf");
/* 434 */       mtu.put("text/uri-list", "public.url");
/* 435 */       mtu.put("application/x-java-file-list", "public.file-url");
/*     */     }
/*     */ 
/* 438 */     if (mtu.containsKey(mime) == true) {
/* 439 */       return (String)mtu.get(mime);
/*     */     }
/* 441 */     return mime;
/*     */   }
/*     */ 
/*     */   private URI createUri(String path, String message)
/*     */   {
/* 446 */     URI uri = null;
/*     */     try {
/* 448 */       uri = new URI(path);
/*     */     } catch (URISyntaxException ex) {
/* 450 */       System.err.println(message + path);
/* 451 */       Thread.dumpStack();
/*     */     }
/*     */ 
/* 454 */     return uri;
/*     */   }
/*     */ 
/*     */   private URI createUri(String scheme, String path, String message) {
/* 458 */     URI uri = null;
/*     */     try {
/* 460 */       uri = new URI(scheme, null, path, null);
/*     */     } catch (URISyntaxException ex) {
/* 462 */       System.err.println(message + path);
/* 463 */       Thread.dumpStack();
/*     */     }
/*     */ 
/* 466 */     return uri;
/*     */   }
/*     */ 
/*     */   private URL createUrl(String path, String message) {
/* 470 */     URL url = null;
/*     */     try {
/* 472 */       url = new URL(path);
/*     */     } catch (MalformedURLException ex) {
/* 474 */       System.err.println(message + path);
/* 475 */       Thread.dumpStack();
/*     */     }
/*     */ 
/* 478 */     return url;
/*     */   }
/*     */ 
/*     */   private byte[] serialize(Object object) {
/* 482 */     if ((object instanceof String)) {
/* 483 */       String string = (String)object;
/* 484 */       return string.getBytes();
/* 485 */     }if ((object instanceof ByteBuffer)) {
/* 486 */       ByteBuffer buffer = (ByteBuffer)object;
/* 487 */       return buffer.array();
/*     */     }
/* 489 */     throw new RuntimeException("can not handle " + object);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacSystemClipboard
 * JD-Core Version:    0.6.2
 */