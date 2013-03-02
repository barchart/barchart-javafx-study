/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ 
/*     */ public final class EventLoop
/*     */ {
/*  13 */   private static final Deque<EventLoop> stack = new ArrayDeque();
/*     */ 
/*  35 */   private State state = State.IDLE;
/*     */   private Object returnValue;
/*     */ 
/*     */   public State getState()
/*     */   {
/*  46 */     Application.checkEventThread();
/*  47 */     return this.state;
/*     */   }
/*     */ 
/*     */   public Object enter()
/*     */   {
/*  75 */     Application.checkEventThread();
/*  76 */     if (!this.state.equals(State.IDLE)) {
/*  77 */       throw new IllegalStateException("The event loop object isn't idle");
/*     */     }
/*     */ 
/*  80 */     this.state = State.ACTIVE;
/*  81 */     stack.push(this);
/*     */     try {
/*  83 */       Application.GetApplication(); Object ret = Application.enterNestedEventLoop();
/*  84 */       assert (ret == this) : "Internal inconsistency - wrong EventLoop";
/*  85 */       assert (stack.peek() == this) : "Internal inconsistency - corrupted event loops stack";
/*  86 */       assert (this.state.equals(State.LEAVING)) : "The event loop isn't leaving";
/*     */ 
/*  88 */       return this.returnValue;
/*     */     } finally {
/*  90 */       this.returnValue = null;
/*  91 */       this.state = State.IDLE;
/*  92 */       stack.pop();
/*     */ 
/*  94 */       if ((!stack.isEmpty()) && (((EventLoop)stack.peek()).state.equals(State.LEAVING))) {
/*  95 */         Application.GetApplication(); Application.invokeLater(new Runnable() {
/*     */           public void run() {
/*  97 */             EventLoop loop = (EventLoop)EventLoop.stack.peek();
/*     */ 
/*  99 */             if ((loop != null) && (loop.state.equals(EventLoop.State.LEAVING))) {
/* 100 */               Application.GetApplication(); Application.leaveNestedEventLoop(loop);
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void leave(Object ret)
/*     */   {
/* 135 */     Application.checkEventThread();
/* 136 */     if (!this.state.equals(State.ACTIVE)) {
/* 137 */       throw new IllegalStateException("The event loop object isn't active");
/*     */     }
/*     */ 
/* 140 */     this.state = State.LEAVING;
/* 141 */     this.returnValue = ret;
/*     */ 
/* 143 */     if (stack.peek() == this) {
/* 144 */       Application.GetApplication(); Application.leaveNestedEventLoop(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum State
/*     */   {
/*  22 */     IDLE, 
/*     */ 
/*  27 */     ACTIVE, 
/*     */ 
/*  32 */     LEAVING;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.EventLoop
 * JD-Core Version:    0.6.2
 */