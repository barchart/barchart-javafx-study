/*     */ package com.sun.webpane.webkit.network;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class DirectoryURLConnection extends URLConnection
/*     */ {
/*  28 */   private static String[] patStrings = { "([\\-ld](?:[r\\-][w\\-][x\\-]){3})\\s*\\d+ (\\w+)\\s*(\\w+)\\s*(\\d+)\\s*([A-Z][a-z][a-z]\\s*\\d+)\\s*((?:\\d\\d:\\d\\d)|(?:\\d{4}))\\s*(\\p{Print}*)", "(\\d{2}/\\d{2}/\\d{4})\\s*(\\d{2}:\\d{2}[ap])\\s*((?:[0-9,]+)|(?:<DIR>))\\s*(\\p{Graph}*)", "(\\d{2}-\\d{2}-\\d{2})\\s*(\\d{2}:\\d{2}[AP]M)\\s*((?:[0-9,]+)|(?:<DIR>))\\s*(\\p{Graph}*)" };
/*     */ 
/*  37 */   private static int[][] patternGroups = { { 7, 4, 5, 6, 1 }, { 4, 3, 1, 2, 0 }, { 4, 3, 1, 2, 0 } };
/*     */ 
/*  43 */   private static Pattern[] patterns = null;
/*  44 */   private static Pattern linkp = Pattern.compile("(\\p{Print}+) \\-\\> (\\p{Print}+)$");
/*     */ 
/*  46 */   private static String styleSheet = "<style type=\"text/css\" media=\"screen\">TABLE { border: 0;}TR.header { background: #FFFFFF; color: black; font-weight: bold; text-align: center;}TR.odd { background: #E0E0E0;}TR.even { background: #C0C0C0;}TD.file { text-align: left;}TD.fsize { text-align: right; padding-right: 1em;}TD.dir { text-align: center; color: green; padding-right: 1em;}TD.link { text-align: center; color: red; padding-right: 1em;}TD.date { text-align: justify;}</style>";
/*     */ 
/*  65 */   private URLConnection inner = null;
/*  66 */   private boolean sure = true;
/*  67 */   private String dirUrl = null;
/*     */ 
/*  70 */   private boolean toHTML = true;
/*  71 */   private boolean ftp = true;
/*  72 */   private InputStream ins = null;
/*     */ 
/*     */   private DirectoryURLConnection()
/*     */   {
/* 378 */     super(null);
/*     */   }
/*     */ 
/*     */   public DirectoryURLConnection(URLConnection paramURLConnection, boolean paramBoolean)
/*     */   {
/* 388 */     super(paramURLConnection.getURL());
/* 389 */     this.dirUrl = paramURLConnection.getURL().toExternalForm();
/* 390 */     this.inner = paramURLConnection;
/* 391 */     this.sure = (!paramBoolean);
/* 392 */     this.ftp = true;
/*     */   }
/*     */ 
/*     */   public DirectoryURLConnection(URLConnection paramURLConnection)
/*     */   {
/* 400 */     super(paramURLConnection.getURL());
/* 401 */     this.dirUrl = paramURLConnection.getURL().toExternalForm();
/* 402 */     this.ftp = false;
/* 403 */     this.sure = true;
/* 404 */     this.inner = paramURLConnection;
/*     */   }
/*     */ 
/*     */   public void connect()
/*     */     throws IOException
/*     */   {
/* 410 */     this.inner.connect();
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream()
/*     */     throws IOException
/*     */   {
/* 416 */     if (this.ins == null) {
/* 417 */       if (this.ftp)
/* 418 */         this.ins = new DirectoryInputStream(this.inner.getInputStream(), !this.sure);
/*     */       else {
/* 420 */         this.ins = new DirectoryInputStream(this.inner.getInputStream(), false);
/*     */       }
/*     */     }
/* 423 */     return this.ins;
/*     */   }
/*     */ 
/*     */   public String getContentType()
/*     */   {
/*     */     try
/*     */     {
/* 430 */       if (!this.sure)
/* 431 */         getInputStream();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/* 436 */     if (this.toHTML) {
/* 437 */       return "text/html";
/*     */     }
/*     */ 
/* 440 */     return this.inner.getContentType();
/*     */   }
/*     */ 
/*     */   public String getContentEncoding()
/*     */   {
/* 446 */     return this.inner.getContentEncoding();
/*     */   }
/*     */ 
/*     */   public int getContentLength()
/*     */   {
/* 452 */     return this.inner.getContentLength();
/*     */   }
/*     */ 
/*     */   public Map<String, List<String>> getHeaderFields()
/*     */   {
/* 458 */     return this.inner.getHeaderFields();
/*     */   }
/*     */ 
/*     */   public String getHeaderField(String paramString)
/*     */   {
/* 464 */     return this.inner.getHeaderField(paramString);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  60 */     patterns = new Pattern[patStrings.length];
/*  61 */     for (int i = 0; i < patStrings.length; i++)
/*  62 */       patterns[i] = Pattern.compile(patStrings[i]);
/*     */   }
/*     */ 
/*     */   private class DirectoryInputStream extends PushbackInputStream
/*     */   {
/*  91 */     private byte[] buffer = new byte[512];
/*     */ 
/*  81 */     private boolean endOfStream = false;
/*  82 */     private ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
/*  83 */     private PrintStream out = new PrintStream(this.bytesOut);
/*  84 */     private ByteArrayInputStream bytesIn = null;
/*  85 */     private StringBuffer tmpString = new StringBuffer();
/*  86 */     private int lineCount = 0;
/*     */ 
/*     */     public DirectoryInputStream(InputStream paramBoolean, boolean arg3)
/*     */     {
/*  90 */       super(512);
/*     */       int i;
/*     */       Object localObject;
/*  96 */       if (i != 0) {
/*  97 */         localObject = new StringBuffer();
/*  98 */         int j = 0;
/*     */         try
/*     */         {
/* 101 */           j = super.read(this.buffer, 0, this.buffer.length);
/*     */         } catch (IOException localIOException1) {
/*     */         }
/* 104 */         if (j <= 0) {
/* 105 */           DirectoryURLConnection.this.toHTML = false;
/*     */         } else {
/* 107 */           for (int k = 0; k < j; k++) {
/* 108 */             ((StringBuffer)localObject).append((char)this.buffer[k]);
/*     */           }
/* 110 */           String str2 = ((StringBuffer)localObject).toString();
/* 111 */           DirectoryURLConnection.this.toHTML = false;
/* 112 */           for (Pattern localPattern : DirectoryURLConnection.patterns) {
/* 113 */             Matcher localMatcher = localPattern.matcher(str2);
/* 114 */             if (localMatcher.find())
/*     */             {
/* 117 */               DirectoryURLConnection.this.toHTML = true;
/* 118 */               break;
/*     */             }
/*     */           }
/*     */           try {
/* 122 */             super.unread(this.buffer, 0, j);
/*     */           }
/*     */           catch (IOException localIOException2) {
/*     */           }
/*     */         }
/*     */       }
/* 128 */       if (DirectoryURLConnection.this.toHTML)
/*     */       {
/* 132 */         localObject = null;
/*     */ 
/* 134 */         URL localURL = null;
/* 135 */         if (!DirectoryURLConnection.this.dirUrl.endsWith("/"))
/* 136 */           DirectoryURLConnection.this.dirUrl = new StringBuilder().append(DirectoryURLConnection.this.dirUrl).append("/").toString();
/*     */         try
/*     */         {
/* 139 */           localURL = URLs.newURL(DirectoryURLConnection.this.dirUrl);
/*     */         }
/*     */         catch (Exception localException) {
/*     */         }
/* 143 */         String str1 = localURL.getPath();
/* 144 */         if ((str1 != null) && (!str1.isEmpty())) {
/* 145 */           int m = str1.lastIndexOf("/", str1.length() - 2);
/* 146 */           if (m >= 0) {
/* 147 */             int n = str1.length() - m - 1;
/* 148 */             m = DirectoryURLConnection.this.dirUrl.indexOf(str1);
/* 149 */             localObject = new StringBuilder().append(DirectoryURLConnection.this.dirUrl.substring(0, m + str1.length() - n)).append(DirectoryURLConnection.this.dirUrl.substring(m + str1.length())).toString();
/*     */           }
/*     */         }
/* 152 */         this.out.print("<html><head><title>index of ");
/* 153 */         this.out.print(DirectoryURLConnection.this.dirUrl);
/* 154 */         this.out.print("</title>");
/* 155 */         this.out.print(DirectoryURLConnection.styleSheet);
/* 156 */         this.out.print("</head><body><h1>Index of ");
/* 157 */         this.out.print(DirectoryURLConnection.this.dirUrl);
/* 158 */         this.out.print("</h1><hr></hr>");
/* 159 */         this.out.print("<TABLE width=\"95%\" cellpadding=\"5\" cellspacing=\"5\">");
/* 160 */         this.out.print("<TR class=\"header\"><TD>File</TD><TD>Size</TD><TD>Last Modified</TD></TR>");
/* 161 */         if (localObject != null) {
/* 162 */           this.lineCount += 1;
/* 163 */           this.out.print("<TR class=\"odd\"><TD colspan=3 class=\"file\"><a href=\"");
/* 164 */           this.out.print((String)localObject);
/* 165 */           this.out.print("\">Up to parent directory</a></TD></TR>");
/*     */         }
/* 167 */         this.out.close();
/* 168 */         this.bytesIn = new ByteArrayInputStream(this.bytesOut.toByteArray());
/* 169 */         this.out = null;
/* 170 */         this.bytesOut = null;
/*     */       }
/*     */     }
/*     */ 
/*     */     private void parseFile(String paramString)
/*     */     {
/* 176 */       this.tmpString.append(paramString);
/*     */       int i;
/* 178 */       while ((i = this.tmpString.indexOf("\n")) >= 0) {
/* 179 */         String str1 = this.tmpString.substring(0, i);
/* 180 */         this.tmpString.delete(0, i + 1);
/* 181 */         String str2 = str1;
/* 182 */         String str3 = null;
/* 183 */         String str4 = null;
/* 184 */         int j = 0;
/* 185 */         int k = 0;
/* 186 */         URL localURL = null;
/*     */ 
/* 188 */         if (str2 != null) {
/* 189 */           this.lineCount += 1;
/*     */           try {
/* 191 */             localURL = URLs.newURL(new StringBuilder().append(DirectoryURLConnection.this.dirUrl).append(URLEncoder.encode(str2, "UTF-8")).toString());
/* 192 */             URLConnection localURLConnection = localURL.openConnection();
/* 193 */             localURLConnection.connect();
/* 194 */             str4 = localURLConnection.getHeaderField("last-modified");
/* 195 */             str3 = localURLConnection.getHeaderField("content-length");
/* 196 */             if (str3 == null) {
/* 197 */               j = 1;
/*     */             }
/* 199 */             localURLConnection.getInputStream().close();
/*     */           }
/*     */           catch (IOException localIOException) {
/* 202 */             k = 1;
/*     */           }
/* 204 */           if (this.bytesOut == null) {
/* 205 */             this.bytesOut = new ByteArrayOutputStream();
/* 206 */             this.out = new PrintStream(this.bytesOut);
/*     */           }
/* 208 */           this.out.print(new StringBuilder().append("<TR class=\"").append(this.lineCount % 2 == 0 ? "even" : "odd").append("\"><TD class=\"file\">").toString());
/* 209 */           if (k != 0) {
/* 210 */             this.out.print(str2);
/*     */           } else {
/* 212 */             this.out.print("<a href=\"");
/* 213 */             this.out.print(localURL.toExternalForm());
/* 214 */             this.out.print("\">");
/* 215 */             this.out.print(str2);
/* 216 */             this.out.print("</a>");
/*     */           }
/* 218 */           if (j != 0)
/* 219 */             this.out.print("</TD><TD class=\"dir\">&lt;Directory&gt;</TD>");
/*     */           else {
/* 221 */             this.out.print(new StringBuilder().append("</TD><TD class=\"fsize\">").append(str3 == null ? " " : str3).append("</TD>").toString());
/*     */           }
/* 223 */           this.out.print(new StringBuilder().append("<TD class=\"date\">").append(str4 == null ? " " : str4).append("</TD></TR>").toString());
/*     */         }
/*     */       }
/* 226 */       if (this.bytesOut != null) {
/* 227 */         this.out.close();
/* 228 */         this.bytesIn = new ByteArrayInputStream(this.bytesOut.toByteArray());
/* 229 */         this.out = null;
/* 230 */         this.bytesOut = null;
/*     */       }
/*     */     }
/*     */ 
/*     */     private void parseFTP(String paramString)
/*     */     {
/* 236 */       this.tmpString.append(paramString);
/*     */       int i;
/* 238 */       while ((i = this.tmpString.indexOf("\n")) >= 0) {
/* 239 */         String str1 = this.tmpString.substring(0, i);
/* 240 */         this.tmpString.delete(0, i + 1);
/* 241 */         String str2 = null;
/* 242 */         String str3 = null;
/* 243 */         String str4 = null;
/* 244 */         String str5 = null;
/* 245 */         boolean bool = false;
/*     */ 
/* 247 */         Matcher localMatcher = null;
/* 248 */         for (int j = 0; j < DirectoryURLConnection.patterns.length; j++) {
/* 249 */           localMatcher = DirectoryURLConnection.patterns[j].matcher(str1);
/* 250 */           if (localMatcher.find()) {
/* 251 */             str2 = localMatcher.group(DirectoryURLConnection.patternGroups[j][0]);
/* 252 */             str4 = localMatcher.group(DirectoryURLConnection.patternGroups[j][1]);
/* 253 */             str5 = localMatcher.group(DirectoryURLConnection.patternGroups[j][2]);
/* 254 */             if (DirectoryURLConnection.patternGroups[j][3] > 0) {
/* 255 */               str5 = new StringBuilder().append(str5).append(" ").append(localMatcher.group(DirectoryURLConnection.patternGroups[j][3])).toString();
/*     */             }
/* 257 */             if (DirectoryURLConnection.patternGroups[j][4] > 0) {
/* 258 */               String str6 = localMatcher.group(DirectoryURLConnection.patternGroups[j][4]);
/* 259 */               bool = str6.startsWith("d");
/*     */             }
/* 261 */             if ("<DIR>".equals(str4)) {
/* 262 */               bool = true;
/* 263 */               str4 = null;
/*     */             }
/*     */           }
/*     */         }
/* 267 */         if (str2 != null) {
/* 268 */           localMatcher = DirectoryURLConnection.linkp.matcher(str2);
/* 269 */           if (localMatcher.find())
/*     */           {
/* 271 */             str2 = localMatcher.group(1);
/* 272 */             str3 = localMatcher.group(2);
/*     */           }
/* 274 */           if (this.bytesOut == null) {
/* 275 */             this.bytesOut = new ByteArrayOutputStream();
/* 276 */             this.out = new PrintStream(this.bytesOut);
/*     */           }
/* 278 */           this.lineCount += 1;
/* 279 */           this.out.print(new StringBuilder().append("<TR class=\"").append(this.lineCount % 2 == 0 ? "even" : "odd").append("\"><TD class=\"file\"><a href=\"").toString());
/*     */           try {
/* 281 */             this.out.print(new StringBuilder().append(DirectoryURLConnection.this.dirUrl).append(URLEncoder.encode(str2, "UTF-8")).toString());
/*     */           }
/*     */           catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/*     */           }
/* 285 */           if (bool) {
/* 286 */             this.out.print("/");
/*     */           }
/* 288 */           this.out.print("\">");
/* 289 */           this.out.print(str2);
/* 290 */           this.out.print("</a>");
/* 291 */           if (str3 != null)
/* 292 */             this.out.print(new StringBuilder().append(" &rarr; ").append(str3).append("</TD><TD class=\"link\">&lt;Link&gt;</TD>").toString());
/* 293 */           else if (bool)
/* 294 */             this.out.print("</TD><TD class=\"dir\">&lt;Directory&gt;</TD>");
/*     */           else {
/* 296 */             this.out.print(new StringBuilder().append("</TD><TD class=\"fsize\">").append(str4).append("</TD>").toString());
/*     */           }
/* 298 */           this.out.print(new StringBuilder().append("<TD class=\"date\">").append(str5).append("</TD></TR>").toString());
/*     */         }
/*     */       }
/* 301 */       if (this.bytesOut != null) {
/* 302 */         this.out.close();
/* 303 */         this.bytesIn = new ByteArrayInputStream(this.bytesOut.toByteArray());
/* 304 */         this.out = null;
/* 305 */         this.bytesOut = null;
/*     */       }
/*     */     }
/*     */ 
/*     */     private void endOfList()
/*     */     {
/* 313 */       if (DirectoryURLConnection.this.ftp)
/* 314 */         parseFTP("\n");
/*     */       else {
/* 316 */         parseFile("\n");
/*     */       }
/* 318 */       if (this.bytesOut == null) {
/* 319 */         this.bytesOut = new ByteArrayOutputStream();
/* 320 */         this.out = new PrintStream(this.bytesOut);
/*     */       }
/* 322 */       this.out.print("</TABLE><br><hr></hr></body></html>");
/* 323 */       this.out.close();
/* 324 */       this.bytesIn = new ByteArrayInputStream(this.bytesOut.toByteArray());
/* 325 */       this.out = null;
/* 326 */       this.bytesOut = null;
/*     */     }
/*     */ 
/*     */     public int read(byte[] paramArrayOfByte)
/*     */       throws IOException
/*     */     {
/* 332 */       return read(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */     }
/*     */ 
/*     */     public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */       throws IOException
/*     */     {
/* 338 */       int i = 0;
/*     */ 
/* 340 */       if (!DirectoryURLConnection.this.toHTML) {
/* 341 */         return super.read(paramArrayOfByte, paramInt1, paramInt2);
/*     */       }
/* 343 */       if (this.bytesIn != null) {
/* 344 */         i = this.bytesIn.read(paramArrayOfByte, paramInt1, paramInt2);
/* 345 */         if (i == -1) {
/* 346 */           this.bytesIn.close();
/* 347 */           this.bytesIn = null;
/* 348 */           if (this.endOfStream)
/* 349 */             return -1;
/*     */         }
/*     */         else {
/* 352 */           return i;
/*     */         }
/*     */       }
/* 355 */       if (!this.endOfStream) {
/* 356 */         i = super.read(this.buffer, 0, this.buffer.length);
/* 357 */         if (i == -1) {
/* 358 */           this.endOfStream = true;
/* 359 */           endOfList();
/* 360 */           return read(paramArrayOfByte, paramInt1, paramInt2);
/*     */         }
/* 362 */         if (DirectoryURLConnection.this.ftp)
/* 363 */           parseFTP(new String(this.buffer, 0, i));
/*     */         else {
/* 365 */           parseFile(new String(this.buffer, 0, i));
/*     */         }
/* 367 */         if (this.bytesIn != null) {
/* 368 */           return read(paramArrayOfByte, paramInt1, paramInt2);
/*     */         }
/*     */       }
/*     */ 
/* 372 */       return 0;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.DirectoryURLConnection
 * JD-Core Version:    0.6.2
 */