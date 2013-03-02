/*     */ package javafx.scene.text;
/*     */ 
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.shape.PathElement;
/*     */ import javafx.scene.shape.ShapeBuilder;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class TextBuilder<B extends TextBuilder<B>> extends ShapeBuilder<B>
/*     */   implements Builder<Text>
/*     */ {
/*     */   private int __set;
/*     */   private TextBoundsType boundsType;
/*     */   private Font font;
/*     */   private FontSmoothingType fontSmoothingType;
/*     */   private boolean impl_caretBias;
/*     */   private int impl_caretPosition;
/*     */   private PathElement[] impl_caretShape;
/*     */   private int impl_selectionEnd;
/*     */   private PathElement[] impl_selectionShape;
/*     */   private int impl_selectionStart;
/*     */   private boolean strikethrough;
/*     */   private String text;
/*     */   private TextAlignment textAlignment;
/*     */   private VPos textOrigin;
/*     */   private boolean underline;
/*     */   private double wrappingWidth;
/*     */   private double x;
/*     */   private double y;
/*     */ 
/*     */   public static TextBuilder<?> create()
/*     */   {
/*  15 */     return new TextBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Text paramText) {
/*  23 */     super.applyTo(paramText);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramText.setBoundsType(this.boundsType); break;
/*     */       case 1:
/*  30 */         paramText.setFont(this.font); break;
/*     */       case 2:
/*  31 */         paramText.setFontSmoothingType(this.fontSmoothingType); break;
/*     */       case 3:
/*  32 */         paramText.setImpl_caretBias(this.impl_caretBias); break;
/*     */       case 4:
/*  33 */         paramText.setImpl_caretPosition(this.impl_caretPosition); break;
/*     */       case 5:
/*  34 */         paramText.setImpl_caretShape(this.impl_caretShape); break;
/*     */       case 6:
/*  35 */         paramText.setImpl_selectionEnd(this.impl_selectionEnd); break;
/*     */       case 7:
/*  36 */         paramText.setImpl_selectionShape(this.impl_selectionShape); break;
/*     */       case 8:
/*  37 */         paramText.setImpl_selectionStart(this.impl_selectionStart); break;
/*     */       case 9:
/*  38 */         paramText.setStrikethrough(this.strikethrough); break;
/*     */       case 10:
/*  39 */         paramText.setText(this.text); break;
/*     */       case 11:
/*  40 */         paramText.setTextAlignment(this.textAlignment); break;
/*     */       case 12:
/*  41 */         paramText.setTextOrigin(this.textOrigin); break;
/*     */       case 13:
/*  42 */         paramText.setUnderline(this.underline); break;
/*     */       case 14:
/*  43 */         paramText.setWrappingWidth(this.wrappingWidth); break;
/*     */       case 15:
/*  44 */         paramText.setX(this.x); break;
/*     */       case 16:
/*  45 */         paramText.setY(this.y);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B boundsType(TextBoundsType paramTextBoundsType)
/*     */   {
/*  56 */     this.boundsType = paramTextBoundsType;
/*  57 */     __set(0);
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */   public B font(Font paramFont)
/*     */   {
/*  67 */     this.font = paramFont;
/*  68 */     __set(1);
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */   public B fontSmoothingType(FontSmoothingType paramFontSmoothingType)
/*     */   {
/*  78 */     this.fontSmoothingType = paramFontSmoothingType;
/*  79 */     __set(2);
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public B impl_caretBias(boolean paramBoolean)
/*     */   {
/*  91 */     this.impl_caretBias = paramBoolean;
/*  92 */     __set(3);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public B impl_caretPosition(int paramInt)
/*     */   {
/* 104 */     this.impl_caretPosition = paramInt;
/* 105 */     __set(4);
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public B impl_caretShape(PathElement[] paramArrayOfPathElement)
/*     */   {
/* 117 */     this.impl_caretShape = paramArrayOfPathElement;
/* 118 */     __set(5);
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public B impl_selectionEnd(int paramInt)
/*     */   {
/* 130 */     this.impl_selectionEnd = paramInt;
/* 131 */     __set(6);
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public B impl_selectionShape(PathElement[] paramArrayOfPathElement)
/*     */   {
/* 143 */     this.impl_selectionShape = paramArrayOfPathElement;
/* 144 */     __set(7);
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public B impl_selectionStart(int paramInt)
/*     */   {
/* 156 */     this.impl_selectionStart = paramInt;
/* 157 */     __set(8);
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   public B strikethrough(boolean paramBoolean)
/*     */   {
/* 167 */     this.strikethrough = paramBoolean;
/* 168 */     __set(9);
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   public B text(String paramString)
/*     */   {
/* 178 */     this.text = paramString;
/* 179 */     __set(10);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public B textAlignment(TextAlignment paramTextAlignment)
/*     */   {
/* 189 */     this.textAlignment = paramTextAlignment;
/* 190 */     __set(11);
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   public B textOrigin(VPos paramVPos)
/*     */   {
/* 200 */     this.textOrigin = paramVPos;
/* 201 */     __set(12);
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   public B underline(boolean paramBoolean)
/*     */   {
/* 211 */     this.underline = paramBoolean;
/* 212 */     __set(13);
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */   public B wrappingWidth(double paramDouble)
/*     */   {
/* 222 */     this.wrappingWidth = paramDouble;
/* 223 */     __set(14);
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */   public B x(double paramDouble)
/*     */   {
/* 233 */     this.x = paramDouble;
/* 234 */     __set(15);
/* 235 */     return this;
/*     */   }
/*     */ 
/*     */   public B y(double paramDouble)
/*     */   {
/* 244 */     this.y = paramDouble;
/* 245 */     __set(16);
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */   public Text build()
/*     */   {
/* 253 */     Text localText = new Text();
/* 254 */     applyTo(localText);
/* 255 */     return localText;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.text.TextBuilder
 * JD-Core Version:    0.6.2
 */