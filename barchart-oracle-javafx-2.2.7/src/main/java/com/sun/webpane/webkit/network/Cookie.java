/*     */ package com.sun.webpane.webkit.network;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.text.ParseException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ final class Cookie
/*     */ {
/*  18 */   private static final Logger logger = Logger.getLogger(Cookie.class.getName());
/*     */ 
/*  20 */   private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
/*     */   private final String name;
/*     */   private final String value;
/*     */   private final long expiryTime;
/*     */   private String domain;
/*     */   private String path;
/*     */   private ExtendedTime creationTime;
/*     */   private long lastAccessTime;
/*     */   private final boolean persistent;
/*     */   private boolean hostOnly;
/*     */   private final boolean secureOnly;
/*     */   private final boolean httpOnly;
/*     */ 
/*     */   private Cookie(String paramString1, String paramString2, long paramLong1, String paramString3, String paramString4, ExtendedTime paramExtendedTime, long paramLong2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*     */   {
/*  45 */     this.name = paramString1;
/*  46 */     this.value = paramString2;
/*  47 */     this.expiryTime = paramLong1;
/*  48 */     this.domain = paramString3;
/*  49 */     this.path = paramString4;
/*  50 */     this.creationTime = paramExtendedTime;
/*  51 */     this.lastAccessTime = paramLong2;
/*  52 */     this.persistent = paramBoolean1;
/*  53 */     this.hostOnly = paramBoolean2;
/*  54 */     this.secureOnly = paramBoolean3;
/*  55 */     this.httpOnly = paramBoolean4;
/*     */   }
/*     */ 
/*     */   static Cookie parse(String paramString, ExtendedTime paramExtendedTime)
/*     */   {
/*  64 */     logger.log(Level.FINEST, "setCookieString: [{0}]", paramString);
/*     */ 
/*  66 */     String[] arrayOfString1 = paramString.split(";", -1);
/*     */ 
/*  68 */     String[] arrayOfString2 = arrayOfString1[0].split("=", 2);
/*  69 */     if (arrayOfString2.length != 2) {
/*  70 */       logger.log(Level.FINEST, "Name-value pair string lacks '=', ignoring cookie");
/*     */ 
/*  72 */       return null;
/*     */     }
/*  74 */     String str1 = arrayOfString2[0].trim();
/*  75 */     String str2 = arrayOfString2[1].trim();
/*  76 */     if (str1.length() == 0) {
/*  77 */       logger.log(Level.FINEST, "Name string is empty, ignoring cookie");
/*  78 */       return null;
/*     */     }
/*     */ 
/*  81 */     Long localLong1 = null;
/*  82 */     Long localLong2 = null;
/*  83 */     String str3 = null;
/*  84 */     String str4 = null;
/*  85 */     boolean bool1 = false;
/*  86 */     boolean bool2 = false;
/*     */ 
/*  88 */     for (int i = 1; i < arrayOfString1.length; i++) {
/*  89 */       String[] arrayOfString3 = arrayOfString1[i].split("=", 2);
/*  90 */       String str5 = arrayOfString3[0].trim();
/*  91 */       localObject = (arrayOfString3.length > 1 ? arrayOfString3[1] : "").trim();
/*     */       try
/*     */       {
/*  94 */         if ("Expires".equalsIgnoreCase(str5))
/*  95 */           localLong1 = Long.valueOf(parseExpires((String)localObject));
/*  96 */         else if ("Max-Age".equalsIgnoreCase(str5))
/*  97 */           localLong2 = Long.valueOf(parseMaxAge((String)localObject, paramExtendedTime.baseTime()));
/*  98 */         else if ("Domain".equalsIgnoreCase(str5))
/*  99 */           str3 = parseDomain((String)localObject);
/* 100 */         else if ("Path".equalsIgnoreCase(str5))
/* 101 */           str4 = parsePath((String)localObject);
/* 102 */         else if ("Secure".equalsIgnoreCase(str5))
/* 103 */           bool1 = true;
/* 104 */         else if ("HttpOnly".equalsIgnoreCase(str5))
/* 105 */           bool2 = true;
/*     */         else
/* 107 */           logger.log(Level.FINEST, "Unknown attribute: [{0}], ignoring", str5);
/*     */       }
/*     */       catch (ParseException localParseException)
/*     */       {
/* 111 */         logger.log(Level.FINEST, "{0}, ignoring", localParseException.getMessage());
/*     */       }
/*     */     }
/*     */     boolean bool3;
/*     */     long l;
/* 117 */     if (localLong2 != null) {
/* 118 */       bool3 = true;
/* 119 */       l = localLong2.longValue();
/* 120 */     } else if (localLong1 != null) {
/* 121 */       bool3 = true;
/* 122 */       l = localLong1.longValue();
/*     */     } else {
/* 124 */       bool3 = false;
/* 125 */       l = 9223372036854775807L;
/*     */     }
/*     */ 
/* 128 */     if (str3 == null) {
/* 129 */       str3 = "";
/*     */     }
/*     */ 
/* 132 */     Object localObject = new Cookie(str1, str2, l, str3, str4, paramExtendedTime, paramExtendedTime.baseTime(), bool3, false, bool1, bool2);
/*     */ 
/* 135 */     logger.log(Level.FINEST, "result: {0}", localObject);
/* 136 */     return localObject;
/*     */   }
/*     */ 
/*     */   private static long parseExpires(String paramString)
/*     */     throws ParseException
/*     */   {
/*     */     try
/*     */     {
/* 146 */       return Math.max(DateParser.parse(paramString), 0L); } catch (ParseException localParseException) {
/*     */     }
/* 148 */     throw new ParseException("Error parsing Expires attribute", 0);
/*     */   }
/*     */ 
/*     */   private static long parseMaxAge(String paramString, long paramLong)
/*     */     throws ParseException
/*     */   {
/*     */     try
/*     */     {
/* 159 */       long l = Long.parseLong(paramString);
/* 160 */       if (l <= 0L) {
/* 161 */         return 0L;
/*     */       }
/* 163 */       return l > (9223372036854775807L - paramLong) / 1000L ? 9223372036854775807L : paramLong + l * 1000L;
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {
/*     */     }
/* 167 */     throw new ParseException("Error parsing Max-Age attribute", 0);
/*     */   }
/*     */ 
/*     */   private static String parseDomain(String paramString)
/*     */     throws ParseException
/*     */   {
/* 177 */     if (paramString.length() == 0) {
/* 178 */       throw new ParseException("Domain attribute is empty", 0);
/*     */     }
/* 180 */     if (paramString.startsWith(".")) {
/* 181 */       paramString = paramString.substring(1);
/*     */     }
/* 183 */     return paramString.toLowerCase();
/*     */   }
/*     */ 
/*     */   private static String parsePath(String paramString)
/*     */   {
/* 190 */     return paramString.startsWith("/") ? paramString : null;
/*     */   }
/*     */ 
/*     */   String getName()
/*     */   {
/* 198 */     return this.name;
/*     */   }
/*     */ 
/*     */   String getValue()
/*     */   {
/* 205 */     return this.value;
/*     */   }
/*     */ 
/*     */   long getExpiryTime()
/*     */   {
/* 212 */     return this.expiryTime;
/*     */   }
/*     */ 
/*     */   String getDomain()
/*     */   {
/* 219 */     return this.domain;
/*     */   }
/*     */ 
/*     */   void setDomain(String paramString)
/*     */   {
/* 226 */     this.domain = paramString;
/*     */   }
/*     */ 
/*     */   String getPath()
/*     */   {
/* 233 */     return this.path;
/*     */   }
/*     */ 
/*     */   void setPath(String paramString)
/*     */   {
/* 240 */     this.path = paramString;
/*     */   }
/*     */ 
/*     */   ExtendedTime getCreationTime()
/*     */   {
/* 247 */     return this.creationTime;
/*     */   }
/*     */ 
/*     */   void setCreationTime(ExtendedTime paramExtendedTime)
/*     */   {
/* 254 */     this.creationTime = paramExtendedTime;
/*     */   }
/*     */ 
/*     */   long getLastAccessTime()
/*     */   {
/* 261 */     return this.lastAccessTime;
/*     */   }
/*     */ 
/*     */   void setLastAccessTime(long paramLong)
/*     */   {
/* 268 */     this.lastAccessTime = paramLong;
/*     */   }
/*     */ 
/*     */   boolean getPersistent()
/*     */   {
/* 275 */     return this.persistent;
/*     */   }
/*     */ 
/*     */   boolean getHostOnly()
/*     */   {
/* 282 */     return this.hostOnly;
/*     */   }
/*     */ 
/*     */   void setHostOnly(boolean paramBoolean)
/*     */   {
/* 289 */     this.hostOnly = paramBoolean;
/*     */   }
/*     */ 
/*     */   boolean getSecureOnly()
/*     */   {
/* 296 */     return this.secureOnly;
/*     */   }
/*     */ 
/*     */   boolean getHttpOnly()
/*     */   {
/* 303 */     return this.httpOnly;
/*     */   }
/*     */ 
/*     */   boolean hasExpired()
/*     */   {
/* 310 */     return System.currentTimeMillis() > this.expiryTime;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 319 */     if ((paramObject instanceof Cookie)) {
/* 320 */       Cookie localCookie = (Cookie)paramObject;
/* 321 */       return (equal(this.name, localCookie.name)) && (equal(this.domain, localCookie.domain)) && (equal(this.path, localCookie.path));
/*     */     }
/*     */ 
/* 325 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean equal(Object paramObject1, Object paramObject2)
/*     */   {
/* 333 */     return ((paramObject1 == null) && (paramObject2 == null)) || ((paramObject1 != null) && (paramObject1.equals(paramObject2)));
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 342 */     int i = 7;
/* 343 */     i = 53 * i + hashCode(this.name);
/* 344 */     i = 53 * i + hashCode(this.domain);
/* 345 */     i = 53 * i + hashCode(this.path);
/* 346 */     return i;
/*     */   }
/*     */ 
/*     */   private static int hashCode(Object paramObject)
/*     */   {
/* 353 */     return paramObject != null ? paramObject.hashCode() : 0;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 361 */     return "[name=" + this.name + ", value=" + this.value + ", " + "expiryTime=" + this.expiryTime + ", domain=" + this.domain + ", " + "path=" + this.path + ", creationTime=" + this.creationTime + ", " + "lastAccessTime=" + this.lastAccessTime + ", " + "persistent=" + this.persistent + ", hostOnly=" + this.hostOnly + ", " + "secureOnly=" + this.secureOnly + ", httpOnly=" + this.httpOnly + "]";
/*     */   }
/*     */ 
/*     */   static boolean domainMatches(String paramString1, String paramString2)
/*     */   {
/* 373 */     return (paramString1.endsWith(paramString2)) && ((paramString1.length() == paramString2.length()) || ((paramString1.charAt(paramString1.length() - paramString2.length() - 1) == '.') && (!isIpAddress(paramString1))));
/*     */   }
/*     */ 
/*     */   private static boolean isIpAddress(String paramString)
/*     */   {
/* 384 */     Matcher localMatcher = IP_ADDRESS_PATTERN.matcher(paramString);
/* 385 */     if (!localMatcher.matches()) {
/* 386 */       return false;
/*     */     }
/* 388 */     for (int i = 1; i <= localMatcher.groupCount(); i++) {
/* 389 */       if (Integer.parseInt(localMatcher.group(i)) > 255) {
/* 390 */         return false;
/*     */       }
/*     */     }
/* 393 */     return true;
/*     */   }
/*     */ 
/*     */   static String defaultPath(URI paramURI)
/*     */   {
/* 400 */     String str = paramURI.getPath();
/* 401 */     if ((str == null) || (!str.startsWith("/"))) {
/* 402 */       return "/";
/*     */     }
/* 404 */     str = str.substring(0, str.lastIndexOf("/"));
/* 405 */     if (str.length() == 0) {
/* 406 */       return "/";
/*     */     }
/* 408 */     return str;
/*     */   }
/*     */ 
/*     */   static boolean pathMatches(String paramString1, String paramString2)
/*     */   {
/* 415 */     return (paramString1 != null) && (paramString1.startsWith(paramString2)) && ((paramString1.length() == paramString2.length()) || (paramString2.endsWith("/")) || (paramString1.charAt(paramString2.length()) == '/'));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.Cookie
 * JD-Core Version:    0.6.2
 */