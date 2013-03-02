/*     */ package com.sun.javafx.runtime;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ public class NativeLibLoader
/*     */ {
/*  27 */   private static final HashSet<String> loaded = new HashSet();
/*     */ 
/*  36 */   private static boolean verbose = false;
/*  37 */   private static File libDir = null;
/*  38 */   private static String libPrefix = "";
/*  39 */   private static String libSuffix = "";
/*  40 */   private static String libSuffixAlt = null;
/*     */ 
/*     */   public static synchronized void loadLibrary(String paramString)
/*     */   {
/*  30 */     if (!loaded.contains(paramString)) {
/*  31 */       loadLibraryInternal(paramString);
/*  32 */       loaded.add(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String[] initializePath(String paramString)
/*     */   {
/*  52 */     String str1 = System.getProperty(paramString, "");
/*  53 */     String str2 = File.pathSeparator;
/*  54 */     int i = str1.length();
/*     */ 
/*  57 */     int j = str1.indexOf(str2);
/*  58 */     int m = 0;
/*  59 */     while (j >= 0) {
/*  60 */       m++;
/*  61 */       j = str1.indexOf(str2, j + 1);
/*     */     }
/*     */ 
/*  65 */     String[] arrayOfString = new String[m + 1];
/*     */ 
/*  68 */     m = j = 0;
/*  69 */     int k = str1.indexOf(str2);
/*  70 */     while (k >= 0) {
/*  71 */       if (k - j > 0)
/*  72 */         arrayOfString[(m++)] = str1.substring(j, k);
/*  73 */       else if (k - j == 0) {
/*  74 */         arrayOfString[(m++)] = ".";
/*     */       }
/*  76 */       j = k + 1;
/*  77 */       k = str1.indexOf(str2, j);
/*     */     }
/*  79 */     arrayOfString[m] = str1.substring(j, i);
/*  80 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   private static void loadLibraryInternal(String paramString)
/*     */   {
/*     */     try
/*     */     {
/*  88 */       loadLibraryFullPath(paramString);
/*     */     }
/*     */     catch (UnsatisfiedLinkError localUnsatisfiedLinkError1)
/*     */     {
/* 100 */       String[] arrayOfString = initializePath("java.library.path");
/* 101 */       for (int i = 0; i < arrayOfString.length; i++)
/*     */         try {
/* 103 */           String str1 = arrayOfString[i];
/* 104 */           if (!str1.endsWith(File.separator)) str1 = str1 + File.separator;
/* 105 */           String str2 = System.mapLibraryName(paramString);
/* 106 */           File localFile = new File(str1 + str2);
/* 107 */           System.load(localFile.getAbsolutePath());
/* 108 */           if (verbose) {
/* 109 */             System.err.println("WARNING: " + localUnsatisfiedLinkError1.toString());
/* 110 */             System.err.println("    using System.loadLibrary(" + paramString + ") as a fallback");
/*     */           }
/*     */ 
/* 113 */           return;
/*     */         }
/*     */         catch (UnsatisfiedLinkError localUnsatisfiedLinkError3)
/*     */         {
/*     */         }
/*     */       try {
/* 119 */         System.loadLibrary(paramString);
/* 120 */         if (verbose) {
/* 121 */           System.err.println("WARNING: " + localUnsatisfiedLinkError1.toString());
/* 122 */           System.err.println("    using System.loadLibrary(" + paramString + ") as a fallback");
/*     */         }
/*     */       }
/*     */       catch (UnsatisfiedLinkError localUnsatisfiedLinkError2)
/*     */       {
/* 127 */         throw localUnsatisfiedLinkError1;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void loadLibraryFullPath(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 138 */       if (libDir == null)
/*     */       {
/* 141 */         localObject1 = "NativeLibLoader.class";
/* 142 */         localObject2 = NativeLibLoader.class;
/* 143 */         String str1 = ((Class)localObject2).getResource((String)localObject1).toString();
/* 144 */         if ((!str1.startsWith("jar:file:")) || (str1.indexOf(33) == -1)) {
/* 145 */           throw new UnsatisfiedLinkError("Invalid URL for class: " + str1);
/*     */         }
/*     */ 
/* 148 */         String str2 = str1.substring(4, str1.lastIndexOf(33));
/*     */ 
/* 150 */         int i = Math.max(str2.lastIndexOf(47), str2.lastIndexOf(92));
/*     */ 
/* 153 */         String str3 = System.getProperty("os.name");
/* 154 */         boolean bool = System.getProperty("sun.arch.data.model", "").startsWith("64");
/*     */ 
/* 156 */         String str4 = null;
/* 157 */         if (str3.startsWith("Windows"))
/* 158 */           str4 = "../bin";
/* 159 */         else if (str3.startsWith("Mac"))
/* 160 */           str4 = ".";
/* 161 */         else if (str3.startsWith("Linux")) {
/* 162 */           if (bool)
/* 163 */             str4 = "amd64";
/*     */           else {
/* 165 */             str4 = "i386";
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 170 */         String str5 = str2.substring(0, i) + "/" + str4;
/*     */ 
/* 172 */         libDir = new File(new URI(str5).getPath());
/*     */ 
/* 175 */         if (str3.startsWith("Windows"))
/*     */         {
/* 177 */           if (bool) {
/* 178 */             File localFile = new File(libDir, "64bit");
/* 179 */             if (localFile.isDirectory()) {
/* 180 */               libDir = localFile;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 185 */           libPrefix = "";
/* 186 */           libSuffix = ".dll";
/* 187 */         } else if (str3.startsWith("Mac")) {
/* 188 */           libPrefix = "lib";
/* 189 */           libSuffix = ".dylib";
/* 190 */           libSuffixAlt = ".jnilib";
/* 191 */         } else if (str3.startsWith("Linux")) {
/* 192 */           libPrefix = "lib";
/* 193 */           libSuffix = ".so";
/*     */         }
/*     */       }
/*     */ 
/* 197 */       Object localObject1 = new File(libDir, libPrefix + paramString + libSuffix);
/* 198 */       Object localObject2 = ((File)localObject1).getCanonicalPath();
/*     */       try {
/* 200 */         System.load((String)localObject2);
/*     */       } catch (UnsatisfiedLinkError localUnsatisfiedLinkError1) {
/* 202 */         if (libSuffixAlt != null)
/*     */           try {
/* 204 */             localObject1 = new File(libDir, libPrefix + paramString + libSuffixAlt);
/* 205 */             localObject2 = ((File)localObject1).getAbsolutePath();
/* 206 */             System.load((String)localObject2);
/*     */           } catch (UnsatisfiedLinkError localUnsatisfiedLinkError2) {
/* 208 */             throw localUnsatisfiedLinkError1;
/*     */           }
/*     */         else
/* 211 */           throw localUnsatisfiedLinkError1;
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 216 */       throw ((UnsatisfiedLinkError)new UnsatisfiedLinkError().initCause(localException));
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  43 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Object run() {
/*  45 */         NativeLibLoader.access$002(Boolean.getBoolean("javafx.verbose"));
/*  46 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.runtime.NativeLibLoader
 * JD-Core Version:    0.6.2
 */