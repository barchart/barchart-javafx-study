/*    */ package com.sun.webpane.platform.event;
/*    */ 
/*    */ public class WCKeyEvent
/*    */ {
/*    */   public static final int KEY_TYPED = 0;
/*    */   public static final int KEY_PRESSED = 1;
/*    */   public static final int KEY_RELEASED = 2;
/*    */   public static final int VK_BACK = 8;
/*    */   public static final int VK_TAB = 9;
/*    */   public static final int VK_RETURN = 13;
/*    */   public static final int VK_ESCAPE = 27;
/*    */   public static final int VK_PRIOR = 33;
/*    */   public static final int VK_NEXT = 34;
/*    */   public static final int VK_END = 35;
/*    */   public static final int VK_HOME = 36;
/*    */   public static final int VK_LEFT = 37;
/*    */   public static final int VK_UP = 38;
/*    */   public static final int VK_RIGHT = 39;
/*    */   public static final int VK_DOWN = 40;
/*    */   public static final int VK_INSERT = 45;
/*    */   public static final int VK_DELETE = 46;
/*    */   public static final int VK_OEM_PERIOD = 190;
/*    */   private final int type;
/*    */   private final String text;
/*    */   private final String keyIdentifier;
/*    */   private final int windowsVirtualKeyCode;
/*    */   private final boolean shift;
/*    */   private final boolean ctrl;
/*    */   private final boolean alt;
/*    */   private final boolean meta;
/*    */ 
/*    */   public WCKeyEvent(int paramInt1, String paramString1, String paramString2, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*    */   {
/* 45 */     this.type = paramInt1;
/* 46 */     this.text = paramString1;
/* 47 */     this.keyIdentifier = paramString2;
/* 48 */     this.windowsVirtualKeyCode = paramInt2;
/* 49 */     this.shift = paramBoolean1;
/* 50 */     this.ctrl = paramBoolean2;
/* 51 */     this.alt = paramBoolean3;
/* 52 */     this.meta = paramBoolean4;
/*    */   }
/*    */ 
/*    */   public int getType() {
/* 56 */     return this.type; } 
/* 57 */   public String getText() { return this.text; } 
/* 58 */   public String getKeyIdentifier() { return this.keyIdentifier; } 
/* 59 */   public int getWindowsVirtualKeyCode() { return this.windowsVirtualKeyCode; } 
/* 60 */   public boolean isShiftDown() { return this.shift; } 
/* 61 */   public boolean isCtrlDown() { return this.ctrl; } 
/* 62 */   public boolean isAltDown() { return this.alt; } 
/* 63 */   public boolean isMetaDown() { return this.meta; }
/*    */ 
/*    */   public static boolean filterEvent(WCKeyEvent paramWCKeyEvent) {
/* 66 */     if (paramWCKeyEvent.getType() == 0) {
/* 67 */       String str = paramWCKeyEvent.getText();
/* 68 */       if ((str == null) || (str.length() != 1)) {
/* 69 */         return true;
/*    */       }
/* 71 */       int i = str.charAt(0);
/*    */ 
/* 74 */       if ((i == 8) || (i == 10) || (i == 9) || (i == 65535) || (i == 24) || (i == 27) || (i == 127))
/*    */       {
/* 77 */         return true;
/*    */       }
/*    */     }
/* 80 */     return false;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.event.WCKeyEvent
 * JD-Core Version:    0.6.2
 */