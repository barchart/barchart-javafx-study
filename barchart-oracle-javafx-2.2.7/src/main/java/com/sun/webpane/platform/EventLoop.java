/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public abstract class EventLoop
/*    */ {
/* 11 */   private static final Logger logger = Logger.getLogger(EventLoop.class.getName());
/*    */   private static EventLoop instance;
/*    */ 
/*    */   public static void setEventLoop(EventLoop paramEventLoop)
/*    */   {
/* 18 */     instance = paramEventLoop;
/*    */   }
/*    */ 
/*    */   private static void fwkCycle() {
/* 22 */     logger.log(Level.FINE, "Executing event loop cycle");
/* 23 */     instance.cycle();
/*    */   }
/*    */ 
/*    */   protected abstract void cycle();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.EventLoop
 * JD-Core Version:    0.6.2
 */