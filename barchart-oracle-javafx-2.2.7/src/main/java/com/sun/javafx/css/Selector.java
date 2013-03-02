/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ 
/*     */ public abstract class Selector
/*     */ {
/*     */   private static final int TYPE_SIMPLE = 1;
/*     */   private static final int TYPE_COMPOUND = 2;
/*     */ 
/*     */   public static Selector getUniversalSelector()
/*     */   {
/*  69 */     return UniversalSelector.INSTANCE;
/*     */   }
/*     */ 
/*     */   abstract Match matches(Node paramNode);
/*     */ 
/*     */   abstract Match matches(Scene paramScene);
/*     */ 
/*     */   public abstract boolean applies(Node paramNode);
/*     */ 
/*     */   abstract boolean mightApply(String paramString1, String paramString2, List<String> paramList);
/*     */ 
/*     */   boolean stateMatches(Node paramNode, List<String> paramList)
/*     */   {
/*  90 */     long l = StyleManager.getInstance().getPseudoclassMask(paramList);
/*  91 */     return stateMatches(paramNode, l);
/*     */   }
/*     */ 
/*     */   abstract boolean stateMatches(Node paramNode, long paramLong);
/*     */ 
/*     */   protected void writeBinary(DataOutputStream paramDataOutputStream, StringStore paramStringStore)
/*     */     throws IOException
/*     */   {
/* 100 */     if ((this instanceof SimpleSelector))
/* 101 */       paramDataOutputStream.writeByte(1);
/*     */     else
/* 103 */       paramDataOutputStream.writeByte(2);
/*     */   }
/*     */ 
/*     */   static Selector readBinary(DataInputStream paramDataInputStream, String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/* 109 */     int i = paramDataInputStream.readByte();
/* 110 */     if (i == 1) {
/* 111 */       return SimpleSelector.readBinary(paramDataInputStream, paramArrayOfString);
/*     */     }
/* 113 */     return CompoundSelector.readBinary(paramDataInputStream, paramArrayOfString);
/*     */   }
/*     */ 
/*     */   public static Selector createSelector(String paramString) {
/* 117 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 118 */       return null;
/*     */     }
/*     */ 
/* 122 */     ArrayList localArrayList1 = new ArrayList();
/* 123 */     ArrayList localArrayList2 = new ArrayList();
/* 124 */     ArrayList localArrayList3 = new ArrayList();
/* 125 */     int i = 0;
/* 126 */     int j = -1;
/* 127 */     int k = 0;
/* 128 */     for (int m = 0; m < paramString.length(); m++) {
/* 129 */       int n = paramString.charAt(m);
/* 130 */       if (n == 32) {
/* 131 */         if (k == 0) {
/* 132 */           k = n;
/* 133 */           j = m;
/*     */         }
/* 135 */       } else if (n == 62) {
/* 136 */         if (k == 0) j = m;
/* 137 */         k = n;
/* 138 */       } else if (k != 0) {
/* 139 */         localArrayList3.add(paramString.substring(i, j));
/* 140 */         i = m;
/* 141 */         localArrayList2.add(k == 32 ? Combinator.DESCENDANT : Combinator.CHILD);
/* 142 */         k = 0;
/*     */       }
/*     */     }
/* 145 */     localArrayList3.add(paramString.substring(i));
/*     */ 
/* 147 */     for (m = 0; m < localArrayList3.size(); m++) {
/* 148 */       String str1 = (String)localArrayList3.get(m);
/* 149 */       if ((str1 != null) && (!str1.equals("")))
/*     */       {
/* 151 */         String[] arrayOfString1 = str1.split(":");
/* 152 */         ArrayList localArrayList4 = new ArrayList();
/* 153 */         for (int i1 = 1; i1 < arrayOfString1.length; i1++) {
/* 154 */           if ((arrayOfString1[i1] != null) && (!arrayOfString1[i1].equals(""))) {
/* 155 */             localArrayList4.add(arrayOfString1[i1].trim());
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 161 */         String str2 = arrayOfString1[0].trim();
/*     */ 
/* 163 */         String[] arrayOfString2 = str2.split("\\.");
/* 164 */         ArrayList localArrayList5 = new ArrayList();
/*     */ 
/* 169 */         for (int i2 = 1; i2 < arrayOfString2.length; i2++) {
/* 170 */           if ((arrayOfString2[i2] != null) && (!arrayOfString2[i2].equals(""))) {
/* 171 */             localArrayList5.add(arrayOfString2[i2].trim());
/*     */           }
/*     */         }
/* 174 */         String str3 = null; String str4 = null;
/* 175 */         if (!arrayOfString2[0].equals(""))
/*     */         {
/* 177 */           if (arrayOfString2[0].charAt(0) == '#')
/* 178 */             str4 = arrayOfString2[0].substring(1).trim();
/*     */           else {
/* 180 */             str3 = arrayOfString2[0].trim();
/*     */           }
/*     */         }
/* 183 */         localArrayList1.add(new SimpleSelector(str3, localArrayList5, localArrayList4, str4));
/*     */       }
/*     */     }
/*     */ 
/* 187 */     if (localArrayList1.size() == 1) {
/* 188 */       return (Selector)localArrayList1.get(0);
/*     */     }
/* 190 */     return new CompoundSelector(localArrayList1, localArrayList2);
/*     */   }
/*     */ 
/*     */   private static class UniversalSelector
/*     */   {
/*  64 */     private static Selector INSTANCE = new SimpleSelector("*", null, null, null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.Selector
 * JD-Core Version:    0.6.2
 */