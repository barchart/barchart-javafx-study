/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.text.Font;
/*     */ import javafx.scene.text.TextAlignment;
/*     */ 
/*     */ public class TooltipBuilder<B extends TooltipBuilder<B>> extends PopupControlBuilder<B>
/*     */ {
/*     */   private int __set;
/*     */   private ContentDisplay contentDisplay;
/*     */   private Font font;
/*     */   private Node graphic;
/*     */   private double graphicTextGap;
/*     */   private String text;
/*     */   private TextAlignment textAlignment;
/*     */   private OverrunStyle textOverrun;
/*     */   private boolean wrapText;
/*     */ 
/*     */   public static TooltipBuilder<?> create()
/*     */   {
/*  15 */     return new TooltipBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Tooltip paramTooltip) {
/*  23 */     super.applyTo(paramTooltip);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramTooltip.setContentDisplay(this.contentDisplay); break;
/*     */       case 1:
/*  30 */         paramTooltip.setFont(this.font); break;
/*     */       case 2:
/*  31 */         paramTooltip.setGraphic(this.graphic); break;
/*     */       case 3:
/*  32 */         paramTooltip.setGraphicTextGap(this.graphicTextGap); break;
/*     */       case 4:
/*  33 */         paramTooltip.setText(this.text); break;
/*     */       case 5:
/*  34 */         paramTooltip.setTextAlignment(this.textAlignment); break;
/*     */       case 6:
/*  35 */         paramTooltip.setTextOverrun(this.textOverrun); break;
/*     */       case 7:
/*  36 */         paramTooltip.setWrapText(this.wrapText);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B contentDisplay(ContentDisplay paramContentDisplay)
/*     */   {
/*  47 */     this.contentDisplay = paramContentDisplay;
/*  48 */     __set(0);
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B font(Font paramFont)
/*     */   {
/*  58 */     this.font = paramFont;
/*  59 */     __set(1);
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B graphic(Node paramNode)
/*     */   {
/*  69 */     this.graphic = paramNode;
/*  70 */     __set(2);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B graphicTextGap(double paramDouble)
/*     */   {
/*  80 */     this.graphicTextGap = paramDouble;
/*  81 */     __set(3);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B text(String paramString)
/*     */   {
/*  91 */     this.text = paramString;
/*  92 */     __set(4);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B textAlignment(TextAlignment paramTextAlignment)
/*     */   {
/* 102 */     this.textAlignment = paramTextAlignment;
/* 103 */     __set(5);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public B textOverrun(OverrunStyle paramOverrunStyle)
/*     */   {
/* 113 */     this.textOverrun = paramOverrunStyle;
/* 114 */     __set(6);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public B wrapText(boolean paramBoolean)
/*     */   {
/* 124 */     this.wrapText = paramBoolean;
/* 125 */     __set(7);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   public Tooltip build()
/*     */   {
/* 133 */     Tooltip localTooltip = new Tooltip();
/* 134 */     applyTo(localTooltip);
/* 135 */     return localTooltip;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TooltipBuilder
 * JD-Core Version:    0.6.2
 */