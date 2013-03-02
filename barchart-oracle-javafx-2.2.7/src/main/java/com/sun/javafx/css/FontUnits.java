/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import javafx.scene.text.FontPosture;
/*     */ import javafx.scene.text.FontWeight;
/*     */ 
/*     */ public final class FontUnits
/*     */ {
/*     */   public static enum Weight
/*     */   {
/*  83 */     NORMAL(FontWeight.NORMAL), 
/*  84 */     BOLD(FontWeight.BOLD), 
/*  85 */     BOLDER, 
/*  86 */     LIGHTER, 
/*  87 */     INHERIT, 
/*  88 */     SCALE_100(FontWeight.THIN), 
/*  89 */     SCALE_200(FontWeight.EXTRA_LIGHT), 
/*  90 */     SCALE_300(FontWeight.LIGHT), 
/*  91 */     SCALE_400(FontWeight.NORMAL), 
/*  92 */     SCALE_500(FontWeight.MEDIUM), 
/*  93 */     SCALE_600(FontWeight.SEMI_BOLD), 
/*  94 */     SCALE_700(FontWeight.BOLD), 
/*  95 */     SCALE_800(FontWeight.EXTRA_BOLD), 
/*  96 */     SCALE_900(FontWeight.BLACK);
/*     */ 
/*     */     FontWeight weight;
/*     */ 
/*     */     private Weight() {
/* 101 */       this.weight = FontWeight.NORMAL;
/*     */     }
/*     */ 
/*     */     private Weight(FontWeight paramFontWeight) {
/* 105 */       this.weight = paramFontWeight;
/*     */     }
/*     */ 
/*     */     public static Weight toWeight(FontWeight paramFontWeight) {
/* 109 */       Weight localWeight = null;
/* 110 */       if (FontWeight.THIN == paramFontWeight)
/* 111 */         localWeight = SCALE_100;
/* 112 */       else if (FontWeight.EXTRA_LIGHT == paramFontWeight)
/* 113 */         localWeight = SCALE_200;
/* 114 */       else if (FontWeight.LIGHT == paramFontWeight)
/* 115 */         localWeight = SCALE_300;
/* 116 */       else if (FontWeight.NORMAL == paramFontWeight)
/* 117 */         localWeight = SCALE_400;
/* 118 */       else if (FontWeight.MEDIUM == paramFontWeight)
/* 119 */         localWeight = SCALE_500;
/* 120 */       else if (FontWeight.SEMI_BOLD == paramFontWeight)
/* 121 */         localWeight = SCALE_600;
/* 122 */       else if (FontWeight.BOLD == paramFontWeight)
/* 123 */         localWeight = SCALE_700;
/* 124 */       else if (FontWeight.EXTRA_BOLD == paramFontWeight)
/* 125 */         localWeight = SCALE_800;
/* 126 */       else if (FontWeight.BLACK == paramFontWeight)
/* 127 */         localWeight = SCALE_900;
/*     */       else {
/* 129 */         localWeight = SCALE_400;
/*     */       }
/* 131 */       return localWeight;
/*     */     }
/*     */ 
/*     */     public FontWeight toFontWeight() {
/* 135 */       return this.weight;
/*     */     }
/*     */ 
/*     */     public String toString() {
/* 139 */       return this.weight.toString();
/*     */     }
/*     */ 
/*     */     public Weight bolder() {
/* 143 */       Weight localWeight = NORMAL;
/* 144 */       switch (FontUnits.1.$SwitchMap$com$sun$javafx$css$FontUnits$Weight[ordinal()]) {
/*     */       case 1:
/* 146 */         localWeight = SCALE_200;
/* 147 */         break;
/*     */       case 2:
/* 149 */         localWeight = SCALE_300;
/* 150 */         break;
/*     */       case 3:
/* 152 */         localWeight = SCALE_400;
/* 153 */         break;
/*     */       case 4:
/*     */       case 5:
/*     */       default:
/* 157 */         localWeight = SCALE_500;
/* 158 */         break;
/*     */       case 6:
/* 160 */         localWeight = SCALE_600;
/* 161 */         break;
/*     */       case 7:
/* 163 */         localWeight = SCALE_700;
/* 164 */         break;
/*     */       case 8:
/*     */       case 9:
/* 167 */         localWeight = SCALE_800;
/* 168 */         break;
/*     */       case 10:
/*     */       case 11:
/* 171 */         localWeight = SCALE_900;
/*     */       }
/*     */ 
/* 174 */       return localWeight;
/*     */     }
/*     */ 
/*     */     public Weight lighter() {
/* 178 */       Weight localWeight = NORMAL;
/* 179 */       switch (FontUnits.1.$SwitchMap$com$sun$javafx$css$FontUnits$Weight[ordinal()]) {
/*     */       case 1:
/*     */       case 2:
/* 182 */         localWeight = SCALE_100;
/* 183 */         break;
/*     */       case 3:
/* 185 */         localWeight = SCALE_200;
/* 186 */         break;
/*     */       case 4:
/*     */       case 5:
/*     */       default:
/* 190 */         localWeight = SCALE_300;
/* 191 */         break;
/*     */       case 6:
/* 193 */         localWeight = SCALE_400;
/* 194 */         break;
/*     */       case 7:
/* 196 */         localWeight = SCALE_500;
/* 197 */         break;
/*     */       case 8:
/*     */       case 9:
/* 200 */         localWeight = SCALE_600;
/* 201 */         break;
/*     */       case 10:
/* 203 */         localWeight = SCALE_700;
/* 204 */         break;
/*     */       case 11:
/* 206 */         localWeight = SCALE_800;
/*     */       }
/*     */ 
/* 209 */       return localWeight;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum Style
/*     */   {
/*  55 */     NORMAL(FontPosture.REGULAR), 
/*  56 */     ITALIC(FontPosture.ITALIC), 
/*  57 */     OBLIQUE(FontPosture.ITALIC), 
/*  58 */     INHERIT;
/*     */ 
/*     */     FontPosture posture;
/*     */ 
/*     */     private Style() {
/*  63 */       this.posture = FontPosture.REGULAR;
/*     */     }
/*     */ 
/*     */     private Style(FontPosture paramFontPosture) {
/*  67 */       this.posture = paramFontPosture;
/*     */     }
/*     */ 
/*     */     public FontPosture toFontPosture() {
/*  71 */       return this.posture;
/*     */     }
/*     */ 
/*     */     public String toString() {
/*  75 */       return this.posture.toString();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.FontUnits
 * JD-Core Version:    0.6.2
 */