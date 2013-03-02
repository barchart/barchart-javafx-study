/*     */ package javafx.embed.swt;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import org.eclipse.swt.SWT;
/*     */ import org.eclipse.swt.events.MouseEvent;
/*     */ 
/*     */ class SWTEvents
/*     */ {
/* 118 */   static final int[][] KeyTable = { { 0, 0 }, { 10, 13 }, { 10, 10 }, { 8, 8 }, { 9, 9 }, { 27, 27 }, { 32, 32 }, { 127, 127 }, { 155, 16777225 }, { 156, 16777297 }, { 16, 131072 }, { 17, 262144 }, { 18, 65536 }, { 524, 4194304 }, { 20, 16777298 }, { 144, 16777299 }, { 145, 16777300 }, { 33, 16777221 }, { 34, 16777222 }, { 35, 16777224 }, { 36, 16777223 }, { 37, 16777219 }, { 38, 16777217 }, { 39, 16777220 }, { 40, 16777218 }, { 44, 44 }, { 45, 45 }, { 46, 46 }, { 47, 47 }, { 59, 59 }, { 61, 61 }, { 91, 91 }, { 92, 92 }, { 93, 93 }, { 106, 16777258 }, { 107, 16777259 }, { 109, 16777261 }, { 110, 16777262 }, { 111, 16777263 }, { 150, 64 }, { 151, 42 }, { 152, 34 }, { 153, 60 }, { 160, 62 }, { 161, 123 }, { 162, 125 }, { 192, 96 }, { 222, 39 }, { 512, 64 }, { 513, 58 }, { 514, 94 }, { 515, 36 }, { 517, 33 }, { 519, 40 }, { 520, 35 }, { 521, 43 }, { 522, 41 }, { 523, 95 }, { 48, 48 }, { 49, 49 }, { 50, 50 }, { 51, 51 }, { 52, 52 }, { 53, 53 }, { 54, 54 }, { 55, 55 }, { 56, 56 }, { 57, 57 }, { 65, 97 }, { 66, 98 }, { 67, 99 }, { 68, 100 }, { 69, 101 }, { 70, 102 }, { 71, 103 }, { 72, 104 }, { 73, 105 }, { 74, 106 }, { 75, 107 }, { 76, 108 }, { 77, 109 }, { 78, 110 }, { 79, 111 }, { 80, 112 }, { 81, 113 }, { 82, 114 }, { 83, 115 }, { 84, 116 }, { 85, 117 }, { 86, 118 }, { 87, 119 }, { 88, 120 }, { 89, 121 }, { 90, 122 }, { 96, 16777264 }, { 97, 16777265 }, { 98, 16777266 }, { 99, 16777267 }, { 100, 16777268 }, { 101, 16777269 }, { 102, 16777270 }, { 103, 16777271 }, { 104, 16777272 }, { 105, 16777273 }, { 112, 16777226 }, { 113, 16777227 }, { 114, 16777228 }, { 115, 16777229 }, { 116, 16777230 }, { 117, 16777231 }, { 118, 16777232 }, { 119, 16777233 }, { 120, 16777234 }, { 121, 16777235 }, { 122, 16777236 }, { 123, 16777237 } };
/*     */ 
/*     */   static int mouseButtonToEmbedMouseButton(int paramInt1, int paramInt2)
/*     */   {
/*  67 */     int i = 0;
/*  68 */     if ((paramInt1 == 1) || ((paramInt2 & 0x80000) != 0)) {
/*  69 */       i |= 1;
/*     */     }
/*  71 */     if ((paramInt1 == 2) || ((paramInt2 & 0x100000) != 0)) {
/*  72 */       i |= 4;
/*     */     }
/*  74 */     if ((paramInt1 == 3) || ((paramInt2 & 0x200000) != 0)) {
/*  75 */       i |= 2;
/*     */     }
/*  77 */     return i;
/*     */   }
/*     */ 
/*     */   static int getWheelRotation(MouseEvent paramMouseEvent, int paramInt) {
/*  81 */     int i = 1;
/*  82 */     if (paramInt == 7) {
/*  83 */       if ("win32".equals(SWT.getPlatform())) {
/*  84 */         int[] arrayOfInt = new int[1];
/*     */         try
/*     */         {
/*  87 */           Class localClass = Class.forName("org.eclipse.swt.internal.win32.OS");
/*  88 */           Method localMethod = localClass.getDeclaredMethod("SystemParametersInfo", new Class[] { Integer.TYPE, Integer.TYPE, [I.class, Integer.TYPE });
/*  89 */           localMethod.invoke(localClass, new Object[] { Integer.valueOf(104), Integer.valueOf(0), arrayOfInt, Integer.valueOf(0) });
/*     */         }
/*     */         catch (Exception localException) {
/*     */         }
/*  93 */         if (arrayOfInt[0] != -1) {
/*  94 */           i = arrayOfInt[0];
/*     */         }
/*     */       }
/*  97 */       else if ("gtk".equals(SWT.getPlatform())) {
/*  98 */         i = 3;
/*     */       }
/*     */ 
/* 101 */       return -paramMouseEvent.count / Math.max(1, i);
/*     */     }
/* 103 */     return 0;
/*     */   }
/*     */ 
/*     */   static int keyIDToEmbedKeyType(int paramInt) {
/* 107 */     switch (paramInt) {
/*     */     case 1:
/* 109 */       return 0;
/*     */     case 2:
/* 111 */       return 1;
/*     */     }
/*     */ 
/* 115 */     return 0;
/*     */   }
/*     */ 
/*     */   static int keyCodeToEmbedKeyCode(int paramInt)
/*     */   {
/* 284 */     for (int i = 0; i < KeyTable.length; i++) {
/* 285 */       if (KeyTable[i][1] == paramInt) return KeyTable[i][0];
/*     */     }
/* 287 */     return 0;
/*     */   }
/*     */ 
/*     */   static int keyModifiersToEmbedKeyModifiers(int paramInt) {
/* 291 */     int i = 0;
/* 292 */     if ((paramInt & 0x20000) != 0) {
/* 293 */       i |= 1;
/*     */     }
/* 295 */     if ((paramInt & 0x40000) != 0) {
/* 296 */       i |= 2;
/*     */     }
/* 298 */     if ((paramInt & 0x10000) != 0) {
/* 299 */       i |= 4;
/*     */     }
/*     */ 
/* 302 */     if ((paramInt & 0x400000) != 0) {
/* 303 */       i |= 8;
/*     */     }
/* 305 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swt.SWTEvents
 * JD-Core Version:    0.6.2
 */