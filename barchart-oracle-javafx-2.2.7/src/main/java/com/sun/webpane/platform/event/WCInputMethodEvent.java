/*    */ package com.sun.webpane.platform.event;
/*    */ 
/*    */ public class WCInputMethodEvent
/*    */ {
/*    */   public static final int INPUT_METHOD_TEXT_CHANGED = 0;
/*    */   public static final int CARET_POSITION_CHANGED = 1;
/*    */   private final int id;
/*    */   private final String composed;
/*    */   private final String committed;
/*    */   private final int[] attributes;
/*    */   private final int caretPosition;
/*    */ 
/*    */   public WCInputMethodEvent(String paramString1, String paramString2, int[] paramArrayOfInt, int paramInt)
/*    */   {
/* 19 */     this.id = 0;
/* 20 */     this.composed = paramString1;
/* 21 */     this.committed = paramString2;
/* 22 */     this.attributes = paramArrayOfInt;
/* 23 */     this.caretPosition = paramInt;
/*    */   }
/*    */ 
/*    */   public WCInputMethodEvent(int paramInt) {
/* 27 */     this.id = 1;
/* 28 */     this.composed = null;
/* 29 */     this.committed = null;
/* 30 */     this.attributes = null;
/* 31 */     this.caretPosition = paramInt;
/*    */   }
/*    */ 
/*    */   public int getID() {
/* 35 */     return this.id;
/*    */   }
/*    */ 
/*    */   public String getComposed() {
/* 39 */     return this.composed;
/*    */   }
/*    */ 
/*    */   public String getCommitted() {
/* 43 */     return this.committed;
/*    */   }
/*    */ 
/*    */   public int[] getAttributes() {
/* 47 */     return this.attributes;
/*    */   }
/*    */ 
/*    */   public int getCaretPosition() {
/* 51 */     return this.caretPosition;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.event.WCInputMethodEvent
 * JD-Core Version:    0.6.2
 */