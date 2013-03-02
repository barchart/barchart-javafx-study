/*    */ package com.sun.javafx.scene.layout.region;
/*    */ 
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import com.sun.javafx.css.StyleableProperty;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javafx.geometry.Insets;
/*    */ import javafx.scene.paint.Paint;
/*    */ 
/*    */ public final class BackgroundFillConverter extends StyleConverter<ParsedValue[], List<BackgroundFill>>
/*    */ {
/*    */   public static BackgroundFillConverter getInstance()
/*    */   {
/* 46 */     return Holder.BACKGROUND_FILLS_CONVERTER;
/*    */   }
/*    */ 
/*    */   public List<BackgroundFill> convert(Map<StyleableProperty, Object> paramMap)
/*    */   {
/* 55 */     Paint[] arrayOfPaint = null;
/* 56 */     Insets[] arrayOfInsets1 = null;
/* 57 */     Insets[] arrayOfInsets2 = null;
/* 58 */     List localList = BackgroundFill.impl_CSS_STYLEABLES();
/* 59 */     for (int i = 0; i < localList.size(); i++) {
/* 60 */       localObject1 = (StyleableProperty)localList.get(i);
/* 61 */       Object localObject2 = paramMap.get(localObject1);
/* 62 */       if (localObject2 != null)
/*    */       {
/* 65 */         if ("-fx-background-color".equals(((StyleableProperty)localObject1).getProperty()))
/* 66 */           arrayOfPaint = (Paint[])localObject2;
/* 67 */         else if ("-fx-background-radius".equals(((StyleableProperty)localObject1).getProperty()))
/* 68 */           arrayOfInsets1 = (Insets[])localObject2;
/* 69 */         else if ("-fx-background-insets".equals(((StyleableProperty)localObject1).getProperty()))
/* 70 */           arrayOfInsets2 = (Insets[])localObject2;
/*    */       }
/*    */     }
/* 73 */     i = arrayOfPaint != null ? arrayOfPaint.length : 0;
/* 74 */     Object localObject1 = new ArrayList();
/* 75 */     for (int j = 0; j < i; j++) {
/* 76 */       Insets localInsets1 = arrayOfInsets1 != null ? arrayOfInsets1[java.lang.Math.min(j, arrayOfInsets1.length - 1)] : Insets.EMPTY;
/* 77 */       Insets localInsets2 = arrayOfInsets2 != null ? arrayOfInsets2[java.lang.Math.min(j, arrayOfInsets2.length - 1)] : Insets.EMPTY;
/* 78 */       BackgroundFill localBackgroundFill = new BackgroundFill(arrayOfPaint[j], localInsets1.getTop(), localInsets1.getRight(), localInsets1.getBottom(), localInsets1.getLeft(), localInsets2);
/* 79 */       ((List)localObject1).add(localBackgroundFill);
/*    */     }
/* 81 */     return localObject1;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 86 */     return "BackgroundFillsType";
/*    */   }
/*    */ 
/*    */   public static class Holder
/*    */   {
/* 41 */     private static final BackgroundFillConverter BACKGROUND_FILLS_CONVERTER = new BackgroundFillConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.layout.region.BackgroundFillConverter
 * JD-Core Version:    0.6.2
 */