/*     */ package com.sun.webpane.platform.graphics;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RenderMediaControls
/*     */ {
/*     */   public static final int PLAY_BUTTON = 1;
/*     */   public static final int PAUSE_BUTTON = 2;
/*     */   public static final int DISABLED_PLAY_BUTTON = 3;
/*     */   public static final int MUTE_BUTTON = 4;
/*     */   public static final int UNMUTE_BUTTON = 5;
/*     */   public static final int DISABLED_MUTE_BUTTON = 6;
/*     */   public static final int TIME_SLIDER_TRACK = 9;
/*     */   public static final int TIME_SLIDER_THUMB = 10;
/*     */   public static final int VOLUME_CONTAINER = 11;
/*     */   public static final int VOLUME_TRACK = 12;
/*     */   public static final int VOLUME_THUMB = 13;
/* 107 */   static final int TimeSliderTrackUnbufferedColor = rgba(236, 135, 125);
/* 108 */   static final int TimeSliderTrackBufferedColor = rgba(249, 26, 2);
/*     */   static final int TimeSliderTrackThickness = 3;
/* 175 */   static final int VolumeTrackColor = rgba(208, 208, 208, 128);
/*     */   static final int VolumeTrackThickness = 1;
/*     */   public static final int SLIDER_TYPE_TIME = 0;
/*     */   public static final int SLIDER_TYPE_VOLUME = 1;
/* 218 */   private static final Map<String, WCImage> controlImages = new HashMap();
/*     */   private static final boolean log = false;
/*     */ 
/*     */   static String getControlName(int paramInt)
/*     */   {
/*  39 */     switch (paramInt) { case 1:
/*  40 */       return "PLAY_BUTTON";
/*     */     case 2:
/*  41 */       return "PAUSE_BUTTON";
/*     */     case 3:
/*  42 */       return "DISABLED_PLAY_BUTTON";
/*     */     case 4:
/*  44 */       return "MUTE_BUTTON";
/*     */     case 5:
/*  45 */       return "UNMUTE_BUTTON";
/*     */     case 6:
/*  46 */       return "DISABLED_MUTE_BUTTON";
/*     */     case 9:
/*  52 */       return "TIME_SLIDER_TRACK";
/*     */     case 10:
/*  53 */       return "TIME_SLIDER_THUMB";
/*     */     case 11:
/*  55 */       return "VOLUME_CONTAINER";
/*     */     case 12:
/*  56 */       return "VOLUME_TRACK";
/*     */     case 13:
/*  57 */       return "VOLUME_THUMB";
/*     */     case 7:
/*     */     case 8:
/*     */     }
/*     */ 
/*  62 */     return "{UNKNOWN CONTROL " + paramInt + "}";
/*     */   }
/*     */ 
/*     */   public static void paintControl(WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  74 */     switch (paramInt1) {
/*     */     case 1:
/*  76 */       paintControlImage("mediaPlay", paramWCGraphicsContext, paramInt2, paramInt3, paramInt4, paramInt5);
/*  77 */       break;
/*     */     case 2:
/*  79 */       paintControlImage("mediaPause", paramWCGraphicsContext, paramInt2, paramInt3, paramInt4, paramInt5);
/*  80 */       break;
/*     */     case 3:
/*  82 */       paintControlImage("mediaPlayDisabled", paramWCGraphicsContext, paramInt2, paramInt3, paramInt4, paramInt5);
/*  83 */       break;
/*     */     case 4:
/*  85 */       paintControlImage("mediaMute", paramWCGraphicsContext, paramInt2, paramInt3, paramInt4, paramInt5);
/*  86 */       break;
/*     */     case 5:
/*  88 */       paintControlImage("mediaUnmute", paramWCGraphicsContext, paramInt2, paramInt3, paramInt4, paramInt5);
/*  89 */       break;
/*     */     case 6:
/*  91 */       paintControlImage("mediaMuteDisabled", paramWCGraphicsContext, paramInt2, paramInt3, paramInt4, paramInt5);
/*  92 */       break;
/*     */     case 10:
/*  94 */       paintControlImage("mediaTimeThumb", paramWCGraphicsContext, paramInt2, paramInt3, paramInt4, paramInt5);
/*  95 */       break;
/*     */     case 11:
/*  97 */       break;
/*     */     case 13:
/*  99 */       paintControlImage("mediaVolumeThumb", paramWCGraphicsContext, paramInt2, paramInt3, paramInt4, paramInt5);
/* 100 */       break;
/*     */     case 7:
/*     */     case 8:
/*     */     case 9:
/*     */     case 12:
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void paintTimeSliderTrack(WCGraphicsContext paramWCGraphicsContext, float paramFloat1, float paramFloat2, float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 130 */     paramInt2 += (paramInt4 - 3) / 2;
/* 131 */     paramInt4 = 3;
/*     */ 
/* 133 */     int i = fwkGetSliderThumbSize(0) >> 16 & 0xFFFF;
/* 134 */     paramInt3 -= i;
/* 135 */     paramInt1 += i / 2;
/*     */ 
/* 137 */     if (paramFloat1 >= 0.0F)
/*     */     {
/* 140 */       float f1 = 1.0F / paramFloat1 * paramInt3;
/* 141 */       float f2 = 0.0F;
/* 142 */       for (int j = 0; j < paramArrayOfFloat.length; j += 2)
/*     */       {
/* 148 */         paramWCGraphicsContext.fillRect(paramInt1 + f1 * f2, paramInt2, f1 * (paramArrayOfFloat[j] - f2), paramInt4, Integer.valueOf(TimeSliderTrackUnbufferedColor));
/*     */ 
/* 156 */         paramWCGraphicsContext.fillRect(paramInt1 + f1 * paramArrayOfFloat[j], paramInt2, f1 * (paramArrayOfFloat[(j + 1)] - paramArrayOfFloat[j]), paramInt4, Integer.valueOf(TimeSliderTrackBufferedColor));
/*     */ 
/* 159 */         f2 = paramArrayOfFloat[(j + 1)];
/*     */       }
/* 161 */       if (f2 < paramFloat1)
/*     */       {
/* 167 */         paramWCGraphicsContext.fillRect(paramInt1 + f1 * f2, paramInt2, f1 * (paramFloat1 - f2), paramInt4, Integer.valueOf(TimeSliderTrackUnbufferedColor));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void paintVolumeTrack(WCGraphicsContext paramWCGraphicsContext, float paramFloat, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 188 */     paramInt1 += (paramInt3 + 1 - 1) / 2;
/* 189 */     paramInt3 = 1;
/*     */ 
/* 191 */     int i = fwkGetSliderThumbSize(0) & 0xFFFF;
/* 192 */     paramInt4 -= i;
/* 193 */     paramInt2 += i / 2;
/*     */ 
/* 196 */     paramWCGraphicsContext.fillRect(paramInt1, paramInt2, paramInt3, paramInt4, Integer.valueOf(VolumeTrackColor));
/*     */   }
/*     */ 
/*     */   public static int fwkGetSliderThumbSize(int paramInt)
/*     */   {
/* 205 */     WCImage localWCImage = null;
/* 206 */     switch (paramInt) {
/*     */     case 0:
/* 208 */       localWCImage = getControlImage("mediaTimeThumb");
/*     */     case 1:
/* 210 */       localWCImage = getControlImage("mediaVolumeThumb");
/*     */     }
/* 212 */     if (localWCImage != null) {
/* 213 */       return localWCImage.getWidth() << 16 | localWCImage.getHeight();
/*     */     }
/* 215 */     return 0;
/*     */   }
/*     */ 
/*     */   private static WCImage getControlImage(String paramString)
/*     */   {
/* 222 */     WCImage localWCImage = (WCImage)controlImages.get(paramString);
/* 223 */     if (localWCImage == null) {
/* 224 */       WCImgDecoder localWCImgDecoder = WCGraphicsManager.getGraphicsManager().getImgDecoder();
/* 225 */       localWCImgDecoder.loadFromResource(paramString);
/* 226 */       WCImageFrame localWCImageFrame = localWCImgDecoder.getFrame(0, null);
/* 227 */       if (localWCImageFrame != null) {
/* 228 */         localWCImage = localWCImageFrame.getFrame();
/* 229 */         controlImages.put(paramString, localWCImage);
/*     */       }
/*     */     }
/* 232 */     return localWCImage;
/*     */   }
/*     */ 
/*     */   private static void paintControlImage(String paramString, WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 237 */     WCImage localWCImage = getControlImage(paramString);
/* 238 */     if (localWCImage != null)
/*     */     {
/* 240 */       paramInt1 += (paramInt3 - localWCImage.getWidth()) / 2;
/* 241 */       paramInt3 = localWCImage.getWidth();
/* 242 */       paramInt2 += (paramInt4 - localWCImage.getHeight()) / 2;
/* 243 */       paramInt4 = localWCImage.getHeight();
/* 244 */       paramWCGraphicsContext.drawImage(localWCImage, paramInt1, paramInt2, paramInt3, paramInt4, 0.0F, 0.0F, localWCImage.getWidth(), localWCImage.getHeight());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static int rgba(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 253 */     return (paramInt4 & 0xFF) << 24 | (paramInt1 & 0xFF) << 16 | (paramInt2 & 0xFF) << 8 | paramInt3 & 0xFF;
/*     */   }
/*     */   private static int rgba(int paramInt1, int paramInt2, int paramInt3) {
/* 256 */     return rgba(paramInt1, paramInt2, paramInt3, 255);
/*     */   }
/*     */ 
/*     */   private static void log(String paramString)
/*     */   {
/* 261 */     System.out.println(paramString);
/* 262 */     System.out.flush();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.RenderMediaControls
 * JD-Core Version:    0.6.2
 */