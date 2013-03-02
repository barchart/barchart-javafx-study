/*     */ package javafx.scene.paint;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class Stop
/*     */ {
/*  51 */   static final List<Stop> NO_STOPS = Collections.unmodifiableList(Arrays.asList(new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(1.0D, Color.TRANSPARENT) }));
/*     */   private double offset;
/*     */   private Color color;
/* 182 */   private int hash = 0;
/*     */ 
/*     */   static List<Stop> normalize(Stop[] paramArrayOfStop)
/*     */   {
/*  58 */     List localList = paramArrayOfStop == null ? null : Arrays.asList(paramArrayOfStop);
/*  59 */     return normalize(localList);
/*     */   }
/*     */ 
/*     */   static List<Stop> normalize(List<Stop> paramList) {
/*  63 */     if (paramList == null) {
/*  64 */       return NO_STOPS;
/*     */     }
/*  66 */     Object localObject1 = null;
/*  67 */     Object localObject2 = null;
/*  68 */     ArrayList localArrayList = new ArrayList(paramList.size());
/*  69 */     for (Object localObject3 = paramList.iterator(); ((Iterator)localObject3).hasNext(); ) { Stop localStop1 = (Stop)((Iterator)localObject3).next();
/*  70 */       if ((localStop1 != null) && (localStop1.getColor() != null)) {
/*  71 */         double d = localStop1.getOffset();
/*  72 */         if (d <= 0.0D) {
/*  73 */           if ((localObject1 == null) || (d >= ((Stop)localObject1).getOffset()))
/*  74 */             localObject1 = localStop1;
/*     */         }
/*  76 */         else if (d >= 1.0D) {
/*  77 */           if ((localObject2 == null) || (d < ((Stop)localObject2).getOffset()))
/*  78 */             localObject2 = localStop1;
/*     */         }
/*  80 */         else if (d == d) {
/*  81 */           for (int i = localArrayList.size() - 1; i >= 0; i--) {
/*  82 */             Stop localStop2 = (Stop)localArrayList.get(i);
/*  83 */             if (localStop2.getOffset() <= d) {
/*  84 */               if (localStop2.getOffset() == d) {
/*  85 */                 if ((i > 0) && (((Stop)localArrayList.get(i - 1)).getOffset() == d))
/*  86 */                   localArrayList.set(i, localStop1);
/*     */                 else
/*  88 */                   localArrayList.add(i + 1, localStop1);
/*     */               }
/*     */               else {
/*  91 */                 localArrayList.add(i + 1, localStop1);
/*     */               }
/*  93 */               localStop1 = null;
/*  94 */               break;
/*     */             }
/*     */           }
/*  97 */           if (localStop1 != null) {
/*  98 */             localArrayList.add(0, localStop1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 103 */     if (localObject1 == null)
/*     */     {
/* 105 */       if (localArrayList.isEmpty()) {
/* 106 */         if (localObject2 == null) {
/* 107 */           return NO_STOPS;
/*     */         }
/* 109 */         localObject3 = ((Stop)localObject2).getColor();
/*     */       } else {
/* 111 */         localObject3 = ((Stop)localArrayList.get(0)).getColor();
/* 112 */         if ((localObject2 == null) && (localArrayList.size() == 1))
/*     */         {
/* 117 */           localArrayList.clear();
/*     */         }
/*     */       }
/* 120 */       localObject1 = new Stop(0.0D, (Color)localObject3);
/* 121 */     } else if (((Stop)localObject1).getOffset() < 0.0D) {
/* 122 */       localObject1 = new Stop(0.0D, ((Stop)localObject1).getColor());
/*     */     }
/* 124 */     localArrayList.add(0, localObject1);
/*     */ 
/* 126 */     if (localObject2 == null)
/* 127 */       localObject2 = new Stop(1.0D, ((Stop)localArrayList.get(localArrayList.size() - 1)).getColor());
/* 128 */     else if (((Stop)localObject2).getOffset() > 1.0D) {
/* 129 */       localObject2 = new Stop(1.0D, ((Stop)localObject2).getColor());
/*     */     }
/* 131 */     localArrayList.add(localObject2);
/*     */ 
/* 133 */     return Collections.unmodifiableList(localArrayList);
/*     */   }
/*     */ 
/*     */   public final double getOffset()
/*     */   {
/* 159 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public final Color getColor()
/*     */   {
/* 175 */     return this.color;
/*     */   }
/*     */ 
/*     */   public Stop(double paramDouble, Color paramColor)
/*     */   {
/* 190 */     this.offset = paramDouble;
/* 191 */     this.color = paramColor;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 200 */     if (paramObject == null) return false;
/* 201 */     if (paramObject == this) return true;
/* 202 */     if ((paramObject instanceof Stop)) {
/* 203 */       Stop localStop = (Stop)paramObject;
/* 204 */       return (this.offset == localStop.offset) && (this.color == null ? localStop.color == null : this.color.equals(localStop.color));
/*     */     }
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 214 */     if (this.hash == 0) {
/* 215 */       long l = 17L;
/* 216 */       l = 37L * l + Double.doubleToLongBits(this.offset);
/* 217 */       l = 37L * l + this.color.hashCode();
/* 218 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/* 220 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 228 */     return this.color + " " + this.offset * 100.0D + "%";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.Stop
 * JD-Core Version:    0.6.2
 */