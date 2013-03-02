/*     */ package com.sun.media.jfxmediaimpl;
/*     */ 
/*     */ import com.sun.media.jfxmedia.control.VideoDataBuffer;
/*     */ import com.sun.media.jfxmedia.control.VideoFormat;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ public class NativeVideoBuffer
/*     */   implements VideoDataBuffer
/*     */ {
/*     */   private long nativePeer;
/*     */   private AtomicInteger holdCount;
/*     */   private NativeVideoBuffer cachedBGRARep;
/*     */   private static final boolean DEBUG_DISPOSED_BUFFERS = false;
/*  40 */   private static final VideoBufferDisposer disposer = new VideoBufferDisposer(null);
/*     */ 
/*     */   private static native void nativeDisposeBuffer(long paramLong);
/*     */ 
/*     */   private native double nativeGetTimestamp(long paramLong);
/*     */ 
/*     */   private native ByteBuffer nativeGetBuffer(long paramLong);
/*     */ 
/*     */   private native long nativeGetFrameNumber(long paramLong);
/*     */ 
/*     */   private native int nativeGetWidth(long paramLong);
/*     */ 
/*     */   private native int nativeGetHeight(long paramLong);
/*     */ 
/*     */   private native int nativeGetEncodedWidth(long paramLong);
/*     */ 
/*     */   private native int nativeGetEncodedHeight(long paramLong);
/*     */ 
/*     */   private native int nativeGetFormat(long paramLong);
/*     */ 
/*     */   private native boolean nativeHasAlpha(long paramLong);
/*     */ 
/*     */   private native int nativeGetPlaneCount(long paramLong);
/*     */ 
/*     */   private native int[] nativeGetPlaneOffsets(long paramLong);
/*     */ 
/*     */   private native int[] nativeGetPlaneStrides(long paramLong);
/*     */ 
/*     */   private native long nativeConvertToFormat(long paramLong, int paramInt);
/*     */ 
/*     */   private native void nativeSetDirty(long paramLong);
/*     */ 
/*  43 */   public static NativeVideoBuffer createVideoBuffer(long nativePeer) { NativeVideoBuffer buffer = new NativeVideoBuffer(nativePeer);
/*  44 */     MediaDisposer.addResourceDisposer(buffer, new Long(nativePeer), disposer);
/*  45 */     return buffer; }
/*     */ 
/*     */   private NativeVideoBuffer(long nativePeer)
/*     */   {
/*  49 */     this.holdCount = new AtomicInteger(1);
/*  50 */     this.nativePeer = nativePeer;
/*     */   }
/*     */ 
/*     */   public void holdFrame()
/*     */   {
/*  55 */     if (0L != this.nativePeer)
/*  56 */       this.holdCount.incrementAndGet();
/*     */   }
/*     */ 
/*     */   public void releaseFrame()
/*     */   {
/*  64 */     if ((0L != this.nativePeer) && 
/*  65 */       (this.holdCount.decrementAndGet() <= 0))
/*     */     {
/*  67 */       if (null != this.cachedBGRARep) {
/*  68 */         this.cachedBGRARep.releaseFrame();
/*  69 */         this.cachedBGRARep = null;
/*     */       }
/*     */ 
/*  73 */       MediaDisposer.removeResourceDisposer(new Long(this.nativePeer));
/*  74 */       nativeDisposeBuffer(this.nativePeer);
/*  75 */       this.nativePeer = 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   public double getTimestamp()
/*     */   {
/*  83 */     if (0L != this.nativePeer) {
/*  84 */       return nativeGetTimestamp(this.nativePeer);
/*     */     }
/*     */ 
/*  88 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public ByteBuffer getBuffer() {
/*  92 */     if (0L != this.nativePeer) {
/*  93 */       ByteBuffer buffer = nativeGetBuffer(this.nativePeer);
/*     */ 
/*  95 */       buffer.order(ByteOrder.nativeOrder());
/*  96 */       return buffer;
/*     */     }
/*     */ 
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   public long getFrameNumber() {
/* 104 */     if (0L != this.nativePeer) {
/* 105 */       return nativeGetFrameNumber(this.nativePeer);
/*     */     }
/*     */ 
/* 109 */     return 0L;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/* 113 */     if (0L != this.nativePeer) {
/* 114 */       return nativeGetWidth(this.nativePeer);
/*     */     }
/*     */ 
/* 118 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 122 */     if (0L != this.nativePeer) {
/* 123 */       return nativeGetHeight(this.nativePeer);
/*     */     }
/*     */ 
/* 127 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getEncodedWidth() {
/* 131 */     if (0L != this.nativePeer) {
/* 132 */       return nativeGetEncodedWidth(this.nativePeer);
/*     */     }
/*     */ 
/* 136 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getEncodedHeight() {
/* 140 */     if (0L != this.nativePeer) {
/* 141 */       return nativeGetEncodedHeight(this.nativePeer);
/*     */     }
/*     */ 
/* 145 */     return 0;
/*     */   }
/*     */ 
/*     */   public VideoFormat getFormat() {
/* 149 */     if (0L != this.nativePeer) {
/* 150 */       int formatType = nativeGetFormat(this.nativePeer);
/* 151 */       return VideoFormat.formatForType(formatType);
/*     */     }
/*     */ 
/* 155 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean hasAlpha() {
/* 159 */     if (0L != this.nativePeer) {
/* 160 */       return nativeHasAlpha(this.nativePeer);
/*     */     }
/*     */ 
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */   public int getPlaneCount() {
/* 168 */     if (0L != this.nativePeer) {
/* 169 */       return nativeGetPlaneCount(this.nativePeer);
/*     */     }
/*     */ 
/* 173 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getOffsetForPlane(int planeIndex) {
/* 177 */     if (0L != this.nativePeer) {
/* 178 */       int[] offsets = nativeGetPlaneOffsets(this.nativePeer);
/* 179 */       return offsets[planeIndex];
/*     */     }
/*     */ 
/* 183 */     return 0;
/*     */   }
/*     */ 
/*     */   public int[] getPlaneOffsets() {
/* 187 */     if (0L != this.nativePeer) {
/* 188 */       return nativeGetPlaneOffsets(this.nativePeer);
/*     */     }
/*     */ 
/* 192 */     return null;
/*     */   }
/*     */ 
/*     */   public int getStrideForPlane(int planeIndex) {
/* 196 */     if (0L != this.nativePeer) {
/* 197 */       int[] strides = nativeGetPlaneStrides(this.nativePeer);
/* 198 */       return strides[planeIndex];
/*     */     }
/*     */ 
/* 202 */     return 0;
/*     */   }
/*     */ 
/*     */   public int[] getPlaneStrides() {
/* 206 */     if (0L != this.nativePeer) {
/* 207 */       return nativeGetPlaneStrides(this.nativePeer);
/*     */     }
/*     */ 
/* 211 */     return null;
/*     */   }
/*     */ 
/*     */   public VideoDataBuffer convertToFormat(VideoFormat newFormat) {
/* 215 */     if (0L != this.nativePeer)
/*     */     {
/* 217 */       if ((newFormat == VideoFormat.BGRA_PRE) && (null != this.cachedBGRARep)) {
/* 218 */         this.cachedBGRARep.holdFrame();
/* 219 */         return this.cachedBGRARep;
/*     */       }
/*     */ 
/* 222 */       long newFrame = nativeConvertToFormat(this.nativePeer, newFormat.getNativeType());
/* 223 */       if (0L != newFrame) {
/* 224 */         NativeVideoBuffer frame = createVideoBuffer(newFrame);
/* 225 */         if (newFormat == VideoFormat.BGRA_PRE) {
/* 226 */           frame.holdFrame();
/* 227 */           this.cachedBGRARep = frame;
/*     */         }
/* 229 */         return frame;
/*     */       }
/* 231 */       return null;
/*     */     }
/*     */ 
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */   public void setDirty() {
/* 239 */     if (0L != this.nativePeer)
/* 240 */       nativeSetDirty(this.nativePeer);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 260 */     return "[NativeVideoBuffer peer=" + Long.toHexString(this.nativePeer) + ", format=" + getFormat() + ", size=(" + getWidth() + "," + getHeight() + "), timestamp=" + getTimestamp() + "]";
/*     */   }
/*     */ 
/*     */   private static class VideoBufferDisposer
/*     */     implements MediaDisposer.ResourceDisposer
/*     */   {
/*     */     public void disposeResource(Object resource)
/*     */     {
/* 249 */       if ((resource instanceof Long))
/* 250 */         NativeVideoBuffer.nativeDisposeBuffer(((Long)resource).longValue());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.NativeVideoBuffer
 * JD-Core Version:    0.6.2
 */