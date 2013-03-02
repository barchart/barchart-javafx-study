/*    */ package com.sun.javafx.scene.control.behavior;
/*    */ 
/*    */ import javafx.event.EventType;
/*    */ import javafx.scene.control.Control;
/*    */ import javafx.scene.input.KeyCode;
/*    */ import javafx.scene.input.KeyEvent;
/*    */ 
/*    */ public abstract class OrientedKeyBinding extends KeyBinding
/*    */ {
/* 41 */   private OptionalBoolean vertical = OptionalBoolean.FALSE;
/*    */ 
/*    */   public OrientedKeyBinding(KeyCode paramKeyCode, String paramString) {
/* 44 */     super(paramKeyCode, paramString);
/*    */   }
/*    */ 
/*    */   public OrientedKeyBinding(KeyCode paramKeyCode, EventType<KeyEvent> paramEventType, String paramString) {
/* 48 */     super(paramKeyCode, paramEventType, paramString);
/*    */   }
/*    */ 
/*    */   public OrientedKeyBinding vertical() {
/* 52 */     this.vertical = OptionalBoolean.TRUE;
/* 53 */     return this;
/*    */   }
/*    */ 
/*    */   protected abstract boolean getVertical(Control paramControl);
/*    */ 
/*    */   public int getSpecificity(Control paramControl, KeyEvent paramKeyEvent)
/*    */   {
/* 61 */     boolean bool = getVertical(paramControl);
/* 62 */     if (!this.vertical.equals(bool)) return 0;
/*    */ 
/* 64 */     int i = super.getSpecificity(paramControl, paramKeyEvent);
/*    */ 
/* 67 */     if (i == 0) return 0;
/*    */ 
/* 69 */     return this.vertical != OptionalBoolean.ANY ? i + 1 : i;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 73 */     return "OrientedKeyBinding [code=" + getCode() + ", shift=" + getShift() + ", ctrl=" + getCtrl() + ", alt=" + getAlt() + ", meta=" + getMeta() + ", type=" + getType() + ", vertical=" + this.vertical + ", action=" + getAction() + "]";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.OrientedKeyBinding
 * JD-Core Version:    0.6.2
 */