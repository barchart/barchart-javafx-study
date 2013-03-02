/*     */ package com.sun.glass.events;
/*     */ 
/*     */ import com.sun.glass.ui.Window;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class NpapiEvent
/*     */ {
/*     */   public static final int NPCocoaEventDrawRect = 1;
/*     */   public static final int NPCocoaEventMouseDown = 2;
/*     */   public static final int NPCocoaEventMouseUp = 3;
/*     */   public static final int NPCocoaEventMouseMoved = 4;
/*     */   public static final int NPCocoaEventMouseEntered = 5;
/*     */   public static final int NPCocoaEventMouseExited = 6;
/*     */   public static final int NPCocoaEventMouseDragged = 7;
/*     */   public static final int NPCocoaEventKeyDown = 8;
/*     */   public static final int NPCocoaEventKeyUp = 9;
/*     */   public static final int NPCocoaEventFlagsChanged = 10;
/*     */   public static final int NPCocoaEventFocusChanged = 11;
/*     */   public static final int NPCocoaEventWindowFocusChanged = 12;
/*     */   public static final int NPCocoaEventScrollWheel = 13;
/*     */   public static final int NPCocoaEventTextInput = 14;
/*     */ 
/*     */   private static native void _dispatchCocoaNpapiDrawEvent(long paramLong1, int paramInt, long paramLong2, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */ 
/*     */   private static native void _dispatchCocoaNpapiMouseEvent(long paramLong, int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, int paramInt3, int paramInt4, double paramDouble3, double paramDouble4, double paramDouble5);
/*     */ 
/*     */   private static native void _dispatchCocoaNpapiKeyEvent(long paramLong, int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2);
/*     */ 
/*     */   private static native void _dispatchCocoaNpapiFocusEvent(long paramLong, int paramInt, boolean paramBoolean);
/*     */ 
/*     */   private static native void _dispatchCocoaNpapiTextInputEvent(long paramLong, int paramInt, String paramString);
/*     */ 
/*     */   private static final boolean getBoolean(Map eventInfo, String key)
/*     */   {
/*  49 */     boolean value = false;
/*     */ 
/*  51 */     if (eventInfo.containsKey(key) == true) {
/*     */       try {
/*  53 */         value = ((Boolean)eventInfo.get(key)).booleanValue();
/*     */       } catch (Exception e) {
/*  55 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/*  59 */     return value;
/*     */   }
/*     */   private static final int getInt(Map eventInfo, String key) {
/*  62 */     int value = 0;
/*     */ 
/*  64 */     if (eventInfo.containsKey(key) == true) {
/*     */       try {
/*  66 */         value = ((Integer)eventInfo.get(key)).intValue();
/*     */       } catch (Exception e) {
/*  68 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/*  72 */     return value;
/*     */   }
/*     */   private static final long getLong(Map eventInfo, String key) {
/*  75 */     long value = 0L;
/*     */ 
/*  77 */     if (eventInfo.containsKey(key) == true) {
/*     */       try {
/*  79 */         value = ((Long)eventInfo.get(key)).longValue();
/*     */       } catch (Exception e) {
/*  81 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/*  85 */     return value;
/*     */   }
/*     */   private static final double getDouble(Map eventInfo, String key) {
/*  88 */     double value = 0.0D;
/*     */ 
/*  90 */     if (eventInfo.containsKey(key) == true) {
/*     */       try {
/*  92 */         value = ((Double)eventInfo.get(key)).doubleValue();
/*     */       } catch (Exception e) {
/*  94 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/*  98 */     return value;
/*     */   }
/*     */   private static final String getString(Map eventInfo, String key) {
/* 101 */     String value = null;
/*     */ 
/* 103 */     if (eventInfo.containsKey(key) == true) {
/*     */       try {
/* 105 */         value = (String)eventInfo.get(key);
/*     */       } catch (Exception e) {
/* 107 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 111 */     return value;
/*     */   }
/*     */   public static void dispatchCocoaNpapiEvent(Window window, Map eventInfo) {
/* 114 */     long windowPtr = window.getNativeWindow();
/*     */ 
/* 116 */     int type = ((Integer)eventInfo.get("type")).intValue();
/* 117 */     switch (type) {
/*     */     case 1:
/* 119 */       long context = getLong(eventInfo, "context");
/* 120 */       double x = getDouble(eventInfo, "x");
/* 121 */       double y = getDouble(eventInfo, "y");
/* 122 */       double width = getDouble(eventInfo, "width");
/* 123 */       double height = getDouble(eventInfo, "height");
/* 124 */       _dispatchCocoaNpapiDrawEvent(windowPtr, type, context, x, y, width, height);
/*     */ 
/* 127 */       break;
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/*     */     case 7:
/*     */     case 13:
/* 135 */       int modifierFlags = getInt(eventInfo, "modifierFlags");
/* 136 */       double pluginX = getDouble(eventInfo, "pluginX");
/* 137 */       double pluginY = getDouble(eventInfo, "pluginY");
/* 138 */       int buttonNumber = getInt(eventInfo, "buttonNumber");
/* 139 */       int clickCount = getInt(eventInfo, "clickCount");
/* 140 */       double deltaX = getDouble(eventInfo, "deltaX");
/* 141 */       double deltaY = getDouble(eventInfo, "deltaY");
/* 142 */       double deltaZ = getDouble(eventInfo, "deltaZ");
/* 143 */       _dispatchCocoaNpapiMouseEvent(windowPtr, type, modifierFlags, pluginX, pluginY, buttonNumber, clickCount, deltaX, deltaY, deltaZ);
/*     */ 
/* 147 */       break;
/*     */     case 8:
/*     */     case 9:
/*     */     case 10:
/* 151 */       int modifierFlags = getInt(eventInfo, "modifierFlags");
/* 152 */       String characters = getString(eventInfo, "characters");
/* 153 */       String charactersIgnoringModifiers = getString(eventInfo, "charactersIgnoringModifiers");
/* 154 */       boolean isARepeat = getBoolean(eventInfo, "isARepeat");
/* 155 */       int keyCode = getInt(eventInfo, "keyCode");
/* 156 */       boolean needsKeyTyped = getBoolean(eventInfo, "needsKeyTyped");
/*     */ 
/* 158 */       _dispatchCocoaNpapiKeyEvent(windowPtr, type, modifierFlags, characters, charactersIgnoringModifiers, isARepeat, keyCode, needsKeyTyped);
/*     */ 
/* 162 */       break;
/*     */     case 11:
/*     */     case 12:
/* 165 */       boolean hasFocus = getBoolean(eventInfo, "hasFocus");
/* 166 */       _dispatchCocoaNpapiFocusEvent(windowPtr, type, hasFocus);
/*     */ 
/* 169 */       break;
/*     */     case 14:
/* 171 */       String text = getString(eventInfo, "text");
/* 172 */       _dispatchCocoaNpapiTextInputEvent(windowPtr, type, text);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.events.NpapiEvent
 * JD-Core Version:    0.6.2
 */