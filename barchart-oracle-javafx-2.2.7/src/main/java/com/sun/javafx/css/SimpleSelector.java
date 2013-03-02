/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ 
/*     */ public final class SimpleSelector extends Selector
/*     */ {
/*  60 */   private static final Object MAX_CLASS_DEPTH = Integer.valueOf(255);
/*     */   private final String name;
/*     */   private final List<String> styleClasses;
/*     */   private final List<String> pseudoclasses;
/*     */   private final String id;
/*     */   private final long pclassMask;
/*     */   private final boolean matchOnName;
/*     */   private final boolean matchOnId;
/*     */   private final boolean matchOnStyleClass;
/* 257 */   private static final Set<String> strSet = new HashSet();
/*     */ 
/* 322 */   private int hash = -1;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  75 */     return this.name;
/*     */   }
/*     */ 
/*     */   public List<String> getStyleClasses()
/*     */   {
/*  84 */     return this.styleClasses;
/*     */   }
/*     */ 
/*     */   public List<String> getPseudoclasses()
/*     */   {
/*  92 */     return this.pseudoclasses;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 101 */     return this.id;
/*     */   }
/*     */ 
/*     */   public SimpleSelector(String paramString1, List<String> paramList1, List<String> paramList2, String paramString2)
/*     */   {
/* 120 */     this.name = (paramString1 == null ? "*" : paramString1);
/*     */ 
/* 123 */     this.matchOnName = ((paramString1 != null) && (!"".equals(paramString1)) && (!"*".equals(paramString1)));
/*     */ 
/* 125 */     this.styleClasses = (paramList1 != null ? Collections.unmodifiableList(paramList1) : Collections.EMPTY_LIST);
/*     */ 
/* 129 */     this.matchOnStyleClass = (this.styleClasses.size() > 0);
/*     */ 
/* 131 */     this.pseudoclasses = (paramList2 != null ? Collections.unmodifiableList(paramList2) : Collections.EMPTY_LIST);
/*     */ 
/* 135 */     this.pclassMask = StyleManager.getInstance().getPseudoclassMask(paramList2);
/*     */ 
/* 137 */     this.id = (paramString2 == null ? "" : paramString2);
/*     */ 
/* 139 */     this.matchOnId = ((paramString2 != null) && (!"".equals(paramString2)));
/*     */   }
/*     */ 
/*     */   Match matches(Node paramNode)
/*     */   {
/* 153 */     if (applies(paramNode)) {
/* 154 */       int i = this.matchOnId ? 1 : 0;
/* 155 */       return new Match(this, this.pseudoclasses, i, this.styleClasses.size());
/*     */     }
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   Match matches(Scene paramScene)
/*     */   {
/* 163 */     if ((!this.matchOnStyleClass) && (!this.matchOnId) && (this.pseudoclasses.isEmpty()))
/*     */     {
/* 165 */       String str = paramScene.getClass().getName();
/* 166 */       int i = ("".equals(this.name)) || (nameMatchesAtEnd(str)) ? 1 : 0;
/*     */ 
/* 169 */       if (i != 0)
/*     */       {
/* 172 */         return new Match(this, this.pseudoclasses, 0, 0);
/*     */       }
/*     */     }
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean applies(Node paramNode)
/*     */   {
/* 183 */     if (this.matchOnId) {
/* 184 */       boolean bool1 = this.id.equals(paramNode.getId());
/* 185 */       if (!bool1) return false;
/*     */ 
/*     */     }
/*     */ 
/* 191 */     if (this.matchOnName) {
/* 192 */       String str = paramNode.getClass().getName();
/* 193 */       boolean bool3 = nameMatchesAtEnd(str);
/* 194 */       if (!bool3) return false;
/*     */     }
/*     */ 
/* 197 */     if (this.matchOnStyleClass) {
/* 198 */       boolean bool2 = matchStyleClasses(paramNode.getStyleClass());
/* 199 */       if (!bool2) return false;
/*     */     }
/* 201 */     return true;
/*     */   }
/*     */ 
/*     */   boolean mightApply(String paramString1, String paramString2, List<String> paramList)
/*     */   {
/* 206 */     if ((this.matchOnName) && (nameMatchesAtEnd(paramString1))) return true;
/* 207 */     if ((this.matchOnId) && (this.id.equals(paramString2))) return true;
/* 208 */     if (this.matchOnStyleClass) return matchStyleClasses(paramList);
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */   boolean stateMatches(Node paramNode, long paramLong)
/*     */   {
/* 214 */     return (this.pclassMask & paramLong) == this.pclassMask;
/*     */   }
/*     */ 
/*     */   private boolean nameMatchesAtEnd(String paramString)
/*     */   {
/* 222 */     if (!this.matchOnName) return false;
/*     */ 
/* 224 */     int i = this.name.length();
/*     */ 
/* 227 */     int j = paramString.length() - i - 1;
/*     */ 
/* 232 */     if ((j == -1) || ((j > -1) && (paramString.charAt(j) == '.'))) {
/* 233 */       return paramString.regionMatches(j + 1, this.name, 0, i);
/*     */     }
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean matchStyleClasses(List<String> paramList)
/*     */   {
/* 250 */     return isSubsetOf(this.styleClasses, paramList);
/*     */   }
/*     */ 
/*     */   boolean isSubsetOf(List<String> paramList1, List<String> paramList2)
/*     */   {
/* 265 */     if ((paramList1 == null) || (paramList2 == null)) return (paramList1 == null) && (paramList2 == null);
/*     */ 
/* 268 */     if ((paramList1.isEmpty()) && (paramList2.isEmpty())) return true;
/*     */ 
/* 271 */     if (paramList1.size() > paramList2.size()) return false;
/*     */ 
/* 275 */     if (paramList1.size() == 1) {
/* 276 */       String str1 = (String)paramList1.get(0);
/* 277 */       if (str1 == null) return false;
/*     */ 
/* 279 */       j = 0; for (int k = paramList2.size(); j < k; j++) {
/* 280 */         String str2 = (String)paramList2.get(j);
/* 281 */         if ((str2 != null) && 
/* 282 */           (str2.equals(str1))) return true;
/*     */       }
/* 284 */       return false;
/*     */     }
/*     */ 
/* 289 */     strSet.clear();
/* 290 */     int i = 0; for (int j = paramList2.size(); i < j; i++) strSet.add(paramList2.get(i));
/* 291 */     i = 0; for (j = paramList1.size(); i < j; i++) {
/* 292 */       if (!strSet.contains(paramList1.get(i))) return false;
/*     */     }
/* 294 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 300 */     if (paramObject == null) {
/* 301 */       return false;
/*     */     }
/* 303 */     if (getClass() != paramObject.getClass()) {
/* 304 */       return false;
/*     */     }
/* 306 */     SimpleSelector localSimpleSelector = (SimpleSelector)paramObject;
/* 307 */     if (this.pclassMask != localSimpleSelector.pclassMask) {
/* 308 */       return false;
/*     */     }
/* 310 */     if (this.name == null ? localSimpleSelector.name != null : !this.name.equals(localSimpleSelector.name)) {
/* 311 */       return false;
/*     */     }
/* 313 */     if (this.id == null ? localSimpleSelector.id != null : !this.id.equals(localSimpleSelector.id)) {
/* 314 */       return false;
/*     */     }
/* 316 */     if ((this.styleClasses != localSimpleSelector.styleClasses) && ((this.styleClasses == null) || (!this.styleClasses.equals(localSimpleSelector.styleClasses)))) {
/* 317 */       return false;
/*     */     }
/* 319 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 326 */     if (this.hash == -1) {
/* 327 */       this.hash = this.name.hashCode();
/* 328 */       this.hash = (31 * (this.hash + this.styleClasses.hashCode()));
/* 329 */       this.hash = (31 * (this.hash + (this.id != null ? this.id.hashCode() : 1229)));
/* 330 */       this.hash = (31 * (int)(this.pclassMask ^ this.pclassMask >>> 32));
/*     */     }
/* 332 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 337 */     StringBuilder localStringBuilder = new StringBuilder();
/* 338 */     if ((this.name != null) && (!this.name.isEmpty())) localStringBuilder.append(this.name); else
/* 339 */       localStringBuilder.append("*");
/* 340 */     for (int i = 0; i < this.styleClasses.size(); i++) {
/* 341 */       localStringBuilder.append('.');
/* 342 */       localStringBuilder.append((String)this.styleClasses.get(i));
/*     */     }
/* 344 */     if ((this.id != null) && (!this.id.isEmpty())) {
/* 345 */       localStringBuilder.append('#');
/* 346 */       localStringBuilder.append(this.id);
/*     */     }
/* 348 */     for (i = 0; i < this.pseudoclasses.size(); i++) {
/* 349 */       localStringBuilder.append(':');
/* 350 */       localStringBuilder.append((String)this.pseudoclasses.get(i));
/*     */     }
/* 352 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public void writeBinary(DataOutputStream paramDataOutputStream, StringStore paramStringStore)
/*     */     throws IOException
/*     */   {
/* 358 */     super.writeBinary(paramDataOutputStream, paramStringStore);
/* 359 */     paramDataOutputStream.writeShort(paramStringStore.addString(this.name));
/* 360 */     paramDataOutputStream.writeShort(this.styleClasses.size());
/*     */     String str;
/* 361 */     for (Iterator localIterator = this.styleClasses.iterator(); localIterator.hasNext(); paramDataOutputStream.writeShort(paramStringStore.addString(str))) str = (String)localIterator.next();
/* 362 */     paramDataOutputStream.writeShort(paramStringStore.addString(this.id));
/* 363 */     paramDataOutputStream.writeShort(this.pseudoclasses.size());
/* 364 */     for (localIterator = this.pseudoclasses.iterator(); localIterator.hasNext(); paramDataOutputStream.writeShort(paramStringStore.addString(str))) str = (String)localIterator.next();
/*     */   }
/*     */ 
/*     */   static SimpleSelector readBinary(DataInputStream paramDataInputStream, String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/* 370 */     String str1 = paramArrayOfString[paramDataInputStream.readShort()];
/* 371 */     int i = paramDataInputStream.readShort();
/* 372 */     ArrayList localArrayList1 = new ArrayList();
/* 373 */     for (int j = 0; j < i; j++) {
/* 374 */       localArrayList1.add(paramArrayOfString[paramDataInputStream.readShort()]);
/*     */     }
/* 376 */     String str2 = paramArrayOfString[paramDataInputStream.readShort()];
/* 377 */     int k = paramDataInputStream.readShort();
/* 378 */     ArrayList localArrayList2 = new ArrayList();
/* 379 */     for (int m = 0; m < k; m++) {
/* 380 */       localArrayList2.add(paramArrayOfString[paramDataInputStream.readShort()]);
/*     */     }
/* 382 */     return new SimpleSelector(str1, localArrayList1, localArrayList2, str2);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.SimpleSelector
 * JD-Core Version:    0.6.2
 */