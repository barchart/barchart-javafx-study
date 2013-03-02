/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.Utils;
/*     */ import javafx.scene.input.KeyCode;
/*     */ 
/*     */ public class KeyCodeUtils
/*     */ {
/*     */   public static String getAccelerator(KeyCode paramKeyCode)
/*     */   {
/*  45 */     char c = getSingleChar(paramKeyCode);
/*  46 */     if (c != 0) {
/*  47 */       return String.valueOf(c);
/*     */     }
/*     */ 
/*  53 */     String str1 = paramKeyCode.toString();
/*     */ 
/*  59 */     StringBuilder localStringBuilder = new StringBuilder();
/*  60 */     String[] arrayOfString1 = Utils.split(str1, "_");
/*  61 */     for (String str2 : arrayOfString1) {
/*  62 */       if (localStringBuilder.length() > 0) {
/*  63 */         localStringBuilder.append(' ');
/*     */       }
/*  65 */       localStringBuilder.append(str2.charAt(0));
/*  66 */       localStringBuilder.append(str2.substring(1).toLowerCase());
/*     */     }
/*  68 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   private static char getSingleChar(KeyCode paramKeyCode)
/*     */   {
/*  73 */     switch (1.$SwitchMap$javafx$scene$input$KeyCode[paramKeyCode.ordinal()]) { case 1:
/*  74 */       return '↵';
/*     */     case 2:
/*  75 */       return '←';
/*     */     case 3:
/*  76 */       return '↑';
/*     */     case 4:
/*  77 */       return '→';
/*     */     case 5:
/*  78 */       return '↓';
/*     */     case 6:
/*  79 */       return ',';
/*     */     case 7:
/*  80 */       return '-';
/*     */     case 8:
/*  81 */       return '.';
/*     */     case 9:
/*  82 */       return '/';
/*     */     case 10:
/*  83 */       return ';';
/*     */     case 11:
/*  84 */       return '=';
/*     */     case 12:
/*  85 */       return '[';
/*     */     case 13:
/*  86 */       return '\\';
/*     */     case 14:
/*  87 */       return ']';
/*     */     case 15:
/*  88 */       return '*';
/*     */     case 16:
/*  89 */       return '+';
/*     */     case 17:
/*  90 */       return '-';
/*     */     case 18:
/*  91 */       return '.';
/*     */     case 19:
/*  92 */       return '/';
/*     */     case 20:
/*  93 */       return '`';
/*     */     case 21:
/*  94 */       return '"';
/*     */     case 22:
/*  95 */       return '&';
/*     */     case 23:
/*  96 */       return '*';
/*     */     case 24:
/*  97 */       return '<';
/*     */     case 25:
/*  98 */       return '>';
/*     */     case 26:
/*  99 */       return '{';
/*     */     case 27:
/* 100 */       return '}';
/*     */     case 28:
/* 101 */       return '@';
/*     */     case 29:
/* 102 */       return ':';
/*     */     case 30:
/* 103 */       return '^';
/*     */     case 31:
/* 104 */       return '$';
/*     */     case 32:
/* 105 */       return '€';
/*     */     case 33:
/* 106 */       return '!';
/*     */     case 34:
/* 107 */       return '(';
/*     */     case 35:
/* 108 */       return '#';
/*     */     case 36:
/* 109 */       return '+';
/*     */     case 37:
/* 110 */       return ')';
/*     */     case 38:
/* 111 */       return '_';
/*     */     case 39:
/* 112 */       return '0';
/*     */     case 40:
/* 113 */       return '1';
/*     */     case 41:
/* 114 */       return '2';
/*     */     case 42:
/* 115 */       return '3';
/*     */     case 43:
/* 116 */       return '4';
/*     */     case 44:
/* 117 */       return '5';
/*     */     case 45:
/* 118 */       return '6';
/*     */     case 46:
/* 119 */       return '7';
/*     */     case 47:
/* 120 */       return '8';
/*     */     case 48:
/* 121 */       return '9';
/*     */     }
/*     */ 
/* 128 */     if (PlatformUtil.isMac()) {
/* 129 */       switch (1.$SwitchMap$javafx$scene$input$KeyCode[paramKeyCode.ordinal()]) { case 49:
/* 130 */         return '⌫';
/*     */       case 50:
/* 131 */         return '⎋';
/*     */       case 51:
/* 132 */         return '⌦';
/*     */       }
/*     */     }
/* 135 */     return '\000';
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.KeyCodeUtils
 * JD-Core Version:    0.6.2
 */