/*     */ package javafx.scene.text;
/*     */ 
/*     */ import com.sun.javafx.tk.FontLoader;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.io.File;
/*     */ import java.io.FilePermission;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.Permission;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class Font
/*     */ {
/*     */   private static final String DEFAULT_FAMILY = "System";
/*     */   private static final String DEFAULT_FULLNAME = "System Regular";
/*  66 */   private static float defaultSystemFontSize = -1.0F;
/*     */   private static Font DEFAULT;
/*     */   private String name;
/*     */   private String family;
/*     */   private String style;
/*     */   private double size;
/* 251 */   private int hash = 0;
/*     */   private Object nativeFont;
/*     */ 
/*     */   private static float getDefaultSystemFontSize()
/*     */   {
/*  68 */     if (defaultSystemFontSize == -1.0F) {
/*  69 */       defaultSystemFontSize = Toolkit.getToolkit().getFontLoader().getSystemFontSize();
/*     */     }
/*     */ 
/*  72 */     return defaultSystemFontSize;
/*     */   }
/*     */ 
/*     */   public static synchronized Font getDefault()
/*     */   {
/*  84 */     if (DEFAULT == null) {
/*  85 */       DEFAULT = new Font("System Regular", getDefaultSystemFontSize());
/*     */     }
/*  87 */     return DEFAULT;
/*     */   }
/*     */ 
/*     */   public static List<String> getFamilies()
/*     */   {
/*  97 */     return Toolkit.getToolkit().getFontLoader().getFamilies();
/*     */   }
/*     */ 
/*     */   public static List<String> getFontNames()
/*     */   {
/* 107 */     return Toolkit.getToolkit().getFontLoader().getFontNames();
/*     */   }
/*     */ 
/*     */   public static List<String> getFontNames(String paramString)
/*     */   {
/* 118 */     return Toolkit.getToolkit().getFontLoader().getFontNames(paramString);
/*     */   }
/*     */ 
/*     */   public static Font font(String paramString, FontWeight paramFontWeight, FontPosture paramFontPosture, double paramDouble)
/*     */   {
/* 140 */     String str = (paramString == null) || ("".equals(paramString)) ? "System" : paramString;
/*     */ 
/* 142 */     double d = paramDouble < 0.0D ? getDefaultSystemFontSize() : paramDouble;
/* 143 */     return Toolkit.getToolkit().getFontLoader().font(str, paramFontWeight, paramFontPosture, (float)d);
/*     */   }
/*     */ 
/*     */   public static Font font(String paramString, FontWeight paramFontWeight, double paramDouble)
/*     */   {
/* 160 */     return font(paramString, paramFontWeight, null, paramDouble);
/*     */   }
/*     */ 
/*     */   public static Font font(String paramString, FontPosture paramFontPosture, double paramDouble)
/*     */   {
/* 176 */     return font(paramString, null, paramFontPosture, paramDouble);
/*     */   }
/*     */ 
/*     */   public static Font font(String paramString, double paramDouble)
/*     */   {
/* 191 */     return font(paramString, null, null, paramDouble);
/*     */   }
/*     */ 
/*     */   public final String getName()
/*     */   {
/* 220 */     return this.name;
/*     */   }
/*     */ 
/*     */   public final String getFamily()
/*     */   {
/* 227 */     return this.family;
/*     */   }
/*     */ 
/*     */   public final String getStyle()
/*     */   {
/* 234 */     return this.style;
/*     */   }
/*     */ 
/*     */   public final double getSize()
/*     */   {
/* 244 */     return this.size;
/*     */   }
/*     */ 
/*     */   public Font(double paramDouble)
/*     */   {
/* 261 */     this(null, paramDouble);
/*     */   }
/*     */ 
/*     */   public Font(String paramString, double paramDouble)
/*     */   {
/* 271 */     this.name = paramString;
/* 272 */     this.size = paramDouble;
/*     */ 
/* 274 */     if ((paramString == null) || ("".equals(paramString))) this.name = "System Regular";
/* 275 */     if (paramDouble < 0.0D) this.size = getDefaultSystemFontSize();
/*     */ 
/* 282 */     Toolkit.getToolkit().getFontLoader().loadFont(this);
/*     */   }
/*     */ 
/*     */   private Font(Object paramObject, String paramString1, String paramString2, String paramString3, double paramDouble)
/*     */   {
/* 295 */     this.nativeFont = paramObject;
/* 296 */     this.family = paramString1;
/* 297 */     this.name = paramString2;
/* 298 */     this.style = paramString3;
/* 299 */     this.size = paramDouble;
/*     */   }
/*     */ 
/*     */   public static Font loadFont(String paramString, double paramDouble)
/*     */   {
/* 333 */     URL localURL = null;
/*     */     try {
/* 335 */       localURL = new URL(paramString);
/*     */     } catch (Exception localException1) {
/* 337 */       return null;
/*     */     }
/* 339 */     if (paramDouble <= 0.0D) {
/* 340 */       paramDouble = 12.0D;
/*     */     }
/*     */ 
/* 344 */     if (localURL.getProtocol().equals("file")) {
/* 345 */       localObject1 = localURL.getFile();
/*     */ 
/* 349 */       localObject1 = new File((String)localObject1).getPath();
/*     */       try {
/* 351 */         SecurityManager localSecurityManager = System.getSecurityManager();
/* 352 */         if (localSecurityManager != null) {
/* 353 */           localObject2 = new FilePermission((String)localObject1, "read");
/*     */ 
/* 355 */           localSecurityManager.checkPermission((Permission)localObject2);
/*     */         }
/*     */       } catch (Exception localException2) {
/* 358 */         return null;
/*     */       }
/* 360 */       return Toolkit.getToolkit().getFontLoader().loadFont((String)localObject1, paramDouble);
/*     */     }
/* 362 */     Object localObject1 = null;
/* 363 */     URLConnection localURLConnection = null;
/* 364 */     Object localObject2 = null;
/*     */     try {
/* 366 */       localURLConnection = localURL.openConnection();
/* 367 */       localObject2 = localURLConnection.getInputStream();
/* 368 */       localObject1 = Toolkit.getToolkit().getFontLoader().loadFont((InputStream)localObject2, paramDouble);
/*     */     } catch (Exception localException4) {
/* 370 */       return null;
/*     */     } finally {
/*     */       try {
/* 373 */         if (localObject2 != null)
/* 374 */           ((InputStream)localObject2).close();
/*     */       }
/*     */       catch (Exception localException6) {
/*     */       }
/*     */     }
/* 379 */     return localObject1;
/*     */   }
/*     */ 
/*     */   public static Font loadFont(InputStream paramInputStream, double paramDouble)
/*     */   {
/* 409 */     if (paramDouble <= 0.0D) {
/* 410 */       paramDouble = 12.0D;
/*     */     }
/* 412 */     return Toolkit.getToolkit().getFontLoader().loadFont(paramInputStream, paramDouble);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 421 */     StringBuilder localStringBuilder = new StringBuilder("Font[name=");
/* 422 */     localStringBuilder = localStringBuilder.append(this.name);
/* 423 */     localStringBuilder = localStringBuilder.append(", family=").append(this.family);
/* 424 */     localStringBuilder = localStringBuilder.append(", style=").append(this.style);
/* 425 */     localStringBuilder = localStringBuilder.append(", size=").append(this.size);
/* 426 */     localStringBuilder = localStringBuilder.append("]");
/* 427 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 436 */     if (paramObject == this) return true;
/* 437 */     if ((paramObject instanceof Font)) {
/* 438 */       Font localFont = (Font)paramObject;
/* 439 */       return (this.name == null ? localFont.name == null : this.name.equals(localFont.name)) && (this.size == localFont.size);
/*     */     }
/*     */ 
/* 442 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 450 */     if (this.hash == 0) {
/* 451 */       long l = 17L;
/* 452 */       l = 37L * l + this.name.hashCode();
/* 453 */       l = 37L * l + Double.doubleToLongBits(this.size);
/* 454 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/* 456 */     return this.hash;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Object impl_getNativeFont()
/*     */   {
/* 466 */     return this.nativeFont;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_setNativeFont(Object paramObject, String paramString1, String paramString2, String paramString3)
/*     */   {
/* 474 */     this.nativeFont = paramObject;
/* 475 */     this.name = paramString1;
/* 476 */     this.family = paramString2;
/* 477 */     this.style = paramString3;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static Font impl_NativeFont(Object paramObject, String paramString1, String paramString2, String paramString3, double paramDouble)
/*     */   {
/* 487 */     Font localFont = new Font(paramObject, paramString2, paramString1, paramString3, paramDouble);
/* 488 */     return localFont;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.text.Font
 * JD-Core Version:    0.6.2
 */