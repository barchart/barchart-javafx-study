/*    */ package javafx.stage;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventHandler;
/*    */ 
/*    */ public abstract class PopupWindowBuilder<B extends PopupWindowBuilder<B>> extends WindowBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private boolean autoFix;
/*    */   private boolean autoHide;
/*    */   private boolean consumeAutoHidingEvents;
/*    */   private boolean hideOnEscape;
/*    */   private EventHandler<Event> onAutoHide;
/*    */ 
/*    */   public void applyTo(PopupWindow paramPopupWindow)
/*    */   {
/* 15 */     super.applyTo(paramPopupWindow);
/* 16 */     int i = this.__set;
/* 17 */     if ((i & 0x1) != 0) paramPopupWindow.setAutoFix(this.autoFix);
/* 18 */     if ((i & 0x2) != 0) paramPopupWindow.setAutoHide(this.autoHide);
/* 19 */     if ((i & 0x4) != 0) paramPopupWindow.setConsumeAutoHidingEvents(this.consumeAutoHidingEvents);
/* 20 */     if ((i & 0x8) != 0) paramPopupWindow.setHideOnEscape(this.hideOnEscape);
/* 21 */     if ((i & 0x10) != 0) paramPopupWindow.setOnAutoHide(this.onAutoHide);
/*    */   }
/*    */ 
/*    */   public B autoFix(boolean paramBoolean)
/*    */   {
/* 30 */     this.autoFix = paramBoolean;
/* 31 */     this.__set |= 1;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public B autoHide(boolean paramBoolean)
/*    */   {
/* 41 */     this.autoHide = paramBoolean;
/* 42 */     this.__set |= 2;
/* 43 */     return this;
/*    */   }
/*    */ 
/*    */   public B consumeAutoHidingEvents(boolean paramBoolean)
/*    */   {
/* 52 */     this.consumeAutoHidingEvents = paramBoolean;
/* 53 */     this.__set |= 4;
/* 54 */     return this;
/*    */   }
/*    */ 
/*    */   public B hideOnEscape(boolean paramBoolean)
/*    */   {
/* 63 */     this.hideOnEscape = paramBoolean;
/* 64 */     this.__set |= 8;
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */   public B onAutoHide(EventHandler<Event> paramEventHandler)
/*    */   {
/* 74 */     this.onAutoHide = paramEventHandler;
/* 75 */     this.__set |= 16;
/* 76 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.PopupWindowBuilder
 * JD-Core Version:    0.6.2
 */