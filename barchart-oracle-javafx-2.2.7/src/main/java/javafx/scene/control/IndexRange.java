/*     */ package javafx.scene.control;
/*     */ 
/*     */ public final class IndexRange
/*     */ {
/*     */   private int start;
/*     */   private int end;
/*     */   public static final String VALUE_DELIMITER = ",";
/*     */ 
/*     */   public IndexRange(int paramInt1, int paramInt2)
/*     */   {
/*  48 */     if (paramInt2 < paramInt1) {
/*  49 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*  52 */     this.start = paramInt1;
/*  53 */     this.end = paramInt2;
/*     */   }
/*     */ 
/*     */   public IndexRange(IndexRange paramIndexRange)
/*     */   {
/*  64 */     this.start = paramIndexRange.start;
/*  65 */     this.end = paramIndexRange.end;
/*     */   }
/*     */ 
/*     */   public int getStart()
/*     */   {
/*  72 */     return this.start;
/*     */   }
/*     */ 
/*     */   public int getEnd()
/*     */   {
/*  79 */     return this.end;
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/*  86 */     return this.end - this.start;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  96 */     if (paramObject == this) return true;
/*  97 */     if ((paramObject instanceof IndexRange)) {
/*  98 */       IndexRange localIndexRange = (IndexRange)paramObject;
/*  99 */       return (this.start == localIndexRange.start) && (this.end == localIndexRange.end);
/*     */     }
/*     */ 
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 112 */     return 31 * this.start + this.end;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 121 */     return this.start + "," + " " + this.end;
/*     */   }
/*     */ 
/*     */   public static IndexRange normalize(int paramInt1, int paramInt2)
/*     */   {
/* 134 */     return new IndexRange(Math.min(paramInt1, paramInt2), Math.max(paramInt1, paramInt2));
/*     */   }
/*     */ 
/*     */   public static IndexRange valueOf(String paramString)
/*     */   {
/* 147 */     if (paramString == null) {
/* 148 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/* 151 */     String[] arrayOfString = paramString.split(",");
/* 152 */     if (arrayOfString.length != 2) {
/* 153 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/* 158 */     int i = Integer.parseInt(arrayOfString[0].trim());
/* 159 */     int j = Integer.parseInt(arrayOfString[1].trim());
/*     */ 
/* 161 */     return normalize(i, j);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.IndexRange
 * JD-Core Version:    0.6.2
 */