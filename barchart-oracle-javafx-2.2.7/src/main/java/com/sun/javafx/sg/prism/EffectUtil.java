/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ import com.sun.scenario.effect.DropShadow;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.InnerShadow;
/*     */ 
/*     */ class EffectUtil
/*     */ {
/*     */   private static final int TEX_SIZE = 256;
/*     */   private static Texture itex;
/*     */   private static Texture dtex;
/*     */ 
/*     */   static boolean renderEffectForRectangularNode(NGNode paramNGNode, Graphics paramGraphics, Effect paramEffect, float paramFloat1, boolean paramBoolean, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*     */   {
/*  38 */     if ((!paramGraphics.getTransformNoClone().is2D()) && (paramGraphics.isDepthBuffer()) && (paramGraphics.isDepthTest()))
/*     */     {
/*  69 */       return false;
/*     */     }
/*     */     Object localObject;
/*     */     float f;
/*  71 */     if (((paramEffect instanceof InnerShadow)) && (!paramBoolean))
/*     */     {
/*  73 */       localObject = (InnerShadow)paramEffect;
/*  74 */       f = ((InnerShadow)localObject).getRadius();
/*  75 */       if ((f > 0.0F) && (f < paramFloat4 / 2.0F) && (f < paramFloat5 / 2.0F) && (((InnerShadow)localObject).getChoke() == 0.0F) && (((InnerShadow)localObject).getShadowSourceInput() == null) && (((InnerShadow)localObject).getContentInput() == null))
/*     */       {
/*  82 */         paramNGNode.renderContent(paramGraphics);
/*  83 */         renderRectInnerShadow(paramGraphics, (InnerShadow)localObject, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
/*  84 */         return true;
/*     */       }
/*  86 */     } else if ((paramEffect instanceof DropShadow)) {
/*  87 */       localObject = (DropShadow)paramEffect;
/*  88 */       f = ((DropShadow)localObject).getRadius();
/*  89 */       if ((f > 0.0F) && (f < paramFloat4 / 2.0F) && (f < paramFloat5 / 2.0F) && (((DropShadow)localObject).getSpread() == 0.0F) && (((DropShadow)localObject).getShadowSourceInput() == null) && (((DropShadow)localObject).getContentInput() == null))
/*     */       {
/*  96 */         renderRectDropShadow(paramGraphics, (DropShadow)localObject, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
/*  97 */         paramNGNode.renderContent(paramGraphics);
/*  98 */         return true;
/*     */       }
/*     */     }
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   static void renderRectInnerShadow(Graphics paramGraphics, InnerShadow paramInnerShadow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*     */   {
/* 107 */     if (itex == null) {
/* 108 */       byte[] arrayOfByte = new byte[65536];
/* 109 */       fillGaussian(arrayOfByte, 256, 128.0F, paramInnerShadow.getChoke(), true);
/* 110 */       Image localImage = Image.fromByteAlphaData(arrayOfByte, 256, 256);
/* 111 */       itex = paramGraphics.getResourceFactory().createTexture(localImage);
/*     */     }
/* 113 */     float f1 = paramInnerShadow.getRadius();
/* 114 */     int i = itex.getPhysicalWidth();
/* 115 */     int j = itex.getContentX();
/* 116 */     int k = j + itex.getContentWidth();
/* 117 */     float f2 = (j + 0.5F) / i;
/* 118 */     float f3 = (k - 0.5F) / i;
/* 119 */     float f4 = paramFloat2;
/* 120 */     float f5 = paramFloat3;
/* 121 */     float f6 = paramFloat2 + paramFloat4;
/* 122 */     float f7 = paramFloat3 + paramFloat5;
/* 123 */     float f8 = f4 + paramInnerShadow.getOffsetX();
/* 124 */     float f9 = f5 + paramInnerShadow.getOffsetY();
/* 125 */     float f10 = f8 + paramFloat4;
/* 126 */     float f11 = f9 + paramFloat5;
/* 127 */     paramGraphics.setPaint(toPrismColor(paramInnerShadow.getColor(), paramFloat1));
/*     */ 
/* 129 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f4, f5, f6, f9 - f1, f2, f2, f2, f2);
/*     */ 
/* 133 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f8 - f1, f9 - f1, f8 + f1, f9 + f1, f2, f2, f3, f3);
/*     */ 
/* 137 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f8 + f1, f9 - f1, f10 - f1, f9 + f1, f3, f2, f3, f3);
/*     */ 
/* 141 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f10 - f1, f9 - f1, f10 + f1, f9 + f1, f3, f2, f2, f3);
/*     */ 
/* 145 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f4, f9 - f1, f8 - f1, f11 + f1, f2, f2, f2, f2);
/*     */ 
/* 149 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f8 - f1, f9 + f1, f8 + f1, f11 - f1, f2, f3, f3, f3);
/*     */ 
/* 153 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f10 - f1, f9 + f1, f10 + f1, f11 - f1, f3, f3, f2, f3);
/*     */ 
/* 157 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f10 + f1, f9 - f1, f6, f11 + f1, f2, f2, f2, f2);
/*     */ 
/* 161 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f8 - f1, f11 - f1, f8 + f1, f11 + f1, f2, f3, f3, f2);
/*     */ 
/* 165 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f8 + f1, f11 - f1, f10 - f1, f11 + f1, f3, f3, f3, f2);
/*     */ 
/* 169 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f10 - f1, f11 - f1, f10 + f1, f11 + f1, f3, f3, f2, f2);
/*     */ 
/* 173 */     drawClippedTexture(paramGraphics, itex, f4, f5, f6, f7, f4, f11 + f1, f6, f7, f2, f2, f2, f2);
/*     */   }
/*     */ 
/*     */   static void drawClippedTexture(Graphics paramGraphics, Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
/*     */   {
/* 184 */     if ((paramFloat5 >= paramFloat7) || (paramFloat6 >= paramFloat8) || (paramFloat1 >= paramFloat3) || (paramFloat2 >= paramFloat4)) return;
/* 185 */     if ((paramFloat7 > paramFloat1) && (paramFloat5 < paramFloat3)) {
/* 186 */       if (paramFloat5 < paramFloat1) {
/* 187 */         paramFloat9 += (paramFloat11 - paramFloat9) * (paramFloat1 - paramFloat5) / (paramFloat7 - paramFloat5);
/* 188 */         paramFloat5 = paramFloat1;
/*     */       }
/* 190 */       if (paramFloat7 > paramFloat3) {
/* 191 */         paramFloat11 -= (paramFloat11 - paramFloat9) * (paramFloat7 - paramFloat3) / (paramFloat7 - paramFloat5);
/* 192 */         paramFloat7 = paramFloat3;
/*     */       }
/*     */     } else {
/* 195 */       return;
/*     */     }
/* 197 */     if ((paramFloat8 > paramFloat2) && (paramFloat6 < paramFloat4)) {
/* 198 */       if (paramFloat6 < paramFloat2) {
/* 199 */         paramFloat10 += (paramFloat12 - paramFloat10) * (paramFloat2 - paramFloat6) / (paramFloat8 - paramFloat6);
/* 200 */         paramFloat6 = paramFloat2;
/*     */       }
/* 202 */       if (paramFloat8 > paramFloat4) {
/* 203 */         paramFloat12 -= (paramFloat12 - paramFloat10) * (paramFloat8 - paramFloat4) / (paramFloat8 - paramFloat6);
/* 204 */         paramFloat8 = paramFloat4;
/*     */       }
/*     */     } else {
/* 207 */       return;
/*     */     }
/* 209 */     paramGraphics.drawTextureRaw(paramTexture, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12);
/*     */   }
/*     */ 
/*     */   static void renderRectDropShadow(Graphics paramGraphics, DropShadow paramDropShadow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*     */   {
/* 215 */     if (dtex == null) {
/* 216 */       byte[] arrayOfByte = new byte[65536];
/* 217 */       fillGaussian(arrayOfByte, 256, 128.0F, paramDropShadow.getSpread(), false);
/*     */ 
/* 219 */       Image localImage = Image.fromByteAlphaData(arrayOfByte, 256, 256);
/* 220 */       dtex = paramGraphics.getResourceFactory().createTexture(localImage);
/*     */     }
/* 222 */     float f1 = paramDropShadow.getRadius();
/* 223 */     int i = dtex.getPhysicalWidth();
/* 224 */     int j = dtex.getContentX();
/* 225 */     int k = j + dtex.getContentWidth();
/* 226 */     float f2 = (j + 0.5F) / i;
/* 227 */     float f3 = (k - 0.5F) / i;
/* 228 */     float f4 = paramFloat2 + paramDropShadow.getOffsetX();
/* 229 */     float f5 = paramFloat3 + paramDropShadow.getOffsetY();
/* 230 */     float f6 = f4 + paramFloat4;
/* 231 */     float f7 = f5 + paramFloat5;
/* 232 */     paramGraphics.setPaint(toPrismColor(paramDropShadow.getColor(), paramFloat1));
/* 233 */     paramGraphics.drawTextureRaw(dtex, f4 - f1, f5 - f1, f4 + f1, f5 + f1, f2, f2, f3, f3);
/*     */ 
/* 236 */     paramGraphics.drawTextureRaw(dtex, f6 - f1, f5 - f1, f6 + f1, f5 + f1, f3, f2, f2, f3);
/*     */ 
/* 239 */     paramGraphics.drawTextureRaw(dtex, f6 - f1, f7 - f1, f6 + f1, f7 + f1, f3, f3, f2, f2);
/*     */ 
/* 242 */     paramGraphics.drawTextureRaw(dtex, f4 - f1, f7 - f1, f4 + f1, f7 + f1, f2, f3, f3, f2);
/*     */ 
/* 245 */     paramGraphics.drawTextureRaw(dtex, f4 + f1, f5 + f1, f6 - f1, f7 - f1, f3, f3, f3, f3);
/*     */ 
/* 248 */     paramGraphics.drawTextureRaw(dtex, f4 - f1, f5 + f1, f4 + f1, f7 - f1, f2, f3, f3, f3);
/*     */ 
/* 251 */     paramGraphics.drawTextureRaw(dtex, f6 - f1, f5 + f1, f6 + f1, f7 - f1, f3, f3, f2, f3);
/*     */ 
/* 254 */     paramGraphics.drawTextureRaw(dtex, f4 + f1, f5 - f1, f6 - f1, f5 + f1, f3, f2, f3, f3);
/*     */ 
/* 257 */     paramGraphics.drawTextureRaw(dtex, f4 + f1, f7 - f1, f6 - f1, f7 + f1, f3, f3, f3, f2);
/*     */   }
/*     */ 
/*     */   private static void fillGaussian(byte[] paramArrayOfByte, int paramInt, float paramFloat1, float paramFloat2, boolean paramBoolean)
/*     */   {
/* 267 */     float f1 = paramFloat1 / 3.0F;
/* 268 */     float f2 = 2.0F * f1 * f1;
/* 269 */     if (f2 < 1.4E-45F)
/*     */     {
/* 271 */       f2 = 1.4E-45F;
/*     */     }
/*     */ 
/* 274 */     float[] arrayOfFloat = new float[paramInt];
/* 275 */     int i = (paramInt + 1) / 2;
/* 276 */     float f3 = 0.0F;
/*     */     int k;
/* 277 */     for (int j = 0; j < arrayOfFloat.length; j++) {
/* 278 */       k = i - j;
/* 279 */       f3 += (float)Math.exp(-(k * k) / f2);
/* 280 */       arrayOfFloat[j] = f3;
/*     */     }
/*     */ 
/* 283 */     for (j = 0; j < arrayOfFloat.length; j++) {
/* 284 */       arrayOfFloat[j] /= f3;
/*     */     }
/* 286 */     for (j = 0; j < paramInt; j++)
/* 287 */       for (k = 0; k < paramInt; k++) {
/* 288 */         float f4 = arrayOfFloat[j] * arrayOfFloat[k];
/* 289 */         if (paramBoolean)
/*     */         {
/* 291 */           f4 = 1.0F - f4;
/*     */         }
/* 293 */         int m = (int)(f4 * 255.0F);
/*     */ 
/* 295 */         if (m < 0) m = 0; else if (m > 255) m = 255;
/* 296 */         paramArrayOfByte[(j * paramInt + k)] = ((byte)m);
/*     */       }
/*     */   }
/*     */ 
/*     */   private static Color toPrismColor(Color4f paramColor4f, float paramFloat)
/*     */   {
/* 302 */     float f1 = paramColor4f.getRed();
/* 303 */     float f2 = paramColor4f.getGreen();
/* 304 */     float f3 = paramColor4f.getBlue();
/* 305 */     float f4 = paramColor4f.getAlpha() * paramFloat;
/* 306 */     return new Color(f1, f2, f3, f4);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.EffectUtil
 * JD-Core Version:    0.6.2
 */