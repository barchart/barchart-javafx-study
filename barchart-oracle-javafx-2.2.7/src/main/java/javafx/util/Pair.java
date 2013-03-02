/*     */ package javafx.util;
/*     */ 
/*     */ public class Pair<K, V>
/*     */ {
/*     */   private K key;
/*     */   private V value;
/*     */ 
/*     */   public K getKey()
/*     */   {
/*  21 */     return this.key;
/*     */   }
/*     */ 
/*     */   public V getValue()
/*     */   {
/*  32 */     return this.value;
/*     */   }
/*     */ 
/*     */   public Pair(K paramK, V paramV)
/*     */   {
/*  40 */     this.key = paramK;
/*  41 */     this.value = paramV;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  54 */     return this.key + "=" + this.value;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  72 */     return this.key.hashCode() * 13 + (this.value == null ? 0 : this.value.hashCode());
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  93 */     if (this == paramObject) return true;
/*  94 */     if ((paramObject instanceof Pair)) {
/*  95 */       Pair localPair = (Pair)paramObject;
/*  96 */       if (this.key != null ? !this.key.equals(localPair.key) : localPair.key != null) return false;
/*  97 */       if (this.value != null ? !this.value.equals(localPair.value) : localPair.value != null) return false;
/*  98 */       return true;
/*     */     }
/* 100 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.Pair
 * JD-Core Version:    0.6.2
 */