/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ 
/*     */ public final class Rule
/*     */ {
/*     */   final List<Selector> selectors;
/*  49 */   final ObservableList<Declaration> declarations = new TrackableObservableList()
/*     */   {
/*     */     protected void onChanged(ListChangeListener.Change<Declaration> paramAnonymousChange)
/*     */     {
/*     */       Iterator localIterator;
/*     */       Declaration localDeclaration;
/*  54 */       while (paramAnonymousChange.next())
/*  55 */         if (paramAnonymousChange.wasAdded())
/*  56 */           for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localDeclaration = (Declaration)localIterator.next();
/*  57 */             localDeclaration.rule = Rule.this;
/*     */           }
/*  59 */         else if (paramAnonymousChange.wasRemoved())
/*  60 */           for (localIterator = paramAnonymousChange.getRemoved().iterator(); localIterator.hasNext(); ) { localDeclaration = (Declaration)localIterator.next();
/*  61 */             if (localDeclaration.rule == Rule.this) localDeclaration.rule = null;
/*     */           }
/*     */     }
/*  49 */   };
/*     */   private Stylesheet stylesheet;
/*     */ 
/*     */   public List<Selector> getSelectors()
/*     */   {
/*  46 */     return this.selectors;
/*     */   }
/*     */ 
/*     */   public List<Declaration> getDeclarations()
/*     */   {
/*  69 */     return this.declarations;
/*     */   }
/*     */ 
/*     */   public Stylesheet getStylesheet()
/*     */   {
/*  75 */     return this.stylesheet;
/*     */   }
/*     */ 
/*     */   void setStylesheet(Stylesheet paramStylesheet) {
/*  79 */     this.stylesheet = paramStylesheet;
/*     */   }
/*     */ 
/*     */   public Stylesheet.Origin getOrigin() {
/*  83 */     return this.stylesheet != null ? this.stylesheet.getOrigin() : null;
/*     */   }
/*     */ 
/*     */   public Rule(List<Selector> paramList, List<Declaration> paramList1)
/*     */   {
/*  88 */     this.selectors = paramList;
/*  89 */     this.declarations.addAll(paramList1);
/*     */   }
/*     */ 
/*     */   private Rule() {
/*  93 */     this(null, null);
/*     */   }
/*     */ 
/*     */   List<Match> matches(Node paramNode)
/*     */   {
/* 108 */     ArrayList localArrayList = new ArrayList();
/* 109 */     for (int i = 0; i < this.selectors.size(); i++) {
/* 110 */       Selector localSelector = (Selector)this.selectors.get(i);
/* 111 */       localArrayList.add(localSelector.matches(paramNode));
/*     */     }
/* 113 */     return localArrayList;
/*     */   }
/*     */ 
/*     */   List<Match> matches(Scene paramScene) {
/* 117 */     ArrayList localArrayList = new ArrayList();
/* 118 */     for (int i = 0; i < this.selectors.size(); i++) {
/* 119 */       Selector localSelector = (Selector)this.selectors.get(i);
/* 120 */       localArrayList.add(localSelector.matches(paramScene));
/*     */     }
/* 122 */     return localArrayList;
/*     */   }
/*     */ 
/*     */   public boolean mightApply(String paramString1, String paramString2, List<String> paramList) {
/* 126 */     for (int i = 0; i < this.selectors.size(); i++) {
/* 127 */       Selector localSelector = (Selector)this.selectors.get(i);
/* 128 */       if (localSelector.mightApply(paramString1, paramString2, paramList)) return true;
/*     */     }
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean applies(Node paramNode) {
/* 134 */     for (int i = 0; i < this.selectors.size(); i++) {
/* 135 */       Selector localSelector = (Selector)this.selectors.get(i);
/* 136 */       if (localSelector.applies(paramNode)) return true;
/*     */     }
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 144 */     StringBuilder localStringBuilder = new StringBuilder();
/* 145 */     if (this.selectors.size() > 0) {
/* 146 */       localStringBuilder.append(this.selectors.get(0));
/*     */     }
/* 148 */     for (int i = 1; i < this.selectors.size(); i++) {
/* 149 */       localStringBuilder.append(',');
/* 150 */       localStringBuilder.append(this.selectors.get(i));
/*     */     }
/* 152 */     localStringBuilder.append("{\n");
/* 153 */     for (Declaration localDeclaration : this.declarations) {
/* 154 */       localStringBuilder.append("\t");
/* 155 */       localStringBuilder.append(localDeclaration);
/* 156 */       localStringBuilder.append("\n");
/*     */     }
/* 158 */     localStringBuilder.append("}");
/* 159 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   void writeBinary(DataOutputStream paramDataOutputStream, StringStore paramStringStore) throws IOException
/*     */   {
/* 164 */     paramDataOutputStream.writeShort(this.selectors.size());
/*     */     Object localObject;
/* 165 */     for (int i = 0; i < this.selectors.size(); i++) {
/* 166 */       localObject = (Selector)this.selectors.get(i);
/* 167 */       ((Selector)localObject).writeBinary(paramDataOutputStream, paramStringStore);
/*     */     }
/* 169 */     paramDataOutputStream.writeShort(this.declarations.size());
/* 170 */     for (i = 0; i < this.declarations.size(); i++) {
/* 171 */       localObject = (Declaration)this.declarations.get(i);
/* 172 */       ((Declaration)localObject).writeBinary(paramDataOutputStream, paramStringStore);
/*     */     }
/*     */   }
/*     */ 
/*     */   static Rule readBinary(DataInputStream paramDataInputStream, String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/* 179 */     int i = paramDataInputStream.readShort();
/* 180 */     ArrayList localArrayList = new ArrayList(i);
/* 181 */     for (int j = 0; j < i; j++) {
/* 182 */       localObject = Selector.readBinary(paramDataInputStream, paramArrayOfString);
/* 183 */       localArrayList.add(localObject);
/*     */     }
/*     */ 
/* 186 */     j = paramDataInputStream.readShort();
/* 187 */     Object localObject = new ArrayList(j);
/* 188 */     for (int k = 0; k < j; k++) {
/* 189 */       Declaration localDeclaration = Declaration.readBinary(paramDataInputStream, paramArrayOfString);
/* 190 */       ((List)localObject).add(localDeclaration);
/*     */     }
/*     */ 
/* 193 */     return new Rule(localArrayList, (List)localObject);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.Rule
 * JD-Core Version:    0.6.2
 */