/*    */ package com.sun.glass.ui;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public abstract class SystemClipboard extends Clipboard
/*    */ {
/*    */   protected SystemClipboard(String name)
/*    */   {
/* 10 */     super(name);
/*    */   }
/*    */ 
/*    */   protected abstract boolean isOwner();
/*    */ 
/*    */   protected abstract void pushToSystem(HashMap<String, Object> paramHashMap, int paramInt);
/*    */ 
/*    */   protected abstract void pushTargetActionToSystem(int paramInt);
/*    */ 
/*    */   protected abstract Object popFromSystem(String paramString);
/*    */ 
/*    */   protected abstract int supportedSourceActionsFromSystem();
/*    */ 
/*    */   protected abstract String[] mimesFromSystem();
/*    */ 
/*    */   public void flush(ClipboardAssistance dataSource, HashMap<String, Object> cacheData, int supportedActions)
/*    */   {
/* 27 */     setSharedData(dataSource, cacheData, supportedActions);
/* 28 */     pushToSystem(cacheData, supportedActions);
/*    */   }
/*    */ 
/*    */   public int getSupportedSourceActions() {
/* 32 */     if (isOwner()) {
/* 33 */       return super.getSupportedSourceActions();
/*    */     }
/* 35 */     return supportedSourceActionsFromSystem();
/*    */   }
/*    */ 
/*    */   public void setTargetAction(int actionDone) {
/* 39 */     pushTargetActionToSystem(actionDone);
/*    */   }
/*    */ 
/*    */   public Object getLocalData(String mimeType) {
/* 43 */     return super.getData(mimeType);
/*    */   }
/*    */ 
/*    */   public Object getData(String mimeType) {
/* 47 */     if (isOwner()) {
/* 48 */       return getLocalData(mimeType);
/*    */     }
/* 50 */     return popFromSystem(mimeType);
/*    */   }
/*    */ 
/*    */   public String[] getMimeTypes() {
/* 54 */     if (isOwner()) {
/* 55 */       return super.getMimeTypes();
/*    */     }
/* 57 */     return mimesFromSystem();
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 61 */     return "System Clipboard";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.SystemClipboard
 * JD-Core Version:    0.6.2
 */