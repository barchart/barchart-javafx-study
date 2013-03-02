/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ public class WinDnDClipboard extends WinSystemClipboard
/*    */ {
/* 56 */   private static int dragButton = 0;
/*    */ 
/* 72 */   private int sourceSupportedActions = 0;
/*    */ 
/*    */   public WinDnDClipboard(String name)
/*    */   {
/*  8 */     super(name);
/*    */   }
/*    */   protected void create() {
/*    */   }
/*    */ 
/*    */   protected native void dispose();
/*    */ 
/*    */   protected boolean isOwner() {
/* 16 */     return getDragButton() != 0;
/*    */   }
/*    */ 
/*    */   protected void pushTargetActionToSystem(int actionDone) {
/* 20 */     throw new UnsupportedOperationException("[Target Action] not supported! Override View.handleDragDrop instead.");
/*    */   }
/*    */ 
/*    */   protected native void push(Object[] paramArrayOfObject, int paramInt);
/*    */ 
/*    */   protected boolean pop()
/*    */   {
/* 35 */     return getPtr() != 0L;
/*    */   }
/*    */ 
/*    */   static WinDnDClipboard getInstance()
/*    */   {
/* 42 */     return (WinDnDClipboard)get("DND");
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 46 */     return "Windows DnD Clipboard";
/*    */   }
/*    */ 
/*    */   public int getDragButton()
/*    */   {
/* 59 */     return dragButton;
/*    */   }
/*    */ 
/*    */   private void setDragButton(int dragButton)
/*    */   {
/* 66 */     dragButton = dragButton;
/*    */   }
/*    */ 
/*    */   protected int supportedSourceActionsFromSystem()
/*    */   {
/* 75 */     return this.sourceSupportedActions != 0 ? this.sourceSupportedActions : super.supportedSourceActionsFromSystem();
/*    */   }
/*    */ 
/*    */   private void setSourceSupportedActions(int sourceSupportedActions)
/*    */   {
/* 84 */     this.sourceSupportedActions = sourceSupportedActions;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinDnDClipboard
 * JD-Core Version:    0.6.2
 */