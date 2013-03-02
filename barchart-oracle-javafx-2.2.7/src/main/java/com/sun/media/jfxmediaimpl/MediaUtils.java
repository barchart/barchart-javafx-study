/*     */ package com.sun.media.jfxmediaimpl;
/*     */ 
/*     */ import com.sun.media.jfxmedia.MediaError;
/*     */ import com.sun.media.jfxmedia.MediaException;
/*     */ import com.sun.media.jfxmedia.events.MediaErrorListener;
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ public class MediaUtils
/*     */ {
/*     */   public static final int MAX_FILE_SIGNATURE_LENGTH = 22;
/*     */   static final String NATIVE_MEDIA_ERROR_FORMAT = "Internal media error: %d";
/*     */   static final String NATIVE_MEDIA_WARNING_FORMAT = "Internal media warning: %d";
/*     */   public static final String CONTENT_TYPE_AIFF = "audio/x-aiff";
/*     */   public static final String CONTENT_TYPE_MP3 = "audio/mp3";
/*     */   public static final String CONTENT_TYPE_MPA = "audio/mpeg";
/*     */   public static final String CONTENT_TYPE_WAV = "audio/x-wav";
/*     */   public static final String CONTENT_TYPE_JFX = "video/x-javafx";
/*     */   public static final String CONTENT_TYPE_FLV = "video/x-flv";
/*     */   public static final String CONTENT_TYPE_MP4 = "video/mp4";
/*     */   public static final String CONTENT_TYPE_M4A = "audio/x-m4a";
/*     */   public static final String CONTENT_TYPE_M4V = "video/x-m4v";
/*     */   private static final String CONTENT_TYPE_M3U8 = "application/vnd.apple.mpegurl";
/*     */   private static final String CONTENT_TYPE_M3U = "audio/mpegurl";
/*     */   private static final String FILE_TYPE_AIF = "aif";
/*     */   private static final String FILE_TYPE_AIFF = "aiff";
/*     */   private static final String FILE_TYPE_FLV = "flv";
/*     */   private static final String FILE_TYPE_FXM = "fxm";
/*     */   private static final String FILE_TYPE_MPA = "mp3";
/*     */   private static final String FILE_TYPE_WAV = "wav";
/*     */   private static final String FILE_TYPE_MP4 = "mp4";
/*     */   private static final String FILE_TYPE_M4A = "m4a";
/*     */   private static final String FILE_TYPE_M4V = "m4v";
/*     */   private static final String FILE_TYPE_M3U8 = "m3u8";
/*     */   private static final String FILE_TYPE_M3U = "m3u";
/*     */ 
/*     */   public static String fileSignatureToContentType(byte[] buf)
/*     */     throws MediaException
/*     */   {
/* 120 */     String contentType = "application/octet-stream";
/*     */ 
/* 122 */     if (buf.length < 22)
/* 123 */       return contentType;
/* 124 */     if (((buf[0] & 0xFF) == 70) && ((buf[1] & 0xFF) == 76) && ((buf[2] & 0xFF) == 86))
/*     */     {
/* 127 */       contentType = "video/x-javafx";
/* 128 */     } else if ((((buf[0] & 0xFF) << 24 | (buf[1] & 0xFF) << 16 | (buf[2] & 0xFF) << 8 | buf[3] & 0xFF) == 1380533830) && (((buf[8] & 0xFF) << 24 | (buf[9] & 0xFF) << 16 | (buf[10] & 0xFF) << 8 | buf[11] & 0xFF) == 1463899717) && (((buf[12] & 0xFF) << 24 | (buf[13] & 0xFF) << 16 | (buf[14] & 0xFF) << 8 | buf[15] & 0xFF) == 1718449184))
/*     */     {
/* 140 */       if ((((buf[20] & 0xFF) == 1) && ((buf[21] & 0xFF) == 0)) || (((buf[20] & 0xFF) == 3) && ((buf[21] & 0xFF) == 0)))
/* 141 */         contentType = "audio/x-wav";
/*     */       else
/* 143 */         throw new MediaException("Compressed WAVE is not supported!");
/*     */     }
/* 145 */     else if ((((buf[0] & 0xFF) << 24 | (buf[1] & 0xFF) << 16 | (buf[2] & 0xFF) << 8 | buf[3] & 0xFF) == 1380533830) && (((buf[8] & 0xFF) << 24 | (buf[9] & 0xFF) << 16 | (buf[10] & 0xFF) << 8 | buf[11] & 0xFF) == 1463899717))
/*     */     {
/* 154 */       contentType = "audio/x-wav";
/* 155 */     } else if ((((buf[0] & 0xFF) << 24 | (buf[1] & 0xFF) << 16 | (buf[2] & 0xFF) << 8 | buf[3] & 0xFF) == 1179603533) && (((buf[8] & 0xFF) << 24 | (buf[9] & 0xFF) << 16 | (buf[10] & 0xFF) << 8 | buf[11] & 0xFF) == 1095321158) && (((buf[12] & 0xFF) << 24 | (buf[13] & 0xFF) << 16 | (buf[14] & 0xFF) << 8 | buf[15] & 0xFF) == 1129270605))
/*     */     {
/* 167 */       contentType = "audio/x-aiff";
/* 168 */     } else if (((buf[0] & 0xFF) == 73) && ((buf[1] & 0xFF) == 68) && ((buf[2] & 0xFF) == 51))
/*     */     {
/* 171 */       contentType = "audio/mpeg";
/* 172 */     } else if (((buf[0] & 0xFF) == 255) && ((buf[1] & 0xE0) == 224) && ((buf[2] & 0x18) != 1) && ((buf[3] & 0x6) != 0))
/*     */     {
/* 175 */       contentType = "audio/mpeg";
/* 176 */     } else if (((buf[4] & 0xFF) << 24 | (buf[5] & 0xFF) << 16 | (buf[6] & 0xFF) << 8 | buf[7] & 0xFF) == 1718909296)
/*     */     {
/* 180 */       if (((buf[8] & 0xFF) == 77) && ((buf[9] & 0xFF) == 52) && ((buf[10] & 0xFF) == 65) && ((buf[11] & 0xFF) == 32))
/* 181 */         contentType = "audio/x-m4a";
/* 182 */       else if (((buf[8] & 0xFF) == 77) && ((buf[9] & 0xFF) == 52) && ((buf[10] & 0xFF) == 86) && ((buf[11] & 0xFF) == 32))
/* 183 */         contentType = "video/x-m4v";
/* 184 */       else if (((buf[8] & 0xFF) == 109) && ((buf[9] & 0xFF) == 112) && ((buf[10] & 0xFF) == 52) && ((buf[11] & 0xFF) == 50))
/* 185 */         contentType = "video/mp4";
/* 186 */       else if (((buf[8] & 0xFF) == 105) && ((buf[9] & 0xFF) == 115) && ((buf[10] & 0xFF) == 111) && ((buf[11] & 0xFF) == 109))
/* 187 */         contentType = "video/mp4";
/* 188 */       else if (((buf[8] & 0xFF) == 77) && ((buf[9] & 0xFF) == 80) && ((buf[10] & 0xFF) == 52) && ((buf[11] & 0xFF) == 32))
/* 189 */         contentType = "video/mp4";
/*     */     }
/* 191 */     else throw new MediaException("Unrecognized file signature!");
/*     */ 
/* 194 */     return contentType;
/*     */   }
/*     */ 
/*     */   public static String filenameToContentType(String filename)
/*     */   {
/* 204 */     String contentType = "application/octet-stream";
/*     */ 
/* 206 */     int dotIndex = filename.lastIndexOf(".");
/*     */ 
/* 208 */     if (dotIndex != -1) {
/* 209 */       String extension = filename.toLowerCase().substring(dotIndex + 1);
/*     */ 
/* 211 */       if ((extension.equals("aif")) || (extension.equals("aiff")))
/* 212 */         contentType = "audio/x-aiff";
/* 213 */       else if ((extension.equals("flv")) || (extension.equals("fxm")))
/* 214 */         contentType = "video/x-javafx";
/* 215 */       else if (extension.equals("mp3"))
/* 216 */         contentType = "audio/mpeg";
/* 217 */       else if (extension.equals("wav"))
/* 218 */         contentType = "audio/x-wav";
/* 219 */       else if (extension.equals("mp4"))
/* 220 */         contentType = "video/mp4";
/* 221 */       else if (extension.equals("m4a"))
/* 222 */         contentType = "audio/x-m4a";
/* 223 */       else if (extension.equals("m4v"))
/* 224 */         contentType = "video/x-m4v";
/* 225 */       else if (extension.equals("m3u8"))
/* 226 */         contentType = "application/vnd.apple.mpegurl";
/* 227 */       else if (extension.equals("m3u")) {
/* 228 */         contentType = "audio/mpegurl";
/*     */       }
/*     */     }
/*     */ 
/* 232 */     return contentType;
/*     */   }
/*     */ 
/*     */   public static void warning(Object source, String message)
/*     */   {
/* 246 */     if (((source != null ? 1 : 0) & (message != null ? 1 : 0)) != 0)
/* 247 */       Logger.logMsg(3, source.getClass().getName() + ": " + message);
/*     */   }
/*     */ 
/*     */   public static void error(Object source, int errCode, String message, Throwable cause)
/*     */   {
/* 262 */     if (cause != null) {
/* 263 */       StackTraceElement[] stackTrace = cause.getStackTrace();
/* 264 */       if ((stackTrace != null) && (stackTrace.length > 0)) {
/* 265 */         StackTraceElement trace = stackTrace[0];
/* 266 */         Logger.logMsg(4, trace.getClassName(), trace.getMethodName(), "( " + trace.getLineNumber() + ") " + message);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 273 */     List listeners = NativeMediaManager.getDefaultInstance().getMediaErrorListeners();
/*     */     ListIterator it;
/* 275 */     if (!listeners.isEmpty()) {
/* 276 */       for (it = listeners.listIterator(); it.hasNext(); ) {
/* 277 */         MediaErrorListener l = (MediaErrorListener)((WeakReference)it.next()).get();
/* 278 */         if (l != null)
/* 279 */           l.onError(source, errCode, message);
/*     */         else
/* 281 */           it.remove();
/*     */       }
/*     */     }
/*     */     else {
/* 285 */       MediaException e = (cause instanceof MediaException) ? (MediaException)cause : new MediaException(message, cause);
/*     */ 
/* 287 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void nativeWarning(Object source, int warningCode, String warningMessage)
/*     */   {
/* 298 */     String message = String.format("Internal media warning: %d", new Object[] { Integer.valueOf(warningCode) });
/*     */ 
/* 300 */     if (warningMessage != null) {
/* 301 */       message = message + ": " + warningMessage;
/*     */     }
/*     */ 
/* 305 */     Logger.logMsg(3, message);
/*     */   }
/*     */ 
/*     */   public static void nativeError(Object source, MediaError error)
/*     */   {
/* 316 */     Logger.logMsg(4, error.description());
/*     */ 
/* 319 */     List listeners = NativeMediaManager.getDefaultInstance().getMediaErrorListeners();
/*     */     ListIterator it;
/* 321 */     if (!listeners.isEmpty()) {
/* 322 */       for (it = listeners.listIterator(); it.hasNext(); ) {
/* 323 */         MediaErrorListener l = (MediaErrorListener)((WeakReference)it.next()).get();
/* 324 */         if (l != null)
/* 325 */           l.onError(source, error.code(), error.description());
/*     */         else
/* 327 */           it.remove();
/*     */       }
/*     */     }
/*     */     else
/* 331 */       throw new MediaException(error.description(), null, error);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.MediaUtils
 * JD-Core Version:    0.6.2
 */