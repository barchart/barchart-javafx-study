/*     */ package javafx.scene.text;
/*     */ 
/*     */ public enum FontWeight
/*     */ {
/*  60 */   THIN(100, new String[] { "Thin" }), 
/*     */ 
/*  65 */   EXTRA_LIGHT(200, new String[] { "Extra Light", "Ultra Light" }), 
/*     */ 
/*  70 */   LIGHT(300, new String[] { "Light" }), 
/*     */ 
/*  75 */   NORMAL(400, new String[] { "Normal", "Regular" }), 
/*     */ 
/*  80 */   MEDIUM(500, new String[] { "Medium" }), 
/*     */ 
/*  85 */   SEMI_BOLD(600, new String[] { "Semi Bold", "Demi Bold" }), 
/*     */ 
/*  90 */   BOLD(700, new String[] { "Bold" }), 
/*     */ 
/*  95 */   EXTRA_BOLD(800, new String[] { "Extra Bold", "Ultra Bold" }), 
/*     */ 
/* 100 */   BLACK(900, new String[] { "Black", "Heavy" });
/*     */ 
/*     */   private int weight;
/*     */   private final String[] names;
/*     */ 
/* 106 */   private FontWeight(int paramInt, String[] paramArrayOfString) { this.names = paramArrayOfString; }
/*     */ 
/*     */ 
/*     */   public int getWeight()
/*     */   {
/* 115 */     return this.weight;
/*     */   }
/*     */ 
/*     */   public static FontWeight findByName(String paramString)
/*     */   {
/* 124 */     if (paramString == null) return null;
/*     */ 
/* 126 */     for (FontWeight localFontWeight : values()) {
/* 127 */       for (String str : localFontWeight.names) {
/* 128 */         if (str.equalsIgnoreCase(paramString)) return localFontWeight;
/*     */       }
/*     */     }
/*     */ 
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */   public static FontWeight findByWeight(int paramInt)
/*     */   {
/* 147 */     if (paramInt <= 150)
/* 148 */       return THIN;
/* 149 */     if (paramInt <= 250)
/* 150 */       return EXTRA_LIGHT;
/* 151 */     if (paramInt < 350)
/* 152 */       return LIGHT;
/* 153 */     if (paramInt <= 450)
/* 154 */       return NORMAL;
/* 155 */     if (paramInt <= 550)
/* 156 */       return MEDIUM;
/* 157 */     if (paramInt < 650)
/* 158 */       return SEMI_BOLD;
/* 159 */     if (paramInt <= 750)
/* 160 */       return BOLD;
/* 161 */     if (paramInt <= 850) {
/* 162 */       return EXTRA_BOLD;
/*     */     }
/* 164 */     return BLACK;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.text.FontWeight
 * JD-Core Version:    0.6.2
 */