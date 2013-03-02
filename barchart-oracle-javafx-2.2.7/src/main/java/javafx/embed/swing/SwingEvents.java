/*     */ package javafx.embed.swing;
/*     */ 
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ 
/*     */ class SwingEvents
/*     */ {
/*     */   static int mouseIDToEmbedMouseType(int paramInt)
/*     */   {
/*  42 */     switch (paramInt) {
/*     */     case 501:
/*  44 */       return 0;
/*     */     case 502:
/*  46 */       return 1;
/*     */     case 500:
/*  48 */       return 2;
/*     */     case 503:
/*  50 */       return 5;
/*     */     case 506:
/*  52 */       return 6;
/*     */     case 504:
/*  54 */       return 3;
/*     */     case 505:
/*  56 */       return 4;
/*     */     case 507:
/*  58 */       return 7;
/*     */     }
/*  60 */     return 0;
/*     */   }
/*     */ 
/*     */   static int mouseButtonToEmbedMouseButton(int paramInt1, int paramInt2) {
/*  64 */     int i = 0;
/*  65 */     switch (paramInt1) {
/*     */     case 1:
/*  67 */       i = 1;
/*  68 */       break;
/*     */     case 2:
/*  70 */       i = 4;
/*  71 */       break;
/*     */     case 3:
/*  73 */       i = 2;
/*     */     }
/*     */ 
/*  77 */     if ((paramInt2 & 0x400) != 0)
/*  78 */       i = 1;
/*  79 */     else if ((paramInt2 & 0x800) != 0)
/*  80 */       i = 4;
/*  81 */     else if ((paramInt2 & 0x1000) != 0) {
/*  82 */       i = 2;
/*     */     }
/*  84 */     return i;
/*     */   }
/*     */ 
/*     */   static int getWheelRotation(MouseEvent paramMouseEvent) {
/*  88 */     if ((paramMouseEvent instanceof MouseWheelEvent)) {
/*  89 */       return ((MouseWheelEvent)paramMouseEvent).getWheelRotation();
/*     */     }
/*  91 */     return 0;
/*     */   }
/*     */ 
/*     */   static int keyIDToEmbedKeyType(int paramInt) {
/*  95 */     switch (paramInt) {
/*     */     case 401:
/*  97 */       return 0;
/*     */     case 402:
/*  99 */       return 1;
/*     */     case 400:
/* 101 */       return 2;
/*     */     }
/* 103 */     return 0;
/*     */   }
/*     */ 
/*     */   static int keyModifiersToEmbedKeyModifiers(int paramInt) {
/* 107 */     int i = 0;
/* 108 */     if ((paramInt & 0x40) != 0) {
/* 109 */       i |= 1;
/*     */     }
/* 111 */     if ((paramInt & 0x80) != 0) {
/* 112 */       i |= 2;
/*     */     }
/* 114 */     if ((paramInt & 0x200) != 0) {
/* 115 */       i |= 4;
/*     */     }
/* 117 */     if ((paramInt & 0x100) != 0) {
/* 118 */       i |= 8;
/*     */     }
/* 120 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swing.SwingEvents
 * JD-Core Version:    0.6.2
 */