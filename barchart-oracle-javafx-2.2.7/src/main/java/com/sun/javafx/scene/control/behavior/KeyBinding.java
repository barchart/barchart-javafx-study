/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ 
/*     */ public class KeyBinding
/*     */ {
/*     */   private KeyCode code;
/*  51 */   private EventType<KeyEvent> eventType = KeyEvent.KEY_PRESSED;
/*     */   private String action;
/*  53 */   private OptionalBoolean shift = OptionalBoolean.FALSE;
/*  54 */   private OptionalBoolean ctrl = OptionalBoolean.FALSE;
/*  55 */   private OptionalBoolean alt = OptionalBoolean.FALSE;
/*  56 */   private OptionalBoolean meta = OptionalBoolean.FALSE;
/*     */ 
/*     */   public KeyBinding(KeyCode paramKeyCode, String paramString) {
/*  59 */     this.code = paramKeyCode;
/*  60 */     this.action = paramString;
/*     */   }
/*     */ 
/*     */   public KeyBinding(KeyCode paramKeyCode, EventType<KeyEvent> paramEventType, String paramString) {
/*  64 */     this.code = paramKeyCode;
/*  65 */     this.eventType = paramEventType;
/*  66 */     this.action = paramString;
/*     */   }
/*     */ 
/*     */   public KeyBinding shift() {
/*  70 */     return shift(OptionalBoolean.TRUE);
/*     */   }
/*     */ 
/*     */   public KeyBinding shift(OptionalBoolean paramOptionalBoolean) {
/*  74 */     this.shift = paramOptionalBoolean;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public KeyBinding ctrl() {
/*  79 */     return ctrl(OptionalBoolean.TRUE);
/*     */   }
/*     */ 
/*     */   public KeyBinding ctrl(OptionalBoolean paramOptionalBoolean) {
/*  83 */     this.ctrl = paramOptionalBoolean;
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   public KeyBinding alt() {
/*  88 */     return alt(OptionalBoolean.TRUE);
/*     */   }
/*     */ 
/*     */   public KeyBinding alt(OptionalBoolean paramOptionalBoolean) {
/*  92 */     this.alt = paramOptionalBoolean;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public KeyBinding meta() {
/*  97 */     return meta(OptionalBoolean.TRUE);
/*     */   }
/*     */ 
/*     */   public KeyBinding meta(OptionalBoolean paramOptionalBoolean) {
/* 101 */     this.meta = paramOptionalBoolean;
/* 102 */     return this;
/*     */   }
/*     */   public final KeyCode getCode() {
/* 105 */     return this.code; } 
/* 106 */   public final EventType<KeyEvent> getType() { return this.eventType; } 
/* 107 */   public final String getAction() { return this.action; } 
/* 108 */   public final OptionalBoolean getShift() { return this.shift; } 
/* 109 */   public final OptionalBoolean getCtrl() { return this.ctrl; } 
/* 110 */   public final OptionalBoolean getAlt() { return this.alt; } 
/* 111 */   public final OptionalBoolean getMeta() { return this.meta; }
/*     */ 
/*     */   public int getSpecificity(Control paramControl, KeyEvent paramKeyEvent) {
/* 114 */     int i = 0;
/* 115 */     if ((this.code != null) && (this.code != paramKeyEvent.getCode())) return 0; i = 1;
/* 116 */     if (!this.shift.equals(paramKeyEvent.isShiftDown())) return 0; if (this.shift != OptionalBoolean.ANY) i++;
/* 117 */     if (!this.ctrl.equals(paramKeyEvent.isControlDown())) return 0; if (this.shift != OptionalBoolean.ANY) i++;
/* 118 */     if (!this.alt.equals(paramKeyEvent.isAltDown())) return 0; if (this.shift != OptionalBoolean.ANY) i++;
/* 119 */     if (!this.meta.equals(paramKeyEvent.isMetaDown())) return 0; if (this.shift != OptionalBoolean.ANY) i++;
/* 120 */     if ((this.eventType != null) && (this.eventType != paramKeyEvent.getEventType())) return 0; i++;
/*     */ 
/* 122 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 126 */     return "KeyBinding [code=" + this.code + ", shift=" + this.shift + ", ctrl=" + this.ctrl + ", alt=" + this.alt + ", meta=" + this.meta + ", type=" + this.eventType + ", action=" + this.action + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.KeyBinding
 * JD-Core Version:    0.6.2
 */