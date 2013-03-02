/*     */ package com.sun.javafx.scene.control;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ 
/*     */ public class Keystroke
/*     */ {
/*     */   private BooleanProperty shift;
/*     */   private BooleanProperty control;
/*     */   private BooleanProperty alt;
/*     */   private BooleanProperty meta;
/*     */   private ObjectProperty<KeyCode> code;
/*     */   private ObjectProperty<EventType<KeyEvent>> eventType;
/*     */ 
/*     */   public final void setShift(boolean paramBoolean)
/*     */   {
/*  44 */     shiftProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isShift() {
/*  48 */     return this.shift == null ? false : this.shift.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty shiftProperty() {
/*  52 */     if (this.shift == null) {
/*  53 */       this.shift = new SimpleBooleanProperty(this, "shift");
/*     */     }
/*  55 */     return this.shift;
/*     */   }
/*     */ 
/*     */   public final void setControl(boolean paramBoolean)
/*     */   {
/*  60 */     controlProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isControl() {
/*  64 */     return this.control == null ? false : this.control.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty controlProperty() {
/*  68 */     if (this.control == null) {
/*  69 */       this.control = new SimpleBooleanProperty(this, "control");
/*     */     }
/*  71 */     return this.control;
/*     */   }
/*     */ 
/*     */   public final void setAlt(boolean paramBoolean)
/*     */   {
/*  76 */     altProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isAlt() {
/*  80 */     return this.alt == null ? false : this.alt.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty altProperty() {
/*  84 */     if (this.alt == null) {
/*  85 */       this.alt = new SimpleBooleanProperty(this, "alt");
/*     */     }
/*  87 */     return this.alt;
/*     */   }
/*     */ 
/*     */   public final void setMeta(boolean paramBoolean)
/*     */   {
/*  92 */     metaProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isMeta() {
/*  96 */     return this.meta == null ? false : this.meta.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty metaProperty() {
/* 100 */     if (this.meta == null) {
/* 101 */       this.meta = new SimpleBooleanProperty(this, "meta");
/*     */     }
/* 103 */     return this.meta;
/*     */   }
/*     */ 
/*     */   public final void setCode(KeyCode paramKeyCode)
/*     */   {
/* 108 */     codeProperty().set(paramKeyCode);
/*     */   }
/*     */ 
/*     */   public final KeyCode getCode() {
/* 112 */     return this.code == null ? null : (KeyCode)this.code.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<KeyCode> codeProperty() {
/* 116 */     if (this.code == null) {
/* 117 */       this.code = new SimpleObjectProperty(this, "code");
/*     */     }
/* 119 */     return this.code;
/*     */   }
/*     */ 
/*     */   public final void setEventType(EventType<KeyEvent> paramEventType)
/*     */   {
/* 124 */     eventTypeProperty().set(paramEventType);
/*     */   }
/*     */ 
/*     */   public final EventType<KeyEvent> getEventType() {
/* 128 */     return this.eventType == null ? KeyEvent.KEY_PRESSED : (EventType)this.eventType.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventType<KeyEvent>> eventTypeProperty() {
/* 132 */     if (this.eventType == null) {
/* 133 */       this.eventType = new SimpleObjectProperty(this, "eventType");
/*     */     }
/* 135 */     return this.eventType;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 144 */     if ((paramObject instanceof Keystroke)) {
/* 145 */       Keystroke localKeystroke = (Keystroke)paramObject;
/* 146 */       return (isShift() == localKeystroke.isShift()) && (isControl() == localKeystroke.isControl()) && (isAlt() == localKeystroke.isAlt()) && (isMeta() == localKeystroke.isMeta()) && (getCode().equals(localKeystroke.getCode())) && (getEventType().equals(localKeystroke.getEventType()));
/*     */     }
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 156 */     int i = 7;
/* 157 */     i = 31 * i + Boolean.valueOf(isShift()).hashCode();
/* 158 */     i = 31 * i + Boolean.valueOf(isControl()).hashCode();
/* 159 */     i = 31 * i + Boolean.valueOf(isAlt()).hashCode();
/* 160 */     i = 31 * i + Boolean.valueOf(isMeta()).hashCode();
/* 161 */     i = 31 * i + getCode().hashCode();
/* 162 */     i = 31 * i + getEventType().hashCode();
/* 163 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 171 */     return "Keystroke [code=" + getCode() + ", shift=" + isShift() + ", control=" + isControl() + ", alt=" + isAlt() + ", meta=" + isMeta() + ", type=" + getEventType() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.Keystroke
 * JD-Core Version:    0.6.2
 */