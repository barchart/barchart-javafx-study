/*     */ package com.sun.javafx.scene;
/*     */ 
/*     */ import com.sun.javafx.event.BasicEventDispatcher;
/*     */ import javafx.event.Event;
/*     */ import javafx.scene.input.DragEvent;
/*     */ import javafx.scene.input.MouseDragEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class EnteredExitedHandler extends BasicEventDispatcher
/*     */ {
/*     */   private final Object eventSource;
/*     */   private boolean eventTypeModified;
/*     */ 
/*     */   public EnteredExitedHandler(Object paramObject)
/*     */   {
/*  44 */     this.eventSource = paramObject;
/*     */   }
/*     */ 
/*     */   public final Event dispatchCapturingEvent(Event paramEvent)
/*     */   {
/*  49 */     if (this.eventSource == paramEvent.getTarget()) {
/*  50 */       if (paramEvent.getEventType() == MouseEvent.MOUSE_ENTERED_TARGET) {
/*  51 */         this.eventTypeModified = true;
/*  52 */         return MouseEvent.impl_copy(this.eventSource, paramEvent.getTarget(), (MouseEvent)paramEvent, MouseEvent.MOUSE_ENTERED);
/*     */       }
/*     */ 
/*  57 */       if (paramEvent.getEventType() == MouseEvent.MOUSE_EXITED_TARGET) {
/*  58 */         this.eventTypeModified = true;
/*  59 */         return MouseEvent.impl_copy(this.eventSource, paramEvent.getTarget(), (MouseEvent)paramEvent, MouseEvent.MOUSE_EXITED);
/*     */       }
/*     */ 
/*  64 */       if (paramEvent.getEventType() == MouseDragEvent.MOUSE_DRAG_ENTERED_TARGET) {
/*  65 */         this.eventTypeModified = true;
/*  66 */         return MouseDragEvent.impl_copy(this.eventSource, paramEvent.getTarget(), ((MouseDragEvent)paramEvent).getGestureSource(), (MouseDragEvent)paramEvent, MouseDragEvent.MOUSE_DRAG_ENTERED);
/*     */       }
/*     */ 
/*  72 */       if (paramEvent.getEventType() == MouseDragEvent.MOUSE_DRAG_EXITED_TARGET) {
/*  73 */         this.eventTypeModified = true;
/*  74 */         return MouseDragEvent.impl_copy(this.eventSource, paramEvent.getTarget(), ((MouseDragEvent)paramEvent).getGestureSource(), (MouseDragEvent)paramEvent, MouseDragEvent.MOUSE_DRAG_EXITED);
/*     */       }
/*     */ 
/*  80 */       if (paramEvent.getEventType() == DragEvent.DRAG_ENTERED_TARGET) {
/*  81 */         this.eventTypeModified = true;
/*  82 */         return DragEvent.impl_copy(this.eventSource, paramEvent.getTarget(), (DragEvent)paramEvent, DragEvent.DRAG_ENTERED);
/*     */       }
/*     */ 
/*  87 */       if (paramEvent.getEventType() == DragEvent.DRAG_EXITED_TARGET) {
/*  88 */         this.eventTypeModified = true;
/*  89 */         return DragEvent.impl_copy(this.eventSource, paramEvent.getTarget(), (DragEvent)paramEvent, DragEvent.DRAG_EXITED);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  95 */     this.eventTypeModified = false;
/*  96 */     return paramEvent;
/*     */   }
/*     */ 
/*     */   public final Event dispatchBubblingEvent(Event paramEvent)
/*     */   {
/* 101 */     if ((this.eventTypeModified) && (this.eventSource == paramEvent.getTarget())) {
/* 102 */       if (paramEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {
/* 103 */         return MouseEvent.impl_copy(this.eventSource, paramEvent.getTarget(), (MouseEvent)paramEvent, MouseEvent.MOUSE_ENTERED_TARGET);
/*     */       }
/*     */ 
/* 108 */       if (paramEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
/* 109 */         return MouseEvent.impl_copy(this.eventSource, paramEvent.getTarget(), (MouseEvent)paramEvent, MouseEvent.MOUSE_EXITED_TARGET);
/*     */       }
/*     */ 
/* 114 */       if (paramEvent.getEventType() == MouseDragEvent.MOUSE_DRAG_ENTERED) {
/* 115 */         this.eventTypeModified = true;
/* 116 */         return MouseDragEvent.impl_copy(this.eventSource, paramEvent.getTarget(), ((MouseDragEvent)paramEvent).getGestureSource(), (MouseDragEvent)paramEvent, MouseDragEvent.MOUSE_DRAG_ENTERED_TARGET);
/*     */       }
/*     */ 
/* 122 */       if (paramEvent.getEventType() == MouseDragEvent.MOUSE_DRAG_EXITED) {
/* 123 */         this.eventTypeModified = true;
/* 124 */         return MouseDragEvent.impl_copy(this.eventSource, paramEvent.getTarget(), ((MouseDragEvent)paramEvent).getGestureSource(), (MouseDragEvent)paramEvent, MouseDragEvent.MOUSE_DRAG_EXITED_TARGET);
/*     */       }
/*     */ 
/* 130 */       if (paramEvent.getEventType() == DragEvent.DRAG_ENTERED) {
/* 131 */         return DragEvent.impl_copy(this.eventSource, paramEvent.getTarget(), (DragEvent)paramEvent, DragEvent.DRAG_ENTERED_TARGET);
/*     */       }
/*     */ 
/* 136 */       if (paramEvent.getEventType() == DragEvent.DRAG_EXITED) {
/* 137 */         return DragEvent.impl_copy(this.eventSource, paramEvent.getTarget(), (DragEvent)paramEvent, DragEvent.DRAG_EXITED_TARGET);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 143 */     return paramEvent;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.EnteredExitedHandler
 * JD-Core Version:    0.6.2
 */