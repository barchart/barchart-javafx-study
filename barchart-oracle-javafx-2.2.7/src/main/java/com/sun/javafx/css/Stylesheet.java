/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public final class Stylesheet
/*     */ {
/*     */   private final URL url;
/*  78 */   private Origin origin = Origin.AUTHOR;
/*     */ 
/*  87 */   private final ObservableList<Rule> rules = new TrackableObservableList()
/*     */   {
/*     */     protected void onChanged(ListChangeListener.Change<Rule> paramAnonymousChange)
/*     */     {
/*     */       Iterator localIterator;
/*     */       Rule localRule;
/*  91 */       while (paramAnonymousChange.next())
/*  92 */         if (paramAnonymousChange.wasAdded())
/*  93 */           for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localRule = (Rule)localIterator.next();
/*  94 */             localRule.setStylesheet(Stylesheet.this);
/*     */           }
/*  96 */         else if (paramAnonymousChange.wasRemoved())
/*  97 */           for (localIterator = paramAnonymousChange.getRemoved().iterator(); localIterator.hasNext(); ) { localRule = (Rule)localIterator.next();
/*  98 */             if (localRule.getStylesheet() == Stylesheet.this) localRule.setStylesheet(null);
/*     */           }
/*     */     }
/*  87 */   };
/*     */ 
/*     */   public URL getUrl()
/*     */   {
/*  70 */     return this.url;
/*     */   }
/*     */ 
/*     */   public Origin getOrigin()
/*     */   {
/*  80 */     return this.origin;
/*     */   }
/*     */   public void setOrigin(Origin paramOrigin) {
/*  83 */     this.origin = paramOrigin;
/*     */   }
/*     */ 
/*     */   public Stylesheet()
/*     */   {
/* 123 */     this(null);
/*     */   }
/*     */ 
/*     */   public Stylesheet(URL paramURL)
/*     */   {
/* 132 */     this.url = paramURL;
/*     */   }
/*     */ 
/*     */   public List<Rule> getRules()
/*     */   {
/* 137 */     return this.rules;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject) {
/* 141 */     if (this == paramObject) return true;
/* 142 */     if ((paramObject instanceof Stylesheet)) {
/* 143 */       Stylesheet localStylesheet = (Stylesheet)paramObject;
/* 144 */       return this.url == null ? false : localStylesheet.url == null ? true : this.url.equals(localStylesheet.url);
/*     */     }
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 151 */     StringBuilder localStringBuilder = new StringBuilder();
/* 152 */     localStringBuilder.append("/* ");
/* 153 */     if (this.url != null) localStringBuilder.append(this.url);
/* 154 */     if (this.rules.isEmpty()) {
/* 155 */       localStringBuilder.append(" */");
/*     */     } else {
/* 157 */       localStringBuilder.append(" */\n");
/* 158 */       for (int i = 0; i < this.rules.size(); i++) {
/* 159 */         localStringBuilder.append(this.rules.get(i));
/* 160 */         localStringBuilder.append('\n');
/*     */       }
/*     */     }
/* 163 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public void writeBinary(DataOutputStream paramDataOutputStream, StringStore paramStringStore)
/*     */     throws IOException
/*     */   {
/* 169 */     int i = paramStringStore.addString(this.origin.name());
/* 170 */     paramDataOutputStream.writeShort(i);
/* 171 */     paramDataOutputStream.writeShort(this.rules.size());
/*     */     Rule localRule;
/* 172 */     for (Iterator localIterator = this.rules.iterator(); localIterator.hasNext(); localRule.writeBinary(paramDataOutputStream, paramStringStore)) localRule = (Rule)localIterator.next();
/*     */   }
/*     */ 
/*     */   public void readBinary(DataInputStream paramDataInputStream, String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/* 180 */     int i = paramDataInputStream.readShort();
/* 181 */     setOrigin(Origin.valueOf(paramArrayOfString[i]));
/* 182 */     int j = paramDataInputStream.readShort();
/* 183 */     ArrayList localArrayList = new ArrayList(j);
/* 184 */     for (int k = 0; k < j; k++) {
/* 185 */       localArrayList.add(Rule.readBinary(paramDataInputStream, paramArrayOfString));
/*     */     }
/* 187 */     this.rules.addAll(localArrayList);
/*     */   }
/*     */ 
/*     */   public static Stylesheet loadBinary(URL paramURL)
/*     */   {
/* 194 */     if (paramURL == null) return null;
/*     */ 
/* 196 */     Stylesheet localStylesheet = null;
/* 197 */     InputStream localInputStream = null;
/* 198 */     BufferedInputStream localBufferedInputStream = null;
/* 199 */     DataInputStream localDataInputStream = null;
/*     */     try {
/* 201 */       localInputStream = paramURL.openStream();
/*     */ 
/* 203 */       localBufferedInputStream = new BufferedInputStream(localInputStream, 40960);
/*     */ 
/* 205 */       localDataInputStream = new DataInputStream(localBufferedInputStream);
/*     */ 
/* 207 */       int i = localDataInputStream.readShort();
/* 208 */       if (i != 2) {
/* 209 */         throw new IOException(new StringBuilder().append(paramURL.toString()).append(" wrong file version. got ").append(i).append(" expected 2").toString());
/*     */       }
/*     */ 
/* 212 */       String[] arrayOfString = StringStore.readBinary(localDataInputStream);
/*     */ 
/* 214 */       localStylesheet = new Stylesheet(paramURL);
/* 215 */       localStylesheet.readBinary(localDataInputStream, arrayOfString);
/*     */     }
/*     */     catch (FileNotFoundException localFileNotFoundException)
/*     */     {
/*     */     }
/*     */     catch (IOException localIOException3)
/*     */     {
/* 222 */       System.err.println(localIOException3);
/* 223 */       localIOException3.printStackTrace(System.err);
/*     */     } finally {
/*     */       try {
/* 226 */         if (localDataInputStream != null)
/* 227 */           localDataInputStream.close();
/* 228 */         else if (localBufferedInputStream != null)
/* 229 */           localBufferedInputStream.close();
/* 230 */         else if (localInputStream != null) {
/* 231 */           localInputStream.close();
/*     */         }
/*     */       }
/*     */       catch (IOException localIOException5)
/*     */       {
/*     */       }
/*     */     }
/* 238 */     return localStylesheet;
/*     */   }
/*     */ 
/*     */   public static enum Origin
/*     */   {
/*  58 */     USER_AGENT, 
/*  59 */     USER, 
/*  60 */     AUTHOR, 
/*  61 */     INLINE;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.Stylesheet
 * JD-Core Version:    0.6.2
 */