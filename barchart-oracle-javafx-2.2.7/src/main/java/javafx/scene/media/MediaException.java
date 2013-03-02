/*     */ package javafx.scene.media;
/*     */ 
/*     */ import com.sun.media.jfxmedia.MediaError;
/*     */ import java.net.UnknownHostException;
/*     */ 
/*     */ public final class MediaException extends RuntimeException
/*     */ {
/*     */   private final Type type;
/*     */ 
/*     */   static Type errorCodeToType(int paramInt)
/*     */   {
/*     */     Type localType;
/*  78 */     if (paramInt == MediaError.ERROR_LOCATOR_CONNECTION_LOST.code())
/*  79 */       localType = Type.MEDIA_INACCESSIBLE;
/*  80 */     else if ((paramInt == MediaError.ERROR_GSTREAMER_SOURCEFILE_NONEXISTENT.code()) || (paramInt == MediaError.ERROR_GSTREAMER_SOURCEFILE_NONREGULAR.code()))
/*     */     {
/*  82 */       localType = Type.MEDIA_UNAVAILABLE;
/*  83 */     } else if ((paramInt == MediaError.ERROR_MEDIA_AUDIO_FORMAT_UNSUPPORTED.code()) || (paramInt == MediaError.ERROR_MEDIA_UNKNOWN_PIXEL_FORMAT.code()) || (paramInt == MediaError.ERROR_MEDIA_VIDEO_FORMAT_UNSUPPORTED.code()) || (paramInt == MediaError.ERROR_LOCATOR_CONTENT_TYPE_NULL.code()) || (paramInt == MediaError.ERROR_LOCATOR_UNSUPPORTED_MEDIA_FORMAT.code()) || (paramInt == MediaError.ERROR_LOCATOR_UNSUPPORTED_TYPE.code()) || (paramInt == MediaError.ERROR_GSTREAMER_UNSUPPORTED_PROTOCOL.code()) || (paramInt == MediaError.ERROR_MEDIA_MP3_FORMAT_UNSUPPORTED.code()) || (paramInt == MediaError.ERROR_MEDIA_AAC_FORMAT_UNSUPPORTED.code()) || (paramInt == MediaError.ERROR_MEDIA_H264_FORMAT_UNSUPPORTED.code()) || (paramInt == MediaError.ERROR_MEDIA_HLS_FORMAT_UNSUPPORTED.code()))
/*     */     {
/*  94 */       localType = Type.MEDIA_UNSUPPORTED;
/*  95 */     } else if (paramInt == MediaError.ERROR_MEDIA_CORRUPTED.code())
/*  96 */       localType = Type.MEDIA_CORRUPTED;
/*  97 */     else if (((paramInt & MediaError.ERROR_BASE_GSTREAMER.code()) == MediaError.ERROR_BASE_GSTREAMER.code()) || ((paramInt & MediaError.ERROR_BASE_JNI.code()) == MediaError.ERROR_BASE_JNI.code()))
/*     */     {
/*  99 */       localType = Type.PLAYBACK_ERROR;
/*     */     }
/* 101 */     else localType = Type.UNKNOWN;
/*     */ 
/* 104 */     return localType;
/*     */   }
/*     */ 
/*     */   static MediaException exceptionToMediaException(Exception paramException)
/*     */   {
/* 111 */     Type localType = Type.UNKNOWN;
/*     */ 
/* 113 */     if ((paramException.getCause() instanceof UnknownHostException)) {
/* 114 */       localType = Type.MEDIA_UNAVAILABLE;
/* 115 */     } else if ((paramException.getCause() instanceof IllegalArgumentException)) {
/* 116 */       localType = Type.MEDIA_UNSUPPORTED;
/* 117 */     } else if ((paramException instanceof com.sun.media.jfxmedia.MediaException)) {
/* 118 */       com.sun.media.jfxmedia.MediaException localMediaException = (com.sun.media.jfxmedia.MediaException)paramException;
/* 119 */       MediaError localMediaError = localMediaException.getMediaError();
/* 120 */       if (localMediaError != null) {
/* 121 */         localType = errorCodeToType(localMediaError.code());
/*     */       }
/*     */     }
/*     */ 
/* 125 */     return new MediaException(localType, paramException);
/*     */   }
/*     */ 
/*     */   static MediaException haltException(String paramString) {
/* 129 */     return new MediaException(Type.PLAYBACK_HALTED, paramString);
/*     */   }
/*     */ 
/*     */   static MediaException getMediaException(Object paramObject, int paramInt, String paramString)
/*     */   {
/* 134 */     String str1 = MediaError.getFromCode(paramInt).description();
/* 135 */     String str2 = "[" + paramObject + "] " + paramString + ": " + str1;
/*     */ 
/* 138 */     Type localType = errorCodeToType(paramInt);
/* 139 */     return new MediaException(localType, str2);
/*     */   }
/*     */ 
/*     */   MediaException(Type paramType, Throwable paramThrowable) {
/* 143 */     super(paramThrowable);
/* 144 */     this.type = paramType;
/*     */   }
/*     */ 
/*     */   MediaException(Type paramType, String paramString, Throwable paramThrowable) {
/* 148 */     super(paramString, paramThrowable);
/* 149 */     this.type = paramType;
/*     */   }
/*     */ 
/*     */   MediaException(Type paramType, String paramString) {
/* 153 */     super(paramString);
/* 154 */     this.type = paramType;
/*     */   }
/*     */ 
/*     */   public Type getType()
/*     */   {
/* 165 */     return this.type;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 174 */     String str = "MediaException: " + this.type;
/* 175 */     if (getMessage() != null) str = str + " : " + getMessage();
/* 176 */     if (getCause() != null) str = str + " : " + getCause();
/* 177 */     return str;
/*     */   }
/*     */ 
/*     */   public static enum Type
/*     */   {
/*  29 */     MEDIA_CORRUPTED, 
/*     */ 
/*  34 */     MEDIA_INACCESSIBLE, 
/*     */ 
/*  41 */     MEDIA_UNAVAILABLE, 
/*     */ 
/*  45 */     MEDIA_UNSPECIFIED, 
/*     */ 
/*  49 */     MEDIA_UNSUPPORTED, 
/*     */ 
/*  54 */     OPERATION_UNSUPPORTED, 
/*     */ 
/*  59 */     PLAYBACK_ERROR, 
/*     */ 
/*  63 */     PLAYBACK_HALTED, 
/*     */ 
/*  67 */     UNKNOWN;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.MediaException
 * JD-Core Version:    0.6.2
 */