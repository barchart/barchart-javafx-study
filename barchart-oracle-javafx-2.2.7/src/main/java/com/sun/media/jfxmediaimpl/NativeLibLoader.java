/*     */ package com.sun.media.jfxmediaimpl;
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
/*     */   public static synchronized void loadLibrary(String libname)
/*     */   {
/*  30 */     if (!loaded.contains(libname)) {
/*  31 */       loadLibraryInternal(libname);
/*  32 */       loaded.add(libname);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String[] initializePath(String propname)
/*     */   {
/*  52 */     String ldpath = System.getProperty(propname, "");
/*  53 */     String ps = File.pathSeparator;
/*  54 */     int ldlen = ldpath.length();
/*     */ 
/*  57 */     int i = ldpath.indexOf(ps);
/*  58 */     int n = 0;
/*  59 */     while (i >= 0) {
/*  60 */       n++;
/*  61 */       i = ldpath.indexOf(ps, i + 1);
/*     */     }
/*     */ 
/*  65 */     String[] paths = new String[n + 1];
/*     */ 
/*  68 */     n = i = 0;
/*  69 */     int j = ldpath.indexOf(ps);
/*  70 */     while (j >= 0) {
/*  71 */       if (j - i > 0)
/*  72 */         paths[(n++)] = ldpath.substring(i, j);
/*  73 */       else if (j - i == 0) {
/*  74 */         paths[(n++)] = ".";
/*     */       }
/*  76 */       i = j + 1;
/*  77 */       j = ldpath.indexOf(ps, i);
/*     */     }
/*  79 */     paths[n] = ldpath.substring(i, ldlen);
/*  80 */     return paths;
/*     */   }
/*     */ 
/*     */   private static void loadLibraryInternal(String libraryName)
/*     */   {
/*     */     try
/*     */     {
/*  88 */       loadLibraryFullPath(libraryName);
/*     */     }
/*     */     catch (UnsatisfiedLinkError ex)
/*     */     {
/* 100 */       String[] libPath = initializePath("java.library.path");
/* 101 */       for (int i = 0; i < libPath.length; i++)
/*     */         try {
/* 103 */           String path = libPath[i];
/* 104 */           if (!path.endsWith(File.separator)) path = path + File.separator;
/* 105 */           String fileName = System.mapLibraryName(libraryName);
/* 106 */           File libFile = new File(path + fileName);
/* 107 */           System.load(libFile.getAbsolutePath());
/* 108 */           if (verbose) {
/* 109 */             System.err.println("WARNING: " + ex.toString());
/* 110 */             System.err.println("    using System.loadLibrary(" + libraryName + ") as a fallback");
/*     */           }
/*     */ 
/* 113 */           return;
/*     */         }
/*     */         catch (UnsatisfiedLinkError ex3)
/*     */         {
/*     */         }
/*     */       try {
/* 119 */         System.loadLibrary(libraryName);
/* 120 */         if (verbose) {
/* 121 */           System.err.println("WARNING: " + ex.toString());
/* 122 */           System.err.println("    using System.loadLibrary(" + libraryName + ") as a fallback");
/*     */         }
/*     */       }
/*     */       catch (UnsatisfiedLinkError ex2)
/*     */       {
/* 127 */         throw ex;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void loadLibraryFullPath(String libraryName)
/*     */   {
/*     */     try
/*     */     {
/* 138 */       if (libDir == null)
/*     */       {
/* 141 */         String theClassFile = "NativeLibLoader.class";
/* 142 */         Class theClass = NativeLibLoader.class;
/* 143 */         String classUrlString = theClass.getResource(theClassFile).toString();
/* 144 */         if ((!classUrlString.startsWith("jar:file:")) || (classUrlString.indexOf('!') == -1)) {
/* 145 */           throw new UnsatisfiedLinkError("Invalid URL for class: " + classUrlString);
/*     */         }
/*     */ 
/* 148 */         String tmpStr = classUrlString.substring(4, classUrlString.lastIndexOf('!'));
/*     */ 
/* 150 */         int lastIndexOfSlash = Math.max(tmpStr.lastIndexOf('/'), tmpStr.lastIndexOf('\\'));
/*     */ 
/* 153 */         String osName = System.getProperty("os.name");
/* 154 */         boolean is64bit = System.getProperty("sun.arch.data.model", "").startsWith("64");
/*     */ 
/* 156 */         String relativeDir = null;
/* 157 */         if (osName.startsWith("Windows"))
/* 158 */           relativeDir = "../bin";
/* 159 */         else if (osName.startsWith("Mac"))
/* 160 */           relativeDir = ".";
/* 161 */         else if (osName.startsWith("Linux")) {
/* 162 */           if (is64bit)
/* 163 */             relativeDir = "amd64";
/*     */           else {
/* 165 */             relativeDir = "i386";
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 170 */         String libDirUrlString = tmpStr.substring(0, lastIndexOfSlash) + "/" + relativeDir;
/*     */ 
/* 172 */         libDir = new File(new URI(libDirUrlString).getPath());
/*     */ 
/* 175 */         if (osName.startsWith("Windows"))
/*     */         {
/* 177 */           if (is64bit) {
/* 178 */             File libDir64 = new File(libDir, "64bit");
/* 179 */             if (libDir64.isDirectory()) {
/* 180 */               libDir = libDir64;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 185 */           libPrefix = "";
/* 186 */           libSuffix = ".dll";
/* 187 */         } else if (osName.startsWith("Mac")) {
/* 188 */           libPrefix = "lib";
/* 189 */           libSuffix = ".dylib";
/* 190 */           libSuffixAlt = ".jnilib";
/* 191 */         } else if (osName.startsWith("Linux")) {
/* 192 */           libPrefix = "lib";
/* 193 */           libSuffix = ".so";
/*     */         }
/*     */       }
/*     */ 
/* 197 */       File libFile = new File(libDir, libPrefix + libraryName + libSuffix);
/* 198 */       String libFileName = libFile.getCanonicalPath();
/*     */       try {
/* 200 */         System.load(libFileName);
/*     */       } catch (UnsatisfiedLinkError ex) {
/* 202 */         if (libSuffixAlt != null)
/*     */           try {
/* 204 */             libFile = new File(libDir, libPrefix + libraryName + libSuffixAlt);
/* 205 */             libFileName = libFile.getAbsolutePath();
/* 206 */             System.load(libFileName);
/*     */           } catch (UnsatisfiedLinkError ex2) {
/* 208 */             throw ex;
/*     */           }
/*     */         else
/* 211 */           throw ex;
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 216 */       throw ((UnsatisfiedLinkError)new UnsatisfiedLinkError().initCause(e));
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
 * Qualified Name:     com.sun.media.jfxmediaimpl.NativeLibLoader
 * JD-Core Version:    0.6.2
 */