/*     */ package com.sun.media.jfxmedia;
/*     */ 
/*     */ public class MediaException extends RuntimeException
/*     */ {
/*     */   private static final long serialVersionUID = 14L;
/*  70 */   private MediaError error = null;
/*     */ 
/*     */   public MediaException(String message)
/*     */   {
/*  80 */     super(message);
/*     */   }
/*     */ 
/*     */   public MediaException(String message, Throwable cause)
/*     */   {
/*  92 */     super(message, cause);
/*     */   }
/*     */ 
/*     */   public MediaException(String message, Throwable cause, MediaError error)
/*     */   {
/* 105 */     super(message, cause);
/* 106 */     this.error = error;
/*     */   }
/*     */ 
/*     */   public MediaError getMediaError() {
/* 110 */     return this.error;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.MediaException
 * JD-Core Version:    0.6.2
 */