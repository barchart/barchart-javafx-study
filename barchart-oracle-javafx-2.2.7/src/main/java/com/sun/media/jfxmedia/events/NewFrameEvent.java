/*    */ package com.sun.media.jfxmedia.events;
/*    */ 
/*    */ import com.sun.media.jfxmedia.control.VideoDataBuffer;
/*    */ 
/*    */ public class NewFrameEvent extends PlayerEvent
/*    */ {
/*    */   private VideoDataBuffer frameData;
/*    */ 
/*    */   public NewFrameEvent(VideoDataBuffer buffer)
/*    */   {
/* 79 */     if (buffer == null) {
/* 80 */       throw new IllegalArgumentException("buffer == null!");
/*    */     }
/* 82 */     this.frameData = buffer;
/*    */   }
/*    */ 
/*    */   public VideoDataBuffer getFrameData()
/*    */   {
/* 91 */     return this.frameData;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.events.NewFrameEvent
 * JD-Core Version:    0.6.2
 */