/*     */ package com.sun.javafx.scene.layout.region;
/*     */ 
/*     */ import com.sun.javafx.css.ParsedValue;
/*     */ import com.sun.javafx.css.StyleConverter;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.scene.image.Image;
/*     */ 
/*     */ public final class BackgroundImageConverter extends StyleConverter<ParsedValue[], List<BackgroundImage>>
/*     */ {
/*     */   public static BackgroundImageConverter getInstance()
/*     */   {
/*  45 */     return Holder.BACKGROUND_IMAGE_CONVERTER;
/*     */   }
/*     */ 
/*     */   public List<BackgroundImage> convert(Map<StyleableProperty, Object> paramMap)
/*     */   {
/*  54 */     String[] arrayOfString = null;
/*  55 */     BackgroundImage.BackgroundRepeat[] arrayOfBackgroundRepeat = null;
/*  56 */     BackgroundImage.BackgroundPosition[] arrayOfBackgroundPosition = null;
/*  57 */     BackgroundImage.BackgroundSize[] arrayOfBackgroundSize = null;
/*  58 */     List localList = BackgroundImage.impl_CSS_STYLEABLES();
/*  59 */     for (int i = 0; i < localList.size(); i++) {
/*  60 */       StyleableProperty localStyleableProperty = (StyleableProperty)localList.get(i);
/*  61 */       Object localObject1 = paramMap.get(localStyleableProperty);
/*  62 */       if (localObject1 != null)
/*     */       {
/*  65 */         if ("-fx-background-image".equals(localStyleableProperty.getProperty()))
/*  66 */           arrayOfString = (String[])localObject1;
/*  67 */         else if ("-fx-background-repeat".equals(localStyleableProperty.getProperty()))
/*  68 */           arrayOfBackgroundRepeat = (BackgroundImage.BackgroundRepeat[])localObject1;
/*  69 */         else if ("-fx-background-position".equals(localStyleableProperty.getProperty()))
/*  70 */           arrayOfBackgroundPosition = (BackgroundImage.BackgroundPosition[])localObject1;
/*  71 */         else if ("-fx-background-size".equals(localStyleableProperty.getProperty()))
/*  72 */           arrayOfBackgroundSize = (BackgroundImage.BackgroundSize[])localObject1;
/*     */       }
/*     */     }
/*  75 */     ArrayList localArrayList = new ArrayList();
/*  76 */     int j = arrayOfString != null ? arrayOfString.length : 0;
/*  77 */     for (int k = 0; k < j; k++)
/*  78 */       if (arrayOfString[k] != null) {
/*  79 */         Object localObject2 = arrayOfBackgroundRepeat != null ? arrayOfBackgroundRepeat[java.lang.Math.min(k, arrayOfBackgroundRepeat.length - 1)] : null;
/*  80 */         Object localObject3 = arrayOfBackgroundPosition != null ? arrayOfBackgroundPosition[java.lang.Math.min(k, arrayOfBackgroundPosition.length - 1)] : null;
/*  81 */         Object localObject4 = arrayOfBackgroundSize != null ? arrayOfBackgroundSize[java.lang.Math.min(k, arrayOfBackgroundSize.length - 1)] : null;
/*  82 */         BackgroundImage.Builder localBuilder = new BackgroundImage.Builder();
/*  83 */         localBuilder.setImage(new Image(arrayOfString[k]));
/*  84 */         if (localObject2 != null) {
/*  85 */           localBuilder.setRepeatX(localObject2.getRepeatX()).setRepeatY(localObject2.getRepeatY());
/*     */         }
/*  87 */         if (localObject3 != null) {
/*  88 */           localBuilder.setTop(localObject3.getTop()).setRight(localObject3.getRight()).setBottom(localObject3.getBottom()).setLeft(localObject3.getLeft()).setProportionalHPos(localObject3.isProportionalHPos()).setProportionalVPos(localObject3.isProportionalVPos());
/*     */         }
/*     */ 
/*  95 */         if (localObject4 != null) {
/*  96 */           localBuilder.setWidth(localObject4.getWidth()).setHeight(localObject4.getHeight()).setContain(localObject4.isContain()).setCover(localObject4.isCover()).setProportionalWidth(localObject4.isProportionalWidth()).setProportionalHeight(localObject4.isProportionalHeight());
/*     */         }
/*     */ 
/* 103 */         localArrayList.add(localBuilder.build());
/*     */       }
/* 105 */     return localArrayList;
/*     */   }
/*     */ 
/*     */   private static class Holder
/*     */   {
/*  40 */     private static final BackgroundImageConverter BACKGROUND_IMAGE_CONVERTER = new BackgroundImageConverter(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.layout.region.BackgroundImageConverter
 * JD-Core Version:    0.6.2
 */