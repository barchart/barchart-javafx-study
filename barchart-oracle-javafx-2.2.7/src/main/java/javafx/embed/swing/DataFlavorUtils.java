/*     */ package javafx.embed.swing;
/*     */ 
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ final class DataFlavorUtils
/*     */ {
/*     */   static String getFxMimeType(DataFlavor paramDataFlavor)
/*     */   {
/*  46 */     return paramDataFlavor.getPrimaryType() + "/" + paramDataFlavor.getSubType();
/*     */   }
/*     */ 
/*     */   static Object adjustFxData(DataFlavor paramDataFlavor, Object paramObject)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  52 */     if (((paramObject instanceof String)) && 
/*  53 */       (paramDataFlavor.isRepresentationClassInputStream())) {
/*  54 */       String str = paramDataFlavor.getParameter("charset");
/*  55 */       return new ByteArrayInputStream(str != null ? ((String)paramObject).getBytes(str) : ((String)paramObject).getBytes());
/*     */     }
/*     */ 
/*  60 */     return paramObject;
/*     */   }
/*     */ 
/*     */   static Object adjustSwingData(DataFlavor paramDataFlavor, Object paramObject)
/*     */   {
/*  65 */     if (paramDataFlavor.isFlavorJavaFileListType())
/*     */     {
/*  67 */       List localList = (List)paramObject;
/*  68 */       String[] arrayOfString = new String[localList.size()];
/*  69 */       int i = 0;
/*  70 */       for (File localFile : localList) {
/*  71 */         arrayOfString[(i++)] = localFile.getPath();
/*     */       }
/*  73 */       return arrayOfString;
/*     */     }
/*  75 */     return paramObject;
/*     */   }
/*     */ 
/*     */   static Map<String, DataFlavor> adjustSwingDataFlavors(DataFlavor[] paramArrayOfDataFlavor)
/*     */   {
/*  84 */     HashMap localHashMap = new HashMap(paramArrayOfDataFlavor.length);
/*     */     Object localObject2;
/*  86 */     for (localObject2 : paramArrayOfDataFlavor) {
/*  87 */       String str2 = getFxMimeType((DataFlavor)localObject2);
/*     */       Object localObject3;
/*  88 */       if (localHashMap.containsKey(str2)) {
/*  89 */         localObject3 = (Set)localHashMap.get(str2);
/*     */         try
/*     */         {
/*  92 */           ((Set)localObject3).add(localObject2);
/*     */         }
/*     */         catch (UnsupportedOperationException localUnsupportedOperationException) {
/*     */         }
/*     */       }
/*     */       else {
/*  98 */         localObject3 = new HashSet();
/*     */ 
/* 103 */         if (((DataFlavor)localObject2).isFlavorTextType()) {
/* 104 */           ((Set)localObject3).add(DataFlavor.stringFlavor);
/* 105 */           localObject3 = Collections.unmodifiableSet((Set)localObject3);
/*     */         }
/*     */         else {
/* 108 */           ((Set)localObject3).add(localObject2);
/*     */         }
/*     */ 
/* 111 */         localHashMap.put(str2, localObject3);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 118 */     ??? = new HashMap();
/*     */ 
/* 120 */     for (String str1 : localHashMap.keySet()) {
/* 121 */       localObject2 = (DataFlavor[])((Set)localHashMap.get(str1)).toArray(new DataFlavor[0]);
/*     */ 
/* 123 */       if (localObject2.length == 1) {
/* 124 */         ((Map)???).put(str1, localObject2[0]);
/*     */       }
/*     */       else {
/* 127 */         ((Map)???).put(str1, localObject2[0]);
/*     */       }
/*     */     }
/*     */ 
/* 131 */     return ???;
/*     */   }
/*     */ 
/*     */   static Map<String, Object> readAllData(Transferable paramTransferable) {
/* 135 */     Map localMap = adjustSwingDataFlavors(paramTransferable.getTransferDataFlavors());
/*     */ 
/* 137 */     return readAllData(paramTransferable, localMap);
/*     */   }
/*     */ 
/*     */   static Map<String, Object> readAllData(Transferable paramTransferable, Map<String, DataFlavor> paramMap)
/*     */   {
/* 142 */     HashMap localHashMap = new HashMap();
/*     */ 
/* 145 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 146 */       Object localObject = null;
/*     */       try {
/* 148 */         localObject = paramTransferable.getTransferData((DataFlavor)localEntry.getValue());
/*     */       }
/*     */       catch (UnsupportedFlavorException localUnsupportedFlavorException)
/*     */       {
/*     */       }
/*     */       catch (IOException localIOException) {
/*     */       }
/* 155 */       if (localObject != null) {
/* 156 */         localObject = adjustSwingData((DataFlavor)localEntry.getValue(), localObject);
/* 157 */         localHashMap.put(localEntry.getKey(), localObject);
/*     */       }
/*     */     }
/* 160 */     return localHashMap;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swing.DataFlavorUtils
 * JD-Core Version:    0.6.2
 */