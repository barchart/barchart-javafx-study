/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ public class SortHelper
/*     */ {
/*     */   private int[] permutation;
/*     */   private int[] reversePermutation;
/*     */   private static final int INSERTIONSORT_THRESHOLD = 7;
/*     */ 
/*     */   public <T extends Comparable<? super T>> int[] sort(List<T> paramList)
/*     */   {
/*  44 */     Comparable[] arrayOfComparable = (Comparable[])Array.newInstance(Comparable.class, paramList.size());
/*     */     try {
/*  46 */       arrayOfComparable = (Comparable[])paramList.toArray(arrayOfComparable);
/*     */     }
/*     */     catch (ArrayStoreException localArrayStoreException) {
/*  49 */       throw new ClassCastException();
/*     */     }
/*  51 */     int[] arrayOfInt = sort(arrayOfComparable);
/*  52 */     ListIterator localListIterator = paramList.listIterator();
/*  53 */     for (int i = 0; i < arrayOfComparable.length; i++) {
/*  54 */       localListIterator.next();
/*  55 */       localListIterator.set(arrayOfComparable[i]);
/*     */     }
/*  57 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   public <T> int[] sort(List<T> paramList, Comparator<? super T> paramComparator) {
/*  61 */     Object[] arrayOfObject = paramList.toArray();
/*  62 */     int[] arrayOfInt = sort(arrayOfObject, paramComparator);
/*  63 */     ListIterator localListIterator = paramList.listIterator();
/*  64 */     for (int i = 0; i < arrayOfObject.length; i++) {
/*  65 */       localListIterator.next();
/*  66 */       localListIterator.set(arrayOfObject[i]);
/*     */     }
/*  68 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   public <T extends Comparable<? super T>> int[] sort(T[] paramArrayOfT) {
/*  72 */     return sort(paramArrayOfT, null);
/*     */   }
/*     */ 
/*     */   public <T> int[] sort(T[] paramArrayOfT, Comparator<? super T> paramComparator) {
/*  76 */     Object[] arrayOfObject = (Object[])paramArrayOfT.clone();
/*  77 */     int[] arrayOfInt = initPermutation(paramArrayOfT.length);
/*  78 */     if (paramComparator == null)
/*  79 */       mergeSort(arrayOfObject, paramArrayOfT, 0, paramArrayOfT.length, 0);
/*     */     else
/*  81 */       mergeSort(arrayOfObject, paramArrayOfT, 0, paramArrayOfT.length, 0, paramComparator);
/*  82 */     this.reversePermutation = null;
/*  83 */     this.permutation = null;
/*  84 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   public <T> int[] sort(T[] paramArrayOfT, int paramInt1, int paramInt2, Comparator<? super T> paramComparator)
/*     */   {
/*  89 */     rangeCheck(paramArrayOfT.length, paramInt1, paramInt2);
/*  90 */     Object[] arrayOfObject = (Object[])copyOfRange(paramArrayOfT, paramInt1, paramInt2);
/*  91 */     int[] arrayOfInt = initPermutation(paramArrayOfT.length);
/*  92 */     if (paramComparator == null)
/*  93 */       mergeSort(arrayOfObject, paramArrayOfT, paramInt1, paramInt2, -paramInt1);
/*     */     else
/*  95 */       mergeSort(arrayOfObject, paramArrayOfT, paramInt1, paramInt2, -paramInt1, paramComparator);
/*  96 */     this.reversePermutation = null;
/*  97 */     this.permutation = null;
/*  98 */     return Arrays.copyOfRange(arrayOfInt, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   private static void rangeCheck(int paramInt1, int paramInt2, int paramInt3) {
/* 102 */     if (paramInt2 > paramInt3) {
/* 103 */       throw new IllegalArgumentException("fromIndex(" + paramInt2 + ") > toIndex(" + paramInt3 + ")");
/*     */     }
/* 105 */     if (paramInt2 < 0)
/* 106 */       throw new ArrayIndexOutOfBoundsException(paramInt2);
/* 107 */     if (paramInt3 > paramInt1)
/* 108 */       throw new ArrayIndexOutOfBoundsException(paramInt3);
/*     */   }
/*     */ 
/*     */   public static <T> T[] copyOfRange(T[] paramArrayOfT, int paramInt1, int paramInt2) {
/* 112 */     return copyOfRange(paramArrayOfT, paramInt1, paramInt2, paramArrayOfT.getClass());
/*     */   }
/*     */ 
/*     */   public static <T, U> T[] copyOfRange(U[] paramArrayOfU, int paramInt1, int paramInt2, Class<? extends T[]> paramClass) {
/* 116 */     int i = paramInt2 - paramInt1;
/* 117 */     if (i < 0)
/* 118 */       throw new IllegalArgumentException(paramInt1 + " > " + paramInt2);
/* 119 */     Object[] arrayOfObject = paramClass == [Ljava.lang.Object.class ? (Object[])new Object[i] : (Object[])Array.newInstance(paramClass.getComponentType(), i);
/*     */ 
/* 122 */     System.arraycopy(paramArrayOfU, paramInt1, arrayOfObject, 0, Math.min(paramArrayOfU.length - paramInt1, i));
/*     */ 
/* 124 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   private void mergeSort(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 135 */     int i = paramInt2 - paramInt1;
/*     */ 
/* 138 */     if (i < 7) {
/* 139 */       for (j = paramInt1; j < paramInt2; j++)
/* 140 */         for (k = j; 
/* 141 */           (k > paramInt1) && (((Comparable)paramArrayOfObject2[(k - 1)]).compareTo(paramArrayOfObject2[k]) > 0); k--)
/* 142 */           swap(paramArrayOfObject2, k, k - 1);
/* 143 */       return;
/*     */     }
/*     */ 
/* 147 */     int j = paramInt1;
/* 148 */     int k = paramInt2;
/* 149 */     paramInt1 += paramInt3;
/* 150 */     paramInt2 += paramInt3;
/* 151 */     int m = paramInt1 + paramInt2 >>> 1;
/* 152 */     mergeSort(paramArrayOfObject2, paramArrayOfObject1, paramInt1, m, -paramInt3);
/* 153 */     mergeSort(paramArrayOfObject2, paramArrayOfObject1, m, paramInt2, -paramInt3);
/*     */ 
/* 157 */     if (((Comparable)paramArrayOfObject1[(m - 1)]).compareTo(paramArrayOfObject1[m]) <= 0) {
/* 158 */       System.arraycopy(paramArrayOfObject1, paramInt1, paramArrayOfObject2, j, i);
/* 159 */       return;
/*     */     }
/*     */ 
/* 163 */     int n = j; int i1 = paramInt1; for (int i2 = m; n < k; n++) {
/* 164 */       if ((i2 >= paramInt2) || ((i1 < m) && (((Comparable)paramArrayOfObject1[i1]).compareTo(paramArrayOfObject1[i2]) <= 0))) {
/* 165 */         paramArrayOfObject2[n] = paramArrayOfObject1[i1];
/* 166 */         this.permutation[this.reversePermutation[(i1++)]] = n;
/*     */       } else {
/* 168 */         paramArrayOfObject2[n] = paramArrayOfObject1[i2];
/* 169 */         this.permutation[this.reversePermutation[(i2++)]] = n;
/*     */       }
/*     */     }
/*     */ 
/* 173 */     for (n = j; n < k; n++)
/* 174 */       this.reversePermutation[this.permutation[n]] = n;
/*     */   }
/*     */ 
/*     */   private void mergeSort(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, int paramInt1, int paramInt2, int paramInt3, Comparator paramComparator)
/*     */   {
/* 182 */     int i = paramInt2 - paramInt1;
/*     */ 
/* 185 */     if (i < 7) {
/* 186 */       for (j = paramInt1; j < paramInt2; j++)
/* 187 */         for (k = j; (k > paramInt1) && (paramComparator.compare(paramArrayOfObject2[(k - 1)], paramArrayOfObject2[k]) > 0); k--)
/* 188 */           swap(paramArrayOfObject2, k, k - 1);
/* 189 */       return;
/*     */     }
/*     */ 
/* 193 */     int j = paramInt1;
/* 194 */     int k = paramInt2;
/* 195 */     paramInt1 += paramInt3;
/* 196 */     paramInt2 += paramInt3;
/* 197 */     int m = paramInt1 + paramInt2 >>> 1;
/* 198 */     mergeSort(paramArrayOfObject2, paramArrayOfObject1, paramInt1, m, -paramInt3, paramComparator);
/* 199 */     mergeSort(paramArrayOfObject2, paramArrayOfObject1, m, paramInt2, -paramInt3, paramComparator);
/*     */ 
/* 203 */     if (paramComparator.compare(paramArrayOfObject1[(m - 1)], paramArrayOfObject1[m]) <= 0) {
/* 204 */       System.arraycopy(paramArrayOfObject1, paramInt1, paramArrayOfObject2, j, i);
/* 205 */       return;
/*     */     }
/*     */ 
/* 209 */     int n = j; int i1 = paramInt1; for (int i2 = m; n < k; n++) {
/* 210 */       if ((i2 >= paramInt2) || ((i1 < m) && (paramComparator.compare(paramArrayOfObject1[i1], paramArrayOfObject1[i2]) <= 0))) {
/* 211 */         paramArrayOfObject2[n] = paramArrayOfObject1[i1];
/* 212 */         this.permutation[this.reversePermutation[(i1++)]] = n;
/*     */       } else {
/* 214 */         paramArrayOfObject2[n] = paramArrayOfObject1[i2];
/* 215 */         this.permutation[this.reversePermutation[(i2++)]] = n;
/*     */       }
/*     */     }
/*     */ 
/* 219 */     for (n = j; n < k; n++)
/* 220 */       this.reversePermutation[this.permutation[n]] = n;
/*     */   }
/*     */ 
/*     */   private void swap(Object[] paramArrayOfObject, int paramInt1, int paramInt2)
/*     */   {
/* 225 */     Object localObject = paramArrayOfObject[paramInt1];
/* 226 */     paramArrayOfObject[paramInt1] = paramArrayOfObject[paramInt2];
/* 227 */     paramArrayOfObject[paramInt2] = localObject;
/* 228 */     this.permutation[this.reversePermutation[paramInt1]] = paramInt2;
/* 229 */     this.permutation[this.reversePermutation[paramInt2]] = paramInt1;
/* 230 */     int i = this.reversePermutation[paramInt1];
/* 231 */     this.reversePermutation[paramInt1] = this.reversePermutation[paramInt2];
/* 232 */     this.reversePermutation[paramInt2] = i;
/*     */   }
/*     */ 
/*     */   private int[] initPermutation(int paramInt) {
/* 236 */     this.permutation = new int[paramInt];
/* 237 */     this.reversePermutation = new int[paramInt];
/* 238 */     for (int i = 0; i < paramInt; i++)
/*     */     {
/*     */       int tmp32_31 = i; this.reversePermutation[i] = tmp32_31; this.permutation[i] = tmp32_31;
/*     */     }
/* 241 */     return this.permutation;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.SortHelper
 * JD-Core Version:    0.6.2
 */