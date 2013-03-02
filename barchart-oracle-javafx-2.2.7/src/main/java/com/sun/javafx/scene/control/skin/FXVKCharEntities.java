/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ class FXVKCharEntities
/*     */ {
/*  34 */   private static final HashMap<String, Character> map = new HashMap();
/*     */ 
/*     */   public static String get(String paramString) {
/*  37 */     Character localCharacter = (Character)map.get(paramString);
/*  38 */     if (localCharacter == null) {
/*  39 */       if (paramString.length() != 1);
/*  42 */       return paramString;
/*     */     }
/*  44 */     return "" + localCharacter;
/*     */   }
/*     */ 
/*     */   private static void put(String paramString, int paramInt)
/*     */   {
/*  49 */     map.put(paramString, Character.valueOf((char)paramInt));
/*     */   }
/*     */ 
/*     */   static {
/*  53 */     put("space", 32);
/*  54 */     put("quot", 34);
/*  55 */     put("apos", 39);
/*  56 */     put("amp", 38);
/*  57 */     put("lt", 60);
/*  58 */     put("gt", 62);
/*  59 */     put("nbsp", 160);
/*  60 */     put("iexcl", 161);
/*  61 */     put("cent", 162);
/*  62 */     put("pound", 163);
/*  63 */     put("curren", 164);
/*  64 */     put("yen", 165);
/*  65 */     put("brvbar", 166);
/*  66 */     put("sect", 167);
/*  67 */     put("uml", 168);
/*  68 */     put("copy", 169);
/*  69 */     put("ordf", 170);
/*  70 */     put("laquo", 171);
/*  71 */     put("not", 172);
/*  72 */     put("shy", 173);
/*  73 */     put("reg", 174);
/*  74 */     put("macr", 175);
/*  75 */     put("deg", 176);
/*  76 */     put("plusmn", 177);
/*  77 */     put("sup2", 178);
/*  78 */     put("sup3", 179);
/*  79 */     put("acute", 180);
/*  80 */     put("micro", 181);
/*  81 */     put("para", 182);
/*  82 */     put("middot", 183);
/*  83 */     put("cedil", 184);
/*  84 */     put("sup1", 185);
/*  85 */     put("ordm", 186);
/*  86 */     put("raquo", 187);
/*  87 */     put("frac14", 188);
/*  88 */     put("frac12", 189);
/*  89 */     put("frac34", 190);
/*  90 */     put("iquest", 191);
/*  91 */     put("times", 215);
/*  92 */     put("divide", 247);
/*     */ 
/*  94 */     put("Agrave", 192);
/*  95 */     put("Aacute", 193);
/*  96 */     put("Acirc", 194);
/*  97 */     put("Atilde", 195);
/*  98 */     put("Auml", 196);
/*  99 */     put("Aring", 197);
/* 100 */     put("AElig", 198);
/* 101 */     put("Ccedil", 199);
/* 102 */     put("Egrave", 200);
/* 103 */     put("Eacute", 201);
/* 104 */     put("Ecirc", 202);
/* 105 */     put("Euml", 203);
/* 106 */     put("Igrave", 204);
/* 107 */     put("Iacute", 205);
/* 108 */     put("Icirc", 206);
/* 109 */     put("Iuml", 207);
/* 110 */     put("ETH", 208);
/* 111 */     put("Ntilde", 209);
/* 112 */     put("Ograve", 210);
/* 113 */     put("Oacute", 211);
/* 114 */     put("Ocirc", 212);
/* 115 */     put("Otilde", 213);
/* 116 */     put("Ouml", 214);
/* 117 */     put("Oslash", 216);
/* 118 */     put("Ugrave", 217);
/* 119 */     put("Uacute", 218);
/* 120 */     put("Ucirc", 219);
/* 121 */     put("Uuml", 220);
/* 122 */     put("Yacute", 221);
/* 123 */     put("THORN", 222);
/* 124 */     put("szlig", 223);
/* 125 */     put("agrave", 224);
/* 126 */     put("aacute", 225);
/* 127 */     put("acirc", 226);
/* 128 */     put("atilde", 227);
/* 129 */     put("auml", 228);
/* 130 */     put("aring", 229);
/* 131 */     put("aelig", 230);
/* 132 */     put("ccedil", 231);
/* 133 */     put("egrave", 232);
/* 134 */     put("eacute", 233);
/* 135 */     put("ecirc", 234);
/* 136 */     put("euml", 235);
/* 137 */     put("igrave", 236);
/* 138 */     put("iacute", 237);
/* 139 */     put("icirc", 238);
/* 140 */     put("iuml", 239);
/* 141 */     put("eth", 240);
/* 142 */     put("ntilde", 241);
/* 143 */     put("ograve", 242);
/* 144 */     put("oacute", 243);
/* 145 */     put("ocirc", 244);
/* 146 */     put("otilde", 245);
/* 147 */     put("ouml", 246);
/* 148 */     put("oslash", 248);
/* 149 */     put("ugrave", 249);
/* 150 */     put("uacute", 250);
/* 151 */     put("ucirc", 251);
/* 152 */     put("uuml", 252);
/* 153 */     put("yacute", 253);
/* 154 */     put("thorn", 254);
/* 155 */     put("yuml", 255);
/*     */ 
/* 157 */     put("scedil", 351);
/* 158 */     put("scaron", 353);
/* 159 */     put("ycirc", 375);
/* 160 */     put("ymacron", 563);
/* 161 */     put("pi", 960);
/* 162 */     put("sigma", 963);
/* 163 */     put("ygrave", 7923);
/* 164 */     put("yhook", 7927);
/* 165 */     put("permil", 8240);
/* 166 */     put("euro", 8364);
/* 167 */     put("tm", 8482);
/* 168 */     put("neq", 8800);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.FXVKCharEntities
 * JD-Core Version:    0.6.2
 */