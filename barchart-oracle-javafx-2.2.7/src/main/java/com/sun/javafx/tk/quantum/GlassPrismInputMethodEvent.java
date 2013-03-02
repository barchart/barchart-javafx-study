/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.input.InputMethodHighlight;
/*     */ import javafx.scene.input.InputMethodTextRun;
/*     */ 
/*     */ public class GlassPrismInputMethodEvent
/*     */   implements PrismEvent
/*     */ {
/*     */   ViewScene view;
/*     */   String text;
/*     */   int[] clauseBoundary;
/*     */   int[] attrBoundary;
/*     */   byte[] attrValue;
/*     */   int commitCount;
/*     */   int cursorPos;
/*     */   private static final byte ATTR_INPUT = 0;
/*     */   private static final byte ATTR_TARGET_CONVERTED = 1;
/*     */   private static final byte ATTR_CONVERTED = 2;
/*     */   private static final byte ATTR_TARGET_NOTCONVERTED = 3;
/*     */   private static final byte ATTR_INPUT_ERROR = 4;
/*     */ 
/*     */   public void handleInputMethodEvent(long paramLong, String paramString, int[] paramArrayOfInt1, int[] paramArrayOfInt2, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected GlassPrismInputMethodEvent(ViewScene paramViewScene, String paramString, int[] paramArrayOfInt1, int[] paramArrayOfInt2, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  45 */     this.view = paramViewScene;
/*  46 */     this.text = (paramString == null ? "" : paramString);
/*  47 */     this.clauseBoundary = paramArrayOfInt1;
/*  48 */     this.attrBoundary = paramArrayOfInt2;
/*  49 */     this.attrValue = paramArrayOfByte;
/*  50 */     this.commitCount = paramInt1;
/*  51 */     this.cursorPos = paramInt2;
/*     */   }
/*     */ 
/*     */   protected ViewScene view() {
/*  55 */     return this.view;
/*     */   }
/*     */ 
/*     */   protected ObservableList<InputMethodTextRun> getComposed() {
/*  59 */     TrackableObservableList local1 = new TrackableObservableList()
/*     */     {
/*     */       protected void onChanged(ListChangeListener.Change<InputMethodTextRun> paramAnonymousChange)
/*     */       {
/*     */       }
/*     */     };
/*  66 */     if (this.commitCount < this.text.length()) {
/*  67 */       if (this.clauseBoundary == null)
/*     */       {
/*  69 */         local1.add(InputMethodTextRun.impl_inputMethodTextRun(this.text.substring(this.commitCount), InputMethodHighlight.UNSELECTED_RAW));
/*     */       }
/*     */       else
/*     */       {
/*  73 */         for (int i = 0; i < this.clauseBoundary.length - 1; i++) {
/*  74 */           if (this.clauseBoundary[i] >= this.commitCount)
/*     */           {
/*     */             InputMethodHighlight localInputMethodHighlight;
/*  79 */             switch (getAttrValue(this.clauseBoundary[i])) {
/*     */             case 1:
/*  81 */               localInputMethodHighlight = InputMethodHighlight.SELECTED_CONVERTED;
/*  82 */               break;
/*     */             case 2:
/*  84 */               localInputMethodHighlight = InputMethodHighlight.UNSELECTED_CONVERTED;
/*  85 */               break;
/*     */             case 3:
/*  87 */               localInputMethodHighlight = InputMethodHighlight.SELECTED_RAW;
/*  88 */               break;
/*     */             case 0:
/*     */             case 4:
/*     */             default:
/*  92 */               localInputMethodHighlight = InputMethodHighlight.UNSELECTED_RAW;
/*     */             }
/*     */ 
/*  95 */             local1.add(InputMethodTextRun.impl_inputMethodTextRun(this.text.substring(this.clauseBoundary[i], this.clauseBoundary[(i + 1)]), localInputMethodHighlight));
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 102 */     return local1;
/*     */   }
/*     */ 
/*     */   protected String getCommitted() {
/* 106 */     return this.text.substring(0, this.commitCount);
/*     */   }
/*     */ 
/*     */   protected int getCursorPos() {
/* 110 */     return this.cursorPos;
/*     */   }
/*     */ 
/*     */   private byte getAttrValue(int paramInt) {
/* 114 */     byte b = 4;
/*     */ 
/* 116 */     if (this.attrBoundary != null) {
/* 117 */       for (int i = 0; i < this.attrBoundary.length - 1; i++) {
/* 118 */         if ((paramInt >= this.attrBoundary[i]) && (paramInt < this.attrBoundary[(i + 1)]))
/*     */         {
/* 120 */           b = this.attrValue[i];
/* 121 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 126 */     return b;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassPrismInputMethodEvent
 * JD-Core Version:    0.6.2
 */