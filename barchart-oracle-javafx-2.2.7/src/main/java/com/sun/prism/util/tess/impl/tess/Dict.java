/*     */ package com.sun.prism.util.tess.impl.tess;
/*     */ 
/*     */ class Dict
/*     */ {
/*     */   DictNode head;
/*     */   Object frame;
/*     */   DictLeq leq;
/*     */ 
/*     */   static Dict dictNewDict(Object paramObject, DictLeq paramDictLeq)
/*     */   {
/*  64 */     Dict localDict = new Dict();
/*  65 */     localDict.head = new DictNode();
/*     */ 
/*  67 */     localDict.head.key = null;
/*  68 */     localDict.head.next = localDict.head;
/*  69 */     localDict.head.prev = localDict.head;
/*     */ 
/*  71 */     localDict.frame = paramObject;
/*  72 */     localDict.leq = paramDictLeq;
/*     */ 
/*  74 */     return localDict;
/*     */   }
/*     */ 
/*     */   static void dictDeleteDict(Dict paramDict) {
/*  78 */     paramDict.head = null;
/*  79 */     paramDict.frame = null;
/*  80 */     paramDict.leq = null;
/*     */   }
/*     */ 
/*     */   static DictNode dictInsert(Dict paramDict, Object paramObject) {
/*  84 */     return dictInsertBefore(paramDict, paramDict.head, paramObject);
/*     */   }
/*     */ 
/*     */   static DictNode dictInsertBefore(Dict paramDict, DictNode paramDictNode, Object paramObject) {
/*     */     do
/*  89 */       paramDictNode = paramDictNode.prev;
/*  90 */     while ((paramDictNode.key != null) && (!paramDict.leq.leq(paramDict.frame, paramDictNode.key, paramObject)));
/*     */ 
/*  92 */     DictNode localDictNode = new DictNode();
/*  93 */     localDictNode.key = paramObject;
/*  94 */     localDictNode.next = paramDictNode.next;
/*  95 */     paramDictNode.next.prev = localDictNode;
/*  96 */     localDictNode.prev = paramDictNode;
/*  97 */     paramDictNode.next = localDictNode;
/*     */ 
/*  99 */     return localDictNode;
/*     */   }
/*     */ 
/*     */   static Object dictKey(DictNode paramDictNode) {
/* 103 */     return paramDictNode.key;
/*     */   }
/*     */ 
/*     */   static DictNode dictSucc(DictNode paramDictNode) {
/* 107 */     return paramDictNode.next;
/*     */   }
/*     */ 
/*     */   static DictNode dictPred(DictNode paramDictNode) {
/* 111 */     return paramDictNode.prev;
/*     */   }
/*     */ 
/*     */   static DictNode dictMin(Dict paramDict) {
/* 115 */     return paramDict.head.next;
/*     */   }
/*     */ 
/*     */   static DictNode dictMax(Dict paramDict) {
/* 119 */     return paramDict.head.prev;
/*     */   }
/*     */ 
/*     */   static void dictDelete(Dict paramDict, DictNode paramDictNode) {
/* 123 */     paramDictNode.next.prev = paramDictNode.prev;
/* 124 */     paramDictNode.prev.next = paramDictNode.next;
/*     */   }
/*     */ 
/*     */   static DictNode dictSearch(Dict paramDict, Object paramObject) {
/* 128 */     DictNode localDictNode = paramDict.head;
/*     */     do
/*     */     {
/* 131 */       localDictNode = localDictNode.next;
/* 132 */     }while ((localDictNode.key != null) && (!paramDict.leq.leq(paramDict.frame, paramObject, localDictNode.key)));
/*     */ 
/* 134 */     return localDictNode;
/*     */   }
/*     */ 
/*     */   public static abstract interface DictLeq
/*     */   {
/*     */     public abstract boolean leq(Object paramObject1, Object paramObject2, Object paramObject3);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.Dict
 * JD-Core Version:    0.6.2
 */