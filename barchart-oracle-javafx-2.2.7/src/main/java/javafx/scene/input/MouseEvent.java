/*     */ package javafx.scene.input;
/*     */ 
/*     */ import com.sun.javafx.scene.input.InputEventUtils;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class MouseEvent extends InputEvent
/*     */ {
/* 133 */   public static final EventType<MouseEvent> ANY = new EventType(InputEvent.ANY, "MOUSE");
/*     */ 
/* 141 */   public static final EventType<MouseEvent> MOUSE_PRESSED = new EventType(ANY, "MOUSE_PRESSED");
/*     */ 
/* 149 */   public static final EventType<MouseEvent> MOUSE_RELEASED = new EventType(ANY, "MOUSE_RELEASED");
/*     */ 
/* 159 */   public static final EventType<MouseEvent> MOUSE_CLICKED = new EventType(ANY, "MOUSE_CLICKED");
/*     */ 
/* 171 */   public static final EventType<MouseEvent> MOUSE_ENTERED_TARGET = new EventType(ANY, "MOUSE_ENTERED_TARGET");
/*     */ 
/* 181 */   public static final EventType<MouseEvent> MOUSE_ENTERED = new EventType(MOUSE_ENTERED_TARGET, "MOUSE_ENTERED");
/*     */ 
/* 193 */   public static final EventType<MouseEvent> MOUSE_EXITED_TARGET = new EventType(ANY, "MOUSE_EXITED_TARGET");
/*     */ 
/* 203 */   public static final EventType<MouseEvent> MOUSE_EXITED = new EventType(MOUSE_EXITED_TARGET, "MOUSE_EXITED");
/*     */ 
/* 211 */   public static final EventType<MouseEvent> MOUSE_MOVED = new EventType(ANY, "MOUSE_MOVED");
/*     */ 
/* 220 */   public static final EventType<MouseEvent> MOUSE_DRAGGED = new EventType(ANY, "MOUSE_DRAGGED");
/*     */ 
/* 240 */   public static final EventType<MouseEvent> DRAG_DETECTED = new EventType(ANY, "DRAG_DETECTED");
/*     */ 
/* 459 */   private Flags flags = new Flags(null);
/*     */   private double x;
/*     */   private double y;
/*     */   private double screenX;
/*     */   private double screenY;
/*     */   private double sceneX;
/*     */   private double sceneY;
/*     */   private MouseButton button;
/*     */   private int clickCount;
/*     */   private boolean stillSincePress;
/*     */   private boolean shiftDown;
/*     */   private boolean controlDown;
/*     */   private boolean altDown;
/*     */   private boolean metaDown;
/*     */   private boolean synthesized;
/*     */   private boolean popupTrigger;
/*     */   private boolean primaryButtonDown;
/*     */   private boolean secondaryButtonDown;
/*     */   private boolean middleButtonDown;
/*     */ 
/*     */   MouseEvent(EventType<? extends MouseEvent> paramEventType)
/*     */   {
/* 244 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   MouseEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends MouseEvent> paramEventType)
/*     */   {
/* 249 */     super(paramObject, paramEventTarget, paramEventType);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static MouseEvent impl_copy(Node paramNode1, Node paramNode2, MouseEvent paramMouseEvent)
/*     */   {
/* 263 */     return impl_copy(paramNode1, paramNode2, paramMouseEvent, null);
/*     */   }
/*     */ 
/*     */   private void recomputeCoordinatesToSource(MouseEvent paramMouseEvent, Object paramObject)
/*     */   {
/* 274 */     Point2D localPoint2D = InputEventUtils.recomputeCoordinates(new Point2D(this.sceneX, this.sceneY), null, paramObject);
/*     */ 
/* 277 */     paramMouseEvent.x = localPoint2D.getX();
/* 278 */     paramMouseEvent.y = localPoint2D.getY();
/*     */   }
/*     */ 
/*     */   public Event copyFor(Object paramObject, EventTarget paramEventTarget)
/*     */   {
/* 291 */     MouseEvent localMouseEvent = (MouseEvent)super.copyFor(paramObject, paramEventTarget);
/* 292 */     recomputeCoordinatesToSource(localMouseEvent, paramObject);
/* 293 */     return localMouseEvent;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_setClickParams(int paramInt, boolean paramBoolean)
/*     */   {
/* 302 */     this.clickCount = paramInt;
/* 303 */     this.stillSincePress = paramBoolean;
/*     */   }
/*     */ 
/*     */   static void copyFields(MouseEvent paramMouseEvent1, MouseEvent paramMouseEvent2, Object paramObject, EventTarget paramEventTarget)
/*     */   {
/* 312 */     paramMouseEvent2.x = paramMouseEvent1.x;
/* 313 */     paramMouseEvent2.y = paramMouseEvent1.y;
/* 314 */     paramMouseEvent2.screenX = paramMouseEvent1.screenX;
/* 315 */     paramMouseEvent2.screenY = paramMouseEvent1.screenY;
/* 316 */     paramMouseEvent2.sceneX = paramMouseEvent1.sceneX;
/* 317 */     paramMouseEvent2.sceneY = paramMouseEvent1.sceneY;
/* 318 */     paramMouseEvent2.button = paramMouseEvent1.button;
/* 319 */     paramMouseEvent2.clickCount = paramMouseEvent1.clickCount;
/* 320 */     paramMouseEvent2.stillSincePress = paramMouseEvent1.stillSincePress;
/* 321 */     paramMouseEvent2.shiftDown = paramMouseEvent1.shiftDown;
/* 322 */     paramMouseEvent2.controlDown = paramMouseEvent1.controlDown;
/* 323 */     paramMouseEvent2.altDown = paramMouseEvent1.altDown;
/* 324 */     paramMouseEvent2.metaDown = paramMouseEvent1.metaDown;
/* 325 */     paramMouseEvent2.popupTrigger = paramMouseEvent1.popupTrigger;
/* 326 */     paramMouseEvent2.primaryButtonDown = paramMouseEvent1.primaryButtonDown;
/* 327 */     paramMouseEvent2.secondaryButtonDown = paramMouseEvent1.secondaryButtonDown;
/* 328 */     paramMouseEvent2.middleButtonDown = paramMouseEvent1.middleButtonDown;
/* 329 */     paramMouseEvent2.synthesized = paramMouseEvent1.synthesized;
/* 330 */     paramMouseEvent2.source = paramObject;
/* 331 */     paramMouseEvent2.target = paramEventTarget;
/*     */ 
/* 333 */     paramMouseEvent1.recomputeCoordinatesToSource(paramMouseEvent2, paramObject);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static MouseEvent impl_copy(Object paramObject, EventTarget paramEventTarget, MouseEvent paramMouseEvent, EventType<? extends MouseEvent> paramEventType)
/*     */   {
/* 343 */     MouseEvent localMouseEvent = impl_mouseEvent(paramObject, paramEventTarget, paramMouseEvent.x, paramMouseEvent.y, paramMouseEvent.screenX, paramMouseEvent.screenY, paramMouseEvent.button, paramMouseEvent.clickCount, paramMouseEvent.stillSincePress, paramMouseEvent.shiftDown, paramMouseEvent.controlDown, paramMouseEvent.altDown, paramMouseEvent.metaDown, paramMouseEvent.popupTrigger, paramMouseEvent.primaryButtonDown, paramMouseEvent.middleButtonDown, paramMouseEvent.secondaryButtonDown, paramMouseEvent.synthesized, paramEventType != null ? paramEventType : paramMouseEvent.getEventType());
/*     */ 
/* 353 */     localMouseEvent.sceneX = paramMouseEvent.sceneX;
/* 354 */     localMouseEvent.sceneY = paramMouseEvent.sceneY;
/*     */ 
/* 356 */     paramMouseEvent.recomputeCoordinatesToSource(localMouseEvent, paramObject);
/* 357 */     return localMouseEvent;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static MouseEvent impl_mouseEvent(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, MouseButton paramMouseButton, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, boolean paramBoolean9, EventType<? extends MouseEvent> paramEventType)
/*     */   {
/* 381 */     MouseEvent localMouseEvent = new MouseEvent(paramEventType);
/* 382 */     localMouseEvent.x = paramDouble1;
/* 383 */     localMouseEvent.y = paramDouble2;
/* 384 */     localMouseEvent.screenX = paramDouble3;
/* 385 */     localMouseEvent.screenY = paramDouble4;
/* 386 */     localMouseEvent.sceneX = paramDouble1;
/* 387 */     localMouseEvent.sceneY = paramDouble2;
/* 388 */     localMouseEvent.button = paramMouseButton;
/* 389 */     localMouseEvent.clickCount = paramInt;
/* 390 */     localMouseEvent.stillSincePress = false;
/* 391 */     localMouseEvent.shiftDown = paramBoolean1;
/* 392 */     localMouseEvent.controlDown = paramBoolean2;
/* 393 */     localMouseEvent.altDown = paramBoolean3;
/* 394 */     localMouseEvent.metaDown = paramBoolean4;
/* 395 */     localMouseEvent.popupTrigger = paramBoolean5;
/* 396 */     localMouseEvent.primaryButtonDown = paramBoolean6;
/* 397 */     localMouseEvent.middleButtonDown = paramBoolean7;
/* 398 */     localMouseEvent.secondaryButtonDown = paramBoolean8;
/* 399 */     localMouseEvent.synthesized = paramBoolean9;
/* 400 */     return localMouseEvent;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   private static MouseEvent impl_mouseEvent(Object paramObject, EventTarget paramEventTarget, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, MouseButton paramMouseButton, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, boolean paramBoolean9, boolean paramBoolean10, EventType<? extends MouseEvent> paramEventType)
/*     */   {
/* 427 */     MouseEvent localMouseEvent = new MouseEvent(paramObject, paramEventTarget, paramEventType);
/* 428 */     localMouseEvent.x = paramDouble1;
/* 429 */     localMouseEvent.y = paramDouble2;
/* 430 */     localMouseEvent.screenX = paramDouble3;
/* 431 */     localMouseEvent.screenY = paramDouble4;
/* 432 */     localMouseEvent.sceneX = paramDouble1;
/* 433 */     localMouseEvent.sceneY = paramDouble2;
/* 434 */     localMouseEvent.button = paramMouseButton;
/* 435 */     localMouseEvent.clickCount = paramInt;
/* 436 */     localMouseEvent.stillSincePress = paramBoolean1;
/* 437 */     localMouseEvent.shiftDown = paramBoolean2;
/* 438 */     localMouseEvent.controlDown = paramBoolean3;
/* 439 */     localMouseEvent.altDown = paramBoolean4;
/* 440 */     localMouseEvent.metaDown = paramBoolean5;
/* 441 */     localMouseEvent.popupTrigger = paramBoolean6;
/* 442 */     localMouseEvent.primaryButtonDown = paramBoolean7;
/* 443 */     localMouseEvent.middleButtonDown = paramBoolean8;
/* 444 */     localMouseEvent.secondaryButtonDown = paramBoolean9;
/* 445 */     localMouseEvent.synthesized = paramBoolean10;
/* 446 */     return localMouseEvent;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static boolean impl_getPopupTrigger(MouseEvent paramMouseEvent)
/*     */   {
/* 456 */     return paramMouseEvent.popupTrigger;
/*     */   }
/*     */ 
/*     */   public boolean isDragDetect()
/*     */   {
/* 469 */     return this.flags.dragDetect;
/*     */   }
/*     */ 
/*     */   public void setDragDetect(boolean paramBoolean)
/*     */   {
/* 480 */     this.flags.dragDetect = paramBoolean;
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/* 497 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 514 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final double getScreenX()
/*     */   {
/* 527 */     return this.screenX;
/*     */   }
/*     */ 
/*     */   public final double getScreenY()
/*     */   {
/* 540 */     return this.screenY;
/*     */   }
/*     */ 
/*     */   public final double getSceneX()
/*     */   {
/* 561 */     return this.sceneX;
/*     */   }
/*     */ 
/*     */   public final double getSceneY()
/*     */   {
/* 582 */     return this.sceneY;
/*     */   }
/*     */ 
/*     */   public final MouseButton getButton()
/*     */   {
/* 596 */     return this.button;
/*     */   }
/*     */ 
/*     */   public final int getClickCount()
/*     */   {
/* 624 */     return this.clickCount;
/*     */   }
/*     */ 
/*     */   public final boolean isStillSincePress()
/*     */   {
/* 651 */     return this.stillSincePress;
/*     */   }
/*     */ 
/*     */   public final boolean isShiftDown()
/*     */   {
/* 664 */     return this.shiftDown;
/*     */   }
/*     */ 
/*     */   public final boolean isControlDown()
/*     */   {
/* 677 */     return this.controlDown;
/*     */   }
/*     */ 
/*     */   public final boolean isAltDown()
/*     */   {
/* 690 */     return this.altDown;
/*     */   }
/*     */ 
/*     */   public final boolean isMetaDown()
/*     */   {
/* 703 */     return this.metaDown;
/*     */   }
/*     */ 
/*     */   public boolean isSynthesized()
/*     */   {
/* 719 */     return this.synthesized;
/*     */   }
/*     */ 
/*     */   public final boolean isShortcutDown()
/*     */   {
/* 732 */     switch (1.$SwitchMap$javafx$scene$input$KeyCode[com.sun.javafx.tk.Toolkit.getToolkit().getPlatformShortcutKey().ordinal()]) {
/*     */     case 1:
/* 734 */       return this.shiftDown;
/*     */     case 2:
/* 737 */       return this.controlDown;
/*     */     case 3:
/* 740 */       return this.altDown;
/*     */     case 4:
/* 743 */       return this.metaDown;
/*     */     }
/*     */ 
/* 746 */     return false;
/*     */   }
/*     */ 
/*     */   public final boolean isPrimaryButtonDown()
/*     */   {
/* 780 */     return this.primaryButtonDown;
/*     */   }
/*     */ 
/*     */   public final boolean isSecondaryButtonDown()
/*     */   {
/* 803 */     return this.secondaryButtonDown;
/*     */   }
/*     */ 
/*     */   public final boolean isMiddleButtonDown()
/*     */   {
/* 825 */     return this.middleButtonDown;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 833 */     StringBuilder localStringBuilder = new StringBuilder("MouseEvent [");
/*     */ 
/* 835 */     localStringBuilder.append("source = ").append(getSource());
/* 836 */     localStringBuilder.append(", target = ").append(getTarget());
/* 837 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 838 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/*     */ 
/* 840 */     localStringBuilder.append(", x = ").append(getX()).append(", y = ").append(getY());
/*     */ 
/* 842 */     if (getButton() != null) {
/* 843 */       localStringBuilder.append(", button = ").append(getButton());
/*     */     }
/* 845 */     if (getClickCount() > 1) {
/* 846 */       localStringBuilder.append(", clickCount = ").append(getClickCount());
/*     */     }
/* 848 */     if (isPrimaryButtonDown()) {
/* 849 */       localStringBuilder.append(", primaryButtonDown");
/*     */     }
/* 851 */     if (isMiddleButtonDown()) {
/* 852 */       localStringBuilder.append(", middleButtonDown");
/*     */     }
/* 854 */     if (isSecondaryButtonDown()) {
/* 855 */       localStringBuilder.append(", secondaryButtonDown");
/*     */     }
/* 857 */     if (isShiftDown()) {
/* 858 */       localStringBuilder.append(", shiftDown");
/*     */     }
/* 860 */     if (isControlDown()) {
/* 861 */       localStringBuilder.append(", controlDown");
/*     */     }
/* 863 */     if (isAltDown()) {
/* 864 */       localStringBuilder.append(", altDown");
/*     */     }
/* 866 */     if (isMetaDown()) {
/* 867 */       localStringBuilder.append(", metaDown");
/*     */     }
/* 869 */     if (isShortcutDown()) {
/* 870 */       localStringBuilder.append(", shortcutDown");
/*     */     }
/* 872 */     if (isSynthesized()) {
/* 873 */       localStringBuilder.append(", synthesized");
/*     */     }
/*     */ 
/* 876 */     return localStringBuilder.append("]").toString();
/*     */   }
/*     */ 
/*     */   private static class Flags
/*     */     implements Cloneable
/*     */   {
/* 889 */     boolean dragDetect = true;
/*     */ 
/*     */     public Flags clone()
/*     */     {
/*     */       try {
/* 894 */         return (Flags)super.clone();
/*     */       } catch (CloneNotSupportedException localCloneNotSupportedException) {
/*     */       }
/* 897 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.MouseEvent
 * JD-Core Version:    0.6.2
 */