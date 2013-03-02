/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Cursor;
/*     */ import com.sun.glass.ui.Size;
/*     */ import com.sun.javafx.cursor.CursorFrame;
/*     */ import com.sun.javafx.cursor.CursorType;
/*     */ import com.sun.javafx.cursor.ImageCursorFrame;
/*     */ import com.sun.javafx.iio.common.PushbroomScaler;
/*     */ import com.sun.javafx.iio.common.ScalerFactory;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javafx.geometry.Dimension2D;
/*     */ 
/*     */ public final class CursorUtils
/*     */ {
/*     */   public static Cursor getPlatformCursor(CursorFrame paramCursorFrame)
/*     */   {
/*  25 */     Cursor localCursor1 = (Cursor)paramCursorFrame.getPlatformCursor(Cursor.class);
/*     */ 
/*  27 */     if (localCursor1 != null)
/*     */     {
/*  29 */       return localCursor1;
/*     */     }
/*     */ 
/*  33 */     Cursor localCursor2 = createPlatformCursor(paramCursorFrame);
/*  34 */     paramCursorFrame.setPlatforCursor(Cursor.class, localCursor2);
/*     */ 
/*  36 */     return localCursor2;
/*     */   }
/*     */ 
/*     */   public static Dimension2D getBestCursorSize(int paramInt1, int paramInt2)
/*     */   {
/*  41 */     Size localSize = Cursor.getBestSize(paramInt1, paramInt2);
/*  42 */     return new Dimension2D(localSize.width, localSize.height);
/*     */   }
/*     */ 
/*     */   private static Cursor createPlatformCursor(CursorFrame paramCursorFrame) {
/*  46 */     Application localApplication = Application.GetApplication();
/*  47 */     switch (1.$SwitchMap$com$sun$javafx$cursor$CursorType[paramCursorFrame.getCursorType().ordinal()]) {
/*     */     case 1:
/*  49 */       return localApplication.createCursor(3);
/*     */     case 2:
/*  51 */       return localApplication.createCursor(2);
/*     */     case 3:
/*  53 */       return localApplication.createCursor(14);
/*     */     case 4:
/*  55 */       return localApplication.createCursor(1);
/*     */     case 5:
/*  57 */       return localApplication.createCursor(5);
/*     */     case 6:
/*  59 */       return localApplication.createCursor(4);
/*     */     case 7:
/*  61 */       return localApplication.createCursor(6);
/*     */     case 8:
/*  63 */       return localApplication.createCursor(11);
/*     */     case 9:
/*  65 */       return localApplication.createCursor(12);
/*     */     case 10:
/*  67 */       return localApplication.createCursor(19);
/*     */     case 11:
/*  69 */       return localApplication.createCursor(13);
/*     */     case 12:
/*  71 */       return localApplication.createCursor(15);
/*     */     case 13:
/*  73 */       return localApplication.createCursor(16);
/*     */     case 14:
/*  75 */       return localApplication.createCursor(17);
/*     */     case 15:
/*  77 */       return localApplication.createCursor(18);
/*     */     case 16:
/*     */     case 17:
/*  80 */       return localApplication.createCursor(12);
/*     */     case 18:
/*     */     case 19:
/*  83 */       return localApplication.createCursor(11);
/*     */     case 20:
/*  85 */       return localApplication.createCursor(-1);
/*     */     case 21:
/*  87 */       return createPlatformImageCursor((ImageCursorFrame)paramCursorFrame);
/*     */     }
/*     */ 
/*  90 */     System.err.println("unhandled Cursor: " + paramCursorFrame.getCursorType());
/*     */ 
/*  92 */     return localApplication.createCursor(1);
/*     */   }
/*     */ 
/*     */   private static Cursor createPlatformImageCursor(ImageCursorFrame paramImageCursorFrame)
/*     */   {
/*  98 */     return createPlatformImageCursor(paramImageCursorFrame.getPlatformImage(), (float)paramImageCursorFrame.getHotspotX(), (float)paramImageCursorFrame.getHotspotY());
/*     */   }
/*     */ 
/*     */   private static Cursor createPlatformImageCursor(Object paramObject, float paramFloat1, float paramFloat2)
/*     */   {
/* 107 */     if (paramObject == null) {
/* 108 */       throw new IllegalArgumentException("QuantumToolkit.createImageCursor: no image");
/*     */     }
/*     */ 
/* 112 */     assert ((paramObject instanceof Image));
/*     */ 
/* 114 */     Image localImage1 = (Image)paramObject;
/*     */ 
/* 116 */     int i = localImage1.getHeight();
/* 117 */     int j = localImage1.getWidth();
/* 118 */     Dimension2D localDimension2D = getBestCursorSize(j, i);
/* 119 */     float f1 = (float)localDimension2D.getWidth();
/* 120 */     float f2 = (float)localDimension2D.getHeight();
/*     */ 
/* 122 */     if ((f1 <= 0.0F) || (f2 <= 0.0F))
/* 123 */       return Application.GetApplication().createCursor(1);
/*     */     ByteBuffer localByteBuffer;
/* 128 */     switch (1.$SwitchMap$com$sun$prism$PixelFormat[localImage1.getPixelFormat().ordinal()]) {
/*     */     case 1:
/* 130 */       return createPlatformImageCursor((int)paramFloat1, (int)paramFloat2, j, i, localImage1.getPixelBuffer());
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/* 136 */       localByteBuffer = (ByteBuffer)localImage1.getPixelBuffer();
/* 137 */       break;
/*     */     default:
/* 139 */       throw new IllegalArgumentException("QuantumToolkit.createImageCursor: bad image format");
/*     */     }
/*     */ 
/* 143 */     float f3 = f1 / j;
/* 144 */     float f4 = f2 / i;
/*     */ 
/* 146 */     int k = (int)(paramFloat1 * f3);
/* 147 */     int m = (int)(paramFloat2 * f4);
/*     */ 
/* 150 */     PushbroomScaler localPushbroomScaler = ScalerFactory.createScaler(j, i, localImage1.getBytesPerPixelUnit(), (int)f1, (int)f2, true);
/*     */ 
/* 155 */     byte[] arrayOfByte = new byte[localByteBuffer.limit()];
/*     */ 
/* 159 */     int n = localImage1.getScanlineStride();
/* 160 */     for (int i1 = 0; i1 < i; i1++) {
/* 161 */       localByteBuffer.position(i1 * n);
/* 162 */       localByteBuffer.get(arrayOfByte, 0, n);
/* 163 */       if (localPushbroomScaler != null) {
/* 164 */         localPushbroomScaler.putSourceScanline(arrayOfByte, 0);
/*     */       }
/*     */     }
/*     */ 
/* 168 */     localByteBuffer.rewind();
/*     */ 
/* 170 */     Image localImage2 = localImage1.iconify(localPushbroomScaler.getDestination(), (int)f1, (int)f2);
/*     */ 
/* 176 */     return createPlatformImageCursor(k, m, localImage2.getWidth(), localImage2.getHeight(), localImage2.getPixelBuffer());
/*     */   }
/*     */ 
/*     */   private static Cursor createPlatformImageCursor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject)
/*     */   {
/* 185 */     Application localApplication = Application.GetApplication();
/* 186 */     return localApplication.createCursor(paramInt1, paramInt2, localApplication.createPixels(paramInt3, paramInt4, (IntBuffer)paramObject));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.CursorUtils
 * JD-Core Version:    0.6.2
 */