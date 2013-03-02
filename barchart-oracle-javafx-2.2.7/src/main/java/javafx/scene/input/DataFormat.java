/*     */ package javafx.scene.input;
/*     */ 
/*     */ import com.sun.javafx.WeakReferenceQueue;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class DataFormat
/*     */ {
/*  49 */   private static final WeakReferenceQueue<DataFormat> DATA_FORMAT_LIST = new WeakReferenceQueue();
/*     */ 
/*  54 */   public static final DataFormat PLAIN_TEXT = new DataFormat(new String[] { "text/plain" });
/*     */ 
/*  59 */   public static final DataFormat HTML = new DataFormat(new String[] { "text/html" });
/*     */ 
/*  64 */   public static final DataFormat RTF = new DataFormat(new String[] { "text/rtf" });
/*     */ 
/*  69 */   public static final DataFormat URL = new DataFormat(new String[] { "text/uri-list" });
/*     */ 
/*  77 */   public static final DataFormat IMAGE = new DataFormat(new String[] { "application/x-java-rawimage" });
/*     */ 
/*  82 */   public static final DataFormat FILES = new DataFormat(new String[] { "application/x-java-file-list", "java.file-list" });
/*     */   private final Set<String> identifier;
/*     */ 
/*     */   public DataFormat(String[] paramArrayOfString)
/*     */   {
/* 110 */     DATA_FORMAT_LIST.cleanup();
/* 111 */     if (paramArrayOfString != null) {
/* 112 */       for (String str : paramArrayOfString) {
/* 113 */         if (lookupMimeType(str) != null) {
/* 114 */           throw new IllegalArgumentException(new StringBuilder().append("DataFormat '").append(str).append("' already exists.").toString());
/*     */         }
/*     */       }
/*     */ 
/* 118 */       this.identifier = Collections.unmodifiableSet(new HashSet(Arrays.asList(paramArrayOfString)));
/*     */     } else {
/* 120 */       this.identifier = Collections.emptySet();
/*     */     }
/*     */ 
/* 124 */     DATA_FORMAT_LIST.add(this);
/*     */   }
/*     */ 
/*     */   public final Set<String> getIdentifiers()
/*     */   {
/* 132 */     return this.identifier;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 140 */     if (this.identifier.isEmpty())
/* 141 */       return "[]";
/* 142 */     if (this.identifier.size() == 1) {
/* 143 */       localStringBuilder = new StringBuilder("[");
/* 144 */       localStringBuilder.append((String)this.identifier.iterator().next());
/* 145 */       return localStringBuilder.append("]").toString();
/*     */     }
/* 147 */     StringBuilder localStringBuilder = new StringBuilder("[");
/* 148 */     Iterator localIterator = this.identifier.iterator();
/* 149 */     while (localIterator.hasNext()) {
/* 150 */       localStringBuilder = localStringBuilder.append((String)localIterator.next());
/* 151 */       if (localIterator.hasNext()) {
/* 152 */         localStringBuilder = localStringBuilder.append(", ");
/*     */       }
/*     */     }
/* 155 */     localStringBuilder = localStringBuilder.append("]");
/* 156 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 165 */     int i = 7;
/*     */ 
/* 167 */     for (String str : this.identifier) {
/* 168 */       i = 31 * i + str.hashCode();
/*     */     }
/*     */ 
/* 171 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 180 */     if ((paramObject == null) || (!(paramObject instanceof DataFormat))) {
/* 181 */       return false;
/*     */     }
/*     */ 
/* 184 */     DataFormat localDataFormat = (DataFormat)paramObject;
/*     */ 
/* 186 */     if (this.identifier.equals(localDataFormat.identifier)) {
/* 187 */       return true;
/*     */     }
/*     */ 
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */   public static DataFormat lookupMimeType(String paramString)
/*     */   {
/* 200 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 201 */       return null;
/*     */     }
/*     */ 
/* 204 */     Iterator localIterator = DATA_FORMAT_LIST.iterator();
/* 205 */     while (localIterator.hasNext()) {
/* 206 */       DataFormat localDataFormat = (DataFormat)localIterator.next();
/* 207 */       if (localDataFormat.getIdentifiers().contains(paramString)) {
/* 208 */         return localDataFormat;
/*     */       }
/*     */     }
/* 211 */     return null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.DataFormat
 * JD-Core Version:    0.6.2
 */