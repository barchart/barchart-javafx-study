/*     */ package javafx.scene.input;
/*     */ 
/*     */ import com.sun.javafx.robot.impl.FXRobotHelper;
/*     */ import com.sun.javafx.robot.impl.FXRobotHelper.FXRobotInputAccessor;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public class KeyEvent extends InputEvent
/*     */ {
/*  87 */   public static final EventType<KeyEvent> ANY = new EventType(InputEvent.ANY, "KEY");
/*     */ 
/*  93 */   public static final EventType<KeyEvent> KEY_PRESSED = new EventType(ANY, "KEY_PRESSED");
/*     */ 
/*  99 */   public static final EventType<KeyEvent> KEY_RELEASED = new EventType(ANY, "KEY_RELEASED");
/*     */ 
/* 107 */   public static final EventType<KeyEvent> KEY_TYPED = new EventType(ANY, "KEY_TYPED");
/*     */ 
/* 222 */   public static final String CHAR_UNDEFINED = KeyCode.UNDEFINED.ch;
/*     */   private String character;
/*     */   private String text;
/*     */   private KeyCode code;
/*     */   private boolean shiftDown;
/*     */   private boolean controlDown;
/*     */   private boolean altDown;
/*     */   private boolean metaDown;
/*     */ 
/*     */   private KeyEvent(EventType<? extends KeyEvent> paramEventType)
/*     */   {
/* 111 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   private KeyEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends KeyEvent> paramEventType)
/*     */   {
/* 117 */     super(paramObject, paramEventTarget, paramEventType);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static KeyEvent impl_copy(EventTarget paramEventTarget, KeyEvent paramKeyEvent)
/*     */   {
/* 193 */     return (KeyEvent)paramKeyEvent.copyFor(paramKeyEvent.source, paramEventTarget);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static KeyEvent impl_keyEvent(EventTarget paramEventTarget, String paramString1, String paramString2, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, EventType<? extends KeyEvent> paramEventType)
/*     */   {
/* 205 */     int i = paramEventType == KEY_TYPED ? 1 : 0;
/*     */ 
/* 207 */     KeyEvent localKeyEvent = new KeyEvent(null, paramEventTarget, paramEventType);
/* 208 */     localKeyEvent.character = (i != 0 ? paramString1 : CHAR_UNDEFINED);
/* 209 */     localKeyEvent.text = (i != 0 ? "" : paramString2);
/* 210 */     localKeyEvent.code = (i != 0 ? KeyCode.UNDEFINED : KeyCode.impl_valueOf(paramInt));
/* 211 */     localKeyEvent.shiftDown = paramBoolean1;
/* 212 */     localKeyEvent.controlDown = paramBoolean2;
/* 213 */     localKeyEvent.altDown = paramBoolean3;
/* 214 */     localKeyEvent.metaDown = paramBoolean4;
/* 215 */     return localKeyEvent;
/*     */   }
/*     */ 
/*     */   static KeyEvent testKeyEvent(EventTarget paramEventTarget, String paramString, KeyCode paramKeyCode, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*     */   {
/* 232 */     KeyEvent localKeyEvent = new KeyEvent(null, paramEventTarget, KEY_PRESSED);
/* 233 */     localKeyEvent.character = paramString;
/* 234 */     localKeyEvent.code = paramKeyCode;
/* 235 */     localKeyEvent.shiftDown = paramBoolean1;
/* 236 */     localKeyEvent.controlDown = paramBoolean2;
/* 237 */     localKeyEvent.altDown = paramBoolean3;
/* 238 */     localKeyEvent.metaDown = paramBoolean4;
/* 239 */     return localKeyEvent;
/*     */   }
/*     */ 
/*     */   public final String getCharacter()
/*     */   {
/* 261 */     return this.character;
/*     */   }
/*     */ 
/*     */   public final String getText()
/*     */   {
/* 279 */     return this.text;
/*     */   }
/*     */ 
/*     */   public final KeyCode getCode()
/*     */   {
/* 297 */     return this.code;
/*     */   }
/*     */ 
/*     */   public final boolean isShiftDown()
/*     */   {
/* 310 */     return this.shiftDown;
/*     */   }
/*     */ 
/*     */   public final boolean isControlDown()
/*     */   {
/* 323 */     return this.controlDown;
/*     */   }
/*     */ 
/*     */   public final boolean isAltDown()
/*     */   {
/* 336 */     return this.altDown;
/*     */   }
/*     */ 
/*     */   public final boolean isMetaDown()
/*     */   {
/* 349 */     return this.metaDown;
/*     */   }
/*     */ 
/*     */   public final boolean isShortcutDown()
/*     */   {
/* 363 */     switch (2.$SwitchMap$javafx$scene$input$KeyCode[com.sun.javafx.tk.Toolkit.getToolkit().getPlatformShortcutKey().ordinal()]) {
/*     */     case 1:
/* 365 */       return this.shiftDown;
/*     */     case 2:
/* 368 */       return this.controlDown;
/*     */     case 3:
/* 371 */       return this.altDown;
/*     */     case 4:
/* 374 */       return this.metaDown;
/*     */     }
/*     */ 
/* 377 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 386 */     StringBuilder localStringBuilder = new StringBuilder("KeyEvent [");
/*     */ 
/* 388 */     localStringBuilder.append("source = ").append(getSource());
/* 389 */     localStringBuilder.append(", target = ").append(getTarget());
/* 390 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 391 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/*     */ 
/* 393 */     localStringBuilder.append(", character = ").append(getCharacter());
/* 394 */     localStringBuilder.append(", text = ").append(getText());
/* 395 */     localStringBuilder.append(", code = ").append(getCode());
/*     */ 
/* 397 */     if (isShiftDown()) {
/* 398 */       localStringBuilder.append(", shiftDown");
/*     */     }
/* 400 */     if (isControlDown()) {
/* 401 */       localStringBuilder.append(", controlDown");
/*     */     }
/* 403 */     if (isAltDown()) {
/* 404 */       localStringBuilder.append(", altDown");
/*     */     }
/* 406 */     if (isMetaDown()) {
/* 407 */       localStringBuilder.append(", metaDown");
/*     */     }
/* 409 */     if (isShortcutDown()) {
/* 410 */       localStringBuilder.append(", shortcutDown");
/*     */     }
/*     */ 
/* 413 */     return localStringBuilder.append("]").toString();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 121 */     FXRobotHelper.FXRobotInputAccessor local1 = new FXRobotHelper.FXRobotInputAccessor() {
/*     */       public int getCodeForKeyCode(KeyCode paramAnonymousKeyCode) {
/* 123 */         return paramAnonymousKeyCode.code;
/*     */       }
/*     */       public KeyCode getKeyCodeForCode(int paramAnonymousInt) {
/* 126 */         return KeyCode.impl_valueOf(paramAnonymousInt);
/*     */       }
/*     */ 
/*     */       public KeyEvent createKeyEvent(EventType<? extends KeyEvent> paramAnonymousEventType, KeyCode paramAnonymousKeyCode, String paramAnonymousString1, String paramAnonymousString2, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, boolean paramAnonymousBoolean3, boolean paramAnonymousBoolean4)
/*     */       {
/* 134 */         KeyEvent localKeyEvent = new KeyEvent(paramAnonymousEventType, null);
/* 135 */         localKeyEvent.character = paramAnonymousString1;
/* 136 */         localKeyEvent.code = paramAnonymousKeyCode;
/* 137 */         localKeyEvent.text = paramAnonymousString2;
/* 138 */         localKeyEvent.shiftDown = paramAnonymousBoolean1;
/* 139 */         localKeyEvent.controlDown = paramAnonymousBoolean2;
/* 140 */         localKeyEvent.altDown = paramAnonymousBoolean3;
/* 141 */         localKeyEvent.metaDown = paramAnonymousBoolean4;
/* 142 */         return localKeyEvent;
/*     */       }
/*     */ 
/*     */       public MouseEvent createMouseEvent(EventType<? extends MouseEvent> paramAnonymousEventType, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, MouseButton paramAnonymousMouseButton, int paramAnonymousInt5, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, boolean paramAnonymousBoolean3, boolean paramAnonymousBoolean4, boolean paramAnonymousBoolean5, boolean paramAnonymousBoolean6, boolean paramAnonymousBoolean7, boolean paramAnonymousBoolean8)
/*     */       {
/* 152 */         return MouseEvent.impl_mouseEvent(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousMouseButton, paramAnonymousInt5, paramAnonymousBoolean1, paramAnonymousBoolean2, paramAnonymousBoolean3, paramAnonymousBoolean4, paramAnonymousBoolean5, paramAnonymousBoolean6, paramAnonymousBoolean7, paramAnonymousBoolean8, false, paramAnonymousEventType);
/*     */       }
/*     */ 
/*     */       public ScrollEvent createScrollEvent(EventType<? extends ScrollEvent> paramAnonymousEventType, int paramAnonymousInt1, int paramAnonymousInt2, ScrollEvent.HorizontalTextScrollUnits paramAnonymousHorizontalTextScrollUnits, int paramAnonymousInt3, ScrollEvent.VerticalTextScrollUnits paramAnonymousVerticalTextScrollUnits, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, boolean paramAnonymousBoolean3, boolean paramAnonymousBoolean4)
/*     */       {
/* 176 */         return ScrollEvent.impl_scrollEvent(ScrollEvent.SCROLL, paramAnonymousInt1, paramAnonymousInt2, 0.0D, 0.0D, paramAnonymousHorizontalTextScrollUnits, paramAnonymousInt3, paramAnonymousVerticalTextScrollUnits, paramAnonymousInt4, 0, paramAnonymousInt5, paramAnonymousInt6, paramAnonymousInt7, paramAnonymousInt8, paramAnonymousBoolean1, paramAnonymousBoolean2, paramAnonymousBoolean3, paramAnonymousBoolean4, false, false);
/*     */       }
/*     */     };
/* 184 */     FXRobotHelper.setInputAccessor(local1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.KeyEvent
 * JD-Core Version:    0.6.2
 */