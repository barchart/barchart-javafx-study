/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.input.DragEvent;
/*     */ import javafx.scene.input.Dragboard;
/*     */ import javafx.scene.input.InputMethodEvent;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.input.TransferMode;
/*     */ 
/*     */ public class PrismEventUtils
/*     */ {
/*     */   private static EventType<MouseEvent> glassMouseEventType(GlassPrismMouseEvent paramGlassPrismMouseEvent)
/*     */   {
/*  23 */     switch (paramGlassPrismMouseEvent.type()) {
/*     */     case 221:
/*  25 */       return MouseEvent.MOUSE_PRESSED;
/*     */     case 222:
/*  27 */       return MouseEvent.MOUSE_RELEASED;
/*     */     case 227:
/*  29 */       return MouseEvent.MOUSE_CLICKED;
/*     */     case 225:
/*  31 */       return MouseEvent.MOUSE_ENTERED;
/*     */     case 226:
/*  33 */       return MouseEvent.MOUSE_EXITED;
/*     */     case 224:
/*  35 */       return MouseEvent.MOUSE_MOVED;
/*     */     case 223:
/*  37 */       return MouseEvent.MOUSE_DRAGGED;
/*     */     case 228:
/*  39 */       throw new IllegalArgumentException("WHEEL event cannot be translated to MouseEvent, must be translated to ScrollEvent");
/*     */     }
/*     */ 
/*  43 */     throw new UnsupportedOperationException("unhandled MouseEvent type: " + paramGlassPrismMouseEvent.type());
/*     */   }
/*     */ 
/*     */   private static EventType<KeyEvent> glassKeyEventType(GlassPrismKeyEvent paramGlassPrismKeyEvent)
/*     */   {
/*  48 */     switch (paramGlassPrismKeyEvent.type()) {
/*     */     case 111:
/*  50 */       return KeyEvent.KEY_PRESSED;
/*     */     case 112:
/*  52 */       return KeyEvent.KEY_RELEASED;
/*     */     case 113:
/*  54 */       return KeyEvent.KEY_TYPED;
/*     */     }
/*  56 */     return null;
/*     */   }
/*     */ 
/*     */   public static MouseEvent glassMouseEventToFX(GlassPrismMouseEvent paramGlassPrismMouseEvent)
/*     */   {
/*  64 */     int i = paramGlassPrismMouseEvent.x();
/*  65 */     int j = paramGlassPrismMouseEvent.y();
/*  66 */     int k = paramGlassPrismMouseEvent.xAbs();
/*  67 */     int m = paramGlassPrismMouseEvent.yAbs();
/*     */     MouseButton localMouseButton;
/*  69 */     switch (paramGlassPrismMouseEvent.button()) {
/*     */     case 212:
/*  71 */       localMouseButton = MouseButton.PRIMARY;
/*  72 */       break;
/*     */     case 213:
/*  74 */       localMouseButton = MouseButton.SECONDARY;
/*  75 */       break;
/*     */     case 214:
/*  77 */       localMouseButton = MouseButton.MIDDLE;
/*  78 */       break;
/*     */     default:
/*  80 */       localMouseButton = MouseButton.NONE;
/*     */     }
/*     */ 
/*  83 */     int n = paramGlassPrismMouseEvent.clickCount();
/*  84 */     int i1 = paramGlassPrismMouseEvent.modifiers();
/*  85 */     boolean bool1 = (i1 & 0x1) > 0;
/*  86 */     boolean bool2 = (i1 & 0x4) > 0;
/*  87 */     boolean bool3 = (i1 & 0x8) > 0;
/*  88 */     boolean bool4 = (i1 & 0x10) > 0;
/*  89 */     boolean bool5 = paramGlassPrismMouseEvent.isPopupTrigger();
/*  90 */     boolean bool6 = (i1 & 0x20) > 0;
/*  91 */     boolean bool7 = (i1 & 0x80) > 0;
/*  92 */     boolean bool8 = (i1 & 0x40) > 0;
/*  93 */     boolean bool9 = paramGlassPrismMouseEvent.isSynthesized();
/*  94 */     EventType localEventType = glassMouseEventType(paramGlassPrismMouseEvent);
/*     */ 
/*  96 */     return MouseEvent.impl_mouseEvent(i, j, k, m, localMouseButton, n, bool1, bool2, bool3, bool4, bool5, bool6, bool7, bool8, bool9, localEventType);
/*     */   }
/*     */ 
/*     */   public static KeyEvent glassKeyEventToFX(GlassPrismKeyEvent paramGlassPrismKeyEvent)
/*     */   {
/* 115 */     int i = paramGlassPrismKeyEvent.type();
/* 116 */     String str1 = new String(paramGlassPrismKeyEvent.characters());
/* 117 */     String str2 = str1;
/* 118 */     int j = paramGlassPrismKeyEvent.key();
/* 119 */     int k = paramGlassPrismKeyEvent.modifiers();
/* 120 */     boolean bool1 = (k & 0x1) > 0;
/* 121 */     boolean bool2 = (k & 0x4) > 0;
/* 122 */     boolean bool3 = (k & 0x8) > 0;
/* 123 */     boolean bool4 = (k & 0x10) > 0;
/* 124 */     EventType localEventType = glassKeyEventType(paramGlassPrismKeyEvent);
/*     */ 
/* 126 */     return KeyEvent.impl_keyEvent(null, str1, str2, j, bool1, bool2, bool3, bool4, localEventType);
/*     */   }
/*     */ 
/*     */   public static InputMethodEvent glassInputMethodEventToFX(GlassPrismInputMethodEvent paramGlassPrismInputMethodEvent)
/*     */   {
/* 140 */     return InputMethodEvent.impl_inputMethodEvent(null, paramGlassPrismInputMethodEvent.getComposed(), paramGlassPrismInputMethodEvent.getCommitted(), paramGlassPrismInputMethodEvent.getCursorPos(), InputMethodEvent.INPUT_METHOD_TEXT_CHANGED);
/*     */   }
/*     */ 
/*     */   private static DragEvent glassDragEventToFX(GlassDragEvent paramGlassDragEvent, Dragboard paramDragboard)
/*     */   {
/* 147 */     DragEvent localDragEvent = DragEvent.impl_create(paramGlassDragEvent.getX(), paramGlassDragEvent.getY(), paramGlassDragEvent.getXAbs(), paramGlassDragEvent.getYAbs(), convertToTransferMode(paramGlassDragEvent.getRecommendedDropAction()), paramDragboard != null ? paramDragboard : paramGlassDragEvent.getDragboard(), paramGlassDragEvent);
/*     */ 
/* 155 */     localDragEvent.impl_setRecognizedEvent(paramGlassDragEvent);
/* 156 */     return localDragEvent;
/*     */   }
/*     */ 
/*     */   public static DragEvent glassDragGestureToFX(GlassDragEvent paramGlassDragEvent, Dragboard paramDragboard)
/*     */   {
/* 162 */     return glassDragEventToFX(paramGlassDragEvent, paramDragboard);
/*     */   }
/*     */ 
/*     */   public static DragEvent glassDragSourceEventToFX(GlassDragEvent paramGlassDragEvent, Dragboard paramDragboard)
/*     */   {
/* 167 */     return glassDragEventToFX(paramGlassDragEvent, paramDragboard);
/*     */   }
/*     */ 
/*     */   public static DragEvent glassDropTargetEventToFX(GlassDragEvent paramGlassDragEvent, Dragboard paramDragboard)
/*     */   {
/* 172 */     return glassDragEventToFX(paramGlassDragEvent, paramDragboard);
/*     */   }
/*     */ 
/*     */   public static TransferMode convertToTransferMode(int paramInt) {
/* 176 */     if (paramInt == 0)
/* 177 */       return null;
/* 178 */     if ((paramInt == 1) || (paramInt == 1073741825))
/*     */     {
/* 182 */       return TransferMode.COPY;
/* 183 */     }if ((paramInt == 2) || (paramInt == 1073741826))
/*     */     {
/* 187 */       return TransferMode.MOVE;
/* 188 */     }if (paramInt == 1073741824)
/* 189 */       return TransferMode.LINK;
/* 190 */     if (paramInt == 1342177279)
/* 191 */       return TransferMode.COPY;
/* 192 */     if (paramInt == 3) {
/* 193 */       throw new IllegalArgumentException("PrismEventUtils.convertToTransferMode: ambiguous drop action: " + Integer.toHexString(paramInt));
/*     */     }
/* 195 */     throw new IllegalArgumentException("PrismEventUtils.convertToTransferMode: bad drop action: " + Integer.toHexString(paramInt));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.PrismEventUtils
 * JD-Core Version:    0.6.2
 */