/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.SystemClipboard;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class GtkDnDClipboard extends SystemClipboard
/*    */ {
/*    */   public GtkDnDClipboard()
/*    */   {
/* 19 */     super("DND");
/*    */   }
/*    */ 
/*    */   protected void pushToSystem(HashMap<String, Object> cacheData, int supportedActions)
/*    */   {
/* 24 */     int performedAction = pushToSystemImpl(cacheData, supportedActions);
/*    */ 
/* 26 */     actionPerformed(performedAction);
/*    */   }
/*    */ 
/*    */   protected native boolean isOwner();
/*    */ 
/*    */   protected native int pushToSystemImpl(HashMap<String, Object> paramHashMap, int paramInt);
/*    */ 
/*    */   protected native void pushTargetActionToSystem(int paramInt);
/*    */ 
/*    */   protected native Object popFromSystem(String paramString);
/*    */ 
/*    */   protected native int supportedSourceActionsFromSystem();
/*    */ 
/*    */   protected native String[] mimesFromSystem();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkDnDClipboard
 * JD-Core Version:    0.6.2
 */