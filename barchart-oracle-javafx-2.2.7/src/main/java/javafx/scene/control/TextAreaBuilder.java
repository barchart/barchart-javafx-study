/*     */ package javafx.scene.control;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class TextAreaBuilder<B extends TextAreaBuilder<B>> extends TextInputControlBuilder<B>
/*     */   implements Builder<TextArea>
/*     */ {
/*     */   private int __set;
/*     */   private Collection<? extends CharSequence> paragraphs;
/*     */   private int prefColumnCount;
/*     */   private int prefRowCount;
/*     */   private String promptText;
/*     */   private double scrollLeft;
/*     */   private double scrollTop;
/*     */   private boolean wrapText;
/*     */ 
/*     */   public static TextAreaBuilder<?> create()
/*     */   {
/*  15 */     return new TextAreaBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(TextArea paramTextArea)
/*     */   {
/*  20 */     super.applyTo(paramTextArea);
/*  21 */     int i = this.__set;
/*  22 */     if ((i & 0x1) != 0) paramTextArea.getParagraphs().addAll(this.paragraphs);
/*  23 */     if ((i & 0x2) != 0) paramTextArea.setPrefColumnCount(this.prefColumnCount);
/*  24 */     if ((i & 0x4) != 0) paramTextArea.setPrefRowCount(this.prefRowCount);
/*  25 */     if ((i & 0x8) != 0) paramTextArea.setPromptText(this.promptText);
/*  26 */     if ((i & 0x10) != 0) paramTextArea.setScrollLeft(this.scrollLeft);
/*  27 */     if ((i & 0x20) != 0) paramTextArea.setScrollTop(this.scrollTop);
/*  28 */     if ((i & 0x40) != 0) paramTextArea.setWrapText(this.wrapText);
/*     */   }
/*     */ 
/*     */   public B paragraphs(Collection<? extends CharSequence> paramCollection)
/*     */   {
/*  37 */     this.paragraphs = paramCollection;
/*  38 */     this.__set |= 1;
/*  39 */     return this;
/*     */   }
/*     */ 
/*     */   public B paragraphs(CharSequence[] paramArrayOfCharSequence)
/*     */   {
/*  46 */     return paragraphs(Arrays.asList(paramArrayOfCharSequence));
/*     */   }
/*     */ 
/*     */   public B prefColumnCount(int paramInt)
/*     */   {
/*  55 */     this.prefColumnCount = paramInt;
/*  56 */     this.__set |= 2;
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefRowCount(int paramInt)
/*     */   {
/*  66 */     this.prefRowCount = paramInt;
/*  67 */     this.__set |= 4;
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */   public B promptText(String paramString)
/*     */   {
/*  77 */     this.promptText = paramString;
/*  78 */     this.__set |= 8;
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   public B scrollLeft(double paramDouble)
/*     */   {
/*  88 */     this.scrollLeft = paramDouble;
/*  89 */     this.__set |= 16;
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   public B scrollTop(double paramDouble)
/*     */   {
/*  99 */     this.scrollTop = paramDouble;
/* 100 */     this.__set |= 32;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public B wrapText(boolean paramBoolean)
/*     */   {
/* 110 */     this.wrapText = paramBoolean;
/* 111 */     this.__set |= 64;
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   public TextArea build()
/*     */   {
/* 119 */     TextArea localTextArea = new TextArea();
/* 120 */     applyTo(localTextArea);
/* 121 */     return localTextArea;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TextAreaBuilder
 * JD-Core Version:    0.6.2
 */