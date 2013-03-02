/*     */ package com.sun.webpane.sg;
/*     */ 
/*     */ import com.sun.webpane.platform.CursorManager;
/*     */ import com.sun.webpane.platform.Utilities;
/*     */ import com.sun.webpane.platform.graphics.WCImage;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.ResourceBundle;
/*     */ import javafx.scene.Cursor;
/*     */ import javafx.scene.ImageCursor;
/*     */ import javafx.scene.image.Image;
/*     */ 
/*     */ public class CursorManagerImpl extends CursorManager<Cursor>
/*     */ {
/*  21 */   private final Map<String, Cursor> map = new HashMap();
/*     */   private ResourceBundle bundle;
/*     */ 
/*     */   protected Cursor getCustomCursor(WCImage paramWCImage, int paramInt1, int paramInt2)
/*     */   {
/*  25 */     return new ImageCursor(Image.impl_fromPlatformImage(Utilities.getUtilities().toPlatformImage(paramWCImage)), paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   protected Cursor getPredefinedCursor(int paramInt) {
/*  29 */     switch (paramInt) { case 0:
/*     */     default:
/*  31 */       return Cursor.DEFAULT;
/*     */     case 1:
/*  32 */       return Cursor.CROSSHAIR;
/*     */     case 2:
/*  33 */       return Cursor.HAND;
/*     */     case 3:
/*  34 */       return Cursor.MOVE;
/*     */     case 4:
/*  35 */       return Cursor.TEXT;
/*     */     case 5:
/*  36 */       return Cursor.WAIT;
/*     */     case 6:
/*  37 */       return getCustomCursor("help", Cursor.DEFAULT);
/*     */     case 7:
/*  38 */       return Cursor.E_RESIZE;
/*     */     case 8:
/*  39 */       return Cursor.N_RESIZE;
/*     */     case 9:
/*  40 */       return Cursor.NE_RESIZE;
/*     */     case 10:
/*  41 */       return Cursor.NW_RESIZE;
/*     */     case 11:
/*  42 */       return Cursor.S_RESIZE;
/*     */     case 12:
/*  43 */       return Cursor.SE_RESIZE;
/*     */     case 13:
/*  44 */       return Cursor.SW_RESIZE;
/*     */     case 14:
/*  45 */       return Cursor.W_RESIZE;
/*     */     case 15:
/*  46 */       return Cursor.V_RESIZE;
/*     */     case 16:
/*  47 */       return Cursor.H_RESIZE;
/*     */     case 17:
/*  48 */       return getCustomCursor("resize.nesw", Cursor.DEFAULT);
/*     */     case 18:
/*  49 */       return getCustomCursor("resize.nwse", Cursor.DEFAULT);
/*     */     case 19:
/*  50 */       return getCustomCursor("resize.column", Cursor.H_RESIZE);
/*     */     case 20:
/*  51 */       return getCustomCursor("resize.row", Cursor.V_RESIZE);
/*     */     case 21:
/*  52 */       return getCustomCursor("panning.middle", Cursor.DEFAULT);
/*     */     case 22:
/*  53 */       return getCustomCursor("panning.east", Cursor.DEFAULT);
/*     */     case 23:
/*  54 */       return getCustomCursor("panning.north", Cursor.DEFAULT);
/*     */     case 24:
/*  55 */       return getCustomCursor("panning.ne", Cursor.DEFAULT);
/*     */     case 25:
/*  56 */       return getCustomCursor("panning.nw", Cursor.DEFAULT);
/*     */     case 26:
/*  57 */       return getCustomCursor("panning.south", Cursor.DEFAULT);
/*     */     case 27:
/*  58 */       return getCustomCursor("panning.se", Cursor.DEFAULT);
/*     */     case 28:
/*  59 */       return getCustomCursor("panning.sw", Cursor.DEFAULT);
/*     */     case 29:
/*  60 */       return getCustomCursor("panning.west", Cursor.DEFAULT);
/*     */     case 30:
/*  61 */       return getCustomCursor("vertical.text", Cursor.DEFAULT);
/*     */     case 31:
/*  62 */       return getCustomCursor("cell", Cursor.DEFAULT);
/*     */     case 32:
/*  63 */       return getCustomCursor("context.menu", Cursor.DEFAULT);
/*     */     case 33:
/*  64 */       return getCustomCursor("no.drop", Cursor.DEFAULT);
/*     */     case 34:
/*  65 */       return getCustomCursor("not.allowed", Cursor.DEFAULT);
/*     */     case 35:
/*  66 */       return getCustomCursor("progress", Cursor.WAIT);
/*     */     case 36:
/*  67 */       return getCustomCursor("alias", Cursor.DEFAULT);
/*     */     case 37:
/*  68 */       return getCustomCursor("zoom.in", Cursor.DEFAULT);
/*     */     case 38:
/*  69 */       return getCustomCursor("zoom.out", Cursor.DEFAULT);
/*     */     case 39:
/*  70 */       return getCustomCursor("copy", Cursor.DEFAULT);
/*     */     case 40:
/*  71 */       return Cursor.NONE;
/*     */     case 41:
/*  72 */       return getCustomCursor("grab", Cursor.OPEN_HAND);
/*  73 */     case 42: } return getCustomCursor("grabbing", Cursor.CLOSED_HAND);
/*     */   }
/*     */ 
/*     */   private Cursor getCustomCursor(String paramString, Cursor paramCursor)
/*     */   {
/*  78 */     Object localObject = (Cursor)this.map.get(paramString);
/*  79 */     if (localObject == null) {
/*     */       try {
/*  81 */         if (this.bundle == null) {
/*  82 */           this.bundle = ResourceBundle.getBundle("com.sun.webpane.sg.Cursors", Locale.getDefault());
/*     */         }
/*  84 */         if (this.bundle != null) {
/*  85 */           String str = this.bundle.getString(paramString + ".file");
/*  86 */           if (str == null) {
/*  87 */             str = paramString + ".cur";
/*     */           }
/*  89 */           Image localImage = null;
/*  90 */           if (localImage == null) {
/*  91 */             throw new IllegalStateException("could not read image file for cursor: " + paramString);
/*     */           }
/*  93 */           str = this.bundle.getString(paramString + ".hotspotX");
/*  94 */           int i = str == null ? 0 : Integer.parseInt(str);
/*     */ 
/*  96 */           str = this.bundle.getString(paramString + ".hotspotY");
/*  97 */           int j = str == null ? 0 : Integer.parseInt(str);
/*     */ 
/*  99 */           localObject = new ImageCursor(localImage, i, j);
/*     */         }
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/* 104 */       if (localObject == null) {
/* 105 */         localObject = paramCursor;
/*     */       }
/* 107 */       this.map.put(paramString, localObject);
/*     */     }
/* 109 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.CursorManagerImpl
 * JD-Core Version:    0.6.2
 */