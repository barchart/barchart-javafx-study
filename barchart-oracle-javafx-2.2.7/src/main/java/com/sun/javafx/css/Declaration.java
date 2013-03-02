/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public final class Declaration
/*     */ {
/*     */   final String property;
/*     */   final ParsedValue parsedValue;
/*     */   final boolean important;
/*     */   Rule rule;
/*     */ 
/*     */   public Declaration(String paramString, ParsedValue paramParsedValue, boolean paramBoolean)
/*     */   {
/*  60 */     this.property = paramString;
/*  61 */     this.parsedValue = paramParsedValue;
/*  62 */     this.important = paramBoolean;
/*     */   }
/*     */ 
/*     */   public ParsedValue getParsedValue()
/*     */   {
/*  67 */     return this.parsedValue;
/*     */   }
/*     */ 
/*     */   public String getProperty()
/*     */   {
/*  72 */     return this.property;
/*     */   }
/*     */ 
/*     */   public Rule getRule()
/*     */   {
/*  77 */     return this.rule;
/*     */   }
/*     */ 
/*     */   public boolean isImportant() {
/*  81 */     return this.important;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  91 */     if (this == paramObject) {
/*  92 */       return true;
/*     */     }
/*  94 */     if (paramObject == null) {
/*  95 */       return false;
/*     */     }
/*  97 */     if (getClass() != paramObject.getClass()) {
/*  98 */       return false;
/*     */     }
/* 100 */     Declaration localDeclaration = (Declaration)paramObject;
/* 101 */     if (this.property == null ? localDeclaration.property != null : !this.property.equals(localDeclaration.property)) {
/* 102 */       return false;
/*     */     }
/* 104 */     if ((this.parsedValue != localDeclaration.parsedValue) && ((this.parsedValue == null) || (!this.parsedValue.equals(localDeclaration.parsedValue)))) {
/* 105 */       return false;
/*     */     }
/* 107 */     if (this.important != localDeclaration.important) {
/* 108 */       return false;
/*     */     }
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 115 */     int i = 5;
/* 116 */     i = 89 * i + (this.property != null ? this.property.hashCode() : 0);
/* 117 */     i = 89 * i + (this.parsedValue != null ? this.parsedValue.hashCode() : 0);
/* 118 */     i = 89 * i + (this.important ? 1 : 0);
/* 119 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 123 */     StringBuilder localStringBuilder = new StringBuilder(this.property);
/* 124 */     localStringBuilder.append(": ");
/* 125 */     localStringBuilder.append(this.parsedValue);
/* 126 */     if (this.important) localStringBuilder.append(" !important");
/* 127 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   void writeBinary(DataOutputStream paramDataOutputStream, StringStore paramStringStore)
/*     */     throws IOException
/*     */   {
/* 133 */     paramDataOutputStream.writeShort(paramStringStore.addString(this.property));
/* 134 */     this.parsedValue.writeBinary(paramDataOutputStream, paramStringStore);
/* 135 */     paramDataOutputStream.writeBoolean(this.important);
/*     */   }
/*     */ 
/*     */   static Declaration readBinary(DataInputStream paramDataInputStream, String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/* 141 */     String str = paramArrayOfString[paramDataInputStream.readShort()];
/* 142 */     ParsedValue localParsedValue = ParsedValue.readBinary(paramDataInputStream, paramArrayOfString);
/* 143 */     boolean bool = paramDataInputStream.readBoolean();
/* 144 */     return new Declaration(str, localParsedValue, bool);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.Declaration
 * JD-Core Version:    0.6.2
 */