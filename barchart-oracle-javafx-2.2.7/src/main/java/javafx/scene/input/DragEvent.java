/*     */ package javafx.scene.input;
/*     */ 
/*     */ import com.sun.javafx.scene.input.InputEventUtils;
/*     */ import com.sun.javafx.tk.TKDropEvent;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.Point2D;
/*     */ 
/*     */ public class DragEvent extends InputEvent
/*     */ {
/* 207 */   public static final EventType<DragEvent> ANY = new EventType(InputEvent.ANY, "DRAG");
/*     */ 
/* 220 */   public static final EventType<DragEvent> DRAG_ENTERED_TARGET = new EventType(ANY, "DRAG_ENTERED_TARGET");
/*     */ 
/* 232 */   public static final EventType<DragEvent> DRAG_ENTERED = new EventType(DRAG_ENTERED_TARGET, "DRAG_ENTERED");
/*     */ 
/* 245 */   public static final EventType<DragEvent> DRAG_EXITED_TARGET = new EventType(ANY, "DRAG_EXITED_TARGET");
/*     */ 
/* 257 */   public static final EventType<DragEvent> DRAG_EXITED = new EventType(DRAG_EXITED_TARGET, "DRAG_EXITED");
/*     */ 
/* 263 */   public static final EventType<DragEvent> DRAG_OVER = new EventType(ANY, "DRAG_OVER");
/*     */ 
/* 282 */   public static final EventType<DragEvent> DRAG_DROPPED = new EventType(ANY, "DRAG_DROPPED");
/*     */ 
/* 295 */   public static final EventType<DragEvent> DRAG_DONE = new EventType(ANY, "DRAG_DONE");
/*     */   private double x;
/*     */   private double y;
/*     */   private double screenX;
/*     */   private double screenY;
/*     */   private double sceneX;
/*     */   private double sceneY;
/*     */   private Object gestureSource;
/*     */   private Object gestureTarget;
/*     */   private TransferMode transferMode;
/* 558 */   private State state = new State(null);
/*     */   private TKDropEvent tkDropEvent;
/*     */   private Object tkRecognizedEvent;
/*     */   private Dragboard dragboard;
/*     */ 
/*     */   private DragEvent(EventType<? extends DragEvent> paramEventType)
/*     */   {
/* 299 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   private DragEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends DragEvent> paramEventType)
/*     */   {
/* 304 */     super(paramObject, paramEventTarget, paramEventType);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static DragEvent impl_copy(Object paramObject1, EventTarget paramEventTarget, Object paramObject2, Object paramObject3, Dragboard paramDragboard, DragEvent paramDragEvent)
/*     */   {
/* 316 */     DragEvent localDragEvent = impl_copy(paramObject1, paramEventTarget, paramObject2, paramObject3, paramDragEvent, null);
/*     */ 
/* 318 */     localDragEvent.dragboard = paramDragboard;
/* 319 */     return localDragEvent;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static DragEvent impl_copy(Object paramObject, EventTarget paramEventTarget, DragEvent paramDragEvent, EventType<DragEvent> paramEventType)
/*     */   {
/* 330 */     return impl_copy(paramObject, paramEventTarget, paramDragEvent.getGestureSource(), paramDragEvent.getGestureTarget(), paramDragEvent, paramEventType);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static DragEvent impl_copy(Object paramObject1, EventTarget paramEventTarget, Object paramObject2, Object paramObject3, DragEvent paramDragEvent, EventType<DragEvent> paramEventType)
/*     */   {
/* 344 */     return impl_copy(paramObject1, paramEventTarget, paramObject2, paramObject3, paramDragEvent.getTransferMode(), paramDragEvent, paramEventType);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static DragEvent impl_copy(Object paramObject1, EventTarget paramEventTarget, Object paramObject2, Object paramObject3, TransferMode paramTransferMode, DragEvent paramDragEvent, EventType<DragEvent> paramEventType)
/*     */   {
/* 358 */     DragEvent localDragEvent = impl_dragEvent(paramObject1, paramEventTarget, paramObject2, paramObject3, paramDragEvent.x, paramDragEvent.y, paramDragEvent.screenX, paramDragEvent.screenY, paramDragEvent.transferMode, paramEventType != null ? paramEventType : paramDragEvent.getEventType(), paramDragEvent.dragboard);
/*     */ 
/* 366 */     paramDragEvent.recomputeCoordinatesToSource(localDragEvent, paramObject1);
/* 367 */     localDragEvent.tkDropEvent = paramDragEvent.tkDropEvent;
/* 368 */     localDragEvent.tkRecognizedEvent = paramDragEvent.tkRecognizedEvent;
/* 369 */     localDragEvent.transferMode = paramTransferMode;
/* 370 */     if ((paramEventType == DRAG_DROPPED) || (paramEventType == DRAG_DONE))
/*     */     {
/* 372 */       localDragEvent.state.accepted = (paramTransferMode != null);
/* 373 */       localDragEvent.state.acceptedTrasferMode = paramTransferMode;
/*     */     }
/* 375 */     return localDragEvent;
/*     */   }
/*     */ 
/*     */   private static DragEvent impl_dragEvent(Object paramObject1, EventTarget paramEventTarget, Object paramObject2, Object paramObject3, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, TransferMode paramTransferMode, EventType<? extends DragEvent> paramEventType, Dragboard paramDragboard)
/*     */   {
/* 384 */     DragEvent localDragEvent = new DragEvent(paramObject1, paramEventTarget, paramEventType);
/* 385 */     localDragEvent.gestureSource = paramObject2;
/* 386 */     localDragEvent.gestureTarget = paramObject3;
/* 387 */     localDragEvent.x = paramDouble1;
/* 388 */     localDragEvent.y = paramDouble2;
/* 389 */     localDragEvent.screenX = paramDouble3;
/* 390 */     localDragEvent.screenY = paramDouble4;
/* 391 */     localDragEvent.sceneX = paramDouble1;
/* 392 */     localDragEvent.sceneY = paramDouble2;
/* 393 */     localDragEvent.transferMode = paramTransferMode;
/* 394 */     localDragEvent.dragboard = paramDragboard;
/* 395 */     return localDragEvent;
/*     */   }
/*     */ 
/*     */   private void recomputeCoordinatesToSource(DragEvent paramDragEvent, Object paramObject)
/*     */   {
/* 406 */     if (paramDragEvent.getEventType() == DRAG_DONE)
/*     */     {
/* 408 */       return;
/*     */     }
/*     */ 
/* 411 */     Point2D localPoint2D = InputEventUtils.recomputeCoordinates(new Point2D(this.sceneX, this.sceneY), null, paramObject);
/*     */ 
/* 414 */     paramDragEvent.x = localPoint2D.getX();
/* 415 */     paramDragEvent.y = localPoint2D.getY();
/*     */   }
/*     */ 
/*     */   public Event copyFor(Object paramObject, EventTarget paramEventTarget)
/*     */   {
/* 420 */     DragEvent localDragEvent = (DragEvent)super.copyFor(paramObject, paramEventTarget);
/* 421 */     recomputeCoordinatesToSource(localDragEvent, paramObject);
/* 422 */     return localDragEvent;
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/* 439 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 456 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final double getScreenX()
/*     */   {
/* 469 */     return this.screenX;
/*     */   }
/*     */ 
/*     */   public final double getScreenY()
/*     */   {
/* 482 */     return this.screenY;
/*     */   }
/*     */ 
/*     */   public final double getSceneX()
/*     */   {
/* 503 */     return this.sceneX;
/*     */   }
/*     */ 
/*     */   public final double getSceneY()
/*     */   {
/* 524 */     return this.sceneY;
/*     */   }
/*     */ 
/*     */   public final Object getGestureSource()
/*     */   {
/* 533 */     return this.gestureSource;
/*     */   }
/*     */ 
/*     */   public final Object getGestureTarget()
/*     */   {
/* 543 */     return this.gestureTarget;
/*     */   }
/*     */ 
/*     */   public final TransferMode getTransferMode()
/*     */   {
/* 555 */     return this.transferMode;
/*     */   }
/*     */ 
/*     */   public final boolean isAccepted()
/*     */   {
/* 565 */     return this.state.accepted;
/*     */   }
/*     */ 
/*     */   public final TransferMode getAcceptedTransferMode()
/*     */   {
/* 572 */     return this.state.acceptedTrasferMode;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Object impl_getAcceptingObject()
/*     */   {
/* 581 */     return this.state.acceptingObject;
/*     */   }
/*     */ 
/*     */   public final Dragboard getDragboard()
/*     */   {
/* 594 */     return this.dragboard;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Dragboard impl_getPlatformDragboard()
/*     */   {
/* 604 */     if (this.tkDropEvent == null) {
/* 605 */       return null;
/*     */     }
/* 607 */     return this.tkDropEvent.getDragboard();
/*     */   }
/*     */ 
/*     */   private static TransferMode chooseTransferMode(Set<TransferMode> paramSet, TransferMode[] paramArrayOfTransferMode, TransferMode paramTransferMode)
/*     */   {
/* 620 */     TransferMode localTransferMode1 = null;
/* 621 */     EnumSet localEnumSet = EnumSet.noneOf(TransferMode.class);
/*     */ 
/* 623 */     for (TransferMode localTransferMode2 : paramArrayOfTransferMode) {
/* 624 */       if (paramSet.contains(localTransferMode2)) {
/* 625 */         localEnumSet.add(localTransferMode2);
/*     */       }
/*     */     }
/*     */ 
/* 629 */     if (localEnumSet.contains(paramTransferMode)) {
/* 630 */       localTransferMode1 = paramTransferMode;
/*     */     }
/* 632 */     else if (localEnumSet.contains(TransferMode.MOVE))
/* 633 */       localTransferMode1 = TransferMode.MOVE;
/* 634 */     else if (localEnumSet.contains(TransferMode.COPY))
/* 635 */       localTransferMode1 = TransferMode.COPY;
/* 636 */     else if (localEnumSet.contains(TransferMode.LINK)) {
/* 637 */       localTransferMode1 = TransferMode.LINK;
/*     */     }
/*     */ 
/* 641 */     return localTransferMode1;
/*     */   }
/*     */ 
/*     */   public void acceptTransferModes(TransferMode[] paramArrayOfTransferMode)
/*     */   {
/* 658 */     if ((this.dragboard == null) || (this.dragboard.getTransferModes() == null) || (this.transferMode == null))
/*     */     {
/* 660 */       this.state.accepted = false;
/* 661 */       return;
/*     */     }
/*     */ 
/* 664 */     TransferMode localTransferMode = chooseTransferMode(this.dragboard.getTransferModes(), paramArrayOfTransferMode, this.transferMode);
/*     */ 
/* 667 */     if ((localTransferMode == null) && (getEventType() == DRAG_DROPPED)) {
/* 668 */       throw new IllegalStateException("Accepting unsupported transfer modes inside DRAG_DROPPED handler");
/*     */     }
/*     */ 
/* 672 */     this.state.accepted = (localTransferMode != null);
/* 673 */     if (this.tkDropEvent != null) {
/* 674 */       if (this.state.accepted)
/* 675 */         this.tkDropEvent.accept(localTransferMode);
/*     */       else {
/* 677 */         this.tkDropEvent.reject();
/*     */       }
/*     */     }
/* 680 */     this.state.acceptedTrasferMode = localTransferMode;
/* 681 */     this.state.acceptingObject = (this.state.accepted ? this.source : null);
/*     */   }
/*     */ 
/*     */   public void setDropCompleted(boolean paramBoolean)
/*     */   {
/* 693 */     if (getEventType() != DRAG_DROPPED) {
/* 694 */       throw new IllegalStateException("setDropCompleted can be called only from DRAG_DROPPED handler");
/*     */     }
/*     */ 
/* 698 */     if (this.tkDropEvent != null) {
/* 699 */       this.tkDropEvent.dropComplete(paramBoolean);
/*     */     }
/*     */ 
/* 702 */     this.state.dropCompleted = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean isDropCompleted()
/*     */   {
/* 710 */     return this.state.dropCompleted;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_setRecognizedEvent(Object paramObject)
/*     */   {
/* 720 */     this.tkRecognizedEvent = paramObject;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static DragEvent impl_create(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, TransferMode paramTransferMode, Dragboard paramDragboard, TKDropEvent paramTKDropEvent)
/*     */   {
/* 733 */     DragEvent localDragEvent = new DragEvent(ANY);
/*     */ 
/* 735 */     localDragEvent.x = paramDouble1;
/* 736 */     localDragEvent.y = paramDouble2;
/* 737 */     localDragEvent.screenX = paramDouble3;
/* 738 */     localDragEvent.screenY = paramDouble4;
/* 739 */     localDragEvent.sceneX = paramDouble1;
/* 740 */     localDragEvent.sceneY = paramDouble2;
/* 741 */     localDragEvent.transferMode = paramTransferMode;
/* 742 */     localDragEvent.dragboard = paramDragboard;
/* 743 */     localDragEvent.tkDropEvent = paramTKDropEvent;
/* 744 */     return localDragEvent;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static DragEvent impl_create(EventType<DragEvent> paramEventType, Object paramObject1, EventTarget paramEventTarget, Object paramObject2, Object paramObject3, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, TransferMode paramTransferMode, Dragboard paramDragboard, TKDropEvent paramTKDropEvent)
/*     */   {
/* 758 */     DragEvent localDragEvent = new DragEvent(paramObject1, paramEventTarget, paramEventType);
/*     */ 
/* 760 */     localDragEvent.gestureSource = paramObject2;
/* 761 */     localDragEvent.gestureTarget = paramObject3;
/* 762 */     localDragEvent.x = paramDouble1;
/* 763 */     localDragEvent.y = paramDouble2;
/* 764 */     localDragEvent.screenX = paramDouble3;
/* 765 */     localDragEvent.screenY = paramDouble4;
/* 766 */     localDragEvent.sceneX = paramDouble1;
/* 767 */     localDragEvent.sceneY = paramDouble2;
/* 768 */     localDragEvent.transferMode = paramTransferMode;
/* 769 */     localDragEvent.dragboard = paramDragboard;
/* 770 */     localDragEvent.tkDropEvent = paramTKDropEvent;
/* 771 */     return localDragEvent;
/*     */   }
/*     */ 
/*     */   private static class State
/*     */   {
/* 783 */     boolean accepted = false;
/*     */ 
/* 788 */     boolean dropCompleted = false;
/*     */ 
/* 793 */     TransferMode acceptedTrasferMode = null;
/*     */ 
/* 798 */     Object acceptingObject = null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.DragEvent
 * JD-Core Version:    0.6.2
 */