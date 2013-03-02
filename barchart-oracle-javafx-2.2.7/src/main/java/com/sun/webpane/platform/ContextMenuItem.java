/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ public final class ContextMenuItem
/*    */ {
/*    */   public static final int ACTION_TYPE = 0;
/*    */   public static final int SEPARATOR_TYPE = 1;
/*    */   public static final int SUBMENU_TYPE = 2;
/*    */   private String title;
/*    */   private int action;
/*    */   private boolean isEnabled;
/*    */   private boolean isChecked;
/*    */   private int type;
/*    */   private ContextMenu submenu;
/*    */ 
/*    */   public String getTitle()
/*    */   {
/* 18 */     return this.title;
/*    */   }
/* 20 */   public int getAction() { return this.action; } 
/*    */   public boolean isEnabled() {
/* 22 */     return this.isEnabled;
/*    */   }
/* 24 */   public boolean isChecked() { return this.isChecked; } 
/*    */   public int getType() {
/* 26 */     return this.type;
/*    */   }
/* 28 */   public ContextMenu getSubmenu() { return this.submenu; }
/*    */ 
/*    */   public String toString() {
/* 31 */     return new String(new StringBuilder().append(super.toString()).append(" [title=\"").append(this.title).append("\", action=").append(this.action).append(", isEnabled=").append(this.isEnabled).append(", isChecked=").append(this.isChecked).append(", type=").append(this.type == 1 ? "SEPARATOR_TYPE" : this.type == 0 ? "ACTION_TYPE" : "SUBMENU_TYPE").append("]").toString());
/*    */   }
/*    */ 
/*    */   private static ContextMenuItem fwkCreateContextMenuItem()
/*    */   {
/* 39 */     return new ContextMenuItem();
/*    */   }
/*    */ 
/*    */   private void fwkSetTitle(String paramString) {
/* 43 */     this.title = paramString;
/*    */   }
/*    */ 
/*    */   private String fwkGetTitle() {
/* 47 */     return getTitle();
/*    */   }
/*    */ 
/*    */   private void fwkSetAction(int paramInt) {
/* 51 */     this.action = paramInt;
/*    */   }
/*    */ 
/*    */   private int fwkGetAction() {
/* 55 */     return getAction();
/*    */   }
/*    */ 
/*    */   private void fwkSetEnabled(boolean paramBoolean) {
/* 59 */     this.isEnabled = paramBoolean;
/*    */   }
/*    */ 
/*    */   private boolean fwkIsEnabled() {
/* 63 */     return isEnabled();
/*    */   }
/*    */ 
/*    */   private void fwkSetChecked(boolean paramBoolean) {
/* 67 */     this.isChecked = paramBoolean;
/*    */   }
/*    */ 
/*    */   private void fwkSetType(int paramInt) {
/* 71 */     this.type = paramInt;
/*    */   }
/*    */ 
/*    */   private int fwkGetType() {
/* 75 */     return getType();
/*    */   }
/*    */ 
/*    */   private void fwkSetSubmenu(ContextMenu paramContextMenu) {
/* 79 */     this.submenu = paramContextMenu;
/*    */   }
/*    */ 
/*    */   private ContextMenu fwkGetSubmenu() {
/* 83 */     return getSubmenu();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.ContextMenuItem
 * JD-Core Version:    0.6.2
 */