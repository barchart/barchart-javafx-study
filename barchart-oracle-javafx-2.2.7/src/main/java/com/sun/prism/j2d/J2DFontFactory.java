/*     */ package com.sun.prism.j2d;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.font.FontFactory;
/*     */ import com.sun.javafx.font.FontResource;
/*     */ import com.sun.javafx.font.PGFont;
/*     */ import java.awt.Font;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ public final class J2DFontFactory
/*     */   implements FontFactory
/*     */ {
/*     */   FontFactory prismFontFactory;
/* 125 */   private static boolean compositeFontMethodsInitialized = false;
/* 126 */   private static Method getCompositeFontUIResource = null;
/*     */ 
/*     */   J2DFontFactory(FontFactory paramFontFactory)
/*     */   {
/*  34 */     this.prismFontFactory = paramFontFactory;
/*     */   }
/*     */ 
/*     */   public PGFont createFont(String paramString, float paramFloat) {
/*  38 */     return this.prismFontFactory.createFont(paramString, paramFloat);
/*     */   }
/*     */ 
/*     */   public PGFont createFont(String paramString, boolean paramBoolean1, boolean paramBoolean2, float paramFloat)
/*     */   {
/*  43 */     return this.prismFontFactory.createFont(paramString, paramBoolean1, paramBoolean2, paramFloat);
/*     */   }
/*     */ 
/*     */   public synchronized PGFont deriveFont(PGFont paramPGFont, boolean paramBoolean1, boolean paramBoolean2, float paramFloat)
/*     */   {
/*  48 */     return this.prismFontFactory.deriveFont(paramPGFont, paramBoolean1, paramBoolean2, paramFloat);
/*     */   }
/*     */ 
/*     */   public String[] getFontFamilyNames() {
/*  52 */     return this.prismFontFactory.getFontFamilyNames();
/*     */   }
/*     */ 
/*     */   public String[] getFontFullNames() {
/*  56 */     return this.prismFontFactory.getFontFullNames();
/*     */   }
/*     */ 
/*     */   public String[] getFontFullNames(String paramString) {
/*  60 */     return this.prismFontFactory.getFontFullNames(paramString);
/*     */   }
/*     */ 
/*     */   public PGFont loadEmbeddedFont(String paramString, InputStream paramInputStream, float paramFloat, boolean paramBoolean)
/*     */   {
/*  72 */     PGFont localPGFont = this.prismFontFactory.loadEmbeddedFont(paramString, paramInputStream, paramFloat, paramBoolean);
/*     */ 
/*  75 */     if (localPGFont == null) return null;
/*  76 */     final FontResource localFontResource = localPGFont.getFontResource();
/*  77 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Object run() {
/*  79 */         FileInputStream localFileInputStream = null;
/*     */         try {
/*  81 */           File localFile = new File(localFontResource.getFileName());
/*  82 */           localFileInputStream = new FileInputStream(localFile);
/*  83 */           Font localFont = Font.createFont(0, localFileInputStream);
/*  84 */           localFontResource.setPeer(localFont);
/*     */         } catch (Exception localException2) {
/*  86 */           localException2.printStackTrace();
/*     */         } finally {
/*  88 */           if (localFileInputStream != null)
/*     */             try {
/*  90 */               localFileInputStream.close();
/*     */             }
/*     */             catch (Exception localException4)
/*     */             {
/*     */             }
/*     */         }
/*  96 */         return null;
/*     */       }
/*     */     });
/*  99 */     return localPGFont;
/*     */   }
/*     */ 
/*     */   public PGFont loadEmbeddedFont(String paramString1, String paramString2, float paramFloat, boolean paramBoolean)
/*     */   {
/* 105 */     PGFont localPGFont = this.prismFontFactory.loadEmbeddedFont(paramString1, paramString2, paramFloat, paramBoolean);
/*     */ 
/* 108 */     if (localPGFont == null) return null;
/* 109 */     final FontResource localFontResource = localPGFont.getFontResource();
/* 110 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Object run() {
/*     */         try {
/* 113 */           File localFile = new File(localFontResource.getFileName());
/* 114 */           Font localFont = Font.createFont(0, localFile);
/* 115 */           localFontResource.setPeer(localFont);
/*     */         } catch (Exception localException) {
/* 117 */           localException.printStackTrace();
/*     */         }
/* 119 */         return null;
/*     */       }
/*     */     });
/* 122 */     return localPGFont;
/*     */   }
/*     */ 
/*     */   static Font getCompositeFont(Font paramFont)
/*     */   {
/* 135 */     if (PlatformUtil.isMac()) {
/* 136 */       return paramFont;
/*     */     }
/* 138 */     synchronized (J2DFontFactory.class) {
/* 139 */       if (!compositeFontMethodsInitialized) {
/* 140 */         AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Void run() {
/* 143 */             J2DFontFactory.access$002(true);
/*     */             Class localClass;
/*     */             try {
/* 147 */               localClass = Class.forName("sun.font.FontUtilities", true, null);
/*     */             }
/*     */             catch (ClassNotFoundException localClassNotFoundException1)
/*     */             {
/*     */               try {
/* 152 */                 localClass = Class.forName("sun.font.FontManager", true, null);
/*     */               }
/*     */               catch (ClassNotFoundException localClassNotFoundException2) {
/* 155 */                 return null;
/*     */               }
/*     */             }
/*     */             try
/*     */             {
/* 160 */               J2DFontFactory.access$102(localClass.getMethod("getCompositeFontUIResource", new Class[] { Font.class }));
/*     */             }
/*     */             catch (NoSuchMethodException localNoSuchMethodException)
/*     */             {
/*     */             }
/*     */ 
/* 166 */             return null;
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */ 
/* 172 */     if (getCompositeFontUIResource != null)
/*     */       try {
/* 174 */         return (Font)getCompositeFontUIResource.invoke(null, new Object[] { paramFont });
/*     */       }
/*     */       catch (IllegalAccessException localIllegalAccessException)
/*     */       {
/*     */       }
/*     */       catch (InvocationTargetException localInvocationTargetException) {
/*     */       }
/* 181 */     return paramFont;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.J2DFontFactory
 * JD-Core Version:    0.6.2
 */