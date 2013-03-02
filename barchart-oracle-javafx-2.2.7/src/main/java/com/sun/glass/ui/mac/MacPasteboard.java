/*     */ package com.sun.glass.ui.mac;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class MacPasteboard
/*     */ {
/*     */   public static final int General = 1;
/*     */   public static final int DragAndDrop = 2;
/*     */   public static final int UtfIndex = 0;
/*     */   public static final int ObjectIndex = 1;
/*     */   public static final String UtfString = "public.utf8-plain-text";
/*     */   public static final String UtfPdf = "com.adobe.pdf";
/*     */   public static final String UtfTiff = "public.tiff";
/*     */   public static final String UtfPng = "public.png";
/*     */   public static final String UtfRtf = "public.rtf";
/*     */   public static final String UtfRtfd = "com.apple.flat-rtfd";
/*     */   public static final String UtfHtml = "public.html";
/*     */   public static final String UtfTabularText = "public.utf8-tab-separated-values-text";
/*     */   public static final String UtfFont = "com.apple.cocoa.pasteboard.character-formatting";
/*     */   public static final String UtfColor = "com.apple.cocoa.pasteboard.color";
/*     */   public static final String UtfSound = "com.apple.cocoa.pasteboard.sound";
/*     */   public static final String UtfMultipleTextSelection = "com.apple.cocoa.pasteboard.multiple-text-selection";
/*     */   public static final String UtfFindPanelSearchOptions = "com.apple.cocoa.pasteboard.find-panel-search-options";
/*     */   public static final String UtfUrl = "public.url";
/*     */   public static final String UtfFileUrl = "public.file-url";
/*  50 */   private long ptr = 0L;
/*     */   private boolean user;
/*     */ 
/*     */   static native void _initIDs();
/*     */ 
/*     */   private native long _createSystemPasteboard(int paramInt);
/*     */ 
/*     */   public MacPasteboard(int type)
/*     */   {
/*  58 */     this.user = false;
/*  59 */     this.ptr = _createSystemPasteboard(type);
/*     */   }
/*     */ 
/*     */   private native long _createUserPasteboard(String paramString);
/*     */ 
/*     */   public MacPasteboard(String name) {
/*  65 */     this.user = true;
/*  66 */     this.ptr = _createUserPasteboard(name);
/*     */   }
/*     */ 
/*     */   public long getNativePasteboard()
/*     */   {
/*  72 */     assertValid();
/*  73 */     return this.ptr;
/*     */   }
/*     */ 
/*     */   private native String _getName(long paramLong);
/*     */ 
/*     */   public String getName() {
/*  79 */     assertValid();
/*  80 */     return _getName(this.ptr);
/*     */   }
/*     */ 
/*     */   private native String[][] _getUTFs(long paramLong);
/*     */ 
/*     */   public String[][] getUTFs()
/*     */   {
/*  88 */     assertValid();
/*  89 */     return _getUTFs(this.ptr);
/*     */   }
/*     */ 
/*     */   private native byte[] _getItemAsRawImage(long paramLong, int paramInt);
/*     */ 
/*     */   public byte[] getItemAsRawImage(int index)
/*     */   {
/*  96 */     assertValid();
/*  97 */     return _getItemAsRawImage(this.ptr, index);
/*     */   }
/*     */ 
/*     */   private native String _getItemAsString(long paramLong, int paramInt);
/*     */ 
/*     */   public String getItemAsString(int index)
/*     */   {
/* 104 */     assertValid();
/* 105 */     return _getItemAsString(this.ptr, index);
/*     */   }
/*     */ 
/*     */   private native String _getItemStringForUTF(long paramLong, int paramInt, String paramString);
/*     */ 
/*     */   public String getItemStringForUTF(int index, String utf) {
/* 111 */     assertValid();
/* 112 */     return _getItemStringForUTF(this.ptr, index, utf);
/*     */   }
/*     */ 
/*     */   private native byte[] _getItemBytesForUTF(long paramLong, int paramInt, String paramString);
/*     */ 
/*     */   public byte[] getItemBytesForUTF(int index, String utf) {
/* 118 */     assertValid();
/* 119 */     return _getItemBytesForUTF(this.ptr, index, utf);
/*     */   }
/*     */ 
/*     */   private native long _getItemForUTF(long paramLong, int paramInt, String paramString);
/*     */ 
/*     */   public long getItemForUTF(int index, String utf)
/*     */   {
/* 127 */     assertValid();
/* 128 */     return _getItemForUTF(this.ptr, index, utf);
/*     */   }
/*     */ 
/*     */   private native long _putItemsFromArray(long paramLong, Object[] paramArrayOfObject, int paramInt);
/*     */ 
/*     */   public long putItemsFromArray(Object[] items, int supportedActions)
/*     */   {
/* 148 */     return _putItemsFromArray(this.ptr, items, supportedActions);
/*     */   }
/*     */   private Object[] hashMapToArray(HashMap hashmap) {
/* 151 */     Object[] array = null;
/* 152 */     if ((hashmap != null) && (hashmap.size() > 0)) {
/* 153 */       array = new Object[hashmap.size()];
/* 154 */       Set keys = hashmap.keySet();
/* 155 */       Iterator iterator = keys.iterator();
/* 156 */       int index = 0;
/* 157 */       while (iterator.hasNext() == true) {
/* 158 */         Object[] item = new Object[2];
/* 159 */         String utf = (String)iterator.next();
/* 160 */         item[0] = utf;
/* 161 */         item[1] = hashmap.get(utf);
/* 162 */         array[(index++)] = item;
/*     */       }
/*     */     }
/* 165 */     return array;
/*     */   }
/*     */   public long putItems(HashMap<String, Object>[] items, int supportedActions) {
/* 168 */     assertValid();
/* 169 */     Object[] array = null;
/* 170 */     if (items.length > 0) {
/* 171 */       array = new Object[items.length];
/* 172 */       for (int i = 0; i < items.length; i++) {
/* 173 */         array[i] = hashMapToArray(items[i]);
/*     */       }
/*     */     }
/* 176 */     return putItemsFromArray(array, supportedActions);
/*     */   }
/*     */ 
/*     */   private native long _clear(long paramLong);
/*     */ 
/*     */   public long clear()
/*     */   {
/* 183 */     assertValid();
/* 184 */     return _clear(this.ptr);
/*     */   }
/*     */ 
/*     */   private native long _getSeed(long paramLong);
/*     */ 
/*     */   public long getSeed() {
/* 190 */     assertValid();
/* 191 */     return _getSeed(this.ptr);
/*     */   }
/*     */ 
/*     */   private native int _getAllowedOperation(long paramLong);
/*     */ 
/*     */   public int getAllowedOperation() {
/* 197 */     assertValid();
/* 198 */     return _getAllowedOperation(this.ptr);
/*     */   }
/*     */   private native void _release(long paramLong);
/*     */ 
/*     */   public void release() {
/* 203 */     assertValid();
/* 204 */     if ((this.ptr != 0L) && (this.user == true)) {
/* 205 */       _release(this.ptr);
/*     */     }
/* 207 */     this.ptr = 0L;
/*     */   }
/*     */ 
/*     */   private void assertValid() {
/* 211 */     if (this.ptr == 0L)
/* 212 */       throw new IllegalStateException("The MacPasteboard is not valid");
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  17 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  20 */         Application.loadNativeLibrary();
/*  21 */         return null;
/*     */       }
/*     */     });
/*  24 */     _initIDs();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacPasteboard
 * JD-Core Version:    0.6.2
 */