/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ 
/*     */ public final class CompoundSelector extends Selector
/*     */ {
/*     */   private final List<SimpleSelector> selectors;
/*     */   private final List<Combinator> relationships;
/* 280 */   private int hash = -1;
/*     */ 
/*     */   public List<SimpleSelector> getSelectors()
/*     */   {
/*  89 */     return this.selectors;
/*     */   }
/*     */ 
/*     */   public List<Combinator> getRelationships()
/*     */   {
/*  98 */     return this.relationships;
/*     */   }
/*     */ 
/*     */   public CompoundSelector(List<SimpleSelector> paramList, List<Combinator> paramList1)
/*     */   {
/* 103 */     this.selectors = (paramList != null ? Collections.unmodifiableList(paramList) : Collections.EMPTY_LIST);
/*     */ 
/* 107 */     this.relationships = (paramList1 != null ? Collections.unmodifiableList(paramList1) : Collections.EMPTY_LIST);
/*     */   }
/*     */ 
/*     */   private CompoundSelector()
/*     */   {
/* 115 */     this(null, null);
/*     */   }
/*     */ 
/*     */   Match matches(Node paramNode)
/*     */   {
/* 128 */     return matches(paramNode, this.selectors.size() - 1);
/*     */   }
/*     */ 
/*     */   private Match matches(Node paramNode, int paramInt)
/*     */   {
/* 133 */     Match localMatch1 = ((SimpleSelector)this.selectors.get(paramInt)).matches(paramNode);
/* 134 */     if ((localMatch1 == null) || (paramInt == 0)) {
/* 135 */       return localMatch1;
/*     */     }
/*     */ 
/* 138 */     Parent localParent = paramNode.getParent();
/* 139 */     while (localParent != null) {
/* 140 */       Match localMatch2 = matches(localParent, paramInt - 1);
/* 141 */       if (localMatch2 != null)
/*     */       {
/* 143 */         List localList1 = localMatch2.pseudoclasses;
/* 144 */         List localList2 = localMatch1.pseudoclasses;
/* 145 */         ArrayList localArrayList = new ArrayList();
/* 146 */         localArrayList.addAll(localMatch2.pseudoclasses);
/* 147 */         localArrayList.addAll(localMatch1.pseudoclasses);
/*     */ 
/* 149 */         return new Match(this, localArrayList, localMatch2.idCount + localMatch1.idCount, localMatch2.styleClassCount + localMatch1.styleClassCount);
/*     */       }
/*     */ 
/* 154 */       if (this.relationships.get(paramInt - 1) == Combinator.CHILD) break;
/* 155 */       localParent = localParent.getParent();
/*     */     }
/*     */ 
/* 158 */     return null;
/*     */   }
/*     */ 
/*     */   Match matches(Scene paramScene)
/*     */   {
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean applies(Node paramNode)
/*     */   {
/* 169 */     return applies(paramNode, this.selectors.size() - 1);
/*     */   }
/*     */ 
/*     */   private boolean applies(Node paramNode, int paramInt)
/*     */   {
/* 174 */     if (paramInt < 0) return false;
/*     */ 
/* 178 */     if (!((SimpleSelector)this.selectors.get(paramInt)).applies(paramNode)) return false;
/*     */ 
/* 182 */     if (paramInt == 0) return true;
/*     */ 
/* 192 */     Combinator localCombinator = (Combinator)this.relationships.get(paramInt - 1);
/* 193 */     if (localCombinator == Combinator.CHILD) {
/* 194 */       localParent = paramNode.getParent();
/* 195 */       if (localParent == null) return false;
/*     */ 
/* 198 */       return applies(localParent, paramInt - 1);
/*     */     }
/* 200 */     Parent localParent = paramNode.getParent();
/* 201 */     while (localParent != null) {
/* 202 */       boolean bool = applies(localParent, paramInt - 1);
/*     */ 
/* 205 */       if (bool) return true;
/*     */ 
/* 207 */       localParent = localParent.getParent();
/*     */     }
/*     */ 
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   boolean mightApply(String paramString1, String paramString2, List<String> paramList)
/*     */   {
/* 215 */     return ((SimpleSelector)this.selectors.get(this.selectors.size() - 1)).mightApply(paramString1, paramString2, paramList);
/*     */   }
/*     */ 
/*     */   boolean stateMatches(Node paramNode, long paramLong)
/*     */   {
/* 220 */     return stateMatches(paramNode, paramLong, this.selectors.size() - 1);
/*     */   }
/*     */ 
/*     */   private boolean stateMatches(Node paramNode, long paramLong, int paramInt)
/*     */   {
/* 225 */     if (paramInt < 0) return false;
/*     */ 
/* 229 */     if (!((SimpleSelector)this.selectors.get(paramInt)).stateMatches(paramNode, paramLong)) return false;
/*     */ 
/* 233 */     if (paramInt == 0) return true;
/*     */ 
/* 243 */     Combinator localCombinator = (Combinator)this.relationships.get(paramInt - 1);
/*     */     Parent localParent;
/*     */     StyleHelper localStyleHelper;
/*     */     long l;
/* 244 */     if (localCombinator == Combinator.CHILD) {
/* 245 */       localParent = paramNode.getParent();
/* 246 */       if (localParent == null) return false;
/* 247 */       if (((SimpleSelector)this.selectors.get(paramInt - 1)).applies(localParent)) {
/* 248 */         localStyleHelper = localParent.impl_getStyleHelper();
/* 249 */         if (localStyleHelper == null) return false;
/* 250 */         l = localStyleHelper.getPseudoClassState();
/*     */ 
/* 253 */         return stateMatches(localParent, l, paramInt - 1);
/*     */       }
/*     */     } else {
/* 256 */       localParent = paramNode.getParent();
/* 257 */       while (localParent != null) {
/* 258 */         if (((SimpleSelector)this.selectors.get(paramInt - 1)).applies(localParent)) {
/* 259 */           localStyleHelper = localParent.impl_getStyleHelper();
/* 260 */           if (localStyleHelper != null) {
/* 261 */             l = localStyleHelper.getPseudoClassState();
/* 262 */             return stateMatches(localParent, l, paramInt - 1);
/*     */           }
/*     */ 
/* 269 */           return false;
/*     */         }
/*     */ 
/* 273 */         localParent = localParent.getParent();
/*     */       }
/*     */     }
/*     */ 
/* 277 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 286 */     if (this.hash == -1) {
/* 287 */       int i = 0; for (int j = this.selectors.size(); i < j; i++)
/* 288 */         this.hash = (31 * (this.hash + ((SimpleSelector)this.selectors.get(i)).hashCode()));
/* 289 */       i = 0; for (j = this.relationships.size(); i < j; i++)
/* 290 */         this.hash = (31 * (this.hash + ((Combinator)this.relationships.get(i)).hashCode()));
/*     */     }
/* 292 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 297 */     if (paramObject == null) {
/* 298 */       return false;
/*     */     }
/* 300 */     if (getClass() != paramObject.getClass()) {
/* 301 */       return false;
/*     */     }
/* 303 */     CompoundSelector localCompoundSelector = (CompoundSelector)paramObject;
/* 304 */     if (localCompoundSelector.selectors.size() != this.selectors.size()) return false;
/*     */ 
/* 306 */     int i = 0; for (int j = this.selectors.size(); i < j; i++) {
/* 307 */       if (!((SimpleSelector)localCompoundSelector.selectors.get(i)).equals(this.selectors.get(i))) return false;
/*     */     }
/*     */ 
/* 310 */     if (localCompoundSelector.relationships.size() != this.relationships.size()) return false;
/* 311 */     i = 0; for (j = this.relationships.size(); i < j; i++) {
/* 312 */       if (!((Combinator)localCompoundSelector.relationships.get(i)).equals(this.relationships.get(i))) return false;
/*     */     }
/* 314 */     return true;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 318 */     StringBuilder localStringBuilder = new StringBuilder();
/* 319 */     localStringBuilder.append(this.selectors.get(0));
/* 320 */     for (int i = 1; i < this.selectors.size(); i++) {
/* 321 */       localStringBuilder.append(this.relationships.get(i - 1));
/* 322 */       localStringBuilder.append(this.selectors.get(i));
/*     */     }
/* 324 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public void writeBinary(DataOutputStream paramDataOutputStream, StringStore paramStringStore)
/*     */     throws IOException
/*     */   {
/* 330 */     super.writeBinary(paramDataOutputStream, paramStringStore);
/* 331 */     paramDataOutputStream.writeShort(this.selectors.size());
/* 332 */     for (int i = 0; i < this.selectors.size(); i++) ((SimpleSelector)this.selectors.get(i)).writeBinary(paramDataOutputStream, paramStringStore);
/* 333 */     paramDataOutputStream.writeShort(this.relationships.size());
/* 334 */     for (i = 0; i < this.relationships.size(); i++) paramDataOutputStream.writeByte(((Combinator)this.relationships.get(i)).ordinal());
/*     */   }
/*     */ 
/*     */   public static CompoundSelector readBinary(DataInputStream paramDataInputStream, String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/* 341 */     int i = paramDataInputStream.readShort();
/* 342 */     ArrayList localArrayList1 = new ArrayList();
/* 343 */     for (int j = 0; j < i; j++) {
/* 344 */       localArrayList1.add((SimpleSelector)Selector.readBinary(paramDataInputStream, paramArrayOfString));
/*     */     }
/*     */ 
/* 347 */     j = paramDataInputStream.readShort();
/*     */ 
/* 349 */     ArrayList localArrayList2 = new ArrayList();
/* 350 */     for (int k = 0; k < j; k++) {
/* 351 */       int m = paramDataInputStream.readByte();
/* 352 */       if (m == Combinator.CHILD.ordinal()) {
/* 353 */         localArrayList2.add(Combinator.CHILD);
/* 354 */       } else if (m == Combinator.DESCENDANT.ordinal()) {
/* 355 */         localArrayList2.add(Combinator.DESCENDANT);
/*     */       } else {
/* 357 */         if (!$assertionsDisabled) throw new AssertionError(new StringBuilder().append("error deserializing CompoundSelector: Combinator = ").append(m).toString());
/* 358 */         localArrayList2.add(Combinator.DESCENDANT);
/*     */       }
/*     */     }
/* 361 */     return new CompoundSelector(localArrayList1, localArrayList2);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.CompoundSelector
 * JD-Core Version:    0.6.2
 */