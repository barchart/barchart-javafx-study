/*      */ package com.sun.javafx.sg.prism;
/*      */ 
/*      */ import com.sun.javafx.font.FontResource;
/*      */ import com.sun.javafx.font.FontStrike;
/*      */ import com.sun.javafx.font.FontStrike.Metrics;
/*      */ import com.sun.javafx.font.PGFont;
/*      */ import com.sun.javafx.geom.BaseBounds;
/*      */ import com.sun.javafx.geom.Line2D;
/*      */ import com.sun.javafx.geom.Path2D;
/*      */ import com.sun.javafx.geom.RectBounds;
/*      */ import com.sun.javafx.geom.RoundRectangle2D;
/*      */ import com.sun.javafx.geom.Shape;
/*      */ import com.sun.javafx.geom.TransformedShape;
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import com.sun.javafx.geom.transform.Translate2D;
/*      */ import com.sun.javafx.sg.PGShape.Mode;
/*      */ import com.sun.javafx.sg.PGShape.StrokeLineCap;
/*      */ import com.sun.javafx.sg.PGShape.StrokeLineJoin;
/*      */ import com.sun.javafx.sg.PGShape.StrokeType;
/*      */ import com.sun.javafx.sg.PGTextHelper;
/*      */ import com.sun.prism.BasicStroke;
/*      */ import com.sun.prism.paint.Paint;
/*      */ import com.sun.t2k.CharToGlyphMapper;
/*      */ import javafx.scene.text.Font;
/*      */ 
/*      */ public class NGTextHelper
/*      */   implements PGTextHelper
/*      */ {
/*      */   static final int ALIGN_LEFT = 0;
/*      */   static final int ALIGN_CENTER = 1;
/*      */   static final int ALIGN_RIGHT = 2;
/*      */   static final int ALIGN_JUSTIFY = 3;
/*      */   private static final int TOP = 0;
/*      */   private static final int CENTER = 1;
/*      */   private static final int BASELINE = 2;
/*      */   private static final int BOTTOM = 3;
/*   43 */   static final BaseTransform IDENT = BaseTransform.IDENTITY_TRANSFORM;
/*      */   public static final boolean hinting = false;
/*      */   private float x;
/*      */   private float y;
/*      */   private boolean locationIsChanged;
/*   51 */   private static final PGFont defaultFont = (PGFont)Font.getDefault().impl_getNativeFont();
/*      */ 
/*   53 */   private PGFont font = defaultFont;
/*      */   private boolean fontChanged;
/*   63 */   private short fontSmoothingType = 0;
/*      */ 
/*   65 */   private FontStrike fontStrike = null;
/*   66 */   private FontStrike identityStrike = null;
/*   67 */   private double[] strikeMat = new double[4];
/*   68 */   private String text = "";
/*      */ 
/*   71 */   int textOrigin = 2;
/*      */   private boolean valid;
/*      */   private Selection selection;
/*      */   private BaseTransform cumulativeTransform;
/*      */   private PGShape.Mode mode;
/*      */   private boolean doStroke;
/*      */   private PGShape.StrokeType strokeType;
/*      */   private BasicStroke drawStroke;
/*      */   private TextAttributes attributes;
/*  531 */   private float maxLineAdvance = 0.0F;
/*      */   private static final int LOGICAL_BOUNDS = 0;
/*      */   private static final int VISUAL_BOUNDS = 1;
/*      */   private static final int LOGICAL_LAYOUT_BOUNDS = 2;
/* 1099 */   int textBoundsType = 0;
/*      */ 
/* 1163 */   private RectBounds visualBounds = null;
/* 1164 */   private RectBounds logicalBounds = null;
/* 1165 */   private float yAdjCached = 0.0F;
/* 1166 */   private boolean yAdjValid = false;
/*      */ 
/* 1715 */   private static double EPSILON = 0.01D;
/*      */ 
/* 1881 */   private boolean geometryIsChanged = false;
/*      */ 
/* 2435 */   private static final String LS = System.getProperty("line.separator");
/*      */ 
/*      */   Selection getSelection()
/*      */   {
/*   87 */     return this.selection;
/*      */   }
/*      */ 
/*      */   public void setCumulativeTransform(BaseTransform paramBaseTransform)
/*      */   {
/*  101 */     this.cumulativeTransform = paramBaseTransform;
/*      */   }
/*      */ 
/*      */   BaseTransform getCumulativeTransform() {
/*  105 */     if (this.cumulativeTransform == null) {
/*  106 */       return IDENT;
/*      */     }
/*  108 */     return this.cumulativeTransform;
/*      */   }
/*      */ 
/*      */   public void setMode(PGShape.Mode paramMode)
/*      */   {
/*  113 */     this.mode = paramMode;
/*      */   }
/*      */ 
/*      */   private PGShape.Mode getMode() {
/*  117 */     if (this.mode == null) {
/*  118 */       return PGShape.Mode.EMPTY;
/*      */     }
/*  120 */     return this.mode;
/*      */   }
/*      */ 
/*      */   public void setStroke(boolean paramBoolean)
/*      */   {
/*  125 */     this.doStroke = paramBoolean;
/*      */   }
/*      */ 
/*      */   private boolean hasStroke() {
/*  129 */     return this.doStroke;
/*      */   }
/*      */ 
/*      */   private PGShape.StrokeType getStrokeType() {
/*  133 */     return this.strokeType;
/*      */   }
/*      */ 
/*      */   public void setStrokeParameters(PGShape.StrokeType paramStrokeType, float[] paramArrayOfFloat, float paramFloat1, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat2, float paramFloat3)
/*      */   {
/*  144 */     this.strokeType = paramStrokeType;
/*      */ 
/*  146 */     this.drawStroke = NGShape.createDrawStroke(paramFloat3, paramStrokeType, paramStrokeLineCap, paramStrokeLineJoin, paramFloat2, paramArrayOfFloat, paramFloat1);
/*      */ 
/*  154 */     geometryChangedTextValid();
/*      */   }
/*      */ 
/*      */   private BasicStroke getStroke() {
/*  158 */     return this.drawStroke;
/*      */   }
/*      */ 
/*      */   private static int[] syncIntArr(int[] paramArrayOfInt1, int[] paramArrayOfInt2) {
/*  162 */     if (paramArrayOfInt1 == null) {
/*  163 */       return null;
/*      */     }
/*  165 */     int i = paramArrayOfInt1.length;
/*  166 */     if ((paramArrayOfInt2 == null) || (paramArrayOfInt2.length != paramArrayOfInt1.length)) {
/*  167 */       return (int[])paramArrayOfInt1.clone();
/*      */     }
/*  169 */     System.arraycopy(paramArrayOfInt1, 0, paramArrayOfInt2, 0, paramArrayOfInt1.length);
/*  170 */     return paramArrayOfInt2;
/*      */   }
/*      */ 
/*      */   private static float[] syncFloatArr(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*      */   {
/*  176 */     if (paramArrayOfFloat1 == null) {
/*  177 */       return null;
/*      */     }
/*  179 */     int i = paramArrayOfFloat1.length;
/*  180 */     if ((paramArrayOfFloat2 == null) || (paramArrayOfFloat2.length != paramArrayOfFloat1.length)) {
/*  181 */       return (float[])paramArrayOfFloat1.clone();
/*      */     }
/*  183 */     System.arraycopy(paramArrayOfFloat1, 0, paramArrayOfFloat2, 0, paramArrayOfFloat1.length);
/*  184 */     return paramArrayOfFloat2;
/*      */   }
/*      */ 
/*      */   public void sync(NGTextHelper paramNGTextHelper)
/*      */   {
/*  204 */     paramNGTextHelper.x = this.x;
/*  205 */     paramNGTextHelper.y = this.y;
/*  206 */     paramNGTextHelper.fontSmoothingType = this.fontSmoothingType;
/*  207 */     paramNGTextHelper.font = this.font;
/*  208 */     paramNGTextHelper.text = this.text;
/*  209 */     paramNGTextHelper.textOrigin = this.textOrigin;
/*  210 */     paramNGTextHelper.textBoundsType = this.textBoundsType;
/*  211 */     if (this.selection == null) {
/*  212 */       paramNGTextHelper.selection = null;
/*      */     } else {
/*  214 */       if (paramNGTextHelper.selection == null) {
/*  215 */         paramNGTextHelper.selection = new Selection();
/*      */       }
/*  217 */       paramNGTextHelper.selection.start = this.selection.start;
/*  218 */       paramNGTextHelper.selection.end = this.selection.end;
/*  219 */       paramNGTextHelper.selection.drawPaint = this.selection.drawPaint;
/*  220 */       paramNGTextHelper.selection.fillPaint = this.selection.fillPaint;
/*      */ 
/*  223 */       paramNGTextHelper.selection.shape = this.selection.shape;
/*      */     }
/*      */ 
/*  227 */     paramNGTextHelper.cumulativeTransform = this.cumulativeTransform;
/*  228 */     paramNGTextHelper.mode = this.mode;
/*  229 */     paramNGTextHelper.doStroke = this.doStroke;
/*  230 */     paramNGTextHelper.strokeType = this.strokeType;
/*  231 */     paramNGTextHelper.drawStroke = this.drawStroke;
/*      */ 
/*  234 */     paramNGTextHelper.locationIsChanged = this.locationIsChanged;
/*  235 */     paramNGTextHelper.geometryIsChanged = this.geometryIsChanged;
/*  236 */     paramNGTextHelper.valid = this.valid;
/*  237 */     paramNGTextHelper.identityStrike = this.identityStrike;
/*      */ 
/*  240 */     if ((this.fontStrike != null) || (this.fontChanged)) {
/*  241 */       paramNGTextHelper.fontStrike = this.fontStrike;
/*  242 */       System.arraycopy(this.strikeMat, 0, paramNGTextHelper.strikeMat, 0, 4);
/*      */     }
/*  244 */     this.fontChanged = false;
/*  245 */     paramNGTextHelper.maxLineAdvance = this.maxLineAdvance;
/*  246 */     paramNGTextHelper.visualBounds = this.visualBounds;
/*  247 */     paramNGTextHelper.logicalBounds = this.logicalBounds;
/*  248 */     paramNGTextHelper.yAdjCached = this.yAdjCached;
/*  249 */     paramNGTextHelper.yAdjValid = this.yAdjValid;
/*      */ 
/*  251 */     if (this.attributes == null) {
/*  252 */       paramNGTextHelper.attributes = null;
/*      */     } else {
/*  254 */       if (paramNGTextHelper.attributes == null) {
/*  255 */         paramNGTextHelper.attributes = new TextAttributes();
/*      */       }
/*  257 */       TextAttributes localTextAttributes = paramNGTextHelper.attributes;
/*      */ 
/*  260 */       localTextAttributes.textAlignment = this.attributes.textAlignment;
/*  261 */       localTextAttributes.underline = this.attributes.underline;
/*  262 */       localTextAttributes.strikethrough = this.attributes.strikethrough;
/*  263 */       localTextAttributes.wrappingWidth = this.attributes.wrappingWidth;
/*      */ 
/*  266 */       localTextAttributes.wrappingWidthSet = this.attributes.wrappingWidthSet;
/*      */ 
/*  268 */       localTextAttributes.overrideWrappingWidth = this.attributes.overrideWrappingWidth;
/*  269 */       localTextAttributes.hasTabs = this.attributes.hasTabs;
/*  270 */       localTextAttributes.cachedShape = this.attributes.cachedShape;
/*      */ 
/*  275 */       localTextAttributes.lineAdvances = syncFloatArr(this.attributes.lineAdvances, localTextAttributes.lineAdvances);
/*      */ 
/*  278 */       localTextAttributes.newLineIndices = syncIntArr(this.attributes.newLineIndices, localTextAttributes.newLineIndices);
/*      */ 
/*  281 */       localTextAttributes.lsb = syncFloatArr(this.attributes.lsb, localTextAttributes.lsb);
/*      */ 
/*  283 */       localTextAttributes.rsb = syncFloatArr(this.attributes.rsb, localTextAttributes.rsb);
/*      */     }
/*      */   }
/*      */ 
/*      */   TextAttributes getAttributes()
/*      */   {
/*  524 */     return this.attributes;
/*      */   }
/*      */ 
/*      */   boolean isEmpty() {
/*  528 */     return this.text.isEmpty();
/*      */   }
/*      */ 
/*      */   float getMaxLineAdvance()
/*      */   {
/*  534 */     return this.maxLineAdvance;
/*      */   }
/*      */ 
/*      */   public Shape getShape()
/*      */   {
/*  539 */     return getShape(true);
/*      */   }
/*      */ 
/*      */   Shape getShape(boolean paramBoolean)
/*      */   {
/*  551 */     if (this.text.isEmpty()) {
/*  552 */       return new Path2D();
/*      */     }
/*  554 */     validateText();
/*      */ 
/*  556 */     if ((this.attributes != null) && (this.attributes.cachedShape != null)) {
/*  557 */       if (paramBoolean) {
/*  558 */         return translateShape(this.attributes.cachedShape);
/*      */       }
/*  560 */       return this.attributes.cachedShape;
/*      */     }
/*      */ 
/*  564 */     BaseTransform localBaseTransform1 = IDENT;
/*  565 */     if (this.attributes == null) {
/*  566 */       this.attributes = new TextAttributes();
/*      */     }
/*  568 */     FontStrike localFontStrike = getStrike(localBaseTransform1);
/*  569 */     if (simpleSingleLine()) {
/*  570 */       Shape localShape1 = localFontStrike.getOutline(this.text, localBaseTransform1);
/*  571 */       Shape localShape2 = getDecorationShape(this.text, localFontStrike, 0.0F, 0.0F, this.maxLineAdvance);
/*      */ 
/*  573 */       this.attributes.cachedShape = union(localShape1, localShape2);
/*      */     }
/*      */     else
/*      */     {
/*  577 */       float f1 = 0.0F;
/*      */ 
/*  581 */       float f2 = 0.0F;
/*  582 */       if (this.attributes.textAlignment == 1)
/*  583 */         f2 = 0.5F;
/*  584 */       else if (this.attributes.textAlignment == 2) {
/*  585 */         f2 = 1.0F;
/*      */       }
/*  587 */       float f3 = getLogicalWidth();
/*  588 */       int i = f3 > 0.0F ? 1 : 0;
/*  589 */       float f4 = 0.0F;
/*  590 */       Object localObject = null;
/*  591 */       double d = localFontStrike.getMetrics().getLineHeight();
/*      */ 
/*  593 */       int[] arrayOfInt = this.attributes.newLineIndices;
/*  594 */       float[] arrayOfFloat = this.attributes.lineAdvances;
/*  595 */       if (arrayOfInt == null) {
/*  596 */         arrayOfInt = new int[] { this.text.length() - 1 };
/*      */       }
/*  598 */       if (arrayOfFloat == null) {
/*  599 */         arrayOfFloat = new float[] { this.maxLineAdvance };
/*      */       }
/*  601 */       int j = 0;
/*  602 */       for (int k = 0; k < arrayOfInt.length; k++) {
/*  603 */         int m = arrayOfInt[k];
/*  604 */         boolean bool = Character.isWhitespace(this.text.charAt(m));
/*  605 */         if ((j >= m) && (bool))
/*      */         {
/*  607 */           j++;
/*  608 */           localBaseTransform1 = localBaseTransform1.deriveWithTranslation(0.0D, d);
/*      */         }
/*      */         else {
/*  611 */           int n = bool ? m : m + 1;
/*  612 */           String str = this.text.substring(j, n);
/*      */ 
/*  615 */           if (i != 0) {
/*  616 */             f4 = f3 - arrayOfFloat[k];
/*  617 */             f1 = f2 * f4;
/*      */           }
/*      */ 
/*  621 */           Shape localShape3 = null;
/*      */           Path2D localPath2D;
/*  624 */           if ((str.indexOf(9) != -1) || (this.attributes.textAlignment == 3))
/*      */           {
/*  628 */             if ((this.attributes.textAlignment != 3) || (this.text.charAt(arrayOfInt[k]) == '\n') || (arrayOfInt.length == k + 1))
/*      */             {
/*  631 */               if (this.attributes.hasTabs) {
/*  632 */                 localPath2D = getTabExpandedOutline(str, localFontStrike, localBaseTransform1.getMxt(), localBaseTransform1.getMyt());
/*      */               }
/*      */               else
/*      */               {
/*  636 */                 localPath2D = (Path2D)localFontStrike.getOutline(str, localBaseTransform1);
/*      */               }
/*      */             }
/*      */             else {
/*  640 */               localPath2D = new Path2D();
/*  641 */               float f5 = getTabJustifyExpandedOutline(localPath2D, str, localFontStrike, localBaseTransform1.getMxt(), localBaseTransform1.getMyt(), f4);
/*      */ 
/*  652 */               localShape3 = getDecorationShape(str, localFontStrike, (float)localBaseTransform1.getMxt() + f1, (float)localBaseTransform1.getMyt(), f5);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  657 */             localPath2D = (Path2D)localFontStrike.getOutline(str, localBaseTransform1);
/*      */           }
/*      */ 
/*  660 */           BaseTransform localBaseTransform2 = BaseTransform.getTranslateInstance(f1, 0.0D);
/*      */ 
/*  662 */           localPath2D.transform(localBaseTransform2);
/*      */ 
/*  664 */           if (localObject == null)
/*  665 */             localObject = localPath2D;
/*      */           else {
/*  667 */             ((Path2D)localObject).append(localPath2D, false);
/*      */           }
/*      */ 
/*  670 */           if (localShape3 == null)
/*      */           {
/*  672 */             localShape3 = getDecorationShape(str, localFontStrike, (float)localBaseTransform1.getMxt() + f1, (float)localBaseTransform1.getMyt(), arrayOfFloat[k]);
/*      */           }
/*      */ 
/*  678 */           localObject = union((Shape)localObject, localShape3);
/*  679 */           localBaseTransform1 = localBaseTransform1.deriveWithTranslation(0.0D, d);
/*  680 */           j = m + 1;
/*      */         }
/*      */       }
/*  682 */       if (localObject == null) {
/*  683 */         localObject = new Path2D();
/*      */       }
/*  685 */       this.attributes.cachedShape = ((Shape)localObject);
/*      */     }
/*  687 */     if (paramBoolean) {
/*  688 */       return translateShape(this.attributes.cachedShape);
/*      */     }
/*  690 */     return this.attributes.cachedShape;
/*      */   }
/*      */ 
/*      */   private float getTabJustifyExpandedOutline(Path2D paramPath2D, String paramString, FontStrike paramFontStrike, double paramDouble1, double paramDouble2, float paramFloat)
/*      */   {
/*  701 */     char[] arrayOfChar = paramString.toCharArray();
/*  702 */     float f1 = 0.0F;
/*  703 */     float f2 = paramFontStrike.getCharAdvance(' ');
/*  704 */     float f3 = 8.0F * f2;
/*  705 */     float f4 = 0.0F;
/*      */ 
/*  708 */     int i = (this.attributes.textAlignment == 3) && (paramFloat > 0.0F) ? 1 : 0;
/*      */ 
/*  712 */     int j = 0;
/*  713 */     int k = 0;
/*  714 */     FloatList localFloatList = new FloatList();
/*  715 */     IntList localIntList = new IntList();
/*  716 */     float f5 = 0.0F;
/*      */ 
/*  720 */     int m = arrayOfChar.length - 1;
/*  721 */     while ((m > 0) && (arrayOfChar[m] == ' ') && (i != 0))
/*  722 */       m--;
/*      */     Translate2D localTranslate2D;
/*      */     Object localObject2;
/*  725 */     while (j <= m) {
/*  726 */       char c = arrayOfChar[j];
/*  727 */       if (c == '\t') {
/*  728 */         if (j > k)
/*      */         {
/*  730 */           localIntList.index = 0;
/*  731 */           localFloatList.index = 0;
/*  732 */           localTranslate2D = new Translate2D(paramDouble1 + f4, paramDouble2);
/*  733 */           String str1 = new String(arrayOfChar, k, j - k);
/*  734 */           localObject2 = (Path2D)paramFontStrike.getOutline(str1, localTranslate2D);
/*  735 */           if (paramPath2D == null)
/*  736 */             paramPath2D = (Path2D)localObject2;
/*      */           else {
/*  738 */             paramPath2D.append((Shape)localObject2, false);
/*      */           }
/*      */         }
/*  741 */         j++; k = j;
/*      */ 
/*  743 */         float f6 = f5 + f1;
/*  744 */         f4 = ((int)(f6 / f3) + 1) * f3;
/*      */ 
/*  746 */         f5 = f4;
/*  747 */         f1 = 0.0F;
/*      */       }
/*  749 */       else if ((i != 0) && (c == ' '))
/*      */       {
/*  751 */         localIntList.add(++j);
/*  752 */         localFloatList.add(f5 + f2);
/*  753 */         f5 += f2 + f1;
/*  754 */         f1 = 0.0F;
/*      */       } else {
/*  756 */         j++;
/*  757 */         f1 += paramFontStrike.getCharAdvance(c);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  762 */     if (paramPath2D == null) {
/*  763 */       paramPath2D = new Path2D();
/*      */     }
/*  765 */     if (j > k)
/*      */     {
/*      */       Object localObject1;
/*      */       Path2D localPath2D;
/*  767 */       if ((i != 0) && (localIntList.index > 0)) {
/*  768 */         localObject1 = localIntList.getData();
/*  769 */         localObject2 = localFloatList.getData();
/*      */ 
/*  774 */         float f7 = paramFloat / localObject1.length;
/*  775 */         float f8 = 0.0F;
/*      */ 
/*  778 */         int n = 0;
/*  779 */         if (k == 0) {
/*  780 */           str2 = new String(arrayOfChar, k, localObject1[n] - k);
/*  781 */           k = localObject1[(n++)];
/*  782 */           f8 += f7;
/*      */ 
/*  784 */           localTranslate2D = new Translate2D(paramDouble1, paramDouble2);
/*  785 */           localPath2D = (Path2D)paramFontStrike.getOutline(str2, localTranslate2D);
/*  786 */           if (localPath2D != null) {
/*  787 */             paramPath2D.append(localPath2D, false);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  792 */         for (; n < localObject1.length; n++) {
/*  793 */           if (k < localObject1[n]) {
/*  794 */             if ((arrayOfChar[k] == ' ') && (localObject1[n] - k == 1))
/*      */             {
/*  796 */               f8 += f7;
/*      */             } else {
/*  798 */               str2 = new String(arrayOfChar, k, localObject1[n] - k);
/*  799 */               localTranslate2D = new Translate2D(paramDouble1 + localObject2[n] + f8, paramDouble2);
/*  800 */               localPath2D = (Path2D)paramFontStrike.getOutline(str2, localTranslate2D);
/*  801 */               if (localPath2D != null) {
/*  802 */                 paramPath2D.append(localPath2D, false);
/*      */               }
/*  804 */               f8 += f7;
/*      */             }
/*      */           }
/*  807 */           k = localObject1[n];
/*      */         }
/*  809 */         String str2 = new String(arrayOfChar, k, j - k);
/*  810 */         localTranslate2D = new Translate2D(paramDouble1 + f5 + f8, paramDouble2);
/*  811 */         localPath2D = (Path2D)paramFontStrike.getOutline(str2, localTranslate2D);
/*  812 */         f5 += f8;
/*      */       } else {
/*  814 */         localObject1 = new String(arrayOfChar, k, j - k);
/*  815 */         localTranslate2D = new Translate2D(paramDouble1 + f5, paramDouble2);
/*  816 */         localPath2D = (Path2D)paramFontStrike.getOutline((String)localObject1, localTranslate2D);
/*      */       }
/*  818 */       paramPath2D.append(localPath2D, false);
/*      */     }
/*  820 */     return f5 + f1;
/*      */   }
/*      */ 
/*      */   private static Path2D getTabExpandedOutline(String paramString, FontStrike paramFontStrike, double paramDouble1, double paramDouble2)
/*      */   {
/*  827 */     char[] arrayOfChar = paramString.toCharArray();
/*  828 */     float f1 = 0.0F;
/*  829 */     float f2 = 8.0F * paramFontStrike.getCharAdvance(' ');
/*  830 */     float f3 = 0.0F;
/*  831 */     Translate2D localTranslate2D = new Translate2D(paramDouble1, paramDouble2);
/*  832 */     Object localObject = null;
/*  833 */     int i = 0;
/*  834 */     int j = 0;
/*  835 */     while (i < arrayOfChar.length) {
/*  836 */       char c = arrayOfChar[i];
/*  837 */       if (c != '\t') {
/*  838 */         i++;
/*  839 */         f1 += paramFontStrike.getCharAdvance(c);
/*      */       }
/*      */       else {
/*  842 */         if (i > j) {
/*  843 */           String str2 = new String(arrayOfChar, j, i - j);
/*  844 */           Path2D localPath2D2 = (Path2D)paramFontStrike.getOutline(str2, localTranslate2D);
/*  845 */           if (localObject == null)
/*  846 */             localObject = localPath2D2;
/*      */           else {
/*  848 */             ((Path2D)localObject).append(localPath2D2, false);
/*      */           }
/*      */         }
/*  851 */         i++; j = i;
/*      */ 
/*  853 */         float f4 = f3 + f1;
/*  854 */         f3 = ((int)(f4 / f2) + 1) * f2;
/*      */ 
/*  856 */         localTranslate2D = new Translate2D(paramDouble1 + f3, paramDouble2);
/*  857 */         f1 = 0.0F;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  862 */     if (i > j) {
/*  863 */       String str1 = new String(arrayOfChar, j, i - j);
/*  864 */       Path2D localPath2D1 = (Path2D)paramFontStrike.getOutline(str1, localTranslate2D);
/*  865 */       if (localObject == null)
/*  866 */         localObject = localPath2D1;
/*      */       else {
/*  868 */         ((Path2D)localObject).append(localPath2D1, false);
/*      */       }
/*      */     }
/*      */ 
/*  872 */     if (localObject == null) {
/*  873 */       localObject = new Path2D();
/*      */     }
/*  875 */     return localObject;
/*      */   }
/*      */ 
/*      */   private float getTabExpandedAdvance(String paramString, FontResource paramFontResource, CharToGlyphMapper paramCharToGlyphMapper, float paramFloat)
/*      */   {
/*  882 */     char[] arrayOfChar = paramString.toCharArray();
/*  883 */     float f1 = 0.0F;
/*  884 */     float f2 = 0.0F;
/*  885 */     float f3 = paramFontResource.getAdvance(paramCharToGlyphMapper.charToGlyph(32), paramFloat);
/*      */ 
/*  888 */     for (int i = 0; i < arrayOfChar.length; i++) {
/*  889 */       int j = arrayOfChar[i];
/*  890 */       if (j == 32) {
/*  891 */         f1 += f3; } else {
/*  892 */         if (j == 10)
/*      */           break;
/*  894 */         if (j != 9) {
/*  895 */           f1 += paramFontResource.getAdvance(paramCharToGlyphMapper.charToGlyph(j), paramFloat);
/*      */         }
/*      */         else {
/*  898 */           if (f2 == 0.0F) {
/*  899 */             f2 = 8.0F * f3;
/*      */           }
/*      */ 
/*  902 */           f1 = ((int)(f1 / f2) + 1) * f2;
/*      */         }
/*      */       }
/*      */     }
/*  905 */     if (f2 != 0.0F) {
/*  906 */       if (this.attributes == null) {
/*  907 */         this.attributes = new TextAttributes();
/*      */       }
/*  909 */       this.attributes.hasTabs = true;
/*      */     }
/*  911 */     return f1;
/*      */   }
/*      */ 
/*      */   static float getTabExpandedAdvance(String paramString, FontStrike paramFontStrike) {
/*  915 */     if (paramString.isEmpty()) {
/*  916 */       return 0.0F;
/*      */     }
/*  918 */     FontResource localFontResource = paramFontStrike.getFontResource();
/*  919 */     CharToGlyphMapper localCharToGlyphMapper = localFontResource.getGlyphMapper();
/*  920 */     float f1 = paramFontStrike.getSize();
/*  921 */     char[] arrayOfChar = paramString.toCharArray();
/*  922 */     float f2 = 0.0F;
/*  923 */     float f3 = 0.0F;
/*      */ 
/*  925 */     for (int i = 0; i < arrayOfChar.length; i++) {
/*  926 */       int j = arrayOfChar[i];
/*  927 */       if (j == 10)
/*      */       {
/*      */         break;
/*      */       }
/*      */ 
/*  932 */       if (j != 9)
/*      */       {
/*  934 */         f2 += localFontResource.getAdvance(localCharToGlyphMapper.charToGlyph(j), f1);
/*      */       }
/*      */       else
/*      */       {
/*  938 */         if (f3 == 0.0F) {
/*  939 */           f3 = 8.0F * localFontResource.getAdvance(localCharToGlyphMapper.charToGlyph(32), f1);
/*      */         }
/*      */ 
/*  943 */         f2 = ((int)(f2 / f3) + 1) * f3;
/*      */       }
/*      */     }
/*  946 */     return f2;
/*      */   }
/*      */ 
/*      */   private static float[] getTabJustifyExpandedAdvance(String paramString, FontStrike paramFontStrike, int paramInt1, int paramInt2, float paramFloat)
/*      */   {
/*  955 */     char[] arrayOfChar = paramString.toCharArray();
/*  956 */     float f1 = 0.0F;
/*  957 */     float f2 = 0.0F;
/*  958 */     float f3 = paramFontStrike.getCharAdvance(' ');
/*      */ 
/*  960 */     float f4 = 0.0F; float f5 = 0.0F;
/*  961 */     int i = 0;
/*  962 */     int j = 0;
/*  963 */     int k = 0;
/*      */ 
/*  967 */     int m = arrayOfChar.length - 1;
/*  968 */     while ((m > 0) && (arrayOfChar[m] == ' ')) {
/*  969 */       m--;
/*      */     }
/*  971 */     if (paramInt2 > m) {
/*  972 */       paramInt2 = m;
/*      */     }
/*      */ 
/*  975 */     for (int n = 0; n <= m; n++) {
/*  976 */       char c = arrayOfChar[n];
/*  977 */       if (paramInt1 == n) {
/*  978 */         f4 = f2;
/*  979 */         j = i;
/*      */       }
/*      */ 
/*  982 */       if (c == '\t') {
/*  983 */         if (f1 == 0.0F) {
/*  984 */           f1 = 8.0F * f3;
/*      */         }
/*      */ 
/*  987 */         f2 = ((int)(f2 / f1) + 1) * f1;
/*      */ 
/*  989 */         i = 0;
/*  990 */         j = 0;
/*  991 */         k = 0;
/*  992 */       } else if (c == ' ') {
/*  993 */         f2 += f3;
/*  994 */         i++;
/*      */       } else {
/*  996 */         f2 += paramFontStrike.getCharAdvance(c);
/*      */       }
/*  998 */       if (paramInt2 == n) {
/*  999 */         f5 = f2;
/* 1000 */         k = i;
/*      */       }
/*      */     }
/*      */ 
/* 1004 */     float f6 = i <= 0 ? 0.0F : (paramFloat - f2) / i;
/*      */ 
/* 1007 */     float f7 = 0.0F; float f8 = 0.0F;
/* 1008 */     f7 = f4 + f6 * j;
/* 1009 */     f8 = f5 + f6 * k;
/*      */ 
/* 1011 */     float[] arrayOfFloat = { f7, f8 };
/* 1012 */     return arrayOfFloat;
/*      */   }
/*      */ 
/*      */   private Path2D union(Shape paramShape1, Shape paramShape2)
/*      */   {
/* 1017 */     Path2D localPath2D = null;
/* 1018 */     if ((paramShape1 instanceof Path2D))
/* 1019 */       localPath2D = (Path2D)paramShape1;
/*      */     else {
/* 1021 */       localPath2D = new Path2D(paramShape1);
/*      */     }
/*      */ 
/* 1024 */     if (paramShape2 == null) {
/* 1025 */       return localPath2D;
/*      */     }
/*      */ 
/* 1028 */     localPath2D.append(paramShape2.getPathIterator(IDENT), false);
/* 1029 */     return localPath2D;
/*      */   }
/*      */ 
/*      */   private Shape translateShape(Shape paramShape) {
/* 1033 */     float f = getYAdjustment();
/* 1034 */     return TransformedShape.translatedShape(paramShape, this.x, this.y + f);
/*      */   }
/*      */ 
/*      */   private static Path2D createRectPath(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/* 1041 */     Path2D localPath2D = new Path2D();
/* 1042 */     localPath2D.moveTo(paramFloat1, paramFloat2);
/* 1043 */     localPath2D.lineTo(paramFloat1 + paramFloat3, paramFloat2);
/* 1044 */     localPath2D.lineTo(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
/* 1045 */     localPath2D.lineTo(paramFloat1, paramFloat2 + paramFloat4);
/* 1046 */     localPath2D.lineTo(paramFloat1, paramFloat2);
/* 1047 */     localPath2D.closePath();
/* 1048 */     return localPath2D;
/*      */   }
/*      */ 
/*      */   private Shape getDecorationShape(String paramString, FontStrike paramFontStrike, float paramFloat1, float paramFloat2, float paramFloat3)
/*      */   {
/* 1055 */     if (this.attributes == null) {
/* 1056 */       return null;
/*      */     }
/*      */ 
/* 1059 */     if ((this.attributes.underline) || (this.attributes.strikethrough)) {
/* 1060 */       if (paramFontStrike == null)
/* 1061 */         paramFontStrike = getStrike();
/*      */       float f1;
/* 1064 */       if (paramFloat3 > 0.0F)
/* 1065 */         f1 = paramFloat3;
/*      */       else {
/* 1067 */         f1 = getTabExpandedAdvance(paramString, paramFontStrike);
/*      */       }
/*      */ 
/* 1070 */       Path2D localPath2D1 = null; Path2D localPath2D2 = null;
/*      */       float f2;
/*      */       float f3;
/* 1071 */       if (this.attributes.underline) {
/* 1072 */         f2 = paramFontStrike.getUnderLineOffset();
/* 1073 */         f3 = paramFontStrike.getUnderLineThickness();
/* 1074 */         localPath2D1 = createRectPath(paramFloat1, paramFloat2 + f2, f1, f3);
/*      */       }
/* 1076 */       if (this.attributes.strikethrough) {
/* 1077 */         f2 = paramFontStrike.getStrikeThroughOffset();
/* 1078 */         f3 = paramFontStrike.getStrikeThroughThickness();
/* 1079 */         localPath2D2 = createRectPath(paramFloat1, paramFloat2 + f2, f1, f3);
/*      */       }
/* 1081 */       if (localPath2D1 == null)
/* 1082 */         return localPath2D2;
/* 1083 */       if (localPath2D2 == null) {
/* 1084 */         return localPath2D1;
/*      */       }
/* 1086 */       Path2D localPath2D3 = new Path2D(localPath2D1);
/* 1087 */       localPath2D3.append(localPath2D2.getPathIterator(IDENT), false);
/* 1088 */       return localPath2D3;
/*      */     }
/*      */ 
/* 1091 */     return null;
/*      */   }
/*      */ 
/*      */   public void setTextBoundsType(int paramInt)
/*      */   {
/* 1101 */     if (this.textBoundsType == paramInt) {
/* 1102 */       return;
/*      */     }
/*      */ 
/* 1119 */     int i = (paramInt == 1) || (this.textBoundsType == 1) ? 1 : 0;
/*      */ 
/* 1122 */     this.textBoundsType = paramInt;
/* 1123 */     if (i != 0)
/* 1124 */       geometryChanged();
/*      */   }
/*      */ 
/*      */   public final BaseBounds computeLayoutBounds(BaseBounds paramBaseBounds)
/*      */   {
/* 1129 */     if (this.textBoundsType == 1) {
/* 1130 */       paramBaseBounds = computeBoundsVisual(paramBaseBounds, IDENT);
/*      */     }
/*      */     else
/*      */     {
/* 1134 */       paramBaseBounds = computeBoundsLogical(paramBaseBounds, IDENT, false);
/*      */     }
/* 1136 */     return paramBaseBounds;
/*      */   }
/*      */ 
/*      */   public BaseBounds computeContentBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*      */   {
/* 1141 */     if (this.textBoundsType == 1) {
/* 1142 */       paramBaseBounds = computeBoundsVisual(paramBaseBounds, paramBaseTransform);
/*      */     }
/*      */     else
/*      */     {
/* 1146 */       paramBaseBounds = computeBoundsLogical(paramBaseBounds, paramBaseTransform, true);
/*      */     }
/* 1148 */     return paramBaseBounds;
/*      */   }
/*      */ 
/*      */   BaseBounds computeBoundsLogical(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, boolean paramBoolean)
/*      */   {
/* 1171 */     if (paramBaseBounds == null) {
/* 1172 */       paramBaseBounds = new RectBounds();
/*      */     }
/*      */ 
/* 1175 */     validateText();
/*      */ 
/* 1177 */     computeBoundsLogicalIdentity();
/* 1178 */     float f = getYAdjustment() + this.y;
/*      */ 
/* 1180 */     RectBounds localRectBounds = new RectBounds(this.logicalBounds.getMinX() + this.x, this.logicalBounds.getMinY() + f, this.logicalBounds.getMaxX() + this.x, this.logicalBounds.getMaxY() + f);
/*      */ 
/* 1184 */     PGShape.Mode localMode = getMode();
/*      */ 
/* 1190 */     if ((paramBoolean) && (!this.text.isEmpty()) && ((this.attributes != null) || (localMode != PGShape.Mode.FILL)))
/*      */     {
/* 1193 */       if (localMode == PGShape.Mode.FILL)
/*      */       {
/* 1197 */         logicalHPadding(localRectBounds);
/*      */       }
/*      */       else {
/* 1200 */         BaseBounds localBaseBounds = computeBoundsVisual(null, IDENT);
/*      */ 
/* 1202 */         localRectBounds.deriveWithUnion(localBaseBounds);
/*      */       }
/*      */     }
/* 1205 */     return NGShape.getRectShapeBounds(paramBaseBounds, paramBaseTransform, NGShape.classify(paramBaseTransform), 0.0F, 0.0F, localRectBounds);
/*      */   }
/*      */ 
/*      */   private RectBounds computeBoundsLogicalIdentity()
/*      */   {
/* 1211 */     if (this.logicalBounds != null) {
/* 1212 */       return this.logicalBounds;
/*      */     }
/*      */ 
/* 1215 */     FontStrike localFontStrike = getStrike(IDENT);
/* 1216 */     FontStrike.Metrics localMetrics = localFontStrike.getMetrics();
/*      */ 
/* 1219 */     float f1 = (this.attributes != null) && (this.attributes.wrappingWidth > 0.0F) ? this.attributes.wrappingWidth : this.maxLineAdvance;
/*      */     RectBounds localRectBounds;
/* 1221 */     if (simpleSingleLine())
/*      */     {
/* 1223 */       localRectBounds = new RectBounds(0.0F, localMetrics.getAscent(), f1, localMetrics.getDescent() + localMetrics.getLineGap());
/*      */     }
/*      */     else {
/* 1226 */       float f2 = this.attributes.numberOfLines() * localMetrics.getLineHeight();
/*      */ 
/* 1230 */       localRectBounds = new RectBounds(0.0F, localMetrics.getAscent(), f1, f2 + localMetrics.getAscent());
/*      */     }
/*      */ 
/* 1233 */     this.logicalBounds = localRectBounds;
/* 1234 */     return localRectBounds;
/*      */   }
/*      */ 
/*      */   private BaseBounds computeBoundsVisual(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*      */   {
/* 1239 */     if (paramBaseBounds == null) {
/* 1240 */       paramBaseBounds = new RectBounds();
/*      */     }
/*      */ 
/* 1243 */     PGShape.Mode localMode = getMode();
/* 1244 */     if ((localMode == PGShape.Mode.EMPTY) || (this.text.isEmpty())) {
/* 1245 */       return paramBaseBounds.makeEmpty();
/*      */     }
/*      */ 
/* 1249 */     validateText();
/*      */ 
/* 1251 */     if (this.visualBounds == null) {
/* 1252 */       this.visualBounds = null;
/*      */ 
/* 1263 */       Shape localShape = getShape(false);
/*      */ 
/* 1265 */       if ((localMode == PGShape.Mode.FILL) || (getStrokeType() == PGShape.StrokeType.INSIDE))
/*      */       {
/* 1270 */         this.visualBounds = localShape.getBounds();
/*      */       }
/*      */       else
/*      */       {
/* 1278 */         localObject = new float[4];
/* 1279 */         BasicStroke localBasicStroke = getStroke();
/* 1280 */         localBasicStroke.accumulateShapeBounds((float[])localObject, localShape, IDENT);
/* 1281 */         this.visualBounds = new RectBounds(localObject[0], localObject[1], localObject[2], localObject[3]);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1286 */     float f = this.y;
/* 1287 */     if (this.textBoundsType == 1)
/*      */     {
/* 1292 */       f += getYAdjustment(this.visualBounds);
/*      */     }
/* 1294 */     else f += getYAdjustment();
/*      */ 
/* 1296 */     Object localObject = new RectBounds(this.visualBounds.getMinX() + this.x, this.visualBounds.getMinY() + f, this.visualBounds.getMaxX() + this.x, this.visualBounds.getMaxY() + f);
/*      */ 
/* 1300 */     return NGShape.getRectShapeBounds(paramBaseBounds, paramBaseTransform, NGShape.classify(paramBaseTransform), 0.0F, 0.0F, (RectBounds)localObject);
/*      */   }
/*      */ 
/*      */   public void setLocation(float paramFloat1, float paramFloat2)
/*      */   {
/* 1305 */     if ((paramFloat1 != this.x) || (paramFloat2 != this.y)) {
/* 1306 */       this.x = paramFloat1;
/* 1307 */       this.y = paramFloat2;
/* 1308 */       this.locationIsChanged = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   float getX() {
/* 1313 */     return this.x;
/*      */   }
/*      */ 
/*      */   float getY() {
/* 1317 */     return this.y;
/*      */   }
/*      */ 
/*      */   boolean isLocationChanged() {
/* 1321 */     return this.locationIsChanged;
/*      */   }
/*      */ 
/*      */   void resetLocationChanged() {
/* 1325 */     this.locationIsChanged = false;
/*      */   }
/*      */ 
/*      */   private float getYAdjustment(FontStrike.Metrics paramMetrics)
/*      */   {
/* 1338 */     float f = 0.0F;
/* 1339 */     if (this.textOrigin == 2)
/*      */     {
/* 1341 */       return f;
/*      */     }
/* 1343 */     if (paramMetrics == null) {
/* 1344 */       paramMetrics = getStrike().getMetrics();
/*      */     }
/*      */ 
/* 1347 */     switch (this.textOrigin) {
/*      */     case 0:
/* 1349 */       f = -paramMetrics.getAscent();
/* 1350 */       break;
/*      */     case 1:
/* 1353 */       f = -paramMetrics.getDescent();
/* 1354 */       if (this.attributes != null) {
/* 1355 */         f -= paramMetrics.getLineHeight() * (this.attributes.numberOfLines() - 1);
/*      */       }
/* 1357 */       f += -paramMetrics.getAscent();
/* 1358 */       f /= 2.0F;
/* 1359 */       break;
/*      */     case 3:
/* 1364 */       f = -paramMetrics.getDescent();
/* 1365 */       if (this.attributes != null)
/* 1366 */         f -= paramMetrics.getLineHeight() * (this.attributes.numberOfLines() - 1); break;
/*      */     case 2:
/*      */     }
/*      */ 
/* 1371 */     return f;
/*      */   }
/*      */ 
/*      */   private float getYAdjustment(RectBounds paramRectBounds)
/*      */   {
/* 1382 */     float f = 0.0F;
/*      */ 
/* 1384 */     switch (this.textOrigin)
/*      */     {
/*      */     case 0:
/* 1387 */       f = -paramRectBounds.getMinY();
/* 1388 */       break;
/*      */     case 1:
/* 1391 */       f = -((paramRectBounds.getMaxY() + paramRectBounds.getMinY()) / 2.0F);
/* 1392 */       break;
/*      */     case 3:
/* 1394 */       f = -paramRectBounds.getMaxY();
/* 1395 */       break;
/*      */     case 2:
/*      */     }
/* 1398 */     this.yAdjCached = f;
/* 1399 */     this.yAdjValid = true;
/* 1400 */     return f;
/*      */   }
/*      */ 
/*      */   float getYAdjustment()
/*      */   {
/* 1409 */     if (this.textOrigin == 2)
/*      */     {
/* 1411 */       this.yAdjCached = 0.0F;
/* 1412 */       this.yAdjValid = true;
/* 1413 */       return this.yAdjCached;
/* 1414 */     }if (this.yAdjValid) {
/* 1415 */       return this.yAdjCached;
/*      */     }
/* 1417 */     if (this.textBoundsType == 1) {
/* 1418 */       BaseBounds localBaseBounds = null;
/* 1419 */       computeBoundsVisual(localBaseBounds, BaseTransform.IDENTITY_TRANSFORM);
/* 1420 */       getYAdjustment(this.visualBounds);
/*      */     } else {
/* 1422 */       getYAdjustment(computeBoundsLogicalIdentity());
/*      */     }
/*      */ 
/* 1425 */     return this.yAdjCached;
/*      */   }
/*      */ 
/*      */   public boolean computeContains(float paramFloat1, float paramFloat2)
/*      */   {
/* 1430 */     return true;
/*      */   }
/*      */ 
/*      */   public Object getCaretShape(int paramInt, boolean paramBoolean)
/*      */   {
/* 1435 */     validateText();
/*      */ 
/* 1437 */     int i = 0;
/* 1438 */     int j = -1;
/*      */     String str;
/* 1440 */     if ((this.attributes != null) && (this.attributes.newLineIndices != null))
/*      */     {
/* 1442 */       int[] arrayOfInt = this.attributes.newLineIndices;
/* 1443 */       for (m = 0; m < arrayOfInt.length; m++) {
/* 1444 */         if (paramInt <= arrayOfInt[m]) {
/* 1445 */           j = m;
/* 1446 */           break;
/*      */         }
/*      */       }
/* 1449 */       if (j == -1) {
/* 1450 */         j = arrayOfInt.length - 1;
/*      */       }
/* 1452 */       if (j > 0) {
/* 1453 */         i = arrayOfInt[(j - 1)] + 1;
/*      */       }
/* 1455 */       m = arrayOfInt[j];
/* 1456 */       str = this.text.substring(i, m + 1);
/*      */     } else {
/* 1458 */       str = this.text;
/* 1459 */       j = 0;
/*      */     }
/*      */ 
/* 1462 */     int k = Math.min(paramInt, this.text.length());
/* 1463 */     int m = k - i;
/*      */ 
/* 1465 */     FontStrike localFontStrike = getStrike();
/* 1466 */     float f1 = this.x + (m == 0 ? 0.0F : getTabExpandedAdvance(str.substring(0, m), localFontStrike));
/*      */ 
/* 1470 */     FontStrike.Metrics localMetrics = localFontStrike.getMetrics();
/* 1471 */     float f2 = this.y + getYAdjustment() + j * localMetrics.getLineHeight();
/* 1472 */     return new Line2D(f1, f2 + localMetrics.getAscent(), f1, f2 + localMetrics.getDescent());
/*      */   }
/*      */ 
/*      */   public Object getHitInfo(float paramFloat1, float paramFloat2)
/*      */   {
/* 1477 */     validateText();
/*      */ 
/* 1479 */     FontStrike localFontStrike = getStrike();
/* 1480 */     FontStrike.Metrics localMetrics = localFontStrike.getMetrics();
/*      */ 
/* 1491 */     float f1 = paramFloat1 < this.x ? 0.0F : paramFloat1 - this.x;
/* 1492 */     float f2 = (paramFloat2 < this.y ? 0.0F : paramFloat2 - this.y) - getYAdjustment() - localMetrics.getAscent();
/*      */ 
/* 1498 */     int i = 0;
/* 1499 */     char[] arrayOfChar = null;
/* 1500 */     String str = null;
/*      */     float f3;
/* 1501 */     if ((this.attributes != null) && (this.attributes.newLineIndices != null)) {
/* 1502 */       f3 = localMetrics.getLineHeight();
/* 1503 */       int[] arrayOfInt = this.attributes.newLineIndices;
/* 1504 */       int j = (int)(f2 / f3);
/* 1505 */       if (j > arrayOfInt.length - 1) {
/* 1506 */         return new Integer(this.text.length());
/*      */       }
/* 1508 */       if (j > 0)
/*      */       {
/* 1510 */         i = arrayOfInt[(j - 1)] + 1;
/*      */       }
/* 1512 */       k = arrayOfInt[j] + 1;
/* 1513 */       str = this.text.substring(i, k);
/*      */     } else {
/* 1515 */       str = this.text;
/*      */     }
/*      */ 
/* 1520 */     float f4 = 0.0F;
/* 1521 */     float f5 = 0.0F;
/* 1522 */     arrayOfChar = str.toCharArray();
/*      */ 
/* 1525 */     for (int k = 0; k < arrayOfChar.length; k++) {
/* 1526 */       int m = arrayOfChar[k];
/* 1527 */       if (m == 9) {
/* 1528 */         if (f4 == 0.0F) {
/* 1529 */           f4 = 8.0F * localFontStrike.getCharAdvance(' ');
/*      */         }
/*      */ 
/* 1532 */         float f6 = ((int)(f5 / f4) + 1) * f4;
/*      */ 
/* 1534 */         f3 = f6 - f5;
/*      */       } else {
/* 1536 */         f3 = localFontStrike.getCharAdvance(arrayOfChar[k]);
/*      */       }
/* 1538 */       if (f1 < f5 + f3 * 0.75D) {
/* 1539 */         return Integer.valueOf(k + i);
/*      */       }
/* 1541 */       f5 += f3;
/*      */     }
/*      */ 
/* 1544 */     return Integer.valueOf(arrayOfChar.length + i);
/*      */   }
/*      */ 
/*      */   public Shape getSelectionShape() {
/* 1548 */     if (this.selection != null) {
/* 1549 */       if ((this.selection.shape == null) && (this.selection.end > this.selection.start)) {
/* 1550 */         this.selection.shape = getRangeShapeImpl(this.selection.start, this.selection.end, false);
/*      */       }
/*      */ 
/* 1553 */       return this.selection.shape;
/*      */     }
/* 1555 */     return null;
/*      */   }
/*      */ 
/*      */   public Shape getRangeShape(int paramInt1, int paramInt2) {
/* 1559 */     return getRangeShapeImpl(paramInt1, paramInt2, false);
/*      */   }
/*      */ 
/*      */   public Shape getUnderlineShape(int paramInt1, int paramInt2) {
/* 1563 */     return getRangeShapeImpl(paramInt1, paramInt2, true);
/*      */   }
/*      */ 
/*      */   private Shape getRangeShapeImpl(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 1567 */     PGShape.Mode localMode = getMode();
/* 1568 */     if ((localMode == PGShape.Mode.EMPTY) || (this.text.isEmpty())) {
/* 1569 */       return null;
/*      */     }
/* 1571 */     validateText();
/*      */ 
/* 1573 */     if (paramInt1 > paramInt2) {
/* 1574 */       int i = paramInt1;
/* 1575 */       paramInt1 = paramInt2;
/* 1576 */       paramInt2 = i;
/*      */     }
/*      */ 
/* 1579 */     paramInt2 = Math.min(paramInt2, this.text.length());
/* 1580 */     FontStrike localFontStrike = getStrike();
/* 1581 */     FontStrike.Metrics localMetrics = localFontStrike.getMetrics();
/* 1582 */     float f1 = localMetrics.getLineHeight();
/* 1583 */     float f2 = this.y + getYAdjustment();
/*      */ 
/* 1585 */     if ((this.attributes != null) && (this.attributes.newLineIndices != null)) {
/* 1586 */       Path2D localPath2D = new Path2D();
/* 1587 */       localObject = this.attributes.newLineIndices;
/* 1588 */       int j = 0;
/*      */ 
/* 1590 */       f4 = getLogicalWidth();
/* 1591 */       int k = f4 > 0.0F ? 1 : 0;
/* 1592 */       f6 = 0.0F;
/* 1593 */       float f7 = 0.0F;
/*      */ 
/* 1595 */       float f8 = this.attributes.textAlignment == 3 ? 0.0F : this.attributes.textAlignment;
/*      */ 
/* 1598 */       f8 /= 2.0F;
/*      */ 
/* 1600 */       for (int m = 0; m < localObject.length; f2 += f1) {
/* 1601 */         int n = localObject[m];
/*      */ 
/* 1603 */         if (paramInt2 < j) {
/*      */           break;
/*      */         }
/* 1606 */         if (paramInt1 > n)
/*      */         {
/* 1608 */           j = n + 1;
/*      */         }
/*      */         else {
/* 1611 */           if (k != 0) {
/* 1612 */             f7 = f4 - this.attributes.lineAdvances[m];
/* 1613 */             f6 = f8 * f7;
/*      */           }
/* 1615 */           boolean bool = Character.isWhitespace(this.text.charAt(n));
/* 1616 */           if ((j >= n) && (bool))
/*      */           {
/* 1618 */             j++;
/*      */           }
/*      */           else {
/* 1621 */             float f9 = 0.0F;
/* 1622 */             if (paramInt1 > j)
/*      */             {
/* 1624 */               String str1 = this.text.substring(j, paramInt1);
/* 1625 */               if ((this.attributes.hasTabs) && (str1.indexOf(9) != -1))
/* 1626 */                 f9 = getTabExpandedAdvance(str1, localFontStrike);
/*      */               else {
/* 1628 */                 f9 = localFontStrike.getStringBounds(str1).getWidth();
/*      */               }
/*      */             }
/* 1631 */             int i1 = bool ? n : n + 1;
/* 1632 */             String str2 = this.text.substring(j, i1 <= paramInt2 ? i1 : paramInt2);
/*      */ 
/* 1634 */             RectBounds localRectBounds2 = localFontStrike.getStringBounds(str2);
/* 1635 */             float f10 = this.x + f9 + f6;
/*      */ 
/* 1637 */             int i2 = (this.text.charAt(localObject[m]) == '\n') || (m == localObject.length - 1) ? 1 : 0;
/*      */             float f11;
/* 1640 */             if ((k != 0) && (this.attributes.textAlignment == 3) && (i2 == 0))
/*      */             {
/* 1645 */               int i3 = paramInt1 <= j ? 0 : paramInt1 - j;
/* 1646 */               int i4 = paramInt2 <= n ? paramInt2 - j : n - j;
/*      */ 
/* 1649 */               str2 = this.text.substring(j, i1);
/* 1650 */               float[] arrayOfFloat = getTabJustifyExpandedAdvance(str2, localFontStrike, i3, i4 - 1, f4);
/*      */ 
/* 1654 */               f11 = 0.0F;
/* 1655 */               if (arrayOfFloat.length == 2) {
/* 1656 */                 f10 = arrayOfFloat[0];
/* 1657 */                 f11 = arrayOfFloat[1] - f10;
/*      */               }
/*      */             } else {
/* 1660 */               f11 = (str2.indexOf(9) != -1 ? getTabExpandedAdvance(str2, localFontStrike) : localRectBounds2.getWidth()) - f9;
/*      */             }
/*      */ 
/* 1664 */             float f12 = f1;
/* 1665 */             if (paramBoolean) {
/* 1666 */               f2 += localFontStrike.getUnderLineOffset() - localMetrics.getAscent();
/* 1667 */               f12 = localFontStrike.getUnderLineThickness();
/*      */             }
/* 1669 */             localPath2D.append(new RoundRectangle2D(f10 + localRectBounds2.getMinX(), f2 + localRectBounds2.getMinY(), f11, f12, 0.0F, 0.0F), false);
/*      */ 
/* 1671 */             j = n + 1;
/*      */           }
/*      */         }
/* 1600 */         m++;
/*      */       }
/*      */ 
/* 1673 */       return localPath2D;
/*      */     }
/* 1675 */     float f3 = 0.0F;
/* 1676 */     if (paramInt1 > 0) {
/* 1677 */       localObject = this.text.substring(0, paramInt1);
/* 1678 */       if ((this.attributes != null) && (this.attributes.hasTabs) && (((String)localObject).indexOf(9) != -1))
/*      */       {
/* 1680 */         f3 = getTabExpandedAdvance((String)localObject, localFontStrike);
/*      */       }
/* 1682 */       else f3 = localFontStrike.getStringBounds((String)localObject).getWidth();
/*      */     }
/*      */ 
/* 1685 */     Object localObject = this.text.substring(0, paramInt2);
/* 1686 */     RectBounds localRectBounds1 = localFontStrike.getStringBounds((String)localObject);
/* 1687 */     float f4 = this.x + f3;
/*      */     float f5;
/* 1689 */     if ((this.attributes != null) && (this.attributes.hasTabs) && (((String)localObject).indexOf(9) != -1))
/*      */     {
/* 1691 */       f5 = getTabExpandedAdvance((String)localObject, localFontStrike) - f3;
/*      */     }
/* 1693 */     else f5 = localRectBounds1.getWidth() - f3;
/*      */ 
/* 1695 */     float f6 = f1;
/* 1696 */     if (paramBoolean) {
/* 1697 */       f2 += localFontStrike.getUnderLineOffset() - localMetrics.getAscent();
/* 1698 */       f6 = localFontStrike.getUnderLineThickness();
/*      */     }
/*      */ 
/* 1701 */     return new RoundRectangle2D(f4 + localRectBounds1.getMinX(), f2 + localRectBounds1.getMinY(), f5, f6, 0.0F, 0.0F);
/*      */   }
/*      */ 
/*      */   FontStrike getStrike()
/*      */   {
/* 1712 */     return getStrike(getCumulativeTransform());
/*      */   }
/*      */ 
/*      */   FontStrike getStrike(BaseTransform paramBaseTransform)
/*      */   {
/* 1717 */     if (paramBaseTransform == null) {
/* 1718 */       return getStrike();
/*      */     }
/*      */ 
/* 1721 */     int i = this.fontSmoothingType;
/* 1722 */     PGShape.Mode localMode = getMode();
/* 1723 */     if ((localMode == PGShape.Mode.STROKE_FILL) && (hasStroke()))
/*      */     {
/* 1726 */       i = 0;
/*      */     }
/* 1728 */     if (paramBaseTransform.isIdentity()) {
/* 1729 */       if ((this.identityStrike == null) || (i != this.identityStrike.getAAMode()))
/*      */       {
/* 1731 */         this.identityStrike = this.font.getStrike(IDENT, i);
/*      */       }
/* 1733 */       return this.identityStrike;
/*      */     }
/*      */ 
/* 1736 */     if ((this.fontStrike == null) || (this.fontStrike.getSize() != this.font.getSize()) || ((paramBaseTransform.getMxy() == 0.0D) && (this.strikeMat[1] != 0.0D)) || ((paramBaseTransform.getMyx() == 0.0D) && (this.strikeMat[2] != 0.0D)) || (Math.abs(this.strikeMat[0] - paramBaseTransform.getMxx()) > EPSILON) || (Math.abs(this.strikeMat[1] - paramBaseTransform.getMxy()) > EPSILON) || (Math.abs(this.strikeMat[2] - paramBaseTransform.getMyx()) > EPSILON) || (Math.abs(this.strikeMat[3] - paramBaseTransform.getMyy()) > EPSILON) || (i != this.fontStrike.getAAMode()))
/*      */     {
/* 1746 */       this.fontStrike = this.font.getStrike(paramBaseTransform, i);
/* 1747 */       this.strikeMat[0] = paramBaseTransform.getMxx();
/* 1748 */       this.strikeMat[1] = paramBaseTransform.getMxy();
/* 1749 */       this.strikeMat[2] = paramBaseTransform.getMyx();
/* 1750 */       this.strikeMat[3] = paramBaseTransform.getMyy();
/*      */     }
/* 1752 */     return this.fontStrike;
/*      */   }
/*      */ 
/*      */   public void setFont(Object paramObject) {
/* 1756 */     if ((this.font != null) && 
/* 1757 */       (this.font.equals(paramObject))) {
/* 1758 */       return;
/*      */     }
/*      */ 
/* 1761 */     this.font = ((PGFont)paramObject);
/* 1762 */     this.identityStrike = null;
/* 1763 */     this.fontStrike = null;
/* 1764 */     this.fontChanged = true;
/* 1765 */     geometryChanged();
/*      */   }
/*      */ 
/*      */   public void setFontSmoothingType(int paramInt) {
/* 1769 */     if (this.fontSmoothingType != paramInt) {
/* 1770 */       this.fontSmoothingType = ((short)paramInt);
/* 1771 */       geometryChangedTextValid();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setLogicalSelection(int paramInt1, int paramInt2) {
/* 1776 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt2 <= paramInt1) || (paramInt1 > this.text.length())) {
/* 1777 */       if (this.selection != null) {
/* 1778 */         this.selection.shape = null;
/*      */ 
/* 1782 */         if (this.selection.start != this.selection.end) {
/* 1783 */           this.selection.start = 0;
/* 1784 */           this.selection.end = 0;
/*      */ 
/* 1786 */           geometryChangedTextValid();
/*      */         }
/*      */       }
/* 1789 */       return;
/*      */     }
/* 1791 */     if ((this.selection == null) || (this.selection.start != paramInt1) || (this.selection.end != paramInt2))
/*      */     {
/* 1793 */       if (this.selection == null) {
/* 1794 */         this.selection = new Selection();
/*      */       }
/* 1796 */       this.selection.start = Math.max(Math.min(paramInt1, paramInt2), 0);
/* 1797 */       this.selection.end = Math.min(Math.max(paramInt1, paramInt2), this.text.length());
/* 1798 */       this.selection.shape = null;
/* 1799 */       geometryChangedTextValid();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setSelectionPaint(Object paramObject1, Object paramObject2) {
/* 1804 */     if (this.selection == null) {
/* 1805 */       this.selection = new Selection();
/*      */     }
/* 1807 */     Paint localPaint1 = (Paint)paramObject1;
/* 1808 */     Paint localPaint2 = (Paint)paramObject2;
/* 1809 */     int i = 0;
/* 1810 */     if ((this.selection.drawPaint == null) || (!this.selection.drawPaint.equals(localPaint1)))
/*      */     {
/* 1813 */       this.selection.drawPaint = localPaint1;
/* 1814 */       i = 1;
/*      */     }
/*      */ 
/* 1817 */     if ((this.selection.fillPaint == null) || (!this.selection.fillPaint.equals(localPaint2))) {
/* 1818 */       this.selection.fillPaint = localPaint2;
/* 1819 */       i = 1;
/*      */     }
/*      */ 
/* 1822 */     if (i != 0)
/* 1823 */       geometryChangedTextValid();
/*      */   }
/*      */ 
/*      */   String getText()
/*      */   {
/* 1828 */     return this.text;
/*      */   }
/*      */ 
/*      */   public void setText(String paramString) {
/* 1832 */     if (paramString == null) {
/* 1833 */       paramString = "";
/*      */     }
/* 1835 */     if (!this.text.equals(paramString)) {
/* 1836 */       this.text = paramString;
/* 1837 */       geometryChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */   void validateText() {
/* 1842 */     if (!this.valid) {
/* 1843 */       if (this.attributes != null) {
/* 1844 */         this.attributes.reset();
/*      */       }
/* 1846 */       this.maxLineAdvance = 0.0F;
/* 1847 */       buildTextLines(this.text);
/* 1848 */       this.valid = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   void geometryChangedTextValid()
/*      */   {
/* 1854 */     boolean bool = this.valid;
/* 1855 */     geometryChanged();
/* 1856 */     this.valid = bool;
/*      */   }
/*      */ 
/*      */   protected void geometryChanged()
/*      */   {
/* 1861 */     this.valid = false;
/* 1862 */     if (this.attributes != null) {
/* 1863 */       this.attributes.resetShape();
/*      */     }
/* 1865 */     if (this.selection != null) {
/* 1866 */       this.selection.shape = null;
/*      */     }
/* 1868 */     this.yAdjValid = false;
/* 1869 */     this.visualBounds = null;
/* 1870 */     this.logicalBounds = null;
/* 1871 */     this.geometryIsChanged = true;
/*      */   }
/*      */ 
/*      */   boolean isGeometryChanged()
/*      */   {
/* 1883 */     return this.geometryIsChanged;
/*      */   }
/*      */ 
/*      */   void resetGeometryChanged() {
/* 1887 */     this.geometryIsChanged = false;
/*      */   }
/*      */ 
/*      */   private static boolean isPreferredBreakCharacter(char paramChar)
/*      */   {
/* 2011 */     if (Character.isWhitespace(paramChar)) {
/* 2012 */       return true;
/*      */     }
/* 2014 */     switch (paramChar) {
/*      */     case '.':
/*      */     case ':':
/*      */     case ';':
/* 2018 */       return true;
/* 2019 */     }return false;
/*      */   }
/*      */ 
/*      */   private void logicalHPadding(BaseBounds paramBaseBounds)
/*      */   {
/* 2036 */     if ((this.attributes == null) || (!this.attributes.visualGreaterThanLogicalBounds()))
/*      */     {
/* 2038 */       return;
/*      */     }
/* 2040 */     float f1 = 0.0F;
/* 2041 */     float f2 = 0.0F;
/*      */ 
/* 2043 */     if (this.attributes.simpleSingleLine()) {
/* 2044 */       if ((this.attributes.lsb != null) && (this.attributes.lsb[0] < 0.0F)) {
/* 2045 */         f1 = this.attributes.lsb[0];
/*      */       }
/* 2047 */       if ((this.attributes.rsb != null) && (this.attributes.rsb[0] > 0.0F))
/* 2048 */         f2 = this.attributes.rsb[0];
/*      */     }
/*      */     else {
/* 2051 */       float f3 = 0.0F;
/* 2052 */       float f4 = 0.0F;
/* 2053 */       switch (this.attributes.textAlignment) {
/*      */       case 1:
/* 2055 */         f4 = 0.5F;
/* 2056 */         break;
/*      */       case 2:
/* 2058 */         f4 = 1.0F;
/* 2059 */         break;
/*      */       case 3:
/* 2066 */         f1 = FloatList.getMin(this.attributes.lsb);
/* 2067 */         f2 = FloatList.getMax(this.attributes.rsb);
/*      */ 
/* 2069 */         paramBaseBounds.deriveWithNewBounds(paramBaseBounds.getMinX() + f1, paramBaseBounds.getMinY(), paramBaseBounds.getMinZ(), paramBaseBounds.getMaxX() + f2, paramBaseBounds.getMaxY(), paramBaseBounds.getMaxZ());
/*      */ 
/* 2075 */         return;
/*      */       }
/*      */ 
/* 2080 */       float[] arrayOfFloat = this.attributes.lineAdvances;
/* 2081 */       if (arrayOfFloat == null) {
/* 2082 */         arrayOfFloat = new float[] { this.maxLineAdvance };
/*      */       }
/* 2084 */       float f5 = this.attributes.wrappingWidth > 0.0F ? this.attributes.wrappingWidth : this.maxLineAdvance;
/*      */ 
/* 2087 */       for (int i = 0; i < arrayOfFloat.length; i++) {
/* 2088 */         f3 = f5 - arrayOfFloat[i];
/* 2089 */         float f7 = f3 * f4;
/*      */ 
/* 2092 */         float f8 = this.attributes.lsb[i] + f7;
/* 2093 */         if (f8 < f1) {
/* 2094 */           f1 = f8;
/*      */         }
/*      */ 
/* 2097 */         f8 = this.attributes.rsb[i] + f7 - f3;
/* 2098 */         if (f2 < f8) {
/* 2099 */           f2 = f8;
/*      */         }
/*      */       }
/* 2102 */       if (this.attributes.overrideWrappingWidth)
/*      */       {
/* 2105 */         float f6 = (this.maxLineAdvance - this.attributes.wrappingWidth) * f4;
/*      */ 
/* 2107 */         f1 += f6;
/* 2108 */         f2 += f6;
/*      */       }
/*      */     }
/* 2111 */     paramBaseBounds.deriveWithNewBounds(paramBaseBounds.getMinX() + f1, paramBaseBounds.getMinY(), paramBaseBounds.getMinZ(), paramBaseBounds.getMaxX() + f2, paramBaseBounds.getMaxY(), paramBaseBounds.getMaxZ());
/*      */   }
/*      */ 
/*      */   float getLogicalWidth()
/*      */   {
/* 2124 */     validateText();
/* 2125 */     if ((this.attributes != null) && (this.attributes.wrappingWidthSet))
/*      */     {
/* 2129 */       return this.attributes.overrideWrappingWidth ? this.maxLineAdvance : this.attributes.wrappingWidth;
/*      */     }
/*      */ 
/* 2132 */     return this.maxLineAdvance;
/*      */   }
/*      */ 
/*      */   boolean simpleSingleLine()
/*      */   {
/* 2137 */     if (this.attributes == null) {
/* 2138 */       return true;
/*      */     }
/* 2140 */     return this.attributes.simpleSingleLine();
/*      */   }
/*      */ 
/*      */   private void buildTextLines(String paramString)
/*      */   {
/* 2172 */     if (paramString.isEmpty()) {
/* 2173 */       return;
/*      */     }
/* 2175 */     FontStrike localFontStrike = getStrike(IDENT);
/*      */ 
/* 2180 */     FontResource localFontResource = localFontStrike.getFontResource();
/* 2181 */     CharToGlyphMapper localCharToGlyphMapper = localFontResource.getGlyphMapper();
/* 2182 */     float f1 = localFontStrike.getSize();
/*      */ 
/* 2184 */     if ((paramString.indexOf(10) == -1) && (
/* 2185 */       (this.attributes == null) || (!this.attributes.wrappingWidthSet))) {
/* 2186 */       this.maxLineAdvance = getTabExpandedAdvance(paramString, localFontResource, localCharToGlyphMapper, f1);
/*      */ 
/* 2188 */       localObject1 = new float[4];
/* 2189 */       localObject2 = TextAttributes.computeSideBearings(paramString, localFontResource, localCharToGlyphMapper, f1, (float[])localObject1);
/*      */ 
/* 2194 */       if ((localObject2[0] < 0.0F) || (0.0F < localObject2[1])) {
/* 2195 */         if (this.attributes == null) {
/* 2196 */           this.attributes = new TextAttributes();
/*      */         }
/* 2198 */         this.attributes.lsb = new float[] { localObject2[0] };
/* 2199 */         this.attributes.rsb = new float[] { localObject2[1] };
/*      */       }
/* 2201 */       return;
/*      */     }
/*      */ 
/* 2206 */     if (this.attributes == null) {
/* 2207 */       this.attributes = new TextAttributes();
/*      */     }
/* 2209 */     Object localObject1 = paramString.toCharArray();
/*      */ 
/* 2214 */     Object localObject2 = new IntList();
/* 2215 */     FloatList localFloatList = new FloatList();
/*      */ 
/* 2217 */     float f2 = 0.0F;
/* 2218 */     int i = 0;
/* 2219 */     int j = 0;
/*      */ 
/* 2235 */     int k = 0;
/* 2236 */     float f3 = 0.0F;
/*      */ 
/* 2240 */     float f5 = 0.0F;
/*      */ 
/* 2242 */     float f6 = localFontResource.getAdvance(localCharToGlyphMapper.charToGlyph(32), f1);
/*      */ 
/* 2244 */     for (int m = 0; m < localObject1.length; m++) {
/* 2245 */       char c = localObject1[m];
/*      */       float f4;
/* 2246 */       if (c == '\t') {
/* 2247 */         this.attributes.hasTabs = true;
/* 2248 */         if (f5 == 0.0F) {
/* 2249 */           f5 = 8.0F * f6;
/*      */         }
/*      */ 
/* 2252 */         float f7 = ((int)(f2 / f5) + 1) * f5;
/*      */ 
/* 2254 */         f4 = f7 - f2;
/*      */       } else {
/* 2256 */         f4 = localFontResource.getAdvance(localCharToGlyphMapper.charToGlyph(c), f1);
/*      */       }
/*      */ 
/* 2260 */       if (c == '\n') {
/* 2261 */         ((IntList)localObject2).add(m);
/* 2262 */         localFloatList.add(f2);
/* 2263 */         j = m + 1;
/* 2264 */         i = 0;
/* 2265 */         f2 = 0.0F;
/* 2266 */         k = 0;
/* 2267 */         f3 = 0.0F;
/*      */       }
/* 2269 */       else if ((this.attributes.wrappingWidthSet) && (f4 + f2 > this.attributes.wrappingWidth))
/*      */       {
/* 2274 */         if (Character.isWhitespace(c)) {
/* 2275 */           ((IntList)localObject2).add(m);
/*      */ 
/* 2277 */           localFloatList.add(f4 + f2);
/* 2278 */           j = m + 1;
/* 2279 */           i = 0;
/* 2280 */           f2 = 0.0F;
/*      */         }
/* 2283 */         else if (k > 0) {
/* 2284 */           ((IntList)localObject2).add(j + k);
/* 2285 */           localFloatList.add(f3);
/* 2286 */           f2 = f2 - f3 + f4;
/*      */ 
/* 2288 */           j += k + 1;
/* 2289 */           i = m - j + 1;
/* 2290 */           if (isPreferredBreakCharacter(c)) {
/* 2291 */             k = i;
/* 2292 */             f3 = f2;
/*      */           }
/*      */         }
/* 2295 */         else if (i == 0)
/*      */         {
/* 2300 */           this.attributes.overrideWrappingWidth = true;
/* 2301 */           ((IntList)localObject2).add(j);
/* 2302 */           localFloatList.add(f4);
/* 2303 */           j = m + 1;
/* 2304 */           i = 0;
/* 2305 */           f2 = 0.0F;
/*      */         }
/*      */         else
/*      */         {
/* 2310 */           ((IntList)localObject2).add(j + i - 1);
/* 2311 */           localFloatList.add(f2);
/* 2312 */           j = m;
/* 2313 */           i = 1;
/* 2314 */           f2 = f4;
/*      */         }
/*      */ 
/* 2317 */         k = 0;
/* 2318 */         f3 = 0.0F;
/*      */       }
/*      */       else {
/* 2321 */         f2 += f4;
/*      */ 
/* 2323 */         if ((this.attributes.wrappingWidthSet) && (isPreferredBreakCharacter(c)))
/*      */         {
/* 2326 */           k = i;
/* 2327 */           f3 = f2;
/*      */         }
/* 2329 */         i++;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2341 */     if ((localObject1.length > 0) && ((i > 0) || (localObject1[(localObject1.length - 1)] == '\n')))
/*      */     {
/* 2344 */       ((IntList)localObject2).add(localObject1.length - 1);
/* 2345 */       localFloatList.add(f2);
/*      */     }
/* 2347 */     this.attributes.lineAdvances = localFloatList.getData();
/* 2348 */     this.attributes.newLineIndices = ((IntList)localObject2).getData();
/*      */ 
/* 2350 */     this.maxLineAdvance = this.attributes.getMaxAdvance();
/* 2351 */     if ((!this.attributes.wrappingWidthSet) && (this.attributes.numberOfLines() > 1))
/*      */     {
/* 2355 */       this.attributes.wrappingWidth = this.maxLineAdvance;
/*      */     }
/* 2357 */     this.attributes.computeLinePadding(this.text, localFontStrike);
/*      */   }
/*      */ 
/*      */   public void setTextAlignment(int paramInt)
/*      */   {
/* 2365 */     if (this.attributes == null) {
/* 2366 */       if (paramInt == 0) {
/* 2367 */         return;
/*      */       }
/* 2369 */       this.attributes = new TextAttributes();
/*      */     }
/* 2371 */     if (paramInt != this.attributes.textAlignment) {
/* 2372 */       this.attributes.textAlignment = paramInt;
/* 2373 */       geometryChangedTextValid();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setTextOrigin(int paramInt) {
/* 2378 */     if (paramInt != this.textOrigin) {
/* 2379 */       this.textOrigin = paramInt;
/* 2380 */       geometryChangedTextValid();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setUnderline(boolean paramBoolean) {
/* 2385 */     if (this.attributes == null) {
/* 2386 */       if (!paramBoolean) {
/* 2387 */         return;
/*      */       }
/* 2389 */       this.attributes = new TextAttributes();
/*      */     }
/* 2391 */     if (paramBoolean != this.attributes.underline) {
/* 2392 */       this.attributes.underline = paramBoolean;
/* 2393 */       geometryChangedTextValid();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setStrikethrough(boolean paramBoolean) {
/* 2398 */     if (this.attributes == null) {
/* 2399 */       if (!paramBoolean) {
/* 2400 */         return;
/*      */       }
/* 2402 */       this.attributes = new TextAttributes();
/*      */     }
/* 2404 */     if (paramBoolean != this.attributes.strikethrough) {
/* 2405 */       this.attributes.strikethrough = paramBoolean;
/* 2406 */       geometryChangedTextValid();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setWrappingWidth(float paramFloat) {
/* 2411 */     if (this.attributes == null) {
/* 2412 */       if (paramFloat <= 0.0F) {
/* 2413 */         return;
/*      */       }
/* 2415 */       this.attributes = new TextAttributes();
/*      */     }
/* 2417 */     this.attributes.wrappingWidthSet = (paramFloat > 0.0F);
/* 2418 */     if (paramFloat != this.attributes.wrappingWidth) {
/* 2419 */       this.attributes.wrappingWidth = paramFloat;
/* 2420 */       geometryChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2437 */     return new StringBuilder().append("NGTextHelper : x=").append(this.x).append(" y=").append(this.y).append(LS).append(" fontSmoothingType = ").append(this.fontSmoothingType).append(LS).append(" font = ").append(this.font).append(LS).append(" textOrigin = ").append(this.textOrigin).append(LS).append(" fontSmoothingType = ").append(this.fontSmoothingType).append(LS).append(" textBoundsType = ").append(this.textBoundsType).append(LS).append(" locationIsChanged = ").append(this.locationIsChanged).append(LS).append(" geometryIsChanged = ").append(this.geometryIsChanged).append(LS).append(" cumulativeTransform = ").append(this.cumulativeTransform).append(LS).append(" mode = ").append(this.mode).append(LS).append(" doStroke = ").append(this.doStroke).append(LS).append(" valid = ").append(this.valid).append(LS).append(" fontTxStrike = ").append(this.fontStrike).append(LS).append(" fontIDStrike = ").append(this.identityStrike).append(LS).append(" strikeMat = [").append(this.strikeMat[0]).append(", ").append(this.strikeMat[1]).append(", ").append(this.strikeMat[2]).append(", ").append(this.strikeMat[3]).append("] ").append(LS).append(" geometryIsChanged = ").append(this.geometryIsChanged).append(LS).append(" maxLineAdvance = ").append(this.maxLineAdvance).append(LS).append(" visualBounds = ").append(this.visualBounds).append(LS).append(" logicalBounds = ").append(this.logicalBounds).append(LS).append(" yAdjCached = ").append(this.yAdjCached).append(" yAdjValid = ").append(this.yAdjValid).append(LS).append(" attributes = ").append(this.attributes == null ? " null " : this.attributes.toString()).append(LS).append(" selection = ").append(this.selection == null ? " null " : this.selection.toString()).toString();
/*      */   }
/*      */ 
/*      */   static class Selection
/*      */   {
/* 2428 */     int start = 0;
/* 2429 */     int end = 0;
/*      */     Paint drawPaint;
/*      */     Paint fillPaint;
/*      */     Shape shape;
/*      */ 
/*      */     boolean isEmpty()
/*      */     {
/* 2426 */       return this.start >= this.end;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class FloatList
/*      */   {
/*      */     private static final int LEN = 10;
/* 1941 */     float[] data = new float[10];
/* 1942 */     int index = 0;
/* 1943 */     int len = 10;
/*      */ 
/*      */     void add(float paramFloat) {
/* 1946 */       if (this.index == this.len) {
/* 1947 */         float[] arrayOfFloat = new float[this.len * 2];
/* 1948 */         int i = this.len * 2;
/* 1949 */         System.arraycopy(this.data, 0, arrayOfFloat, 0, this.len);
/* 1950 */         this.data = arrayOfFloat;
/* 1951 */         this.len = i;
/*      */       }
/* 1953 */       this.data[(this.index++)] = paramFloat;
/*      */     }
/*      */ 
/*      */     float[] getData() {
/* 1957 */       if (this.index == 0)
/*      */       {
/* 1959 */         return null;
/*      */       }
/* 1961 */       if (this.index == 10) {
/* 1962 */         return this.data;
/*      */       }
/* 1964 */       float[] arrayOfFloat = new float[this.index];
/* 1965 */       System.arraycopy(this.data, 0, arrayOfFloat, 0, this.index);
/* 1966 */       return arrayOfFloat;
/*      */     }
/*      */ 
/*      */     float getMax() {
/* 1970 */       float f = 0.0F;
/* 1971 */       for (int i = 0; i < this.index; i++) {
/* 1972 */         if (f < this.data[i]) {
/* 1973 */           f = this.data[i];
/*      */         }
/*      */       }
/* 1976 */       return f;
/*      */     }
/*      */ 
/*      */     static float getMin(float[] paramArrayOfFloat) {
/* 1980 */       if (paramArrayOfFloat == null) {
/* 1981 */         return 0.0F;
/*      */       }
/* 1983 */       float f1 = 0.0F;
/* 1984 */       for (float f2 : paramArrayOfFloat) {
/* 1985 */         if (f2 < f1) {
/* 1986 */           f1 = f2;
/*      */         }
/*      */       }
/* 1989 */       return f1;
/*      */     }
/*      */ 
/*      */     static float getMax(float[] paramArrayOfFloat) {
/* 1993 */       if (paramArrayOfFloat == null) {
/* 1994 */         return 0.0F;
/*      */       }
/* 1996 */       float f1 = 0.0F;
/* 1997 */       for (float f2 : paramArrayOfFloat) {
/* 1998 */         if (f2 > f1) {
/* 1999 */           f1 = f2;
/*      */         }
/*      */       }
/* 2002 */       return f1;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class IntList
/*      */   {
/*      */     private static final int LEN = 10;
/* 1892 */     int[] data = new int[10];
/* 1893 */     int index = 0;
/* 1894 */     int len = 10;
/*      */ 
/*      */     void add(int paramInt) {
/* 1897 */       if (this.index == this.len) {
/* 1898 */         int[] arrayOfInt = new int[this.len * 2];
/* 1899 */         int i = this.len * 2;
/* 1900 */         System.arraycopy(this.data, 0, arrayOfInt, 0, this.len);
/* 1901 */         this.data = arrayOfInt;
/* 1902 */         this.len = i;
/*      */       }
/* 1904 */       this.data[(this.index++)] = paramInt;
/*      */     }
/*      */ 
/*      */     int getAtIndex(int paramInt) {
/* 1908 */       return this.data[paramInt];
/*      */     }
/*      */ 
/*      */     int getCurrent()
/*      */     {
/* 1913 */       return this.index > 0 ? this.data[(this.index - 1)] : -1;
/*      */     }
/*      */ 
/*      */     boolean removeCurrent() {
/* 1917 */       if (this.index > 0) {
/* 1918 */         this.index -= 1;
/* 1919 */         return true;
/*      */       }
/* 1921 */       return false;
/*      */     }
/*      */ 
/*      */     int[] getData()
/*      */     {
/* 1926 */       if (this.index == 0)
/*      */       {
/* 1928 */         return null;
/*      */       }
/* 1930 */       if (this.index == 10) {
/* 1931 */         return this.data;
/*      */       }
/* 1933 */       int[] arrayOfInt = new int[this.index];
/* 1934 */       System.arraycopy(this.data, 0, arrayOfInt, 0, this.index);
/* 1935 */       return arrayOfInt;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class TextAttributes
/*      */   {
/*  297 */     float wrappingWidth = 0.0F;
/*  298 */     boolean wrappingWidthSet = false;
/*      */ 
/*  304 */     boolean overrideWrappingWidth = false;
/*      */ 
/*  306 */     int textAlignment = 0;
/*      */     boolean underline;
/*      */     boolean strikethrough;
/*      */     static final int X_MIN_INDEX = 0;
/*      */     static final int Y_MIN_INDEX = 1;
/*      */     static final int X_MAX_INDEX = 2;
/*      */     static final int Y_MAX_INDEX = 3;
/*      */     boolean hasTabs;
/*      */     float[] lineAdvances;
/*      */     int[] newLineIndices;
/*      */     float[] lsb;
/*      */     float[] rsb;
/*      */     Shape cachedShape;
/*      */ 
/*      */     int numberOfLines()
/*      */     {
/*  325 */       return this.newLineIndices == null ? 1 : this.newLineIndices.length;
/*      */     }
/*      */ 
/*      */     boolean simpleSingleLine()
/*      */     {
/*  333 */       if ((numberOfLines() != 1) || (this.hasTabs)) {
/*  334 */         return false;
/*      */       }
/*  336 */       return (this.textAlignment == 0) || (this.textAlignment == 3);
/*      */     }
/*      */ 
/*      */     boolean visualGreaterThanLogicalBounds()
/*      */     {
/*  341 */       return (this.lsb != null ? 1 : 0) | (this.rsb != null ? 1 : 0);
/*      */     }
/*      */ 
/*      */     float getMaxAdvance() {
/*  345 */       float f = 0.0F;
/*  346 */       if (this.lineAdvances == null) {
/*  347 */         return 0.0F;
/*      */       }
/*  349 */       int i = this.lineAdvances.length;
/*  350 */       for (int j = 0; j < i; j++) {
/*  351 */         if (f < this.lineAdvances[j]) {
/*  352 */           f = this.lineAdvances[j];
/*      */         }
/*      */       }
/*  355 */       return f;
/*      */     }
/*      */ 
/*      */     private boolean computeLinePadding(String paramString, FontStrike paramFontStrike)
/*      */     {
/*  369 */       float[] arrayOfFloat2 = new float[4];
/*  370 */       FontResource localFontResource = paramFontStrike.getFontResource();
/*  371 */       CharToGlyphMapper localCharToGlyphMapper = localFontResource.getGlyphMapper();
/*  372 */       float f1 = paramFontStrike.getSize();
/*      */       float[] arrayOfFloat1;
/*  373 */       if (numberOfLines() == 1) {
/*  374 */         arrayOfFloat1 = computeSideBearings(paramString, localFontResource, localCharToGlyphMapper, f1, arrayOfFloat2);
/*      */ 
/*  376 */         return (arrayOfFloat1[0] < 0.0F) || (arrayOfFloat1[1] > 0.0F);
/*  377 */       }if (this.newLineIndices == null) {
/*  378 */         return false;
/*      */       }
/*  380 */       int i = this.newLineIndices.length;
/*  381 */       float[] arrayOfFloat3 = new float[i];
/*  382 */       float[] arrayOfFloat4 = new float[i];
/*      */ 
/*  384 */       float f2 = 0.0F;
/*  385 */       float f3 = 0.0F;
/*      */ 
/*  387 */       int j = 0;
/*  388 */       for (int k = 0; k < i; k++) {
/*  389 */         int m = this.newLineIndices[k];
/*  390 */         if (j > m) {
/*      */           break;
/*      */         }
/*  393 */         arrayOfFloat1 = computeSideBearings(paramString.substring(j, m + 1), localFontResource, localCharToGlyphMapper, f1, arrayOfFloat2);
/*      */ 
/*  396 */         arrayOfFloat3[k] = arrayOfFloat1[0];
/*  397 */         arrayOfFloat4[k] = arrayOfFloat1[1];
/*  398 */         if (arrayOfFloat1[0] < f2) {
/*  399 */           f2 = arrayOfFloat1[0];
/*      */         }
/*  401 */         if (arrayOfFloat1[1] > f3) {
/*  402 */           f3 = arrayOfFloat1[0];
/*      */         }
/*      */ 
/*  405 */         j = this.newLineIndices[k] + 1;
/*      */       }
/*      */ 
/*  410 */       if ((f2 < 0.0F) || (f3 > 0.0F)) {
/*  411 */         this.lsb = arrayOfFloat3;
/*  412 */         this.rsb = arrayOfFloat4;
/*  413 */         return true;
/*      */       }
/*  415 */       return false;
/*      */     }
/*      */ 
/*      */     private static float[] computeSideBearings(String paramString, FontResource paramFontResource, CharToGlyphMapper paramCharToGlyphMapper, float paramFloat, float[] paramArrayOfFloat)
/*      */     {
/*  428 */       int i = 0;
/*  429 */       float[] arrayOfFloat = { 0.0F, 0.0F };
/*  430 */       int j = paramString.length() - 1;
/*  431 */       if (i > j) {
/*  432 */         return arrayOfFloat;
/*      */       }
/*      */ 
/*  436 */       char c = paramString.charAt(i);
/*  437 */       int k = paramCharToGlyphMapper.charToGlyph(c);
/*      */ 
/*  439 */       float f3 = 0.0F; float f4 = 0.0F;
/*      */ 
/*  446 */       while ((Character.isWhitespace(c)) && (i < j)) {
/*  447 */         f3 += paramFontResource.getAdvance(k, paramFloat);
/*  448 */         c = paramString.charAt(++i);
/*  449 */         k = paramCharToGlyphMapper.charToGlyph(c);
/*      */       }
/*      */ 
/*  452 */       paramFontResource.getGlyphBoundingBox(k, paramFloat, paramArrayOfFloat);
/*  453 */       paramArrayOfFloat[0] += f3;
/*      */ 
/*  455 */       float f1 = paramArrayOfFloat[0];
/*      */ 
/*  458 */       c = paramString.charAt(j);
/*  459 */       k = paramCharToGlyphMapper.charToGlyph(c);
/*  460 */       while ((Character.isWhitespace(c)) && (i < j)) {
/*  461 */         f4 += paramFontResource.getAdvance(k, paramFloat);
/*  462 */         c = paramString.charAt(--j);
/*  463 */         k = paramCharToGlyphMapper.charToGlyph(c);
/*      */       }
/*      */ 
/*  466 */       if (i != j) {
/*  467 */         paramFontResource.getGlyphBoundingBox(k, paramFloat, paramArrayOfFloat);
/*      */       }
/*      */ 
/*  470 */       f4 += paramFontResource.getAdvance(k, paramFloat);
/*  471 */       paramArrayOfFloat[2] += -f4;
/*      */ 
/*  473 */       float f2 = paramArrayOfFloat[2];
/*  474 */       arrayOfFloat[0] = f1;
/*  475 */       arrayOfFloat[1] = f2;
/*  476 */       return arrayOfFloat;
/*      */     }
/*      */ 
/*      */     void reset()
/*      */     {
/*  484 */       this.lineAdvances = null;
/*  485 */       this.newLineIndices = null;
/*  486 */       this.lsb = (this.rsb = null);
/*  487 */       if (!this.wrappingWidthSet) {
/*  488 */         this.wrappingWidth = 0.0F;
/*      */       }
/*  490 */       this.overrideWrappingWidth = false;
/*  491 */       this.hasTabs = false;
/*  492 */       resetShape();
/*      */     }
/*      */ 
/*      */     void resetShape() {
/*  496 */       this.cachedShape = null;
/*      */     }
/*      */ 
/*      */     public String toString() {
/*  500 */       return new StringBuilder().append("Attributes = textAlignment = ").append(this.textAlignment).append(NGTextHelper.LS).append("underline = ").append(this.underline).append(NGTextHelper.LS).append("strikethrough = ").append(this.strikethrough).append(NGTextHelper.LS).append("wrappingWidth = ").append(this.wrappingWidth).append(NGTextHelper.LS).append("wrappingWidthSet = ").append(this.wrappingWidthSet).append(NGTextHelper.LS).append("overrideWrappingWidth = ").append(this.overrideWrappingWidth).append(NGTextHelper.LS).append("hasTabs = ").append(this.hasTabs).append(NGTextHelper.LS).append("cachedShape = ").append(this.cachedShape).append(NGTextHelper.LS).append("wrappingWidthSet = ").append(this.wrappingWidthSet).append(NGTextHelper.LS).append("lineAdvances = ").append(this.lineAdvances == null ? "null" : new StringBuilder().append("Array of length ").append(this.lineAdvances.length).toString()).append(NGTextHelper.LS).append("newLineIndices = ").append(this.newLineIndices == null ? "null" : new StringBuilder().append("Array of length ").append(this.newLineIndices.length).toString()).append(NGTextHelper.LS).append("lsb = ").append(this.lsb == null ? "null" : new StringBuilder().append("Array of length ").append(this.lsb.length).toString()).append(NGTextHelper.LS).append("rsb = ").append(this.rsb == null ? "null" : new StringBuilder().append("Array of length ").append(this.rsb.length).toString()).toString();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGTextHelper
 * JD-Core Version:    0.6.2
 */