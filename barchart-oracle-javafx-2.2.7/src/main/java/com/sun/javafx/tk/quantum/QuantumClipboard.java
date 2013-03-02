/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.ClipboardAssistance;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.javafx.tk.TKClipboard;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.SocketPermission;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.AllPermission;
/*     */ import java.security.Permission;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javafx.scene.input.DataFormat;
/*     */ import javafx.scene.input.TransferMode;
/*     */ import javafx.util.Pair;
/*     */ 
/*     */ public class QuantumClipboard
/*     */   implements TKClipboard
/*     */ {
/*     */   private ClipboardAssistance systemAssistant;
/*  68 */   private AccessControlContext accessContext = null;
/*     */   private boolean isCaching;
/*     */   private List<Pair<DataFormat, Object>> dataCache;
/*     */   private Set<TransferMode> transferModesCache;
/* 253 */   private static final Permission all = new AllPermission();
/*     */ 
/* 304 */   private static final Pattern findTagIMG = Pattern.compile("IMG\\s+SRC=\\\"([^\\\"]+)\\\"", 34);
/*     */ 
/*     */   public void initSecurityContext()
/*     */   {
/*  76 */     this.accessContext = AccessController.getContext();
/*     */   }
/*     */ 
/*     */   public static QuantumClipboard getClipboardInstance(ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 105 */     QuantumClipboard localQuantumClipboard = new QuantumClipboard();
/* 106 */     localQuantumClipboard.systemAssistant = paramClipboardAssistance;
/* 107 */     localQuantumClipboard.isCaching = false;
/* 108 */     return localQuantumClipboard;
/*     */   }
/*     */ 
/*     */   public static QuantumClipboard getDragboardInstance(ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 120 */     QuantumClipboard localQuantumClipboard = new QuantumClipboard();
/* 121 */     localQuantumClipboard.systemAssistant = paramClipboardAssistance;
/* 122 */     localQuantumClipboard.isCaching = true;
/* 123 */     return localQuantumClipboard;
/*     */   }
/*     */ 
/*     */   public static int transferModesToClipboardActions(Set<TransferMode> paramSet) {
/* 127 */     int i = 0;
/* 128 */     for (TransferMode localTransferMode : paramSet) {
/* 129 */       switch (2.$SwitchMap$javafx$scene$input$TransferMode[localTransferMode.ordinal()]) {
/*     */       case 1:
/* 131 */         i |= 1;
/* 132 */         break;
/*     */       case 2:
/* 134 */         i |= 2;
/* 135 */         break;
/*     */       case 3:
/* 137 */         i |= 1073741824;
/* 138 */         break;
/*     */       default:
/* 140 */         throw new IllegalArgumentException("unsupported TransferMode " + paramSet);
/*     */       }
/*     */     }
/*     */ 
/* 144 */     return i;
/*     */   }
/*     */ 
/*     */   public void setSupportedTransferMode(Set<TransferMode> paramSet)
/*     */   {
/* 149 */     if (this.isCaching) {
/* 150 */       this.transferModesCache = paramSet;
/*     */     }
/*     */ 
/* 153 */     int i = transferModesToClipboardActions(paramSet);
/*     */ 
/* 155 */     this.systemAssistant.setSupportedActions(i);
/*     */   }
/*     */ 
/*     */   public static Set<TransferMode> clipboardActionsToTransferModes(int paramInt) {
/* 159 */     EnumSet localEnumSet = EnumSet.noneOf(TransferMode.class);
/*     */ 
/* 161 */     if ((paramInt & 0x1) != 0) {
/* 162 */       localEnumSet.add(TransferMode.COPY);
/*     */     }
/* 164 */     if ((paramInt & 0x2) != 0) {
/* 165 */       localEnumSet.add(TransferMode.MOVE);
/*     */     }
/* 167 */     if ((paramInt & 0x40000000) != 0) {
/* 168 */       localEnumSet.add(TransferMode.LINK);
/*     */     }
/*     */ 
/* 171 */     return localEnumSet;
/*     */   }
/*     */ 
/*     */   public Set<TransferMode> getTransferModes()
/*     */   {
/* 176 */     if (this.transferModesCache != null) {
/* 177 */       return EnumSet.copyOf(this.transferModesCache);
/*     */     }
/*     */ 
/* 180 */     Set localSet = clipboardActionsToTransferModes(this.systemAssistant.getSupportedSourceActions());
/*     */ 
/* 182 */     return localSet;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 189 */     this.systemAssistant.close();
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */   {
/* 196 */     clearCache();
/* 197 */     this.systemAssistant.flush();
/*     */   }
/*     */ 
/*     */   public Object getContent(DataFormat paramDataFormat)
/*     */   {
/*     */     Object localObject2;
/* 201 */     if (this.dataCache != null) {
/* 202 */       for (localObject1 = this.dataCache.iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Pair)((Iterator)localObject1).next();
/* 203 */         if (((Pair)localObject2).getKey() == paramDataFormat) {
/* 204 */           return ((Pair)localObject2).getValue();
/*     */         }
/*     */       }
/* 207 */       return null;
/*     */     }
/*     */ 
/* 210 */     if (paramDataFormat == DataFormat.IMAGE)
/* 211 */       return readImage();
/* 212 */     if (paramDataFormat == DataFormat.URL)
/*     */     {
/* 214 */       return this.systemAssistant.getData("text/uri-list");
/*     */     }
/*     */     Object localObject3;
/* 215 */     if (paramDataFormat == DataFormat.FILES) {
/* 216 */       localObject1 = this.systemAssistant.getData("application/x-java-file-list");
/* 217 */       if (localObject1 == null) return Collections.emptyList();
/* 218 */       localObject2 = (String[])localObject1;
/* 219 */       localObject3 = new ArrayList(localObject2.length);
/* 220 */       for (int i = 0; i < localObject2.length; i++) {
/* 221 */         ((List)localObject3).add(new File(localObject2[i]));
/*     */       }
/* 223 */       return localObject3;
/*     */     }
/*     */ 
/* 226 */     for (Object localObject1 = paramDataFormat.getIdentifiers().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (String)((Iterator)localObject1).next();
/* 227 */       localObject3 = this.systemAssistant.getData((String)localObject2);
/* 228 */       if ((localObject3 instanceof ByteBuffer))
/*     */         try {
/* 230 */           ByteBuffer localByteBuffer = (ByteBuffer)localObject3;
/* 231 */           ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localByteBuffer.array());
/*     */ 
/* 233 */           ObjectInputStream local1 = new ObjectInputStream(localByteArrayInputStream)
/*     */           {
/*     */             protected Class<?> resolveClass(ObjectStreamClass paramAnonymousObjectStreamClass) throws IOException, ClassNotFoundException
/*     */             {
/* 237 */               return Class.forName(paramAnonymousObjectStreamClass.getName(), false, Thread.currentThread().getContextClassLoader());
/*     */             }
/*     */           };
/* 241 */           localObject3 = local1.readObject();
/*     */         }
/*     */         catch (IOException localIOException)
/*     */         {
/*     */         }
/*     */         catch (ClassNotFoundException localClassNotFoundException) {
/*     */         }
/* 248 */       if (localObject3 != null) return localObject3;
/*     */     }
/* 250 */     return null;
/*     */   }
/*     */ 
/*     */   private javafx.scene.image.Image readImage()
/*     */   {
/* 256 */     Object localObject1 = this.systemAssistant.getData("application/x-java-rawimage");
/* 257 */     if (localObject1 == null) {
/* 258 */       localObject2 = this.systemAssistant.getData("text/html");
/* 259 */       if (localObject2 != null) {
/* 260 */         localObject3 = parseIMG(localObject2);
/* 261 */         if (localObject3 != null) {
/*     */           try {
/* 263 */             SecurityManager localSecurityManager = System.getSecurityManager();
/* 264 */             if (localSecurityManager != null) {
/* 265 */               URL localURL = new URL((String)localObject3);
/* 266 */               String str1 = localURL.getProtocol();
/*     */               Object localObject4;
/* 267 */               if (str1.equalsIgnoreCase("jar")) {
/* 268 */                 localObject4 = localURL.getFile();
/* 269 */                 localURL = new URL((String)localObject4);
/* 270 */                 str1 = localURL.getProtocol();
/*     */               }
/* 272 */               if (str1.equalsIgnoreCase("file")) {
/* 273 */                 localObject4 = new FilePermission(localURL.getFile(), "read");
/* 274 */                 this.accessContext.checkPermission((Permission)localObject4);
/* 275 */               } else if ((str1.equalsIgnoreCase("ftp")) || (str1.equalsIgnoreCase("http")) || (str1.equalsIgnoreCase("https")))
/*     */               {
/* 278 */                 int i = localURL.getPort();
/* 279 */                 String str2 = localURL.getHost() + ":" + i;
/* 280 */                 SocketPermission localSocketPermission = new SocketPermission(str2, "connect");
/* 281 */                 this.accessContext.checkPermission(localSocketPermission);
/*     */               } else {
/* 283 */                 this.accessContext.checkPermission(all);
/*     */               }
/*     */             }
/* 286 */             return new javafx.scene.image.Image((String)localObject3);
/*     */           } catch (MalformedURLException localMalformedURLException) {
/* 288 */             return null;
/*     */           } catch (SecurityException localSecurityException) {
/* 290 */             return null;
/*     */           }
/*     */         }
/*     */       }
/* 294 */       return null;
/* 295 */     }if ((localObject1 instanceof javafx.scene.image.Image)) {
/* 296 */       return (javafx.scene.image.Image)localObject1;
/*     */     }
/* 298 */     Object localObject2 = PixelUtils.pixelsToImage((Pixels)localObject1);
/* 299 */     Object localObject3 = Toolkit.getToolkit().loadPlatformImage(localObject2);
/* 300 */     return javafx.scene.image.Image.impl_fromPlatformImage(localObject3);
/*     */   }
/*     */ 
/*     */   private String parseIMG(Object paramObject)
/*     */   {
/* 309 */     if (paramObject == null) {
/* 310 */       return null;
/*     */     }
/* 312 */     if (!(paramObject instanceof String)) {
/* 313 */       return null;
/*     */     }
/* 315 */     String str = (String)paramObject;
/* 316 */     Matcher localMatcher = findTagIMG.matcher(str);
/* 317 */     if (localMatcher.find()) {
/* 318 */       return localMatcher.group(1);
/*     */     }
/* 320 */     return null;
/*     */   }
/*     */ 
/*     */   private boolean placeImage(javafx.scene.image.Image paramImage)
/*     */   {
/* 325 */     if (paramImage == null) {
/* 326 */       return false;
/*     */     }
/*     */ 
/* 329 */     String str = paramImage.impl_getUrl();
/* 330 */     if ((str == null) || (PixelUtils.supportedFormatType(str))) {
/* 331 */       com.sun.prism.Image localImage = (com.sun.prism.Image)paramImage.impl_getPlatformImage();
/*     */ 
/* 333 */       Pixels localPixels = PixelUtils.imageToPixels(localImage);
/* 334 */       if (localPixels != null) {
/* 335 */         this.systemAssistant.setData("application/x-java-rawimage", localPixels);
/* 336 */         return true;
/*     */       }
/* 338 */       return false;
/*     */     }
/*     */ 
/* 341 */     this.systemAssistant.setData("text/uri-list", str);
/* 342 */     return true;
/*     */   }
/*     */ 
/*     */   public Set<DataFormat> getContentTypes()
/*     */   {
/* 353 */     HashSet localHashSet = new HashSet();
/*     */     Object localObject2;
/* 355 */     if (this.dataCache != null) {
/* 356 */       for (localObject1 = this.dataCache.iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Pair)((Iterator)localObject1).next();
/* 357 */         localHashSet.add(((Pair)localObject2).getKey());
/*     */       }
/* 359 */       return localHashSet;
/*     */     }
/*     */ 
/* 362 */     Object localObject1 = this.systemAssistant.getMimeTypes();
/* 363 */     if (localObject1 == null) {
/* 364 */       return localHashSet;
/*     */     }
/* 366 */     for (String str : localObject1) {
/* 367 */       if (str.equalsIgnoreCase("application/x-java-rawimage")) {
/* 368 */         localHashSet.add(DataFormat.IMAGE);
/* 369 */       } else if (str.equalsIgnoreCase("text/uri-list")) {
/* 370 */         localHashSet.add(DataFormat.URL);
/* 371 */       } else if (str.equalsIgnoreCase("application/x-java-file-list")) {
/* 372 */         localHashSet.add(DataFormat.FILES);
/* 373 */       } else if (str.equalsIgnoreCase("text/html")) {
/* 374 */         localHashSet.add(DataFormat.HTML);
/*     */         try
/*     */         {
/* 378 */           if (parseIMG(this.systemAssistant.getData("text/html")) != null)
/* 379 */             localHashSet.add(DataFormat.IMAGE);
/*     */         }
/*     */         catch (Exception localException) {
/*     */         }
/*     */       }
/*     */       else {
/* 385 */         DataFormat localDataFormat = DataFormat.lookupMimeType(str);
/* 386 */         if (localDataFormat == null)
/*     */         {
/* 388 */           localDataFormat = new DataFormat(new String[] { str });
/*     */         }
/* 390 */         localHashSet.add(localDataFormat);
/*     */       }
/*     */     }
/* 393 */     return localHashSet;
/*     */   }
/*     */ 
/*     */   public boolean hasContent(DataFormat paramDataFormat)
/*     */   {
/*     */     Object localObject2;
/* 397 */     if (this.dataCache != null) {
/* 398 */       for (localObject1 = this.dataCache.iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Pair)((Iterator)localObject1).next();
/* 399 */         if (((Pair)localObject2).getKey() == paramDataFormat) {
/* 400 */           return true;
/*     */         }
/*     */       }
/* 403 */       return false;
/*     */     }
/*     */ 
/* 406 */     Object localObject1 = this.systemAssistant.getMimeTypes();
/* 407 */     if (localObject1 == null) {
/* 408 */       return false;
/*     */     }
/* 410 */     for (String str : localObject1) {
/* 411 */       if ((paramDataFormat == DataFormat.IMAGE) && (str.equalsIgnoreCase("application/x-java-rawimage")))
/*     */       {
/* 413 */         return true;
/* 414 */       }if ((paramDataFormat == DataFormat.URL) && (str.equalsIgnoreCase("text/uri-list")))
/*     */       {
/* 416 */         return true;
/* 417 */       }if ((paramDataFormat == DataFormat.IMAGE) && (str.equalsIgnoreCase("text/html")))
/*     */       {
/* 419 */         return parseIMG(this.systemAssistant.getData("text/html")) != null;
/* 420 */       }if ((paramDataFormat == DataFormat.FILES) && (str.equalsIgnoreCase("application/x-java-file-list")))
/*     */       {
/* 422 */         return true;
/*     */       }
/*     */ 
/* 425 */       DataFormat localDataFormat = DataFormat.lookupMimeType(str);
/* 426 */       if ((localDataFormat != null) && (localDataFormat.equals(paramDataFormat))) {
/* 427 */         return true;
/*     */       }
/*     */     }
/* 430 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean putContent(Pair<DataFormat, Object>[] paramArrayOfPair) {
/* 434 */     this.systemAssistant.emptyCache();
/*     */ 
/* 436 */     if (this.isCaching) {
/* 437 */       this.dataCache = new ArrayList(paramArrayOfPair.length);
/*     */     }
/*     */ 
/* 440 */     boolean bool = false;
/*     */     Pair<DataFormat, Object> localPair;
/*     */     DataFormat localDataFormat;
/*     */     Object localObject1;
/* 442 */     for (localPair : paramArrayOfPair) {
/* 443 */       localDataFormat = (DataFormat)localPair.getKey();
/* 444 */       localObject1 = localPair.getValue();
/*     */ 
/* 446 */       if (localDataFormat == null) {
/* 447 */         throw new NullPointerException("Clipboard.putContent: null data format");
/*     */       }
/* 449 */       if (localObject1 == null)
/* 450 */         throw new NullPointerException("Clipboard.putContent: null data");
/*     */     }
/*     */     Object localObject3;
/*     */     Iterator localIterator1;
/* 460 */     for (localPair : paramArrayOfPair) {
/* 461 */       localDataFormat = (DataFormat)localPair.getKey();
/* 462 */       localObject1 = localPair.getValue();
/*     */ 
/* 464 */       if (this.isCaching) {
/* 465 */         this.dataCache.add(localPair);
/*     */       }
/*     */ 
/* 476 */       if ((localDataFormat == DataFormat.IMAGE) && ((localObject1 instanceof javafx.scene.image.Image))) {
/* 477 */         placeImage((javafx.scene.image.Image)localObject1);
/* 478 */         bool = true;
/* 479 */       } else if (localDataFormat == DataFormat.URL)
/*     */       {
/* 481 */         this.systemAssistant.setData("text/uri-list", localObject1);
/* 482 */         bool = true;
/* 483 */       } else if (localDataFormat == DataFormat.RTF) {
/* 484 */         this.systemAssistant.setData("text/rtf", localObject1);
/* 485 */         bool = true;
/*     */       }
/*     */       else
/*     */       {
/*     */         Object localObject2;
/* 486 */         if (localDataFormat == DataFormat.FILES)
/*     */         {
/* 488 */           localObject2 = (List)localObject1;
/* 489 */           localObject3 = new String[((List)localObject2).size()];
/* 490 */           int k = 0;
/* 491 */           for (File localFile : (List)localObject2) {
/* 492 */             localObject3[(k++)] = localFile.getAbsolutePath();
/*     */           }
/* 494 */           this.systemAssistant.setData("application/x-java-file-list", localObject3);
/* 495 */           bool = true;
/*     */         } else {
/* 497 */           if ((localObject1 instanceof Serializable)) {
/* 498 */             if (((localDataFormat != DataFormat.PLAIN_TEXT) && (localDataFormat != DataFormat.HTML)) || (!(localObject1 instanceof String)))
/*     */             {
/*     */               try
/*     */               {
/* 502 */                 localObject2 = new ByteArrayOutputStream();
/* 503 */                 localObject3 = new ObjectOutputStream((OutputStream)localObject2);
/* 504 */                 ((ObjectOutput)localObject3).writeObject(localObject1);
/* 505 */                 ((ObjectOutput)localObject3).close();
/* 506 */                 localObject1 = ByteBuffer.wrap(((ByteArrayOutputStream)localObject2).toByteArray());
/*     */               } catch (IOException localIOException) {
/* 508 */                 throw new IllegalArgumentException("Could not serialize the data", localIOException);
/*     */               }
/*     */             }
/*     */           }
/* 512 */           else if (!(localObject1 instanceof ByteBuffer)) {
/* 513 */             throw new IllegalArgumentException("Only serializable objects or ByteBuffer can be used as data with data format " + localDataFormat);
/*     */           }
/*     */ 
/* 517 */           for (localIterator1 = localDataFormat.getIdentifiers().iterator(); localIterator1.hasNext(); ) { localObject3 = (String)localIterator1.next();
/* 518 */             this.systemAssistant.setData((String)localObject3, localObject1);
/* 519 */             bool = true; }
/*     */         }
/*     */       }
/*     */     }
/* 523 */     if (!this.isCaching) {
/* 524 */       this.systemAssistant.flush();
/*     */     }
/* 526 */     return bool;
/*     */   }
/*     */ 
/*     */   private void clearCache() {
/* 530 */     this.dataCache = null;
/* 531 */     this.transferModesCache = null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.QuantumClipboard
 * JD-Core Version:    0.6.2
 */