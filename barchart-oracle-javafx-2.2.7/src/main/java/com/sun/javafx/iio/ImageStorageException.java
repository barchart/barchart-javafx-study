/*    */ package com.sun.javafx.iio;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class ImageStorageException extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ImageStorageException(String paramString)
/*    */   {
/* 39 */     super(paramString);
/*    */   }
/*    */ 
/*    */   public ImageStorageException(String paramString, Throwable paramThrowable)
/*    */   {
/* 55 */     super(paramString);
/* 56 */     initCause(paramThrowable);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.ImageStorageException
 * JD-Core Version:    0.6.2
 */