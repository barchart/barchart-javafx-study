/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.SystemClipboard;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ class GtkSystemClipboard extends SystemClipboard
/*    */ {
/*    */   public GtkSystemClipboard()
/*    */   {
/* 18 */     super("SYSTEM");
/*    */   }
/*    */ 
/*    */   protected native boolean isOwner();
/*    */ 
/*    */   protected native void pushToSystem(HashMap<String, Object> paramHashMap, int paramInt);
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
 * Qualified Name:     com.sun.glass.ui.gtk.GtkSystemClipboard
 * JD-Core Version:    0.6.2
 */