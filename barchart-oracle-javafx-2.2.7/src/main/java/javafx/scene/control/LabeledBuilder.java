/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.paint.Paint;
/*     */ import javafx.scene.text.Font;
/*     */ import javafx.scene.text.TextAlignment;
/*     */ 
/*     */ public abstract class LabeledBuilder<B extends LabeledBuilder<B>> extends ControlBuilder<B>
/*     */ {
/*     */   private int __set;
/*     */   private Pos alignment;
/*     */   private ContentDisplay contentDisplay;
/*     */   private String ellipsisString;
/*     */   private Font font;
/*     */   private Node graphic;
/*     */   private double graphicTextGap;
/*     */   private boolean mnemonicParsing;
/*     */   private String text;
/*     */   private TextAlignment textAlignment;
/*     */   private Paint textFill;
/*     */   private OverrunStyle textOverrun;
/*     */   private boolean underline;
/*     */   private boolean wrapText;
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  15 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Labeled paramLabeled) {
/*  18 */     super.applyTo(paramLabeled);
/*  19 */     int i = this.__set;
/*  20 */     while (i != 0) {
/*  21 */       int j = Integer.numberOfTrailingZeros(i);
/*  22 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  23 */       switch (j) { case 0:
/*  24 */         paramLabeled.setAlignment(this.alignment); break;
/*     */       case 1:
/*  25 */         paramLabeled.setContentDisplay(this.contentDisplay); break;
/*     */       case 2:
/*  26 */         paramLabeled.setEllipsisString(this.ellipsisString); break;
/*     */       case 3:
/*  27 */         paramLabeled.setFont(this.font); break;
/*     */       case 4:
/*  28 */         paramLabeled.setGraphic(this.graphic); break;
/*     */       case 5:
/*  29 */         paramLabeled.setGraphicTextGap(this.graphicTextGap); break;
/*     */       case 6:
/*  30 */         paramLabeled.setMnemonicParsing(this.mnemonicParsing); break;
/*     */       case 7:
/*  31 */         paramLabeled.setText(this.text); break;
/*     */       case 8:
/*  32 */         paramLabeled.setTextAlignment(this.textAlignment); break;
/*     */       case 9:
/*  33 */         paramLabeled.setTextFill(this.textFill); break;
/*     */       case 10:
/*  34 */         paramLabeled.setTextOverrun(this.textOverrun); break;
/*     */       case 11:
/*  35 */         paramLabeled.setUnderline(this.underline); break;
/*     */       case 12:
/*  36 */         paramLabeled.setWrapText(this.wrapText);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B alignment(Pos paramPos)
/*     */   {
/*  47 */     this.alignment = paramPos;
/*  48 */     __set(0);
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B contentDisplay(ContentDisplay paramContentDisplay)
/*     */   {
/*  58 */     this.contentDisplay = paramContentDisplay;
/*  59 */     __set(1);
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B ellipsisString(String paramString)
/*     */   {
/*  69 */     this.ellipsisString = paramString;
/*  70 */     __set(2);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B font(Font paramFont)
/*     */   {
/*  80 */     this.font = paramFont;
/*  81 */     __set(3);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B graphic(Node paramNode)
/*     */   {
/*  91 */     this.graphic = paramNode;
/*  92 */     __set(4);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B graphicTextGap(double paramDouble)
/*     */   {
/* 102 */     this.graphicTextGap = paramDouble;
/* 103 */     __set(5);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public B mnemonicParsing(boolean paramBoolean)
/*     */   {
/* 113 */     this.mnemonicParsing = paramBoolean;
/* 114 */     __set(6);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public B text(String paramString)
/*     */   {
/* 124 */     this.text = paramString;
/* 125 */     __set(7);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   public B textAlignment(TextAlignment paramTextAlignment)
/*     */   {
/* 135 */     this.textAlignment = paramTextAlignment;
/* 136 */     __set(8);
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   public B textFill(Paint paramPaint)
/*     */   {
/* 146 */     this.textFill = paramPaint;
/* 147 */     __set(9);
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   public B textOverrun(OverrunStyle paramOverrunStyle)
/*     */   {
/* 157 */     this.textOverrun = paramOverrunStyle;
/* 158 */     __set(10);
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   public B underline(boolean paramBoolean)
/*     */   {
/* 168 */     this.underline = paramBoolean;
/* 169 */     __set(11);
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */   public B wrapText(boolean paramBoolean)
/*     */   {
/* 179 */     this.wrapText = paramBoolean;
/* 180 */     __set(12);
/* 181 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.LabeledBuilder
 * JD-Core Version:    0.6.2
 */