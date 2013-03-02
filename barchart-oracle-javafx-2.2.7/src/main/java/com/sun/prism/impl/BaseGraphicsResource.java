/*    */ package com.sun.prism.impl;
/*    */ 
/*    */ import com.sun.prism.GraphicsResource;
/*    */ 
/*    */ public abstract class BaseGraphicsResource
/*    */   implements GraphicsResource
/*    */ {
/* 13 */   private final Object disposerReferent = new Object();
/*    */   protected final Disposer.Record disposerRecord;
/*    */ 
/*    */   protected BaseGraphicsResource(Disposer.Record paramRecord)
/*    */   {
/* 17 */     this.disposerRecord = paramRecord;
/* 18 */     Disposer.addRecord(this.disposerReferent, paramRecord);
/*    */   }
/*    */ 
/*    */   public abstract void dispose();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.BaseGraphicsResource
 * JD-Core Version:    0.6.2
 */