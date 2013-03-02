/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import com.sun.javafx.css.converters.BooleanConverter;
/*     */ import com.sun.javafx.css.converters.ColorConverter;
/*     */ import com.sun.javafx.css.converters.CursorConverter;
/*     */ import com.sun.javafx.css.converters.EffectConverter;
/*     */ import com.sun.javafx.css.converters.EffectConverter.DropShadowConverter;
/*     */ import com.sun.javafx.css.converters.EffectConverter.InnerShadowConverter;
/*     */ import com.sun.javafx.css.converters.EnumConverter;
/*     */ import com.sun.javafx.css.converters.FontConverter;
/*     */ import com.sun.javafx.css.converters.FontConverter.SizeConverter;
/*     */ import com.sun.javafx.css.converters.FontConverter.StyleConverter;
/*     */ import com.sun.javafx.css.converters.FontConverter.WeightConverter;
/*     */ import com.sun.javafx.css.converters.InsetsConverter;
/*     */ import com.sun.javafx.css.converters.InsetsConverter.SequenceConverter;
/*     */ import com.sun.javafx.css.converters.PaintConverter;
/*     */ import com.sun.javafx.css.converters.PaintConverter.LinearGradientConverter;
/*     */ import com.sun.javafx.css.converters.PaintConverter.RadialGradientConverter;
/*     */ import com.sun.javafx.css.converters.PaintConverter.SequenceConverter;
/*     */ import com.sun.javafx.css.converters.SizeConverter;
/*     */ import com.sun.javafx.css.converters.SizeConverter.SequenceConverter;
/*     */ import com.sun.javafx.css.converters.StringConverter;
/*     */ import com.sun.javafx.css.converters.StringConverter.SequenceConverter;
/*     */ import com.sun.javafx.css.converters.URLConverter;
/*     */ import com.sun.javafx.css.converters.URLConverter.SequenceConverter;
/*     */ import com.sun.javafx.css.parser.DeriveColorConverter;
/*     */ import com.sun.javafx.css.parser.DeriveSizeConverter;
/*     */ import com.sun.javafx.css.parser.LadderConverter;
/*     */ import com.sun.javafx.css.parser.StopConverter;
/*     */ import com.sun.javafx.scene.layout.region.BackgroundFillConverter;
/*     */ import com.sun.javafx.scene.layout.region.BackgroundImage.BackgroundPositionConverter;
/*     */ import com.sun.javafx.scene.layout.region.BackgroundImage.BackgroundRepeatConverter;
/*     */ import com.sun.javafx.scene.layout.region.BackgroundImage.BackgroundSizeConverter;
/*     */ import com.sun.javafx.scene.layout.region.BackgroundImage.LayeredBackgroundPositionConverter;
/*     */ import com.sun.javafx.scene.layout.region.BackgroundImage.LayeredBackgroundSizeConverter;
/*     */ import com.sun.javafx.scene.layout.region.BackgroundImageConverter;
/*     */ import com.sun.javafx.scene.layout.region.BorderImage.RepeatConverter;
/*     */ import com.sun.javafx.scene.layout.region.BorderImage.SliceConverter;
/*     */ import com.sun.javafx.scene.layout.region.BorderImage.SliceSequenceConverter;
/*     */ import com.sun.javafx.scene.layout.region.BorderImageConverter;
/*     */ import com.sun.javafx.scene.layout.region.Margins.Converter;
/*     */ import com.sun.javafx.scene.layout.region.Margins.SequenceConverter;
/*     */ import com.sun.javafx.scene.layout.region.StrokeBorder.BorderPaintConverter;
/*     */ import com.sun.javafx.scene.layout.region.StrokeBorder.BorderStyleConverter;
/*     */ import com.sun.javafx.scene.layout.region.StrokeBorder.BorderStyleSequenceConverter;
/*     */ import com.sun.javafx.scene.layout.region.StrokeBorder.LayeredBorderPaintConverter;
/*     */ import com.sun.javafx.scene.layout.region.StrokeBorder.LayeredBorderStyleConverter;
/*     */ import com.sun.javafx.scene.layout.region.StrokeBorderConverter;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javafx.scene.text.Font;
/*     */ 
/*     */ public class StyleConverter<F, T>
/*     */ {
/*     */   static Map<String, StyleConverter> tmap;
/*     */ 
/*     */   public T convert(ParsedValue<F, T> paramParsedValue, Font paramFont)
/*     */   {
/*  60 */     return paramParsedValue.getValue();
/*     */   }
/*     */ 
/*     */   public T convert(Map<StyleableProperty, Object> paramMap)
/*     */   {
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */   public static StyleConverter getInstance()
/*     */   {
/*  77 */     return Holder.CONVERTER;
/*     */   }
/*     */ 
/*     */   public void writeBinary(DataOutputStream paramDataOutputStream, StringStore paramStringStore)
/*     */     throws IOException
/*     */   {
/*  87 */     String str = getClass().getName();
/*  88 */     int i = paramStringStore.addString(str);
/*  89 */     paramDataOutputStream.writeShort(i);
/*     */   }
/*     */ 
/*     */   public static StyleConverter readBinary(DataInputStream paramDataInputStream, String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/*  98 */     int i = paramDataInputStream.readShort();
/*  99 */     String str = paramArrayOfString[i];
/*     */ 
/* 102 */     if ((tmap == null) || (!tmap.containsKey(str))) {
/* 103 */       Object localObject = null;
/*     */       try {
/* 105 */         Class localClass = Class.forName(str);
/*     */ 
/* 107 */         if (EnumConverter.class.isAssignableFrom(localClass))
/* 108 */           localObject = new EnumConverter(paramDataInputStream, paramArrayOfString);
/*     */         else
/* 110 */           localObject = getInstance(localClass);
/*     */       }
/*     */       catch (ClassNotFoundException localClassNotFoundException)
/*     */       {
/* 114 */         System.err.println(localClassNotFoundException.toString());
/*     */       }
/* 116 */       if (localObject == null) {
/* 117 */         System.err.println("could not deserialize " + str);
/*     */       }
/* 119 */       if (tmap == null) tmap = new HashMap();
/* 120 */       tmap.put(str, localObject);
/* 121 */       return localObject;
/*     */     }
/* 123 */     return (StyleConverter)tmap.get(str);
/*     */   }
/*     */ 
/*     */   static StyleConverter getInstance(Class paramClass)
/*     */   {
/* 130 */     Object localObject = null;
/*     */ 
/* 132 */     if (StyleConverter.class.equals(paramClass)) {
/* 133 */       localObject = getInstance();
/*     */     }
/* 135 */     else if (BooleanConverter.class.equals(paramClass)) {
/* 136 */       localObject = BooleanConverter.getInstance();
/*     */     }
/* 138 */     else if (ColorConverter.class.equals(paramClass)) {
/* 139 */       localObject = ColorConverter.getInstance();
/*     */     }
/* 141 */     else if (CursorConverter.class.equals(paramClass)) {
/* 142 */       localObject = CursorConverter.getInstance();
/*     */     }
/* 144 */     else if (EffectConverter.class.equals(paramClass))
/* 145 */       localObject = EffectConverter.getInstance();
/* 146 */     else if (EffectConverter.DropShadowConverter.class.equals(paramClass))
/* 147 */       localObject = EffectConverter.DropShadowConverter.getInstance();
/* 148 */     else if (EffectConverter.InnerShadowConverter.class.equals(paramClass)) {
/* 149 */       localObject = EffectConverter.InnerShadowConverter.getInstance();
/*     */     }
/* 153 */     else if (FontConverter.class.equals(paramClass))
/* 154 */       localObject = FontConverter.getInstance();
/* 155 */     else if (FontConverter.StyleConverter.class.equals(paramClass))
/* 156 */       localObject = FontConverter.StyleConverter.getInstance();
/* 157 */     else if (FontConverter.WeightConverter.class.equals(paramClass))
/* 158 */       localObject = FontConverter.WeightConverter.getInstance();
/* 159 */     else if (FontConverter.SizeConverter.class.equals(paramClass)) {
/* 160 */       localObject = FontConverter.SizeConverter.getInstance();
/*     */     }
/* 162 */     else if (InsetsConverter.class.equals(paramClass))
/* 163 */       localObject = InsetsConverter.getInstance();
/* 164 */     else if (InsetsConverter.SequenceConverter.class.equals(paramClass)) {
/* 165 */       localObject = InsetsConverter.SequenceConverter.getInstance();
/*     */     }
/* 167 */     else if (PaintConverter.class.equals(paramClass))
/* 168 */       localObject = PaintConverter.getInstance();
/* 169 */     else if (PaintConverter.SequenceConverter.class.equals(paramClass))
/* 170 */       localObject = PaintConverter.SequenceConverter.getInstance();
/* 171 */     else if (PaintConverter.LinearGradientConverter.class.equals(paramClass))
/* 172 */       localObject = PaintConverter.LinearGradientConverter.getInstance();
/* 173 */     else if (PaintConverter.RadialGradientConverter.class.equals(paramClass)) {
/* 174 */       localObject = PaintConverter.RadialGradientConverter.getInstance();
/*     */     }
/* 176 */     else if (SizeConverter.class.equals(paramClass))
/* 177 */       localObject = SizeConverter.getInstance();
/* 178 */     else if (SizeConverter.SequenceConverter.class.equals(paramClass)) {
/* 179 */       localObject = SizeConverter.SequenceConverter.getInstance();
/*     */     }
/* 181 */     else if (StringConverter.class.equals(paramClass))
/* 182 */       localObject = StringConverter.getInstance();
/* 183 */     else if (StringConverter.SequenceConverter.class.equals(paramClass)) {
/* 184 */       localObject = StringConverter.SequenceConverter.getInstance();
/*     */     }
/* 186 */     else if (URLConverter.class.equals(paramClass))
/* 187 */       localObject = URLConverter.getInstance();
/* 188 */     else if (URLConverter.SequenceConverter.class.equals(paramClass)) {
/* 189 */       localObject = URLConverter.SequenceConverter.getInstance();
/*     */     }
/* 192 */     else if (BackgroundFillConverter.class.equals(paramClass)) {
/* 193 */       localObject = BackgroundFillConverter.getInstance();
/*     */     }
/* 195 */     else if (BackgroundImageConverter.class.equals(paramClass))
/* 196 */       localObject = BackgroundImageConverter.getInstance();
/* 197 */     else if (BackgroundImage.BackgroundPositionConverter.class.equals(paramClass))
/* 198 */       localObject = BackgroundImage.BackgroundPositionConverter.getInstance();
/* 199 */     else if (BackgroundImage.BackgroundRepeatConverter.class.equals(paramClass))
/* 200 */       localObject = BackgroundImage.BackgroundRepeatConverter.getInstance();
/* 201 */     else if (BackgroundImage.BackgroundSizeConverter.class.equals(paramClass))
/* 202 */       localObject = BackgroundImage.BackgroundSizeConverter.getInstance();
/* 203 */     else if (BackgroundImage.LayeredBackgroundPositionConverter.class.equals(paramClass))
/* 204 */       localObject = BackgroundImage.LayeredBackgroundPositionConverter.getInstance();
/* 205 */     else if (BackgroundImage.LayeredBackgroundSizeConverter.class.equals(paramClass)) {
/* 206 */       localObject = BackgroundImage.LayeredBackgroundSizeConverter.getInstance();
/*     */     }
/* 208 */     else if (BorderImageConverter.class.equals(paramClass))
/* 209 */       localObject = BorderImageConverter.getInstance();
/* 210 */     else if (BorderImage.RepeatConverter.class.equals(paramClass))
/* 211 */       localObject = BorderImage.RepeatConverter.getInstance();
/* 212 */     else if (BorderImage.SliceConverter.class.equals(paramClass))
/* 213 */       localObject = BorderImage.SliceConverter.getInstance();
/* 214 */     else if (BorderImage.SliceSequenceConverter.class.equals(paramClass)) {
/* 215 */       localObject = BorderImage.SliceSequenceConverter.getInstance();
/*     */     }
/* 217 */     else if (StrokeBorderConverter.class.equals(paramClass))
/* 218 */       localObject = StrokeBorderConverter.getInstance();
/* 219 */     else if (StrokeBorder.BorderPaintConverter.class.equals(paramClass))
/* 220 */       localObject = StrokeBorder.BorderPaintConverter.getInstance();
/* 221 */     else if (StrokeBorder.BorderStyleConverter.class.equals(paramClass))
/* 222 */       localObject = StrokeBorder.BorderStyleConverter.getInstance();
/* 223 */     else if (StrokeBorder.BorderStyleSequenceConverter.class.equals(paramClass))
/* 224 */       localObject = StrokeBorder.BorderStyleSequenceConverter.getInstance();
/* 225 */     else if (StrokeBorder.LayeredBorderPaintConverter.class.equals(paramClass))
/* 226 */       localObject = StrokeBorder.LayeredBorderPaintConverter.getInstance();
/* 227 */     else if (StrokeBorder.LayeredBorderStyleConverter.class.equals(paramClass)) {
/* 228 */       localObject = StrokeBorder.LayeredBorderStyleConverter.getInstance();
/*     */     }
/* 230 */     else if (Margins.Converter.class.equals(paramClass))
/* 231 */       localObject = Margins.Converter.getInstance();
/* 232 */     else if (Margins.SequenceConverter.class.equals(paramClass)) {
/* 233 */       localObject = Margins.SequenceConverter.getInstance();
/*     */     }
/* 236 */     else if (DeriveColorConverter.class.equals(paramClass))
/* 237 */       localObject = DeriveColorConverter.getInstance();
/* 238 */     else if (DeriveSizeConverter.class.equals(paramClass))
/* 239 */       localObject = DeriveSizeConverter.getInstance();
/* 240 */     else if (LadderConverter.class.equals(paramClass))
/* 241 */       localObject = LadderConverter.getInstance();
/* 242 */     else if (StopConverter.class.equals(paramClass)) {
/* 243 */       localObject = StopConverter.getInstance();
/*     */     }
/*     */ 
/* 246 */     return localObject;
/*     */   }
/*     */ 
/*     */   private static class Holder
/*     */   {
/*  72 */     private static StyleConverter CONVERTER = new StyleConverter();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StyleConverter
 * JD-Core Version:    0.6.2
 */