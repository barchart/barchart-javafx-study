/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class ClipboardAssistance
/*     */ {
/*   9 */   private final HashMap<String, Object> cacheData = new HashMap();
/*     */   private final Clipboard clipboard;
/*  11 */   private int supportedActions = 1342177279;
/*     */ 
/*     */   public ClipboardAssistance(String cipboardName)
/*     */   {
/*  18 */     this.clipboard = Clipboard.get(cipboardName);
/*  19 */     this.clipboard.add(this);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*  26 */     this.clipboard.remove(this);
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */   {
/*  34 */     this.clipboard.flush(this, this.cacheData, this.supportedActions);
/*     */   }
/*     */ 
/*     */   public void emptyCache()
/*     */   {
/*  41 */     this.cacheData.clear();
/*     */   }
/*     */ 
/*     */   public boolean isCacheEmpty() {
/*  45 */     return this.cacheData.isEmpty();
/*     */   }
/*     */ 
/*     */   public void setData(String mimeType, Object data)
/*     */   {
/*  56 */     this.cacheData.put(mimeType, data);
/*     */   }
/*     */ 
/*     */   public Object getData(String mimeType)
/*     */   {
/*  66 */     return this.clipboard.getData(mimeType);
/*     */   }
/*     */ 
/*     */   public void setSupportedActions(int supportedActions)
/*     */   {
/*  75 */     this.supportedActions = supportedActions;
/*     */   }
/*     */ 
/*     */   public int getSupportedSourceActions()
/*     */   {
/*  83 */     return this.clipboard.getSupportedSourceActions();
/*     */   }
/*     */ 
/*     */   public void setTargetAction(int actionDone)
/*     */   {
/*  91 */     this.clipboard.setTargetAction(actionDone);
/*     */   }
/*     */ 
/*     */   public void contentChanged()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void actionPerformed(int action)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String[] getMimeTypes()
/*     */   {
/* 107 */     return this.clipboard.getMimeTypes();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 112 */     return "ClipboardAssistance[" + this.clipboard + "]";
/*     */   }
/*     */ 
/*     */   public String dumpToString() {
/* 116 */     StringBuilder out = new StringBuilder("{ClipboardAssistance[").append(this.clipboard.toString()).append("]");
/*     */ 
/* 119 */     String[] mimes = getMimeTypes();
/* 120 */     if (mimes == null) {
/* 121 */       out.append(" clipbard is empty");
/*     */     } else {
/* 123 */       for (String mime : mimes) {
/* 124 */         out.append("\nmimeType:").append(mime).append(" value:").append(getData(mime));
/*     */       }
/* 126 */       out.append("\n");
/*     */     }
/* 128 */     out.append("}ClipboardAssistance");
/* 129 */     return out.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.ClipboardAssistance
 * JD-Core Version:    0.6.2
 */