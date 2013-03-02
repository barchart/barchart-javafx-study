/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.font.FontStrike;
/*     */ import com.sun.javafx.font.FontStrike.Metrics;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.RoundRectangle2D;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.sg.PGShape.Mode;
/*     */ import com.sun.javafx.sg.PGText;
/*     */ import com.sun.javafx.sg.PGTextHelper;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Paint;
/*     */ 
/*     */ public class NGText extends NGShape
/*     */   implements PGText
/*     */ {
/*  37 */   NGTextHelper helper = null;
/*  38 */   NGTextHelper sgTextHelper = null;
/*     */ 
/* 385 */   private boolean drawingEffect = false;
/*     */ 
/*     */   public NGText()
/*     */   {
/*  41 */     this.helper = new NGTextHelper();
/*  42 */     this.sgTextHelper = new NGTextHelper();
/*     */   }
/*     */ 
/*     */   public PGTextHelper getTextHelper() {
/*  46 */     return this.sgTextHelper;
/*     */   }
/*     */ 
/*     */   public void setText(String paramString)
/*     */   {
/*  57 */     this.helper.setText(paramString);
/*     */   }
/*     */ 
/*     */   public void setFont(Object paramObject) {
/*  61 */     this.helper.setFont(paramObject);
/*     */   }
/*     */ 
/*     */   public void setLocation(float paramFloat1, float paramFloat2) {
/*  65 */     this.helper.setLocation(paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public void geometryChanged()
/*     */   {
/*  70 */     super.geometryChanged();
/*  71 */     this.helper.geometryChangedTextValid();
/*     */   }
/*     */ 
/*     */   public void locationChanged() {
/*  75 */     super.locationChanged();
/*  76 */     this.helper.geometryChangedTextValid();
/*     */   }
/*     */ 
/*     */   public void updateText() {
/*  80 */     this.sgTextHelper.sync(this.helper);
/*  81 */     if (this.sgTextHelper.isGeometryChanged()) {
/*  82 */       super.geometryChanged();
/*  83 */       this.helper.resetGeometryChanged();
/*  84 */       this.sgTextHelper.resetGeometryChanged();
/*     */     }
/*  86 */     if (this.sgTextHelper.isLocationChanged()) {
/*  87 */       super.locationChanged();
/*  88 */       this.helper.resetLocationChanged();
/*  89 */       this.sgTextHelper.resetLocationChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   private FontStrike getStrike() {
/*  94 */     return this.helper.getStrike();
/*     */   }
/*     */ 
/*     */   private FontStrike getStrike(BaseTransform paramBaseTransform) {
/*  98 */     return this.helper.getStrike(paramBaseTransform);
/*     */   }
/*     */ 
/*     */   private Shape getShape(boolean paramBoolean)
/*     */   {
/* 110 */     return this.helper.getShape(paramBoolean);
/*     */   }
/*     */ 
/*     */   public Shape getShape()
/*     */   {
/* 115 */     return getShape(true);
/*     */   }
/*     */ 
/*     */   private void decorate(Graphics paramGraphics, FontStrike paramFontStrike, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 121 */     NGTextHelper.TextAttributes localTextAttributes = this.helper.getAttributes();
/*     */ 
/* 123 */     if (localTextAttributes == null) {
/* 124 */       return;
/*     */     }
/*     */ 
/* 127 */     if ((localTextAttributes.underline) || (localTextAttributes.strikethrough))
/*     */     {
/*     */       float f;
/* 129 */       if (paramFloat3 > 0.0F)
/* 130 */         f = paramFloat3;
/*     */       else {
/* 132 */         f = NGTextHelper.getTabExpandedAdvance(this.helper.getText(), paramFontStrike);
/*     */       }
/*     */ 
/* 136 */       if (localTextAttributes.underline) {
/* 137 */         drawUnderline(paramGraphics, paramFontStrike, paramFloat1, paramFloat2, f);
/*     */       }
/* 139 */       if (localTextAttributes.strikethrough)
/* 140 */         drawStrikeThrough(paramGraphics, paramFontStrike, paramFloat1, paramFloat2, f);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void drawUnderline(Graphics paramGraphics, FontStrike paramFontStrike, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 156 */     if (paramFontStrike == null) {
/* 157 */       paramFontStrike = getStrike(paramGraphics.getTransformNoClone());
/*     */     }
/* 159 */     float f1 = paramFontStrike.getUnderLineOffset();
/* 160 */     float f2 = paramFontStrike.getUnderLineThickness();
/* 161 */     drawDecoration(paramGraphics, paramFloat1, paramFloat2, paramFloat3, f1, f2);
/*     */   }
/*     */ 
/*     */   private void drawStrikeThrough(Graphics paramGraphics, FontStrike paramFontStrike, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 175 */     if (paramFontStrike == null) {
/* 176 */       paramFontStrike = getStrike(paramGraphics.getTransformNoClone());
/*     */     }
/* 178 */     float f1 = paramFontStrike.getStrikeThroughOffset();
/* 179 */     float f2 = paramFontStrike.getStrikeThroughThickness();
/* 180 */     drawDecoration(paramGraphics, paramFloat1, paramFloat2, paramFloat3, f1, f2);
/*     */   }
/*     */ 
/*     */   private void drawDecoration(Graphics paramGraphics, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*     */   {
/* 198 */     RoundRectangle2D localRoundRectangle2D = new RoundRectangle2D();
/* 199 */     localRoundRectangle2D.x = paramFloat1;
/* 200 */     localRoundRectangle2D.y = (paramFloat2 + paramFloat4);
/* 201 */     localRoundRectangle2D.width = paramFloat3;
/* 202 */     localRoundRectangle2D.height = paramFloat5;
/*     */ 
/* 204 */     Paint localPaint = paramGraphics.getPaint();
/*     */ 
/* 206 */     if (this.mode != PGShape.Mode.FILL) {
/* 207 */       paramGraphics.setPaint(this.drawPaint);
/* 208 */       paramGraphics.setStroke(this.drawStroke);
/* 209 */       paramGraphics.draw(localRoundRectangle2D);
/*     */     }
/* 211 */     if (this.mode != PGShape.Mode.STROKE) {
/* 212 */       paramGraphics.setPaint(this.fillPaint);
/* 213 */       paramGraphics.fill(localRoundRectangle2D);
/*     */     }
/*     */ 
/* 217 */     if (paramGraphics.getPaint() != localPaint)
/* 218 */       paramGraphics.setPaint(localPaint);
/*     */   }
/*     */ 
/*     */   private void drawString(Graphics paramGraphics, String paramString, FontStrike paramFontStrike, float paramFloat1, float paramFloat2, int paramInt)
/*     */   {
/* 231 */     NGTextHelper.Selection localSelection = this.helper.getSelection();
/*     */ 
/* 233 */     if ((localSelection != null) && (!localSelection.isEmpty()) && (localSelection.fillPaint != this.fillPaint) && ((localSelection.fillPaint instanceof Color)))
/*     */     {
/* 237 */       paramGraphics.drawString(paramString, paramFontStrike, paramFloat1, paramFloat2, (Color)localSelection.fillPaint, localSelection.start - paramInt, localSelection.end - paramInt);
/*     */     }
/*     */     else
/*     */     {
/* 242 */       paramGraphics.drawString(paramString, paramFontStrike, paramFloat1, paramFloat2);
/*     */     }
/*     */   }
/*     */ 
/*     */   private float tabJustifyExpandedDrawstring(Graphics paramGraphics, String paramString, FontStrike paramFontStrike, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt)
/*     */   {
/* 258 */     char[] arrayOfChar = paramString.toCharArray();
/* 259 */     float f1 = 0.0F;
/* 260 */     float f2 = paramFontStrike.getCharAdvance(' ');
/* 261 */     float f3 = 8.0F * f2;
/* 262 */     float f4 = 0.0F;
/* 263 */     NGTextHelper.IntList localIntList = new NGTextHelper.IntList();
/* 264 */     NGTextHelper.FloatList localFloatList = new NGTextHelper.FloatList();
/*     */ 
/* 266 */     NGTextHelper.TextAttributes localTextAttributes = this.helper.getAttributes();
/* 267 */     int i = (localTextAttributes.textAlignment == 3) && (paramFloat3 > 0.0F) ? 1 : 0;
/*     */ 
/* 270 */     int j = 0;
/* 271 */     int k = 0;
/* 272 */     float f5 = 0.0F;
/*     */ 
/* 275 */     int m = arrayOfChar.length - 1;
/* 276 */     while ((m > 0) && (arrayOfChar[m] == ' ') && (i != 0)) {
/* 277 */       m--;
/*     */     }
/*     */ 
/* 280 */     while (j <= m) {
/* 281 */       char c = arrayOfChar[j];
/* 282 */       if (c == '\t') {
/* 283 */         if (j > k) {
/* 284 */           String str2 = new String(arrayOfChar, k, j - k);
/* 285 */           drawString(paramGraphics, str2, paramFontStrike, paramFloat1 + f4, paramFloat2, paramInt + k);
/*     */ 
/* 289 */           localIntList.index = 0;
/* 290 */           localFloatList.index = 0;
/*     */         }
/* 292 */         j++; k = j;
/*     */ 
/* 294 */         float f6 = f1 + f5;
/*     */ 
/* 296 */         f4 = ((int)(f6 / f3) + 1) * f3;
/* 297 */         f5 = f4;
/* 298 */         f1 = 0.0F;
/*     */       }
/* 300 */       else if ((i != 0) && (c == ' ')) {
/* 301 */         localIntList.add(++j);
/*     */ 
/* 303 */         localFloatList.add(f5 + f2);
/* 304 */         f5 += f2 + f1;
/* 305 */         f1 = 0.0F;
/*     */       } else {
/* 307 */         j++;
/* 308 */         f1 += paramFontStrike.getCharAdvance(c);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 313 */     if (j > k)
/*     */     {
/* 315 */       if ((i != 0) && (localIntList.index > 0)) {
/* 316 */         int[] arrayOfInt = localIntList.getData();
/* 317 */         float[] arrayOfFloat = localFloatList.getData();
/* 318 */         float f7 = paramFloat3 / arrayOfInt.length;
/* 319 */         float f8 = 0.0F;
/* 320 */         int n = 0;
/* 321 */         if (k == 0) {
/* 322 */           str1 = new String(arrayOfChar, k, arrayOfInt[n] - k);
/* 323 */           drawString(paramGraphics, str1, paramFontStrike, paramFloat1, paramFloat2, paramInt + k);
/* 324 */           k = arrayOfInt[(n++)];
/* 325 */           f8 += f7;
/*     */         }
/*     */ 
/* 328 */         for (; n < arrayOfInt.length; n++) {
/* 329 */           if (k < arrayOfInt[n]) {
/* 330 */             str1 = new String(arrayOfChar, k, arrayOfInt[n] - k);
/* 331 */             drawString(paramGraphics, str1, paramFontStrike, paramFloat1 + arrayOfFloat[n] + f8, paramFloat2, paramInt + k);
/*     */ 
/* 333 */             f8 += f7;
/*     */           }
/* 335 */           k = arrayOfInt[n];
/*     */         }
/*     */ 
/* 338 */         f5 += f8;
/*     */       }
/*     */ 
/* 342 */       String str1 = new String(arrayOfChar, k, j - k);
/* 343 */       drawString(paramGraphics, str1, paramFontStrike, paramFloat1 + f5, paramFloat2, paramInt + k);
/*     */     }
/* 345 */     return f5 + f1;
/*     */   }
/*     */ 
/*     */   private float tabExpandedDrawstring(Graphics paramGraphics, String paramString, FontStrike paramFontStrike, float paramFloat1, float paramFloat2, int paramInt)
/*     */   {
/* 351 */     char[] arrayOfChar = paramString.toCharArray();
/* 352 */     float f1 = 0.0F;
/* 353 */     float f2 = 8.0F * paramFontStrike.getCharAdvance(' ');
/* 354 */     float f3 = 0.0F;
/*     */ 
/* 356 */     int i = 0;
/* 357 */     int j = 0;
/* 358 */     while (i < arrayOfChar.length) {
/* 359 */       char c = arrayOfChar[i];
/* 360 */       if (c != '\t') {
/* 361 */         i++;
/* 362 */         f1 += paramFontStrike.getCharAdvance(c);
/*     */       }
/*     */       else {
/* 365 */         if (i > j) {
/* 366 */           String str2 = new String(arrayOfChar, j, i - j);
/* 367 */           drawString(paramGraphics, str2, paramFontStrike, paramFloat1 + f3, paramFloat2, paramInt + j);
/*     */         }
/* 369 */         i++; j = i;
/*     */ 
/* 371 */         float f4 = f3 + f1;
/* 372 */         f3 = ((int)(f4 / f2) + 1) * f2;
/*     */ 
/* 374 */         f1 = 0.0F;
/*     */       }
/*     */     }
/*     */ 
/* 378 */     if (i > j) {
/* 379 */       String str1 = new String(arrayOfChar, j, i - j);
/* 380 */       drawString(paramGraphics, str1, paramFontStrike, paramFloat1 + f3, paramFloat2, paramInt + j);
/*     */     }
/* 382 */     return f3 + f1;
/*     */   }
/*     */ 
/*     */   protected void renderEffect(Graphics paramGraphics)
/*     */   {
/* 409 */     if (!paramGraphics.getTransformNoClone().isTranslateOrIdentity())
/* 410 */       this.drawingEffect = true;
/*     */     try
/*     */     {
/* 413 */       super.renderEffect(paramGraphics);
/*     */     } finally {
/* 415 */       this.drawingEffect = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void renderContent(Graphics paramGraphics)
/*     */   {
/* 421 */     if ((this.mode == PGShape.Mode.EMPTY) || (this.helper.isEmpty())) {
/* 422 */       return;
/*     */     }
/*     */ 
/* 425 */     this.helper.validateText();
/*     */ 
/* 428 */     if (!paramGraphics.getTransformNoClone().is2D()) {
/* 429 */       super.renderContent(paramGraphics);
/* 430 */       return;
/*     */     }
/*     */ 
/* 439 */     Object localObject1 = null;
/* 440 */     String str1 = this.helper.getText();
/* 441 */     NGTextHelper.TextAttributes localTextAttributes = this.helper.getAttributes();
/*     */ 
/* 443 */     if ((localTextAttributes != null) && (localTextAttributes.numberOfLines() > 1) && (getClipNode() != null))
/*     */     {
/* 447 */       localObject2 = (NGNode)getClipNode();
/* 448 */       localObject1 = new RectBounds();
/* 449 */       localObject1 = getClippedBounds((BaseBounds)localObject1, NGTextHelper.IDENT);
/*     */     }
/* 451 */     Object localObject2 = getStrike(paramGraphics.getTransformNoClone());
/* 452 */     if ((((FontStrike)localObject2).getAAMode() == 1) || ((this.fillPaint != null) && (this.fillPaint.isProportional())) || ((this.drawPaint != null) && (this.drawPaint.isProportional())))
/*     */     {
/* 466 */       BaseBounds localBaseBounds = this.helper.computeContentBounds(null, NGTextHelper.IDENT);
/* 467 */       paramGraphics.setNodeBounds((RectBounds)localBaseBounds);
/*     */     }
/* 469 */     if ((this.mode != PGShape.Mode.STROKE) && (!((FontStrike)localObject2).drawAsShapes()) && (!this.drawingEffect))
/*     */     {
/* 471 */       paramGraphics.setPaint(this.fillPaint);
/* 472 */       float f1 = this.helper.getY() + this.helper.getYAdjustment();
/*     */ 
/* 474 */       if (this.helper.simpleSingleLine()) {
/* 475 */         drawString(paramGraphics, str1, (FontStrike)localObject2, this.helper.getX(), f1, 0);
/*     */ 
/* 477 */         if (this.helper.getAttributes() != null) {
/* 478 */           decorate(paramGraphics, (FontStrike)localObject2, this.helper.getX(), f1, this.helper.getMaxLineAdvance());
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 485 */         float f3 = 0.0F;
/* 486 */         if (localTextAttributes.textAlignment == 1)
/* 487 */           f3 = 0.5F;
/* 488 */         else if (localTextAttributes.textAlignment == 2) {
/* 489 */           f3 = 1.0F;
/*     */         }
/* 491 */         float f4 = ((FontStrike)localObject2).getMetrics().getLineHeight();
/* 492 */         int[] arrayOfInt = localTextAttributes.newLineIndices;
/* 493 */         float[] arrayOfFloat = localTextAttributes.lineAdvances;
/* 494 */         if (arrayOfInt == null) {
/* 495 */           arrayOfInt = new int[] { str1.length() - 1 };
/*     */         }
/* 497 */         float f5 = this.helper.getMaxLineAdvance();
/* 498 */         if (arrayOfFloat == null) {
/* 499 */           arrayOfFloat = new float[] { f5 };
/*     */         }
/*     */ 
/* 502 */         int i = 0;
/* 503 */         float f6 = 0.0F;
/*     */ 
/* 506 */         float f7 = localTextAttributes.overrideWrappingWidth ? f5 : localTextAttributes.wrappingWidth;
/*     */ 
/* 508 */         int j = f7 > 0.0F ? 1 : 0;
/* 509 */         float f8 = 0.0F;
/*     */ 
/* 511 */         for (int k = 0; k < arrayOfInt.length; k++)
/*     */         {
/* 514 */           if ((localObject1 != null) && (f1 - f4 > ((BaseBounds)localObject1).getMaxY()))
/*     */           {
/*     */             break;
/*     */           }
/* 518 */           if (j != 0) {
/* 519 */             f8 = f7 - arrayOfFloat[k];
/* 520 */             f6 = f3 * f8;
/*     */           }
/* 522 */           int m = arrayOfInt[k];
/* 523 */           boolean bool = Character.isWhitespace(str1.charAt(m));
/* 524 */           if ((i >= m) && (bool))
/*     */           {
/* 529 */             i++;
/* 530 */             f1 += f4;
/*     */           }
/* 533 */           else if ((localObject1 != null) && (f1 + f4 < ((BaseBounds)localObject1).getMinY()))
/*     */           {
/* 535 */             f1 += f4;
/* 536 */             i = m + 1;
/*     */           }
/*     */           else
/*     */           {
/* 544 */             int n = bool ? m : m + 1;
/* 545 */             String str2 = str1.substring(i, n);
/* 546 */             float f9 = this.helper.getX();
/* 547 */             if (((localTextAttributes.hasTabs) && (str2.indexOf(9) != -1)) || (localTextAttributes.textAlignment == 3))
/*     */             {
/* 550 */               int i1 = 0;
/*     */ 
/* 552 */               if ((str1.charAt(arrayOfInt[k]) == '\n') || (k == arrayOfInt.length - 1))
/*     */               {
/* 555 */                 i1 = 1;
/*     */               }
/*     */               float f2;
/* 561 */               if ((localTextAttributes.textAlignment == 3) && (j != 0) && (i1 == 0))
/*     */               {
/* 564 */                 f2 = tabJustifyExpandedDrawstring(paramGraphics, str2, (FontStrike)localObject2, f9 + f6, f1, f7 - arrayOfFloat[k], i);
/*     */ 
/* 568 */                 decorate(paramGraphics, (FontStrike)localObject2, f9 + f6, f1, f2);
/*     */               } else {
/* 570 */                 f2 = tabExpandedDrawstring(paramGraphics, str2, (FontStrike)localObject2, f9 + f6, f1, i);
/*     */ 
/* 572 */                 decorate(paramGraphics, (FontStrike)localObject2, f9 + f6, f1, f2);
/*     */               }
/*     */             } else {
/* 575 */               drawString(paramGraphics, str2, (FontStrike)localObject2, f9 + f6, f1, i);
/* 576 */               decorate(paramGraphics, (FontStrike)localObject2, f9 + f6, f1, arrayOfFloat[k]);
/*     */             }
/* 578 */             f1 += f4;
/* 579 */             i = m + 1;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 586 */     else if ((this.mode != PGShape.Mode.STROKE) && ((((FontStrike)localObject2).drawAsShapes()) || (this.drawingEffect)))
/*     */     {
/* 588 */       paramGraphics.setPaint(this.fillPaint);
/* 589 */       paramGraphics.fill(getShape(true));
/*     */     }
/*     */ 
/* 594 */     if (this.mode != PGShape.Mode.FILL) {
/* 595 */       paramGraphics.setPaint(this.drawPaint);
/* 596 */       paramGraphics.setStroke(this.drawStroke);
/*     */ 
/* 598 */       paramGraphics.draw(getShape(true));
/*     */     }
/* 600 */     paramGraphics.setNodeBounds(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGText
 * JD-Core Version:    0.6.2
 */