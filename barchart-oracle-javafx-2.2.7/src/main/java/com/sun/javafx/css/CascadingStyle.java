/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ class CascadingStyle
/*     */   implements Comparable
/*     */ {
/*     */   private final Style style;
/*     */   private final List<String> pseudoclasses;
/*     */   private final int specificity;
/*     */   private final int ordinal;
/*     */   private final boolean skinProp;
/*  58 */   private int hash = -1;
/*     */ 
/*  61 */   private static Set<String> strSet = new HashSet();
/*     */ 
/*     */   Style getStyle()
/*     */   {
/*  39 */     return this.style;
/*     */   }
/*     */ 
/*     */   CascadingStyle(Style paramStyle, List<String> paramList, int paramInt1, int paramInt2)
/*     */   {
/*  65 */     this.style = paramStyle;
/*  66 */     this.pseudoclasses = paramList;
/*  67 */     this.specificity = paramInt1;
/*  68 */     this.ordinal = paramInt2;
/*  69 */     this.skinProp = "-fx-skin".equals(paramStyle.getDeclaration().getProperty());
/*     */   }
/*     */ 
/*     */   String getProperty()
/*     */   {
/*  74 */     return this.style.getDeclaration().getProperty();
/*     */   }
/*     */ 
/*     */   Selector getSelector()
/*     */   {
/*  79 */     return this.style.getSelector();
/*     */   }
/*     */ 
/*     */   Rule getRule()
/*     */   {
/*  84 */     return this.style.getDeclaration().getRule();
/*     */   }
/*     */ 
/*     */   Stylesheet.Origin getOrigin()
/*     */   {
/*  89 */     return getRule().getOrigin();
/*     */   }
/*     */ 
/*     */   ParsedValue getParsedValue()
/*     */   {
/*  94 */     return this.style.getDeclaration().getParsedValue();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 103 */     if (paramObject == null) {
/* 104 */       return false;
/*     */     }
/* 106 */     if (getClass() != paramObject.getClass()) {
/* 107 */       return false;
/*     */     }
/* 109 */     CascadingStyle localCascadingStyle = (CascadingStyle)paramObject;
/*     */ 
/* 111 */     String str1 = getProperty();
/* 112 */     String str2 = localCascadingStyle.getProperty();
/* 113 */     if (str1 == null ? str2 != null : !str1.equals(str2)) {
/* 114 */       return false;
/*     */     }
/*     */ 
/* 118 */     if ((this.pseudoclasses == null) && (localCascadingStyle.pseudoclasses == null))
/* 119 */       return true;
/* 120 */     if ((this.pseudoclasses == null) || (localCascadingStyle.pseudoclasses == null)) {
/* 121 */       return false;
/*     */     }
/*     */ 
/* 124 */     if (this.pseudoclasses.size() != localCascadingStyle.pseudoclasses.size()) return false;
/*     */ 
/* 128 */     strSet.clear();
/* 129 */     int i = 0; for (int j = this.pseudoclasses.size(); i < j; i++)
/* 130 */       strSet.add(localCascadingStyle.pseudoclasses.get(i));
/* 131 */     i = 0; for (j = localCascadingStyle.pseudoclasses.size(); i < j; i++) {
/* 132 */       if (!strSet.contains(localCascadingStyle.pseudoclasses.get(i))) return false;
/*     */     }
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 143 */     if (this.hash == -1) {
/* 144 */       this.hash = super.hashCode();
/* 145 */       if (this.pseudoclasses != null) {
/* 146 */         int i = 0; for (int j = this.pseudoclasses.size(); i < j; i++) {
/* 147 */           this.hash = (31 * this.hash + ((String)this.pseudoclasses.get(i)).hashCode());
/*     */         }
/*     */       }
/*     */     }
/* 151 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public int compareTo(Object paramObject)
/*     */   {
/* 160 */     CascadingStyle localCascadingStyle = (CascadingStyle)paramObject;
/*     */ 
/* 168 */     Declaration localDeclaration1 = this.style.getDeclaration();
/* 169 */     int i = localDeclaration1 != null ? localDeclaration1.isImportant() : 0;
/* 170 */     Object localObject1 = localDeclaration1 != null ? localDeclaration1.getRule() : null;
/* 171 */     Enum localEnum = localObject1 != null ? localObject1.getOrigin() : null;
/*     */ 
/* 173 */     Declaration localDeclaration2 = localCascadingStyle.style.getDeclaration();
/* 174 */     int j = localDeclaration2 != null ? localDeclaration2.isImportant() : 0;
/* 175 */     Object localObject2 = localDeclaration2 != null ? localDeclaration2.getRule() : null;
/* 176 */     Object localObject3 = localObject1 != null ? localObject2.getOrigin() : null;
/*     */ 
/* 178 */     int k = 0;
/* 179 */     if ((this.skinProp) && (!localCascadingStyle.skinProp))
/* 180 */       k = 1;
/* 181 */     else if (i != j)
/* 182 */       k = i != 0 ? -1 : 1;
/* 183 */     else if (localEnum != localObject3) {
/* 184 */       if (localEnum == null) k = -1;
/* 185 */       else if (localObject3 == null) k = 1; else
/* 186 */         k = localObject3.compareTo(localEnum);
/*     */     }
/* 188 */     else k = localCascadingStyle.specificity - this.specificity;
/*     */ 
/* 191 */     if (k == 0) k = localCascadingStyle.ordinal - this.ordinal;
/* 192 */     return k;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.CascadingStyle
 * JD-Core Version:    0.6.2
 */