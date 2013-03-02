/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.scene.control.behavior.TextBinding;
/*     */ import com.sun.javafx.scene.text.HitInfo;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.MenuItem;
/*     */ import javafx.scene.control.OverrunStyle;
/*     */ import javafx.scene.control.Skin;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyCodeCombination;
/*     */ import javafx.scene.input.KeyCombination;
/*     */ import javafx.scene.input.KeyCombination.Modifier;
/*     */ import javafx.scene.input.Mnemonic;
/*     */ import javafx.scene.text.Font;
/*     */ import javafx.scene.text.Text;
/*     */ 
/*     */ public class Utils
/*     */ {
/*  52 */   static Text helper = new Text();
/*     */ 
/*     */   static double computeTextWidth(Font paramFont, String paramString, double paramDouble) {
/*  55 */     helper.setText(paramString);
/*  56 */     helper.setFont(paramFont);
/*     */ 
/*  59 */     helper.setWrappingWidth(0.0D);
/*  60 */     double d = Math.min(helper.prefWidth(-1.0D), paramDouble);
/*  61 */     helper.setWrappingWidth((int)Math.ceil(d));
/*  62 */     return Math.ceil(helper.getLayoutBounds().getWidth());
/*     */   }
/*     */ 
/*     */   static double computeTextHeight(Font paramFont, String paramString, double paramDouble) {
/*  66 */     helper.setText(paramString);
/*  67 */     helper.setFont(paramFont);
/*  68 */     helper.setWrappingWidth((int)paramDouble);
/*  69 */     return helper.getLayoutBounds().getHeight();
/*     */   }
/*     */ 
/*     */   static String computeClippedText(Font paramFont, String paramString1, double paramDouble, OverrunStyle paramOverrunStyle, String paramString2)
/*     */   {
/*  74 */     if (paramFont == null) {
/*  75 */       throw new IllegalArgumentException("Must specify a font");
/*     */     }
/*  77 */     OverrunStyle localOverrunStyle = (paramOverrunStyle == null) || (OverrunStyle.CLIP.equals(paramOverrunStyle)) ? OverrunStyle.ELLIPSIS : paramOverrunStyle;
/*  78 */     String str1 = OverrunStyle.CLIP.equals(paramOverrunStyle) ? "" : paramString2;
/*     */ 
/*  80 */     if ((paramString1 == null) || ("".equals(paramString1))) {
/*  81 */       return paramString1;
/*     */     }
/*     */ 
/*  89 */     double d1 = computeTextWidth(paramFont, paramString1, 0.0D);
/*  90 */     if (d1 - paramDouble < 0.001000000047497451D) {
/*  91 */       return paramString1;
/*     */     }
/*     */ 
/*  94 */     double d2 = computeTextWidth(paramFont, str1, 0.0D);
/*     */ 
/*  97 */     double d3 = paramDouble - d2;
/*     */ 
/*  99 */     if (paramDouble < d2)
/*     */     {
/* 101 */       return "";
/*     */     }
/*     */     int i3;
/*     */     int i4;
/* 120 */     if ((localOverrunStyle.equals(OverrunStyle.ELLIPSIS)) || (localOverrunStyle.equals(OverrunStyle.WORD_ELLIPSIS)) || (localOverrunStyle.equals(OverrunStyle.LEADING_ELLIPSIS)) || (localOverrunStyle.equals(OverrunStyle.LEADING_WORD_ELLIPSIS)))
/*     */     {
/* 125 */       i = (OverrunStyle.WORD_ELLIPSIS.equals(localOverrunStyle)) || (OverrunStyle.LEADING_WORD_ELLIPSIS.equals(localOverrunStyle)) ? 1 : 0;
/*     */ 
/* 136 */       double d4 = 0.0D;
/* 137 */       int n = -1;
/*     */ 
/* 140 */       i1 = 0;
/* 141 */       i2 = (localOverrunStyle.equals(OverrunStyle.LEADING_ELLIPSIS)) || (localOverrunStyle.equals(OverrunStyle.LEADING_WORD_ELLIPSIS)) ? paramString1.length() - 1 : 0;
/* 142 */       i3 = i2 == 0 ? paramString1.length() - 1 : 0;
/* 143 */       i4 = i2 == 0 ? 1 : -1;
/* 144 */       int i5 = i2 < i3 ? 1 : i2 == 0 ? 0 : i2 > i3 ? 1 : 0;
/* 145 */       for (int i6 = i2; i5 == 0; i6 += i4) {
/* 146 */         i1 = i6;
/* 147 */         char c2 = paramString1.charAt(i1);
/* 148 */         d4 = computeTextWidth(paramFont, i2 == 0 ? paramString1.substring(0, i6 + 1) : paramString1.substring(i6, i2 + 1), 0.0D);
/*     */ 
/* 152 */         if (Character.isWhitespace(c2)) {
/* 153 */           n = i1;
/*     */         }
/* 155 */         if (d4 > d3) {
/*     */           break;
/*     */         }
/* 158 */         i5 = i6 <= i3 ? 1 : i2 == 0 ? 0 : i6 >= i3 ? 1 : 0;
/*     */       }
/* 160 */       int i7 = (i == 0) || (n == -1) ? 1 : 0;
/* 161 */       String str2 = i2 == 0 ? paramString1.substring(0, i7 != 0 ? i1 : n) : paramString1.substring((i7 != 0 ? i1 : n) + 1);
/*     */ 
/* 166 */       if ((OverrunStyle.ELLIPSIS.equals(localOverrunStyle)) || (OverrunStyle.WORD_ELLIPSIS.equals(localOverrunStyle))) {
/* 167 */         return str2 + str1;
/*     */       }
/*     */ 
/* 170 */       return str1 + str2;
/*     */     }
/*     */ 
/* 174 */     int i = 0;
/* 175 */     int j = 0;
/* 176 */     int k = -1;
/* 177 */     int m = -1;
/*     */ 
/* 208 */     i = -1;
/* 209 */     j = -1;
/* 210 */     double d5 = 0.0D;
/* 211 */     for (int i2 = 0; i2 <= paramString1.length() - 1; i2++) {
/* 212 */       i3 = paramString1.charAt(i2);
/*     */ 
/* 214 */       d5 += computeTextWidth(paramFont, "" + i3, 0.0D);
/* 215 */       if (d5 > d3) {
/*     */         break;
/*     */       }
/* 218 */       i = i2;
/* 219 */       if (Character.isWhitespace(i3)) {
/* 220 */         k = i;
/*     */       }
/* 222 */       i4 = paramString1.length() - 1 - i2;
/* 223 */       char c1 = paramString1.charAt(i4);
/*     */ 
/* 225 */       d5 += computeTextWidth(paramFont, "" + c1, 0.0D);
/* 226 */       if (d5 > d3) {
/*     */         break;
/*     */       }
/* 229 */       j = i4;
/* 230 */       if (Character.isWhitespace(c1)) {
/* 231 */         m = j;
/*     */       }
/*     */     }
/*     */ 
/* 235 */     if (i < 0) {
/* 236 */       return str1;
/*     */     }
/* 238 */     if (OverrunStyle.CENTER_ELLIPSIS.equals(localOverrunStyle)) {
/* 239 */       if (j < 0) {
/* 240 */         return paramString1.substring(0, i + 1) + str1;
/*     */       }
/* 242 */       return paramString1.substring(0, i + 1) + str1 + paramString1.substring(j);
/*     */     }
/* 244 */     boolean bool1 = Character.isWhitespace(paramString1.charAt(i + 1));
/*     */ 
/* 246 */     int i1 = (k == -1) || (bool1) ? i + 1 : k;
/* 247 */     String str3 = paramString1.substring(0, i1);
/* 248 */     if (j < 0) {
/* 249 */       return str3 + str1;
/*     */     }
/* 251 */     boolean bool2 = Character.isWhitespace(paramString1.charAt(j - 1));
/*     */ 
/* 253 */     i1 = (m == -1) || (bool2) ? j : m + 1;
/* 254 */     String str4 = paramString1.substring(i1);
/* 255 */     return str3 + str1 + str4;
/*     */   }
/*     */ 
/*     */   static String computeClippedWrappedText(Font paramFont, String paramString1, double paramDouble1, double paramDouble2, OverrunStyle paramOverrunStyle, String paramString2)
/*     */   {
/* 263 */     if (paramFont == null) {
/* 264 */       throw new IllegalArgumentException("Must specify a font");
/*     */     }
/*     */ 
/* 267 */     String str1 = paramOverrunStyle == OverrunStyle.CLIP ? "" : paramString2;
/* 268 */     int i = str1.length();
/*     */ 
/* 270 */     double d1 = computeTextWidth(paramFont, str1, 0.0D);
/* 271 */     double d2 = computeTextHeight(paramFont, str1, 0.0D);
/*     */ 
/* 273 */     if ((paramDouble1 < d1) || (paramDouble2 < d2))
/*     */     {
/* 275 */       return "";
/*     */     }
/*     */ 
/* 278 */     helper.setText(paramString1);
/* 279 */     helper.setFont(paramFont);
/* 280 */     helper.setWrappingWidth((int)Math.ceil(paramDouble1));
/*     */ 
/* 282 */     int j = (paramOverrunStyle == OverrunStyle.LEADING_ELLIPSIS) || (paramOverrunStyle == OverrunStyle.LEADING_WORD_ELLIPSIS) ? 1 : 0;
/*     */ 
/* 284 */     int k = (paramOverrunStyle == OverrunStyle.CENTER_ELLIPSIS) || (paramOverrunStyle == OverrunStyle.CENTER_WORD_ELLIPSIS) ? 1 : 0;
/*     */ 
/* 286 */     int m = (j == 0) && (k == 0) ? 1 : 0;
/* 287 */     int n = (paramOverrunStyle == OverrunStyle.WORD_ELLIPSIS) || (paramOverrunStyle == OverrunStyle.LEADING_WORD_ELLIPSIS) || (paramOverrunStyle == OverrunStyle.CENTER_WORD_ELLIPSIS) ? 1 : 0;
/*     */ 
/* 291 */     String str2 = paramString1;
/* 292 */     int i1 = str2 != null ? str2.length() : 0;
/* 293 */     int i2 = -1;
/*     */ 
/* 295 */     Point2D localPoint2D1 = null;
/* 296 */     if (k != 0)
/*     */     {
/* 298 */       localPoint2D1 = new Point2D((paramDouble1 - d1) / 2.0D, paramDouble2 / 2.0D - helper.getBaselineOffset());
/*     */     }
/*     */ 
/* 303 */     Point2D localPoint2D2 = new Point2D(0.0D, paramDouble2 - helper.getBaselineOffset());
/*     */ 
/* 305 */     int i3 = helper.impl_hitTestChar(localPoint2D2).getCharIndex();
/* 306 */     if (i3 >= i1) {
/* 307 */       return paramString1;
/*     */     }
/* 309 */     if (k != 0) {
/* 310 */       i3 = helper.impl_hitTestChar(localPoint2D1).getCharIndex();
/*     */     }
/*     */ 
/* 313 */     if ((i3 > 0) && (i3 < i1))
/*     */     {
/*     */       int i4;
/*     */       int i5;
/* 316 */       if ((k != 0) || (m != 0)) {
/* 317 */         i4 = i3;
/* 318 */         if (k != 0)
/*     */         {
/* 320 */           if (n != 0) {
/* 321 */             i5 = lastBreakCharIndex(paramString1, i4 + 1);
/* 322 */             if (i5 >= 0) {
/* 323 */               i4 = i5 + 1;
/*     */             } else {
/* 325 */               i5 = firstBreakCharIndex(paramString1, i4);
/* 326 */               if (i5 >= 0) {
/* 327 */                 i4 = i5 + 1;
/*     */               }
/*     */             }
/*     */           }
/* 331 */           i2 = i4 + i;
/*     */         }
/* 333 */         str2 = str2.substring(0, i4) + str1;
/*     */       }
/*     */ 
/* 336 */       if ((j != 0) || (k != 0))
/*     */       {
/* 347 */         i4 = Math.max(0, i1 - i3 - 10);
/* 348 */         if ((i4 > 0) && (n != 0)) {
/* 349 */           i5 = lastBreakCharIndex(paramString1, i4 + 1);
/* 350 */           if (i5 >= 0) {
/* 351 */             i4 = i5 + 1;
/*     */           } else {
/* 353 */             i5 = firstBreakCharIndex(paramString1, i4);
/* 354 */             if (i5 >= 0) {
/* 355 */               i4 = i5 + 1;
/*     */             }
/*     */           }
/*     */         }
/* 359 */         if (k != 0)
/*     */         {
/* 361 */           str2 = str2 + paramString1.substring(i4);
/*     */         }
/* 363 */         else str2 = str1 + paramString1.substring(i4);
/*     */ 
/*     */       }
/*     */ 
/*     */       while (true)
/*     */       {
/* 370 */         helper.setText(str2);
/* 371 */         i4 = helper.impl_hitTestChar(localPoint2D2).getCharIndex();
/* 372 */         if ((k != 0) && (i4 < i2))
/*     */         {
/* 375 */           if ((i4 > 0) && (str2.charAt(i4 - 1) == '\n')) {
/* 376 */             i4--;
/*     */           }
/* 378 */           str2 = paramString1.substring(0, i4) + str1;
/* 379 */           break;
/* 380 */         }if ((i4 <= 0) || (i4 >= str2.length()))
/*     */           break;
/*     */         int i6;
/* 381 */         if (j != 0) {
/* 382 */           i5 = i + 1;
/* 383 */           if (n != 0) {
/* 384 */             i6 = firstBreakCharIndex(str2, i5);
/* 385 */             if (i6 >= 0) {
/* 386 */               i5 = i6 + 1;
/*     */             }
/*     */           }
/* 389 */           str2 = str1 + str2.substring(i5);
/* 390 */         } else if (k != 0) {
/* 391 */           i5 = i2 + 1;
/* 392 */           if (n != 0) {
/* 393 */             i6 = firstBreakCharIndex(str2, i5);
/* 394 */             if (i6 >= 0) {
/* 395 */               i5 = i6 + 1;
/*     */             }
/*     */           }
/* 398 */           str2 = str2.substring(0, i2) + str2.substring(i5);
/*     */         } else {
/* 400 */           i5 = str2.length() - i - 1;
/* 401 */           if (n != 0) {
/* 402 */             i6 = lastBreakCharIndex(str2, i5);
/* 403 */             if (i6 >= 0) {
/* 404 */               i5 = i6;
/*     */             }
/*     */           }
/* 407 */           str2 = str2.substring(0, i5) + str1;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 414 */     return str2;
/*     */   }
/*     */ 
/*     */   private static int firstBreakCharIndex(String paramString, int paramInt)
/*     */   {
/* 419 */     char[] arrayOfChar = paramString.toCharArray();
/* 420 */     for (int i = paramInt; i < arrayOfChar.length; i++) {
/* 421 */       if (isPreferredBreakCharacter(arrayOfChar[i])) {
/* 422 */         return i;
/*     */       }
/*     */     }
/* 425 */     return -1;
/*     */   }
/*     */ 
/*     */   private static int lastBreakCharIndex(String paramString, int paramInt) {
/* 429 */     char[] arrayOfChar = paramString.toCharArray();
/* 430 */     for (int i = paramInt; i >= 0; i--) {
/* 431 */       if (isPreferredBreakCharacter(arrayOfChar[i])) {
/* 432 */         return i;
/*     */       }
/*     */     }
/* 435 */     return -1;
/*     */   }
/*     */ 
/*     */   private static boolean isPreferredBreakCharacter(char paramChar)
/*     */   {
/* 443 */     if (Character.isWhitespace(paramChar)) {
/* 444 */       return true;
/*     */     }
/* 446 */     switch (paramChar) {
/*     */     case '.':
/*     */     case ':':
/*     */     case ';':
/* 450 */       return true;
/* 451 */     }return false;
/*     */   }
/*     */ 
/*     */   private static boolean requiresComplexLayout(Font paramFont, String paramString)
/*     */   {
/* 654 */     return false;
/*     */   }
/*     */ 
/*     */   static int computeStartOfWord(Font paramFont, String paramString, int paramInt) {
/* 658 */     if (("".equals(paramString)) || (paramInt < 0)) return 0;
/* 659 */     if (paramString.length() <= paramInt) return paramString.length();
/*     */ 
/* 662 */     if (Character.isWhitespace(paramString.charAt(paramInt))) {
/* 663 */       return paramInt;
/*     */     }
/* 665 */     boolean bool = requiresComplexLayout(paramFont, paramString);
/* 666 */     if (bool)
/*     */     {
/* 668 */       return 0;
/*     */     }
/*     */ 
/* 672 */     int i = paramInt;
/*     */     do { i--; if (i < 0) break; }
/* 674 */     while (!Character.isWhitespace(paramString.charAt(i)));
/* 675 */     return i + 1;
/*     */ 
/* 678 */     return 0;
/*     */   }
/*     */ 
/*     */   static int computeEndOfWord(Font paramFont, String paramString, int paramInt)
/*     */   {
/* 683 */     if ((paramString.equals("")) || (paramInt < 0)) {
/* 684 */       return 0;
/*     */     }
/* 686 */     if (paramString.length() <= paramInt) {
/* 687 */       return paramString.length();
/*     */     }
/*     */ 
/* 691 */     if (Character.isWhitespace(paramString.charAt(paramInt))) {
/* 692 */       return paramInt;
/*     */     }
/* 694 */     boolean bool = requiresComplexLayout(paramFont, paramString);
/* 695 */     if (bool)
/*     */     {
/* 697 */       return paramString.length();
/*     */     }
/*     */ 
/* 701 */     int i = paramInt;
/*     */     do { i++; if (i >= paramString.length()) break; }
/* 703 */     while (!Character.isWhitespace(paramString.charAt(i)));
/* 704 */     return i;
/*     */ 
/* 707 */     return paramString.length();
/*     */   }
/*     */ 
/*     */   public static double boundedSize(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 716 */     return Math.min(Math.max(paramDouble1, paramDouble2), Math.max(paramDouble2, paramDouble3));
/*     */   }
/*     */ 
/*     */   static void addMnemonics(ContextMenu paramContextMenu, Scene paramScene)
/*     */   {
/* 721 */     if (!PlatformUtil.isMac())
/*     */     {
/* 723 */       ContextMenuContent localContextMenuContent = (ContextMenuContent)paramContextMenu.getSkin().getNode();
/*     */ 
/* 726 */       for (int i = 0; i < paramContextMenu.getItems().size(); i++) {
/* 727 */         MenuItem localMenuItem = (MenuItem)paramContextMenu.getItems().get(i);
/*     */ 
/* 731 */         if (localMenuItem.isMnemonicParsing())
/*     */         {
/* 733 */           TextBinding localTextBinding = new TextBinding(localMenuItem.getText());
/* 734 */           int j = localTextBinding.getMnemonicIndex();
/* 735 */           if (j >= 0)
/*     */           {
/* 737 */             KeyCode localKeyCode = localTextBinding.getMnemonic();
/*     */ 
/* 739 */             KeyCodeCombination localKeyCodeCombination = new KeyCodeCombination(localKeyCode, new KeyCombination.Modifier[] { PlatformUtil.isMac() ? KeyCombination.META_DOWN : KeyCombination.ALT_DOWN });
/*     */ 
/* 746 */             Mnemonic localMnemonic = new Mnemonic(localContextMenuContent.getLabelAt(i), localKeyCodeCombination);
/* 747 */             paramScene.addMnemonic(localMnemonic);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static void removeMnemonics(ContextMenu paramContextMenu, Scene paramScene)
/*     */   {
/* 758 */     if (!PlatformUtil.isMac())
/*     */     {
/* 760 */       ContextMenuContent localContextMenuContent = (ContextMenuContent)paramContextMenu.getSkin().getNode();
/*     */ 
/* 763 */       for (int i = 0; i < paramContextMenu.getItems().size(); i++) {
/* 764 */         MenuItem localMenuItem = (MenuItem)paramContextMenu.getItems().get(i);
/*     */ 
/* 768 */         if (localMenuItem.isMnemonicParsing())
/*     */         {
/* 770 */           TextBinding localTextBinding = new TextBinding(localMenuItem.getText());
/* 771 */           int j = localTextBinding.getMnemonicIndex();
/* 772 */           if (j >= 0)
/*     */           {
/* 774 */             KeyCode localKeyCode = localTextBinding.getMnemonic();
/*     */ 
/* 776 */             KeyCodeCombination localKeyCodeCombination = new KeyCodeCombination(localKeyCode, new KeyCombination.Modifier[] { PlatformUtil.isMac() ? KeyCombination.META_DOWN : KeyCombination.ALT_DOWN });
/*     */ 
/* 783 */             ObservableList localObservableList = (ObservableList)paramScene.getMnemonics().get(localKeyCodeCombination);
/* 784 */             if (localObservableList != null)
/* 785 */               for (int k = 0; k < localObservableList.size(); k++)
/* 786 */                 if (((Mnemonic)localObservableList.get(k)).getNode() == localContextMenuContent.getLabelAt(i))
/* 787 */                   localObservableList.remove(k);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static double computeXOffset(double paramDouble1, double paramDouble2, HPos paramHPos)
/*     */   {
/* 798 */     switch (1.$SwitchMap$javafx$geometry$HPos[paramHPos.ordinal()]) {
/*     */     case 1:
/* 800 */       return 0.0D;
/*     */     case 2:
/* 802 */       return (paramDouble1 - paramDouble2) / 2.0D;
/*     */     case 3:
/* 804 */       return paramDouble1 - paramDouble2;
/*     */     }
/* 806 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   static double computeYOffset(double paramDouble1, double paramDouble2, VPos paramVPos) {
/* 810 */     switch (1.$SwitchMap$javafx$geometry$VPos[paramVPos.ordinal()]) {
/*     */     case 1:
/* 812 */       return 0.0D;
/*     */     case 2:
/* 814 */       return (paramDouble1 - paramDouble2) / 2.0D;
/*     */     case 3:
/* 816 */       return paramDouble1 - paramDouble2;
/*     */     }
/* 818 */     return 0.0D;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.Utils
 * JD-Core Version:    0.6.2
 */